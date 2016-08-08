package com.fortes.rh.model.relatorio;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.model.captacao.Experiencia;
import com.fortes.rh.model.captacao.Formacao;
import com.fortes.rh.model.captacao.HistoricoCandidato;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.ColaboradorIdioma;
import com.fortes.rh.model.geral.ColaboradorOcorrencia;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.model.geral.DocumentoAnexo;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.model.sesmt.Cat;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.util.CampoExtraUtil;

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
	private Collection<Experiencia> experiencias;


	public RelatorioPerformanceFuncional(Colaborador colaborador, Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras, 
			Collection<ColaboradorQuestionario> avaliacaoDesempenhos,Collection<ColaboradorQuestionario> avaliacaoExperiencias,
			Collection<HistoricoColaborador> historicoColaboradors, HistoricoColaborador historicoColaborador,
			Collection<ColaboradorIdioma> idiomasColaborador, Collection<Formacao> formacaos, Collection<ColaboradorTurma> cursosColaborador,
			Collection<ColaboradorOcorrencia> ocorrenciasColaborador, Collection<ColaboradorAfastamento> afastamentosColaborador, 
			Collection<DocumentoAnexo> documentoAnexosColaborador, Collection<DocumentoAnexo> documentoAnexosCandidato, 
			Collection<HistoricoCandidato> historicosCandidatoByColaborador, Collection<Cat> catsColaborador, 
			Collection<ParticipacaoColaboradorCipa> participacoesNaCipaColaborador,	Collection<AreaOrganizacional> areaOrganizacionals, Collection<Experiencia> experiencias) {
		
		this.colaborador = colaborador;
		this.configuracaoCampoExtras = CampoExtraUtil.preencheConteudoCampoExtra(colaborador.getCamposExtras(),  configuracaoCampoExtras);
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
		this.experiencias = experiencias;
	}

	public RelatorioPerformanceFuncional() {
		// TODO Auto-generated constructor stub
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
	
	public Collection<Experiencia> getExperiencias() {
		return experiencias;
	}

	public Boolean isCamposExtras() {
		return configuracaoCampoExtras.size() > 0;
	}
}
