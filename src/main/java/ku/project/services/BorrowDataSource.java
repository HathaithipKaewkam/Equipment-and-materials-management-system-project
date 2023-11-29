package ku.project.services;


import ku.project.models.user.Borrow;
import ku.project.models.user.BorrowList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class BorrowDataSource implements DataSource<BorrowList> {
    private static String directory;

    private static String filename;


    public BorrowDataSource(String directory, String fileName) {
        this.directory = directory;
        this.filename = fileName;
        FileService.initialFileIfNotExist(directory, filename);

    }

    public BorrowDataSource() {
        this("database", "borrowHistory.csv");
    }


    @Override
    public BorrowList readData() {
        BorrowList borrowList = new BorrowList();
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
                String username = data[0].trim();
                String idProduct = data[1].trim();
                String nameProduct = data[2].trim();
                String category = data[3].trim();
                String status = data[4].trim();
                String borrowDate = data[5].trim();
                String borrowTime = data[6].trim();
                String returnedDate = data[7].trim();
                String returnedTime = data[8].trim();
                String staffApproveBorrow = data[9].trim();
                String staffApproveReturned = data[10].trim();

                borrowList.addBorrow(new Borrow(username, idProduct, nameProduct, category, status, borrowDate, borrowTime, returnedDate,returnedTime,staffApproveBorrow,staffApproveReturned));
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
        return borrowList;
    }

    @Override
    public void writeData(BorrowList borrowList) {

        String path = directory + File.separator + filename;
        File file = new File(path);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file, StandardCharsets.UTF_8);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(borrowList.toCsv());
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
