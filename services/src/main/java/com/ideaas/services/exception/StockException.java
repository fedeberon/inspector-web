package com.ideaas.services.exception;

public class StockException extends RuntimeException implements Fault<StockException.Code>{

    public enum Code {
        NOT_INITIALIZED,
        WRONG_MINIMUM_DEVICES_NUMBER_BETWEEN_DEPOSIT_AND_REPAIR,
        WRONG_CANT_ELEMENT_STREET,
        WRONG_CANT_ELEMENT_DEPOSIT,
        WRONG_CANT_ELEMENT_REPAIR
    }

    private Code code;

    public StockException(String message) {
        super(message);
    }

    public StockException(Code code) {
        super("Stock exception");
        this.code = code;
    }

    @Override
    public Code code() {
        return code;
    }
}
