import models.Member;
import models.Message;

import org.junit.*;

import play.test.*;
import play.libs.F.*;
import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

/**
 * 
 * ModelやサーバーにアクセスしてControllerのチェック
 *
 */
public class IntegrationTest {

    @Test
    public void test() {
    	// fakeApplicationの設定
    	// fakeApplicationにinMemoryDatabase
    	// メモリ上にデータベースを保管するアプリケーションの設定
    	// データベースが何に設定されていても、影響をあたえることなく実行できる
    	// 
    	// testServerの設定
    	// 第一引数：ポート番号
    	// 第二引数：実行するアプリケーション
    	// 
    	// testServerの設定
    	// 第一引数：実行するTestServerインスタンス
    	// 第二引数：HTMLUNIT
    	// 第三引数：起動後に呼び出されるコールバック処理
       	// 
       	// 
        running(testServer(3333, fakeApplication(inMemoryDatabase())), 
        		HTMLUNIT, new Callback<TestBrowser>() {
        	// テストサーバー起動後のコールバック時に実行される
            public void invoke(TestBrowser browser) {
            	// 指定のアドレス（index）にアクセス
                browser.goTo("http://localhost:3333");
                // ページのソースを取得し、メッセージがあるかチェック
                assertThat(browser.pageSource()).contains("投稿メッセージ");
                
                // Messageのチェック
                Message msg = Message.find.all().get(0);
                assertThat(browser.pageSource().contains(msg.name));
                assertThat(browser.pageSource().contains(msg.message));
            }
        });
    }

    /**
     * Modelの機能を直接呼び出しているので、Controllerは不要、TestServerも不要
     */
    public void checkModel(){

    	// FakeApplicationインスタンス作成 
    	// 引数にはアプリケーションの設定情報を設定
    	// ここではメモリ上にデータベースを保管している
    	// これでデータベースの設定に影響されることがなくなる
    	FakeApplication fakeApplication = fakeApplication(inMemoryDatabase());
    	
    	try {
        	// テスト時のアプリケーションの起動
    		start(fakeApplication);
    		
    		// Memberの用意
    		Member member = new Member();
    		member.name = "dummy member";
    		member.mail = "dummy@dummuy";
    		member.save();
    		
    		Member member2 = Member.find.byId(member.id);
    		
    		// Messageの用意
    		Message message = new Message();
    		message.id = 99999L;
    		message.member = member2;
    		message.name = member.name;
    		message.message = "dummy test message";
    		message.save();
    		
    		Message message2 = Message.find.byId(message.id);
    		
    		// 連携データの追加
    		member2.messages.add(message2);
    		member2.update();
    		
    		// Modelのチェック
    		assertThat(member2.name).isEqualTo(member.name);
    		assertThat(message2.message).isEqualTo(message.message);
    		assertThat(message2.member).isEqualTo(member2);
    		
    		// Nullのチェック
    		Member member3 = Member.find.byId(98765L);
    		assertThat(member3).isNull();
    		
    		// 削除
    		long id = message.id;
    		Message message3 = Message.find.byId(id);
    		member3.delete();
    		assertThat(Message.find.byId(id)).isNull();
    	} finally {
        	// テスト時のアプリケーションの終了
    		stop(fakeApplication);
    	}
    }
}
