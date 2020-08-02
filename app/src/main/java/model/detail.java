package model;

import java.io.Serializable;
import java.util.List;

public class detail implements Serializable {
    private String validateResult;
    private String imgUrl;
    private String number;
    private String category;
    private String location;
    private String productPhone;
    private String weight;
    private String length;
    private String width;
    private String img;

    private String squre;
    private String density;
    private String totalYield;
    private String muYield;
    private String ph;
    private String oxygen;
    private String nitrogen;
    private String salt;
    private String hydrogen;

    private String text_checkLocation;
    private String text_checkName;
    private String text_checkPhone;
    private String text_protin;
    private String text_fat;
    private String text_grey;
    private String text_bacteria;
    private String text_checkTime;


    private List<Sale> sales;

    public String getValidateResult() {
        return validateResult;
    }

    public void setValidateResult(String validateResult) {
        this.validateResult = validateResult;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProductPhone() {
        return productPhone;
    }

    public void setProductPhone(String productPhone) {
        this.productPhone = productPhone;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSqure() {
        return squre;
    }

    public void setSqure(String squre) {
        this.squre = squre;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getTotalYield() {
        return totalYield;
    }

    public void setTotalYield(String totalYield) {
        this.totalYield = totalYield;
    }

    public String getMuYield() {
        return muYield;
    }

    public void setMuYield(String muYield) {
        this.muYield = muYield;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getOxygen() {
        return oxygen;
    }

    public void setOxygen(String oxygen) {
        this.oxygen = oxygen;
    }

    public String getNitrogen() {
        return nitrogen;
    }

    public void setNitrogen(String nitrogen) {
        this.nitrogen = nitrogen;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHydrogen() {
        return hydrogen;
    }

    public void setHydrogen(String hydrogen) {
        this.hydrogen = hydrogen;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }

    public String getText_checkLocation() {
        return text_checkLocation;
    }

    public void setText_checkLocation(String text_checkLocation) {
        this.text_checkLocation = text_checkLocation;
    }

    public String getText_checkName() {
        return text_checkName;
    }

    public void setText_checkName(String text_checkName) {
        this.text_checkName = text_checkName;
    }

    public String getText_checkPhone() {
        return text_checkPhone;
    }

    public void setText_checkPhone(String text_checkPhone) {
        this.text_checkPhone = text_checkPhone;
    }

    public String getText_protin() {
        return text_protin;
    }

    public void setText_protin(String text_protin) {
        this.text_protin = text_protin;
    }

    public String getText_fat() {
        return text_fat;
    }

    public void setText_fat(String text_fat) {
        this.text_fat = text_fat;
    }

    public String getText_grey() {
        return text_grey;
    }

    public void setText_grey(String text_grey) {
        this.text_grey = text_grey;
    }

    public String getText_bacteria() {
        return text_bacteria;
    }

    public void setText_bacteria(String text_bacteria) {
        this.text_bacteria = text_bacteria;
    }

    public String getText_checkTime() {
        return text_checkTime;
    }

    public void setText_checkTime(String text_checkTime) {
        this.text_checkTime = text_checkTime;
    }

    public static class Sale implements Serializable{
        private String saleLocation;
        private String saleName;
        private String salePhone;
        private String saleTime;

        public String getSaleLocation() {
            return saleLocation;
        }

        public void setSaleLocation(String saleLocation) {
            this.saleLocation = saleLocation;
        }

        public String getSaleName() {
            return saleName;
        }

        public void setSaleName(String saleName) {
            this.saleName = saleName;
        }

        public String getSalePhone() {
            return salePhone;
        }

        public void setSalePhone(String salePhone) {
            this.salePhone = salePhone;
        }

        public String getSaleTime() {
            return saleTime;
        }

        public void setSaleTime(String saleTime) {
            this.saleTime = saleTime;
        }
    }
}
