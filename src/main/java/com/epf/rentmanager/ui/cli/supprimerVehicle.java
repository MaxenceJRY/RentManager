package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class supprimerVehicle {
    public static void main(String[] args) throws DaoException, ServiceException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VehicleService vehicleService;
        vehicleService = context.getBean(VehicleService.class);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer l'ID du véhicule à supprimer : ");
        int number = scanner.nextInt();
        vehicleService.delete(number);
    }
}
