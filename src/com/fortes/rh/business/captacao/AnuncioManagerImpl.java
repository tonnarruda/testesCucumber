package com.fortes.rh.business.captacao;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.captacao.AnuncioDao;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.model.captacao.CandidatoSolicitacao;
import com.fortes.rh.model.captacao.EmpresaBds;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.web.tags.CheckBox;

public class AnuncioManagerImpl extends GenericManagerImpl<Anuncio, AnuncioDao> implements AnuncioManager
{
	private EmpresaBdsManager empresaBdsManager;

	public Collection<CheckBox> getEmpresasCheck(Long empresaId) throws Exception
	{
		return CheckListBoxUtil.populaCheckListBox( empresaBdsManager.findToList(new String[]{"id", "nome"},new String[]{"id", "nome"},new String[]{"empresa.id"},new Object[]{empresaId},new String[]{"nome"}), "getId", "getNome");
	}

	public void setEmpresaBdsManager(EmpresaBdsManager empresaBdsManager)
	{
		this.empresaBdsManager = empresaBdsManager;
	}

	public String[] montaEmails(String emailAvulso, String[] empresasCheck)
	{
		String[] emailsAvulsosArray = new String[0];

		if(!emailAvulso.trim().equals(""))
			emailsAvulsosArray = emailAvulso.split(";");

		Collection<String> emailsAvulsos = new CollectionUtil<String>().convertArrayToCollection(emailsAvulsosArray);

		int i = 0;

		int tamanhoInicial = 0;
		if(empresasCheck != null)
			tamanhoInicial = empresasCheck.length ;

		String[] emails = new String[tamanhoInicial + emailsAvulsos.size()];

		Collection<EmpresaBds> emps = empresaBdsManager.findAllProjection(LongUtil.arrayStringToArrayLong(empresasCheck));

		for (EmpresaBds empresaTmp : emps)
		{
			emails[i] = empresaTmp.getEmail();
			i++;
		}

		for (String email : emailsAvulsos)
		{
			if(!email.trim().equals(""))
			{
				emails[i] = email;
				i++;
			}
		}

		return emails;
	}

	public void removeBySolicitacao(Long solicitacaoId)
	{
		getDao().removeBySolicitacao(solicitacaoId);
	}

	public Anuncio findByIdProjection(Long anuncioId)
	{
		return getDao().findByIdProjection(anuncioId);
	}

	public Anuncio findBySolicitacao(Long solicitacaoId)
	{
		return getDao().findBySolicitacao(solicitacaoId);
	}

	public Collection<Anuncio> findAnunciosModuloExterno(Long empresaId, Long candidatoId) 
	{
		return getDao().findAnunciosModuloExterno(empresaId, candidatoId);
	}

}