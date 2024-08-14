package org.example.sqlserverexample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SqlServerExampleApplication implements CommandLineRunner {

    private final DataRepository dataRepository;

    public SqlServerExampleApplication(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(SqlServerExampleApplication.class, args);
    }


    @Override
    public void run(String... args) {
        String filename = "test.txt";
        dataRepository.saveDataFile(filename);
        Long dataId = dataRepository.getDataFileId(filename);
        Long maxId = dataRepository.getMaxId();
        List<DataDTO> dataDTOList = new ArrayList<>();

        for (int i = 0; i < 50000; i++) {
            dataDTOList.add(new DataDTO(maxId + 1, i + 1, i + 2, i + 3, i + 4, i + 5));
        }

        dataRepository.saveAllWacdDataInBatch(dataDTOList, dataId);
    }
}
