package net.supersun.discoveryserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import javax.net.ssl.HttpsURLConnection;

/**
 * author   : Clement Nosariemen Ojo
 * email    : clement.ojo@live.com, clement.ojo@cwlgroup.com
 * date     : April 19, 2016  8:53 AM
 */

@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
public class DiscoveryServerApplication {

    private static final Logger LOG = LoggerFactory.getLogger(DiscoveryServerApplication.class);

    static {
        // for localhost testing only
        LOG.warn("Will now disable hostname check in SSL, only to be used during development");
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
        XTrustProvider.install();
    }
    public static void main(String[] args) {

        SpringApplication.run(DiscoveryServerApplication.class, args);
    }
}
