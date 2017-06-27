package com.fortes.rh.test.business.captacao;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.captacao.CandidatoDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoJsonVO;
import com.fortes.rh.model.dicionario.OrigemCandidato;
import com.fortes.rh.model.geral.Contato;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.CamposExtrasFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;

public class CandidatoManagerTest_Junit4 
{
	private CandidatoManagerImpl candidatoManager;
	private CandidatoDao candidatoDao;
	private ParametrosDoSistemaManager parametrosDoSistemaManager; 
	
	@Before
    public void setUp() throws Exception
    {
		candidatoManager = new CandidatoManagerImpl();
        candidatoDao = mock(CandidatoDao.class);
        parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
        
        candidatoManager.setDao(candidatoDao);
        candidatoManager.setParametrosDoSistemaManager(parametrosDoSistemaManager);
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
	
	@Test
	public void testFindPorEmpresaByCpfSenhaApenasUmCandidatoComOCpfInformado(){
		String cpf = "234";
		String senha = "1234";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Candidato candidato = CandidatoFactory.getCandidatoDiponivel("C1", cpf, empresa);
		candidato.setOrigem(OrigemCandidato.EXTERNO);
		
		Collection<Candidato> candidatos = Arrays.asList(candidato);
		
		when(candidatoDao.findPorEmpresaByCpfSenha(cpf, senha, empresa.getId())).thenReturn(candidatos);
		assertEquals(candidato.getPessoal().getCpf(), candidatoManager.findPorEmpresaByCpfSenha(cpf, senha, empresa.getId()).getPessoal().getCpf());
	}
	
	@Test
	public void testFindPorEmpresaByCpfSenhaMaisDeUmCandidatoComOCpfInformado(){
		String cpf = "234";
		String senha = "1234";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Candidato candidato1 = CandidatoFactory.getCandidatoDiponivel("C1", cpf, empresa);
		candidato1.setId(1L);
		candidato1.setOrigem(OrigemCandidato.EXTERNO);
		
		Candidato candidato2 = CandidatoFactory.getCandidatoDiponivel("C1", cpf, empresa);
		candidato2.setId(2L);
		candidato2.setOrigem(OrigemCandidato.CADASTRADO);
		
		Collection<Candidato> candidatos = Arrays.asList(candidato1, candidato2);
		
		when(candidatoDao.findPorEmpresaByCpfSenha(cpf, senha, empresa.getId())).thenReturn(candidatos);
		assertEquals(candidato1.getId(), candidatoManager.findPorEmpresaByCpfSenha(cpf, senha, empresa.getId()).getId());
	}
	
	@Test
	public void testFindPorEmpresaByCpfSenhaMaisDeUmCandidatoComOCpfInformadoMasNenhumEOrigemExterna(){
		String cpf = "234";
		String senha = "1234";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Candidato candidato1 = CandidatoFactory.getCandidatoDiponivel("C1", cpf, empresa);
		candidato1.setId(1L);
		candidato1.setOrigem(OrigemCandidato.CADASTRADO);
		
		Candidato candidato2 = CandidatoFactory.getCandidatoDiponivel("C1", cpf, empresa);
		candidato2.setId(2L);
		candidato2.setOrigem(OrigemCandidato.CADASTRADO);
		
		Collection<Candidato> candidatos = Arrays.asList(candidato1, candidato2);
		
		when(candidatoDao.findPorEmpresaByCpfSenha(cpf, senha, empresa.getId())).thenReturn(candidatos);
		assertEquals(candidato1.getId(), candidatoManager.findPorEmpresaByCpfSenha(cpf, senha, empresa.getId()).getId());
	}
	
	@Test
	public void testFindPorEmpresaByCpfSenhaSenhaNaoConfere(){
		String cpf = "234";
		String senha = "1234";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		Candidato candidato1 = CandidatoFactory.getCandidatoDiponivel("C1", cpf, empresa);
		candidato1.setId(1L);
		candidato1.setOrigem(OrigemCandidato.EXTERNO);
		
		Collection<Candidato> candidatos = Arrays.asList(candidato1);
		
		when(candidatoDao.findPorEmpresaByCpfSenha(cpf, senha, empresa.getId())).thenReturn(new ArrayList<Candidato>());
		when(candidatoDao.findByCPF(cpf, empresa.getId(), null, null)).thenReturn(candidatos);
		
		assertEquals(candidato1.getId(), candidatoManager.findPorEmpresaByCpfSenha(cpf, senha, empresa.getId()).getId());
	}
	
	@Test
	public void testFindPorEmpresaByCpfRetornaNuloQuandoCpfNaoConfere(){
		String cpf = "234";
		String senha = "1234";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		
		when(candidatoDao.findPorEmpresaByCpfSenha(cpf, senha, empresa.getId())).thenReturn(new ArrayList<Candidato>());
		when(candidatoDao.findByCPF(cpf, empresa.getId(), null, null)).thenReturn(new ArrayList<Candidato>());
		
		assertNull(candidatoManager.findPorEmpresaByCpfSenha(cpf, senha, empresa.getId()));
	}

	@Test
	public void testRecuperaSenhaUsuarioNulo() throws Exception
	{
		String cpf = "123";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
		parametrosDoSistema.setAppUrl("url");

		when(candidatoDao.findByCPF(cpf, empresa.getId(), null, null)).thenReturn(new ArrayList<Candidato>());
		assertEquals("Candidato não localizado!", candidatoManager.recuperaSenha(cpf, empresa));
	}

	@Test
	public void testRecuperaSenhaUsuarioSemEmail() throws Exception
	{
		String cpf = "123";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Contato contato = new Contato();
		
		Candidato candidato = CandidatoFactory.getCandidato(1L);
		candidato.setCpf(cpf);
		candidato.setContato(contato);
		candidato.setEmpresa(empresa);

		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
		parametrosDoSistema.setAppUrl("url");
		
		when(candidatoDao.findByCPF(cpf, empresa.getId(), null, null)).thenReturn(Arrays.asList(candidato));
		assertEquals("Candidato não possui email cadastrado!\n Por favor entre em contato com a empresa.", candidatoManager.recuperaSenha(cpf, empresa));
	}
	
	@Test
	public void testRecuperaSenha() throws Exception
	{
		String cpf = "123";
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Candidato candidato = CandidatoFactory.getCandidato(1L);
		candidato.setCpf(cpf);
		candidato.setEmpresa(empresa);

		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
		parametrosDoSistema.setAppUrl("url");

		when(candidatoDao.findByCPF(cpf, empresa.getId(), null, null)).thenReturn(Arrays.asList(candidato));
		when(parametrosDoSistemaManager.findById(eq(1L))).thenReturn(parametrosDoSistema);
		
		assertEquals("Nova Senha enviada por e-mail (mail@mail.com). <br>(Caso não tenha recebido, favor entrar em contato com a empresa)", candidatoManager.recuperaSenha(cpf, empresa));
		verify(candidatoDao).atualizaSenha(eq(candidato.getId()), anyString());
	}	
	
	@Test
	public void testRecuperaNovaSenha() throws Exception
	{
		String cpf = "123";
		Empresa empresa = new Empresa();
		empresa.setId(1L);

		Candidato candidato = CandidatoFactory.getCandidato();
		candidato.setId(1L);
		candidato.setCpf(cpf);
		candidato.setEmpresa(empresa);
		candidato.getContato().setEmail("email@grupofortes.com.br");

		ParametrosDoSistema parametrosDoSistema = new ParametrosDoSistema();
		parametrosDoSistema.setAppUrl("url");

		when(parametrosDoSistemaManager.findById(eq(1L))).thenReturn(parametrosDoSistema);
		candidatoManager.enviaNovaSenha(candidato, empresa);

		candidatoManager.setMail(null);
		candidatoManager.enviaNovaSenha(candidato, empresa);
	}
}