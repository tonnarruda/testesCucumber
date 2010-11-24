<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Inserir Candidatos na Solicitação</title>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>
	<style type="text/css">#menuBusca a.ativaF2rh{color: #FFCB03;}</style>
	
	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/buscaCandidatoSolicitacao.js"/>'></script>

	<script type='text/javascript'>
	</script>

	<#include "../ftl/showFilterImports.ftl" />

	<#if !palavrasChave?exists>
		<#assign palavrasChave=""/>
	</#if>
	<#if !formas?exists>
		<#assign formas=""/>
	</#if>
	

	<#assign validarCampos="return validaFormulario('formBusca', null,  null)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
</head>

<body>
	<#include "buscaCandidatoSolicitacaoLinks.ftl" />

	<#include "../util/topFiltro.ftl" />	
		<@ww.form name="formBuscaF2rh" id="formBuscaF2rh" action="buscaF2rh.action" onsubmit="${validarCampos}" method="POST">
			<@ww.textfield label="Nome" id="cargo" name="curriculo.nome" cssStyle="width: 350px;"/>
			<@ww.textfield label="CPF" id="cpf" name="curriculo.user.cpf" cssStyle="width: 350px;"/>
			<@ww.textfield label="Atualizado em" id="updated_at" name="curriculo.updated_at" cssStyle="width: 350px;"/>
			<@ww.textfield label="Escolaridade" id="escolaridade" name="curriculo.escolaridade_rh" cssStyle="width: 350px;"/>
			<@ww.textfield label="Idioma" id="idioma" name="curriculo.idioma" cssStyle="width: 350px;"/>
			<@ww.textfield label="Estado" id="estado" name="curriculo.estado" cssStyle="width: 350px;"/>
			<@ww.textfield label="Cidade" id="cidade" name="curriculo.cidade_rh" cssStyle="width: 350px;"/>
			<@ww.textfield label="Bairro" id="bairro" name="curriculo.bairro" cssStyle="width: 350px;"/>
			<@ww.textfield label="Sexo" id="sexo" name="curriculo.sexo" cssStyle="width: 350px;"/>
			<@ww.textfield label="Idade" id="idade" name="curriculo.data_nascimento" cssStyle="width: 350px;"/>
			<@ww.textfield label="Cargo" id="cargo" name="curriculo.cargo" cssStyle="width: 350px;"/>
			
			<@ww.textfield label="Palavra chave" id="observacoes_complementares" name="curriculo.observacoes_complementares" cssStyle="width: 350px;"/>
			
			<@ww.hidden name="filtro" value="true"/>
			<@ww.hidden name="solicitacao.id"/>
			
			<div class="buttonGroup">
				<input type="submit" value="" class="btnPesquisar grayBGE" onclick="${validarCampos};">
			</div>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />

	<#if curriculos?exists && 0 < curriculos?size>
		<br>
		<@ww.form name="formCand" action="insertCandidatosByF2rh.action" validate="true" method="POST">

			<@ww.hidden name="solicitacao.id"/>

			<@display.table name="curriculos" id="curriculo" class="dados" >
			
				<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.formCand);' />" style="width: 30px; text-align: center;">
					<input type="checkbox" value="${curriculo.id?string?replace(".", "")?replace(",","")}" name="candidatosId" />
				</@display.column>
			
				<@display.column property="nome" title="Nome" style="width: 200px;"/>
				<@display.column property="escolaridade_rh" title="Escolaridade" style="width: 100px;"/>
				<@display.column title="Cidade/UF" style="width: 200px;">
					<#if curriculo.cidade_rh?exists && curriculo.estado?exists>
						${curriculo.cidade_rh}/${curriculo.estado}
					</#if>
				</@display.column>
				<@display.column property="updated_at" title="Atualizado em" style="width: 100px;"/>
			</@display.table>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="prepareEnviarForm();" class="btnInserirSelecionados"></button>
			<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar"></button>
		</div>
	</#if>
</body>
</html>