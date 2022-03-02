package com.udesc.chefs;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.net.UnknownHostException;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ChefSpawnerInterface {

    @WebMethod Character spawnChef(Character chefId) throws UnknownHostException;
    @WebMethod void kill();

}
