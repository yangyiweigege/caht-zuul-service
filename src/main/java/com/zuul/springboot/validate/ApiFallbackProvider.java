package com.zuul.springboot.validate;

import com.alibaba.fastjson.JSONObject;
import com.chat.springboot.common.response.ResponseResult;
import com.chat.springboot.common.response.ResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description: 网关层熔断
 * @Author: 杨乙伟
 * @Date Created in 2019-02-11 17:19:36
 */
@Component
@Slf4j
public class ApiFallbackProvider implements ZuulFallbackProvider {
    @Override
    public String getRoute() {
        //设置熔断的服务名
        //如果是所有服务则设置为*
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                ResponseResult<?> responseResult = new ResponseResult<>();
                responseResult.setCode(ResultStatus.MICRO_SERVICE_DIED);
                return JSONObject.toJSONString(responseResult);
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(getStatusText().getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }


}
