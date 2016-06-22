package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.sesmt.OrdemDeServicoDao;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.model.sesmt.RiscoFuncao;

public class OrdemDeServicoManagerImpl extends GenericManagerImpl<OrdemDeServico, OrdemDeServicoDao> implements OrdemDeServicoManager
{
	private ColaboradorManager colaboradorManager;
	private RiscoFuncaoManager riscoFuncaoManager;
	private CursoManager cursoManager;
	private EpiManager epiManager;
	
	public OrdemDeServico findOrdemServicoProjection(Long id){
		return getDao().findOrdemServicoProjection(id);
	}
	
	public OrdemDeServico montaOrdemDeServico(OrdemDeServico ordemDeServico, Colaborador colaborador, Empresa empresa, Date dataOdemDeServico) {
		
		if(ordemDeServico != null && ordemDeServico.getId() != null){
			ordemDeServico = getDao().findOrdemServicoProjection(ordemDeServico.getId());
			if (!ordemDeServico.isImpressa()){
				colaborador = colaboradorManager.findComDadosBasicosParaOrdemDeServico(ordemDeServico.getColaborador(), dataOdemDeServico);
				montaDadosProvenientesDoColaborador(ordemDeServico, colaborador);
				montaDadosProvenienteDaEmpresa(ordemDeServico, empresa);
			}
		}
		else{
			ordemDeServico = new OrdemDeServico();
			colaborador = colaboradorManager.findComDadosBasicosParaOrdemDeServico(colaborador, dataOdemDeServico);
			ordemDeServico.setAtividades(colaborador.getFuncao().getHistoricoAtual().getDescricao());
			montaDadosProvenientesDoColaborador(ordemDeServico, colaborador);
			montaDadosProvenienteDaEmpresa(ordemDeServico, empresa);
			montaListaRiscosEMedidasPreventivas(ordemDeServico, colaborador.getFuncao().getHistoricoAtual()); 
			montaListaEpi(ordemDeServico, colaborador.getFuncao().getHistoricoAtual());
			montaListaTreinamentos(ordemDeServico, colaborador.getFuncao().getHistoricoAtual());
		}
		return ordemDeServico;
	}

	private void montaDadosProvenienteDaEmpresa(OrdemDeServico ordemDeServico, Empresa empresa) {
		ordemDeServico.setNormasInternas(empresa.getNormasInternas());
		ordemDeServico.setProcedimentoEmCasoDeAcidente(empresa.getProcedimentoEmCasoDeAcidente());
		ordemDeServico.setTermoDeResponsabilidade(empresa.getTermoDeResponsabilidade());
	}

	private void montaDadosProvenientesDoColaborador(OrdemDeServico ordemDeServico, Colaborador colaborador) {
		ordemDeServico.setColaboradorId(colaborador.getId());
		ordemDeServico.setNomeColaborador(colaborador.getNome());
		ordemDeServico.setDataAdmisaoColaborador(colaborador.getDataAdmissao());
		ordemDeServico.setCodigoCBO(colaborador.getFaixaSalarial().getCargo().getCboCodigo());
		ordemDeServico.setNomeFuncao(colaborador.getFuncaoNome());
	}

	private void montaListaRiscosEMedidasPreventivas(OrdemDeServico ordemDeServico, HistoricoFuncao historicoAtual) {
		List<RiscoFuncao> riscosFuncao = riscoFuncaoManager.riscosByHistoricoFuncao(historicoAtual);
		String riscosDaOperacao = "";
		String medidasPreventivas = "";
		
		for (RiscoFuncao riscoFuncao : riscosFuncao){ 
			riscosDaOperacao += "- " + riscoFuncao.getRisco().getDescricao() + "\n";
			medidasPreventivas += "- " + riscoFuncao.getMedidaDeSeguranca() + "\n";
		}
		ordemDeServico.setRiscos(riscosDaOperacao);
		ordemDeServico.setMedidasPreventivas(medidasPreventivas);
	}
	
	private void montaListaTreinamentos(OrdemDeServico ordemDeServico, HistoricoFuncao historicoFuncao) {
		Collection<Curso> cursos = cursoManager.findByHistoricoFuncaoId(historicoFuncao.getId());
		String nomeTreinamento = "";
		for (Curso curso : cursos) 
			nomeTreinamento += "- " + curso.getNome() + "\n";
		
		ordemDeServico.setTreinamentos(nomeTreinamento);
	}

	private void montaListaEpi(OrdemDeServico ordemDeServico, HistoricoFuncao historicoFuncao) {
		 Collection<Epi> epis = epiManager.findByHistoricoFuncao(historicoFuncao.getId());
		 String nomeEpis = "";
		 for (Epi epi : epis) 
			nomeEpis += "- " + epi.getNome() + "\n";
		 ordemDeServico.setEpis(nomeEpis);
	}

	public OrdemDeServico findUltimaOrdemDeServico(Long colaboradorId) {
		return getDao().ultimaOrdemDeServico(colaboradorId);
	}
	
	public OrdemDeServico findUltimaOrdemDeServicoImpressa(Long colaboradorId) {
		return getDao().findUltimaOrdemDeServicoImpressa(colaboradorId);
	}
	
	public void setColaboradorManager(ColaboradorManager colaboradorManager) {
		this.colaboradorManager = colaboradorManager;
	}
	
	public void setRiscoFuncaoManager(RiscoFuncaoManager riscoFuncaoManager) {
		this.riscoFuncaoManager = riscoFuncaoManager;
	}

	public void setEpiManager(EpiManager epiManager) {
		this.epiManager = epiManager;
	}

	public CursoManager getCursoManager() {
		return cursoManager;
	}

	public void setCursoManager(CursoManager cursoManager) {
		this.cursoManager = cursoManager;
	}
}