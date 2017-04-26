package com.fortes.rh.test.business.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManagerImpl;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.desenvolvimento.ColaboradorTurmaDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.dicionario.MotivoReprovacao;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusAprovacao;
import com.fortes.rh.model.dicionario.StatusTreinamento;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.desenvolvimento.CertificacaoFactory;
import com.fortes.rh.test.factory.desenvolvimento.ColaboradorTurmaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;

public class ColaboradorTurmaManagerTest_Junit4
{
	private ColaboradorTurmaManagerImpl colaboradorTurmaManager = new ColaboradorTurmaManagerImpl();
	private ColaboradorTurmaDao colaboradorTurmaDao;
	private ColaboradorManager colaboradorManager;
	private CursoManager cursoManager;

	@Before
	public void setUp() throws Exception
	{
		colaboradorTurmaDao = mock(ColaboradorTurmaDao.class);
		colaboradorManager = mock(ColaboradorManager.class);
		cursoManager = mock(CursoManager.class);
		
		colaboradorTurmaManager.setDao(colaboradorTurmaDao);
		
		colaboradorManager = mock(ColaboradorManager.class);
		cursoManager = mock(CursoManager.class);
		
		colaboradorTurmaManager.setColaboradorManager(colaboradorManager);
		colaboradorTurmaManager.setCursoManager(cursoManager);
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
	
	@Test
	public void testFindRelatorioSemTreinamentoAprovadosOrReprovados() throws Exception {
		Curso curso = CursoFactory.getEntity(10L);
		Long[] empresaId= new Long[]{1L};
		Long[] areaIds=null;
		Long[] estabelecimentoIds=null;
		Boolean aprovado = null;
		
		Colaborador colaborador = ColaboradorFactory.getEntity(10L, "Colaborador que fez o treinamento a Zero meses");
		colaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(100L, colaborador);
		colaboradorTurma.setCurso(curso);

		Collection<ColaboradorTurma> colaboradorTurmas = Arrays.asList(colaboradorTurma);
		
		when(colaboradorTurmaDao.findRelatorioSemTreinamentoAprovadosOrReprovados(eq(empresaId), eq(new Long[]{curso.getId()}), eq(areaIds), eq(estabelecimentoIds), any(Date.class), eq(SituacaoColaborador.ATIVO), eq(aprovado))).thenReturn(colaboradorTurmas);
		
		Collection<ColaboradorTurma> resultado = colaboradorTurmaManager.findRelatorioSemTreinamentoAprovadosOrReprovados(empresaId, new Long[]{curso.getId()}, areaIds, estabelecimentoIds, 0, SituacaoColaborador.ATIVO, StatusTreinamento.APROVADO_REPROVADO);
		
		assertEquals(1, resultado.size());
		assertEquals("Colaborador que fez o treinamento a Zero meses", ((ColaboradorTurma)resultado.toArray()[0]).getColaborador().getNome());
	}
	
	@Test
	public void testFindRelatorioSemTreinamentoAprovadosOrReprovadosAprovado() throws Exception {
		Long[] empresaId = new Long[]{1L};
		Long[] areaIds=null;
		Long[] estabelecimentoIds=null;

		Curso curso = CursoFactory.getEntity(10L);
		
		Colaborador colaborador = ColaboradorFactory.getEntity(10L, "Colab Aprovado");
		colaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));

		ColaboradorTurma colaboradorTurma1 = ColaboradorTurmaFactory.getEntity(100l, colaborador);
		colaboradorTurma1.setAprovado(true);
		colaboradorTurma1.setCurso(curso);

