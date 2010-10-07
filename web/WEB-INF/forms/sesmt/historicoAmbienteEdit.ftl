<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
	<#include "../ftl/mascarasImports.ftl" />
	<@ww.head/>
	<#if historicoAmbiente.id?exists>
		<title>Editar de Histórico Ambiente - ${ambiente.nome}</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Inserir Histórico de Ambiente - ${ambiente.nome}</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>
	<#assign validarCampos="return validaFormulario('form', new Array('dataHist','descricao'), new Array('dataHist'))"/>

	<#if historicoAmbiente.data?exists>
			<#assign data = historicoAmbiente.data>
		<#else>
			<#assign data = "">
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
		
		function popularRiscos()
		{
		<#if historicoAmbiente?exists && historicoAmbiente.riscoAmbientes?exists>
			<#list historicoAmbiente.riscoAmbientes as riscoAmbiente>
				riscoId = ${riscoAmbiente.risco.id};
				elementoCheck = document.getElementById("check"+riscoId);
				elementoCheck.checked = true;
				desabilitaEpcEficaz(elementoCheck);
				<#if riscoAmbiente.epcEficaz>
				elementoCheck = document.getElementById("epcEficazId"+riscoId);
				elementoCheck.checked = true;
				</#if>
			</#list>
		</#if>
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.datepicker label="A partir de" name="historicoAmbiente.data" id="dataHist" required="true" value="${data}" cssClass="mascaraData"/>
		<@ww.textarea label="Descrição do Ambiente" name="historicoAmbiente.descricao" id="descricao" required="true"/>
		
		<@ww.textfield label="Tempo de Exposição" name="historicoAmbiente.tempoExposicao" id="tempoExposicao" maxLength="40" style="width:100px;"/>

		<@frt.checkListBox label="EPCs existentes no Ambiente" name="epcCheck" list="epcCheckList" />
		
		Riscos existentes:<br>
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
			</@display.column>
		</@display.table>

		<@ww.hidden name="historicoAmbiente.id" />
		<@ww.hidden name="historicoAmbiente.ambiente.id" />
		<@ww.hidden name="ambiente.id" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='../ambiente/prepareUpdate.action?ambiente.id=${ambiente.id}'" class="btnCancelar" accesskey="V"></button>
	</div>
	<script>
		marcarDesmarcar(document.form.md);
		popularRiscos();
	</script>
</body>
</html>