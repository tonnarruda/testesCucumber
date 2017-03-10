package com.fortes.rh.business.acesso;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.dao.acesso.PerfilDao;
import com.fortes.rh.model.acesso.Papel;
import com.fortes.rh.model.acesso.Perfil;

@Component
public class PerfilManagerImpl extends GenericManagerImpl<Perfil, PerfilDao> implements PerfilManager
{
	@Autowired private PapelManager papelManager;
	private ColaboradorManager colaboradorManager;
	
	@Autowired
	PerfilManagerImpl(PerfilDao dao) {
		setDao(dao);
	}
	
	public Collection<Perfil> findAll(Integer page, Integer pagingSize)
	{
		return getDao().findAll(page, pagingSize);
	}
	
	public Integer getCount()
	{
		return getDao().getCount();
	}
	
	public String[] montaPermissoes(Perfil perfil)
	{
		String[] permissoes = null;
		if (perfil.getPapeis() != null)
		{
			int i = 0;
			permissoes = new String[perfil.getPapeis().size()];

			for (Papel papel : perfil.getPapeis())
			{
				permissoes[i] = papel.getId().toString();
				i++;
			}
		}

		return permissoes;
	}

	public Collection<String> getEmailsByRoleLiberaSolicitacao(Long empresaId)
	{
		Collection<Perfil> perfis = getDao().findPerfisByCodigoPapel("ROLE_LIBERA_SOLICITACAO");
		Collection<String> emails = colaboradorManager.findEmailsDeColaboradoresByPerfis(perfis, empresaId);
		
		return emails;
	}

	public Collection<Perfil> findPapeis(Long[] perfisIds) 
	{
		Collection<Perfil> perfis = getDao().findByIds(perfisIds);
		
		for (Perfil perfil : perfis) 
		{
			perfil.setArvorePapeis(papelManager.montarArvore(papelManager.findByPerfil(perfil.getId())));
		}
		
		return perfis;
	}
	
	public void removePerfilPapelByPapelId(Long papelId) {
		getDao().removePerfilPapelByPapelId(papelId);
	}
}