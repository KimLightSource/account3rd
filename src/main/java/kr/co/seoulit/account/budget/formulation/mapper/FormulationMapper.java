package kr.co.seoulit.account.budget.formulation.mapper;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.ibatis.annotations.Mapper;

import kr.co.seoulit.account.budget.formulation.to.BudgetBean;
import kr.co.seoulit.account.budget.formulation.to.BudgetStatusBean;

@Mapper
public interface FormulationMapper {
	
	public BudgetBean selectBudget(BudgetBean bean);
	
	public BudgetBean selectBudgetorganization(BudgetBean bean);
	
	public void selectBudgetList(BudgetBean bean);
	
	public BudgetBean selectBudgetAppl(BudgetBean bean);
	
	public ArrayList<BudgetStatusBean> selectBudgetStatus(BudgetBean bean);

	public void insertBudget(BudgetBean bean);

	public void updateBudget(BudgetBean bean);
}
