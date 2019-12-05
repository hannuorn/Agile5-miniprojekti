/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dataAccess.MemoryItemDAO;
import org.junit.rules.ExternalResource;
import spark.Spark;

/**
 *
 * @author htomi
 */
public class ServerRule extends ExternalResource {
    
    
    private final int port;

    public ServerRule(int port) {
        this.port = port;
    }

    @Override
    protected void before() throws Throwable {
        Spark.port(port);
        MemoryItemDAO testidao = new MemoryItemDAO();
        UI ui = new UI(testidao);
        
    }

    @Override
    protected void after() {
        Spark.stop();
    }
    
}
    

