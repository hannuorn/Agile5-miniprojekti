/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Random;

/**
 *
 * @author htomi
 */
public class HexadecimalGenerator implements IdGenerator{
    
    private final Random randomizer;
    
    public HexadecimalGenerator(){
        this.randomizer = new Random();
    }
    
    @Override
    public String getId(){
        int id = randomizer.nextInt();
        return Integer.toHexString(id);
    }
    
}
