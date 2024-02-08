package cft.test.task;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.List;

class FilesFilter {

    private static final String DEFAULT_INTS_OUTPUT = "integers.txt";
    private static final String DEFAULT_FLOATS_OUTPUT = "floats.txt";
    private static final String DEFAULT_STRINGS_OUTPUT = "strings.txt";
    private final Path intsOutputPath;
    private final Path floatsOutputPath;
    private final Path stringsOutputPath;
    private final Options options;
    private BigInteger maxInt;
    private BigInteger minInt;
    private BigInteger intsSum;
    private BigDecimal intsAvr;
    private BigDecimal maxFloat;
    private BigDecimal minFloat;
    private BigDecimal floatsSum;
    private BigDecimal floatsAvr;
    private Integer maxStrLength;
    private Integer minStrLength;
    private Integer amountInts;
    private Integer amountFloats;
    private Integer amountStrings;

    FilesFilter(Options options) {
        this.options = options;

        String resultPath = options.outputPath().toString();
        intsOutputPath = Path.of(resultPath, options.namesPrefix() + DEFAULT_INTS_OUTPUT);
        floatsOutputPath = Path.of(resultPath, options.namesPrefix() + DEFAULT_FLOATS_OUTPUT);
        stringsOutputPath = Path.of(resultPath, options.namesPrefix() + DEFAULT_STRINGS_OUTPUT);
    }

    void filter(List<String> integers, List<String> floats, List<String> strings) {
        filterInputFile(integers, floats, strings);
        writeFilteredInfo(integers, floats, strings);
    }

    void printFullStatistics() {
        String strMaxInt = maxInt != null ? maxInt.toString() : "no max";
        String strMinInt = minInt != null ? minInt.toString() : "no min";

        String strIntsSum = intsSum.toString();
        String strIntsAvr = intsAvr == null ? "no avr" : intsAvr.toString();

        String strMaxFloat = maxFloat != null ? maxFloat.toString() : "no max";
        String strMinFloat = minFloat != null ? minFloat.toString() : "no min";

        String strFloatsSum = floatsSum.toString();
        String strFloatsAvr = floatsAvr == null ? "no avr" : floatsAvr.toString();

        String strMinStrLength = minStrLength != null ? minStrLength.toString() : "no min";
        String strMaxStrLength = maxStrLength != null ? maxStrLength.toString() : "no max";

        String fullIntegersStatistics = String.format("Integers:\nAmount:\t%s\nMin:\t%s\nMax:\t%s\nSum:\t%s\nAvr:\t%s\n",
                amountInts, strMinInt, strMaxInt, strIntsSum, strIntsAvr);

        String fullFloatsStatistics = String.format("Floats:\nAmount:\t%s\nMin:\t%s\nMax:\t%s\nSum:\t%s\nAvr:\t%s\n",
                amountFloats, strMinFloat, strMaxFloat, strFloatsSum, strFloatsAvr);

        String fullStringsStatistics = String.format("Strings:\nAmount:\t%s\nMin length:\t%s\nMax length:\t%s\n",
                amountStrings, strMinStrLength, strMaxStrLength);

        System.out.println("-----------------------\nFull statistics:\n");
        System.out.println(fullIntegersStatistics + '\n' + fullFloatsStatistics + '\n' + fullStringsStatistics);
    }

    void printShortStatistics() {
        System.out.println("-----------------------\nShort statistics:\n");
        String shortStatistics = String.format("Amount of integers:\t%d\nAmount of floats:\t%d\nAmount of strings:\t%d",
                amountInts, amountFloats, amountStrings);

        System.out.println(shortStatistics);
    }

