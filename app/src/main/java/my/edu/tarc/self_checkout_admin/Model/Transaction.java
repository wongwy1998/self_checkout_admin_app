package my.edu.tarc.self_checkout_admin.Model;

import java.util.List;

public class Transaction {

    String tscID;
    String tscDate;
    String tscTime;
    Double total;
    String discountValue;
    Double subtotal;
    String customerPhone;
    String paymentMethod;
    int verifyStatus;


    public Transaction() {
    }


    public Transaction(String tscID, String tscDate, String tscTime, Double total, String discountValue, Double subtotal, String customerPhone, String paymentMethod, int verifyStatus) {
        this.tscID = tscID;
        this.tscDate = tscDate;
        this.tscTime = tscTime;
        this.total = total;
        this.discountValue = discountValue;
        this.subtotal = subtotal;
        this.customerPhone = customerPhone;
        this.paymentMethod = paymentMethod;
        this.verifyStatus = verifyStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(int verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getTscID() {
        return tscID;
    }

    public void setTscID(String tscID) {
        this.tscID = tscID;
    }

    public String getTscDate() {
        return tscDate;
    }

    public void setTscDate(String tscDate) {
        this.tscDate = tscDate;
    }

    public String getTscTime() {
        return tscTime;
    }

    public void setTscTime(String tscTime) {
        this.tscTime = tscTime;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(String discountValue) {
        this.discountValue = discountValue;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }


}
