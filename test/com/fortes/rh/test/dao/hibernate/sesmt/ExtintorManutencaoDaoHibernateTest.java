package com.fortes.rh.test.dao.hibernate.sesmt;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.ExtintorDao;
import com.fortes.rh.dao.sesmt.ExtintorManutencaoDao;
import com.fortes.rh.model.dicionario.MotivoExtintorManutencao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Extintor;
import com.fortes.rh.model.sesmt.ExtintorManutencao;
import com.fortes.rh.model.sesmt.ExtintorManutencaoServico;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorFactory;
import com.fortes.rh.test.factory.sesmt.ExtintorManutencaoFactory;
import com.fortes.rh.util.DateUtil;

public class ExtintorManutencaoDaoHibernateTest extends GenericDaoHibernateTest<ExtintorManutencao>
{
	private ExtintorManutencaoDao extintorManutencaoDao;
	private ExtintorDao extintorDao;
	private EmpresaDao empresaDao;
	private EstabelecimentoDao estabelecimentoDao;

	@Override
	public ExtintorManutencao getEntity()
	{
		ExtintorManutencao extintorManutencao = new ExtintorManutencao();
		return extintorManutencao;
	}

	@Override
	public GenericDao<ExtintorManutencao> getGenericDao()
	{
		return extintorManutencaoDao;
	}

	public void setExtintorManutencaoDao(ExtintorManutencaoDao extintorManutencaoDao)
	{
		this.extintorManutencaoDao = extintorManutencaoDao;
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

		ExtintorManutencao extintorManutencao = ExtintorManutencaoFactory.getEntity();
		extintorManutencao.setExtintor(extintor);
		extintorManutencao.setSaida(hoje);
		extintorManutencaoDao.save(extintorManutencao);

		ExtintorManutencao extintorManutencaoFora = ExtintorManutencaoFactory.getEntity();
		extintorManutencaoFora.setExtintor(extintor);
		extintorManutencaoFora.setSaida(ontem.getTime());
		extintorManutencaoFora.setRetorno(hoje);
		extintorManutencaoDao.save(extintorManutencaoFora);

		assertEquals(Integer.valueOf(1),
					extintorManutencaoDao.getCount(empresa.getId(), estabelecimento.getId(), extintor.getId(), new Date(), null, true));
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
		extintor.setLocalizacao("Local teste");
		extintorDao.save(extintor);

		Extintor extintor2 = ExtintorFactory.getEntity();
		extintor2.setEmpresa(empresa);
		extintor2.setEstabelecimento(estabelecimento);
		extintorDao.save(extintor2);

		ExtintorManutencao extintorManutencao = ExtintorManutencaoFactory.getEntity();
		extintorManutencao.setExtintor(extintor);
		extintorManutencao.setSaida(hoje);
		extintorManutencaoDao.save(extintorManutencao);

		ExtintorManutencao extintorManutencao2 = ExtintorManutencaoFactory.getEntity();
		extintorManutencao2.setExtintor(extintor);
		extintorManutencao2.setSaida(ontem.getTime());
		extintorManutencaoDao.save(extintorManutencao2);

		ExtintorManutencao extintorManutencaoFora = ExtintorManutencaoFactory.getEntity();
		extintorManutencaoFora.setExtintor(extintor2);
		extintorManutencaoFora.setSaida(ontem.getTime());
		extintorManutencaoDao.save(extintorManutencaoFora);

		assertEquals(2, extintorManutencaoDao.
				findAllSelect(0, 0, empresa.getId(), estabelecimento.getId(), extintor.getId(), ontem.getTime(), amanha.getTime(), false, null).size());
		
		assertEquals(2, extintorManutencaoDao.
				findAllSelect(0, 0, empresa.getId(), estabelecimento.getId(), extintor.getId(), ontem.getTime(), amanha.getTime(), false, "Local").size());
		
		assertEquals(0, extintorManutencaoDao.
				findAllSelect(0, 0, empresa.getId(), estabelecimento.getId(), extintor.getId(), ontem.getTime(), amanha.getTime(), false, "Locais").size());
	}

	public void testFindManutencaoRecargaVencidas()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Extintor extintor = ExtintorFactory.getEntity();
		extintor.setAtivo(true);
		extintor.setPeriodoMaxRecarga(2);
		extintor.setEstabelecimento(estabelecimento);
		extintorDao.save(extintor);
		
		ExtintorManutencao extintorManutencao = ExtintorManutencaoFactory.getEntity();
		Date dataDaRecarga = DateUtil.criarDataMesAno(10, 04, 2009);
		extintorManutencao.setSaida(dataDaRecarga);
		extintorManutencao.setExtintor(extintor);
		extintorManutencao.setMotivo(MotivoExtintorManutencao.PRAZO_RECARGA);
		
		ExtintorManutencaoServico servico = new ExtintorManutencaoServico();
		servico.setId(1L); // ID FIXO do Serviço "Recarga"
		
		Collection<ExtintorManutencaoServico> servicos = Arrays.asList(servico);
		extintorManutencao.setServicos(servicos);
		
		extintorManutencaoDao.save(extintorManutencao);
		
		Collection<ExtintorManutencao> extintorManutencaoVencidas = extintorManutencaoDao.findManutencaoVencida(estabelecimento.getId(), DateUtil.criarDataMesAno(1, 10, 2009), MotivoExtintorManutencao.PRAZO_RECARGA);
		assertEquals(1, extintorManutencaoVencidas.size());

