package com.vivebest.mall.admin.controller;
/**
 * 
 */


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author liujc
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath*:/applicationContext-test.xml", "classpath*:/applicationContext-mvc-test.xml"})
// 当然 你可以声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否
public class CommonControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	// 执行测试方法之前初始化模拟request,response
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	/**
	 * Test method for
	 * {@link com.vivebest.mall.core.service.BaseService#update(java.lang.Object)}
	 * .
	 */
	@Test
	public void testTT() {
		try {
			this.mockMvc.perform(post("/admin/common/tt.jhtml")).andExpect(status().isOk()).andDo(print());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
