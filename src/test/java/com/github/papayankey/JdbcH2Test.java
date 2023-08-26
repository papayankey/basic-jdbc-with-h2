package com.github.papayankey;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcH2Test {
    private Connection connection;

    @BeforeEach
    void openDbConnection() throws SQLException {
        var URL = "jdbc:h2:~/test";
        var username = "user";
        var password = "pass";
        connection = DriverManager.getConnection(URL, username, password);
    }

    @AfterEach
    void closeDbConnection() throws SQLException {
        connection.close();
    }

    @Test
    void testCreateTable() throws SQLException {
        try (var statement = connection.createStatement()) {
            var sql = """
                    CREATE TABLE IF NOT EXISTS students (
                    id INTEGER NOT NULL AUTO_INCREMENT,
                    first_name TEXT,
                    last_name TEXT,
                    PRIMARY KEY(id)
                    );
                    """;
            boolean result = statement.execute(sql);

            assertThat(result).isFalse();
        }
    }
}
