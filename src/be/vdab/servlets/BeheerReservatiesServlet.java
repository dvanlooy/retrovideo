package be.vdab.servlets;

import java.io.IOException;
import java.util.HashSet;
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

		List<Reservatie> reservaties = reservatieDAO.findReservaties();
		Set<Film> gereserveerdeFilms = new HashSet<>();
		Set<Klant> klantenMetFilmGereserveerd = new HashSet<>();

		for (Reservatie reservatie : reservaties) {
			gereserveerdeFilms.add(filmDAO.findFilmById(reservatie.getFilmid()));
		}

		request.setAttribute("gereserveerdeFilms", gereserveerdeFilms);
		request.setAttribute("reservaties", reservaties);

		// FIND klanten WITH filmid IN RESERVATION
		if (request.getParameter("filmid") != null) {
			try {
				long filmid = Long.parseLong(request.getParameter("filmid"));
				for (Reservatie reservatie : reservaties) {
					if (reservatie.getFilmid() == filmid) {
						klantenMetFilmGereserveerd.add(klantDAO.findKlantById(reservatie.getKlantid()));
					}
				}
				request.setAttribute("geselecteerdeFilm", filmDAO.findFilmById(filmid).getTitel());
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

		// GET ON WITH IT
		response.sendRedirect(String.format(REDIRECT_URL, request.getContextPath()));
	}

}
