package com.dongkap.activity.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.dongkap.common.exceptions.CustomOauthException;

@Configuration
public class GlobalConfiguration {
	
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        final OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
        entryPoint.setExceptionTranslator(exceptionTranslator());
        return entryPoint;
    }
    
    @Bean
    public WebResponseExceptionTranslator<OAuth2Exception> exceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
	    	@Override
	        public ResponseEntity<OAuth2Exception> translate(Exception exception) throws Exception {
	            ResponseEntity<OAuth2Exception> responseEntity = super.translate(exception);
	            OAuth2Exception oAuth2Exception = responseEntity.getBody();
	            HttpHeaders headers = new HttpHeaders();
	            headers.setAll(responseEntity.getHeaders().toSingleValueMap());
				return new ResponseEntity<OAuth2Exception>(new CustomOauthException(oAuth2Exception.getMessage(), oAuth2Exception.getOAuth2ErrorCode()),
	            		headers,
	            		responseEntity.getStatusCode());
	        }
	    };
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        final OAuth2AccessDeniedHandler handler = new OAuth2AccessDeniedHandler();
        handler.setExceptionTranslator(exceptionTranslator());
        return handler;
    }

	@Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder(13);
    }
    
    @Bean
    public TokenStore tokenStore() {
        // return new JdbcOauth2TokenStore(dataSource);
        return new RedisTokenStore(redisConnectionFactory);
    }
	
}
