<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<#include "../ftl/mascarasImports.ftl" />
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/fortes.css?version=${versao}"/>');
	    @import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	    @import url('<@ww.url value="/css/font-awesome.min.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/font-awesome_buttons.css"/>');
	    
	    #wwgrp_descricaoCBO
	    {
			float: left;
	    	background-color: #E9E9E9;
			width: 420px;
			padding-left: 4px;
		}
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/autoCompleteFortes.js?version=${versao}"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/fortes.js?version=${versao}"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/functions.js?version=${versao}"/>'></script>

	<#if funcao.id?exists>
		<title>Históricos da Função ${funcao.nome}</title>
		<#assign accessKey="A"/>
		<#assign validarCampos=""/>
	<#else>
		<title>Inserir Função</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
		<#assign validarCampos="return validaFormulario('form', new Array('dataHist','funcaoNome','descricao'), new Array('dataHist'))"/>
	</#if>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@ww.form name="form" action="insert.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<#if funcao.id?exists>
			<@ww.hidden name="funcao.id" />
		<#else>
			<li>
				<fieldset>
					<legend>Dados do Primeiro Histórico da Função</legend>
					<ul>
						<#include "includeHistoricoFuncao.ftl" />
					</ul>
				</fieldset>
			</li>
		</#if>
	</@ww.form>
	
	<#if funcao.id?exists && historicoFuncaos?exists>
		<br>
		<@display.table name="historicoFuncaos" id="historicoFuncao" pagesize=10 class="dados">
			<@display.column title="Ações" class="acao">
				<@frt.link href="../historicoFuncao/prepareUpdate.action?historicoFuncao.id=${historicoFuncao.id}&funcao.id=${funcao.id}&funcao.nome=${funcao.nome}" imgTitle="Editar" iconeClass="fa-edit"/>
				<#if 1 < historicoFuncaos?size>
					<@frt.link href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='../historicoFuncao/delete.action?historicoFuncao.id=${historicoFuncao.id}&funcao.id=${funcao.id}'});" imgTitle="Excluir" iconeClass="fa-times"/>
				<#else>
					<@frt.link href="javascript:;" imgTitle="Não é possível remover o único histórico da função" iconeClass="fa-times" opacity=true/>
				</#if>
			</@display.column>
			<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="text-align: center;width:80px;"/>
			<@display.column property="funcaoNome" title="Nome da Funação" style="text-align: center;width:100px;"/>
			<@display.column property="codigoCbo" title="CBO" style="text-align: center;width:50px;"/>
			<@display.column property="descricao" title="Descrição das Atividades Executadas"/>
		</@display.table>
	
		<div class="buttonGroup">
			<button onclick="window.location='../historicoFuncao/prepareInsert.action?funcao.id=${funcao.id}&funcao.nome=${funcao.nome}'">Inserir</button>
			<button onclick="window.location='list.action'">Voltar</button>
		</div>
	<#else>
		<div class="buttonGroup">
			<button onclick="${validarCampos};">Gravar</button>
			<button onclick="window.location='list.action'">Voltar</button>
		</div>
	</#if>
</body>
</html>