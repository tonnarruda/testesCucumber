package com.fortes.rh.test.dao.hibernate.captacao;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.captacao.EtapaSeletivaDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.model.captacao.EtapaSeletiva;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.captacao.EtapaSeletivaFactory;

public class EtapaSeletivaDaoHibernateTest extends GenericDaoHibernateTest<EtapaSeletiva>
{
	private EtapaSeletivaDao etapaSeletivaDao;
	private EmpresaDao empresaDao;

	public EtapaSeletiva getEntity()
	{
		EtapaSeletiva etapaSeletiva = new EtapaSeletiva();

		etapaSeletiva.setId(null);
		etapaSeletiva.setNome("nome");
		etapaSeletiva.setOrdem(1);
		return etapaSeletiva;
	}

	public GenericDao<EtapaSeletiva> getGenericDao()
	{
		return etapaSeletivaDao;
	}

	public void setEtapaSeletivaDao(EtapaSeletivaDao etapaSeletivaDao)
	{
		this.etapaSeletivaDao = etapaSeletivaDao;
	}

	public void testFind()
	{
		Empresa empresa1 = new Empresa();
		empresa1.setNome("empresa");
		empresa1.setCnpj("21212121212");
		empresa1.setRazaoSocial("empresa");
		empresa1 = empresaDao.save(empresa1);

		Empresa empresa2 = new Empresa();
		empresa2.setNome("empresa");
		empresa2.setCnpj("21212121212");
		empresa2.setRazaoSocial("empresa");
		empresa2 = empresaDao.save(empresa2);

		EtapaSeletiva etapa1 = new EtapaSeletiva();
		etapa1.setNome("etapa1");
		etapa1.setOrdem(1);
		etapa1.setEmpresa(empresa1);
		etapa1 = etapaSeletivaDao.save(etapa1);

		EtapaSeletiva etapa2 = new EtapaSeletiva();
		etapa2.setNome("etapa2");
		etapa2.setOrdem(2);
		etapa2.setEmpresa(empresa1);
		etapaSeletivaDao.save(etapa2);

		EtapaSeletiva etapa3 = new EtapaSeletiva();
		etapa3.setNome("etapa3");
		etapa3.setOrdem(3);
		etapa3.setEmpresa(empresa2);
		etapaSeletivaDao.save(etapa3);

		Collection<EtapaSeletiva> etapas = etapaSeletivaDao.findAllSelect(0, 0, empresa1.getId());

		assertEquals(2, etapas.size());
		assertEquals(etapa1, etapas.toArray()[0]);
	}

	public void testFindByEtapaSeletivaId()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		EtapaSeletiva etapa = new EtapaSeletiva();
		etapa.setNome("etapa1");
		etapa.setOrdem(1);
		etapa.setEmpresa(empresa);
		etapa = etapaSeletivaDao.save(etapa);

		EtapaSeletiva etapaSeletivaRetorno = etapaSeletivaDao.findByEtapaSeletivaId(etapa.getId(),empresa.getId());

