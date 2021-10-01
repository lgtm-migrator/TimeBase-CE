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
package com.epam.deltix.qsrv.hf.tickdb.lang.compiler.cg;

import com.epam.deltix.util.jcg.*;

import java.lang.reflect.Modifier;

/**
 *
 */
public class QVariableContainer {
    protected final int                 modifiers;
    protected final JVariableContainer  container;
    private final String                prefix;
    private final JExpr                 accessExpr;
    private int                         counter = 1;

    public QVariableContainer (
        int                     modifiers,
        JVariableContainer      container,
        JExpr                   accessExpr,
        String                  prefix
    )
    {
        this.modifiers = modifiers;
        this.container = container;
        this.accessExpr = accessExpr;
        this.prefix = prefix;
    }

    public JExpr                access (JVariable v) {
        if (v instanceof JLocalVariable)
            return ((JLocalVariable) v);
        else if (v instanceof JMemberVariable) {
            JMemberVariable     mv = (JMemberVariable) v;

            return (accessExpr == null ? mv.access () : mv.access (accessExpr));
        }
        else
            throw new RuntimeException (v.getClass ().getSimpleName ());
    }

    public JVariable            addVar (
        String                      comment,
        boolean                     forceFinal, 
        Class <?>                   type, 
        JExpr                       initValue
    )
    {
        int         m = modifiers;
        
        if (forceFinal)
            m |= Modifier.FINAL;
        
        if (comment != null)
            container.addComment (comment);
                
        return (container.addVar (m, type, prefix + (counter++), initValue));
    }

    public JVariable addVar(String comment, boolean forceFinal, Class<?> type, Class<?>[] typeArgs, JExpr initValue) {
        int m = modifiers;
        if (forceFinal)
            m |= Modifier.FINAL;

        if (comment != null)
            container.addComment(comment);

        return container.addVar(m, type, typeArgs,prefix + counter++, initValue);
    }
}