package be.vdab.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import be.vdab.entities.Reservatie;
import be.vdab.exceptions.DAOException;
import be.vdab.exceptions.RetroException;

public class ReservatieDAO extends AbstractDAO {
	private final static Logger logger = Logger.getLogger(ReservatieDAO.class.getName());
	// private final transient FilmDAO filmDAO = new FilmDAO();

	private static final String INSERT_RESERVATIE = "INSERT INTO reservaties " + "(klantid, filmid, reservatieDatum) "
			+ "VALUES (?, ? , {fn now()})";
	private static final String SELECT_RESERVATIES = "SELECT * FROM reservaties " + "ORDER BY filmid ASC";
	private static final String DELETE_RESERVATIE = "DELETE FROM reservaties " + "WHERE klantid = ? "
			+ "AND filmid = ? " + "AND reservatieDatum = ?";

	/**
	 * Make required updates in database for 1 film/reservation
	 * 
	 * @param filmid
	 * @param klantid
	 * @return true when update successful
	 */
	public boolean makeReservation(long filmid, long klantid) throws DAOException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement_1 = connection.prepareStatement(FilmDAO.UPDATE_FILM_GERESERVEERD);
				PreparedStatement statement_2 = connection.prepareStatement(INSERT_RESERVATIE)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.setAutoCommit(false);

			statement_1.setLong(1, filmid);

			statement_2.setLong(1, klantid);
			statement_2.setLong(2, filmid);

			int filmsUpdated = statement_1.executeUpdate();
			if (filmsUpdated == 1) {
				statement_2.executeUpdate();
				connection.commit();
				return true;
			} else {
				connection.rollback();
				return false;
			}

		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met database retrovideo", ex);
			throw new DAOException(ex);
		}
	}

	/*
	 * DEZE METHOD VALT BUITEN DE SCOPE VAN DE OPDRACHT
	 * MAAR WERD AANGEMAAKT OM EENVOUDIG DE RESERVATIES TE CONTROLEREN/VERWIJDEREN
	 */
	/**
	 * Gets all reservaties from database
	 * 
	 * @return List with Reservatie objects
	 */
	public List<Reservatie> findReservaties() throws DAOException {
		try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_RESERVATIES)) {
			List<Reservatie> reservaties = new ArrayList<>();
			while (resultSet.next()) {
				reservaties.add(resultSetRowToReservatie(resultSet));
			}
			return reservaties;
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met database retrovideo", ex);
			throw new DAOException(ex);
		}
	}

	/*
	 * DEZE METHOD VALT BUITEN DE SCOPE VAN DE OPDRACHT
	 * MAAR WERD AANGEMAAKT OM EENVOUDIG DE RESERVATIES TE CONTROLEREN/VERWIJDEREN
	 */
	/**
	 * Builds a Reservatie object based on row data from ResultSet
	 * 
	 * @param resultSet
	 * @return Reservatie Object
	 * @throws DAOException
	 */
	private Reservatie resultSetRowToReservatie(ResultSet resultSet) throws DAOException {
		Reservatie reservatie = null;
		try {
			reservatie = new Reservatie(resultSet.getLong("klantid"), resultSet.getLong("filmid"),
					resultSet.getTimestamp("reservatieDatum"));
		} catch (RetroException ex) {
			logger.log(Level.SEVERE, "Probleem met het aanmaken van Reservatie Object", ex);
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met ResultSet verwerking", ex);
			throw new DAOException(ex);
		}
		return reservatie;
	}

	/*
	 * DEZE METHOD VALT BUITEN DE SCOPE VAN DE OPDRACHT
	 * MAAR WERD AANGEMAAKT OM EENVOUDIG DE RESERVATIES TE CONTROLEREN/VERWIJDEREN
	 */
	/**
	 * Removes a reservation and adjust gereserveerd in film
	 * 
	 * @param filmid
	 * @param klantid
	 * @param reservatieDatum
	 * @return true when reservation removed
	 */
	public void removeReservation(long filmid, long klantid, Timestamp reservatieDatum) throws DAOException {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement_1 = connection.prepareStatement(FilmDAO.UPDATE_FILM_VERWIJDER_RESERVATIE);
				PreparedStatement statement_2 = connection.prepareStatement(DELETE_RESERVATIE)) {
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.setAutoCommit(false);

			statement_1.setLong(1, filmid);

			statement_2.setLong(1, klantid);
			statement_2.setLong(2, filmid);
			statement_2.setTimestamp(3, reservatieDatum);

			int resultReturned = statement_1.executeUpdate();
			if (resultReturned == 1) {
				statement_2.executeUpdate();
				connection.commit();
			} else {
				statement_2.executeUpdate();
				connection.commit();
				logger.log(Level.WARNING, "Film was niet gereserveerd, reservatierecord verwijderd!");
			}

		} catch (SQLException ex) {
			logger.log(Level.SEVERE, "Probleem met database retrovideo", ex);
			throw new DAOException(ex);
		}
	}
}
