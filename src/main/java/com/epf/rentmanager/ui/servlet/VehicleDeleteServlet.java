package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/delete/*")
public class VehicleDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vehicleIdStr = request.getPathInfo().substring(1);
        int vehicleId = Integer.parseInt(vehicleIdStr);
        System.out.println(vehicleId);
        try {
            vehicleService.delete(vehicleId);
        } catch (DaoException e) {
            throw new ServletException(e);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        response.sendRedirect(request.getContextPath() + "/cars/list");
    }


}
