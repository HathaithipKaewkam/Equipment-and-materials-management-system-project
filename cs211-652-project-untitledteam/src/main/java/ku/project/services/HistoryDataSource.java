package ku.project.services;

import ku.project.models.material.History;
import ku.project.models.material.HistoryList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HistoryDataSource implements DataSource<HistoryList>{
    private  String directory;

    private  String filename;


    public HistoryDataSource(String directory, String fileName) {
        this.directory = directory;
        this.filename = fileName;
        FileService.initialFileIfNotExist(directory, filename);

    }

//    public RequisitionDataSource() {
//        this("database", "HistoryMaterial.csv");
//    }


    @Override
    public HistoryList readData() {
        HistoryList historyList = new HistoryList();
        String path = directory + File.separator + filename;
        File file = new File(path);
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                String nameUser = data[0].trim();
                String nameMaterial = data[1].trim();
                String category = data[2].trim();
                int amount = Integer.parseInt(data[3].trim());
                String Date = data[4].trim();
                String Time = data[5].trim();
                String status = data[6].trim();
                History requisition = new History(nameUser, nameMaterial, category,amount,Date,Time,status);
//                Requisition requisition = new Requisition("", "", "", 0,"","","");

                historyList.addRequisition(requisition);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return historyList;
    }

    @Override
    public void writeData(HistoryList historyList) {

        String path = directory + File.separator + filename;
        File file = new File(path);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file, StandardCharsets.UTF_8);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(historyList.toCSV());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}