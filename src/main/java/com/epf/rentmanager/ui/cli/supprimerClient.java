package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;

import java.util.Scanner;

public class supprimerClient {
    public static void main(String[] args) throws DaoException, ServiceException {
        ClientService.getInstance().findAll().forEach(System.out::println);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer l'ID du client à supprimer : ");
        int number = scanner.nextInt();
        ClientService.getInstance().delete(number);
    }
}
