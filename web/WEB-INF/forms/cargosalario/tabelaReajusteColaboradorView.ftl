<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
		.dados tfoot td { background-color: #f3f3f3; color: #333; }
	</style>
	
	<title>Planejamento de Realinhamento por ${tabelaReajusteColaborador.tipoReajusteDescricao}</title>
	
	<script language='javascript'>
		function prepareUpdate()
		{
			document.forms[0].action = "<@ww.url includeParams="none" value="/cargosalario/tabelaReajusteColaborador/visualizar.action"/>";
			document.forms[0].submit();
		}

		function filtrarOpt()
		{
			value = document.getElementById('optFiltro').value;
			if(value == "1")
			{
				document.getElementById('areaOrganizacional').style.display = "";
				document.getElementById('grupoOcupacional').style.display = "none";
			}
			else if(value == "2")
			{
				document.getElementById('areaOrganizacional').style.display = "none";
				document.getElementById('grupoOcupacional').style.display = "";

			}
		}
		
		function aplicar(existemDesligados)
		{
			if (existemDesligados)
			{
				jAlert('Colaboradores desligados não podem receber reajuste\nFavor ajustar antes de aplicar');
				return false;
			}
			
			newConfirm('Deseja realmente aplicar o reajuste?', function(){window.location='aplicar.action?tabelaReajusteColaborador.id=${tabelaReajusteColaborador.id}&tabelaReajusteColaborador.data=${tabelaReajusteColaborador.data}&tabelaReajusteColaborador.dissidio=${tabelaReajusteColaborador.dissidio?string}'});
		}
	</script>

	<#include "../ftl/showFilterImports.ftl" />


 	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<#if tabelaReajusteColaborador.id?exists>
		<p>Promoção/Reajuste<br> <b>${tabelaReajusteColaborador.nome}</b></p>
		<p>A partir de<br> <b>${tabelaReajusteColaborador.data}</b></p>
		<p>Tipo de Reajuste:<br> <b>${tabelaReajusteColaborador.tipoReajusteDescricao}</b></p>
		<br />

		<#if tabelaReajusteColaborador.tipoReajuste == 'C'>
			<#include "tabelaReajusteColaborador.ftl" />
		
		<#elseif tabelaReajusteColaborador.tipoReajuste == 'F'>
			<#include "tabelaReajusteFaixaSalarial.ftl" />
		
		<#elseif tabelaReajusteColaborador.tipoReajuste == 'I'>
			Tipo ÍNDICE
		
		<#else>
			Tipo de reajuste não identificado.
		</#if>
	
		<div class="buttonGroup">
			<button onclick="window.location='list.action'" class="btnVoltar" accesskey="V">
			</button>
			<#if tabelaReajusteColaborador.id?exists && reajustes?exists && 0 < reajustes?size>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button onclick="aplicar(${existemDesligados?string})" class="btnAplicar" accesskey="P">
				</button>
			</#if>
		</div>
	</#if>
</body>
</html>