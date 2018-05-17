package ua.training.controller.commands.exception;

/**
 * Description: This exception says to user about wrong work with Data base
 *
 * @author Zakusylo Pavlo
 */
public class DataSqlException extends RuntimeException {

    public DataSqlException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
