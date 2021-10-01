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
package com.epam.deltix.data.stream;

import com.epam.deltix.timebase.messages.TimeStampedMessage;
import com.epam.deltix.util.lang.MathUtil;
import java.util.Comparator;

/**
 *
 */
public class TimeMsgComparator 
    implements Comparator <TimeStampedMessage>
{
    private TimeMsgComparator () { }
    
    public static final Comparator <TimeStampedMessage> INSTANCE =
        new TimeMsgComparator ();
    
    public int      compare (
        TimeStampedMessage o1,
        TimeStampedMessage o2
    )
    {
        return (MathUtil.compare (o1.getNanoTime(), o2.getNanoTime()));
    }
}