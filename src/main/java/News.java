import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.ArrayList;

public class News {
    public String Header;
    public String Content;
    public int Department;
    public int Id;

    private static ArrayList<News> instances = new ArrayList<>();

    public News(String Header, String Content, int DepartmentId) {

        this.Header = Header;
        this.Content = Content;
        this.Department = Department;
        instances.add(this);
//        this.Id = instances.size();
    }

    public String getHeader() {
        return this.Header;
    }

    public String getContent() {
        return this.Content;
    }

    public int getDepartment() {
        return this.Department;
    }

    public static ArrayList<News> getAllInstances() {
        return instances;
    }

    public int getId() {
        return this.Id;
    }

    public static News findById(int id) {
        return instances.get(id - 1);
    }

//    public News deleteById(int id) {
//
//    }

}



