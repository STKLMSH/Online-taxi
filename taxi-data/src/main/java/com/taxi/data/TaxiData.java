package com.taxi.data;

import java.util.List;

public class TaxiData {

    private String service;
    private String currency;
    private List<Driver> drivers;

    public String getService() { return service; }
    public String getCurrency() { return currency; }
    public List<Driver> getDrivers() { return drivers; }
}
