package org.geotools.filter.function;

/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2005-2008, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */

// this code is autogenerated - you shouldnt be modifying it!

import static org.geotools.filter.capability.FunctionNameImpl.parameter;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import org.geotools.filter.FunctionExpressionImpl;
import org.geotools.filter.capability.FunctionNameImpl;
import org.opengis.filter.capability.FunctionName;

/** @source $URL$ */
public class FilterFunction_endPoint extends FunctionExpressionImpl {

    public static FunctionName NAME =
            new FunctionNameImpl(
                    "endPoint", Point.class, parameter("linestring", LineString.class));

    public FilterFunction_endPoint() {
        super(NAME);
    }

    public Object evaluate(Object feature) {
        Geometry arg0;

        try { // attempt to get value and perform conversion
            arg0 = getExpression(0).evaluate(feature, LineString.class);
        } catch (Exception e) // probably a type error
        {
            throw new IllegalArgumentException(
                    "Filter Function problem for function endPoint argument #0 - expected type Geometry");
        }

        return (StaticGeometry.endPoint(arg0));
    }
}
