package fr.eni.demoSpringFramework.Dal.Error;

public class SqlException extends Exception {
    public SqlException(String message) {
        super(message);
    }
}