		Collection<ColaboradorTurma> colaboradorTurmas =Arrays.asList(colaboradorTurma1);
		when(colaboradorTurmaDao.findRelatorioSemTreinamentoAprovadosOrReprovados(eq(empresaId), eq(new Long[]{curso.getId()}), eq(areaIds), eq(estabelecimentoIds), any(Date.class), eq(SituacaoColaborador.ATIVO), eq(true))).thenReturn(colaboradorTurmas);
		Collection<ColaboradorTurma> resultado = colaboradorTurmaManager.findRelatorioSemTreinamentoAprovadosOrReprovados(empresaId, new Long[]{curso.getId()}, areaIds, estabelecimentoIds, 0, SituacaoColaborador.ATIVO, StatusTreinamento.APROVADO);
		assertEquals(1, resultado.size());
		assertEquals("Colab Aprovado", ((ColaboradorTurma)resultado.toArray()[0]).getColaborador().getNome());
	}
	
	@Test
	public void testFindRelatorioSemTreinamentoColecaoVazia() throws Exception {
		Curso curso = CursoFactory.getEntity(10L);
		Long[] empresaId = new Long[]{1L};
		Long[] areaIds=null;
		Long[] estabelecimentoIds=null;
		
		Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		
		when(colaboradorTurmaDao.findRelatorioSemTreinamentoAprovadosOrReprovados(eq(empresaId), eq(new Long[]{curso.getId()}), eq(areaIds), eq(estabelecimentoIds), any(Date.class), eq(SituacaoColaborador.DESLIGADO), eq(false))).thenReturn(colaboradorTurmas);
		Exception exception = null;
		try{
			colaboradorTurmaManager.findRelatorioSemTreinamentoAprovadosOrReprovados(empresaId, new Long[]{curso.getId()}, areaIds, estabelecimentoIds, 0, SituacaoColaborador.DESLIGADO, StatusTreinamento.REPROVADO);
		} catch (ColecaoVaziaException e) { 
			exception = e; 
		}
		assertNotNull(exception);
		assertTrue(exception instanceof ColecaoVaziaException);
	}
	
	@Test
	public void testFindRelatorioColaboradoresQueNuncaRealizaramOsCursosSelecioandosException() throws Exception {
		Curso curso = CursoFactory.getEntity(10L);
		Long[] empresaId= new Long[]{1L};
		Long[] areaIds=null;
		Long[] estabelecimentoIds=null;
		Long[] cursosIds = {curso.getId()};
		
		Colaborador colaborador = ColaboradorFactory.getEntity(10L, "Colab");
		colaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		ColaboradorTurma colaboradorTurma = new ColaboradorTurma(colaborador, curso);
		Collection<ColaboradorTurma> colaboradoresTurmas = Arrays.asList(colaboradorTurma);
		
		when(cursoManager.findByEmpresaIdAndCursosId(empresaId, cursosIds)).thenReturn(Arrays.asList(curso));
		when(colaboradorManager.findByEmpresaEstabelecimentoAndAreaOrganizacional(empresaId, estabelecimentoIds, areaIds, SituacaoColaborador.ATIVO)).thenReturn(Arrays.asList(colaborador));
		when(colaboradorTurmaDao.findColabororesTurmaComTreinamento(empresaId, areaIds, estabelecimentoIds, cursosIds)).thenReturn(colaboradoresTurmas);

		Exception exception = null;
		try{
			colaboradorTurmaManager.findRelatorioColaboradoresQueNuncaRealizaramOsCursosSelecioandos(empresaId, cursosIds, areaIds, estabelecimentoIds, SituacaoColaborador.ATIVO);
		} catch (ColecaoVaziaException e) { 
			exception = e; 
		}
		assertNotNull(exception);
		assertTrue(exception instanceof ColecaoVaziaException);
	}
	
	@Test
	public void testFindRelatorioColaboradoresQueNuncaRealizaramOsCursosSelecioandos() throws Exception {
		Curso curso = CursoFactory.getEntity(10L);
		Long[] empresaId = new Long[]{1L};
		Long[] areaIds=null;
		Long[] estabelecimentoIds=null;
		Long[] cursosIds = {curso.getId()};
		
		Colaborador colaborador = ColaboradorFactory.getEntity(10L, "Colab");
		colaborador.setAreaOrganizacional(AreaOrganizacionalFactory.getEntity(1L));
		
		when(cursoManager.findByEmpresaIdAndCursosId(empresaId, cursosIds)).thenReturn(Arrays.asList(curso));
		when(colaboradorManager.findByEmpresaEstabelecimentoAndAreaOrganizacional(empresaId, estabelecimentoIds, areaIds, SituacaoColaborador.ATIVO)).thenReturn(Arrays.asList(colaborador));
		when(colaboradorTurmaDao.findColabororesTurmaComTreinamento(empresaId, areaIds, estabelecimentoIds, cursosIds)).thenReturn(new ArrayList<ColaboradorTurma>());
		
		Collection<ColaboradorTurma> colaboradoresTurmasRetorno = colaboradorTurmaManager.findRelatorioColaboradoresQueNuncaRealizaramOsCursosSelecioandos(empresaId, cursosIds, areaIds, estabelecimentoIds, SituacaoColaborador.ATIVO);
		assertEquals(1, colaboradoresTurmasRetorno.size());
	}
	
	@Test
	public void testMontaExibicaoAprovadosReprovados() throws Exception
	{
		Collection<ColaboradorTurma> colaboradorTurmas = montaColaboradoresTurmasAprovadosEReprovados(); 
		
		long turmaId = 1;
		
		when(colaboradorTurmaDao.findByTurmaId(turmaId)).thenReturn(colaboradorTurmas);
		Collection<Colaborador> colaboradores = colaboradorTurmaManager.montaExibicaoAprovadosReprovados(turmaId);
		
		assertEquals(5, colaboradores.size());
		 
		assertEquals("Colaborador Aprovado", ((Colaborador) colaboradores.toArray()[0]).getNome());
		assertEquals("<span style='color: red;'>Colaborador Reprovado (" + MotivoReprovacao.REPROVADO.getDescricao() + ") </span>", ((Colaborador) colaboradores.toArray()[1]).getNome());
		assertEquals("<span style='color: red;'>Colaborador Reprovado Por Frequencia (" + MotivoReprovacao.FREQUENCIA.getDescricao() + ") </span>" , ((Colaborador) colaboradores.toArray()[2]).getNome());
		assertEquals("<span style='color: red;'>Colaborador Reprovado Por Nota (" +  MotivoReprovacao.NOTA.getDescricao() + ") </span>" , ((Colaborador) colaboradores.toArray()[3]).getNome());
		assertEquals("<span style='color: red;'>Colaborador Reprovado Por Nota E Frequencia ("  + MotivoReprovacao.NOTA_FREQUENCIA.getDescricao() + ") </span>" , ((Colaborador) colaboradores.toArray()[4]).getNome());
	}
	
	private Collection<ColaboradorTurma> montaColaboradoresTurmasAprovadosEReprovados(){
		ColaboradorTurma colaboradorTurmaAprovado = criarColaboradorTurma("Colaborador Aprovado", true, null);
		ColaboradorTurma colaboradorTurmaReprovadoPorNota = criarColaboradorTurma("Colaborador Reprovado Por Nota", false, MotivoReprovacao.NOTA.getMotivo());
		ColaboradorTurma colaboradorTurmaReprovadoPorFrequencia = criarColaboradorTurma("Colaborador Reprovado Por Frequencia", false, MotivoReprovacao.FREQUENCIA.getMotivo());
		ColaboradorTurma colaboradorTurmaReprovadoPorNotaEFrequencia = criarColaboradorTurma("Colaborador Reprovado Por Nota E Frequencia", false, MotivoReprovacao.NOTA_FREQUENCIA.getMotivo());
		ColaboradorTurma colaboradorTurmaReprovado = criarColaboradorTurma("Colaborador Reprovado", false, MotivoReprovacao.REPROVADO.getMotivo());
		
		Collection<ColaboradorTurma> colaboradoresTurma = Arrays.asList(colaboradorTurmaAprovado, colaboradorTurmaReprovado, colaboradorTurmaReprovadoPorFrequencia, colaboradorTurmaReprovadoPorNota, colaboradorTurmaReprovadoPorNotaEFrequencia);
		return colaboradoresTurma;
	}

	private ColaboradorTurma criarColaboradorTurma(String nomeColaborador, boolean aprovado, String motivoReprovacao ){
		ColaboradorTurma colaboradorTurma = ColaboradorTurmaFactory.getEntity(nomeColaborador, aprovado, motivoReprovacao);
		return colaboradorTurma;
	}
}