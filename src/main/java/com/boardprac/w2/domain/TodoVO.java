package com.boardprac.w2.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TodoVO { //DB에 있는 테이블의 데이터를 Java 객체로 처리하기 위한 VO 클래스(읽기 전용)

    private Long tno;

    private String title;

    private LocalDate dueDate;

    private boolean finished;
}
