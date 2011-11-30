package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.CatDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;

public class CatDaoHibernateTest extends GenericDaoHibernateTest<Cat>
{
	private CatDao catDao;
	private ColaboradorDao colaboradorDao;
	private EmpresaDao empresaDao;
	private EstabelecimentoDao estabelecimentoDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;

	public Cat getEntity()
	{
		Cat cat = new Cat();
		cat.setData(new Date());
		cat.setNumeroCat("111");
		return cat;
	}

	public void testFindCatsColaborador()
	{
		Collection<Cat> cats = catDao.findByColaborador(new Colaborador());
		assertNotNull(cats);
	}

	public void testFindCatsColaboradorByDate()
	{
		Collection<Cat> cats = catDao.findCatsColaboradorByDate(new Colaborador(),new Date());
		assertNotNull(cats);
	}

	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setNome("teste");
		areaOrganizacionalDao.save(areaOrganizacional);

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoAtual.setNome("Filial A");
		estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Ana");
		colaborador.setEmpresa(empresa);
		colaborador.setEstabelecimentoNomeProjection(estabelecimentoAtual.getNome());
		colaborador.setAreaOrganizacional(areaOrganizacional);
		colaboradorDao.save(colaborador);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2009));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		Cat cat = new Cat();
		cat.setNumeroCat("123456");
		cat.setObservacao("observacao");
		cat.setGerouAfastamento(false);
		cat.setData(new Date());
		cat.setColaborador(colaborador);
		catDao.save(cat);

		Long[] estabelecimentoIds = new Long[]{estabelecimentoAtual.getId(), -1325L};
		Long[] areaIds = new Long[]{areaOrganizacional.getId()};

		Collection<Cat> colecao = catDao.findAllSelect(empresa.getId(), new Date(), new Date(), estabelecimentoIds, "a", areaIds);

		assertEquals(1, colecao.size());
	}
	
	public void testGetCountRelatorio()
	{
		Date inicio = new Date();
		Date fim = new Date();
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Ana");
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setEmpresa(empresa);
		estabelecimento.setNome("Filial A");
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2009));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimento);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		Cat cat = new Cat();
		cat.setData(new Date());
		cat.setColaborador(colaborador);
		catDao.save(cat);
		
		Collection<Object[]> resultado = catDao.getCatsRelatorio(estabelecimento.getId(), inicio, fim);
		
		assertEquals(1, resultado.size());
	}
	
	public void testGetQdtDiasSemAcidentes()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Empresa empresa2 = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa2);

		Colaborador ana = ColaboradorFactory.getEntity();
		ana.setNome("Ana");
		ana.setEmpresa(empresa);
		colaboradorDao.save(ana);

		Colaborador pedro = ColaboradorFactory.getEntity();
		pedro.setNome("Pedro");
		pedro.setEmpresa(empresa2);
		colaboradorDao.save(pedro);

		Cat cat = new Cat();
		cat.setData(DateUtil.criarDataMesAno(1, 1, 2011));
		cat.setColaborador(ana);
		catDao.save(cat);

		Cat cat2 = new Cat();
		cat2.setData(DateUtil.criarDataMesAno(1, 2, 2011));
		cat2.setColaborador(ana);
		catDao.save(cat2);

		Cat cat3 = new Cat();
		cat3.setData(DateUtil.criarDataMesAno(1, 3, 2011));
		cat3.setColaborador(pedro);
		catDao.save(cat3);
		
		Cat ultimoCat = catDao.findUltimoCat(empresa.getId());
		
		assertEquals(DateUtil.criarDataMesAno(1, 2, 2011), ultimoCat.getData());
	}

	public GenericDao<Cat> getGenericDao()
	{
		return catDao;
	}

	public void setCatDao(CatDao catDao)
	{
		this.catDao = catDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao)
	{
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao) {
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}
}