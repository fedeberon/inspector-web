package com.ideaas.services.exception;

/**
 * Created by federicoberon on 30/11/2019.
 */
public class FileStorageException extends RuntimeException {

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
