package cft.test.task;

import java.io.IOException;
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

    void filter() {
        List<Path> inputFilesPaths = options.inputFilesPaths();

        boolean isAtLeastOneInt = false;
        boolean isAtLeastOneFloat = false;
        boolean isAtLeastOneString = false;

        try {
            for (Path inputFilePath : inputFilesPaths) {
                List<String> allFileLines = Files.readAllLines(inputFilePath);
                for (String line : allFileLines) {
                    if (isInteger(line)) {
                        if (!isAtLeastOneInt) {
                            prepareOutputFile(intsOutputPath);
                            isAtLeastOneInt = true;
                        }
                        Files.write(intsOutputPath, (line + '\n').getBytes(), StandardOpenOption.APPEND);
                        continue;
                    }
                    if (isFloat(line)) {
                        if (!isAtLeastOneFloat) {
                            prepareOutputFile(floatsOutputPath);
                            isAtLeastOneFloat = true;
                        }
                        Files.write(floatsOutputPath, (line + '\n').getBytes(), StandardOpenOption.APPEND);
                        continue;
                    }
                    if (!isAtLeastOneString) {
                        prepareOutputFile(stringsOutputPath);
                        isAtLeastOneString = true;
                    }
                    Files.write(stringsOutputPath, (line + '\n').getBytes(), StandardOpenOption.APPEND);
                }
            }
        } catch (IOException e) {
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

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isInteger(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isFloat(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
