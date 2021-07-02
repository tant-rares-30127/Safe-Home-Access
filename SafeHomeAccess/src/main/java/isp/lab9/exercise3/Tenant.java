
package isp.lab9.exercise3;

import java.io.Serializable;

public class Tenant implements Serializable{
    private String name;

    public Tenant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
