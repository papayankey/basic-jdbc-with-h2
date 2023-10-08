package com.github.papayankey;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcH2PreparedStatementsTest extends JdbcH2BaseTest {
    @Test
    void testGetData() throws SQLException {
        var sql = """
                SELECT id, first_name, last_name from students
                WHERE id = ?;
                """;

        try (var statement = connection.prepareStatement(sql)) {
            statement.setInt(1, 1);

            var resultSet = statement.executeQuery();
            resultSet.next();

            var firstName = resultSet.getString("first_name");
            var lastName = resultSet.getString("last_name");

            assertThat(firstName).isEqualTo("mia");
            assertThat(lastName).isEqualTo("yankey");
        }
    }
}
