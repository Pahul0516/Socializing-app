package org.example.lab7.Repository;

import org.example.lab7.Domain.Entity;

import java.sql.SQLException;
import java.util.Optional;

public abstract class AbstractRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {

    @Override
    public abstract Optional<E> create(E user) throws SQLException;

    @Override
    public abstract void read() throws SQLException;

    @Override
    public abstract void write(E entity,  String operationType) throws SQLException;

    @Override
    public abstract Optional<E> update(E entity) throws SQLException;

    @Override
    public abstract Optional<E> delete(E user) throws SQLException;

    @Override
    public abstract Optional<E> findOne(ID id);

    @Override
    public abstract Iterable<E> findAll();

}
