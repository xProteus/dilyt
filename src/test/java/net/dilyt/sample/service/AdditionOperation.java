package net.dilyt.sample.service;

public class AdditionOperation implements Operation {
	@Override
	public int calculate(int a, int b) {
		return a + b;
	}
}
