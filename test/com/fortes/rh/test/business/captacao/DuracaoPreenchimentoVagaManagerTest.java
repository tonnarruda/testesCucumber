package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.captacao.CandidatoSolicitacaoManager;
import com.fortes.rh.business.captacao.DuracaoPreenchimentoVagaManagerImpl;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.model.captacao.relatorio.IndicadorDuracaoPreenchimentoVaga;
import com.fortes.rh.model.dicionario.StatusSolicitacao;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

@SuppressWarnings("deprecation")
public class DuracaoPreenchimentoVagaManagerTest extends MockObjectTestCase
{
	DuracaoPreenchimentoVagaManagerImpl duracaoPreenchimentoVagaManager;
	Mock candidatoSolicitacaoManager;
	private Mock estabelecimentoManager;
	private Mock cargoManager;
	private Mock areaOrganizacionalManager;
	private Mock solicitacaoManager;
	
    protected void setUp() throws Exception
    {
        duracaoPreenchimentoVagaManager = new DuracaoPreenchimentoVagaManagerImpl();

        candidatoSolicitacaoManager = new Mock(CandidatoSolicitacaoManager.class);
        
        estabelecimentoManager = new Mock(EstabelecimentoManager.class);
        duracaoPreenchimentoVagaManager.setEstabelecimentoManager((EstabelecimentoManager) estabelecimentoManager.proxy());
        
        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        duracaoPreenchimentoVagaManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
        
        cargoManager = new Mock(CargoManager.class);
        duracaoPreenchimentoVagaManager.setCargoManager((CargoManager) cargoManager.proxy());
        
        solicitacaoManager = new Mock(SolicitacaoManager.class);
        duracaoPreenchimentoVagaManager.setSolicitacaoManager((SolicitacaoManager) solicitacaoManager.proxy());

        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
    }

    protected void tearDown() throws Exception
    {
    	Mockit.restoreAllOriginalDefinitions();
    }

    public void testGerarIndicadorDuracaoPreenchimentoVagas() throws Exception
    {
    	MockSpringUtil.mocks.put("candidatoSolicitacaoManager", candidatoSolicitacaoManager);
    	
    	Date dataDe = DateUtil.criarDataMesAno(1,1,2008);
    	Date dataAte = DateUtil.criarAnoMesDia(20,1,2008);
    	
    	Collection<Long> areaIds = Arrays.asList(1L);
    	Collection<Long> estabelecimentoIds = Arrays.asList(1L);
    	Long[] solicitacaoIds = null;
    	Long empresaId = 1L;
    	
    	Collection<AreaOrganizacional> areaOrganizacionals = new ArrayList<AreaOrganizacional>();
    	areaOrganizacionals.add(AreaOrganizacionalFactory.getEntity(1L));
    	areaOrganizacionals.add(AreaOrganizacionalFactory.getEntity(2L));
    	Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
    	estabelecimentos.add(EstabelecimentoFactory.getEntity(10L));
    	
		areaOrganizacionalManager.expects(once()).method("findAllSelectOrderDescricao").with(eq(empresaId), eq(AreaOrganizacional.TODAS), ANYTHING, ANYTHING).will(returnValue(areaOrganizacionals));
    	estabelecimentoManager.expects(once()).method("findAllSelect").will(returnValue(estabelecimentos));
    	
    	Collection<IndicadorDuracaoPreenchimentoVaga> indicadores = new ArrayList<IndicadorDuracaoPreenchimentoVaga>();
    	IndicadorDuracaoPreenchimentoVaga mediaDias1 = new IndicadorDuracaoPreenchimentoVaga(1L, 1L, 1L, 3, 45.0);
    	IndicadorDuracaoPreenchimentoVaga mediaDias2 = new IndicadorDuracaoPreenchimentoVaga(1L, 1L, 2L, 1, 0.0);
    	indicadores.add(mediaDias1);
    	indicadores.add(mediaDias2);
    	
    	Collection<IndicadorDuracaoPreenchimentoVaga> indicadoresQtdCandidatos = new ArrayList<IndicadorDuracaoPreenchimentoVaga>();
    	IndicadorDuracaoPreenchimentoVaga qtdCandidatos1 = new IndicadorDuracaoPreenchimentoVaga(1L, 1L, 1L, 27);
    	IndicadorDuracaoPreenchimentoVaga qtdCandidatos2 = new IndicadorDuracaoPreenchimentoVaga(1L, 1L, 2L, 12);
    	indicadoresQtdCandidatos.add(qtdCandidatos1);
    	indicadoresQtdCandidatos.add(qtdCandidatos2);
    	
    	List<IndicadorDuracaoPreenchimentoVaga> indicadoresQtdVagas = new ArrayList<IndicadorDuracaoPreenchimentoVaga>();
    	IndicadorDuracaoPreenchimentoVaga qtdVagas1 = new IndicadorDuracaoPreenchimentoVaga(5, 1L, 1L, 1L);
    	IndicadorDuracaoPreenchimentoVaga qtdVagas2 = new IndicadorDuracaoPreenchimentoVaga(2, 1L, 1L, 2L);
    	indicadoresQtdVagas.add(qtdVagas1);
    	indicadoresQtdVagas.add(qtdVagas2);
    	
    	solicitacaoManager.expects(once()).method("getIndicadorMediaDiasPreenchimentoVagas").with(new Constraint[]{eq(dataDe), eq(dataAte), eq(areaIds), eq(estabelecimentoIds), eq(solicitacaoIds), eq(empresaId), eq(true)}).will(returnValue(indicadores));
    	solicitacaoManager.expects(once()).method("getIndicadorQtdCandidatos").with(new Constraint[]{eq(dataDe), eq(dataAte), eq(areaIds), eq(estabelecimentoIds), eq(solicitacaoIds)}).will(returnValue(indicadoresQtdCandidatos));
		solicitacaoManager.expects(once()).method("getIndicadorQtdVagas").with(new Constraint[]{eq(dataDe), eq(dataAte), eq(areaIds), eq(estabelecimentoIds), eq(solicitacaoIds)}).will(returnValue(indicadoresQtdVagas));
		
		cargoManager.expects(atLeastOnce()).method("findByIdProjection").with(eq(1L)).will(returnValue(CargoFactory.getEntity(1L)));
		cargoManager.expects(atLeastOnce()).method("findByIdProjection").with(eq(2L)).will(returnValue(CargoFactory.getEntity(2L)));
		
		Collection<IndicadorDuracaoPreenchimentoVaga> resultado = duracaoPreenchimentoVagaManager.gerarIndicadorDuracaoPreenchimentoVagas(dataDe,dataAte,areaIds,estabelecimentoIds, 1L, solicitacaoIds, true);
    	assertEquals(2, resultado.size());
		IndicadorDuracaoPreenchimentoVaga indicador1 = (IndicadorDuracaoPreenchimentoVaga)resultado.toArray()[0];
		IndicadorDuracaoPreenchimentoVaga indicador2 = (IndicadorDuracaoPreenchimentoVaga)resultado.toArray()[1];
		assertEquals(45.0, indicador1.getMediaDias());
		assertEquals(0.0, indicador2.getMediaDias());
		assertEquals(12, indicador2.getQtdCandidatos().intValue());
		assertEquals(2, indicador2.getQtdVagas().intValue());
    }

