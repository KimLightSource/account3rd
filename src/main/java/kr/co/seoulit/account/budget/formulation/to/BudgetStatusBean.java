package kr.co.seoulit.account.budget.formulation.to;

import kr.co.seoulit.account.sys.base.to.BaseBean;

import java.util.List;


public class BudgetStatusBean extends BaseBean{
private String accountInnerCode;
private String accountName;
private long annualBudgetRecord;//연간 예산 실적
private long annualBudget;
private long remainingBudget;
private double budgetExecRatio;//집행률
private long monthBudgetRecord;
private long monthBudget;
private long remainingMonthBudget;
private double monthBudgetExecRatio;//집행률

public String getAccountInnerCode() {
	return accountInnerCode;
}
public void setAccountInnerCode(String accountInnerCode) {
	this.accountInnerCode = accountInnerCode;
}
public String getAccountName() {
	return accountName;
}
public void setAccountName(String accountName) {
	this.accountName = accountName;
}
public long getAnnualBudgetRecord() {
	return annualBudgetRecord;
}
public void setAnnualBudgetRecord(long annualBudgetRecord) {
	this.annualBudgetRecord = annualBudgetRecord;
}
public long getAnnualBudget() {
	return annualBudget;
}
public void setAnnualBudget(long annualBudget) {
	this.annualBudget = annualBudget;
}
public long getRemainingBudget() {
	return remainingBudget;
}
public void setRemainingBudget(long remainingBudget) {
	this.remainingBudget = remainingBudget;
}
public double getBudgetExecRatio() {
	return budgetExecRatio;
}
public void setBudgetExecRatio(double budgetExecRatio) {
	this.budgetExecRatio = budgetExecRatio;
}
public long getMonthBudgetRecord() {
	return monthBudgetRecord;
}
public void setMonthBudgetRecord(long monthBudgetRecord) {
	this.monthBudgetRecord = monthBudgetRecord;
}
public long getMonthBudget() {
	return monthBudget;
}
public void setMonthBudget(long monthBudget) {
	this.monthBudget = monthBudget;
}
public long getRemainingMonthBudget() {
	return remainingMonthBudget;
}
public void setRemainingMonthBudget(long remainingMonthBudget) {
	this.remainingMonthBudget = remainingMonthBudget;
}
public double getMonthBudgetExecRatio() {
	return monthBudgetExecRatio;
}
public void setMonthBudgetExecRatio(double monthBudgetExecRatio) {
	this.monthBudgetExecRatio = monthBudgetExecRatio;
}


}
