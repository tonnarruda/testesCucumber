package com.fortes.rh.web.action.sesmt;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.AmbienteManager;
import com.fortes.rh.business.sesmt.MedicaoRiscoManager;
import com.fortes.rh.business.sesmt.RiscoAmbienteManager;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.Ambiente;
import com.fortes.rh.model.sesmt.MedicaoRisco;
import com.fortes.rh.model.sesmt.Risco;
import com.fortes.rh.model.sesmt.RiscoMedicaoRisco;
import com.fortes.rh.web.action.MyActionSupportList;

public class MedicaoRiscoEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private MedicaoRiscoManager medicaoRiscoManager;
	private AmbienteManager ambienteManager;
	private EstabelecimentoManager estabelecimentoManager;
	private RiscoAmbienteManager riscoAmbienteManager;
	
	private Ambiente ambiente;
	private MedicaoRisco medicaoRisco;
	private Risco risco;
	private Estabelecimento estabelecimento;
	
	private Date data;
	
	private String[] ltcatValues;
	private String[] ppraValues;
	private String[] tecnicaValues;
	private String[] intensidadeValues;
	private String[] riscoIds;
	
	private String tecnicasUtilizadas; 	// Auto complete
	
	private boolean desabilitarGravar = true;
	
	private Collection<Ambiente> ambientes;
	private Collection<MedicaoRisco> medicaoRiscos;
	private Collection<Estabelecimento> estabelecimentos;
	private Collection<Risco> riscos;
	private Collection<RiscoMedicaoRisco> riscoMedicaoRiscos = new ArrayList<RiscoMedicaoRisco>();

	private void prepare() throws Exception
	{
		tecnicasUtilizadas = medicaoRiscoManager.getTecnicasUtilizadas(getEmpresaSistema().getId());
		estabelecimentos = estabelecimentoManager.findAllSelect(getEmpresaSistema().getId());
		
		if (getMedicaoRisco().getId() != null)
		{
			medicaoRisco = medicaoRiscoManager.findById(medicaoRisco.getId());
			estabelecimento = medicaoRisco.getAmbiente().getEstabelecimento();
		}
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return SUCCESS;
	}
	
	public String carregarRiscos() throws Exception
	{
		prepare();
		
		desabilitarGravar = false;
		
		ambientes = ambienteManager.findByEstabelecimento(estabelecimento.getId());
		riscos = riscoAmbienteManager.findRiscosByAmbienteData(ambiente.getId(), data);
		riscoMedicaoRiscos = medicaoRiscoManager.preparaRiscosDaMedicao(medicaoRisco, riscos);
		return SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		
		data = medicaoRisco.getData();
		ambiente = medicaoRisco.getAmbiente();
		
		desabilitarGravar = false;
		
		ambientes = ambienteManager.findByEstabelecimento(estabelecimento.getId());
		riscos = riscoAmbienteManager.findRiscosByAmbienteData(medicaoRisco.getAmbiente().getId(), medicaoRisco.getData());
		riscoMedicaoRiscos = medicaoRiscoManager.preparaRiscosDaMedicao(medicaoRisco, riscos);
		
		return SUCCESS;
	}

	public String insert() throws Exception
	{
		try 
		{
			medicaoRisco.setData(data);
			medicaoRisco.setAmbiente(ambiente);
			
			medicaoRiscoManager.save(medicaoRisco, riscoIds, ltcatValues, ppraValues, tecnicaValues, intensidadeValues);
		}
		catch (Exception e) 
		{
			addActionError("Não foi possível gravar esta medição.");
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
			medicaoRisco.setAmbiente(ambiente);
			
			medicaoRiscoManager.save(medicaoRisco, riscoIds, ltcatValues, ppraValues, tecnicaValues, intensidadeValues);
		}
		catch (Exception e) 
		{
			addActionError("Não foi possível gravar esta medição.");
			prepareUpdate();
			return INPUT;
		}
		return SUCCESS;
	}

	public String list() throws Exception
	{
		ambientes = ambienteManager.findAmbientes(getEmpresaSistema().getId());
		medicaoRiscos = medicaoRiscoManager.findAllSelect(getEmpresaSistema().getId(), getAmbiente().getId());
		return SUCCESS;
	}

	public String delete() throws Exception
	{
		medicaoRiscoManager.removeCascade(medicaoRisco.getId());
		addActionMessage("Medição excluída com sucesso.");

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
		if (ambiente == null)
			ambiente = new Ambiente();
		
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
}
