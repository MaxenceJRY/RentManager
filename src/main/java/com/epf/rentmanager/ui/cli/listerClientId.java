package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class listerClientId {

    public static void main(String[] args) throws DaoException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ClientService clientService;
        clientService = context.getBean(ClientService.class);


        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer l'ID du client à lister : ");
        int number = scanner.nextInt();
        try {
            System.out.println("Le client numéro " + number + " est " + clientService.findById(number).prenom() + " " +
                    clientService.findById(number).nom());
        } catch (ServiceException e) {
            throw new DaoException("Erreur lors de la récupération du client");
        }

    }
}
