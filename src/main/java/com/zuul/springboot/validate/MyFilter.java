package com.zuul.springboot.validate;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.chat.springboot.common.response.ResponseResult;
import com.chat.springboot.common.response.ResultStatus;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * zuul自带的过滤器
 * @author yangyiwei
 * @date 2018年9月25日
 * @time 下午1:40:55
 */
@Component
@Slf4j
public class MyFilter extends ZuulFilter {

	@Autowired
	private JedisPool jedisPool;

	@Override
	public Object run() {
		log.info("执行网关拦截器......");
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String accessToken = request.getParameter("token");
		if (request.getRequestURI().indexOf("login") != -1 || request.getRequestURI().indexOf("register") != -1) {
			log.info("登陆或者请求....放行.....");
			return null;
		}
		if (accessToken == null) {
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(200);
			try {
				HttpServletResponse response = ctx.getResponse();
				response.setContentType("application/json;charset=utf-8");
				ResponseResult<String> responseResult = new ResponseResult<String>(
						ResultStatus.TOKEN_IS_NULL);
				PrintWriter out = response.getWriter();
				out.write(JSONObject.toJSONString(responseResult));
				out.flush();
				out.close();
			} catch (Exception e) {
			}
		} else { // 不为空
			Jedis jedis = jedisPool.getResource();
			if (!jedis.exists(accessToken)) {
				ctx.setSendZuulResponse(false);
				ctx.setResponseStatusCode(200);
				try {
					HttpServletResponse response = ctx.getResponse();
					response.setContentType("application/json;charset=utf-8");
					ResponseResult<String> responseResult = new ResponseResult<String>(
							ResultStatus.TOKEN_IS_INVALID);
					PrintWriter out = response.getWriter();
					out.write(JSONObject.toJSONString(responseResult));
					out.flush();
					out.close();
				} catch (Exception e) {
				}
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {

		return 0;
	}

	@Override
	public String filterType() {

		return "pre";
	}

}
