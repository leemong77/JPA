/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.play.jpa.service;

import com.play.jpa.entity.Hobby;
import com.play.jpa.entity.HobbyOfMember;
import com.play.jpa.entity.Ledger;
import com.play.jpa.entity.Member;
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
    
    public void enjoyHobby(Hobby h, Member m){
        boolean isHave = false;
        
        for(HobbyOfMember hom:m.getHobbyList()){
            if(hom.getHobby().getHobbyId() == h.getHobbyId()){
                isHave = true;
                break;
            }
        }
        
        if(isHave){
            try {
                m.usePoint(h.getPoint());

                Ledger ledger = new Ledger();
                ledger.setMember(m);
                ledger.setHobby(h);
                ledger.setPoint(h.getPoint());
                em.persist(ledger);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Print.out("That's not your Hobby.");  
        }
    }
}
