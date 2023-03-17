package org.ssvv.validation;

public interface Validator<E> {
    void validate(E entity) throws ValidationException;
}