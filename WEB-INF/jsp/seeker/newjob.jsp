<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>New Job Post</title>
    <link type="text/css" rel="stylesheet" href="/HomeJobMarketplace/css/bootstrap.css">
    <script src="/HomeJobMarketplace/js/bootstrap.js"></script>
</head>
<body>
    <div class="container mt-5 w-25 mb-5 center-block">
        <div id="wrapper">
            <div id="header">
                <h3 class="text-center mb-3">Post a new Job</h3>
            </div>
        </div>

        <html:form action="seeker/save-job" method="post">

            <div class="form-group">
                <label for="title">Title:</label>
                <html:text property="title" styleId="title" styleClass="form-control"/>
                <html:errors property="title"/>
            </div>

            <div class="form-group">
                <label for="sdate">Start Date:</label>
                <input type="date" class="form-control" id="sdate" name="startDate" value="${jobForm.startDate}">
                <html:errors property="startDate"/>
            </div>

            <div class="form-group">
                <label for="edate">End Date:</label>
                <input type="date" class="form-control" id="edate" name="endDate" value="${jobForm.endDate}">
                <html:errors property="endDate"/>
            </div>

            <div class="form-group">
                <label for="payperhr">Pay Per Hour:</label>
                <input type="number" step="any" class="form-control" id="payperhr" name="payPerHour" value="${jobForm.payPerHour}">
                <html:errors property="payPerHour"/>
            </div>

            <div style="text-align:center">
                <html:submit styleClass="btn btn-default" value="PostJob"/>
            </div>

        </html:form>
        </br></br>
        <div style="text-align:center">
            <a href="/HomeJobMarketplace/" class="btn btn-primary" role="button" >Home</a>
        </div>
    </div>
</body>
</html>