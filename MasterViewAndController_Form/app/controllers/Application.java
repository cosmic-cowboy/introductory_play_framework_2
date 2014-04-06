package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Member;
import models.Message;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Tuple2;
import views.html.*;

public class Application extends Controller {

    // 様々な入力形式の入力フォームに対応する入出力インタフェース
	public static class SampleForm {
		public String input;
		public String pass;
		public boolean check;
		public String radio;
		public String sel;
		public String area;
		public Date date;
	}
	
	
    // 様々な入力形式の入力フォームの値を表示する
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

    // 様々な入力形式の入力フォームからの値を受け取り、表示可能な状態にして返す
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

    // 複数入力可能な可変の入力フォーム
	public static class RepeatForm {
	    // フィールドは必ずpublicを設定する
	    // そうしないと不可視になって値を設定・取得できなくなる場合がある
		public List<String> inputs;
	}
    
    // 複数入力可能な可変の入力フォームからの値を表示する
    public static Result repeat() {
    	
    	Form<RepeatForm> form = new Form(RepeatForm.class);
    	// 設定された初期値をフォームに代入する
		return ok(repeat.render("please set form.", form));
    	
    }

    // 複数入力可能な可変の入力フォームからの値を受け取り、表示可能な状態にして返す
    public static Result sendRepeat() {
    	
    	Form<RepeatForm> f = new Form(RepeatForm.class).bindFromRequest();

    	if(!f.hasErrors()){
    		RepeatForm rf = f.get();
    		String res = "value: ";
    		for(String s : rf.inputs){
    			res += " " + s;
    		}
    		rf.inputs.add("");
    		return ok(repeat.render(res, f));
    	} else {
    		return ok(repeat.render("ERROR", f));
    		
    	}
    	
    }

    // メッセージを繰り返し表示する
    public static Result repeatMessage() {
    	
    	List<Message> messages = Message.find.all();
		return ok(repeatMessage.render("please set form.", messages));
    	
    }


    //メッセージ追加画面（GET）
    public static Result addMessage() {
    	
    	// 入力フォーム
    	Form<Message> f = new Form(Message.class);
    	// メンバー一覧を取得
    	List<Member> members = Member.find.select("name").findList();
    	List<Tuple2<String, String>> opts = new ArrayList<Tuple2<String, String>>();
    	for(Member member : members){
    		opts.add(new Tuple2(member.name, member.name));
    	}
		return ok(addMessage.render("please set form.", f,  opts));
    	
    }

    // メッセージ追加（POST）
    public static Result createMessage() {
    	
		// 送信されたフォームの値をバインドしたFormインスタンスを生成 
    	Form<Message> f = new Form(Message.class).bindFromRequest();
    	// バリデーション
    	if(!f.hasErrors()){
    		// フォームからMessageインスタンスを取得
    		// ユーザ名はMemberテーブルに登録されていなければならなくなる
    		Message data = f.get();
    		// 取得した値をDB登録
    		data.save();
    		// リダイレクト
    		return redirect("/");
    	} else {
        	List<Tuple2<String, String>> opts = new ArrayList<Tuple2<String, String>>();
    		return badRequest(addMessage.render("ERROR", f, opts));
    	}
    }

    // メンバー追加画面（GET）
    public static Result addMember(){
    	Form<Member> f = new Form(Member.class);
    	return ok(addMember.render("メンバー登録フォーム", f));
    }

    // メンバー追加（POST）
    public static Result createMember() {
    	// 送信されたフォームの値をバインドしたFormインスタンスを生成 
    	Form<Member> f = new Form(Member.class).bindFromRequest();
    	// バリデーション
    	if(!f.hasErrors()){
    		// フォームからMemberインスタンスを取得
    		Member data = f.get();
    		// 取得した値をDB登録
    		data.save();
    		// リダイレクト
    		return redirect(routes.Application.index());
    	} else {
    		return badRequest(addMember.render("ERROR", f));
    	}
    }

    // ajaxメソッド(GET)
    public static Result getAjax() {
    	List<Message> msgs = Message.find.all();
    	return ok(ajax.render("please set form.", msgs));
    }

    // ajaxメソッド(POST)
    public static Result postAjax() {
    	String input = request().body().asFormUrlEncoded().get("input")[0];
    	// Jsonのノード
    	ObjectNode result = Json.newObject();
    	if(input == null){
    		result.put("status", "BAD");
    		result.put("message", "Can't get sending data...");
    		return badRequest(result);
    	} else {
    		result.put("status", "OK");
    		result.put("message", input);
    		return ok(result);
    	}
    }
}
