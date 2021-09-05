package com.case3.service.icon;

import com.case3.config.ConnectionJDBC;
import com.case3.model.Icon;
import com.case3.service.IService;

import javax.naming.ldap.PagedResultsControl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IconService {
    private static final String FIND_ALL_ICON = "select * from icon;";
    private static final String UPDATE_ICON = "update icon set link_icon = ? where id_icon = ?;";
    private static final String ADD_ICON = "insert into icon(link_icon) value (?);";

    Connection connection = ConnectionJDBC.getConnection();

    public List<Icon> findAll() {
        List<Icon> listIcon = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_ICON);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_icon");
                String link = resultSet.getString("link_icon");
                listIcon.add(new Icon(id, link));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listIcon;
    }

    public void editIcon(int id, String linkIcon) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_ICON);
            statement.setString(1, linkIcon);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addIcon(String linkIcon) {
        try {
            PreparedStatement statement = connection.prepareStatement(ADD_ICON);
            statement.setString(1, linkIcon);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
