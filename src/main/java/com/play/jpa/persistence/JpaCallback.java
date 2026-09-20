package com.play.jpa.persistence;

import jakarta.persistence.EntityManager;

@FunctionalInterface
public interface JpaCallback<T> {
    // EntityManager를 인자로 받아 비즈니스 로직을 수행하고 결과를 반환하는 메서드
    T useEm(EntityManager em);
}
