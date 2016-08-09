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

public class RetrovideoDAO extends AbstractDAO {
	private static final String SELECT_GENRES = "SELECT * FROM retrovideo.genres ORDER BY naam";
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
}
