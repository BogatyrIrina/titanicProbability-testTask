package com.example.titanicProbability_testTask.titanicProbability_testTask.service;

import com.example.titanicProbability_testTask.titanicProbability_testTask.model.Passenger;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PassengerService {
    Page<Passenger> getPassengers(int page, int size, String sortField, String sortDirection);
    List<Passenger> searchPassengers(String name);
    List<Passenger> getFilteredPassengers(boolean survived, boolean adult, boolean male, boolean noRelatives);
    double getTotalFare();
    long getCountSurvived();
    long getCountWithRelatives();
    void savePassengers(Passenger[] passengers);
}
