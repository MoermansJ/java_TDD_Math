package be.intecbrussel.mathtest.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Operation {
    //properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String number1;
    private String number2;
    private String result;

    @Enumerated
    private OperationType operationType;


    //getters & setters
    public String getNumber1() {
        return number1;
    }

    public String getNumber2() {
        return number2;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public String getResult() {
        return result;
    }


    //custom methods
    public Operation setData(String number1, String number2, String result, OperationType operationType) {
        this.number1 = number1;
        this.number2 = number2;
        this.result = result;
        this.operationType = operationType;
        return this;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", number1=" + number1 +
                ", number2=" + number2 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(number1, operation.number1) && Objects.equals(number2, operation.number2) && Objects.equals(result, operation.result) && operationType == operation.operationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number1, number2, result, operationType);
    }
}
