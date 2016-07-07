package com.fortes.rh.test.model.avaliacao;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.model.avaliacao.RelatorioAnaliseDesempenhoColaborador;
import com.fortes.rh.model.avaliacao.ResultadoCompetencia;
import com.fortes.rh.model.avaliacao.ResultadoCompetenciaColaborador;
import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.geral.Colaborador;

public class RelatorioAnaliseDesempenhoColaboradorTest  {
	
	RelatorioAnaliseDesempenhoColaborador relatorioAnaliseDesempenhoColaborador = new RelatorioAnaliseDesempenhoColaborador();
	
	@Before
	public void setUp() throws Exception{
		ResultadoCompetencia resultadoCompetencia1A = new ResultadoCompetencia(null, RelatorioAnaliseDesempenhoColaborador.AUTOAVALIACAO, 5.0);
		ResultadoCompetencia resultadoCompetencia2A = new ResultadoCompetencia(null, "colab 2A", 2.0);
		ResultadoCompetencia resultadoCompetencia3A = new ResultadoCompetencia(null, "colab 1A", 3.0);
		ResultadoCompetencia resultadoCompetencia4A = new ResultadoCompetencia(null, RelatorioAnaliseDesempenhoColaborador.OUTROSAVALIADORES, 2.0);
		ResultadoCompetencia resultadoCompetencia5A = new ResultadoCompetencia(null, RelatorioAnaliseDesempenhoColaborador.MEDIA, 3.0);
		
		ResultadoCompetencia resultadoCompetencia1B = new ResultadoCompetencia(null, RelatorioAnaliseDesempenhoColaborador.AUTOAVALIACAO, 2.0);
		ResultadoCompetencia resultadoCompetencia2B = new ResultadoCompetencia(null, "colab 2B", 3.0);
		ResultadoCompetencia resultadoCompetencia3B = new ResultadoCompetencia(null, RelatorioAnaliseDesempenhoColaborador.OUTROSAVALIADORES, 3.0);
		ResultadoCompetencia resultadoCompetencia4B = new ResultadoCompetencia(null, RelatorioAnaliseDesempenhoColaborador.MEDIA, 3.0);
		
		ResultadoCompetenciaColaborador resultadoCompetenciaColaboradorA = new ResultadoCompetenciaColaborador();
		resultadoCompetenciaColaboradorA.setCompetenciaId(1L);
		resultadoCompetenciaColaboradorA.setCompetenciaNome("Competência A");
		resultadoCompetenciaColaboradorA.setResultadoCompetencias(Arrays.asList(resultadoCompetencia1A,resultadoCompetencia2A,resultadoCompetencia3A,resultadoCompetencia4A,resultadoCompetencia5A));
		
		ResultadoCompetenciaColaborador resultadoCompetenciaColaboradorB = new ResultadoCompetenciaColaborador();
		resultadoCompetenciaColaboradorB.setCompetenciaId(2L);
		resultadoCompetenciaColaboradorB.setCompetenciaNome("Competência B");
		resultadoCompetenciaColaboradorB.setResultadoCompetencias(Arrays.asList(resultadoCompetencia1B,resultadoCompetencia2B,resultadoCompetencia3B,resultadoCompetencia4B));
		
		NivelCompetencia nivelCompetencia1 = new NivelCompetencia("Ruim", 1);
		NivelCompetencia nivelCompetencia2 = new NivelCompetencia("Bom", 2);
		NivelCompetencia nivelCompetencia3 = new NivelCompetencia("Ótimo", 3);
		
		relatorioAnaliseDesempenhoColaborador.setNotaMinimaMediaGeralCompetencia(2);
		relatorioAnaliseDesempenhoColaborador.setAvaliado(new Colaborador("Samuel"));
		relatorioAnaliseDesempenhoColaborador.setValorMaximoGrafico(4);
		relatorioAnaliseDesempenhoColaborador.setResultadosCompetenciaColaborador(Arrays.asList(resultadoCompetenciaColaboradorA,resultadoCompetenciaColaboradorB));
		relatorioAnaliseDesempenhoColaborador.setNiveisCompetencias(Arrays.asList(nivelCompetencia1,nivelCompetencia2,nivelCompetencia3));
		relatorioAnaliseDesempenhoColaborador.montaLegenda();
    }
	
	@Test
	public void testGetResultadoCompetenciaFinal(){
		Collection<ResultadoCompetencia> result = relatorioAnaliseDesempenhoColaborador.getResultadoCompetenciaFinal();
		assertEquals(6, result.size());
	}
	
	@Test
	public void testGetLegenda(){
		assertEquals("A - Auto-Avaliação\nB - colab 1A\nC - colab 2A\nD - colab 2B\nE - Outros Avaliadores\nF - Média\n", relatorioAnaliseDesempenhoColaborador.getLegenda());
	}
	
	@Test
	public void testGetNiveisCompetenciasDescricao(){
		assertEquals("Ruim,Bom,Ótimo", relatorioAnaliseDesempenhoColaborador.getNiveisCompetenciasDescricao());
	}
	
	@Test
	public void testGetNiveisCompetenciasOrdem(){
		assertEquals("1,2,3", relatorioAnaliseDesempenhoColaborador.getNiveisCompetenciasOrdem());
	}
	
	@Test
	public void testGetsAndSets(){
		assertEquals("Samuel", relatorioAnaliseDesempenhoColaborador.getAvaliado().getNome());
		assertEquals(2, relatorioAnaliseDesempenhoColaborador.getResultadosCompetenciaColaborador().size());
		assertEquals(new Integer(4), relatorioAnaliseDesempenhoColaborador.getValorMaximoGrafico());
		assertEquals(new Integer(2), relatorioAnaliseDesempenhoColaborador.getNotaMinimaMediaGeralCompetencia());
		assertEquals(new Integer(3), relatorioAnaliseDesempenhoColaborador.getTamanhoCollectionNiveisCompetencias());
		assertEquals(3, relatorioAnaliseDesempenhoColaborador.getNiveisCompetencias().size());
	}
}