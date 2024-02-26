package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;

import java.util.Scanner;

public class listerReservationClient {
    public static void main(String[] args) throws ServiceException, DaoException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer l'ID du client à lister : ");
        int number = scanner.nextInt();
        ReservationService.getInstance().findResaByClientId(number).forEach(System.out::println);
    }
}
