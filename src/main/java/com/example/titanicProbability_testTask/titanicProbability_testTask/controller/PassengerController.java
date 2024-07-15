package com.example.titanicProbability_testTask.titanicProbability_testTask.controller;

import com.example.titanicProbability_testTask.titanicProbability_testTask.model.Passenger;
import com.example.titanicProbability_testTask.titanicProbability_testTask.model.PassengerStats;
import com.example.titanicProbability_testTask.titanicProbability_testTask.service.PassengerService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public ResponseEntity<Page<Passenger>> getPassengers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        Page<Passenger> passengers = passengerService.getPassengers(page, size, sortField, sortDirection);
        return ResponseEntity.ok(passengers);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Passenger>> searchPassengers(@RequestParam String name) {
        List<Passenger> passengers = passengerService.searchPassengers(name);
        return ResponseEntity.ok(passengers);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Passenger>> getFilteredPassengers(
            @RequestParam(required = false, defaultValue = "false") boolean survived,
            @RequestParam(required = false, defaultValue = "false") boolean adult,
            @RequestParam(required = false, defaultValue = "false") boolean male,
            @RequestParam(required = false, defaultValue = "false") boolean noRelatives) {
        List<Passenger> passengers = passengerService.getFilteredPassengers(survived, adult, male, noRelatives);
        return ResponseEntity.ok(passengers);
    }

    @GetMapping("/stats")
    public ResponseEntity<PassengerStats> getPassengerStats() {
        double totalFare = passengerService.getTotalFare();
        long countSurvived = passengerService.getCountSurvived();
        long countWithRelatives = passengerService.getCountWithRelatives();
        PassengerStats stats = new PassengerStats(totalFare, countSurvived, countWithRelatives);
        return ResponseEntity.ok(stats);
    }
}

