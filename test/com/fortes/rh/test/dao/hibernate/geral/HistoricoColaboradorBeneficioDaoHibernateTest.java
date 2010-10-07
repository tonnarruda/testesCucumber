package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.BeneficioDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.HistoricoColaboradorBeneficioDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.HistoricoColaboradorBeneficio;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.BeneficioFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.util.DateUtil;

public class HistoricoColaboradorBeneficioDaoHibernateTest extends GenericDaoHibernateTest<HistoricoColaboradorBeneficio>
{
	private HistoricoColaboradorBeneficioDao historicoColaboradorBeneficioDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private BeneficioDao beneficioDao;
	private ColaboradorDao colaboradorDao;
	private EmpresaDao empresaDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private EstabelecimentoDao estabelecimentoDao;

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public HistoricoColaboradorBeneficio getEntity()
	{
    	Colaborador c = ColaboradorFactory.getEntity();
    	c = colaboradorDao.save(c);

    	Beneficio b1 = BeneficioFactory.getEntity();
    	b1 = beneficioDao.save(b1);

    	Beneficio b2 = BeneficioFactory.getEntity();
    	b2 = beneficioDao.save(b2);

    	HistoricoColaboradorBeneficio historico = new HistoricoColaboradorBeneficio();
    	historico.setId(1L);
    	historico.setColaborador(c);
    	Collection<Beneficio> beneficios = new ArrayList<Beneficio>();
    	beneficios.add(b1);
    	beneficios.add(b2);
    	historico.setBeneficios(beneficios);

		return historico;
	}

	@SuppressWarnings("unchecked")
	public void testFiltroRelatorio()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setEmpresa(empresa);
		areaOrganizacional.setNome("AO1");
		areaOrganizacional = areaOrganizacionalDao.save(areaOrganizacional);

		AreaOrganizacional areaOrganizacional2 = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional2.setEmpresa(empresa);
		areaOrganizacional2.setNome("AO2");
		areaOrganizacional2 = areaOrganizacionalDao.save(areaOrganizacional2);

		Beneficio b1 = new Beneficio();
		b1.setId(null);
		b1.setNome("B1");
		b1.setEmpresa(empresa);
		b1 = beneficioDao.save(b1);

		Beneficio b2 = new Beneficio();
		b2.setNome("B2");
		b2.setEmpresa(empresa);
		b2 = beneficioDao.save(b2);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		HistoricoColaborador hc0 = new HistoricoColaborador();
		hc0.setId(null);
		hc0.setData(DateUtil.criarDataMesAno(1, 3, 2006));
		hc0.setMotivo("p");
		hc0.setSalario(1D);
		hc0.setColaborador(colaborador);
		hc0.setAreaOrganizacional(areaOrganizacional);
		hc0.setEstabelecimento(estabelecimento);
		hc0 = historicoColaboradorDao.save(hc0);

		Colaborador colaborador2 = ColaboradorFactory.getEntity();
		colaborador2 = colaboradorDao.save(colaborador2);

		HistoricoColaborador hc1 = new HistoricoColaborador();
		hc1.setId(null);
		hc1.setData(DateUtil.criarDataMesAno(1, 4, 2006));
		hc1.setMotivo("p");
		hc1.setSalario(1D);
		hc1.setColaborador(colaborador2);
		hc1.setAreaOrganizacional(areaOrganizacional2);
		hc1.setEstabelecimento(estabelecimento);
		hc1 = historicoColaboradorDao.save(hc1);

		Collection<Beneficio> colB1 = new ArrayList<Beneficio>();
		colB1.add(b1);

		Collection<Beneficio> colB2 = new ArrayList<Beneficio>();
		colB1.add(b2);

		Collection<Beneficio> colB12 = new ArrayList<Beneficio>();
		colB12.add(b1);
		colB12.add(b2);

