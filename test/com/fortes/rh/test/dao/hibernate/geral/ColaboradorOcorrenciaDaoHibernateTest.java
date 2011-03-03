package com.fortes.rh.test.dao.hibernate.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.ColaboradorOcorrenciaDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.geral.GrupoACDao;
import com.fortes.rh.dao.geral.OcorrenciaDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.geral.Ocorrencia;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.ColaboradorOcorrenciaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.OcorrenciaFactory;
import com.fortes.rh.util.DateUtil;

public class ColaboradorOcorrenciaDaoHibernateTest extends GenericDaoHibernateTest<ColaboradorOcorrencia>
{
	private ColaboradorOcorrenciaDao colaboradorOcorrenciaDao;
	private ColaboradorDao colaboradorDao;
	private EmpresaDao empresaDao;
	private OcorrenciaDao ocorrenciaDao;
	private HistoricoColaboradorDao historicoColaboradorDao;
	private EstabelecimentoDao estabelecimentoDao;
	private GrupoACDao grupoACDao;

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

	public void testVerifyExistsMesmaData()
	{

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresa = empresaDao.save(empresa);

		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaboradorDao.save(colaborador);

		Ocorrencia ocorrencia = OcorrenciaFactory.getEntity();
		ocorrencia.setEmpresa(empresa);
		ocorrenciaDao.save(ocorrencia);
		Date hoje = new Date();

		ColaboradorOcorrencia colaboradorOcorrencia = new ColaboradorOcorrencia();
		colaboradorOcorrencia.setDataIni(hoje);
		colaboradorOcorrencia.setColaborador(colaborador);
		colaboradorOcorrencia.setOcorrencia(ocorrencia);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia);

		assertFalse(colaboradorOcorrenciaDao.verifyExistsMesmaData(colaboradorOcorrencia.getId(), colaborador.getId(), ocorrencia.getId(), empresa.getId(), hoje));

		ColaboradorOcorrencia colaboradorOcorrencia2 = new ColaboradorOcorrencia();
		colaboradorOcorrencia2.setDataIni(hoje);
		colaboradorOcorrencia2.setColaborador(colaborador);
		colaboradorOcorrencia2.setOcorrencia(ocorrencia);
		colaboradorOcorrenciaDao.save(colaboradorOcorrencia2);

		assertTrue(colaboradorOcorrenciaDao.verifyExistsMesmaData(colaboradorOcorrencia.getId(), colaborador.getId(), ocorrencia.getId(), empresa.getId(), hoje));

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

}