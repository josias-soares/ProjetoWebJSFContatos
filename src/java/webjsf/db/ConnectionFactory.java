package webjsf.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {
    
    public Connection getConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            return DriverManager.getConnection(
                    "jdbc:derby://localhost:1527/agenda", "root", "root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    
    /*public Connection getConnectionFactory(){
        try {
            return DriverManager.getConnection("jdbc:derby://localhost:1527/contatos", "root", "123");   
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
}
