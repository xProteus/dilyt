package net.dilyt.sample.service;

public class SymbolFormat implements Format {
	@Override
	public String format(String amount) {
		return "$ " + amount;
	}
}
