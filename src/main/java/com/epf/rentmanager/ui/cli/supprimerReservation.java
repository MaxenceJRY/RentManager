package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;

import java.util.Scanner;

public class supprimerReservation {
    public static void main(String[] args) throws ServiceException, DaoException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer l'ID de la réservation à supprimer : ");
        int number = scanner.nextInt();
        ReservationService.getInstance().delete(number);
    }
}
