/*
 * This file is generated by jOOQ.
*/
package com.kevindeyne.tasker.jooq.tables.records;


import com.kevindeyne.tasker.jooq.tables.ReleasesChangelog;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ReleasesChangelogRecord extends UpdatableRecordImpl<ReleasesChangelogRecord> implements Record3<Long, Long, Long> {

    private static final long serialVersionUID = -1439102961;

    /**
     * Setter for <code>taskr.releases_changelog.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>taskr.releases_changelog.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>taskr.releases_changelog.release_id</code>.
     */
    public void setReleaseId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>taskr.releases_changelog.release_id</code>.
     */
    public Long getReleaseId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>taskr.releases_changelog.issue_id</code>.
     */
    public void setIssueId(Long value) {
        set(2, value);
    }

    /**
     * Getter for <code>taskr.releases_changelog.issue_id</code>.
     */
    public Long getIssueId() {
        return (Long) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, Long, Long> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Long, Long, Long> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return ReleasesChangelog.RELEASES_CHANGELOG.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return ReleasesChangelog.RELEASES_CHANGELOG.RELEASE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field3() {
        return ReleasesChangelog.RELEASES_CHANGELOG.ISSUE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component2() {
        return getReleaseId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component3() {
        return getIssueId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getReleaseId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value3() {
        return getIssueId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReleasesChangelogRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReleasesChangelogRecord value2(Long value) {
        setReleaseId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReleasesChangelogRecord value3(Long value) {
        setIssueId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReleasesChangelogRecord values(Long value1, Long value2, Long value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ReleasesChangelogRecord
     */
    public ReleasesChangelogRecord() {
        super(ReleasesChangelog.RELEASES_CHANGELOG);
    }

    /**
     * Create a detached, initialised ReleasesChangelogRecord
     */
    public ReleasesChangelogRecord(Long id, Long releaseId, Long issueId) {
        super(ReleasesChangelog.RELEASES_CHANGELOG);

        set(0, id);
        set(1, releaseId);
        set(2, issueId);
    }
}
