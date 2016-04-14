package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorOcorrenciaDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.dao.geral.OcorrenciaDao;
import com.fortes.rh.dao.geral.ProvidenciaDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.SituacaoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.model.geral.Providencia;
import com.fortes.rh.model.geral.relatorio.Absenteismo;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.ColaboradorOcorrenciaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.test.factory.geral.ProvidenciaFactory;
import com.fortes.rh.util.DateUtil;

public class ColaboradorOcorrenciaDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorOcorrencia>
{
	private ColaboradorOcorrenciaDao colaboradorOcorrenciaDao;
	private ColaboradorDao colaboradorDao;
	private EmpresaDao empresaDao;
	private OcorrenciaDao ocorrenciaDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private EstabelecimentoDao estabelecimentoDao;
	private AreaOrganizacionalDao areaOrganizacionalDao;
	private GrupoACDao grupoACDao;
	private ProvidenciaDao providenciaDao;

	public ColaboradorOcorrencia getEntity()
	{
		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia.setPontuacao(10);
		ocorrencia.setDescricao("bla");
		ocorrencia.setEmpresa(empresa);
		ocorrencia = ocorrenciaDao.save(ocorrencia);

		colaboradorOcorrencia.setId(null);
		colaboradorOcorrencia.setColaborador(colaborador);
		colaboradorOcorrencia.setDataIni(new Date());
		colaboradorOcorrencia.setObservacao("as");
		colaboradorOcorrencia.setOcorrencia(ocorrencia);
		
		return colaboradorOcorrencia;
	}

	public void testFindByColaborador()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia = ocorrenciaDao.save(ocorrencia);

		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity();
		colaboradorOcorrencia.setColaborador(colaborador);
		colaboradorOcorrencia.setOcorrencia(ocorrencia);
		colaboradorOcorrencia = colaboradorOcorrenciaDao.save(colaboradorOcorrencia);

		Collection<ColaboradorOcorrencia> retorno = colaboradorOcorrenciaDao.findByColaborador(colaborador.getId());

