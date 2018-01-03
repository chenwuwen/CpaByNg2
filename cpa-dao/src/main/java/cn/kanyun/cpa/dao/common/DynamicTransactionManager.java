//package cn.kanyun.cpa.dao.common;
//
//import org.hibernate.SessionFactory;
//import org.springframework.orm.hibernate4.HibernateTransactionManager;
//import org.springframework.orm.hibernate4.SessionFactoryUtils;
//import javax.sql.DataSource;
//
///**
// * 新建HibernateTransactionManager的子类DynamicTransactionManager，重写getSessionFactory方法，
// * 不然取出来的不是SessionFactory，而是DynamicSessionFactory。
// */
//public class DynamicTransactionManager extends HibernateTransactionManager {
//
//    private static final long serialVersionUID = 2591641126163504954L;
//
//    @Override
//    public DataSource getDataSource() {
//        return SessionFactoryUtils.getDataSource(getSessionFactory());
//    }
//
//    @Override
//    public SessionFactory getSessionFactory() {
//        DynamicSessionFactory dynamicSessionFactory = (DynamicSessionFactory) super.getSessionFactory();
//        SessionFactory hibernateSessionFactory = dynamicSessionFactory.getHibernateSessionFactory();
//        return hibernateSessionFactory;
//    }
//}
