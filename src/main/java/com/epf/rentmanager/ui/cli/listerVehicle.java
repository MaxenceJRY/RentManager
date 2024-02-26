package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.VehicleService;

public class listerVehicle {
    public static void main(String[] args) throws ServiceException, DaoException {
        VehicleService.getInstance().findAll().forEach(System.out::println);
    }
}
