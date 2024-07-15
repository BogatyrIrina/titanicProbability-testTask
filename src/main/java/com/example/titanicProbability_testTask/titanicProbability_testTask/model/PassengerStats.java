package com.example.titanicProbability_testTask.titanicProbability_testTask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PassengerStats {
    private double totalFare;
    private long countSurvived;
    private long countWithRelatives;
}
