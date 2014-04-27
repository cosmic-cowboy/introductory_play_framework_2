package controllers;

import java.util.Date;

import models.request.Check.ResultPostRequest;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Checks extends Controller {

    public static Result index() {
    	Form<ResultPostRequest> form = new Form(ResultPostRequest.class);
        return ok(views.html.check.index.render("CheckYou", "CheckYou", form));
    }


    public static Result result() {
        return TODO;
    }


    public static Result resultId(Long id) {
        return TODO;
    }


    public static Result recent(Integer page) {
        return TODO;
    }


    
}
