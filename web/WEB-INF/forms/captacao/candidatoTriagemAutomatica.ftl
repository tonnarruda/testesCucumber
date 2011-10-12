<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>

	<#if solicitacao?exists && solicitacao.id?exists>
		<title>Inserir Candidatos na Solicitação</title>
	<#else>
		<title>Triagem de currículos</title>
	</#if>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CandidatoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/buscaCandidatoSolicitacao.js"/>'></script>

	<#include "../ftl/mascarasImports.ftl" />
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	</style>
	<style type="text/css">#menuBusca a.ativaTriagemAutomatica{color: #FFCB03;}</style>

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

<#include "../ftl/showFilterImports.ftl" />
<#assign validarCampos="return validaFormularioEPeriodo('formBusca', new Array('qtdRegistros', 'cargoId'), false)"/>
<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

</head>

<body>
	<#if !BDS>
		<#include "buscaCandidatoSolicitacaoLinks.ftl" />
	</#if>

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" id="formBusca" action="triagemAutomatica.action" onsubmit="${validarCampos}" method="POST">

			<#if BDS?exists && !BDS>
				<@ww.select label="Empresa" name="empresaId" list="empresas" id="empresaSelect" listKey="id" listValue="nome" required="true" headerKey="-1" headerValue="Todas" disabled="!compartilharCandidatos"/>
			</#if>

			<@ww.hidden name="BDS"/>
			<#if BDS?exists && !BDS && solicitacao?exists && solicitacao.id?exists>
				<@ww.hidden name="solicitacao.id"/>
			</#if>

			<@ww.textfield label="Peso" name="pesos['escolaridade']" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
			<@ww.select label="Escolaridade mínima" name="candidato.pessoal.escolaridade" id="escolaridade" list="escolaridades" cssStyle="width: 220px;" headerKey="" headerValue=""/>
			<@ww.textfield label="Peso" name="pesos['cidade']" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
			<@ww.select label="Estado" name="candidato.endereco.uf.id" id="uf" list="ufs" liClass="liLeft" cssStyle="width: 45px;" headerKey="" headerValue="" onchange="javascript:populaCidades()"/>
			<@ww.select label="Cidade" name="cidade" id="cidade" list="cidades" cssStyle="width: 200px;" headerKey="" headerValue="Selecione um Estado..." liClass="liLeft" />

			<li style="clear:both;"></li>

			<@ww.textfield label="Peso" name="pesos['sexo']" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
			<@ww.select label="Sexo" name="sexo" id="sexo" list="sexos" cssStyle="width: 130px;"/>

			<@ww.textfield label="Peso" name="pesos['idade']" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
			<li>
				<span>
				Idade Preferencial:
				</span>
			</li>
			<@ww.textfield name="idadeMin" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress="return somenteNumeros(event,'');"/>
			<@ww.label value="a" liClass="liLeft" />
			<@ww.textfield name="idadeMax" id="idadeMax" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress="return somenteNumeros(event,'');"/>
			<@ww.label value="anos"/><div style="clear: both"></div>

			<@ww.textfield label="Peso" name="pesos['cargo']" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
			<@ww.select label="Cargo" id="cargoId" name="cargoId" required="true" list="cargosCheckList" cssStyle="width: 220px;" listKey="id" listValue="nome" headerKey="cargoId" headerValue=""/>
			
			<@ww.textfield label="Peso" name="pesos['tempoExperiencia']" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
			<@ww.textfield label="Experiência em meses" name="tempoExperiencia" id="tempoExperiencia" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>

			<br clear="all"/>
			
			<@ww.textfield label="Peso" name="pesos['pretensaoSalarial']" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="2" onkeypress="return somenteNumeros(event,'');"/>
			<@ww.textfield label="Pretensão Salarial" name="candidato.pretencaoSalarial" onkeypress = "return(somenteNumeros(event,','));" cssStyle="width:85px; text-align:right;" maxLength="12" />
			
			<@ww.textfield label="Quantidade de registros a serem listados"name="qtdRegistros" id="qtdRegistros" cssStyle="width: 45px; text-align:right;" onkeypress = "return(somenteNumeros(event,''));" maxLength="6" required="true" />
			
			<div class="buttonGroup">
				<input type="submit" value="" class="btnPesquisar grayBGE" onclick="${validarCampos};">
			</div>
		</@ww.form>

	<#include "../util/bottomFiltro.ftl" />

	<#if BDS?exists && !BDS && solicitacao?exists && solicitacao.id?exists>
		<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar" accesskey="V">
		</button>
	</#if>

	<#if candidatos?exists >
		<br>


<@ww.form name="formCand" action="insertCandidatos.action" validate="true" method="POST">
	<#if BDS?exists && !BDS>
		<@ww.hidden name="solicitacao.id"/>
	</#if>
	<@display.table name="candidatos" id="candidato" class="dados" >

		<#if solicitacao?exists && solicitacao.id?exists>
			<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.formCand);' />" style="width: 30px; text-align: center;">
				<input type="checkbox" value="${candidato.id?string?replace(".", "")?replace(",","")}" name="candidatosId" />
			</@display.column>
		</#if>
		
		<@display.column title="Nome">
			<a title="Ver Informação" href="javascript:popup('<@ww.url includeParams="none" value="/captacao/candidato/infoCandidato.action?candidato.id=${candidato.id?string?replace('.', '')}"/>', 580, 750)">
			${candidato.nome}
			</a>
		</@display.column>
		<@display.column property="pessoal.sexo" title="Sexo" style="width: 30px; text-align: center;" />
		<@display.column property="pessoal.idade" title="Idade" style="width: 30px; text-align: center;" />
		<@display.column title="Cidade/UF" >
			<#if candidato.endereco.cidade.nome?exists>
			${candidato.endereco.cidade.nome}/${candidato.endereco.uf.sigla}
			</#if>
		</@display.column>
		<@display.column property="pessoal.escolaridadeDescricao" title="Escolaridade" style="width: 180px;"/>
		<@display.column property="tempoExperiencia" title="Experiencia (mêses)" style="width: 65px; text-align: center;"/>
		<@display.column title="Pretensão Salarial" style="text-align: right;">
			<#if candidato.pretencaoSalarial?exists> ${candidato.pretencaoSalarial?string(",##0.00")}</#if>
		</@display.column>
		<@display.column title="Compatibilidade" style="text-align: right;">
			<#if candidato.percentualCompatibilidade?exists> ${candidato.percentualCompatibilidade?string(",##0.00")}%</#if>
		</@display.column>
		
	</@display.table>
	<br>Total de Candidatos: ${candidatos?size}
</@ww.form>



		<#if solicitacao?exists && solicitacao.id?exists>
			<div class="buttonGroup">
				<button onclick="prepareEnviarForm();" class="btnInserirSelecionados" accesskey="I"></button>
				<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar" accesskey="V"></button>
			</div>
		</#if>
	</#if>
</body>
</html>