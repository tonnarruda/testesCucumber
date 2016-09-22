package com.fortes.rh.test.business.geral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.acesso.UsuarioManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorManagerImpl;
import com.fortes.rh.business.geral.MensagemManager;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.CidadeFactory;
import com.fortes.rh.test.factory.geral.EstadoFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtilJUnit4;
import com.fortes.rh.util.SpringUtil;
import com.fortes.rh.web.ws.AcPessoalClientColaborador;

public class ColaboradorManagerTest_Junit4
{
	private ColaboradorManagerImpl colaboradorManager = new ColaboradorManagerImpl();
    private ColaboradorDao colaboradorDao;
    private CandidatoManager candidatoManager;
    private CandidatoSolicitacaoManager candidatoSolicitacaoManager;
    private MensagemManager mensagemManager;
    private UsuarioManager usuarioManager;
    private CidadeManager cidadeManager;
    private AcPessoalClientColaborador acPessoalClientColaborador;
    private HistoricoColaboradorManager historicoColaboradorManager;

    @Before
    public void setUp() throws Exception
    {
        colaboradorDao = mock(ColaboradorDao.class);
        candidatoManager = mock(CandidatoManager.class);
        candidatoSolicitacaoManager = mock(CandidatoSolicitacaoManager.class);
        mensagemManager = mock(MensagemManager.class);
        cidadeManager = mock(CidadeManager.class);
        acPessoalClientColaborador = mock(AcPessoalClientColaborador.class);
        historicoColaboradorManager= mock(HistoricoColaboradorManager.class);
        
        colaboradorManager.setDao(colaboradorDao);
        colaboradorManager.setCandidatoManager(candidatoManager);
        colaboradorManager.setCandidatoSolicitacaoManager(candidatoSolicitacaoManager);
        colaboradorManager.setMensagemManager(mensagemManager);
        colaboradorManager.setCidadeManager(cidadeManager);
        colaboradorManager.setAcPessoalClientColaborador(acPessoalClientColaborador);
        colaboradorManager.setHistoricoColaboradorManager(historicoColaboradorManager);
        
        usuarioManager = mock(UsuarioManager.class);
        MockSpringUtilJUnit4.mocks.put("usuarioManager", usuarioManager);
        
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtilJUnit4.class);
    }
    
    @Test
	public void testReligaColaborador() 
	{
    	Long colaboradorId = 1L;
    	Exception exception = null;

    	try {
			colaboradorManager.religaColaborador(colaboradorId);
		} catch (Exception e) {
			exception = e;
		}
    	assertNull(exception);
	}
    
    @Test
	public void testReligaColaboradorAC() 
	{
    	Colaborador colaborador = ColaboradorFactory.getEntity(1L);
    	String codigoAC = "000001";
    	String empresaCodigoAC =	"0002";
    	String grupoAC = "0001"; 
    	
    	when(colaboradorDao.findByCodigoAC(codigoAC, empresaCodigoAC, grupoAC)).thenReturn(colaborador);
		colaboradorManager.religaColaboradorAC(codigoAC, empresaCodigoAC, grupoAC);
    	assertEquals(colaborador.getId(), colaboradorManager.religaColaboradorAC(codigoAC, empresaCodigoAC, grupoAC));
	}
    
    @Test
    public void testReenviaAguardandoContratacao() throws Exception{
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Cidade cidade = CidadeFactory.getEntity(1L);
    	cidade.setUf(EstadoFactory.getEntity(1L));
    	
    	Colaborador colaborador1 = ColaboradorFactory.getEntity(1L);
    	colaborador1.setHistoricoColaborador(HistoricoColaboradorFactory.getEntity(1L));
    	colaborador1.getPessoal().setEscolaridade(Escolaridade.ESPECIALIZACAO_INCOMPLETA);
    	colaborador1.getEndereco().setCidade(cidade);
    	
    	Colaborador colaborador2 = ColaboradorFactory.getEntity(2L);
    	colaborador2.setHistoricoColaborador(HistoricoColaboradorFactory.getEntity(2L));
    	colaborador2.getEndereco().setCidade(cidade);
    	
    	Collection<Colaborador> colaboradores = Arrays.asList(colaborador1, colaborador2);
    	
    	when(colaboradorDao.findByEmpresaAndStatusAC(empresa.getId(), null, null, StatusRetornoAC.AGUARDANDO, true, false, SituacaoColaborador.ATIVO, true, "c.nome")).thenReturn(colaboradores);
    	when(cidadeManager.findByIdProjection(cidade.getId())).thenReturn(cidade);
    	when(historicoColaboradorManager.bindSituacao(colaborador1.getHistoricoColaborador(), empresa.getCodigoAC())).thenReturn(new TSituacao());
    	when(historicoColaboradorManager.bindSituacao(colaborador2.getHistoricoColaborador(), empresa.getCodigoAC())).thenReturn(new TSituacao());
    	
    	Exception ex = null;
    	try {
    		colaboradorManager.reenviaAguardandoContratacao(empresa);
		} catch (Exception e) {
			ex = e;
		}
    	
    	assertNull(ex);
    }
    
}