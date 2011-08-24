package com.fortes.rh.test.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import junit.framework.TestCase;

import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.util.LongUtil;

public class LongUtilTest extends TestCase
{
	@SuppressWarnings("unused")
	protected void setUp(){
		LongUtil longUtil = new LongUtil();
	}

	public void testCollectionStringToArrayLong(){

		Collection<String> colecao = new ArrayList<String>();

		assertEquals("Test 1", 0, LongUtil.collectionStringToArrayLong(colecao).length);

		colecao.add("10");
		colecao.add("15");
		colecao.add("20");
		colecao.add("30");
		colecao.add("40");

		Long[] longs = LongUtil.collectionStringToArrayLong(colecao);

		assertEquals("Test 2", colecao.size(), longs.length);
		assertEquals("Test 3", Long.valueOf(10), longs[0]);
		assertEquals("Test 4", Long.valueOf(15), longs[1]);
		assertEquals("Test 5", Long.valueOf(20), longs[2]);
		assertEquals("Test 6", Long.valueOf(30), longs[3]);
		assertEquals("Test 7", Long.valueOf(40), longs[4]);
	}

	public void testArrayStringToCollectionLong(){

		String[] strings = null;

		assertEquals("Test 1", 0, LongUtil.arrayStringToCollectionLong(strings).size());

		strings = new String[5];
		strings[0] = "10";
		strings[1] = "15";
		strings[2] = "20";
		strings[3] = "30";
		strings[4] = "40";

		Collection<Long> colLongs = LongUtil.arrayStringToCollectionLong(strings);
		Long[] longs = colLongs.toArray(new Long[]{});

		assertEquals("Test 2", strings.length, longs.length);
		assertEquals("Test 3", Long.valueOf(10), longs[0]);
		assertEquals("Test 4", Long.valueOf(15), longs[1]);
		assertEquals("Test 5", Long.valueOf(20), longs[2]);
		assertEquals("Test 6", Long.valueOf(30), longs[3]);
		assertEquals("Test 7", Long.valueOf(40), longs[4]);
	}
	
	public void testArrayLongToCollectionLong(){
		
		Long[] longs = null;
		
		assertEquals("Test 1", 0, LongUtil.arrayLongToCollectionLong(longs).size());
		
		longs = new Long[5];
		longs[0] = 10L;
		longs[1] = 15L;
		longs[2] = 20L;
		longs[3] = 30L;
		longs[4] = 40L;
		
		Collection<Long> colLongs = LongUtil.arrayLongToCollectionLong(longs);
		Long[] longsCheck = colLongs.toArray(new Long[]{});
		
		assertEquals("Test 2", longs.length, longs.length);
		assertEquals("Test 3", Long.valueOf(10), longsCheck[0]);
		assertEquals("Test 4", Long.valueOf(15), longsCheck[1]);
		assertEquals("Test 5", Long.valueOf(20), longsCheck[2]);
		assertEquals("Test 6", Long.valueOf(30), longsCheck[3]);
		assertEquals("Test 7", Long.valueOf(40), longsCheck[4]);
	}

	public void testArrayStringToArrayLong(){

		String[] strings = null;

		assertEquals("Test 1", 0, LongUtil.arrayStringToCollectionLong(strings).size());

		strings = new String[5];
		strings[0] = "10";
		strings[1] = "15";
		strings[2] = "20";
		strings[3] = "30";
		strings[4] = "40";

		Long[] longs = LongUtil.arrayStringToArrayLong(strings);

		assertEquals("Test 2", strings.length, longs.length);
		assertEquals("Test 3", Long.valueOf(10), longs[0]);
		assertEquals("Test 4", Long.valueOf(15), longs[1]);
		assertEquals("Test 5", Long.valueOf(20), longs[2]);
		assertEquals("Test 6", Long.valueOf(30), longs[3]);
		assertEquals("Test 7", Long.valueOf(40), longs[4]);

		assertEquals("Test 8", new Long[0].length, LongUtil.arrayStringToArrayLong(null).length);
	}
	
	public void testContains()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(100L);
		Colaborador colaborador2 = ColaboradorFactory.getEntity(200L);
		Collection<Colaborador> colaboradors =  Arrays.asList(colaborador,colaborador2);
		
		assertTrue(LongUtil.contains(100L, colaboradors));
		assertTrue(LongUtil.contains(200L, colaboradors));
		assertFalse(LongUtil.contains(1L, colaboradors));
	}
	
	public void testIsNotEmpty()
	{
		assertFalse(LongUtil.isNotEmpty(null));
		assertFalse(LongUtil.isNotEmpty(new Long[]{}));
		assertTrue(LongUtil.isNotEmpty(new Long[]{1L}));
		assertTrue(LongUtil.isNotEmpty(new Long[]{1L,2L}));
	}

	public void testCollectionToCollectionLong()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity(100L);
		Colaborador colaborador2 = ColaboradorFactory.getEntity(200L);
		Collection<Colaborador> colaboradors =  Arrays.asList(colaborador,colaborador2);
		
		assertEquals(2, LongUtil.collectionToCollectionLong(colaboradors).size());
		assertEquals(colaborador.getId(), LongUtil.collectionToCollectionLong(colaboradors).toArray()[0]);
	}
	
}
