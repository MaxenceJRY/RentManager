package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {

	private VehicleDao() {}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele ,nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele ,nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException, ServiceException {
		if(vehicle.constructeur() == null || vehicle.modele() == null || vehicle.nb_places() < 2) {
			throw new ServiceException("Le constructeur et le modèle du véhicule sont obligatoire et le nombre de places doit être supérieur à 1.");
		}
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setString(1, vehicle.constructeur());
			prepareStatement.setString(2, vehicle.modele());
			prepareStatement.setInt(3, vehicle.nb_places());
			prepareStatement.execute();
			prepareStatement.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du véhicule: " + e.getMessage());
		}
		return 0;
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, vehicle.id());
			prepareStatement.execute();
			prepareStatement.close();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du véhicule: " + e.getMessage());
		}
		return 0;
	}

	public Vehicle findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(FIND_VEHICLE_QUERY);
			prepareStatement.setLong(1, id);
			ResultSet resultSet = prepareStatement.executeQuery();
			String constructeur = null;
			String modele = null;
			int nb_places = 0;
			if(resultSet.next()) {
				constructeur = resultSet.getString("constructeur");
				modele = resultSet.getString("modele");
				nb_places = resultSet.getInt("nb_places");
				return new Vehicle(id, constructeur, modele, nb_places);
			}
			prepareStatement.close();
		}catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du véhicule: " + e.getMessage());
		}
		return new Vehicle();
	}

	public List<Vehicle> findAll() throws DaoException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(FIND_VEHICLES_QUERY);
			ResultSet resultSet = prepareStatement.executeQuery();
			List<Vehicle> vehicles = new ArrayList<Vehicle>();
			while(resultSet.next()) {
				Vehicle vehicle= new Vehicle(resultSet.getInt("id"),
									resultSet.getString("constructeur"),
									resultSet.getString("modele"),
									resultSet.getInt("nb_places"));
				vehicles.add(vehicle);
			}
			return vehicles;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche des véhicules: " + e.getMessage());
		}

	}

	public int count () throws DaoException {
		try {
			return this.findAll().size();
		} catch (DaoException e) {
			throw new DaoException("Erreur lors du comptage des véhicules: " + e.getMessage());
		}
	}
	

}
