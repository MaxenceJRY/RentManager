package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.configuration.AppConfiguration;
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
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cars/list")
public class VehicleListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private VehicleService vehicleService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //VehicleService vehicleService;
        List<Vehicle> vehicles = new ArrayList<>();

        try {
            vehicles = vehicleService.findAll();
        } catch (ServiceException e) {
            throw new ServletException(e.getMessage());
        }
        request.setAttribute("vehicles", vehicles);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);
    }
}
