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
import java.util.Optional;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	private ClientDao() {}
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";

	public long create(Client client) throws DaoException, ServiceException {
		if(client.nom() == null || client.prenom() == null) {
			throw new ServiceException("Le nom et le prénom du client sont obligatoires");
		}
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setString(1, client.nom().toUpperCase());
			prepareStatement.setString(2, client.prenom());
			prepareStatement.setString(3, client.email());
			prepareStatement.setDate(4, Date.valueOf(client.naissance()));
			prepareStatement.execute();
			prepareStatement.close();
		return 0;
	} catch (SQLException e) {
            throw new DaoException("Erreur lors de la création du client: " + e.getMessage());
        }
	}

        public long delete(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(DELETE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, client.id());
			prepareStatement.execute();
			prepareStatement.close();
		}catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du client: " + e.getMessage());
		}
		return 0;
	}

	public Client findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(FIND_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepareStatement.setLong(1, id);
			ResultSet resultSet = prepareStatement.executeQuery();
			String nom = null, prenom = null, email = null;
			LocalDate naissance = null;
			if(resultSet.next()) {
				nom = resultSet.getString("nom");
				prenom = resultSet.getString("prenom");
				email = resultSet.getString("email");
				naissance = resultSet.getDate("naissance").toLocalDate();
			}
			prepareStatement.close();
			return new Client(id, nom, prenom, email, naissance);
		}catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du client: " + e.getMessage());
		}
	}

	public List<Client> findAll() throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(FIND_CLIENTS_QUERY, Statement.RETURN_GENERATED_KEYS);
			ResultSet resultSet = prepareStatement.executeQuery();
			List<Client> clients = new ArrayList<>();
			while(resultSet.next()) {
				clients.add(new Client(resultSet.getLong("id"), resultSet.getString("nom"),
						resultSet.getString("prenom"),
						resultSet.getString("email"),
						resultSet.getDate("naissance").toLocalDate()));
			}
			prepareStatement.close();
			return clients;
		}catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche des clients: " + e.getMessage());
		}

	}
}

