package com.desing.test.project.titan.dao;

import com.desing.test.project.titan.model.Calculation;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CalculationDAO {
    private Connection connection;

    public CalculationDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Calculation> findAll() throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from calculations")) {
            List<Calculation> calculations = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Calculation calculation = new Calculation();
                calculation.setId(resultSet.getLong("id"));
                calculation.setDate(resultSet.getTimestamp("date").toLocalDateTime());
                calculations.add(calculation);
            }
            return calculations;
        }
    }

    public Long save() throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into calculations (date) values (?)")) {
            Long calculationId = null;
            preparedStatement.setTimestamp(1, Timestamp.from(Instant.now()));
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatementSelect = connection.prepareStatement("select id from calculations order by id desc limit 1");
            ResultSet resultSet = preparedStatementSelect.executeQuery();
            if (resultSet.next()) {
                calculationId = resultSet.getLong(1);
            }
            return calculationId;
        }
    }

    public void delete(Long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from calculations as c where c.id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
