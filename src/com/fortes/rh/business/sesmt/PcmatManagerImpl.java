package com.fortes.rh.business.sesmt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.dao.sesmt.PcmatDao;
import com.fortes.rh.exception.FortesException;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.sesmt.AreaVivenciaPcmat;
import com.fortes.rh.model.sesmt.AtividadeSegurancaPcmat;
import com.fortes.rh.model.sesmt.EpcPcmat;
import com.fortes.rh.model.sesmt.EpiPcmat;
import com.fortes.rh.model.sesmt.FasePcmat;
import com.fortes.rh.model.sesmt.MedidaRiscoFasePcmat;
import com.fortes.rh.model.sesmt.Obra;
import com.fortes.rh.model.sesmt.Pcmat;
import com.fortes.rh.model.sesmt.RiscoFasePcmat;
import com.fortes.rh.model.sesmt.SinalizacaoPcmat;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.DocX;
import com.fortes.rh.util.StringUtil;

@Component
public class PcmatManagerImpl extends GenericManagerImpl<Pcmat, PcmatDao> implements PcmatManager
{
	@Autowired private EmpresaManager empresaManager;
	@Autowired private ObraManager obraManager;
	@Autowired private FasePcmatManager fasePcmatManager;
	@Autowired private MedidaRiscoFasePcmatManager medidaRiscoFasePcmatManager;
	@Autowired private AreaVivenciaPcmatManager areaVivenciaPcmatManager;
	@Autowired private AtividadeSegurancaPcmatManager atividadeSegurancaPcmatManager;
	@Autowired private EpiPcmatManager epiPcmatManager;
	@Autowired private EpcPcmatManager epcPcmatManager;
	@Autowired private SinalizacaoPcmatManager sinalizacaoPcmatManager;
	
	@Autowired
	PcmatManagerImpl(PcmatDao pcmatDao) {
			setDao(pcmatDao);
	}
	
	public Collection<Pcmat> findByObra(Long obraId) 
	{
		return getDao().findByObra(obraId);
	}

	public void validaDataMaiorQueUltimoHistorico(Long pcmatId, Long obraId, Date aPartirDe) throws FortesException 
	{
		Pcmat ultimoPcmat = getDao().findUltimoHistorico(pcmatId, obraId);
		
		if (ultimoPcmat != null && !ultimoPcmat.getAPartirDe().before(aPartirDe)) 
			throw new FortesException("Somente é possível cadastrar um PCMAT após a data "+DateUtil.formataDiaMesAno(ultimoPcmat.getAPartirDe()) + ".");		
	}
	
	public Pcmat findUltimoHistorico(Long pcmatId, Long obraId)
	{
		return getDao().findUltimoHistorico(pcmatId, obraId);
	}

	public void clonar(Long pcmatOrigemId, Date aPartirDe, Long obraId) 
	{
		Pcmat pcmatDestino = (Pcmat) getDao().findEntidadeComAtributosSimplesById(pcmatOrigemId).clone();
		pcmatDestino.setId(null);
		pcmatDestino.setProjectionIdObra(obraId);
		pcmatDestino.setAPartirDe(aPartirDe);
		getDao().save(pcmatDestino);
		
		fasePcmatManager.clonar(pcmatOrigemId, pcmatDestino.getId());
		areaVivenciaPcmatManager.clonar(pcmatOrigemId, pcmatDestino.getId());
		atividadeSegurancaPcmatManager.clonar(pcmatOrigemId, pcmatDestino.getId());
		epiPcmatManager.clonar(pcmatOrigemId, pcmatDestino.getId());
		epcPcmatManager.clonar(pcmatOrigemId, pcmatDestino.getId());
	}
	
	public XWPFDocument gerarDocumento(Long empresaId, Long obraId, Long pcmatId) throws Exception 
	{
		Empresa empresa = empresaManager.findByIdProjection(empresaId);
		Obra obra = obraManager.findByIdProjection(obraId);
		Pcmat pcmat = findEntidadeComAtributosSimplesById(pcmatId);
		
		DocX docx = new DocX();
		
		montarCapa(docx, obra);
		montarObjetivo(docx, pcmat);
		montarIdentificacao(docx, empresa, obra, pcmat);
		montarLayoutCanteiroObra(docx);
		montarAreasVivencia(docx, pcmat);
		montarRiscos(docx, pcmat);
	    montarMedidasControleIndividualColetivas(docx, pcmat);
	    montarSinalizacao(docx, pcmat);
	    montarAtividadesSeguranca(docx, pcmat);
		
        return docx;
	}

