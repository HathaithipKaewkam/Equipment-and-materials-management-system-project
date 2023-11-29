package ku.project.services;

import ku.project.models.products.Product;
import ku.project.models.products.ProductList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ProductDataSrc implements DataSource<ProductList> {
    private static String directory;

    private static String filename;

    public ProductDataSrc(){
        this("database","product.csv");
    }

    public ProductDataSrc(String directory, String filename) {
        this.directory = directory;
        this.filename = filename;
        FileService.initialFileIfNotExist(directory, filename);
    }

    @Override
    public ProductList readData() {
        ProductList productList = new ProductList();
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
                String username = data[0];
                String idProduct = data[1];
                String nameProduct = data[2];
                String categoryProduct = data[3];
                String locationProduct = data[4];
                String detailProduct = data[5];
                String datePurchased = data[6];
                String timePurchase =  data[7];
                String statusProduct = data[8];
                String dayAddProduct = data[9];
                String timeAddProduct = data[10];
                String imagePath = data[11];
                String isBorrow = data[12];
                String holder = data[13];
                String dateTimeOwner = data[14];
                productList.addProduct(new Product(username,idProduct,nameProduct,categoryProduct,locationProduct,detailProduct,datePurchased
                        ,timePurchase,statusProduct,dayAddProduct,timeAddProduct,imagePath,isBorrow,holder,dateTimeOwner));
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
        return productList;
    }

    @Override
    public void writeData(ProductList productList) {
        String path = directory + File.separator + filename;
        File file = new File(path);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file, StandardCharsets.UTF_8);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(productList.toCsv());
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
