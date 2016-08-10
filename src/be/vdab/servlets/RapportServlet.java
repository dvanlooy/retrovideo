package be.vdab.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.dao.RetrovideoDAO;
import be.vdab.entities.Film;

@WebServlet("/rapport.htm")
public class RapportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final transient RetrovideoDAO retrovideoDAO = new RetrovideoDAO();
	private static final String VIEW = "/WEB-INF/JSP/rapport.jsp";

	@Resource(name = RetrovideoDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		retrovideoDAO.setDataSource(dataSource);
	}

	public RapportServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// RETRIEVE mandje WITH ID's FROM SESSION
		@SuppressWarnings("unchecked")
		Set<Long> mandje = (Set<Long>) request.getSession().getAttribute("mandje");
		// RETRIEVE klantid FROM SESSION
		long klantid = (Long) request.getSession().getAttribute("klantid");

		// PUT RESERVATIONS IN DATABASE
		Map<Film, Boolean> reservaties = new HashMap<>();
		for (long filmid : mandje) {
			if (retrovideoDAO.makeReservation(filmid, klantid)) {
				reservaties.put(retrovideoDAO.findFilmById(filmid), true);
			}else{
				reservaties.put(retrovideoDAO.findFilmById(filmid), false);
			}
		}
		

	}

}
