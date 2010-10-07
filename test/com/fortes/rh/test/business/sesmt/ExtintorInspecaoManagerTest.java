package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.sesmt.ExtintorInspecaoManagerImpl;
import com.fortes.rh.dao.sesmt.ExtintorInspecaoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorInspecaoFactory;

public class ExtintorInspecaoManagerTest extends MockObjectTestCase
{
	private ExtintorInspecaoManagerImpl extintorInspecaoManager = new ExtintorInspecaoManagerImpl();
	private Mock extintorInspecaoDao = null;

	protected void setUp() throws Exception
    {
        super.setUp();
        extintorInspecaoDao = new Mock(ExtintorInspecaoDao.class);
        extintorInspecaoManager.setDao((ExtintorInspecaoDao) extintorInspecaoDao.proxy());
    }

	Long empresaId = 5L;
	Long estabelecimentoId = 5L;
	Long extintorId = 5L;
	Date inicio = new Date();
	Date fim = new Date();


	public void testGetCount()
	{
		extintorInspecaoDao.expects(once()).method("getCount").will(returnValue(1));
		assertEquals(Integer.valueOf(1),
				extintorInspecaoManager.getCount(empresaId, estabelecimentoId, extintorId, inicio, fim));
	}

	public void testFindAllSelect()
	{
		Collection<ExtintorInspecao> colecao = new ArrayList<ExtintorInspecao>();
		colecao.add(ExtintorInspecaoFactory.getEntity(1L));

		extintorInspecaoDao.expects(once()).method("findAllSelect").will(returnValue(colecao ));
		assertEquals(colecao, extintorInspecaoManager.findAllSelect(0, 0, empresaId, estabelecimentoId, extintorId, inicio, fim));
	}

	public void testSaveOrUpdate() throws Exception
	{
		String[] itemChecks = {"1","3","5"};

		ExtintorInspecao extintorInspecao = ExtintorInspecaoFactory.getEntity();
		extintorInspecao.setExtintor(ExtintorFactory.getEntity(1L));
		extintorInspecao.setData(new Date());

		extintorInspecaoDao.expects(once()).method("save").will(returnValue(extintorInspecao));

		extintorInspecao = extintorInspecaoManager.saveOrUpdate(extintorInspecao, itemChecks);

		assertNotNull(extintorInspecao.getItens());
		assertEquals(3, extintorInspecao.getItens().size());

		// Update
		extintorInspecao.setId(1L);
		itemChecks = new String[]{"5"};

		extintorInspecaoDao.expects(once()).method("update").isVoid();

		extintorInspecao = extintorInspecaoManager.saveOrUpdate(extintorInspecao, itemChecks);

		assertEquals(1, extintorInspecao.getItens().size());
	}

	public void testGetEmpresasResponsaveis()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Collection<String> empresas = new ArrayList<String>();
		empresas.add("Gas");
		empresas.add("Extint");

		extintorInspecaoDao.expects(once()).method("findEmpresasResponsaveisDistinct").with(eq(empresa.getId())).will(returnValue(empresas));
		assertEquals("\"Gas\",\"Extint\"", extintorInspecaoManager.getEmpresasResponsaveis(empresa.getId()));
	}

	public void testGetEmpresasResponsaveisVazio()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		extintorInspecaoDao.expects(once()).method("findEmpresasResponsaveisDistinct").with(eq(empresa.getId())).will(returnValue(new ArrayList<String>()));
		assertEquals("", extintorInspecaoManager.getEmpresasResponsaveis(empresa.getId()));
	}
	
	public void testFindInspecoesVencidas()
	{
		extintorInspecaoDao.expects(once()).method("findInspecoesVencidas").with(ANYTHING, ANYTHING).will(returnValue(new ArrayList<ExtintorInspecao>()));
		assertNotNull(extintorInspecaoManager.findInspecoesVencidas(null, null));
	}
}