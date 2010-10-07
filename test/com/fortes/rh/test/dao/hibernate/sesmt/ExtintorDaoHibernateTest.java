package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.ExtintorDao;
import com.fortes.rh.model.dicionario.TipoExtintor;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorFactory;

public class ExtintorDaoHibernateTest extends GenericDaoHibernateTest<Extintor>
{
	private ExtintorDao extintorDao;
	private EmpresaDao empresaDao;
	private EstabelecimentoDao estabelecimentoDao;

	@Override
	public Extintor getEntity()
	{
		Extintor extintor = new Extintor();
		return extintor;
	}

	@Override
	public GenericDao<Extintor> getGenericDao()
	{
		return extintorDao;
	}

	public void setExtintorDao(ExtintorDao extintorDao)
	{
		this.extintorDao = extintorDao;
	}

	public void testGetCount()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Extintor extintor = ExtintorFactory.getEntity();
		extintor.setEmpresa(empresa);
		extintor.setNumeroCilindro(0243);
		extintor.setTipo(TipoExtintor.PO_QUIMICO_SECO);
		extintorDao.save(extintor);

		Extintor extintor2 = ExtintorFactory.getEntity();
		extintor2.setEmpresa(empresa);
		extintor2.setNumeroCilindro(00000);
		extintor2.setTipo(TipoExtintor.PO_QUIMICO_SECO);
		extintorDao.save(extintor2);

		assertEquals(Integer.valueOf(1),
					extintorDao.getCount(empresa.getId(), TipoExtintor.PO_QUIMICO_SECO, 0243, null));
	}

	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Extintor extintor = ExtintorFactory.getEntity();
		extintor.setEmpresa(empresa);
		extintor.setNumeroCilindro(555);
		extintor.setLocalizacao("Sala do SESMT");
		extintor.setTipo(TipoExtintor.AGUA_GAS);
		extintor.setAtivo(true);
		extintorDao.save(extintor);

		Extintor extintor2 = ExtintorFactory.getEntity();
		extintor2.setEmpresa(empresa);
		extintor2.setNumeroCilindro(1243);
		extintor2.setLocalizacao("Administração");
		extintor2.setTipo(TipoExtintor.AGUA_GAS);
		extintor2.setAtivo(true);
		extintorDao.save(extintor2);
		
		Extintor extintor3 = ExtintorFactory.getEntity();
		extintor3.setEmpresa(empresa);
		extintor3.setNumeroCilindro(111);
		extintor3.setLocalizacao("Recepção");
		extintor3.setTipo(TipoExtintor.AGUA_GAS);
		extintor3.setAtivo(true);
		extintorDao.save(extintor3);
		
		Extintor extintorInativo = ExtintorFactory.getEntity();
		extintorInativo.setEmpresa(empresa);
		extintorInativo.setNumeroCilindro(243);
		extintorInativo.setTipo(TipoExtintor.AGUA_GAS);
		extintorInativo.setAtivo(false);
		extintorDao.save(extintorInativo);

		Collection<Extintor> extintors = extintorDao.findAllSelect(1,15,empresa.getId(), TipoExtintor.AGUA_GAS, null, true);
		
		assertEquals(3, extintors.size());
		
		extintor = (Extintor) extintors.toArray()[2];
		assertEquals("AG - Sala do SESMT - 555",extintor.getDescricao());

	}

	public void testFindByEstabelecimento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);

		Extintor extintor = ExtintorFactory.getEntity();
		extintor.setEmpresa(empresa);
		extintor.setEstabelecimento(estabelecimento);
		extintor.setAtivo(true);
		extintorDao.save(extintor);

		Extintor extintorInativo = ExtintorFactory.getEntity();
		extintorInativo.setEmpresa(empresa);
		extintorInativo.setEstabelecimento(estabelecimento);
		extintorInativo.setAtivo(false);
		extintorDao.save(extintorInativo);

		assertEquals(1, extintorDao.findByEstabelecimento(estabelecimento.getId(), true).size());
	}

	public void testGetFabricantesDistinct()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Extintor extintor1 = ExtintorFactory.getEntity();
		extintor1.setFabricante("Gaas");
		extintor1.setEmpresa(empresa);
		extintorDao.save(extintor1);

		Extintor extintor2 = ExtintorFactory.getEntity();
		extintor2.setFabricante("Aextinct");
		extintor2.setEmpresa(empresa);
		extintorDao.save(extintor2);

		Extintor extintor3 = ExtintorFactory.getEntity();
		extintor3.setFabricante("Aextinct");
		extintor3.setEmpresa(empresa);
		extintorDao.save(extintor3);

		Collection<String> fabricantes = extintorDao.findFabricantesDistinctByEmpresa(empresa.getId());
		assertEquals(2, fabricantes.size());
		assertEquals(extintor2.getFabricante(), fabricantes.toArray()[0]);
		assertEquals(extintor1.getFabricante(), fabricantes.toArray()[1]);
	}

	public void testGetLocalizacaoDistinct()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Extintor extintor1 = ExtintorFactory.getEntity();
		extintor1.setLocalizacao("Gaas");
		extintor1.setEmpresa(empresa);
		extintorDao.save(extintor1);

		Extintor extintor2 = ExtintorFactory.getEntity();
		extintor2.setLocalizacao("Aextinct");
		extintor2.setEmpresa(empresa);
		extintorDao.save(extintor2);

		Extintor extintor3 = ExtintorFactory.getEntity();
		extintor3.setLocalizacao("Aextinct");
		extintor3.setEmpresa(empresa);
		extintorDao.save(extintor3);

		Collection<String> Localizacoes = extintorDao.findLocalizacoesDistinctByEmpresa(empresa.getId());
		assertEquals(2, Localizacoes.size());
		assertEquals(extintor2.getLocalizacao(), Localizacoes.toArray()[0]);
		assertEquals(extintor1.getLocalizacao(), Localizacoes.toArray()[1]);
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
	}
}