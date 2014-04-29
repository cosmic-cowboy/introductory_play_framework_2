package apps;

import java.io.IOException;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;

import com.avaje.ebean.Ebean;

import static play.test.Helpers.*;
import play.test.FakeApplication;

public class FakeApp {

	public static FakeApplication app;
	public static String createDd1 = "";
	public static String dropDd1 = "";
	
	/**
	 * @throws IOException
	 */
	public static void startApp() throws IOException {
		app = fakeApplication(inMemoryDatabase());
		start(app);
		String evolutionContent = 
				FileUtils.readFileToString(app.getWrappedApplication()
						.getFile("conf/evolutions/defalut/1.sql"));
		String [] splitEvolutionContent = evolutionContent.split("# --- !Ups");
		String [] upsDowns = splitEvolutionContent[1].split("# --- !Downs");
		createDd1 = upsDowns[0];
		dropDd1   = upsDowns[1];	
	}
	
	@Before
	public void createCleanDb(){
		initDb();
	}
	
	/**
	 * 各テストの起動時
	 * メモリデータベースのDROP
	 * メモリデータベースのCREATE
	 * キャッシュのクリア
	 */
	private static void initDb() {
		Ebean.execute(Ebean.createCallableSql(dropDd1));
		Ebean.execute(Ebean.createCallableSql(createDd1));
		
		// Ehcacheキャッシュのクリア
		CacheManager manage = CacheManager.create();
		Cache cache = manage.getCache("play");
		cache.removeAll();
	}

	public static void restartApp(){
		start(app);
	}
	
	@AfterClass
	public static void stopApp(){
		stop(app);
	}

}
