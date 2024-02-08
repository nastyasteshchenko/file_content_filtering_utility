package cft.test.task;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

class StatisticsManager {
     private final List<String> strIntegers;
    private final List<String> strFloats;
    private final List<String> strings;

    StatisticsManager(List<String> integers, List<String> floats, List<String> strings) {
        this.strIntegers= integers;
        this.strFloats = floats;
        this.strings = strings;
    }

    void countAndPrintFullStatistics() {
        List<BigInteger> integers = strIntegers.stream().map(BigInteger::new).toList();
        List<BigDecimal> floats = strFloats.stream().map(BigDecimal::new).toList();

        Optional<BigInteger> opMaxInts = integers.stream().max(BigInteger::compareTo);
        Optional<BigInteger> opMinInts = integers.stream().min(BigInteger::compareTo);

        String maxInts = opMaxInts.isPresent() ? opMaxInts.get().toString() : "no max";
        String minInts = opMinInts.isPresent() ? opMinInts.get().toString() : "no min";

        BigInteger biIntsSum = integers.stream().reduce(BigInteger.ZERO, BigInteger::add);

        String intsSum = biIntsSum.toString();
        String intsAvr = integers.isEmpty() ? "no avr" : biIntsSum.divide(BigInteger.valueOf(integers.size())).toString();

        Optional<BigDecimal> opMaxFloats = floats.stream().max(BigDecimal::compareTo);
        Optional<BigDecimal> opMinFloats = floats.stream().min(BigDecimal::compareTo);

        String maxFloats = opMaxFloats.isPresent() ? opMaxFloats.get().toString() : "no max";
        String minFloats = opMinFloats.isPresent() ? opMinFloats.get().toString() : "no min";

        BigDecimal bdFloatsSum = floats.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        String floatsSum = bdFloatsSum.toString();
        String floatsAvr = floats.isEmpty() ? "no avr" : bdFloatsSum.divide(BigDecimal.valueOf(floats.size()), RoundingMode.UNNECESSARY).toString();

        Optional<String> opMinStrLength = strings.stream().min(Comparator.comparingInt(String::length));
        Optional<String> opMaxStrLength = strings.stream().max(Comparator.comparingInt(String::length));
        String minStrLength = opMinStrLength.map(s -> String.valueOf(s.length())).orElse("no min");
        String maxStrLength = opMaxStrLength.map(s -> String.valueOf(s.length())).orElse("no max");

        String fullIntegersStatistics = String.format("Integers:\nAmount:\t%s\nMin:\t%s\nMax:\t%s\nSum:\t%s\nAvr:\t%s\n",
                integers.size(), minInts, maxInts, intsSum, intsAvr);

        String fullFloatsStatistics = String.format("Floats:\nAmount:\t%s\nMin:\t%s\nMax:\t%s\nSum:\t%s\nAvr:\t%s\n",
                floats.size(), minFloats, maxFloats, floatsSum, floatsAvr);

        String fullStringsStatistics = String.format("Strings:\nAmount:\t%s\nMin length:\t%s\nMax length:\t%s\n",
                strings.size(), minStrLength, maxStrLength);

        System.out.println("-----------------------\nFull statistics:\n");
        System.out.println(fullIntegersStatistics + '\n' + fullFloatsStatistics + '\n' + fullStringsStatistics);
    }

    void countAndPrintShortStatistics() {
        System.out.println("-----------------------\nShort statistics:\n");
        String shortStatistics = String.format("Amount of integers:\t%d\nAmount of floats:\t%d\nAmount of strings:\t%d",
                strIntegers.size(), strFloats.size(), strings.size());

        System.out.println(shortStatistics);
    }
}
