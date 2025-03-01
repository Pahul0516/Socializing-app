package org.example.lab7.Domain.Validator;

import org.example.lab7.Repository.Database.RepositoryUsersDatabase;


public class ValidatorRegisterPassword implements ValidatorStrategy{

    RepositoryUsersDatabase repositoryUsers;

    public ValidatorRegisterPassword(RepositoryUsersDatabase repositoryUsers) {
        this.repositoryUsers = repositoryUsers;
    }

    @Override
    public void validate(String data) throws ValidationException {
        var info = data.split(" ");
        if (info.length != 1) {
            throw new ValidationException("Invalid data format");
        }
        else {
            if (repositoryUsers.findPassword(info[0]) != false || info[0].isEmpty())
                throw new ValidationException("Invalid password");
        }
    }
}
