package com.cybertek.service;

import org.springframework.stereotype.Component;

import java.util.List;

public interface CrudService<T, ID> {

    List<T> findAll();
    T findById(ID id);
    T save(T t);
    void delete(T t);
    void deleteById(ID id);
    void update(T object);
}
