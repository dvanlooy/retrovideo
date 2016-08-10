package be.vdab.servlets;

import java.io.IOException;
import java.util.ArrayList;
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

import be.vdab.dao.RetrovideoDAO;
import be.vdab.entities.Film;
import be.vdab.entities.Klant;
import be.vdab.entities.Reservatie;

@WebServlet("/beheerreservaties.htm")
public class BeheerReservatiesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final transient RetrovideoDAO retrovideoDAO = new RetrovideoDAO();
	private static final String VIEW = "/WEB-INF/JSP/beheerreservaties.jsp";
	private static final String REDIRECT_URL = "%s/beheerreservaties.htm";

	@Resource(name = RetrovideoDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		retrovideoDAO.setDataSource(dataSource);
	}

	public BeheerReservatiesServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Reservatie> reservaties = retrovideoDAO.findReservaties();
		
		Set<Film> gereserveerdeFilms = new HashSet<>();
		Set<Klant> klantenMetReservaties = new HashSet<>();
		for (Reservatie reservatie : reservaties) {
			gereserveerdeFilms.add(retrovideoDAO.findFilmById(reservatie.getFilmid()));
			klantenMetReservaties.add(retrovideoDAO.findKlantById(reservatie.getKlantid()));

		}
		request.setAttribute("reservaties", reservaties);
		request.setAttribute("gereserveerdeFilms", gereserveerdeFilms);
		request.setAttribute("klantenMetReservaties", klantenMetReservaties);
		// GET ON WITH IT
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// GET ON WITH IT
		response.sendRedirect(String.format(REDIRECT_URL, request.getContextPath()));
	}

}