	private void montarCapa(DocX docx, Obra obra) 
	{
		XWPFParagraph para;
		XWPFRun run;
		
		para = docx.addParagraph(obra.getNome());
		para.setAlignment(ParagraphAlignment.CENTER);
		
		run = para.getRuns().get(0);
		run.setFontSize(28);
		run.setBold(true);
		
		run.addBreak();
		run.addBreak();
		run.addBreak();
		run.addBreak();
		run.addBreak();
		
		para = docx.addParagraph("PCMAT");
		para.setAlignment(ParagraphAlignment.CENTER);
		
		run = para.getRuns().get(0);
		run.setFontSize(24);
		run.setBold(true);
		run.setUnderline(UnderlinePatterns.SINGLE);
		
		run.addBreak();
		
		para = docx.addParagraph("PROGRAMA DE CONDIÇÕES E MEIO AMBIENTE DE TRABALHO NA INDÚSTRIA DA CONSTRUÇÃO");
		para.setAlignment(ParagraphAlignment.CENTER);
		
		run = para.getRuns().get(0);
		run.setFontSize(16);
		run.setBold(true);
		run.addBreak(BreakType.PAGE);
	}

	private void montarObjetivo(DocX docx, Pcmat pcmat) 
	{
		if (!StringUtil.isBlank(pcmat.getObjetivo()))
		{
			docx.addParagraph("OBJETIVO DO PCMAT", "Heading1");
			docx.addParagraph(pcmat.getObjetivo(), null, 700, false)
				.setAlignment(ParagraphAlignment.BOTH);
		}
	}
	
	private void montarIdentificacao(DocX docx, Empresa empresa, Obra obra, Pcmat pcmat) 
	{
		docx.addParagraph("IDENTIFICAÇÃO DO ESTABELECIMENTO", "Heading1");
		
		docx.addParagraph("EMPRESA: " + empresa.getRazaoSocial(), "Heading2");
		docx.addParagraph("Endereço: " + StringUtils.defaultString(empresa.getEndereco()));
		docx.addParagraph("Cidade: " + empresa.getCidade().getNome() + "/" + empresa.getUf().getSigla());
		docx.addParagraph("CNPJ: " + empresa.getCnpj() + "   # INSERIR COMPLEMENTO DO CNPJ #").getRuns().get(0).setColor("FF0000");
		docx.addParagraph("CNAE: " + empresa.getCnae());
		
		docx.addParagraph("OBRA: " + obra.getNome(), "Heading2");
		docx.addParagraph("Tipo de Obra: " + StringUtils.defaultString(obra.getTipoObra()));
		docx.addParagraph("Endereço: " + obra.getEndereco().getEnderecoFormatado());
		docx.addParagraph("Bairro: " + StringUtils.defaultString(obra.getEndereco().getBairro()));
		docx.addParagraph("Cidade: " + obra.getEndereco().getCidadeEstado());
		docx.addParagraph("CEP: " + StringUtils.defaultString(obra.getEndereco().getCepFormatado()));
		
		docx.addParagraph("Nº DE EMPREGADOS PREVISTOS", "Heading2");
		docx.addParagraph("Número máximo de empregados: " + pcmat.getQtdFuncionarios());
		
		docx.addParagraph("CRONOGRAMA DE ÍNICIO E TÉRMINO PREVISTO", "Heading2");
		docx.addParagraph("Início: " + DateUtil.formataMesExtensoAno(pcmat.getDataIniObra()));
		docx.addParagraph("Conclusão: " + DateUtil.formataMesExtensoAno(pcmat.getDataFimObra()));
		
		docx.addParagraph("CRONOGRAMA GERAL DA OBRA", "Heading2")
			.getRuns().get(0).addBreak();
		
		XWPFTable table;
		XWPFTableRow row;
		
		Collection<FasePcmat> fasesPcmat = fasePcmatManager.findByPcmat(pcmat.getId());
		if (!fasesPcmat.isEmpty())
		{
			FasePcmat[] fases = fasesPcmat.toArray(new FasePcmat[fasesPcmat.size()]);
			int qtdMaxMeses = fases[fasesPcmat.size()-1].getMesFim();
			double qtdAnos = Math.ceil(qtdMaxMeses / 12.0);
			
			for (int nAno = 0; nAno < qtdAnos; nAno++)
			{
				table = docx.createTable();
				
				Collection<String> titulos = new ArrayList<String>();
				Collection<Integer> largs = new ArrayList<Integer>();
				
				titulos.add("FASE / MÊS");
				largs.add(100);
				
				int primeiroMes = (nAno * 12) + 1;
				int ultimoMes   = 12 * (nAno + 1);
				
				for (int nMes = primeiroMes; nMes <= ultimoMes; nMes++)
				{
					titulos.add(String.valueOf(nMes));
					largs.add(160);
				}
				
				docx.addTableHeader(table, titulos.toArray(new String[13]), largs.toArray(new Integer[13]));
				
				for (FasePcmat fasePcmat : fasesPcmat)
				{
					row = docx.addTableRow(table, fasePcmat.getFase().getDescricao());
					
					for (int mesFase = fasePcmat.getMesIni(); mesFase <= fasePcmat.getMesFim(); mesFase++)
						if (mesFase >= primeiroMes && mesFase <= ultimoMes)
							row.getCell( mesFase - primeiroMes + 1 ).setColor("999999");
				}
			}
		}
	}
	
