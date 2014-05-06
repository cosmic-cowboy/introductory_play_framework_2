package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
@Table(name="admins", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class Admin extends Model{

	private static final long serialVersionUID = 1L;

	@Id
	public Long id;
	
	@Required(message="必須項目です")
	public String username;
	
	@Required(message="必須項目です")
	public String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
