package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fortes.rh.business.sesmt.ExameManager;
import com.fortes.rh.business.sesmt.ExameSolicitacaoExameManager;
import com.fortes.rh.business.sesmt.SolicitacaoExameManager;
import com.fortes.rh.model.dicionario.MotivoSolicitacaoExame;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.dicionario.TipoPessoa;
import com.fortes.rh.model.dicionario.TipoRiscoSistema;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.fortes.web.tags.CheckBox;


/**
* @author Tiago Lopes
*/
public class SolicitacaoExameListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private SolicitacaoExameManager solicitacaoExameManager;
	private ExameSolicitacaoExameManager exameSolicitacaoExameManager;
	private ExameManager exameManager;

	private Collection<SolicitacaoExame> solicitacaoExames;
	private Collection<ExameSolicitacaoExame> exameSolicitacaoExames;
	private SolicitacaoExame solicitacaoExame;
	private Map<String, String> tiposRiscoSistema = TipoRiscoSistema.getInstance();
	
	private Date dataIni;
	private Date dataFim;
	private String vinculo = "TODOS";
	//private TipoPessoa vinculo = TipoPessoa.TODOS;
	private String nomeBusca;
	private String matriculaBusca;
	private String motivo = "";
	private String resultado = "";
	private Map<String, String> motivos;
	private String[] examesCheck = new String[]{};
	private Collection<CheckBox> examesCheckList = new ArrayList<CheckBox>();
	
	private static Integer QTD_MAX_EXAMES_POR_CLINICA_PARA_RELATORIOS_PEQUENOS = 8;

	public List<TipoPessoa> getVinculos()
	{
		return Arrays.asList(TipoPessoa.values());
	}
	
	public String getMotivoCONSULTA()
	{
		return MotivoSolicitacaoExame.CONSULTA; // p/ não deixar hardcoded no ftl
	}
	public String getMotivoATESTADO()
	{
		return MotivoSolicitacaoExame.ATESTADO;
	}
	
	public String list() throws Exception
	{
		motivos = MotivoSolicitacaoExame.getInstance();

		// usar o ww.select com enum
		ResultadoExame resultadoExame = null;
		if (StringUtils.isNotBlank(resultado))
			resultadoExame = ResultadoExame.valueOf(this.resultado);
		
		TipoPessoa vinculo = TipoPessoa.valueOf(String.valueOf(this.vinculo));
		
		examesCheckList = exameManager.populaCheckBox(getEmpresaSistema().getId());
		examesCheckList = CheckListBoxUtil.marcaCheckListBox(examesCheckList, examesCheck);

		setTotalSize(solicitacaoExameManager.getCount(getEmpresaSistema().getId(), dataIni, dataFim, vinculo, nomeBusca, matriculaBusca, motivo, examesCheck, resultadoExame));
		solicitacaoExames = solicitacaoExameManager.findAllSelect(getPage(), getPagingSize(), getEmpresaSistema().getId(), dataIni, dataFim, vinculo, nomeBusca, matriculaBusca, motivo, examesCheck, resultadoExame);

		if (solicitacaoExames == null || solicitacaoExames.isEmpty())
		{
			addActionMessage("Não há solicitação/atendimento médico a ser listado.");
			return SUCCESS;
		}

		CollectionUtil<SolicitacaoExame> util = new CollectionUtil<SolicitacaoExame>();
		exameSolicitacaoExames = exameSolicitacaoExameManager.findBySolicitacaoExame(util.convertCollectionToArrayIds(solicitacaoExames));
		
		setSolicitacoesSemExames();

		return SUCCESS;
	}

	private void setSolicitacoesSemExames(){
		for(SolicitacaoExame solicitacaoExame : solicitacaoExames) {
			solicitacaoExame.setExameSolicitacaoExames(new ArrayList<ExameSolicitacaoExame>());
			for(ExameSolicitacaoExame exameSolicitacaoExame : exameSolicitacaoExames) {
				if (exameSolicitacaoExame.getSolicitacaoExame().getId().equals(solicitacaoExame.getId())){
					solicitacaoExame.getExameSolicitacaoExames().add(exameSolicitacaoExame);
				}
			}
		}

		for(SolicitacaoExame solicitacaoExame : solicitacaoExames){
			if(solicitacaoExame.getExameSolicitacaoExames().size() > 0)
				solicitacaoExame.setSemExames(false);
			
			Map<Long, Integer> quantidadeDeExamesPorClinica = new HashMap<Long, Integer>(); 
			for(ExameSolicitacaoExame exameSolicitacaoExame : solicitacaoExame.getExameSolicitacaoExames()) {
				if(exameSolicitacaoExame.getClinicaAutorizada() != null && exameSolicitacaoExame.getClinicaAutorizada().getId() != null ){
					Long clinicaId = exameSolicitacaoExame.getClinicaAutorizada().getId();
					if(!quantidadeDeExamesPorClinica.containsKey(clinicaId))
						quantidadeDeExamesPorClinica.put(clinicaId, 0);

					quantidadeDeExamesPorClinica.put(clinicaId, quantidadeDeExamesPorClinica.get(clinicaId) + 1);
				}
			}
			
			for(Integer qtd : quantidadeDeExamesPorClinica.values())
				if(qtd > QTD_MAX_EXAMES_POR_CLINICA_PARA_RELATORIOS_PEQUENOS)
					solicitacaoExame.setMuitasClinicas(true);
		}
	}

	public String delete() throws Exception
	{
		solicitacaoExameManager.ajustaOrdemDoList(solicitacaoExame.getData(),solicitacaoExame.getOrdem());
		solicitacaoExameManager.remove(solicitacaoExame.getId());
		addActionSuccess("Solicitação/Atendimento Médico excluído com sucesso.");

		return SUCCESS;
	}

	public Collection<SolicitacaoExame> getSolicitacaoExames() {
		return solicitacaoExames;
	}

	public SolicitacaoExame getSolicitacaoExame(){
		if(solicitacaoExame == null){
			solicitacaoExame = new SolicitacaoExame();
		}
		return solicitacaoExame;
	}

	public void setSolicitacaoExame(SolicitacaoExame solicitacaoExame){
		this.solicitacaoExame=solicitacaoExame;
	}

	public void setSolicitacaoExameManager(SolicitacaoExameManager solicitacaoExameManager){
		this.solicitacaoExameManager=solicitacaoExameManager;
	}

	public Date getDataFim()
	{
		return dataFim;
	}

	public void setDataFim(Date dataFim)
	{
		this.dataFim = dataFim;
	}

	public Date getDataIni()
	{
		return dataIni;
	}

	public void setDataIni(Date dataIni)
	{
		this.dataIni = dataIni;
	}

	public String getMatriculaBusca()
	{
		return matriculaBusca;
	}

	public void setMatriculaBusca(String matriculaBusca)
	{
		this.matriculaBusca = matriculaBusca;
	}

	public String getNomeBusca()
	{
		return nomeBusca;
	}

	public void setNomeBusca(String nomeBusca)
	{
		this.nomeBusca = nomeBusca;
	}

	public String getMotivo()
	{
		return motivo;
	}

	public void setMotivo(String motivo)
	{
		this.motivo = motivo;
	}

	public Map<String, String> getMotivos()
	{
		return motivos;
	}

	public String getVinculo()
	{
		return vinculo;
	}

	public void setVinculo(String vinculo)
	{
		this.vinculo = vinculo;
	}

	public Collection<ExameSolicitacaoExame> getExameSolicitacaoExames()
	{
		return exameSolicitacaoExames;
	}

	public void setExameSolicitacaoExameManager(ExameSolicitacaoExameManager exameSolicitacaoExameManager)
	{
		this.exameSolicitacaoExameManager = exameSolicitacaoExameManager;
	}

	public String[] getExamesCheck() {
		return examesCheck;
	}

	public void setExamesCheck(String[] examesCheck) {
		this.examesCheck = examesCheck;
	}

	public Collection<CheckBox> getExamesCheckList() {
		return examesCheckList;
	}

	public void setExameManager(ExameManager exameManager) {
		this.exameManager = exameManager;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Map<String, String> getTiposRiscoSistema() {
		return tiposRiscoSistema;
	}
}