/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.jackrabbit.oak.plugins.blob.datastore.directaccess;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Optional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.google.common.base.Joiner;
import org.apache.jackrabbit.oak.spi.blob.AbstractSharedBackend;
import org.apache.jackrabbit.util.Base64;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an upload token returned by
 * {@link DataRecordAccessProvider#initiateDataRecordUpload(long, int)} and
 * used in subsequent calls to {@link
 * DataRecordAccessProvider#completeDataRecordUpload(String)}.  This class
 * handles creation, signing, and parsing of the token and uses a provided
 * secret key to sign the contents of the token and to validate contents of
 * tokens.
 */
public class DataRecordUploadToken {
    private static Logger LOG = LoggerFactory.getLogger(DataRecordUploadToken.class);

    private String blobId;
    private Optional<String> uploadId;

    /**
     * Create an upload token from the provided {@code blobId} and {@code
     * uploadId}.  At creation time the token is not encoded or signed; to do
     * that call {@link #getEncodedToken(byte[])} after creating the token.
     *
     * @param blobId The blob ID, usually a {@link
     *        org.apache.jackrabbit.core.data.DataIdentifier}.
     * @param uploadId A free-form string used to identify this upload.  This
     *        may be provided by the service provider; if not a free-form
     *        upload ID generated by the implementation will suffice.  May be
     *        {@code null} if no upload ID is available.  However, some service
     *        providers will require an upload ID to complete the upload so be
     *        sure to check whether the service provider API provides one and
     *        use that if it is available.
     */
    public DataRecordUploadToken(@NotNull String blobId, @Nullable String uploadId) {
        this.blobId = blobId;
        this.uploadId = Optional.ofNullable(uploadId);
    }

    /**
     * Create an upload token instance from the provided encoded token string,
     * using the provided secret key to verify the string.  The encoded token
     * string should have been created originally by a prior call to {@link
     * #getEncodedToken(byte[])}.
     * <p>
     * This method will parse and validate the contents of the provided encoded
     * token string.  An instance of this class is returned if the parsing and
     * validation is successful.
     * <p>
     * A secret key is required to verify the encoded token.  You are strongly
     * encouraged to use the secret key used by the data store backend
     * implementation.  This key can be obtained by calling {@link
     * AbstractSharedBackend#getOrCreateReferenceKey()}.
     *
     * @param encoded The encoded, signed token string.
     * @param secret The secret key to be used to verify the contents of the
     *         token string.
     * @return A new instance containing the parsed upload token propreties.
     * @throws IllegalArgumentException if the token string cannot be parsed or
     *         if validation fails.
     */
    public static DataRecordUploadToken fromEncodedToken(@NotNull String encoded, @NotNull byte[] secret)
            throws IllegalArgumentException {
        String[] parts = encoded.split("#", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Encoded string is missing the signature");
        }

        String toBeDecoded = parts[0];
        String expectedSig = Base64.decode(parts[1]);
        String actualSig = getSignedString(toBeDecoded, secret);
        if (!expectedSig.equals(actualSig)) {
            throw new IllegalArgumentException("Upload token signature does not match");
        }

        String decoded = Base64.decode(toBeDecoded);
        String decodedParts[] = decoded.split("#");
        if (decodedParts.length < 2) {
            throw new IllegalArgumentException("Not all upload token parts provided");
        }

        return new DataRecordUploadToken(decodedParts[0], decodedParts.length > 2 ? decodedParts[2] : null);
    }

    /**
     * Generate an encoded, signed token string from this instance.  The
     * resulting token can later be parsed and validated by {@link
     * #fromEncodedToken(String, byte[])}.
     * <p>
     * A secret key is required to generate the encoded token.  You are strongly
     * encouraged to use the secret key used by the data store backend
     * implementation.  This key can be obtained by calling {@link
     * AbstractSharedBackend#getOrCreateReferenceKey()}.
     *
     * @param secret The secret key used to sign the contents of the token.
     * @return An encoded token string that can later be used to uniquely and
     *         securely identify an upload.
     */
    public String getEncodedToken(@NotNull byte[] secret) {
        String now = Instant.now().toString();
        String toBeEncoded = uploadId.isPresent() ?
                Joiner.on("#").join(blobId, now, uploadId.get()) :
                Joiner.on("#").join(blobId, now);
        String toBeSigned = Base64.encode(toBeEncoded);

        String sig = getSignedString(toBeSigned, secret);
        return sig != null ? Joiner.on("#").join(toBeSigned, sig) : toBeSigned;
    }

    private static String getSignedString(String toBeSigned, byte[] secret) {
        try {
            final String algorithm = "HmacSHA1";
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secret, algorithm));
            byte[] hash = mac.doFinal(toBeSigned.getBytes());
            return new String(hash);
        }
        catch (NoSuchAlgorithmException | InvalidKeyException e) {
            LOG.warn("Could not sign upload token", e);
        }
        return null;
    }

    /**
     * Returns the blob ID of this instance.
     *
     * @return The blob ID.
     */
    public String getBlobId() {
        return blobId;
    }

    /**
     * Returns the upload ID of this instance.
     *
     * @return The upload ID.
     */
    public Optional<String> getUploadId() {
        return uploadId;
    }
}
