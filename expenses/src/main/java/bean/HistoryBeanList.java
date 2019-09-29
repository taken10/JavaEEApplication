package bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import model.MonthlyHistory;

@Named(value ="historyBean")
@RequestScoped
public class HistoryBeanList {

	// 履歴リスト
//	private List<History> list;
	private List<OutputHistoryDataBean2> list;

	// 履歴リスト
	private List<MonthlyHistory> monthlylist;

	public List<OutputHistoryDataBean2> getList() {
		return list;
	}

	public void setList(List<OutputHistoryDataBean2> list){
		this.list = list;
	}

	public List<MonthlyHistory> getMonthlylist() {
		return monthlylist;
	}

	public void setMonthlylist(List<MonthlyHistory> monthlylist) {
		this.monthlylist = monthlylist;
	}

}
