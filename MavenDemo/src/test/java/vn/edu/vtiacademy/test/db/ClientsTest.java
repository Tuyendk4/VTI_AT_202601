package vn.edu.vtiacademy.test.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import vn.edu.vtiacademy.common.database.DBKeywords;
import vn.edu.vtiacademy.common.database.DBType;

public class ClientsTest extends DBBaseTest{


//  private static final String SERVER = "127.0.0.1";
//  private static final String PORT = "3306";
//  private static final String DB_NAME = "sql_invoicing";
//  private static final String USER = "root";
//  private static final String PASS = "root@123";
//
//  private static DBKeywords dbKeywords;
//  private static Connection connection;
//
//  //3A = AAA = Arrange, Act, Assert
//  // Arrange = Pre-condition
//  // Act = Steps
//  // Assert = Assertion - Post-condition
//  @BeforeAll
//  public static void setUp() {
//    //Arrange
//    dbKeywords = new DBKeywords();
//    connection = dbKeywords.createConnection(DBType.mysql, SERVER, PORT, DB_NAME, USER, PASS);
//  }

  @Test
  @DisplayName("DB001: Verify client names from database")
  public void DB001_verifyClientNames() {
//    Connection connection = dbKeywords.createConnection(DBType.mysql, SERVER, PORT, DB_NAME, USER, PASS);

    //Act = Steps
    String query = "SELECT name FROM clients";

    ResultSet resultSet = dbKeywords.executeQuery(connection, query);
    List<String> clientNames = dbKeywords.getCellValues(resultSet, "name");

    //Assert = Assertion - Post-condition
    assertNotNull(clientNames);
    assertTrue(clientNames.size() > 0);

//    dbKeywords.closeConnection(connection);
  }

  @Test
  @DisplayName("DB002: Insert a new client into clients table")
  public void DB002_insertNewClient() {
//    Connection connection = dbKeywords.createConnection(DBType.mysql, SERVER, PORT, DB_NAME, USER, PASS);
    //Arrange
    String insertClientQuery = "INSERT INTO `clients` VALUES (6,'John Doe','0863 Farmco Road','Portland','OR','971-888-9129')";
    dbKeywords.executeUpdate(connection, insertClientQuery);
    String getClientQuery = "SELECT * FROM clients WHERE client_id = 6";
    ResultSet resultSet = dbKeywords.executeQuery(connection, getClientQuery);
    assertEquals("John Doe", dbKeywords.getStringCellValue(resultSet, 1, "name"));
    assertEquals("0863 Farmco Road", dbKeywords.getStringCellValue(resultSet, 1, "address"));
    assertEquals("Portland", dbKeywords.getStringCellValue(resultSet, 1, "city"));
    assertEquals("OR", dbKeywords.getStringCellValue(resultSet, 1, "state"));
    assertEquals("971-888-9129", dbKeywords.getStringCellValue(resultSet, 1, "phone"));
//    dbKeywords.closeConnection(connection);
  }

//  @AfterAll
//  public static void tearDown() {
//    dbKeywords.closeConnection(connection);
//  }

  //Arrange - Act - Assert - Arrange - Act - Assert - Act - Assert
}
