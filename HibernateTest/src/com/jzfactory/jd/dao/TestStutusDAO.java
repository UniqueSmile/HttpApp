package com.jzfactory.jd.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jzfactory.jd.bean.Master;
import com.jzfactory.jd.util.BaseHibernateDAO;

/**
 * 测试Hibernate三种状态的转换
 * @author wangy 2016-10-11
 *
 */
public class TestStutusDAO extends BaseHibernateDAO {

	/**
	 * 测试临时状态到持久状态的转变
	 */
	public void testT2P()
	{
		
		Master master=new Master();
		//临时态
		master.setName("陈老师");
		master.setSex(1);
		Session session=getSession();
		Transaction trans=session.beginTransaction();
		session.save(master);
		trans.commit();
		session.close();
		
		 //持久态
		master.setSex(0);
		session.close();
		//游离态
	    master.setName("aaaa");
		//临时态
		master.setId(1000);

	}
	/**
	 * 测试临时状态到持久状态后，更改属性提交。
	 */
	public void testT2P2Update()
	{
		Master master=new Master();
		master.setName("孙老师");
		master.setSex(0);
		Session session=getSession();
		Transaction trans=session.beginTransaction();
		session.save(master);
		master.setName("孙老师");
		trans.commit();
		session.close();
		
	}
	/**
	 * 测试持久状态修改后提交
	 */
	public void testP2Update()
	{
	
		Session session=getSession();
		Master master=(Master)session.get(Master.class, 4);
		Transaction trans=session.beginTransaction();
		
		//此处保存无意义
		session.save(master);
		session.save(master);
		
		master.setSex(0);
		master.setName("许嵩");
		trans.commit();
		session.close();

		
	}
	/**
	 * 将持久态转换为游离态后更新提交
	 * clear(全部) 或 evict(一个) 或 session关闭
	 */
	public void testP2D2Update()
	{
		Session session=getSession();
		//持久化状态
		Master master=(Master)session.get(Master.class, 4);
		//将持久对象转换为游离对象
		session.evict(master);
		Transaction trans=session.beginTransaction();
		//游离态
		master.setName("老张老师");
		trans.commit();
		session.close();
	}
	/**
	 * 将持久态转换为临时态
	 */
	public void testP2T()
	{
		Session session=getSession();
		//持久化状态
		Master master=(Master)session.get(Master.class, 4);
		Transaction trans=session.beginTransaction();
		session.delete(master);
		master.setName("aaa");
		trans.commit();
		session.close();
	}
	/**
	 * 同步持久化对象
	 */
	public void testRefresh()
	{
		Session session=getSession();
		Master master=(Master)session.get(Master.class, 2);
		System.out.println("before:"+master.getName());
		Transaction trans=session.beginTransaction();
		trans.commit();
		session.refresh(master);
		System.out.println("after:"+master.getName());
		session.close();
		
	}
	/**
	 * 将游离态转换成持久态
	 */
	public void testD2P()
	{
		Session session=getSession();
		
		Transaction trans=session.beginTransaction();
		//临时态
		Master master=new Master();
		//游离态
		master.setId(3);
		master.setName("童老师");
		master.setSex(1);
		session.update(master);
		//持久态
		String name=master.getName();
		trans.commit();
	   
		
	}
	/**
	 * 持久状态修改id (报错)
	 */
	public void testP2EditId()
	{
        Session session=getSession();
		Transaction trans=session.beginTransaction();
		Master master=(Master)session.get(Master.class, 2);
		master.setId(100);
		trans.commit();
		session.close();
	}
	/**
	 * 游离态转换为暂时状态
	 */
	public void testD2S()
	{
        Session session=getSession();
		Transaction trans=session.beginTransaction();
		//临时态
		Master master=new Master();
		//游离态
		master.setId(2);
		session.delete(master);
		//临时态
		trans.commit();
		session.close();
	}
	/**
	 * 测试重复的持久化对象(报错)
	 */
	public void testDuplicateP()
	{
        Session session=getSession();
		Transaction trans=session.beginTransaction();
		//持久态
		Master master=(Master)session.get(Master.class, 3);
		//临时态
		Master master1=new Master();
		//游离态
		master1.setId(3);
		master1.setName("游离");
		session.update(master1);
		//持久态
		System.out.println(master1);
		
		trans.commit();
		session.close();
		
	}
	/**
	 * 消除重复的持久化对象
	 */
	public void testRemoveDupli()
	{
		Session session=getSession();
		Transaction trans=session.beginTransaction();
		//持久态
		Master master=(Master)session.get(Master.class, 3);
		//临时态
		Master master1=new Master();
		//游离态
		master1.setId(3);
		master1.setName("游离");
		session.merge(master1);
		//持久态
		System.out.println(master1);
		//master.setName("一个游离");
		master1.setName("两个游离");
		trans.commit();
		
		session.close();
	}

	public static void main(String[] args) {
		TestStutusDAO td=new TestStutusDAO();
		//td.testT2P();
		//td.testT2P2Update();
		//td.testP2Update();
		//td.testP2D2Update();
		//td.testP2T();
		//td.testRefresh();
		//td.testD2P();
		//td.testP2EditId();
		//td.testD2S();
		//td.testDuplicateP();
		td.testRemoveDupli();
	}
}
