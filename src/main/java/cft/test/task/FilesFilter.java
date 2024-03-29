package cft.test.task;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.List;

import static cft.test.task.NumbersUtils.*;

class FilesFilter {

    private static final String WRITING_OUTPUT_ERROR = "Error while writing to file ";
    private static final String READING_INPUT_ERROR = "Error while reading file ";
    private static final String DEFAULT_INTS_OUTPUT = "integers.txt";
    private static final String DEFAULT_FLOATS_OUTPUT = "floats.txt";
    private static final String DEFAULT_STRINGS_OUTPUT = "strings.txt";
    private final Path intsOutputPath;
    private final Path floatsOutputPath;
    private final Path stringsOutputPath;
    private final Options options;
    private Statistics statistics;

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
        String fullIntegersStatistics = String.format("Integers:\n\nAmount:\t%s\nMin:\t%s\nMax:\t%s\nSum:\t%s\nAvr:\t%s\n",
                statistics.amountInts(), statistics.minInt(), statistics.maxInt(), statistics.intsSum(), statistics.intsAvr());

        String fullFloatsStatistics = String.format("Floats:\n\nAmount:\t%s\nMin:\t%s\nMax:\t%s\nSum:\t%s\nAvr:\t%s\n",
                statistics.amountFloats(), statistics.minFloat(), statistics.maxFloat(), statistics.floatsSum(), statistics.floatsAvr());

        String fullStringsStatistics = String.format("Strings:\n\nAmount:\t%s\nMin length:\t%s\nMax length:\t%s\n",
                statistics.amountStrings(), statistics.minStrLength(), statistics.maxStrLength());

        System.out.println("-----------------------\nFull statistics:\n");
        System.out.println(fullIntegersStatistics + '\n' + fullFloatsStatistics + '\n' + fullStringsStatistics);
    }

    void printShortStatistics() {
        System.out.println("-----------------------\nShort statistics:\n");
        String shortStatistics = String.format("Amount of integers:\t%s\nAmount of floats:\t%s\nAmount of strings:\t%s",
                statistics.amountInts(), statistics.amountFloats(), statistics.amountStrings());

        System.out.println(shortStatistics);
    }

    private void filterInputFile(List<String> integers, List<String> floats, List<String> strings) {
        Statistics.Builder statisticsBuilder = new Statistics.Builder();

        BigInteger intsSum = BigInteger.ZERO;
        BigDecimal floatsSum = BigDecimal.ZERO;

        BigInteger maxInt = null;
        BigInteger minInt = null;
        BigDecimal maxFloat = null;
        BigDecimal minFloat = null;
        Integer maxStrLength = null;
        Integer minStrLength = null;

        List<Path> inputFilesPaths = options.inputFilesPaths();
        for (Path inputFilePath : inputFilesPaths) {
            try {
                List<String> allFileLines = Files.readAllLines(inputFilePath);
                for (String line : allFileLines) {
                    if (isInteger(line)) {
                        BigInteger newInt = new BigInteger(line);
                        if (maxInt == null || minInt == null) {
                            maxInt = newInt;
                            minInt = newInt;
                        } else {
                            maxInt = (BigInteger) max(newInt, maxInt);
                            minInt = (BigInteger) min(newInt, minInt);
                        }
                        intsSum = intsSum.add(newInt);
                        integers.add(line);
                        continue;
                    }
                    if (isFloat(line)) {
                        BigDecimal newFloat = new BigDecimal(line);
                        if (maxFloat == null || minFloat == null) {
                            maxFloat = newFloat;
                            minFloat = newFloat;
                        } else {
                            maxFloat = (BigDecimal) max(newFloat, maxFloat);
                            minFloat = (BigDecimal) min(newFloat, minFloat);
                        }
                        floatsSum = floatsSum.add(newFloat);
                        floats.add(line);
                        continue;
                    }
                    if (minStrLength == null || maxStrLength == null) {
                        maxStrLength = line.length();
                        minStrLength = line.length();
                    } else {
                        int strLength = line.length();
                        minStrLength = Math.min(strLength, minStrLength);
                        maxStrLength = Math.max(strLength, maxStrLength);
                    }
                    strings.add(line);
                }
            } catch (IOException e) {
                System.out.println(READING_INPUT_ERROR + inputFilePath + "\n" + e.getMessage());
            }
        }

        statisticsBuilder.floatsSum(floatsSum).intsSum(intsSum)
                .amountInts(integers.size()).amountFloats(floats.size()).amountStrings(strings.size())
                .maxInt(maxInt).maxFloat(maxFloat).maxStrLength(maxStrLength)
                .minFloat(minFloat).minInt(minInt).minStrLength(minStrLength);

        if (!integers.isEmpty()) {
            BigDecimal decIntsSum = new BigDecimal(intsSum.toString());
            statisticsBuilder.intsAvr(decIntsSum.divide(BigDecimal.valueOf(integers.size()), MathContext.DECIMAL64));
        }
        if (!floats.isEmpty()) {
            statisticsBuilder.floatsAvr(floatsSum.divide(BigDecimal.valueOf(floats.size()), MathContext.DECIMAL64));
        }

        statistics = statisticsBuilder.build();
    }

    private void writeFilteredInfo(List<String> integers, List<String> floats, List<String> strings) {
        writeIntegers(integers);
        writeFloats(floats);
        writeStrings(strings);
    }

    private void writeStrings(List<String> strings) {
        try {
            if (!strings.isEmpty()) {
                prepareOutputFile(stringsOutputPath);
                Files.write(stringsOutputPath, strings, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.err.println(WRITING_OUTPUT_ERROR + stringsOutputPath + "\n" + e.getMessage());
        }
    }

    private void writeFloats(List<String> floats) {
        try {
            if (!floats.isEmpty()) {
                prepareOutputFile(floatsOutputPath);
                Files.write(floatsOutputPath, floats, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.err.println(WRITING_OUTPUT_ERROR + floatsOutputPath + "\n" + e.getMessage());
        }
    }

    private void writeIntegers(List<String> integers) {
        try {
            if (!integers.isEmpty()) {
                prepareOutputFile(intsOutputPath);
                Files.write(intsOutputPath, integers, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            System.err.println(WRITING_OUTPUT_ERROR + intsOutputPath + "\n" + e.getMessage());
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

}
