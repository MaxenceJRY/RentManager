package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import java.util.Scanner;

public class creerVehicle {
    public static void main(String[] args) throws ServiceException, DaoException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer le constructeur du véhicule : ");
        String constructeur = scanner.nextLine();
        System.out.println("Veuillez entrer le modèle du véhicule : ");
        String modele = scanner.nextLine();
        System.out.println("Veuillez entrer le nombre de places du véhicule : ");
        int nb_places = scanner.nextInt();
        Vehicle vehicle = new Vehicle(0, constructeur, modele, nb_places);

        VehicleService.getInstance().create(vehicle);
    }
}
