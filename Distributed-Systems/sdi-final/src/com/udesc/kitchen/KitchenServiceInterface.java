package com.udesc.kitchen;

import com.udesc.utils.Vec2;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface KitchenServiceInterface {

    @WebMethod void start(@WebParam(name = "kitchen") Kitchen kitchen);
    @WebMethod Integer getTablePriority(Vec2 position);
    @WebMethod boolean walk(
            @WebParam(name = "position") Vec2 position,
            @WebParam(name = "chefId") Character chefId,
            @WebParam(name = "full") boolean full
    );
    @WebMethod boolean get(
            @WebParam(name = "position") Vec2 position,
            @WebParam(name = "chefId") Character chefId,
            @WebParam(name = "full") boolean full
    ) ;
    @WebMethod boolean put(
            @WebParam(name = "position") Vec2 position,
            @WebParam(name = "chefId") Character chefId,
            @WebParam(name = "full") boolean full
    );
    @WebMethod boolean isFinished();
    @WebMethod boolean hasPlate(@WebParam(name = "position")Vec2 position);
    @WebMethod boolean isEntrance(Vec2 position);
    @WebMethod boolean isExit(Vec2 position);

}
