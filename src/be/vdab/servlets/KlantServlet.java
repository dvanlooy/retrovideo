package be.vdab.servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.dao.KlantDAO;

@WebServlet("/klant.htm")
public class KlantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final transient KlantDAO klantDAO = new KlantDAO();
	private static final String VIEW = "/WEB-INF/JSP/klant.jsp";

	@Resource(name = KlantDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		klantDAO.setDataSource(dataSource);
	}

	public KlantServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getParameter("zoekopdracht") != null) {
			if (!request.getParameter("zoekopdracht").isEmpty()) {
				request.setAttribute("klanten",
						klantDAO.findKlantenByFamilienaam(request.getParameter("zoekopdracht")));
			} else {
				request.setAttribute("fout", "tik minstens één letter");
			}
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
