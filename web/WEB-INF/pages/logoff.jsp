<%@ page import="javax.servlet.http.Cookie" %>
<%@ page import="org.springframewor.ui.rememberme.TokenBasedRememberMeServices" %>
<%
session.invalidate();
Cookie terminate = new Cookie(TokenBasedRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY, null);
terminate.setMaxAge(0);
response.addCookie(terminate);
response.sendRedirect("login.action");
%>