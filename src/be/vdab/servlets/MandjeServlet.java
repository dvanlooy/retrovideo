package be.vdab.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.dao.FilmDAO;
import be.vdab.entities.Film;

@WebServlet("/mandje.htm")
public class MandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final transient FilmDAO filmDAO = new FilmDAO();
	private static final String VIEW = "/WEB-INF/JSP/mandje.jsp";
	private static final String REDIRECT_URL = "%s/mandje.htm";

	@Resource(name = FilmDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		filmDAO.setDataSource(dataSource);
	}

	public MandjeServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// RETRIEVE mandje WITH ID's FROM SESSION
		@SuppressWarnings("unchecked")
		Set<Long> mandje = (Set<Long>) request.getSession().getAttribute("mandje");

		// CREATE LIST WITH FILM OBJECTS
		if (mandje != null) {
			List<Film> filmsInMandje = new ArrayList<>();
			for (long id : mandje) {
				filmsInMandje.add(filmDAO.findFilmById(id));
			}
			request.setAttribute("FilmsInMandje", filmsInMandje);
		}

		// GET ON WITH IT
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// RETRIEVE mandje WITH ID's FROM SESSION
		@SuppressWarnings("unchecked")
		Set<Long> mandje = (Set<Long>) request.getSession().getAttribute("mandje");

		// CREATE NEW mandje IF NECESSARY
		if (mandje == null) {
			mandje = new LinkedHashSet<>();
		}

		// CHECK IF ID NEEDS TO BE ADDED TO mandje
		if (request.getParameter("add") != null) {
			String id = request.getParameter("add");
			if (!mandje.contains(id)) {
				mandje.add(Long.parseLong(id));
			}
			request.getSession().setAttribute("mandje", mandje);
		}

		// CHECK IF ID's NEED TO BE REMOVED FROM mandje; WHEN NO FILMS IN
		// mandje, REMOVE SESSION ATTRIBUTE mandje
		if (request.getParameterValues("remove") != null) {
			for (String id : request.getParameterValues("remove")) {
				mandje.remove(Long.parseLong(id));
			}
			if (mandje.isEmpty()) {
				request.getSession().removeAttribute("mandje");
			} else {
				request.getSession().setAttribute("mandje", mandje);
			}
		}

		// GET ON WITH IT
		response.sendRedirect(String.format(REDIRECT_URL, request.getContextPath()));
	}

}
