package org.example.lab7.Domain.Validator;

import java.sql.SQLException;

public interface ValidatorStrategy {
    public void validate(String data) throws ValidationException;
}
