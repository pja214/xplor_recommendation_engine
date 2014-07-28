/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.xplor_recommendation_engine;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author patrickadduci
 */
public class Xplor {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws SQLException, IOException {
        FileConverter fc = FileConverter.getFileConverter();
        int[] columnSpecs = new int[2];
        columnSpecs[0] = 2;
        columnSpecs[1] = 2;
        File csv = fc.convertToCSV("xplor_development", "blog_profile_maps", columnSpecs);
        System.out.println("We are here.");
    }
    
}
