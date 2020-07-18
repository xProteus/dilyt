package net.dilyt.sample.service;

public class SubtractionOperation implements Operation {
	@Override
	public int calculate(int a, int b) {
		return a - b;
	}
}
