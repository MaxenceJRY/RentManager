package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ClientService clientService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        List<Client> clients = new ArrayList<>();
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            clients = clientService.findAll();
            vehicles = vehicleService.findAll();
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }
        request.setAttribute("clients", clients);
        request.setAttribute("vehicles", vehicles);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {

        String car = request.getParameter("car");
        String client = request.getParameter("client");
        LocalDate begin = LocalDate.parse(request.getParameter("begin"));
        LocalDate end = LocalDate.parse(request.getParameter("end"));
        Reservation reservation = new Reservation(-1, Long.parseLong(client), Long.parseLong(car), begin, end);
        boolean possible;
        try {
            possible = reservationService.isVehicleAvailable(reservation);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        if (possible){
            possible = reservationService.sevenDaysMax(reservation);
            if (!possible) {
                request.setAttribute("Error", "La réservation ne peut pas dépasser 7 jours.");
                doGet(request, response);
                return;
            }
            try {
                possible = reservationService.pause(reservation);
            } catch (ServiceException e) {
                throw new ServletException(e);
            }
            if (!possible) {
                request.setAttribute("Error", "La voiture a besoin de faire une pause");
                doGet(request, response);
                return;
            }
            try {
                reservationService.create(reservation);
            } catch (ServiceException e) {
                throw new ServletException(e);
            }
            response.sendRedirect(request.getContextPath() + "/rents/list");
        }else {
            request.setAttribute("Error", "Le véhicule n'est pas disponible.");
            doGet(request, response);
        }

    }
}