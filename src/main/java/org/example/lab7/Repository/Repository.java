package org.example.lab7.Repository;

import org.example.lab7.Domain.Entity;
import java.sql.SQLException;
import java.util.Optional;

public interface Repository<ID, E extends Entity<ID>> {
    Optional<E> create(E entity) throws SQLException;
    void read() throws SQLException;
    void write(E entity,  String operationType) throws SQLException;
    Optional<E> delete(E entity) throws SQLException;
    Optional<E> update(E entity) throws SQLException;

    Optional<E> findOne(ID id);
    Iterable<E> findAll();

}
