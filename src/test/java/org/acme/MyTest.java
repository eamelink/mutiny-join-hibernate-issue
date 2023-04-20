package org.acme;

import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.UniAsserter;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class MyTest {

    @Inject
    TestRepository repository;

    TestEntity entity1 = createTestEntity("1", "Computer 1");
    TestEntity entity2 = createTestEntity("2", "Computer 2");
    TestEntity entity3 = createTestEntity("3", "Computer 3");
    TestEntity entity4 = createTestEntity("4", "Computer 4");

    @Test
    @TestReactiveTransaction
    public void test(UniAsserter uniAsserter) {
        uniAsserter
                .execute(this::insertTestData)
                .assertThat(
                        () -> repository.findById("1"),
                        found -> assertThat(found.id).isEqualTo("1"));
    }

    private Uni<Void> insertTestData() {
        return Uni.join().all(
                        repository.save(entity1),
                        repository.save(entity2),
                        repository.save(entity3),
                        repository.save(entity4))
                // .usingConcurrencyOf(1) // Uncommenting this makes the test pass.
                .andFailFast()
                .replaceWithVoid();
    }


    public static TestEntity createTestEntity(String id, String name) {
        TestEntity ce = new TestEntity();
        ce.id = id;
        ce.name = name;
        return ce;
    }
}
