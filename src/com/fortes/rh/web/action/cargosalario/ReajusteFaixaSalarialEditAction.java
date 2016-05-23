package com.fortes.rh.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.ReajusteFaixaSalarialManager;
import com.fortes.rh.business.cargosalario.TabelaReajusteColaboradorManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.ReajusteFaixaSalarial;
import com.fortes.rh.model.cargosalario.TabelaReajusteColaborador;
import com.fortes.rh.model.dicionario.TipoReajuste;
import com.fortes.rh.util.ExceptionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

@SuppressWarnings("serial")
public class ReajusteFaixaSalarialEditAction extends MyActionSupportEdit
{
	private CargoManager cargoManager;
	private TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager;
	private ReajusteFaixaSalarialManager reajusteFaixaSalarialManager;
	
	private TabelaReajusteColaborador tabelaReajusteColaborador;
	private ReajusteFaixaSalarial reajusteFaixaSalarial;
	private Cargo cargo;
	private FaixaSalarial faixaSalarial;
	
	private Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors = new ArrayList<TabelaReajusteColaborador>();
	private Collection<Cargo> cargos = new ArrayList<Cargo>();
	
	private String[] cargosCheck;
	private Collection<CheckBox> cargosCheckList = new ArrayList<CheckBox>();	
	private String[] faixasCheck;
	private Collection<CheckBox> faixasCheckList = new ArrayList<CheckBox>();
	
	private char dissidioPor;
	private Double valorDissidio;
	
	public String prepareInsert() throws Exception
	{
		tabelaReajusteColaboradors = tabelaReajusteColaboradorManager.findAllSelectByNaoAprovada(getEmpresaSistema().getId(), TipoReajuste.FAIXA_SALARIAL);
		
		cargos = cargoManager.findAllSelect("nomeMercado", null, Cargo.TODOS, getEmpresaSistema().getId());
		
		return Action.SUCCESS;
	}
	
	public String insert() throws Exception
	{
		try
		{
			reajusteFaixaSalarialManager.insertReajustes(tabelaReajusteColaborador.getId(), new Long[] { faixaSalarial.getId() }, dissidioPor, valorDissidio);
			addActionSuccess("Proposta de reajuste gravada com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ExceptionUtil.traduzirMensagem(this, e, "Ocorreu um erro ao gravar a proposta de reajuste.");
		}
		finally
		{
			prepareInsert();
		}

		return Action.SUCCESS;
	}
	
	public String prepareUpdate() throws Exception
	{
		reajusteFaixaSalarial = reajusteFaixaSalarialManager.findByIdProjection(reajusteFaixaSalarial.getId());
		
		return Action.SUCCESS;
	}
	
	public String update() throws Exception
	{
		try
		{
			reajusteFaixaSalarialManager.updateValorProposto(reajusteFaixaSalarial.getId(), reajusteFaixaSalarial.getValorAtual(), dissidioPor, valorDissidio);
			addActionSuccess("Proposta de reajuste alterada com sucesso");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ExceptionUtil.traduzirMensagem(this, e, "Ocorreu um erro ao alterar a proposta de reajuste");
		}
		finally
		{
			prepareUpdate();
		}

		return Action.SUCCESS;
	}
	
	public String prepareDissidio() throws Exception
	{
		tabelaReajusteColaboradors = tabelaReajusteColaboradorManager.findAllSelectByNaoAprovada(getEmpresaSistema().getId(), TipoReajuste.FAIXA_SALARIAL);
		
		cargosCheckList = cargoManager.populaCheckBox(false, getEmpresaSistema().getId());

		return Action.SUCCESS;
	}

	public String insertColetivo() throws Exception
	{
		try
		{
			reajusteFaixaSalarialManager.insertReajustes(tabelaReajusteColaborador.getId(), LongUtil.arrayStringToArrayLong(faixasCheck), dissidioPor, valorDissidio);
			addActionSuccess("Propostas de reajuste gravadas com sucesso");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Ocorreu um erro ao gravar as propostas de reajuste coletivo");
		}
		finally
		{
			prepareDissidio();
		}

		return Action.SUCCESS;
	}
	
	public String delete() throws Exception 
	{
		reajusteFaixaSalarialManager.remove(new Long[]{ reajusteFaixaSalarial.getId() });

		return Action.SUCCESS;
	}
	
	public Collection<CheckBox> getCargosCheckList() {
		return cargosCheckList;
	}

	public Collection<CheckBox> getFaixasCheckList() {
		return faixasCheckList;
	}

	public String[] getCargosCheck() {
		return cargosCheck;
	}
	
	public void setCargosCheck(String[] cargosCheck) {
		this.cargosCheck = cargosCheck;
	}

	public void setFaixasCheck(String[] faixasCheck) {
		this.faixasCheck = faixasCheck;
	}

	public void setCargoManager(CargoManager cargoManager) {
		this.cargoManager = cargoManager;
	}

	public TabelaReajusteColaborador getTabelaReajusteColaborador() {
		return tabelaReajusteColaborador;
	}

	public void setTabelaReajusteColaborador(TabelaReajusteColaborador tabelaReajusteColaborador) {
		this.tabelaReajusteColaborador = tabelaReajusteColaborador;
	}

	public Collection<TabelaReajusteColaborador> getTabelaReajusteColaboradors() {
		return tabelaReajusteColaboradors;
	}

	public void setTabelaReajusteColaboradors(	Collection<TabelaReajusteColaborador> tabelaReajusteColaboradors) {
		this.tabelaReajusteColaboradors = tabelaReajusteColaboradors;
	}

	public void setTabelaReajusteColaboradorManager(TabelaReajusteColaboradorManager tabelaReajusteColaboradorManager) {
		this.tabelaReajusteColaboradorManager = tabelaReajusteColaboradorManager;
	}

	public char getDissidioPor() {
		return dissidioPor;
	}

	public void setDissidioPor(char dissidioPor) {
		this.dissidioPor = dissidioPor;
	}

	public Double getValorDissidio() {
		return valorDissidio;
	}

	public void setValorDissidio(Double valorDissidio) {
		this.valorDissidio = valorDissidio;
	}

	public void setReajusteFaixaSalarialManager(ReajusteFaixaSalarialManager reajusteFaixaSalarialManager) {
		this.reajusteFaixaSalarialManager = reajusteFaixaSalarialManager;
	}

	public ReajusteFaixaSalarial getReajusteFaixaSalarial() {
		return reajusteFaixaSalarial;
	}

	public void setReajusteFaixaSalarial(ReajusteFaixaSalarial reajusteFaixaSalarial) {
		this.reajusteFaixaSalarial = reajusteFaixaSalarial;
	}

	public Collection<Cargo> getCargos() {
		return cargos;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public FaixaSalarial getFaixaSalarial() {
		return faixaSalarial;
	}

	public void setFaixaSalarial(FaixaSalarial faixaSalarial) {
		this.faixaSalarial = faixaSalarial;
	}
}