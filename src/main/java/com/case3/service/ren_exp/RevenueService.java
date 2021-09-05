package com.case3.service.ren_exp;

import com.case3.config.ConnectionJDBC;
import com.case3.model.Category;
import com.case3.model.Revenue;
import com.case3.service.category.CategoryReService;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class RevenueService implements IRenExpService<Revenue> {


    private static final String SELECT_ALL_REVENUE = "SELECT*FROM revenue";

    private static final String SELECT_REVENUE_BY_ID = "SELECT*FROM revenue WHERE id_re=?";

    private static final String II_REVENUE = "INSERT INTO revenue(date_re,money_re,note,id_rc) " +
            "VALUE (?,?,?,?)";

    private static final String UPDATE_REVENUE_BY_ID = "UPDATE revenue " +
            "SET date_re= ?, money_re= ?, note= ?, id_rc= ? WHERE id_re=?";

    private static final String DELETE_REVENUE_BY_ID = "DELETE FROM revenue WHERE id_re=?";

    private static final String SELECT_REVENUE_BY_DAY_AND_USER_ID =
            "SELECT r.id_re\n" +
                    "FROM revenue r JOIN revenue_Categories rC on rC.id_rc = r.id_rc\n" +
                    "WHERE rC.id_user=?  AND r.date_re=?";

    private static final String SELECT_REVENUE_BY_USER_ID_AND_WEEK_OF_DATE =
            "SELECT r.id_re\n" +
                    "FROM revenue r JOIN revenue_Categories rC on rC.id_rc = r.id_rc\n" +
                    "WHERE rC.id_user=? AND weekOfYear(r.date_re) =  weekOfYear(?)";

    private static final String SELECT_REVENUE_BY_USER_ID_AND_MONTH_OF_DATE =
            "SELECT r.id_re\n" +
                    "FROM revenue r JOIN revenue_Categories rC on rC.id_rc = r.id_rc\n" +
                    "WHERE rC.id_user=? AND month(r.date_re) =  month(?)";

    private static final String SELECT_REVENUE_BY_MONEY_AND_USER_ID =
            "SELECT r.id_re\n" +
                    "FROM revenua r JOIN revenua_Categories rC ON rC.id_rc = r.id_rc\n" +
                    "WHERE rC.id_user = ? AND (r.money_re BETWEEN ? AND ?)";

    private static final String SELECT_CATEGORY_AND_SUM_MONEY_BY_USER_ID_AND_WEEK_OF_DATE =
            "select rC.name_rc,sum(r.money_re)\n" +
                    "from revenue r join revenue_Categories rC on rC.id_rc = r.id_rc\n" +
                    "where rC.id_user = ? and weekOfYear(r.date_re)=weekOfYear(?)\n" +
                    "group by rC.name_rc";

    private static final String SELECT_CATEGORY_AND_SUM_MONEY_BY_USER_ID_AND_MONTH_OF_DATE =
            "select rC.name_rc,sum(r.money_re)\n" +
                    "from revenue r join revenue_Categories rC on rC.id_rc = r.id_rc\n" +
                    "where rC.id_user = ? and month(r.date_re)=month(?)\n" +
                    "group by rC.name_rc";
    private static final String SELECT_CATEGORY_AND_SUM_MONEY_BY_USER_ID_AND_DAY_OF_DATE =
            "select rC.name_rc,sum(r.money_re)\n" +
                    "from revenue r join revenue_Categories rC on rC.id_rc = r.id_rc\n" +
                    "where rC.id_user = ? and r.date_re=?\n" +
                    "group by rC.name_rc";

    Connection connection = ConnectionJDBC.getConnection();
    CategoryReService categoryReService = new CategoryReService();

    @Override
    public List<Revenue> findAll() {
        List<Revenue> revenues = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REVENUE);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                Date date = rs.getDate(2);
                int money = rs.getInt(3);
                String note = rs.getString(4);
                Category category = categoryReService.findById(rs.getInt(5));
                revenues.add(new Revenue(id, category, date, money, note));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return revenues;
    }

    @Override
    public Revenue findById(int id) {
        Revenue revenue = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_REVENUE_BY_ID);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Date date = rs.getDate(2);
                int money = rs.getInt(3);
                String note = rs.getString(4);
                Category category = categoryReService.findById(rs.getInt(5));
                revenue = new Revenue(id, category, date, money, note);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return revenue;
    }

    @Override
    public void save(Revenue r) {
        try {
            PreparedStatement statement = connection.prepareStatement(II_REVENUE);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            statement.setString(1, dateFormat.format(r.getDate()));
            statement.setInt(2, r.getMoney());
            statement.setString(3, r.getNote());
            statement.setInt(4, r.getCategory().getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void edit(Revenue r, int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_REVENUE_BY_ID);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            statement.setString(1, dateFormat.format(r.getDate()));
            statement.setInt(2, r.getMoney());
            statement.setString(3, r.getNote());
            statement.setInt(4, r.getCategory().getId());
            statement.setInt(5, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_REVENUE_BY_ID);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Revenue> findByDay(Date date, int id_user) {
        List<Revenue> revenues = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_REVENUE_BY_DAY_AND_USER_ID);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            statement.setInt(1, id_user);
            statement.setString(2, dateFormat.format(date));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                revenues.add(findById(id));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return revenues;
    }

    @Override
    public List<Revenue> findByWeek(Date date, int id_user) {
        List<Revenue> revenues = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_REVENUE_BY_USER_ID_AND_WEEK_OF_DATE);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            statement.setInt(1, id_user);
            statement.setString(2, dateFormat.format(date));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                revenues.add(findById(id));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return revenues;
    }

    @Override
    public List<Revenue> findByMonth(Date date, int id_user) {
        List<Revenue> revenues = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_REVENUE_BY_USER_ID_AND_MONTH_OF_DATE);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            statement.setInt(1, id_user);
            statement.setString(2, dateFormat.format(date));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                revenues.add(findById(id));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return revenues;
    }

    @Override
    public List<Revenue> findByMoney(int minMoney, int maxMoney, int id_user) {
        List<Revenue> revenues = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_REVENUE_BY_MONEY_AND_USER_ID);
            statement.setInt(1, id_user);
            statement.setInt(2, minMoney);
            statement.setInt(3, maxMoney);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                revenues.add(findById(id));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return revenues;
    }

    @Override
    public Map<String, Integer> sumMoneyOfCategoryByWeek(int id_user, Date date) {
        Map<String, Integer> sumMoneyRevanueCategories = new HashMap<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORY_AND_SUM_MONEY_BY_USER_ID_AND_WEEK_OF_DATE);
            statement.setInt(1, id_user);
            statement.setString(2, dateFormat.format(date));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String categoryName = rs.getString(1);
                int sumMoney = rs.getInt(2);
                sumMoneyRevanueCategories.put(categoryName, sumMoney);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sumMoneyRevanueCategories;
    }

    @Override
    public Map<String, Integer> sumMoneyOfCategoryByMonth(int id_user, Date date) {
        Map<String, Integer> sumMoneyRevanueCategories = new HashMap<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORY_AND_SUM_MONEY_BY_USER_ID_AND_MONTH_OF_DATE);
            statement.setInt(1, id_user);
            statement.setString(2, dateFormat.format(date));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String categoryName = rs.getString(1);
                int sumMoney = rs.getInt(2);
                sumMoneyRevanueCategories.put(categoryName, sumMoney);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sumMoneyRevanueCategories;
    }

    @Override
    public Map<String, Integer> sumMoneyOfCategoryByDay(int id_user, Date date) {
        Map<String, Integer> sumMoneyRevanueCategories = new HashMap<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORY_AND_SUM_MONEY_BY_USER_ID_AND_DAY_OF_DATE);
            statement.setInt(1, id_user);
            statement.setString(2, dateFormat.format(date));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String categoryName = rs.getString(1);
                int sumMoney = rs.getInt(2);
                sumMoneyRevanueCategories.put(categoryName, sumMoney);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sumMoneyRevanueCategories;
    }
}
