package com.fortes.rh.test.web.action.captacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.captacao.CompetenciaManager;
import com.fortes.rh.business.captacao.ConfiguracaoNivelCompetenciaManager;
import com.fortes.rh.business.captacao.CriterioAvaliacaoCompetenciaManager;
import com.fortes.rh.business.captacao.HabilidadeManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.captacao.Habilidade;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.HabilidadeFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.web.action.captacao.HabilidadeEditAction;
import com.fortes.web.tags.CheckBox;

public class HabilidadeEditActionTest extends MockObjectTestCase
{
	private HabilidadeEditAction action;
	private Mock configuracaoNivelCompetenciaManager;
	private Mock areaOrganizacionalManager;
	private Mock habilidadeManager;
	private Mock competenciaManager;
	private Mock cursoManager;
	private Mock criterioAvaliacaoCompetenciaManager;

	protected void setUp() throws Exception
	{
		super.setUp();
		habilidadeManager = new Mock(HabilidadeManager.class);
		action = new HabilidadeEditAction();
		areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
		cursoManager = new Mock(CursoManager.class);
		competenciaManager = new Mock(CompetenciaManager.class);
		criterioAvaliacaoCompetenciaManager = new Mock(CriterioAvaliacaoCompetenciaManager.class);
		configuracaoNivelCompetenciaManager = new Mock(ConfiguracaoNivelCompetenciaManager.class);
		
		action.setHabilidade(new Habilidade());
		action.setHabilidadeManager((HabilidadeManager) habilidadeManager.proxy());
		action.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
		action.setCursoManager((CursoManager) cursoManager.proxy());
		action.setCompetenciaManager((CompetenciaManager) competenciaManager.proxy());
		action.setConfiguracaoNivelCompetenciaManager((ConfiguracaoNivelCompetenciaManager) configuracaoNivelCompetenciaManager.proxy());
		action.setCriterioAvaliacaoCompetenciaManager((CriterioAvaliacaoCompetenciaManager) criterioAvaliacaoCompetenciaManager.proxy());
		
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
	}

	protected void tearDown() throws Exception
	{
		habilidadeManager = null;
		action = null;
		super.tearDown();
	}

	public void testPrepareInsert() throws Exception
	{
		Habilidade habilidade = new Habilidade();
		habilidade.setId(1L);
		action.setHabilidade(habilidade);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();		
		Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();		
		
		habilidadeManager.expects(once()).method("findByIdProjection").will(returnValue(habilidade));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(1L)).will(returnValue(areasCheckList));
		cursoManager.expects(once()).method("populaCheckOrderDescricao").with(eq(1L)).will(returnValue(cursosCheckList));
		
