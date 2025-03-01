package org.example.lab7.Domain.Validator;

import org.example.lab7.Repository.Database.RepositoryUsersDatabase;


public class ValidatorModifyPassword implements ValidatorStrategy{

    private RepositoryUsersDatabase repositoryUsers;
    private Integer id_credentials;

    public ValidatorModifyPassword(RepositoryUsersDatabase repositoryUsers,Integer id) {
        this.repositoryUsers = repositoryUsers;
        this.id_credentials = id;
    }

    @Override
    public void validate(String data) throws ValidationException {
        var info = data.split(" ");
        if (info.length != 1) {
            throw new ValidationException("Invalid data format");
        }
        else {
            if (repositoryUsers.findPasswordExceptId(info[0], id_credentials) != false || info[0].isEmpty())
                throw new ValidationException("Invalid email");
        }
    }
}
