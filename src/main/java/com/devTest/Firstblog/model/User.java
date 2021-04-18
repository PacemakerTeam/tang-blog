package com.devTest.Firstblog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder//빌더 패턴
@Entity // User 클래스가 MySQL에 테이블이 생성 된다.
//@DynamicInsert // insert시에 mull인 필드를제외시켜준다.
public class User {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략 자동
    private int id; // 시퀀스, auto-increment

    @Column(nullable = false, length = 30)
    private String username; // 아이디

    @Column(nullable = false, length = 100) // 123456 => 해쉬 ( 비밀번호 암호화 )
    private String password;

    @Column(nullable=false, length = 50)
    private String email;

//    @ColumnDefault("user")
    //DB에 RoleType이란 게 없어. 넣어줘야 함. @EEnumerated
    @Enumerated(EnumType.STRING)
    private RoleType role; // Enum을 쓰는 게 좋다. // admin, user, manager @Enumerated

    @CreationTimestamp // 시간이 자동 입력
    private Timestamp createDate;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
