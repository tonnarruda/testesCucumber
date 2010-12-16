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
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/buscaCandidatoSolicitacao.js"/>'></script>

	<script type="text/javascript">


	</script>
	<#include "../ftl/showFilterImports.ftl" />

	<#if !palavrasChave?exists>
		<#assign palavrasChave=""/>
	</#if>
	<#if !formas?exists>
		<#assign formas=""/>
	</#if>
	

	<#if dataCadIni?exists>
		<#assign dataIni = dataCadIni?date/>
	<#else>
		<#assign dataIni = ""/>
	</#if>
	<#if dataCadFim?exists>
		<#assign dataFim = dataCadFim?date/>
	<#else>
		<#assign dataFim = ""/>
	</#if>

	<#assign validarCampos="return validaFormulario('formBuscaF2rh', null, new Array('dataCadIni','dataCadFim'))"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
</head>

<body>
	<#include "buscaCandidatoSolicitacaoLinks.ftl" />

	<#include "../util/topFiltro.ftl" />	
		<@ww.form name="formBuscaF2rh" id="formBuscaF2rh" action="buscaF2rh.action" onsubmit="${validarCampos}" method="POST">
			<@ww.select label="Escolaridade" name="escolaridade" id="escolaridade" list="escolaridades" cssStyle="width: 170px;" liClass="liLeft" headerKey="" headerValue="" />
			<@ww.select label="Idioma" name="idioma" id="idioma" list="idiomas" listKey="id" listValue="nome" liClass="liLeft" cssStyle="width: 150px;" headerKey="" headerValue=""/>
			
			<li>
				<span>
				Cadastrado/Atualizado entre:
				</span>
			</li>
			<@ww.datepicker name="dataCadIni" value="${dataIni}" id="dataCadIni" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
			<@ww.datepicker name="dataCadFim" value="${dataFim}" id="dataCadFim" cssClass="mascaraData validaDataFim" />

			<@ww.textfield label="Cargo" id="cargo" name="curriculo.cargo" cssStyle="width: 290px;" liClass="liLeft"/>			
			<@ww.select label="Sexo" name="curriculo.sexo" id="sexo" list="sexos" cssStyle="width: 100px;"  liClass="liLeft"/>
			<li>
				<span>
				Idade Preferencial:
				</span>
			</li>

			<@ww.textfield name="idadeMin" id="dataPrevIni" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
			<@ww.label value="a" liClass="liLeft" />
			<@ww.textfield name="idadeMax" id="dataPrevFim" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
			<@ww.label value="anos"/>
			
			<@ww.select label="Estado" name="uf" id="uf" list="ufs" liClass="liLeft" cssStyle="width: 45px;" headerKey="" headerValue="" onchange="javascript:populaCidades()"/>
			<@ww.select label="Cidade" name="cidade" id="cidade" list="cidades" cssStyle="width: 200px;" headerKey="" headerValue="Selecione um Estado..." liClass="liLeft" />
			<@ww.textfield label="Bairro" id="bairro" name="curriculo.bairro" cssStyle="width: 264px;"/>
			
			<@ww.textfield label="Palavra chave(Ex.: programador superior completo)" id="observacoes_complementares" name="curriculo.observacoes_complementares" cssStyle="width: 525px;"/>
			
			<@ww.hidden name="filtro" value="true"/>
			<@ww.hidden name="solicitacao.id"/>
			
			<div class="buttonGroup">
				<input type="submit" value="" class="btnPesquisar grayBGE" onclick="${validarCampos};">
			</div>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
		<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar" ></button>
		<br>

	<#if curriculos?exists && 0 < curriculos?size>
		<br>
		<@ww.form name="formCand" action="insertCandidatosByF2rh.action" validate="true" method="POST">

			<@ww.hidden name="solicitacao.id"/>

			<@display.table name="curriculos" id="curriculo" class="dados" >
			
				<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.formCand);' />" style="width: 30px; text-align: center;">
					<input type="checkbox" value="${curriculo.id?string?replace(".", "")?replace(",","")}" name="candidatosId" />
				</@display.column>
			
				<@display.column property="nome" title="Nome" style="width: 250px;"/>
				<@display.column property="escolaridade_rh" title="Escolaridade" style="width: 150px;"/>
				<@display.column title="Cidade/UF" style="width: 150px;">
					<#if curriculo.cidade_rh?exists && curriculo.estado?exists>
						${curriculo.cidade_rh}/${curriculo.estado}
					</#if>
				</@display.column>
				<@display.column property="updated_rh" title="Atualizado em" style="width: 60px;text-align:center;"/>
			</@display.table>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="prepareEnviarForm();" class="btnInserirSelecionados"></button>
			<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar"></button>
		</div>
	</#if>
</body>
</html>