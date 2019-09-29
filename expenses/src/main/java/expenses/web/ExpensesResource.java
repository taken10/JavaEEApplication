package expenses.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mvc.Controller;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import bean.DepositBean;
import bean.HistoryBeanList;
import bean.HistoryDataBean;
import bean.OutputDepositBean;
import bean.OutputHistoryDataBean;
import bean.OutputHistoryDataBean2;
import ejb.ExpensesEjb;
import model.Balance;
import model.History;

@Path("data")
@Controller
@RequestScoped
public class ExpensesResource {

	@Inject
	private ExpensesEjb ejb;

	@Inject
	private HistoryBeanList historyBeanList;

	@Inject
	private OutputHistoryDataBean outputHistoryDataBean;

	@Inject
	private OutputDepositBean outputDepositBean;

	@Context
	HttpServletRequest request;

	@Context
	HttpServletResponse response;

	@GET
	@Path("top")
	@Produces
	public void top() throws ServletException, IOException {
		System.out.println("/////JAX-RSリソース_トップページ遷移処理/////");

		RequestDispatcher dis = request.getRequestDispatcher("/views/TopPage.xhtml");
		dis.forward(request, response);
	}

	@GET
	@Path("back")
	@Produces
	public void backTop() throws ServletException, IOException {
		System.out.println("/////JAX-RSリソース_トップページ戻る処理/////");

		RequestDispatcher dis = request.getRequestDispatcher("/views/TopPage.xhtml");
		dis.forward(request, response);
	}

	@GET
	@Path("addhistory")
	@Produces
	public void addhistory() throws ServletException, IOException {
		System.out.println("/////JAX-RSリソース_履歴登録画面遷移/////");

		RequestDispatcher dis = request.getRequestDispatcher("/views/AddHistory.xhtml");
		dis.forward(request, response);
	}

	@GET
	@Path("selectspending")
	@Produces
	public void selectspending() throws ServletException, IOException {
		System.out.println("/////JAX-RSリソース_月ごと支出画面遷移/////");

		RequestDispatcher dis = request.getRequestDispatcher("/views/SelectSpending.xhtml");
		dis.forward(request, response);
	}

	@GET
	@Path("addmoney")
	@Produces
	public void addmoney() throws ServletException, IOException {
		System.out.println("/////JAX-RSリソース_入金画面遷移/////");

		RequestDispatcher dis = request.getRequestDispatcher("/views/AddMoney.xhtml");
		dis.forward(request, response);
	}

	/*
	 * DBから全履歴を取得
	*/
	@GET
	@Path("alldata")
	@Produces
	public void getList() throws ServletException, IOException {
		System.out.println("/////JAX-RSリソース_履歴一覧表示処理/////");

		List<History> historyList = ejb.getAllHistory();
		List<OutputHistoryDataBean2> outputHistoryList = new ArrayList<>();

		for (History h : historyList) {
			OutputHistoryDataBean2 historyDataBean = new OutputHistoryDataBean2();

			String formatImplementationDay = new SimpleDateFormat("yyyy年MM月dd日").format(h.getImplementationDay());
			String formatCreateDay = new SimpleDateFormat("yyyy年MM月dd日").format(h.getCreateDay());

			String inOut = h.getInOut();
			if (inOut.equals("in")) {
				historyDataBean.setInOut("収入");
			} else if (inOut.equals("out")) {
				historyDataBean.setInOut("出費");
			}

			historyDataBean.setId(h.getId());
			historyDataBean.setPrice(h.getPrice());
			historyDataBean.setImplementationDay(formatImplementationDay);
			historyDataBean.setCreateDay(formatCreateDay);

			outputHistoryList.add(historyDataBean);
		}
		historyBeanList.setList(outputHistoryList);
		//		historyBeanList.setList(ejb.getAllHistory());

		RequestDispatcher dis = request.getRequestDispatcher("/views/AllDataPage.xhtml");
		dis.forward(request, response);
	}

	/*
	 * DBから対象月の情報取得
	*/
	@POST
	@Path("getspending")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void getspending(@BeanParam HistoryDataBean historyDataBean)
			throws ServletException, IOException, ParseException {
		System.out.println("/////JAX-RSリソース_履歴一覧表示処理/////");

		Date implementation = sdfYearMonthDay(historyDataBean.getImplementationDay());

		// 対象月の履歴取得
		String searchValue = new SimpleDateFormat("yyyyMM").format(implementation);
		historyBeanList.setMonthlylist(ejb.getSelectHistory(searchValue));

		// 対象月の残金取得
		List<Balance> balanceList = getBalance(implementation);
		if (balanceList.size() == 1) {
			Balance balance = balanceList.get(0);
			outputDepositBean.setDeposit(balance.getBalance());
		}

		RequestDispatcher dis = request.getRequestDispatcher("/views/MonthryDataPage.xhtml");
		dis.forward(request, response);
	}

