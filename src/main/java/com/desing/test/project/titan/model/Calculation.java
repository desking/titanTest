package com.desing.test.project.titan.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Calculation {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;
    private List<ExpressionResult> expressions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<ExpressionResult> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<ExpressionResult> expressions) {
        this.expressions = expressions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calculation that = (Calculation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(expressions, that.expressions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, date, expressions);
    }

    @Override
    public String toString() {
        return "Calculation{" +
                "id=" + id +
                ", date=" + date +
                ", expressions=" + expressions +
                '}';
    }
}
