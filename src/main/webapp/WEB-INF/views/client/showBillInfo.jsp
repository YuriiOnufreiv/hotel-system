<%--
  Created by IntelliJ IDEA.
  User: yurii
  Date: 1/14/17
  Time: 2:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="dateTag" uri="/WEB-INF/views/tld/dateTag.tld" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/client/jspf/head.jspf" %>
    <title><fmt:message key="header.billInfo"/></title>
</head>
<body>
<%@ include file="/WEB-INF/views/client/jspf/header.jspf" %>
<div class="main-1">
    <div class="container big-padding">
        <div class="sinbt">
            <a class="hvr-shutter-in-horizontal" href="">Make payment</a>
        </div>
        <h3 class="hdg">Bill #1</h3>
        <div class="single-top">
            <div class="col-md-6 single-right">
                <p><dateTag:date date="${requestScope.billCreationDate}" locale="${sessionScope.language}"
                                 showTime="true"/></p>
                <br><h4>Client Info</h4>
                <hr>
                <p><strong>Name: </strong>${requestScope.user.name} ${requestScope.user.surname}</p>
                <p><strong>Email: </strong>${requestScope.user.email}</p>
                <p><strong>Phone: </strong>${requestScope.user.phoneNum}</p>
            </div>
            <div class="col-md-6 single-right">
                <br><br><h4>Room Info</h4>
                <hr>
                <p><strong>Room #: </strong>${requestScope.roomNumber}</p>
                <p><strong>Room type: </strong>${requestScope.roomType}</p>
                <p><strong>Price: </strong>$${requestScope.roomPrice}</p>
                <p><strong>Check In: </strong>${requestScope.checkInDate}</p>
                <p><strong>Check Out: </strong>${requestScope.checkOutDate}</p>
            </div>
            <div class="clearfix"></div>
            <hr>
            <p align="right"><strong>TOTAL PRICE: $${requestScope.billTotalPrice}</strong></p>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/client/jspf/footer.jspf" %>
</body>
</html>
