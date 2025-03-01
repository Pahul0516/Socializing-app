package org.example.lab7.Domain.Validator;

import org.example.lab7.Repository.Database.RepositoryUsersDatabase;


public class ValidatorRegisterName implements ValidatorStrategy{

    RepositoryUsersDatabase repositoryUsers;

    public ValidatorRegisterName(RepositoryUsersDatabase repositoryUsers) {
        this.repositoryUsers = repositoryUsers;
    }

    @Override
    public void validate(String data) throws ValidationException {
        if (data.isEmpty())
            throw new ValidationException("Empty data");
    }
}
