/*
 * Copyright (C) 2017 Czech Technical University in Prague
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package cz.cvut.fel.aic.geographtools.util;

import cz.cvut.fel.aic.geographtools.Edge;
import cz.cvut.fel.aic.geographtools.GPSLocation;
import cz.cvut.fel.aic.geographtools.Graph;
import cz.cvut.fel.aic.geographtools.GraphSpec2D;
import cz.cvut.fel.aic.geographtools.Node;

/**
 *
 * @author fido
 */
public class Utils2D {
    public static <N extends Node,E  extends Edge> GPSLocation getGraphCentroid(Graph<N,E> graph){
        long latProjectedSum = 0;
        long lonProjectedSum = 0;
        
        for(N node : graph.getAllNodes()){
            latProjectedSum += node.getLatitudeProjected1E2();
            lonProjectedSum += node.getLongitudeProjected1E2();
        }
        
        int averageLatProjected = (int) Math.round((double) latProjectedSum / graph.getAllNodes().size());     
        int averageLonProjected = (int) Math.round((double) lonProjectedSum / graph.getAllNodes().size());
        
        return new GPSLocation(0, 0, averageLatProjected, averageLonProjected);
    }
    
    public static <N extends Node,E  extends Edge> int getGraphWidth(Graph<N,E> graph){
        int minLon = Integer.MAX_VALUE;
        int maxLon = Integer.MIN_VALUE;
        
        for(N node : graph.getAllNodes()){
            if(node.getLongitudeProjected1E2() < minLon){
                minLon = node.getLongitudeProjected1E2();
            }
            if(node.getLongitudeProjected1E2() > maxLon){
                maxLon = node.getLongitudeProjected1E2();
            }
        }
        
        return (int) ((double) (maxLon - minLon) / 1E2);
    }
    
    public static <N extends Node,E  extends Edge> GraphSpec2D getGraphSpec(Graph<N,E> graph){
        long latProjectedSum = 0;
        long lonProjectedSum = 0;
        
        int minLon = Integer.MAX_VALUE;
        int maxLon = Integer.MIN_VALUE;
        int minLat = Integer.MAX_VALUE;
        int maxLat = Integer.MIN_VALUE;
        
        for(N node : graph.getAllNodes()){
            latProjectedSum += node.getLatitudeProjected1E2();
            lonProjectedSum += node.getLongitudeProjected1E2();
            
            if(node.getLongitudeProjected1E2() < minLon){
                minLon = node.getLongitudeProjected1E2();
            }
            if(node.getLongitudeProjected1E2() > maxLon){
                maxLon = node.getLongitudeProjected1E2();
            }
            if(node.getLatitudeProjected1E2() < minLat){
                minLat = node.getLatitudeProjected1E2();
            }
            if(node.getLatitudeProjected1E2() > maxLat){
                maxLat = node.getLatitudeProjected1E2();
            }
        }
        
        int averageLatProjected = (int) Math.round((double) latProjectedSum / graph.getAllNodes().size());     
        int averageLonProjected = (int) Math.round((double) lonProjectedSum / graph.getAllNodes().size());
        
        GPSLocation centroid = new GPSLocation(0, 0, averageLatProjected, averageLonProjected);
        
        return new GraphSpec2D((double) (minLat / 1E2), (double) (minLon / 1E2), (double) (maxLat / 1E2), 
                (double) (maxLon / 1E2), centroid);
    }
}
