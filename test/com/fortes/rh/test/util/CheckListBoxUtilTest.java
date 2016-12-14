package com.fortes.rh.test.util;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.web.tags.CheckBox;

public class CheckListBoxUtilTest extends TestCase
{

	protected void setUp(){
		CheckListBoxUtil checkListBoxUtil = new CheckListBoxUtil();
	}

	public void testMarcaCheckListBox(){

		CheckBox cb1 = new CheckBox();
		cb1.setId(1L);
		cb1.setNome("c1");
		cb1.setSelecionado(false);

		CheckBox cb2 = new CheckBox();
		cb2.setId(2L);
		cb2.setNome("c2");
		cb2.setSelecionado(false);

		CheckBox cb3 = new CheckBox();
		cb3.setId(3L);
		cb3.setNome("c3");
		cb3.setSelecionado(false);

		Collection<CheckBox> checks = new ArrayList<CheckBox>();

		checks.add(cb1);
		checks.add(cb2);
		checks.add(cb3);

		String[] marcados = new String[]{"1"};
		Collection<CheckBox> checksRetorno = CheckListBoxUtil.marcaCheckListBox(checks, marcados);

		assertTrue("Test 1", ((CheckBox)(checksRetorno.toArray()[0])).isSelecionado());
		assertFalse("Test 2",((CheckBox)(checksRetorno.toArray()[1])).isSelecionado());
		assertFalse("Test 3",((CheckBox)(checksRetorno.toArray()[2])).isSelecionado());

		cb1.setSelecionado(false);
		marcados = new String[]{};
		checksRetorno = CheckListBoxUtil.marcaCheckListBox(checks, marcados);

		assertFalse("Test 4",((CheckBox)(checksRetorno.toArray()[0])).isSelecionado());
		assertFalse("Test 5",((CheckBox)(checksRetorno.toArray()[1])).isSelecionado());
		assertFalse("Test 6",((CheckBox)(checksRetorno.toArray()[2])).isSelecionado());

		marcados = new String[]{"1,2"};
		checksRetorno = CheckListBoxUtil.marcaCheckListBox(checks, marcados);

		assertTrue("Test 7",((CheckBox)(checksRetorno.toArray()[0])).isSelecionado());
		assertTrue("Test 8",((CheckBox)(checksRetorno.toArray()[1])).isSelecionado());
		assertFalse("Test 9",((CheckBox)(checksRetorno.toArray()[2])).isSelecionado());
	}

	public void testMarcaCheckListBox2() throws Exception{

		CheckBox cb1 = new CheckBox();
		cb1.setId(1L);
		cb1.setNome("c1");
		cb1.setSelecionado(false);

		CheckBox cb2 = new CheckBox();
		cb2.setId(2L);
		cb2.setNome("c2");
		cb2.setSelecionado(false);

		CheckBox cb3 = new CheckBox();
		cb3.setId(3L);
		cb3.setNome("c3");
		cb3.setSelecionado(false);

		Collection<CheckBox> checks = new ArrayList<CheckBox>();

		checks.add(cb1);
		checks.add(cb2);
		checks.add(cb3);

		Collection<CheckBox> marcados = new ArrayList<CheckBox>();
		marcados.add(cb1);

		Collection<CheckBox> checksRetorno = CheckListBoxUtil.marcaCheckListBox(checks, marcados, "getId");

		assertTrue("Test 1", ((CheckBox)(checksRetorno.toArray()[0])).isSelecionado());
		assertFalse("Test 2",((CheckBox)(checksRetorno.toArray()[1])).isSelecionado());
		assertFalse("Test 3",((CheckBox)(checksRetorno.toArray()[2])).isSelecionado());

		cb1.setSelecionado(false);
		marcados = new ArrayList<CheckBox>();
		checksRetorno = CheckListBoxUtil.marcaCheckListBox(checks, marcados, "getId");

		assertFalse("Test 4",((CheckBox)(checksRetorno.toArray()[0])).isSelecionado());
		assertFalse("Test 5",((CheckBox)(checksRetorno.toArray()[1])).isSelecionado());
		assertFalse("Test 6",((CheckBox)(checksRetorno.toArray()[2])).isSelecionado());
	}

	public void testMarcaCheckListBoxString() throws Exception{

		CheckBox cb1 = new CheckBox();
		cb1.setId(1L);
		cb1.setNome("c1");
		cb1.setSelecionado(false);

		CheckBox cb2 = new CheckBox();
		cb2.setId(2L);
		cb2.setNome("c2");
		cb2.setSelecionado(false);

		CheckBox cb3 = new CheckBox();
		cb3.setId(3L);
		cb3.setNome("c3");
		cb3.setSelecionado(false);

		Collection<CheckBox> checks = new ArrayList<CheckBox>();

		checks.add(cb1);
		checks.add(cb2);
		checks.add(cb3);

		Collection<CheckBox> marcados = new ArrayList<CheckBox>();
		marcados.add(cb1);

		Collection<CheckBox> checksRetorno = CheckListBoxUtil.marcaCheckListBoxString(checks, marcados, "getNome");

		assertTrue("Test 1", ((CheckBox)(checksRetorno.toArray()[0])).isSelecionado());
		assertFalse("Test 2",((CheckBox)(checksRetorno.toArray()[1])).isSelecionado());
		assertFalse("Test 3",((CheckBox)(checksRetorno.toArray()[2])).isSelecionado());

		cb1.setSelecionado(false);
		marcados = new ArrayList<CheckBox>();
		checksRetorno = CheckListBoxUtil.marcaCheckListBoxString(checks, marcados, "getNome");

		assertFalse("Test 4",((CheckBox)(checksRetorno.toArray()[0])).isSelecionado());
		assertFalse("Test 5",((CheckBox)(checksRetorno.toArray()[1])).isSelecionado());
		assertFalse("Test 6",((CheckBox)(checksRetorno.toArray()[2])).isSelecionado());

	}

	public void testPopulaCheckListBox() throws Exception{

		Collection<Usuario> list = new ArrayList<Usuario>();

		Usuario u1 = new Usuario();
		u1.setId(1L);
		u1.setNome("u1");
		list.add(u1);

		Usuario u2 = new Usuario();
		u2.setId(2L);
		u2.setNome("u2");
		list.add(u2);

		Usuario u3 = new Usuario();
		u3.setId(3L);
		u3.setNome("u3");
		list.add(u3);

		Usuario u4 = new Usuario();
		u4.setId(4L);
		u4.setNome("u4");
		list.add(u4);

		Collection<CheckBox> checks = CheckListBoxUtil.populaCheckListBox(list, "getId", "getNome", null);
		assertEquals("Test 1", 4, checks.size());
		assertEquals("Test 2", u1.getId().toString(), checks.toArray(new CheckBox[]{})[0].getId());
		assertEquals("Test 3", u1.getNome(), checks.toArray(new CheckBox[]{})[0].getNome());
		assertEquals("Test 4", u2.getId().toString(), checks.toArray(new CheckBox[]{})[1].getId());
		assertEquals("Test 5", u2.getNome(), checks.toArray(new CheckBox[]{})[1].getNome());
		assertEquals("Test 6", u3.getId().toString(), checks.toArray(new CheckBox[]{})[2].getId());
		assertEquals("Test 7", u3.getNome(), checks.toArray(new CheckBox[]{})[2].getNome());
		assertEquals("Test 8", u4.getId().toString(), checks.toArray(new CheckBox[]{})[3].getId());
		assertEquals("Test 9", u4.getNome(), checks.toArray(new CheckBox[]{})[3].getNome());


	}
}
