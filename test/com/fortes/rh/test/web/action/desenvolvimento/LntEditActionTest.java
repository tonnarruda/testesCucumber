package com.fortes.rh.test.web.action.desenvolvimento;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import mockit.Mockit;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.CursoLntManager;
import com.fortes.rh.business.desenvolvimento.LntManager;
import com.fortes.rh.business.desenvolvimento.ParticipanteCursoLntManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.dicionario.StatusLnt;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.acesso.UsuarioFactory;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoLntFactory;
import com.fortes.rh.test.factory.desenvolvimento.LntFactory;
import com.fortes.rh.test.factory.desenvolvimento.ParticipanteCursoLntFactory;
import com.fortes.rh.test.util.mockObjects.MockRelatorioUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.desenvolvimento.LntEditAction;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class LntEditActionTest
{
	private LntEditAction action;
	private LntManager lntManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private ParticipanteCursoLntManager participanteCursoLntManager;
	private CursoLntManager cursoLntManager;
	private EmpresaManager empresaManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;

	@Before
	public void setUp() throws Exception
	{
		action = new LntEditAction();

		lntManager = mock(LntManager.class);
		action.setLntManager(lntManager);

		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		action.setAreaOrganizacionalManager(areaOrganizacionalManager);

		gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
		action.setGerenciadorComunicacaoManager(gerenciadorComunicacaoManager);
		
		participanteCursoLntManager = mock(ParticipanteCursoLntManager.class);
		action.setParticipanteCursoLntManager(participanteCursoLntManager);
		
		cursoLntManager = mock(CursoLntManager.class);
		action.setCursoLntManager(cursoLntManager);
		
		empresaManager = mock(EmpresaManager.class);
		action.setEmpresaManager(empresaManager);
		
		colaboradorTurmaManager = mock(ColaboradorTurmaManager.class);
		action.setColaboradorTurmaManager(colaboradorTurmaManager);
		
		action.setLnt(new Lnt());
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		Mockit.redefineMethods(RelatorioUtil.class, MockRelatorioUtil.class);
	}

	@Test
	public void testList() throws Exception
	{
		when(lntManager.getCountFindAllLnt(eq(action.getLnt().getDescricao()), eq(StatusLnt.TODOS), eq(action.getEmpresaSistema().getId()))).thenReturn(0);
		when(lntManager.findAllLnt(eq(action.getLnt().getDescricao()), eq(StatusLnt.TODOS), eq(action.getEmpresaSistema().getId()), eq(action.getPage()), eq(action.getPagingSize()))).thenReturn(new ArrayList<Lnt>());
		assertEquals("success", action.list());
	}
	
	@Test
	public void testPrepareInsert() throws Exception{
		Empresa empresa = EmpresaFactory.getEmpresa(1L);
		action.setUsuarioLogado(UsuarioFactory.getEntity(1L));
		
		when(empresaManager.findEmpresasPermitidas(true, action.getEmpresaSistema().getId(), action.getUsuarioLogado().getId())).thenReturn(Arrays.asList(empresa));
		assertEquals("success", action.prepareInsert());
	}
	
	@Test
	public void testPrepareUpdate() throws Exception{
		Lnt lnt = LntFactory.getEntity(1L);
		Empresa empresaNaoPermitida = EmpresaFactory.getEmpresa(1L);
		Empresa empresaPermitida = EmpresaFactory.getEmpresa(2L);
		
		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity();
		
		action.setUsuarioLogado(UsuarioFactory.getEntity(1L));
		action.setLnt(lnt);
		
		CheckBox checkBox = new CheckBox();
		checkBox.setId(empresaNaoPermitida.getId());
		checkBox.setNome(empresaNaoPermitida.getNome());
		
		when(lntManager.findEntidadeComAtributosSimplesById(action.getLnt().getId())).thenReturn(action.getLnt());
		when(areaOrganizacionalManager.findByLntIdComEmpresa(lnt.getId(), new Long[]{})).thenReturn(Arrays.asList(area1));
		when(empresaManager.findByLntId(lnt.getId())).thenReturn(Arrays.asList(empresaNaoPermitida));		
		when(empresaManager.findEmpresasPermitidas(true, action.getEmpresaSistema().getId(), action.getUsuarioLogado().getId())).thenReturn(Arrays.asList(empresaPermitida));
		when(areaOrganizacionalManager.populaCheckComParameters(empresaNaoPermitida.getId())).thenReturn(Arrays.asList(checkBox));
		
		assertEquals("success", action.prepareUpdate());
	}

	@Test
	public void testInsert()
	{
		Lnt lnt = LntFactory.getEntity(1L);
		lnt.setDataInicio(DateUtil.incrementaDias(new Date(), 1));
		lnt.setDataFim(DateUtil.incrementaDias(new Date(), 2));
		action.setLnt(lnt);
		when(lntManager.save(eq(lnt))).thenReturn(lnt);
		assertEquals("success", action.insert());
		assertEquals("LNT gravada com sucesso.", action.getActionSuccess().iterator().next());
		verify(gerenciadorComunicacaoManager, never()).enviaAvisoInicioLnt(Arrays.asList(lnt));
	}
	
	@Test
	public void testInsertComDataAtualIgualAoInicioDoPeriodoDaLNT()
	{
		Lnt lnt = LntFactory.getEntity(1L);
		lnt.setDataInicio(DateUtil.criarDataMesAno(new Date()));
		lnt.setDataFim(DateUtil.incrementaDias(new Date(), 2));
		action.setLnt(lnt);
		
		when(lntManager.save(eq(lnt))).thenReturn(lnt);
		assertEquals("success", action.insert());
		assertEquals("LNT gravada com sucesso.", action.getActionSuccess().iterator().next());
		
		verify(gerenciadorComunicacaoManager).enviaAvisoInicioLnt(Arrays.asList(lnt));
	}
	
	@Test
	public void testInsertComDataAtualCompreendidaDentroDoPeriodoDaLNT()
	{
		Lnt lnt = LntFactory.getEntity(1L);
		lnt.setDataInicio(DateUtil.incrementaDias(new Date(), -1));
		lnt.setDataFim(DateUtil.criarDataMesAno(new Date()));
		action.setLnt(lnt);
		when(lntManager.save(eq(lnt))).thenReturn(lnt);
		assertEquals("success", action.insert());
		assertEquals("LNT gravada com sucesso.", action.getActionSuccess().iterator().next());
		verify(gerenciadorComunicacaoManager).enviaAvisoInicioLnt(Arrays.asList(lnt));
	}
	
	@Test
	public void testInsertComPeriodoAnteriorADataAtual()
	{
		Lnt lnt = LntFactory.getEntity(1L);
		lnt.setDataInicio(DateUtil.incrementaDias(new Date(), -1));
		lnt.setDataFim(DateUtil.incrementaDias(new Date(), -1));
		action.setLnt(lnt);
		when(lntManager.save(eq(lnt))).thenReturn(lnt);
		assertEquals("success", action.insert());
		assertEquals("LNT gravada com sucesso.", action.getActionSuccess().iterator().next());
		verify(gerenciadorComunicacaoManager, never()).enviaAvisoInicioLnt(Arrays.asList(lnt));
	}
	
	@Test
	public void testInsertException()
	{
		doThrow(Exception.class).when(lntManager).save(action.getLnt());
		assertEquals("input", action.insert());
		assertEquals("Não foi possível gravar esta LNT.", action.getActionErrors().iterator().next());
		verify(gerenciadorComunicacaoManager, never()).enviaAvisoInicioLnt(Arrays.asList(action.getLnt()));
	}

	@Test
	public void testUpdateInicioDaLNTNaoEModificado()
	{
		Lnt lnt = LntFactory.getEntity(1L);
		lnt.setDataInicio(DateUtil.criarDataMesAno(new Date()));
		lnt.setDataFim(DateUtil.criarDataMesAno(new Date()));
		action.setLnt(lnt);
		
		when(lntManager.findById(lnt.getId())).thenReturn(lnt);
		
		assertEquals("success", action.update());
		assertEquals("LNT atualizada com sucesso.", action.getActionSuccess().iterator().next());
		verify(gerenciadorComunicacaoManager, never()).enviaAvisoInicioLnt(Arrays.asList(lnt));
	}
	
	@Test
	public void testUpdateLntComInicioFuturoAtualizadoParaDataAtual()
	{
		Lnt lntAntiga = LntFactory.getEntity(1L);
		lntAntiga.setDataInicio(DateUtil.incrementaDias(new Date(), 1));
		lntAntiga.setDataFim(DateUtil.incrementaDias(new Date(), 2));
		
		Lnt lntAtualizada = LntFactory.getEntity(1L);
		lntAtualizada.setDataInicio(DateUtil.criarDataMesAno(new Date()));
		lntAtualizada.setDataFim(DateUtil.criarDataMesAno(new Date()));
		action.setLnt(lntAtualizada);
		
		when(lntManager.findById(lntAtualizada.getId())).thenReturn(lntAntiga);
		when(lntManager.findEntidadeComAtributosSimplesById(lntAtualizada.getId())).thenReturn(lntAntiga);
		
		assertEquals("success", action.update());
		assertEquals("LNT atualizada com sucesso.", action.getActionSuccess().iterator().next());
		verify(gerenciadorComunicacaoManager).enviaAvisoInicioLnt(Arrays.asList(action.getLnt()));
	}
	
	@Test
	public void testUpdateInicioDaLntAtualizadoParaUmDataFutura()
	{
		Lnt lntAntiga = LntFactory.getEntity(1L);
		lntAntiga.setDataInicio(DateUtil.criarDataMesAno(new Date()));
		lntAntiga.setDataFim(DateUtil.criarDataMesAno(new Date()));
		action.setLnt(lntAntiga);
		
		Lnt lntAtualizada = LntFactory.getEntity(1L);
		lntAtualizada.setDataInicio(DateUtil.incrementaDias(new Date(), 1));
		lntAtualizada.setDataFim(DateUtil.incrementaDias(new Date(), 2));
		
		when(lntManager.findById(lntAtualizada.getId())).thenReturn(lntAntiga);
		
		assertEquals("success", action.update());
		assertEquals("LNT atualizada com sucesso.", action.getActionSuccess().iterator().next());
		verify(gerenciadorComunicacaoManager, never()).enviaAvisoInicioLnt(Arrays.asList(action.getLnt()));
	}

	@Test
	public void testUpdateLntComInicioFuturoAtualizadoParaUmaDataPassada()
	{
		Lnt lntAntiga = LntFactory.getEntity(1L);
		lntAntiga.setDataInicio(DateUtil.incrementaDias(new Date(), 1));
		lntAntiga.setDataFim(DateUtil.incrementaDias(new Date(), 2));

		Lnt lntAtualizada = LntFactory.getEntity(1L);
		lntAtualizada.setDataInicio(DateUtil.incrementaDias(new Date(), -1));
		lntAtualizada.setDataFim(DateUtil.incrementaDias(new Date(), 2));
		action.setLnt(lntAtualizada);
		
		when(lntManager.findById(lntAtualizada.getId())).thenReturn(lntAntiga);
		
		assertEquals("success", action.update());
		assertEquals("LNT atualizada com sucesso.", action.getActionSuccess().iterator().next());
		verify(gerenciadorComunicacaoManager).enviaAvisoInicioLnt(Arrays.asList(action.getLnt()));
	}
	
	@Test
	public void testUpdateLntComInicioIgualADataAtualEAtualizaParaUmaDataPassada()
	{
		Lnt lntAntiga = LntFactory.getEntity(1L);
		lntAntiga.setDataInicio(DateUtil.incrementaDias(new Date(), 1));
		lntAntiga.setDataFim(DateUtil.incrementaDias(new Date(), 2));

		Lnt lntAtualizada = LntFactory.getEntity(1L);
		lntAtualizada.setDataInicio(DateUtil.incrementaDias(new Date(), -1));
		lntAtualizada.setDataFim(DateUtil.incrementaDias(new Date(), 2));
		action.setLnt(lntAtualizada);
		
		when(lntManager.findById(lntAtualizada.getId())).thenReturn(lntAntiga);
		
		assertEquals("success", action.update());
		assertEquals("LNT atualizada com sucesso.", action.getActionSuccess().iterator().next());
		verify(gerenciadorComunicacaoManager).enviaAvisoInicioLnt(Arrays.asList(action.getLnt()));
	}
	
	@Test
	public void testUpdateLntPrazoVencido()
	{
		Lnt lntAntiga = LntFactory.getEntity(1L);
		lntAntiga.setDataInicio(DateUtil.criarDataMesAno(1, 1, 2016));
		lntAntiga.setDataFim(DateUtil.criarDataMesAno(10, 2, 2016));

		Lnt lntAtualizada = LntFactory.getEntity(1L);
		lntAtualizada.setDataInicio(DateUtil.incrementaDias(new Date(), -1));
		lntAtualizada.setDataFim(DateUtil.incrementaDias(new Date(), 2));
		action.setLnt(lntAtualizada);
		
		when(lntManager.findById(lntAtualizada.getId())).thenReturn(lntAntiga);
		
		assertEquals("success", action.update());
		assertEquals("LNT atualizada com sucesso.", action.getActionSuccess().iterator().next());
		verify(gerenciadorComunicacaoManager, never()).enviaAvisoInicioLnt(Arrays.asList(action.getLnt()));
	}
	
	@Test
	public void testUpdateLntFinalizada()
	{
		Lnt lntAntiga = LntFactory.getEntity(1L);
		lntAntiga.setDataInicio(DateUtil.criarDataMesAno(1, 1, 2016));
		lntAntiga.setDataFim(DateUtil.criarDataMesAno(10, 2, 2016));
		lntAntiga.setDataFinalizada(new Date());

		Lnt lntAtualizada = LntFactory.getEntity(1L);
		action.setLnt(lntAtualizada);
		
		when(lntManager.findById(lntAtualizada.getId())).thenReturn(lntAntiga);
		
		assertEquals("success", action.update());
		assertEquals("LNT atualizada com sucesso.", action.getActionSuccess().iterator().next());
		verify(gerenciadorComunicacaoManager, never()).enviaAvisoInicioLnt(Arrays.asList(action.getLnt()));
	}

	@Test
	public void testUpdateException() throws Exception
	{
		doThrow(Exception.class).when(lntManager).update(action.getLnt());
		assertEquals("input", action.update());
		assertEquals("Não foi possível atualizar esta LNT.", action.getActionErrors().iterator().next());
		verify(gerenciadorComunicacaoManager, never()).enviaAvisoInicioLnt(Arrays.asList(action.getLnt()));
	}
	
	@Test
	public void testDelete() throws Exception
	{
		Lnt lnt = LntFactory.getEntity(1L);
		action.setLnt(lnt);

		when(lntManager.getCount(new String[]{"empresa.id"}, new Long[]{action.getEmpresaSistema().getId()})).thenReturn(0);
		when(lntManager.find(0, 15, new String[]{"empresa.id"}, new Long[]{action.getEmpresaSistema().getId()}, new String[]{"inicio"})).thenReturn(new ArrayList<Lnt>());
		
		assertEquals("success", action.delete());
		assertEquals("LNT excluída com sucesso.", action.getActionSuccess().iterator().next());
		
	}
	
	@Test
	public void testDeleteException() throws Exception
	{
		Lnt lnt = LntFactory.getEntity(1L);
		action.setLnt(lnt);
		
		doThrow(Exception.class).when(lntManager).removeComDependencias(action.getLnt().getId(), colaboradorTurmaManager, cursoLntManager);
		
		when(lntManager.getCount(new String[]{"empresa.id"}, new Long[]{action.getEmpresaSistema().getId()})).thenReturn(0);
		when(lntManager.find(0, 15, new String[]{"empresa.id"}, new Long[]{action.getEmpresaSistema().getId()}, new String[]{"inicio"})).thenReturn(new ArrayList<Lnt>());
		
		assertEquals("success", action.delete());
		assertEquals("Não foi possivel remover Lnt e suas dependências.", action.getActionErrors().iterator().next());
	}
	
	@Test
	public void testFinalizar() throws Exception
	{
		assertEquals(Action.SUCCESS, action.finalizar());
		verify(lntManager).finalizar(null, action.getEmpresaSistema().getId());
	}
	
	
	@Test
	public void testReabrir() throws Exception
	{
		Lnt lnt = LntFactory.getEntity(1L);
		action.setLnt(lnt);
		assertEquals(Action.SUCCESS, action.reabrir());
		verify(lntManager).reabrir(lnt.getId());
	}
	
	@Test
	public void testGetSet() throws Exception
	{
		action.setLnt(null);

		assertNotNull(action.getLnt());
		assertTrue(action.getLnt() instanceof Lnt);
	}
	
	@Test
	public void testSaveParticipantes() throws Exception{
		populaPrepareParticipantes();
		
		ParticipanteCursoLnt participanteCursoLnt = ParticipanteCursoLntFactory.getEntity();
		participanteCursoLnt.setColaborador(ColaboradorFactory.getEntity(1L));
		
		CursoLnt cursoLnt = CursoLntFactory.getEntity(1L, "nome");
		cursoLnt.setParticipanteCursoLnts(Arrays.asList(participanteCursoLnt));
		
		action.setCursosLnt(Arrays.asList(cursoLnt));
		action.setParticipantesRemovidos(new String[]{"1"});
		action.setCursosRemovidos(new Long[]{1L});
		
		assertEquals(Action.SUCCESS, action.saveParticipantes());
	}
	
	@Test
	public void testPrepareParticipantes() throws Exception
	{
		populaPrepareParticipantes();
		
		assertEquals(Action.SUCCESS, action.prepareParticipantes());
	}

	private void populaPrepareParticipantes() {
		Lnt lnt = LntFactory.getEntity(1L);
		action.setLnt(lnt);

		Empresa empresaPermitida = EmpresaFactory.getEmpresa(1L);
		action.setUsuarioLogado(UsuarioFactory.getEntity(1L));
		
		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity();
		area1.setEmpresa(empresaPermitida);
		
		Collection<Empresa> empresasPermitidas = Arrays.asList(empresaPermitida);
		
		CursoLnt cursoLnt = CursoLntFactory.getEntity();
		cursoLnt.setNomeNovoCurso("nomeNovoCurso");
		
		action.setEmpresasCheck(new Long[]{empresaPermitida.getId()});
		
		when(empresaManager.findEmpresasPermitidas(true, empresaPermitida.getId(), action.getUsuarioLogado().getId())).thenReturn(Arrays.asList(empresaPermitida));
		when(areaOrganizacionalManager.findByLntIdComEmpresa(lnt.getId(), new CollectionUtil<Empresa>().convertCollectionToArrayIds(empresasPermitidas))).thenReturn(Arrays.asList(area1));		
		when(lntManager.findEntidadeComAtributosSimplesById(action.getLnt().getId())).thenReturn(action.getLnt());
		when(cursoLntManager.findByLntIdAndEmpresasIdsAndAreasParticipantesIds(lnt.getId(),new Long[]{area1.getId()},new Long[]{empresaPermitida.getId()})).thenReturn(Arrays.asList(cursoLnt));
		when(lntManager.findEntidadeComAtributosSimplesById(lnt.getId())).thenReturn(lnt);
		when(areaOrganizacionalManager.findByLntIdComEmpresa(lnt.getId(), new Long[]{empresaPermitida.getId()})).thenReturn(Arrays.asList(area1));
	}
	
	@Test
	public void testRelatorioParticipantesAgrupadoPorCursoLnt() throws Exception{
		action.setApruparRelatorioPor('C');
		
		Lnt lnt = LntFactory.getEntity(1L);
		action.setLnt(lnt);
		
		Empresa empresaPermitida = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresaPermitida);
		
		action.setUsuarioLogado(UsuarioFactory.getEntity(1L));
		
		Long[] areasIds = new Long[]{};
		action.setAreasIds(areasIds);
		String[] order = new String[]{"cu.nomeNovoCurso","areaNome","c.nome"};

		ParticipanteCursoLnt participanteCursoLnt = ParticipanteCursoLntFactory.getEntity(1L);
		
		when(participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lnt.getId(), areasIds, null, order)).thenReturn(Arrays.asList(participanteCursoLnt));
		when(lntManager.findEntidadeComAtributosSimplesById(lnt.getId())).thenReturn(lnt);
		
		assertEquals("agrupadoPorCursoLnt", action.relatorioParticipantes());
	}
	
	@Test
	public void testRelatorioParticipantesAgrupadoPorArea() throws Exception{
		action.setApruparRelatorioPor('A');
		
		Lnt lnt = LntFactory.getEntity(1L);
		action.setLnt(lnt);
		
		Empresa empresaPermitida = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresaPermitida);
		
		action.setUsuarioLogado(UsuarioFactory.getEntity(1L));
		
		Long[] areasIds = new Long[]{};
		action.setAreasIds(areasIds);
		String[] order = new String[]{"areaNome","cu.nomeNovoCurso","c.nome"};

		ParticipanteCursoLnt participanteCursoLnt = ParticipanteCursoLntFactory.getEntity(1L);
		
		when(participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lnt.getId(), areasIds, null, order)).thenReturn(Arrays.asList(participanteCursoLnt));
		when(lntManager.findEntidadeComAtributosSimplesById(lnt.getId())).thenReturn(lnt);
		
		assertEquals("agrupadoPorArea", action.relatorioParticipantes());
	}
	
	@Test
	public void testRelatorioParticipantesInput() throws Exception{
		Lnt lnt = LntFactory.getEntity(1L);
		action.setLnt(lnt);
		
		Empresa empresaPermitida = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresaPermitida);
		action.setUsuarioLogado(UsuarioFactory.getEntity(1L));
		
		Long[] areasIds = new Long[]{};
		action.setAreasIds(areasIds);
		String[] order = new String[]{"areaNome","cu.nomeNovoCurso","c.nome"};
		
		when(participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lnt.getId(), areasIds, null, order)).thenReturn(new ArrayList<ParticipanteCursoLnt>());
		populaPrepareParticipantes();
		
		assertEquals(Action.INPUT, action.relatorioParticipantes());
	}
	
	@Test
	public void testRelatorioParticipantesByUsuarioMsg() throws Exception{
		Lnt lnt = LntFactory.getEntity(1L);
		action.setLnt(lnt);
		
		Empresa empresaPermitida = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresaPermitida);
		action.setUsuarioLogado(UsuarioFactory.getEntity(1L));
		
		Long[] areasIds = new Long[]{1L};
		action.setAreasIds(areasIds);
		String[] order = new String[]{"areaNome","cu.nomeNovoCurso","c.nome"};

		ParticipanteCursoLnt participanteCursoLnt = ParticipanteCursoLntFactory.getEntity(1L);

		when(areaOrganizacionalManager.findIdsAreasDoResponsavelCoResponsavel(action.getUsuarioLogado().getId(), action.getEmpresaSistema().getId())).thenReturn(areasIds);
		when(areaOrganizacionalManager.getDescendentesIds(areasIds)).thenReturn(Arrays.asList(1L));
		when(participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lnt.getId(), areasIds, null, order)).thenReturn(Arrays.asList(participanteCursoLnt));
		when(lntManager.findEntidadeComAtributosSimplesById(lnt.getId())).thenReturn(lnt);
		
		assertEquals(Action.SUCCESS, action.relatorioParticipantesByUsuarioMsg());
	}
	
	@Test
	public void testRelatorioParticipantesByUsuarioMsgSemAreasResp() throws Exception{
		Lnt lnt = LntFactory.getEntity(1L);
		action.setLnt(lnt);
		
		Empresa empresaPermitida = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresaPermitida);
		action.setUsuarioLogado(UsuarioFactory.getEntity(1L));
		
		Long[] areasIds = new Long[]{};
		action.setAreasIds(areasIds);

		when(areaOrganizacionalManager.findIdsAreasDoResponsavelCoResponsavel(action.getUsuarioLogado().getId(), action.getEmpresaSistema().getId())).thenReturn(areasIds);
		when(areaOrganizacionalManager.getDescendentesIds(areasIds)).thenReturn(new ArrayList<Long>());
		
		assertEquals(Action.INPUT, action.relatorioParticipantesByUsuarioMsg());
		assertEquals("Não existem participantes para esta LNT ou sua área organizacional, cujo é responsável ou corresponsável, não consta na LNT.", action.getActionMsg());
	}
	
	@Test
	public void testRelatorioParticipantesByUsuarioMsgSemParticipantes() throws Exception{
		Lnt lnt = LntFactory.getEntity(1L);
		action.setLnt(lnt);
		
		Empresa empresaPermitida = EmpresaFactory.getEmpresa(1L);
		action.setEmpresaSistema(empresaPermitida);
		action.setUsuarioLogado(UsuarioFactory.getEntity(1L));
		
		Long[] areasIds = new Long[]{1L};
		action.setAreasIds(areasIds);
		String[] order = new String[]{"areaNome","cu.nomeNovoCurso","c.nome"};

		when(areaOrganizacionalManager.findIdsAreasDoResponsavelCoResponsavel(action.getUsuarioLogado().getId(), action.getEmpresaSistema().getId())).thenReturn(areasIds);
		when(areaOrganizacionalManager.getDescendentesIds(areasIds)).thenReturn(Arrays.asList(1L));
		when(participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lnt.getId(), areasIds, null, order)).thenReturn(new ArrayList<ParticipanteCursoLnt>());
		
		assertEquals(Action.INPUT, action.relatorioParticipantesByUsuarioMsg());
		assertEquals("Não existem participantes para esta LNT.", action.getActionMsg());
	}
	
	@Test
	public void testRelatorioParticipantesByUsuarioMsgException() throws Exception{
		populaPrepareParticipantes();
		assertEquals(Action.INPUT, action.relatorioParticipantesByUsuarioMsg());
	}
	
	@Test
	public void testRelatorioParticipantesException() throws Exception{
		populaPrepareParticipantes();
		assertEquals(Action.INPUT, action.relatorioParticipantes());
	}
}
