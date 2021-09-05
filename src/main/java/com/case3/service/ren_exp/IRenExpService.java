package com.case3.service.ren_exp;

import com.case3.service.IService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IRenExpService<E> extends IService<E> {
    List<E> findByDay (Date date,int id_user);
    List<E> findByWeek (Date date,int id_user);
    List<E> findByMonth (Date date,int id_user);
    List<E> findByMoney (int minMoney,int maxMoney,int id_user);
    Map<String, Integer> sumMoneyOfCategoryByWeek(int id_user, Date date);
    Map<String, Integer> sumMoneyOfCategoryByMonth(int id_user, Date date);
    Map<String, Integer> sumMoneyOfCategoryByDay(int id_user, Date date);
}
