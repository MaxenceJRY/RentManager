package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private ReservationDao reservationDao;

    private ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public long create(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public long delete(long id) throws ServiceException {
        try {
            Reservation reservation = new Reservation(id,0,0,null,null);
            return reservationDao.delete(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Reservation> findResaByClientId(long id) throws ServiceException {
        try {
            return reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Reservation> findResaByVehicleId(long id) throws ServiceException {
        try {
            return reservationDao.findResaByVehicleId(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}
