package com.fortes.rh.business.geral;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.config.JDBCConnection;
import com.fortes.rh.dao.geral.ConfiguracaoLimiteColaboradorDao;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.rh.util.Mail;

public class ConfiguracaoLimiteColaboradorManagerImpl extends GenericManagerImpl<ConfiguracaoLimiteColaborador, ConfiguracaoLimiteColaboradorDao> implements ConfiguracaoLimiteColaboradorManager
{
	private Mail mail;
	private EmpresaManager empresaManager;
	private CargoManager cargoManager;
	private AreaOrganizacionalManager areaOrganizacionalManager;
	
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
			
			body.append("Contrato: " + configuracaoLimiteColaborador.getDescricao() + "<br>");
			body.append("Área Organizacional: " + areaOrganizacionalManager.findByIdProjection(configuracaoLimiteColaborador.getAreaOrganizacional().getId()).getNome() + "<br><br>");
			body.append( "<table style='width:450px' ><thead><tr><th>Cargo</th><th>Limite</th></tr></thead><tbody>");
			
			for (QuantidadeLimiteColaboradoresPorCargo qtdLimiteColabCargo:  quantidadeLimiteColaboradoresPorCargos) 
			{
				body.append("<tr>" );
				body.append("<td>" + cargoManager.findByIdProjection(qtdLimiteColabCargo.getCargo().getId()).getNomeMercado() + "</td>" );
				body.append("<td align='right' style='width:60px'>" + qtdLimiteColabCargo.getLimite() + "</td>" );
				body.append("</tr>");
			}
			
			body.append("</tbody></table>");
			
			mail.send(empresa, "Novo Contrato ("+configuracaoLimiteColaborador.getDescricao()+") com limite de Colaboradores por Cargo adicionado.", body.toString(), null, emails);
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

	public void setCargoManager(CargoManager cargoManager) {
		this.cargoManager = cargoManager;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager) {
		this.areaOrganizacionalManager = areaOrganizacionalManager;
	}

	public void deleteByAreaOrganizacional(Long[] areaIds) throws Exception {
		getDao().deleteByAreaOrganizacional(areaIds);
	}
}
