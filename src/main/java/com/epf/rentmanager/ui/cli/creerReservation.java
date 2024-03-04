package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class creerReservation {
    public static void main(String[] args) throws ServiceException, DaoException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ReservationService reservationService;
        reservationService = context.getBean(ReservationService.class);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer l'ID du client : ");
        int clientId = scanner.nextInt();
        System.out.println("Veuillez entrer l'ID du véhicule : ");
        int vehicleId = scanner.nextInt();
        System.out.println("Veuillez entrer la date de début de la réservation (format YYYY-MM-DD) : ");
        String debut = scanner.next();
        System.out.println("Veuillez entrer la date de fin de la réservation (format YYYY-MM-DD) : ");
        String fin = scanner.next();
        Reservation reservation = new Reservation(0, clientId, vehicleId,
                java.time.LocalDate.parse(debut), java.time.LocalDate.parse(fin));
        reservationService.create(reservation);
    }
}
