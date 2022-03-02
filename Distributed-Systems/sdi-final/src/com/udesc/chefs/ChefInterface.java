package com.udesc.chefs;

import com.udesc.kitchen.KitchenMap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ChefInterface {

    @WebMethod void start(
            @WebParam(name = "kitchenMap") KitchenMap kitchenMap,
            @WebParam(name = "chefId") Character chefId,
            @WebParam(name = "kitchenServiceURL") String kitchenServiceURL
    );

    @WebMethod Character getChefId();

}
