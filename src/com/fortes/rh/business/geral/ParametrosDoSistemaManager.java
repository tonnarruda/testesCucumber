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
	public void updateModulos(String papeis);
	public String[] getModulosDecodificados(ParametrosDoSistema parametros);
	public void disablePapeisIds();
	public String getUrlDaAplicacao();
	public String getEmailDoSuporteTecnico();
	public Boolean isIdiomaCorreto();
	public void ajustaCamposExtras(ParametrosDoSistema parametrosDoSistema, String[] findAllNomes);
	public void verificaRelacionamento(Long[] ids, String relacionamento, String entidade) throws Exception;
}