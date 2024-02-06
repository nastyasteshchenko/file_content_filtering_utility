package cft.test.task;

import java.nio.file.Path;
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
        List<Path> inputFiles = options.inputFiles();
    }
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean isInteger(String str) {

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean isFloat(String str) {

        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
