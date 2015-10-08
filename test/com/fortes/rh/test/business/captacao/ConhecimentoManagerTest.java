package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.ConhecimentoManagerImpl;
import com.fortes.rh.business.captacao.CriterioAvaliacaoCompetenciaManager;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.dao.captacao.ConhecimentoDao;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.TipoCompetencia;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ConhecimentoFactory;
import com.fortes.rh.test.util.mockObjects.MockSpringUtil;
import com.fortes.rh.util.SpringUtil;
import com.fortes.web.tags.CheckBox;

public class ConhecimentoManagerTest extends MockObjectTestCase
{
	ConhecimentoManagerImpl conhecimentoManager;
	Mock conhecimentoDao;
	Mock areaOrganizacionalManager;
	private Mock cursoManager;
	private Mock criterioAvaliacaoCompetenciaManager;

    protected void setUp() throws Exception
    {
        conhecimentoManager = new ConhecimentoManagerImpl();

        conhecimentoDao = new Mock(ConhecimentoDao.class);
        conhecimentoManager.setDao((ConhecimentoDao) conhecimentoDao.proxy());
        
        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        conhecimentoManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
        
        criterioAvaliacaoCompetenciaManager = new Mock(CriterioAvaliacaoCompetenciaManager.class);
        conhecimentoManager.setCriterioAvaliacaoCompetenciaManager((CriterioAvaliacaoCompetenciaManager) criterioAvaliacaoCompetenciaManager.proxy());
        
        cursoManager = new Mock(CursoManager.class);
        Mockit.redefineMethods(SpringUtil.class, MockSpringUtil.class);
    }

    public void testFindByAreasOrganizacionalIds()
    {
    	Long empresaId = 1L;
		Long[] areasOrganizacionais = new Long[]{1L,2L};

		Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();

		conhecimentoDao.expects(once()).method("findByAreaOrganizacionalIds").with(eq(areasOrganizacionais), eq(empresaId)).will(returnValue(conhecimentos));

		assertEquals(conhecimentos, conhecimentoManager.findByAreasOrganizacionalIds(areasOrganizacionais, empresaId));
    }

    public void testFindByAreaInteresse(){
    	Long empresaId = 1L;
    	Long[] longs = new Long[]{1L,2L};

    	Collection<Conhecimento> conhecimentos = new ArrayList<Conhecimento>();

    	conhecimentoDao.expects(once()).method("findByAreaInteresse").with(eq(longs), eq(empresaId)).will(returnValue(conhecimentos));

		assertEquals(conhecimentos, conhecimentoManager.findByAreaInteresse(longs, empresaId));
    }

    public void testFindAllSelect()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	conhecimentoDao.expects(once()).method("findAllSelect").with(eq(new Long[]{empresa.getId()})).will(returnValue(ConhecimentoFactory.getCollection()));

