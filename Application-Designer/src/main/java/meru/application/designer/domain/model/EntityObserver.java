package meru.application.designer.domain.model;

public interface EntityObserver {

    void observe(EntityInterest paramEntityInterest,
                 Entity paramEntity);
}