	<#include "questionarioAplicarInclude.ftl" />
	<#if preview == false>
		<button class="btnConcluir" onclick="javascript: executeLink('aplicarByOrdem.action?questionario.id=${questionario.id}');" accesskey="E">
		</button>
		<button class="btnVoltar" onclick="javascript: executeLink('../pergunta/list.action?questionario.id=${questionario.id}');" accesskey="V">
		</button>	
	<#else>
		<#-- Monta o botão de acordo com o destino pesquisa, avaliação, entrevista-->
		<#if urlVoltar?exists>
			<button class="btnVoltar" onclick="javascript: executeLink('${urlVoltar}');"></button>
		</#if>		
	</#if>
</body>
</html>