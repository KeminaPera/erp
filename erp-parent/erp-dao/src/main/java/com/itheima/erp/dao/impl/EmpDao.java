package com.itheima.erp.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itheima.erp.dao.IEmpDao;
import com.itheima.erp.entity.Emp;
/**
 * dao层员工的实现类
 * @author KeminaPera
 *
 */
public class EmpDao extends BaseDao<Emp> implements IEmpDao {

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 用户登录
	 */
	public Emp login(String username, String pwd) {
		String hql = "from Emp where username = ? and pwd = ?";
		List<Emp> list = (List<Emp>) this.getHibernateTemplate().find(hql, username, pwd);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	/**
	 * 添加条件
	 */
	public DetachedCriteria addCriteria(Emp emp1, Emp emp2) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Emp.class);
		if(emp1 != null) {
			String temp = null;
			if((temp = emp1.getTele()) != null && temp.trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("tele", temp, MatchMode.ANYWHERE));
			}
			if((temp = emp1.getName()) != null && temp.trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("name", temp, MatchMode.ANYWHERE));
			}
			Long number = null;
			if((number = emp1.getGender()) != null) {
				detachedCriteria.add(Restrictions.eq("gender", number));
			}
			if(emp1.getDep() !=null && (number = emp1.getDep().getUuid()) != null) {
				detachedCriteria.add(Restrictions.eq("dep", emp1.getDep()));
			}
		}
		return detachedCriteria;
	}

	/**
	 * 修改密码
	 * @param uuid ：用户ID
	 * @param encryptPwd : 加密后的密码
	 */
	public void updatePwd(Long uuid, String encryptPwd) {

		String hql = "update Emp set pwd = ? where uuid = ?";
		this.getHibernateTemplate().bulkUpdate(hql, encryptPwd, uuid);
	}

}
