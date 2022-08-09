package kr.co.seoulit.account.budget.formulation.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.seoulit.account.budget.formulation.mapper.FormulationMapper;
import kr.co.seoulit.account.budget.formulation.to.BudgetBean;
import kr.co.seoulit.account.budget.formulation.to.BudgetStatusBean;
import org.springframework.ui.ModelMap;

@Service
@Transactional
public class FormulationServiceImpl implements FormulationService {

	@Autowired
	private FormulationMapper formulationDAO;

	ModelMap map = null;

	@Override
	public BudgetBean findBudget(BudgetBean bean) {
		// TODO Auto-generated method stub

			bean = formulationDAO.selectBudget(bean);

		return bean;
	}

	@Override
	public void findBudgetList(BudgetBean bean) {
		// TODO Auto-generated method stub

			formulationDAO.selectBudgetList(bean);

	}

	@Override
	public Vector<BudgetStatusBean> findBudgetStatus(BudgetBean bean) {
		// TODO Auto-generated method stub

		Vector<BudgetStatusBean> beans = null;
		beans = formulationDAO.selectBudgetStatus(bean);
		
		return beans;
	}

	@Override
	public ArrayList<BudgetBean> findBudgetAppl(BudgetBean bean) {
		// TODO Auto-generated method stub

			return formulationDAO.selectBudgetAppl(bean);

	}

	@Override
	public ModelMap registerBudget(BudgetBean bean) {
		map = new ModelMap();
		try{
		formulationDAO.insertBudget(bean);
		map.put("errorCode", 1);
		map.put("errorMsg", "성공!");
		}
		catch (DuplicateKeyException e){
			map.put("errorCode", -2);
			map.put("exceptionClass", e.getClass());
		}
		catch (Exception e) {
			map.put("errorCode", -1);
			map.put("exceptionClass", e.getClass());
		}
		return map;
	}

	@Override
	public ModelMap modifyBudget(BudgetBean bean) {
		map = new ModelMap();
		try{
			formulationDAO.updateBudget(bean);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공!");
		}
		catch (Exception e) {
			map.put("errorCode", -1);
			map.put("exceptionClass", e.getClass());
		}
		return map;
	}
}
