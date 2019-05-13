package pl.masi.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Range<T extends Number & Comparable<T>> {
    private T min;
    private T max;
    private T step;

    public Range() {
    }

    public Range(T min, T max, T step) {
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public boolean contains(T value) {
        if (value.compareTo(min) >= 0 && value.compareTo(max) <= 0) {
            return value.doubleValue() % step.doubleValue() == 0;
        }
        return false;
    }

}
