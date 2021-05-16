package com.devTest.Firstblog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

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

    //@ColumnDefault("0") //문자는 '' 붙여주고, 숫자는 안 부텽줘도 됨
    private int count; // 조회수

    @ManyToOne(fetch = FetchType.EAGER) //Many==board, user=one // 한개만 가져와 기본적으로 EAGER
    @JoinColumn(name="userId")
    private User user; // DB는 오브젝트를 저장할 수 없다. Fk, 자바는 오브젝트를 저장할 수 있다.

    //List로 가져오니까..(많잖아) OneToMany는 LAZY가 기본 전략.
    //mappedBy 연관관계의 주인이 아니다 (난 FK가 아니에요) DB에 만들지 마세요
    @OneToMany(mappedBy = "board", fetch=FetchType.EAGER)
    @JsonIgnoreProperties({"board","user"})
    @OrderBy("id desc")
    private List<Reply> replys;

    @CreationTimestamp // 시간이 자동 입력
    private Timestamp createDate;
}



