package com.fortes.rh.test.business.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManagerImpl;
import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Certificado;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.relatorio.CertificacaoTreinamentosRelatorio;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorAvaliacaoPraticaFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorCertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;


public class CertificacaoManagerTest
{
	CertificacaoManagerImpl certificacaoManager = new CertificacaoManagerImpl();
	CertificacaoDao certificacaoDao;
	FaixaSalarialManager faixaSalarialManager; 
	AvaliacaoPraticaManager avaliacaoPraticaManager;
	ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager;
	
	@Before
	public void setUp() throws Exception
	{
		certificacaoDao = mock(CertificacaoDao.class);
		certificacaoManager.setDao(certificacaoDao);
		
		faixaSalarialManager = mock(FaixaSalarialManager.class);
		certificacaoManager.setFaixaSalarialManager(faixaSalarialManager);
		
		avaliacaoPraticaManager = mock(AvaliacaoPraticaManager.class);
		certificacaoManager.setAvaliacaoPraticaManager(avaliacaoPraticaManager);
		
		colaboradorCertificacaoManager = mock(ColaboradorCertificacaoManager.class);
		certificacaoManager.setColaboradorCertificacaoManager(colaboradorCertificacaoManager);

		colaboradorAvaliacaoPraticaManager = mock(ColaboradorAvaliacaoPraticaManager.class);
		certificacaoManager.setColaboradorAvaliacaoPraticaManager(colaboradorAvaliacaoPraticaManager);
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
	
	@Test
	public void montaCertificacao(){
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
		Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
		
		Curso curso = CursoFactory.getEntity(1L);
		curso.setCargaHoraria(5);
		curso.setNome("Curso Nome");
		Collection<Curso> cursos = new ArrayList<Curso>();
		cursos.add(curso);
		
		ColaboradorCertificacao colaboradorCertificacao1 = ColaboradorCertificacaoFactory.getEntity(colaborador1, certificacao, null);
		colaboradorCertificacao1.setId(1L);
		ColaboradorCertificacao colaboradorCertificacao2 = ColaboradorCertificacaoFactory.getEntity(colaborador1, certificacao, null);
		colaboradorCertificacao2.setId(2L);

		AvaliacaoPratica avaliacaoPratica1 = AvaliacaoPraticaFactory.getEntity(1L);
		avaliacaoPratica1.setTitulo("Titulo Av. Pratica 1");
		AvaliacaoPratica avaliacaoPratica2 = AvaliacaoPraticaFactory.getEntity(2L);
		avaliacaoPratica2.setTitulo("Titulo Av. Pratica 2");
		Collection<AvaliacaoPratica> avaliacoesPraticas = new ArrayList<AvaliacaoPratica>();
		avaliacoesPraticas.add(avaliacaoPratica1);
		avaliacoesPraticas.add(avaliacaoPratica2);
		
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica1 = ColaboradorAvaliacaoPraticaFactory.getEntity(1L);
		colaboradorAvaliacaoPratica1.setNota(90.0);
		colaboradorAvaliacaoPratica1.setAvaliacaoPratica(avaliacaoPratica1);
		ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica2 = ColaboradorAvaliacaoPraticaFactory.getEntity(2L);
		colaboradorAvaliacaoPratica2.setNota(70.0);
		colaboradorAvaliacaoPratica2.setAvaliacaoPratica(avaliacaoPratica1);
		
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPraticas1 = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacoesPraticas1.add(colaboradorAvaliacaoPratica1);
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPraticas2 = new ArrayList<ColaboradorAvaliacaoPratica>();
		colaboradorAvaliacoesPraticas2.add(colaboradorAvaliacaoPratica2);

		String[] colaboradoresCheck = new String[]{colaborador1.getId().toString(),colaborador2.getId().toString()};
		
		when(avaliacaoPraticaManager.findByCertificacaoId(certificacao.getId())).thenReturn(avaliacoesPraticas);
		when(colaboradorCertificacaoManager.findUltimaCertificacaoByColaboradorIdAndCertificacaoId(colaborador1.getId(), certificacao.getId())).thenReturn(colaboradorCertificacao1);
		when(colaboradorCertificacaoManager.findUltimaCertificacaoByColaboradorIdAndCertificacaoId(colaborador2.getId(), certificacao.getId())).thenReturn(colaboradorCertificacao2);
		when(colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaborador1.getId(), certificacao.getId(), colaboradorCertificacao1.getId(), null, true, true)).thenReturn(colaboradorAvaliacoesPraticas1);
		when(colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaborador2.getId(), certificacao.getId(), colaboradorCertificacao2.getId(), null, true, true)).thenReturn(colaboradorAvaliacoesPraticas2);
		
		Collection<CertificacaoTreinamentosRelatorio> retornoCollection = certificacaoManager.montaCertificacao(certificacao.getId(), colaboradoresCheck, new Certificado(), cursos, true);
		CertificacaoTreinamentosRelatorio retorno1 = ((CertificacaoTreinamentosRelatorio) retornoCollection.toArray()[0]);
		CertificacaoTreinamentosRelatorio retorno2 = ((CertificacaoTreinamentosRelatorio) retornoCollection.toArray()[1]);
		
		assertEquals(2, retornoCollection.size());
		assertEquals(3, retorno1.getCursos().size());
		assertEquals(3, retorno2.getCursos().size());
		
		assertEquals("Treinamento: Curso Nome", ((Curso) retorno2.getCursos().toArray()[0]).getNome());
		assertEquals("Avaliação Prática: Titulo Av. Pratica 1", ((Curso) retorno2.getCursos().toArray()[1]).getNome());
		assertEquals("Nota: 70.0", ((Curso) retorno2.getCursos().toArray()[1]).getInformacao());
		assertEquals("Avaliação Prática: Titulo Av. Pratica 2", ((Curso) retorno2.getCursos().toArray()[2]).getNome());
		assertEquals("Nota: -", ((Curso) retorno2.getCursos().toArray()[2]).getInformacao());
	}
}