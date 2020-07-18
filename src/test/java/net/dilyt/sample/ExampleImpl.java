package net.dilyt.sample;

import net.dilyt.Inject;
import net.dilyt.sample.service.Format;
import net.dilyt.sample.service.Operation;

public class ExampleImpl implements Example {

	private final Operation operation;
	private final Format format;

	@Inject
	public ExampleImpl(Operation operation, Format format) {
		this.operation = operation;
		this.format = format;
	}

	@Override
	public String process(int a, int b) {
		int number = operation.calculate(a, b);
		return format.format(String.valueOf(number));
	}
}