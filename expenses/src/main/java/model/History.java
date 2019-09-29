package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the history database table.
 *
 */
@Entity
@NamedQueries({
@NamedQuery(name="History.findAll", query="SELECT h FROM History h")
})
public class History implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="change_day")
	private Date changeDay;

	@Temporal(TemporalType.DATE)
	@Column(name="create_day")
	private Date createDay;

	private String delflg;

	private String category;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="implementation_day")
	private Date implementationDay;

	@Column(name="in_out")
	private String inOut;

	private String price;

	public History() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getChangeDay() {
		return this.changeDay;
	}

	public void setChangeDay(Date changeDay) {
		this.changeDay = changeDay;
	}

	public Date getCreateDay() {
		return this.createDay;
	}

	public void setCreateDay(Date createDay) {
		this.createDay = createDay;
	}

	public String getDelflg() {
		return this.delflg;
	}

	public void setDelflg(String delflg) {
		this.delflg = delflg;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getImplementationDay() {
		return this.implementationDay;
	}

	public void setImplementationDay(Date implementationDay) {
		this.implementationDay = implementationDay;
	}

	public String getInOut() {
		return this.inOut;
	}

	public void setInOut(String inOut) {
		this.inOut = inOut;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}