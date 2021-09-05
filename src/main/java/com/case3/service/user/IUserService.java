package com.case3.service.user;

import com.case3.service.IService;

public interface IUserService<E> extends IService<E> {
    E findByUsernameAndPassword(String username, String password);
}
