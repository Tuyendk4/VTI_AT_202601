package vn.edu.vtiacademy.common.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBKeywords {

  private static final Logger LOGGER = LoggerFactory.getLogger(DBKeywords.class);

  public Connection createConnection(DBType dbType, String server, String port, String databaseName, String user, String password) {
    String url = getConnectionUrl(dbType, server, port, databaseName);
    Connection connection = null;
    try {
      LOGGER.info("Connecting to {}...", url);
      connection = DriverManager.getConnection(url, user, password);
      if (connection != null) {
        LOGGER.info("Connection successful");
      }
    } catch (SQLException e) {
      LOGGER.error("Failed to create connection to url {}. Root cause: {}", url, e.getMessage());
    }
    return connection;
  }

  private String getConnectionUrl(DBType dbType, String server, String port, String databaseName) {
    return switch (dbType) {
      case mysql -> "jdbc:mysql://" + server + ":" + port + "/" + databaseName;
      case postgresql -> "jdbc:postgresql://" + server + ":" + port + "/" + databaseName;
      case oracle -> "jdbc:oracle:thin:@" + server + ":" + port + "/" + databaseName;
      case sqlserver -> "jdbc:sqlserver://" + server + ":" + port + "/" + databaseName;
      default -> "";
    };
  }

  public void closeConnection(Connection connection) {
    try {
      LOGGER.info("Closing the connection...");
      if (connection != null && !connection.isClosed()) {
        connection.close();
        LOGGER.info("Connection closed successfully");
      } else {
        LOGGER.warn("Connection is already closed or null");
      }
    } catch (SQLException e) {
      LOGGER.error("Failed to close connection. Root cause: {}", e.getMessage());
    }
  }

  public ResultSet executeQuery(Connection connection, String sqlQuery) {
    ResultSet resultSet = null;
    try {
      LOGGER.info("Executing query: {}", sqlQuery);
      Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
      resultSet = statement.executeQuery(sqlQuery);
      LOGGER.info("Query executed successfully");
    } catch (SQLException e) {
      LOGGER.error("Failed to execute query. Root cause: {}", e.getMessage());
    }
    return resultSet;
  }

  public void executeUpdate(Connection connection, String sqlQuery) {
    try {
      LOGGER.info("Executing query: {}", sqlQuery);
      Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
      statement.executeUpdate(sqlQuery);
      LOGGER.info("Query executed successfully");
    } catch (SQLException e) {
      LOGGER.error("Failed to execute query. Root cause: {}", e.getMessage());
    }
  }

  public List<String> getCellValues(ResultSet resultSet, String columnName) {
    List<String> values = new ArrayList<>();
    LOGGER.info("Retrieving cell values from column: {}", columnName);
    try {
      resultSet.beforeFirst();
      while (resultSet.next()) {
        values.add(resultSet.getString(columnName));
      }
      LOGGER.info("Got all cell values of column name '{}' in result set", columnName);
    } catch (Exception e) {
      LOGGER.error("Failed to retrieve cell values of column name {}. Root cause: {}", columnName, e.getMessage());
    }
    return values;
  }

  public List<String> getCellValues(ResultSet resultSet, int columnIndex) {
    List<String> values = new ArrayList<>();
    LOGGER.info("Retrieving cell values from column: {}", columnIndex);
    try {
      resultSet.beforeFirst();
      while (resultSet.next()) {
        values.add(resultSet.getString(columnIndex));
      }
      LOGGER.info("Got all cell values of column index '{}' in result set", columnIndex);
    } catch (Exception e) {
      LOGGER.error("Failed to retrieve cell values of column index {}. Root cause: {}", columnIndex, e.getMessage());
    }
    return values;
  }

  public String getStringCellValue(ResultSet resultSet, int rowIndex, String columnName) {
    String cellValue = null;
    try {
      LOGGER.info("Retrieving string cell value at row {} and column name '{}'", rowIndex, columnName);
      resultSet.absolute(rowIndex);
      cellValue = resultSet.getString(columnName);
      LOGGER.info("Got string cell value at row {} and column name '{}'", rowIndex, columnName);
    } catch (SQLException e) {
      LOGGER.error("Failed to retrieve string cell value at row {} and column name {}. Root cause: {}", rowIndex, columnName, e.getMessage());
    }
    return cellValue;
  }

  public String getStringCellValue(ResultSet resultSet, int rowIndex, int columnIndex) {
    String cellValue = null;
    try {
      LOGGER.info("Retrieving string cell value at row {} and column index '{}'", rowIndex, columnIndex);
      resultSet.absolute(rowIndex);
      cellValue = resultSet.getString(columnIndex);
      LOGGER.info("Got string cell value at row {} and column index '{}'", rowIndex, columnIndex);
    } catch (SQLException e) {
      LOGGER.error("Failed to retrieve string cell value at row {} and column index {}. Root cause: {}", rowIndex, columnIndex, e.getMessage());
    }
    return cellValue;
  }

}
