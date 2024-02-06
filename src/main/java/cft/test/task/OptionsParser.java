package cft.test.task;

public class OptionsParser {

    static Options parse(String[] args) throws UserInputException {

        Options.Builder builder = new Options.Builder();

        for (int i = 0; i < args.length; ) {
            switch (args[i]) {
                case "-s" -> {
                    builder.needShortStatistic();
                    i += 1;
                }

                case "-a" -> {
                    builder.notRewriteFiles();
                    i += 1;
                }

                case "-f" -> {
                    builder.needFullStatistic();
                    i += 1;
                }

                case "-p" -> {

                    if (i + 1 >= args.length) {
                        throw UserInputException.noArgument("-p");
                    }

                    builder.namesPrefix(args[i + 1]);
                    i+=2;
                }

                case "-o" -> {

                    if (i + 1 >= args.length) {
                        throw UserInputException.noArgument("-o");
                    }

                    builder.resultPath(args[i + 1]);
                    i+=2;
                }

                default -> {
                    builder.inputFile(args[i]);
                    ++i;
                }
            }
        }

        return builder.build();
    }

}
