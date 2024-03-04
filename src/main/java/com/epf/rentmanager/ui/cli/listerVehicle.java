package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class listerVehicle {
    public static void main(String[] args) throws ServiceException, DaoException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VehicleService vehicleService;
        vehicleService = context.getBean(VehicleService.class);
        vehicleService.findAll().forEach(System.out::println);
    }
}
