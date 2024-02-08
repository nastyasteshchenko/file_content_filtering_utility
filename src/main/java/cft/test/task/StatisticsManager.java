package cft.test.task;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

class StatisticsManager {

    private final List<BigInteger> integers;
    private final List<BigDecimal> floats;
    private final List<String> strings;
    private final String minInts;
    private final String maxInts;
    private final String minFloats;
    private final String maxFloats;
    private final String intsSum;
    private final String floatsSum;
    private final String intsAvr;
    private final String floatsAvr;

//    Полная статистика для чисел
//    дополнительно содержит минимальное и максимальное значения, сумма и среднее.
//    Полная статистика для строк, помимо их количества, содержит также размер самой
//    короткой строки и самой длинной.

    StatisticsManager(List<String> integers, List<String> floats, List<String> strings) {
        this.integers = integers.stream().map(BigInteger::new).toList();
        this.floats = floats.stream().map(BigDecimal::new).toList();
        this.strings = strings;

        Optional<BigInteger> opMaxInts = this.integers.stream().max(BigInteger::compareTo);
        Optional<BigInteger> opMinInts = this.integers.stream().min(BigInteger::compareTo);
        maxInts = opMaxInts.isPresent() ? opMaxInts.get().toString() : "no max";
        minInts = opMinInts.isPresent() ? opMinInts.get().toString() : "no min";
        BigInteger biIntsSum = this.integers.stream().reduce(BigInteger.ZERO, BigInteger::add);
        intsSum = biIntsSum.toString();
        intsAvr = biIntsSum.divide(BigInteger.valueOf(this.integers.size())).toString();

        Optional<BigDecimal> opMaxFloats = this.floats.stream().max(BigDecimal::compareTo);
        Optional<BigDecimal> opMinFloats = this.floats.stream().min(BigDecimal::compareTo);
        maxFloats = opMaxFloats.isPresent() ? opMaxFloats.get().toString() : "no max";
        minFloats = opMinFloats.isPresent() ? opMinFloats.get().toString() : "no min";
        BigDecimal bdFloatsSum = this.floats.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        floatsSum = bdFloatsSum.toString();
        floatsAvr = bdFloatsSum.divide(BigDecimal.valueOf(this.floats.size()), RoundingMode.UNNECESSARY).toString();

    }

    void printFullStatistics() {
        String fullIntegersStatistics = String.format("Integers:\nMin:\t%s\nMax:\t%s\nSum:\t%s\nAvr:\t%s\n",
                minInts, maxInts, intsSum, intsAvr);

        String fullFloatsStatistics = String.format("Floats:\nMin:\t%s\nMax:\t%s\nSum:\t%s\nAvr:\t%s\n",
                minFloats, maxFloats, floatsSum, floatsAvr);

        System.out.println(fullIntegersStatistics + fullFloatsStatistics);
    }

    void printShortStatistics() {
        String shortStatistics = String.format("Short statistics:\nAmount of integers:\t%d\nAmount of floats:\t%d\nAmount of strings:\t%d",
                integers.size(), floats.size(), strings.size());

        System.out.println(shortStatistics);
    }
}
