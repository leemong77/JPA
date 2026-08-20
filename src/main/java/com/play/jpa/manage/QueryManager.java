package com.play.jpa.manage;

import com.play.jpa.entity.Hobby;
import com.play.jpa.entity.HobbyOfMember;
import com.play.jpa.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QueryManager {

    private final EntityManager em;

    public QueryManager(EntityManager em) {
        this.em = em;
    }

    /**
     * memberId로 Member - HobbyOfMember - Hobby를 JPQL로 조인 조회한 뒤
     * 각 행을 Map<컬럼명, 값> 형태로 변환해서 반환한다.
     */
    public List<Map<String, Object>> toOneRows(Long memberId) {
        String jpql =
            "SELECT m, o, h " +
            "FROM HobbyOfMember o " +
            "JOIN o.member m " +
            "JOIN o.hobby h " +
            "WHERE m.id = :memberId";

        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        query.setParameter("memberId", memberId);

        List<Object[]> results = query.getResultList();

        List<Map<String, Object>> rows = new ArrayList<>();

        for (Object[] result : results) {
            Member member = (Member) result[0];
            HobbyOfMember hom = (HobbyOfMember) result[1];
            Hobby hobby = (Hobby) result[2];

            Map<String, Object> row = new LinkedHashMap<>();

            // member 테이블 컬럼
            row.put("m_id", member.getId());
            row.put("m_name", member.getName());

            // hobby_of_member 테이블 컬럼
            row.put("o_id", hom.getId());
            row.put("o_member_id", member.getId());
            row.put("o_hobby_id", hobby.getHobbyId());
            row.put("o_input_date", hom.getInputDate());
            row.put("o_input_id", hom.getInputId());

            // hobby 테이블 컬럼
            row.put("h_hobby_id", hobby.getHobbyId());
            row.put("h_hobby_name", hobby.getHobbyName());

            rows.add(row);
        }

        return rows;
    }
}