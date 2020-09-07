package my.edu.tarc.self_checkout_admin.Model;

public class PromoCode {
    String Code;
    String Desc;
    String rate;
    int totalRedemption,LimitPerUser;
    long expiryDate;
    int maxDiscount,minPurchase;
    String header;
    public PromoCode() {
    }

    public PromoCode(String code, String desc,String header, String rate, int totalRedemption, int limitPerUser, long expiryDate, int maxDiscount, int minPurchase) {
        Code = code;
        Desc = desc;
        header = header;
        this.rate = rate;
        this.totalRedemption = totalRedemption;
        LimitPerUser = limitPerUser;
        this.expiryDate = expiryDate;
        this.maxDiscount = maxDiscount;
        this.minPurchase = minPurchase;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getTotalRedemption() {
        return totalRedemption;
    }

    public void setTotalRedemption(int totalRedemption) {
        this.totalRedemption = totalRedemption;
    }

    public int getLimitPerUser() {
        return LimitPerUser;
    }

    public void setLimitPerUser(int limitPerUser) {
        LimitPerUser = limitPerUser;
    }

    public long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(int maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public int getMinPurchase() {
        return minPurchase;
    }

    public void setMinPurchase(int minPurchase) {
        this.minPurchase = minPurchase;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
