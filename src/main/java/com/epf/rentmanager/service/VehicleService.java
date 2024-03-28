package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	@Autowired
	private ReservationService reservationService;
    private final VehicleDao vehicleDao;
	
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}
	
	public long create(Vehicle vehicle) throws ServiceException, DaoException {
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public Vehicle findById(long id) throws ServiceException, DaoException {
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
		
	}

	public List<Vehicle> findAll() throws ServiceException {
		try	{
			return vehicleDao.findAll();
		} catch (DaoException e){
			throw new ServiceException(e.getMessage());
		}
		
	}
	public void delete(int number) throws DaoException, ServiceException {
		try {
			List<Reservation> reservations = reservationService.findResaByVehicleId(number);
			for (Reservation r : reservations) reservationService.delete(r.id());
			vehicleDao.delete(findById(number));
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public int count() throws ServiceException, DaoException {
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public boolean valide (Vehicle vehicle) {
		if (vehicle.modele() == "" || vehicle.modele() == null)
			return false;
		if (vehicle.constructeur() == "" || vehicle.constructeur() == null)
			return false;
		return vehicle.nb_places() >= 2 && vehicle.nb_places() <= 9;
    }
}
