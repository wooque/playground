package org.wooque.test;

import java.io.File;
import java.sql.*;

public class App 
{
    public static void main( String[] args )
    {
        try
        {
            File db = new File("./test.db");
            db.delete();
            
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            conn.setAutoCommit(false);
            
            Statement stmt = conn.createStatement();
            String create = "CREATE TABLE lang" + 
                            "(id integer primary key not null," +
                            "name text," +
                            "desc text," +
                            "num integer," +
                            "percent real)";
            stmt.executeUpdate(create);
            stmt.close();
            
            String insert_t = "INSERT INTO lang (name, desc, num, percent)" + 
                              "VALUES (?, ?, ?, ?)";

            PreparedStatement pres = conn.prepareStatement(insert_t);
            long start = System.currentTimeMillis();
            
            for (int i = 0; i < 1000000; i++)
            {
                pres.setString(1, "Java" + i);
                pres.setString(2, "Bla" + i);
                pres.setInt(3, i);
                pres.setDouble(4, i*0.27);
                pres.executeUpdate();
            }
            
            pres.close();
            conn.commit();
            conn.close();
            
            long total = System.currentTimeMillis() - start;
            System.out.println("Executing: " + total/1000.0 + " s");
        } 
        catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
