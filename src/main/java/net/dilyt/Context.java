package net.dilyt;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Context {
  private final Map<Class<?>, Class<?>> classMap = new HashMap<>();

  public Context() {
    this.configure();
  }

  protected abstract void configure();

  protected <T> void register(final Class<T> baseClass, final Class<? extends T> subClass) {
    classMap.put(baseClass, subClass.asSubclass(baseClass));
  }

  public <T> T getInstance(final Class<T> base) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    final Class<?> impl = classMap.get(base);
    // look for Inject annotations
    final Optional<Constructor<?>> annotated = Arrays.stream(impl.getConstructors())
        .filter(c -> c.isAnnotationPresent(Inject.class))
        .findFirst();
    // use annotated constructor or if none are annotated the default constructor
    Constructor<?> constructor = annotated.isPresent() ? annotated.get() : impl.getConstructor();
    final Class<?>[] parameterTypes = constructor.getParameterTypes();
    final Object[] objArr = new Object[parameterTypes.length];
    int i = 0;
    for (final Class<?> clazz : parameterTypes) {
      // recurse to build dependencies
      objArr[i++] = getInstance(clazz);
    }
    // instantiate
    return (T) constructor.newInstance(objArr);
  }
}
