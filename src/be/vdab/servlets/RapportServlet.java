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

import be.vdab.dao.FilmDAO;
import be.vdab.dao.ReservatieDAO;
import be.vdab.entities.Film;
import be.vdab.entities.Klant;

@WebServlet("/rapport.htm")
public class RapportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final transient ReservatieDAO reservatieDAO = new ReservatieDAO();
	private final transient FilmDAO filmDAO = new FilmDAO();
	private static final String VIEW = "/WEB-INF/JSP/rapport.jsp";
	private static final String REDIRECT_URL = "%s/rapport.htm";

	@Resource(name = ReservatieDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		reservatieDAO.setDataSource(dataSource);
		filmDAO.setDataSource(dataSource);
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

		// RETRIEVE klant FROM SESSION
		Klant klant = (Klant) request.getSession().getAttribute("klant");
		long klantid = klant.getId();

		// PUT RESERVATIONS IN DATABASE
		Map<Film, Boolean> reservaties = new HashMap<>();
		for (long filmid : mandje) {
			reservaties.put(filmDAO.findFilmById(filmid), reservatieDAO.makeReservation(filmid, klantid));
			System.out.println(reservaties.get(filmDAO.findFilmById(filmid)));
		}

		// IF NO FALSE VALUES RESERVATION IS SUCCESFULL
		if (!reservaties.values().contains(false)) {
			System.out.println("zou je niet mogen zien staan");
			request.getSession().setAttribute("succes", "De Reservatie is OK");
		}
		else{
			request.getSession().removeAttribute("succes");
		}
		request.getSession().removeAttribute("mandje");
		request.getSession().removeAttribute("klantid");
		request.getSession().setAttribute("reservaties", reservaties);

		// GET ON WITH IT
		response.sendRedirect(String.format(REDIRECT_URL, request.getContextPath()));
	}

}
