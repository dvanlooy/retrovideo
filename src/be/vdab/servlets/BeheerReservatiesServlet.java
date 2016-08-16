package be.vdab.servlets;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.dao.FilmDAO;
import be.vdab.dao.KlantDAO;
import be.vdab.dao.ReservatieDAO;
import be.vdab.entities.Film;
import be.vdab.entities.Klant;
import be.vdab.entities.Reservatie;

@WebServlet("/beheerreservaties.htm")
public class BeheerReservatiesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final transient ReservatieDAO reservatieDAO = new ReservatieDAO();
	private final transient FilmDAO filmDAO = new FilmDAO();
	private final transient KlantDAO klantDAO = new KlantDAO();
	private static final String VIEW = "/WEB-INF/JSP/beheerreservaties.jsp";
	private static final String REDIRECT_URL = "%s/beheerreservaties.htm";

	@Resource(name = ReservatieDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		reservatieDAO.setDataSource(dataSource);
		klantDAO.setDataSource(dataSource);
		filmDAO.setDataSource(dataSource);
	}

	public BeheerReservatiesServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// FIND GERESERVEERDE FILMS
		List<Reservatie> reservaties = reservatieDAO.findReservaties();
		Set<Film> gereserveerdeFilms = new TreeSet<>();
		if (request.getParameter("filmid") == null && request.getParameter("klantid") == null) {
			for (Reservatie reservatie : reservaties) {
				gereserveerdeFilms.add(filmDAO.findFilmById(reservatie.getFilmid()));
			}
			request.setAttribute("gereserveerdeFilms", gereserveerdeFilms);
		}

		// FIND klanten WITH filmid IN RESERVATION
		Map<Klant, Timestamp> klantenMetFilmGereserveerd = new HashMap<>();
		if (request.getParameter("filmid") != null) {
			try {
				long filmid = Long.parseLong(request.getParameter("filmid"));
				for (Reservatie reservatie : reservaties) {
					if (reservatie.getFilmid() == filmid) {
						klantenMetFilmGereserveerd.put(klantDAO.findKlantById(reservatie.getKlantid()),
								reservatie.getReservatieDatum());
					}
				}
				request.setAttribute("geselecteerdeFilm", filmDAO.findFilmById(filmid));
				request.setAttribute("klantenMetFilmGereserveerd", klantenMetFilmGereserveerd);
			} catch (NumberFormatException e) {
				request.setAttribute("fout", "Geen correct filmid opgegeven");
			}
		}

		// GET ON WITH IT
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// REMOVE reservatie
		if (Boolean.parseBoolean(request.getParameter("remove"))) {
			try {
				long filmid = Long.parseLong(request.getParameter("filmid"));
				long klantid = Long.parseLong(request.getParameter("klantid"));
				Timestamp reservatieDatum = Timestamp.valueOf(request.getParameter("reservatieDatum"));

				reservatieDAO.removeReservation(filmid, klantid, reservatieDatum);

			} catch (IllegalArgumentException e) {
				request.setAttribute("fout", "Fout bij het verzamelen van de parameters");
			}
		}
		// GET ON WITH IT
		response.sendRedirect(String.format(REDIRECT_URL, request.getContextPath()));
	}

}
