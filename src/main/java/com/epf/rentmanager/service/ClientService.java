package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}
	
	
	public long create(Client client) throws ServiceException {
		// TODO: créer un client
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

	public void delete(int number) throws ServiceException {
		try {
			clientDao.delete(findById(number));
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
}
