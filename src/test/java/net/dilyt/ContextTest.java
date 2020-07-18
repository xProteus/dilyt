package net.dilyt;

import net.dilyt.sample.ExampleImpl;
import net.dilyt.sample.service.AdditionOperation;
import net.dilyt.sample.service.CurrencyFormat;
import net.dilyt.sample.service.Format;
import net.dilyt.sample.service.Operation;
import net.dilyt.sample.service.SymbolFormat;
import net.dilyt.sample.Example;
import net.dilyt.sample.service.SubtractionOperation;
import org.junit.Assert;
import org.junit.Test;

public class ContextTest {
  private class AddingContext extends Context {
    @Override
    protected void configure() {
      register(Example.class, ExampleImpl.class);
      register(Operation.class, AdditionOperation.class);
      register(Format.class, SymbolFormat.class);
    }
  }

  private class SubtractionContext extends Context {
    @Override
    protected void configure() {
      register(Example.class, ExampleImpl.class);
      register(Operation.class, SubtractionOperation.class);
      register(Format.class, CurrencyFormat.class);
    }
  }

  @Test
  public void testCreateContext() throws Exception {
    Context context = new AddingContext();
    final Operation service = context.getInstance(Operation.class);
    Assert.assertEquals(service.calculate(2, 1), 3);
  }

  @Test
  public void testDifferentContext() throws Exception {
    Context context = new SubtractionContext();
    final Operation service = context.getInstance(Operation.class);
    Assert.assertEquals(service.calculate(2, 1), 1);
  }

  @Test
  public void testInjectsDependencies() throws Exception {
    Context context = new AddingContext();
    final Example example = context.getInstance(Example.class);
    Assert.assertEquals(example.process(2, 1), "$ 3");
  }

  @Test
  public void testPrototypeCreatesMultipleInstances() throws Exception {
    Context context = new AddingContext();
    final Format instance1 = context.getInstance(Format.class);
    final Format instance2 = context.getInstance(Format.class);
    Assert.assertNotSame(instance1, instance2);
  }
}