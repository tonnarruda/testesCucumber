/* Autor: Robertson Freitas
 * Data: 04/07/2006
 * Requisito: RFA019 - Solicitar Banco de Dados Solidário */
package com.fortes.rh.web.action.captacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.model.type.File;
import com.fortes.rh.business.captacao.CandidatoManager;
import com.fortes.rh.business.captacao.EmpresaBdsManager;
import com.fortes.rh.business.captacao.SolicitacaoBDSManager;
import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.model.captacao.EmpresaBds;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoBDS;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.dicionario.Escolaridade;
import com.fortes.rh.model.dicionario.Sexo;
import com.fortes.rh.model.dicionario.Vinculo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.ArquivoUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings("serial")
public class SolicitacaoBDSEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired private CargoManager cargoManager;
	@Autowired private CandidatoManager candidatoManager;
	@Autowired private EmpresaBdsManager empresaBdsManager;
	@Autowired private SolicitacaoBDSManager solicitacaoBDSManager;
	@Autowired private AreaOrganizacionalManager areaOrganizacionalManager;

	private String empresasBdsEnviadas;
	private SolicitacaoBDS solicitacaoBDS;
	private Solicitacao solicitacao;
	private File arquivoBDS;

	private Collection<AreaOrganizacional> areas;
	private Collection<Cargo> cargos;

	// declaração do Manager e do Modelo de EmpresaBDS
	private Collection<EmpresaBds> empresasBDS = new ArrayList<EmpresaBds>();

	// variaveis que guardam os IDs das EmpresasBDS selecionadas no formulario
	private Collection<Long> empresasBDSIdSelecionadas = new ArrayList<Long>();
	private Collection<Long> empresasBDSIdNaoSelecionadas = new ArrayList<Long>();

	// variaveis que guardam a conversão de Collection para Maps, exigidos pelo componente @ww.optiontransferselect
	private Map<Long,String> empresasBDSSelecionadasMap = new HashMap<Long, String>();
	private Map<Long,String> empresasBDSNaoSelecionadasMap = new HashMap<Long,String>();

	private Map vinculos;
	private Map escolaridades;
	private Map sexos;

	private Mail mail;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if (solicitacaoBDS != null && solicitacaoBDS.getId() != null)
		{
			solicitacaoBDS = (SolicitacaoBDS) solicitacaoBDSManager.findById(solicitacaoBDS.getId());
		}

		Long empresaId = SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession()).getId();
		areas = areaOrganizacionalManager.findAllListAndInativas(AreaOrganizacional.TODAS, null, empresaId);

		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areas = cu1.sortCollectionStringIgnoreCase(areas, "descricao");

		cargos = cargoManager.findAllSelect("nome", null, Cargo.TODOS, empresaId);

		// pega td do banco do tipo EmpresaBDS a joga num HashMap
		empresasBDS = empresaBdsManager.findAll();
		for (Iterator iter = empresasBDS.iterator(); iter.hasNext();)
		{
			EmpresaBds a = (EmpresaBds) iter.next();
			empresasBDSNaoSelecionadasMap.put(a.getId(), a.getNome());
		}
		setEmpresasBdsEnviadas();

		vinculos = new Vinculo();
		escolaridades = new Escolaridade();
		sexos = new Sexo();
	}

	public String prepareInsert() throws Exception
	{
		prepare();

		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();

		// preenche um HashMap com as EmpresasBDS q pertencem a uma determindada EmpresaBDS
		for (Iterator iter = solicitacaoBDS.getEmpresasBDSs().iterator(); iter.hasNext();)
		{
			EmpresaBds empresaBdsTmp = (EmpresaBds) iter.next();
			empresasBDSSelecionadasMap.put(empresaBdsTmp.getId(), empresaBdsTmp.getNome());
			empresasBDSNaoSelecionadasMap.remove(empresaBdsTmp.getId());
		}
		return Action.SUCCESS;
	}

	public String consulta() throws Exception
	{
		return prepareUpdate();
	}

	public String insert() throws Exception
	{
		Collection<EmpresaBds> empresaBdsTmp = new ArrayList<EmpresaBds>();
		EmpresaBds empresaBdsAux = null;

		// povoa o Collection com as EmpresasBDS cujos Ids foram enviados na submissão do formulário de inserção
		for (Iterator iter = getEmpresasBDSIdSelecionadas().iterator(); iter.hasNext();)
		{
			Object obj = iter.next();

			empresaBdsAux = new EmpresaBds();
			empresaBdsAux.setId(Long.parseLong(obj.toString()));
			empresaBdsTmp.add(empresaBdsAux);

			//TODO verificar se esse set serve para alguma coisa era pra ir apenas id? Francisco
			solicitacaoBDS.setAreaOrganizacional(areaOrganizacionalManager.findById(solicitacaoBDS.getAreaOrganizacional().getId()));
			solicitacaoBDS.setCargo(cargoManager.findById(solicitacaoBDS.getCargo().getId()));

		}

		solicitacaoBDS.setEmpresasBDSs(empresaBdsTmp);
		solicitacaoBDSManager.save(solicitacaoBDS);

		return Action.SUCCESS;
	}

	public String update() throws Exception
	{

		Collection<EmpresaBds> empresaBdsTmp = new ArrayList<EmpresaBds>();
		// povoa o Collection com as EmpresasBDS cujos Ids foram enviados na submissão do formulário de inserção
		for (Iterator iter = getEmpresasBDSIdSelecionadas().iterator(); iter.hasNext();)
		{
			Object obj = iter.next();
			Long empresaBDSIdTmp = Long.parseLong(obj.toString());
			EmpresaBds aa = empresaBdsManager.findById(empresaBDSIdTmp);
			empresaBdsTmp.add(aa);
		}
		solicitacaoBDS.setEmpresasBDSs(empresaBdsTmp);
		solicitacaoBDSManager.update(solicitacaoBDS);

		return Action.SUCCESS;
	}

	public String prepareImportacaoBDS() throws Exception
	{
		return Action.SUCCESS;
	}

	public String importacaoBDS() throws Exception
	{
		if (solicitacaoBDSManager.validaArquivoBDS(arquivoBDS))
		{
			// Upload do arquivo .fortesrh
			java.io.File arquivoSalvo = ArquivoUtil.salvaArquivo(null, arquivoBDS, false);

			solicitacao.setEmpresa(getEmpresaSistema());

			try
			{
				candidatoManager.importaBDS(arquivoSalvo, solicitacao);
				return Action.SUCCESS;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				addActionError("Não foi possível importar os Candidatos");
				return Action.INPUT;
			}
		}
		else
		{
			addActionError("Formato do arquivo inválido");
			return Action.INPUT;
		}
	}

	public SolicitacaoBDS getSolicitacaoBDS()
	{
		if (solicitacaoBDS == null)
		{
			solicitacaoBDS = new SolicitacaoBDS();
		}
		return solicitacaoBDS;
	}

	public void setSolicitacaoBDS(SolicitacaoBDS solicitacaoBDS)
	{
		this.solicitacaoBDS = solicitacaoBDS;
	}

	public Object getModel()
	{
		return getSolicitacaoBDS();
	}

	public Collection<AreaOrganizacional> getAreas()
	{
		return areas;
	}

	public void setAreas(Collection<AreaOrganizacional> areas)
	{
		this.areas = areas;
	}

	public Collection<Cargo> getCargos()
	{
		return cargos;
	}

	public void setCargos(Collection<Cargo> cargos)
	{
		this.cargos = cargos;
	}

	public Collection<Long> getEmpresasBDSIdNaoSelecionadas()
	{
		return empresasBDSIdNaoSelecionadas;
	}

	public void setEmpresasBDSIdNaoSelecionadas(Collection<Long> empresasBDSIdNaoSelecionadas)
	{
		this.empresasBDSIdNaoSelecionadas = empresasBDSIdNaoSelecionadas;
	}

	public Collection<Long> getEmpresasBDSIdSelecionadas()
	{
		return empresasBDSIdSelecionadas;
	}

	public void setEmpresasBDSIdSelecionadas(Collection<Long> empresasBDSIdSelecionadas)
	{
		this.empresasBDSIdSelecionadas = empresasBDSIdSelecionadas;
	}

	public Map getEmpresasBDSNaoSelecionadasMap()
	{
		return empresasBDSNaoSelecionadasMap;
	}

	public void setEmpresasBDSNaoSelecionadasMap(Map<Long,String> empresasBDSNaoSelecionadasMap)
	{
		this.empresasBDSNaoSelecionadasMap = empresasBDSNaoSelecionadasMap;
	}

	public Map getEmpresasBDSSelecionadasMap()
	{
		return empresasBDSSelecionadasMap;
	}

	public void setEmpresasBDSSelecionadasMap(Map<Long,String> empresasBDSSelecionadasMap)
	{
		this.empresasBDSSelecionadasMap = empresasBDSSelecionadasMap;
	}

	public Collection<EmpresaBds> getEmpresasBDS()
	{
		return empresasBDS;
	}

	public void setEmpresasBDS(Collection<EmpresaBds> empresasBDS)
	{
		this.empresasBDS = empresasBDS;
	}

	public Solicitacao getSolicitacao()
	{
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao)
	{
		this.solicitacao = solicitacao;
	}

	public Mail getMail()
	{
		return mail;
	}

	public void setMail(Mail mail)
	{
		this.mail = mail;
	}

	public String getEmpresasBdsEnviadas()
	{
		return empresasBdsEnviadas;
	}

	public void setEmpresasBdsEnviadas()
	{
		if ((solicitacaoBDS != null) && (solicitacaoBDS.getEmpresasBDSs().size() > 0))
		{
			for (Iterator iter = solicitacaoBDS.getEmpresasBDSs().iterator(); iter.hasNext();)
			{
				EmpresaBds ebds = (EmpresaBds) iter.next();
				empresasBdsEnviadas = empresasBdsEnviadas + ebds.getNome() + "\n";
			}
		}
		else
			empresasBdsEnviadas = "";

	}

	public File getArquivoBDS()
	{
		return arquivoBDS;
	}

	public void setArquivoBDS(File arquivoBDS)
	{
		this.arquivoBDS = arquivoBDS;
	}

	public Map getVinculos()
	{
		return vinculos;
	}

	public void setVinculos(Map vinculos)
	{
		this.vinculos = vinculos;
	}

	public Map getEscolaridades()
	{
		return escolaridades;
	}

	public void setEscolaridades(Map escolaridades)
	{
		this.escolaridades = escolaridades;
	}

	public Map getSexos()
	{
		return sexos;
	}

	public void setSexos(Map sexos)
	{
		this.sexos = sexos;
	}
}