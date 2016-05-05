package net.supersun;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

import javax.inject.Inject;
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
public class CompositeApiController {

    private static final Logger LOG = LoggerFactory.getLogger(CompositeApiController.class);

    @Inject
    @Qualifier("loadBalancedRestTemplate")
    private RestOperations restTemplate;

    @RequestMapping("/c-alpha1")
    @HystrixCommand(fallbackMethod = "defaultAlpha1")
    public String getAlpha1() {

        LOG.debug("Will alpha1 with Hystrix protection");

        String url = "https://z-dummy-api-alpha-server/alpha-1/";
//        String url = "https://localhost:2222/alpha-1/alpha-1/";
        LOG.debug("Get alpha1 from URL: {}", url);

//        restTemplate.

        ResponseEntity<String> originalAlpha1Response = restTemplate.getForEntity(url, String.class);


        LOG.debug("getAlpha1 http-status: {}", originalAlpha1Response.getStatusCode());

        LOG.debug("getAlpha1 body: {}", originalAlpha1Response.getBody());

        return originalAlpha1Response.getBody();
    }

    @RequestMapping(value= "/gamma-1")
    public String gamma1() {
        return "gamma-1 is up";
    }

    /**
     * Fallback method for alpha1
     *
     * @return
     */
    public String defaultAlpha1() {
        LOG.warn("Using fallback method for alpha-1");
        return "Fallback for alpha-1 is up";
    }


}
