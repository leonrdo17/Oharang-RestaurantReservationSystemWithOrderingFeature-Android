package com.leonardo.tableappreservation.manageTable;

public class Table {
    private String tableNumber;
    private String orderTime;
    private String orderDate;

    public Table(){
        // empty constructor
    }

    public Table(String tableNumber, String orderTime, String orderDate) {
        this.tableNumber = tableNumber;
        this.orderTime = orderTime;
        this.orderDate = orderDate;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getOrderDate() {
        return orderDate;
    }
}
