<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<title>Relacionador da função entre os sistemas RH e Fortes Pessoal</title>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/formModal.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/font-awesome.min.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/relacionarFuncaoFP.css?version=${versao}"/>');
	</style>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/relacionarFuncaoFP.js?version=${versao}"/>'></script>

	<@ww.head/>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
			
	<div id="funcoesRH" class="box">
		<h1 class="ui-widget-header title">Fuções no RH</h1>
		<div class="box-search" style="display: block;">
			<input type="text" class="searchRH" placeholder="Pesquisar...">
		</div>
		<div class="ui-widget-content column">
		    <ol id="funcoesRH-list">
			   	<#list funcaos as funcao>
			      	<li class="ui-widget-content funcoesRHLi">
				      	<input type="hidden" class="funcaoRH" value="${funcao.id}"/>
			      		<div class="nome">${funcao.nome}</div>
			      		<div style="clear:both;float: none;"></div>
			      	</li>
			    </#list>
		    </ol>
		</div>
	</div>
	
	<div id="acoes" class="box">
		<h1 class="ui-widget-header title">Ações</h1>
		<button onclick="addAcaoRelacionarAutomaticamente();" id="relacionarAutomaticamente" type="button" class="buttonDuplo">Relacionar</br>Automaticamente</br>Por Decrição</button>
		<button onclick="addAcaoRelacionar();" id="relacionar" type="button" class="buttonSimples" disabled="disabled" style="opacity: 0.4">Relacionar</button>
		<button onclick="addAcaoCriarPessoal()" id="criarPessoal" type="button" class="buttonSimples" disabled="disabled" style="opacity: 0.4">Criar no Pessoal</button>
		<button onclick="addAcaoExcluirRH();" id="excluirRH" type="button" class="buttonSimples" disabled="disabled" style="opacity: 0.4">Excluir no RH</button>
		<button onclick="addAcaoCriarRH()" id="criarRH" type="button" class="buttonSimples" disabled="disabled" style="opacity: 0.4">Criar no RH</button>
	</div>
	
	<div id="funcoesFPessoal" class="box">	
		<h1 class="ui-widget-header title">Fuções no Fortes Pessoal</h1>
		<div class="box-search" style="display: block;">
			<input type="text" class="searchFP" placeholder="Pesquisar...">
		</div>
		<div class="ui-widget-content column">
		    <ol id="funcoesFPessoal-list">
			   	<#list funcaosFPessoal as funcao>
			      	<li class="ui-widget-content funcoesFPessoalLi">
				    	<input type="hidden" class="funcaoFP" value="${funcao.id}"/>
			      		<div class="nome">${funcao.nome}</div>
			      		<div style="clear:both;float: none;"></div>
			      	</li>
			    </#list>
		    </ol>
		</div>
	</div>
		
	<@ww.form name="form" id="form" action="relacionarFuncaoFP.action" method="POST">
		<div id="acoesFuncoes" class="box">	
			<@frt.link  href="" imgTitle="Remover todas as ações" iconeClass="fa-trash" onclick="removerTodasAcoes();"/>
			<h1 class="ui-widget-header title">Ações a serem realizadas</h1>
			<input type="text" class="searchAcao" placeholder="Pesquisar...">
			
			<div class="ui-widget-content columnAcoes">
			    <ol id="acoesFuncoes-list">
			    	<table style="width:100%">
						<tr>
							<th width="4%">Remover</th>
							<th width="48%">Funções no RH</th>
							<th width="48%">Funções no Fortes Pessoal</th>
						</tr>
			    	</table>
			    </ol>
			</div>
		</div>
	</@ww.form>
		
	<div class="buttonGroup">
		<button onclick="document.form.submit();" type="button" class="buttonFinalizar">Finalizar</button>
	</div>
</body>
</html>