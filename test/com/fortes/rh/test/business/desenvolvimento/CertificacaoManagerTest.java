package com.fortes.rh.test.business.desenvolvimento;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManagerImpl;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;


public class CertificacaoManagerTest
{
	CertificacaoManagerImpl certificacaoManager = new CertificacaoManagerImpl();
	CertificacaoDao certificacaoDao;
	FaixaSalarialManager faixaSalarialManager; 
	
	@Before
	public void setUp() throws Exception
	{
		certificacaoDao = mock(CertificacaoDao.class);
		certificacaoManager.setDao(certificacaoDao);
		
		faixaSalarialManager = mock(FaixaSalarialManager.class);
		certificacaoManager.setFaixaSalarialManager(faixaSalarialManager);
	}

	@Test
	public void findAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Collection<Certificacao> certificacaos = new ArrayList<Certificacao>();
		
		when(certificacaoDao.findAllSelect(empresa.getId())).thenReturn(certificacaos);

		assertEquals(certificacaos, certificacaoManager.findAllSelect(empresa.getId()));
	}

	@Test
	public void testFindAllSelectNomeBusca()
	{
		String nomeBusca = "habilidades humanas";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		Collection<Certificacao> certificacaos = new ArrayList<Certificacao>();
		when(certificacaoDao.findAllSelect(null, null, empresa.getId(), nomeBusca)).thenReturn(certificacaos);
		
		assertEquals(certificacaos, certificacaoManager.findAllSelect(null, null, empresa.getId(), nomeBusca));
	}
	
	@Test
	public void testFindByIdProjection()
	{
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		when(certificacaoDao.findByIdProjection(certificacao.getId())).thenReturn(certificacao);
		
		assertEquals(certificacao, certificacaoManager.findByIdProjection(certificacao.getId()));
	}
	
	@Test
	public void testGetByFaixasOrCargos()
	{
		Collection<MatrizTreinamento> matrizs = new ArrayList<MatrizTreinamento>();
		String[] faixaSalarialsCheck = new String[]{"1"};
		String[] cargosCheck = new String[]{};
		when(certificacaoDao.findMatrizTreinamento(new ArrayList<Long>())).thenReturn(matrizs);
		
		assertEquals(matrizs, certificacaoManager.getByFaixasOrCargos(faixaSalarialsCheck, cargosCheck));
	}
	
	@Test
	public void testGetByFaixasOrCargosVazio()
	{
		Collection<MatrizTreinamento> matrizs = new ArrayList<MatrizTreinamento>();
		String[] faixaSalarialsCheck = new String[]{};
		String[] cargosCheck = new String[]{};
		
		when(faixaSalarialManager.findByCargos(null)).thenReturn(null);
		when(certificacaoDao.findMatrizTreinamento(new ArrayList<Long>())).thenReturn(matrizs);
		
		assertEquals(matrizs, certificacaoManager.getByFaixasOrCargos(faixaSalarialsCheck, cargosCheck));
	}
	
	@Test
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
		
		when(certificacaoDao.findAllSelectNotCertificacaoIdAndCertificacaoPreRequisito(1L, certificacao1.getId())).thenReturn(certificacoes);
		
		Collection<Certificacao> retorno = certificacaoManager.findAllSelectNotCertificacaoIdAndCertificacaoPreRequisito(1L, certificacao1.getId());
		
		assertEquals(1, retorno.size());
		assertEquals(certificacao6.getId(), ((Certificacao)retorno.toArray()[0]).getId());
	}
}