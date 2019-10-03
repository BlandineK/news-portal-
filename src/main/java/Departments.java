import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;


public class Departments {

    public String Name;
    public String Description;
    public int Employees;
    public int Id;
    private static ArrayList<Departments> instances = new ArrayList<>();
    public Departments(String Name, String Description, int Employees) {
        this.Name = Name;
        this.Description = Description;
        this.Employees = Employees;
        instances.add(this);
    }
    public String getName(){
        return this.Name;
    }
    public String getDescription(){
        return this.Description;
    }
    public int getEmployees(){
        return this.Employees;
    }
    public static ArrayList<Departments> getAllInstances(){
        return instances;
    }
    public int getId(){
        return this.Id;
    }




}