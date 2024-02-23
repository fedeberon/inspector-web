package com.ideaas.services.exception;

/**
 * This interface must be used by exception that support enumerations from those that do not.
 *
 * @param <C>
 */
public interface Fault<C extends Enum<C>> {
    C code();
}