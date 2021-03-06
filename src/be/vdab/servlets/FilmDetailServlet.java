package be.vdab.servlets;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.dao.FilmDAO;
import be.vdab.entities.Film;

@WebServlet("/filmdetail.htm")
public class FilmDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final transient FilmDAO filmDAO = new FilmDAO();
	private static final String VIEW = "/WEB-INF/JSP/filmdetail.jsp";

	@Resource(name = FilmDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		filmDAO.setDataSource(dataSource);
	}

	public FilmDetailServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// FIND FILM ON SELECTED ID
		if (request.getParameter("id") != null) {
			try {
				long selectedFilm = Long.parseLong(request.getParameter("id"));
				Film film = filmDAO.findFilmById(selectedFilm);
				long beschikbaar = film.getVoorraad() - film.getGereserveerd();
				request.setAttribute("beschikbaar", beschikbaar);
				request.setAttribute("film", film);
			} catch (NumberFormatException ex) {
				request.setAttribute("fout", "Film id is niet correct");
			}
		}else{
			request.setAttribute("fout", "Geen film geselecteerd");
		}

		// GET ON WITH IT
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
