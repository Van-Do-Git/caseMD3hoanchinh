package com.case3.service.user;

import com.case3.config.ConnectionJDBC;
import com.case3.model.Category;
import com.case3.model.Limited;
import com.case3.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService<User> {
    private static final String UPDATE_STATUS = "update users set status = ? where id_user = ?;";
    Connection connection = ConnectionJDBC.getConnection();
    private static final String ADD_EX_CATE = "insert into expenditure_categories(name_ec,id_icon,id_user) value (?,?,?)";
    private static final String ADD_RE_CATE = "insert into revenue_categories(name_rc,id_icon,id_user) value (?,?,?)";
    private static final String FIND_USER = "select * from users join role on users.id_role = role.id_role join limited on users.id_user = limited.id_user where username = ? and password = ?;";
    private static final String ADD_USER = "insert into users(fullName, phone, username, password, id_role) VALUE (?,?,?,?,?);";
    private static final String ADD_LIMITED = "insert into limited(id_user) VALUE (?);";
    private static final String SELECT_ALL_USERS = "select * from role join users on role.id_role = users.id_role;";
    private static List<Category> listExCate = new ArrayList<>();
    private static List<Category> listReCate = new ArrayList<>();

    static {
        listExCate.add(new Category("Tien dien"));
        listExCate.add(new Category("Tien nuoc"));
        listExCate.add(new Category("Tien xang"));
        listReCate.add(new Category("Tien luong"));
        listReCate.add(new Category("Tien thuong"));
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_user");
                String fullName = resultSet.getString("fullName");
                String phone = resultSet.getString("phone");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("name_role");
                boolean status = resultSet.getBoolean("status");
                User user = new User(id, fullName, phone, username, password, role, status);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;

    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public void save(User user) {

        try {
            PreparedStatement preAddUser = connection.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement preAddExCate = connection.prepareStatement(ADD_EX_CATE);
            PreparedStatement preAddReCate = connection.prepareStatement(ADD_RE_CATE);
            PreparedStatement preAddLimited = connection.prepareStatement(ADD_LIMITED);

            connection.setAutoCommit(false);
            preAddUser.setString(1, user.getFullName());
            preAddUser.setString(2, user.getPhone());
            preAddUser.setString(3, user.getUsername());
            preAddUser.setString(4, user.getPassword());
            preAddUser.setInt(5, 2);
            preAddUser.executeUpdate();
            ResultSet resultSet = preAddUser.getGeneratedKeys();
            int idUser = -1;
            if (resultSet.next()) {
                idUser = resultSet.getInt(1);
            }
            int idIcon = 1;
            for (Category category : listExCate
            ) {
                String namecate = category.getName();
                preAddExCate.setString(1, category.getName());
                preAddExCate.setInt(2, idIcon);
                preAddExCate.setInt(3, idUser);
                preAddExCate.executeUpdate();
                idIcon++;
            }
            for (Category category : listReCate
            ) {
                preAddReCate.setString(1, category.getName());
                preAddReCate.setInt(2, idIcon);
                preAddReCate.setInt(3, idUser);
                preAddReCate.executeUpdate();
                idIcon++;
            }
            preAddLimited.setInt(1, idUser);
            preAddLimited.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
    }

    @Override
    public void edit(User user, int id) {

    }

    public void updateStatus(int id_user, boolean status) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS);
            statement.setBoolean(1, status);
            statement.setInt(2, id_user);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            PreparedStatement preFindUser = connection.prepareStatement(FIND_USER);
            preFindUser.setString(1, username);
            preFindUser.setString(2, password);
            ResultSet resultSet = preFindUser.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_user");
                String fullName = resultSet.getString("fullName");
                String phone = resultSet.getString("phone");
                String role = resultSet.getString("name_role");
                boolean status = resultSet.getBoolean("status");
                int idLimited = resultSet.getInt("id_limited");
                int limitDay = resultSet.getInt("limit_day");
                int limitMonth = resultSet.getInt("limit_month");
                user = new User(id, fullName, phone, username, password, role, status, new Limited(idLimited, limitDay, limitMonth));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }
}
