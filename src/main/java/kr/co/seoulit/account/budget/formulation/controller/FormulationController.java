package kr.co.seoulit.account.budget.formulation.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import kr.co.seoulit.account.sys.common.exception.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import kr.co.seoulit.account.sys.common.util.BeanCreator;

import kr.co.seoulit.account.budget.formulation.service.FormulationService;

import kr.co.seoulit.account.budget.formulation.to.BudgetBean;
import kr.co.seoulit.account.budget.formulation.to.BudgetStatusBean;

import net.sf.json.JSONObject;

@RestController
@RequestMapping("/budget")
public class FormulationController {

    @Autowired
    private FormulationService formulationService;

    ModelMap map = null;
    BeanCreator beanCreator = BeanCreator.getInstance();

    @GetMapping("/budget")
    public BudgetBean findBudget(@RequestParam String budgetObj) {

        JSONObject budgetJsonObj = JSONObject.fromObject(budgetObj); //예산
        BudgetBean budgetBean = beanCreator.create(budgetJsonObj, BudgetBean.class);


        return formulationService.findBudget(budgetBean);
    }

    @GetMapping("/budgetlist")
    public void findBudgetList(@RequestParam String budgetObj) {


        JSONObject budgetJsonObj = JSONObject.fromObject(budgetObj); //예산
        BudgetBean budgetBean = beanCreator.create(budgetJsonObj, BudgetBean.class);
        formulationService.findBudgetList(budgetBean);

    }

    @PostMapping("/budgetlist")
    public ModelMap registerBudget(@RequestParam(value = "budgetObj") String budgetObj) {
        JSONObject budgetJsonObj = JSONObject.fromObject(budgetObj); //예산
        BudgetBean budgetBean = beanCreator.create(budgetJsonObj, BudgetBean.class);
        map = new ModelMap();
        map = formulationService.registerBudget(budgetBean);
        return map;

    }

    ;

    @PutMapping("/budgetlist")
    public ModelMap modifyBudget(@RequestParam(value = "budgetObj") String budgetObj) {
        JSONObject budgetJsonObj = JSONObject.fromObject(budgetObj); //예산
        BudgetBean budgetBean = beanCreator.create(budgetJsonObj, BudgetBean.class);
        map = new ModelMap();
        try {
            formulationService.modifyBudget(budgetBean);
            map.put("errorCode", 1);
            map.put("errorMsg", "성공!");
        }
        catch (Exception e) {
            map.put("errorCode", -1);
            map.put("exceptionClass", e.getClass());
        }
        return map;
    }


    @GetMapping("/budgetstatus")
    public HashMap<String, Object> findBudgetStatus(@RequestParam String budgetObj) {

        HashMap<String, Object> params = new HashMap<>();
        JSONObject budgetJsonObj = JSONObject.fromObject(budgetObj); //예산
        BudgetBean budgetBean = beanCreator.create(budgetJsonObj, BudgetBean.class);
        params.put("accountPeriodNo", budgetBean.getAccountPeriodNo());
        params.put("deptCode", budgetBean.getDeptCode());
        params.put("workplaceCode", budgetBean.getWorkplaceCode());
        formulationService.findBudgetStatus(params);

        return params;
    }

    @RequestMapping(value = "/budgetappl", method = RequestMethod.POST)
    public ArrayList<BudgetBean> findBudgetAppl(@RequestParam String budgetObj) {


        JSONObject budgetJsonObj = JSONObject.fromObject(budgetObj); //예산
        BudgetBean budgetBean = beanCreator.create(budgetJsonObj, BudgetBean.class);

        return null;
    }
}
