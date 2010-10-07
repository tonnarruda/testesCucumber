<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>
	<#include "../ftl/mascarasImports.ftl" />
	<title>Entrega de EPIs</title>

	<script>
	function mudarQtd(elementCheck)
	{
		var id = "selectQtdSolicitado_" + elementCheck.value;
		var elementSelect = document.getElementById(id);

		if(elementSelect.disabled)
			elementSelect.disabled = false;
		else
			elementSelect.disabled = true;
	}

	function marcarDesmarcar(frm)
	{
		var vMarcar;

		if (document.getElementById('md').checked)
			vMarcar = true;
		else
			vMarcar = false;

		with(frm)
		{
			for(i = 0; i < elements.length; i++)
			{
				if(elements[i].name == 'epiIds' && elements[i].type == 'checkbox')
				{
					elements[i].checked = vMarcar;
					document.getElementById("selectQtdSolicitado_" + elements[i].value).disabled = !vMarcar;
				}
			}
		}
	}
	</script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="entrega.action" method="POST">
		<h4> Colaborador: ${solicitacaoEpi.colaborador.nome}<br/>Data Solicitação: ${solicitacaoEpi.data?string('dd/MM/yyyy')}</h4>
		<@ww.hidden name="solicitacaoEpi.colaborador.id" />
		<div>EPIs:</div>

		<#assign i = 0/>
		<@display.table name="listaEpis" id="lista" class="dados" defaultsort=2 sort="list">

			<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(form);' />" style="width: 30px; text-align: center;">
				<input type="checkbox" value="${lista[0].id}" name="epiIds" onclick="mudarQtd(this);"/>
			</@display.column>

			<@display.column title="EPI" style="width:500px;">
				${lista[0].nome}
			</@display.column>

			<@display.column title="Quantidade">
				${lista[1].qtdSolicitado?string}
			</@display.column>

			<@display.column title="Entregue">
				<select name="selectQtdSolicitado" id="selectQtdSolicitado_${lista[0].id}" disabled/>
					<#list 0..lista[1].qtdSolicitado as i>
					  <#if i == lista[1].qtdEntregue>
					  <option value="${i}" selected>${i}</option>
					  <#else>
					  <option value="${i}">${i}</option>
					  </#if>
					</#list>
				</select>
			</@display.column>
		</@display.table>

		<@ww.hidden name="solicitacaoEpi.id" />
		<@ww.hidden name="solicitacaoEpi.empresa.id" />
		<@ww.hidden name="solicitacaoEpi.data" />
		<@ww.hidden name="solicitacaoEpi.cargo.id" />
		<@ww.hidden name="solicitacaoEpi.entregue" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="document.forms[0].submit();" class="btnGravar">
		</button>
		<button onclick="window.location='list.action'" class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>