
package isp.lab9.exercise3;

import java.io.Serializable;

public class AccessKey implements Serializable{
    private String pin;

    public AccessKey(String pin) {
        this.pin = pin;
    }
    
    public String getPin() {
        return pin;
    }
    
}
