package com.github.papayankey;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcH2StatementsTest extends JdbcH2BaseTest {
    @Test
    void testGetData() throws SQLException {
        try (var statement = connection.createStatement()) {
            var sql = """
                    SELECT id, first_name, last_name from students
                    WHERE id = 1;
                    """;

            var resultSet = statement.executeQuery(sql);
            resultSet.next();

            var firstName = resultSet.getString("first_name");
            var lastName = resultSet.getString("last_name");

            assertThat(firstName).isEqualTo("mia");
            assertThat(lastName).isEqualTo("yankey");
        }
    }

    @Test
    void testUpdateData() throws SQLException {
        try (var statement = connection.createStatement()) {
            var sql = """
                    UPDATE students
                    SET first_name = 'mia akuba'
                    WHERE id = 1;
                    """;

            statement.executeUpdate(sql);

            sql = """
                    SELECT * from students
                    WHERE id = 1;
                    """;

            var resultSet = statement.executeQuery(sql);
            resultSet.next();

            assertThat(resultSet.getString("first_name")).isEqualTo("mia akuba");
            assertThat(resultSet.getString("last_name")).isEqualTo("yankey");
        }
    }
}
