package com.zuul.springboot.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 配置restTemplate
 * @author yangyiwei
 * @date 2018年6月4日
 * @time 下午1:46:21
 */
@Configuration
public class RestTemplateConfig {


    
/*	@Bean
	public AlwaysSampler defaultSampler() { //服务追踪
		return new AlwaysSampler();
	}*/

    @Bean("clientFactory")
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(500);//单位为ms
        factory.setConnectTimeout(500);//单位为ms
        return factory;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(@Qualifier("clientFactory") ClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }
}