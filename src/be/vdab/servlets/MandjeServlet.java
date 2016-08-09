package be.vdab.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.vdab.dao.RetrovideoDAO;
import be.vdab.entities.Film;

@WebServlet("/mandje.htm")
public class MandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final transient RetrovideoDAO retrovideoDAO = new RetrovideoDAO();
	private static final String MANDJE = "mandje";
	private static final String VIEW = "/WEB-INF/JSP/mandje.jsp";

	public MandjeServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		//CHECK FOR SESSION THEN GET MANDJE FROM SESSION
		if (session != null) {
			@SuppressWarnings("unchecked")
			Set<Long> mandje = (Set<Long>) session.getAttribute(MANDJE);
			if (mandje != null) {
				List<Film> filmsInMandje = new ArrayList<>();
				for (long id : mandje) {
					filmsInMandje.add(retrovideoDAO.findFilmById(id));
				}
				request.setAttribute("FilmsInMandje", filmsInMandje);
			}
		}
		
		// GET ON WITH IT
		request.getRequestDispatcher(VIEW).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// CHECK FOR ID, THEN GET mandje FROM SESSION
		if (request.getParameter("id") != null) {
			HttpSession session = request.getSession();
			@SuppressWarnings("unchecked")
			Set<Long> mandje = (Set<Long>) session.getAttribute(MANDJE);
			if (mandje == null) {
				mandje = new LinkedHashSet<>();
			}
			String id = request.getParameter("id");
			mandje.add(Long.parseLong(id));

			}
		}
	}

