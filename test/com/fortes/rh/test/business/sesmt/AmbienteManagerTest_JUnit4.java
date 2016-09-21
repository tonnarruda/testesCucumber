package com.fortes.rh.test.business.sesmt;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManagerImpl;
import com.fortes.rh.business.sesmt.ComposicaoSesmtManager;
import com.fortes.rh.business.sesmt.EpcManager;
import com.fortes.rh.business.sesmt.EpiManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoMedicaoRiscoManager;
import com.fortes.rh.dao.sesmt.AmbienteDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoAmbienteFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;

public class AmbienteManagerTest_JUnit4
{
	private AmbienteManagerImpl ambienteManager = new AmbienteManagerImpl();
	private AmbienteDao ambienteDao = null;
	private HistoricoAmbienteManager historicoAmbienteManager = null;
	private EstabelecimentoManager estabelecimentoManager;
	private FuncaoManager funcaoManager;
	private RiscoMedicaoRiscoManager riscoMedicaoRiscoManager;
	private EpcManager epcManager;
	private EpiManager epiManager;
	private ComposicaoSesmtManager composicaoSesmtManager;
	private EmpresaManager empresaManager;

	@Before
	public void setUp() throws Exception
    {
        ambienteDao = mock(AmbienteDao.class);
        ambienteManager.setDao(ambienteDao);

        historicoAmbienteManager = mock(HistoricoAmbienteManager.class);
        ambienteManager.setHistoricoAmbienteManager(historicoAmbienteManager);
        
        estabelecimentoManager = mock(EstabelecimentoManager.class);
        ambienteManager.setEstabelecimentoManager(estabelecimentoManager);
        
        funcaoManager = mock(FuncaoManager.class);
        ambienteManager.setFuncaoManager(funcaoManager);
        
        riscoMedicaoRiscoManager = mock(RiscoMedicaoRiscoManager.class);
        ambienteManager.setRiscoMedicaoRiscoManager(riscoMedicaoRiscoManager);
        
        epcManager = mock(EpcManager.class);
        ambienteManager.setEpcManager(epcManager);
        
        epiManager = mock(EpiManager.class);
        ambienteManager.setEpiManager(epiManager);

        composicaoSesmtManager = mock(ComposicaoSesmtManager.class);
        ambienteManager.setComposicaoSesmtManager(composicaoSesmtManager);
        
        empresaManager = mock(EmpresaManager.class);
        ambienteManager.setEmpresaManager(empresaManager);
    }
	
	@Test
	public void testSincronizar(){
		
		Empresa empresaOrigem = EmpresaFactory.getEmpresa(1L);
		Empresa empresaDestino = EmpresaFactory.getEmpresa(2L);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
		Collection<Estabelecimento> estabelecimentos = Arrays.asList(estabelecimento);
		
		HistoricoAmbiente ha1 = HistoricoAmbienteFactory.getEntity("ha1", null, DateUtil.criarDataMesAno(1, 1, 2000), "tempoExposicao hc1");
		HistoricoAmbiente ha2 = HistoricoAmbienteFactory.getEntity("ha2", null, DateUtil.criarDataMesAno(1, 2, 2000), "tempoExposicao hc2");
		
		Ambiente a1 = new Ambiente();
		a1.setHistoricoAtual(ha1);
		a1.setNome("A1");

		Ambiente a2 = new Ambiente();
		a2.setHistoricoAtual(ha2);
		a2.setNome("F2");

		Collection<Ambiente> ambientes = Arrays.asList(a1, a2);
		
		when(ambienteDao.findAllByEmpresa(empresaOrigem.getId())).thenReturn(ambientes);
		when(estabelecimentoManager.findByEmpresa(empresaDestino.getId())).thenReturn(estabelecimentos);
		
		Exception ex = null;
		try {
			ambienteManager.sincronizar(empresaOrigem.getId(), empresaDestino.getId());
		} catch (Exception e) {
			ex = e;
		}
		
		assertNull(ex);
	}
}