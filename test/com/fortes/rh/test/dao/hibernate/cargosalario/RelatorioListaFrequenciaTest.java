package com.fortes.rh.test.dao.hibernate.cargosalario;

import java.util.Collection;
import java.util.Date;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.cargosalario.HistoricoColaboradorDao;
import com.fortes.rh.dao.geral.AreaOrganizacionalDao;
import com.fortes.rh.dao.geral.ColaboradorDao;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.dao.sesmt.EleicaoDao;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.test.dao.BaseDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.sesmt.EleicaoFactory;
import com.fortes.rh.util.DateUtil;

public class RelatorioListaFrequenciaTest extends BaseDaoHibernateTest
{
	Estabelecimento matriz;

	Date votacaoIni;

	Date votacaoFim;

	AreaOrganizacional areaOrganizacional;

	Eleicao eleicao;

	private HistoricoColaboradorDao historicoColaboradorDao;

	private ColaboradorDao colaboradorDao;

	private AreaOrganizacionalDao areaOrganizacionalDao;

	private EstabelecimentoDao estabelecimentoDao;

	private EleicaoDao eleicaoDao;

	private Colaborador ultimoColaboradorCriado;

//	@Override
	public GenericDao<HistoricoColaborador> getGenericDao()
	{
		return historicoColaboradorDao;
	}

	public void setup()
	{
		matriz = new Estabelecimento();
		votacaoIni = DateUtil.montaDataByString("21/09/2010");
		votacaoFim = DateUtil.montaDataByString("23/09/2010");
	}

	public void testDeveriaTrazerColaboradoresNaoDesligados()
	{

		// dado que
		setup();

		dadoUmEstabelecimentoMatriz();
		dadoUmaAreaOrganizacionalQualquer();

		dadoQueExisteColaboradorNaoDesligadoCom("Rafael", "15/09/2010", matriz);
		dadoQueExisteColaboradorNaoDesligadoCom("Bruno", "22/09/2010", matriz);
		dadoQueExisteColaboradorNaoDesligadoCom("Milfont", "25/09/2010", matriz);

		// quando
		Collection<HistoricoColaborador> lista = historicoColaboradorDao.findImprimirListaFrequencia(matriz, votacaoIni, votacaoFim);

		// entao (espera-se)
		assertEquals("Colaboradores encontrados", 2, lista.size());
		assertTrue("Rafael deve estar na lista", contem(lista, "Rafael"));
		assertTrue("Bruno deve estar na lista", contem(lista, "Bruno"));
		assertFalse("Milfont não deve estar na lista", contem(lista, "Milfont"));

	}

	public void testDeveriaNaoTrazerColaboradoresDesligados()
	{
		// dado que
		setup();

		dadoUmEstabelecimentoMatriz();
		dadoUmaAreaOrganizacionalQualquer();

		dadoQueExisteColaboradorNaoDesligadoCom("Rafael", "15/09/2010", matriz);
		dadoQueExisteColaboradorNaoDesligadoCom("Bruno", "22/09/2010", matriz);
		dadoQueExisteColaboradorNaoDesligadoCom("Milfont", "25/09/2010", matriz);
		dadoQueExisteColaboradorDesligadoCom("Barroso", "10/08/2010"); // antes da eleicao

		// quando
		Collection<HistoricoColaborador> lista = historicoColaboradorDao.findImprimirListaFrequencia(matriz, votacaoIni, votacaoFim);

		// entao (espera-se)
		assertEquals("Colaboradores encontrados", 2, lista.size());
		assertTrue("Rafael deve estar na lista", contem(lista, "Rafael"));
		assertTrue("Bruno deve estar na lista", contem(lista, "Bruno"));
		assertFalse("Milfont não deve estar na lista", contem(lista, "Milfont"));
		assertFalse("Barroso não deve estar na lista", contem(lista, "Barroso"));

	}

	public void testDeveriaTrazerColaboradoresDesligadosDentroDoPeriodo()
	{
		// dado que
		setup();

		dadoUmEstabelecimentoMatriz();
		dadoUmaAreaOrganizacionalQualquer();

		dadoQueExisteColaboradorNaoDesligadoCom("Rafael", "15/09/2010", matriz);
		dadoQueExisteColaboradorNaoDesligadoCom("Bruno", "22/09/2010", matriz);
		dadoQueExisteColaboradorNaoDesligadoCom("Milfont", "25/09/2010", matriz);
		dadoQueExisteColaboradorDesligadoDentroDoPeriodoCom("Barroso", "01/09/2010"); // dentro do periodo

		// quando
		Collection<HistoricoColaborador> lista = historicoColaboradorDao.findImprimirListaFrequencia(matriz, votacaoIni, votacaoFim);

		// entao (espera-se)
		assertEquals("Colaboradores encontrados", 3, lista.size());
		assertTrue("Rafael deve estar na lista", contem(lista, "Rafael"));
		assertTrue("Bruno deve estar na lista", contem(lista, "Bruno"));
		assertTrue("Barroso deve estar na lista", contem(lista, "Barroso"));
		assertFalse("Milfont não deve estar na lista", contem(lista, "Milfont"));

	}

