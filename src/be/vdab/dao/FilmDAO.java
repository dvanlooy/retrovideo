package be.vdab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.vdab.entities.Film;
import be.vdab.entities.Genre;
import be.vdab.exceptions.DAOException;
import be.vdab.exceptions.RetroException;

public class FilmDAO extends AbstractDAO {
	private final static Logger logger = Logger.getLogger(FilmDAO.class.getName());

	private static final String SELECT_FILMS = "SELECT * FROM films "
			+ "INNER JOIN genres ON films.genreid = genres.id "
			+ "WHERE genreid = ? "
			+ "ORDER BY titel ASC";
	private static final String SELECT_ONE_FILM = "SELECT * FROM films "
			+ "INNER JOIN genres ON films.genreid = genres.id "
			+ "WHERE films.id = ?";
	public static final String UPDATE_FILM_GERESERVEERD = "UPDATE films "
			+ "SET gereserveerd = gereserveerd + 1 "
			+ "WHERE id = ? "
			+ "AND voorraad - gereserveerd > 0";
	public static final String UPDATE_FILM_VERWIJDER_RESERVATIE = "UPDATE films "
			+ "SET gereserveerd = gereserveerd -1 "
			+ "WHERE id = ? "
			+ "AND gereserveerd > 0";

	/**
	 * Gets all films from a specified Genre from database
	 * 
	 * @param id
	 *            (genreid)
	 * @return List with Film objects
	 */
	public List<Film> findFilmsByGenre(long id) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_FILMS)) {
			List<Film> films = new ArrayList<>();
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					films.add(resultSetRowToFilm(resultSet));
				}
				return films;
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met database retrovideo", ex);
			throw new DAOException(ex);
		}
	}

	/**
	 * Gets a Film from database
	 * 
	 * @param id
	 * @return Film Object
	 */
	public Film findFilmById(long id) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_ONE_FILM)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				Film film = null;
				while (resultSet.next()) {
					film = resultSetRowToFilm(resultSet);
				}
				return film;
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met database retrovideo", ex);
			throw new DAOException(ex);
		}
	}

	/**
	 * Builds a Film object based on row data from ResultSet
	 * @param resultSet
	 * @return Film Object or null when Exception is thrown
	 * @throws DAOException
	 */
	private Film resultSetRowToFilm(ResultSet resultSet) throws DAOException {
		Film film = null;
		try {
			film = new Film(resultSet.getLong("films.id"),
					new Genre(resultSet.getLong("genres.id"), resultSet.getString("naam")),
					resultSet.getString("titel"), resultSet.getInt("voorraad"), resultSet.getInt("gereserveerd"),
					resultSet.getBigDecimal("prijs"));
		} catch (RetroException ex) {
			logger.log(Level.SEVERE, "Probleem met het aanmaken van Film Object", ex);
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met ResultSet verwerking", ex);
			throw new DAOException(ex);
		}
		return film;
	}

}
