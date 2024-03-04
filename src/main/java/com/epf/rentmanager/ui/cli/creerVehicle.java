package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class creerVehicle {
    public static void main(String[] args) throws ServiceException, DaoException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        VehicleService vehicleService;
        vehicleService = context.getBean(VehicleService.class);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer le constructeur du véhicule : ");
        String constructeur = scanner.nextLine();
        System.out.println("Veuillez entrer le modèle du véhicule : ");
        String modele = scanner.nextLine();
        System.out.println("Veuillez entrer le nombre de places du véhicule : ");
        int nb_places = scanner.nextInt();
        Vehicle vehicle = new Vehicle(0, constructeur, modele, nb_places);

        vehicleService.create(vehicle);
    }
}
