package com.github.kkrull.jbehave.steps;

import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.stream.Stream;

/** Finds and creates JBehave step definition classes annotated with @Steps */
public final class StepDefinitionScanner {
    public Stream<?> newInstances(String packageName) {
        return stepDefinitionClasses(packageName)
            .map(this::noArgConstructor)
            .map(this::instantiate);
    }

    private Stream<Class<?>> stepDefinitionClasses(String packageName) {
        return new Reflections(packageName)
            .getTypesAnnotatedWith(Steps.class)
            .stream();
    }

    private Constructor<?> noArgConstructor(Class<?> theClass) {
        try {
            return theClass.getConstructor();
        } catch(NoSuchMethodException ex) {
            throw MissingNoArgConstructor.forClass(theClass, ex);
        }
    }

    private <T> T instantiate(Constructor<T> noArgConstructor) {
        try {
            return noArgConstructor.newInstance();
        } catch(Exception ex) {
            throw InstantiationFailed.forConstructor(noArgConstructor, ex);
        }
    }

    public static final class MissingNoArgConstructor extends RuntimeException {
        public static MissingNoArgConstructor forClass(Class<?> theClass, Throwable cause) {
            String message = String.format("Step definition class %s does not have any no-arg constructors", theClass);
            return new MissingNoArgConstructor(message, cause);
        }

        private MissingNoArgConstructor(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static final class InstantiationFailed extends RuntimeException {
        public static InstantiationFailed forConstructor(Constructor<?> constructor, Exception ex) {
            String message = String.format("Failed to instantiate %s", constructor);
            return new InstantiationFailed(message, ex);
        }

        private InstantiationFailed(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
