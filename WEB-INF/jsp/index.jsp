<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.care.context.MyApplicationContext"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Home Job Marketplace</title>
    <link type="text/css" rel="stylesheet" href="/HomeJobMarketplace/css/bootstrap.css">
    <script src="/HomeJobMarketplace/js/bootstrap.js"></script>
</head>
<body>
    <div class="container mt-5 w-25">
        <div class="page-header">
            <h2 class="text-center mb-5 font-weight-bold">Home Job Marketplace</h2>
        </div>

        <c:if test="${not empty requestScope.msg}">
            <div class="alert alert-success">
              ${requestScope.msg}
            </div>
        </c:if>

        <c:if test="${not empty requestScope.error}">
            <div class="alert alert-danger">
              ${requestScope.error}
            </div>
        </c:if>

        <html:form action="visitor/login" method="post">
            <div class="form-group">
                <label for="email">Email address:</label>
                <html:text property="emailId" styleClass="form-control" styleId="email"/>
                <html:errors property="emailId"/>
            </div>
            <div class="form-group">
                <label for="pwd">Password:</label>
                <html:password property="password" styleClass="form-control" styleId="pwd"/>
                <html:errors property="password"/>
            </div>

            <div style="text-align:center">
                <html:submit value="Login" styleClass="btn btn-default"/>
            </div>
        </html:form>

        <div>
            <div style="text-align:center">
                <a href="/HomeJobMarketplace/visitor/reset-password">Forgot Password? Click here..</a>
            </div>

            <h3 class="text-center mt-1">or</h3>

            <div style="text-align:center">
                First time? Choose your purpose..
            </div>

        </div>

        <div class="row mt-2" >
            <div class="col align-self-center" style="text-align:center">
                <html:link action="visitor/register.do?type=sitter" styleClass="btn btn-dark">Apply for a Job</html:link>
            </div>
            <div class="col align-self-center" style="text-align:center">
                <html:link action="visitor/register.do?type=seeker" styleClass="btn btn-dark">Post a Job</html:link>
            </div>
        </div>
    </div>
</body>
</html>