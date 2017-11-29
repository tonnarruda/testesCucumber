package com.fortes.rh.test.util;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.util.CollectionUtil;

public class CollectionUtilTest_JUnit4 extends TestCase
{
	@Before
	public void setUp() throws Exception
	{
	}
	@Test
	public void testSortCollectionBooleanAsc()
	{
		Collection<BeanBasico> beans = new ArrayList<BeanBasico>();

		BeanBasico b1 = new BeanBasico();
		b1.setId(1L);
		b1.setAtivo(Boolean.TRUE);

		BeanBasico b2 = new BeanBasico();
		b2.setId(2L);
		b2.setAtivo(Boolean.TRUE);

		BeanBasico b3 = new BeanBasico();
		b3.setId(3L);
		b3.setAtivo(Boolean.FALSE);

		BeanBasico b4 = new BeanBasico();
		b4.setId(4L);
		b4.setAtivo(Boolean.FALSE);
		
		BeanBasico b5 = new BeanBasico();
		b5.setId(5L);
		b5.setAtivo(Boolean.FALSE);

		beans.add(b1);
		beans.add(b2);
		beans.add(b3);
		beans.add(b4);
		beans.add(b5);
		
		CollectionUtil<BeanBasico> collectionUtil = new CollectionUtil<BeanBasico>();
		Collection<BeanBasico> list = collectionUtil.sortCollectionBoolean(beans, "ativo","asc");
		
		assertEquals(Boolean.FALSE, ((BeanBasico)list.toArray()[0]).getAtivo());
		assertEquals(Boolean.FALSE, ((BeanBasico)list.toArray()[1]).getAtivo());
		assertEquals(Boolean.FALSE, ((BeanBasico)list.toArray()[2]).getAtivo());
		assertEquals(Boolean.TRUE, ((BeanBasico)list.toArray()[3]).getAtivo());
		assertEquals(Boolean.TRUE, ((BeanBasico)list.toArray()[4]).getAtivo());
	}
	
	@Test
	public void testSortCollectionBooleanDesc()
	{
		Collection<BeanBasico> beans = new ArrayList<BeanBasico>();
		
		BeanBasico b1 = new BeanBasico();
		b1.setId(1L);
		b1.setAtivo(Boolean.TRUE);
		
		BeanBasico b2 = new BeanBasico();
		b2.setId(2L);
		b2.setAtivo(Boolean.TRUE);
		
		BeanBasico b3 = new BeanBasico();
		b3.setId(3L);
		b3.setAtivo(Boolean.FALSE);
		
		BeanBasico b4 = new BeanBasico();
		b4.setId(4L);
		b4.setAtivo(Boolean.FALSE);
		
		BeanBasico b5 = new BeanBasico();
		b5.setId(5L);
		b5.setAtivo(Boolean.FALSE);
		
		beans.add(b1);
		beans.add(b2);
		beans.add(b3);
		beans.add(b4);
		beans.add(b5);
		
		CollectionUtil<BeanBasico> collectionUtil = new CollectionUtil<BeanBasico>();
		Collection<BeanBasico> list = collectionUtil.sortCollectionBoolean(beans, "ativo","desc");
		
		assertEquals(Boolean.TRUE, ((BeanBasico)list.toArray()[0]).getAtivo());
		assertEquals(Boolean.TRUE, ((BeanBasico)list.toArray()[1]).getAtivo());
		assertEquals(Boolean.FALSE, ((BeanBasico)list.toArray()[2]).getAtivo());
		assertEquals(Boolean.FALSE, ((BeanBasico)list.toArray()[3]).getAtivo());
		assertEquals(Boolean.FALSE, ((BeanBasico)list.toArray()[4]).getAtivo());
		
	}
}