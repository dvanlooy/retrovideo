package be.vdab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.vdab.entities.Film;
import be.vdab.entities.Genre;

public class RetrovideoDAO extends AbstractDAO {
	private static final String SELECT_GENRES = "SELECT * FROM genres ORDER BY naam";
	private static final String SELECT_FILMS = "SELECT * FROM films INNER JOIN genres ON films.genreid = genres.id WHERE genreid = ? ORDER BY titel";
	private static final String SELECT_ONE_FILM = "SELECT * FROM films INNER JOIN genres ON films.genreid = genres.id WHERE films.id = ?";
	private final static Logger logger = Logger.getLogger(RetrovideoDAO.class.getName());

	public List<Genre> findGenres() {
		try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_GENRES)) {
			List<Genre> genres = new ArrayList<>();
			while (resultSet.next()) {
				genres.add(resultSetRijNaarGenre(resultSet));
			}
			return genres;
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met database retrovideo", ex);
			throw new DAOException(ex);
		}
	}

	private Genre resultSetRijNaarGenre(ResultSet resultSet) throws SQLException {
		return new Genre(resultSet.getLong("id"), resultSet.getString("naam"));
	}

	public List<Film> findFilmsByGenre(long id) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_FILMS)) {
			List<Film> films = new ArrayList<>();
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					films.add(resultSetRijNaarFilm(resultSet));
				}
				return films;
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met database retrovideo", ex);
			throw new DAOException(ex);
		}
	}
	private Film resultSetRijNaarFilm(ResultSet resultSet) throws SQLException {
		return new Film(resultSet.getLong("films.id"), new Genre(resultSet.getLong("genres.id"), 
				resultSet.getString("naam")), resultSet.getString("titel"), resultSet.getInt("voorraad"), 
				resultSet.getInt("gereserveerd"), resultSet.getBigDecimal("prijs"));
	}
	public Film findFilmById(long id) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_ONE_FILM)) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				Film film = null;
				while (resultSet.next()) {
					film = resultSetRijNaarFilm(resultSet);
				}
				return film;
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met database retrovideo", ex);
			throw new DAOException(ex);
		}
	}
}
