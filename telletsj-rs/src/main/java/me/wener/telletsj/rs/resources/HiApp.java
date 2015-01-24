package me.wener.telletsj.rs.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hi")
public class HiApp
{
    @GET
    public String get()
    {
        return "Hi, telletsj.";
    }
}
