package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.MedicoCoordenadorManager;
import com.fortes.rh.exception.ValidacaoAssinaturaException;
import com.fortes.rh.model.dicionario.TipoEstabelecimentoResponsavel;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.ModelUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

public class MedicoCoordenadorEditAction extends MyActionSupportEdit implements ModelDriven
{
	private static final long serialVersionUID = 1L;

	private MedicoCoordenadorManager medicoCoordenadorManager;
	private EstabelecimentoManager estabelecimentoManager;

	private MedicoCoordenador medicoCoordenador;
	private boolean manterAssinatura;

	private Long[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();

	private TipoEstabelecimentoResponsavel tipoEstabelecimentoResponsavel = new TipoEstabelecimentoResponsavel();
			
	private void prepare() throws Exception
	{
		if(ModelUtil.hasNotNull("getId()", medicoCoordenador))
		{
			medicoCoordenador = medicoCoordenadorManager.findByIdProjection(medicoCoordenador.getId());
			if (medicoCoordenador != null){
				medicoCoordenador.setAssinaturaDigital(medicoCoordenadorManager.getAssinaturaDigital(medicoCoordenador.getId()));
				medicoCoordenador.setEstabelecimentos(estabelecimentoManager.findByMedicoCoordenador(medicoCoordenador.getId(), Boolean.TRUE));
			}
		}
		estabelecimentosCheckList = estabelecimentoManager.populaCheckBox(getEmpresaSistema().getId());			
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		if(medicoCoordenador == null || !getEmpresaSistema().getId().equals(medicoCoordenador.getEmpresa().getId()))
		{
			addActionWarning("O médico solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return Action.ERROR;
		}
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, medicoCoordenador.getEstabelecimentos());

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try {
			medicoCoordenador.setEmpresa(getEmpresaSistema());
			medicoCoordenadorManager.insere(medicoCoordenador, estabelecimentosCheck);
			
			addActionSuccess("Médico coordenador gravado com sucesso.");
		} catch (Exception e) {
			processaException(e);
			prepareInsert();

			addActionError("Cadastro não pôde ser realizado.");
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			medicoCoordenador.setEmpresa(getEmpresaSistema());
			medicoCoordenadorManager.atualiza(medicoCoordenador, estabelecimentosCheck, manterAssinatura);
			
			addActionSuccess("Médico coordenador atualizado com sucesso.");
		} catch (Exception e) {
			processaException(e);
			prepareUpdate();
			
			addActionError("Atualização não pôde ser realizada.");
			return Action.INPUT;
		}

		return Action.SUCCESS;
	}

	private void processaException(Exception exception) {
		if(exception instanceof ValidacaoAssinaturaException){
			medicoCoordenador.setAssinaturaDigital(null);
			addActionWarning(exception.getMessage());
		}
		exception.printStackTrace();
	}

	public String showAssinatura() throws Exception
	{
		if (medicoCoordenador != null && medicoCoordenador.getId() != null)
		{
			medicoCoordenador.setAssinaturaDigital(medicoCoordenadorManager.getAssinaturaDigital(medicoCoordenador.getId()));

			if (medicoCoordenador.getAssinaturaDigital() != null && medicoCoordenador.getAssinaturaDigital().getBytes() != null)
			{
				HttpServletResponse response = ServletActionContext.getResponse();

		        response.addHeader("Expires", "0");
		        response.addHeader("Pragma", "no-cache");
		        response.addHeader("Content-type", medicoCoordenador.getAssinaturaDigital().getContentType());
		        response.addHeader("Content-Transfer-Encoding", "binary");
		        response.getOutputStream().write(medicoCoordenador.getAssinaturaDigital().getBytes());
			}
		}

		return Action.SUCCESS;
	}

	public Object getModel()
	{
		return getMedicoCoordenador();
	}

	public MedicoCoordenador getMedicoCoordenador()
	{
		if(medicoCoordenador == null)
			medicoCoordenador = new MedicoCoordenador();
		return medicoCoordenador;
	}

	public void setMedicoCoordenador(MedicoCoordenador medicoCoordenador)
	{
		this.medicoCoordenador = medicoCoordenador;
	}

	public void setMedicoCoordenadorManager(MedicoCoordenadorManager medicoCoordenadorManager)
	{
		this.medicoCoordenadorManager = medicoCoordenadorManager;
	}

	public boolean isManterAssinatura()
	{
		return manterAssinatura;
	}

	public void setManterAssinatura(boolean manterAssinatura)
	{
		this.manterAssinatura = manterAssinatura;
	}

	public void setEstabelecimentosCheck(Long[] estabelecimentosCheck) {
		this.estabelecimentosCheck = estabelecimentosCheck;
	}
	
	public TipoEstabelecimentoResponsavel getTipoEstabelecimentoResponsavel() {
		return tipoEstabelecimentoResponsavel;
	}
	
	public Collection<CheckBox> getEstabelecimentosCheckList() {
		return estabelecimentosCheckList;
	}
	
	public String getTipoEstabelecimentoResponsavelAlguns() {
		return TipoEstabelecimentoResponsavel.ALGUNS;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}
}