package models.services.Check;

import java.util.List;

import play.libs.F.Option;
import models.entity.Check;
import models.services.Model.ModelService;

public class CheckModelService implements ModelService<Check>{

	@Override
	public Option<Check> findById(Long id) {
		return null;
	}

	@Override
	public Option<Check> save(Check entry) {
		return null;
	}

	@Override
	public Option<List<Check>> findWithPage(Integer pageSource) {
		return null;
	}

	/**
	 * 最大ページ数を取得
	 * @return
	 */
	public Option<Integer> getMaxPage() {
		return null;
	}

}
