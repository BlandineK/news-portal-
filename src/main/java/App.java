import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;
import static spark.Spark.staticFileLocation;



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
//            model.put("departments", Departments.all());
//            model.put("template", "templates/index.hbs");
            return new ModelAndView(model, "index.hbs");
        },new HandlebarsTemplateEngine());


        get("Departments/new", (request, response) -> {

            Map<String, Object> model = new HashMap<String, Object>();
//            model.put("template", "templates/Department-form.hbs");
            model.put("allDepartments",Departments.getAll());
            return new ModelAndView(model, "Department-form.hbs");
        },new HandlebarsTemplateEngine());

        post("/Departments" , (request, response) -> {

            Map<String, Object> model = new HashMap<String,Object>();

            String Name = request.queryParams("Name");
            String Description = request.queryParams("Description");
            int Employees = Integer.parseInt(request.queryParams("Employees"));

            Departments newDepartment = new Departments (Name, Description, Employees);
//            newDepartment.save();
//            Departments.add(newDepartment);

            model.put("Name",Name);
            model.put("Description", Description);
            model.put("Employees", Employees);
//            model.put("template", "templates/Department.hbs");
            return new ModelAndView(model, "Department.hbs");
        },new HandlebarsTemplateEngine());

        get("Departments", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();

            return new ModelAndView(model, layout);
        },new HandlebarsTemplateEngine());

        //*********************USER*************************//

        get("User/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
//            model.put("template", "templates/User-form.hbs");
            return new ModelAndView(model, "User-form.hbs");
        },new HandlebarsTemplateEngine());

        post("/Users", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.queryParams("name");
            String position = request.queryParams("position");
            String role = request.queryParams("role");
            String department = request.queryParams("department");

            Users newUser = new Users(name,position,role,department);

            return new ModelAndView(model, "User-success.hbs");
        }, new HandlebarsTemplateEngine());

        get("Users", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model , "User.hbs");
        },new HandlebarsTemplateEngine());


        get("News/new", (response, request) -> {
            Map<String, Object> model = new HashMap<String, Object>();

            return new ModelAndView(model, "News-form.hbs");
        },new HandlebarsTemplateEngine());

        post("News", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String Header = request.queryParams("Header");
            String Content = request.queryParams("Content");
            int departmentId = Integer.parseInt(request.queryParams("departmentId"));
            News newNews = new News(Header,Content,departmentId);

            model.put("Header",Header);
            model.put("Content",Content);
            model.put("departmentId",departmentId);
            return new ModelAndView(model, "News-success.hbs");
        }, new HandlebarsTemplateEngine());

        get("News", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();

            return new ModelAndView(model, "News.hbs");
        }, new HandlebarsTemplateEngine());

        post("/Departments/:id/News", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();

            int departId = Integer.parseInt(request.params("id"));
            Departments idToFind = Departments.findById(DepartmentId);
            model.put("id", departId);
            model.put("dataId", idToFind.getId());
            model.put("allDepartments",Departments.getAllDepartmentsNews(departId));

            return new ModelAndView(model, "Department-news.hbs");
        },new HandlebarsTemplateEngine());

        // Add News to a Department

        get("/Departments/:id/News/new", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            int departmentId = Integer.parseInt(request.params("id"));
            String Header = request.queryParams("Header");
            String Content = request.queryParams("Content");
            Departments newNews = new Departments(Header, Content, departmentId);
            System.out.println(Departments.getAllNewsDepartments(departmentId));
            return new ModelAndView(user, "New-success.hbs");
        }, new HandlebarsTemplateEngine());

        // Department Details

        get("/Departments/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            int id = Integer.parseInt(request.queryParams("id"));
            System.out.println(id);
            Departments findDepDetails=Departments.findById(id);
            Departments idToFind = Departments.findById(Integer.parseInt(request.queryParams("id")));
            model.put("id", findDepDetails);
            model.put("departmentDetails",findDepDetails);
            model.put("users", Users.getAll());
            return new ModelAndView(model, "dep-details.hbs");
        },new HandlebarsTemplateEngine());

        // Users Details

        get("/users/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            int id = Integer.parseInt(request.queryParams("id"));
            System.out.println(id);
            Users findUserDetails = Users.findById(id);
            Users user=Users.findById(Integer.parseInt(request.queryParams("id")));
            model.put("id", findUserDetails);
            model.put("user",user);
            model.put("userDetails", findUserDetails);
            return new ModelAndView(model, "user-details.hbs");
        },new HandlebarsTemplateEngine());

        get("/users/:id/edit", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            int idOfUserToEdit = Integer.parseInt(request.queryParams("id"));
            Users editUser=Users.findById(idOfUserToEdit);
            model.put("editUser",editUser);
            model.put("editUser",true);
            return new ModelAndView(model, "users.hbs");
        },new HandlebarsTemplateEngine());

        get("/users/:id/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            int idOfUserToDelete = Integer.parseInt(request.queryParams("id"));
            Users deleteUser = Users.deleteById(idOfUserToDelete);
            Users.deleteById(idOfUserToDelete);
            model.put("deleteUser",deleteUser);
            model.put("deleteUser",true);
            return new ModelAndView(model, "del-user.hbs");
        },new HandlebarsTemplateEngine());

        get("/departments/:id/users", (request, response) -> {
            Map<String, Object> user = new HashMap<>();
            int departmentId = Integer.parseInt(request.queryParams("id"));
            Departments idToFind = Departments.findById(departmentId);
            user.put("depUsers", Departments.getAllUsers(departmentId));
            user.put("departmentId", departmentId);
            user.put("departmentId", idToFind.getId());
            user.put("allUsers", Users.getAll());
            return new ModelAndView(user, "department-users.hbs");
        }, new HandlebarsTemplateEngine());

        //  Add a new user to a Department

        post("/Departments/:departmentId/Users/new", (req, res) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            int departmentId = Integer.parseInt(request.queryParams("departmentId"));
            int userId = Integer.parseInt(request.queryParams("userId"));
            Departments department = Departments.findById(departmentId);
            Users users = Users.findById(userId);
            return new ModelAndView(model, "user-success.hbs");
        }, new HandlebarsTemplateEngine());

        // News Details

        get("/News/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            int departmentId = Integer.parseInt(request.queryParams("departmentId"));
            Departments idToFind = Departments.findById(departmentId);
            model.put("departmentId", idToFind);
            return new ModelAndView(model, "news-details.hbs");
        },new HandlebarsTemplateEngine());


    }
}