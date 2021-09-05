package com.case3.service;

import java.util.List;

public interface IService<E> {
    List<E> findAll();
    E findById(int id);
    void save(E e);
    void edit(E e,int id);
    void delete(int id);
}
