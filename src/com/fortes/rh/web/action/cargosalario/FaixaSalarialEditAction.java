package com.fortes.rh.web.action.cargosalario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialHistoricoManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.business.cargosalario.IndiceManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistoricoVO;
import com.fortes.rh.model.cargosalario.Indice;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings({"serial"})
public class FaixaSalarialEditAction extends MyActionSupportEdit implements ModelDriven
{
	private FaixaSalarialManager faixaSalarialManager;
	private CargoManager cargoManager;
	private IndiceManager indiceManager;
	private FaixaSalarialHistoricoManager faixaSalarialHistoricoManager;
	private CertificacaoManager certificacaoManager;

	private FaixaSalarial faixaSalarialAux;
	private FaixaSalarialHistorico faixaSalarialHistorico;
	private Cargo cargoAux;

	private Collection<Indice> indices = new ArrayList<Indice>();
	private Collection<FaixaSalarialHistorico> faixaSalarialsHistoricos = new ArrayList<FaixaSalarialHistorico>();
	private Collection<FaixaSalarialHistoricoVO> faixaSalarialHistoricoVOs = new ArrayList<FaixaSalarialHistoricoVO>();

	private Map tipoAplicacaoIndices;
	private TipoAplicacaoIndice tipoAplicacaoIndice = new TipoAplicacaoIndice();
	private StatusRetornoAC statusRetornoAC = new StatusRetornoAC();
	
	private String[] certificacaosCheck;
	private Collection<CheckBox> certificacaosCheckList = new ArrayList<CheckBox>();

	private Double salario;
	private boolean integradoAC;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if(faixaSalarialAux != null && faixaSalarialAux.getId() != null)
		{
			faixaSalarialAux = faixaSalarialManager.findByFaixaSalarialId(faixaSalarialAux.getId());
			cargoAux = faixaSalarialAux.getCargo();
		}

		tipoAplicacaoIndices = new TipoAplicacaoIndice();
		tipoAplicacaoIndices.remove(TipoAplicacaoIndice.CARGO);

		indices = indiceManager.findAll(getEmpresaSistema());
		integradoAC = getEmpresaSistema().isAcIntegra();
		
