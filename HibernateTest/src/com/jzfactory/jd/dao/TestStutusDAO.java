package com.jzfactory.jd.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jzfactory.jd.bean.Master;
import com.jzfactory.jd.util.BaseHibernateDAO;

/**
 * ����Hibernate����״̬��ת��
 * @author wangy 2016-10-11
 *
 */
public class TestStutusDAO extends BaseHibernateDAO {

	/**
	 * ������ʱ״̬���־�״̬��ת��
	 */
	public void testT2P()
	{
		
		Master master=new Master();
		//��ʱ̬
		master.setName("����ʦ");
		master.setSex(1);
		Session session=getSession();
		Transaction trans=session.beginTransaction();
		session.save(master);
		trans.commit();
		session.close();
		
		 //�־�̬
		master.setSex(0);
		session.close();
		//����̬
	    master.setName("aaaa");
		//��ʱ̬
		master.setId(1000);

	}
	/**
	 * ������ʱ״̬���־�״̬�󣬸��������ύ��
	 */
	public void testT2P2Update()
	{
		Master master=new Master();
		master.setName("����ʦ");
		master.setSex(0);
		Session session=getSession();
		Transaction trans=session.beginTransaction();
		session.save(master);
		master.setName("����ʦ");
		trans.commit();
		session.close();
		
	}
	/**
	 * ���Գ־�״̬�޸ĺ��ύ
	 */
	public void testP2Update()
	{
	
		Session session=getSession();
		Master master=(Master)session.get(Master.class, 4);
		Transaction trans=session.beginTransaction();
		
		//�˴�����������
		session.save(master);
		session.save(master);
		
		master.setSex(0);
		master.setName("����");
		trans.commit();
		session.close();

		
	}
	/**
	 * ���־�̬ת��Ϊ����̬������ύ
	 * clear(ȫ��) �� evict(һ��) �� session�ر�
	 */
	public void testP2D2Update()
	{
		Session session=getSession();
		//�־û�״̬
		Master master=(Master)session.get(Master.class, 4);
		//���־ö���ת��Ϊ�������
		session.evict(master);
		Transaction trans=session.beginTransaction();
		//����̬
		master.setName("������ʦ");
		trans.commit();
		session.close();
	}
	/**
	 * ���־�̬ת��Ϊ��ʱ̬
	 */
	public void testP2T()
	{
		Session session=getSession();
		//�־û�״̬
		Master master=(Master)session.get(Master.class, 4);
		Transaction trans=session.beginTransaction();
		session.delete(master);
		master.setName("aaa");
		trans.commit();
		session.close();
	}
	/**
	 * ͬ���־û�����
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
	 * ������̬ת���ɳ־�̬
	 */
	public void testD2P()
	{
		Session session=getSession();
		
		Transaction trans=session.beginTransaction();
		//��ʱ̬
		Master master=new Master();
		//����̬
		master.setId(3);
		master.setName("ͯ��ʦ");
		master.setSex(1);
		session.update(master);
		//�־�̬
		String name=master.getName();
		trans.commit();
	   
		
	}
	/**
	 * �־�״̬�޸�id (����)
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
	 * ����̬ת��Ϊ��ʱ״̬
	 */
	public void testD2S()
	{
        Session session=getSession();
		Transaction trans=session.beginTransaction();
		//��ʱ̬
		Master master=new Master();
		//����̬
		master.setId(2);
		session.delete(master);
		//��ʱ̬
		trans.commit();
		session.close();
	}
	/**
	 * �����ظ��ĳ־û�����(����)
	 */
	public void testDuplicateP()
	{
        Session session=getSession();
		Transaction trans=session.beginTransaction();
		//�־�̬
		Master master=(Master)session.get(Master.class, 3);
		//��ʱ̬
		Master master1=new Master();
		//����̬
		master1.setId(3);
		master1.setName("����");
		session.update(master1);
		//�־�̬
		System.out.println(master1);
		
		trans.commit();
		session.close();
		
	}
	/**
	 * �����ظ��ĳ־û�����
	 */
	public void testRemoveDupli()
	{
		Session session=getSession();
		Transaction trans=session.beginTransaction();
		//�־�̬
		Master master=(Master)session.get(Master.class, 3);
		//��ʱ̬
		Master master1=new Master();
		//����̬
		master1.setId(3);
		master1.setName("����");
		session.merge(master1);
		//�־�̬
		System.out.println(master1);
		//master.setName("һ������");
		master1.setName("��������");
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
