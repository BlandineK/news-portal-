import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.ArrayList;

public class Users {

    public String name;
    public String position;
    public String role;
    public String department;
    public int Id;

    private static ArrayList<Users> instances = new ArrayList<>();

    public Users(String name, String position, String role, String department) {

        this.name = name;
        this.position = position;
        this.role = role;
        this.department = department;
        instances.add(this);
//        this.Id = instances.size();

    }
    public static Object getAll() {
        return newUsers();
    }

    public String getName(){
        return this.name;
    }
    public String getPosition(){
        return this.position;
    }
    public String getRole(){
        return this.role;
    }
    public String getDepartment(){
        return this.department;
    }
    public static ArrayList<Users> getAllInstances(){

        return instances;
    }
    public int getId(){

        return this.Id;
    }
    public static Users findById(int id) {
        return instances.get(id-1);
    }
    public static Users deleteById(int id){
        return instances.get(id-1);
    }



}
