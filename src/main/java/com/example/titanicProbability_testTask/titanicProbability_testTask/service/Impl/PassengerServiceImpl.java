package com.example.titanicProbability_testTask.titanicProbability_testTask.service.Impl;

import com.example.titanicProbability_testTask.titanicProbability_testTask.model.Passenger;
import com.example.titanicProbability_testTask.titanicProbability_testTask.repository.PassengerRepository;
import com.example.titanicProbability_testTask.titanicProbability_testTask.service.PassengerService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
//    private final CacheManager cacheManager;

    @Override
    public Page<Passenger> getPassengers(int page, int size, String sortField, String sortDirection) {
        // Проверяем, какое направление сортировки было передано
        Sort.Direction direction = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;

        // Создаем объект Pageable, который будет использован для запроса
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

        // Выполняем запрос и возвращаем результат
        return passengerRepository.findAll(pageable);
    }

    @Override
    public List<Passenger> searchPassengers(String name) {
        return passengerRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Passenger> getFilteredPassengers(boolean survived, boolean adult, boolean male, boolean noRelatives) {
        List<Passenger> passengers = passengerRepository.findAll();
        return passengers.stream()
                .filter(p -> survived ? p.isSurvived() : true)
                .filter(p -> adult ? p.getAge() >= 16 : true)
                .filter(p -> male ? p.isGender() : true)
                .filter(p -> noRelatives ? !p.isHasRelatives() : true)
                .collect(Collectors.toList());
    }

    @Override
    public double getTotalFare() {
        return passengerRepository.findAll().stream()
                .mapToDouble(Passenger::getFare)
                .sum();
    }

    @Override
    public long getCountSurvived() {
        return passengerRepository.findAll().stream()
                .filter(Passenger::isSurvived)
                .count();
    }

    @Override
    public long getCountWithRelatives() {
        return passengerRepository.findAll().stream()
                .filter(Passenger::isHasRelatives)
                .count();
    }

    @Override
    public void savePassengers(Passenger[] passengers) {
        List<Passenger> passengerList = Arrays.asList(passengers);
        passengerRepository.saveAll(passengerList);
    }
}

