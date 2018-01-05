//package cn.kanyun.cpa.dao.common;
//
//import org.hibernate.*;
//import org.hibernate.boot.spi.SessionFactoryOptions;
//import org.hibernate.engine.spi.FilterDefinition;
//import org.hibernate.metadata.ClassMetadata;
//import org.hibernate.metadata.CollectionMetadata;
//import org.hibernate.stat.Statistics;
//
//import javax.naming.NamingException;
//import javax.naming.Reference;
//import java.io.Serializable;
//import java.sql.Connection;
//import java.util.Map;
//import java.util.Set;
//
///**
// * 动态切换数据源的工具类。关键有几点：
// * a、定义属性Map<Object, SessionFactory> targetSessionFactorys，通过Spring注入几个SessionFactory；
// * b、实现方法public SessionFactory getHibernateSessionFactory()，根据DataSourceContextHolder中存放的当前sessionFactory别名来从targetSessionFactorys属性中取出对应的SessionFactory；
// * c、实现SessionFactory，重写其所有方法，将原本直接调用session的地方，改为getHibernateSessionFactory()。
// */
//
///**
// * 此类可适用于，多数据源，但数据源不是一类数据库的情况，如mysql，oracle配置的多数据源，由于他们
// * 的数据库方言不一致，所以SessionFactory也不一致，其需要配置多个SessionFactory
// */
//@SuppressWarnings({"unchecked", "deprecation"})
//public class DynamicSessionFactory implements SessionFactory {
//    private static final long serialVersionUID = 156487979415646L;
//    private Map<Object, SessionFactory> targetSessionFactorys;
//    private SessionFactory defaultTargetSessionFactory;
//
//    public void setTargetSessionFactorys(Map<Object, SessionFactory> targetSessionFactorys) {
//        this.targetSessionFactorys = targetSessionFactorys;
//    }
//
//    public void setDefaultTargetSessionFactory(SessionFactory defaultTargetSessionFactory) {
//        this.defaultTargetSessionFactory = defaultTargetSessionFactory;
//    }
//
//    @Override
//    public SessionFactoryOptions getSessionFactoryOptions() {
//        return getHibernateSessionFactory().getSessionFactoryOptions();
//    }
//
//    @Override
//    public SessionBuilder withOptions() {
//        return getHibernateSessionFactory().withOptions();
//    }
//
//    @Override
//    public Session openSession() throws HibernateException {
//        return getHibernateSessionFactory().openSession();
//    }
//
//    @Override
//    public Session getCurrentSession() throws HibernateException {
//        return getHibernateSessionFactory().getCurrentSession();
//    }
//
//    @Override
//    public StatelessSessionBuilder withStatelessOptions() {
//        return getHibernateSessionFactory().withStatelessOptions();
//    }
//
//    @Override
//    public StatelessSession openStatelessSession() {
//        return getHibernateSessionFactory().openStatelessSession();
//    }
//
//    @Override
//    public StatelessSession openStatelessSession(Connection connection) {
//        return getHibernateSessionFactory().openStatelessSession(connection);
//    }
//
//    @Override
//    public ClassMetadata getClassMetadata(
//            @SuppressWarnings("rawtypes") Class entityClass) {
//        return getHibernateSessionFactory().getClassMetadata(entityClass);
//    }
//
//    @Override
//    public ClassMetadata getClassMetadata(String entityName) {
//        return getHibernateSessionFactory().getClassMetadata(entityName);
//    }
//
//    @Override
//    public CollectionMetadata getCollectionMetadata(String roleName) {
//        return getHibernateSessionFactory().getCollectionMetadata(roleName);
//    }
//
//    @Override
//    public Map<String, ClassMetadata> getAllClassMetadata() {
//        return getHibernateSessionFactory().getAllClassMetadata();
//    }
//
//    @SuppressWarnings("rawtypes")
//    @Override
//    public Map getAllCollectionMetadata() {
//        return getHibernateSessionFactory().getAllCollectionMetadata();
//    }
//
//    @Override
//    public Statistics getStatistics() {
//        return getHibernateSessionFactory().getStatistics();
//    }
//
//    @Override
//    public void close() throws HibernateException {
//        getHibernateSessionFactory().close();
//    }
//
//    @Override
//    public boolean isClosed() {
//        return getHibernateSessionFactory().isClosed();
//    }
//
//    @Override
//    public Cache getCache() {
//        return getHibernateSessionFactory().getCache();
//    }
//
//    @SuppressWarnings({"deprecation", "rawtypes"})
//    @Override
//    public void evict(Class persistentClass) throws HibernateException {
//        getHibernateSessionFactory().evict(persistentClass);
//    }
//
//    @SuppressWarnings({"deprecation", "rawtypes"})
//    @Override
//    public void evict(Class persistentClass, Serializable id)
//            throws HibernateException {
//        getHibernateSessionFactory().evict(persistentClass, id);
//    }
//
//    @SuppressWarnings("deprecation")
//    @Override
//    public void evictEntity(String entityName) throws HibernateException {
//        getHibernateSessionFactory().evictEntity(entityName);
//    }
//
//    @SuppressWarnings("deprecation")
//    @Override
//    public void evictEntity(String entityName, Serializable id)
//            throws HibernateException {
//        getHibernateSessionFactory().evictEntity(entityName, id);
//    }
//
//    @SuppressWarnings("deprecation")
//    @Override
//    public void evictCollection(String roleName) throws HibernateException {
//        getHibernateSessionFactory().evictCollection(roleName);
//    }
//
//    @SuppressWarnings("deprecation")
//    @Override
//    public void evictCollection(String roleName, Serializable id)
//            throws HibernateException {
//        getHibernateSessionFactory().evictCollection(roleName, id);
//    }
//
//    @SuppressWarnings("deprecation")
//    @Override
//    public void evictQueries(String cacheRegion) throws HibernateException {
//        getHibernateSessionFactory().evictQueries(cacheRegion);
//    }
//
//    @SuppressWarnings("deprecation")
//    @Override
//    public void evictQueries() throws HibernateException {
//        getHibernateSessionFactory().evictQueries();
//    }
//
//    @SuppressWarnings("rawtypes")
//    @Override
//    public Set getDefinedFilterNames() {
//        return getHibernateSessionFactory().getDefinedFilterNames();
//    }
//
//    @Override
//    public FilterDefinition getFilterDefinition(String filterName)
//            throws HibernateException {
//        return getHibernateSessionFactory().getFilterDefinition(filterName);
//    }
//
//    @Override
//    public boolean containsFetchProfileDefinition(String name) {
//        return getHibernateSessionFactory()
//                .containsFetchProfileDefinition(name);
//    }
//
//    @Override
//    public TypeHelper getTypeHelper() {
//
//        return getHibernateSessionFactory().getTypeHelper();
//    }
//
//    @Override
//    public Reference getReference() throws NamingException {
//        return getHibernateSessionFactory().getReference();
//    }
//
//    public SessionFactory getHibernateSessionFactory() {
//        SessionFactory targetSessionFactory = targetSessionFactorys
//                .get(DataSourceContextHolder.getSessionFactoryType());
//        if (targetSessionFactory != null) {
//            return targetSessionFactory;
//        } else if (defaultTargetSessionFactory != null) {
//            return defaultTargetSessionFactory;
//        }
//        return null;
//    }
//}
