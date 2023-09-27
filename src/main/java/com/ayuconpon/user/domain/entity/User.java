package com.ayuconpon.user.domain.entity;

import com.ayuconpon.common.BaseEntity;
import com.ayuconpon.user.domain.value.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;

    public User(String name, Status status) {
        this.name = name;
        this.status = status;
    }

}