		HistoricoColaboradorBeneficio h0 = new HistoricoColaboradorBeneficio();
		h0.setColaborador(colaborador);
		h0.setData(DateUtil.criarDataMesAno(02, 12, 2005));
		h0.setDataAte(DateUtil.criarDataMesAno(01, 01, 2007));
		h0.setBeneficios(null);
		historicoColaboradorBeneficioDao.save(h0);

		HistoricoColaboradorBeneficio h1 = new HistoricoColaboradorBeneficio();
		h1.setColaborador(colaborador);
		h1.setData(DateUtil.criarDataMesAno(01, 01, 2007));
		h1.setBeneficios(colB12);
		h1.setDataAte(DateUtil.criarDataMesAno(04, 05, 2007));
		historicoColaboradorBeneficioDao.save(h1);

		HistoricoColaboradorBeneficio h2 = new HistoricoColaboradorBeneficio();
		h2.setColaborador(colaborador);
		h2.setData(DateUtil.criarDataMesAno(04, 05, 2007));
		h2.setBeneficios(colB2);
		historicoColaboradorBeneficioDao.save(h2);

		HistoricoColaboradorBeneficio h4 = new HistoricoColaboradorBeneficio();
		h4.setColaborador(colaborador2);
		h4.setData(DateUtil.criarDataMesAno(01, 04, 2007));
		h4.setBeneficios(colB12);
		historicoColaboradorBeneficioDao.save(h4);

		Collection<Long> areasId = new ArrayList<Long>();
		areasId.add(areaOrganizacional.getId());
		areasId.add(areaOrganizacional2.getId());

		Collection<Long> estabelecimentosId = new ArrayList<Long>();
		estabelecimentosId.add(estabelecimento.getId());

		LinkedHashMap filtro = new LinkedHashMap();
		filtro.put("dataIni", DateUtil.criarDataMesAno(01, 01, 2007));
		filtro.put("dataFim", DateUtil.criarDataMesAno(01, 06, 2007));
		filtro.put("areas", areasId);
		filtro.put("estabelecimentos", estabelecimentosId);
		filtro.put("colaborador", null);

		Collection<HistoricoColaboradorBeneficio> retorno = historicoColaboradorBeneficioDao.filtroRelatorioByAreasEstabelecimentos(filtro);

		assertEquals(5, retorno.size());

		filtro.put("dataIni", DateUtil.criarDataMesAno(01, 01, 2007));
		filtro.put("dataFim", DateUtil.criarDataMesAno(01, 06, 2007));
		filtro.put("areas", null);
		filtro.put("colaborador", colaborador2);

		retorno = historicoColaboradorBeneficioDao.filtroRelatorioByColaborador(filtro);

