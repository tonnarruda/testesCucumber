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
	boolean findExibirSalarioById(Long empresaId);
	Collection<Empresa> verifyExistsCnpj(String cnpj);
	String findCidade(Long id);
	Collection<Empresa> findDistinctEmpresaByQuestionario(Long questionarioId);
	Empresa findByIdProjection(Long id);
	Collection<Empresa> findByUsuarioPermissao(Long usuarioId, String... roles);
	void removeEmpresaPadrao(long id);
	Collection<Empresa> findTodasEmpresas();
	void updateCampoExtra(Long id, boolean habilitaCampoExtraColaborador, boolean habilitaCampoExtraCandidato);
	boolean checkEmpresaCodACGrupoAC(Empresa empresa);
	Collection<Empresa> findByCartaoAniversario();
	boolean checkEmpresaIntegradaAc();
	Collection<Empresa> findComCodigoAC();
	public Empresa findEmailsEmpresa(Long empresaId);
	boolean isControlaRiscoPorAmbiente(Long empresaId);
}