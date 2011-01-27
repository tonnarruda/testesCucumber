package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.ExameManagerImpl;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.dicionario.OrigemCandidato;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.ParametrosDoSistemaFactory;
import com.fortes.rh.test.factory.sesmt.ExameFactory;
import com.fortes.rh.util.SpringUtil;

public class ExameManagerTest extends MockObjectTestCase
{
	private ExameManagerImpl exameManager = new ExameManagerImpl();
	private Mock exameDao = null;
	private Mock parametrosDoSistemaManager;
	private Mock areaOrganizacionalManager;

	protected void setUp() throws Exception
    {
        super.setUp();
        exameDao = new Mock(ExameDao.class);
        exameManager.setDao((ExameDao) exameDao.proxy());
        
        parametrosDoSistemaManager= mock(ParametrosDoSistemaManager.class);
        exameManager.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
        
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        exameManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());
    }

	public void testFindByIdProjection() throws Exception
	{
		Exame exame = new Exame();
		exame.setId(1L);

		exameDao.expects(once()).method("findByIdProjection").with(eq(exame.getId())).will(returnValue(exame));

		assertEquals(exame, exameManager.findByIdProjection(exame.getId()));
	}

    public void testPopulaExames()
    {
    	Empresa empresa = new Empresa();
    	empresa.setId(1L);

    	String[] examesCheck = new String[]{"1"};

    	assertEquals(1, exameManager.populaExames(examesCheck).size());
    }

    public void testFindRelatorioExamesPrevistos() throws Exception
    {
    	Date hoje = new Date();
		Calendar doisMesesAtras = Calendar.getInstance();
    	doisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar tresMesesAtras = Calendar.getInstance();
    	tresMesesAtras.add(Calendar.MONTH, -3);
    	
    	Long[] areasCheck={1L}, estabelecimentosCheck={1L}, colaboradoresCheck={1L}, examesCheck = {1L};
    	Collection<ExamesPrevistosRelatorio> colecao = new ArrayList<ExamesPrevistosRelatorio>();

    	ExamesPrevistosRelatorio examesPrevistosColaborador1Exame1Fora = new ExamesPrevistosRelatorio(1L,1L,3L,"Cargo","","","",tresMesesAtras.getTime(),tresMesesAtras.getTime(),1,"PERIODICO");
    	ExamesPrevistosRelatorio examesPrevistosColaborador1Exame1Atual = new ExamesPrevistosRelatorio(1L,1L,3L,"Cargo","","","",doisMesesAtras.getTime(),doisMesesAtras.getTime(),1,"PERIODICO");
    	ExamesPrevistosRelatorio examesPrevistosColaborador2Exame2 = new ExamesPrevistosRelatorio(2L,2L,3L,"Cargo","","","",doisMesesAtras.getTime(),doisMesesAtras.getTime(),1,"PERIODICO");
//    	ExamesPrevistosRelatorio examesPrevistosColaborador2Exame2Fora = new ExamesPrevistosRelatorio(2L,2L,"","",hoje,hoje,1);
    	colecao.add(examesPrevistosColaborador1Exame1Fora);
    	colecao.add(examesPrevistosColaborador1Exame1Atual);
    	colecao.add(examesPrevistosColaborador2Exame2);
//    	colecao.add(examesPrevistosColaborador2Exame2Fora);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	exameDao.expects(once()).method("findExamesPeriodicosPrevistos").with(new Constraint[]{eq(empresa.getId()),eq(hoje),ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colecao));

    	Collection<ExamesPrevistosRelatorio> resultado = null;

    	Exception exception = null;
		try
		{
			resultado = exameManager.findRelatorioExamesPrevistos(empresa.getId(), hoje, examesCheck, estabelecimentosCheck, areasCheck, colaboradoresCheck, false, false);
		}
		catch (ColecaoVaziaException e)
		{
			exception = e;
		}

    	assertEquals(2, resultado.size());
    	assertNull(exception);
    }
    
    public void testFindRelatorioExamesPrevistosAgruparPorArea() throws Exception
    {
    	
    	boolean agruparPorArea=true;
    	boolean imprimirAfastados=true;
    	
    	Date hoje = new Date();
    	Calendar doisMesesAtras = Calendar.getInstance();
    	doisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar tresMesesAtras = Calendar.getInstance();
    	tresMesesAtras.add(Calendar.MONTH, -3);
    	
    	Long[] areasCheck={1L}, estabelecimentosCheck={1L}, colaboradoresCheck={1L}, examesCheck = {1L};
    	Collection<ExamesPrevistosRelatorio> colecao = new ArrayList<ExamesPrevistosRelatorio>();
    	Collection<ExamesPrevistosRelatorio> colecaoFiltrada = new ArrayList<ExamesPrevistosRelatorio>();
    	
    	ExamesPrevistosRelatorio examesPrevistosColaborador1Exame1Fora = new ExamesPrevistosRelatorio(1L,1L,2L,"Cargo","","","",tresMesesAtras.getTime(),tresMesesAtras.getTime(),1, "PERIODICO");
    	ExamesPrevistosRelatorio examesPrevistosColaborador1Exame1Atual = new ExamesPrevistosRelatorio(1L,1L,1L,"Cargo","","","",doisMesesAtras.getTime(),doisMesesAtras.getTime(),1, "PERIODICO");
    	ExamesPrevistosRelatorio examesPrevistosColaborador2Exame2 = new ExamesPrevistosRelatorio(2L,2L,2L,"Cargo","","","",doisMesesAtras.getTime(),doisMesesAtras.getTime(),1, "PERIODICO");
//    	ExamesPrevistosRelatorio examesPrevistosColaborador2Exame2Fora = new ExamesPrevistosRelatorio(2L,2L,"","",hoje,hoje,1);
    	colecao.add(examesPrevistosColaborador1Exame1Fora);
    	colecao.add(examesPrevistosColaborador1Exame1Atual);
    	colecao.add(examesPrevistosColaborador2Exame2);
//    	colecao.add(examesPrevistosColaborador2Exame2Fora);
    	
    	//setando nome das áreas para simular a ordenação
    	examesPrevistosColaborador1Exame1Atual.getAreaOrganizacional().setNome("Cabaré");
    	examesPrevistosColaborador1Exame1Fora.getAreaOrganizacional().setNome("Administração");
    	examesPrevistosColaborador2Exame2.getAreaOrganizacional().setNome("Administração");
    	
    	// coleção após filtrar os resultados
    	colecaoFiltrada.add(examesPrevistosColaborador1Exame1Atual);
    	colecaoFiltrada.add(examesPrevistosColaborador2Exame2);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	exameDao.expects(once()).method("findExamesPeriodicosPrevistos").with(new Constraint[]{eq(empresa.getId()),eq(hoje),ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colecao));
    	
    	// Cabaré > Administração
    	Collection<AreaOrganizacional> areas = new ArrayList<AreaOrganizacional>();
    	areas.add(AreaOrganizacionalFactory.getEntity(1L));
    	AreaOrganizacional area2 = AreaOrganizacionalFactory.getEntity(2L);
    	area2.setAreaMaeId(1L);
    	areas.add(area2);
    	
		areaOrganizacionalManager.expects(once()).method("setFamiliaAreas").with(ANYTHING, ANYTHING).will(returnValue(colecaoFiltrada));
    	Collection<ExamesPrevistosRelatorio> resultado = null;
    	
    	Exception exception = null;
    	try
    	{
    		resultado = exameManager.findRelatorioExamesPrevistos(empresa.getId(), hoje, examesCheck, estabelecimentosCheck, areasCheck, colaboradoresCheck, agruparPorArea, imprimirAfastados );
    	}
    	catch (ColecaoVaziaException e)
    	{
    		exception = e;
    	}
    	
    	assertEquals(2, resultado.size());
    	assertNull(exception);
    }

    public void testFindRelatorioExamesPrevistosColecaoVaziaException() throws Exception
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	exameDao.expects(once()).method("findExamesPeriodicosPrevistos").with(new Constraint[]{eq(empresa.getId()),ANYTHING,ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(new ArrayList<ExamesRealizadosRelatorio>()));

    	Exception exception = null;

    	try
		{
			exameManager.findRelatorioExamesPrevistos(empresa.getId(), null, null, null, null, null,false, false);
		}
		catch (ColecaoVaziaException e)
		{
			exception = e;
		}

		assertNotNull(exception);
    }

    public void testFindBySolicitacaoExame()
    {
    	Collection<Long> exameIds = new ArrayList<Long>();
    	exameIds.add(1L);
    	exameDao.expects(once()).method("findBySolicitacaoExame").will(returnValue(exameIds));

    	assertNotNull(exameManager.findBySolicitacaoExame(1L));
    }

    public void testFindByHistoricoFuncao()
	{
    	exameDao.expects(once()).method("findByHistoricoFuncao").will(returnValue(new ArrayList<Exame>()));
    	assertEquals(0, exameManager.findByHistoricoFuncao(1L).size());
	}

    public void testFindAllSelect()
	{
    	Long empresaId=3L;
    	exameDao.expects(once()).method("findToList").will(returnValue(new ArrayList<Exame>()));
		assertEquals(0, exameManager.findAllSelect(empresaId).size());
	}

    public void testPopulaCheckBox()
    {
    	Long empresaId=3L;
    	exameDao.expects(once()).method("findToList").will(returnValue(new ArrayList<Exame>()));
    	assertNotNull(exameManager.populaCheckBox(empresaId));
    }
    public void testPopulaCheckBoxException()
    {
    	Long empresaId=3L;
    	exameDao.expects(once()).method("findToList").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
    	assertNotNull(exameManager.populaCheckBox(empresaId));
    }
    
    public void testFindRelatorioExamesRealizados() throws ColecaoVaziaException
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Collection<ExamesRealizadosRelatorio> colecao = new ArrayList<ExamesRealizadosRelatorio>();
    	ExamesRealizadosRelatorio examesPrevistosRelatorio = new ExamesRealizadosRelatorio();
    	colecao.add(examesPrevistosRelatorio);
    	
    	exameDao.expects(once()).method("findExamesRealizados").will(returnValue(colecao));
    	
    	assertEquals(1,exameManager.findRelatorioExamesRealizados(empresa.getId(), null, new Date(), new Date(), null, null, null, null, null, null).size());
    }
    public void testFindRelatorioExamesRealizadosColecaoVaziaException()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	exameDao.expects(once()).method("findExamesRealizados").will(returnValue(new ArrayList<ExamesRealizadosRelatorio>()));

    	Exception exception = null;

    	try
		{
			exameManager.findRelatorioExamesRealizados(empresa.getId(), null, null, null, null, null, null,null,null, null);
		}
		catch (ColecaoVaziaException e)
		{
			exception = e;
		}

		assertNotNull(exception);
    }
    
    public void testGetExameAso()
    {
    	ParametrosDoSistema parametrosDoSistema= ParametrosDoSistemaFactory.getEntity(1L);
    	parametrosDoSistema.setExame(ExameFactory.getEntity(33L));
		
    	parametrosDoSistemaManager.expects(once()).method("findById").with(eq(1L)).will(returnValue(parametrosDoSistema));
		
		assertEquals(33L, exameManager.getExameAso().getId().longValue());
    }
    
    public void testEnviaLembreteExamesPrevistos() throws Exception
    {
    	ExameManager exameManager = (ExameManager) SpringUtil.getBeanOld("exameManager");
    	EmpresaManager empresaManager = (EmpresaManager) SpringUtil.getBeanOld("empresaManager");
		try
		{
			exameManager.enviaLembreteExamesPrevistos(empresaManager.findEmailsEmpresa());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
    }
    
}