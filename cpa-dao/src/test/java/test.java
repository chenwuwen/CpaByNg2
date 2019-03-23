import cn.kanyun.cpa.dao.HibernateSessionFactory;
import cn.kanyun.cpa.model.entity.system.CpaPermission;
import cn.kanyun.cpa.model.entity.system.UserRole;
import cn.kanyun.cpa.model.entity.user.CpaUser;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import java.util.*;

/**
 * Created by Administrator on 2017/6/20.
 */
public class test {

	public static void main(String[] args) {
		// Integer userId = 1;
		// exeute(userId);
		// exec3();
		exec4();

	}

	public static void exeute(Integer userId) {
		Session session = HibernateSessionFactory.getSession();
		String hql = " from UserRole o where o.userId = :userId";
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		List<UserRole> list = (List<UserRole>) query.list();
		System.out.print(list.get(0).getCpaRole().getRolePermissions());
	}

	@Test
	public void exeute2() {
		Session session = HibernateSessionFactory.getSession();
		String hql = " from CpaUser o where o.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", 1);
		List<CpaUser> list = query.list();
		System.out.print(list);
	}

	@Test
	public void exec3() {
		Session session = HibernateSessionFactory.getSession();
		String hql = " from CpaPermission o where o.id in (:id)";
		Query query = session.createQuery(hql);
		List list = new ArrayList();
		list.add(1);
		list.add(2);
		list.add(3);
		query.setParameterList("id", list);
		List<CpaPermission> list1 = query.list();
		for (CpaPermission cpaPermission : list1) {
			System.out.print(cpaPermission.getPermissionCode());
		}
	}

	public static void exec4() {
		try {
			for (int i = 0; i < 5; i++) {
				System.out.println(1);
				System.out.println(2);
				if (1 == 1) {
					throw new Exception();
				}
				System.out.println(3);
				System.out.println(4);
				System.out.println(5);
			}
		} catch (Exception e) {
			System.out.println("捕获异常");
		}

	}

	@Test
	public void exec5() {
		String s = "  aabbccdd ";
		System.out.println(s.length());
		String ss = StringUtils.stripToEmpty(s);
		s = s.trim();
		System.out.println(s.length());
		System.out.println(s);
		System.out.println(ss.length());
	}

	@Test
	public void exec6() {
		String s = "  aa23b4bc6c7dd8 ";
		System.out.println(StringUtils.getDigits(s));
	}

}
