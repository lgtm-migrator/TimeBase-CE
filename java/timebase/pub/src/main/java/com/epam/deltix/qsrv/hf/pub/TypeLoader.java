/*
 * Copyright 2021 EPAM Systems, Inc
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.epam.deltix.qsrv.hf.pub;

import com.epam.deltix.qsrv.hf.pub.md.ClassDescriptor;

/**
 * Class factory. Used by bound codecs.
 * <p>
 * An implementation is responsible for handling both user-defined and "pre-existent" types.
 * Delegate to <code>TypeLoaderImpl.DEFAULT_INSTANCE</code> handling of types you don't know about.
 * </p>
 *
 */
public interface TypeLoader {
    /** 
     * @return Class for type specified by given descriptor (never null).
     * Implementation of this function must return the same class for identical class descriptors (i.e. must be "pure function").
     * @throws ClassNotFoundException
     */
    public Class<?> load(ClassDescriptor cd) throws ClassNotFoundException;
}