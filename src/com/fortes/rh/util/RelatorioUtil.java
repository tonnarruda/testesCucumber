package com.fortes.rh.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.fortes.rh.business.geral.ParametrosDoSistemaManager;
import com.fortes.rh.model.acesso.Usuario;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;
import com.fortes.rh.model.relatorio.Cabecalho;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.security.licenca.AutenticadorJarvis;
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
    	String pathLogoRelatorio = "";
    	
    	String pathLogo = ArquivoUtil.getPathLogoEmpresa() + empresa.getLogoUrl();
    	File logo = new File(pathLogo);
    	if(logo.exists())
    		pathLogoRelatorio = pathLogo;

    	String msgRegistro = "";
		if (parametrosDoSistema.verificaLicenca())
		{
			try {
				if(AutenticadorJarvis.isDemo())
					msgRegistro = AutenticadorJarvis.getMsgPadrao();
			else
				msgRegistro = AutenticadorJarvis.getMsgAutenticado();			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

    	Cabecalho cabecalho = new Cabecalho(titulo, empresa.getNome(), filtro, usuario.getNome(), parametrosDoSistema.getAppVersao(), pathLogoRelatorio, msgRegistro, parametrosDoSistema.isVersaoAcademica());
    	cabecalho.setLicenciadoPara(empresa.getNome());
    	
    	parametros.put("CABECALHO", cabecalho);
    	parametros.put("SUBREPORT_DIR", path);

    	return parametros;
	}
}