	private void montarLayoutCanteiroObra(DocX docx) 
	{
		XWPFParagraph para;
		XWPFRun run;
		
		docx.addParagraph("LAYOUT DO CANTEIRO DE OBRA", "Heading1", null, true);
		
		para = docx.addParagraph("# INSERIR IMAGEM DO LAYOUT #");
		run = para.getRuns().get(0);
		run.setColor("FF0000");
		run.addBreak(BreakType.PAGE);
	}

	private void montarAreasVivencia(DocX docx, Pcmat pcmat) 
	{
		Collection<AreaVivenciaPcmat> areasVivenciaPcmat = areaVivenciaPcmatManager.findByPcmat(pcmat.getId());
		
        docx.addParagraph("DIMENSIONAMENTO DA ÁREA DE VIVÊNCIA", "Heading1");
        
        if (!StringUtil.isBlank(pcmat.getTextoAreasVivencia()))
			docx.addParagraph(pcmat.getTextoAreasVivencia(), null, 700, true)
				.setAlignment(ParagraphAlignment.BOTH);
        
        for (AreaVivenciaPcmat areaVivenciaPcmat : areasVivenciaPcmat) 
        {
        	docx.addParagraph(areaVivenciaPcmat.getAreaVivencia().getNome(), "Heading2");
        	docx.addParagraph(areaVivenciaPcmat.getDescricao(), null, 700, false)
				.setAlignment(ParagraphAlignment.BOTH);
		}
	}

	private void montarRiscos(DocX docx, Pcmat pcmat) 
	{
		XWPFParagraph para;
		XWPFRun run;
		XWPFTable table;
		XWPFTableRow row;
		
		Map<FasePcmat, Collection<RiscoFasePcmat>> riscosFasesPcmat = fasePcmatManager.findByPcmatRiscos(pcmat.getId());
		Map<Long, Collection<MedidaRiscoFasePcmat>> medidasRiscosFasesPcmat = medidaRiscoFasePcmatManager.findByPcmatRiscos(pcmat.getId());
		
		docx.addParagraph("MEMORIAL SOBRE CONDIÇÕES E MEIO AMBIENTE DE TRABALHO", "Heading1");
		
		if (!StringUtil.isBlank(pcmat.getTextoCondicoesTrabalho()))
			docx.addParagraph(pcmat.getTextoCondicoesTrabalho(), null, 700, true)
				.setAlignment(ParagraphAlignment.BOTH);
		
		docx.addParagraph("DESCRIÇÃO GERAL DE RISCOS", "Heading2");
		
		Iterator<Entry<FasePcmat, Collection<RiscoFasePcmat>>> faseIterator = riscosFasesPcmat.entrySet().iterator();
	    while (faseIterator.hasNext()) 
	    {
	    	Map.Entry<FasePcmat, Collection<RiscoFasePcmat>> pares = (Map.Entry<FasePcmat, Collection<RiscoFasePcmat>>) faseIterator.next();
	    	
	    	docx.addParagraph(pares.getKey().getFase().getDescricao(), "Heading3");
	    	docx.addParagraph(pares.getKey().getDescricao(), null, 700, true)
				.setAlignment(ParagraphAlignment.BOTH);
			
			if (!pares.getValue().isEmpty())
			{
				table = docx.createTable();
		        
				docx.addTableHeader(table, new String[] { "GRUPO DE RISCOS", "TIPO DE RISCO", "MEDIDAS PREVENTIVAS" }, new Integer[] { 2200, 3000, 3000 });
				
		        for (RiscoFasePcmat riscoFasePcmat : pares.getValue())
		        {
			        row = docx.addTableRow(table, riscoFasePcmat.getRisco().getDescricaoGrupoRisco(), riscoFasePcmat.getRisco().getDescricao());
			        
			        para = row.getCell(2).getParagraphs().get(0);
			        
			        if (medidasRiscosFasesPcmat.containsKey(riscoFasePcmat.getId()))
			        {
			        	Iterator<MedidaRiscoFasePcmat> medidasIterator = medidasRiscosFasesPcmat.get(riscoFasePcmat.getId()).iterator();
			        	
				        while (medidasIterator.hasNext())
				        {
					        run = para.createRun();
					        run.setText("- "+ medidasIterator.next().getMedidaSeguranca().getDescricao());
				        	
					        if (medidasIterator.hasNext())
				        		run.addBreak();
				        }
			        }
		        }
			}
	    }
	}
	
