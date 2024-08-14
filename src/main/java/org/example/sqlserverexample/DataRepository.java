package org.example.sqlserverexample;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DataRepository {

    private final JdbcTemplate jdbcTemplate;

    public DataRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void saveAllWacdDataInBatch(List<DataDTO> dataDTOList, Long dataFileId) {
        jdbcTemplate.batchUpdate("INSERT INTO TEST_DATA(id, data_file_id, weight_a, weight_b, weight_c," +
                " weight_d, weight_e) VALUES(?,?,?,?,?,?,?)", dataDTOList, 500, (ps, dataDTO) -> {
            ps.setLong(1, dataDTO.id());
            ps.setLong(2, dataFileId);
            ps.setInt(3, dataDTO.weightA());
            ps.setInt(4, dataDTO.weightB());
            ps.setInt(5, dataDTO.weightC());
            ps.setInt(6, dataDTO.weightD());
            ps.setInt(7, dataDTO.weightE());
        });
    }

    @Transactional
    public void saveDataFile(String filename) {
        jdbcTemplate.update("INSERT INTO DATA_FILE(name) VALUES(?)", filename);
    }

    public Long getDataFileId(String filename) {
        List<Long> ids = jdbcTemplate.queryForList("SELECT id FROM DATA_FILE WHERE name = ?", Long.class, filename);
        return ids.getFirst();
    }

    public Long getMaxId() {
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM TEST_DATA", Long.class);
    }

}
