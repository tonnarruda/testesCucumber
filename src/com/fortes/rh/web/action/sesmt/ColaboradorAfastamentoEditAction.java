package com.fortes.rh.web.action.sesmt;


import java.util.Collection;

import com.fortes.rh.business.geral.CidManager;
import com.fortes.rh.business.geral.ColaboradorManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.sesmt.AfastamentoManager;
import com.fortes.rh.business.sesmt.ColaboradorAfastamentoManager;
import com.fortes.rh.exception.FortesDataAdmisao;
import com.fortes.rh.model.dicionario.StatusRetornoAC;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.sesmt.Afastamento;
import com.fortes.rh.model.sesmt.ColaboradorAfastamento;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportEdit;

public class ColaboradorAfastamentoEditAction extends MyActionSupportEdit
{
	private static final long serialVersionUID = 1L;

	private ColaboradorAfastamentoManager colaboradorAfastamentoManager;
	private ColaboradorAfastamento colaboradorAfastamento;
	private ColaboradorManager colaboradorManager;
	private AfastamentoManager afastamentoManager;
	private CidManager cidManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;

	private Collection<Afastamento> afastamentos;

	private Collection<Colaborador> colaboradors;
	private Colaborador colaborador = new Colaborador();
	private String descricao;

	private void prepare() throws Exception
	{
		if(colaboradorAfastamento != null && colaboradorAfastamento.getId() != null)
		{
			colaboradorAfastamento = (ColaboradorAfastamento) colaboradorAfastamentoManager.findById(colaboradorAfastamento.getId());
			descricao = cidManager.findDescricaoByCodigo(colaboradorAfastamento.getCid());
		}
		
		afastamentos = afastamentoManager.findAll(new String[]{"descricao"});
	}

	public String prepareInsert() throws Exception
	{
		return SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return SUCCESS;
	}

	public String insert() throws Exception
	{
		try {
			verificaDataAdmissao();
			if(colaboradorAfastamentoManager.possuiAfastamentoNestePeriodo(colaboradorAfastamento, false)) {
				colaboradorAfastamentoManager.save(colaboradorAfastamento);
				gerenciadorComunicacaoManager.enviaAvisoDeAfastamento(colaboradorAfastamento.getId(), getEmpresaSistema());
				addActionMessage("Afastamento gravado com sucesso.");

				return SUCCESS;
			}else {
				addActionMessage("O colaborador já possui um afastamento que compreende este período.");
				filtrarColaboradores();

				return INPUT;
			}
		}
		catch(FortesDataAdmisao e){
			addActionMessage(e.getMessage());
			prepareInsert();
			return INPUT;
		}catch (Exception e) {
			addActionError("Não foi possível gravar o afastamento.");
			filtrarColaboradores();
			return INPUT;
		}
	}

	private void verificaDataAdmissao() throws FortesDataAdmisao {
		colaborador = colaboradorManager.findColaboradorByIdProjection(colaboradorAfastamento.getColaborador().getId());
		if(colaborador.getDataAdmissao().compareTo(colaboradorAfastamento.getInicio()) == 1)
			throw new FortesDataAdmisao("Data do afastamento não pode ser inferior à data de admissão (Data Admissão: " + colaborador.getDataAdmissaoFormatada() + ")." );
	}

	public String update() throws Exception
	{
		try
		{
			verificaDataAdmissao();
			if(colaboradorAfastamentoManager.possuiAfastamentoNestePeriodo(colaboradorAfastamento, true))
			{
				colaboradorAfastamentoManager.update(colaboradorAfastamento);
				addActionMessage("Afastamento gravado com sucesso.");
			}else
			{
				addActionMessage("O colaborador já possui um afastamento que compreende este período.");
				prepare();

				return INPUT;
			}

			return SUCCESS;
		}
		catch(FortesDataAdmisao e){
			addActionMessage(e.getMessage());
			prepareUpdate();
			return INPUT;
		}
		catch (Exception e)
		{
			addActionError("Não foi possível gravar o afastamento.");
			prepare();
			return INPUT;
		}
	}

	public String filtrarColaboradores() throws Exception
	{
		colaborador.setPessoalCpf(StringUtil.removeMascara(colaborador.getPessoal().getCpf()));
		colaboradors = colaboradorManager.findByNomeCpfMatricula(colaborador, false, null, StatusRetornoAC.CONFIRMADO, getEmpresaSistema().getId());

		if (colaboradors == null || colaboradors.isEmpty())
		{
			addActionMessage("Nenhum colaborador para o filtro informado.");
			return INPUT;
		}

		prepare();

		return SUCCESS;
	}

	public ColaboradorAfastamento getColaboradorAfastamento()
	{
		if(colaboradorAfastamento == null)
			colaboradorAfastamento = new ColaboradorAfastamento();
		return colaboradorAfastamento;
	}

	public void setColaboradorAfastamento(ColaboradorAfastamento colaboradorAfastamento)
	{
		this.colaboradorAfastamento = colaboradorAfastamento;
	}

	public void setColaboradorAfastamentoManager(ColaboradorAfastamentoManager colaboradorAfastamentoManager)
	{
		this.colaboradorAfastamentoManager = colaboradorAfastamentoManager;
	}

	public void setColaboradorManager(ColaboradorManager colaboradorManager)
	{
		this.colaboradorManager = colaboradorManager;
	}

	public Collection<Colaborador> getColaboradors()
	{
		return colaboradors;
	}

	public Colaborador getColaborador()
	{
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador)
	{
		this.colaborador = colaborador;
	}

	public Collection<Afastamento> getAfastamentos()
	{
		return afastamentos;
	}

	public void setAfastamentoManager(AfastamentoManager afastamentoManager)
	{
		this.afastamentoManager = afastamentoManager;
	}

	public void setCidManager(CidManager cidManager) {
		this.cidManager = cidManager;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setGerenciadorComunicacaoManager(
			GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}
}