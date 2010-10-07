<html>
<head>
	<title>Frequência</title>
	<@ww.head />
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/matriz.css"/>');
	</style>
	<script type="text/javascript">
	function vertical(valor)
	{
		for(var i = 0; i < valor.length; i++)
		{
			document.write(valor.charAt(i).toUpperCase() + "<br>");
		}
	}
	</script>
</head>
<body>
<div id="divPrint">

<table width="100%" cellspacing="0" cellpadding="0" class="matriz">

	<tr>
		<td class="matrizQualificacao">
			<b>Relatório de Frequência</b>
		</td>
		<#list comissaoReuniaos as reuniao>
			<td class="nomedoCurso">
				<script type="text/javascript">vertical("${reuniao.descricaoFmt}");</script>
			</td>
		</#list>
	</tr>

	<#list comissaoReuniaoPresencaMatrizes as comissaoReuniaoPresencaMatriz>
		<tr>
			<td class="colaborador">
				${comissaoReuniaoPresencaMatriz.colaborador.nome}
			</td>
			<#list comissaoReuniaos as comissaoReuniao>
				<td class="pontuacao">
					<#list comissaoReuniaoPresencaMatriz.comissaoReuniaoPresencas as presenca>
						<#if comissaoReuniao.id == presenca.comissaoReuniao.id>
							<#if presenca.presente>
								<img border="0" title="Presente" src="<@ww.url value="/imgs/check_ok.gif"/>">
							<#else>
								<#if presenca.justificativaFalta?exists && presenca.justificativaFalta?j_string != "">
									<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${presenca.justificativaFalta?j_string}');return false"><img border="0" src="<@ww.url value="/imgs/check_ok_Justificado.gif"/>"></span>
								<#else>
									<#if comissaoReuniao.frequeniciaReuniao>
										<span href=# style="cursor: default;"><img border="0" title="Presente" src="<@ww.url value="/imgs/check_falta.gif"/>"></span>
									<#else>
										<span href=# style="cursor: default;">&nbsp;</span>
									</#if>	
								</#if>
							</#if>
						</#if>
					</#list>
				</td>
			</#list>
		</tr>
	</#list>
</table>
</div>

<br>
<div class="buttonGroup">
	<button class="btnVoltar" onclick="window.location='list.action?comissao.id=${comissao.id}'" accesskey="V">
	</button>
</div>
</body>
</html>