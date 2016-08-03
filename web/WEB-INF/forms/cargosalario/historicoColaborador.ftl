<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
	<script type="text/javascript">
	$(function() {
		$('.tooltipHelp').qtip({
			content: 'Não foi possível obter o salário. Verifique se o índice/faixa possui histórico nesta data.'
		});
	});
	</script>
	
	<title>Progressão do Colaborador</title>
</head>

<body>
<table>
<tr>
	<td>
		<strong>Colaborador:</strong> ${colaborador.nomeComercial}
	</td>
</tr>
<tr>
	<td>
		<#if colaborador.desligado>
			<strong>Colaborador Desligado</strong><br>
			<strong>Data Desligamento:</strong> <#if colaborador?exists && colaborador.dataDesligamento?exists>${colaborador.dataDesligamento}</#if><br>
			<strong>Motivo:</strong> <#if colaborador?exists && colaborador.motivoDemissao?exists && colaborador.motivoDemissao.motivo?exists>${colaborador.motivoDemissao.motivo}</#if><br>
			<strong>Observação:</strong> <#if colaborador?exists && colaborador.observacaoDemissao?exists>${colaborador.observacaoDemissao}</#if><br>
		<#else>
			<strong>Cargo Atual:</strong> <#if historicoColaborador?exists && historicoColaborador.faixaSalarial?exists>${historicoColaborador.faixaSalarial.descricao}</#if><br>
			<strong>Área Organizacional Atual:</strong> <#if historicoColaborador?exists && historicoColaborador.areaOrganizacional?exists>${historicoColaborador.areaOrganizacional.descricao}</#if><br>
			<strong>Sálario Atual:</strong> <#if historicoColaborador?exists && historicoColaborador.salarioCalculado?exists>${historicoColaborador.salarioCalculado?string.currency}</#if>
		</#if>
	</td>
</tr>
<tr>
	<td>
		<img src='<@ww.url includeParams="none" value="/grafico.action?id=${colaborador.id}"/>'/>
	</td>
</tr>
</table>



	<@display.table name="historicoColaboradors" id="historicoColaboradors" class="dados" defaultsort=1 >

		<#assign style="">

		<#if integradoAC?exists && integradoAC>

			<#if historicoColaboradors.status?exists && historicoColaboradors.status == statusRetornoAC.getConfirmado()>
				<#assign style="color: #555;">
			</#if>

			<#if historicoColaboradors.status?exists && historicoColaboradors.status == statusRetornoAC.getAguardando()>
				<#assign style="color: #002EB8;">
			</#if>

			<#if historicoColaboradors.status?exists && historicoColaboradors.status == statusRetornoAC.getCancelado()>
				<#assign style="color: #FF0000;">
			</#if>

		</#if>

		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:60px; ${style}"/>
		<@display.column property="motivoDescricao" title="Situação" style="${style}" />
		<@display.column title="Estabelecimento" style="${style}">
			${historicoColaboradors.estabelecimento.nome}
		</@display.column>
		<@display.column title="Cargo" style="${style}">
			${historicoColaboradors.faixaSalarial.descricao}
		</@display.column>
		<@display.column property="areaOrganizacional.descricao" title="Área Organizacional" style="width:250px; ${style}"/>

		<@display.column property="descricaoTipoSalario" title="Tipo" style="width:100px; ${style}"/>
		<@display.column title="Salário" style="text-align:right; width:80px; ${style}">
			<#if historicoColaboradors.salarioCalculado?exists>
				${historicoColaboradors.salarioCalculado?string.currency}
			<#else>
				<div style="width: 100%; text-align: center;">
					<img class="tooltipHelp" src="<@ww.url value="/imgs/iconWarning.gif"/>" />
				</div>
			</#if>
		</@display.column>

	</@display.table>
	<#if integradoAC?exists && integradoAC>
		<div id="legendas" align="right"></div>
		<br />

		<script type="text/javascript">
			var obj = document.getElementById("legendas");
			obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #002EB8;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Aguardando Confirmação";
			obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #FF0000;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Canceladas";
			obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #555;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Confirmadas";
		</script>
	</#if>
		<div class="buttonGroup">
			<button onclick="javascript:history.back();" class="btnVoltar"></button>
			<#if !colaborador.desligado>
				<@authz.authorize ifAllGranted="ROLE_CAD_HISTORICOCOLABORADOR">
					<button onclick="window.location='historicoColaboradorList.action?colaborador.id=${colaborador.id}'" class="btnEditarHistoricos"></button>
				</@authz.authorize>
			</#if>
		</div>
</body>
</html>