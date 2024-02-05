package cft.test.task;

class UserInputException extends Exception {

    private UserInputException(String message) {
        super(message);
    }

    static UserInputException duplicateOption(String optionName) {
        return new UserInputException(String.format("double definition of '%s' option", optionName));
    }

    static UserInputException resultPathIsNotDirectory(String resultPath) {
        return new UserInputException(String.format("result path '%s' is not a directory", resultPath));
    }

    static UserInputException resultPathDoesNotExist(String resultPath) {
        return new UserInputException(String.format("result path '%s' does not exist", resultPath));
    }
}
