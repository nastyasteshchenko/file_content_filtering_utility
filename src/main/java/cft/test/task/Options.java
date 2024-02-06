package cft.test.task;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

record Options(List<Path> inputFilesPaths, Path outputPath, String namesPrefix, boolean rewriteFiles,
               boolean needShortStatistic, boolean needFullStatistic) {

    static class Builder {
        private static final boolean REWRITE_FILES = true;
        private static final boolean NEED_SHORT_STATISTICS = false;
        private static final boolean NEED_FULL_STATISTICS = false;
        private static final Path DEFAULT_OUTPUT_PATH = Path.of(".");
        private static final String DEFAULT_NAMES_PREFIX = "";
        private List<Path> inputFilesPaths;
        private Path outputPath;
        private String namesPrefix;
        private Boolean rewriteFiles;
        private Boolean needShortStatistics;
        private Boolean needFullStatistics;

        @SuppressWarnings("UnusedReturnValue")
        Builder inputFilePath(String inputPath) throws UserInputException {

            Path filePath = Path.of(inputPath);

            checkIfFileExists(inputPath, filePath);

            if (!Files.isRegularFile(filePath.toAbsolutePath())) {
                throw UserInputException.wrongInputFile(inputPath);
            }

            if (inputFilesPaths == null) {
                inputFilesPaths = new ArrayList<>();
            }

            if (inputFilesPaths.contains(filePath)) {
                throw UserInputException.duplicateInputFile(inputPath);
            }

            inputFilesPaths.add(filePath);

            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        Builder outputPath(String outputPath) throws UserInputException {

            checkForDuplicates("-o", this.outputPath);

            Path dir = Path.of(outputPath);

            checkIfFileExists(outputPath, dir);

            if (!Files.isDirectory(dir.toAbsolutePath())) {
                throw UserInputException.fileIsNotDirectory(outputPath);
            }

            this.outputPath = dir;
            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        Builder namesPrefix(String namesPrefix) throws UserInputException {

            checkForDuplicates("-p", this.namesPrefix);

            this.namesPrefix = namesPrefix;
            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        Builder notRewriteFiles() throws UserInputException {

            checkForDuplicates("-a", this.rewriteFiles);

            this.rewriteFiles = false;
            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        Builder needShortStatistics() throws UserInputException {

            checkForDuplicates("-s", this.needShortStatistics);

            this.needShortStatistics = true;
            return this;
        }

        @SuppressWarnings("UnusedReturnValue")
        Builder needFullStatistics() throws UserInputException {

            checkForDuplicates("-f", this.needFullStatistics);

            this.needFullStatistics = true;
            return this;
        }

        Options build() throws UserInputException {
            fillDefaultForOptions();
            return new Options(inputFilesPaths, outputPath, namesPrefix, rewriteFiles, needShortStatistics, needFullStatistics);
        }

        private void fillDefaultForOptions() throws UserInputException {

            if (inputFilesPaths == null) {
                throw UserInputException.noInputFiles();
            }

            if (outputPath == null) {
                outputPath = DEFAULT_OUTPUT_PATH;
            }

            if (namesPrefix == null) {
                namesPrefix = DEFAULT_NAMES_PREFIX;
            }

            if (rewriteFiles == null) {
                rewriteFiles = REWRITE_FILES;
            }

            if (needShortStatistics == null) {
                needShortStatistics = NEED_SHORT_STATISTICS;
            }

            if (needFullStatistics == null) {
                needFullStatistics = NEED_FULL_STATISTICS;
            }
        }

        private <T> void checkForDuplicates(String optionName, T value) throws UserInputException {
            if (value != null) {
                throw UserInputException.duplicateOption(optionName);
            }
        }

        private static void checkIfFileExists(String inputPath, Path file) throws UserInputException {
            if (Files.notExists(file.toAbsolutePath())) {
                throw UserInputException.wrongFile(inputPath);
            }
        }
    }
}
