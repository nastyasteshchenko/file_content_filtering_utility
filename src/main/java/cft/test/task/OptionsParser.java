package cft.test.task;

public class OptionsParser {

    static Options parse(String[] args) throws UserInputException {

        Options.Builder builder = new Options.Builder();

        for (int i = 0; i < args.length; ) {
            switch (args[i]) {
                case "-s" -> {
                    builder.needShortStatistics();
                    i += 1;
                }

                case "-a" -> {
                    builder.notRewriteFiles();
                    i += 1;
                }

                case "-f" -> {
                    builder.needFullStatistics();
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

                    builder.outputPath(args[i + 1]);
                    i+=2;
                }

                default -> {
                    builder.inputFilePath(args[i]);
                    ++i;
                }
            }
        }

        return builder.build();
    }

}
