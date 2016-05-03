package net.supersun;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;


/**
 * author   : Clement Nosariemen Ojo
 * email    : clement.ojo@live.com, clement.ojo@cwlgroup.com
 * date     : April 20, 2016  4:28 PM
 */
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@RestController
public class ApiController {

    @RequestMapping(value= "/alpha-1")
    public String alpha1() {
        return "alpha-1 is up";
    }
    @RequestMapping(value= "/alpha-2")
    public String alpha2() {
        return "alpha-2 is up";
    }
}
