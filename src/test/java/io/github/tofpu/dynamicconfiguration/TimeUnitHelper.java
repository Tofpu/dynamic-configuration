package io.github.tofpu.dynamicconfiguration;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class TimeUnitHelper {

    // credit: https://stackoverflow.com/a/72757001
    public static ChronoUnit toChronoUnit(final TimeUnit timeUnit) {
        switch (timeUnit) {
            case NANOSECONDS:  return ChronoUnit.NANOS;
            case MICROSECONDS: return ChronoUnit.MICROS;
            case MILLISECONDS: return ChronoUnit.MILLIS;
            case SECONDS:      return ChronoUnit.SECONDS;
            case MINUTES:      return ChronoUnit.MINUTES;
            case HOURS:        return ChronoUnit.HOURS;
            case DAYS:         return ChronoUnit.DAYS;
            default: throw new AssertionError();
        }
    }

}
