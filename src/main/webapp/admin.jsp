<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Dai
  Date: 31/08/2021
  Time: 4:32 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<html>
<head>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0" charset="UTF-8">--%>
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
<section>

    <div style="background-color: aquamarine" class="col-12">
        <table style="width: 100%">
            <tr>
                <th>ID USER</th>
                <th>Full name</th>
                <th>Số điện thoại</th>
                <th>Quyền</th>
                <th>Trạng thái</th>
                <th>Sửa</th>
            </tr>

            <c:forEach items="${userList}" var="users">
                <tr>
                    <td>${users.id}</td>
                    <td>${users.fullName}</td>
                    <td>${users.phone}</td>
                    <td>${users.role}</td>
                    <td>${users.status}</td>
                    <td>
                        <a href="/homepage?action=editStatus&idUser=${users.id}&status=${users.status}">
                            <button>Sửa trạng thái</button>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="col-12">
        <form method="post" action="/homepage?action=addIcon">
            <table>
                <tr>
                    <th>Link ICon</th>
                    <th>Thêm Icon</th>
                </tr>
                <tr>
                    <td><input name="linkIcon" value="" type="text"></td>
                    <td>
                        <button type="submit">Thêm</button>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</section>
<div class="row">
    <footer>
        <p>Liên hệ: 18008198.</p>
    </footer>
</div>
</body>
</html>