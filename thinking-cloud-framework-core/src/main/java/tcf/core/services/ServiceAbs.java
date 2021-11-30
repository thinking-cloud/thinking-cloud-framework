package tcf.core.services;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import tcf.beans.entity.Entity;
import tcf.beans.entity.LogicalDeletion;
import tcf.beans.entity.Timestamp;
import tcf.beans.enums.STATE;
import tcf.beans.page.Limit;
import tcf.beans.page.Page;
import tcf.core.exception.ServicesException;
import tcf.core.mapper.Mapper;
import tcf.core.mapper.simple.BatchDeleteMapper;
import tcf.core.mapper.simple.BatchInsertMapper;
import tcf.core.mapper.simple.CountMapper;
import tcf.core.mapper.simple.DeleteMapper;
import tcf.core.mapper.simple.InsertMapper;
import tcf.core.mapper.simple.PageMapper;
import tcf.core.mapper.simple.SelectMapper;
import tcf.core.mapper.simple.UpdateMapper;
import tcf.core.services.simple.BatchDeleteService;
import tcf.core.services.simple.BatchInsertService;
import tcf.core.services.simple.CountService;
import tcf.core.services.simple.DeleteService;
import tcf.core.services.simple.InsertService;
import tcf.core.services.simple.LimitService;
import tcf.core.services.simple.SelectService;
import tcf.core.services.simple.UpdateService;



/**
 * 操作数据库的默认Service
 * 
 * @param <T> 实体泛型
 * @param <PK> 主键泛型
 * @see InsertService
 * @see BatchInsertService
 * @see DeleteService
 * @see BatchDeleteService
 * @see UpdateService
 * @see SelectService
 * @see LimitService
 * @see CountService
 * @author think
 * @date 2021年11月29日
 * @version 1.0.0
 */
public abstract class ServiceAbs<PK extends Serializable, T extends Entity<PK>> implements 
																				InsertService<PK, T>,
																				BatchInsertService<PK, T>,
																				DeleteService<PK, T>,
																				BatchDeleteService<PK, T>,
																				UpdateService<PK,T>,
																				SelectService<PK, T>,
																				LimitService<PK,T>,
																				CountService{

	/**
	 * 获取mapper接口的实现类
	 * @return mapper接口的实现
	 */
	public abstract Mapper<PK,T>  getMapper();
	
	@Override
	public T insert(T entity){
		judgeMapperImplement(InsertMapper.class);
		int number = ((InsertMapper<PK,T>)getMapper()).insert(entity);
		if(number != 0){
			return entity;
		}
		return null;
	}
	
	@Override
	public int inserts(Collection<T> entitys){
		judgeMapperImplement(BatchInsertMapper.class);
		return ((BatchInsertMapper<PK,T>)getMapper()).inserts(entitys);
	}
	
	@Override
	public Collection<T> insertsAndReturnId(Collection<T> entitys){
		judgeMapperImplement(BatchInsertMapper.class);
		long count = ((BatchInsertMapper<PK,T>)getMapper()).insertsAndReturnId(entitys);
		if(count == entitys.size()) {
			return entitys;
		}
		throw new ServicesException("实际插入成功的数据条数与预期不符");
	}

	@Override
	public int deleteById(PK id){
		judgeMapperImplement(DeleteMapper.class);
		return ((DeleteMapper<PK,T>)getMapper()).deleteById(id);
	}
	
	@Override
	public int delete(T entity){
		judgeMapperImplement(DeleteMapper.class);
		return ((DeleteMapper<PK,T>)getMapper()).delete(entity);
	}
	
	@Override
	public long deleteByIds(Iterable<PK> ids) {
		judgeMapperImplement(BatchDeleteMapper.class);
		return ((BatchDeleteMapper<PK,T>)getMapper()).deleteByIds(ids);
	}
	
	@Override
	public int update(T entity){
		judgeMapperImplement(UpdateMapper.class);
		if(entity instanceof Timestamp) {
			Date lastUpdateTime = ((Timestamp)entity).getLastUpdateTime();
			if(lastUpdateTime == null) {
				((Timestamp)entity).setLastUpdateTime(new Date());
			}
		}
		return ((UpdateMapper<PK, T>)getMapper()).update(entity);
	}

	@Override
	public T selectByPK(PK pk){
		judgeMapperImplement(SelectMapper.class);
		return ((SelectMapper<PK, T>)getMapper()).selectById(pk);
	}

	@Override
	public Page<T> queryPage(Limit limit){
		judgeMapperImplement(PageMapper.class);
		List<T> list = ((PageMapper<PK,T>)getMapper()).queryPage(limit);
		Page<T> page = new Page<>();
		BeanUtils.copyProperties(limit, page);
		page.setRecords(list);
		return page;
	}
	
	@Override
	public long count(Limit limit){
		judgeMapperImplement(CountMapper.class);
		return ((CountMapper<PK, T>)getMapper()).count(limit);
	}
	
	/**
	 * 判断mapper实例是否实现指定的接口
	 * 
	 * @param mapperInterface Mapper接口
	 */
	private void judgeMapperImplement(Class mapperInterface) {
		Class mapper = getMapper().getClass();
		if(! mapperInterface.isAssignableFrom(mapper)){
			throw new ServicesException(mapper.getName()+" 没有实现 "+mapperInterface.getName()+" 接口！");
		}
	}
}
