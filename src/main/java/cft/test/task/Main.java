package cft.test.task;

public class Main {
    public static void main(String[] args) {
        Options options = parseOptions(args);
        if (options == null) {
            return;
        }
    }

    private static Options parseOptions(String[] args) {
        Options options;

        try {
            options = OptionsParser.parse(args);
        } catch (UserInputException e) {
            System.err.println(e.getMessage());
            return null;
        }
        return options;
    }
}