		ExtintorManutencao retorno = (ExtintorManutencao) extintorManutencaoVencidas.toArray()[0];
		assertEquals(DateUtil.formataDiaMesAno(dataDaRecarga), DateUtil.formataDiaMesAno(retorno.getSaida()));
		assertEquals(DateUtil.formataDiaMesAno(DateUtil.criarDataMesAno(9, 06, 2009)), DateUtil.formataDiaMesAno(retorno.getVencimento()));
	}
	
	public void testFindManutencaoTesteHidrostaticoVencidas()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Extintor extintor = ExtintorFactory.getEntity();
		extintor.setAtivo(true);
		extintor.setPeriodoMaxHidrostatico(2);
		extintor.setEstabelecimento(estabelecimento);
		extintorDao.save(extintor);
		
		ExtintorManutencao extintorManutencao = ExtintorManutencaoFactory.getEntity();
		Date dataDaRecarga = DateUtil.criarDataMesAno(10, 04, 2009);
		extintorManutencao.setSaida(dataDaRecarga);
		extintorManutencao.setExtintor(extintor);
		extintorManutencao.setMotivo(MotivoExtintorManutencao.PRAZO_HIDROSTATICO);
		
		ExtintorManutencaoServico servico = new ExtintorManutencaoServico();
		servico.setId(3L); // ID FIXO do Serviço "Teste Hidrostático"
		
		Collection<ExtintorManutencaoServico> servicos = Arrays.asList(servico);
		extintorManutencao.setServicos(servicos);
		
		extintorManutencaoDao.save(extintorManutencao);
		
		Collection<ExtintorManutencao> extintorManutencaoVencidas = extintorManutencaoDao.findManutencaoVencida(estabelecimento.getId(), DateUtil.criarDataMesAno(1, 10, 2009), MotivoExtintorManutencao.PRAZO_HIDROSTATICO);
		assertEquals(1, extintorManutencaoVencidas.size());
		
		ExtintorManutencao retorno = (ExtintorManutencao) extintorManutencaoVencidas.toArray()[0];
		assertEquals(DateUtil.formataDiaMesAno(dataDaRecarga), DateUtil.formataDiaMesAno(retorno.getSaida()));
		assertEquals(DateUtil.formataDiaMesAno(DateUtil.criarDataMesAno(9, 06, 2009)), DateUtil.formataDiaMesAno(retorno.getVencimento()));
	}
	
	public void testFindManutencaoVencidasVerificaDatas()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimentoDao.save(estabelecimento);
		
		Extintor extintorVencido = ExtintorFactory.getEntity();
		extintorVencido.setAtivo(true);
		extintorVencido.setPeriodoMaxRecarga(2);
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
		
		ExtintorManutencaoServico servico = new ExtintorManutencaoServico();
		servico.setId(1L); // ID FIXO do Serviço "Recarga"
		Collection<ExtintorManutencaoServico> servicos = Arrays.asList(servico);
		
		ExtintorManutencao extintorManutencaoVencida = ExtintorManutencaoFactory.getEntity();
		Date dataDaRecarga = DateUtil.criarDataMesAno(2, 8, 2009);//fronteira do vencimento
		extintorManutencaoVencida.setSaida(dataDaRecarga);
		extintorManutencaoVencida.setExtintor(extintorVencido);
		extintorManutencaoVencida.setMotivo(MotivoExtintorManutencao.PRAZO_RECARGA);
		
		extintorManutencaoVencida.setServicos(servicos);
		
		extintorManutencaoDao.save(extintorManutencaoVencida);
	
		ExtintorManutencao extintorManutencaoVencidaMaisAntiga = ExtintorManutencaoFactory.getEntity();
		extintorManutencaoVencidaMaisAntiga.setExtintor(extintorVencido);
		extintorManutencaoVencidaMaisAntiga.setSaida(DateUtil.criarDataMesAno(2, 1, 2009));
		extintorManutencaoVencidaMaisAntiga.setMotivo(MotivoExtintorManutencao.PRAZO_RECARGA);
		extintorManutencaoVencidaMaisAntiga.setServicos(servicos);
		extintorManutencaoDao.save(extintorManutencaoVencidaMaisAntiga);
		
		ExtintorManutencao extintorManutencaoVencidaDesativado = ExtintorManutencaoFactory.getEntity();
		extintorManutencaoVencidaDesativado.setExtintor(extintorVencidoDesativado);
		extintorManutencaoVencidaDesativado.setSaida(DateUtil.criarDataMesAno(10, 6, 2009));
		extintorManutencaoVencidaDesativado.setMotivo(MotivoExtintorManutencao.PRAZO_RECARGA);
		extintorManutencaoVencidaDesativado.setServicos(servicos);
		extintorManutencaoDao.save(extintorManutencaoVencidaDesativado);
		
		ExtintorManutencao extintorManutencaoEmDia = ExtintorManutencaoFactory.getEntity();
		extintorManutencaoEmDia.setExtintor(extintorEmDia);
		extintorManutencaoEmDia.setSaida(DateUtil.criarDataMesAno(3, 8, 2009));//fronteira do vencimento
		extintorManutencaoEmDia.setMotivo(MotivoExtintorManutencao.PRAZO_RECARGA);
		extintorManutencaoEmDia.setServicos(servicos);
		extintorManutencaoDao.save(extintorManutencaoEmDia);

		Collection<ExtintorManutencao> extintorManutencaoVencidas = extintorManutencaoDao.findManutencaoVencida(estabelecimento.getId(), DateUtil.criarDataMesAno(1, 10, 2009), MotivoExtintorManutencao.PRAZO_RECARGA);
		assertEquals(1, extintorManutencaoVencidas.size());
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
}