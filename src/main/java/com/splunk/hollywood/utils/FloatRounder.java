package com.splunk.hollywood.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FloatRounder {
    private FloatRounder() {
    }

    public  static String floor(float input) {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(input);
    }
}
