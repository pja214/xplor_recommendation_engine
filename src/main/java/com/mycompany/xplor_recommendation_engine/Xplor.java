/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.xplor_recommendation_engine;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

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
    public static void main(String[] args) throws SQLException, IOException, TasteException {
        
        FileConverter fc = FileConverter.getFileConverter();
        int[] columnSpecs = new int[2];
        columnSpecs[0] = 2;
        columnSpecs[1] = 2;
        fc.convertToCSV("xplor_development", "blog_profile_maps", columnSpecs);
        
        // Calibrate recommender for csv file
        DataModel model = new FileDataModel(new File("blog_profile_maps.csv"));
        UserSimilarity similarity = new TanimotoCoefficientSimilarity(model);
        UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
        Recommender recommender = new GenericBooleanPrefUserBasedRecommender(model, neighborhood, similarity);
        Recommender cachingRecommender = new CachingRecommender(recommender);
        
        // Establish JDBC connection
        
        
        for (int i = 1; i <= model.getNumUsers(); i++) {
            List <RecommendedItem> recommendations = cachingRecommender.recommend(i, 10);
            for (RecommendedItem recommendation: recommendations) {
                // Store recommendations in recommendations table
            }
        }
        
        // Store recommendations in database
    }
    
}