	/*
	 * DBに入金額を登録
	*/
	@POST
	@Path("insertmoney")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void insertmoney(@BeanParam DepositBean depositBean) throws ParseException, ServletException, IOException {
		System.out.println("/////JAX-RSリソース_入金処理/////");

		// String型をDate型に変換・フォーマット
		//		SimpleDateFormat sdf1 = new SimpleDateFormat(formatYearMonthDay);
		//		Date implementation = sdf1.parse(depositBean.getDepositDay());
		Date implementation = sdfYearMonthDay(depositBean.getDepositDay());

		// 入力された年月で検索
		List<Balance> balance = getBalance(implementation);

		if (balance.size() == 0) {
			Balance insertBalance = new Balance();
			insertBalance.setBalance(depositBean.getDeposit());
			insertBalance.setDepositMonth(implementation);

			//EJBの登録処理
			try {
				ejb.addmoney(insertBalance);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			// 画面出力ビーンに値を格納
			outputDepositBean.setDeposit(depositBean.getDeposit());

			RequestDispatcher dis = request.getRequestDispatcher("/views/ResultMoney.xhtml");
			dis.forward(request, response);
		}
		RequestDispatcher dis = request.getRequestDispatcher("/views/ResultErrorMoney.xhtml");
		dis.forward(request, response);
	}

	/*
	 * DBに履歴を登録
	*/
	@POST
	@Path("insert")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void inserthistory(@BeanParam HistoryDataBean historyDataBean)
			throws ServletException, IOException, ParseException {
		System.out.println("/////JAX-RSリソース_履歴登録処理/////");

		Date implementation = sdfYearMonthDay(historyDataBean.getImplementationDay());

		// 入力された年月で検索
		List<Balance> balanceList = getBalance(implementation);

		if (balanceList.size() == 1) {
			History history = new History();
			history.setInOut(historyDataBean.getInOut());
			history.setPrice(historyDataBean.getPrice());
			//		SimpleDateFormat sdf = new SimpleDateFormat(formatYearMonthDay);
			//		Date implementation = sdf.parse(historyDataBean.getImplementationDay());
			//		history.setImplementationDay(implementation);
			history.setCategory(historyDataBean.getCategory());
			history.setImplementationDay(implementation);
			Date date = new Date();
			history.setCreateDay(date);

			Balance beforeBalance = balanceList.get(0);
			int beforeBalanceValue = beforeBalance.getBalance();
			int afterBalanceValue = 0;
			int price = Integer.valueOf(historyDataBean.getPrice());

			// 入金額を更新
			if (historyDataBean.getInOut().equals("in")) {
				afterBalanceValue = beforeBalanceValue + price;
				updateBalance(afterBalanceValue, implementation);
			} else {
				afterBalanceValue = beforeBalanceValue - price;
				updateBalance(afterBalanceValue, implementation);
			}

			// 履歴を登録
			try {
				ejb.addHistory(history);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

			// 更新結果を出力用ビーンに格納
			outputHistoryDataBean.setInOut(historyDataBean.getInOut());
			outputHistoryDataBean.setPrice(historyDataBean.getPrice());
			outputHistoryDataBean.setImplementationDay(historyDataBean.getImplementationDay());
			List<Balance> afterBalanceList = getBalance(implementation);
			Balance balance = afterBalanceList.get(0);
			outputDepositBean.setDeposit(balance.getBalance());

			RequestDispatcher dis = request.getRequestDispatcher("/views/ResultAddHistory.xhtml");
			dis.forward(request, response);
		} else {
			RequestDispatcher dis = request.getRequestDispatcher("/views/ResultErrorHistory.xhtml");
			dis.forward(request, response);
		}
	}

	private Date sdfYearMonthDay(String dateValue) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(dateValue);
	}

	private List<Balance> getBalance(Date date) {
		// 入力された年月で検索
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String searchValue = sdf.format(date);
		return ejb.getBalance(searchValue);
	}

	private int updateBalance(int afterBalanceValue, Date date) {
		// 入力された年月で検索
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMM");
		String searchValue = sdf2.format(date);
		return ejb.updateBalance(afterBalanceValue, searchValue);
	}
}
