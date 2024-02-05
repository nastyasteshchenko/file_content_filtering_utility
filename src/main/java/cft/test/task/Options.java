package cft.test.task;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

record Options(List<Path> inputFiles, Path resultPath, String namesPrefix, boolean rewriteFiles,
               boolean needShortStatistic, boolean needFullStatistic) {

    static class Builder {
        private static final boolean REWRITE_FILES = true;
        private static final boolean NEED_SHORT_STATISTIC = false;
        private static final boolean NEED_FULL_STATISTIC = false;
        private static final Path DEFAULT_RESULT_PATH = Path.of(".");
        private static final String  DEFAULT_NAMES_PREFIX = "";

        private List<Path> inputFiles;
        private Path resultPath;
        private String namesPrefix;
        private Boolean rewriteFiles;
        private Boolean needShortStatistic;
        private Boolean needFullStatistic;

        Builder resultPath(String resultPath) throws UserInputException {

            checkForDuplicates("-o", this.resultPath);

            Path dir = Path.of(resultPath);

            if (Files.notExists(dir.toAbsolutePath())) {
                throw UserInputException.wrongDirectory(resultPath);
            }

            if (!Files.isDirectory(dir.toAbsolutePath())) {
                throw UserInputException.fileIsNotDirectory(resultPath);
            }

            this.resultPath = dir;
            return this;
        }

        Builder namesPrefix(String namesPrefix) throws UserInputException {

            checkForDuplicates("-p", this.namesPrefix);

            this.namesPrefix = namesPrefix;
            return this;
        }

        Builder notRewriteFiles() throws UserInputException {

            checkForDuplicates("-a", this.rewriteFiles);

            this.rewriteFiles = false;
            return this;
        }

        Builder needShortStatistic() throws UserInputException {

            checkForDuplicates("-s", this.needShortStatistic);

            this.needShortStatistic = true;
            return this;
        }

        Builder needFullStatistic() throws UserInputException {

            checkForDuplicates("-f", this.needFullStatistic);

            this.needFullStatistic = true;
            return this;
        }

        Options build() throws UserInputException {
            fillDefaultForOptions();
            return new Options(inputFiles, resultPath, namesPrefix, rewriteFiles, needShortStatistic, needFullStatistic);
        }

        private void fillDefaultForOptions() throws UserInputException {

            if (inputFiles == null) {
                throw UserInputException.noInputFiles();
            }

            if (resultPath == null) {
                resultPath = DEFAULT_RESULT_PATH;
            }

            if (namesPrefix == null) {
                namesPrefix = DEFAULT_NAMES_PREFIX;
            }

            if (rewriteFiles == null) {
                rewriteFiles = REWRITE_FILES;
            }

            if (needShortStatistic == null) {
                needShortStatistic = NEED_SHORT_STATISTIC;
            }

            if (needFullStatistic == null) {
                needFullStatistic = NEED_FULL_STATISTIC;
            }
        }

        private <T> void checkForDuplicates(String optionName, T value) throws UserInputException {
            if (value != null) {
                throw UserInputException.duplicateOption(optionName);
            }
        }

    }
}
