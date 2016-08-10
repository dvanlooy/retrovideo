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
import be.vdab.entities.Klant;

public class RetrovideoDAO extends AbstractDAO {
	private static final String SELECT_GENRES = "SELECT * FROM genres ORDER BY naam";
	private static final String SELECT_FILMS = "SELECT * FROM films INNER JOIN genres ON films.genreid = genres.id WHERE genreid = ? ORDER BY titel ASC";
	private static final String SELECT_ONE_FILM = "SELECT * FROM films INNER JOIN genres ON films.genreid = genres.id WHERE films.id = ?";
	
	private static final String SELECT_KLANTEN_FAMILIENAAM = "SELECT * FROM klanten WHERE familienaam LIKE ? ORDER BY familienaam ASC";
	private static final String SELECT_KLANT = "SELECT * FROM klanten WHERE id = ?";
	
	private final static Logger logger = Logger.getLogger(RetrovideoDAO.class.getName());

	
	//METHODS FOR FILMS
	
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

	/**
	 * Gets all films from a specified Genre from database
	 * @param id (genreid)
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
	 * Builds a Film object based on row data from ResultSet
	 * @param resultSet
	 * @return Film Object
	 * @throws SQLException
	 */
	private Film resultSetRowToFilm(ResultSet resultSet) throws SQLException {
		return new Film(resultSet.getLong("films.id"), new Genre(resultSet.getLong("genres.id"), 
				resultSet.getString("naam")), resultSet.getString("titel"), resultSet.getInt("voorraad"), 
				resultSet.getInt("gereserveerd"), resultSet.getBigDecimal("prijs"));
	}
	
	/**
	 * Gets a Film from database
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
	
	//METHODS FOR KLANT
	/**
	 * Gets all klanten from a specified zoekopdracht from database
	 * @param zoekopdracht
	 * @return List with Klant objects
	 */
	public List<Klant> findKlantenByFamilienaam(String zoekopdracht){
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_KLANTEN_FAMILIENAAM)) {
			
			String zoekopdracht_SQL = "%"+zoekopdracht+"%";
			List<Klant> klanten = new ArrayList<>();
			
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			statement.setString(1, zoekopdracht_SQL);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					klanten.add(resultSetRowToKlant(resultSet));
				}
				return klanten;
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met database retrovideo", ex);
			throw new DAOException(ex);
		}
	}
	/**
	 * Builds a Klant object based on row data from ResultSet
	 * @param resultSet
	 * @return Klant Object
	 * @throws SQLException
	 */
	private Klant resultSetRowToKlant(ResultSet resultSet) throws SQLException {
		return new Klant(resultSet.getLong("id"), resultSet.getString("familienaam"), 
				resultSet.getString("voornaam"), resultSet.getString("straatNummer"), 
				resultSet.getString("postcode"), resultSet.getString("gemeente"));
	}
	public Klant findKlantById(long id) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_KLANT)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				Klant klant = null;
				while (resultSet.next()) {
					klant = resultSetRowToKlant(resultSet);
				}
				return klant;
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met database retrovideo", ex);
			throw new DAOException(ex);
		}
	}
}
