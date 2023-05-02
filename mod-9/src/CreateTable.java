
import java.net.URL;
import java.sql.*;

public class CreateTable {

    Connection con;
    Statement stmt;
    String db = "databasedb";
    String username = "student1";
    String password = "pass";
    String table = "address33";

    public CreateTable() {

        // connect to DB
        try{
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/databasedb?";
            
            // connect to database
            con = DriverManager.getConnection(url, username, password);
            stmt = con.createStatement();

            System.out.println(db + " Connected...");
        }
        catch(Exception e) {
            System.out.println("Error connection to database.");
            System.exit(0);
        }
        // drop table
        try{
            stmt.executeUpdate("DROP TABLE IF EXISTS " + table);
            System.out.println("Table Dropped: " + table);
        }
        catch(SQLException e) {
            System.out.println("Table does not exist.");
        }
        // create table if not exists
        try{
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + table + " (ID int PRIMARY KEY,LASTNAME varchar(40)," 
                            + "FIRSTNAME varchar(40), STREET varchar(40), CITY varchar(40),"  
                            + "STATE varchar(40), ZIP varchar(40))");
            System.out.println("Table Created: " + table);
        }
        catch(SQLException e) {
            System.out.println("Error creating table.");
        }

        //insert data into created table
        try{

            //test out prepared statement
            PreparedStatement prepStmt = con.prepareStatement("INSERT INTO " + table + " (ID, LASTNAME, FIRSTNAME, STREET, CITY, STATE, ZIP) "
                                                                + "VALUES (?, ?, ?, ?, ?, ?, ?)");

            // prepStmt.setInt(1, 55);
            // prepStmt.setString(2, "Larry");
            // prepStmt.setString(3, "Rich");
            // prepStmt.setString(4, "1111 Redwing Circle888");
            // prepStmt.setString(5, "Bellevue");
            // prepStmt.setString(6, "NE");
            // prepStmt.setString(7, "68123");
            // prepStmt.executeUpdate();

            // test out callable statement
            // Create stored procedure
            // String sql ="CREATE PROCEDURE insert_address33 (IN p_id INT, IN p_lastname VARCHAR(50), IN p_firstname VARCHAR(50), IN p_street VARCHAR(50), IN p_city VARCHAR(50), IN p_state VARCHAR(2), IN p_zip VARCHAR(10)) " +
            //             "BEGIN " +
            //             "    INSERT INTO address33 (ID, LASTNAME, FIRSTNAME, STREET, CITY, STATE, ZIP) " +
            //             "    VALUES (p_id, p_lastname, p_firstname, p_street, p_city, p_state, p_zip); " +
            //             "END";

            // stmt.executeUpdate(sql);
            CallableStatement callStmt = con.prepareCall("{call insert_address33(?, ?, ?, ?, ?, ?, ?)}");

            Object[] values = new Object[] {55,"Larry","Rich","1111 Redwing Circle888","Bellevue","NE","68123",
                                1,"Fine","Ruth","1111 Redwing Circle","Bellevue","NE","68123",
                                2,"Howard","Curly","1000 Galvin Road South","Bellevue","NE","68005",
                                3,"Howard","Will","2919 Redwing Circle","Bellevue","NE","68123",
                                4,"Wilson","Larry","1121 Redwing Circle","Bellevue","NE","68124",
                                5,"Johnson","George","1300 Galvin Road South","Bellevue","NE","68006",
                                6,"Long","Matthew","2419 Redwing Circle","Bellevue","NE","68127", 
                                44,"Tom","Matthew","1999 Redwing Circle","Bellevue","NE","68123"};

            // forloop to execute insert of values in array works with both callable statement and prepated tatement
            for (int i = 0; i < values.length; i += 7) {
                prepStmt.setInt(1, (int) values[i]);
                for (int j = 1; j <= 6; j++) {
                    prepStmt.setString(j + 1, (String) values[i + j]);
                }
                prepStmt.execute();
            }

            // callStmt.setInt(1, 1);
            // callStmt.setString(2, "Fine");
            // callStmt.setString(3, "Ruth");
            // callStmt.setString(4, "1111 Redwing Circle888");
            // callStmt.setString(5, "Bellevue");
            // callStmt.setString(6, "NE");
            // callStmt.setString(7, "68123");
            // callStmt.execute();

            // callStmt.setInt(1, 2);
            // callStmt.setString(2, "Howard");
            // callStmt.setString(3, "Curly");
            // callStmt.setString(4, "1111 Redwing Circle888");
            // callStmt.setString(5, "Bellevue");
            // callStmt.setString(6, "NE");
            // callStmt.setString(7, "68005");
            // callStmt.execute();

            // callStmt.setInt(1, 3);
            // callStmt.setString(2, "Wilson");
            // callStmt.setString(3, "Larry");
            // callStmt.setString(4, "1111 Redwing Circle888");
            // callStmt.setString(5, "Bellevue");
            // callStmt.setString(6, "NE");
            // callStmt.setString(7, "68124");
            // callStmt.execute();

            // callStmt.setInt(1, 3);
            // callStmt.setString(2, "Wilson");
            // callStmt.setString(3, "Larry");
            // callStmt.setString(4, "1111 Redwing Circle888");
            // callStmt.setString(5, "Bellevue");
            // callStmt.setString(6, "NE");
            // callStmt.setString(7, "68124");
            // callStmt.execute();

            // System.out.println(
            // stmt.executeUpdate("INSERT INTO " + table + " VALUES(55,"Larry","Rich","1111 Redwing Circle888","Bellevue","NE","68123")") + "row updated");

            // System.out.println(
            // stmt.executeUpdate("INSERT INTO " + table + " VALUES(1,"Fine","Ruth","1111 Redwing Circle","Bellevue","NE","68123")") + "row updated");
            // System.out.println(
            // stmt.executeUpdate("INSERT INTO " + table + " VALUES(2,"Howard","Curly","1000 Galvin Road South","Bellevue","NE","68005")") + "row updated");
            // System.out.println(
            // stmt.executeUpdate("INSERT INTO " + table + " VALUES(3,"Howard","Will","2919 Redwing Circle","Bellevue","NE","68123")") + "row updated");

            // System.out.println(
            // stmt.executeUpdate("INSERT INTO " + table + " VALUES(4,"Wilson","Larry","1121 Redwing Circle","Bellevue","NE","68124")") + "row updated");
            // System.out.println(
            // stmt.executeUpdate("INSERT INTO " + table + " VALUES(5,"Johnson","George","1300 Galvin Road South","Bellevue","NE","68006")") + "row updated");
            // System.out.println(
            // stmt.executeUpdate("INSERT INTO " + table + " VALUES(6,"Long","Matthew","2419 Redwing Circle","Bellevue","NE","68127")") + "row updated");

            // System.out.println(
            // stmt.executeUpdate("INSERT INTO " + table + " VALUES(44,"Tom","Matthew","1999 Redwing Circle","Bellevue","NE","68123")") + "row updated");

            stmt.executeUpdate("COMMIT");

            System.out.println("Data Inserted");
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
        // perform selection query to display address33 table data
        try{
            System.out.println("Performing selection query...");
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
            System.out.println("Received results");
            System.out.println("Displaying table: " + table);
            // Get the column names
            ResultSetMetaData metaData = rs.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                System.out.printf("%-20s\t",metaData.getColumnName(i));
            }
            System.out.println();

            // Print out the data
            while (rs.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {  
                    System.out.printf("%-20s\t", rs.getString(i));
                }
                System.out.println();
            }  

            rs.close();
        }
        catch (java.lang.Exception ex){
            ex.printStackTrace();
        }

        try{
            stmt.close();
            con.close();
            System.out.println("Database connections closed");
        }
        catch(SQLException e){
    
        System.out.println("Connection close failed");
        }
    }

    public static void main (String[] args) {
        new CreateTable();
    }
}