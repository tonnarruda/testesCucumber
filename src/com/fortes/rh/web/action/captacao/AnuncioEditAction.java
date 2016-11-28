package com.fortes.rh.web.action.captacao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.captacao.AnuncioManager;
import com.fortes.rh.business.captacao.SolicitacaoAvaliacaoManager;
import com.fortes.rh.business.captacao.SolicitacaoManager;
import com.fortes.rh.model.captacao.Anuncio;
import com.fortes.rh.model.captacao.Conhecimento;
import com.fortes.rh.model.captacao.Solicitacao;
import com.fortes.rh.model.captacao.SolicitacaoAvaliacao;
import com.fortes.rh.security.SecurityUtil;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.rh.util.LongUtil;
import com.fortes.rh.util.Mail;
import com.fortes.rh.web.action.MyActionSupportEdit;
import com.fortes.web.tags.CheckBox;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings("serial")
public class AnuncioEditAction extends MyActionSupportEdit implements ModelDriven
{
	@Autowired
	private AnuncioManager anuncioManager;
	@Autowired
	private SolicitacaoManager solicitacaoManager;
	@Autowired
	private SolicitacaoAvaliacaoManager solicitacaoAvaliacaoManager;

	private Collection<SolicitacaoAvaliacao> solicitacaoAvaliacaos;
	
	private String[] solicitacaoAvaliacaosCheck;
    private Collection<CheckBox> solicitacaoAvaliacaosCheckList = new ArrayList<CheckBox>();
	
