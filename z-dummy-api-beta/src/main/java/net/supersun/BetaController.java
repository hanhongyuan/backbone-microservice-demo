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
public class BetaController {

    @RequestMapping(value= "/beta-1")
    public String beta1() {
        return "beta-1 is up";
    }
    @RequestMapping(value= "/beta-2")
    public String beta2() {
        return "beta-2 is up";
    }
}
