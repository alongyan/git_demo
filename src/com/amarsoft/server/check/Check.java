package com.amarsoft.server.check;
/**
 * 继承检查类实现检查方法，方法体必须要有try catch ，以防止服务出错不能及时返回信息
 */
import java.util.Map;

import com.amarsoft.server.config.ErrorConfig;
import com.amarsoft.server.dao.SQLQuery;

public interface Check {
	/**
	 * 系统检查调用的执行方法
	 * @param srcMsg 客户端传送的字符串报文
	 * @param map  客户端传送的map报文，经过消息编码器把字符串报文进行转换
	 * @param errorMap 错误报文存放的载体
	 * @param ec 检查的实例对象对应的错误消息对象
	 * @return
	 */
	public abstract boolean excute(String srcMsg, Map<String, Object> map, Map<String, Object> errorMap, ErrorConfig ec);
	
	/**
	 * 业务检查调用的执行方法
	 * @param requestMap
	 * @param sqlQuery
	 * @return
	 */
	public abstract boolean excute(Map<String, Object> requestMap, SQLQuery sqlQuery);
}
