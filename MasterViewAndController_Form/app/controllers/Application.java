package controllers;

import java.util.Date;
import java.util.Map;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	public static class SampleForm {
		public String input;
		public String pass;
		public boolean check;
		public String radio;
		public String sel;
		public String area;
		public Date date;
	}
	
	
    public static Result index() {
    	
    	// 初期値を設定する
    	SampleForm sf = new SampleForm();
    	sf.radio = "windows";
    	sf.check = true;
    	sf.input = "default value";
    	sf.sel = "uk";
    	Form<SampleForm> form = new Form(SampleForm.class).fill(sf);
    	// 設定された初期値をフォームに代入する
		return ok(index.render("please set form.", form));
    	
    }

    public static Result send() {
    	// フォームに代入された値を取得する
    	Form<SampleForm> f = new Form(SampleForm.class).bindFromRequest();
    	
    	if (!f.hasErrors()){
    		// フォームに代入された値を表示する
    		SampleForm sf = f.get();
    		String res = "value: ";
    		res += "input = " + sf.input
    				+ ", pass = "  + sf.pass
    				+ ", check = " + sf.check
    				+ ", radio = " + sf.radio
    				+ ", sel = "   + sf.sel
    				+ ", area = "  + sf.area
    				+ ", date = "  + sf.date;
    		return ok(index.render(res, f));
    	} else {
    		return badRequest(index.render("ERROR",f));
    	}
    	
    }
}
