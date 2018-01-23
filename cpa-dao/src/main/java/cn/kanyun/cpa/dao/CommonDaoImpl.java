package cn.kanyun.cpa.dao;

import cn.kanyun.cpa.model.entity.CpaResult;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 不一定必须是abstract类型的， 请不要对CommonDaoImpl使用@Repository注解，因为无法直接指定clatt属性值
 * class值由继承类来指定
 *
 * @param <T>
 * @author BearSmall
 */
public abstract class CommonDaoImpl<K extends Serializable, T extends Serializable>
        implements CommonDao<K, T> {
    @Resource
    private SessionFactory sessionFactory; // 从容器中注入session工厂【无需get,set方法】
//    private DynamicSessionFactory dynamicSessionFactory; // 从容器中注入session工厂【无需get,set方法】(动态SessionFactory)

    private Class<T> clatt; // 【实体类对应的Class对象】

    /**
     * //向子类暴露的接口获用来获取sessionFactory
     *
     * @return sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
//    public DynamicSessionFactory getSessionFactory() {
//        return dynamicSessionFactory;
//    }

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


  /*  采用getCurrentSession()创建的session会绑定到当前线程中，
    而采用openSession()创建的session则不会。
    采用getCurrentSession()创建的session在commit或rollback时会自动关闭，
    而采用openSession()创建的session必须手动关闭。
    使用getCurrentSession()需要在hibernate.cfg.xml文件中加入如下配置：
    *如果使用的是本地事务（jdbc事务）
    <property name="hibernate.current_session_context_class">thread</property>
    *如果使用的是全局事务（jta事务）
    <property name="hibernate.current_session_context_class">jta</property>
    如果采用的时Hibernate4，使用getCurrentSession()必须配置事务，否则无法取到session*/

    @Override
    public Session getSession() {
        return getSessionFactory().getCurrentSession();
    }

    @Override
    public void evict(T t) {
        Session session = getSession();
        session.evict(t);
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
    public T loadById(K id) {
        Session session = getSession();
        @SuppressWarnings("unchecked")
        T t = (T) session.load(clatt, id);
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
        session.flush();
        session.clear();
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
        if (t == null) {
            return false;
        }
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
                + buildWhere(where) : "";
        Session session = getSession();
        Query query = session.createQuery("select o from " + entityName + " o"
                + whereql + buildOrderby(orderby));
        query.setCacheable(true);   //激活查询缓存,查询缓存,缓存的是对象的ID
        if (firstResult != -1 && maxResult != -1) {
            query.setFirstResult(firstResult).setMaxResults(maxResult);
        }
        setQueryParameter(query, params);

        CpaResult<T> result = new CpaResult<T>();
        Query queryCount = session.createQuery("select count(o) from "
                + entityName + " o" + whereql);
        setQueryParameter(queryCount, params);
        long count = (Long) queryCount.uniqueResult();
        result.setTotalCount(count);
        result.setData(query.list());

        return result;
    }

    @Override
    public long getTotalCount(String where, Object[] params) {
        String entityName = clatt.getSimpleName();
        String whereql = where != null && !"".equals(where.trim()) ? " where "
                + where : "";
        Session session = getSession();
        Query queryCount = session.createQuery("select count(o) from "
                + entityName + " o" + whereql);
        setQueryParameter(queryCount, params);
        long count = (Long) queryCount.uniqueResult();
        return count;
    }

    @Override
    public List<Map<Object, Object>> getGroupByList(Object[] fields, String where, Map<String, Collection> params) {
        String entityName = clatt.getSimpleName();
        String whereql = where != null && !"".equals(where.trim()) ? " where "
                + buildWhere(where) : "";
        Session session = getSession();
        Query queryList = session.createQuery("select count(o) as count, " + buildGroupBy(0, fields) + " from "
                + entityName + " o" + whereql + " group by " + buildGroupBy(1, fields));
        Iterator iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Collection> entry = (Map.Entry<String, Collection>) iterator.next();
            queryList.setParameterList(entry.getKey(), entry.getValue());
        }
/*        设置Hibernate返回值类型为Map(但是其是返回了一个封装所有Map的List,ALIAS_TO_ENTITY_MAP是指以数据库的字段为Key,
          以字段内容为value的Map,有几个字段就会生成几个Map,然后将所有Map封装到List中),不过随着Hibernate的发展,
          可以直接在Hql中直接使用集合查询语句(如list，map)*/
        queryList.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
        List<Map<Object, Object>> list = queryList.list();
        return list;
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
     * @param orderby 排序属性与asc/desc, Key为属性(即要排序的字段),Value为asc/desc
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
        } else {
            sb.append("order by o.id desc");
        }
        return sb.toString();
    }

    /**
     * 构建group by语句
     *
     * @param groupby 传入需要进行分组查询的参数
     * @return
     */
    public static String buildGroupBy(Integer num, Object[] fields) {
        StringBuilder sb = new StringBuilder();
        if (num == 0) {
            if (fields != null) {
                for (int i = 0; i < fields.length; i++) {
                    sb.append("o." + fields[i] + " as " + fields[i] + ",");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
        } else {
            if (fields != null) {
                for (int i = 0; i < fields.length; i++) {
                    sb.append("o." + fields[i] + ",");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
        }

        return sb.toString();
    }

    /**
     * 组织where语句,当参数有查询参数有多个时,where语句可能存在多个 "and" 字符串,
     * 该方法取出多余 "and" 字符串,返回正确的where,此处不必判空,如若where为空,不会执行此方法
     *
     * @param where
     * @return
     */
    public static String buildWhere(String where) {
        //去掉where开头结尾空格(此处没有使用String的trim方法,是因为String的trim方法有一些其他缺陷,如不能去掉全角空格),需要使用where接收返回值,否则where值不变
        where = StringUtils.stripToEmpty(where);
        int index = where.indexOf("and");
        /**
         * index == -1 表示当前where语句只存在一个参数,即没有and参数
         * index == 0 表示and 第一次出现在where语句的最前面
         */
        if (index != -1 && index == 0) {
//            where.substring(3);
            where = StringUtils.substringAfter(where, "and");
        }
        return where;
    }


}  