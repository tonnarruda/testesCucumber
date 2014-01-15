/* Autor: Bruno Bachiega
 * Data: 7/06/2006
 * Requisito: RFA004 */
package com.fortes.rh.web.action.geral;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.util.BooleanUtil;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.RelatorioUtil;
import com.fortes.rh.util.StringUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

public class AreaOrganizacionalListAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;

	private AreaOrganizacionalManager areaOrganizacionalManager;

	private Collection<AreaOrganizacional> areaOrganizacionals;
	private AreaOrganizacional areaOrganizacional = new AreaOrganizacional();
	private Map<String,Object> parametros;

	private Long areaId;
	private String areasOrganizacionaisJson;
	private String organograma;
	
	private boolean integradoAC;
	private char ativa = 'S';

	public String list() throws Exception
	{
		//foi retirado a paginação pois não dava para ordenar(Francisco, 03/06/09)
		getAreasOrdenadas();

		integradoAC = getEmpresaSistema().isAcIntegra();

		return Action.SUCCESS;
	}
	
	public String organograma() throws Exception
	{
		return Action.SUCCESS;
	}
	
	public String downloadOrganograma() throws Exception
	{
		if (!StringUtil.isBlank(organograma))
		{
			String imagem = organograma.substring(organograma.indexOf(',') + 1);
			
			byte[] imgBytes = Base64.decodeBase64(imagem.getBytes());
	
			HttpServletResponse response = ServletActionContext.getResponse();

			response.addHeader("Expires", "0");
			response.addHeader("Pragma", "no-cache");
			response.setContentType("application/force-download");
			response.setContentLength((int)imgBytes.length);
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition","attachment; filename=\"organograma.png\"");

			response.getOutputStream().write(imgBytes);
		}
		
		return Action.SUCCESS;
	}
	
	
	public String imprimirLista() throws Exception
	{
		getAreasOrdenadas();
		
		parametros = RelatorioUtil.getParametrosRelatorio("Relatório de Areas Organizacionais", getEmpresaSistema(),"Ativas: " + BooleanUtil.getDescricao(ativa));
		
		if (areaOrganizacionals.isEmpty()) 
		{
			addActionMessage("Não existem dados para o filtro informado");
			list();
			return Action.INPUT;
		}
		
		return Action.SUCCESS;
	}
	
	private void getAreasOrdenadas() throws Exception 
	{
		areaOrganizacionals = areaOrganizacionalManager.findAllList(0, 0,areaOrganizacional.getNome(), getEmpresaSistema().getId(), BooleanUtil.getValueCombo(ativa));
		
		Collection<AreaOrganizacional> areasTmp = areaOrganizacionalManager.findAllListAndInativas(getEmpresaSistema().getId(), AreaOrganizacional.TODAS, null);
		areasTmp = areaOrganizacionalManager.montaFamilia(areasTmp);
		areaOrganizacionals = areaOrganizacionalManager.getDistinctAreaMae(areasTmp, areaOrganizacionals);
		CollectionUtil<AreaOrganizacional> cu1 = new CollectionUtil<AreaOrganizacional>();
		areaOrganizacionals = cu1.sortCollectionStringIgnoreCase(areaOrganizacionals, "descricao");
	}

	public String delete() throws Exception
	{
		try {
			if(getEmpresaSistema().getId().equals(areaOrganizacional.getEmpresa().getId()))
			{
				areaOrganizacionalManager.deleteLotacaoAC(areaOrganizacional, getEmpresaSistema());
				addActionSuccess("Área organizacional excluída com sucesso.");
			}
			else
				addActionWarning("A área organizacional solicitada não existe na empresa " + getEmpresaSistema().getNome() +".");
		
		} catch (IntegraACException e) {
			addActionError(e.getMessage());
			e.printStackTrace();
		
		} catch (Exception e) {
			addActionError("Não foi possível excluir a Área Organizacional.");
			e.printStackTrace();
		}

		return Action.SUCCESS;
	}

	public Collection<AreaOrganizacional> getAreaOrganizacionals()
	{
		return areaOrganizacionals;
	}

	public AreaOrganizacional getAreaOrganizacional()
	{
		if(areaOrganizacional == null)
			areaOrganizacional = new AreaOrganizacional();
		return areaOrganizacional;
	}

	public void setAreaOrganizacional(AreaOrganizacional areaOrganizacional)
	{
		this.areaOrganizacional=areaOrganizacional;
	}

	public void setAreaOrganizacionalManager(AreaOrganizacionalManager areaOrganizacionalManager)
	{
		this.areaOrganizacionalManager=areaOrganizacionalManager;
	}

	public boolean isIntegradoAC()
	{
		return integradoAC;
	}

	public void setIntegradoAC(boolean integradoAC)
	{
		this.integradoAC = integradoAC;
	}


	public Map<String, Object> getParametros() {
		return parametros;
	}

	public char getAtiva() {
		return ativa;
	}

	public void setAtiva(char ativa) {
		this.ativa = ativa;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreasOrganizacionaisJson() {
		return areasOrganizacionaisJson;
	}

	public void setAreasOrganizacionaisJson(String areasOrganizacionaisJson) {
		this.areasOrganizacionaisJson = areasOrganizacionaisJson;
	}

	public void setOrganograma(String organograma) {
		this.organograma = organograma;
	}
}