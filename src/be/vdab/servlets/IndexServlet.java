package be.vdab.servlets;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.dao.FilmDAO;
import be.vdab.dao.GenreDAO;
import be.vdab.entities.Film;
import be.vdab.entities.Genre;

@WebServlet("/index.htm")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final transient GenreDAO genreDAO = new GenreDAO();
	private final transient FilmDAO filmDAO = new FilmDAO();
	private static final String VIEW = "/WEB-INF/JSP/index.jsp";

	@Resource(name = GenreDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		genreDAO.setDataSource(dataSource);
		filmDAO.setDataSource(dataSource);
	}
	

	public IndexServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// FIND GENRES (TO DISPLAY MENU)
		List<Genre> genres = genreDAO.findGenres();
		request.setAttribute("genres", genres);

		// FIND FILMS ON SELECTED GENRE
		if (request.getParameter("id") != null){
		try {
			long selectedGenre = Long.parseLong(request.getParameter("id"));
			List<Film> films = filmDAO.findFilmsByGenre(selectedGenre);
			request.setAttribute("films", films);
		} catch (NumberFormatException ex) {
			request.setAttribute("fout", "Genre id niet correct");
		}
		}	else {
			request.setAttribute("melding", "Selecteer een genre");
		}	
		
		
		//GET ON WITH IT
		request.getRequestDispatcher(VIEW).forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
