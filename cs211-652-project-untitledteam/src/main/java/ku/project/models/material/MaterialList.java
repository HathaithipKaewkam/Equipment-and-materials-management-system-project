package ku.project.models.material;

import java.util.ArrayList;

public class MaterialList {
    private ArrayList<Material>materials;

    public MaterialList(){
        materials = new ArrayList<>();
    }


    public Material findMaterialByName(String name){
        for(Material material : materials){
            if(material.isName(name)){
                return material;
            }
        }
        return null;
    }
    public boolean ExistNameMaterial(String name){
        for(Material material : materials) {
            if(material.getName().equals(name)){
                return false;
            }
        }
        return true;
    }

    //    public void decreaseAddAmountToAmount(String name,int Amount){
//        Material exist = findMaterialByName(name);
//        if (exist != null){
//            exist.decreaseTotal(Amount);
//        }
//    }

    public void addMaterial(Material materialList){ materials.add(materialList);}


    public ArrayList<Material>getMaterials(){
        return materials;
    }

    public void addNewMaterial(String name, String category, int amount, String property){
        name = name.trim();
        category = category.trim();
        if(!name.equals("")&&!category.equals("")){
            Material exist = findMaterialByName(name);
            if (exist == null){
                materials.add(new Material(name,category,amount,property));
            }
        }
    }

    public String toCSV() {
        String result = "";
        for (Material material: materials) {
            result += material.toCsv() + "\n";
        }
        return result;
    }
}
