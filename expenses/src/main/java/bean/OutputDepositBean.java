package bean;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


/*
 * JSFへ情報を出力用のマネージドビーン
 */

@Named(value ="outputDepositBean")
@RequestScoped
public class OutputDepositBean implements Serializable {

	public OutputDepositBean() {
	}

    private int id;

	private int deposit;

	private String depositDay;

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