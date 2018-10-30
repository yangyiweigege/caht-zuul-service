package com.zuul.springboot.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;

/**
 * 负责调用chat-user-service服务
 * 使用restTemplate实现
 * @author yangyiwei
 * @date 2018年9月7日
 * @time 下午4:20:44
 */
@RestController
@RequestMapping("/user/service")
public class UserServiceController {

	@Resource
	private RestTemplate restTemplate;

	@RequestMapping("/login")
	public JSONObject login(String userName, String password) {
		return restTemplate.getForObject(
				"http://chat-user-service/user/info/login?userName=" + userName + "&password=" + password,
				JSONObject.class);

	}

}
