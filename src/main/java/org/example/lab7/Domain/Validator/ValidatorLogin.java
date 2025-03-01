package org.example.lab7.Domain.Validator;

import org.example.lab7.Repository.Database.RepositoryUsersDatabase;

public class ValidatorLogin implements ValidatorStrategy{

    RepositoryUsersDatabase repositoryUsers;

    public ValidatorLogin(RepositoryUsersDatabase repositoryUsers) {
        this.repositoryUsers = repositoryUsers;
    }

    @Override
    public void validate(String data) throws ValidationException {
        String[] info = data.split(" ");
        if (info.length != 2)
            throw new ValidationException("Invalid data");
        if (repositoryUsers.findCredentialId(info[0],info[1])==-1)
            throw new ValidationException("Invalid data");

    }
}
