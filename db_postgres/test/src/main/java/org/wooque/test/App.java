package org.wooque.test;

import java.sql.*;

public class App 
{
    private static final class Unit implements Runnable
    {
        public void run() {
            try
            {
                Connection conn = DriverManager.getConnection("jdbc:postgresql:bla");
                
                String insert_t = "INSERT INTO lang (name, dsc, num, percent)" + 
                                  "VALUES (?, ?, ?, ?)";

                PreparedStatement pres = conn.prepareStatement(insert_t);
                
                for (int i = 0; i < 100000; i++)
                {
                    pres.setString(1, "Java" + i);
                    pres.setString(2, "Bla" + i);
                    pres.setInt(3, i);
                    pres.setDouble(4, i*0.27);
                    pres.executeUpdate();
                }
                
                pres.close();
                conn.close();     
            } 
            catch (Exception e) {
                System.out.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }
    
    private static final class Unit2 extends Thread
    {
        private int index;
        
        Unit2(int index) {
            this.index = index;
        }
        
        public void run() {
            try
            {
                Connection conn = DriverManager.getConnection("jdbc:postgresql:bla");
                
                String insert_t = "UPDATE lang SET name=?, dsc=?, num=?, percent=?" +
                                  "WHERE id=?";

                PreparedStatement pres = conn.prepareStatement(insert_t);
                
                for (int i = 0; i < 100000; i++)
                {
                    pres.setString(1, "Java" + (100000-i));
                    pres.setString(2, "Bla" + (100000-i));
                    pres.setInt(3, (100000-i));
                    pres.setDouble(4, (100000-i)*0.27);
                    pres.setInt(5, (index+i));
                    pres.executeUpdate();
                }
                
                pres.close();
                conn.close();
            } 
            catch (Exception e) {
                System.out.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }
    
    private static final class Unit3 extends Thread
    {
        private int index;
        
        Unit3(int index) {
            this.index = index;
        }
        
        public void run() {
            try
            {
                Connection conn = DriverManager.getConnection("jdbc:postgresql:bla");
                
                String insert_t = "DELETE FROM lang WHERE id=?" +
                                  "RETURNING *";

                PreparedStatement pres = conn.prepareStatement(insert_t);
                
                for (int i = 0; i < 100000; i++)
                {
                    pres.setInt(1, index+i);
                    ResultSet rs = pres.executeQuery();
                    while(rs.next())
                    {
                        String name = rs.getString(2);
                        String dsc = rs.getString(3);
                        int num = rs.getInt(4);
                        double percent = rs.getDouble(5);
                        System.out.println(name + "|" + dsc + "|" + num + "|" + percent);
                    }
                }
                
                pres.close();
                conn.close();
            } 
            catch (Exception e) {
                System.out.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }

    public static void main( String[] args ) throws ClassNotFoundException, InterruptedException
    {
        Class.forName("org.postgresql.Driver");
        
        Thread[] threads = new Thread[6];
        
        for(int i = 0; i < 2; i++) {
            threads[i] = new Thread(new Unit());
        }
        
        for(int i = 0; i < 2; i++) {
            threads[2+i] = new Unit2(800000 + i*100000);
        }
        
        for(int i = 0; i < 2; i++) {
            threads[4+i] = new Unit3(600000 + i*100000);
        }
        
        long start = System.currentTimeMillis();
        
        for(Thread t: threads) {
            if (t != null) 
                t.start();
        }
        
        for(Thread t: threads) {
            if (t != null)
                t.join();
        }
        
        long total = System.currentTimeMillis() - start;
        System.out.println("Executing: " + total/1000.0 + " s");
    }
}
