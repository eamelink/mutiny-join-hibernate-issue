# Minimal reproducer

This repository demonstrates an issue with Mutiny and Hibernate, causing the following exception to be thrown:

```
org.hibernate.HibernateException: java.util.concurrent.CompletionException: java.lang.IllegalStateException: Illegal pop() with non-matching JdbcValuesSourceProcessingState
```

There's one test case.

It contains a commented line:

    // .usingConcurrencyOf(1) // Uncommenting this makes the test pass.

Uncommenting that line makes the test pass without errors.

# Running the tests

    ./mvnw test