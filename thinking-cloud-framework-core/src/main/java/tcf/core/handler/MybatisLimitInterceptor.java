package tcf.core.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;

import tcf.beans.page.Limit;
import tcf.utils.reflect.ReflectUtils;

/**
 * mybatis 分页插件
 * <P>
 * 1. mybatis中处理sql语句的Handler类的继承结构 StatementHandler(顶级父类) 
 * 		|--- RoutingStatementHandler (处理sql语句) 
 * 		|--- BaseStatementHandler (抽象类) 
 * 		|		|--- SimpleStatementHandler (处理不需要预编译sql语句的Handler) 
 * 		|		|--- PreparedStatementHandler (处理预编译sql语句的Handler) 
 * 		|		|--- CallableStatementHandler (处理存储过程的Handler)
 *
 * 2. mybatis的handle的处理流程 
 * 	1) Mybatis在进行Sql语句处理的时候都是建立的RoutingStatementHandler,在RoutingStatementHandler里面拥有一个delegate属性(StatementHandler类型).
 * 	2) RoutingStatementHandler会依据delegate处理的Statement不同,建立不同的BaseStatementHandler(SimpleStatementHandler、PreparedStatementHandler、CallableStatementHandler) 
 *  3) RoutingStatementHandler里面所有StatementHandler接口方法的实现都是调用的delegate对应的方法 
 *  
 *3.拦截器中三个方法的作用 
 *	1) intercept(): 拦截StatementHandler,进行增强处理的方法 
 *	2) plugin(): 封装StatementHandler, 在此方法中确定是否进行拦截. 
 *	3) setProperties():读取配置信息
 * </P>
 * 
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })

public class MybatisLimitInterceptor implements Interceptor {

	private String databaseType;// 数据库类型，不同的数据库有不同的分页方法

	/**
	 * 拦截StatementHandler,进行增强处理的方法
	 */
	public Object intercept(Invocation invocation) throws Throwable {
		Object target = invocation.getTarget();
		RoutingStatementHandler handler = (RoutingStatementHandler)invocation.getTarget();		
		BaseStatementHandler delegate = (BaseStatementHandler) ReflectUtils.fieldValue(handler, true,"delegate");
		
		// 获取sql语句
		BoundSql boundSql = delegate.getBoundSql();
		// 获取查询参数对象
		Object obj = boundSql.getParameterObject();
		
		if (obj instanceof Limit) {
			Limit limit = (Limit) obj;
			Connection connection = (Connection) invocation.getArgs()[0];
			String sql = boundSql.getSql();
			long totalRecord =  queryTotalRecord(limit, delegate, connection);
			limit.setTotalRecord(totalRecord);
			
			// 处理分页的sql
			String pageSql = generatorPageSql(limit, sql);
			Configuration configuration = ReflectUtils.fieldValue(delegate, true, "configuration");
			boundSql = new BoundSql(configuration, pageSql, boundSql.getParameterMappings(), boundSql.getParameterObject());  
			ReflectUtils.fieldValue(delegate,true, "boundSql", boundSql);
		}
		Object proceed = invocation.proceed();
		return proceed;
	}

	/**
	 * 拦截器对应的封装原始对象的方法
	 */
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/**
	 * 设置注册拦截器时设定的属性
	 */
	public void setProperties(Properties properties) {
		this.databaseType = properties.getProperty("databaseType");
	}

	/**
	 * 根据page对象获取对应的分页查询Sql语句，这里只做了两种数据库类型，Mysql和Oracle 其它的数据库都 没有进行分页
	 * 
	 * @param page 分页对象
	 * @param sql  原sql语句
	 * @return
	 */
	private String generatorPageSql(Limit limit, String sql) {
		StringBuffer sqlBuffer = new StringBuffer(sql);
		if (databaseType == null || "mysql".equalsIgnoreCase(databaseType) ) {
			return mysqlPageSql(limit, sqlBuffer);
		} else if ("oracle".equalsIgnoreCase(databaseType)) {
			return oraclePageSql(limit, sqlBuffer);
		}
		return sqlBuffer.toString();
	}

	/**
	 * 给当前的参数对象page设置总记录数
	 * 
	 * @param page            Mapper映射语句对应的参数对象
	 * @param mappedStatement Mapper映射语句
	 * @param connection      当前的数据库连接
	 */
	private long queryTotalRecord(Limit limit, StatementHandler delegate, Connection connection) {
		
		long totalRecord  = 0;
		// 获取分页查询的sql语句 及 查询参数
		MappedStatement mappedStatement = (MappedStatement) ReflectUtils.fieldValue(delegate, true,"mappedStatement");
		BoundSql boundSql = mappedStatement.getBoundSql(limit);
		String sql = boundSql.getSql();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		
		// 改造为获取总数的sql对象
		int index = sql.indexOf("from");
		index = index==-1 ? sql.indexOf("FROM") : index;
		String countSql ="select count(*) " + sql.substring(index);
		BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, limit);
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, limit, countBoundSql);
		
		// 通过JDBC的接口的方式获取总数
		try(
			PreparedStatement pstmt = connection.prepareStatement(countSql);
		) {
			parameterHandler.setParameters(pstmt);
			try(
				ResultSet rs =pstmt.executeQuery();
			){
				if (rs.next()) {
					totalRecord = rs.getLong(1);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} 
		return totalRecord;
	}
	
	
	/**
	 * 获取Mysql数据库的分页查询语句
	 * 
	 * @param page      分页对象
	 * @param sqlBuffer 包含原sql语句的StringBuffer对象
	 * @return Mysql数据库分页语句
	 */
	private String mysqlPageSql(Limit limi, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
		int offset = (limi.getPageNo() - 1) * limi.getPageSize();
		sqlBuffer.append(" limit ").append(offset).append(",").append(limi.getPageSize());
		return sqlBuffer.toString();
	}

	/**
	 * 获取Oracle数据库的分页查询语句
	 * 
	 * @param page      分页对象
	 * @param sqlBuffer 包含原sql语句的StringBuffer对象
	 * @return Oracle数据库的分页查询语句
	 */
	private String oraclePageSql(Limit limi, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
		int offset = (limi.getPageNo() - 1) * limi.getPageSize() + 1;
		sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ")
				.append(offset + limi.getPageSize());
		sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
		// 上面的Sql语句拼接之后大概是这个样子：
		// select * from (select u.*, rownum r from (select * from t_user) u where
		// rownum < 31) where r >= 16
		return sqlBuffer.toString();
	}
}
