package com.fortes.rh.web.action.sesmt;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;

import com.fortes.rh.business.geral.EstabelecimentoManager;
import com.fortes.rh.business.sesmt.EngenheiroResponsavelManager;
import com.fortes.rh.model.dicionario.TipoEstabelecimentoResponsavel;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.model.sesmt.EngenheiroResponsavel;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.ModelUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.opensymphony.xwork.Action;

public class EngenheiroResponsavelEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;

	private EngenheiroResponsavelManager engenheiroResponsavelManager;
	private EstabelecimentoManager estabelecimentoManager;

	private EngenheiroResponsavel engenheiroResponsavel;

	private Long[] estabelecimentosCheck;
	private Collection<CheckBox> estabelecimentosCheckList = new ArrayList<CheckBox>();

	private TipoEstabelecimentoResponsavel tipoEstabelecimentoResponsavel = new TipoEstabelecimentoResponsavel();

	private void prepare() throws Exception
	{
		if (ModelUtil.hasNotNull("getId()", engenheiroResponsavel)) {
			engenheiroResponsavel = (EngenheiroResponsavel) engenheiroResponsavelManager.findByIdProjection(engenheiroResponsavel.getId());
			if (engenheiroResponsavel != null)
				engenheiroResponsavel.setEstabelecimentos(estabelecimentoManager.findByEngenheiroResponsavel(engenheiroResponsavel.getId(), Boolean.TRUE));

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
		if(engenheiroResponsavel == null || !getEmpresaSistema().getId().equals(engenheiroResponsavel.getEmpresa().getId()))
		{
			addActionWarning("O engenheiro solicitado não existe na empresa " + getEmpresaSistema().getNome() +".");
			return Action.ERROR;
		}
		estabelecimentosCheckList = CheckListBoxUtil.marcaCheckListBox(estabelecimentosCheckList, engenheiroResponsavel.getEstabelecimentos());

		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try {
			engenheiroResponsavel.setEmpresa(getEmpresaSistema());
			engenheiroResponsavel.setEstabelecimentos(getEstabelecimentosMarcados(engenheiroResponsavel.getEstabelecimentoResponsavel(), estabelecimentosCheck));
			engenheiroResponsavelManager.save(engenheiroResponsavel);
			
			addActionSuccess("Engenheiro responsável gravado com sucesso.");
		} catch (Exception e) {
			prepareInsert();

			e.printStackTrace();
			addActionError("Cadastro não pôde ser realizado.");

			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			engenheiroResponsavel.setEmpresa(getEmpresaSistema());
			engenheiroResponsavel.setEstabelecimentos(getEstabelecimentosMarcados(engenheiroResponsavel.getEstabelecimentoResponsavel(), estabelecimentosCheck));
			engenheiroResponsavelManager.update(engenheiroResponsavel);

			addActionSuccess("Engenheiro responsável atualizado com sucesso.");
		} catch (Exception e) {
			prepareUpdate();
			
			e.printStackTrace();
			addActionError("Atualização não pôde ser realizada.");

			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}

	private Collection<Estabelecimento> getEstabelecimentosMarcados(String tipoEstabelecimentoResponsavel, Long[] estabelecimentosIds) {
		if(ArrayUtils.isEmpty(estabelecimentosIds) || TipoEstabelecimentoResponsavel.TODOS.equals(tipoEstabelecimentoResponsavel))
			return null;

		return estabelecimentoManager.findById(estabelecimentosIds);
	}

	public EngenheiroResponsavel getEngenheiroResponsavel()
	{
		if(engenheiroResponsavel == null)
			engenheiroResponsavel = new EngenheiroResponsavel();
		return engenheiroResponsavel;
	}

	public void setEngenheiroResponsavel(EngenheiroResponsavel engenheiroResponsavel)
	{
		this.engenheiroResponsavel = engenheiroResponsavel;
	}

	public void setEngenheiroResponsavelManager(EngenheiroResponsavelManager engenheiroResponsavelManager)
	{
		this.engenheiroResponsavelManager = engenheiroResponsavelManager;
	}

	public Collection<CheckBox> getEstabelecimentosCheckList() {
		return estabelecimentosCheckList;
	}

	public TipoEstabelecimentoResponsavel getTipoEstabelecimentoResponsavel() {
		return tipoEstabelecimentoResponsavel;
	}

	public void setEstabelecimentosCheck(Long[] estabelecimentosCheck) {
		this.estabelecimentosCheck = estabelecimentosCheck;
	}

	public String getTipoEstabelecimentoResponsavelAlguns() {
		return TipoEstabelecimentoResponsavel.ALGUNS;
	}

	public void setEstabelecimentoManager(EstabelecimentoManager estabelecimentoManager) {
		this.estabelecimentoManager = estabelecimentoManager;
	}

}