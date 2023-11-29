package ku.project.models.material;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class History {
    private String nameAccount;
    private String nameMaterial;
    private String category;
    private int amount;
    private String Date;
    private String Time;
    private String status;

    public History(String nameAccount, String nameMaterial, String category, int amount, String Date, String Time, String status) {
        this.nameAccount = nameAccount;
        this.nameMaterial = nameMaterial;
        this.category = category;
        this.amount = amount;
        this.Date = Date;
        this.Time = Time;
        this.status = status;
    }
    public History(String nameAccount, String nameMaterial, String category, int Amount, String status){
        this(nameAccount,nameMaterial,category,Amount,"","",status);
        initialRequisitionMaterialTime();
    }
//    public Requisition(String User, String nameMaterial, String category, int requisitionAmount,String status) {
//        this.User = User;
//        this.nameMaterial = nameMaterial;
//        this.category = category;
//        this.requisitionAmount = requisitionAmount;
//        this.status = status;
//        initialRequisitionMaterialTime();
//    }

//    public Requisition(String user, String nameMaterial,String category, String requisitionDate, String requisitionTime) {
//        this.User = user;
//        this.nameMaterial = nameMaterial;
//        this.category = category;
//        requisitionAmount = 0;
//        this.requisitionDate = requisitionDate;
//        this.requisitionTime = requisitionTime;
//    }




    public void  initialRequisitionMaterialTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        String loginDateTime = localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        String[] time = loginDateTime.split(" ");
        Date = time[0];
        Time = time[1];
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public String getNameMaterial() {
        return nameMaterial;
    }

    public String getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public String getStatus() {
        return status;
    }

    public  void totalRequisitionAmount(int requisitionAmount){
        if (requisitionAmount > 0) {
            this.amount = requisitionAmount;
        }
    }
    public boolean isName(String User) {
        return this.nameAccount.equals(User);
    }


    @Override
    public String toString() {
        return "{" +
                "ชื่อ: '" + nameAccount + '\'' +
                "วัสดุ: '" + nameMaterial + '\'' +
                "หมวดหมู่: " + category + '\'' +
                "จำนวนที่เบิก: '" + amount + '\'' +
                "วันที่เบิก: '" + Date + '\'' +
                "เวลาที่เบิก: '" + Time + '\'' +
                '}';
    }
    public String toCSV(){
        return nameAccount+","+nameMaterial+","+category+","+amount+","+Date+","+Time+","+status;
    }
}
