/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.aic.geographtools.util;

/**
 *
 * @author fido
 */
public class NearestElementUtilPair<Coordinate, TElement>{
        
    private final Coordinate value0;

    private final TElement value1;

    public NearestElementUtilPair(Coordinate value0, TElement value1) {
        this.value0 = value0;
        this.value1 = value1;
    }


    public Coordinate getValue0() {
        return value0;
    }

    public TElement getValue1() {
        return value1;
    }

}