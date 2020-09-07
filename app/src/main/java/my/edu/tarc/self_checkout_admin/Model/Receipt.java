package my.edu.tarc.self_checkout_admin.Model;

public class Receipt {
    String pname;
    String price ;
    String weight;
    String quantity ;
    String imageRef;

    public Receipt() {
    }

    public Receipt(String pname, String price, String weight, String quantity, String imageRef) {
        this.pname = pname;
        this.price = price;
        this.weight = weight;
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
