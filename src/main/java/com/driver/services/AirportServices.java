package com.driver.services;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import com.driver.repositories.AirportRepositories;

import java.util.Date;

public class AirportServices {

    AirportRepositories airportRepositories = new AirportRepositories();
    public void addAirport(Airport airport) {
        airportRepositories.addAirport(airport);
    }

    public String getLargestAirportName() {
        return airportRepositories.getLargestAirportName();
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        return  airportRepositories.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);
    }

    public String addFlight(Flight flight) {
        return  airportRepositories.addFlight(flight);
    }

    public void addPassenger(Passenger passenger) {
        airportRepositories.addPassenger(passenger);
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        return airportRepositories.bookATicket(flightId,passengerId);
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        return airportRepositories.getNumberOfPeopleOn(date,airportName);
    }

    public int calculateFlightFare(Integer flightId) {
        return airportRepositories.calculateFlightFare(flightId);
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        return airportRepositories.cancelATicket(flightId,passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        return airportRepositories.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        return airportRepositories.getAirportNameFromFlightId(flightId);
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        return airportRepositories.calculateRevenueOfAFlight(flightId);
    }
}
