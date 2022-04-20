package com.epam.deltix.computations.finanalysis;

import com.epam.deltix.computations.finanalysis.util.AdxrProcessor;
import com.epam.deltix.computations.api.annotations.*;
import com.epam.deltix.computations.api.generated.ObjectStatefulFunctionBase;
import com.epam.deltix.computations.messages.ADXRMessage;

import javax.naming.OperationNotSupportedException;

@Function("ADXR")
public class Adxr extends ObjectStatefulFunctionBase<ADXRMessage> implements BarFunction {

    private int period;
    private boolean reset;
    private AdxrProcessor adxr;

    public Adxr() {
        super(ADXRMessage::new);
    }

    @Init
    public void init(@Arg(defaultValue = "14") int period, @Arg(defaultValue = "false") boolean reset) {
        this.period = period;
        this.reset = reset;
        this.adxr = new AdxrProcessor(period);
    }

    @Compute
    @Override
    public void set(@BuiltInTimestampMs long timestamp, double open, double high, double low, double close, double volume) {
        try {
            adxr.add(open, high, low, close, volume, timestamp);
            buffer.setAdxr(adxr.adxr);
            buffer.setAdx(adxr.adx);
            buffer.setDx(adxr.dx);
            buffer.setMinusDI(adxr.minusDI);
            buffer.setPlusDI(adxr.plusDI);
            if (value == null) {
                value = buffer;
            }
        } catch (OperationNotSupportedException ignored) {
        }
    }

    @Result
    @Type("OBJECT(com.epam.deltix.computations.messages.ADXRMessage)")
    @Override
    public ADXRMessage get() {
        return super.get();
    }

    @Reset
    @Override
    public void reset() {
        if (reset) {
            adxr = new AdxrProcessor(period);
        }
        super.reset();
    }
}

