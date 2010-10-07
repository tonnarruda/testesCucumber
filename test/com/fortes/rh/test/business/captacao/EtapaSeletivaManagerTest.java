package com.fortes.rh.test.business.captacao;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.captacao.EtapaSeletivaManagerImpl;
import com.fortes.rh.dao.captacao.EtapaSeletivaDao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.EtapaSeletivaFactory;

public class EtapaSeletivaManagerTest extends MockObjectTestCase
{
	private EtapaSeletivaManagerImpl etapaSeletivaManager = new EtapaSeletivaManagerImpl();
	private Mock etapaSeletivaDao = null;

    protected void setUp() throws Exception
    {
        super.setUp();
        etapaSeletivaDao = new Mock(EtapaSeletivaDao.class);
        etapaSeletivaManager.setDao((EtapaSeletivaDao) etapaSeletivaDao.proxy());
    }

    public void testFindByEtapaSeletivaId()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);

    	etapaSeletivaDao.expects(once()).method("findByEtapaSeletivaId").with(eq(etapaSeletiva.getId()),eq(empresa.getId())).will(returnValue(etapaSeletiva));

    	EtapaSeletiva etapaSeletivaRetorno = etapaSeletivaManager.findByEtapaSeletivaId(etapaSeletiva.getId(),empresa.getId());

    	assertEquals(etapaSeletiva.getId(), etapaSeletivaRetorno.getId());
    }

    public void testSugerirOrdem()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1234L);

    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);

    	Collection<EtapaSeletiva> etapaSeletivas = new ArrayList<EtapaSeletiva>();
    	etapaSeletivas.add(etapaSeletiva);

    	etapaSeletivaDao.expects(once()).method("findAllSelect").with(eq(0), eq(0), eq(empresa.getId())).will(returnValue(etapaSeletivas));

    	int retorno = etapaSeletivaManager.sugerirOrdem(empresa.getId());

    	assertEquals(2, retorno);
    }

    public void testFindPrimeiraEtapa()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1234L);

    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);

    	etapaSeletivaDao.expects(once()).method("findPrimeiraEtapa").with(eq(empresa.getId())).will(returnValue(etapaSeletiva));

    	EtapaSeletiva etapaSeletivaRetorno = etapaSeletivaManager.findPrimeiraEtapa(empresa.getId());

    	assertEquals(etapaSeletiva.getId(), etapaSeletivaRetorno.getId());
    }

    public void testFindByIdProjection()
    {
    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);

    	etapaSeletivaDao.expects(once()).method("findByIdProjection").with(eq(etapaSeletiva.getId())).will(returnValue(etapaSeletiva));
    	EtapaSeletiva etapaSeletivaRetorno = etapaSeletivaManager.findByIdProjection(etapaSeletiva.getId());

    	assertEquals(etapaSeletiva.getId(), etapaSeletivaRetorno.getId());
    }

    public void testSave()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1234L);

    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);
    	etapaSeletiva.setOrdem(3);

    	EtapaSeletiva etapaSeletivaSave = EtapaSeletivaFactory.getEntity();
    	etapaSeletivaSave.setId(2L);
    	etapaSeletivaSave.setOrdem(4);

    	etapaSeletivaDao.expects(once()).method("ordeneCrescentementeAPartirDe").with(eq(etapaSeletiva.getOrdem()),eq(etapaSeletiva));
    	etapaSeletivaDao.expects(once()).method("save").with(eq(etapaSeletiva)).will(returnValue(etapaSeletivaSave));

    	EtapaSeletiva etapaSeletivaRetorno = etapaSeletivaManager.save(etapaSeletiva);

    	assertEquals(4, etapaSeletivaRetorno.getOrdem());
    }

    public void testSaveOrdemZero()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1234L);

    	EtapaSeletiva etapaSeletivaZero = EtapaSeletivaFactory.getEntity();
    	etapaSeletivaZero.setId(1L);
    	etapaSeletivaZero.setOrdem(0);

    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);
    	etapaSeletiva.setOrdem(1);
    	etapaSeletivaDao.expects(once()).method("ordeneCrescentementeAPartirDe").with(eq(etapaSeletiva.getOrdem()),eq(etapaSeletiva));

    	EtapaSeletiva etapaSeletivaSave = EtapaSeletivaFactory.getEntity();
    	etapaSeletivaSave.setId(2L);
    	etapaSeletivaSave.setOrdem(2);
    	etapaSeletivaDao.expects(once()).method("save").with(eq(etapaSeletiva)).will(returnValue(etapaSeletivaSave));

    	EtapaSeletiva etapaSeletivaRetorno = etapaSeletivaManager.save(etapaSeletivaZero);

    	assertEquals(2, etapaSeletivaRetorno.getOrdem());
    }

    public void testUpdateOrdemMaior()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);
    	etapaSeletiva.setEmpresa(empresa);
    	etapaSeletiva.setOrdem(1);

    	EtapaSeletiva etapaSeletivaFind = EtapaSeletivaFactory.getEntity();
    	etapaSeletivaFind.setId(1L);
    	etapaSeletivaFind.setEmpresa(empresa);
    	etapaSeletivaFind.setOrdem(2);

    	etapaSeletivaDao.expects(once()).method("findByIdProjection").with(eq(etapaSeletivaFind.getId())).will(returnValue(etapaSeletivaFind));
    	etapaSeletivaDao.expects(once()).method("ordeneCrescentementeEntre").with(eq(etapaSeletivaFind.getOrdem()),eq(etapaSeletiva.getOrdem()),eq(etapaSeletiva));
    	etapaSeletivaDao.expects(once()).method("update").with(eq(etapaSeletiva));

    	etapaSeletivaManager.update(etapaSeletiva,empresa);
    }

    public void testUpdateOrdemMenor()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);
    	etapaSeletiva.setEmpresa(empresa);
    	etapaSeletiva.setOrdem(2);

    	EtapaSeletiva etapaSeletivaFind = EtapaSeletivaFactory.getEntity();
    	etapaSeletivaFind.setId(1L);
    	etapaSeletivaFind.setEmpresa(empresa);
    	etapaSeletivaFind.setOrdem(1);

    	etapaSeletivaDao.expects(once()).method("findByIdProjection").with(eq(etapaSeletivaFind.getId())).will(returnValue(etapaSeletivaFind));
    	etapaSeletivaDao.expects(once()).method("ordeneDecrescentementeEntre").with(eq(etapaSeletivaFind.getOrdem()),eq(etapaSeletiva.getOrdem()),eq(etapaSeletiva));
    	etapaSeletivaDao.expects(once()).method("update").with(eq(etapaSeletiva));

    	etapaSeletivaManager.update(etapaSeletiva,empresa);
    }

    public void testRemove()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);
    	etapaSeletiva.setEmpresa(empresa);
    	etapaSeletiva.setOrdem(2);

    	etapaSeletivaDao.expects(once()).method("findByEtapaSeletivaId").with(eq(etapaSeletiva.getId()),eq(empresa.getId())).will(returnValue(etapaSeletiva));
    	etapaSeletivaDao.expects(once()).method("ordeneDecrescentementeApartirDe").with(eq(etapaSeletiva.getOrdem()),eq(etapaSeletiva));
    	etapaSeletivaDao.expects(once()).method("remove").with(eq(etapaSeletiva));

    	etapaSeletivaManager.remove(etapaSeletiva,empresa);
    }

    public void testFindAllSelectSemPaginacao()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	Collection<EtapaSeletiva> etapaSeletivas = getEtapaSeletivas(empresa);

    	etapaSeletivaDao.expects(once()).method("findAllSelect").with(eq(0),eq(0),eq(empresa.getId())).will(returnValue(etapaSeletivas));

    	Collection<EtapaSeletiva> etapaSeletivasRetorno = etapaSeletivaManager.findAllSelect(0, 0, empresa.getId());

    	assertEquals(etapaSeletivas.size(), etapaSeletivasRetorno.size());
    }

    public void testFindAllSelectComPaginacao()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	Collection<EtapaSeletiva> etapaSeletivas = getEtapaSeletivas(empresa);

    	etapaSeletivaDao.expects(once()).method("findAllSelect").with(eq(1),eq(15),eq(empresa.getId())).will(returnValue(etapaSeletivas));

    	Collection<EtapaSeletiva> etapaSeletivasRetorno = etapaSeletivaManager.findAllSelect(1,15,empresa.getId());

    	assertEquals(etapaSeletivas.size(), etapaSeletivasRetorno.size());
    }


    public void testGetCount()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	Collection<EtapaSeletiva> etapaSeletivas = getEtapaSeletivas(empresa);

    	etapaSeletivaDao.expects(once()).method("getCount").with(eq(empresa.getId())).will(returnValue(etapaSeletivas.size()));

    	int retorno = etapaSeletivaManager.getCount(empresa.getId());

    	assertEquals(etapaSeletivas.size(), retorno);
    }

    private Collection<EtapaSeletiva> getEtapaSeletivas(Empresa empresa)
    {
    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);
    	etapaSeletiva.setEmpresa(empresa);

    	EtapaSeletiva etapaSeletiva2 = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva2.setId(2L);
    	etapaSeletiva2.setEmpresa(empresa);

    	Collection<EtapaSeletiva> etapaSeletivas = new ArrayList<EtapaSeletiva>();
    	etapaSeletivas.add(etapaSeletiva);
    	etapaSeletivas.add(etapaSeletiva2);

    	return etapaSeletivas;
    }

    public void testFindAllSelect()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa();
    	empresa.setId(1L);

    	EtapaSeletiva etapaSeletiva = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva.setId(1L);
    	etapaSeletiva.setEmpresa(empresa);

    	EtapaSeletiva etapaSeletiva2 = EtapaSeletivaFactory.getEntity();
    	etapaSeletiva2.setId(2L);
    	etapaSeletiva2.setEmpresa(empresa);

    	Collection<EtapaSeletiva> etapaSeletivas = new ArrayList<EtapaSeletiva>();
    	etapaSeletivas.add(etapaSeletiva);
    	etapaSeletivas.add(etapaSeletiva2);

    	etapaSeletivaDao.expects(once()).method("findAllSelect").with(ANYTHING,ANYTHING,ANYTHING).will(returnValue(etapaSeletivas));

    	Collection<EtapaSeletiva> etapaSeletivasRetorno = etapaSeletivaManager.findAllSelect(empresa.getId());

    	assertEquals(etapaSeletivas.size(), etapaSeletivasRetorno.size());

    }

    public void testRemoveWithObject(){

    	NoSuchMethodError exp = null;
    	try
		{
    		etapaSeletivaManager.remove(new EtapaSeletiva());
		}
		catch (NoSuchMethodError e)
		{
			exp = e;
		}

		assertNotNull(exp);
    }

    public void testRemoveWithLong(){

    	NoSuchMethodError exp = null;
    	try
    	{
    		etapaSeletivaManager.remove(1L);
    	}
    	catch (NoSuchMethodError e)
    	{
    		exp = e;
    	}

    	assertNotNull(exp);
    }

    public void testRemoveWithLongArray(){

    	NoSuchMethodError exp = null;
    	try
    	{
    		etapaSeletivaManager.remove(new Long[]{1L});
    	}
    	catch (NoSuchMethodError e)
    	{
    		exp = e;
    	}

    	assertNotNull(exp);
    }

    public void testUpdate(){

    	NoSuchMethodError exp = null;
    	try
    	{
    		etapaSeletivaManager.update(new EtapaSeletiva());
    	}
    	catch (NoSuchMethodError e)
    	{
    		exp = e;
    	}

    	assertNotNull(exp);
    }

    public void testSaveOrUpdate(){

    	NoSuchMethodError exp = null;
    	try
    	{
    		etapaSeletivaManager.saveOrUpdate(new ArrayList<EtapaSeletiva>());
    	}
    	catch (NoSuchMethodError e)
    	{
    		exp = e;
    	}

    	assertNotNull(exp);
    }
}
