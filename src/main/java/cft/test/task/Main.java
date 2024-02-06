package cft.test.task;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Options options = parseOptions(args);
        if (options == null) {
            return;
        }

        startUtilityWork(options);
    }

    private static void startUtilityWork(Options options) {
        FilesFilter filesFilter = new FilesFilter(options);

        List<String> integers = new ArrayList<>();
        List<String> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        filesFilter.filter(integers, floats, strings);
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