package com.taxi.web;

import com.taxi.core.TaxiService;
import com.taxi.data.Driver;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/drivers")
public class DriversServlet extends HttpServlet {

    private TaxiService service;

    @Override
    public void init() {
        service = TaxiService.fromJson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String tariff = req.getParameter("tariff");
        String query = req.getParameter("q");
        boolean onlyAvailable = req.getParameter("all") == null;

        List<Driver> drivers = service.search(tariff, query, onlyAvailable);

        req.setAttribute("service", service);
        req.setAttribute("drivers", drivers);
        req.setAttribute("stats", service.statistics(drivers));
        req.setAttribute("report", service.tariffReport());
        req.setAttribute("ratingGroups", service.ratingGroups());
        req.getRequestDispatcher("/WEB-INF/jsp/drivers.jsp").forward(req, resp);
    }
}
