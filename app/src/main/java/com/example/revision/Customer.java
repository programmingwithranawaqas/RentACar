package com.example.revision;

public class Customer {
   private String name, cnic, cell, car;

    public Customer() {
    }

    public Customer(String name, String cnic, String cell, String car) {
        this.name = name;
        this.cnic = cnic;
        this.cell = cell;
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", cnic='" + cnic + '\'' +
                ", cell='" + cell + '\'' +
                ", car='" + car + '\'' +
                '}';
    }
}