		Collection<Certificacao> certificacaos = certificacaoManager.findAllSelect(getEmpresaSistema().getId());
		certificacaosCheckList = CheckListBoxUtil.populaCheckListBox(certificacaos, "getId", "getNome");
	}

	public String prepareInsert() throws Exception
	{
		faixaSalarialAux.setId(null);//caso der erro no cadastro do cargo no AC, Francisco e Arnaldo 14/01/09
		prepare();
		cargoAux = cargoManager.findByIdProjection(cargoAux.getId());
		faixaSalarialAux.setCargo(cargoAux);

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		certificacaosCheckList = CheckListBoxUtil.marcaCheckListBox(certificacaosCheckList, certificacaoManager.findByFaixa(faixaSalarialAux.getId()), "getId");
		faixaSalarialHistoricoVOs = faixaSalarialHistoricoManager.findAllComHistoricoIndice(faixaSalarialAux.getId());
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		if(faixaSalarialManager.verifyExistsNomeByCargo(faixaSalarialAux.getCargo().getId(), faixaSalarialAux.getNome()))
		{
			addActionError("Já existe uma Faixa Salarial com esse nome.");
			prepareInsert();
			return Action.INPUT;
		}

		if (faixaSalarialHistorico.getTipo() == TipoAplicacaoIndice.getIndice())
		{
			if (faixaSalarialHistoricoManager.verifyData(faixaSalarialHistorico.getId(), faixaSalarialHistorico.getData(), faixaSalarialAux.getId()))
			{
				addActionMessage("Já existe um histórico com essa data.");
				prepareInsert();
				return Action.INPUT;
			}

			if (!faixaSalarialHistoricoManager.verifyHistoricoIndiceNaData(faixaSalarialHistorico.getData(), faixaSalarialHistorico.getIndice().getId()))
			{
				addActionMessage("Não existe histórico para o índice selecionado nesta data.");
				prepareInsert();
				return Action.INPUT;
			}
		}

		try
		{
			faixaSalarialManager.saveFaixaSalarial(faixaSalarialAux, faixaSalarialHistorico, getEmpresaSistema(), certificacaosCheck);
			return Action.SUCCESS;
		}
		catch(IntegraACException e)
		{
			addActionError("O Cargo não pôde ser cadastrado no Fortes Pessoal.");
			prepareInsert();
			return Action.INPUT;
		}
		catch(Exception e)
		{
			String message = "O Cargo não pôde ser cadastrado.";
			
			if(e.getMessage() != null)
				message = e.getMessage();
			else if(e.getCause() != null && e.getCause().getLocalizedMessage() != null)
				message = e.getCause().getLocalizedMessage();
			
			addActionError(message);
			prepareInsert();
			return Action.INPUT;
		}
	}

	public String update() throws Exception
	{
		FaixaSalarial faixaSalarialVerifica = faixaSalarialManager.findByFaixaSalarialId(faixaSalarialAux.getId());

		if(faixaSalarialManager.verifyExistsNomeByCargo(faixaSalarialAux.getCargo().getId(), faixaSalarialAux.getNome()) && !faixaSalarialVerifica.getNome().equalsIgnoreCase(faixaSalarialAux.getNome()))
		{
			addActionError("Já existe uma Faixa Salarial com esse nome.");
			prepareUpdate();
			return Action.INPUT;
		}

		try
		{
			faixaSalarialManager.updateFaixaSalarial(faixaSalarialAux, getEmpresaSistema(), certificacaosCheck);
			return Action.SUCCESS;
		}
		catch(IntegraACException e)
		{
			addActionError("O Cargo não pôde ser atualizado no Fortes Pessoal.");
			return Action.INPUT;
		}
		catch(Exception e)
		{
			addActionError("O Cargo não pôde ser atualizado.");
			return Action.INPUT;
		}
	}

	public Object getModel()
	{
		return getFaixaSalarialAux();
	}

	public FaixaSalarial getFaixaSalarialAux()
	{
		if(faixaSalarialAux == null)
			faixaSalarialAux = new FaixaSalarial();
		return faixaSalarialAux;
	}

	public void setFaixaSalarialAux(FaixaSalarial faixaSalarialAux)
	{
		this.faixaSalarialAux = faixaSalarialAux;
	}

	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public void setCargoManager(CargoManager cargoManager)
	{
		this.cargoManager = cargoManager;
	}

	public Cargo getCargoAux()
	{
		return cargoAux;
	}

	public void setCargoAux(Cargo cargoAux)
	{
		this.cargoAux = cargoAux;
	}

	public FaixaSalarialHistorico getFaixaSalarialHistorico()
	{
		return faixaSalarialHistorico;
	}

	public void setFaixaSalarialHistorico(FaixaSalarialHistorico faixaSalarialHistorico)
	{
		this.faixaSalarialHistorico = faixaSalarialHistorico;
	}

	public TipoAplicacaoIndice getTipoAplicacaoIndice()
	{
		return tipoAplicacaoIndice;
	}

	public Map getTipoAplicacaoIndices()
	{
		return tipoAplicacaoIndices;
	}

	public Collection<Indice> getIndices()
	{
		return indices;
	}

	public Collection<FaixaSalarialHistorico> getFaixaSalarialsHistoricos()
	{
		return faixaSalarialsHistoricos;
	}

	public void setIndiceManager(IndiceManager indiceManager)
	{
		this.indiceManager = indiceManager;
	}

	public void setFaixaSalarialHistoricoManager(FaixaSalarialHistoricoManager faixaSalarialHistoricoManager)
	{
		this.faixaSalarialHistoricoManager = faixaSalarialHistoricoManager;
	}

	public Double getSalario()
	{
		return salario;
	}

	public void setSalario(Double salario)
	{
		this.salario = salario;
	}

	public boolean isIntegradoAC()
	{
		return integradoAC;
	}

	public void setIntegradoAC(boolean integradoAC)
	{
		this.integradoAC = integradoAC;
	}

	public StatusRetornoAC getStatusRetornoAC()
	{
		return statusRetornoAC;
	}

	public void setCertificacaoManager(CertificacaoManager certificacaoManager)
	{
		this.certificacaoManager = certificacaoManager;
	}

	public Collection<CheckBox> getCertificacaosCheckList()
	{
		return certificacaosCheckList;
	}

	public void setCertificacaosCheck(String[] certificacaosCheck)
	{
		this.certificacaosCheck = certificacaosCheck;
	}

	public void setFaixaSalarialHistoricoVOs(
			Collection<FaixaSalarialHistoricoVO> faixaSalarialHistoricoVOs) {
		this.faixaSalarialHistoricoVOs = faixaSalarialHistoricoVOs;
	}

	public Collection<FaixaSalarialHistoricoVO> getFaixaSalarialHistoricoVOs() {
		return faixaSalarialHistoricoVOs;
	}
}