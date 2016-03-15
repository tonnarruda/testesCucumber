package com.fortes.rh.test.web.action.sesmt;

import java.util.Collection;
import java.util.HashSet;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Epc;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.test.util.mockObjects.MockSecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.sesmt.EpcEditAction;
import com.fortes.web.tags.CheckBox;


public class EpcEditActionTest extends MockObjectTestCase
{
	EpcEditAction action;
	Mock epcManager;
	Collection<CheckBox> ambientesCheckList;

	private Collection<CheckBox> populaAmbiente()
	{
		Collection<Ambiente> ambientes = getAmbientes();

		try
		{
			ambientesCheckList = CheckListBoxUtil.populaCheckListBox(ambientes, "getId", "getNome");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return ambientesCheckList;
	}

	private Collection<Ambiente> getAmbientes()
	{
		Collection<Ambiente> ambientes = new HashSet<Ambiente>(3);
		Ambiente ambiente = new Ambiente();
		ambiente.setId(1L);
		ambiente.setNome("A");
		Ambiente ambiente2 = new Ambiente();
		ambiente2.setNome("B");
		ambiente2.setId(2L);

		ambientes.add(ambiente);
		ambientes.add(ambiente2);
		return ambientes;
	}

	protected void setUp() throws Exception
	{
		ambientesCheckList = populaAmbiente();

		action = new EpcEditAction();

		Mockit.redefineMethods(SecurityUtil.class, MockSecurityUtil.class);

		epcManager = new Mock(EpcManager.class);
		action.setEpcManager((EpcManager) epcManager.proxy());
	}

	@Override
	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
		ambientesCheckList = null;
        MockSecurityUtil.verifyRole = false;
	}

	public void testPrepareInsert() throws Exception
	{
		Epc epc = new Epc();
		action.setEpc(epc);

		assertEquals("success", action.prepareInsert());
	}

	public void testPrepareUpdate() throws Exception
	{
		Epc epc = new Epc();
		epc.setId(1L);
		Empresa empresa = new Empresa();
		empresa.setId(1L);
		epc.setEmpresa(empresa);
		action.setEpc(epc);

		epcManager.expects(once()).method("findById").with(eq(epc.getId())).will(returnValue(epc));
		epcManager.expects(once()).method("findByIdProjection").with(eq(epc.getId())).will(returnValue(epc));

		assertEquals("success", action.prepareUpdate());
	}

	public void testPrepareUpdateEmpresaErrada() throws Exception
	{
		Epc epc = new Epc();
		epc.setId(1L);
		action.setEpc(epc);

		epcManager.expects(once()).method("findById").with(eq(epc.getId())).will(returnValue(epc));
		epcManager.expects(once()).method("findByIdProjection").with(eq(epc.getId())).will(returnValue(epc));

		assertEquals("error", action.prepareUpdate());
	}

	public void testInsert() throws Exception
	{
		Epc epc = new Epc();
		epc.setCodigo("codigo");
		epc.setEmpresa(new Empresa());
		epc.setDescricao("descricao");
		action.setEpc(epc);

		epcManager.expects(once()).method("save");
		assertEquals("success", action.insert());
	}

	public void testInsertSemDados() throws Exception
	{
		Epc epc = new Epc();
		action.setEpc(epc);

		epcManager.expects(never()).method("save").with(ANYTHING);
		assertEquals("error", action.insert());
	}

	public void testUpdate() throws Exception
	{
		Epc epc = new Epc();
		epc.setId(1l);
		epc.setCodigo("codigo");
		Empresa empresa = new Empresa();
		empresa.setId(1L);
		epc.setEmpresa(empresa);
		epc.setDescricao("descricao");
		action.setEpc(epc);

		Epc epcTmp = new Epc();
		epcTmp.setId(1l);
		epcTmp.setCodigo("codigo");
		epcTmp.setEmpresa(empresa);
		epcTmp.setDescricao("descricao");

		// chamado em validaEmpresa()
		epcManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(epcTmp));

		epcManager.expects(once()).method("update").with(ANYTHING);
		assertEquals("success", action.update());
	}

	public void testUpdateEmpresaErrada() throws Exception
	{
		Epc epc = new Epc();
		epc.setId(1L);
		epc.setCodigo("codigo");
		epc.setEmpresa(new Empresa());
		epc.setDescricao("descricao");
		action.setEpc(epc);

		Empresa empresaErrada = new Empresa();
		empresaErrada.setId(2L);
		Epc epcEmpresaErrada = new Epc();
		epcEmpresaErrada.setId(2L);
		epcEmpresaErrada.setCodigo("codigo");
		epcEmpresaErrada.setEmpresa(empresaErrada);
		epcEmpresaErrada.setDescricao("descricao");

		// validaEmpresa()
		epcManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(epcEmpresaErrada));

		// prepare
		epcManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(epc));

		epcManager.expects(never()).method("update").with(ANYTHING);
		assertEquals("error", action.update());
	}

	public void testUpdateSemDados() throws Exception
	{
		Empresa empresa = new Empresa();
		empresa.setId(1L);
		Epc epc = new Epc();
		epc.setId(1L);
		epc.setEmpresa(empresa);
		action.setEpc(epc);

		// validaEmpresa()
		epcManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(epc));

		// prepare
		epcManager.expects(once()).method("findById").with(ANYTHING).will(returnValue(epc));

		epcManager.expects(never()).method("update").with(ANYTHING);
		assertEquals("error", action.update());
	}

	public void testGetSet(){
		action.getModel();
		action.getEpc();
	}
}