package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectTests {

    @Test
    public void test1(){

        int v1 = 10;
        int v2 = 20;

        Assertions.assertEquals(v1,v2);
    }

    @Test
    public void testConnection() throws Exception { //DB 연결 테스트

        Class.forName("org.mariadb.jdbc.Driver"); //JDBC 드라이버 클래스를 메모리상으로 로딩

        Connection connection = DriverManager.getConnection( //Connection 객체 생성 & 대상 DB에 연결 시도
                "jdbc:mariadb://localhost:3306/webdb",
                "webuser",
                "webuser");

        Assertions.assertNotNull(connection); //DB 정상 연결 시 Connection 객체는 NULL이 아님을 확신한다.

        connection.close(); //DB 연결 종료
    }

    @Test
    public void testHikariCP() throws Exception { //Connection 생성을 위한 HikariCP 테스트

        HikariConfig config = new HikariConfig(); //HikariConfig 타입의 객체를 생성한다.(CP를 생성하는데 필요한 정보를 가지고 있음)
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/webdb");
        config.setUsername("webuser");
        config.setPassword("webuser");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource ds = new HikariDataSource(config); //HikariDataSource 객체를 생성해서
        Connection connection = ds.getConnection(); // getConnection()으로 Connection 객체를 얻어서 사용한다.

        System.out.println(connection);

        connection.close();

    }
}