		assertEquals("success", action.prepareInsert());
	}

	public void testPrepareUpdate() throws Exception
	{
		Habilidade habilidade = new Habilidade();
		habilidade.setId(1L);
		action.setHabilidade(habilidade);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();		
		Collection<CheckBox> cursosCheckList = new ArrayList<CheckBox>();		
		
		habilidadeManager.expects(once()).method("findByIdProjection").will(returnValue(habilidade));
		areaOrganizacionalManager.expects(once()).method("populaCheckOrderDescricao").with(eq(1L)).will(returnValue(areasCheckList));
		cursoManager.expects(once()).method("populaCheckOrderDescricao").with(eq(1L)).will(returnValue(cursosCheckList));
		
		assertEquals("success", action.prepareUpdate());
	}

	public void testList() throws Exception
	{
		Habilidade Habilidade = new Habilidade();
		Habilidade.setId(1L);
		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
		
		habilidadeManager.expects(once()).method("getCount").will(returnValue(new Integer (1)));
		habilidadeManager.expects(once()).method("findToList").will(returnValue(new ArrayList<Habilidade>()));
		
		assertEquals("success", action.list());
		assertNotNull(action.getHabilidades());
	}

	public void testDelete() throws Exception
	{
		Habilidade habilidade = HabilidadeFactory.getEntity(1L);
		action.setHabilidade(habilidade);

		configuracaoNivelCompetenciaManager.expects(once()).method("existeConfiguracaoNivelCompetencia").with(eq(habilidade.getId()), eq(TipoCompetencia.HABILIDADE)).will(returnValue(false));
		criterioAvaliacaoCompetenciaManager.expects(once()).method("removeByCompetencia");
		habilidadeManager.expects(once()).method("remove");
		assertEquals("success", action.delete());
	}
	
	public void testDeleteComDependenciaConfiguracaoNivelCompetencia() throws Exception
	{
		Habilidade habilidade = HabilidadeFactory.getEntity(1L);
		action.setHabilidade(habilidade);

		configuracaoNivelCompetenciaManager.expects(once()).method("existeConfiguracaoNivelCompetencia").with(eq(habilidade.getId()), eq(TipoCompetencia.HABILIDADE)).will(returnValue(true));
		assertEquals("input", action.delete());
	}
	
	public void testDeleteException() throws Exception
	{
		Habilidade habilidade = HabilidadeFactory.getEntity(1L);
		action.setHabilidade(habilidade);
		
		configuracaoNivelCompetenciaManager.expects(once()).method("existeConfiguracaoNivelCompetencia").with(eq(habilidade.getId()), eq(TipoCompetencia.HABILIDADE)).will(returnValue(false));
		criterioAvaliacaoCompetenciaManager.expects(once()).method("removeByCompetencia");
		habilidadeManager.expects(once()).method("remove").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
		
		try {
			action.delete();
		} catch (Exception e) {
			fail("Não deveria ter lançado uma exceçao");
		}
	}

	public void testInsert() throws Exception
	{
		Habilidade habilidade = HabilidadeFactory.getEntity();
		action.setHabilidade(habilidade);

		Collection<AreaOrganizacional> areas = collectionAreasOrganizacionais();
		Collection<Curso> cursos = Arrays.asList(CursoFactory.getEntity(1L));
		
		competenciaManager.expects(once()).method("existeNome").will(returnValue(false));
		areaOrganizacionalManager.expects(once()).method("populaAreas").with(ANYTHING).will(returnValue(areas));
		cursoManager.expects(once()).method("populaCursos").will(returnValue(cursos));
		habilidadeManager.expects(once()).method("save").with(eq(habilidade)).will(returnValue(habilidade));
		
		assertEquals("success", action.insert());
	}

	public void testInsertException() throws Exception
	{
		Exception exception = null;
		
    	try
		{
    		Habilidade habilidade = HabilidadeFactory.getEntity();
    		habilidade.setNome("habilidade");

    		action.setHabilidade(habilidade);
    		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    		
    		competenciaManager.expects(once()).method("existeNome").will(returnValue(false));
    		
    		Collection<AreaOrganizacional> areas = collectionAreasOrganizacionais();
    		Collection<Curso> cursos = Arrays.asList(CursoFactory.getEntity(1L));
    		
    		areaOrganizacionalManager.expects(once()).method("populaAreas").with(ANYTHING).will(returnValue(areas));
    		cursoManager.expects(once()).method("populaCursos").will(returnValue(cursos));
    		habilidadeManager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
    		action.insert();
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
	}

	public void testUpdate() throws Exception
	{
		Habilidade habilidade = HabilidadeFactory.getEntity(1L);
		action.setHabilidade(habilidade);

		Collection<AreaOrganizacional> areas = collectionAreasOrganizacionais();
		Collection<Curso> cursos = Arrays.asList(CursoFactory.getEntity(1L));
		
		competenciaManager.expects(once()).method("existeNome").will(returnValue(false));
		areaOrganizacionalManager.expects(once()).method("populaAreas").with(ANYTHING).will(returnValue(areas));
		cursoManager.expects(once()).method("populaCursos").will(returnValue(cursos));
		criterioAvaliacaoCompetenciaManager.expects(once()).method("removeByCompetencia");
		habilidadeManager.expects(once()).method("update").with(eq(habilidade)).isVoid();

		assertEquals("success", action.update());
	}

	public void testUpdateException() throws Exception
	{
		Exception exception = null;
		
    	try
		{
    		Habilidade habilidade = HabilidadeFactory.getEntity(1L);
    		habilidade.setNome("habilidade");

    		action.setHabilidade(habilidade);
    		action.setEmpresaSistema(EmpresaFactory.getEmpresa(1L));
    		
    		competenciaManager.expects(once()).method("existeNome").will(returnValue(false));
    		
    		Collection<AreaOrganizacional> areas = collectionAreasOrganizacionais();
    		Collection<Curso> cursos = Arrays.asList(CursoFactory.getEntity(1L));
    		
    		areaOrganizacionalManager.expects(once()).method("populaAreas").with(ANYTHING).will(returnValue(areas));
    		cursoManager.expects(once()).method("populaCursos").will(returnValue(cursos));
    		criterioAvaliacaoCompetenciaManager.expects(once()).method("removeByCompetencia");
    		habilidadeManager.expects(once()).method("update").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
    		action.update();
		}
		catch (Exception e)
		{
			exception = e;
		}

		assertNotNull(exception);
		
	}

	public void testGetSet() throws Exception
	{
		action.setHabilidade(null);

		assertNotNull(action.getHabilidade());
		assertTrue(action.getHabilidade() instanceof Habilidade);
	}
	
	private Collection<AreaOrganizacional> collectionAreasOrganizacionais() {
		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1L);
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areas.add(area);
		return areas;
	}
}
