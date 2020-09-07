package my.edu.tarc.self_checkout_admin.Model;

public class ReportProduct {


    int CurrentStock,OpeningStock;
    String Name;
    int Sold;
    double Price;

    public ReportProduct() {
    }

    public ReportProduct(String name,int openingStock, int sold,int currentStock,double price) {
        this.Price = price;
        this.CurrentStock = currentStock;
        this.OpeningStock = openingStock;
        this.Name = name;
        this.Sold = sold;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getCurrentStock() {
        return CurrentStock;
    }

    public void setCurrentStock(int currentStock) {
        CurrentStock = currentStock;
    }

    public int getOpeningStock() {
        return OpeningStock;
    }

    public void setOpeningStock(int openingStock) {
        OpeningStock = openingStock;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSold() {
        return Sold;
    }

    public void setSold(int sold) {
        Sold = sold;
    }
}
