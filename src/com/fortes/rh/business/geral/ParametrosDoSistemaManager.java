package com.fortes.rh.business.geral;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;

public interface ParametrosDoSistemaManager extends GenericManager<ParametrosDoSistema>
{
	public ParametrosDoSistema findByIdProjection(Long id);
	public ParametrosDoSistema findByIdProjectionSession(Long id);
	public boolean getSistemaAtualizado();
	public boolean verificaCompatibilidadeComWebServiceAC(String versaoAC, String versaoMinimaCompativel);
	public String getVersaoWebServiceAC(Empresa empresa) throws Exception;
	public boolean isACIntegrado(Empresa empresa) throws Exception;
	public String getUrlDaAplicacao();
	public String getEmailDoSuporteTecnico();
	public Boolean isIdiomaCorreto();
	public void updateServidorRemprot(String servidorRemprot);
	public String getContexto();
	public Integer getQuantidadeConstraintsDoBanco();
	public boolean isUtilizarCaptchaNoLogin(Long id);
	public boolean isAderiuAoESocial(Empresa empresa)throws Exception;
}