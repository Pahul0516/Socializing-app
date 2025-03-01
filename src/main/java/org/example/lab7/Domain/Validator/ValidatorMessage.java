package org.example.lab7.Domain.Validator;

public class ValidatorMessage implements ValidatorStrategy{
    @Override
    public void validate(String data) throws ValidationException {
        if (data==null || data.trim().isEmpty())
            throw new ValidationException("Empty String");
    }
}
