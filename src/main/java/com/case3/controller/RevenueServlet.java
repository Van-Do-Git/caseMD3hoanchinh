package com.case3.controller;


import com.case3.model.*;
import com.case3.service.category.CategoryReService;
import com.case3.service.icon.IconService;

import com.case3.service.ren_exp.RevenueService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Revenue", value = "/revenue")
public class RevenueServlet extends HttpServlet {
    RevenueService revenueService = new RevenueService();
    CategoryReService categoryReService = new CategoryReService();
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
                response.sendRedirect("/revenue?action=");
                break;
            case "addre":
                response.sendRedirect("/addre.jsp");
                break;
            case "editre":
                showFormEditRevenue(request, response);
                break;
            default:
                if (session.getAttribute("user") == null) {
                    response.sendRedirect("/homepage");
                } else {
                    showRevenue(request, response);
                }
                break;
        }
    }

    private void showFormAddCategory(HttpServletRequest request, HttpServletResponse response) {
        String type = "re";
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

    private void showFormEditRevenue(HttpServletRequest request, HttpServletResponse response) {
        int id_re = Integer.parseInt(request.getParameter("idre"));
        Revenue revenue = revenueService.findById(id_re);
        request.setAttribute("re", revenue);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/editre.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showRevenue(HttpServletRequest request, HttpServletResponse response) {
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("revenue.jsp");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        session.setAttribute("dateNow", dateNow);
        List<Revenue> listMonth = revenueService.findByMonth(date, user.getId());
        List<Category> listCategory = categoryReService.findByIdUser(user.getId());
        Map<String, Integer> map = revenueService.sumMoneyOfCategoryByMonth(user.getId(), date);
        int size = map.size();
        request.setAttribute("map", map);
        request.setAttribute("size", size);
        int totalMoney = 0;
        for (int i = 0; i < listMonth.size(); i++) {
            totalMoney += listMonth.get(i).getMoney();
        }
        request.setAttribute("totalMoney", totalMoney);
        request.setAttribute("listRe", listMonth);
        session.setAttribute("listCategoryRe", listCategory);
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
        HttpSession session = request.getSession();
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
            case "editre":
                editRevenue(request, response);
                break;
            case "addre":
                addNewRevenue(request, response);
                break;
            case "addCate":
                addNewCategory(request, response);
                break;
            default:
                if (session.getAttribute("user") == null) {
                    response.sendRedirect("/homepage");
                } else {
                    showRevenue(request, response);
                }
                break;
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
        List<Revenue> listMonth = revenueService.findByMonth(date, user.getId());
        Map<String, Integer> map = revenueService.sumMoneyOfCategoryByMonth(user.getId(), date);
        int size = map.size();
        request.setAttribute("map", map);
        request.setAttribute("size", size);
        int totalMoney = 0;
        for (int i = 0; i < listMonth.size(); i++) {
            totalMoney += listMonth.get(i).getMoney();
        }
        request.setAttribute("totalMoney", totalMoney);
        request.setAttribute("listRe", listMonth);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/revenue.jsp");
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
        Map<String, Integer> map = revenueService.sumMoneyOfCategoryByWeek(user.getId(), date);
        int size = map.size();
        request.setAttribute("map", map);
        request.setAttribute("size", size);
        List<Revenue> listWeek = revenueService.findByWeek(date, user.getId());
        int totalMoney = 0;
        for (int i = 0; i < listWeek.size(); i++) {
            totalMoney += listWeek.get(i).getMoney();
        }
        request.setAttribute("totalMoney", totalMoney);
        request.setAttribute("listRe", listWeek);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/revenue.jsp");
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
        List<Revenue> listDay = revenueService.findByDay(date, user.getId());
        Map<String, Integer> map = revenueService.sumMoneyOfCategoryByDay(user.getId(), date);
        int size = map.size();
        request.setAttribute("map", map);
        request.setAttribute("size", size);
        int totalMoney = 0;
        for (int i = 0; i < listDay.size(); i++) {
            totalMoney += listDay.get(i).getMoney();
        }
        request.setAttribute("totalMoney", totalMoney);
        request.setAttribute("listRe", listDay);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/revenue.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showListByMoney(HttpServletRequest request, HttpServletResponse response) {
        int min = Integer.parseInt(request.getParameter("min"));
        int max = Integer.parseInt(request.getParameter("max"));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Revenue> listMoney = revenueService.findByMoney(min, max, user.getId());
        request.setAttribute("map", null);
        request.setAttribute("listRe", listMoney);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/revenue.jsp");
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
        categoryReService.addNewCate(idIcon, nameCate, user.getId());
        try {
            response.sendRedirect("/revenue?action=");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editRevenue(HttpServletRequest request, HttpServletResponse response) {
        Date date = new Date();
        int money = Integer.parseInt(request.getParameter("money"));
        String note = request.getParameter("note");
        int id_exp = Integer.parseInt(request.getParameter("idre"));
        int id_category = Integer.parseInt(request.getParameter("id"));
        Category category = categoryReService.findById(id_category);
        Revenue revenue = new Revenue(category, date, money, note);
        revenueService.edit(revenue, id_exp);
        try {
            response.sendRedirect("/revenue?action=");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNewRevenue(HttpServletRequest request, HttpServletResponse response) {
        Date date = new Date();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int money = Integer.parseInt(request.getParameter("money"));
        String note = request.getParameter("note");
        int id_category = Integer.parseInt(request.getParameter("id"));
        Category category = categoryReService.findById(id_category);
        Revenue revenue = new Revenue(category, date, money, note);
        revenueService.save(revenue);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/revenue?action=");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
