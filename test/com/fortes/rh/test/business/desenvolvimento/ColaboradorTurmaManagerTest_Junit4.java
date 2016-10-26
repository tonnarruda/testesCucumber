package com.fortes.rh.test.business.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusAprovacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;

public class ColaboradorTurmaManagerTest_Junit4
{
	private ColaboradorTurmaManagerImpl colaboradorTurmaManager = new ColaboradorTurmaManagerImpl();
	private ColaboradorTurmaDao colaboradorTurmaDao;

	@Before
	public void setUp() throws Exception
	{
		colaboradorTurmaDao = mock(ColaboradorTurmaDao.class);
		colaboradorTurmaManager.setDao(colaboradorTurmaDao);
	}
	
	@Test
	public void testFindAprovadosByCertificacaoControleVencimentoPorCurso()
	{
		Colaborador francisco = ColaboradorFactory.getEntity(1L, "Francisco");
		Colaborador maria = ColaboradorFactory.getEntity(2L, "Maria");
		Colaborador pedro = ColaboradorFactory.getEntity(3L, "Pedro");
		
		Collection<ColaboradorTurma> colabTurmas =  new ArrayList<ColaboradorTurma>();
		ColaboradorTurma pedroCertificado = montaColaboradorTurma(pedro, true);
		ColaboradorTurma franciscoNaoCertificado = montaColaboradorTurma(francisco,false);
		ColaboradorTurma mariaNaoCertificada = montaColaboradorTurma(maria, false);

		colabTurmas.add(pedroCertificado);
		colabTurmas.add(franciscoNaoCertificado);
		colabTurmas.add(mariaNaoCertificada);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		Integer qtdCusos = 3;
		
		when(colaboradorTurmaDao.findColaboradorTurmaByCertificacaoControleVencimentoPorCurso(certificacao.getId(), qtdCusos)).thenReturn(colabTurmas);
		Collection<Colaborador> colaboradores = colaboradorTurmaManager.findAprovadosByCertificacao(certificacao, qtdCusos, false);
		assertEquals(3, colaboradores.size());
		
		Colaborador[] colabs = colaboradores.toArray(new Colaborador[]{});
		assertEquals("Pedro", colabs[0].getNome());
		assertEquals("<span style='color: red;'>Francisco (Não certificado)</span>", colabs[1].getNome());
		assertEquals("<span style='color: red;'>Maria (Não certificado)</span>", colabs[2].getNome());
	}
	
	@Test
	public void testFindAprovadosByCertificacaoConrolevencimentoPorCertificacao()
	{
		Colaborador francisco = ColaboradorFactory.getEntity(1L, "Francisco");
		Colaborador maria = ColaboradorFactory.getEntity(2L, "Maria");
		Colaborador pedro = ColaboradorFactory.getEntity(3L, "Pedro");
		
		Collection<ColaboradorTurma> colabTurmas =  new ArrayList<ColaboradorTurma>();
		ColaboradorTurma pedroNaoCertificado = montaColaboradorTurma(pedro, false);
		ColaboradorTurma franciscoCertificado = montaColaboradorTurma(francisco,true);
		ColaboradorTurma mariaCertificada = montaColaboradorTurma(maria, true);

		colabTurmas.add(pedroNaoCertificado);
		colabTurmas.add(franciscoCertificado);
		colabTurmas.add(mariaCertificada);
		
		Certificacao certificacao = CertificacaoFactory.getEntity(1L);
		Integer qtdCusos = 3;
		
		when(colaboradorTurmaDao.findColaboradorTurmaByCertificacaoControleVencimentoPorCertificacao(certificacao.getId())).thenReturn(colabTurmas);
		Collection<Colaborador> colaboradores = colaboradorTurmaManager.findAprovadosByCertificacao(certificacao, qtdCusos, true);
		assertEquals(3, colaboradores.size());
		
		Colaborador[] colabs = colaboradores.toArray(new Colaborador[]{});
		assertEquals("Francisco", colabs[0].getNome());
		assertEquals("Maria", colabs[1].getNome());
		assertEquals("<span style='color: red;'>Pedro (Não certificado)</span>", colabs[2].getNome());
	}
		
	@Test
	public void findRelatorioComTreinamento(){
		Collection<ColaboradorTurma> colaboradorTurmas = Arrays.asList(ColaboradorTurmaFactory.getEntity());
		when(colaboradorTurmaDao.findRelatorioComTreinamento(1L, new Long[]{1L}, new Long[]{1L}, new Long[]{1L}, null, null, SituacaoColaborador.ATIVO,StatusAprovacao.APROVADO)).thenReturn(colaboradorTurmas);
		Exception exception = null;
		try {
			colaboradorTurmaManager.findRelatorioComTreinamento(1L, new Long[]{1L}, new Long[]{1L}, new Long[]{1L}, null, null, StatusAprovacao.APROVADO, SituacaoColaborador.ATIVO);
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}

	@Test
	public void findRelatorioComTreinamentoExceptionColecaoVaziaException(){
		when(colaboradorTurmaDao.findRelatorioComTreinamento(1L, new Long[]{1L}, new Long[]{1L}, new Long[]{1L}, null, null, SituacaoColaborador.ATIVO,StatusAprovacao.APROVADO)).thenReturn(new ArrayList<ColaboradorTurma>());
		Exception exception = null;
		try {
			colaboradorTurmaManager.findRelatorioComTreinamento(1L, new Long[]{1L}, new Long[]{1L}, new Long[]{1L}, null, null, StatusAprovacao.APROVADO, SituacaoColaborador.ATIVO);
		} catch (Exception e) {
			exception = e;
		}
		assertEquals("Não existem dados para o filtro informado.", exception.getMessage());
	}
	
	@Test
	public void testFindColabodoresByTurmaId() {
		Long turmaId = 2L;
		Collection<Colaborador> colaboradores = ColaboradorFactory.getCollection();
		when(colaboradorTurmaDao.findColabodoresByTurmaId(turmaId)).thenReturn(colaboradores);
		
		Collection<Colaborador> retorno = colaboradorTurmaManager.findColabodoresByTurmaId(turmaId);
		verify(colaboradorTurmaDao, times(1)).findColabodoresByTurmaId(eq(turmaId));
		assertEquals(colaboradores.size(), retorno.size());
	}
	
	private ColaboradorTurma montaColaboradorTurma(Colaborador colaborador, boolean certificado){
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity();
		colaboradorTurma.setColaborador(colaborador);
		colaboradorTurma.setCertificado(certificado);
		return colaboradorTurma;
	}
}