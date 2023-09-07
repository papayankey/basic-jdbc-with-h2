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

    @Test
    void testIfTableExists() throws SQLException {
        try (var statement = connection.createStatement()) {
            var sql = "SELECT * from students;";
            try (var resultSet = statement.executeQuery(sql)) {
                var index = resultSet.findColumn("id");
                var firstName = resultSet.findColumn("first_name");
                var lastName = resultSet.findColumn("last_name");

                assertThat(index).isEqualTo(1);
                assertThat(firstName).isEqualTo(2);
                assertThat(lastName).isEqualTo(3);
            }
        }
    }

    @Test
    void testDropTableIfExists() throws SQLException {
        try (var statement = connection.createStatement()) {
            var sql = "DROP TABLE IF EXISTS students;";
            boolean result = statement.execute(sql);

            assertThat(result).isFalse();
        }
    }

    @Test
    void testInsertData() throws SQLException {
        try (var statement = connection.createStatement()) {
            var sql = """
                    INSERT INTO students (first_name, last_name)
                    VALUES
                        ('mia', 'yankey'),
                        ('rebecca', 'attuah'),
                        ('dominic', 'yankey');
                    """;
            var result = statement.execute(sql);

            assertThat(result).isFalse();
        }
    }

    @Test
    void testGetData() throws SQLException {
        try (var statement = connection.createStatement()) {
            var sql = """
                    SELECT id, first_name, last_name from students
                    WHERE first_name='mia';
                    """;

            var resultSet = statement.executeQuery(sql);
            resultSet.next();

            var firstName = resultSet.getString("first_name");
            var lastName = resultSet.getString("last_name");

            assertThat(firstName).isEqualTo("mia");
            assertThat(lastName).isEqualTo("yankey");
        }
    }
}
