package controllers;

import java.util.List;

import models.Message;
import play.*;
import play.mvc.*;
import views.html.*;
import play.data.*;
import play.data.validation.Constraints.Required;
import static play.data.Form.*;

public class Application extends Controller {

	// Form用の内部クラス
	public static class SampleForm {
		public String message;
	}

    // トップ画面（GET）
    public static Result index() {
    	// findクラスを利用してエンティティを取得
    	// index.scala.htmlの引数に設定
    	List<Message> messages = Message.find.all();
        return ok(index.render(
        	"データベースのサンプル", messages
        ));
    }
    
    // メッセージ追加画面（GET）
    public static Result add(){
    	Form<Message> f = new Form(Message.class);
    	return ok(add.render("投稿フォーム", f));
    }

    // メッセージ追加（POST）
    public static Result create() {
    	// 送信されたフォームの値をバインドしたFormインスタンスを生成 
    	Form<Message> f = new Form(Message.class).bindFromRequest();
    	// バリデーション
    	if(!f.hasErrors()){
    		// フォームからMessageインスタンスを取得
    		Message data = f.get();
    		// 取得した値をDB登録
    		data.save();
    		// リダイレクト
    		return redirect("/");
    	} else {
    		return badRequest(add.render("ERROR", f));
    	}
    }
    
    // メッセージ編集画面の入り口(GET)
    public static Result setitem(){
    	Form<Message> f = new Form(Message.class);
    	return ok(item.render("ID番号を入力", f));
    }

    // メッセージ編集画面(POST)
    public static Result edit(){
    	// 送信されたフォームの値をバインドしたFormインスタンスを生成
    	Form<Message> f = new Form(Message.class).bindFromRequest();
    	// バリデーション
    	if(!f.hasErrors()){
    		// フォームからMessageインスタンスを取得
    		Message obj = f.get();
    		// インスタンスからIDを取得
    		Long id = obj.id;
    		// IDからエンティティの取得
    		obj = Message.find.byId(id);
    		if(obj != null){
    			// 取得したMessageインスタンスをフォームに設定
    			f = new Form(Message.class).fill(obj);
    			// 編集画面(edit)を呼び出す
    			return ok(edit.render("ID=" + id + "の投稿を編集", f));
    		} else {
    			// 検索画面(item)のまま
    			return ok(item.render("ERROR:IDの投稿が見つかりません",f));
    		}
    	} else {
			// 検索画面(item)のまま
			return ok(item.render("ERROR:入力に問題があります",f));
    	}
    }
    
    // メッセージを更新する（POST）
    public static Result update(){
		// フォームからMessageインスタンスを取得
    	Form<Message> f = new Form(Message.class).bindFromRequest();
    	if(!f.hasErrors()){
    		Message data = f.get();
    		// エンティティの更新
    		data.update();
    		// ルートにリダイレクト
    		return redirect("/");
    	} else {
    		// 編集画面(edit)のまま
			return ok(edit.render("ERROR:再入力してください",f));
    	}
    }
    
    // メッセージ削除画面（GET）
    public static Result delete(){
		Form<Message> f = new Form(Message.class);
		return ok(delete.render("ID番号を入力", f));
    }

    // メッセージを削除する（POST）
    public static Result remove(){
    	// 送信されたフォームの値をバインドしたFormインスタンスを生成
    	Form<Message> f = new Form(Message.class).bindFromRequest();
    	if(!f.hasErrors()){
    		// フォームからMessageインスタンスを取得
    		Message message = f.get();
    		// インスタンスからIDを取得
    		Long id = message.id;
    		// IDからエンティティの取得
    		message = Message.find.byId(id);
    		if(message != null){
    			message.delete();
    			return redirect("/");
    		} else {
        		return ok(delete.render("ERROR:そのIDは見つかりません",f));    			
    		}
    		
    	} else {
    		return ok(delete.render("ERROR:入力にエラーが起こりました",f));
    	}
    }

    // メッセージ検索画面(GET)
    public static Result find(){
    	// 検索キーワード
    	Form<FindForm> f = new Form(FindForm.class).bindFromRequest();
    	List<Message> datas = null;
		// 検索キーワードがあるときだけ検索を実行する
    	if(!f.hasErrors()){
    		String input = f.get().input;
    		// 検索条件を設定
    		datas = Message.find.where()
    				.like("name", "%" +input+ "%").orderBy("name")
    				.findList();
    	}
		// 検索結果を返却
    	return ok(find.render("投稿の検索", f, datas));
    	
    }
    public static class FindForm {
    	@Required
    	public String input;
    }
    
//    // sendにアクセスした際のAction
//    public static Result send() {
//    	// フォームの情報を取得
//    	// 送信されたフォームの値をバインドしたFormインスタンスを生成
//    	Form<SampleForm> f = form(SampleForm.class).bindFromRequest();
//    	// バリデーション
//    	if(!f.hasErrors()){
//    		// フォームの取り出し
//    		SampleForm data = f.get();
//    		String msg = "you typed: " + data.message;
//    		// フォームの値をindexページに設定
//	        return ok(index.render(msg, f));
//    	} else {
//    		// エラーが発生したことをクライアントに伝える
//    		return badRequest(index.render("Error", form(SampleForm.class)));
//    	}
//    }

}
