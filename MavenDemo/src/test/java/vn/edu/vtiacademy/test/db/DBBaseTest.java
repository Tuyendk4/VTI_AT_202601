package vn.edu.vtiacademy.test.db;

import java.sql.Connection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import vn.edu.vtiacademy.common.database.DBKeywords;
import vn.edu.vtiacademy.common.database.DBType;

public class DBBaseTest {

  private static final String SERVER = "127.0.0.1";
  private static final String PORT = "3306";
  private static final String DB_NAME = "sql_invoicing";
  private static final String USER = "root";
  private static final String PASS = "root@123";

  protected static DBKeywords dbKeywords;
  protected static Connection connection;

  //3A = AAA = Arrange, Act, Assert
  // Arrange = Pre-condition
  // Act = Steps
  // Assert = Assertion - Post-condition
  @BeforeAll
  public static void setUp() {
    //Arrange
    dbKeywords = new DBKeywords();
    connection = dbKeywords.createConnection(DBType.mysql, SERVER, PORT, DB_NAME, USER, PASS);
  }

  @AfterAll
  public static void tearDown() {
    dbKeywords.closeConnection(connection);
  }


}
