package org.example.sqlserverexample;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
public class SqlServerExampleApplication implements CommandLineRunner {

    private final DataRepository dataRepository;

    private Logger logger = Logger.getLogger(SqlServerExampleApplication.class.getName());

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
        maxId = maxId != null ? maxId : 0;
        List<DataDTO> dataDTOList = new ArrayList<>();

        for (int i = 0; i < 200000; i++) {
            dataDTOList.add(new DataDTO(maxId + 1, i + 1, i + 2, i + 3, i + 4, i + 5));
            maxId++;
        }

        long start = System.currentTimeMillis();
        dataRepository.saveAllWacdDataInBatch(dataDTOList, dataId);
        logger.info("Insert bulk done in " + (System.currentTimeMillis() - start) + " ms");

    }
}
