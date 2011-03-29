package com.fortes.rh.test.business.geral;

import mockit.Mockit;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.MensagemManagerImpl;
import com.fortes.rh.dao.geral.MensagemDao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.ColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialFactory;
import com.fortes.rh.test.factory.cargosalario.FaixaSalarialHistoricoFactory;
import com.fortes.rh.test.factory.cargosalario.HistoricoColaboradorFactory;
import com.fortes.rh.test.factory.cargosalario.IndiceFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;
import com.fortes.rh.test.factory.geral.MensagemFactory;
import com.fortes.rh.test.util.mockObjects.MockArquivoUtil;
import com.fortes.rh.test.util.mockObjects.MockServletActionContext;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.DateUtil;
import com.opensymphony.webwork.ServletActionContext;

public class MensagemManagerTest extends MockObjectTestCase
{
	private MensagemManagerImpl mensagemManager = new MensagemManagerImpl();
	private Mock mensagemDao = null;

    protected void setUp() throws Exception
    {
        super.setUp();

        mensagemDao = new Mock(MensagemDao.class);
        mensagemManager.setDao((MensagemDao) mensagemDao.proxy());

        Mockit.redefineMethods(ServletActionContext.class, MockServletActionContext.class);
		Mockit.redefineMethods(ArquivoUtil.class, MockArquivoUtil.class);
	}

	protected void tearDown() throws Exception
	{
		Mockit.restoreAllOriginalDefinitions();
	}

	public void testSave() throws Exception
	{
		Mensagem mensagem = MensagemFactory.getEntity(1L);

		mensagemDao.expects(once()).method("save").with(ANYTHING).will(returnValue(mensagem));

		Mensagem retorno = mensagemManager.save(mensagem);

		assertEquals(mensagem.getId(), retorno.getId());

	}

	public void testFormataMensagemCancelamentoFaixaSalarialHistorico() throws Exception
	{
		Indice indice = IndiceFactory.getEntity();
		indice.setNome("indice");
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("Cargo");
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setNome("Faixa Salarial");
		faixaSalarial.setCargo(cargo);
		
		FaixaSalarialHistorico faixaSalarialHistorico = FaixaSalarialHistoricoFactory.getEntity();
		faixaSalarialHistorico.setTipo(TipoAplicacaoIndice.INDICE);
		faixaSalarialHistorico.setQuantidade(22.0);
		faixaSalarialHistorico.setIndice(indice);
		faixaSalarialHistorico.setData(DateUtil.criarDataMesAno(02, 05, 2010));
		faixaSalarialHistorico.setFaixaSalarial(faixaSalarial);
		
		String mensagem = "super mensagem";
		String retorno = mensagemManager.formataMensagemCancelamentoFaixaSalarialHistorico(mensagem, faixaSalarialHistorico);
		
		assertEquals("Cancelamento de Histórico da Faixa Salarial. \r\n\r\n<b>Motivo do Cancelamento:</b> \r\n" + mensagem +"\r\n\r\n<b>Dados do Histórico Cancelado:</b> " +
				"\r\nCargo: Cargo\r\nFaixa Salarial: Faixa Salarial\r\nData: 02/05/2010\r\nTipo: Índice\r\nÍndice: indice\r\nQuantidade: 22,00", retorno);
	}
	
	public void testFormataMensagemCancelamentoHistoricoColaborador() throws Exception
	{
		Indice indice = IndiceFactory.getEntity();
		indice.setNome("indice");
		
		Cargo cargo = CargoFactory.getEntity();
		cargo.setNome("Cargo");
		cargo.setNomeMercado("Cargão");
		
		FaixaSalarial faixaSalarial = FaixaSalarialFactory.getEntity();
		faixaSalarial.setNome("Faixa Salarial");
		faixaSalarial.setCargo(cargo);
		
		Colaborador colaborador = ColaboradorFactory.getEntity();
		colaborador.setNomeComercial("nomeComercial");
		
		Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
		estabelecimento.setNome("estabelecimentão");
		
		AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity();
		areaOrganizacional.setDescricao("Area Organizacional");
		
		HistoricoColaborador historicoColaborador = HistoricoColaboradorFactory.getEntity();
		historicoColaborador.setData(DateUtil.criarDataMesAno(02, 05, 2010));
		historicoColaborador.setTipoSalario(0);
		historicoColaborador.setFaixaSalarial(faixaSalarial);
		historicoColaborador.setColaborador(colaborador);
		historicoColaborador.setEstabelecimento(estabelecimento);
		historicoColaborador.setAreaOrganizacional(areaOrganizacional);
		historicoColaborador.setIndice(indice);
		
		String mensagem = "super mensagem";
		String retorno = mensagemManager.formataMensagemCancelamentoHistoricoColaborador(mensagem, historicoColaborador);
		
		assertEquals("Cancelamento de Situação do Colaborador. \r\n\r\n<b>Motivo do Cancelamento:</b> \r\nsuper mensagem\r\n\r\n<b>Dados da Situação Cancelada:</b> \r\n" +
				"Nome: nomeComercial\r\nData: 02/05/2010\r\nEstabelecimento: estabelecimentão\r\nÁrea Organizacional: nome da area organizacional\r\nCargo: Cargão\r\n" +
				"Faixa Salarial: Faixa Salarial\r\nTipo do Salário: \r\n", retorno);
	}
}