package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

import java.util.Scanner;

public class supprimerVehicle {
    public static void main(String[] args) throws DaoException, ServiceException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer l'ID du véhicule à supprimer : ");
        int number = scanner.nextInt();
        VehicleService.getInstance().delete(number);
    }
}
