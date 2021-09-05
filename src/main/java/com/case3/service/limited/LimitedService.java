package com.case3.service.limited;

import com.case3.config.ConnectionJDBC;
import com.case3.model.Limited;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LimitedService implements ILimitedService<Limited> {


    private static final String SELECT_LIMITED_BY_USER_ID = "select *from limited where id_user =?;";
    private static final String UPDATE_LIMIT_DAY = "update limited set limit_day = ? where id_user = ?";
    private static final String UPDATE_LIMIT_MONTH = "update limited set limit_month = ? where id_user = ?";


    @Override
    public List<Limited> findAll() {
        return null;
    }

    @Override
    public Limited findById(int id) {
        return null;
    }

    @Override
    public void save(Limited limited) {

    }

    @Override
    public void edit(Limited limited, int id) {

    }

    @Override
    public void delete(int id) {

    }

    public Limited findByIdUser(int id_user) {
        Limited limited = new Limited();
        try {
            Connection connection = ConnectionJDBC.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_LIMITED_BY_USER_ID);
            statement.setInt(1, id_user);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id_limited = resultSet.getInt("id_limited");
                int limit_day = resultSet.getInt("limit_day");
                int limit_month = resultSet.getInt("limit_month");
                limited.setId(id_limited);
                limited.setLimitDay(limit_day);
                limited.setLimitMonth(limit_month);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return limited;
    }

    public void upateLimitDay(int id_user, int limitday) {
        try {
            Connection connection = ConnectionJDBC.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_LIMIT_DAY);
            statement.setInt(1, limitday);
            statement.setInt(2, id_user);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void upateLimitMonth(int id_user, int limitmonth) {
        try {
            Connection connection = ConnectionJDBC.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_LIMIT_MONTH);
            statement.setInt(1, limitmonth);
            statement.setInt(2, id_user);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
