package com.desing.test.project.titan.dao;

import com.desing.test.project.titan.model.ExpressionResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpressionDAO {
    private Connection connection;

    public ExpressionDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(ExpressionResult expression, Long calculationId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into expressions(expression, result, calculation_id) values (?, ?, ?)")) {
            preparedStatement.setString(1, expression.getExpression());
            preparedStatement.setString(2, expression.getResult());
            preparedStatement.setLong(3, calculationId);
            preparedStatement.executeUpdate();
        }
    }

    public List<ExpressionResult> findByCalculationId(Long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select * from expressions as ex where ex.calculation_id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ExpressionResult> expressions = new ArrayList<>();
            while (resultSet.next()) {
                ExpressionResult expression = new ExpressionResult();
                expression.setId(resultSet.getLong("id"));
                expression.setExpression(resultSet.getString("expression"));
                expression.setResult(resultSet.getString("result"));
                expressions.add(expression);
            }
            return expressions;
        }
    }

    public void delete(Long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from expressions as c where c.calculation_id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