    private void filterInputFile(List<String> integers, List<String> floats, List<String> strings) {
        List<Path> inputFilesPaths = options.inputFilesPaths();
        fillStatisticsBeginningVals();
        for (Path inputFilePath : inputFilesPaths) {
            try {
                List<String> allFileLines = Files.readAllLines(inputFilePath);
                for (String line : allFileLines) {
                    if (isInteger(line)) {
                        BigInteger newInt = new BigInteger(line);
                        detectNewMaxAndMinInt(newInt);
                        intsSum = intsSum.add(newInt);
                        integers.add(line);
                        continue;
                    }
                    if (isFloat(line)) {
                        BigDecimal newFloat = new BigDecimal(line);
                        detectNewMaxAndMinFloat(newFloat);
                        floatsSum = floatsSum.add(newFloat);
                        floats.add(line);
                        continue;
                    }
                    detectNewMaxAndMinString(line);
                    strings.add(line);

                }
            } catch (IOException e) {
                //TODO
                System.out.println(e.getMessage());
            }
        }
        if (!integers.isEmpty()) {
            BigDecimal decIntsSum = new BigDecimal(intsSum.toString());
            intsAvr = decIntsSum.divide(BigDecimal.valueOf(integers.size()), MathContext.UNLIMITED);
        }
        if (!floats.isEmpty()) {
            floatsAvr = floatsSum.divide(BigDecimal.valueOf(floats.size()), RoundingMode.HALF_UP);
        }
        amountInts = integers.size();
        amountFloats = floats.size();
        amountStrings = strings.size();
    }

    private void fillStatisticsBeginningVals() {
        maxInt = null;
        minInt = null;
        minFloat = null;
        maxFloat = null;
        maxStrLength = null;
        minStrLength = null;
        intsAvr = null;
        floatsAvr = null;
        intsSum = BigInteger.ZERO;
        floatsSum = BigDecimal.ZERO;
    }

    private void detectNewMaxAndMinString(String line) {
        if (maxStrLength == null && minStrLength == null) {
            maxStrLength = line.length();
            minStrLength = line.length();
        } else {
            int strLength = line.length();
            if (strLength < minStrLength) {
                minStrLength = strLength;
            }
            if (strLength > maxStrLength) {
                maxStrLength = strLength;
            }
        }
    }

    private void detectNewMaxAndMinInt(BigInteger newInt) {
        if (maxInt == null && minInt == null) {
            maxInt = newInt;
            minInt = newInt;
        } else {
            if (newInt.compareTo(maxInt) > 0) {
                maxInt = newInt;
            }
            if (newInt.compareTo(minInt) < 0) {
                minInt = newInt;
            }
        }
    }

    private void detectNewMaxAndMinFloat(BigDecimal newFloat) {
        if (maxFloat == null && minFloat == null) {
            maxFloat = newFloat;
            minFloat = newFloat;
        } else {
            if (newFloat.compareTo(maxFloat) > 0) {
                maxFloat = newFloat;
            }
            if (newFloat.compareTo(minFloat) < 0) {
                minFloat = newFloat;
            }
        }
    }

    private void writeFilteredInfo(List<String> integers, List<String> floats, List<String> strings) {
        try {
            if (!integers.isEmpty()) {
                prepareOutputFile(intsOutputPath);
                Files.write(intsOutputPath, integers, StandardOpenOption.APPEND);
            }
            if (!floats.isEmpty()) {
                prepareOutputFile(floatsOutputPath);
                Files.write(floatsOutputPath, floats, StandardOpenOption.APPEND);
            }
            if (!strings.isEmpty()) {
                prepareOutputFile(stringsOutputPath);
                Files.write(stringsOutputPath, strings, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            //TODO
            System.out.println(e.getMessage());
        }
    }

    private void prepareOutputFile(Path outputPath) throws IOException {
        if (Files.notExists(outputPath)) {
            Files.createFile(outputPath);
        } else {
            if (options.rewriteFiles()) {
                clearFileContent(outputPath);
            }
        }
    }

    private void clearFileContent(Path path) throws IOException {
        FileChannel.open(path, StandardOpenOption.WRITE).truncate(0).close();
    }

    private boolean isInteger(String str) {
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

    private boolean isFloat(String str) {
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
}
