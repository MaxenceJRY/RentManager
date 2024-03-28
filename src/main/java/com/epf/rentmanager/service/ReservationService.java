package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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


    public int count() throws ServiceException, DaoException {
        try {
            return reservationDao.count();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean isVehicleAvailable(Reservation reservation) throws ServiceException {
        List<Reservation> reservations = this.findResaByVehicleId(reservation.vehicle_id());
        for (Reservation r : reservations) {
            if (r.debut().isBefore(reservation.debut()) && r.fin().isAfter(reservation.debut())) {
                return false;
            }
            if (r.debut().isBefore(reservation.fin()) && r.fin().isAfter(reservation.fin())) {
                return false;
            }
            if (r.debut().isAfter(reservation.debut()) && r.fin().isBefore(reservation.fin())) {
                return false;
            }
        }
        return true;
    }

    public boolean sevenDaysMax (Reservation reservation) {
        return ChronoUnit.DAYS.between(reservation.debut(), reservation.fin()) <= 7;
    }

    public boolean pause(Reservation reservation) throws ServiceException {
        List<Reservation> reservations = this.findResaByVehicleId(reservation.vehicle_id());
        reservations.add(reservation);
        int jourDeSuite = 0;
        for (int i = 0; i < reservations.size() - 1; i++) {
            Reservation current = reservations.get(i);
            Reservation next = reservations.get(i + 1);
            if (i==0)
                jourDeSuite += (int) ChronoUnit.DAYS.between(current.debut(), current.fin());
            long diffInDays = ChronoUnit.DAYS.between(current.fin(), next.debut());
            if (diffInDays == 1) {
                jourDeSuite += (int) ChronoUnit.DAYS.between(next.debut(), next.fin()) + 1;
                System.out.println(jourDeSuite);
                if (jourDeSuite > 30) {
                    return false;
                }
            } else {
                jourDeSuite = 1;
            }
        }
        return true;
    }


}
