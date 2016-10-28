package com.fortes.rh.business.captacao;

import java.util.Collection;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.dao.captacao.AnuncioDao;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.model.captacao.EmpresaBds;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.StringUtil;
import com.fortes.web.tags.CheckBox;

@Component
public class AnuncioManagerImpl extends GenericManagerImpl<Anuncio, AnuncioDao> implements AnuncioManager
{
	private EmpresaManager empresaManager;
	private ParametrosDoSistemaManager parametrosDoSistemaManager;
	private EmpresaBdsManager empresaBdsManager;
	private Mail mail;
	
	@Autowired
	AnuncioManagerImpl(AnuncioDao anuncioDao) {
		setDao(anuncioDao);
	}

	public Collection<CheckBox> getEmpresasCheck(Long empresaId) throws Exception
	{
		return CheckListBoxUtil.populaCheckListBox( empresaBdsManager.findToList(new String[]{"id", "nome"},new String[]{"id", "nome"},new String[]{"empresa.id"},new Object[]{empresaId},new String[]{"nome"}), "getId", "getNome", null);
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

	public Collection<Anuncio> findAnunciosSolicitacaoAberta(Long empresaId) 
	{
		return getDao().findAnunciosSolicitacaoAberta(empresaId);
	}

	public void enviarAnuncioEmail(Long anuncioId, Long empresaId, String nomeFrom, String emailFrom, String nomeTo, String emailTo) throws AddressException, MessagingException 
	{
		String subject = nomeFrom + " lhe recomendou uma oportunidade de emprego";
		StringBuffer body = new StringBuffer();
		
		ParametrosDoSistema parametros = parametrosDoSistemaManager.findByIdProjection(1L);
		Empresa empresa = empresaManager.findByIdProjection(empresaId);
		Anuncio anuncio = getDao().findByIdProjection(anuncioId);
		
		body.append("<br />");
		body.append("Caro(a) " + nomeTo + ",<br /><br />");
		body.append(nomeFrom + " está lhe recomendando a seguinte oportunidade de emprego na empresa " + empresa.getNome() + ":<br /><br />");
		body.append("<strong>" + anuncio.getTitulo() + "</strong><br /><br />");
		body.append(anuncio.getCabecalho() + "<br /><br />");
		body.append("<a href='" + parametros.getAppUrl() + "/externo/verAnuncio.action?anuncio.id=" + anuncioId + "' title='Mais informações'>Mais informações</a><br /><br />");
		
		mail.send(emailFrom, StringUtil.retiraAcento(subject), body.toString(), emailTo);
	}
	
	public void setEmpresaBdsManager(EmpresaBdsManager empresaBdsManager)
	{
		this.empresaBdsManager = empresaBdsManager;
	}

	public void setMail(Mail mail) 
	{
		this.mail = mail;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) 
	{
		this.empresaManager = empresaManager;
	}

	public void setParametrosDoSistemaManager(ParametrosDoSistemaManager parametrosDoSistemaManager) 
	{
		this.parametrosDoSistemaManager = parametrosDoSistemaManager;
	}
}