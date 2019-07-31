package com.desing.test.project.titan.service;

import com.desing.test.project.titan.dao.ExpressionDAO;
import com.desing.test.project.titan.service.calculator.Calculator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.desing.test.project.titan.dao.CalculationDAO;
import com.desing.test.project.titan.exception.ExpressionsException;
import com.desing.test.project.titan.model.Calculation;
import com.desing.test.project.titan.model.ExpressionResult;
import com.desing.test.project.titan.util.ObjectMapperUtil;
import com.desing.test.project.titan.util.PostrgeSQLUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CalculationService extends PostrgeSQLUtil {
    public void save(List<ExpressionResult> expressions) throws SQLException {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            CalculationDAO calculationDAO = new CalculationDAO(connection);
            ExpressionDAO expressionDAO = new ExpressionDAO(connection);
            Long calculationId = calculationDAO.save();
            for (ExpressionResult expression : expressions) {
                expressionDAO.save(expression, calculationId);
            }
            connection.commit();
        }
    }

    public List<Calculation> findAll() throws SQLException {
        try (Connection connection = getConnection()) {
            CalculationDAO calculationDAO = new CalculationDAO(connection);
            return calculationDAO.findAll();
        }
    }

    public List<ExpressionResult> findExpressionByCalculationId(Long id) throws SQLException {
        try (Connection connection = getConnection()) {
            ExpressionDAO expressionDAO = new ExpressionDAO(connection);
            return expressionDAO.findByCalculationId(id);
        }
    }

    public void deleteCalculation(Long id) throws SQLException {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            CalculationDAO calculationDAO = new CalculationDAO(connection);
            calculationDAO.delete(id);
            ExpressionDAO expressionDAO = new ExpressionDAO(connection);
            expressionDAO.delete(id);
            connection.commit();
        }
    }

    public List<ExpressionResult> calculate(String expressions) throws ExpressionsException {
        ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();
        try {
            List<String> expressionsList = objectMapper.readValue(expressions, List.class);
            Calculator calculator = new Calculator();
            return expressionsList.stream()
                    .map(expression -> {
                        ExpressionResult expressionResult = new ExpressionResult();
                        expressionResult.setExpression(expression);
                        String result = String.valueOf(calculator.calculate(expression));
                        expressionResult.setResult(result.equals("Infinity") ? "NULL" : result);
                        return expressionResult;
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
