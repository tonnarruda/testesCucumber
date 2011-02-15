package com.fortes.rh.test.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.util.CollectionUtil;

public class CollectionUtilTest extends TestCase
{
	public void testConvertCollectionToMap()
	{

		Collection<Usuario> users = new ArrayList<Usuario>();

		Usuario u1 = new Usuario();
		u1.setId(1L);
		u1.setNome("nome 1");

		Usuario u2 = new Usuario();
		u2.setId(2L);
		u2.setNome("nome 2");

		Usuario u3 = new Usuario();
		u3.setId(3L);
		u3.setNome("nome 3");

		Usuario u4 = new Usuario();
		u4.setId(3L);
		u4.setNome("nome 3 X");

		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);

		CollectionUtil<Usuario> cu = new CollectionUtil<Usuario>();
		Map mapa = cu.convertCollectionToMap(users, "getId", "getNome");

		assertEquals("Test 1", 3, mapa.size());
		assertEquals("Test 2", "nome 1", mapa.get(1L));
		assertEquals("Test 3", "nome 2", mapa.get(2L));
		assertEquals("Test 4", "nome 3 X", mapa.get(3L));

		mapa = cu.convertCollectionToMap(null, "getId", "getNome");

		assertNull(mapa);

	}
	
	public void testConvertArrayStringToCollection() throws Exception
	{
		String[] array = new String[]{"1","2"};
		CollectionUtil<AvaliacaoCurso> collectionUtil = new CollectionUtil<AvaliacaoCurso>(); 
		Collection<AvaliacaoCurso> avaliacoes = collectionUtil.convertArrayStringToCollection(AvaliacaoCurso.class, array);  
		assertEquals(2, avaliacoes.size());
		Long id1 = 1L;
		assertEquals(id1, ((AvaliacaoCurso)avaliacoes.toArray()[0]).getId());
	}
	
	public void testConvertArrayLongToCollection() throws Exception
	{
		Long[] array = new Long[]{1L, 2L};
		CollectionUtil<AvaliacaoCurso> collectionUtil = new CollectionUtil<AvaliacaoCurso>(); 
		Collection<AvaliacaoCurso> avaliacoes = collectionUtil.convertArrayLongToCollection(AvaliacaoCurso.class, array);  
		assertEquals(2, avaliacoes.size());
		Long id1 = 1L;
		assertEquals(id1, ((AvaliacaoCurso)avaliacoes.toArray()[0]).getId());
	}
	
	public void testConvertCollectionToMap1Exception()
	{
		Collection<Usuario> users = new ArrayList<Usuario>();
		users.add(null);
		
		CollectionUtil<Usuario> cu = new CollectionUtil<Usuario>();
		
		assertEquals(null, cu.convertCollectionToMap(users, "", ""));
	}
	
	public void testConvertCollectionToArrayIdsException()
	{
		Collection<Usuario> users = new ArrayList<Usuario>();
		users.add(null);
		
		CollectionUtil<Usuario> cu = new CollectionUtil<Usuario>();
		
		assertEquals(null, cu.convertCollectionToArrayIds(users));
	}
	
	public void testConvertCollectionToArrayStringException()
	{
		Collection<Usuario> users = new ArrayList<Usuario>();
		users.add(null);
		
		CollectionUtil<Usuario> cu = new CollectionUtil<Usuario>();
		
		assertEquals(null, cu.convertCollectionToArrayString(users, ""));
	}
	
	public void testConvertCollectionToMapException()
	{
		Collection<Usuario> users = new ArrayList<Usuario>();
		users.add(null);
		
		CollectionUtil<Usuario> cu = new CollectionUtil<Usuario>();
		
		assertEquals(null, cu.convertCollectionToArrayString(users, ""));
	}

	public void testConvertCollectionToMapCollMap()
	{
		Collection<Usuario> users = new ArrayList<Usuario>();

		Usuario u1 = new Usuario();
		u1.setId(1L);
		u1.setNome("nome 1");

		users.add(u1);

		CollectionUtil<Usuario> cu = new CollectionUtil<Usuario>();
		Map mapa = cu.convertCollectionToMap(users, "getNome");

		assertEquals("Test 1", 1, mapa.size());
	}
	
	public void testConvertCollectionToMapCollMapException()
	{
		Collection<Usuario> users = new ArrayList<Usuario>();
		users.add(null);
		
		CollectionUtil<Usuario> cu = new CollectionUtil<Usuario>();
		
		assertEquals(null, cu.convertCollectionToMap(users, ""));
	}

	public void testConvertCollectionToArrayIds()
	{

		Collection<Usuario> users = new ArrayList<Usuario>();

		Usuario u1 = new Usuario();
		u1.setId(1L);
		u1.setNome("nome 1");

		Usuario u2 = new Usuario();
		u2.setId(2L);
		u2.setNome("nome 2");

		Usuario u3 = new Usuario();
		u3.setId(3L);
		u3.setNome("nome 3");

		Usuario u4 = new Usuario();
		u4.setId(4L);
		u4.setNome("nome 4");

		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);

		CollectionUtil<Usuario> cu = new CollectionUtil<Usuario>();
		Long[] ids = cu.convertCollectionToArrayIds(users);

		assertEquals("Test 1", 4, ids.length);
		assertEquals("Test 2", u1.getId(), ids[0]);
		assertEquals("Test 3", u2.getId(), ids[1]);
		assertEquals("Test 4", u3.getId(), ids[2]);
		assertEquals("Test 5", u4.getId(), ids[3]);

		ids = cu.convertCollectionToArrayIds(null);

		assertNull("Test 6", ids);
	}

	public void testConvertCollectionToArrayString()
	{

		Collection<Usuario> users = new ArrayList<Usuario>();

		Usuario u1 = new Usuario();
		u1.setId(1L);
		u1.setNome("nome 1");

		Usuario u2 = new Usuario();
		u2.setId(2L);
		u2.setNome("nome 2");

		Usuario u3 = new Usuario();
		u3.setId(3L);
		u3.setNome("nome 3");

		Usuario u4 = new Usuario();
		u4.setId(4L);
		u4.setNome("nome 4");

		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);

		CollectionUtil<Usuario> cu = new CollectionUtil<Usuario>();
		String[] nomes = cu.convertCollectionToArrayString(users, "getNome");

		assertEquals("Test 1", 4, nomes.length);
		assertEquals("Test 2", u1.getNome(), nomes[0]);
		assertEquals("Test 3", u2.getNome(), nomes[1]);
		assertEquals("Test 4", u3.getNome(), nomes[2]);
		assertEquals("Test 5", u4.getNome(), nomes[3]);

		nomes = cu.convertCollectionToArrayString(null, "getNome");

		assertNull("Test 6", nomes);
	}

	public void testConvertCollectionToMap2()
	{

		Collection<Usuario> users = new ArrayList<Usuario>();

		Usuario u1 = new Usuario();
		u1.setId(1L);
		u1.setNome("nome 1");

		Usuario u2 = new Usuario();
		u2.setId(2L);
		u2.setNome("nome 2");

		Usuario u3 = new Usuario();
		u3.setId(3L);
		u3.setNome("nome 3");

		Usuario u4 = new Usuario();
		u4.setId(3L);
		u4.setNome("nome 3 X");

		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);

		Map mapa = CollectionUtil.convertCollectionToMap(users, "getId", "getNome", Usuario.class);

		assertEquals("Test 1", 3, mapa.size());
		assertEquals("Test 2", "nome 1", mapa.get(1L));
		assertEquals("Test 3", "nome 2", mapa.get(2L));
		assertEquals("Test 4", "nome 3 X", mapa.get(3L));

		mapa = CollectionUtil.convertCollectionToMap(null, "getId", "getNome", Usuario.class);

		assertNull("Test 5", mapa);
	}

	public void testConvertCollectionToList()
	{

		Collection<Usuario> users = new ArrayList<Usuario>();

		Usuario u1 = new Usuario();
		u1.setId(1L);
		u1.setNome("nome 1");

		Usuario u2 = new Usuario();
		u2.setId(2L);
		u2.setNome("nome 2");

		Usuario u3 = new Usuario();
		u3.setId(3L);
		u3.setNome("nome 3");

		Usuario u4 = new Usuario();
		u4.setId(3L);
		u4.setNome("nome 3 X");

		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);

		CollectionUtil<Usuario> cu = new CollectionUtil<Usuario>();
		List list = cu.convertCollectionToList(users);

		assertEquals("Test 1", 4, list.size());

	}

	public void testDistinctCollection()
	{

		Collection<Usuario> users = new ArrayList<Usuario>();

		Usuario u1 = new Usuario();
		u1.setId(1L);
		u1.setNome("nome 1");

		Usuario u2 = new Usuario();
		u2.setId(2L);
		u2.setNome("nome 2");

		Usuario u3 = new Usuario();
		u3.setId(3L);
		u3.setNome("nome 3");

		Usuario u4 = new Usuario();
		u4.setId(4L);
		u4.setNome("nome 4");

		users.add(u1);
		users.add(u1);
		users.add(u1);
		users.add(u2);
		users.add(u2);
		users.add(u2);
		users.add(u3);
		users.add(u3);
		users.add(u3);
		users.add(u3);
		users.add(u3);
		users.add(u3);
		users.add(u4);
		users.add(u4);
		users.add(u4);
		users.add(u4);

		CollectionUtil<Usuario> cu = new CollectionUtil<Usuario>();
		Collection<Usuario> list = cu.distinctCollection(users);

		assertEquals("Test 1", 4, list.size());

	}

	public void testSortCollection()
	{
		Collection<BeanBasico> beanss = new ArrayList<BeanBasico>();

		BeanBasico b1 = new BeanBasico();
		b1.setId(1L);
		b1.setOrdem(1);

		BeanBasico b2 = new BeanBasico();
		b2.setId(2L);
		b2.setOrdem(2);

		BeanBasico b3 = new BeanBasico();
		b3.setId(3L);
		b3.setOrdem(3);

		BeanBasico b4 = new BeanBasico();
		b4.setId(4L);
		b4.setOrdem(4);

		BeanBasico b5 = new BeanBasico();
		b5.setId(5L);
		b5.setOrdem(5);

		beanss.add(b5);
		beanss.add(b4);
		beanss.add(b3);
		beanss.add(b2);
		beanss.add(b1);

		assertEquals("Test 1", b1, beanss.toArray()[4]);
		assertEquals("Test 2", b2, beanss.toArray()[3]);
		assertEquals("Test 3", b3, beanss.toArray()[2]);
		assertEquals("Test 4", b4, beanss.toArray()[1]);
		assertEquals("Test 5", b5, beanss.toArray()[0]);

		CollectionUtil<BeanBasico> cu = new CollectionUtil<BeanBasico>();
		Collection<BeanBasico> beanssSort = cu.sortCollection(beanss, "ordem");

		assertEquals("Test 6", 5, beanssSort.size());

		assertEquals("Test 7", b1, beanssSort.toArray()[0]);
		assertEquals("Test 8", b2, beanssSort.toArray()[1]);
		assertEquals("Test 9", b3, beanssSort.toArray()[2]);
		assertEquals("Test 10", b4, beanssSort.toArray()[3]);
		assertEquals("Test 11", b5, beanssSort.toArray()[4]);
	}

	public void testSortCollectionDesc()
	{
		Collection<BeanBasico> beanss = new ArrayList<BeanBasico>();

		BeanBasico b1 = new BeanBasico();
		b1.setId(1L);
		b1.setOrdem(1);

		BeanBasico b2 = new BeanBasico();
		b2.setId(2L);
		b2.setOrdem(2);

		BeanBasico b3 = new BeanBasico();
		b3.setId(3L);
		b3.setOrdem(3);

		BeanBasico b4 = new BeanBasico();
		b4.setId(4L);
		b4.setOrdem(4);

		BeanBasico b5 = new BeanBasico();
		b5.setId(5L);
		b5.setOrdem(5);

		beanss.add(b1);
		beanss.add(b2);
		beanss.add(b3);
		beanss.add(b4);
		beanss.add(b5);

		assertEquals("Test 1", b1, beanss.toArray()[0]);
		assertEquals("Test 2", b2, beanss.toArray()[1]);
		assertEquals("Test 3", b3, beanss.toArray()[2]);
		assertEquals("Test 4", b4, beanss.toArray()[3]);
		assertEquals("Test 5", b5, beanss.toArray()[4]);

		CollectionUtil<BeanBasico> cu = new CollectionUtil<BeanBasico>();
		Collection<BeanBasico> beanssSort = cu.sortCollectionDesc(beanss, "ordem");

		assertEquals("Test 6", 5, beanssSort.size());

		assertEquals("Test 7", b1, beanssSort.toArray()[4]);
		assertEquals("Test 8", b2, beanssSort.toArray()[3]);
		assertEquals("Test 9", b3, beanssSort.toArray()[2]);
		assertEquals("Test 10", b4, beanssSort.toArray()[1]);
		assertEquals("Test 11", b5, beanssSort.toArray()[0]);
	}

	public void testSortCollectionStringIgnoreCase()
	{
		Collection<BeanBasico> beanss = new ArrayList<BeanBasico>();

		BeanBasico b1 = new BeanBasico();
		b1.setId(1L);
		b1.setNome("Ab");

		BeanBasico b2 = new BeanBasico();
		b2.setId(2L);
		b2.setNome("Bb");

		BeanBasico b3 = new BeanBasico();
		b3.setId(3L);
		b3.setNome("Cb");

		BeanBasico b4 = new BeanBasico();
		b4.setId(4L);
		b4.setNome("Db");

		BeanBasico b5 = new BeanBasico();
		b5.setId(5L);
		b5.setNome("ac");

		beanss.add(b5);
		beanss.add(b4);
		beanss.add(b3);
		beanss.add(b2);
		beanss.add(b1);

		assertEquals("Test 1", b1, beanss.toArray()[4]);
		assertEquals("Test 2", b2, beanss.toArray()[3]);
		assertEquals("Test 3", b3, beanss.toArray()[2]);
		assertEquals("Test 4", b4, beanss.toArray()[1]);
		assertEquals("Test 5", b5, beanss.toArray()[0]);

		CollectionUtil<BeanBasico> cu = new CollectionUtil<BeanBasico>();
		Collection<BeanBasico> beanssSort = cu.sortCollectionStringIgnoreCase(beanss, "nome");

		assertEquals("Test 6", 5, beanssSort.size());

		assertEquals("Test 7", b1, beanssSort.toArray()[0]);
		assertEquals("Test 8", b5, beanssSort.toArray()[1]);
		assertEquals("Test 9", b2, beanssSort.toArray()[2]);
		assertEquals("Test 10", b3, beanssSort.toArray()[3]);
		assertEquals("Test 11", b4, beanssSort.toArray()[4]);

		beanssSort = cu.sortCollectionStringIgnoreCase(beanss, "nomeNaoExist");
	}

	@SuppressWarnings("deprecation")
	public void testSortCollectionDate()
	{
		Collection<BeanBasico> beanss = new ArrayList<BeanBasico>();

		BeanBasico b1 = new BeanBasico();
		b1.setId(1L);
		b1.setData(new Date(2000, 5, 1));

		BeanBasico b2 = new BeanBasico();
		b2.setId(2L);
		b2.setData(new Date(2000, 4, 1));

		BeanBasico b3 = new BeanBasico();
		b3.setId(3L);
		b3.setData(new Date(2000, 3, 1));

		BeanBasico b4 = new BeanBasico();
		b4.setId(4L);
		b4.setData(new Date(2000, 2, 1));

		BeanBasico b5 = new BeanBasico();
		b5.setId(5L);
		b5.setData(new Date(2000, 1, 1));

		beanss.add(b5);
		beanss.add(b4);
		beanss.add(b3);
		beanss.add(b2);
		beanss.add(b1);

		assertEquals("Test 1", b1, beanss.toArray()[4]);
		assertEquals("Test 2", b2, beanss.toArray()[3]);
		assertEquals("Test 3", b3, beanss.toArray()[2]);
		assertEquals("Test 4", b4, beanss.toArray()[1]);
		assertEquals("Test 5", b5, beanss.toArray()[0]);

		CollectionUtil<BeanBasico> cu = new CollectionUtil<BeanBasico>();
		Collection<BeanBasico> beanssSort = cu.sortCollectionDate(beanss, "data");

		assertEquals("Test 6", 5, beanssSort.size());

		assertEquals("Test 7", b1, beanssSort.toArray()[0]);
		assertEquals("Test 8", b2, beanssSort.toArray()[1]);
		assertEquals("Test 9", b3, beanssSort.toArray()[2]);
		assertEquals("Test 10", b4, beanssSort.toArray()[3]);
		assertEquals("Test 11", b5, beanssSort.toArray()[4]);
	}

	@SuppressWarnings("deprecation")
	public void testSortCollectionDate2()
	{
		Collection<BeanBasico> beanss = new ArrayList<BeanBasico>();

		BeanBasico b1 = new BeanBasico();
		b1.setId(1L);
		b1.setData(new Date(2000, 5, 1));

		BeanBasico b2 = new BeanBasico();
		b2.setId(2L);
		b2.setData(new Date(2000, 4, 1));

		BeanBasico b3 = new BeanBasico();
		b3.setId(3L);
		b3.setData(new Date(2000, 3, 1));

		BeanBasico b4 = new BeanBasico();
		b4.setId(4L);
		b4.setData(new Date(2000, 2, 1));

		BeanBasico b5 = new BeanBasico();
		b5.setId(5L);
		b5.setData(new Date(2000, 1, 1));

		beanss.add(b5);
		beanss.add(b4);
		beanss.add(b3);
		beanss.add(b2);
		beanss.add(b1);

		assertEquals("Test 1", b1, beanss.toArray()[4]);
		assertEquals("Test 2", b2, beanss.toArray()[3]);
		assertEquals("Test 3", b3, beanss.toArray()[2]);
		assertEquals("Test 4", b4, beanss.toArray()[1]);
		assertEquals("Test 5", b5, beanss.toArray()[0]);

		CollectionUtil<BeanBasico> cu = new CollectionUtil<BeanBasico>();
		Collection<BeanBasico> beanssSort = cu.sortCollectionDate(beanss, "data", "desc");

		assertEquals("Test 6", 5, beanssSort.size());

		assertEquals("Test 7", b1, beanssSort.toArray()[0]);
		assertEquals("Test 8", b2, beanssSort.toArray()[1]);
		assertEquals("Test 9", b3, beanssSort.toArray()[2]);
		assertEquals("Test 10", b4, beanssSort.toArray()[3]);
		assertEquals("Test 11", b5, beanssSort.toArray()[4]);

		beanssSort = cu.sortCollectionDate(beanss, "data", "asc");

		assertEquals("Test 12", 5, beanssSort.size());

		assertEquals("Test 13", b5, beanssSort.toArray()[0]);
		assertEquals("Test 14", b4, beanssSort.toArray()[1]);
		assertEquals("Test 15", b3, beanssSort.toArray()[2]);
		assertEquals("Test 16", b2, beanssSort.toArray()[3]);
		assertEquals("Test 17", b1, beanssSort.toArray()[4]);

	}

	public void testConvertArrayToCollection()
	{

		CollectionUtil<BeanBasico> cu = new CollectionUtil<BeanBasico>();

		BeanBasico b1 = new BeanBasico();
		b1.setId(1L);
		b1.setNome("Ab");

		BeanBasico b2 = new BeanBasico();
		b2.setId(2L);
		b2.setNome("Bb");

		BeanBasico b3 = new BeanBasico();
		b3.setId(3L);
		b3.setNome("Cb");

		BeanBasico b4 = new BeanBasico();
		b4.setId(4L);
		b4.setNome("Db");

		BeanBasico b5 = new BeanBasico();
		b5.setId(5L);
		b5.setNome("ac");

		BeanBasico[] beans = new BeanBasico[] { b1, b2, b3, b4, b5 };

		Collection<BeanBasico> colBeans = cu.convertArrayToCollection(beans);

		assertEquals("Test 1", 5, colBeans.size());

		assertEquals("Test 2", b1, colBeans.toArray()[0]);
		assertEquals("Test 3", b2, colBeans.toArray()[1]);
		assertEquals("Test 4", b3, colBeans.toArray()[2]);
		assertEquals("Test 5", b4, colBeans.toArray()[3]);
		assertEquals("Test 6", b5, colBeans.toArray()[4]);
	}

	public void testSortDistinctCollection()
	{

		Collection<BeanBasico> users = new ArrayList<BeanBasico>();

		BeanBasico u1 = new BeanBasico();
		u1.setId(1L);
		u1.setNome("nome 1");
		u1.setOrdem(1);

		BeanBasico u2 = new BeanBasico();
		u2.setId(2L);
		u2.setNome("nome 2");
		u2.setOrdem(2);

		BeanBasico u3 = new BeanBasico();
		u3.setId(3L);
		u3.setNome("nome 3");
		u3.setOrdem(3);

		BeanBasico u4 = new BeanBasico();
		u4.setId(4L);
		u4.setNome("nome 4");
		u4.setOrdem(4);

		users.add(u4);
		users.add(u4);
		users.add(u4);
		users.add(u2);
		users.add(u2);
		users.add(u2);
		users.add(u1);
		users.add(u1);
		users.add(u3);
		users.add(u3);
		users.add(u1);
		users.add(u3);
		users.add(u3);
		users.add(u3);
		users.add(u3);
		users.add(u4);

		CollectionUtil<BeanBasico> cu = new CollectionUtil<BeanBasico>();
		Collection<BeanBasico> list = cu.sortDistinctCollection(users, "ordem");

		assertEquals("Test 1", 4, list.size());

		assertEquals("Test 2", u1, list.toArray()[0]);
		assertEquals("Test 3", u2, list.toArray()[1]);
		assertEquals("Test 4", u3, list.toArray()[2]);
		assertEquals("Test 5", u4, list.toArray()[3]);
	}
}