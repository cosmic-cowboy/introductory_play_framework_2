package models.service.Check;

import org.junit.Test;

import models.entity.Check;
import models.services.Check.CheckModelService;
import play.libs.F.Option;
import play.libs.F.Some;
import static org.fest.assertions.Assertions.assertThat;

import com.avaje.ebean.Ebean;

import apps.FakeApp;

public class CheckModelServiceTest extends FakeApp {

	@Test
	public void テーブルから1件のレコードから1つ取り出す(){
//		Ebean.execute(Ebean.createSqlUpdate(
//				"INSERT INTO `checks` (`id`, `name`, `result`, `result`, `created`, `modified`) VALUES ('1',  'kara_d',  'kara_dさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:56', '2013-08-20 12:34:56');"
//		));
        Ebean.execute(Ebean.createSqlUpdate("INSERT INTO  `checks` (`id`, `name`, `result`, `created`, `modified`) VALUES ('1',  'kara_d',  'kara_dさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。',  '2013-08-20 12:34:56', '2013-08-20 12:34:56');"));

		Option<Check> model = new CheckModelService().findById(1L);
		assertThat(model.getClass()).isEqualTo(Some.class);
		assertThat(model.get().getId()).isEqualTo(1L);
		assertThat(model.get().getName()).isEqualTo("kara_d");
		assertThat(model.get().getResult()).isEqualTo("kara_dさんにオススメなPlay frameworkのバージョンは、2.1.3 Javaです。");
	}
}