		assertEquals(1, retorno.size());
	}
	
	public void testDeleteByOcorrencia()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrenciaDao.save(ocorrencia);
		
		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity();
		colaboradorOcorrencia.setColaborador(colaborador);
		colaboradorOcorrencia.setOcorrencia(ocorrencia);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia);
		
		Collection<ColaboradorOcorrencia> retorno = colaboradorOcorrenciaDao.findByColaborador(colaborador.getId());
		assertEquals(1, retorno.size());
		
		try {
			colaboradorOcorrenciaDao.deleteByOcorrencia(new Long[]{ocorrencia.getId()});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		retorno = colaboradorOcorrenciaDao.findByColaborador(colaborador.getId());
		assertEquals(0, retorno.size());
	}
	
	public void testFindColaboradorOcorrenciaSemDetalhe()
	{
		Date data1 = DateUtil.criarDataMesAno(1, 1, 2011);
		Date data2 = DateUtil.criarDataMesAno(1, 1, 2012);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Ocorrencia falta = OcorrenciaFactory.getEntity(empresa, "Falta", 5);
		ocorrenciaDao.save(falta);
		
		Ocorrencia acidente = OcorrenciaFactory.getEntity(empresa, "Acidente", 7);
		ocorrenciaDao.save(acidente);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Colaborador joao = ColaboradorFactory.getEntity(null, "João");
		colaboradorDao.save(joao);
		
		HistoricoColaborador historicoJoao = HistoricoColaboradorFactory.getEntity(joao, DateUtil.criarDataMesAno(15, 12, 2011), null, estabelecimento, areaOrganizacional, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoJoao);
		
		Providencia providencia = ProvidenciaFactory.getEntity("Não faltar mais");
		providenciaDao.save(providencia);
		
		saveColaboradorOcorrencia(joao, falta, providencia, DateUtil.criarDataMesAno(17, 12, 2011), DateUtil.criarDataMesAno(19, 12, 2011));
		saveColaboradorOcorrencia(joao, acidente, DateUtil.criarDataMesAno(20, 12, 2011), DateUtil.criarDataMesAno(22, 12, 2011));
		
		Collection<ColaboradorOcorrencia> colaboradorOcorrencias = colaboradorOcorrenciaDao.findColaboradorOcorrencia(Arrays.asList(falta.getId(), acidente.getId()), Arrays.asList(joao.getId()), data1, data2, Arrays.asList(empresa.getId()), null, null, false, 'C', SituacaoColaborador.ATIVO);
		assertEquals(1, colaboradorOcorrencias.size());
		assertEquals(12, ((ColaboradorOcorrencia)colaboradorOcorrencias.toArray()[0]).getOcorrencia().getPontuacao());
	}

	public void testFindColaboradorOcorrenciaComDetalhe()
	{
		Date dataFiltroInicio = DateUtil.criarDataMesAno(3, 3, 2015);
		Date dataFiltroFim = DateUtil.criarDataMesAno(5, 5, 20115);
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacionalDao.save(areaOrganizacional);
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Colaborador joao = ColaboradorFactory.getEntity(null, "João");
		colaboradorDao.save(joao);
		
		HistoricoColaborador historicoJoao = HistoricoColaboradorFactory.getEntity(joao, DateUtil.criarDataMesAno(15, 12, 2011), null, estabelecimento, areaOrganizacional, null, null, StatusRetornoAC.CONFIRMADO);
		historicoColaboradorDao.save(historicoJoao);
		
		Ocorrencia falta = OcorrenciaFactory.getEntity(empresa, "Falta", 5);
		ocorrenciaDao.save(falta);

		Providencia providencia = ProvidenciaFactory.getEntity("Não faltar mais");
		providenciaDao.save(providencia);
		
		saveColaboradorOcorrencia(joao, falta, providencia, DateUtil.criarDataMesAno(24, 02, 2015), DateUtil.criarDataMesAno(9, 03, 2015));
		
		Collection<ColaboradorOcorrencia> colaboradorOcorrenciasDetalhados = colaboradorOcorrenciaDao.findColaboradorOcorrencia(new ArrayList<Long>() , Arrays.asList(joao.getId()), dataFiltroInicio, dataFiltroFim, Arrays.asList(empresa.getId()), null, null, true, 'C', SituacaoColaborador.TODOS);
		assertEquals(1, colaboradorOcorrenciasDetalhados.size());
		assertEquals("Não faltar mais", ((ColaboradorOcorrencia)colaboradorOcorrenciasDetalhados.toArray()[0]).getProvidencia().getDescricao());
	}

	private ColaboradorOcorrencia saveColaboradorOcorrencia(Colaborador colaborador, Ocorrencia ocorrencia, Date dataInicio, Date dataFim) {
		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity(colaborador, ocorrencia, dataInicio, dataFim);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia);
		return colaboradorOcorrencia;
	}

	private ColaboradorOcorrencia saveColaboradorOcorrencia(Colaborador colaborador, Ocorrencia ocorrencia, Providencia providencia, Date dataInicio, Date dataFim) {
		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity(colaborador, ocorrencia, providencia, dataInicio, dataFim);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia);
		return colaboradorOcorrencia;
	}
	
	public void testFiltrar()
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia = ocorrenciaDao.save(ocorrencia);

		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity();
		colaboradorOcorrencia.setColaborador(colaborador);
		colaboradorOcorrencia.setOcorrencia(ocorrencia);
		colaboradorOcorrencia.setDataIni(DateUtil.criarDataMesAno(01, 01, 2009));
		colaboradorOcorrencia.setDataFim(DateUtil.criarDataMesAno(01, 03, 2009));
		colaboradorOcorrencia = colaboradorOcorrenciaDao.save(colaboradorOcorrencia);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento = estabelecimentoDao.save(estabelecimento);

		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setData(new Date());
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador = historicoColaboradorDao.save(historicoColaborador);

		Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
		historicoColaboradors.add(historicoColaborador);

		colaborador.setHistoricoColaboradors(historicoColaboradors);

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("dataIni", DateUtil.criarDataMesAno(01, 01, 2009));
		parametros.put("dataFim", DateUtil.criarDataMesAno(01, 03, 2009));
		Long[] ocorrenciaIds = new Long[]{ocorrencia.getId()};
		Long[] colaboradorIds = new Long[]{colaborador.getId()};
		Long[] estabelecimentoIds = new Long[]{estabelecimento.getId()};

		Collection<ColaboradorOcorrencia> retorno = colaboradorOcorrenciaDao.filtrar(ocorrenciaIds, colaboradorIds, estabelecimentoIds, parametros);

		assertEquals(1, retorno.size());
	}

	public void testFindProjection()
	{
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia = ocorrenciaDao.save(ocorrencia);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador = colaboradorDao.save(colaborador);

		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();
		colaboradorOcorrencia.setColaborador(colaborador);
		colaboradorOcorrencia.setOcorrencia(ocorrencia);

		colaboradorOcorrencia = colaboradorOcorrenciaDao.save(colaboradorOcorrencia);

		Collection<ColaboradorOcorrencia> colaboradorOcorrencias = colaboradorOcorrenciaDao.findProjection(1, 15, colaborador.getId());

		assertEquals(1, colaboradorOcorrencias.size());
	}

	public void testFindByIdProjection()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("1234");
		empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setEmpresa(empresa);
		colaborador.setCodigoAC("4561");
		colaboradorDao.save(colaborador);

		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia.setCodigoAC("789");
		ocorrenciaDao.save(ocorrencia);
		
		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();
		colaboradorOcorrencia.setDataIni(new Date());
		colaboradorOcorrencia.setObservacao("observacao");
		colaboradorOcorrencia.setColaborador(colaborador);
		colaboradorOcorrencia.setOcorrencia(ocorrencia);

		colaboradorOcorrenciaDao.save(colaboradorOcorrencia);

		ColaboradorOcorrencia colaboradorOcorrenciaRetorno = colaboradorOcorrenciaDao.findByIdProjection(colaboradorOcorrencia.getId());

		assertEquals(colaboradorOcorrencia, colaboradorOcorrenciaRetorno);
		assertEquals(colaboradorOcorrencia.getId(), colaboradorOcorrenciaRetorno.getId());
		assertTrue(DateUtil.equals(colaboradorOcorrencia.getDataIni(), colaboradorOcorrenciaRetorno.getDataIni()));
		assertEquals(colaboradorOcorrencia.getObservacao(), colaboradorOcorrenciaRetorno.getObservacao());
		assertEquals(empresa.getId(), colaboradorOcorrenciaRetorno.getColaborador().getEmpresa().getId());
		assertEquals(empresa.getCodigoAC(), colaboradorOcorrenciaRetorno.getColaborador().getEmpresa().getCodigoAC());
		assertEquals(colaborador.getId(), colaboradorOcorrenciaRetorno.getColaborador().getId());
		assertEquals(colaborador.getCodigoAC(), colaboradorOcorrenciaRetorno.getColaborador().getCodigoAC());
		assertEquals(ocorrencia.getId(), colaboradorOcorrenciaRetorno.getOcorrencia().getId());
		assertEquals(ocorrencia.getCodigoAC(), colaboradorOcorrenciaRetorno.getOcorrencia().getCodigoAC());
	}

	public void testFindByDadosAC()
	{
		GrupoAC grupoAC = new GrupoAC("XXX", "desc");
		grupoACDao.save(grupoAC);
		
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa.setCodigoAC("3");
		empresa.setGrupoAC(grupoAC.getCodigo());
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setCodigoAC("123");
		colaboradorDao.save(colaborador);

		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia.setEmpresa(empresa);
		ocorrencia.setCodigoAC("456");
		ocorrenciaDao.save(ocorrencia);

		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();
		colaboradorOcorrencia.setDataIni(new Date());
		colaboradorOcorrencia.setColaborador(colaborador);
		colaboradorOcorrencia.setOcorrencia(ocorrencia);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia);

		assertEquals(colaboradorOcorrencia, colaboradorOcorrenciaDao.findByDadosAC(colaboradorOcorrencia.getDataIni(), "456", "123", "3", "XXX"));
	}
	
	public void testCountFaltasByPeriodo()
	{
		Collection<Long> areasIds = Arrays.asList(1L);
		Collection<Long> estabelecimentoIds = Arrays.asList(1L, 2L);
		
		@SuppressWarnings("unused")
		Collection<Absenteismo> absenteismo = colaboradorOcorrenciaDao.countFaltasByPeriodo(DateUtil.criarDataMesAno(27, 01, 2011), DateUtil.criarDataMesAno(28, 05, 2011), Arrays.asList(EmpresaFactory.getEmpresa(1L).getId()), estabelecimentoIds, areasIds, null, null);
		assertTrue(true);//testa apenas se a consulta roda, é um sql e o hibernate roda o teste em outra transação
	}
	
	public void testFindByFiltros()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);
		
		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia.setEmpresa(empresa);
		ocorrenciaDao.save(ocorrencia);
		
		Providencia providencia = ProvidenciaFactory.getEntity();
		providencia.setDescricao("Providencia");
		providenciaDao.save(providencia);
		
		ColaboradorOcorrencia colaboradorOcorrencia = ColaboradorOcorrenciaFactory.getEntity();
		colaboradorOcorrencia.setColaborador(colaborador);
		colaboradorOcorrencia.setOcorrencia(ocorrencia);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia);

		ColaboradorOcorrencia colaboradorOcorrencia2 = ColaboradorOcorrenciaFactory.getEntity();
		colaboradorOcorrencia2.setColaborador(colaborador);
		colaboradorOcorrencia2.setOcorrencia(ocorrencia);
		colaboradorOcorrencia2.setProvidencia(providencia);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia2);
		
		//com e sem providencia
		Collection<ColaboradorOcorrencia> retorno = colaboradorOcorrenciaDao.findByFiltros(0, 15, null, null, null, null, empresa.getId());
		assertEquals(2, retorno.size());

		//sem providencia
		boolean comProvidencia = false;
		retorno = colaboradorOcorrenciaDao.findByFiltros(0, 15, null, null, comProvidencia, null, empresa.getId());
		assertEquals(1, retorno.size());
		
		//com providencia
		comProvidencia = true;
		retorno = colaboradorOcorrenciaDao.findByFiltros(0, 15, null, null, comProvidencia, null, empresa.getId());
		assertEquals(1, retorno.size());
	}

	public GenericDao<ColaboradorOcorrencia> getGenericDao()
	{
		return colaboradorOcorrenciaDao;
	}

	public void setColaboradorOcorrenciaDao(ColaboradorOcorrenciaDao colaboradorOcorrenciaDao)
	{
		this.colaboradorOcorrenciaDao = colaboradorOcorrenciaDao;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setOcorrenciaDao(OcorrenciaDao ocorrenciaDao)
	{
		this.ocorrenciaDao = ocorrenciaDao;
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

	public void setGrupoACDao(GrupoACDao grupoACDao) {
		this.grupoACDao = grupoACDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao) {
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setProvidenciaDao(ProvidenciaDao providenciaDao) {
		this.providenciaDao = providenciaDao;
	}

}