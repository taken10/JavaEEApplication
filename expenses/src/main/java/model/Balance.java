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
 * The persistent class for the balance database table.
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Balance.findAll", query="SELECT b FROM Balance b"),
	@NamedQuery(name="Balance.findMonthBalance", query="SELECT b FROM Balance b WHERE function('date_format',b.depositMonth, '%Y%m') = :searchValue"),
	@NamedQuery(name="Balance.updateBalance", query="UPDATE Balance b SET  b.balance = :afterBalanceValue WHERE function('date_format',b.depositMonth, '%Y%m') = :searchValue")
	})
public class Balance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private int balance;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="deposit_month")
	private Date depositMonth;

	public Balance() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBalance() {
		return this.balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Date getDepositMonth() {
		return this.depositMonth;
	}

	public void setDepositMonth(Date depositMonth) {
		this.depositMonth = depositMonth;
	}

}