	public void testColaboradorComEstabelecimentoIgualDaEleicao()
	{
		// dado que
		setup();
		dadoUmEstabelecimentoMatriz();
		Estabelecimento filial = dadoUmEstabelecimentoFilial();
		dadoUmaAreaOrganizacionalQualquer();

		dadoQueExisteColaboradorNaoDesligadoCom("Rafael", "21/09/2010", matriz);
		dadoQueExisteColaboradorNaoDesligadoCom("Bruno", "21/09/2010", matriz);
		dadoQueExisteColaboradorNaoDesligadoCom("Milfont", "21/09/2010", matriz);
		dadoQueExisteColaboradorNaoDesligadoCom("Barroso", "21/09/2010", filial);

		dadoUmaEleicaoQualquer();

		// quando
		Collection<HistoricoColaborador> lista = historicoColaboradorDao.findImprimirListaFrequencia(matriz, votacaoIni, votacaoFim);

		// entao (espera-se)
		assertEquals("Colaboradores encontrados", 3, lista.size());
		assertTrue("O estabelecimento do Rafael deve ser o mesmo da Eleição", mesmoEstabelecimento(lista, "Rafael", eleicao));
		assertTrue("O estabelecimento do Bruno deve ser o mesmo da Eleição", mesmoEstabelecimento(lista, "Bruno", eleicao));
		assertTrue("O estabelecimento do Milfont deve ser o mesmo da Eleição", mesmoEstabelecimento(lista, "Milfont", eleicao));
		assertFalse("O estabelecimento do Barroso não deve estar na lista", mesmoEstabelecimento(lista, "Barroso", eleicao));

	}

	public void testComHistoricosComEstabelecimentosDiferentes()
	{
		// dado que
		setup();
		dadoUmEstabelecimentoMatriz();
		Estabelecimento filial = dadoUmEstabelecimentoFilial();
		dadoUmaAreaOrganizacionalQualquer();

		dadoQueExisteColaboradorNaoDesligadoCom("Rafael", "15/08/2010", matriz);
		dadoQueExisteColaboradorNaoDesligadoCom("Bruno", "22/09/2010", matriz);

		dadoQueExisteColaboradorNaoDesligadoCom("Milfont", "10/09/2010", matriz); // fora
		dadoQueColaboradorMudouDeEstabelecimento(ultimoColaboradorCriado, "24/09/2010", filial);

		dadoQueExisteColaboradorNaoDesligadoCom("Barroso", "21/09/2010", matriz); // dentro
		dadoQueColaboradorMudouDeEstabelecimento(ultimoColaboradorCriado, "24/09/2010", filial);

		dadoUmaEleicaoQualquer();

		// quando
		Collection<HistoricoColaborador> lista = historicoColaboradorDao.findImprimirListaFrequencia(matriz, votacaoIni, votacaoFim);

		// entao (espera-se)
		assertEquals("Colaboradores encontrados", 4, lista.size());
		assertTrue("O estabelecimento do Rafael deve ser o mesmo da Eleição", mesmoEstabelecimento(lista, "Rafael", eleicao));
		assertTrue("O estabelecimento do Bruno deve ser o mesmo da Eleição", mesmoEstabelecimento(lista, "Bruno", eleicao));
		assertTrue("O estabelecimento do Milfont deve ser o mesmo da Eleição", mesmoEstabelecimento(lista, "Milfont", eleicao));
		assertTrue("O estabelecimento do Barroso deve ser o mesmo da Eleição", mesmoEstabelecimento(lista, "Barroso", eleicao));

	}

	private void dadoQueColaboradorMudouDeEstabelecimento(Colaborador colaborador, String transferidoEm, Estabelecimento estabelecimento)
	{
		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setEstabelecimento(estabelecimento);
		historico.setData(DateUtil.montaDataByString(transferidoEm));
		historico.setAreaOrganizacional(areaOrganizacional);
		historico.setColaborador(colaborador);
		historicoColaboradorDao.save(historico);
	}

