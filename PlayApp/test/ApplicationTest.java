import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Member;
import models.Message;

import org.junit.Test;

import play.data.Form;
import play.mvc.Content;


/**
*
* テンプレートのチェック
*
*/
public class ApplicationTest {

	// ダミーデータ
	List<Member>  dummy_mems = null;
	List<Message> dummy_msgs = null;

	public ApplicationTest(){
		// 事前準備
		initialData();
	}
	
	// ダミーデータ作成
    public void initialData() {
    	// ダミーのメンバー作成
		dummy_mems = new ArrayList<Member>();
		Member mem = new Member();
		mem.id   = 10001L;
		mem.name = "dummy_name";
		mem.mail = "dummy@mail";
		mem.tel  = "0000";
		dummy_mems.add(mem);
		
    	// ダミーのメッセージ作成
		dummy_msgs  = new ArrayList<Message>();
		Message msg = new Message();
		msg.id = 1002L;
		msg.name = mem.name;
		msg.message = "dummy message";
		msg.postdate = new Date();
		dummy_msgs.add(msg);
		// メンバーにも設定
		mem.messages.add(msg);
		
	}

	/**
	 * add.scala.htmlのチェック
	 */
	@Test
    public void renderTemplate_add() {
    	String msg = "テストメッセージ";
    	// テンプレートのをレンダリングする
    	// テンプレートのインスタンスからrenderを呼び出し、Contentを取得する
    	Content add = views.html.add.render(msg, new Form(Message.class));
    	// ContentをcontentAsAtringを利用し、テキストに変換する
    	// 変換したテキストにmsgが含まれていることを確認する
        assertThat(contentAsString(add)).contains(msg);
    }

	/**
	 * index.scala.htmlのチェック
	 */
	@Test
    public void renderTemplate_index() {
    	String msg = "テストメッセージ";
    	// テンプレートのインスタンスからrenderを呼び出すため
    	// 事前に用意していた[initialData]ダミーのModelデータを引数に設定する
    	Content index = views.html.index.render(msg, dummy_msgs, dummy_mems);
    	// ContentTypeのチェック
        assertThat(contentType(index)).isEqualTo("text/html");
    	// ContentをcontentAsAtringを利用し、テキストに変換する
    	// 変換したテキストにmsgが含まれていることを確認する
        assertThat(contentAsString(index)).contains(msg);
    	// ダミーのModelデータが変換したテキストに含まれていることを確認する
        assertThat(contentAsString(index)).contains(dummy_msgs.get(0).message);
    }


}
