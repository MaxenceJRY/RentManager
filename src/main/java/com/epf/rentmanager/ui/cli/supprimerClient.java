package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class supprimerClient {
    public static void main(String[] args) throws DaoException, ServiceException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ClientService clientService;
        clientService = context.getBean(ClientService.class);

        clientService.findAll().forEach(System.out::println);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer l'ID du client à supprimer : ");
        int number = scanner.nextInt();
        clientService.delete(number);
    }
}
