<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dai
  Date: 31/08/2021
  Time: 4:32 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" charset="UTF-8">
    <link rel="stylesheet" href="/style.css">
    <LINK REL="SHORTCUT ICON" HREF="/iconweb.ico">
    <title>Quản lý tài chính</title>
</head>
<body>

<header class="row">
    <table style="border: none; width: 100%">
        <tr style="margin-bottom: 2px">
            <td style="text-align: left;border: none;">
                <h1 style="font-size: 2.2em;color: white">Tài Chính Team</h1>
            </td>
            <td style="text-align: right;border: none;">
                <div style="text-align: right; padding-bottom: 2%">
                    <p style="color: white;font-size: 15px">${user.fullName}</p>
                    <a href="/expenditure?action=logout" style="color: white;font-size: 15px">
                        Đăng xuất
                    </a>
                </div>
            </td>
        </tr>
    </table>
</header>

<section class="row">
    <c:if test="${type.equals('exp')}">
        <form action="/expenditure?action=addCate" method="post" class="col-6 col-m-6">
            <table>
                <tr>
                    <th>ICON Danh Mục</th>
                    <th>Tên Danh Mục</th>
                </tr>
                <tr>
                    <td style="width: 100%">
                        <c:forEach items="${icon}" var="icon" varStatus="countTD">
                            <div style="position: relative; float: left">
                                <img width="50px" height="50px" src="${icon.linkIcon}"/>
                                <input type="radio" name="idIcon" value="${icon.id}">
                            </div>
                        </c:forEach>
                    </td>
                    <td>
                        <input name="nameCate" type="text"/>
                    </td>
                </tr>
            </table>
            <button type="submit">Thêm mới</button>
        </form>
    </c:if>
    <c:if test="${type.equals('re')}">
        <form action="/revenue?action=addCate" method="post" class="col-6 col-m-6">
            <table>
                <tr>
                    <th>ICON Danh Mục</th>
                    <th>Tên Danh Mục</th>
                </tr>
                <tr>
                    <td style="width: 100%">
                        <c:forEach items="${icon}" var="icon" varStatus="countTD">
                            <div style="position: relative; float: left">
                                <img width="50px" height="50px" src="${icon.linkIcon}"/>
                                <input type="radio" name="idIcon" value="${icon.id}">
                            </div>
                        </c:forEach>
                    </td>
                    <td>
                        <input name="nameCate" type="text"/>
                    </td>
                </tr>
            </table>
            <button type="submit">Thêm mới</button>
        </form>
    </c:if>
</section>
<div class="row">
    <footer>
        <p>Liên hệ: 18008198.</p>
    </footer>
</div>
<script type="text/javascript" src="/ModalAndChar.js"></script>
</body>
</html>