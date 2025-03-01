package org.example.lab7.Domain.Validator;

import org.example.lab7.Repository.Database.RepositoryUsersDatabase;


public class ValidatorRegisterEmail implements ValidatorStrategy{

    RepositoryUsersDatabase repositoryUsers;

    public ValidatorRegisterEmail(RepositoryUsersDatabase repositoryUsers) {
        this.repositoryUsers = repositoryUsers;
    }

    @Override
    public void validate(String data) throws ValidationException {
        var info = data.split(" ");
        if (info.length != 1) {
            throw new ValidationException("Invalid data format");
        }
        else {
            if (repositoryUsers.findEmail(info[0]) != false || info[0].isEmpty())
                throw new ValidationException("Invalid email");
        }
    }
}
