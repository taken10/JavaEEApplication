package bean;

import java.io.Serializable;

import javax.ws.rs.FormParam;


/*
 * JSFの情報を受け取るマネージドビーン
 */
public class DepositBean implements Serializable {

	public DepositBean() {
	}

    private int id;

	@FormParam("deposit") private int deposit;

	@FormParam("depositDay") private String depositDay;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeposit() {
		return deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}

	public String getDepositDay() {
		return depositDay;
	}

	public void setDepositDay(String depositDay) {
		this.depositDay = depositDay;
	}

}