package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, reservation.client_id());
			prepareStatement.setLong(2, reservation.vehicle_id());
			prepareStatement.setDate(3, Date.valueOf(reservation.debut()));
			prepareStatement.setDate(4, Date.valueOf(reservation.fin()));
			prepareStatement.execute();
			prepareStatement.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création de la réservation: " + e.getMessage());
		}
		return 0;
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, reservation.id());
			prepareStatement.execute();
			prepareStatement.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression de la réservation: " + e.getMessage());
		}
		return 0;
	}

	public Reservation findResaByID(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM Reservation WHERE id=?;");
			prepareStatement.setLong(1, id);
			ResultSet resultSet = prepareStatement.executeQuery();
			long clientId = 0;
			long vehicleId = 0;
			LocalDate debut = null;
			LocalDate fin = null;
			while(resultSet.next()) {
				clientId = resultSet.getLong("client_id");
				vehicleId = resultSet.getLong("vehicle_id");
				debut = resultSet.getDate("debut").toLocalDate();
				fin = resultSet.getDate("fin").toLocalDate();
			}
			prepareStatement.close();
			return new Reservation(id, clientId, vehicleId, debut, fin);
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération de la réservation: " + e.getMessage());
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, clientId);
			ResultSet resultSet = prepareStatement.executeQuery();
			List<Reservation> reservations = new ArrayList<Reservation>();
			while(resultSet.next()) {
				Reservation reservation = new Reservation(resultSet.getLong("id"),
												clientId, resultSet.getLong("vehicle_id"),
												resultSet.getDate("debut").toLocalDate(), resultSet.getDate("fin").toLocalDate());
				reservations.add(reservation);
			}
			prepareStatement.close();
			return reservations;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des réservations: " + e.getMessage());
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, vehicleId);
			ResultSet resultSet = prepareStatement.executeQuery();
			List<Reservation> reservations = new ArrayList<Reservation>();
			while(resultSet.next()) {
				Reservation reservation = new Reservation(resultSet.getLong("id"),
												resultSet.getLong("client_id"), vehicleId,
												resultSet.getDate("debut").toLocalDate(), resultSet.getDate("fin").toLocalDate());
				reservations.add(reservation);
			}
			prepareStatement.close();
			return reservations;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des réservations: " + e.getMessage());
		}
	}

	public List<Reservation> findAll() throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(FIND_RESERVATIONS_QUERY, Statement.RETURN_GENERATED_KEYS);
			ResultSet resultSet = prepareStatement.executeQuery();
			List<Reservation> reservations = new ArrayList<Reservation>();
			while(resultSet.next()) {
				Reservation reservation = new Reservation(resultSet.getLong("id"),
												resultSet.getLong("client_id"), resultSet.getLong("vehicle_id"),
												resultSet.getDate("debut").toLocalDate(), resultSet.getDate("fin").toLocalDate());
				reservations.add(reservation);
			}
			prepareStatement.close();
			return reservations;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des réservations: " + e.getMessage());
		}
	}
}
