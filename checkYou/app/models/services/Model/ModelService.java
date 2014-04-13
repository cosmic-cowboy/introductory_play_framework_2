package models.services.Model;

import java.util.List;

import play.db.ebean.Model;
import play.libs.F.Option;

public interface ModelService<T extends Model> {

	/**
	 * IDで検索
	 * @param id
	 * @return
	 */
	public Option<T> findById(Long id);

	/**
	 * 保存
	 * @param entry
	 * @return
	 */
	public Option<T> save(T entry);

	/**
	 * ページ番号で取得
	 * @param pageSource
	 * @return
	 */
	public Option<List<T>> findWithPage(Integer pageSource);
}
