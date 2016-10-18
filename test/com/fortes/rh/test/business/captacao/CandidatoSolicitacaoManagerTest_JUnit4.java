package com.fortes.rh.test.business.captacao;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.captacao.CandidatoSolicitacaoDao;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.dicionario.StatusCandidatoSolicitacao;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.CandidatoFactory;
import com.fortes.rh.test.factory.captacao.CandidatoSolicitacaoFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.SolicitacaoFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.SpringUtil;

public class CandidatoSolicitacaoManagerTest_JUnit4
{
	private CandidatoSolicitacaoManagerImpl candidatoSolicitacaoManager = new CandidatoSolicitacaoManagerImpl();
	private CandidatoSolicitacaoDao candidatoSolicitacaoDao;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private ColaboradorManager colaboradorManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;

	@Before
    public void setUp() throws Exception
    {
		candidatoSolicitacaoDao = mock(CandidatoSolicitacaoDao.class);
		candidatoSolicitacaoManager.setDao(candidatoSolicitacaoDao);
		
		parametrosDoSistemaManager = mock(ParametrosDoSistemaManager.class);
		candidatoSolicitacaoManager.setParametrosDoSistemaManager(parametrosDoSistemaManager);
		
		colaboradorManager = mock(ColaboradorManager.class);
		MockSpringUtilJUnit4.mocks.put("colaboradorManager", colaboradorManager);
		
		gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
		MockSpringUtilJUnit4.mocks.put("gerenciadorComunicacaoManager", gerenciadorComunicacaoManager);

        Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
    }

	@Test
    public void testInsertCandidatos(){

		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();

		Candidato c1 = new Candidato();
		c1.setId(1L);

		Candidato c2 = new Candidato();
		c2.setId(3L);

		Candidato c3 = new Candidato();
		c3.setId(2L);

		CandidatoSolicitacao cs1 = CandidatoSolicitacaoFactory.getEntity();
		cs1.setId(1L);
		cs1.setCandidato(c1);

		CandidatoSolicitacao cs2 = CandidatoSolicitacaoFactory.getEntity();
		cs2.setId(2L);
		cs2.setCandidato(c2);

		CandidatoSolicitacao cs3 = CandidatoSolicitacaoFactory.getEntity();
		cs3.setId(3L);
		cs3.setCandidato(c3);
		cs3.setTriagem(true);

		Collection<CandidatoSolicitacao> cands = new ArrayList<CandidatoSolicitacao>();

		cands.add(cs1);
		cands.add(cs2);
		cands.add(cs3);

        String[] properties = new String[]{"id","candidato.id","solicitacao.id","triagem"};
        String[] sets = new String[]{"id","candidatoId","solicitacaoId","triagem"};
		
		when(candidatoSolicitacaoDao.findToList(properties, sets,new String[]{"solicitacao"}, new Object[]{solicitacao})).thenReturn(cands);
		when(candidatoSolicitacaoDao.save(cs1)).thenReturn(cs1);
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
    	
    	candidatoSolicitacaoManager.insertCandidatos(new String[]{"1", "2", "4"}, solicitacao, StatusCandidatoSolicitacao.INDIFERENTE, EmpresaFactory.getEmpresa(1L), UsuarioFactory.getEntity(1L));
    }
	
	@Test
    public void testInsertCandidatosAutorizacaoGestorNaSolicitacaoPessoal(){

		ParametrosDoSistema parametrosDoSistema = ParametrosDoSistemaFactory.getEntity(1L);
		parametrosDoSistema.setAutorizacaoGestorNaSolicitacaoPessoal(true);
		
    	Solicitacao solicitacao = SolicitacaoFactory.getSolicitacao();

		Candidato c1 = CandidatoFactory.getCandidato(1L);
		Candidato c2 = CandidatoFactory.getCandidato(2L);
		Candidato c3 = CandidatoFactory.getCandidato(3L);

		CandidatoSolicitacao cs1 = CandidatoSolicitacaoFactory.getEntity(1L);
		cs1.setCandidato(c1);

		CandidatoSolicitacao cs2 = CandidatoSolicitacaoFactory.getEntity(2L);
		cs2.setCandidato(c2);

		CandidatoSolicitacao cs3 = CandidatoSolicitacaoFactory.getEntity(3L);
		cs3.setCandidato(c3);
		cs3.setTriagem(true);

		Collection<CandidatoSolicitacao> cands = new ArrayList<CandidatoSolicitacao>();
		cands.add(cs1);
		cands.add(cs2);
		cands.add(cs3);

        String[] properties = new String[]{"id","candidato.id","solicitacao.id","triagem"};
        String[] sets = new String[]{"id","candidatoId","solicitacaoId","triagem"};
		
        Colaborador colaborador = ColaboradorFactory.getEntity(1L);
        
		when(candidatoSolicitacaoDao.findToList(properties, sets,new String[]{"solicitacao"}, new Object[]{solicitacao})).thenReturn(cands);
		when(candidatoSolicitacaoDao.save(cs1)).thenReturn(cs1);
		when(parametrosDoSistemaManager.findById(1L)).thenReturn(parametrosDoSistema);
		when(colaboradorManager.findByCandidato(4L, 1L)).thenReturn(colaborador);
    	
    	candidatoSolicitacaoManager.insertCandidatos(new String[]{"1", "2", "4"}, solicitacao, StatusCandidatoSolicitacao.INDIFERENTE, EmpresaFactory.getEmpresa(1L), UsuarioFactory.getEntity(1L));
    }
	
	@Test
	public void atualizaCandidatoSolicitacaoAoReligarColaborador() 
	{
		Long colaboradorId = 1L;
    	Exception exception = null;

    	try {
			candidatoSolicitacaoManager.atualizaCandidatoSolicitacaoAoReligarColaborador(colaboradorId);
		} catch (Exception e) {
			exception = e;
		}
    	assertNull(exception);
	}
	
	@Test
	public void testAtalizaStatusEDataContratacaoOrPromocao()
	{
		CandidatoSolicitacao csDoBanco = CandidatoSolicitacaoFactory.getEntity(1L, StatusCandidatoSolicitacao.CONTRATADO, new Date());
		
		CandidatoSolicitacao csAtualizado = csDoBanco;
		csDoBanco.setStatus(StatusCandidatoSolicitacao.INDIFERENTE);
		csDoBanco.setDataAutorizacaoGestor(null);

		when(candidatoSolicitacaoDao.findById(eq(csDoBanco.getId()))).thenReturn(csDoBanco);
		candidatoSolicitacaoManager.setStatusAndDataContratacaoOrPromocao(csDoBanco.getId(), StatusCandidatoSolicitacao.INDIFERENTE, null);
		verify(candidatoSolicitacaoDao).update(csAtualizado);
	}
}