package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.ExtintorDao;
import com.fortes.rh.dao.sesmt.ExtintorInspecaoDao;
import com.fortes.rh.dao.sesmt.ExtintorInspecaoItemDao;
import com.fortes.rh.dao.sesmt.HistoricoExtintorDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.model.sesmt.ExtintorInspecaoItem;
import com.fortes.rh.model.sesmt.HistoricoExtintor;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorInspecaoFactory;
import com.fortes.rh.test.factory.sesmt.HistoricoExtintorFactory;
import com.fortes.rh.util.DateUtil;

import dbunit.DbUnitManager;

public class ExtintorInspecaoDaoHibernateTest extends GenericDaoHibernateTest<ExtintorInspecao>
{
	private ExtintorInspecaoDao extintorInspecaoDao;
	private ExtintorInspecaoItemDao extintorInspecaoItemDao;
	private HistoricoExtintorDao historicoExtintorDao;
	private ExtintorDao extintorDao;
	private EmpresaDao empresaDao;
	private EstabelecimentoDao estabelecimentoDao;
	
	
	private static final String dataSet = "test/dbunit/dataset/ExtintorInspecaoDaoHibernateTest.xml";
	
	DbUnitManager dbUnitManager;

	public void setDbUnitManager(DbUnitManager dbUnitManager) {
		this.dbUnitManager = dbUnitManager;
	}

	@Override
	public ExtintorInspecao getEntity()
	{
		ExtintorInspecao extintorInspecao = new ExtintorInspecao();
		return extintorInspecao;
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		dbUnitManager.deleteAll(dataSet);
	}
	
	@Override
	public GenericDao<ExtintorInspecao> getGenericDao()
	{
		return extintorInspecaoDao;
	}

	public void setExtintorInspecaoDao(ExtintorInspecaoDao extintorInspecaoDao)
	{
		this.extintorInspecaoDao = extintorInspecaoDao;
	}

	public void testGetCount()
	{
		
		Date hoje = new Date();
		Calendar ontem = Calendar.getInstance();
		ontem.add(Calendar.DAY_OF_MONTH, -1);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);

		Extintor extintor = ExtintorFactory.getEntity();
		extintor.setEmpresa(empresa);
		extintorDao.save(extintor);

		HistoricoExtintor hist1 = HistoricoExtintorFactory.getEntity();
		hist1.setEstabelecimento(estabelecimento);
		hist1.setLocalizacao("Gaas");
		hist1.setExtintor(extintor);
		historicoExtintorDao.save(hist1);
		
		Extintor extintor2 = ExtintorFactory.getEntity();
		extintor2.setEmpresa(empresa);
		extintorDao.save(extintor2);
		
		HistoricoExtintor hist2 = HistoricoExtintorFactory.getEntity();
		hist2.setEstabelecimento(estabelecimento);
		hist2.setLocalizacao("Local teste");
		hist2.setExtintor(extintor2);
		historicoExtintorDao.save(hist2);
		ExtintorInspecaoItem extintorInspecaoItem = new ExtintorInspecaoItem();
		extintorInspecaoItem.setDescricao("teste");
		extintorInspecaoItemDao.save(extintorInspecaoItem);

		Collection<ExtintorInspecaoItem> extintorInspecaoItems = new ArrayList<ExtintorInspecaoItem>();
		extintorInspecaoItems.add(extintorInspecaoItem);
		
		ExtintorInspecao extintorInspecao = ExtintorInspecaoFactory.getEntity();
		extintorInspecao.setExtintor(extintor);
		extintorInspecao.setData(hoje);
		extintorInspecao.setItens(extintorInspecaoItems);
		extintorInspecaoDao.save(extintorInspecao);

		ExtintorInspecao extintorInspecaoFora = ExtintorInspecaoFactory.getEntity();
		extintorInspecaoFora.setExtintor(extintor);
		extintorInspecaoFora.setData(ontem.getTime());
		extintorInspecaoFora.setItens(extintorInspecaoItems);
		extintorInspecaoDao.save(extintorInspecaoFora);

