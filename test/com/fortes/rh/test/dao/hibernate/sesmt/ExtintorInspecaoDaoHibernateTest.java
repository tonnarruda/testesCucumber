package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.ExtintorDao;
import com.fortes.rh.dao.sesmt.ExtintorInspecaoDao;
import com.fortes.rh.dao.sesmt.ExtintorInspecaoItemDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorInspecao;
import com.fortes.rh.model.sesmt.ExtintorInspecaoItem;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorInspecaoFactory;
import com.fortes.rh.util.DateUtil;

public class ExtintorInspecaoDaoHibernateTest extends GenericDaoHibernateTest<ExtintorInspecao>
{
	private ExtintorInspecaoDao extintorInspecaoDao;
	private ExtintorInspecaoItemDao extintorInspecaoItemDao;
	private ExtintorDao extintorDao;
	private EmpresaDao empresaDao;
	private EstabelecimentoDao estabelecimentoDao;
	
	
	private static final String dataSet = "test/dbunit/dataset/ExtintorInspecaoDaoHibernateTest.xml";

	@Override
	public ExtintorInspecao getEntity()
	{
		ExtintorInspecao extintorInspecao = new ExtintorInspecao();
		return extintorInspecao;
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
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
		extintor.setEstabelecimento(estabelecimento);
		extintorDao.save(extintor);

		Extintor extintor2 = ExtintorFactory.getEntity();
		extintor2.setEmpresa(empresa);
		extintor2.setEstabelecimento(estabelecimento);
		extintorDao.save(extintor2);

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
		extintor.setEstabelecimento(estabelecimento);
		extintorDao.save(extintor);

		Extintor extintor2 = ExtintorFactory.getEntity();
		extintor2.setEmpresa(empresa);
		extintor2.setEstabelecimento(estabelecimento);
		extintorDao.save(extintor2);

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
				findAllSelect(1, 15, empresa.getId(), estabelecimento.getId(), extintor.getId(), ontem.getTime(), amanha.getTime(), '0').size());

		assertEquals(1, extintorInspecaoDao.
				findAllSelect(1, 15, empresa.getId(), estabelecimento.getId(), extintor.getId(), ontem.getTime(), amanha.getTime(), '2').size());
	
		assertEquals(0, extintorInspecaoDao.
				findAllSelect(1, 15, empresa.getId(), estabelecimento.getId(), extintor.getId(), ontem.getTime(), amanha.getTime(), '1').size());

		int numeroDeRegistrosEncontrados = extintorInspecaoDao.
				findAllSelect(1, 15, empresa.getId(), estabelecimento.getId(), extintor.getId(), ontem.getTime(), amanha.getTime(), '0').size();
		
		assertEquals(1, numeroDeRegistrosEncontrados);
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
		extintor.setEstabelecimento(estabelecimento);
		extintorDao.save(extintor);
		
		ExtintorInspecao extintorInspecao = ExtintorInspecaoFactory.getEntity();
		extintorInspecao.setExtintor(extintor);
		Date dataDaInspecao = DateUtil.criarDataMesAno(10, 04, 2009);
		extintorInspecao.setData(dataDaInspecao);
		extintorInspecaoDao.save(extintorInspecao);
		
		Collection<ExtintorInspecao> extintorInspecaos = extintorInspecaoDao.findInspecoesVencidas(estabelecimento.getId(), DateUtil.criarDataMesAno(1, 10, 2009));
		assertEquals(1, extintorInspecaos.size());

		ExtintorInspecao retorno = (ExtintorInspecao) extintorInspecaos.toArray()[0];
		assertEquals(DateUtil.formataDiaMesAno(dataDaInspecao), DateUtil.formataDiaMesAno(retorno.getData()));
		assertEquals(DateUtil.formataDiaMesAno(DateUtil.criarDataMesAno(9, 06, 2009)), DateUtil.formataDiaMesAno(retorno.getVencimento()));
	}
	
	public void testFindInspecoesVencidasVerificaDatas()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Extintor extintorVencido = ExtintorFactory.getEntity();
		extintorVencido.setAtivo(true);
		extintorVencido.setPeriodoMaxInspecao(2);
		extintorVencido.setEstabelecimento(estabelecimento);
		extintorDao.save(extintorVencido);
		
		Extintor extintorVencidoDesativado = ExtintorFactory.getEntity();
		extintorVencidoDesativado.setAtivo(false);
		extintorVencidoDesativado.setPeriodoMaxInspecao(2);
		extintorVencidoDesativado.setEstabelecimento(estabelecimento);
		extintorDao.save(extintorVencidoDesativado);
		
		Extintor extintorEmDia = ExtintorFactory.getEntity();
		extintorEmDia.setAtivo(true);
		extintorEmDia.setPeriodoMaxInspecao(2);
		extintorEmDia.setEstabelecimento(estabelecimento);
		extintorDao.save(extintorEmDia);
		
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
		
		Collection<ExtintorInspecao> extintorInspecaos = extintorInspecaoDao.findInspecoesVencidas(estabelecimento.getId(), DateUtil.criarDataMesAno(1, 10, 2009));
		assertEquals(1, extintorInspecaos.size());
		assertEquals(extintorInspecaoVencida, extintorInspecaos.toArray()[0]);
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
}
