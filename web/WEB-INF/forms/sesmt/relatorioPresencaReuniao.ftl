<html>
<head>
	<title>Frequência</title>
	<@ww.head />
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/matriz.css?version=${versao}"/>');
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
<@ww.actionerror />
<@ww.actionmessage />

<div id="divPrint">

	<table width="100%" cellspacing="0" cellpadding="0" class="matriz">
		<tr>
			<td class="matrizQualificacao">
				<b>Relatório de Frequência</b>
			</td>
			<#if comissaoReuniaos?exists>
				<#list comissaoReuniaos as reuniao>
					<td class="nomedoCurso">
						<script type="text/javascript">vertical("${reuniao.descricaoFmt}");</script>
					</td>
				</#list>
			</#if>
		</tr>
		
		<#assign colaboradorAtualId = -1/>
	
		<#if presencas?exists>
			<tr>
				<#list presencas as presenca>
					<#if colaboradorAtualId != presenca.colaborador.id>
						<#if colaboradorAtualId != -1>
							</tr><tr><#-- Cria uma linha a cada novo colaborador -->
						</#if>
						<td class="colaborador">
							<#if presenca.colaborador.membroComissaoCipa>
								${presenca.colaborador.nome}
							<#else>
								<span style='color: red;'>${presenca.colaborador.nome} </span>
							</#if>
						</td>
						<#assign colaboradorAtualId = presenca.colaborador.id/>
					</#if>
					<td class="pontuacao">
						<#if !(presenca.presente) && presenca.desligado>
							<img border="0" title="Não é membro da comissão desta reunião" src="<@ww.url value="/imgs/user_out.png"/>"/>
						<#elseif presenca.presente>
							<img border="0" title="Presente" src="<@ww.url value="/imgs/check_ok.gif"/>"/>
						<#elseif presenca.justificativaFalta?exists && presenca.justificativaFalta?j_string != "">
							<span href="#" style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${presenca.justificativaFalta?j_string}');return false"><img border="0" src="<@ww.url value="/imgs/check_ok_Justificado.gif"/>"></span>
						<#else>
							<img border="0" title="Faltou" src="<@ww.url value="/imgs/check_falta.gif"/>"/>
						</#if>				
					</td>
				</#list>
			</tr>
		</#if>
	</table>

	<span style='color: red;'>Colaborador(es) que não faz(em) mais parte da comissao.</span>
</div>

<br>
<div class="buttonGroup">
	<button class="btnVoltar" onclick="window.location='list.action?comissao.id=${comissao.id}'" ></button>
</div>
</body>
</html>