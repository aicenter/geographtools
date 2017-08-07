/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.aic.geographtools;

/**
 *
 * @author fido
 */
public class GraphSpec2D {
    public final double minLat;
    
    public final double minLon;
    
    public final double maxLat;
    
    public final double maxLon;
    
    public final GPSLocation centroid;

    public GraphSpec2D(double minLat, double minLon, double maxLat, double maxLon, GPSLocation centroid) {
        this.minLat = minLat;
        this.minLon = minLon;
        this.maxLat = maxLat;
        this.maxLon = maxLon;
        this.centroid = centroid;
    }
    
    public int getWidth(){
        return (int) Math.round(maxLon - minLon);
    }
    
    public int getHeight(){
        return (int) Math.round(maxLat - minLat);
    }
    
}
