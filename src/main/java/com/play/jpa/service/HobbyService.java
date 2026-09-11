/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.service;

import com.play.jpa.entity.Hobby;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author window10
 */
public class HobbyService {
    private final EntityManager em;

    public HobbyService(EntityManager em) {
        this.em = em;
    }
    
    public void registerHobby(String hobbyName , int point) {
        Hobby isH = pickHobby(hobbyName);
        
        if(isH == null){
            Hobby h = new Hobby();
            h.setHobbyName(hobbyName);
            h.setPoint(point);
            em.persist(h);
        }else{
            isH.setPoint(point);
        }
    }
    
     public List<Hobby> listHobby() {
        String jpql = "select h from Hobby h";
        List<Hobby> isList = em.createQuery(jpql,Hobby.class)
                .getResultList();
               
        return isList;
    }
    
    public Hobby pickHobby(String hobbyName) {
        String jpql = "select h from Hobby h where h.hobbyName = :name";
        List<Hobby> isList = em.createQuery(jpql,Hobby.class)
                .setParameter("name", hobbyName)
                .getResultList();
        
        if(isList.isEmpty()){
            return null;
        }else{
            return isList.get(0);
        }
        
    }
    public Hobby pickHobby(int id) {
        String jpql = "select h from Hobby h where h.id = :id";
        List<Hobby> isList = em.createQuery(jpql,Hobby.class)
                .setParameter("id", id)
                .getResultList();
        
        if(isList.isEmpty()){
            return null;
        }else{
            return isList.get(0);
        }
        
    }
}