    public void testGerarIndicadorMotivoPreenchimentoVagas() throws Exception
    {
    	Date dataDe = DateUtil.criarAnoMesDia(2008, 01, 01);
    	Date dataAte = DateUtil.criarAnoMesDia(2008, 01, 20);
    	Collection<Long> areasIds = new ArrayList<Long>();
    	areasIds.add(1L);
    	Collection<Long> estabelecimentosIds = new ArrayList<Long>();
    	estabelecimentosIds.add(1L);
    	
    	Collection<AreaOrganizacional> areaOrganizacionals = Arrays.asList(AreaOrganizacionalFactory.getEntity(1L));

    	Long empresaId=1L;
    	IndicadorDuracaoPreenchimentoVaga indicadorDuracaoPreenchimentoVaga = new IndicadorDuracaoPreenchimentoVaga("Estabelecimento", 1L, "Cargo", 1L, "Aumento de Quadro", 65);
    	List<IndicadorDuracaoPreenchimentoVaga> indicadores = Arrays.asList(indicadorDuracaoPreenchimentoVaga);
    	
		areaOrganizacionalManager.expects(once()).method("findAllSelectOrderDescricao").with(eq(empresaId), eq(AreaOrganizacional.TODAS), ANYTHING, ANYTHING).will(returnValue(areaOrganizacionals));
    	
    	solicitacaoManager.expects(once()).method("getIndicadorMotivosSolicitacao").with(new Constraint[]{eq(dataDe), eq(dataAte), eq(areasIds),eq(estabelecimentosIds),eq(empresaId),ANYTHING,ANYTHING,ANYTHING}).will(returnValue(indicadores));

		Collection<IndicadorDuracaoPreenchimentoVaga> resultado = duracaoPreenchimentoVagaManager.gerarIndicadorMotivoPreenchimentoVagas(dataDe, dataAte, areasIds,estabelecimentosIds, empresaId, StatusSolicitacao.TODAS, 'S', false);
		
		assertEquals(1, resultado.size());
		assertEquals(65, ((IndicadorDuracaoPreenchimentoVaga)resultado.toArray()[0]).getQtdAberturas().intValue());
    }
}
