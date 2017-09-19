/* Autor: Bruno Bachiega
 * Data: 6/06/2006
 * Requisito: RFA003 */
package com.fortes.rh.dao.geral;

import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.model.geral.Empresa;

public interface EmpresaDao extends GenericDao<Empresa>
{
	Empresa findByCodigo(String codigo, String grupoAC);
	boolean getIntegracaoAC(Long id);
	Collection<Empresa> verifyExistsCnpj(String cnpj);
	String findCidade(Long id);
	Collection<Empresa> findDistinctEmpresaByQuestionario(Long questionarioId);
	Empresa findByIdProjection(Long id);
	Collection<Empresa> findByUsuarioPermissao(Long usuarioId, String... roles);
	void removeEmpresaPadrao(long id);
	Collection<Empresa> findTodasEmpresas();
	void updateCampoExtra(Long id, boolean habilitaCampoExtraColaborador, boolean habilitaCampoExtraCandidato, boolean habilitaCampoExtraAtualizarMeusDados);
	boolean checkEmpresaCodACGrupoAC(Empresa empresa);
	boolean checkEmpresaIntegradaAc();
	boolean checkEmpresaIntegradaAc(Long empresaId);
	Collection<Empresa> findComCodigoAC();
	Empresa findEmailsEmpresa(Long empresaId);
	boolean isControlaRiscoPorAmbiente(Long empresaId);
	Collection<Empresa> findEmpresasIntegradas();
	Empresa getCnae(Long empresaId);
	void updateCodigoAC(Long empresaId, String codigoAC, String grupoAC);
	Collection<Empresa> findByGruposAC(String... gruposAC);
	String getCodigoGrupoAC(Long empresaId);
	boolean emProcessoExportacaoAC(Long empresaId);
	void setProcessoExportacaoAC(Long empresaId, boolean processoExportacaoAC);
	Collection<Empresa> findByLntId(Long lntId);
	Collection<Empresa> findEmpresasComCodigoACAndAtualizouDddCelularAndUFHabilitacao();
	public void setDddCelularAndUFHabilitacaoAtualizados(Long empresaId);
}