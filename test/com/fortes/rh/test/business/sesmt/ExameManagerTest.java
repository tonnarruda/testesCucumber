package com.fortes.rh.test.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.sesmt.ExameManagerImpl;
import com.fortes.rh.dao.sesmt.ExameDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.relatorio.ExamesRealizadosRelatorio;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.util.DateUtil;

public class ExameManagerTest extends MockObjectTestCase
{
	private ExameManagerImpl exameManager = new ExameManagerImpl();
	private Mock exameDao = null;
	private Mock gerenciadorComunicacaoManager;

	protected void setUp() throws Exception
    {
        super.setUp();
        exameDao = new Mock(ExameDao.class);
        exameManager.setDao((ExameDao) exameDao.proxy());
        
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

    public void testPopulaCheckBox()
    {
    	Long empresaId=3L;
    	exameDao.expects(once()).method("findByEmpresaComAsoPadrao").will(returnValue(new ArrayList<Exame>()));
    	assertNotNull(exameManager.populaCheckBox(empresaId));
    }
    public void testPopulaCheckBoxException()
    {
    	Long empresaId=3L;
    	exameDao.expects(once()).method("findByEmpresaComAsoPadrao").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException("",""))));
    	assertNotNull(exameManager.populaCheckBox(empresaId));
    }
    
    public void testFindRelatorioExamesRealizados() throws ColecaoVaziaException
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	Collection<ExamesRealizadosRelatorio> colecao = new ArrayList<ExamesRealizadosRelatorio>();
    	ExamesRealizadosRelatorio examesPrevistosRelatorio = new ExamesRealizadosRelatorio(1L, "do dedo", 'C', "Toque", new Date(), 1L, "Socorro", "Passou não", "problema", 1L, "estabelecimento", "observação");
    	
    	colecao.add(examesPrevistosRelatorio);
    	
    	exameDao.expects(once()).method("findExamesRealizadosColaboradores").will(returnValue(colecao));
    	exameDao.expects(once()).method("findExamesRealizadosCandidatos").will(returnValue(colecao));
    	
    	assertEquals(2, exameManager.findRelatorioExamesRealizados(empresa.getId(), null, new Date(), new Date(), null, null, null, null, null, TipoPessoa.TODOS.getChave()).size());
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
    	
    	assertEquals(1,exameManager.findRelatorioExamesRealizadosResumido(empresa.getId(), dataIni, dataFim, null, null, null).size());
    }
    
    public void testFindRelatorioExamesRealizadosColecaoVaziaException()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	exameDao.expects(once()).method("findExamesRealizadosColaboradores").will(returnValue(new ArrayList<ExamesRealizadosRelatorio>()));
    	exameDao.expects(once()).method("findExamesRealizadosCandidatos").will(returnValue(new ArrayList<ExamesRealizadosRelatorio>()));
    	
    	Exception exception = null;

    	try
		{
			exameManager.findRelatorioExamesRealizados(empresa.getId(), null, null, null, null, null, null,null,null, TipoPessoa.TODOS.getChave());
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
    		exameManager.findRelatorioExamesRealizadosResumido(empresa.getId(), dataIni, dataFim, null, null, null).size();
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