		assertEquals(2, retorno.size());
	}

	public void testGetHistoricoByColaboradorData()
	{
		Beneficio b1 = new Beneficio();
		b1.setId(null);
		b1.setNome("B1");
		b1 = beneficioDao.save(b1);

		Colaborador colaborador = ColaboradorFactory.getEntity();

		colaborador = colaboradorDao.save(colaborador);

		Collection<Beneficio> colB12 = new ArrayList<Beneficio>();
		colB12.add(b1);

		HistoricoColaboradorBeneficio h0 = new HistoricoColaboradorBeneficio();
		h0.setColaborador(colaborador);
		h0.setData(DateUtil.criarDataMesAno(02, 12, 2005));
		h0.setBeneficios(colB12);
		h0 = historicoColaboradorBeneficioDao.save(h0);

		HistoricoColaboradorBeneficio retorno = historicoColaboradorBeneficioDao.getHistoricoByColaboradorData(colaborador.getId(),h0.getData());

		assertEquals(h0.getId(), retorno.getId());

		retorno = historicoColaboradorBeneficioDao.getHistoricoByColaboradorData(6454651651L, h0.getData());

		assertEquals(null, retorno);

	}

	public void testGetDataUltimoHistorico()
	{
		Beneficio b1 = new Beneficio();
		b1.setId(null);
		b1.setNome("B1");
		b1 = beneficioDao.save(b1);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		Collection<Beneficio> colB12 = new ArrayList<Beneficio>();
		colB12.add(b1);

		HistoricoColaboradorBeneficio h0 = new HistoricoColaboradorBeneficio();
		h0.setColaborador(colaborador);
		h0.setData(DateUtil.criarDataMesAno(02, 12, 2005));
		h0.setBeneficios(colB12);
		h0 = historicoColaboradorBeneficioDao.save(h0);

		HistoricoColaboradorBeneficio h1 = new HistoricoColaboradorBeneficio();
		h1.setColaborador(colaborador);
		h1.setData(DateUtil.criarDataMesAno(02, 12, 2007));
		h1.setBeneficios(colB12);
		h1 = historicoColaboradorBeneficioDao.save(h1);

		Date data = historicoColaboradorBeneficioDao.getDataUltimoHistorico(colaborador.getId());

		assertEquals(h1.getData(), data);

	}

	public void testGetUltimoHistorico()
	{
		Beneficio b1 = new Beneficio();
		b1.setId(null);
		b1.setNome("B1");
		b1 = beneficioDao.save(b1);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		Collection<Beneficio> colB12 = new ArrayList<Beneficio>();
		colB12.add(b1);

		HistoricoColaboradorBeneficio h0 = new HistoricoColaboradorBeneficio();
		h0.setColaborador(colaborador);
		h0.setData(DateUtil.criarDataMesAno(02, 12, 2005));
		h0.setBeneficios(colB12);
		h0 = historicoColaboradorBeneficioDao.save(h0);

		HistoricoColaboradorBeneficio h1 = new HistoricoColaboradorBeneficio();
		h1.setColaborador(colaborador);
		h1.setData(DateUtil.criarDataMesAno(02, 12, 2007));
		h1.setBeneficios(colB12);
		h1 = historicoColaboradorBeneficioDao.save(h1);

		HistoricoColaboradorBeneficio h2 = new HistoricoColaboradorBeneficio();
		h2.setColaborador(colaborador);
		h2.setData(new Date());
		h2.setBeneficios(colB12);
		h2 = historicoColaboradorBeneficioDao.save(h2);

		HistoricoColaboradorBeneficio retorno = historicoColaboradorBeneficioDao.getUltimoHistorico(colaborador.getId());

		assertEquals(h2.getId(), retorno.getId());
	}

	public void testUpdateDataAteUltimoHistorico()
	{
		Beneficio b1 = new Beneficio();
		b1.setId(null);
		b1.setNome("B1");
		b1 = beneficioDao.save(b1);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		Collection<Beneficio> beneficio = new ArrayList<Beneficio>();
		beneficio.add(b1);

		HistoricoColaboradorBeneficio historico = new HistoricoColaboradorBeneficio();
		historico.setColaborador(colaborador);
		historico.setData(new Date());
		historico.setDataAte(null);
		historico.setBeneficios(beneficio);
		historico = historicoColaboradorBeneficioDao.save(historico);

		Date dataAte = DateUtil.criarDataMesAno(01, 02, 2008);

		historicoColaboradorBeneficioDao.updateDataAteUltimoHistorico(historico.getId(),dataAte);

		@SuppressWarnings("unused")
		HistoricoColaboradorBeneficio retorno = historicoColaboradorBeneficioDao.findById(historico.getId());

//		assertEquals(dataAte, retorno.getDataAte());
	}

	public GenericDao<HistoricoColaboradorBeneficio> getGenericDao()
	{
		return historicoColaboradorBeneficioDao;
	}

	public void setHistoricoColaboradorBeneficioDao(HistoricoColaboradorBeneficioDao historicoColaboradorBeneficioDao)
	{
		this.historicoColaboradorBeneficioDao = historicoColaboradorBeneficioDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setBeneficioDao(BeneficioDao beneficioDao)
	{
		this.beneficioDao = beneficioDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao)
	{
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
	}

}