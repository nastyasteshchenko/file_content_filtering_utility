package cft.test.task;

import java.math.BigDecimal;
import java.math.BigInteger;

public record Statistics(String maxInt, String minInt, String intsSum, String intsAvr,
                         String maxFloat, String minFloat, String floatsSum, String floatsAvr, String maxStrLength,
                         String minStrLength, String amountInts, String amountFloats, String amountStrings) {

    static class Builder {
        private String maxInt = "no max";
        private String minInt = "no min";
        private String intsSum = "0";
        private String intsAvr = "no avr";
        private String maxFloat = "no max";
        private String minFloat = "no min";
        private String floatsSum = "0";
        private String floatsAvr = "no avr";
        private String maxStrLength = "no max";
        private String minStrLength = "no min";
        private String amountInts = "0";
        private String amountFloats = "0";
        private String amountStrings = "0";

        Builder amountInts(Integer amountInts) {
            if (amountInts != null) {
                this.amountInts = String.valueOf(amountInts);
            }
            return this;
        }

        Builder amountFloats(Integer amountFloats) {
            if (amountFloats != null) {
                this.amountFloats = String.valueOf(amountFloats);
            }
            return this;
        }

        Builder amountStrings(Integer amountStrings) {
            if (amountStrings != null) {
                this.amountStrings = String.valueOf(amountStrings);
            }
            return this;
        }

        Builder maxStrLength(Integer maxStrLength) {
            if (maxStrLength != null) {
                this.maxStrLength = String.valueOf(maxStrLength);
            }
            return this;
        }

        Builder minStrLength(Integer minStrLength) {
            if (minStrLength != null) {
                this.minStrLength = String.valueOf(minStrLength);
            }
            return this;
        }

        Builder maxInt(BigInteger maxInt) {
            if (maxInt != null) {
                this.maxInt = maxInt.toString();
            }
            return this;
        }

        Builder minInt(BigInteger minInt) {
            if (minInt != null) {
                this.minInt = minInt.toString();
            }
            return this;
        }

        Builder maxFloat(BigDecimal maxFloat) {
            if (maxFloat != null) {
                this.maxFloat = maxFloat.toString();
            }
            return this;
        }

        Builder minFloat(BigDecimal minFloat) {
            if (minFloat != null) {
                this.minFloat = minFloat.toString();
            }
            return this;
        }

        Builder intsSum(BigInteger intsSum) {
            if (intsSum != null) {
                this.intsSum = intsSum.toString();
            }
            return this;
        }

        Builder floatsSum(BigDecimal floatsSum) {
            if (floatsSum != null) {
                this.floatsSum = floatsSum.toString();
            }
            return this;
        }

        Builder intsAvr(BigDecimal intsAvr) {
            if (intsAvr != null) {
                this.intsAvr = intsAvr.toString();
            }
            return this;
        }

        Builder floatsAvr(BigDecimal floatsAvr) {
            if (floatsAvr != null) {
                this.floatsAvr = floatsAvr.toString();
            }
            return this;
        }

        Statistics build() {
            return new Statistics(maxInt, minInt, intsSum, intsAvr, maxFloat, minFloat, floatsSum, floatsAvr, maxStrLength,
                    minStrLength, amountInts, amountFloats, amountStrings);
        }

    }
}
