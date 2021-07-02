package isp.lab9.exercise3;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoorLockController implements ControllerInterface{
    
    private Map<Tenant,AccessKey> validAccess=new HashMap<>(); 
    private Door door=new Door();
    private int counter=0;
    private String file="TenantFile";

    public DoorLockController() {
        try {
            readTenantList();
        } catch (IOException ex) {
            Logger.getLogger(DoorLockController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DoorLockController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public DoorStatus enterPin(String pin) throws TooManyAttemptsException, InvalidPinException {
        AccessKey key=new AccessKey(pin);

        if (pin.equals(MASTER_KEY)){
                    this.counter=0;
                    this.door.unlockDoor();
        }
        else    if (this.counter==3){
                this.door.lockDoor();
                throw new TooManyAttemptsException();
            }
            else{
                if(validAccess.values().stream().anyMatch(v -> v.getPin().equals(pin))){
                    if (this.door.getStatus().equals(DoorStatus.CLOSE)) this.door.unlockDoor();
                    else this.door.lockDoor();
                }
                else{
                    this.counter++;
                    throw new InvalidPinException();
                }
            }
        return this.door.getStatus();
    }

    @Override
    public void addTenant(String pin, String name) throws Exception {
        AccessKey key=new AccessKey(pin);
        Tenant tenant=new Tenant(name);
        
        if (this.validAccess.keySet().stream().anyMatch(k->k.getName().equals(name))) throw new TenantAlreadyExistsException();
        else{
            this.validAccess.put(tenant, key);
            saveTenantList();
        }
    }

    @Override
    public void removeTenant(String name) throws Exception {
        Tenant tenant=new Tenant(name);
        
        if (this.validAccess.keySet().stream().noneMatch(k->k.getName().equals(name))) throw new TenantNotFoundException();
        else {
            validAccess.keySet().stream()
                .filter(accessKey -> accessKey.getName().equals(name))
                .findFirst().ifPresent(validAccess::remove);
            saveTenantList();
        }
    
    }
    
    public Map<Tenant, AccessKey> getTenants() throws Exception{
        if (validAccess.isEmpty()) throw new ThereAreNoTenantsException();
        else return validAccess;
    }
    
    public void saveTenantList() throws IOException{
        ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream(file));
        o.writeObject(validAccess);
        o.close();
    }
    
    public void readTenantList() throws IOException, ClassNotFoundException{
        ObjectInputStream in=new ObjectInputStream(new FileInputStream(file));
        Map<Tenant,AccessKey> tenantList=(Map<Tenant,AccessKey>)in.readObject();
        in.close();
        this.validAccess=tenantList;
    }
 
}
