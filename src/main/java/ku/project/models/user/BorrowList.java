package ku.project.models.user;


import java.util.ArrayList;

public class BorrowList {
    private ArrayList<Borrow> borrowList;
    public BorrowList() { borrowList = new ArrayList<>();}
    public void addBorrow(Borrow borrow){ borrowList.add(borrow);}
    public ArrayList<Borrow> getAllBorrow(){
        return borrowList;
    }
    public String toCsv() {
        String result = "";
        for (Borrow borrow: borrowList) {
            result += borrow.toCsv() + "\n";
        }
        return result;
    }

}
