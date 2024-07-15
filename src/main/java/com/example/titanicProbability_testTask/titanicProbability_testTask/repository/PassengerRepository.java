package com.example.titanicProbability_testTask.titanicProbability_testTask.repository;

import com.example.titanicProbability_testTask.titanicProbability_testTask.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findByNameContainingIgnoreCase(String name);
    List<Passenger> findBySurvivedTrue();
    List<Passenger> findByAgeGreaterThanEqual(int age);
    List<Passenger> findByGenderTrue();
    List<Passenger> findByHasRelativesFalse();
}
