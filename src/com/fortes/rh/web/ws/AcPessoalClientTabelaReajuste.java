package com.fortes.rh.web.ws;

import java.util.Collection;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;

import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.ws.TItemTabelaEmpregados;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.util.DateUtil;

public class AcPessoalClientTabelaReajuste implements AcPessoalClientTabelaReajusteInterface
{
	private AcPessoalClient acPessoalClient;

	public void aplicaReajuste(Collection<HistoricoColaborador> historicosAc, Empresa empresa) throws Exception
	{
		saveHistoricoColaborador(historicosAc, empresa, 0.0, true);
	}

	public void saveHistoricoColaborador(Collection<HistoricoColaborador> historicosAc, Empresa empresa, Double salarioAntigo, boolean ehRealinhamento) throws Exception
	{
		String exceptionMessage = "";
		 if (ehRealinhamento)
			 exceptionMessage = "Inserção do Planejamento de Realinhamento falhou no AC Pessoal";
		 else
			 exceptionMessage = "Erro ao inserir Situação no AC Pessoal";
		
		try
		{
			if(salarioAntigo == null)
				salarioAntigo = 0.0;

			String token = acPessoalClient.getToken(empresa);
			
			Call call = acPessoalClient.createCall(empresa.getAcUrlSoap(), "SetTabelaRhEmpregados");

            QName qname = new QName(empresa.getAcUrlWsdl(),"TItemTabelaEmpregados");
            call.registerTypeMapping(TItemTabelaEmpregados.class, qname, new BeanSerializerFactory(TItemTabelaEmpregados.class, qname), new BeanDeserializerFactory(TItemTabelaEmpregados.class, qname));

	        call.addParameter("Token",org.apache.axis.encoding.XMLType.XSD_STRING,ParameterMode.IN);
	        call.addParameter("Tabela",org.apache.axis.encoding.XMLType.SOAP_ARRAY,ParameterMode.IN);

	        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_BOOLEAN);

	        TItemTabelaEmpregados[] arrayReajuste = new TItemTabelaEmpregados[historicosAc.size()];

	        int cont = 0;
	        for (HistoricoColaborador historico : historicosAc)
			{
	        	TItemTabelaEmpregados item = new TItemTabelaEmpregados();
	        	item.setCargo(historico.getFaixaSalarial().getCodigoAC());
	        	item.setCodigo(historico.getColaborador().getCodigoAC());
	        	item.setData(DateUtil.formataDiaMesAno(historico.getData()));
	        	item.setEmpresa(empresa.getCodigoAC());
	        	item.setLotacao(historico.getAreaOrganizacional().getCodigoAC());
	        	item.setRh_sep_id(Integer.valueOf(historico.getId().toString()));
	        	item.setValor_alt(salarioAntigo); // Este valor só é utilizado no update de um único histórico.
	        	item.setEstabelecimento(historico.getEstabelecimento().getCodigoAC());
	        	item.setSaltipo(String.valueOf(TipoAplicacaoIndice.getCodigoAC(historico.getTipoSalario())));
	        	
	        	item.setExpAgenteNocivo(historico.getGfip());

	    		switch (historico.getTipoSalario())
	    		{
	    			case TipoAplicacaoIndice.CARGO:
	    			{
	    				item.setIndcodigosalario("");
	    				item.setIndqtde(0.0);
	    				item.setValor(0.0);
	    				break;
	    			}
	    			case TipoAplicacaoIndice.INDICE:
	    				item.setIndcodigosalario(historico.getIndice().getCodigoAC());
	    				item.setIndqtde(historico.getQuantidadeIndice());
	    				item.setValor(0.0);
	    				break;
	    			case TipoAplicacaoIndice.VALOR:
	    				item.setIndcodigosalario("");
	    				item.setIndqtde(0.0);
	    				item.setValor(historico.getSalarioCalculado());
	    				break;
	    		}

	        	arrayReajuste[cont++] = item;
			}

	       	Object[] param = new Object[]{token, arrayReajuste};

	        boolean result = (Boolean) call.invoke(param);
	        
	        if(!result)
	        	throw new IntegraACException(exceptionMessage);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
			throw new IntegraACException(exception, exceptionMessage);
		}
	}

	public void deleteHistoricoColaboradorAC(Empresa empresa, TSituacao... situacao) throws Exception
	{
		try
		{
			String token = acPessoalClient.getToken(empresa);
			
			Call call = acPessoalClient.createCall(empresa.getAcUrlSoap(), "DelTabelaRhEmpregados");

			QName xmlstring = new QName("xs:string");

			QName qname = new QName(empresa.getAcUrlWsdl(),"TSituacao");
			call.registerTypeMapping(TSituacao.class, qname, new BeanSerializerFactory(TSituacao.class, qname), new BeanDeserializerFactory(TSituacao.class, qname));

			call.addParameter("token",xmlstring,ParameterMode.IN);
			call.addParameter("situacoes", org.apache.axis.encoding.XMLType.SOAP_ARRAY, ParameterMode.IN);

			call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_BOOLEAN);

			Object[] param = new Object[]{token, situacao};
			boolean result = (Boolean) call.invoke(param);

			if (!result)
				throw new Exception();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IntegraACException(e, "Erro ao excluir Situação no AC Pessoal.");
		}
	}

	public void setAcPessoalClient(AcPessoalClient acPessoalClient)
	{
		this.acPessoalClient = acPessoalClient;
	}
}