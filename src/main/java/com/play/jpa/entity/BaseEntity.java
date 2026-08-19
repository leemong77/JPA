package com.play.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

/**
 * 공통 속성(생성일시, 생성자)을 정의하는 매핑 전용 상위 클래스.
 * 이 클래스 자체는 테이블로 생성되지 않고, 상속받는 자식 엔티티의
 * 테이블에 컬럼으로 합쳐진다.
 */
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "input_date")
    private LocalDateTime inputDate;

    @Column(name = "input_id")
    private String inputId;

    /**
     * 엔티티가 처음 저장(persist)되기 직전에 자동으로 호출되어
     * 기본값을 채워준다.
     */
    @PrePersist
    protected void prePersist() {
        if (this.inputDate == null) {
            this.inputDate = LocalDateTime.now();
        }
        if (this.inputId == null) {
            this.inputId = "anonymous";
        }
    }

    public LocalDateTime getInputDate() { return inputDate; }
    public void setInputDate(LocalDateTime inputDate) { this.inputDate = inputDate; }
    public String getInputId() { return inputId; }
    public void setInputId(String inputId) { this.inputId = inputId; }
}