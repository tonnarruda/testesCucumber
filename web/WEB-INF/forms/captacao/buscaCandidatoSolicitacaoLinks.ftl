<style type="text/css">
	#menuBusca
	{
		margin: -16px;
		color: #FFCB03;
		background: #E0DFDF;
		/* background-color: #6B2323;
		border-top: 1px solid #953232;  Sombra clara */
	}
	#menuBusca a 
	{
		float: left;
		display: block;
		padding: 7px 15px;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 12px;
		text-align: center;
		text-decoration: none;
		color: #5C5C5A;
		border-right: 1px solid #C6C6C6;
	}
	#menuBusca a:hover
	{
		  color: #5292C0;
	}
</style> 
	<#if solicitacao?exists && solicitacao.id?exists>
		<#assign solicitacaoId = "?solicitacao.id=${solicitacao.id}"/>
	<#else>
		<#assign solicitacaoId = ""/>
	</#if>
<div id="menuBusca">
	
	<a href="../candidato/prepareBuscaSimples.action${solicitacaoId}" class="ativaSimples">Triagem Simples</a>
	<a href="../candidato/prepareBusca.action${solicitacaoId}" class="ativaAvancada">Triagem Avançada</a>
	<a href="../candidato/prepareBuscaF2rh.action${solicitacaoId}" class="ativaF2rh">Triagem no F2rh</a>
	<#if solicitacao?exists && solicitacao.id?exists>
		<a href="../candidato/prepareTriagemAutomatica.action${solicitacaoId}" class="ativaTriagemAutomatica">Triagem Automática</a>
		<a href="../candidato/prepareTriagemColaboradores.action${solicitacaoId}" class="ativaTriagemColaboradores">Triagem de Colaboradores</a>
	</#if>
	<a style="border-right: none;">&nbsp;</a> <!-- Essa ultima serve só para deixar uma bordinha clara -->
	<div style="clear: both"></div>
</div>
	
	<br>
	<br>
	<@ww.actionmessage />
	<@ww.actionerror />