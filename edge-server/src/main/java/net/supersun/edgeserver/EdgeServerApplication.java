package net.supersun.edgeserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import javax.net.ssl.HttpsURLConnection;

/**
 * author   : Clement Nosariemen Ojo
 * email    : clement.ojo@live.com, clement.ojo@cwlgroup.com
 * date     : April 19, 2016  8:53 AM
 */

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableEurekaClient

//@EnableHystrix
@EnableEurekaClient
//@EnableZuulServer
//@EnableCircuitBreaker  <-- is already included in EnableZuulProxy
@EnableZuulProxy
@EnableResourceServer
//@EnableOAuth2Sso

public class EdgeServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdgeServerApplication.class, args);
    }

    private static final Logger LOG = LoggerFactory.getLogger(EdgeServerApplication.class);

    static {
        // for localhost testing only
        LOG.warn("Will now disable hostname check in SSL, only to be used during development");
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
        XTrustProvider.install();
    }
    @Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }
}