		assertEquals(etapa.getId(), etapaSeletivaRetorno.getId());
	}

	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		EtapaSeletiva etapa = new EtapaSeletiva();
		etapa.setNome("etapa1");
		etapa.setOrdem(1);
		etapa.setEmpresa(empresa);
		etapa = etapaSeletivaDao.save(etapa);

		EtapaSeletiva etapaSeletivaRetorno = etapaSeletivaDao.findByIdProjection(etapa.getId());

		assertEquals(etapa.getId(), etapaSeletivaRetorno.getId());
	}

	public void testFindByIdsProjection()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		EtapaSeletiva etapa = new EtapaSeletiva();
		etapa.setNome("etapa1");
		etapa.setOrdem(1);
		etapa.setEmpresa(empresa);
		etapa = etapaSeletivaDao.save(etapa);

		EtapaSeletiva etapa2 = new EtapaSeletiva();
		etapa2.setNome("etapa2");
		etapa2.setOrdem(2);
		etapa2.setEmpresa(empresa);
		etapaSeletivaDao.save(etapa2);

		Long[] ids = {etapa.getId(),etapa2.getId()};

		Collection<EtapaSeletiva> etapaSeletivas = etapaSeletivaDao.findByIdProjection(empresa.getId(), ids);
		assertEquals(2, etapaSeletivas.size());

		etapaSeletivas = etapaSeletivaDao.findByIdProjection(empresa.getId(), null);
		assertEquals(2, etapaSeletivas.size());
		
	}

	public void testFindPrimeiraEtapa()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		EtapaSeletiva etapa = new EtapaSeletiva();
		etapa.setNome("etapa1");
		etapa.setOrdem(1);
		etapa.setEmpresa(empresa);
		etapa = etapaSeletivaDao.save(etapa);

		EtapaSeletiva etapa2 = new EtapaSeletiva();
		etapa2.setNome("etapa2");
		etapa2.setOrdem(2);
		etapa2.setEmpresa(empresa);
		etapaSeletivaDao.save(etapa2);

		EtapaSeletiva etapa3 = new EtapaSeletiva();
		etapa3.setNome("etapa3");
		etapa3.setOrdem(3);
		etapa3.setEmpresa(empresa);
		etapaSeletivaDao.save(etapa3);

		EtapaSeletiva etapaSeletivaRetorno = etapaSeletivaDao.findPrimeiraEtapa(empresa.getId());

		assertEquals(etapa.getId(), etapaSeletivaRetorno.getId());
	}

	public void testGetCount()
	{
		Collection<EtapaSeletiva> lista = etapaSeletivaDao.find(new String[]{"empresa.id"}, new Object[] {1L});
		int result = etapaSeletivaDao.getCount(1L);

		assertEquals(result, lista.size());
	}

	public void testFindAllSelectSemPaginacao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);
		empresa = empresaDao.save(empresa);

		populaEmpresaComEtapaSeletiva(empresa);

		Collection<EtapaSeletiva> etapaSeletivas = etapaSeletivaDao.findAllSelect(0, 0, empresa.getId());

		assertEquals(2, etapaSeletivas.size());
	}

	public void testFindAllSelectComPaginacao()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setId(1L);
		empresa = empresaDao.save(empresa);

		populaEmpresaComEtapaSeletiva(empresa);

		Collection<EtapaSeletiva> etapaSeletivas = etapaSeletivaDao.findAllSelect(1,15,empresa.getId());

		assertEquals(2, etapaSeletivas.size());
	}

	/**
	 * Utilizado nos testes "testFindAllSelectSemPaginacao e testFindAllSelect"
	 */
	private void populaEmpresaComEtapaSeletiva(Empresa empresa)
	{
		EtapaSeletiva etapaSeletiva1 = EtapaSeletivaFactory.getEntity();
		etapaSeletiva1.setId(1L);
		etapaSeletiva1.setEmpresa(empresa);
		etapaSeletivaDao.save(etapaSeletiva1);

		EtapaSeletiva etapaSeletiva2 = EtapaSeletivaFactory.getEntity();
		etapaSeletiva2.setId(2L);
		etapaSeletiva2.setEmpresa(empresa);
		etapaSeletivaDao.save(etapaSeletiva2);
	}

	public void testOrdeneDecrescentementeEntre()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		EtapaSeletiva etapa = new EtapaSeletiva();
		etapa.setNome("etapa1");
		etapa.setOrdem(2);
		etapa.setEmpresa(empresa);
		etapa = etapaSeletivaDao.save(etapa);

		EtapaSeletiva etapa2 = new EtapaSeletiva();
		etapa2.setNome("etapa2");
		etapa2.setOrdem(3);
		etapa2.setEmpresa(empresa);
		etapaSeletivaDao.save(etapa2);

		EtapaSeletiva etapa3 = new EtapaSeletiva();
		etapa3.setNome("etapa3");
		etapa3.setOrdem(4);
		etapa3.setEmpresa(empresa);
		etapaSeletivaDao.save(etapa3);

		etapaSeletivaDao.ordeneDecrescentementeEntre(3, 1, etapa3);

		//assertEquals(3, etapa3.getOrdem());
	}

	public void testOrdeneCrescentementeEntre()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		EtapaSeletiva etapa = new EtapaSeletiva();
		etapa.setNome("etapa1");
		etapa.setOrdem(1);
		etapa.setEmpresa(empresa);
		etapa = etapaSeletivaDao.save(etapa);

		EtapaSeletiva etapa2 = new EtapaSeletiva();
		etapa2.setNome("etapa2");
		etapa2.setOrdem(2);
		etapa2.setEmpresa(empresa);
		etapaSeletivaDao.save(etapa2);

		EtapaSeletiva etapa3 = new EtapaSeletiva();
		etapa3.setNome("etapa3");
		etapa3.setOrdem(3);
		etapa3.setEmpresa(empresa);
		etapaSeletivaDao.save(etapa3);

		etapaSeletivaDao.ordeneCrescentementeEntre(3, 1, etapa);

		//assertEquals(3, etapa2.getOrdem());
	}

	public void testOrdeneCrescentementeApartirDe()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		EtapaSeletiva etapa = new EtapaSeletiva();
		etapa.setNome("etapa1");
		etapa.setOrdem(1);
		etapa.setEmpresa(empresa);
		etapa = etapaSeletivaDao.save(etapa);

		EtapaSeletiva etapa2 = new EtapaSeletiva();
		etapa2.setNome("etapa2");
		etapa2.setOrdem(2);
		etapa2.setEmpresa(empresa);
		etapaSeletivaDao.save(etapa2);

		EtapaSeletiva etapa3 = new EtapaSeletiva();
		etapa3.setNome("etapa3");
		etapa3.setOrdem(3);
		etapa3.setEmpresa(empresa);
		etapaSeletivaDao.save(etapa3);

		etapaSeletivaDao.ordeneCrescentementeAPartirDe(1, etapa);

		//assertEquals(3, etapa2.getOrdem());
	}

	public void testOrdeneDecrescentementeApartirDe()
	{
		Empresa empresa = new Empresa();
		empresa.setNome("empresa");
		empresa.setCnpj("21212121212");
		empresa.setRazaoSocial("empresa");
		empresa = empresaDao.save(empresa);

		EtapaSeletiva etapa = new EtapaSeletiva();
		etapa.setNome("etapa1");
		etapa.setOrdem(1);
		etapa.setEmpresa(empresa);
		etapa = etapaSeletivaDao.save(etapa);

		EtapaSeletiva etapa2 = new EtapaSeletiva();
		etapa2.setNome("etapa2");
		etapa2.setOrdem(2);
		etapa2.setEmpresa(empresa);
		etapaSeletivaDao.save(etapa2);

		EtapaSeletiva etapa3 = new EtapaSeletiva();
		etapa3.setNome("etapa3");
		etapa3.setOrdem(3);
		etapa3.setEmpresa(empresa);
		etapaSeletivaDao.save(etapa3);

		etapaSeletivaDao.ordeneDecrescentementeApartirDe(1, etapa);

		//assertEquals(1, etapa2.getOrdem());
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
}
