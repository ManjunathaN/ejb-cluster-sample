package com.psi;

import java.io.IOException;
import java.io.Writer;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private Hello bean;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		final Writer writer = response.getWriter();
		writer.append("<html>");
		writer.append("<body>");
		writer.append("<h1>Hello Servlet!!!</h1>");
		writer.append("<h1>"+bean.sayHello()+"!!!</h1>");

		writer.append("<br><br><b>System Properties!!!</b><br><br>");
		writer.append("<table style='width:100%'>");
		writer.append("<tr><th>Key</th><th>Value</th></tr>");

		Properties systemProperties = System.getProperties();
		SortedMap sortedSystemProperties = new TreeMap(systemProperties);
		Set keySet = sortedSystemProperties.keySet();
		Iterator iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String propertyName = (String) iterator.next();
			String propertyValue = systemProperties.getProperty(propertyName);
			writer.append("<tr><td>"+propertyName + "</td><td>" + propertyValue+ "</td></tr>");
		}
		writer.append("</table>");

		writer.append("</body>");
		writer.append("</html>");
	}
}
