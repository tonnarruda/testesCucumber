<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>

	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
	<#include "../ftl/mascarasImports.ftl" />
	<#if ambiente.id?exists>
		<title>Editar Ambiente</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
		<#assign validarCampos="return validaFormulario('form', new Array('estabelecimento','nome'), null)"/>
	<#else>
		<title>Inserir Ambiente</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
		<#assign validarCampos="return validaFormulario('form', new Array('estabelecimento','dataHist','nome','descricao'), new Array('dataHist'))"/>
	</#if>
	
	<script type="text/javascript">
	
		function desabilitaEpcEficaz(elementoCheck)
		{
			elemEpcEficaz = document.getElementById("epcEficazId" + elementoCheck.value);
			
			
			elemEpcEficaz.disabled = !elementoCheck.checked;
			if (!elementoCheck.checked)
			{
				elemEpcEficaz.checked = "";
			}
		}
		
		function marcarDesmarcar(mdCheck)
		{
			var vMarcar;
			
			if (mdCheck.checked)
			{
				vMarcar = true;
			}
			else
			{
				vMarcar = false;
			}

			for (var i = 0; i < document.form.elements.length; i++)
			{
				var elementForm = document.form.elements[i];
				if ((elementForm != null) && (elementForm.type == 'checkbox') && (elementForm.id != 'md') && (elementForm.name != 'epcCheck') && (elementForm.name != 'epcEficazChecks') )
				{
					elementForm.checked = vMarcar;
					desabilitaEpcEficaz(elementForm);
				}
			}
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
	
		<@ww.select label="Estabelecimento" name="ambiente.estabelecimento.id" id="estabelecimento" required="true" list="estabelecimentos" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..."/>
		<@ww.textfield label="Nome" name="ambiente.nome" id="nome" cssClass="inputNome" maxLength="100" required="true" />
		
		<#if !ambiente.id?exists>
			<li>
			<fieldset>
				<ul>
					<legend>Dados do Primeiro Histórico do Ambiente</legend>
					<@ww.datepicker label="A partir de" name="historicoAmbiente.data" id="dataHist" required="true" cssClass="mascaraData"/>
					<@ww.textfield label="Tempo de Exposição" name="historicoAmbiente.tempoExposicao" id="tempoExposicao" maxLength="40" style="width:100px;"/>
					<@ww.textarea label="Descrição do Ambiente" name="historicoAmbiente.descricao" id="descricao" required="true"/>
					<@frt.checkListBox label="EPCs existentes no Ambiente" name="epcCheck" list="epcCheckList" />
					
					<@display.table name="riscos" id="risco" class="dados" style="width:500px;border:none;">
						<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(this);' />" style="width: 30px; text-align: center;">
							<input type="checkbox" onclick="desabilitaEpcEficaz(this);" id="check${risco.id}" value="${risco.id}" name="riscoChecks" />
						</@display.column>
						<@display.column property="descricao" title="Risco" style="width: 240px;"/>
						<@display.column property="descricaoGrupoRisco" title="Tipo" style="width: 240px;"/>
						<@display.column title="EPI Eficaz" style="width: 140px;text-align:center;">
							<#if risco.epiEficaz == true> 
							Sim
							<#else>
							NA
							</#if>
						</@display.column>
						<@display.column title="EPC Eficaz" style="width: 140px;text-align:center;">
							<input type="checkbox" onclick="" id="epcEficazId${risco.id}" value="${risco.id}" name="epcEficazChecks" />
					        <@ww.hidden name="riscoIds" value="${risco.id}"/>
						</@display.column>
					</@display.table>
				</ul>
			</fieldset>
		</li>
			
			<script type="text/javascript">
				marcarDesmarcar(document.form.md);
			</script>
		</#if>

		<@ww.hidden name="ambiente.id" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};"  class="btnGravar" accesskey="${accessKey}"></button>

	<#if ambiente.id?exists && historicoAmbientes?exists>
		</div>
		<br>
		<@display.table name="historicoAmbientes" id="historicoAmbiente" pagesize=10 class="dados">
			<@display.column title="Ações" class="acao">
				<a href="../historicoAmbiente/prepareUpdate.action?historicoAmbiente.id=${historicoAmbiente.id}&ambiente.id=${ambiente.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='../historicoAmbiente/delete.action?historicoAmbiente.id=${historicoAmbiente.id}&ambiente.id=${ambiente.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			</@display.column>
			<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="text-align: center;width:80px;"/>
			<@display.column property="descricao" title="Histórico - Descrição"/>
		</@display.table>

	<div class="buttonGroup">
		<button onclick="window.location='../historicoAmbiente/prepareInsert.action?ambiente.id=${ambiente.id}'" class="btnInserir" accesskey="I"></button>
	</#if>
		<button onclick="window.location='list.action'" class="btnVoltar" accesskey="V"></button>
	</div>
</body>
</html>