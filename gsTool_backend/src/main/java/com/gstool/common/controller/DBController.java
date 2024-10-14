package com.gstool.common.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class DBController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/testConnection")
    public String testDatabaseConnection() {
        List<String> result = jdbcTemplate.queryForList("SELECT 'Connected to DM database successfully!' FROM dual", String.class);
        return result.isEmpty() ? "Connection failed." : result.get(0);
    }

}
