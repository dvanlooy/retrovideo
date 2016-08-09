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

import be.vdab.dao.RetrovideoDAO;
import be.vdab.entities.Genre;

@WebServlet("/index.htm")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final transient RetrovideoDAO retrovideoDAO = new RetrovideoDAO();
	private static final String VIEW = "/WEB-INF/JSP/index.jsp";

	@Resource(name = RetrovideoDAO.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		retrovideoDAO.setDataSource(dataSource);
	}

	public IndexServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// FIND GENRES (TO DISPLAY MENU)
		List<Genre> genres = retrovideoDAO.findGenres();
		request.setAttribute("genres", genres);

		// FIND FILMS ON SELECTED GENRE
		long selectedGenre = 0;
		try {
			selectedGenre = Long.parseLong(request.getParameter("id"));

		} catch (Exception ex) {
			request.setAttribute("fout", "Nummer niet correct");
		}
		
		
		
		//GET ON WITH IT
		request.getRequestDispatcher(VIEW).forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
