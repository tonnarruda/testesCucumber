package com.fortes.rh.business.geral;

import java.util.Collection;

import com.fortes.business.GenericManager;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.ParametrosDoSistema;

public interface ParametrosDoSistemaManager extends GenericManager<ParametrosDoSistema>
{
	public Collection<Integer> getDiasLembretePesquisa();
	public Collection<Integer> getDiasLembretePeriodoExperiencia();
	public ParametrosDoSistema findByIdProjection(Long id);
	public boolean getSistemaAtualizado();
	public boolean verificaCompatibilidadeComWebServiceAC(String versaoAC, String versaoMinimaCompativel);
	public String getVersaoWebServiceAC(Empresa empresa) throws Exception;
	public boolean isACIntegrado(Empresa empresa) throws Exception;
	public String findModulos(Long id);
	public void updateModulos(String papeis);
	public String[] getModulosDecodificados();
	public void disablePapeisIds();
	public String getUrlDaAplicacao();
	public String getEmailDoSuporteTecnico();
	public void updateCampoExtra(boolean campoExtraColaborador);
}