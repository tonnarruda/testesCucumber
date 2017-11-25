package com.fortes.rh.web.action.sesmt;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.FuncaoManager;
import com.fortes.rh.business.sesmt.HistoricoAmbienteManager;
import com.fortes.rh.business.sesmt.MedicaoRiscoManager;
import com.fortes.rh.business.sesmt.RiscoAmbienteManager;
import com.fortes.rh.business.sesmt.RiscoFuncaoManager;
import com.fortes.rh.business.sesmt.RiscoMedicaoRiscoManager;
import com.fortes.rh.model.dicionario.LocalAmbiente;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.Funcao;
import com.fortes.rh.model.sesmt.HistoricoAmbiente;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.web.action.MyActionSupportList;

public class MedicaoRiscoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private MedicaoRiscoManager medicaoRiscoManager;
	private AmbienteManager ambienteManager;
	private FuncaoManager funcaoManager;
	private EstabelecimentoManager estabelecimentoManager;
	private RiscoAmbienteManager riscoAmbienteManager;
	private RiscoFuncaoManager riscoFuncaoManager;
	private RiscoMedicaoRiscoManager riscoMedicaoRiscoManager;
	private HistoricoAmbienteManager historicoAmbienteManager;
	
	private Ambiente ambiente = new Ambiente();
	private Funcao funcao = new Funcao();
	private MedicaoRisco medicaoRisco = new MedicaoRisco();
	private Risco risco;
	private Estabelecimento estabelecimento;
	private Integer localAmbiente;
	
	private char controlaRiscoPor;
	
	private Date data;
	
	private String[] ltcatValues;
	private String[] ppraValues;
	private String[] tecnicaValues;
	private String[] intensidadeValues;
	private String[] riscoIds;
	
	private String tecnicasUtilizadas; 	// Auto complete
	
	private boolean desabilitarGravar = true;
	
	private Collection<Ambiente> ambientes;
	private Collection<Funcao> funcoes;
	private Collection<MedicaoRisco> medicaoRiscos;
	private Collection<Estabelecimento> estabelecimentos;
	private Collection<Risco> riscos;
	private Collection<RiscoMedicaoRisco> riscoMedicaoRiscos = new ArrayList<RiscoMedicaoRisco>();
	private Map<Integer, String> locaisAmbiente;

	private void prepare() throws Exception
	{
		tecnicasUtilizadas = medicaoRiscoManager.getTecnicasUtilizadas(getEmpresaSistema().getId());
		
		if (controlaRiscoPor == 'A'){
			locaisAmbiente = LocalAmbiente.mapLocalAmbiente();
			estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		}
		else if (controlaRiscoPor == 'F')
			funcoes = funcaoManager.findByEmpresa(getEmpresaSistema().getId());
			
		if (medicaoRisco.getId() != null)
		{
			if (controlaRiscoPor == 'A')
			{
				medicaoRisco = medicaoRiscoManager.findById(medicaoRisco.getId());
			}else  if (controlaRiscoPor == 'F')
			{
				medicaoRisco = medicaoRiscoManager.getMedicaoRiscoMedicaoPorFuncao(medicaoRisco.getId());
				medicaoRisco.setRiscoMedicaoRiscos(medicaoRiscoManager.findRiscoMedicaoRiscos(medicaoRisco.getId()));
			}
			
		}
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return SUCCESS;
	}
	
	public String carregarRiscos() throws Exception
	{
		populaRiscos();
		riscoMedicaoRiscos = medicaoRiscoManager.preparaRiscosDaMedicao(medicaoRisco, riscos);
		return SUCCESS;
	}

	private void populaRiscos() throws Exception 
	{
		prepare();
		desabilitarGravar = false;
		if (controlaRiscoPor == 'A')
		{
			ambientes = ambienteManager.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(getEmpresaSistema().getId(), estabelecimento.getId(), localAmbiente, data);
			riscos = riscoAmbienteManager.findRiscosByAmbienteData(ambiente.getId(), data);
		} 
		else  if (controlaRiscoPor == 'F')
		{
			riscos = riscoFuncaoManager.findRiscosByFuncaoData(funcao.getId(), data);
		}
	}

	public String carregarRiscosComMedicao() throws Exception
	{
		populaRiscos();
		String contexto = "";

		if (controlaRiscoPor == 'A')
		{
			riscoMedicaoRiscos = riscoMedicaoRiscoManager.findMedicoesDeRiscosDoAmbiente(ambiente.getId(), data);
			contexto = "este Ambiente";
		}
		else if (controlaRiscoPor == 'F')
		{
			riscoMedicaoRiscos = riscoMedicaoRiscoManager.findMedicoesDeRiscosDaFuncao(funcao.getId(), data);
			contexto = "esta Função";
		}
		
		if (riscoMedicaoRiscos.isEmpty())
			addActionMessage("Não há medição anterior para " + contexto);

		medicaoRisco.setRiscoMedicaoRiscos(riscoMedicaoRiscos);
		riscoMedicaoRiscos = medicaoRiscoManager.preparaRiscosDaMedicao(medicaoRisco, riscos);
		
		return SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		
		if (controlaRiscoPor == 'A')
		{
			ambiente = medicaoRisco.getAmbiente();
			HistoricoAmbiente historicoAmbiente = historicoAmbienteManager.findUltimoHistoricoAteData(ambiente.getId(), medicaoRisco.getData());
			estabelecimento = historicoAmbiente.getEstabelecimento(); 
			setLocalAmbiente(historicoAmbiente.getLocalAmbiente());
			ambientes = ambienteManager.findAmbientesPorEstabelecimentoOrAmbientesDeTerceiro(getEmpresaSistema().getId(), estabelecimento.getId(), historicoAmbiente.getLocalAmbiente(), medicaoRisco.getData());
			riscos = riscoAmbienteManager.findRiscosByAmbienteData(medicaoRisco.getAmbiente().getId(), medicaoRisco.getData());
		}
		else  if (controlaRiscoPor == 'F')
		{
			funcao = medicaoRisco.getFuncao();
			riscos = riscoFuncaoManager.findRiscosByFuncaoData(medicaoRisco.getFuncao().getId(), medicaoRisco.getData());
		}
		
		data = medicaoRisco.getData();
		desabilitarGravar = false;
		riscoMedicaoRiscos = medicaoRiscoManager.preparaRiscosDaMedicao(medicaoRisco, riscos);
		
		return SUCCESS;
	}

	public String insert() throws Exception
	{
		try 
		{
			medicaoRisco.setData(data);
			
			if (controlaRiscoPor == 'A')
				medicaoRisco.setAmbiente(ambiente);
			else if (controlaRiscoPor == 'F')
				medicaoRisco.setFuncao(funcao);
			
			medicaoRiscoManager.save(medicaoRisco, riscoIds, ltcatValues, ppraValues, tecnicaValues, intensidadeValues);
			addActionSuccess("Medição de risco gravada com sucesso.");
		}
		catch (Exception e) 
		{
			addActionError("Não foi possível gravar esta medição de risco.");
			prepareInsert();
			return INPUT;
		}
		return SUCCESS;
	}

	public String update() throws Exception
	{
		try 
		{
			medicaoRisco.setData(data);
			
			if (controlaRiscoPor == 'A')
				medicaoRisco.setAmbiente(ambiente);
			else if (controlaRiscoPor == 'F')
				medicaoRisco.setFuncao(funcao);
			
			medicaoRiscoManager.save(medicaoRisco, riscoIds, ltcatValues, ppraValues, tecnicaValues, intensidadeValues);
			addActionSuccess("Medição de risco atualizada com sucesso.");
		}
		catch (Exception e) 
		{
			addActionError("Não foi possível gravar esta medição de risco.");
			prepareUpdate();
			return INPUT;
		}
		return SUCCESS;
	}
	
	public String list() throws Exception
	{
		if (controlaRiscoPor == 'A') {
			ambientes = ambienteManager.findAmbientes(getEmpresaSistema().getId());
			medicaoRiscos = medicaoRiscoManager.findAllSelectByAmbiente(getEmpresaSistema().getId(), getAmbiente().getId());
		} else if (controlaRiscoPor == 'F') {
			funcoes = funcaoManager.findByEmpresa(getEmpresaSistema().getId());
			medicaoRiscos = medicaoRiscoManager.findAllSelectByFuncao(getEmpresaSistema().getId(), getFuncao().getId());
		}
	
		return SUCCESS;
	}

	public String delete() throws Exception
	{
		medicaoRiscoManager.removeCascade(medicaoRisco.getId());
		addActionSuccess("Medição excluída com sucesso.");

		return SUCCESS;
	}
	
	public MedicaoRisco getMedicaoRisco()
	{
		if(medicaoRisco == null)
			medicaoRisco = new MedicaoRisco();
		
		return medicaoRisco;
	}

	public void setMedicaoRisco(MedicaoRisco medicaoRisco)
	{
		this.medicaoRisco = medicaoRisco;
	}

	public void setMedicaoRiscoManager(MedicaoRiscoManager medicaoRiscoManager)
	{
		this.medicaoRiscoManager = medicaoRiscoManager;
	}
	
	public Collection<MedicaoRisco> getMedicaoRiscos()
	{
		return medicaoRiscos;
	}

	public void setAmbienteManager(AmbienteManager ambienteManager) {
		this.ambienteManager = ambienteManager;
	}

	public Collection<Ambiente> getAmbientes() {
		return ambientes;
	}

	public void setAmbientes(Collection<Ambiente> ambientes) {
		this.ambientes = ambientes;
	}

	public Ambiente getAmbiente() 
	{
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}

	public void setEstabelecimentoManager(
			EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

	public Collection<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public Risco getRisco() {
		return risco;
	}

	public void setRisco(Risco risco) {
		this.risco = risco;
	}

	public String[] getLtcatValues() {
		return ltcatValues;
	}

	public void setLtcatValues(String[] ltcatValues) {
		this.ltcatValues = ltcatValues;
	}

	public String[] getPpraValues() {
		return ppraValues;
	}

	public void setPpraValues(String[] ppraValues) {
		this.ppraValues = ppraValues;
	}

	public String[] getTecnicaValues() {
		return tecnicaValues;
	}

	public void setTecnicaValues(String[] tecnicaValues) {
		this.tecnicaValues = tecnicaValues;
	}

	public void setRiscoAmbienteManager(RiscoAmbienteManager riscoAmbienteManager) {
		this.riscoAmbienteManager = riscoAmbienteManager;
	}

	public Collection<Risco> getRiscos() {
		return riscos;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public String[] getIntensidadeValues() {
		return intensidadeValues;
	}

	public void setIntensidadeValues(String[] intensidadeValues) {
		this.intensidadeValues = intensidadeValues;
	}

	public String[] getRiscoIds() {
		return riscoIds;
	}

	public void setRiscoIds(String[] riscoIds) {
		this.riscoIds = riscoIds;
	}

	public String getTecnicasUtilizadas() {
		return tecnicasUtilizadas;
	}

	public Collection<RiscoMedicaoRisco> getRiscoMedicaoRiscos() {
		return riscoMedicaoRiscos;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isDesabilitarGravar() {
		return desabilitarGravar;
	}

	public void setDesabilitarGravar(boolean desabilitarGravar) {
		this.desabilitarGravar = desabilitarGravar;
	}

	public Collection<Funcao> getFuncoes() {
		return funcoes;
	}

	public Funcao getFuncao() {
		if (funcao == null)
			funcao = new Funcao();
			
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public char getControlaRiscoPor() {
		return controlaRiscoPor;
	}
	
	public void setControlaRiscoPor(char controlaRiscoPor) {
		this.controlaRiscoPor = controlaRiscoPor;
	}

	public void setFuncaoManager(FuncaoManager funcaoManager) {
		this.funcaoManager = funcaoManager;
	}

	public void setRiscoFuncaoManager(RiscoFuncaoManager riscoFuncaoManager) {
		this.riscoFuncaoManager = riscoFuncaoManager;
	}

	public void setRiscoMedicaoRiscoManager(
			RiscoMedicaoRiscoManager riscoMedicaoRiscoManager) {
		this.riscoMedicaoRiscoManager = riscoMedicaoRiscoManager;
	}

	public Map<Integer, String> getLocaisAmbiente() {
		return locaisAmbiente;
	}

	public Integer getLocalAmbiente() {
		return localAmbiente;
	}

	public void setLocalAmbiente(Integer localAmbiente) {
		this.localAmbiente = localAmbiente;
	}

	public void setHistoricoAmbienteManager(
			HistoricoAmbienteManager historicoAmbienteManager) {
		this.historicoAmbienteManager = historicoAmbienteManager;
	}
}
