package ku.project.models.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Borrow {
    private String userName;
    private String idProduct;
    private String nameProduct;
    private String category;
    private String status;
    private String borrowDate;
    private String borrowTime;
    private String returnedDate;
    private String returnedTime;
    private String staffApproveBorrow;
    private String staffApproveReturned;


    public Borrow(String userName, String idProduct, String nameProduct,String category, String status) {
        this(userName,idProduct,nameProduct,category,status,"","","Never","Never","Never","Never");
        initialBorrowTime();
    }
//    public Borrow(String userName, String idProduct, String nameProduct,String category, String status, String borrowDate, String borrowTime) {
//        this(userName,idProduct,nameProduct,category,status,borrowDate,borrowTime,"","");
//
//    }

    public Borrow(String userName, String idProduct, String nameProduct,String category, String status, String borrowDate, String borrowTime, String returnedDate,String returnedTime,String staffApproveBorrow,String staffApproveReturned) {
        this.userName = userName;
        this.idProduct = idProduct;
        this.nameProduct = nameProduct;
        this.category = category;
        this.status = status;
        this.borrowDate = borrowDate;
        this.borrowTime = borrowTime;
        this.returnedDate = returnedDate;
        this.returnedTime = returnedTime;
        this.staffApproveBorrow = staffApproveBorrow;
        this.staffApproveReturned = staffApproveReturned;

    }
    public void initialBorrowTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        String borrowDateTime = localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        String[] time = borrowDateTime.split(" ");
        borrowDate = time[0];
        borrowTime = time[1];
    }



    public String getUserName() {
        return userName;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public String getCategory() {return category;}

    public String getStatus() {
        return status;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public String getReturnedDate() {
        return returnedDate;
    }

    public String getReturnedTime() {
        return returnedTime;
    }

    public String getStaffApproveBorrow() {
        return staffApproveBorrow;
    }

    public String getStaffApproveReturned() {
        return staffApproveReturned;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }

    public void setReturnedTime(String returnedTime) {
        this.returnedTime = returnedTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStaffApproveBorrow(String staffApproveBorrow) {
        this.staffApproveBorrow = staffApproveBorrow;
    }

    public void setStaffApproveReturned(String staffApproveReturned) {
        this.staffApproveReturned = staffApproveReturned;
    }

    public String toCsv(){
        return userName+","+idProduct+","+nameProduct+","+category+","+status+","+borrowDate+","+borrowTime+","+returnedDate+","+returnedTime+","+staffApproveBorrow+","+staffApproveReturned;
    }
}
