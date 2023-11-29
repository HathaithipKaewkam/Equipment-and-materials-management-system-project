package ku.project.models.material;


import java.util.ArrayList;

public class HistoryList {
    private ArrayList<History> requisitionList;
    public HistoryList() { requisitionList = new ArrayList<>();}
    public void addRequisition(History requisition){ requisitionList.add(requisition);}
    public ArrayList<History> getAllRequisition(){
        return requisitionList;
    }

    public String toCSV() {
        String result = "";
        for (History requisitions: requisitionList) {
            result += requisitions.toCSV() + "\n";
        }
        return result;
    }
}