    	assertEquals(1, conhecimentoManager.findAllSelect(empresa.getId()).size());
    }

    public void testFindByCargo()
    {
    	Cargo cargo = new Cargo();
    	cargo.setId(1L);

    	conhecimentoDao.expects(once()).method("findByCargo").with(eq(cargo.getId())).will(returnValue(new ArrayList<Conhecimento>()));

    	assertNotNull(conhecimentoManager.findByCargo(cargo.getId()));
    }

    public void testPopulaCheckOrderNome()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	conhecimentoDao.expects(once()).method("findAllSelect").with(eq(new Long[]{empresa.getId()})).will(returnValue(ConhecimentoFactory.getCollection()));

    	assertEquals(1, conhecimentoManager.populaCheckOrderNome(empresa.getId()).size());
    }

    public void testPopulaCheckOrderNomeException()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	conhecimentoManager.setDao(null);

    	Collection<CheckBox> checks = conhecimentoManager.populaCheckOrderNome(empresa.getId());
		assertTrue(checks.isEmpty());
    }

    public void testPopulaCheckOrderNomeByAreaOrganizacionals()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	Long[] areasIds = new Long[]{1L};

    	conhecimentoDao.expects(once()).method("findByAreaOrganizacionalIds").with(eq(areasIds), eq(empresa.getId())).will(returnValue(ConhecimentoFactory.getCollection()));

    	assertEquals(1, conhecimentoManager.populaCheckOrderNomeByAreaOrganizacionals(areasIds, empresa.getId()).size());
    }

    public void testPopulaCheckOrderNomeByAreaOrganizacionalsException()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	Long[] areasIds = new Long[]{1L};

    	conhecimentoManager.setDao(null);

    	Collection<CheckBox> checks = conhecimentoManager.populaCheckOrderNomeByAreaOrganizacionals(areasIds, empresa.getId());
    	assertTrue(checks.isEmpty());
    }
	
	public void testFindByIdProjection()
	{
		MockSpringUtil.mocks.put("cursoManager", cursoManager);
		
		Conhecimento conhecimento = ConhecimentoFactory.getConhecimento();
		conhecimento.setId(1L);
		
		conhecimentoDao.expects(once()).method("findByIdProjection").with(eq(conhecimento.getId())).will(returnValue(conhecimento));

		AreaOrganizacional area = AreaOrganizacionalFactory.getEntity(1L);
		Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
		areas.add(area);
		
		areaOrganizacionalManager.expects(once()).method("findByConhecimento").with(eq(conhecimento.getId())).will(returnValue(areas));
		cursoManager.expects(once()).method("findByCompetencia").with(eq(conhecimento.getId()), eq(TipoCompetencia.CONHECIMENTO)).will(returnValue(areas));
		criterioAvaliacaoCompetenciaManager.expects(once()).method("findByCompetencia").with(eq(conhecimento.getId()), eq(TipoCompetencia.CONHECIMENTO));
		
		assertEquals(conhecimento, conhecimentoManager.findByIdProjection(conhecimento.getId()));
	}
	
	public void testFindAllSelectDistinctNome()
	{
		conhecimentoDao.expects(once()).method("findAllSelectDistinctNome").will(returnValue(new ArrayList<Conhecimento>()));
		assertTrue(conhecimentoManager.findAllSelectDistinctNome(new Long[]{}).isEmpty());
	}
	
	public void testFindByCandidato()
	{
		conhecimentoDao.expects(once()).method("findByCandidato").will(returnValue(new ArrayList<Conhecimento>()));
		assertTrue(conhecimentoManager.findByCandidato(1000L).isEmpty());
	}
    
    public void testPopulaConhecimentos()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	String[] conhecimentosCheck = new String[]{"1"};

    	assertEquals(1, conhecimentoManager.populaConhecimentos(conhecimentosCheck).size());
    }
    
    public void testSincronizar()
    {
    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
    	
    	AreaOrganizacional areaOrganizacional1 = AreaOrganizacionalFactory.getEntity(1L);
		areaOrganizacional1.setNome("área1");
		areas.add(areaOrganizacional1);
		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity(2L);
		areaOrganizacional2.setNome("área2");
		areas.add(areaOrganizacional2);
    	
    	Collection<Conhecimento> conhecimentosOrigem = new ArrayList<Conhecimento>();
    	Conhecimento conhecimento1 = ConhecimentoFactory.getConhecimento(1L);
    	Conhecimento conhecimento2 = ConhecimentoFactory.getConhecimento(2L);
    	Conhecimento conhecimento3 = ConhecimentoFactory.getConhecimento(3L);
    	conhecimentosOrigem.add(conhecimento1);
    	conhecimentosOrigem.add(conhecimento2);
    	conhecimentosOrigem.add(conhecimento3);
    	
    	conhecimentoDao.expects(once()).method("findSincronizarConhecimentos").will(returnValue(conhecimentosOrigem));
    	conhecimentoDao.expects(atLeastOnce()).method("save");
    	areaOrganizacionalManager.expects(atLeastOnce()).method("findByConhecimento").will(returnValue(areas));
    	conhecimentoDao.expects(atLeastOnce()).method("update");
    	
    	Map<Long, Long> areaIds = new  HashMap<Long, Long>();
    	areaIds.put(1L, 5L);
		
		Long empresaOrigemId=1L;
		Long empresaDestinoId=2L;
    	Map<Long, Long> conhecimentoIds = new  HashMap<Long, Long>();
    	
		conhecimentoManager.sincronizar(empresaOrigemId, empresaDestinoId, areaIds, conhecimentoIds);
		
		assertEquals(3, conhecimentoIds.size());
    }
}
