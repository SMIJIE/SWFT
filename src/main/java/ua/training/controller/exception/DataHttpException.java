package ua.training.controller.exception;

/**
 * Description: This exception says to user about wrong http data
 *
 * @author Zakusylo Pavlo
 */
public class DataHttpException extends Exception {

    public DataHttpException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
