package be.vdab.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.vdab.entities.Genre;

public class GenreDAO extends AbstractDAO {
	private final static Logger logger = Logger.getLogger(GenreDAO.class.getName());
	
	private static final String SELECT_GENRES = "SELECT * FROM genres ORDER BY naam";
	
	/**
	 * Gets all genres from database
	 * @return List with Genre objects
	 */
	public List<Genre> findGenres() {
		try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_GENRES)) {
			List<Genre> genres = new ArrayList<>();
			while (resultSet.next()) {
				genres.add(resultSetRowToGenre(resultSet));
			}
			return genres;
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met database retrovideo", ex);
			throw new DAOException(ex);
		}
	}
	
	/**
	 * Builds a Genre object based on row data from ResultSet
	 * @param resultSet
	 * @return Genre Object
	 * @throws SQLException
	 */
	private Genre resultSetRowToGenre(ResultSet resultSet) throws SQLException {
		return new Genre(resultSet.getLong("id"), resultSet.getString("naam"));
	}
	
}
