package net.supersun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * author   : Clement Nosariemen Ojo
 * email    : clement.ojo@live.com, clement.ojo@cwlgroup.com
 * date     : April 21, 2016  12:51 AM
 */
@Configuration
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

    private int tokenLifeTimeInSeconds = 1 * 60 * 60;//1 hour

    @Value("${server.ssl.key-alias}")
    private String  KEYSTORE_ALIAS;

    @Value("${server.ssl.key-store-filename}")
    private String  KEYSTORE_FILENAME;

    @Value("${server.ssl.key-password}")
    private String  KEYSTORE_PASSWORD;

    @Autowired
//    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    /**
     * #security.oauth2.client.clientId: acme
     * #security.oauth2.client.clientSecret: acmesecret
     * #security.oauth2.client.authorized-grant-types: authorization_code,refresh_token,implicit,password,client_credentials
     * #security.oauth2.client.scope: webshop
     *
     * @param clients
     * @throws Exception
     *
     * curl -X POST -k -vu yellow_dot_phone_pay_clientID:YellowDotClientSecret https://localhost:9999/uaa/oauth/token -d grant_type=password -d client_id=yellow_dot_phone_pay_clientID -d scope=trust -d username=user -d password=password | jq .
     * curl -X POST -k -u yellow_dot_phone_pay_clientID:YellowDotClientSecret https://localhost:9999/uaa/oauth/token -d grant_type=password -d client_id=yellow_dot_phone_pay_clientID -d scope=trust -d username=user -d password=password | jq .
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("yellow_dot_phone_pay_clientID")
                    .secret("YellowDotClientSecret")
                    .scopes("read", "write", "trust")
                    .autoApprove(true)
                    .authorities(AuthorityConstants.AUTH_SERVICE_READ, AuthorityConstants.AUTH_SERVICE_WRITE)
                    .accessTokenValiditySeconds(tokenLifeTimeInSeconds)
                    .authorizedGrantTypes("authorization_code", "refresh_token", "implicit", "password", "client_credentials")

//                .and()
//
//                // Public client where client secret is vulnerable (e.g. mobile apps, browsers)
//                .withClient("public") // No secret!
//                .authorizedGrantTypes("client_credentials", "implicit")
//                .scopes("read")
//                .redirectUris("http://localhost:8080/client/")
        ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer()).authenticationManager(authenticationManager);
//        endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer());
//        endpoints.authenticationManager(authenticationManager);
        //endpoints.authenticationManager(authenticationManager).accessTokenConverter(jwtAccessTokenConverter());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
//        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("iyeke.jks"), "password".toCharArray());
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(KEYSTORE_FILENAME), KEYSTORE_PASSWORD.toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        //keytool -v -list -keystore /usr/local/_development/_workspaces/eclipse_workspace/_playground_demos/backbone-microservice-architecture/auth-server/src/main/resources/server.jks
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(KEYSTORE_ALIAS));//alias used when generating the imuetiyan_keystore.jks was tomcat
        return converter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer)
            throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }
}
