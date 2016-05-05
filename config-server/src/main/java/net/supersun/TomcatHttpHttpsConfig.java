package net.supersun;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author .: Clement Nosariemen Ojo
 * @email ..: clement.ojo@live.com, clement.ojo@cwlgroup.com
 * @created : Aug 21, 2015 7:18 PM
 * <p/>
 * In some cases it might be a good idea to make your application accessible over
 * HTTP too, but redirect all traffic to HTTPS. To achieve this we’ll need to add
 * a second Tomcat connector, but currently it is not possible to configure two
 * connector in the application.properties like mentioned before. Because of this we’ll
 * add the HTTP connector programmatically and make sure it redirects all traffic to
 * our HTTPS connector
 * https://drissamri.be/blog/java/enable-https-in-spring-boot/
 */
@Configuration
public class TomcatHttpHttpsConfig {

    @Value("${server.http.port}")
    private String HTTP_PORT;

    @Value("${server.port}")
    private String HTTPS_PORT;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };

        tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
        return tomcat;
    }

    private Connector initiateHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(Integer.valueOf(HTTP_PORT));
        connector.setSecure(false);
        connector.setRedirectPort(Integer.valueOf(HTTPS_PORT));

        return connector;
    }
}