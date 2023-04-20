package org.acme;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TestRepository implements PanacheRepositoryBase<TestEntity, String> {

    public Uni<Void> save(TestEntity entity) {
        return getSession().flatMap(session ->
                session.merge(entity).replaceWithVoid());
    }

}
