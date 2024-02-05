package cft.test.task;

class UserInputException extends Exception {

    static final String availableOptions = """
            Available options:

            """;

    private UserInputException(String message) {
        super(message);
    }

    static UserInputException duplicateOption(String optionName) {
        return new UserInputException(String.format("double definition of '%s' option", optionName));
    }

    static UserInputException wrongDirectory(String dirName) {
        return new UserInputException(String.format("cannot access '%s': No such file or directory\n\n" + availableOptions, dirName));
    }

    static UserInputException fileIsNotDirectory(String path) {
        return new UserInputException(String.format("file '%s' is not a directory", path));
    }

    static UserInputException noArgument(String optionName) {
        return new UserInputException(String.format("option requires an argument -- '%s'\n\n" + availableOptions, optionName));
    }

    static UserInputException noInputFiles() {
        return new UserInputException("please enter input files\n\n" + availableOptions);
    }
}
