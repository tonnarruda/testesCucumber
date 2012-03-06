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
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.ExameManagerImpl;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.relatorio.ExamesPrevistosRelatorio;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.SpringUtil;

public class ExameManagerTest extends MockObjectTestCase
{
	private ExameManagerImpl exameManager = new ExameManagerImpl();
	private Mock exameDao = null;
	private Mock parametrosDoSistemaManager;
	private Mock areaOrganizacionalManager;
	private Mock colaboradorManager;
	private Mock gerenciadorComunicacaoManager;

	protected void setUp() throws Exception
    {
        super.setUp();
        exameDao = new Mock(ExameDao.class);
        exameManager.setDao((ExameDao) exameDao.proxy());
        
        parametrosDoSistemaManager= mock(ParametrosDoSistemaManager.class);
        exameManager.setParametrosDoSistemaManager((ParametrosDoSistemaManager) parametrosDoSistemaManager.proxy());
        
        areaOrganizacionalManager = mock(AreaOrganizacionalManager.class);
        exameManager.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

        colaboradorManager = mock(ColaboradorManager.class);
        exameManager.setColaboradorManager((ColaboradorManager) colaboradorManager.proxy());
        
        gerenciadorComunicacaoManager = mock(GerenciadorComunicacaoManager.class);
        exameManager.setGerenciadorComunicacaoManager((GerenciadorComunicacaoManager) gerenciadorComunicacaoManager.proxy());
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

    	ExamesPrevistosRelatorio examesPrevistosColaborador1Exame1Fora = new ExamesPrevistosRelatorio(1L,1L,3L,"Cargo","","","",tresMesesAtras.getTime(),tresMesesAtras.getTime(),1,"PERIODICO",1L,"EST");
    	ExamesPrevistosRelatorio examesPrevistosColaborador1Exame1Atual = new ExamesPrevistosRelatorio(1L,1L,3L,"Cargo","","","",doisMesesAtras.getTime(),doisMesesAtras.getTime(),1,"PERIODICO",1L,"EST");
    	ExamesPrevistosRelatorio examesPrevistosColaborador2Exame2 = new ExamesPrevistosRelatorio(2L,2L,3L,"Cargo","","","",doisMesesAtras.getTime(),doisMesesAtras.getTime(),1,"PERIODICO",1L,"EST");
//    	ExamesPrevistosRelatorio examesPrevistosColaborador2Exame2Fora = new ExamesPrevistosRelatorio(2L,2L,"","",hoje,hoje,1);
    	colecao.add(examesPrevistosColaborador1Exame1Fora);
    	colecao.add(examesPrevistosColaborador1Exame1Atual);
    	colecao.add(examesPrevistosColaborador2Exame2);
//    	colecao.add(examesPrevistosColaborador2Exame2Fora);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	exameDao.expects(once()).method("findExamesPeriodicosPrevistos").with(new Constraint[]{eq(empresa.getId()),eq(hoje),ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colecao));

    	Collection<ExamesPrevistosRelatorio> resultado = null;

    	Exception exception = null;
		try
		{
			resultado = exameManager.findRelatorioExamesPrevistos(empresa.getId(), hoje, examesCheck, estabelecimentosCheck, areasCheck, colaboradoresCheck, 'N', false, false);
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
    	
    	boolean imprimirAfastados=true;
    	
    	Date hoje = new Date();
    	Calendar doisMesesAtras = Calendar.getInstance();
    	doisMesesAtras.add(Calendar.MONTH, -2);
    	Calendar tresMesesAtras = Calendar.getInstance();
    	tresMesesAtras.add(Calendar.MONTH, -3);
    	
    	Long[] areasCheck={1L}, estabelecimentosCheck={1L}, colaboradoresCheck={1L}, examesCheck = {1L};
    	Collection<ExamesPrevistosRelatorio> colecao = new ArrayList<ExamesPrevistosRelatorio>();
    	Collection<ExamesPrevistosRelatorio> colecaoFiltrada = new ArrayList<ExamesPrevistosRelatorio>();
    	
    	ExamesPrevistosRelatorio examesPrevistosColaborador1Exame1Fora = new ExamesPrevistosRelatorio(1L,1L,2L,"Cargo","","","",tresMesesAtras.getTime(),tresMesesAtras.getTime(),1, "PERIODICO",1L,"EST");
    	ExamesPrevistosRelatorio examesPrevistosColaborador1Exame1Atual = new ExamesPrevistosRelatorio(1L,1L,1L,"Cargo","","","",doisMesesAtras.getTime(),doisMesesAtras.getTime(),1, "PERIODICO",1L,"EST");
    	ExamesPrevistosRelatorio examesPrevistosColaborador2Exame2 = new ExamesPrevistosRelatorio(2L,2L,2L,"Cargo","","","",doisMesesAtras.getTime(),doisMesesAtras.getTime(),1, "PERIODICO",1L,"EST");
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
    	
    	exameDao.expects(once()).method("findExamesPeriodicosPrevistos").with(new Constraint[]{eq(empresa.getId()),eq(hoje),ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(colecao));
    	
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
    		resultado = exameManager.findRelatorioExamesPrevistos(empresa.getId(), hoje, examesCheck, estabelecimentosCheck, areasCheck, colaboradoresCheck, 'A', imprimirAfastados, false );
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
    	exameDao.expects(once()).method("findExamesPeriodicosPrevistos").with(new Constraint[]{eq(empresa.getId()),ANYTHING,ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING, ANYTHING}).will(returnValue(new ArrayList<ExamesRealizadosRelatorio>()));

		assertTrue(exameManager.findRelatorioExamesPrevistos(empresa.getId(), null, null, null, null, null,'N', false, false).isEmpty());
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

    public void testFindRelatorioExamesRealizadosResumido() throws ColecaoVaziaException
    {
    	Date dataIni = DateUtil.criarDataMesAno(1, 1, 2009); 
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2011); 
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Collection<ExamesRealizadosRelatorio> colecao = new ArrayList<ExamesRealizadosRelatorio>();
    	ExamesRealizadosRelatorio examesPrevistosRelatorio = new ExamesRealizadosRelatorio();
    	colecao.add(examesPrevistosRelatorio);
    	
    	exameDao.expects(once()).method("findExamesRealizadosRelatorioResumido").will(returnValue(colecao));
    	
    	assertEquals(1,exameManager.findRelatorioExamesRealizadosResumido(empresa.getId(), dataIni, dataFim, null, null).size());
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

    public void testFindRelatorioExamesRealizadosResumidoColecaoVaziaException()
    {
    	Date dataIni = DateUtil.criarDataMesAno(1, 1, 2009); 
		Date dataFim = DateUtil.criarDataMesAno(1, 1, 2011); 
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	exameDao.expects(once()).method("findExamesRealizadosRelatorioResumido").will(returnValue(new ArrayList<ExamesRealizadosRelatorio>()));
    	
    	Exception exception = null;
    	
    	try
    	{
    		exameManager.findRelatorioExamesRealizadosResumido(empresa.getId(), dataIni, dataFim, null, null).size();
    	}
    	catch (ColecaoVaziaException e)
    	{
    		exception = e;
    	}
    	
    	assertNotNull(exception);
    }
    
    public void testEnviaLembreteExamesPrevistos() throws Exception
    {
    	Collection<Empresa> empresas = new ArrayList<Empresa>();
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresas.add(empresa);
    	
    	Collection<String> emails = new ArrayList<String>();
    	gerenciadorComunicacaoManager.expects(once()).method("enviaLembreteExamesPrevistos").with(ANYTHING).isVoid();
    	
    	Exception exception = null;
		try
		{
			exameManager.enviaLembreteExamesPrevistos(empresas);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			exception = e;
		}		
		
		assertNull(exception);
    }
    
}