package models.services.Check;

import java.util.List;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;
import play.libs.F.Option;
import utils.ModelUtil;
import utils.OptionUtil;
import utils.PageUtil;
import models.entity.Check;
import models.services.Model.ModelService;
import models.setting.CheckYouSetting;
import static play.libs.F.*;

public class CheckModelService implements ModelService<Check>{

	
	/**
	 * 
	 * 別にシングルトンなわけでもない
	 * 操作性を考慮してFactory Method的なメソッドを用意
	 * 注意点：状態を持たないようにする
	 * 
	 * @return
	 */
	public static CheckModelService use(){
		return new CheckModelService();
	}
	
	@Override
	public Option<Check> findById(Long id) {
		Option<Long> idOps = OptionUtil.apply(id);
		if(idOps.isDefined()){
			// EbeanのModelクラスであるCheckクラスを指定
			Model.Finder<Long, Check> find = ModelUtil.getFinder(Check.class);
			// 指定したIDの情報を取得
			return OptionUtil.apply(find.byId(id));
		}
		return new None<Check>();
	}

	@Override
	public Option<Check> save(Check entry) {
		Option<Check> entryOps = OptionUtil.apply(entry);
		if(entryOps.isDefined()){
			// Modelのインスタンス情報を保存
			entry.save();
			// 登録されていることを確認
			if(OptionUtil.apply(entry.getId()).isDefined()){
				return OptionUtil.apply(entry);
			}
		}
		// 登録されていない場合
		return new None<Check>();
	}

	@Override
	public Option<List<Check>> findWithPage(Integer pageSource) {
		Integer page = PageUtil.rightPage(pageSource);
		// EbeanのModelクラスであるCheckクラスを指定
		Finder<Long, Check> find = ModelUtil.getFinder(Check.class);
		return OptionUtil.apply(
				find.order().asc("created")
					.findPagingList(CheckYouSetting.LIMIT)
					.getPage(page).getList()
				);
	}

	/**
	 * 最大ページ数を取得
	 * @return
	 */
	public Option<Integer> getMaxPage() {
		return null;
	}

}
