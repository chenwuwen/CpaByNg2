package cn.kanyun.cpa.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import cn.kanyun.cpa.model.entity.CpaResult;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;


/**
 * 不一定必须是abstract类型的， 请不要对CommonDaoImpl使用@Repository注解，因为无法直接指定clatt属性值
 * class值由继承类来指定
 *
 * @param <T>
 * @author BearSmall
 */
public abstract class CommonDaoImpl<K extends Serializable, T extends Serializable>
        implements ICommonDao<K, T> {
    @Resource
    private SessionFactory sessionFactory; // 从容器中注入session工厂【无需get,set方法】

    private Class<T> clatt; // 【实体类对应的Class对象】

    /**
     * //向子类暴露的接口获用来获取sessionFactory
     *
     * @return sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * 保留指定clatt值的接口【通过子类显示调用父类的构造函数来指定】
     *
     * @param clatt
     */
    public CommonDaoImpl(Class<T> clatt) {
        this.clatt = clatt;
    }

    // @SuppressWarnings("unchecked")
    // public BaseDaoImpl() {//另外一种方式指定clatt值，要求类必须是abstract类型
    // ParameterizedType parameterizedType =
    // (ParameterizedType)this.getClass().getGenericSuperclass();
    // clatt= (Class<T>)(parameterizedType.getActualTypeArguments()[0]);
    // }

    @Override
    public Session getSession() {
        return getSessionFactory().openSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public K save(T t) {
        Session session = getSession();
        return (K) session.save(t);
    }

    @Override
    public T findById(K id) {
        Session session = getSession();
        @SuppressWarnings("unchecked")
        T t = (T) session.get(clatt, id);
        return t;
    }

    @Override
    public void saveAll(Collection<T> ct) {
        Session session = getSession();
        for (T t : ct) {
            session.save(t);
        }
    }

    @Override
    public T update(T t) {
        Session session = getSession();
        session.update(t);
        return t;
    }

    @Override
    public void deleteAll(Collection<T> ct) {
        Session session = getSession();
        for (T t : ct) {
            session.delete(t);
        }
    }

    @Override
    public T saveOrUpdate(T t) {
        Session session = getSession();
        session.saveOrUpdate(t);
        return t;
    }

    @Override
    public void delete(T t) {
        Session session = getSession();
        session.delete(t);
    }

    @Override
    public boolean deleteById(K id) {
        Session session = getSession();
        @SuppressWarnings("unchecked")
        T t = (T) session.get(clatt, id);
        if (t == null)
            return false;
        session.delete(t);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public CpaResult<T> loadAll() {
        Session session = getSession();
        Criteria criteria = session.createCriteria(clatt);
        CpaResult<T> result = new CpaResult<T>();
        result.setData(criteria.list());
        result.setTotalCount(Long.parseLong(criteria
                .setProjection(Projections.rowCount()).uniqueResult()
                .toString()));
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public CpaResult<T> load(int page, int rows) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(clatt);
        criteria.setFirstResult((page - 1) * rows);
        criteria.setMaxResults(rows);
        CpaResult<T> result = new CpaResult<T>();
        result.setData(criteria.list());
        result.setTotalCount(Long.parseLong(criteria
                .setProjection(Projections.rowCount()).uniqueResult()
                .toString()));
        return result;
    }

    @Override
    public long getTotalCount() {
        Session session = getSession();
        Criteria criteria = session.createCriteria(clatt);
        Object object = criteria.setProjection(Projections.rowCount())
                .uniqueResult();
        long totalCount = 0;
        if (object != null) {
            totalCount = Long.parseLong(object.toString());
        }
        return totalCount;
    }

    /****************************** HQL ******************************/
    @Override
    public CpaResult<T> getScrollData() {
        return getScrollData(-1, -1, null, null, null);
    }

    @Override
    public CpaResult<T> getScrollData(int firstResult, int maxResult) {
        return getScrollData(firstResult, maxResult, null, null, null);
    }

    @Override
    public CpaResult<T> getScrollData(int firstResult, int maxResult,
                                      LinkedHashMap<String, String> orderby) {
        return getScrollData(firstResult, maxResult, null, null, orderby);
    }

    @Override
    public CpaResult<T> getScrollData(int firstResult, int maxResult,
                                      String where, Object[] params) {
        return getScrollData(firstResult, maxResult, where, params, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public CpaResult<T> getScrollData(int firstResult, int maxResult,
                                      String where, Object[] params, LinkedHashMap<String, String> orderby) {
        String entityName = clatt.getSimpleName();
        String whereql = where != null && !"".equals(where.trim()) ? " where "
                + where : "";
        Session session = getSession();
        Query query = session.createQuery("select o from " + entityName + " o"
                + whereql + buildOrderby(orderby));
        query.setCacheable(true);   //激活查询缓存,查询缓存,缓存的是对象的ID

        if (firstResult != -1 && maxResult != -1)
            query.setFirstResult(firstResult).setMaxResults(maxResult);
        setQueryParameter(query, params);

        CpaResult<T> result = new CpaResult<T>();
        Query queryCount = session.createQuery("select count(o) from "
                + entityName + " o" + whereql);
        setQueryParameter(queryCount, params);
        long count = (Long) queryCount.uniqueResult();
        result.setTotalCount(count);
        result.setData(query.setCacheable(true).list());

        return result;
    }

    /**
     * 设置查询参数
     *
     * @param query  查询对象
     * @param params 参数值
     */
    public static void setQueryParameter(Query query, Object[] params) {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
    }

    /**
     * 构建排序语句
     *
     * @param orderby 排序属性与asc/desc, Key为属性,Value为asc/desc
     * @return
     */
    public static String buildOrderby(LinkedHashMap<String, String> orderby) {
        StringBuilder sb = new StringBuilder();
        if (orderby != null && !orderby.isEmpty()) {
            sb.append(" order by ");
            for (Map.Entry<String, String> entry : orderby.entrySet()) {
                sb.append("o.").append(entry.getKey()).append(" ")
                        .append(entry.getValue()).append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}  