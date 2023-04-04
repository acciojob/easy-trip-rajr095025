package com.driver.repositories;
import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;

import java.util.*;


public class AirportRepositories {

    private HashMap <String, Airport> airportDB;

    private HashMap <Integer, Flight> flightDB;

    private HashMap <Integer, Passenger> passengerDB;

    private HashMap<Flight, HashSet<Passenger>> flightPassengerDB;

    private HashMap<Passenger,HashSet<Flight>> passengerFlightDB;



    public AirportRepositories(){
        airportDB = new HashMap<>();
        flightDB = new HashMap<>();
        passengerDB = new HashMap<>();
        passengerFlightDB = new HashMap<>();
        flightPassengerDB = new HashMap<>();
    }


    public void addAirport(Airport airport) {
        airportDB.put(airport.getAirportName(), airport);

    }

    public String getLargestAirportName() {
        /*
        String ans = "";
        int terminals = 0;
        for(Airport airport : airportDb.values()){

            if(airport.getNoOfTerminals()>terminals){
                ans = airport.getAirportName();
                terminals = airport.getNoOfTerminals();
            }else if(airport.getNoOfTerminals()==terminals){
                if(airport.getAirportName().compareTo(ans)<0){
                    ans = airport.getAirportName();
                }
            }
        }
        return ans;
        */
        String ans = "";
        int max = -1;
        for(Airport airport : airportDB.values()){
            if(airport.getNoOfTerminals() > max){
                max = airport.getNoOfTerminals();
                ans = airport.getAirportName();
            }
            else if(airport.getNoOfTerminals() == max){
                if(airport.getAirportName().compareTo(ans) < 0){
                    ans = airport.getAirportName();
                }
            }
        }
        return ans;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double ans = Integer.MAX_VALUE;
        for(Flight flight : flightDB.values()){
            if(flight.getFromCity() == fromCity && flight.getToCity() == toCity && flight.getDuration() < ans){
                ans = flight.getDuration();
            }
        }
        if(ans != Integer.MAX_VALUE) return ans;
        return -1;
    }

    public String addFlight(Flight flight) {
        flightDB.put(flight.getFlightId(), flight);
        return "SUCCESS";
    }

    public void addPassenger(Passenger passenger) {
        passengerDB.put(passenger.getPassengerId(),passenger);
        passengerFlightDB.put(passenger,new HashSet<>());
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        Flight flight = flightDB.get(flightId);
        Passenger passenger = passengerDB.get(passengerId);
        HashSet<Passenger> passengerList = flightPassengerDB.get(flight);
        HashSet<Flight> flightList = passengerFlightDB.get(passenger);
        if(Objects.nonNull(flightList) && flightList.contains(flight)){
            return "FAILURE";
        }
        else if(Objects.nonNull(passengerList)  && flight.getMaxCapacity() == passengerList.size()){
            return "FAILURE";
        }
        if(!Objects.nonNull(passengerList)){
            passengerList = new HashSet<>();
        }
        if(!Objects.nonNull(flightList)) {
            flightList = new HashSet<>();
        }
        passengerList.add(passenger);
        flightList.add(flight);
        flightPassengerDB.put(flight,passengerList);
        passengerFlightDB.put(passenger,flightList);
        return "SUCCESS";
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        Airport airport = airportDB.get(airportName);
        if(Objects.isNull(airport)){
            return 0;
        }
        City city = airport.getCity();
        int ans = 0;
        for(Flight flight : flightPassengerDB.keySet()){
            if(date.equals(flight.getFlightDate()) && (city.equals(flight.getFromCity()) || city.equals(flight.getToCity()))){
                ans += flightPassengerDB.get(flight).size();
            }
        }
        return ans;

        /*
        Airport airport = airportDb.get(airportName);
        if(Objects.isNull(airport)){
            return 0;
        }
        City city = airport.getCity();
        int count = 0;
        for(Flight flight:flightDb.values()){
            if(date.equals(flight.getFlightDate()))
                if(flight.getToCity().equals(city)||flight.getFromCity().equals(city)){

                    int flightId = flight.getFlightId();
                    count = count + flightToPassengerDb.get(flightId).size();
                }
        }
        return count;
        */

    }

    public int calculateFlightFare(Integer flightId) {
        Flight flight = flightDB.get(flightId);
        HashSet<Passenger> list = flightPassengerDB.get(flight);
        return 3000 + (list.size() * 50);
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        Flight flight = flightDB.get(flightId);
        Passenger passenger = passengerDB.get(passengerId);
        HashSet passengerList = flightPassengerDB.get(flight);
        HashSet flightList = passengerFlightDB.get(passenger);
        if(!flightDB.containsKey(flightId)){
            return "FAILURE";
        }
        if(!flightList.contains(flight)){
            return "FAILURE";
        }
        passengerList.remove(passenger);
        flightPassengerDB.put(flight,passengerList);
        flightList.remove(flight);
        passengerFlightDB.put(passenger,flightList);
        return "SUCCESS";
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        Passenger passenger = passengerDB.get(passengerId);
        HashSet <Flight> flightList = passengerFlightDB.get(passenger);
        return flightList.size();
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        if(!flightDB.containsKey(flightId)){
            return null;
        }
        Flight flight = flightDB.get(flightId);
        City city = flight.getFromCity();
        for(Airport airport : airportDB.values()){
            if(airport.getCity() == city){
                return airport.getAirportName();
            }
        }
        return null;
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        Flight flight = flightDB.get(flightId);
        HashSet passengerList = flightPassengerDB.get(flight);
        int noOfPeopleBooked = passengerList.size();
        if(noOfPeopleBooked == 1){
            return 3000;
        }
        int variableFare = (noOfPeopleBooked*(noOfPeopleBooked+1))*25;
        int fixedFare = 3000*noOfPeopleBooked;
        int totalFare = variableFare + fixedFare;
        return totalFare;
        /*
        int ans = 0;
        for(int i=1; i<=noOfPassengers; i++){
            ans += (3000 + 50*i);
        }
        return ans;
        */
        /*
        int noOfPeopleBooked = flightToPassengerDb.get(flightId).size();
        int variableFare = (noOfPeopleBooked*(noOfPeopleBooked+1))*25;
        int fixedFare = 3000*noOfPeopleBooked;
        int totalFare = variableFare + fixedFare;

        return totalFare;
        */
    }
}

