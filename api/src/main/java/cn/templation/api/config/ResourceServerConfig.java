package cn.templation.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 资源服务配置
 *
 * @author: Gaozj
 * @date: 2021/02/18
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

////	/**
////	 * token服务配置
////	 */
////    @Override
////	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
////		resources.tokenServices(tokenServices());
//	}

	/**
	 * 路由安全认证配置
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// 配置deliver打头的路由需要安全认证
				.antMatchers("/deliver/**").authenticated()
				.and().csrf().disable();
	}

	/**
	 * jwt token 校验解析器
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	/**
	 * Spring Security有自己的默认令牌，其实就是一个UUID
	 * 但是在实际使用中，一般不会使用默认令牌，而是使用jwt令牌来替代默认令牌，
	 * 这样做的好处是携带默认令牌访问资源，每次都要通过授权服务来认证令牌是否有效，
	 * 而jwt则可以做到资源服务中自己解析从而判断令牌的有效性；
	 * 另外一个优势就是jwt令牌有更高的安全性，可以使用公钥和私钥进行加密和解密，不容易被破解。
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("Deliver");
		return accessTokenConverter;
	}

//	/**
//	 * 资源服务令牌解析服务
//	 */
//	@Bean
//	@Primary
//	/** 令牌解析服务：携带获取到的token去请求服务时，会将之前的token解析成相关的用户及客户端信息*/
//	public ResourceServerTokenServices tokenServices() {
//        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//        remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:8082/oauth/check_token");
//        remoteTokenServices.setClientId("client_1");
//        remoteTokenServices.setClientSecret("123456");
//        return remoteTokenServices;
//	}
}
