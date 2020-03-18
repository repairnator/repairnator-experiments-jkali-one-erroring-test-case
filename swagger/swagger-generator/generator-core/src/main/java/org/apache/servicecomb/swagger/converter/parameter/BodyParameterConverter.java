/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.swagger.converter.parameter;

import org.apache.servicecomb.swagger.converter.Converter;
import org.apache.servicecomb.swagger.converter.ConverterMgr;

import com.fasterxml.jackson.databind.JavaType;

import io.swagger.models.Swagger;
import io.swagger.models.parameters.BodyParameter;

public class BodyParameterConverter implements Converter {

  @Override
  public JavaType convert(ClassLoader classLoader, String packageName, Swagger swagger, Object def) {
    BodyParameter param = (BodyParameter) def;
    return ConverterMgr.findJavaType(classLoader, packageName, swagger, param.getSchema());
  }
}
