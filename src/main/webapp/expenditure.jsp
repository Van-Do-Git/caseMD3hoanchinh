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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <div class="col-12">
        <div>
            <a href="/expenditure?action=">
                <button type="button" class="col-6 col-m-6" style="background-color: white">Chi</button>
            </a>
            <a href="/revenue?action=">
                <button type="button" class="col-6 col-m-6">Thu</button>
            </a>
        </div>
        <div id="menu" style="width: 70%;height: 30px;background: none;border: none;">
            <ul>
                <li><a>Chi Tiết</a>
                    <ul>
                        <li><a id="day">Theo ngày</a></li>
                        <li><a id="week">Theo tuần</a></li>
                        <li><a id="month">Theo tháng</a></li>
                        <li><a id="money">Theo tiền</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <div style="width: 70%;height: 0;background: none;border: none;float: left;clear: both">
        </div>
        <div>
            <form class="row" id="formmoney" action="/expenditure?action=money" method="post" style="display: none">
                <table>
                    <tr>
                        <td>
                            <label class="col-2">Từ:</label>
                        </td>
                        <td>
                            <input class="col-2" name="min" type="number" value="0" style="width: 120px"><br>
                        </td>
                        <td>
                            <label class="col-2">Đến:</label>
                        </td>
                        <td>
                            <input class="col-2" name="max" type="number" value="0" style="width: 120px"><br>
                        </td>
                        <td>
                            <div class="col-2">
                                <button class="close" type="submit">Ok</button>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
            <form class="row" id="formday" action="/expenditure?action=day" method="post" style="display: none">
                <table>
                    <tr>
                        <td>
                            <label class="col-2">Ngày tháng:</label>
                        </td>
                        <td>
                            <input class="col-2" name="date" type="date" style="width: 150px"><br>
                        </td>
                        <td>
                            <div class="col-2">
                                <button class="close" type="submit">Ok</button>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
            <form class="row" id="formweek" action="/expenditure?action=week" method="post" style="display: none;">
                <table>
                    <tr>
                        <td>
                            <label class="col-2">Ngày tháng:</label>
                        </td>
                        <td>
                            <input class="col-2" name="date" type="date" style="width: 150px"><br>
                        </td>
                        <td>
                            <div class="col-2">
                                <button class="close" type="submit">Ok</button>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
            <form class="row" id="formmonth" action="/expenditure?action=month" method="post" style="display: none;">
                <table>
                    <tr>
                        <td>
                            <label class="col-2">Ngày tháng:</label>
                        </td>
                        <td>
                            <input class="col-2" name="date" type="date" style="width: 150px"><br>
                        </td>
                        <td>
                            <div class="col-2">
                                <button class="close" type="submit">Ok</button>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div>
            <div style="background-color: aquamarine" class="col-12">
                <table style="width: 100%">
                    <tr>
                        <th>Ngày tháng</th>
                        <th>Danh mục</th>
                        <th>Tên danh mục</th>
                        <th>Số tiền</th>
                        <th>Ghi chú</th>
                        <th>Sửa</th>
                    </tr>

                    <c:forEach items="${listEx}" var="re">
                        <tr>
                            <td>${re.date}</td>
                            <td><img width="35px" height="35px" src="${re.category.linkIcon}"></td>
                            <td>${re.category.name}</td>
                            <td>${re.money}</td>
                            <td>${re.note}</td>
                            <td>
                                <c:if test="${re.date.equals(dateNow)}">
                                    <a href="/expenditure?action=editexp&idexp=${re.id}">
                                        <button>Sửa</button>
                                    </a>
                                </c:if>
                            </td>

                        </tr>
                    </c:forEach>
                    <tr>
                        <th colspan="3">
                            Tổng số tiền:
                        </th>
                        <th colspan="3">
                            ${totalMoney}
                        </th>
                    </tr>
                </table>
                <div class="col-6">
                    <a href="/expenditure?action=addexp">
                        <button type="button" class="col-6">Thêm khoản chi</button>
                    </a>
                </div>
                <div class="col-6">
                    <button type="button" onclick="ve();">Xem biểu đồ</button>
                    <canvas id="myChart" style="width:100%;max-width:450px; color: black"></canvas>
                </div>
                <div style="display: none">
                    <c:if test="${map!=null}">
                        <c:forEach items="${map}" var="map" varStatus="count">
                            <input id="${count.index}v" value="${map.value}" type="number">
                            <input id="${count.index}k" value="${map.key}" type="text">
                        </c:forEach>
                        <input id="size" value="${size}">
                    </c:if>
                </div>
            </div>

        </div>
        <div style="background-color: chocolate" class="col-12">
            <div class="col-6" style="background-color: aquamarine">
                <table style="width: 100%">
                    <tr>
                        <th>Danh mục</th>
                        <th>Tên danh mục</th>
                    </tr>
                    <c:forEach items="${listCategory}" var="cate">
                        <tr>
                            <td><img width="50px" height="50px" src="${cate.linkIcon}"/></td>
                            <td>${cate.name}</td>

                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="2"><a href="/expenditure?action=addCate">
                            <button>Thêm danh mục</button>
                        </a></td>
                    </tr>

                </table>

            </div>
            <div class="col-6">
                <table style="width: 100%">
                    <tr>
                        <th>Hạn mức</th>
                        <th>Số tiền</th>
                        <th>Sửa</th>
                    </tr>

                    <tr>
                        <td style="background-color: lawngreen">Hạn mức ngày</td>
                        <th>${limited.limitDay}</th>
                        <td>
                            <button id="openeditday">Edit</button>
                        </td>
                    </tr>
                    <tr>
                        <td style="background-color: lawngreen">Hạn mức tháng</td>
                        <th>${limited.limitMonth}</th>
                        <td>
                            <button id="openeditmonth">Edit</button>
                        </td>
                    </tr>
                </table>
                <c:if test="${message!=''}">
                    <p style="color: #cc3333">${message}</p>
                </c:if>
                <form id="editmonth" action="/expenditure?action=editmonth&id=${limited.id}" method="post"
                      style="display: none">
                    <table>
                        <tr>
                            <td><input name="limitmonth" type="number" value="${limited.limitMonth}"></td>
                            <td>
                                <button class="close" type="submit">Ok</button>
                            </td>
                        </tr>
                    </table>

                </form>
                <form id="editday" action="/expenditure?action=editday&id=${limited.id}" method="post"
                      style="display: none">
                    <table>
                        <tr>
                            <td><input name="limitday" type="number" value="${limited.limitDay}"></td>
                            <td>
                                <button class="close" type="submit">Ok</button>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>

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
