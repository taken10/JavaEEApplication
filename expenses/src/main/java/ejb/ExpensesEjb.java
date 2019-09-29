package ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import model.Balance;
import model.History;
import model.MonthlyHistory;

@Stateless
public class ExpensesEjb {

	@PersistenceContext
	EntityManager em;

	/*
	 * DBから全履歴を取得
	*/
	public List<History> getAllHistory() {
		System.out.println("/////EJB処理が始まりました。/////");

		// 名前付きクエリを使用して処理
		TypedQuery<History> query = this.em.createNamedQuery("History.findAll", History.class);
		List<History> list = query.getResultList();

		System.out.println("/////EJB処理が終わりました。/////");
		return list;
	}

	/*
	 * DBから指定した月の支出額を取得
	*/
	@SuppressWarnings("unchecked")
	public List<MonthlyHistory> getSelectHistory(String searchValue) {
		System.out.println("/////EJB処理が始まりました。/////");

		// 名前付きクエリを使用して処理
		Query query = em.createQuery("SELECT SUM(h.price),h.category FROM History h WHERE h.inOut = 'out' AND function('date_format',h.implementationDay, '%Y%m') = ?1 GROUP BY h.category", MonthlyHistory.class)
				.setParameter(1, searchValue);
		List<MonthlyHistory> monthlyHistoryList = query.getResultList();

		System.out.println("/////EJB処理が終わりました。/////");
		return monthlyHistoryList;
	}

	/*
	 * DBから指定した月の入金額を取得
	*/
	public List<Balance> getBalance(String searchValue) {
		System.out.println("/////入金取得EJB処理が始まりました。/////");
			// 名前付きクエリを使用して処理
			TypedQuery<Balance> query = this.em.createNamedQuery("Balance.findMonthBalance", Balance.class)
					.setParameter("searchValue", searchValue);
			List<Balance> balance = query.getResultList();
			System.out.println("/////入金取得EJB処理が終わりました。/////");
			return balance;
	}

	/*
	 * DBから指定した月の入金額を更新
	*/
	public int updateBalance(int afterBalanceValue, String searchValue) {
		System.out.println("/////入金更新EJB処理が始まりました。/////");
			// 名前付きクエリを使用して処理
			TypedQuery<Balance> query = this.em.createNamedQuery("Balance.updateBalance", Balance.class)
					.setParameter("afterBalanceValue", afterBalanceValue)
					.setParameter("searchValue", searchValue);
			int result = query.executeUpdate();
			System.out.println("/////入金更新EJB処理が終わりました。/////");
			return result;
	}

	/*
	 * DBに入金額を登録
	*/
	public void addmoney(Balance balance) {
		System.out.println("/////入金登録EJB処理が始まりました。/////");

		// DB追加
		em.persist(balance);

		System.out.println("/////入金登録EJB処理が終わりました。/////");
	}

	/*
	 * DBに収支履歴を登録
	*/
	public void addHistory(History history) {
		System.out.println("/////履歴登録EJB処理が始まりました。/////");

		// DB追加
		em.persist(history);

		System.out.println("/////履歴登録EJB処理が終わりました。/////");
	}

}
