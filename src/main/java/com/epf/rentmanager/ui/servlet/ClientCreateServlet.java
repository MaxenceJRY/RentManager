package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("last_name");
        String prenom = request.getParameter("first_name");
        String email = request.getParameter("email");
        LocalDate date = LocalDate.parse(request.getParameter("birth_date"));
        Client client = new Client(0, nom, prenom, email, date);
        boolean possible = clientService.eighteen(client);
        if (possible) {
            try {
                possible = clientService.emailExists(client);
            } catch (ServiceException e) {
                throw new ServletException(e);
            }
            if (!possible) {
                request.setAttribute("Error", "L'email est déjà utilisé.");
                doGet(request, response);
                return;
            }
            possible = clientService.lastFirstName_lenght(client);
            if (!possible) {
                request.setAttribute("Error", "Le nom et le prénom doivent contenir au moins 3 caractères.");
                doGet(request, response);
                return;
            }
            try {
                clientService.create(client);
            } catch (ServiceException e) {
                throw new ServletException(e);
            }
            response.sendRedirect(request.getContextPath() + "/users/list");
        } else {
            request.setAttribute("Error", "Le client doit être majeur pour être enregistré.");
            doGet(request, response);
        }

    }

}
