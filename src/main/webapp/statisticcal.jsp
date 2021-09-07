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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<html>
<head>
    <%--    <meta name="viewport" content="width=device-width, initial-scale=1.0" charset="UTF-8">--%>
    <link rel="stylesheet" href="/style.css">
    <LINK REL="SHORTCUT ICON" HREF="/iconweb.ico">
    <title>Quản lý tài chính</title>
</head>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
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
    <div style="background-color: aquamarine;height: 92%" class="col-12 col-m-12">
        <div>
            <button type="button" onclick="vebieudomien();">Bấm để xem</button>
            <canvas id="bieudomien" width="200" height="90" class="col-10 col-m-10"></canvas>
        </div>
        <div style="display: none">
            <c:if test="${map!=null}">
                <c:forEach items="${map}" var="map" varStatus="count">
                    <input id="${count.index}v" value="${map.value}" type="number">
                    <input id="${count.index}k" value="${map.key}" type="text">
                </c:forEach>
                <input id="size" value="${size}">
            </c:if>
            <c:forEach items="${listSumEx}" var="sumex" varStatus="loopex">
                <input id="${loopex.index}exp" value="${sumex}" type="number">
            </c:forEach>
            <c:forEach items="${listSumRe}" var="sumre" varStatus="loopre">
                <input id="${loopre.index}rev" value="${sumre}" type="number">
            </c:forEach>
        </div>
    </div>

</section>
<div class="row">
    <footer>
        <p>Liên hệ: 18008198.</p>
    </footer>
</div>
<script type="text/javascript" src="/ModalAndChar.js"></script>
</body>
</html>
