package net.dilyt.sample.service;

public class CurrencyFormat implements Format {
	@Override
	public String format(String amount) {
		return "SGD " + amount;
	}
}
