package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.geral.ConfiguracaoLimiteColaboradorDao;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.rh.util.Mail;

public class ConfiguracaoLimiteColaboradorManagerImpl extends GenericManagerImpl<ConfiguracaoLimiteColaborador, ConfiguracaoLimiteColaboradorDao> implements ConfiguracaoLimiteColaboradorManager
{
	private Mail mail;
	private EmpresaManager empresaManager;
	public Collection<ConfiguracaoLimiteColaborador> findAllSelect(Long empresaId) 
	{
		return getDao().findAllSelect(empresaId);
	}

	public Collection<Long> findIdAreas(Long empresaId) {
		return getDao().findIdAreas(empresaId);
	}

	public void enviaEmail(ConfiguracaoLimiteColaborador configuracaoLimiteColaborador, Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos, Empresa empresa) 
	{
		try {
			String[] emails = empresaManager.findByIdProjection(empresa.getId()).getEmailRespLimiteContrato().split(";");
			StringBuilder body = new StringBuilder();
			
			body.append("Contrato: " + configuracaoLimiteColaborador.getDescricao() + "<br><br>");
			
			mail.send(empresa, "Nova configuração(Contrato) de limite de Colaboradores por Cargo adicionada.", body.toString(), null, emails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public void setEmpresaManager(EmpresaManager empresaManager) {
		this.empresaManager = empresaManager;
	}
}