	private boolean mesmoEstabelecimento(Collection<HistoricoColaborador> lista, String nome, Eleicao eleicao2)
	{
		Boolean encontrado = Boolean.FALSE;
		for (HistoricoColaborador historicoColaborador : lista)
		{
			String nomeEstabelecimento = historicoColaboradorDao.findById(historicoColaborador.getId()).getEstabelecimento().getNome();
			if(historicoColaborador.getColaborador().getNome().equals(nome) && eleicao2.getEstabelecimento().getNome().equals(nomeEstabelecimento))
			{
				encontrado = Boolean.TRUE;
			}
		}
		return encontrado;
	}

	private void dadoUmaEleicaoQualquer()
	{
		eleicao = EleicaoFactory.getEntity();
		eleicao.setEstabelecimento(matriz);
		eleicaoDao.save(eleicao);
	}

	private void dadoQueExisteColaboradorDesligadoDentroDoPeriodoCom(String nome, String admissao)
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome(nome);
		colaborador.setDesligado(true); // desligado
		colaborador.setDataAdmissao(DateUtil.montaDataByString(admissao));
		colaborador.setDataDesligamento(DateUtil.montaDataByString("22/09/2010"));
		colaboradorDao.save(colaborador);

		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setEstabelecimento(matriz);
		historico.setData(DateUtil.montaDataByString(admissao));
		historico.setAreaOrganizacional(areaOrganizacional);
		historico.setColaborador(colaborador);
		historicoColaboradorDao.save(historico);

	}

	private void dadoQueExisteColaboradorDesligadoCom(String nome, String admissao)
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome(nome);
		colaborador.setDesligado(true); // desligado
		colaborador.setDataAdmissao(DateUtil.montaDataByString(admissao));
		colaborador.setDataDesligamento(DateUtil.montaDataByString("10/09/2010"));
		colaboradorDao.save(colaborador);

		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setEstabelecimento(matriz);
		historico.setData(DateUtil.montaDataByString(admissao));
		historico.setAreaOrganizacional(areaOrganizacional);
		historico.setColaborador(colaborador);
		historicoColaboradorDao.save(historico);
	}

	private void dadoUmaAreaOrganizacionalQualquer()
	{
		areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setNome("Administrativo Financeiro");
		areaOrganizacionalDao.save(areaOrganizacional);
	}

	private void dadoUmEstabelecimentoMatriz()
	{
		matriz = EstabelecimentoFactory.getEntity();
		matriz.setNome("Matriz");
		estabelecimentoDao.save(matriz);
	}

	private Estabelecimento dadoUmEstabelecimentoFilial()
	{
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("Filial");
		estabelecimentoDao.save(estabelecimento);
		return estabelecimento;
	}

	private Boolean contem(Collection<HistoricoColaborador> lista, String nome)
	{
		Boolean encontrado = Boolean.FALSE;
		for (HistoricoColaborador historicoColaborador : lista)
		{
			if(historicoColaborador.getColaborador().getNome().equals(nome))
				encontrado = Boolean.TRUE;
		}
		return encontrado;
	}

	private void dadoQueExisteColaboradorNaoDesligadoCom(String nome, String admissao, Estabelecimento estabelecimento)
	{
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNome(nome);
		colaborador.setDesligado(false); // nao desligado
		colaborador.setDataAdmissao(DateUtil.montaDataByString(admissao));
		colaboradorDao.save(colaborador);

		ultimoColaboradorCriado = colaborador;

		HistoricoColaborador historico = HistoricoColaboradorFactory.getEntity();
		historico.setEstabelecimento(estabelecimento);
		historico.setData(DateUtil.montaDataByString(admissao));
		historico.setAreaOrganizacional(areaOrganizacional);
		historico.setColaborador(colaborador);
		historicoColaboradorDao.save(historico);
	}

	public void setHistoricoColaboradorDao(HistoricoColaboradorDao historicoColaboradorDao)
	{
		this.historicoColaboradorDao = historicoColaboradorDao;
	}

	public void setColaboradorDao(ColaboradorDao colaboradorDao)
	{
		this.colaboradorDao = colaboradorDao;
	}

	public void setAreaOrganizacionalDao(AreaOrganizacionalDao areaOrganizacionalDao)
	{
		this.areaOrganizacionalDao = areaOrganizacionalDao;
	}

	public void setEstabelecimentoDao(EstabelecimentoDao estabelecimentoDao)
	{
		this.estabelecimentoDao = estabelecimentoDao;
	}

//	@Override
	public HistoricoColaborador getEntity()
	{
		return HistoricoColaboradorFactory.getEntity();
	}

	public void setEleicaoDao(EleicaoDao eleicaoDao)
	{
		this.eleicaoDao = eleicaoDao;
	}

}
