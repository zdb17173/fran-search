package org.fran.microservice.search.es.index.db.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by fran on 2016/8/15.
 */
public abstract class QueryTemplate<T>{
    protected JdbcTemplate template;
    private Class<T> persistentClass;

    public Class<T> getPersistentClass(){
        return persistentClass;
    }

    @SuppressWarnings("unchecked")
    private static Class<Object> getSuperClassGenericType(final Class<?> clazz,
                                                         final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class<Object>) params[index];
    }

    public QueryTemplate(JdbcTemplate template)
    {
        this.persistentClass = (Class<T>) getSuperClassGenericType(getClass(), 0);
        this.template = template;
    }

    T findByPrimaryKey(String sql, Object[] args){
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(getPersistentClass());
        List<T> res = template.query(sql, args, rowMapper);
        if(res!= null && res.size() > 0)
            return res.get(0);
        else
            return null;
    }

    List<T> find(String sql, Object[] args){
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(getPersistentClass());
        return template.query(sql, args, rowMapper);
    }

    List<T> findByPage(String sql ,Object[] args, Pageable pageable){
        int argSize = 0;
        if(args == null){

        }else{
            argSize = args.length;
        }

        int size = pageable.getPageSize();
        int num = pageable.getPageNumber();
        Object[] newsArgs = new Object[argSize + 2];
        for(int i = 0; i <args.length; i ++){
            newsArgs[i] = args[i];
        }
        newsArgs[argSize] = num * size;
        newsArgs[argSize + 1] = size;

        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(getPersistentClass());
        return template.query(sql + " limit ?, ?", newsArgs, rowMapper);
    }
}
