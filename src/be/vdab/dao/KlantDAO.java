package be.vdab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.vdab.entities.Klant;
import be.vdab.exceptions.DAOException;
import be.vdab.exceptions.RetroException;

public class KlantDAO extends AbstractDAO {
	private final static Logger logger = Logger.getLogger(KlantDAO.class.getName());
	
	private static final String SELECT_KLANTEN_FAMILIENAAM = "SELECT * FROM klanten "
			+ "WHERE familienaam LIKE ? "
			+ "ORDER BY familienaam ASC";
	private static final String SELECT_KLANT = "SELECT * FROM klanten "
			+ "WHERE id = ?";
	
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
	 * Gets a Klant from database
	 * @param id
	 * @return Klant Object
	 */
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
	
	/**
	 * Builds a Klant object based on row data from ResultSet
	 * @param resultSet
	 * @return Klant Object or null when Exception is thrown
	 * @throws DAOException
	 */
	private Klant resultSetRowToKlant(ResultSet resultSet) throws DAOException {
		Klant klant = null;
		try {
			klant = new Klant(resultSet.getLong("id"), resultSet.getString("familienaam"), 
					resultSet.getString("voornaam"), resultSet.getString("straatNummer"), 
					resultSet.getString("postcode"), resultSet.getString("gemeente"));
		} catch (RetroException ex) {
			logger.log(Level.SEVERE, "Probleem met het aanmaken van Klant Object", ex);
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met ResultSet verwerking", ex);
			throw new DAOException(ex);
		}
		return klant;
	}
}
