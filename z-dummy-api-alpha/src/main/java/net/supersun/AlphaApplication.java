package net.supersun;

import com.netflix.discovery.DiscoveryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import javax.net.ssl.HttpsURLConnection;

/**
 * author   : Clement Nosariemen Ojo
 * email    : clement.ojo@live.com, clement.ojo@cwlgroup.com
 * date     : April 19, 2016  8:53 AM
 *
 */

@SpringBootApplication
//@EnableDiscoveryClient
@EnableEurekaClient
//@EnableHystrix
@EnableResourceServer
@EnableCircuitBreaker
public class AlphaApplication {

    private static final Logger LOG = LoggerFactory.getLogger(AlphaApplication.class);

    static {
        // for localhost testing only
        LOG.warn("Will now disable hostname check in SSL, only to be used during development");
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
        XTrustProvider.install();
    }



    public static void main(String[] args) {
        SpringApplication.run(AlphaApplication.class, args);

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
