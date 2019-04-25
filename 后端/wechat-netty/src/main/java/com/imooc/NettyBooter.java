/*
 * netty的启动类
 */
package com.imooc;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.imooc.netty.WSserver_start;

import ch.qos.logback.core.Context;

/**
 * @author Jayyan
 *项目名称：
 *类名称：
 *类描述:
 *创建时间:2019年4月15日下午5:18:14
 * @version
 *TODO：
 */
@Component
//实现applictionlistener然后去监听contextrefreshedevent上下文对象
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {

	/* （非 Javadoc）
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent()==null) {
			
			try {
				WSserver_start.getInstance().start();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	

}