	private void montarMedidasControleIndividualColetivas(DocX docx, Pcmat pcmat) 
	{
		Collection<EpiPcmat> episPcmat = epiPcmatManager.findByPcmat(pcmat.getId());
		
		docx.addParagraph("MEDIDAS DE CONTROLE INDIVIDUAL E COLETIVA", "Heading1");
		docx.addParagraph("MEDIDA DE CONTROLE INDIVIDUAL: EPI - EQUIPAMENTOS DE PROTECÃO INDIVIDUAL", "Heading2", null, true);
		
		if (!StringUtil.isBlank(pcmat.getTextoEpis()))
			docx.addParagraph(pcmat.getTextoEpis(), null, 700, true)
				.setAlignment(ParagraphAlignment.BOTH);
		
		XWPFTable table = docx.createTable();
		docx.addTableHeader(table, new String[] { "EPI", "CARACTERÍSTICAS", "ATIVIDADES" }, new Integer[] { 2200, 3000, 3000 });
		
        for (EpiPcmat epiPcmat : episPcmat) 
        	docx.addTableRow(table, epiPcmat.getEpi().getNome(), epiPcmat.getEpi().getDescricao(), epiPcmat.getAtividades());
        
        docx.addParagraph("MEDIDAS DE CONTROLE COLETIVO: EPC - EQUIPAMENTOS DE PROTEÇÃO COLETIVA", "Heading2");
        
        if (!StringUtil.isBlank(pcmat.getTextoEpcs()))
			docx.addParagraph(pcmat.getTextoEpcs(), null, 700, true)
				.setAlignment(ParagraphAlignment.BOTH);
		
		Collection<EpcPcmat> epcsPcmat = epcPcmatManager.findByPcmat(pcmat.getId());
		
		for (EpcPcmat epcPcmat : epcsPcmat) 
		{
			docx.addParagraph(epcPcmat.getEpc().getDescricao(), "Heading3");
			docx.addParagraph(epcPcmat.getDescricao(), null, 700, false)
				.setAlignment(ParagraphAlignment.BOTH);
		}
	}

	private void montarSinalizacao(DocX docx, Pcmat pcmat) 
	{
		Collection<SinalizacaoPcmat> sinalizacaoPcmats = sinalizacaoPcmatManager.findByPcmat(pcmat.getId());
		
		docx.addParagraph("SINALIZAÇÃO", "Heading1");

		if (!StringUtil.isBlank(pcmat.getTextoSinalizacao()))
			docx.addParagraph(pcmat.getTextoSinalizacao(), null, 700, true)
				.setAlignment(ParagraphAlignment.BOTH);
		
		XWPFTable table = docx.createTable();
		docx.addTableHeader(table, new String[] { "TIPO DE CARTAZ" }, new Integer[] { 8000 });
		
		for (SinalizacaoPcmat sinalizacaoPcmat : sinalizacaoPcmats) 
			docx.addTableRow(table, sinalizacaoPcmat.getDescricao());
	}
	
	private void montarAtividadesSeguranca(DocX docx, Pcmat pcmat) 
	{
		Collection<AtividadeSegurancaPcmat> atividadesSegurancaPcmat = atividadeSegurancaPcmatManager.findByPcmat(pcmat.getId());
		
		docx.addParagraph("PROGRAMA EDUCATIVO", "Heading1");
		
		if (!StringUtil.isBlank(pcmat.getTextoAtividadesSeguranca()))
			docx.addParagraph(pcmat.getTextoAtividadesSeguranca(), null, 700, true)
				.setAlignment(ParagraphAlignment.BOTH);
		
		XWPFTable table = docx.createTable();
		docx.addTableHeader(table, new String[] { "PALESTRA", "PROGRAMAÇÃO", "CARGA HORÁRIA" }, new Integer[] { 3500, 3500, 1000 });
		
		for (AtividadeSegurancaPcmat atividade : atividadesSegurancaPcmat) 
			docx.addTableRow(table, atividade.getNome(), DateUtil.formataMesExtensoAno(atividade.getData()), atividade.getCargaHorariaEmHora());
	}
}