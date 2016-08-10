package be.vdab.servlets;

import java.io.IOException;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.dao.RetrovideoDAO;


@WebServlet("/bevestigen.htm")
public class BevestigenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final transient RetrovideoDAO retrovideoDAO = new RetrovideoDAO();
	private static final String VIEW = "/WEB-INF/JSP/bevestigen.jsp";
	
	@Resource(name = RetrovideoDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		retrovideoDAO.setDataSource(dataSource);
	}
	
    public BevestigenServlet() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// RETRIEVE mandje WITH ID's FROM SESSION
		@SuppressWarnings("unchecked")
		Set<Long> mandje = (Set<Long>) request.getSession().getAttribute("mandje");
		// COUNT ITEMS IN MANDJE
		if (mandje != null) {
			request.setAttribute("aantal", mandje.size());
		}else{
			request.setAttribute("leeg", "Er is geen winkelmandje");
		}
		// GET klant FROM PARAMETER
		long klantid = Long.parseLong(request.getParameter("id"));
		request.setAttribute("klant", retrovideoDAO.findKlantById(klantid));
		request.getSession().setAttribute("klantid", klantid);
		
		
		request.getRequestDispatcher(VIEW).forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
