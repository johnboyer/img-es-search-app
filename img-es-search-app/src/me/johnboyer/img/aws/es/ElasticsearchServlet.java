package me.johnboyer.img.aws.es;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class ElasticsearchServlet
 */
@WebServlet(urlPatterns="/es")
public class ElasticsearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger LOG = Logger.getLogger(ElasticsearchServlet.class);
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchQuery = request.getParameter("search");
		LOG.debug(searchQuery);
		try {
			ElasticsearchService service = ElasticsearchService.getInstance();
			String json = service.search(searchQuery);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			response.setContentType("application/json");
			response.setHeader("Access-Control-Allow-Origin", "*");
			PrintWriter writer = response.getWriter();
			writer.println(json);
			
		} catch (ConcurrentException e) {
			throw new IOException("Unable to connect to search service", e);

		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new ServletException("POST unsupported");
	}

}
