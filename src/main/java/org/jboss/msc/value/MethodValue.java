/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.msc.value;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public final class MethodValue<T> implements Value<T> {
    private final Value<Method> methodValue;
    private final Value<?> targetValue;
    private final List<? extends Value<?>> parameters;

    public MethodValue(final Value<Method> methodValue, final Value<?> targetValue, final List<? extends Value<?>> parameters) {
        this.methodValue = methodValue;
        this.targetValue = targetValue;
        this.parameters = parameters;
    }

    @SuppressWarnings({ "unchecked" })
    public T getValue() throws IllegalStateException {
        try {
            return (T) methodValue.getValue().invoke(targetValue.getValue(), Values.getValues(parameters));
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Field is not accessible", e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("Failed to invoke method", e);
        }
    }
}