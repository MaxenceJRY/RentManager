package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;

import java.util.Scanner;

public class listerAllReservation {
    public static void main(String[] args) throws ServiceException, DaoException {
        ReservationService.getInstance().findAll().forEach(System.out::println);
    }
}
