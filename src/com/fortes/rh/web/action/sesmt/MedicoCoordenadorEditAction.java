package com.fortes.rh.web.action.sesmt;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.sesmt.MedicoCoordenadorManager;
import com.fortes.rh.model.sesmt.MedicoCoordenador;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

public class MedicoCoordenadorEditAction extends MyActionSupportEdit implements ModelDriven
{
	private static final long serialVersionUID = 1L;

	@Autowired private MedicoCoordenadorManager medicoCoordenadorManager;

	private MedicoCoordenador medicoCoordenador;
	private boolean manterAssinatura;

	private void prepare() throws Exception
	{
		if(medicoCoordenador != null && medicoCoordenador.getId() != null)
		{
			medicoCoordenador = (MedicoCoordenador) medicoCoordenadorManager.findByIdProjection(medicoCoordenador.getId());
			if (medicoCoordenador != null)
				medicoCoordenador.setAssinaturaDigital(medicoCoordenadorManager.getAssinaturaDigital(medicoCoordenador.getId()));
		}
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
			addActionError("O Médico solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return Action.ERROR;
		}

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		if(!assinaturaValida(medicoCoordenador.getAssinaturaDigital()))
		{
			prepareInsert();
			medicoCoordenador.setAssinaturaDigital(null);
			return Action.INPUT;
		}

		medicoCoordenador.setEmpresa(getEmpresaSistema());
		medicoCoordenadorManager.save(medicoCoordenador);
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		medicoCoordenador.setEmpresa(getEmpresaSistema());

		if (manterAssinatura)
		{
			medicoCoordenador.setAssinaturaDigital(medicoCoordenadorManager.getAssinaturaDigital(medicoCoordenador.getId()));
		}
		else if(!assinaturaValida(medicoCoordenador.getAssinaturaDigital()))
		{
			prepareUpdate();
			medicoCoordenador.setAssinaturaDigital(null);
			return Action.INPUT;
		}

		medicoCoordenadorManager.update(medicoCoordenador);
		return Action.SUCCESS;
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

	private boolean assinaturaValida(com.fortes.model.type.File assinatura)
	{
		boolean fotoValida =  true;
		if(assinatura != null)
		{
			if(assinatura.getContentType().length() >= 5)
			{
				if(!assinatura.getContentType().substring(0, 5).equals("image"))
				{
					addActionError("Tipo de arquivo não suportado");
					fotoValida = false;
				}

				else if(assinatura.getSize() > 524288)
				{
					addActionError("Tamanho do arquivo maior que o suportado");
					fotoValida = false;
				}
			}
		}

		return fotoValida;
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

	public boolean isManterAssinatura()
	{
		return manterAssinatura;
	}

	public void setManterAssinatura(boolean manterAssinatura)
	{
		this.manterAssinatura = manterAssinatura;
	}
}