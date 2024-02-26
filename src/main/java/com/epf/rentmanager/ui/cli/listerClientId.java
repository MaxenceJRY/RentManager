package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;

import java.util.Scanner;

public class listerClientId {
    public static void main(String[] args) throws DaoException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Veuillez entrer l'ID du client à lister : ");
        int number = scanner.nextInt();
        System.out.println("Le client numéro " + number + " est " + ClientDao.getInstance().findById(number).prenom() + " " +
                ClientDao.getInstance().findById(number).nom());

    }
}
