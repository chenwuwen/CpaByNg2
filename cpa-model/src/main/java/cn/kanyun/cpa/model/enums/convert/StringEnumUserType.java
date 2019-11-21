package cn.kanyun.cpa.model.enums.convert;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Hibernate枚举转换String
 * Hibernate 枚举默认的转换是按照 枚举对象 在枚举类中定义的位置的数字(从0开始)进行转换的
 * @author Kanyun
 */
public class StringEnumUserType<E extends Enum<E>> implements UserType {

    private Class<E> clazz ;

    protected StringEnumUserType(Class<E> c) {
        this.clazz = c;
    }

    private static final int[] SQL_TYPES = {Types.VARCHAR};

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class<E> returnedClass() {
        return clazz;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }
        if (null == x || null == y) {
            return false;
        } else {
            return x.equals(y);
        }
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException {
        System.out.println(">>>>>>>>>>>>>Hibernate Enum 类型转换 nullSafeGet() 方法执行<<<<<<<<<<<<<<<");
        final String name = resultSet.getString(names[0]);
        if (!resultSet.wasNull()) {
            try {
                return clazz.getMethod("findByName", new Class[]{String.class}).invoke(null, name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException {
        System.out.println(">>>>>>>>>>>>>Hibernate Enum 类型转换 nullSafeSet() 方法执行<<<<<<<<<<<<<<<");
        if (null == value) {
            preparedStatement.setNull(index, SQL_TYPES[0]);
        } else {
            Convert myEnum = (Convert) value;
            preparedStatement.setString(index, myEnum.getName());
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

}
