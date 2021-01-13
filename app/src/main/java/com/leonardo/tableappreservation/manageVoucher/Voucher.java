package com.leonardo.tableappreservation.manageVoucher;

public class Voucher {
    private String email;
    private String voucher;

    public Voucher(){
        // empty constructor
    }

    public Voucher(String email, String voucher) {
        this.email = email;
        this.voucher = voucher;
    }

    public String getEmail() {
        return email;
    }

    public String getVoucher() {
        return voucher;
    }
}
