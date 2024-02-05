package cft.test.task;

import java.nio.file.Path;
import java.util.List;

record Options(List<Path> inputFiles, Path resultPath, String namesPrefix, boolean rewriteFiles,
               boolean needShortStatistic, boolean needFullStatistic) {

    static class Builder {
        private static final boolean REWRITE_FILES = true;
        private static final boolean NEED_SHORT_STATISTIC = false;
        private static final boolean NEED_FULL_STATISTIC = false;
        private static final Path DEFAULT_RESULT_PATH = Path.of(".");

        private List<Path> inputFiles;
        private Path resultPath;
        private String namesPrefix;
        private Boolean rewriteFiles;
        private Boolean needShortStatistic;
        private Boolean needFullStatistic;

        //TODO check

        Builder resultPath(String resultPath){

            this.resultPath = Path.of(resultPath);

            return this;
        }

        Builder namesPrefix(String namesPrefix){

            this.namesPrefix = namesPrefix;

            return this;
        }

        Builder notRewriteFiles(){

            this.rewriteFiles = false;

            return this;
        }

        Builder needShortStatistic(){

            this.needShortStatistic = true;

            return this;
        }

        Builder needFullStatistic(){

            this.needFullStatistic = true;

            return this;
        }
    }
}
