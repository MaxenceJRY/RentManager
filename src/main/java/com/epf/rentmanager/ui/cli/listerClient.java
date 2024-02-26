package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;

public class listerClient {
    public static void main(String[] args) throws DaoException, ServiceException {
        ClientService.getInstance().findAll().forEach(System.out::println);
    }
}
