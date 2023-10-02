package com.keduit.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class}) // Auditing 적용을 위해서
@MappedSuperclass // 공통매핑 정보가 필요한 경우 사용, 부모 클래스를 상속 받는 자식 클래스에 매핑 정보 제공
@Getter @Setter
public abstract class BaseTimeEntity {

    @CreatedDate // 생성시 자동 시간 저장
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate // 수정시 자동 시간 저장
    private LocalDateTime updateTime;
}
