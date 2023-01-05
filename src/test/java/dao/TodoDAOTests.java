package dao;

import com.boardprac.w2.dao.TodoDAO;
import com.boardprac.w2.domain.TodoVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class TodoDAOTests {
    private TodoDAO todoDAO;

    @BeforeEach
    public void ready(){
        todoDAO = new TodoDAO(); //모든 테스트 전에 TodoDAO 타입의 객체를 생성한다.
    }

    @Test
    public void testTime() throws Exception{ //TodoDAO에 작성한 get-Time이 정상작동하는지 확인

        System.out.println(todoDAO.getTime() );

    }

    @Test
    public void testInsert() throws Exception { //TodoDAO에 작성한 insert() 메소드를 테스트
        TodoVO todoVO = TodoVO.builder()
                .title("Sample Title...")
                .dueDate(LocalDate.of(2021,12,31))
                .build();

        todoDAO.insert(todoVO); //TodoVO 객체를 DB에 추가한다.
    }

    @Test
    public void testList() throws Exception { //selectALL 메소드 테스트

        List<TodoVO> list = todoDAO.selectAll();

        list.forEach(vo -> System.out.println(vo));

    }

    @Test
    public void testSelectOne() throws Exception {

        Long tno = 1L; //반드시 존재하는 번호를 이용

        TodoVO vo = todoDAO.selectOne(tno);

        System.out.println(vo);
    }

    @Test
    public void testUpdateOne() throws Exception {
        TodoVO todoVO = TodoVO.builder()
                .tno(1L)
                .title("Sample Title...")
                .dueDate(LocalDate.of(2021,12,31))
                .finished(true)
                .build();

        todoDAO.updateOne(todoVO);

    }
}
