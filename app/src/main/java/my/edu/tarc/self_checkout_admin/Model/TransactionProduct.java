package my.edu.tarc.self_checkout_admin.Model;

public class TransactionProduct {
    String pname;
    String price;
    String weight;
    double discount;
    String quantity;
    String imageRef;

    public TransactionProduct() {

    }

    public TransactionProduct(String pname, String price, String weight, double discount, String quantity, String imageRef) {
        this.pname = pname;
        this.price = price;
        this.weight = weight;
        this.discount = discount;
        this.quantity = quantity;
        this.imageRef = imageRef;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }
}