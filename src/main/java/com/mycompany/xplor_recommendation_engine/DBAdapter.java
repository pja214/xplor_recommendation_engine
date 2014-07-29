/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.xplor_recommendation_engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *-
 * @author patrickadduci
 */
public class DBAdapter {
    
    Connection dbConnection;
    
    public DBAdapter(String databaseName) {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
        }
        
        try {
            String url = "jdbc:postgresql:" + databaseName;
            System.out.println(url);
            dbConnection = DriverManager.getConnection(url);
        }
        catch (SQLException e) {
            System.out.println("Connection failed.");
        }
    }
    
    public File convertToCSV(String tableName, int... columns) throws SQLException, FileNotFoundException, IOException {
        ResultSet rs = openSQLTable(tableName);
        String csv = "";
        ResultSetMetaData rsmd = rs.getMetaData();
        int numColumns;
        if (columns == null) {
            numColumns = rsmd.getColumnCount();
        }
        else {
            numColumns = columns[1];
        }
        while (rs.next()) {
            for (int i = columns[0]; i < columns[0] + numColumns; i++) {
                Reader colChars = rs.getCharacterStream(i);
                int ascii;
                while ((ascii = colChars.read()) != -1)
                    csv += (char)ascii;
                if (i < columns[0] + numColumns - 1)
                    csv += ",";
                else {
                    csv += "\n";
                }
            }
        }
        File csvFile = new File(tableName + ".csv");
        PrintWriter out = new PrintWriter(csvFile);
        out.println(csv);
        out.flush();
        return csvFile;
    }
    
    private ResultSet openSQLTable(String tableName) throws SQLException {
        String sql = "SELECT * FROM " + tableName;
        PreparedStatement pstmt;
        pstmt = dbConnection.prepareStatement(sql);
        return pstmt.executeQuery();       
    }
    
}
