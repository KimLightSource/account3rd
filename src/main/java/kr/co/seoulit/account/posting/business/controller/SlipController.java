package kr.co.seoulit.account.posting.business.controller;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.seoulit.account.posting.business.service.BusinessService;
import kr.co.seoulit.account.posting.business.service.BusinessServiceImpl;
import kr.co.seoulit.account.posting.business.to.JournalBean;
import kr.co.seoulit.account.posting.business.to.JournalDetailBean;
import kr.co.seoulit.account.posting.business.to.SlipBean;
import kr.co.seoulit.account.sys.common.exception.DataAccessException;
import kr.co.seoulit.account.sys.common.util.BeanCreator;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import com.google.gson.Gson;

@RestController
@RequestMapping("/posting")
public class SlipController {

    @Autowired
    private BusinessService businessService;

    ModelAndView mav = null;
    ModelMap map = new ModelMap();

    @RequestMapping(value = "/slipmodification", method = {RequestMethod.POST, RequestMethod.GET})
    public String modifySlip(@RequestParam(value = "slipObj", required = false) String slipObj,
                             @RequestParam(value = "journalObj", required = false) String journalObj,
                             @RequestParam(value = "slipStatus", required = false) String slipStatus) {

        ArrayList<JournalBean> journalBeans;
        JSONArray journalJSONArray;
        SlipBean slipBean;
        Gson gson = new Gson();

        journalJSONArray = JSONArray.fromObject(journalObj); //?????????
        slipBean = gson.fromJson(slipObj, SlipBean.class);
        journalBeans = new ArrayList<>();
        for (Object journalObjs : journalJSONArray) {

            JournalBean journalBean = gson.fromJson(journalObjs.toString(), JournalBean.class);
            journalBean.setSlipNo(slipBean.getSlipNo());
            System.out.println(journalBean.getJournalNo() + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println("customerName: ++" + journalBean.getCustomerName());
            journalBeans.add(journalBean);
        }

        if (slipStatus.equals("????????????")) {
            slipBean.setSlipStatus("????????????");
        }

        return businessService.modifySlip(slipBean, journalBeans);
    }

    @RequestMapping(value = "/registerslip")
    public void registerSlip(@RequestParam(value = "slipObj", required = false) String slipObj,
                             @RequestParam(value = "journalObj", required = false) String journalObj,
                             @RequestParam(value = "slipStatus", required = false) String slipStatus) {

        Gson gson = new Gson();
        SlipBean slipBean = gson.fromJson(slipObj, SlipBean.class);
        JSONArray journalObjs = JSONArray.fromObject(journalObj);
        /*
         * slipBean.setReportingEmpCode(request.getSession().getAttribute("empCode").
         * toString()); // beanCreator?????? ??????????????? ??????..(dong) //?????? ?????????????????? ?????? ????????? ???????????? ????????? ??????
         * slipBean.setDeptCode(request.getSession().getAttribute("deptCode").toString()
         * ); //????????????
         */
        if (slipStatus.equals("????????????")) {
            slipBean.setSlipStatus("????????????"); //????????? ??????????????? ?????? null????????? ??? ????????? ??????????????? ?????? ?????????
        }

        ArrayList<JournalBean> journalBeans = new ArrayList<>();


        for (Object journalObjt : journalObjs) {
            JournalBean journalBean = gson.fromJson(journalObjt.toString(), JournalBean.class);
            System.out.println(slipBean.getSlipNo() + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

            journalBean.setSlipNo(slipBean.getSlipNo()); //slipNo??? journalBean??? ?????? ????????? ????????????
            journalBeans.add(journalBean);

        }
        businessService.registerSlip(slipBean, journalBeans);
    }

    @GetMapping("/slipremoval")
    public void removeSlip(@RequestParam String slipNo) {
        businessService.removeSlip(slipNo);

    }

    @GetMapping("/approvalslip")
    public void modifyapproveSlip(@RequestParam String approveSlipList,
                                  @RequestParam String isApprove
    ) {


        JSONArray approveSlipLists = JSONArray.fromObject(approveSlipList); // slip_no??? ???????????? //JSONArray.fromObject json ????????? ????????????
        String slipStatus = isApprove; // true ???????????? ????????? true ??? ?????????
        ArrayList<SlipBean> slipBeans = new ArrayList<>(); //?????? ?????? ?????????

        for (Object approveSlip : approveSlipLists) { // ??????????????? ????????? ?????????
            Calendar calendar = Calendar.getInstance(); //import???
            String year = calendar.get(Calendar.YEAR) + "";
            String month = "0" + (calendar.get(Calendar.MONTH) + 1); // 0~11??????
            String date = "0" + calendar.get(Calendar.DATE);
            String today = year + "-" + month.substring(month.length() - 2) + "-" + date.substring(date.length() - 2); //????????? 0,1 ?????? 0?????? ???????????? ????????? -2??? ???????????? ????????? 1????????? ???????????? -1????????? ?????????0?????????
            //2021-11-15
            System.out.println("approveSlip : " + approveSlip);
            SlipBean slipBean = new SlipBean();
            slipBean.setSlipNo(approveSlip.toString()); //????????????
            slipBean.setApprovalDate(today); //??????????????? ????????????
            slipBean.setSlipStatus(slipStatus); //????????????
            // slipBean.setApprovalEmpCode(request.getSession().getAttribute("empCode").toString()); //String ?????? ?????? ??? ??????
            slipBeans.add(slipBean);
        }

        businessService.modifyapproveSlip(slipBeans);

    }

    @GetMapping("/rangedsliplist")
    public ArrayList<SlipBean> findRangedSlipList(
            @RequestParam String fromDate,
            @RequestParam String toDate,
            @RequestParam String slipStatus) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("fromDate", fromDate);
        param.put("toDate", toDate);
        param.put("slipStatus", slipStatus);

        return businessService.findRangedSlipList(param);
    }

    @GetMapping("/disapprovalsliplist")
    public ArrayList<SlipBean> findDisApprovalSlipList() {

        return businessService.findDisApprovalSlipList();
    }

    @GetMapping("/findSlip")
    public ArrayList<SlipBean> findSlip(@RequestParam String slipNo) {

        return businessService.findSlip(slipNo);
    }

    @GetMapping("/accountingsettlementstatus")
    public HashMap<String, Object> findAccountingSettlementStatus(@RequestParam String accountPeriodNo,
                                                                  @RequestParam String callResult) {
        JSONObject json = new JSONObject();
        HashMap<String, Object> params = new HashMap<>();

        params.put("accountPeriodNo", accountPeriodNo);
        params.put("callResult", callResult);

        json.put("errorCode", 0);
        json.put("errorMsg", "????????? ?????? ??????");

        businessService.findAccountingSettlementStatus(params);


        return params;
    }

}