		assertEquals(Integer.valueOf(1),
					extintorInspecaoDao.getCount(empresa.getId(), estabelecimento.getId(), extintor.getId(), new Date(), null, '0'));
	}

	public void testFindAllSelect()
	{
		Date hoje = new Date();
		Calendar ontem = Calendar.getInstance();
		ontem.add(Calendar.DAY_OF_MONTH, -1);
		Calendar amanha = Calendar.getInstance();
		amanha.add(Calendar.DAY_OF_MONTH, +1);

		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);

		Extintor extintor = ExtintorFactory.getEntity();
		extintor.setEmpresa(empresa);
		extintorDao.save(extintor);

		HistoricoExtintor hist1 = HistoricoExtintorFactory.getEntity();
		hist1.setEstabelecimento(estabelecimento);
		hist1.setLocalizacao("Gaas");
		hist1.setExtintor(extintor);
		historicoExtintorDao.save(hist1);
		
		Extintor extintor2 = ExtintorFactory.getEntity();
		extintor2.setEmpresa(empresa);
		extintorDao.save(extintor2);
		
		HistoricoExtintor hist2 = HistoricoExtintorFactory.getEntity();
		hist2.setEstabelecimento(estabelecimento);
		hist2.setLocalizacao("Local teste");
		hist2.setExtintor(extintor2);
		historicoExtintorDao.save(hist2);

		ExtintorInspecaoItem extintorInspecaoItem = new ExtintorInspecaoItem();
		extintorInspecaoItem.setDescricao("teste");
		extintorInspecaoItemDao.save(extintorInspecaoItem);

		Collection<ExtintorInspecaoItem> extintorInspecaoItems = new ArrayList<ExtintorInspecaoItem>();
		extintorInspecaoItems.add(extintorInspecaoItem);

		ExtintorInspecao extintorInspecao = ExtintorInspecaoFactory.getEntity();
		extintorInspecao.setExtintor(extintor);
		extintorInspecao.setData(hoje);
		extintorInspecao.setItens(extintorInspecaoItems);
		extintorInspecaoDao.save(extintorInspecao);

		ExtintorInspecao extintorInspecao2 = ExtintorInspecaoFactory.getEntity();
		extintorInspecao2.setExtintor(extintor);
		extintorInspecao2.setData(ontem.getTime());
		extintorInspecaoDao.save(extintorInspecao2);

		ExtintorInspecao extintorInspecaoFora = ExtintorInspecaoFactory.getEntity();
		extintorInspecaoFora.setExtintor(extintor2);
		extintorInspecaoFora.setData(ontem.getTime());
		extintorInspecaoFora.setItens(extintorInspecaoItems);
		extintorInspecaoDao.save(extintorInspecaoFora);

		assertEquals(1, extintorInspecaoDao.
				findAllSelect(1, 15, empresa.getId(), estabelecimento.getId(), extintor.getId(), ontem.getTime(), amanha.getTime(), '2', null).size());
	
		assertEquals(1, extintorInspecaoDao.
				findAllSelect(1, 15, empresa.getId(), estabelecimento.getId(), extintor.getId(), ontem.getTime(), amanha.getTime(), '1', null).size());

		assertEquals(2, extintorInspecaoDao.
				findAllSelect(1, 15, empresa.getId(), estabelecimento.getId(), extintor.getId(), ontem.getTime(), amanha.getTime(), '0', null).size());
		
		assertEquals(1, extintorInspecaoDao.
				findAllSelect(1, 15, empresa.getId(), estabelecimento.getId(), extintor2.getId(), ontem.getTime(), amanha.getTime(), '0', "Local").size());
	}

	public void testFindEmpresasResponsaveisDistinct()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);

		Extintor extintor1 = ExtintorFactory.getEntity();
		extintor1.setEmpresa(empresa);
		extintorDao.save(extintor1);

		Extintor extintor2 = ExtintorFactory.getEntity();
		extintor2.setEmpresa(empresa);
		extintorDao.save(extintor2);

		Extintor extintor3 = ExtintorFactory.getEntity();
		extintor3.setEmpresa(empresa);
		extintorDao.save(extintor3);

		ExtintorInspecao extintorInspecao = ExtintorInspecaoFactory.getEntity();
		extintorInspecao.setEmpresaResponsavel("Gaas");
		extintorInspecao.setExtintor(extintor1);
		extintorInspecaoDao.save(extintorInspecao);

		ExtintorInspecao extintorInspecao2 = ExtintorInspecaoFactory.getEntity();
		extintorInspecao2.setEmpresaResponsavel("Aextinct");
		extintorInspecao2.setExtintor(extintor2);
		extintorInspecaoDao.save(extintorInspecao2);

		ExtintorInspecao extintorInspecao3 = ExtintorInspecaoFactory.getEntity();
		extintorInspecao3.setEmpresaResponsavel("Aextinct");
		extintorInspecao3.setExtintor(extintor3);
		extintorInspecaoDao.save(extintorInspecao3);

		ExtintorInspecaoItem extintorInspecaoItem = new ExtintorInspecaoItem();
		extintorInspecaoItem.setDescricao("teste");
		extintorInspecaoItemDao.save(extintorInspecaoItem);
		
		Collection<String> empresas = extintorInspecaoDao.findEmpresasResponsaveisDistinct(empresa.getId());
		assertEquals(2, empresas.size());
		assertEquals(extintorInspecao2.getEmpresaResponsavel(), empresas.toArray()[0]);
		assertEquals(extintorInspecao.getEmpresaResponsavel(), empresas.toArray()[1]);
	}

	public void testFindInspecoesVencidas()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Extintor extintor = ExtintorFactory.getEntity();
		extintor.setAtivo(true);
		extintor.setPeriodoMaxInspecao(2);
		extintorDao.save(extintor);
		
		HistoricoExtintor hist1 = HistoricoExtintorFactory.getEntity();
		hist1.setEstabelecimento(estabelecimento);
		hist1.setLocalizacao("Gaas");
		hist1.setExtintor(extintor);
		historicoExtintorDao.save(hist1);
		
		ExtintorInspecao extintorInspecao = ExtintorInspecaoFactory.getEntity();
		extintorInspecao.setExtintor(extintor);
		Date dataDaInspecao = DateUtil.criarDataMesAno(10, 04, 2009);
		extintorInspecao.setData(dataDaInspecao);
		extintorInspecaoDao.save(extintorInspecao);
		
		extintorInspecaoDao.getHibernateTemplateByGenericDao().flush();
		
		Collection<ExtintorInspecao> extintorInspecaos = extintorInspecaoDao.findInspecoesVencidas(estabelecimento.getId(), DateUtil.criarDataMesAno(1, 10, 2009));
		assertEquals(1, extintorInspecaos.size());

		ExtintorInspecao retorno = (ExtintorInspecao) extintorInspecaos.toArray()[0];
		assertEquals(DateUtil.formataDiaMesAno(dataDaInspecao), DateUtil.formataDiaMesAno(retorno.getData()));
		assertEquals(DateUtil.formataDiaMesAno(DateUtil.criarDataMesAno(10, 06, 2009)), DateUtil.formataDiaMesAno(retorno.getVencimento()));
	}
	
	public void testFindInspecoesVencidasVerificaDatas()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Extintor extintorVencido = ExtintorFactory.getEntity();
		extintorVencido.setAtivo(true);
		extintorVencido.setPeriodoMaxInspecao(2);
		extintorDao.save(extintorVencido);
		
		HistoricoExtintor hist1 = HistoricoExtintorFactory.getEntity();
		hist1.setEstabelecimento(estabelecimento);
		hist1.setExtintor(extintorVencido);
		historicoExtintorDao.save(hist1);
		
		Extintor extintorVencidoDesativado = ExtintorFactory.getEntity();
		extintorVencidoDesativado.setAtivo(false);
		extintorVencidoDesativado.setPeriodoMaxInspecao(2);
		extintorDao.save(extintorVencidoDesativado);
		
		HistoricoExtintor hist2 = HistoricoExtintorFactory.getEntity();
		hist2.setEstabelecimento(estabelecimento);
		hist2.setExtintor(extintorVencidoDesativado);
		historicoExtintorDao.save(hist2);
		
		Extintor extintorEmDia = ExtintorFactory.getEntity();
		extintorEmDia.setAtivo(true);
		extintorEmDia.setPeriodoMaxInspecao(2);
		extintorDao.save(extintorEmDia);
		
		HistoricoExtintor hist3 = HistoricoExtintorFactory.getEntity();
		hist3.setEstabelecimento(estabelecimento);
		hist3.setExtintor(extintorEmDia);
		historicoExtintorDao.save(hist3);
		
		ExtintorInspecao extintorInspecaoVencida = ExtintorInspecaoFactory.getEntity();
		extintorInspecaoVencida.setExtintor(extintorVencido);
		extintorInspecaoVencida.setData(DateUtil.criarDataMesAno(2, 8, 2009));//fronteira do vencimento
		extintorInspecaoDao.save(extintorInspecaoVencida);
		
		ExtintorInspecao extintorInspecaoVencidaMaisAntiga = ExtintorInspecaoFactory.getEntity();
		extintorInspecaoVencidaMaisAntiga.setExtintor(extintorVencido);
		extintorInspecaoVencidaMaisAntiga.setData(DateUtil.criarDataMesAno(2, 1, 2009));
		extintorInspecaoDao.save(extintorInspecaoVencidaMaisAntiga);
		
		ExtintorInspecao extintorInspecaoVencidaDesativado = ExtintorInspecaoFactory.getEntity();
		extintorInspecaoVencidaDesativado.setExtintor(extintorVencidoDesativado);
		extintorInspecaoVencidaDesativado.setData(DateUtil.criarDataMesAno(10, 6, 2009));
		extintorInspecaoDao.save(extintorInspecaoVencidaDesativado);
		
		ExtintorInspecao extintorInspecaoEmDia = ExtintorInspecaoFactory.getEntity();
		extintorInspecaoEmDia.setExtintor(extintorEmDia);
		extintorInspecaoEmDia.setData(DateUtil.criarDataMesAno(3, 8, 2009));//fronteira do vencimento
		extintorInspecaoDao.save(extintorInspecaoEmDia);
		
		extintorInspecaoDao.getHibernateTemplateByGenericDao().flush();
		
		Collection<ExtintorInspecao> extintorInspecaos = extintorInspecaoDao.findInspecoesVencidas(estabelecimento.getId(), DateUtil.criarDataMesAno(2, 10, 2009));
		assertEquals(1, extintorInspecaos.size());
		assertEquals(extintorInspecaoVencida, extintorInspecaos.toArray()[0]);
	}

	public void testFindByIdProjection()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Extintor extintor = ExtintorFactory.getEntity();
		extintor.setAtivo(true);
		extintorDao.save(extintor);
		
		Date data = DateUtil.criarDataMesAno(10, 11, 2011);
		
		HistoricoExtintor historico = HistoricoExtintorFactory.getEntity();
		historico.setEstabelecimento(estabelecimento);
		historico.setExtintor(extintor);
		historico.setLocalizacao("teste");
		historico.setData(data);
		historicoExtintorDao.save(historico);

		ExtintorInspecaoItem item1 = new ExtintorInspecaoItem();
		item1.setDescricao("teste 1");
		extintorInspecaoItemDao.save(item1);

		ExtintorInspecaoItem item2 = new ExtintorInspecaoItem();
		item2.setDescricao("teste 2");
		extintorInspecaoItemDao.save(item2);
		
		Collection<ExtintorInspecaoItem> itens = Arrays.asList(item1, item2);
		
		ExtintorInspecao inspecao = ExtintorInspecaoFactory.getEntity();
		inspecao.setExtintor(extintor);
		inspecao.setItens(itens);
		extintorInspecaoDao.save(inspecao);
		
		ExtintorInspecao extintorInspecao = extintorInspecaoDao.findByIdProjection(inspecao.getId());
		
		assertEquals(2, extintorInspecao.getItens().size());
		assertNotNull(extintorInspecao.getExtintor().getUltimoHistorico());
	}
	
	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
	}

	public void setExtintorDao(ExtintorDao extintorDao)
	{
		this.extintorDao = extintorDao;
	}

	public void setExtintorInspecaoItemDao(ExtintorInspecaoItemDao extintorInspecaoItemDao) {
		this.extintorInspecaoItemDao = extintorInspecaoItemDao;
	}

	public void setHistoricoExtintorDao(HistoricoExtintorDao historicoExtintorDao) {
		this.historicoExtintorDao = historicoExtintorDao;
	}
}
