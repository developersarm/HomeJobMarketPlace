@echo off
set TOMCAT_HOME=D:\abhay\apache-tomcat-7.0.82\apache-tomcat-7.0.82
set CATALINA_HOME=D:\abhay\apache-tomcat-7.0.82\apache-tomcat-7.0.82
set JAVA_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9797
%CATALINA_HOME%\bin\catalina.bat run -config conf/myapp.xml