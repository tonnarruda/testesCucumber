package com.fortes.rh.util;

import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.relatorio.Cabecalho;
import com.fortes.rh.security.SecurityUtil;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;

public class RelatorioUtil
{
	public static Map<String, Object> getParametrosRelatorio(String titulo, Empresa empresa, String filtro)
	{
		ParametrosDoSistemaManager parametrosDoSistemaManager = (ParametrosDoSistemaManager) SpringUtil.getBean("parametrosDoSistemaManager");
		ParametrosDoSistema parametrosDoSistema = parametrosDoSistemaManager.findByIdProjection(1L);

		Map<String, Object> parametros = new HashMap<String, Object>();

		Usuario usuario = SecurityUtil.getUsuarioLoged(ActionContext.getContext().getSession());

		String path = ServletActionContext.getServletContext().getRealPath("/WEB-INF/report/") + java.io.File.separator;
    	String logo = ArquivoUtil.getPathLogoEmpresa() + empresa.getLogoUrl();

    	String msgRegistro = Autenticador.getMsgPadrao();
    	Boolean registrado = (Boolean) ActionContext.getContext().getSession().get("REG_LOGS");
    	if(registrado != null && registrado)
    		msgRegistro = Autenticador.getMsgAutenticado(parametrosDoSistema.getServidorRemprot());

    	Cabecalho cabecalho = new Cabecalho(titulo, empresa.getNome(), filtro, usuario.getNome(), parametrosDoSistema.getAppVersao(), logo, msgRegistro);
    	cabecalho.setLicenciadoPara(empresa.getNome());
    	
    	parametros.put("CABECALHO", cabecalho);
    	parametros.put("SUBREPORT_DIR", path);

    	return parametros;
	}
}
