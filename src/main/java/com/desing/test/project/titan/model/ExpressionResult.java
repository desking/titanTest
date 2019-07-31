package com.desing.test.project.titan.model;

import java.util.Objects;

public class ExpressionResult {
    private Long id;
    private String expression;
    private String result;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpressionResult that = (ExpressionResult) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(expression, that.expression) &&
                Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, expression, result);
    }

    @Override
    public String toString() {
        return "ExpressionResult{" +
                "id=" + id +
                ", expression='" + expression + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
