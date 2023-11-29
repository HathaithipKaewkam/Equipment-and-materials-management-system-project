package ku.project.models.material;

public class Category {
    private String category;
    private String type;
    public Category(String category,String type){

        this.category = category;
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public  String toCsv(){
        return category + "," + type;
    }

}