	private Mail mail;
	private Anuncio anuncio;
	private Solicitacao solicitacao;
	private String acao = "";
	private String emailAvulso;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		solicitacaoAvaliacaos = solicitacaoAvaliacaoManager.findBySolicitacaoId(solicitacao.getId(), null);
		solicitacaoAvaliacaosCheckList = CheckListBoxUtil.populaCheckListBox(solicitacaoAvaliacaos, "getId", "getAvaliacaoTitulo", null);
	}

	public String anunciar() throws Exception
	{
		if (anuncioManager.verifyExists(new String[]{"solicitacao.id"}, new Object[]{solicitacao.getId()}))
			return "success2";

		return Action.SUCCESS;
	}

	public String prepareInsert() throws Exception
	{
		solicitacao = solicitacaoManager.findByIdProjectionAreaFaixaSalarial(solicitacao.getId());
		if (anuncio == null)
			anuncio = new Anuncio();
		anuncio.setSolicitacao(solicitacao);
		
		prepare();
			
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		anuncio = anuncioManager.findBySolicitacao(solicitacao.getId());
		
		prepare();
		
		Collection<SolicitacaoAvaliacao> solicitacaoAvaliacaos = solicitacaoAvaliacaoManager.findBySolicitacaoId(solicitacao.getId(), true);
		solicitacaoAvaliacaosCheckList = CheckListBoxUtil.marcaCheckListBox(solicitacaoAvaliacaosCheckList, solicitacaoAvaliacaos, "getId");
		
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		anuncio.setSolicitacao(solicitacao);
		anuncio = anuncioManager.save(anuncio);

		solicitacaoAvaliacaoManager.setResponderModuloExterno(solicitacao.getId(), LongUtil.arrayStringToArrayLong(solicitacaoAvaliacaosCheck));
		
		if(acao.equals("I"))
		{
			return "successimprime";
		}
		else//envia email
		{
			return "successemail";
		}
	}

	public String update() throws Exception
	{
		anuncioManager.update(anuncio);

		solicitacaoAvaliacaoManager.setResponderModuloExterno(solicitacao.getId(), LongUtil.arrayStringToArrayLong(solicitacaoAvaliacaosCheck));
		
		if(acao.equals("I"))
		{
			return "successimprime";
		}
		else//envia email
		{
			return "successemail";
		}
	}

	public String imprimir() throws Exception
	{
		anuncio = anuncioManager.findById(anuncio.getId());
		anuncio.getSolicitacao().getRemuneracao();

		return Action.SUCCESS;
	}

	public String email() throws Exception
	{
		if (anuncio != null && anuncio.getId() != null)
			anuncio = anuncioManager.findByIdProjection(anuncio.getId());
		
		return Action.SUCCESS;
	}

	public String enviaEmail() throws Exception
	{
		try
		{
			Map corpoAnuncio = geraCorpoAnuncio(anuncio);
			Date data = new Date();
			String fileName = "";
	
			Document document = new Document();
			try
			{
				fileName = ServletActionContext.getServletContext().getRealPath("/WEB-INF/temp") + File.separatorChar + data.getTime() +".pdf";
				PdfWriter.getInstance(document, new FileOutputStream(fileName));
				document.open();
	
				document = montaPdf(document, corpoAnuncio);
	
			}
			catch (DocumentException de)
			{
				System.err.println(de.getMessage());
			}
			catch (IOException ioe)
			{
				System.err.println(ioe.getMessage());
			}
	
			// step 5: we close the document
			document.close();
			File arquivo = new File(fileName);
	
			String[] emails = anuncioManager.montaEmails(emailAvulso, null);
			mail.send(SecurityUtil.getEmpresaSession(ActionContext.getContext().getSession()), anuncio.getTitulo(), geraCorpoAnuncioHtml(corpoAnuncio), new File[]{arquivo}, emails);
			
			addActionMessage("Anúncio enviado com sucesso.");
	
			//deletar pdf criado para envio do email
			arquivo.delete();
			return Action.SUCCESS;
			
		} catch (Exception e)
		{
			e.printStackTrace();
			addActionError("Erro ao enviar email");
			email();
			return Action.INPUT;
		}
	}

	private String geraCorpoAnuncioHtml(Map corpoAnuncio)
	{
		String corpo = "";

		corpo += "<h3 align='center'>" + (String) corpoAnuncio.get("titulo") + "</h3><br>";

		corpo += (String) corpoAnuncio.get("cabecalho")+"<br><br>";

		if(corpoAnuncio.get("cargo") != null)
		{
			corpo += "<BR><b>CARGO:</b><br>";
			corpo += (String) corpoAnuncio.get("cargo")+"<br>";
		}
		if(corpoAnuncio.get("salario") != null)
		{
			corpo += "<BR><b>SALÁRIO:</b><br>";
			corpo += "R$ "+(String) corpoAnuncio.get("salario")+"<br>";
		}
		if(corpoAnuncio.get("sexo") != null)
		{
			corpo += "<BR><b>SEXO:</b><br>";
			corpo += (String) corpoAnuncio.get("sexo")+"<br>";
		}

		if(corpoAnuncio.get("idadeMin") != null && corpoAnuncio.get("idadeMax") != null)
		{
			corpo += "<BR><b>FAIXA ETÁRIA PREFERENCIAL:</b><br>";
			corpo += (Integer) corpoAnuncio.get("idadeMin")+" - "+(Integer) corpoAnuncio.get("idadeMax")+"<br><br>";
		}
		if(corpoAnuncio.get("conhecimento") != null && !((String)corpoAnuncio.get("conhecimento")).trim().equals(""))
		{
			corpo += "<b>CONHECIMENTOS:</b><ul>";
			corpo += ((String) corpoAnuncio.get("conhecimento")).replaceAll("\n", "<li>") + "</ul>";
		}

		if(corpoAnuncio.get("beneficio") != null && !((String)corpoAnuncio.get("beneficio")).trim().equals(""))
		{
			corpo += "<BR><b>BENEFÍCIOS:</b><ul>";
			corpo += ((String) corpoAnuncio.get("beneficio")).replaceAll("\n", "<li>") + "</ul>";
		}

		if(corpoAnuncio.get("informacoes") != null && !((String)corpoAnuncio.get("informacoes")).trim().equals(""))
		{
			corpo += "<BR><b>INFORMAÇÕES COMPLEMENTARES:</b><br>";
			corpo += (String) corpoAnuncio.get("informacoes")+"<br>";
		}

		return corpo;
	}

	private Document montaPdf(Document document, Map corpoAnuncio) throws DocumentException
	{
		Font fontTitulo = new Font(Font.COURIER, 24, Font.BOLD);
		Font fontItem = new Font(Font.COURIER, 16, Font.BOLD);
		Font fontTexto = new Font(Font.COURIER, 16, Font.NORMAL);

		Paragraph titulo = new Paragraph((String) corpoAnuncio.get("titulo")+"\n\n", fontTitulo);
		titulo.setAlignment(Element.ALIGN_CENTER);
		document.add(titulo);
		document.add(new Paragraph((String) corpoAnuncio.get("cabecalho")+"\n\n", fontTexto));

		if(corpoAnuncio.get("cargo") != null)
		{
			document.add(new Paragraph("Cargo:", fontItem));
			document.add(new Paragraph((String) corpoAnuncio.get("cargo")+"\n\n", fontTexto));
		}

		if(corpoAnuncio.get("salario") != null)
		{
			document.add(new Paragraph("Salário:", fontItem));
			document.add(new Paragraph("R$ "+(String) corpoAnuncio.get("salario")+"\n\n", fontTexto));
		}

		if(corpoAnuncio.get("sexo") != null)
		{
			document.add(new Paragraph("Sexo:", fontItem));
			document.add(new Paragraph((String) corpoAnuncio.get("sexo")+"\n\n", fontTexto));
		}

		if(corpoAnuncio.get("idadeMin") != null && corpoAnuncio.get("idadeMax") != null)
		{
			document.add(new Paragraph("Faixa Etária Preferencial:", fontItem));
			document.add(new Paragraph((Integer) corpoAnuncio.get("idadeMin")+" - "+(Integer) corpoAnuncio.get("idadeMax")+"\n\n", fontTexto));
		}

		if(corpoAnuncio.get("conhecimento") != null && !((String)corpoAnuncio.get("conhecimento")).trim().equals(""))
		{
			document.add(new Paragraph("Conhecimentos:", fontItem));
			document.add(new Paragraph((String) corpoAnuncio.get("conhecimento")+"\n", fontTexto));
		}

		if(corpoAnuncio.get("beneficio") != null && !((String)corpoAnuncio.get("beneficio")).trim().equals(""))
		{
			document.add(new Paragraph("Benefícios:", fontItem));
			document.add(new Paragraph((String) corpoAnuncio.get("beneficio")+"\n", fontTexto));
		}

		if(corpoAnuncio.get("informacoes") != null && !((String)corpoAnuncio.get("informacoes")).trim().equals(""))
		{
			document.add(new Paragraph("Informações Complementares:", fontItem));
			document.add(new Paragraph((String) corpoAnuncio.get("informacoes")+"\n\n", fontTexto));
		}

		return document;
	}

	@SuppressWarnings("unchecked")
	private Map geraCorpoAnuncio(Anuncio anuncioAux)
	{
		Map corpo = new HashMap<String, String>();

		anuncioAux = anuncioManager.findById(anuncioAux.getId());
//		anuncioAux.getSolicitacao().setBeneficios(new CollectionUtil().distinctCollection(anuncioAux.getSolicitacao().getBeneficios()));

		corpo.put("titulo", anuncioAux.getTitulo());
		corpo.put("cabecalho", anuncioAux.getCabecalho());

		if(anuncioAux.isMostraConhecimento())
		{
			String conhecimento = "";

			if(anuncioAux.getSolicitacao().getAreaOrganizacional() != null && anuncioAux.getSolicitacao().getAreaOrganizacional().getConhecimentos() != null)
			{
				for(Conhecimento c : anuncioAux.getSolicitacao().getAreaOrganizacional().getConhecimentos())
				{
					conhecimento += "\n" + c.getNome();
				}
			}

			corpo.put("conhecimento", conhecimento);
		}
		
//		if(anuncioAux.isMostraBeneficio())
//		{
//			String beneficio = "";
//			if(anuncioAux.getSolicitacao().getBeneficios() != null)
//			{
//				for(Beneficio b : anuncioAux.getSolicitacao().getBeneficios())
//				{
//					beneficio += "\n" + b.getNome();
//				}
//			}
//
//			corpo.put("beneficio", beneficio);
//		}
		
		if(anuncioAux.isMostraSalario())
		{
			if(anuncioAux.getSolicitacao().getFaixaSalarial() != null && anuncioAux.getSolicitacao().getFaixaSalarial().getFaixaSalarialHistoricoAtual() != null && anuncioAux.getSolicitacao().getFaixaSalarial().getFaixaSalarialHistoricoAtual().getValor() != null)
				corpo.put("salario", (anuncioAux.getSolicitacao().getFaixaSalarial().getFaixaSalarialHistoricoAtual().getValor()).toString());
		}
		if(anuncioAux.isMostraCargo())
		{
			if(anuncioAux.getSolicitacao().getFaixaSalarial() != null &&
				anuncioAux.getSolicitacao().getFaixaSalarial().getCargo() != null &&
				anuncioAux.getSolicitacao().getFaixaSalarial().getCargo().getNomeMercado() != null)
			{
				corpo.put("cargo", anuncioAux.getSolicitacao().getFaixaSalarial().getCargo().getNomeMercado());
			}
		}
		if(anuncioAux.isMostraSexo())
		{
			String sexo = "";
			if(anuncioAux.getSolicitacao().getSexo() != null)
			{
				String sexoTmp = anuncioAux.getSolicitacao().getSexo();
				if(sexoTmp.equals("I"))
					sexo += "Indiferente";
				if(sexoTmp.equals("M"))
					sexo += "Masculino";
				if(sexoTmp.equals("F"))
					sexo += "Feminino";
			}

			corpo.put("sexo", sexo);
		}
		if(anuncioAux.isMostraIdade())
		{
			corpo.put("idadeMin", anuncioAux.getSolicitacao().getIdadeMinimaDesc());
			corpo.put("idadeMax", anuncioAux.getSolicitacao().getIdadeMaximaDesc());
		}
		if(anuncioAux.getInformacoes() != null && anuncioAux.getInformacoes().trim().equals(""))
		{
			if(anuncioAux.getSolicitacao().getInfoComplementares() != null)
				corpo.put("informacoes", anuncioAux.getSolicitacao().getInfoComplementares());
		}

		return corpo;
	}

	public Anuncio getAnuncio()
	{
		if(anuncio == null)
		{
			anuncio = new Anuncio();
		}
		return anuncio;
	}

	public void setAnuncio(Anuncio anuncio){
		this.anuncio=anuncio;
	}

	public Object getModel()
	{
		return getAnuncio();
	}

	public Solicitacao getSolicitacao()
	{
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao)
	{
		this.solicitacao = solicitacao;
	}

	public String getAcao()
	{
		return acao;
	}

	public void setAcao(String acao)
	{
		this.acao = acao;
	}

	public String getEmailAvulso()
	{
		return emailAvulso;
	}

	public void setEmailAvulso(String emailAvulso)
	{
		this.emailAvulso = emailAvulso;
	}

	public void setMail(Mail mail)
	{
		this.mail = mail;
	}

	public Collection<SolicitacaoAvaliacao> getSolicitacaoAvaliacaos() 
	{
		return solicitacaoAvaliacaos;
	}

	public void setSolicitacaoAvaliacaos(Collection<SolicitacaoAvaliacao> solicitacaoAvaliacaos) 
	{
		this.solicitacaoAvaliacaos = solicitacaoAvaliacaos;
	}

	public Collection<CheckBox> getSolicitacaoAvaliacaosCheckList() 
	{
		return solicitacaoAvaliacaosCheckList;
	}

	public void setSolicitacaoAvaliacaosCheck(String[] solicitacaoAvaliacaosCheck) 
	{
		this.solicitacaoAvaliacaosCheck = solicitacaoAvaliacaosCheck;
	}
}