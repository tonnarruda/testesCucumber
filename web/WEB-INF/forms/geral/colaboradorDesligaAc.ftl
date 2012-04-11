<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Solicitação de Desligamento de Colaborador no AC Pessoal</title>

	<#include "../ftl/mascarasImports.ftl" />

	<script type="text/javascript">
		function enviarForm(acao)
		{ 
			if (!validaDatasPeriodo("${colaborador.dataAdmissao?string}", document.form.dataDesligamento.value)) 
			{
				jAlert("A data de desligamento não pode ser anterior à data de admissão\nData de admissão: ${colaborador.dataAdmissao?string}");
				return false;
			}
		
			if(validaFormulario('form', new Array('data','motivoId'), new Array('data'), true))
				newConfirm('Confirma Solicitação de Desligamento?', function(){document.form.submit();});
		}
	</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="solicitacaoDesligamento.action" method="POST">
	
		<@ww.hidden label="Id" name="colaborador.id" />
		<@ww.hidden name="colaborador.dataAdmissao" />
		<@ww.label cssStyle="font-family: Arial, Helvetica, sans-serif;font-size:16px;font-weight:bold;" name="colaborador.nome"/><br>
	
		<@ww.datepicker label="Data da Solicitação de Desligamento" name="dataDesligamento" id="data" cssClass="mascaraData" required="true"/>
		<@ww.select label="Motivo do Desligamento" name="motDemissao.motivo" id="motivoId" list="motivoDemissaos"  listKey="id" listValue="motivo" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;" required="true" />
		<@ww.textarea label="Mensagem para o AC Pessoal" name="observacaoDemissao" cssStyle="width:445px;"/>
	
	</@ww.form>
	
	<div class="buttonGroup">
		<input type="button" value=" " onclick="enviarForm(this);" class="btnSolicitarDesligamento" />
		<input type="button" value=" " onclick="window.location='list.action'" class="btnVoltar" />
	</div>
</body>
</html>