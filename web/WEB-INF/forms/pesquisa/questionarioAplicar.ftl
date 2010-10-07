	<#include "questionarioAplicarInclude.ftl" />
	<#if preview == false>
		<button class="btnConcluir" onclick="window.location='aplicarByOrdem.action?questionario.id=${questionario.id}'" accesskey="E">
		</button>
		<button class="btnVoltar" onclick="window.location='../pergunta/list.action?questionario.id=${questionario.id}'" accesskey="V">
		</button>	
	<#else>
		<#-- Monta o botão de acordo com o destino pesquisa, avaliação, entrevista-->
		<#if urlVoltar?exists>
			<button class="btnVoltar" onclick="window.location='${urlVoltar}'"></button>
		</#if>		
	</#if>
</body>
</html>