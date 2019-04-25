/**
 * 
 */
package com.imooc;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

import com.github.tobato.fastdfs.FdfsClientConfig;

/**
 * @author Jayyan
 *项目名称：
 *类名称：
 *类描述:
 *创建时间:2019年4月20日下午9:21:45
 * @version
 *TODO：
 */
@Configuration
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration=RegistrationPolicy.IGNORE_EXISTING)
public class FastdfsImporter {
		//通过上面的主键导入依赖组件
}
