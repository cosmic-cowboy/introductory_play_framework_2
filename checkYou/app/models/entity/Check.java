package models.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import models.services.Check.CheckModelService;
import models.services.Check.CheckService;
import play.data.format.Formats.DateTime;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.libs.F.Option;

/**
 * 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="checks")
public class Check extends Model {

	// Transientを使い、Ebeanの管理対象からはずす
	@Transient
	private CheckService checkService = new CheckService();
	
	// Transientを使い、Ebeanの管理対象からはずす
	@Transient
	private CheckModelService checkModelService = new CheckModelService();
	
	@Id
	public Long id;
	
	@Required
	public String name;
	
	@Required
	public String result;
	
	@DateTime(pattern="yyyy/MM/dd")
	public Date created;
	
	@DateTime(pattern="yyyy/MM/dd")
	public Date modified;

	// コンストラクタ
	public Check() {
		super();
	}	
	public Check(String name) {
		this.name = name;
	}
	public Check(Long id) {
		this.id = id;
	}
	public Check(String name, String result) {
		this.name = name;
		this.result = result;
		this.created = new Date();
		this.modified = new Date();
	}
	
	
	// getter, setter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	/**
	 * 結果を取得
	 * @return 
	 */
	public Option<String> result(){
		return checkService.getResult(name);
	}
	
	/**
	 * 結果を保存
	 * @return
	 */
	public Option<Check> store(){
		return checkModelService.save(this);
	}
	
	/**
	 * idに該当するものを検索
	 * @return
	 */
	public Option<Check> unique(){
		return checkModelService.findById(id);
	}
	
	/**
	 * 指定ページの一覧
	 * @param page
	 * @return
	 */
	public Option<List<Check>> entitiesList(Integer page){
		return checkModelService.findWithPage(page);
	}
	
	/**
	 * 最大ページ数を取得
	 * @param value
	 * @return
	 */
	public Integer entitiesMaxPage(Integer value){
		return checkModelService.getMaxPage().getOrElse(value);
	}
	
}
