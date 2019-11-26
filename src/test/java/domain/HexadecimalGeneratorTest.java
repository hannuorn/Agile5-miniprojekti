/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.HashSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author htomi
 */
public class HexadecimalGeneratorTest {
    
    private HashSet<String> generatedHexadecimals;
    private HexadecimalGenerator generator;
    
    public HexadecimalGeneratorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.generatedHexadecimals = new HashSet();
        this.generator = new HexadecimalGenerator();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void inHundredThousandIdsThereAreNoDuplicates(){
        
        boolean noDuplicates = true;
        for(int i = 0; i<100000; i++){
            String id = generator.getId();
            if(generatedHexadecimals.contains(id)){
                noDuplicates = false;
                break;
            }
            generatedHexadecimals.add(id);
        }
        
        assertTrue(noDuplicates);
        
    }

    
}
