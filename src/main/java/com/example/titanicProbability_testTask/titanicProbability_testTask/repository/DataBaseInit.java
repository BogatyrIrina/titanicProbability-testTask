package com.example.titanicProbability_testTask.titanicProbability_testTask.repository;

import com.example.titanicProbability_testTask.titanicProbability_testTask.model.Passenger;
import com.example.titanicProbability_testTask.titanicProbability_testTask.model.PassengerClass;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static com.example.titanicProbability_testTask.titanicProbability_testTask.model.PassengerClass.FIRST;
import static com.example.titanicProbability_testTask.titanicProbability_testTask.model.PassengerClass.SECOND;
import static com.example.titanicProbability_testTask.titanicProbability_testTask.model.PassengerClass.THIRD;


@Component
public class DataBaseInit {
    private final String pass = "/Users/admin/IdeaProjects/titanicProbability/titanic.csv";

    private static final Logger log = LoggerFactory.getLogger(DataBaseInit.class);
    @Autowired
    private PassengerRepository passengerRepository;

    @PostConstruct
    public void init() throws IOException {
        File file = new File(pass);
        if (!file.exists()) {
            InputStream inputStream = new URL("https://web.stanford.edu/class/archive/cs/cs109/cs109.1166/stuff/titanic.csv").openStream();
            Files.copy(inputStream, Paths.get(pass), StandardCopyOption.REPLACE_EXISTING);
            transformInModel();
        }
    }

    @Transactional
    public void transformInModel() {
        CSVReader csvReader = null;
        try {
            csvReader = new CSVReaderBuilder(new FileReader(pass)).build();
            String[] nextLine;
            csvReader.skip(1); // Пропускаем заголовок
            List<Passenger> listPass = new ArrayList<>();
            while ((nextLine = csvReader.readNext()) != null) {
                String survived = nextLine[0];
                String pClass = nextLine[1];
                String name = nextLine[2];
                String sex = nextLine[3];
                String age = nextLine[4];
                String siblingsSpousesAboard = nextLine[5];
                String parentsChildrenAboard = nextLine[6];
                String fare = nextLine[7];

                // Определяем значения полей Passenger
                boolean hasRelatives = !siblingsSpousesAboard.isEmpty() || !parentsChildrenAboard.isEmpty();
                boolean isSurived = "1".equals(survived);
                PassengerClass passengerClass;
                if (pClass.equals("1")) {
                    passengerClass = FIRST;
                } else if (pClass.equals("2")) {
                    passengerClass = SECOND;
                } else {
                    passengerClass = THIRD;
                }
                boolean isGender = "male".equalsIgnoreCase(sex);
                double fareValue = Double.parseDouble(fare);
                int ageValue = Math.round(Float.parseFloat(age));

                // Создаем объект Passenger и добавляем его в список
                if (!name.isEmpty() && ageValue > 0 && fareValue >= 0) {
                    Passenger passenger = new Passenger(
                            null,
                            name,
                            ageValue,
                            isGender,
                            fareValue,
                            isSurived,
                            hasRelatives,
                            passengerClass
                    );
                    listPass.add(passenger);
                }
            }

            csvReader.close();
            passengerRepository.saveAll(listPass); // Сохраняем список пассажиров в базу данных
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

