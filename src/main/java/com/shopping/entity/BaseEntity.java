package com.shopping.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

// Entity에 대하여 AuditingEntityListener를 이용하여 리스닝할 겁니다.
@EntityListeners(value={AuditingEntityListener.class})
@MappedSuperclass
@Getter @Setter
public abstract class BaseEntity extends BaseTimeEntity{
    @CreatedBy // 엔터티 생성시 사용자 id를 기록할께요
    @Column(updatable = false) // Entity 수정시 같이 갱신하지 않을 겁니다.
    private String createdBy ; // 생성자

    @LastModifiedBy // 엔터티 수정시 수정자 id를 기록할께요
    private String modifiedBy ; // 수정자
}
