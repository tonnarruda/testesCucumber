package com.fortes.rh.web.action.sesmt;


import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.dao.DataIntegrityViolationException;

import com.fortes.rh.business.sesmt.ObraManager;
import com.fortes.rh.business.sesmt.PcmatManager;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.sesmt.Fase;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

public class PcmatEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	
	private PcmatManager pcmatManager;
	private ObraManager obraManager;
	
	private Pcmat pcmat;
	private Pcmat ultimoPcmat;
	private Long ultimoPcmatId = 0L;
	private Obra obra;
	
	private Collection<Pcmat> pcmats;
	private Collection<Obra> obras;
	private Collection<Fase> fases;
	private Collection<FasePcmat> fasesPcmat;
	
	private String nomeObra;
	private Date aPartirDe;

	public String prepareInsert() throws Exception
	{
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		pcmat = (Pcmat) pcmatManager.findById(pcmat.getId());
		ultimoPcmat = pcmatManager.findUltimoHistorico(null, pcmat.getObra().getId());
		
		if(ultimoPcmat != null)
			ultimoPcmatId = ultimoPcmat.getId();
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		try {
			pcmatManager.validaDataMaiorQueUltimoHistorico(null, pcmat.getObra().getId(), pcmat.getAPartirDe());
			pcmatManager.save(pcmat);
			
		} catch (DataIntegrityViolationException de) {
			addActionWarning("Já existe um PCMAT cadastrado na data " + DateUtil.formataDiaMesAno(pcmat.getAPartirDe()) + ".");
			de.printStackTrace();
			return Action.INPUT;
			
		} catch (FortesException fe) {
			addActionWarning(fe.getMessage());
			fe.printStackTrace();
			return Action.INPUT;
			
		} catch (Exception e) {
			addActionError("Ocorreu um erro ao cadastrar o PCMAT.");
			e.printStackTrace();
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		try {
			pcmatManager.validaDataMaiorQueUltimoHistorico(pcmat.getId(), pcmat.getObra().getId(), pcmat.getAPartirDe());
			pcmatManager.update(pcmat);
			addActionSuccess("PCMAT atualizado com sucesso.");
			
		} catch (DataIntegrityViolationException de) {
			addActionWarning("Já existe um PCMAT cadastrado na data " + DateUtil.formataDiaMesAno(pcmat.getAPartirDe()) + ".");
			de.printStackTrace();
			return Action.INPUT;
			
		} catch (FortesException fe) {
			addActionWarning(fe.getMessage());
			fe.printStackTrace();
			return Action.INPUT;
			
		} catch (Exception e) {
			addActionError("Ocorreu um erro ao atualizar o PCMAT.");
			e.printStackTrace();
			return Action.INPUT;
		}
		return Action.SUCCESS;
	}

	public String list() throws Exception
	{
		obras = obraManager.findAllSelect(nomeObra, getEmpresaSistema().getId());
		return Action.SUCCESS;
	}
	
	public String listPcmats() throws Exception
	{
		pcmats = pcmatManager.findByObra(this.obra.getId());
		ultimoPcmat = pcmatManager.findUltimoHistorico(null, obra.getId());
		
		if(ultimoPcmat != null)
			ultimoPcmatId = ultimoPcmat.getId();
		
		Obra obra = obraManager.findByIdProjection(this.obra.getId());
		nomeObra = obra.getNome(); 
		
		return Action.SUCCESS;
	}
	
	public String gerar() throws Exception
	{
		try {
			XWPFDocument document = pcmatManager.gerarDocumento(getEmpresaSistema().getId(), obra.getId(), pcmat.getId());
			
			HttpServletResponse response = ServletActionContext.getResponse();

			response.addHeader("Expires", "0");
			response.addHeader("Pragma", "no-cache");
			response.setContentType("application/force-download");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition","attachment; filename=\"PCMAT.docx\"");

			document.write(response.getOutputStream());
		
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Não foi possível gerar esse documento.");
		}
		
		return Action.SUCCESS;
	}

	public String delete() throws Exception
	{
		try
		{
			pcmatManager.remove(pcmat.getId());
			addActionSuccess("PCMAT excluído com sucesso.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Não foi possível excluir esse PCMAT.");
		}

		return Action.SUCCESS;
	}
	
	public String clonar() throws Exception
	{
		try {
			pcmatManager.validaDataMaiorQueUltimoHistorico(null, obra.getId(), aPartirDe);
			pcmatManager.clonar(pcmat.getId(), aPartirDe, obra.getId());
			addActionSuccess("PCMAT clonado com sucesso.");
		
		} catch (DataIntegrityViolationException de) {
			addActionWarning("Já existe um PCMAT cadastrado na data " + DateUtil.formataDiaMesAno(aPartirDe) + ".");
			de.printStackTrace();
			
		} catch (FortesException fe) {
			addActionWarning(fe.getMessage());
			fe.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Não foi possível clonar o PCMAT.");
		}
		
		return listPcmats();
	}
	
	public Pcmat getPcmat()
	{
		if(pcmat == null)
			pcmat = new Pcmat();
		return pcmat;
	}

	public void setPcmat(Pcmat pcmat)
	{
		this.pcmat = pcmat;
	}

	public void setPcmatManager(PcmatManager pcmatManager)
	{
		this.pcmatManager = pcmatManager;
	}
	
	public Collection<Pcmat> getPcmats()
	{
		return pcmats;
	}

	public Collection<Obra> getObras() {
		return obras;
	}

	public void setObraManager(ObraManager obraManager) {
		this.obraManager = obraManager;
	}

	public Obra getObra() {
		return obra;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}

	public String getNomeObra() {
		return nomeObra;
	}

	public void setNomeObra(String nomeObra) {
		this.nomeObra = nomeObra;
	}

	public Collection<Fase> getFases() {
		return fases;
	}

	public void setFases(Collection<Fase> fases) {
		this.fases = fases;
	}

	public Collection<FasePcmat> getFasesPcmat() {
		return fasesPcmat;
	}

	public void setFasesPcmat(Collection<FasePcmat> fasesPcmat) {
		this.fasesPcmat = fasesPcmat;
	}

	public Pcmat getUltimoPcmat() {
		return ultimoPcmat;
	}

	public Long getUltimoPcmatId() {
		return ultimoPcmatId;
	}

	public Date getaPartirDe() {
		return aPartirDe;
	}

	public void setaPartirDe(Date aPartirDe) {
		this.aPartirDe = aPartirDe;
	}
}
