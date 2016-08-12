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

import be.vdab.dao.KlantDAO;

@WebServlet("/bevestigen.htm")
public class BevestigenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final transient KlantDAO klantDAO = new KlantDAO();
	private static final String VIEW = "/WEB-INF/JSP/bevestigen.jsp";

	@Resource(name = KlantDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		klantDAO.setDataSource(dataSource);
	}

	public BevestigenServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// RETRIEVE mandje WITH ID's FROM SESSION
		@SuppressWarnings("unchecked")
		Set<Long> mandje = (Set<Long>) request.getSession().getAttribute("mandje");

		// COUNT ITEMS IN mandje & GET klant
		if (mandje != null) {
			request.setAttribute("aantal", mandje.size());
			try {
				long klantid = Long.parseLong(request.getParameter("id"));
				request.getSession().setAttribute("klant", klantDAO.findKlantById(klantid));
			} catch (NumberFormatException e) {
				request.setAttribute("fout", "klantid is niet correct");
			}

		} else {
			request.setAttribute("fout", "Er is geen winkelmandje");
		}

		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
