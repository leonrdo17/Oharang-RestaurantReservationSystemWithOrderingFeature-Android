package com.leonardo.tableappreservation.request;

public class Request {
    private String fullName;
    private String email;
    private String phone;
    private String tableNumber;
    private String orderTime;
    private String orderDate;
    private String partySize;
    private String voucher;
    private String menuOrder;
    private String orderStatus;
    private String orderID;

    public Request() {
        // Empty constructor
    }

    public Request(String fullName, String email, String phone, String tableNumber, String orderTime, String orderDate, String partySize, String voucher, String menuOrder, String orderStatus, String orderID) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.tableNumber = tableNumber;
        this.orderTime = orderTime;
        this.orderDate = orderDate;
        this.partySize = partySize;
        this.voucher = voucher;
        this.menuOrder = menuOrder;
        this.orderStatus = orderStatus;
        this.orderID = orderID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
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

    public String getPartySize() {
        return partySize;
    }

    public String getVoucher() {
        return voucher;
    }

    public String getMenuOrder() {
        return menuOrder;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderID() {
        return orderID;
    }
}

