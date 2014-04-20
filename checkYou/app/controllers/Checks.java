package controllers;

import java.util.Date;

import play.*;
import play.mvc.*;
import views.html.*;

public class Checks extends Controller {

    public static Result index() {
        return ok(views.html.check.index.render("Your new application is ready.", new Date()));
    }


    public static Result result() {
        return ok(views.html.check.result.render("Your new application is ready.", new Date()));
    }


    public static Result resultId(Long id) {
        return ok(views.html.check.result.render("Your new application is ready.", new Date()));
    }


    public static Result recent(Integer page) {
        return ok(views.html.check.recent.render("Your new application is ready.", new Date()));
    }


    
}
