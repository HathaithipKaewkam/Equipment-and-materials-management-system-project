package ku.project.models.products;

import ku.project.services.HandleImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ku.project.models.account.Account.fileSelected;


public class Product implements HandleImage {

    private String username;
    private String id;
    private String productName;

    private String category;
    private String locationProduct;
    private String detail;

    private String statusProduct;
    private String imagePath;

    private String datePurchased;

    private String timePurchased;
    private String productAddDate;
    private String productAddTime;
    private String isBorrow;
    private String holder;
    private String dateTimeOwner;

    public Product(String username,String id, String productName, String category,String locationProduct, String detail,String datePurchased,
                   String timePurchased, String statusProduct, String imagePath,String isBorrow,String holder,String dateTimeOwner){
        this(username,id,productName,category,locationProduct,detail,datePurchased,timePurchased,statusProduct,"","",imagePath,isBorrow,holder,dateTimeOwner);
        initialAddProductTime();


    }

    public Product(String username,String id, String productName, String category,String locationProduct, String detail,
                   String datePurchased,String timePurchased, String statusProduct, String productAddDate, String productAddTime,
                   String imagePath, String isBorrow, String holder, String dateTimeOwner) {
        this.username = username;
        this.id = id;
        this.productName = productName;
        this.category = category;
        this.locationProduct = locationProduct;
        this.detail = detail;
        this.datePurchased = datePurchased;
        this.timePurchased = timePurchased;
        this.statusProduct = statusProduct;
        this.productAddDate = productAddDate;
        this.productAddTime = productAddTime;
        this.imagePath = imagePath;
        this.isBorrow = isBorrow;
        this.holder = holder;
        this.dateTimeOwner = dateTimeOwner;


    }

    public Product(String s) {
    }

    public boolean checkId(String id) {
        if (this.id.equals(id)) return true;
        return false;
    }
    public void  initialAddProductTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        String loginDateTime = localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        String[] time = loginDateTime.split(" ");
        productAddTime = time[1];
        productAddDate = time[0];
    }
    public String toCsv() {
        return username + "," + id + "," + productName + "," + category + "," + locationProduct + "," + detail + "," + datePurchased + "," + timePurchased + ","
                + statusProduct + "," + productAddDate + "," + productAddTime + "," + imagePath + "," + isBorrow + "," + holder + "," + dateTimeOwner;
    }
    public String getImagePath() {
        return imagePath;
//        return new File(System.getProperty("user.dir") +
//                File.separator +
//                "image/products" +
//                File.separator +
//                imagePath).toURI().toString();
    }

    public String getHolder() {
        return holder;
    }

    public String getProductName() {
        return productName;
    }

    public String getDetail() { return detail; }
    public String getId() { return id; }
    public String getCategory() {
        return category;
    }

    public String getUsername(){return username;}

    public String getLocation(){return locationProduct;}

    public String getStatusProduct(){return statusProduct;}

    public String getDatePurchased() {
        return datePurchased + " " + timePurchased;
    }
    public String getYearPurchased() {
        String[] date = datePurchased.split("-");
        String year = date[2];
        return year;
    }

    public String getProductAddDate() {
        return productAddDate + " " + productAddTime;
    }

    public String getIsBorrow() {return isBorrow;}

    public String getDateTimeOwner() {
        return dateTimeOwner;
    }

    public void setDateTimeOwner(String dateTimeOwner) {
        this.dateTimeOwner = dateTimeOwner;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void setIsBorrow(String isBorrow) {
        this.isBorrow = isBorrow;
    }

    public void setLocationProduct(String locationProduct) {
        this.locationProduct = locationProduct;
    }

    @Override
    public void setImagePathToDirectory(String path) {
        String[] fileSplit = path.split("\\.");
        this.imagePath = getFilePictureName() + "." + fileSplit[fileSplit.length - 1];
    }


    @Override
    public String getFilePictureName() {
        return null;
    }
    public void setImagePath() {
        imagePath = id + "-" + "product.png";
//        if (fileSelected != null) {
//            imagePath = id + "-" + "product.png";
//            copyProductImageToPackage(fileSelected, imagePath);
//        }
//        else {
//            imagePath = "product.png";
//        }
    }

    public static void copyProductImageToPackage(File image, String imageName) {
        File file = new File("image/products");
        try {
            BufferedImage bi = ImageIO.read(image);
            ImageIO.write(bi, "png", new File(file.getAbsolutePath(), imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

