package cft.test.task;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
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

    private void filterInputFile(List<String> integers, List<String> floats, List<String> strings) {
        List<Path> inputFilesPaths = options.inputFilesPaths();

        for (Path inputFilePath : inputFilesPaths) {
            try {
                List<String> allFileLines = Files.readAllLines(inputFilePath);
                for (String line : allFileLines) {
                    if (isInteger(line)) {
                        integers.add(line);
                        continue;
                    }
                    if (isFloat(line)) {
                        floats.add(line);
                        continue;
                    }
                    strings.add(line);
                }
            } catch (IOException e) {
                //TODO
                System.out.println(e.getMessage());
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
