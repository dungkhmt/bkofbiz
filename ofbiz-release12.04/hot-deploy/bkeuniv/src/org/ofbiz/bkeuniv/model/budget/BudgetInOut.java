package org.ofbiz.bkeuniv.model.budget;

public enum BudgetInOut {
	
	IN("1"),
	OUT("0");
	
	@SuppressWarnings("unused")
	private String value;
	
	BudgetInOut(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
