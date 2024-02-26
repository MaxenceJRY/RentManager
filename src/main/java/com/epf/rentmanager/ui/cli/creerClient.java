package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;

import java.util.Scanner;

public class creerClient {
    public static void main(String[] args) throws ServiceException, DaoException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer le nom du client : ");
        String nom = scanner.nextLine();
        System.out.println("Veuillez entrer le prénom du client : ");
        String prenom = scanner.nextLine();
        System.out.println("Veuillez entrer l'email du client : ");
        String email = scanner.nextLine();
        System.out.println("Veuillez entrer la date de naissance du client : (AAAA-MM-JJ)");
        String naissance = scanner.nextLine();
        Client client = new Client(0, nom, prenom, email, java.time.LocalDate.parse(naissance));

        ClientService.getInstance().create(client);

    }
}