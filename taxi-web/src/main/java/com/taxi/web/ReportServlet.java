package com.taxi.web;

import com.taxi.core.TaxiService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {

    private TaxiService service;

    @Override
    public void init() {
        service = TaxiService.fromJson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/csv; charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=\"taxi-report.csv\"");
        resp.getWriter().write('﻿');   // чтобы Excel понял, что файл в UTF-8
        resp.getWriter().write(service.toCsv());
    }
}
