package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.sesmt.CandidatoEleicaoManager;
import com.fortes.rh.business.sesmt.EleicaoManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.relatorio.LinhaCedulaEleitoralRelatorio;
import com.fortes.rh.model.sesmt.CandidatoEleicao;
import com.fortes.rh.model.sesmt.Eleicao;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class CandidatoEleicaoListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	@Autowired private EleicaoManager eleicaoManager;
	@Autowired private ColaboradorManager colaboradorManager;
	@Autowired private CandidatoEleicaoManager candidatoEleicaoManager;
	@Autowired private AreaOrganizacionalManager areaOrganizacionalManager;
	@Autowired private HistoricoColaboradorManager historicoColaboradorManager;
	
	private Collection<CandidatoEleicao> candidatoEleicaos;

	private CandidatoEleicao candidatoEleicao;
	private Eleicao eleicao;
	private Collection<CheckBox> areasCheckList = new ArrayList<CheckBox>();
	private String[] candidatosCheck;
	private Collection<CheckBox> candidatosCheckList = new ArrayList<CheckBox>();

	private String[] eleitosIds;
	private String[] qtdVotos;
	private String[] idCandidatoEleicaos;

	private String nomeBusca;
	private Collection<LinhaCedulaEleitoralRelatorio> cedulas;
	private Map<String, Object> parametros;
	
	private Colaborador colaborador;
	private Collection<Colaborador> colaboradors = new ArrayList<Colaborador>();
	
	private HistoricoColaborador historicoColaborador;
	private Collection<HistoricoColaborador> historicoColaboradors = new ArrayList<HistoricoColaborador>();
	private Date data;
	
	public String list() throws Exception
	{
		areasCheckList = areaOrganizacionalManager.populaCheckOrderDescricao(getEmpresaSistema().getId());
		candidatoEleicaos = candidatoEleicaoManager.getColaboradoresByEleicao(eleicao.getId(), getEmpresaSistema().getId());
		eleicao = eleicaoManager.findByIdProjection(eleicao.getId());
		data = new Date();
		
		return Action.SUCCESS;
	}

	public String listVotos() throws Exception
	{
		eleicao = eleicaoManager.findByIdProjection(eleicao.getId());
		candidatoEleicaos = candidatoEleicaoManager.findByEleicao(eleicao.getId());
		
		return Action.SUCCESS;
	}

	public String saveVotos() throws Exception
	{
		try
		{
			candidatoEleicaoManager.saveVotosEleicao(eleitosIds, qtdVotos, idCandidatoEleicaos, eleicao);
			addActionSuccess("Resultado gravado com sucesso.");
		}
		catch (Exception e)
		{
			addActionError("Erro ao gravar resultados.");
		}

		listVotos();
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		candidatoEleicaoManager.remove(candidatoEleicao.getId());
		list();

		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if (candidatoEleicao != null && candidatoEleicao.getId() != null)
			candidatoEleicao = (CandidatoEleicao) candidatoEleicaoManager.findById(candidatoEleicao.getId());
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try
		{
			candidatoEleicaoManager.save(candidatosCheck, eleicao);
		}
		catch (Exception e)
		{
			addActionError("Erro ao incluir Candidato.");
		}

		list();

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		candidatoEleicaoManager.update(candidatoEleicao);
		return Action.SUCCESS;
	}

	public String imprimirCedulas() throws Exception
	{
		try
		{
			list();
			parametros = RelatorioUtil.getParametrosRelatorio("", getEmpresaSistema(), "");
			cedulas = candidatoEleicaoManager.montaCedulas(candidatoEleicaos);

			return Action.SUCCESS;
		}
		catch (Exception e)
		{
			addActionError("Erro ao gerar cédulas.");
			e.printStackTrace();
			return Action.INPUT;
		}
	}
	
	public String imprimirComprovanteInscricao() throws Exception
	{
		eleicao = eleicaoManager.findByIdProjection(eleicao.getId());
		colaborador = colaboradorManager.findByIdDadosBasicos(candidatoEleicao.getCandidato().getId(), StatusRetornoAC.CONFIRMADO);
		
		parametros = RelatorioUtil.getParametrosRelatorio("", getEmpresaSistema(), "");
		candidatoEleicaos = new ArrayList<CandidatoEleicao>();
		candidatoEleicaos.add(null);//exibir os dados no pdf

		String descricao = eleicao.getDescricao()==null? "" : eleicao.getDescricao();
		String colaboradorMatricula = StringUtils.isBlank(colaborador.getMatricula()) ? "            " : colaborador.getMatricula();

		parametros.put("DESCRICAO", descricao);
		parametros.put("COLABORADOR", colaborador.getNome());
		parametros.put("MATRICULA", colaboradorMatricula);
		parametros.put("EMPRESA", getEmpresaSistema().getNome());

		return SUCCESS;
	}
	
	public String imprimirListaFrequencia() throws Exception {

		try {
		
			eleicao = eleicaoManager.findByIdProjection(eleicao.getId());
		
			Estabelecimento estabelecimento = eleicao.getEstabelecimento();
			Date votacaoIni = eleicao.getVotacaoIni();
			Date votacaoFim = eleicao.getVotacaoFim();
			
			historicoColaboradors = historicoColaboradorManager.findImprimirListaFrequencia(estabelecimento, votacaoIni, votacaoFim);
			if (historicoColaboradors.isEmpty())
				throw new FortesException("Não existem colaboradores neste estabelecimento para o período de votação.");				
			
			String filtro = eleicao.getDescricao() + " \n " + DateUtil.formataDiaMesAno(votacaoIni) + " à " + DateUtil.formataDiaMesAno(votacaoFim);
			
			parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Frequência ", getEmpresaSistema(), filtro);
		} catch (FortesException e) {
			addActionMessage(e.getMessage());
			e.printStackTrace();
			list();
			return Action.INPUT;
		} catch (Exception e) {
			addActionError("Erro ao gerar relatório: " + e.getMessage());
			e.printStackTrace();
			list();
			return Action.INPUT;
		}
		return SUCCESS;


	}

	public Collection<CandidatoEleicao> getCandidatoEleicaos()
	{
		return candidatoEleicaos;
	}

	public CandidatoEleicao getCandidatoEleicao()
	{
		if (candidatoEleicao == null)
		{
			candidatoEleicao = new CandidatoEleicao();
		}
		return candidatoEleicao;
	}

	public void setCandidatoEleicao(CandidatoEleicao candidatoEleicao)
	{
		this.candidatoEleicao = candidatoEleicao;
	}

	public Eleicao getEleicao()
	{
		return eleicao;
	}

	public void setEleicao(Eleicao eleicao)
	{
		this.eleicao = eleicao;
	}

	public Collection<CheckBox> getAreasCheckList()
	{
		return areasCheckList;
	}

	public String getNomeBusca()
	{
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = nomeBusca;
	}

	public void setCandidatosCheck(String[] candidatosCheck)
	{
		this.candidatosCheck = candidatosCheck;
	}

	public Collection<CheckBox> getCandidatosCheckList()
	{
		return candidatosCheckList;
	}

	public void setEleitosIds(String[] eleitosIds)
	{
		this.eleitosIds = eleitosIds;
	}

	public void setIdCandidatoEleicaos(String[] idCandidatoEleicaos)
	{
		this.idCandidatoEleicaos = idCandidatoEleicaos;
	}

	public void setQtdVotos(String[] qtdVotos)
	{
		this.qtdVotos = qtdVotos;
	}

	public Collection<LinhaCedulaEleitoralRelatorio> getCedulas()
	{
		return cedulas;
	}

	public Map<String, Object> getParametros()
	{
		return parametros;
	}

	public String getData() 
	{
		String dataString = DateUtil.formataDiaMesAno(data);
		return dataString;
	}

	public void setData(Date data) 
	{
		this.data = data;
	}

	public Collection<Colaborador> getColaboradors() {
		return colaboradors;
	}

	public Collection<HistoricoColaborador> getHistoricoColaboradors() {
		return historicoColaboradors;
	}

	public HistoricoColaborador getHistoricoColaborador() {
		return historicoColaborador;
	}
}