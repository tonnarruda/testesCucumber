package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.CandidatoEleicaoManager;
import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.business.sesmt.EtapaProcessoEleitoralManager;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;

public class EleicaoEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;
	
	private EleicaoManager eleicaoManager;
	private EtapaProcessoEleitoralManager etapaProcessoEleitoralManager;
	private CandidatoEleicaoManager candidatoEleicaoManager;
	private ColaboradorManager colaboradorManager;
	private EstabelecimentoManager estabelecimentoManager;

	private Eleicao eleicao;
	private Collection<Estabelecimento> estabelecimentos;

	private Collection<CandidatoEleicao> dataSource;
	private Map<String,Object> parametros = new HashMap<String, Object>();

	private String imprimaRelatorio;
	private void prepare() throws Exception
	{
		if (eleicao != null && eleicao.getId() != null)
			eleicao = (Eleicao) eleicaoManager.findByIdProjection(eleicao.getId());

		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		eleicao = new Eleicao("08:00", "18:00");

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		eleicao.setEmpresa(getEmpresaSistema());
		eleicaoManager.save(eleicao);
		etapaProcessoEleitoralManager.clonarEtapas(getEmpresaSistema().getId(), eleicao);

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		eleicao.setEmpresa(getEmpresaSistema());
		eleicaoManager.update(eleicao);

		addActionMessage("Eleição gravada com sucesso.");

		prepare();
		return Action.SUCCESS;
	}

	public String imprimirResultado()
	{
		try
		{
			eleicao = eleicaoManager.findImprimirResultado(eleicao.getId());
			dataSource = eleicao.getCandidatoEleicaos();

			parametros = RelatorioUtil.getParametrosRelatorio("", getEmpresaSistema(), "");

			parametros.put("VOTOS_BRANCOS", eleicao.getQtdVotoBranco());
			parametros.put("VOTOS_NULOS", eleicao.getQtdVotoNulo());
			parametros.put("VOTOS_TOTAL", eleicao.getSomaVotos());

			String logoEmpresa = ArquivoUtil.getPathLogoEmpresa() + getEmpresaSistema().getLogoUrl();
			parametros.put("LOGO", logoEmpresa);

			Collection<CandidatoEleicao> candidatosGrafico = eleicaoManager.setCandidatosGrafico(eleicao);
			parametros.put("CANDIDATOS_GRAFICO", candidatosGrafico);

			return SUCCESS;
		}
		catch (ColecaoVaziaException e)
		{
			addActionError(e.getMessage());
			return INPUT;
		}
	}
	
	public String prepareComunicados() throws Exception
	{
		prepare();
		prepareTextoParaAtaDaEleicao();
		return SUCCESS;
	}
	
	private void prepareTextoParaAtaDaEleicao()
	{
		if (StringUtils.isBlank(getEleicao().getTextoAtaEleicao()))
		{
			String textoAtaEleicao = "        Aos ........ dias do mês ........ ano ........ no local designado no Edital de Convocação ........, com a presença dos Senhores ................................ instalou-se a mesa receptora e apuradora dos votos  às ........ horas. O Sr. Presidente da mesa declarou iniciados os trabalhos. \n";
			textoAtaEleicao+= "        Durante a votação, verificaram-se as seguintes ocorrências: ............................ \n";
			textoAtaEleicao+= "        Às ........ horas, o Sr. Presidente declarou encerrados os trabalhos de eleição, verificando-se que compareceram ........ empregados, passando-se à apuração na presença de quantos desejassem.";
			
			getEleicao().setTextoAtaEleicao(textoAtaEleicao);
		}
	}

	public String updateImprimir() throws Exception
	{
		if(imprimaRelatorio.equals("localInscricao"))
		{
			updateImprimirLocalIncricao();
			return "imprimirLocalIncricao";
		}
		else if(imprimaRelatorio.equals("localVotacao"))
		{
			updateImprimirLocalVotacao();
			return "imprimirLocalVotacao";
		}
		else if(imprimaRelatorio.equals("chamadoEleicao"))
		{
			updateImprimirSindicato();	
			return "imprimirSindicado";
		}
		else if(imprimaRelatorio.equals("ataEleicao"))
		{
			updateImprimirAtaEleicao();	
			return "imprimirAtaEleicao";
		}
		
		return INPUT;
	}
	
	public String updateImprimirAtaEleicao() throws ColecaoVaziaException
	{
		try
		{
			prepareImpressaoComunicado("");
			
			eleicao = eleicaoManager.montaAtaDaEleicao(eleicao.getId());
			dataSource = eleicao.getCandidatoEleicaos();

			parametros.put("TEXTO", eleicao.getTextoAtaEleicao());
			parametros.put("VOTOS_BRANCOS", eleicao.getQtdVotoBranco());
			parametros.put("VOTOS_NULOS", eleicao.getQtdVotoNulo());
			parametros.put("VOTOS_TOTAL", eleicao.getSomaVotos());

			String logoEmpresa = ArquivoUtil.getPathLogoEmpresa() + getEmpresaSistema().getLogoUrl();
			parametros.put("LOGO", logoEmpresa);

			return SUCCESS;
		}
		catch (ColecaoVaziaException e)
		{
			addActionError(e.getMessage());
			return INPUT;
		}
	}

	private String updateImprimirLocalIncricao() throws Exception
	{
		prepareImpressaoComunicado("EDITAL DE INSCRIÇÃO PARA MEMBROS DA CIPA");
		dataSource = new ArrayList<CandidatoEleicao>();
		dataSource.add(null);//exibir os dados no pdf

		parametros.put("PERIODO", "Período: " + DateUtil.formataDiaMesAno(eleicao.getInscricaoCandidatoIni()) + " a " + DateUtil.formataDiaMesAno(eleicao.getInscricaoCandidatoFim()));
		parametros.put("LOCAL", "Local de inscrição: " + eleicao.getLocalInscricao());

		return SUCCESS;
	}

	private String updateImprimirSindicato() throws Exception
	{
		prepareImpressaoComunicado("");
		dataSource = new ArrayList<CandidatoEleicao>();
		dataSource.add(null);//exibir os dados no pdf

		parametros.put("DATA_ATUAL_FORMATADA", DateUtil.formataDataExtenso(new Date()));
		parametros.put("SINDICATO", eleicao.getSindicato());
		parametros.put("EMPRESA", getEmpresaSistema().getNome());
		parametros.put("DATA_VOTACAOINI", DateUtil.formataDiaMesAno(eleicao.getVotacaoIni()));
		parametros.put("DATA_VOTACAOFIM", DateUtil.formataDiaMesAno(eleicao.getVotacaoFim()));
		
		parametros.put("LOCAL_VOTACAO", "Local da votação: " + eleicao.getLocalVotacao());
		parametros.put("DATA_VOTACAO", DateUtil.formataDiaMesAno(eleicao.getVotacaoIni()) + " a " + DateUtil.formataDiaMesAno(eleicao.getVotacaoFim()));		
		parametros.put("HORARIO_VOTACAO", "Horário da votação: " + eleicao.getHorarioVotacaoIni() + " a " + eleicao.getHorarioVotacaoFim());
		
		parametros.put("LOCAL_APURACAO", "Local da apuração dos votos: " + eleicao.getLocalApuracao());
		parametros.put("DATA_APURACAO", "Data da apuração dos votos: " + DateUtil.formataDiaMesAno(eleicao.getApuracao()));
		parametros.put("HORARIO_APURACAO", "Horário da apuração: " + eleicao.getHorarioApuracao());

		return SUCCESS;
	}

	public String imprimirDRT() throws Exception
	{
		String estabelecimentoNome = "", estabelecimentoEndereco = "";
		Integer totalEmpregados = null;

		Empresa empresaSistema = getEmpresaSistema();
		eleicao = eleicaoManager.findByIdProjection(eleicao.getId());

		parametros = RelatorioUtil.getParametrosRelatorio("", empresaSistema, "");
		dataSource = new ArrayList<CandidatoEleicao>();
		dataSource.add(null);//exibir os dados no pdf

		Date hoje = new Date();

		if (eleicao.getEstabelecimento() != null)
		{
			estabelecimentoNome = eleicao.getEstabelecimento().getNome();
			estabelecimentoEndereco = eleicao.getEstabelecimento().getEnderecoFormatado();
			totalEmpregados = colaboradorManager.getCountAtivosEstabelecimento(eleicao.getEstabelecimento().getId());
		}
		
		// Evita string "null" no pdf. Cabe ao usuário a percepção das informações que faltam para a empresa...
		String cidadeNome = empresaSistema.getCidade() != null ? empresaSistema.getCidade().getNome() : "";
		String ufSigla = empresaSistema.getUf() != null ? empresaSistema.getUf().getSigla() : "";
		
		parametros.put("ENDERECO", estabelecimentoEndereco + " - " + cidadeNome + " - " + ufSigla);
		parametros.put("ATIVIDADE", getEmpresaSistema().getAtividade());

		String dataFormatada = DateUtil.formataDataExtenso(hoje);
		if (StringUtils.isNotBlank(cidadeNome))
			  dataFormatada = cidadeNome + ", " + dataFormatada;
		
		parametros.put("DATA_ATUAL_FORMATADA", dataFormatada);
		parametros.put("TOTAL_EMPREGADOS", totalEmpregados);
		parametros.put("EMPRESA", getEmpresaSistema().getNome());
		parametros.put("ESTABELECIMENTO", estabelecimentoNome);
		parametros.put("RISCO", empresaSistema.getGrauDeRisco());

		return SUCCESS;
	}
		
	private String updateImprimirLocalVotacao() throws Exception
	{
		// salva os dados primeiro (mesmo se não for imprimir).
		prepareImpressaoComunicado("ELEIÇÃO DA CIPA");

		dataSource = candidatoEleicaoManager.findByEleicao(eleicao.getId());
		if(dataSource.isEmpty())
		{
			prepareComunicados();
			addActionMessage("Não existem candidatos para a eleição.");

			return INPUT;
		}

		if(eleicao.getVotacaoIni() != null && eleicao.getVotacaoIni().equals(eleicao.getVotacaoFim()))
			parametros.put("DATA", "Data: " + DateUtil.formataDiaMesAno(eleicao.getVotacaoIni()));
		else
			parametros.put("DATA", "Data: " + DateUtil.formataDiaMesAno(eleicao.getVotacaoIni()) + " a " + DateUtil.formataDiaMesAno(eleicao.getVotacaoFim()));
		
		parametros.put("HORARIO", "Horário: " + eleicao.getHorarioVotacaoIni() + " a " + eleicao.getHorarioVotacaoFim());
		parametros.put("LOCAL", "Local: " + eleicao.getLocalVotacao());

		return SUCCESS;
	}

	private void prepareImpressaoComunicado(String titulo)
	{
		eleicao.setEmpresa(getEmpresaSistema());
		eleicaoManager.update(eleicao);

		parametros = RelatorioUtil.getParametrosRelatorio(titulo, getEmpresaSistema(), "");
	}

	public Eleicao getEleicao()
	{
		if (eleicao == null)
			eleicao = new Eleicao();
		return eleicao;
	}

	public void setEleicao(Eleicao eleicao)
	{
		this.eleicao = eleicao;
	}

	public void setEleicaoManager(EleicaoManager eleicaoManager)
	{
		this.eleicaoManager = eleicaoManager;
	}

	public void setEtapaProcessoEleitoralManager(EtapaProcessoEleitoralManager etapaProcessoEleitoralManager)
	{
		this.etapaProcessoEleitoralManager = etapaProcessoEleitoralManager;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public Collection<CandidatoEleicao> getDataSource()
	{
		return dataSource;
	}

	public void setCandidatoEleicaoManager(CandidatoEleicaoManager candidatoEleicaoManager)
	{
		this.candidatoEleicaoManager = candidatoEleicaoManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager)
	{
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Collection<Estabelecimento> getEstabelecimentos()
	{
		return estabelecimentos;
	}

	public String getImprimaRelatorio()
	{
		return imprimaRelatorio;
	}

	public void setImprimaRelatorio(String imprimaRelatorio)
	{
		this.imprimaRelatorio = imprimaRelatorio;
	}
}