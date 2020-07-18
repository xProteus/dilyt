A small dependency injection framework

# Design considerations
* Use a map with its keys as the interfaces and the values as the implementations.
* A method to register the different implementations to be wired, called through the `configure` that runs on init.
* A method to get instances of registered interfaces that wires all required dependencies.

# Limitations

* Constructor injection only, method injection can be implemented in a future iteration.
* For simplicity only a single constructor should be marked with the `@Inject` annotation.
* No cyclic dependency checking :(

# Getting Started / Usage Guide

Please refer to the examples below or unit tests for basic usage.

Create a new configuration by extending the Context class, calling the register method with the first arg as the interface and the second arg as the concrete implementation:
```java
public class AddingContext extends Context {
   @Override
   protected void configure() {
      register(Example.class, ExampleImpl.class, Scope.Prototype);
      register(Operation.class, AdditionOperation.class, Scope.Prototype);
      register(Format.class, SymbolFormat.class, Scope.Singleton);
   }
 }
```
Available scopes are `Prototype` and `Singleton` for now.

Use the included `Inject` annotation to mark constructor to be used for dependencies:
```
@Inject
public ExampleImpl(Operation operation, Format format) {
  this.operation = operation;
  this.format = format;
}
```

Instantiate the `Context` and use the `getInstance` method to get the constructed objects with wired in dependencies:
```java
    Context context = new AddingContext();
    final Example example = context.getInstance(Example.class);

```