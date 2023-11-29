package ku.project.services;

import ku.project.models.material.Category;
import ku.project.models.material.CategoryList;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CategoryDataSource implements DataSource<CategoryList>{
    private String directory;
    private String fileName;
    public CategoryDataSource(String directory, String fileName) {
        this.directory = directory;
        this.fileName = fileName;
    }
    public CategoryDataSource() {
        this("database", "category.csv");
    }

    @Override
    public CategoryList readData() {
        CategoryList categoryList = new CategoryList();
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
                String category = data[0];
                String type = data[1];
                categoryList.addCategory(new Category(category,type));
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
        return categoryList;
    }
    @Override
    public void writeData(CategoryList categoryList) {

        String path = directory + File.separator + fileName;
        File file = new File(path);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file, StandardCharsets.UTF_8);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(categoryList.toCSV());
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
