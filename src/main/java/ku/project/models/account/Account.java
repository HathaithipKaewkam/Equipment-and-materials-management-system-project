package ku.project.models.account;

import ku.project.services.HandleImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Account implements HandleImage {

    public static File fileSelected;
    private String role;
    private String name;
    private String username;
    private String password;

    private String imagePath;
    private String loginDate;
    private String loginTime;

    public Account(String name, String username, String password){
        this("role_",name,username,password,"profile-user.png","","");
        initialLoginTime();
    }


    public Account(String role, String name, String username, String password ,String imagePath,String loginDate, String loginTime) {
        this.role = role;
        this.name = name;
        this.username = username;
        this.password = password;
        this.imagePath = imagePath;
        this.loginDate = loginDate;
        this.loginTime = loginTime;
    }

    public Account() {

    }


    public void initialLoginTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        String loginDateTime = localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        String[] time = loginDateTime.split(" ");
        loginDate = time[0];
        loginTime = time[1];

    }

    public String getLoginDateTime(){
        return loginDate + " " + loginTime;
    }

    public LocalDateTime getTime(){
        String[] data = loginDate.split("-");
        int day = Integer.parseInt(data[0]);
        int month = Integer.parseInt(data[1]);
        int year = Integer.parseInt(data[2]);
        data = loginTime.split(":");
        int hour = Integer.parseInt(data[0]);
        int minute = Integer.parseInt(data[1]);
        int second = Integer.parseInt(data[2]);
        return LocalDateTime.of(year, month, day, hour, minute, second);

    }

    public boolean canLogin(String username, String password){
        if (this.username.equals(username) && this.password.equals(password)){
            initialLoginTime();
            return true;
        }
        return false;
    }

    public String getRole() {return role;}

    public String getName(){
        return name;
    }
    public String getUsername(){
        return username;
    }

    public String getPassword(){return password;}

    public String getLoginDate(){ return loginDate;}

    public String getLoginTime(){ return loginTime;}

    public void setPassword(String password){ this.password = password;}

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isAdmin(){
        if(role.equals("Admin"))
            return true;
        return false;
    }
    public boolean isStaff(){
        if(role.equals("Staff"))
            return true;
        return false;
    }
    public boolean isUser(){
        if(role.equals("User"))
            return true;
        return false;
    }

    public boolean isPassword(String password) {
        return this.password != null && this.password.equals(password);
    }

    public void changePassword(String newPassword){
        password = newPassword;
    }

    public boolean validPassword(String password){return ((password.matches("^[a-zA-Z0-9]{6,10}$")));}
    public boolean validUsername(String username){return ((username.matches("^[a-zA-Z0-9]+(.+){6,10}$")));}



    public String toString(){
        //return role + "," + name + "," + username + "," + password + "," + imagePath + "," + loginDate + "," + loginTime;
        return role + "," + name + "," + username + ","  + loginDate + "," + loginTime;
    }

    public boolean checkAccount(String username) {
        return this.username.equals(username);
    }

    public String getUrlOfImagePath(){
        String fs = File.separator;
        String url = System.getProperty("user.dir") + fs + "image/profiles" + fs + role + fs + imagePath;
        return url;
//        return new File(System.getProperty("user.dir") +
//                File.separator +
//                "image/profiles" +
//                File.separator +
//                imagePath).toURI().toString();
    }

    @Override
    public void setImagePathToDirectory(String path) {
        String[] fileSplit = path.split("\\.");
        this.imagePath = getFilePictureName() + "." + fileSplit[fileSplit.length - 1];

    }
    //    public String getImagePath() {
//        return imagePath;
//    }
//    public void setImagePath(String dirPic) {
//        this.imagePath = imagePath;
//    }
//
    @Override
    public String getFilePictureName() {
        return imagePath;
    }

//    public void setImagePath() {
//        if (fileSelected != null) {
//            imagePath = username + "-" + "profile.png";
//            copyUserImageToPackage(fileSelected, imagePath);
//        }
//        else {
//            imagePath = "src/main/resources/images/newuser.png";
//        }
//    }
//    public void copyUserImageToPackage(File image, String imageName) {
//        File file = new File("image/profiles");
//        try {
//            BufferedImage bi = ImageIO.read(image);
//            ImageIO.write(bi, "png", new File(file.getAbsolutePath(), imageName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
