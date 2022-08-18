package kr.co.seoulit.account.budget.formulation.service;

import java.util.ArrayList;
import java.util.Vector;

import kr.co.seoulit.account.budget.formulation.to.BudgetBean;
import kr.co.seoulit.account.budget.formulation.to.BudgetStatusBean;
import org.springframework.ui.ModelMap;

public interface FormulationService {
	
	public BudgetBean findBudget(BudgetBean bean);
	
	public BudgetBean findBudgetorganization(BudgetBean bean);
	
	public void findBudgetList(BudgetBean bean);
	
	public BudgetBean findBudgetAppl(BudgetBean bean);
	
	public ArrayList<BudgetStatusBean> findBudgetStatus(BudgetBean bean);

	public ModelMap registerBudget(BudgetBean bean);

	public ModelMap modifyBudget(BudgetBean bean);
}
