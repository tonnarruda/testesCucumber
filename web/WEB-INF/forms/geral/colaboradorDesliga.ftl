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

	<#if colaborador.observacao?exists>
		<#assign observacao = colaborador.observacao/>
	<#else>
		<#assign observacao = ""/>
	</#if>

	<#include "../ftl/mascarasImports.ftl" />
	<script type="text/javascript">
		function enviarForm(acao)
		{
			if (acao.id == 'religa'){
				document.form.action = 'religa.action';
				validaFormulario('form', null, null);
			}else{
				validaFormulario('form', new Array('data','motivoId'), new Array('data'));
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
		<@ww.datepicker label="Data de Desligamento" name="dataDesligamento" value="${dataDesligamento}" id="data" cssClass="mascaraData" required="true"/>
		<@ww.select label="Motivo do Desligamento" name="motDemissao.id" id="motivoId" list="motivoDemissaos"  listKey="id" listValue="motivo" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;" required="true" />
		<@ww.textarea label="Observações" name="observacao" value="${observacao}" cssStyle="width:445px;"/>
	
		<@ww.hidden name="nomeBusca" />
		<@ww.hidden name="cpfBusca" />
	</@ww.form>
	
	
	<div class="buttonGroup">
		<#if colaborador.motivoDemissao.id?exists>
			<input type="button" value=" " onclick="enviarForm(this);" class="btnGravar" />
		<#else>
			<input type="button" value=" " onclick="if (confirm('Confirma desligamento?')) enviarForm(this);" class="btnDesligarColaborador" />
		</#if>
		<input type="button" value=" " onclick="window.location='list.action?nomeBusca=${nomeBusca}&cpfBusca=${cpfBusca}'" class="btnVoltar" />
		<#if colaborador.desligado>
			<input type="button" value=" " onclick=" if (confirm('Tem certeza que deseja cancelar o desligamento?')) enviarForm(this);" id="religa" class="btnCancelarDesligamento" />
		</#if>
		
	</div>
</body>
</html>