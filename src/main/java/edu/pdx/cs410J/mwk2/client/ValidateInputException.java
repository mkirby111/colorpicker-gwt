package edu.pdx.cs410J.mwk2.client;

/**
 *
 */
public class ValidateInputException extends RuntimeException {
    public ValidateInputException(String message) {
        super(message);
    }

    public ValidateInputException() {
    }
}