<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<#if colaborador.desligado>
		<title>Colaborador Desligado</title>
	<#else>
		<title>Desligar Colaborador</title>
	</#if>
	
	<#assign formAction="desliga.action"/>
	
	<#if colaborador.dataDesligamento?exists>
		<#assign dataDesligamento = colaborador.dataDesligamento?date/>
	<#else>
		<#assign dataDesligamento = ""/>
	</#if>

	<#if colaborador.observacaoDemissao?exists>
		<#assign observacaoDemissao = colaborador.observacaoDemissao/>
	<#else>
		<#assign observacaoDemissao = ""/>
	</#if>

	<#include "../ftl/mascarasImports.ftl" />
	<script type="text/javascript">
		function enviarForm(acao)
		{ 
			if (!validaDatasPeriodo("${colaborador.dataAdmissao?string}", document.form.dataDesligamento.value)) 
			{
				jAlert("A data de desligamento não pode ser anterior à data de admissão\nData de admissão: ${colaborador.dataAdmissao?string}");
				return false;
			}
		
			if (acao.id == 'religa')
			{
				document.form.action = 'religa.action';
				newConfirm('Tem certeza que deseja cancelar o desligamento?', function(){validaFormulario('form', null, null);});
			}
			else
			{
				if(validaFormulario('form', new Array('data','motivoId'), new Array('data'), true))
					newConfirm('Confirma desligamento?', function(){document.form.submit();});
			}
		}
	</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" method="POST">
	
		<@ww.hidden label="Id" name="colaborador.id" />
	
		<@ww.label cssStyle="font-family: Arial, Helvetica, sans-serif;font-size:16px;font-weight:bold;" name="colaborador.nome"/>
		<br>
	
		<@ww.hidden label="Desligado" name="desligado" fieldValue="true" value="true" />
		<#if integraAc>
			Data de Desligamento: ${dataDesligamento}<br><br>
			<@ww.hidden name="dataDesligamento" value="${dataDesligamento}" id="data" />
		<#else>
			<@ww.datepicker label="Data de Desligamento" name="dataDesligamento" value="${dataDesligamento}" id="data" cssClass="mascaraData" required="true"/>
		</#if>	

		<@ww.select label="Motivo do Desligamento" name="motDemissao.id" id="motivoId" list="motivoDemissaos"  listKey="id" listValue="motivo" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;" required="true" />
		<@ww.textarea label="Observações" name="observacaoDemissao" value="${observacaoDemissao}" cssStyle="width:445px;"/>
	
		<@ww.hidden name="nomeBusca" />
		<@ww.hidden name="cpfBusca" />
		<@ww.hidden name="colaborador.dataAdmissao" />
	</@ww.form>
	
	
	<div class="buttonGroup">
		<#if colaborador.motivoDemissao.id?exists>
			<input type="button" value=" " onclick="enviarForm(this);" class="btnGravar" />
		<#else>
			<input type="button" value=" " onclick="enviarForm(this);" class="btnDesligarColaborador" />
		</#if>
		
		<input type="button" value=" " onclick="window.location='list.action'" class="btnVoltar" />
		
		<#if colaborador.desligado && !integraAc>
			<input type="button" value=" " onclick="enviarForm(this);" id="religa" class="btnCancelarDesligamento" />
		</#if>
		
	</div>
</body>
</html>