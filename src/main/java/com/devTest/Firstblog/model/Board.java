package com.devTest.Firstblog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) // Auto-increment
    private int id;

    @Column(nullable=false, length =100)
    private String title;

    @Lob // 대용량
    private String content; //(섬머노트 라이브러리) <html>태그가 섞여서 디자인이 됨

    @ColumnDefault("0") //문자는 '' 붙여주고, 숫자는 안 부텽줘도 됨
    private int count; // 조회수

    @ManyToOne //Many==board, user=one
    @JoinColumn(name="userId")
    private User user; // DB는 오브젝트를 저장할 수 없다. Fk, 자바는 오브젝트를 저장할 수 있다.

    @CreationTimestamp // 시간이 자동 입력
    private Timestamp createDate;
}



