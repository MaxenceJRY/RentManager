package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	@Autowired
	private ReservationService reservationService;
	private ClientDao clientDao;
	
	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	
	public long create(Client client) throws ServiceException {
		try	{
			return clientDao.create(client);
		} catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public Client findById(long id) throws ServiceException {
		// TODO: récupérer un client par son id
		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public List<Client> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public long delete(int number) throws ServiceException {
		try {
			List<Reservation> reservations = reservationService.findResaByClientId(number);
				for (Reservation r : reservations) reservationService.delete(r.id());
			return clientDao.delete(findById(number));
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public int count() throws ServiceException, DaoException {
		try {
			return clientDao.count();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public boolean eighteen(Client client) {
        return ChronoUnit.YEARS.between(client.naissance(), LocalDate.now()) >= 18;
	}

	public boolean emailExists(Client client) throws ServiceException {
		List<Client> clients = new ArrayList<>();
        clients = this.findAll();
        for (Client c : clients) {
			if (c.email().equals(client.email())) {
				return false;
			}
		}
		return true;
	}

	public boolean lastFirstName_lenght(Client client) {
		return client.nom().length() >= 3 && client.prenom().length() >= 3;
	}
}
