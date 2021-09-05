package com.case3.controller;

import com.case3.model.Category;
import com.case3.model.Expenditure;
import com.case3.model.Icon;
import com.case3.model.Limited;
import com.case3.model.User;
import com.case3.service.category.CategoryExService;
import com.case3.service.icon.IconService;
import com.case3.service.limited.LimitedService;
import com.case3.service.ren_exp.ExpenditureService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ExpenditureServlet", value = "/expenditure")
public class ExpenditureServlet extends HttpServlet {
    ExpenditureService expenditureService = new ExpenditureService();
    CategoryExService categoryExService = new CategoryExService();
    LimitedService limitedService = new LimitedService();
    IconService iconService = new IconService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "addCate":
                showFormAddCategory(request, response);
                break;
            case "logout":
                session.removeAttribute("user");
                response.sendRedirect("/expenditure?action=");
                break;
            case "addexp":
                response.sendRedirect("/addexp.jsp");
                break;
            case "editexp":
                showFormEditExpenditure(request, response);
                break;
            default:
                if (session.getAttribute("user") == null) {
                    response.sendRedirect("/homepage");
                } else {
                    showExpenditure(request, response);
                }
                break;

        }
    }

    private void showFormAddCategory(HttpServletRequest request, HttpServletResponse response) {
        String type = "exp";
        List<Icon> iconList = iconService.findAll();
        request.setAttribute("icon", iconList);
        request.setAttribute("type", type);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/addcate.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showFormEditExpenditure(HttpServletRequest request, HttpServletResponse response) {
        int id_ex = Integer.parseInt(request.getParameter("idexp"));
        Expenditure expenditure = expenditureService.findById(id_ex);
        request.setAttribute("ex", expenditure);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/editexp.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "money":
                showListByMoney(request, response);
                break;
            case "day":
                showListByDay(request, response);
                break;
            case "week":
                showListByWeek(request, response);
                break;
            case "month":
                showListByMonth(request, response);
                break;
            case "editday":
                editLimitDay(request, response);
                break;
            case "editmonth":
                editLimitMonth(request, response);
                break;
            case "editexp":
                editExpenditure(request, response);
                break;
            case "addexp":
                addNewExpenditure(request, response);
                break;
            case "addCate":
                addNewCategory(request, response);
                break;
            default:
                showExpenditure(request, response);
        }
    }

    private void showListByMoney(HttpServletRequest request, HttpServletResponse response) {
        int min = Integer.parseInt(request.getParameter("min"));
        int max = Integer.parseInt(request.getParameter("max"));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Expenditure> listMoney = expenditureService.findByMoney(min, max, user.getId());
        int totalMoney = 0;
        for (int i = 0; i < listMoney.size(); i++) {
            totalMoney += listMoney.get(i).getMoney();
        }
        request.setAttribute("map",null);
        request.setAttribute("totalMoney", totalMoney);
        request.setAttribute("listEx", listMoney);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/expenditure.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNewCategory(HttpServletRequest request, HttpServletResponse response) {
        int idIcon = Integer.parseInt(request.getParameter("idIcon"));
        String nameCate = request.getParameter("nameCate");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        categoryExService.addNewCate(idIcon, nameCate, user.getId());
        try {
            response.sendRedirect("/expenditure?action=");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showListByMonth(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Date date = new Date();
        String dateString = request.getParameter("date");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Expenditure> listMonth = expenditureService.findByMonth(date, user.getId());
        int totalMoney = 0;
        for (int i = 0; i < listMonth.size(); i++) {
            totalMoney += listMonth.get(i).getMoney();
        }
        Map<String, Integer> map = expenditureService.sumMoneyOfCategoryByMonth(user.getId(), date);
        int size = map.size();
        request.setAttribute("map", map);
        request.setAttribute("size", size);
        request.setAttribute("totalMoney", totalMoney);
        request.setAttribute("listEx", listMonth);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/expenditure.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showListByWeek(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Date date = new Date();
        String dateString = request.getParameter("date");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Expenditure> listWeek = expenditureService.findByWeek(date, user.getId());
        int totalMoney = 0;
        for (int i = 0; i < listWeek.size(); i++) {
            totalMoney += listWeek.get(i).getMoney();
        }
        Map<String, Integer> map = expenditureService.sumMoneyOfCategoryByWeek(user.getId(), date);
        int size = map.size();
        request.setAttribute("map", map);
        request.setAttribute("size", size);
        request.setAttribute("totalMoney", totalMoney);
        request.setAttribute("listEx", listWeek);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/expenditure.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showListByDay(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Date date = new Date();
        String dateString = request.getParameter("date");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Expenditure> listDay = expenditureService.findByDay(date, user.getId());
        int totalMoney = 0;
        for (int i = 0; i < listDay.size(); i++) {
            totalMoney += listDay.get(i).getMoney();
        }
        Map<String, Integer> map = expenditureService.sumMoneyOfCategoryByDay(user.getId(), date);
        int size = map.size();
        request.setAttribute("map", map);
        request.setAttribute("size", size);
        request.setAttribute("totalMoney", totalMoney);
        request.setAttribute("listEx", listDay);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/expenditure.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editLimitMonth(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int id_user = user.getId();
        int limitmonth = Integer.parseInt(request.getParameter("limitmonth"));
        limitedService.upateLimitMonth(id_user, limitmonth);
        try {
            response.sendRedirect("/expenditure?action=");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editLimitDay(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int id_user = user.getId();
        int limitday = Integer.parseInt(request.getParameter("limitday"));
        limitedService.upateLimitDay(id_user, limitday);
        try {
            response.sendRedirect("/expenditure?action=");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editExpenditure(HttpServletRequest request, HttpServletResponse response) {
        Date date = new Date();
        int money = Integer.parseInt(request.getParameter("money"));
        String note = request.getParameter("note");
        int id_exp = Integer.parseInt(request.getParameter("idexp"));
        int id_category = Integer.parseInt(request.getParameter("id"));
        Category category = categoryExService.findById(id_category);
        Expenditure expenditure = new Expenditure(category, date, money, note);
        expenditureService.edit(expenditure, id_exp);
        try {
            response.sendRedirect("/expenditure?action=");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNewExpenditure(HttpServletRequest request, HttpServletResponse response) {
        Date date = new Date();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Limited limited = (Limited) session.getAttribute("limited");
        int money = Integer.parseInt(request.getParameter("money"));
        String note = request.getParameter("note");
        int id_category = Integer.parseInt(request.getParameter("id"));
        Category category = categoryExService.findById(id_category);
        Expenditure expenditure = new Expenditure(category, date, money, note);
        expenditureService.save(expenditure);
        List<Expenditure> listDay = expenditureService.findByDay(date, user.getId());
        List<Expenditure> listMonth = expenditureService.findByMonth(date, user.getId());
        int totalMoneyByDay = 0;
        int totalMoneyByMonth = 0;
        for (int i = 0; i < listDay.size(); i++) {
            totalMoneyByDay += listDay.get(i).getMoney();
        }
        for (int i = 0; i < listMonth.size(); i++) {
            totalMoneyByMonth += listMonth.get(i).getMoney();
        }
        String message = "";
        if (totalMoneyByDay > limited.getLimitDay()) {
            message = message + "Vuot qua han muc ngay !";
        }
        if (totalMoneyByMonth > limited.getLimitMonth()) {
            message = message + " Vuot qua han muc thang !";
        }
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/expenditure?action=");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showExpenditure(HttpServletRequest request, HttpServletResponse response) {
        Date date = new Date();
        SimpleDateFormat format =
                new SimpleDateFormat("yyyy.MM.dd");
        String dateString = format.format(date);
        Date dateNow = null;
        try {
            dateNow = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("expenditure.jsp");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        session.setAttribute("dateNow", dateNow);
        List<Expenditure> listMonth = expenditureService.findByMonth(date, user.getId());
        List<Category> listCategory = categoryExService.findByIdUser(user.getId());
        Limited limited = limitedService.findByIdUser(user.getId());
        int totalMoney = 0;
        for (int i = 0; i < listMonth.size(); i++) {
            totalMoney += listMonth.get(i).getMoney();
        }
        Map<String, Integer> map = expenditureService.sumMoneyOfCategoryByMonth(user.getId(), date);
        int size = map.size();
        request.setAttribute("map", map);
        request.setAttribute("size", size);
        request.setAttribute("totalMoney", totalMoney);
        request.setAttribute("listEx", listMonth);
        session.setAttribute("listCategory", listCategory);
        session.setAttribute("limited", limited);
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
