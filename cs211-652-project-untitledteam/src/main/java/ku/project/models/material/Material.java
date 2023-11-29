package ku.project.models.material;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Material {
    private String name;
    private String category;
    private int amount;
    private String property;



    //    public Material(String name, String category, int amount, String addDate, String addTime, int addAmount, String nameUser, String recordDate, String recordTime, int pickUpAmount) {
//        this.name = name;
//        this.category = category;
//        this.amount = 0;
//        this.addDate = addDate;
//        this.addTime = addTime;
//        this.addAmount = 0;
//        this.nameUser = nameUser;
//        this.recordDate = recordDate;
//        this.recordTime = recordTime;
//        this.pickUpAmount = 0;
//    }
    public Material(String name, String category,int amount, String property) {
        this.name = name;
        this.category = category;
        this.amount = amount;
        this.property = property;
    }





    public String getName() {
        return name;
    }

    public  String getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public String getProperty() {
        return property;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setProperty(String property) {
        this.property = property;
    }



    public boolean isCategory(String name) {
        return this.category.equals(category);
    }

    public boolean isName(String name) {
        return this.name.equals(name);
    }

    public String toCsv() {
        return name + "," + category + "," + amount + "," + property;
    }
}
