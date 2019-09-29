package bean;

import java.io.Serializable;

import javax.ws.rs.FormParam;


/*
 * JSFの情報を受け取るマネージドビーン
 */

//@Named
//@RequestScoped
public class HistoryDataBean implements Serializable {

	public HistoryDataBean() {
	}

    private int id;

	private String changeDay;

	private String createDay;

	private String delflg;

	@FormParam("implementationDay") private String implementationDay;

	@FormParam("inout") private String inOut;

	@FormParam("price") private String price;

	@FormParam("category") private String category;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChangeDay() {
		return this.changeDay;
	}

	public void setChangeDay(String changeDay) {
		this.changeDay = changeDay;
	}

	public String getCreateDay() {
		return this.createDay;
	}

	public void setCreateDay(String createDay) {
		this.createDay = createDay;
	}

	public String getDelflg() {
		return this.delflg;
	}

	public void setDelflg(String delflg) {
		this.delflg = delflg;
	}

	public String getImplementationDay() {
		return this.implementationDay;
	}

	public void setImplementationDay(String implementationDay) {
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}