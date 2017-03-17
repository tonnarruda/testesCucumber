package com.fortes.rh.business.sesmt;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.desenvolvimento.CursoManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.sesmt.OrdemDeServicoDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.Epi;
import com.fortes.rh.model.sesmt.HistoricoFuncao;
import com.fortes.rh.model.sesmt.OrdemDeServico;
import com.fortes.rh.model.sesmt.RiscoFuncao;
import com.fortes.rh.util.DateUtil;

@Component
public class OrdemDeServicoManagerImpl extends GenericManagerImpl<OrdemDeServico, OrdemDeServicoDao> implements OrdemDeServicoManager
{
	@Autowired private HistoricoFuncaoManager historicoFuncaoManager;
	@Autowired private ColaboradorManager colaboradorManager;
	@Autowired private RiscoFuncaoManager riscoFuncaoManager;
	@Autowired private CursoManager cursoManager;
	@Autowired private EpiManager epiManager;

	@Autowired
	OrdemDeServicoManagerImpl(OrdemDeServicoDao fooDao) {
		setDao(fooDao);
	}
	
	public OrdemDeServico findOrdemServicoProjection(Long id){
		return getDao().findOrdemServicoProjection(id);
	}
	
	public void auditaImpressao(OrdemDeServico ordemDeServico, Usuario usuarioLogado) {
		Logger logger = Logger.getLogger(OrdemDeServico.class);
		logger.info("Auditoria da impressão da Ordem de Serviço");
		logger.info("Colaborador: " + ordemDeServico.getNomeColaborador());
		logger.info("Empresa: " + ordemDeServico.getNomeEmpresa());
		logger.info("Data da impressão: " + DateUtil.formataDiaMesAno(new Date()));
		logger.info("Usuário que execultou a impressão: " + usuarioLogado.getNome());
	}
	
	public OrdemDeServico montaOrdemDeServico(Long colaboradorId, Empresa empresa, Date dataOdemDeServico) {
		Colaborador colaborador = colaboradorManager.findComDadosBasicosParaOrdemDeServico(colaboradorId, dataOdemDeServico);
		OrdemDeServico ordemDeServico = new OrdemDeServico();
		montaDadosProvenientesDoColaborador(ordemDeServico, colaborador);
		montaDadosProvenienteDaEmpresa(ordemDeServico, empresa);
		
		if(colaborador.getFuncao() != null && colaborador.getFuncao().getId() !=null){
			HistoricoFuncao historicoFuncao = historicoFuncaoManager.findByFuncaoAndData(colaborador.getFuncao().getId(), dataOdemDeServico);
			if(historicoFuncao != null && historicoFuncao.getId() != null){
				ordemDeServico.setAtividades(historicoFuncao.getDescricao());
				ordemDeServico.setNormasInternas(historicoFuncao.getNormasInternas());
				montaListaRiscosEMedidasPreventivas(ordemDeServico, historicoFuncao); 
				montaListaEpi(ordemDeServico, historicoFuncao);
				montaListaTreinamentos(ordemDeServico, historicoFuncao);
			}		
		}
		
		return ordemDeServico;
	}

	private void montaDadosProvenienteDaEmpresa(OrdemDeServico ordemDeServico, Empresa empresa) {
		ordemDeServico.setProcedimentoEmCasoDeAcidente(empresa.getProcedimentoEmCasoDeAcidente());
		ordemDeServico.setTermoDeResponsabilidade(empresa.getTermoDeResponsabilidade());
		ordemDeServico.setEmpresaCnpj(empresa.getCnpj());
		ordemDeServico.setNomeEmpresa(empresa.getNome());
	}

	private void montaDadosProvenientesDoColaborador(OrdemDeServico ordemDeServico, Colaborador colaborador) {
		ordemDeServico.setColaboradorId(colaborador.getId());
		ordemDeServico.setNomeColaborador(colaborador.getNome());
		ordemDeServico.setDataAdmisaoColaborador(colaborador.getDataAdmissao());
		ordemDeServico.setCodigoCBO(colaborador.getFaixaSalarial().getCargo().getCboCodigo());
		ordemDeServico.setNomeCargo(colaborador.getFaixaSalarial().getCargo().getNome());
		ordemDeServico.setNomeFuncao(colaborador.getFuncaoNome());
		ordemDeServico.setNomeEstabelecimento(colaborador.getEstabelecimentoNome());
		ordemDeServico.setEstabelecimentoComplementoCnpj(colaborador.getEstabelecimento().getComplementoCnpj());
		ordemDeServico.setEstabelecimentoEndereco(colaborador.getEstabelecimento().getEndereco().getEnderecoCompletoFormatado());
	}
	
	private void montaListaRiscosEMedidasPreventivas(OrdemDeServico ordemDeServico, HistoricoFuncao historicoFuncao) {
		List<RiscoFuncao> riscosFuncao = riscoFuncaoManager.riscosByHistoricoFuncao(historicoFuncao);
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
}