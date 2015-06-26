<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<script>
	function CallPrint(strid)
	{
		var browserName=navigator.appName;

		var prtContent = document.getElementById(strid);
		var WinPrint = window.open('','','letf=20,top=20,width=680,height=368,toolbar=0,scrollbars=1,status=0');

		var cssLink = "";
		cssLink	+=	"<style type='text/css'>";
		cssLink	+=	"@import url('<@ww.url includeParams="none" value="/css/gastoEmpresa.css?version=${versao}"/>');";
		cssLink	+=	"</style>";

		WinPrint.document.write(cssLink + prtContent.innerHTML);

		WinPrint.document.close();
		WinPrint.focus();
		WinPrint.print();
		WinPrint.close();
	}
</script>
<title>Investimentos da Empresa</title>

<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/gastoEmpresa.css?version=${versao}"/>');
</style>
</head>
<body>
<div id="divPrint">
<h4>Período: ${dataIni} a ${dataFim}</h4>
<#if colab?exists>
	<h4>Colaborador: ${colab.nome}</h4>
</#if>
<table width="100%" cellspacing="0" cellpadding="0">
	<#assign colspan = 	meses?size + 1>
	<#list gastoRelatorioAreas as relatorioArea>
	<tr>
		<th class="area" colspan="${colspan}">
			${relatorioArea.area.nome}
		</th>
	</tr>
	<tr>
		<th>
			Itens
		</th>
		<#list meses as mes>
		<th class="direita">
			${mes}
		</th>
		</#list>
	</tr>
	<#list relatorioArea.grupoGastos as grupo>
	<tr>
		<th class="grupo" colspan="${colspan}">
			${grupo.nome}
		</th>
	</tr>
	<#list relatorioArea.gastoRelatorios as gastoRelatorio>
	<#if grupo.id == gastoRelatorio.gasto.grupoGasto.id>
	<tr>
		<td>
			&nbsp; ${gastoRelatorio.gasto.nome}
		</td>
	<#list gastoRelatorio.gastoRelatorioItems as item>
		<td class="direita">
			${item.valor?string(",##0.00")}
		</td>
	</#list>
	</tr>
	</#if>

	<#if (relatorioArea.beneficios?exists) && (relatorioArea.beneficios?size>0) >
		<tr>
			<th class="grupo" colspan="${colspan}">
				Benefícios
			</th>
		</tr>
	</#if>

	</#list>

	<#list relatorioArea.gastoRelatorioGrupoSubTotals as subTotal>
	<#if grupo.id == subTotal.grupoGasto.id>
	<tr class="subTotal">
		<td class="direita">
			Sub-Total
		</td>

		<#list subTotal.gastoRelatorioItems as itemTotal>
		<td class="direita">
			${itemTotal.valor?string(",##0.00")}
		</td>
		</#list>
	</tr>
	</#if>
	</#list>

	</#list>
	<tr class="total">
		<td class="direita">
			Total
		</td>
		<#list relatorioArea.gastoRelatorioGrupoTotals as total>
		<td class="direita">
			${total.valor?string(",##0.00")}
		</td>
		</#list>
	</tr>
	</#list>
	<tr class="totalGeral">
		<td class="direita">
			Total Geral
		</td>
		<#list gastoRelatorioItems as total>
		<td class="direita">
			${total.valor?string(",##0.00")}
		</td>
		</#list>
	</tr>
</table>
</div>

<br>
<div class="buttonGroup">
	<button class="btnImprimir" onclick="CallPrint('divPrint')" accesskey="I">
	</button>
	<button class="btnVoltar" onclick="window.location='prepareImprimir.action'" accesskey="V">
	</button>
</div>
</body>
</html>