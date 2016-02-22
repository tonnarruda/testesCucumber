package com.fortes.rh.test.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManagerImpl;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;

public class CertificacaoManagerTest extends MockObjectTestCase
{
	CertificacaoManagerImpl certificacaoManager = new CertificacaoManagerImpl();
	private Mock certificacaoDao;
	private Mock faixaSalarialManager;
	
	protected void setUp() throws Exception
	{
		certificacaoDao = new Mock(CertificacaoDao.class);
		certificacaoManager.setDao((CertificacaoDao) certificacaoDao.proxy());
		
		faixaSalarialManager = new Mock(FaixaSalarialManager.class);
		certificacaoManager.setFaixaSalarialManager((FaixaSalarialManager) faixaSalarialManager.proxy());
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();

		Mockit.restoreAllOriginalDefinitions();
	}

	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Collection<Certificacao> certificacaos = new ArrayList<Certificacao>();
		certificacaoDao.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(certificacaos));

		assertEquals(certificacaos, certificacaoManager.findAllSelect(empresa.getId()));
	}
	public void testFindAllSelectNomeBusca()
	{
		String nomeBusca = "habilidades humanas";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Collection<Certificacao> certificacaos = new ArrayList<Certificacao>();
		certificacaoDao.expects(once()).method("findAllSelect").with(ANYTHING, ANYTHING, eq(empresa.getId()),eq(nomeBusca)).will(returnValue(certificacaos));
		
		assertEquals(certificacaos, certificacaoManager.findAllSelect(null, null, empresa.getId(), nomeBusca));
	}
	
	public void testFindByIdProjection()
	{
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		certificacaoDao.expects(once()).method("findByIdProjection").with(eq(certificacao.getId())).will(returnValue(certificacao));
		
		assertEquals(certificacao, certificacaoManager.findByIdProjection(certificacao.getId()));
	}

	public void testGetByFaixasOrCargos()
	{
		Collection<MatrizTreinamento> matrizs = new ArrayList<MatrizTreinamento>();
		String[] faixaSalarialsCheck = new String[]{"1"};
		String[] cargosCheck = new String[]{};
		certificacaoDao.expects(once()).method("findMatrizTreinamento").with(ANYTHING).will(returnValue(matrizs));
		
		assertEquals(matrizs, certificacaoManager.getByFaixasOrCargos(faixaSalarialsCheck, cargosCheck));
	}
	
	public void testGetByFaixasOrCargosVazio()
	{
		Collection<MatrizTreinamento> matrizs = new ArrayList<MatrizTreinamento>();
		String[] faixaSalarialsCheck = new String[]{};
		String[] cargosCheck = new String[]{};
		
		faixaSalarialManager.expects(once()).method("findByCargos").with(ANYTHING).will(returnValue(null));
		certificacaoDao.expects(once()).method("findMatrizTreinamento").with(ANYTHING).will(returnValue(matrizs));
		
		assertEquals(matrizs, certificacaoManager.getByFaixasOrCargos(faixaSalarialsCheck, cargosCheck));
	}
	
	public void testFindAllSelectNotCertificacaoIdAndCertificacaoPreRequisito(){

		Certificacao certificacao1 = CertificacaoFactory.getEntity(1L);
		
		Certificacao certificacao2 = CertificacaoFactory.getEntity(2L);
		certificacao2.setCertificacaoPreRequisito(certificacao1);
		
		Certificacao certificacao3 = CertificacaoFactory.getEntity(3L);
		certificacao3.setCertificacaoPreRequisito(certificacao2);
		
		Certificacao certificacao4 = CertificacaoFactory.getEntity(4L);
		certificacao4.setCertificacaoPreRequisito(certificacao3);
		
		Certificacao certificacao5 = CertificacaoFactory.getEntity(5L);
		certificacao5.setCertificacaoPreRequisito(certificacao3);
		
		Certificacao certificacao6 = CertificacaoFactory.getEntity(6L);
		
		Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();
		certificacoes.add(certificacao2);
		certificacoes.add(certificacao3);
		certificacoes.add(certificacao4);
		certificacoes.add(certificacao5);
		certificacoes.add(certificacao6);
		
		certificacaoDao.expects(once()).method("findAllSelectNotCertificacaoIdAndCertificacaoPreRequisito").will(returnValue(certificacoes));
		
		Collection<Certificacao> retorno = certificacaoManager.findAllSelectNotCertificacaoIdAndCertificacaoPreRequisito(1L, certificacao1.getId());
		
		assertEquals(1, retorno.size());
		assertEquals(certificacao6.getId(), ((Certificacao)retorno.toArray()[0]).getId());
	}
}