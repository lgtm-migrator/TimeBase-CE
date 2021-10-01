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
package com.epam.deltix.computations.api;

import com.epam.deltix.qsrv.hf.pub.md.TimebaseTypes;

/**
 * Implementation of {@link TimeSaver}, that cuts whole interval on pretty intervals
 * according to time step and time range.
 */
public class PrettyTimeSaver implements TimeSaver {

    private long step = TimebaseTypes.DATETIME_NULL;
    private long start = TimebaseTypes.DATETIME_NULL;
    private long end = TimebaseTypes.DATETIME_NULL;
    private long last = TimebaseTypes.DATETIME_NULL;
    private long ready = TimebaseTypes.DATETIME_NULL;

    private PrettyTimeSaver() { }

    private PrettyTimeSaver(long start, long end, long step) {
        this.start = start;
        this.end = end;
        this.step = step;
    }

    public static PrettyTimeSaver create() {
        return new PrettyTimeSaver();
    }

    public static PrettyTimeSaver create(long start, long step) {
        assert step > 0 && start >= 0;
        long nearest = nearest(start, step);
        return nearest == start ? new PrettyTimeSaver(start - step, start, step) : new PrettyTimeSaver(start, nearest, step);
    }

    @Override
    public long put(long timestamp) {
        last = timestamp;
        if (timestamp > end) {
            ready = end;
            long d = (timestamp - end - 1) / step;
            start = end + step * d;
            end = end + step * (d + 1);
            return d;
        }
        return -1;
    }

    @Override
    public long getStart() {
        return start;
    }

    @Override
    public long getEnd() {
        return end;
    }

    @Override
    public long getStep() {
        return step;
    }

    @Override
    public long getLast() {
        return last;
    }

    @Override
    public long getReady() {
        return ready;
    }

    @Override
    public void reset(long start, long step) {
        assert step > 0 && start >= 0;
        long nearest = nearest(start, step);
        if (nearest == start) {
            this.start = start - step;
            this.end = start;
            this.step = step;
        } else {
            this.start = start;
            this.end = nearest;
            this.step = step;
        }
    }

    private static long nearest(long timestamp, long interval) {
        long d = timestamp % interval;
        return d == 0 ? timestamp: timestamp - d + interval;
    }
}
