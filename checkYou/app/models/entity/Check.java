package models.entity;

import java.util.Date;

import javax.persistence.*;

import play.data.format.Formats.DateTime;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="checks")
public class Check extends Model {

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

}
