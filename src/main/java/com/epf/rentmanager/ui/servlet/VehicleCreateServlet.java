package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
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

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private VehicleService vehicleService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        // VehicleService vehicleService;
        String constructeur = request.getParameter("manufacturer");
        String modele = request.getParameter("modele");
        String nb_places_str = request.getParameter("seats");
        int nb_places;
        if (nb_places_str != null && !nb_places_str.isEmpty()) {
            nb_places = Integer.parseInt(nb_places_str);
        } else {
            throw new ServletException("Missing parameter: nb_places " + constructeur + " " + modele);
        }
        Vehicle vehicle = new Vehicle(0, constructeur, modele, nb_places);
        try {
            vehicleService.create(vehicle);
        } catch (ServiceException | DaoException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/cars/list");
    }
}
