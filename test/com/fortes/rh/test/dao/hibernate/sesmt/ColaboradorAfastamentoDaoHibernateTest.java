package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.AfastamentoDao;
import com.fortes.rh.dao.sesmt.ColaboradorAfastamentoDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.AfastamentoFactory;
import com.fortes.rh.test.factory.sesmt.ColaboradorAfastamentoFactory;
import com.fortes.rh.util.DateUtil;

public class ColaboradorAfastamentoDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorAfastamento>
{
	private ColaboradorAfastamentoDao colaboradorAfastamentoDao;
	private ColaboradorDao colaboradorDao;
	private AfastamentoDao afastamentoDao;
	private EmpresaDao empresaDao;
	private EstabelecimentoDao estabelecimentoDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;

	@Override
	public ColaboradorAfastamento getEntity()
	{
		ColaboradorAfastamento colaboradorAfastamento = new ColaboradorAfastamento();
		return colaboradorAfastamento;
	}

	@Override
	public GenericDao<ColaboradorAfastamento> getGenericDao()
	{
		return colaboradorAfastamentoDao;
	}

	public void testFindAllSelect()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Ana");
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoAtual.setNome("Filial A");
		estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

		Estabelecimento estabelecimentoAntigo = EstabelecimentoFactory.getEntity();
		estabelecimentoAntigo.setEmpresa(empresa);
		estabelecimentoAntigo.setNome("B");
		estabelecimentoAntigo = estabelecimentoDao.save(estabelecimentoAntigo);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2009));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		HistoricoColaborador historicoColaboradorAntigo = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAntigo.setData(DateUtil.criarDataMesAno(1, 1, 2008));
		historicoColaboradorAntigo.setColaborador(colaborador);
		historicoColaboradorAntigo.setEstabelecimento(estabelecimentoAntigo);
		historicoColaboradorAntigo.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAntigo = historicoColaboradorDao.save(historicoColaboradorAntigo);

		Afastamento afastamento = AfastamentoFactory.getEntity();
		afastamentoDao.save(afastamento);

		ColaboradorAfastamento colaboradorAfastamento = ColaboradorAfastamentoFactory.getEntity();
		colaboradorAfastamento.setInicio(DateUtil.criarDataMesAno(5, 3, 2009));
		colaboradorAfastamento.setColaborador(colaborador);
		colaboradorAfastamento.setAfastamento(afastamento);
		colaboradorAfastamentoDao.save(colaboradorAfastamento);

		Long[] estabelecimentoIds = new Long[]{estabelecimentoAntigo.getId(),estabelecimentoAtual.getId(), -1325L};
		
		char afastadoPeloINSS = 'T';//Todos

		Collection<ColaboradorAfastamento> teste = colaboradorAfastamentoDao.findAllSelect(0, 0, empresa.getId(), afastamento.getId(), "a", estabelecimentoIds, null, DateUtil.criarDataMesAno(1, 3, 2009), DateUtil.criarDataMesAno(1, 6, 2009), "DESC", false, false, afastadoPeloINSS);
		assertEquals(1, teste.size());

		Collection<ColaboradorAfastamento> teste2 = colaboradorAfastamentoDao.findAllSelect(1, 15, empresa.getId(), null, "", estabelecimentoIds, null, null, null, "DESC", false, false, afastadoPeloINSS);
		assertEquals(1, teste2.size());

		//Não afastados pelo inss
		afastadoPeloINSS = 'N';
		afastamento.setInss(false);
		afastamentoDao.save(afastamento);
		Collection<ColaboradorAfastamento> teste3 = colaboradorAfastamentoDao.findAllSelect(1, 15, empresa.getId(), null, "", estabelecimentoIds, null, null, null, "DESC", false, false, afastadoPeloINSS);
		assertEquals(1, teste3.size());

		//Afastados por inss
		afastadoPeloINSS = 'A';
		afastamento.setInss(true);
		afastamentoDao.save(afastamento);
		Collection<ColaboradorAfastamento> teste4 = colaboradorAfastamentoDao.findAllSelect(1, 15, empresa.getId(), null, "", estabelecimentoIds, null, null, null, "DESC", false, false, afastadoPeloINSS);
		assertEquals(1, teste4.size());
	}

	public void testGetCount()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome("Ana");
		colaborador.setEmpresa(empresa);
		colaboradorDao.save(colaborador);

		Estabelecimento estabelecimentoAtual = EstabelecimentoFactory.getEntity();
		estabelecimentoAtual.setEmpresa(empresa);
		estabelecimentoAtual.setNome("Filial A");
		estabelecimentoAtual = estabelecimentoDao.save(estabelecimentoAtual);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2009));
		historicoColaboradorAtual.setColaborador(colaborador);
		historicoColaboradorAtual.setEstabelecimento(estabelecimentoAtual);
		historicoColaboradorAtual.setAreaOrganizacional(areaOrganizacional);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		Afastamento afastamento = AfastamentoFactory.getEntity();
		afastamentoDao.save(afastamento);

		ColaboradorAfastamento colaboradorAfastamento = ColaboradorAfastamentoFactory.getEntity();
		colaboradorAfastamento.setInicio(DateUtil.criarDataMesAno(5, 3, 2009));
		colaboradorAfastamento.setColaborador(colaborador);
		colaboradorAfastamento.setAfastamento(afastamento);
		colaboradorAfastamentoDao.save(colaboradorAfastamento);

		assertEquals((Integer)1,
				colaboradorAfastamentoDao.getCount(empresa.getId(), afastamento.getId(), "", new Long[0], null, null));
	}
	
	public void testFindByColaborador()
	{
		Colaborador colaboradorFora = ColaboradorFactory.getEntity();
		colaboradorFora.setNome("Ana");
		colaboradorDao.save(colaboradorFora);
		
		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2.setNome("Josefa");
		colaboradorDao.save(colaborador2);

		HistoricoColaborador historicoColaboradorAtual = HistoricoColaboradorFactory.getEntity();
		historicoColaboradorAtual.setData(DateUtil.criarDataMesAno(1, 1, 2009));
		historicoColaboradorAtual.setColaborador(colaborador2);
		historicoColaboradorAtual = historicoColaboradorDao.save(historicoColaboradorAtual);

		Afastamento afastamento = AfastamentoFactory.getEntity();
		afastamento.setDescricao("Problemas pessoais");
		afastamentoDao.save(afastamento);

		ColaboradorAfastamento colaboradorAfastamento = ColaboradorAfastamentoFactory.getEntity();
		colaboradorAfastamento.setInicio(DateUtil.criarDataMesAno(5, 3, 2009));
		colaboradorAfastamento.setColaborador(colaborador2);
		colaboradorAfastamento.setAfastamento(afastamento);
		colaboradorAfastamentoDao.save(colaboradorAfastamento);
		
		ColaboradorAfastamento colaboradorAfastamentoFora = ColaboradorAfastamentoFactory.getEntity();
		colaboradorAfastamentoFora.setInicio(DateUtil.criarDataMesAno(5, 3, 2009));
		colaboradorAfastamentoFora.setColaborador(colaboradorFora);
		colaboradorAfastamentoFora.setAfastamento(afastamento);
		colaboradorAfastamentoDao.save(colaboradorAfastamentoFora);
		
		Collection<ColaboradorAfastamento> resultado = colaboradorAfastamentoDao.findByColaborador(colaborador2.getId());
		assertEquals(1, resultado.size());
	}
	
	public void testFindQtdAfastamentosPorMotivo()
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
		
		Afastamento pessoais = AfastamentoFactory.getEntity();
		pessoais.setDescricao("Problemas pessoais");
		afastamentoDao.save(pessoais);

		Afastamento virose = AfastamentoFactory.getEntity();
		virose.setDescricao("Virose");
		afastamentoDao.save(virose);

		ColaboradorAfastamento colabAfast1 = ColaboradorAfastamentoFactory.getEntity();
		colabAfast1.setInicio(DateUtil.criarDataMesAno(5, 11, 2011));
		colabAfast1.setColaborador(ana);
		colabAfast1.setAfastamento(pessoais);
		colaboradorAfastamentoDao.save(colabAfast1);
		
		ColaboradorAfastamento colabAfast2 = ColaboradorAfastamentoFactory.getEntity();
		colabAfast2.setInicio(DateUtil.criarDataMesAno(15, 11, 2011));
		colabAfast2.setColaborador(ana);
		colabAfast2.setAfastamento(virose);
		colaboradorAfastamentoDao.save(colabAfast2);

		ColaboradorAfastamento colabAfast3 = ColaboradorAfastamentoFactory.getEntity();
		colabAfast3.setInicio(DateUtil.criarDataMesAno(10, 11, 2011));
		colabAfast3.setColaborador(ana);
		colabAfast3.setAfastamento(virose);
		colaboradorAfastamentoDao.save(colabAfast3);

		ColaboradorAfastamento colabAfast4 = ColaboradorAfastamentoFactory.getEntity();
		colabAfast4.setInicio(DateUtil.criarDataMesAno(20, 11, 2011));
		colabAfast4.setColaborador(ana);
		colabAfast4.setAfastamento(pessoais);
		colaboradorAfastamentoDao.save(colabAfast4);

		ColaboradorAfastamento colabAfast5 = ColaboradorAfastamentoFactory.getEntity();
		colabAfast5.setInicio(DateUtil.criarDataMesAno(10, 11, 2011));
		colabAfast5.setColaborador(pedro);
		colabAfast5.setAfastamento(pessoais);
		colaboradorAfastamentoDao.save(colabAfast5);
		
		Collection<Afastamento> resultado = colaboradorAfastamentoDao.findQtdAfastamentosPorMotivo(empresa.getId(), DateUtil.criarDataMesAno(5, 11, 2011), DateUtil.criarDataMesAno(15, 11, 2011));
		assertEquals(2, resultado.size());
	}

	public void setAfastamentoDao(AfastamentoDao afastamentoDao)
	{
		this.afastamentoDao = afastamentoDao;
	}
	public void setColaboradorAfastamentoDao(ColaboradorAfastamentoDao colaboradorAfastamentoDao)
	{
		this.colaboradorAfastamentoDao = colaboradorAfastamentoDao;
	}
	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao)
	{
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
	}
	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}
}