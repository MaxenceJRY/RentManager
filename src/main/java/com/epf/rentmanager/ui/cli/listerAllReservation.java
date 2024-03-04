package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class listerAllReservation {
    public static void main(String[] args) throws ServiceException, DaoException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ReservationService reservationService;
        reservationService = context.getBean(ReservationService.class);
        reservationService.findAll().forEach(System.out::println);
    }
}
