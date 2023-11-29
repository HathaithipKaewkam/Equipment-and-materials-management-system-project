package ku.project.services;

import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileService {
    static public void initialFileIfNotExist(String directory, String filename){
        File file = new File(directory); // set directory path
        if (!file.exists()) file.mkdir(); // if file not exist will make new directory
        file = new File(directory + File.separator + filename); // set file path
        if (!file.exists()) { // if file not exist in path
            try {
                file.createNewFile(); // make new file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static public Image handleUploadPicture(File fileChooser, HandleImage object, String directory) {
        String filename = object.getFilePictureName();
        Path target = null;
        if (fileChooser != null) {
            try {
                File destDir = new File(directory); // picture path
                String[] fileSplit = fileChooser.getName().split("\\.");
                filename = filename + "." + fileSplit[fileSplit.length - 1];
                target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath() + System.getProperty("file.separator") + filename);
                Files.copy(fileChooser.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                object.setImagePathToDirectory(directory + "/" + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Image(target.toUri().toString());
        } return null;
    }
}
