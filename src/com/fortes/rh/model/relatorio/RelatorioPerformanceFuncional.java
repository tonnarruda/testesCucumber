package com.fortes.rh.model.relatorio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.captacao.CandidatoIdiomaManager;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.ExperienciaManager;
import com.fortes.rh.business.captacao.FormacaoManager;
import com.fortes.rh.business.captacao.HistoricoCandidatoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.HistoricoColaboradorManager;
import com.fortes.rh.business.cargosalario.IndiceHistoricoManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.CamposExtrasManager;
import com.fortes.rh.business.geral.CidadeManager;
import com.fortes.rh.business.geral.ColaboradorIdiomaManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.ColaboradorOcorrenciaManager;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.business.geral.DocumentoAnexoManager;
import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.geral.EstadoManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.business.pesquisa.ColaboradorQuestionarioManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.CatManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.business.sesmt.ComissaoManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.model.captacao.Candidato;
import com.fortes.rh.model.captacao.CandidatoIdioma;
import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.dicionario.OrigemAnexo;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Beneficio;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.Cidade;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.geral.Estado;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.web.ws.AcPessoalClientSistema;

public class RelatorioPerformanceFuncional {

	private Colaborador colaborador;
	private Collection<ColaboradorQuestionario> avaliacaoExperiencias;
	private Collection<ColaboradorQuestionario> avaliacaoDesempenhos;
	private Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
	private Collection<HistoricoColaborador> historicoColaboradors;
	private HistoricoColaborador historicoColaborador;
	private Collection<ColaboradorIdioma> idiomasColaborador;
	private Collection<ColaboradorTurma> cursosColaborador;
	private Collection<ColaboradorOcorrencia> ocorrenciasColaborador;
	private Collection<ColaboradorAfastamento> afastamentosColaborador;
	private Collection<DocumentoAnexo> documentoAnexosColaborador;
	private Collection<DocumentoAnexo> documentoAnexosCandidato;
	private Collection<HistoricoCandidato> historicosCandidatoByColaborador;
	private Collection<Cat> catsColaborador;
	private Collection<ParticipacaoColaboradorCipa> participacoesNaCipaColaborador;
	private Collection<Formacao> formacaos;
	private Collection<AreaOrganizacional> areaOrganizacionals;


	public RelatorioPerformanceFuncional(Colaborador colaborador, Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras, 
			Collection<ColaboradorQuestionario> avaliacaoDesempenhos,Collection<ColaboradorQuestionario> avaliacaoExperiencias,
			Collection<HistoricoColaborador> historicoColaboradors, HistoricoColaborador historicoColaborador,
			Collection<ColaboradorIdioma> idiomasColaborador, Collection<Formacao> formacaos, Collection<ColaboradorTurma> cursosColaborador,
			Collection<ColaboradorOcorrencia> ocorrenciasColaborador, Collection<ColaboradorAfastamento> afastamentosColaborador, 
			Collection<DocumentoAnexo> documentoAnexosColaborador, Collection<DocumentoAnexo> documentoAnexosCandidato, 
			Collection<HistoricoCandidato> historicosCandidatoByColaborador, Collection<Cat> catsColaborador, 
			Collection<ParticipacaoColaboradorCipa> participacoesNaCipaColaborador,	Collection<AreaOrganizacional> areaOrganizacionals) {
		
		this.colaborador = colaborador;
		this.configuracaoCampoExtras = configuracaoCampoExtras;
		this.avaliacaoDesempenhos = avaliacaoDesempenhos;
		this.avaliacaoExperiencias = avaliacaoExperiencias;
		this.historicoColaboradors = historicoColaboradors;
		this.historicoColaborador = historicoColaborador;
		this.idiomasColaborador = idiomasColaborador;
		this.formacaos = formacaos;
		this.cursosColaborador = cursosColaborador;
		this.ocorrenciasColaborador = ocorrenciasColaborador;
		this.afastamentosColaborador = afastamentosColaborador;
		this.documentoAnexosColaborador = documentoAnexosColaborador;
		this.documentoAnexosCandidato = documentoAnexosCandidato;
		this.historicosCandidatoByColaborador = historicosCandidatoByColaborador;
		this.catsColaborador = catsColaborador;
		this.participacoesNaCipaColaborador = participacoesNaCipaColaborador;
		this.areaOrganizacionals = areaOrganizacionals;
	}


	public Colaborador getColaborador() {
		return colaborador;
	}


	public Collection<ColaboradorQuestionario> getAvaliacaoExperiencias() {
		return avaliacaoExperiencias;
	}


	public Collection<ColaboradorQuestionario> getAvaliacaoDesempenhos() {
		return avaliacaoDesempenhos;
	}


	public Collection<ConfiguracaoCampoExtra> getConfiguracaoCampoExtras() {
		return configuracaoCampoExtras;
	}


	public Collection<HistoricoColaborador> getHistoricoColaboradors() {
		return historicoColaboradors;
	}


	public HistoricoColaborador getHistoricoColaborador() {
		return historicoColaborador;
	}


	public Collection<ColaboradorIdioma> getIdiomasColaborador() {
		return idiomasColaborador;
	}


	public Collection<ColaboradorTurma> getCursosColaborador() {
		return cursosColaborador;
	}


	public Collection<ColaboradorOcorrencia> getOcorrenciasColaborador() {
		return ocorrenciasColaborador;
	}


	public Collection<ColaboradorAfastamento> getAfastamentosColaborador() {
		return afastamentosColaborador;
	}


	public Collection<DocumentoAnexo> getDocumentoAnexosColaborador() {
		return documentoAnexosColaborador;
	}


	public Collection<DocumentoAnexo> getDocumentoAnexosCandidato() {
		return documentoAnexosCandidato;
	}


	public Collection<HistoricoCandidato> getHistoricosCandidatoByColaborador() {
		return historicosCandidatoByColaborador;
	}


	public Collection<Cat> getCatsColaborador() {
		return catsColaborador;
	}


	public Collection<ParticipacaoColaboradorCipa> getParticipacoesNaCipaColaborador() {
		return participacoesNaCipaColaborador;
	}


	public Collection<Formacao> getFormacaos() {
		return formacaos;
	}


	public Collection<AreaOrganizacional> getAreaOrganizacionals() {
		return areaOrganizacionals;
	}
}
