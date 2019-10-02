import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;




public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String layout = "templates/layout.hbs";
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        setPort(port);

        get("/" , (request, response) -> {

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("departments", Departments.all());
            model.put("template", "templates/index.hbs");
            return new ModelAndView(model, layout);
        },new HandlebarsTemplateEngine());


        get("Departments/new", (request, response) -> {

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/Department-form.hbs");
            return new ModelAndView(model, layout);
        },new HandlebarsTemplateEngine());

        post("/Departments" , (request, response) -> {

            Map<String, Object> model = new HashMap<String,Object>();

            String Name = request.queryParams("Name");
            String Description = request.queryParams("Description");
            int Employees = Integer.parseInt(request.queryParams("Employees"));

            Departments newDepartment = new Departments (Name, Description, Employees);
            newDepartment.save();

            model.put("Name",Name);
            model.put("Description", Description);
            model.put("Employees", Employees);
            model.put("template", "templates/department.hbs");
            return new ModelAndView(model, layout);
        },new HandlebarsTemplateEngine());

        get("Departments", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();

        })

    }
}
