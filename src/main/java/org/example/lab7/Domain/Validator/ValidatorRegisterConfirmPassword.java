package org.example.lab7.Domain.Validator;

import org.example.lab7.Repository.Database.RepositoryUsersDatabase;


public class ValidatorRegisterConfirmPassword implements ValidatorStrategy{

    RepositoryUsersDatabase repositoryUsers;

    public ValidatorRegisterConfirmPassword(RepositoryUsersDatabase repositoryUsers) {
        this.repositoryUsers = repositoryUsers;
    }

    @Override
    public void validate(String data) throws ValidationException {
        var info = data.split(" ");
        if (info.length != 2) {
            throw new ValidationException("Invalid data format");
        }
        else {
            if (!info[0].equals(info[1]))
                throw new ValidationException("Invalid data format");
        }
    }
}
