package com.fortes.rh.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.UsuarioMensagemDao;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.acesso.UsuarioEmpresa;
import com.fortes.rh.model.acesso.UsuarioEmpresaManager;
import com.fortes.rh.model.dicionario.TipoMensagem;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Mensagem;
import com.fortes.rh.model.geral.UsuarioMensagem;

public class UsuarioMensagemManagerImpl extends GenericManagerImpl<UsuarioMensagem, UsuarioMensagemDao> implements UsuarioMensagemManager
{
	private MensagemManager mensagemManager;
	private UsuarioEmpresaManager usuarioEmpresaManager;

	public Map<Character, Collection<UsuarioMensagem>> listaUsuarioMensagem(Long usuarioId, Long empresaId)
	{
		Collection<UsuarioMensagem> usuarioMensagens = getDao().listaUsuarioMensagem(usuarioId, empresaId);
		Map<Character, Collection<UsuarioMensagem>> mensagensAgrupadas = new LinkedHashMap<Character, Collection<UsuarioMensagem>>();
		
		for (UsuarioMensagem usuarioMensagem : usuarioMensagens)
		{
			if (!mensagensAgrupadas.containsKey(usuarioMensagem.getMensagem().getTipo()))
				mensagensAgrupadas.put(usuarioMensagem.getMensagem().getTipo(), new ArrayList<UsuarioMensagem>());
			
			mensagensAgrupadas.get(usuarioMensagem.getMensagem().getTipo()).add(usuarioMensagem);
		}
		
		return mensagensAgrupadas;
	}

	public UsuarioMensagem findByIdProjection(Long usuarioMensagemId, Long empresaId)
	{
		return getDao().findByIdProjection(usuarioMensagemId, empresaId);
	}

	public Boolean possuiMensagemNaoLida(Long usuarioId, Long empresaId)
	{
		return getDao().possuiMensagemNaoLida(usuarioId, empresaId);
	}

	public void salvaMensagem(Empresa empresa, Mensagem mensagem, String[] usuariosCheck) throws Exception
	{
		try
		{
			for(int i=0; i < usuariosCheck.length; i++)
			{
				Usuario usuario = new Usuario();
				usuario.setId(Long.parseLong(usuariosCheck[i]));

				UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
				usuarioMensagem.setUsuario(usuario);
				usuarioMensagem.setMensagem(mensagem);
				usuarioMensagem.setEmpresa(empresa);
				usuarioMensagem.setLida(false);

				save(usuarioMensagem);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	public void saveMensagemAndUsuarioMensagem(String msg, String remetente, String link, Collection<UsuarioEmpresa> usuarioEmpresas, Colaborador colaborador, char tipoMensagem)
	{
		Mensagem mensagem = new Mensagem();
		mensagem.setData(new Date());

		mensagem.setTexto(msg);
		mensagem.setRemetente(remetente);
		mensagem.setLink(link);
		mensagem.setColaborador(colaborador);
		mensagem.setTipo(tipoMensagem);

		mensagem = mensagemManager.save(mensagem);

		for (UsuarioEmpresa usuarioEmpresa : usuarioEmpresas)
		{
			UsuarioMensagem usuarioMensagem = new UsuarioMensagem();
			usuarioMensagem.setUsuario(usuarioEmpresa.getUsuario());
			usuarioMensagem.setMensagem(mensagem);
			usuarioMensagem.setEmpresa(usuarioEmpresa.getEmpresa());
			usuarioMensagem.setLida(false);

			save(usuarioMensagem);
		}
	}

	public void saveMensagemAndUsuarioMensagemRespAreaOrganizacional(String msg, String remetente, String link, Collection<Long> areasIds)
	{
		Collection<UsuarioEmpresa> usuariosResponsaveisAreaOrganizacionais = usuarioEmpresaManager.findUsuarioResponsavelAreaOrganizacional(areasIds);
		saveMensagemAndUsuarioMensagem(msg, remetente, link, usuariosResponsaveisAreaOrganizacionais, null, TipoMensagem.AVALIACAO_DESEMPENHO);
	}

	public void setMensagemManager(MensagemManager mensagemManager)
	{
		this.mensagemManager = mensagemManager;
	}

	public Long getAnteriorOuProximo(Long usuarioMensagemId, Long usuarioId, Long empresaId, char opcao)
	{
		Integer count = getDao().getCount(new String[]{"empresa.id", "usuario.id"}, new Object[]{empresaId, usuarioId});
		if (count <= 1)
			return null;
		else
		{
			Long id = getDao().findAnteriorOuProximo(usuarioMensagemId, usuarioId, empresaId, opcao);
			return id;
		}
	}

	public void delete(UsuarioMensagem usuarioMensagem, Long[] usuarioMensagemIds)
	{
		if(usuarioMensagem != null && usuarioMensagem.getId() != null)
			remove(usuarioMensagem.getId());
		else
			if(usuarioMensagemIds != null && usuarioMensagemIds.length > 0)
				remove(usuarioMensagemIds);		
	}

	public void setUsuarioEmpresaManager(UsuarioEmpresaManager usuarioEmpresaManager) {
		this.usuarioEmpresaManager = usuarioEmpresaManager;
	}
}