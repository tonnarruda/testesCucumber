package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.MotivoDemissaoManagerImpl;
import com.fortes.rh.dao.geral.MotivoDemissaoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.MotivoDemissao;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.MotivoDemissaoFactory;


public class MotivoDemissaoManagerTest extends MockObjectTestCase
{
	MotivoDemissaoManagerImpl motivoDemissaManager = null;
	Mock MotivoDemissaDao = null;
	Mock acPessoalClientMotivoDemissa;

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();

		motivoDemissaManager = new MotivoDemissaoManagerImpl();

		MotivoDemissaDao = mock(MotivoDemissaoDao.class);
		motivoDemissaManager.setDao((MotivoDemissaoDao) MotivoDemissaDao.proxy());
	}


	public void testSincronizar() throws Exception
	{
		Empresa empresaOrigem = EmpresaFactory.getEmpresa(1L);
		Empresa empresaDestino = EmpresaFactory.getEmpresa(2L);
		MotivoDemissao motivoDemissa = MotivoDemissaoFactory.getEntity();
		Collection<MotivoDemissao> motivoDemissas = new ArrayList<MotivoDemissao>();
		motivoDemissas.add(motivoDemissa);
		
		MotivoDemissaDao.expects(once()).method("findAllSelect").with(eq(empresaOrigem.getId())).will(returnValue(motivoDemissas));
		MotivoDemissaDao.expects(once()).method("save");
		MotivoDemissaDao.expects(once()).method("update");
		
		motivoDemissaManager.sincronizar(empresaOrigem.getId(), empresaDestino.getId());
		
		assertEquals(empresaDestino.getId(), motivoDemissa.getEmpresa().getId() );
	}
}
