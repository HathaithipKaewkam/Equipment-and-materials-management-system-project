package ku.project.models.material;

import ku.project.models.account.Account;

import java.util.ArrayList;

public class CategoryList {
    private ArrayList<Category> categoryList;
    public CategoryList() {
        categoryList = new ArrayList<>();
    }

    public void addCategory(Category category) {
        categoryList.add(category);
    }

    public ArrayList<Category> getAllCategory(){

        return categoryList;
    }

    public boolean ExistNameCategory(String name){
        for(Category category:categoryList) {
            if(category.getCategory().equals(name)){
                return false;
            }
        }
        return true;
    }
    public String toCSV() {
        String result = "";
        for (Category category:categoryList) {
            result += category.toCsv() + "\n";
        }
        return result;
    }
}
