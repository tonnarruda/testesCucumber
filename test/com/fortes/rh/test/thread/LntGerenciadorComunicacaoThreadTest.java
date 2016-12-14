package com.fortes.rh.test.thread;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.fortes.rh.business.desenvolvimento.ParticipanteCursoLntManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.model.desenvolvimento.CursoLnt;
import com.fortes.rh.model.desenvolvimento.Lnt;
import com.fortes.rh.model.desenvolvimento.ParticipanteCursoLnt;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoLntFactory;
import com.fortes.rh.test.factory.desenvolvimento.LntFactory;
import com.fortes.rh.test.factory.desenvolvimento.ParticipanteCursoLntFactory;
import com.fortes.rh.thread.LntGerenciadorComunicacaoThread;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;

public class LntGerenciadorComunicacaoThreadTest 
{
	private Lnt lnt;
	private ParticipanteCursoLntManager participanteCursoLntManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;
	private Map<String, Object> parametros;
	private LntGerenciadorComunicacaoThread lntGerenciadorComunicacaoThread;
	
	@Before
	public void setUp() throws Exception
    {
		lnt = LntFactory.getEntity(null, "Desfcrição LNT", DateUtil.criarDataMesAno(1, 11, 2016), DateUtil.criarDataMesAno(1, 12, 2016), null);
		lnt.setId(1L);
		participanteCursoLntManager = mock(ParticipanteCursoLntManager.class);
		areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
		gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
		parametros = new HashMap<String, Object>();

		lntGerenciadorComunicacaoThread = new LntGerenciadorComunicacaoThread(lnt, participanteCursoLntManager, areaOrganizacionalManager,gerenciadorComunicacaoManager, parametros);
    }
	
	@Test
	public void testRun(){
		Empresa empresa = EmpresaFactory.getEmpresa(1L);

		Colaborador colaborador = ColaboradorFactory.getEntity(1L);
		
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1l, "Nome Area", true, EmpresaFactory.getEmpresa(1L));
		area.setResponsavel(colaborador);
		
		Collection<Long> areasIds = Arrays.asList(area.getId());
		Collection<AreaOrganizacional> areas = Arrays.asList(area);
		
		CursoLnt cursoLnt = CursoLntFactory.getEntity(null, lnt);
		
		ParticipanteCursoLnt participanteCursoLnt = ParticipanteCursoLntFactory.getEntity(colaborador, area, cursoLnt);
		participanteCursoLnt.setEmpresaId(empresa.getId());
		Collection<ParticipanteCursoLnt> participanteCursoLnts = Arrays.asList(participanteCursoLnt);
		
		Map<Long, AreaOrganizacional> mapAreasIds = new HashMap<Long, AreaOrganizacional>();
		mapAreasIds.put(area.getId(), area);
		
		when(participanteCursoLntManager.findByLntIdAndAreasParticipantesIdsAndEmpresasIds(lnt.getId(), null, null, new String[]{"e.nome", "areaNome","c.nome","cu.nomeNovoCurso"})).thenReturn(participanteCursoLnts);
		when(areaOrganizacionalManager.findAllMapAreasIds(empresa.getId())).thenReturn(mapAreasIds);
		when(areaOrganizacionalManager.findByLntId(lnt.getId(), new Long[]{})).thenReturn(areas);
		when(areaOrganizacionalManager.getAncestraisIds(new CollectionUtil<AreaOrganizacional>().convertCollectionToArrayIds(areas))).thenReturn(areasIds);
		
		lntGerenciadorComunicacaoThread.run();
	}
	
	@Test
	public void testMontaMapParticipantesLnt()
	{
		Colaborador colaboradorParticipante1 = ColaboradorFactory.getEntity(10L);
		Colaborador colaboradorParticipante2 = ColaboradorFactory.getEntity(11L);
		
		Colaborador responsavel1 = ColaboradorFactory.getEntity(1L);
		Colaborador responsavel2 = ColaboradorFactory.getEntity(2L);
		Colaborador coResponsavel1 = ColaboradorFactory.getEntity(3L);
		
		AreaOrganizacional areaMae1 = AreaOrganizacionalFactory.getEntity(1l, "Nome Area Mae", true, null);
		areaMae1.setResponsavel(responsavel2);
		
		AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity(2l, "Nome Area 2", true, null);
		area2.setResponsavel(responsavel1);
		area2.setCoResponsavel(coResponsavel1);
		
		AreaOrganizacional area1 = AreaOrganizacionalFactory.getEntity(3l, "Nome Area 1", true, null);
		area1.setAreaMae(areaMae1);
		area1.setResponsavel(responsavel1);
		
		CursoLnt cursoLnt = CursoLntFactory.getEntity(null, lnt);
		ParticipanteCursoLnt participanteCursoLnt1 = ParticipanteCursoLntFactory.getEntity(colaboradorParticipante1, area1, cursoLnt);
		ParticipanteCursoLnt participanteCursoLnt2 = ParticipanteCursoLntFactory.getEntity(colaboradorParticipante1, area2, cursoLnt);
		ParticipanteCursoLnt participanteCursoLnt3 = ParticipanteCursoLntFactory.getEntity(colaboradorParticipante2, area1, cursoLnt);
		
		Collection<ParticipanteCursoLnt> participanteCursoLnts = Arrays.asList(participanteCursoLnt1, participanteCursoLnt2, participanteCursoLnt3);
		
		Map<Long, AreaOrganizacional> mapAreasIds = new HashMap<Long, AreaOrganizacional>();
		mapAreasIds.put(area1.getId(), area1);
		mapAreasIds.put(area2.getId(), area2);
		mapAreasIds.put(areaMae1.getId(), areaMae1);
		
		Map<Long, Collection<ParticipanteCursoLnt>> mapPerticipantesLNTPorResponsaveis = new HashMap<Long, Collection<ParticipanteCursoLnt>>();
		lntGerenciadorComunicacaoThread.montaMapParticipantesLnt(mapPerticipantesLNTPorResponsaveis, participanteCursoLnts, mapAreasIds);
		
		assertEquals(3, mapPerticipantesLNTPorResponsaveis.size());
		assertEquals(3, mapPerticipantesLNTPorResponsaveis.get(responsavel1.getId()).size());
		assertEquals(2, mapPerticipantesLNTPorResponsaveis.get(responsavel2.getId()).size());
		assertEquals(1, mapPerticipantesLNTPorResponsaveis.get(coResponsavel1.getId()).size());
	}
}