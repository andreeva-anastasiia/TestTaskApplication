package com.example.testapp.model;

public class Currency {

    int id;
    String CharCode;
    Double Value;
    String Name;

    public Currency(int id, String charCode, Double value, String name) {
        this.id = id;
        CharCode = charCode;
        Value = value;
        Name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharCode() {
        return CharCode;
    }

    public void setCharCode(String charCode) {
        CharCode = charCode;
    }

    public Double getValue() {
        return Value;
    }

    public void setValue(Double value) {
        Value = value;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
