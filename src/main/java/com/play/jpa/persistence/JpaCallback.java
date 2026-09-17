package com.play.jpa.persistence;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import jakarta.persistence.EntityManager;

@FunctionalInterface
public interface JpaCallback<T> {
    // EntityManager를 인자로 받아 비즈니스 로직을 수행하고 결과를 반환하는 메서드
    T doInJpa(EntityManager em);
}
