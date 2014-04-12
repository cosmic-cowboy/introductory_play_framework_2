import java.util.List;
import java.util.Map;

import models.Member;
import models.Message;

import com.avaje.ebean.Ebean;

import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;


/**
 * アプリケーションの設定を適用する
 * 
 * アプリケーション起動時に状況に応じて発生するイベントにより、
 * 自動的に予備が呼び出され、設定が適用される
 *
 * 必ずGlobalという名前でなければならない
 * 
 * イベントは
 * Badリクエスト時、エラー時、actionがない場合、設定ロード時、リクエスト時、ルートリクエスト時
 * アプリケーション開始時、終了時などがある。
 * 
 * http://www.playframework.com/documentation/2.2.1/ScalaGlobal
 */
public class Global extends GlobalSettings{

		@Override
		public void onStart(Application app) {
			insert(app);
		}

		public void insert(Application app) {
			Map<String, List<Object>> all = 
			(Map<String, List<Object>>) Yaml.load("test-data.yml");
			Ebean.save(all.get("members"));
			Ebean.save(all.get("messages"));
			for (Object message : all.get("messages")){
				Message target = Message.find.byId(((Message) message).id);
				target.member = Member.findByName(target.name);
				target.update();
			}
		}
		
}
