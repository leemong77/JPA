/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.service;

import com.play.jpa.entity.Hobby;
import com.play.jpa.util.Print;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author window10
 */
public class HobbyService extends BaseService{

    public HobbyService(EntityManager em) {
        super(em);
    }
    
    public void registerHobby(String hobbyName , int point) {
        Hobby isH = pickHobby(hobbyName);
        
        if(isH == null){
            Hobby h = new Hobby();
            h.setHobbyName(hobbyName);
            h.setPoint(point);
            query.persist(h);
        }else{
            isH.setPoint(point);
        }
    }
    
     public List<Hobby> listHobby() {
        return query.selectList("select h from Hobby h", Hobby.class);
    }
    
    public void showHobbies(){
        query.selectList("select h from Hobby h", Hobby.class).forEach(h->{
            Print.out( h.getHobbyName()+"["+h.getHobbyId() +"]");
        });
    }
    
    public Hobby pickHobby(String hobbyName) {
        String jpql = "select h from Hobby h where h.hobbyName = :name";
        List<Hobby> isList = query.selectList(jpql,Hobby.class,"name",hobbyName);
        if(isList.isEmpty()){
            return null;
        }else{
            return isList.get(0);
        }
        
    }
    public Hobby pickHobby(int id) {
        String jpql = "select h from Hobby h where h.id = :id";
        List<Hobby> isList = query.selectList(jpql,Hobby.class,"id",id);
        
        if(isList.isEmpty()){
            return null;
        }else{
            return isList.get(0);
        }
        
    }
}
