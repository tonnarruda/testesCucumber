<style type="text/css">
	#menuBusca
	{
		margin: -16px;
		color: #FFCB03;
		background-color: #23516B;
		border-top: 1px solid #327195; /* Sombra clara */
	}
	#menuBusca a 
	{
		float: left;
		display: block;
		padding: 5px 15px;
		font-weight: bold!important;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 12px;
		text-align: center;
		text-decoration: none;
		color: #FFF;
		border-left: 1px solid #327195; /* Sombra clara */
		border-right: 1px solid #1C4055; /* Sombra escura */
	}
	#menuBusca a:hover
	{
		background-color: #6391AB;
	}
</style> 
	<#if solicitacao?exists && solicitacao.id?exists>
		<#assign solicitacaoId = "?solicitacao.id=${solicitacao.id}"/>
	<#else>
		<#assign solicitacaoId = ""/>
	</#if>
<div id="menuBusca">
	
	<a href="../candidato/prepareBuscaSimples.action${solicitacaoId}" class="ativaSimples">Busca Simples</a>
	<a href="../candidato/prepareBusca.action${solicitacaoId}" class="ativaAvancada">Busca Avançada</a>
	<a href="../candidato/prepareBuscaF2rh.action${solicitacaoId}" class="ativaF2rh">Busca no F2rh</a>
	<a href="../candidato/prepareTriagemAutomatica.action${solicitacaoId}" class="ativaTriagemAutomatica">Busca Automática</a>
	
	<a style="border-right: none;">&nbsp;</a> <!-- Essa ultima serve só para deixar uma bordinha clara -->
	<div style="clear: both"></div>
</div>
	
	<br>
	<br>
	<@ww.actionmessage />
	<@ww.actionerror />