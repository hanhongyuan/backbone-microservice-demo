package net.supersun;

import com.netflix.discovery.DiscoveryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * author   : Clement Nosariemen Ojo
 * email    : clement.ojo@live.com, clement.ojo@cwlgroup.com
 * date     : April 19, 2016  8:53 AM
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class CompositeApplication {

    private static final Logger LOG = LoggerFactory.getLogger(CompositeApplication.class);

    static {
        // for localhost testing only
        //LOG.warn("Will now disable hostname check in SSL, only to be used during development");
//        HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
    }

    public static void main(String[] args) {
        SpringApplication.run(CompositeApplication.class, args);

        LOG.info("Register ShutdownHook");
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                LOG.info("Shutting down, unregister from Eureka!");
                DiscoveryManager.getInstance().shutdownComponent();
            }
        });
    }
}
