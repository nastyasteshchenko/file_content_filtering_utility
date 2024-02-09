package cft.test.task;

import java.math.BigDecimal;
import java.math.BigInteger;

class NumbersUtils {
    private NumbersUtils() {
    }

    static boolean isInteger(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e1) {
            try {
                new BigInteger(str);
                return true;
            } catch (NumberFormatException e2) {
                return false;
            }
        }
    }

    static boolean isFloat(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            try {
                new BigDecimal(str);
                return true;
            } catch (NumberFormatException e2) {
                return false;
            }
        }
    }

    static Number max(Number a, Number b) {
        if (a instanceof BigInteger ai) {
            if (b instanceof BigInteger bi) {
                return ai.compareTo(bi) > 0 ? ai : bi;
            }
        }
        if (a instanceof BigDecimal ad) {
            if (b instanceof BigDecimal bd) {
                return ad.compareTo(bd) > 0 ? ad : bd;
            }
        }
        return null;
    }

    static Number min(Number a, Number b) {
        if (a instanceof BigInteger ai) {
            if (b instanceof BigInteger bi) {
                return ai.compareTo(bi) < 0 ? ai : bi;
            }
        }
        if (a instanceof BigDecimal ad) {
            if (b instanceof BigDecimal bd) {
                return ad.compareTo(bd) < 0 ? ad : bd;
            }
        }
        return null;
    }
}
