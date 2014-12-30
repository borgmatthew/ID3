package com.assignment.ID3.tree;

public class InfoGainPair {

	Field<?> target;
	Field<String> outcome;
	
	public InfoGainPair(Field<?> target, Field<String> outcome) {
		this.target = target;
		this.outcome = outcome;
	}
	
	public Field<?> getTarget() {
		return target;
	}
	public void setTarget(Field<?> target) {
		this.target = target;
	}
	public Field<String> getOutcome() {
		return outcome;
	}
	public void setOutcome(Field<String> outcome) {
		this.outcome = outcome;
	}
}
