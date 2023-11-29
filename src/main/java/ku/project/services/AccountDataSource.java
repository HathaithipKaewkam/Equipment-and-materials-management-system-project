package ku.project.services;


import ku.project.models.account.Account;
import ku.project.models.account.AccountList;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class AccountDataSource implements DataSource<AccountList> {
    private String directory;
    private String fileName;
    private AccountList accountList;

    public AccountDataSource(String directory, String fileName) {
        this.directory = directory;
        this.fileName = fileName;
        accountList = new AccountList();
        checkFileExisted();
    }

    public AccountDataSource() {
        this("database", "account.csv");
    }

    private void checkFileExisted() {
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdirs();
        }

        String filePath = directory + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public AccountList readData() {
        AccountList accountList = new AccountList();
        String filePath = directory + File.separator + fileName;
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;
        try {
            reader = new FileReader(file, StandardCharsets.UTF_8);
            buffer = new BufferedReader(reader);

            String line = "";

            while ((line = buffer.readLine()) != null) {
                System.out.println(line);
                String data[] = line.split(",");
                String role = data[0];
                String name = data[1];
                String username = data[2];
                String password = data[3];
                String imagePath = data[4];
                String loginDate = data[5];
                String loginTime = data[6];
                accountList.addAccount(new Account(role,name,username,password,imagePath,loginDate,loginTime));
            }

        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                buffer.close();
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(accountList.getAllAccount().size());
        return accountList;
    }


    @Override
    public void writeData(AccountList accountList) {
        String filePath = directory + File.separator + "account.csv";
        File file = new File(filePath);
        FileWriter writer = null;
        BufferedWriter buffer = null;


        try {
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            buffer = new BufferedWriter(writer);
            for (Account account : accountList.getAllAccount()) {
                if ("Admin".equals(account.getRole())) {
                    String line = "Admin" + "," + account.getName() + "," + account.getUsername() + ","
                            + account.getPassword() + ","
                            + account.getFilePictureName() + ","
                            + account.getLoginDate() + "," + account.getLoginTime();
                    buffer.write(line);
                    buffer.newLine();
                    buffer.flush();

                } else if ("Staff".equals(account.getRole())) {
                    String line = "Staff" + "," + account.getName() + "," + account.getUsername() + ","
                            + account.getPassword() + ","
                            + account.getFilePictureName() + ","
                            + account.getLoginDate() + "," + account.getLoginTime();
                    buffer.write(line);
                    buffer.newLine();
                    buffer.flush();
                }
                else if ("User".equals(account.getRole())){
                    String line = "User" + "," + account.getName() + "," + account.getUsername() + ","
                            + account.getPassword() + ","
                            + account.getFilePictureName() + ","
                            + account.getLoginDate() + "," + account.getLoginTime();
                    buffer.write(line);
                    buffer.newLine();
                    buffer.flush();
                    System.out.println(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
