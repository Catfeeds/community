package com.tongwii.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.tongwii.dao.BaseDao;
import com.tongwii.service.IBaseService;
import com.tongwii.service.IPageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public abstract class BaseServiceImpl<M extends Serializable> implements IBaseService<M> {

	private final Logger log = LogManager.getLogger((Class<Object>) getSuperClassGenricType(getClass(), 0));

	@Autowired
	public abstract BaseDao<M, String> getDao();

	@Override
	public M findById(String id) {
		return getDao().findById(id);
	}

	@Override
	public List<M> findAll(Order... orders) {
		return getDao().findAll(orders);
	}

	@Override
	public List<M> findAll(IPageInfo pageInfo, Order... orders) {
		return getDao().findAll(pageInfo, orders);
	}

	@Override
	public List<M> findByExample(M model, Order... orders) {
		return getDao().findByExample(model, orders);
	}

	@Override
	public List<M> findByExample(M model, IPageInfo pageInfo, Order... orders) {
		return getDao().findByExample(model, pageInfo, orders);
	}


	@Override
	public M findUnique(M model) {
		return getDao().findUnique(model);
	}

	@Override
	public void save(M model) {
		getDao().save(model);
		log.info((Class<Object>)getSuperClassGenricType(getClass(), 0)+"保存了name为:"+getObjectField(model, "name")+"的对象");
	}

	@Override
	public void saveOrUpdate(M model) {
		getDao().saveOrUpdate(model);
		if(null != getObjectField(model,"id")){
			log.info((Class<Object>)getSuperClassGenricType(getClass(), 0)+"修改了name为:"+getObjectField(model, "name")+"的对象");
		}else{
			log.info((Class<Object>)getSuperClassGenricType(getClass(), 0)+"保存了name为:"+getObjectField(model, "name")+"的对象");
		}
	}

	@Override
	public void update(M model) {
		getDao().update(model);
		log.info((Class<Object>)getSuperClassGenricType(getClass(), 0)+"修改了name为:"+getObjectField(model, "name")+"的对象");
	}

	@Override
	public void deleteObject(M model) {
		getDao().deleteObject(model);
		log.info((Class<Object>)getSuperClassGenricType(getClass(), 0)+"删除了id为:"+getObjectField(model, "id")+"的对象");
	}

	@Override
	public void delete(String id) {
		getDao().delete(id);
		log.info((Class<Object>)getSuperClassGenricType(getClass(), 0)+"删除了id为:"+id+"的对象");
	}

	@Override
	public void batchDelete(List<String> ids) {
		for (String k : ids) {
			delete(k);
		}
	}

	@Override
	public void batchDelete(String[] ids) {
		for (String k : ids) {
			delete(k);
		}
	}

	@Override
	public void batchSave(List<M> models) {
		for (M m : models) {
			save(m);
		}
	}

	@Override
	public void batchDeleteModel(List<M> models) {
		for (M m : models) {
			deleteObject(m);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Class<Object> getSuperClassGenricType(final Class clazz, final int index) {

		// 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		// 返回表示此类型实际类型参数的 Type 对象的数组。
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}

		return (Class) params[index];
	}

	public String getObjectField(M m,String f) {
		Field fields[] = ((Class<Object>) getSuperClassGenricType(getClass(), 0)).getDeclaredFields();// 获得对象所有属性
		Field field = null;
		String[] attr = { f};
		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			field.setAccessible(true);// 修改访问权限
			for (int j = 0; j < attr.length; j++) {
				if (attr[j].equals(field.getName())) {
					try {
						Object o = field.get(m);
						return null == o ? "" : o.toString();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}// 读取属性值
				}
			}
		}
		return null;
	}

}
