package com.fortes.rh.test.business.captacao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.CandidatoManagerImpl;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoJsonVO;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.geral.CamposExtrasFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;

public class CandidatoManagerTest_Junit4 
{
	private CandidatoManagerImpl candidatoManager;
	private CandidatoDao candidatoDao;
	
	@Before
    public void setUp() throws Exception
    {
		candidatoManager = new CandidatoManagerImpl();
        candidatoDao = mock(CandidatoDao.class);
        
        candidatoManager.setDao(candidatoDao);
    }
	
	@Test
	public void testFindCandidatosIndicadosPor() throws ColecaoVaziaException
	{
		Collection<Candidato> candidatosIndicadosPor = Arrays.asList(CandidatoFactory.getCandidato());
		Long[] empresasIds = new Long[]{1L};
		when(candidatoDao.findCandidatosIndicadosPor(any(Date.class), any(Date.class), eq(empresasIds))).thenReturn(candidatosIndicadosPor);
		
		assertEquals(candidatosIndicadosPor, candidatoManager.findCandidatosIndicadosPor(new Date(), new Date(), empresasIds));
	}
	
	@Test(expected=ColecaoVaziaException.class)
	public void testFindCandidatosIndicadosPorColecaoVaziaException() throws ColecaoVaziaException
	{
		Long[] empresasIds = new Long[]{1L};
		when(candidatoDao.findCandidatosIndicadosPor(any(Date.class), any(Date.class), eq(empresasIds))).thenReturn(new ArrayList<Candidato>());
		candidatoManager.findCandidatosIndicadosPor(new Date(), new Date(), empresasIds);
	}
	
	@Test
	public void testGetCandidatosJsonVO(){
		Long etapaSeletivaId = 1L;
		Long candidatoId = 2L;
		
		Candidato candidato = CandidatoFactory.getCandidato(candidatoId, "candidato VO");
		candidato.getEndereco().setCidade(CidadeFactory.getEntity(1L));
		candidato.getEndereco().setUf(EstadoFactory.getEntity(1L));
		candidato.setCamposExtras(CamposExtrasFactory.getEntity(1L));
		
		Collection<Candidato> candidatos = Arrays.asList(candidato);
		
		Map<Long, Collection<String>> funcoesPretendidasMap = new HashMap<Long, Collection<String>>();
		funcoesPretendidasMap.put(candidatoId, new ArrayList<String>());
		funcoesPretendidasMap.get(candidatoId).add("Desenvolvedor");
		funcoesPretendidasMap.get(candidatoId).add("Analista");
		
		when(candidatoDao.getCandidatosByEtapaSeletiva(etapaSeletivaId)).thenReturn(candidatos);
		when(candidatoDao.getFuncoesPretendidasByEtapaSeletiva(etapaSeletivaId)).thenReturn(funcoesPretendidasMap);
		
		Collection<CandidatoJsonVO> candidatoJsonVOs = candidatoManager.getCandidatosJsonVO(etapaSeletivaId);
		
		assertEquals(1, candidatoJsonVOs.size());
		assertEquals(2, ((CandidatoJsonVO) candidatoJsonVOs.toArray()[0]).getFuncoesPretendidas().length);
		assertEquals("candidato VO", ((CandidatoJsonVO) candidatoJsonVOs.toArray()[0]).getNome());
	}
	
	@Test
	public void testGetCandidatosJsonVOSemFuncaoPretendida(){
		Long etapaSeletivaId = 1L;
		Long candidatoId = 2L;
		
		Candidato candidato = CandidatoFactory.getCandidato(candidatoId, "CandVO");
		candidato.getEndereco().setCidade(CidadeFactory.getEntity(3L));
		candidato.getEndereco().setUf(EstadoFactory.getEntity(5L));
		candidato.setCamposExtras(CamposExtrasFactory.getEntity(1L));
		
		Collection<Candidato> candidatos = Arrays.asList(candidato);
		
		when(candidatoDao.getCandidatosByEtapaSeletiva(etapaSeletivaId)).thenReturn(candidatos);
		when(candidatoDao.getFuncoesPretendidasByEtapaSeletiva(etapaSeletivaId)).thenReturn(new HashMap<Long, Collection<String>>());
		
		Collection<CandidatoJsonVO> candidatoJsonVOs = candidatoManager.getCandidatosJsonVO(etapaSeletivaId);
		
		assertEquals(1, candidatoJsonVOs.size());
		assertEquals(0, ((CandidatoJsonVO) candidatoJsonVOs.toArray()[0]).getFuncoesPretendidas().length);
	}
	
	@Test
	public void testGetCandidatosJsonVOComerroEmUmCandidato(){
		Long etapaSeletivaId = 1L;
		Long candidatoId = 2L;
		
		Candidato candidatoQuebrado = CandidatoFactory.getCandidato(1L, "candidato VO");
		
		Candidato candidato = CandidatoFactory.getCandidato(candidatoId, "CandVO");
		candidato.getEndereco().setCidade(CidadeFactory.getEntity(3L));
		candidato.getEndereco().setUf(EstadoFactory.getEntity(5L));
		candidato.setCamposExtras(CamposExtrasFactory.getEntity(1L));
		
		Collection<Candidato> candidatos = Arrays.asList(candidatoQuebrado, candidato);
		
		Map<Long, Collection<String>> funcoesPretendidasMap = new HashMap<Long, Collection<String>>();
		funcoesPretendidasMap.put(candidatoId, new ArrayList<String>());
		funcoesPretendidasMap.get(candidatoId).add("Desenvolvedor");
		funcoesPretendidasMap.get(candidatoId).add("Analista");
		
		when(candidatoDao.getCandidatosByEtapaSeletiva(etapaSeletivaId)).thenReturn(candidatos);
		when(candidatoDao.getFuncoesPretendidasByEtapaSeletiva(etapaSeletivaId)).thenReturn(funcoesPretendidasMap);
		
		Collection<CandidatoJsonVO> candidatoJsonVOs = candidatoManager.getCandidatosJsonVO(etapaSeletivaId);
		
		assertEquals(1, candidatoJsonVOs.size());
		assertEquals(2, ((CandidatoJsonVO) candidatoJsonVOs.toArray()[0]).getFuncoesPretendidas().length);
		assertEquals("CandVO", ((CandidatoJsonVO) candidatoJsonVOs.toArray()[0]).getNome());
	}
}
