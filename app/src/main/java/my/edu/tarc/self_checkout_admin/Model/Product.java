package my.edu.tarc.self_checkout_admin.Model;

public class Product {

    String Name ;
    double Price ;
    String Desc ;
    String Weight;
    double Discount;
    int Sold;
    int CurrentStock;
    String Zone;
    String Shelf;
    String Image;
    String Barcode;
    String Category;
    String date;
    String time;
    int OpeningStock;

    public Product() {
    }

    public Product(String name, double price, String desc, String weight, double Discount, int sold, int currentStock, String zone, String shelf, String image, String barcode, String category, String date, String time,int openingStock) {
        Name = name;
        Price = price;
        Desc = desc;
        Weight = weight;
        this.Discount = Discount;
        Sold = sold;
        CurrentStock = currentStock;
        Zone = zone;
        Shelf = shelf;
        Image = image;
        Barcode = barcode;
        Category = category;
        this.date = date;
        this.time = time;
        this.OpeningStock = openingStock;
    }

    public int getOpeningStock() {
        return OpeningStock;
    }

    public void setOpeningStock(int openingStock) {
        OpeningStock = openingStock;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public int getSold() {
        return Sold;
    }

    public void setSold(int sold) {
        Sold = sold;
    }

    public int getCurrentStock() {
        return CurrentStock;
    }

    public void setCurrentStock(int currentStock) {
        CurrentStock = currentStock;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getShelf() {
        return Shelf;
    }

    public void setShelf(String shelf) {
        Shelf = shelf;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }


    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }




}
