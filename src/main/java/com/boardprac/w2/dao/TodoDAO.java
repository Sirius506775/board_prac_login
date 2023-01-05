package com.boardprac.w2.dao;

import com.boardprac.w2.domain.TodoVO;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {
    public String getTime(){ //try-with-resource를 이용한 방식

        String now = null;

        try(Connection connection = ConnectionUtil.INSTANCE.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select now()");
            ResultSet resultSet = preparedStatement.executeQuery();
        ) {

            resultSet.next();

            now = resultSet.getString(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        return now;
    }

    public String getTime2() throws Exception { //Lombok의 @CleanUp 어노테이션을 사용한 방식이다. 위의 방식보다 더 간결하다.
        // @CleanUp이 추가된 변수는 해당 메소드가 끝날 때 close()가 호출되는 것을 보장한다.
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement("select now()");
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        String now = resultSet.getString(1);

        return now;
    } // 단점이 있다면 lombok 라이브러리에 종속적인 코드가 된다는 점이지만 최소한의 코드로 close()가 보장된 코드를 작성할 수 있다는 장점이 있다.

    public void insert(TodoVO vo) throws Exception { //TodoDAO의 등록기능
        String sql = "insert into tbl_todo (title, dueDate, finished) values (?, ?, ?)";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, vo.getTitle());
        preparedStatement.setDate(2, Date.valueOf(vo.getDueDate()));
        preparedStatement.setBoolean(3, vo.isFinished());

        preparedStatement.executeUpdate();

    }

    public List<TodoVO> selectAll() throws Exception  { //TodoDAO의 목록 기능

        String sql = "select * from tbl_todo";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        //resultSet으로 각 행을 이동하면서 각 행의 데이터를 TodoVO로 변환한다. next()의 결과는 이동할 행이 있으면 true 아니면 false



        List<TodoVO> list = new ArrayList<>();

        while(resultSet.next()) {
            TodoVO vo = TodoVO.builder() //빌더 패턴을 이용해서 TodoVO 객체 생성
                    .tno(resultSet.getLong("tno"))
                    .title(resultSet.getString("title"))
                    .dueDate( resultSet.getDate("dueDate").toLocalDate())
                    .finished(resultSet.getBoolean("finished"))
                    .build();

            list.add(vo);
        }

        return list;
    }

    public TodoVO selectOne(Long tno) throws Exception { //TodoDAO의 조회 기능

        String sql = "select * from tbl_todo where tno = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setLong(1, tno);

        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        TodoVO vo = TodoVO.builder()
                .tno(resultSet.getLong("tno"))
                .title(resultSet.getString("title"))
                .dueDate( resultSet.getDate("dueDate").toLocalDate())
                .finished(resultSet.getBoolean("finished"))
                .build();

        return vo;
    }


    public void deleteOne(Long tno) throws Exception {

        String sql = "delete from tbl_todo where tno = ?";

        @Cleanup Connection    connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setLong(1, tno);

        preparedStatement.executeUpdate();
    }

    public void updateOne(TodoVO todoVO)throws Exception{

        String sql = "update tbl_todo set title =?, dueDate = ?, finished = ? where tno =?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, todoVO.getTitle());
        preparedStatement.setDate(2, Date.valueOf(todoVO.getDueDate()));
        preparedStatement.setBoolean(3, todoVO.isFinished());
        preparedStatement.setLong(4, todoVO.getTno());

        preparedStatement.executeUpdate();
    }
}
