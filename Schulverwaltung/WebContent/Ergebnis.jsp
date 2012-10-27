<%@page import="model.ZinsberechnungsBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ergebnis</title>
</head>
<body>
	<%
	try
	{
		double geldStart = Double.parseDouble(String.valueOf(request.getAttribute("GeldStart")));
		double zinsen = Double.parseDouble(String.valueOf(request.getAttribute("Zins")));
		double laufzeit = Double.parseDouble(String.valueOf(request.getAttribute("Laufzeit")));
		double endbetrag = Double.parseDouble(String.valueOf(request.getAttribute("Endbetrag")));

		out.write("Anfangsbetrag: " + geldStart + " € <br/>");
		out.write("Zinssatz: " + zinsen + " % <br/>"); 
		out.write("Laufzeit: " + laufzeit + " Jahre <br/>");
		
		out.write("---------------------------------------<br/>");
		out.write("Endbetrag: " + (Math.round(endbetrag) * 100) / 100.0 + " € <br/>");
	}
	catch(Exception ex)
	{
		if(ex.getMessage().contains("empty String"))
		{
			out.write("Es wurden nicht alle Daten eingegeben <br/>");
		}
		if(ex.getMessage().contains("For input string:"))
		{
			out.write("Es wurden falsche Daten eingegeben <br/>");
		}
		out.write("Sie werden in 3 Sekunden weitergeleitet<br/>");
		out.write("<script>setTimeout('history.back()', 3000)</script>");
	}
		
	%>
</body>
</html>