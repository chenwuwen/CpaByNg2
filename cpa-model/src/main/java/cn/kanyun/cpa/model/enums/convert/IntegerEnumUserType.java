package cn.kanyun.cpa.model.enums.convert;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

/**
 * Hibernate枚举转换Integer
 * Hibernate 枚举默认的转换是按照 枚举对象 在枚举类中定义的位置的数字(从0开始)进行转换的
 *
 * @author Kanyun
 */
public class IntegerEnumUserType<E extends Enum<E>> implements UserType {

    private Class<E> clazz;

    protected IntegerEnumUserType(Class<E> c) {
        this.clazz = c;
    }

    private static final int[] SQL_TYPES = {Types.INTEGER};

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
        final int id = resultSet.getInt(names[0]);
        if (!resultSet.wasNull()) {
            try {
                return clazz.getMethod("findById", new Class[]{Integer.class}).invoke(null, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException {
        if (null == value) {
            preparedStatement.setNull(index, SQL_TYPES[0]);
        } else {
            Convert myEnum = (Convert) value;
//            看具体定义什么方法
//            preparedStatement.setInt(index, myEnum.getId());
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
