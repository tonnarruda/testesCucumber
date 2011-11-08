package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.ExtintorDao;
import com.fortes.rh.dao.sesmt.HistoricoExtintorDao;
import com.fortes.rh.model.dicionario.TipoExtintor;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.HistoricoExtintor;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorFactory;
import com.fortes.rh.test.factory.sesmt.HistoricoExtintorFactory;

public class ExtintorDaoHibernateTest extends GenericDaoHibernateTest<Extintor>
{
	private ExtintorDao extintorDao;
	private HistoricoExtintorDao historicoExtintorDao;
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
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);

		Extintor extintor = ExtintorFactory.getEntity();
		extintor.setEmpresa(empresa);
		extintor.setNumeroCilindro(0243);
		extintor.setTipo(TipoExtintor.PO_QUIMICO_SECO);
		extintorDao.save(extintor);
		
		HistoricoExtintor hist1 = HistoricoExtintorFactory.getEntity();
		hist1.setExtintor(extintor);
		hist1.setEstabelecimento(estabelecimento);
		hist1.setLocalizacao("A1");
		historicoExtintorDao.save(hist1);

		Extintor extintor2 = ExtintorFactory.getEntity();
		extintor2.setEmpresa(empresa);
		extintor2.setNumeroCilindro(00000);
		extintor2.setTipo(TipoExtintor.PO_QUIMICO_SECO);
		extintorDao.save(extintor2);

		HistoricoExtintor hist2 = HistoricoExtintorFactory.getEntity();
		hist2.setExtintor(extintor2);
		hist2.setEstabelecimento(estabelecimento);
		hist2.setLocalizacao("A2");
		historicoExtintorDao.save(hist2);

		assertEquals(Integer.valueOf(1), extintorDao.getCount(empresa.getId(), TipoExtintor.PO_QUIMICO_SECO, 0243, null));
	}

	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);

		Extintor extintor = ExtintorFactory.getEntity();
		extintor.setEmpresa(empresa);
		extintor.setNumeroCilindro(555);
		extintor.setTipo(TipoExtintor.AGUA_GAS);
		extintor.setAtivo(true);
		extintorDao.save(extintor);
		
		HistoricoExtintor hist1 = HistoricoExtintorFactory.getEntity();
		hist1.setEstabelecimento(estabelecimento);
		hist1.setLocalizacao("Sala do SESMT");
		hist1.setExtintor(extintor);
		historicoExtintorDao.save(hist1);

		Extintor extintor2 = ExtintorFactory.getEntity();
		extintor2.setEmpresa(empresa);
		extintor2.setNumeroCilindro(1243);
		extintor2.setTipo(TipoExtintor.AGUA_GAS);
		extintor2.setAtivo(true);
		extintorDao.save(extintor2);
		
		HistoricoExtintor hist2 = HistoricoExtintorFactory.getEntity();
		hist2.setEstabelecimento(estabelecimento);
		hist2.setLocalizacao("Administração");
		hist2.setExtintor(extintor2);
		historicoExtintorDao.save(hist2);
		
		Extintor extintor3 = ExtintorFactory.getEntity();
		extintor3.setEmpresa(empresa);
		extintor3.setNumeroCilindro(111);
		extintor3.setTipo(TipoExtintor.AGUA_GAS);
		extintor3.setAtivo(true);
		extintorDao.save(extintor3);
		
		HistoricoExtintor hist3 = HistoricoExtintorFactory.getEntity();
		hist3.setEstabelecimento(estabelecimento);
		hist3.setLocalizacao("Recepção");
		hist3.setExtintor(extintor3);
		historicoExtintorDao.save(hist3);
		
		Extintor extintorInativo = ExtintorFactory.getEntity();
		extintorInativo.setEmpresa(empresa);
		extintorInativo.setNumeroCilindro(243);
		extintorInativo.setTipo(TipoExtintor.AGUA_GAS);
		extintorInativo.setAtivo(false);
		extintorDao.save(extintorInativo);
		
		HistoricoExtintor histInativo = HistoricoExtintorFactory.getEntity();
		histInativo.setEstabelecimento(estabelecimento);
		histInativo.setLocalizacao("Recepção");
		histInativo.setExtintor(extintorInativo);
		historicoExtintorDao.save(histInativo);

		Collection<Extintor> extintors = extintorDao.findAllSelect(1,15,empresa.getId(), TipoExtintor.AGUA_GAS, null, true);
		
		assertEquals(3, extintors.size());
		
		extintor = (Extintor) extintors.toArray()[2];
		assertEquals("AG - 555",extintor.getDescricao());

	}

	public void testFindByEstabelecimento()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);

		Extintor extintor = ExtintorFactory.getEntity();
		extintor.setEmpresa(empresa);
		extintor.setAtivo(true);
		extintorDao.save(extintor);
		
		HistoricoExtintor hist1 = HistoricoExtintorFactory.getEntity();
		hist1.setEstabelecimento(estabelecimento);
		hist1.setLocalizacao("carro 999");
		hist1.setExtintor(extintor);
		historicoExtintorDao.save(hist1);

		Extintor extintorInativo = ExtintorFactory.getEntity();
		extintorInativo.setEmpresa(empresa);
		extintorInativo.setAtivo(false);
		extintorDao.save(extintorInativo);

		HistoricoExtintor hist2 = HistoricoExtintorFactory.getEntity();
		hist2.setEstabelecimento(estabelecimento);
		hist2.setLocalizacao("carro 100");
		hist2.setExtintor(extintorInativo);
		historicoExtintorDao.save(hist2);

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
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);

		Extintor extintor1 = ExtintorFactory.getEntity();
		extintor1.setEmpresa(empresa);
		extintorDao.save(extintor1);
		
		HistoricoExtintor hist1 = HistoricoExtintorFactory.getEntity();
		hist1.setEstabelecimento(estabelecimento);
		hist1.setLocalizacao("Gaas");
		hist1.setExtintor(extintor1);
		historicoExtintorDao.save(hist1);

		Extintor extintor2 = ExtintorFactory.getEntity();
		extintor2.setEmpresa(empresa);
		extintorDao.save(extintor2);

		HistoricoExtintor hist2 = HistoricoExtintorFactory.getEntity();
		hist2.setEstabelecimento(estabelecimento);
		hist2.setLocalizacao("Aextinct");
		hist2.setExtintor(extintor2);
		historicoExtintorDao.save(hist2);

		Extintor extintor3 = ExtintorFactory.getEntity();
		extintor3.setEmpresa(empresa);
		extintorDao.save(extintor3);

		HistoricoExtintor hist3 = HistoricoExtintorFactory.getEntity();
		hist3.setEstabelecimento(estabelecimento);
		hist3.setLocalizacao("Aextinct");
		hist3.setExtintor(extintor3);
		historicoExtintorDao.save(hist3);

		Collection<String> localizacoes = extintorDao.findLocalizacoesDistinctByEmpresa(empresa.getId());
		assertEquals(2, localizacoes.size());
		assertEquals(hist2.getLocalizacao(), localizacoes.toArray()[0]);
		assertEquals(hist1.getLocalizacao(), localizacoes.toArray()[1]);
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setHistoricoExtintorDao(HistoricoExtintorDao historicoExtintorDao) {
		this.historicoExtintorDao = historicoExtintorDao;
	}
}