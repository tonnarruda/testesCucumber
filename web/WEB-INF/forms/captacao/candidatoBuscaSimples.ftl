<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	
	<#if solicitacao?exists && solicitacao.id?exists>
		<title>Inserir Candidatos na Solicitação</title>
	<#else>
		<title>Triagem de currículos</title>
	</#if>
	

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>
	<style type="text/css">#menuBusca a.ativaSimples{color: #FFCB03;}</style>
	
	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ConhecimentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CidadeDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/buscaCandidatoSolicitacao.js"/>'></script>

	<script type='text/javascript'>
		$(function() {
			$('#empresaSelect').change(function() {
				populaCargosConhecimentos($(this).val());
			});
			
			var obj = document.getElementById("legendas");
			obj.innerHTML += "&nbsp;&nbsp;<span style='background-color: #009900;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Participa ou participou de processo seletivo";
		});
	
		function populaCargosConhecimentos(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			<!-- Caso a empresa passada seja -1, vai trazer todos os cargos dando distinct pelo nomeMercado -->
			CargoDWR.getByEmpresa(createListCargo, empresaId);
			ConhecimentoDWR.getByEmpresa(createListConhecimentos, empresaId);
		}
		
		function createListCargo(data)
		{
			addChecks('cargosCheck',data);
		}
		
		function createListConhecimentos(data)
		{
			addChecks('conhecimentosCheck',data)
		}
		
		function limparFiltro()
		{
			var valueCpf = "   .   .   -  ";
			var campos = new Array('indicadoPor', 'nomeBusca', 'uf', 'cidade');
			$('#qtdRegistros').val(100);
			$('#ordenar').val('dataAtualizacao');
			
			for(var contador = 0; contador < campos.length; contador++)
			{
				document.getElementById(campos[contador]).value = "";
			}
			
			populaCidadesCheckList();
			document.getElementById('cpfBusca').value = valueCpf; 
			marcarDesmarcarListCheckBox(document.forms[0], 'cargosCheck',false);
			marcarDesmarcarListCheckBox(document.forms[0], 'conhecimentosCheck',false);
		}
	</script>

	<#include "../ftl/showFilterImports.ftl" />

	<#if !palavrasChave?exists>
		<#assign palavrasChave=""/>
	</#if>
	<#if !formas?exists>
		<#assign formas=""/>
	</#if>
	

	<#assign validarCampos="return validaFormulario('formBusca', new Array('qtdRegistros'),  null)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
</head>

<body>
	<#include "buscaCandidatoSolicitacaoLinks.ftl" />

	<#include "../util/topFiltro.ftl" />
		<button onclick="limparFiltro();" class="btnLimparFiltro grayBGE"></button>		

		<@ww.form name="formBusca" id="formBusca" action="buscaSimples.action" onsubmit="${validarCampos}" method="POST">
			
			<@ww.select label="Empresa" name="empresaId" list="empresas" id="empresaSelect" listKey="id" listValue="nome" required="true" liClass="liLeft" headerKey="-1" headerValue="Todas" disabled="!compartilharCandidatos"/>
			<@ww.textfield label="Indicado Por" id="indicadoPor" name="indicadoPorBusca" cssStyle="width: 350px;"/>
			<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width: 395px;" liClass="liLeft" />
			<@ww.textfield label="CPF" name="cpfBusca" id="cpfBusca" cssClass="mascaraCpf"/>

			<@ww.select label="Escolaridade mínima" name="escolaridade" id="escolaridade" list="escolaridades" cssStyle="width: 220px;" headerKey="" headerValue=""/>
			<@ww.select label="Estado" name="uf" id="uf" list="ufs" cssStyle="width: 45px;" headerKey="" headerValue="" onchange="javascript:populaCidadesCheckList()"/>
			<@frt.checkListBox label="Cidades" id="cidadesCheck" name="cidadesCheck" list="cidadesCheckList" />

			<@frt.checkListBox label="Cargo / Função Pretendida" name="cargosCheck" list="cargosCheckList" />
			<@frt.checkListBox label="Conhecimentos" name="conhecimentosCheck" list="conhecimentosCheckList" />
			
			<@ww.select label="Ordenar Por" name="ordenar" id="ordenar" list=r"#{'dataAtualizacao':'Data de Atualização','nome':'Nome'}" cssStyle="width: 170px;" />			
			<@ww.textfield label="Quantidade de registros a serem listados"name="qtdRegistros" id="qtdRegistros" cssStyle="width: 45px; text-align:right;" onkeypress = "return(somenteNumeros(event,''));" maxLength="6" required="true" />

			<#if solicitacao?exists && solicitacao.id?exists>			
				<@ww.checkbox label="Trazer apenas candidatos que nunca participaram de processos seletivos" id="somenteCandidatosSemSolicitacao" name="somenteCandidatosSemSolicitacao" labelPosition="left"/>
				<@ww.hidden name="solicitacao.id"/>
			</#if>

			<@ww.hidden name="filtro" value="true"/>
			
			<div class="buttonGroup">
				<input type="submit" value="" class="btnPesquisar grayBGE" onclick="${validarCampos};">
			</div>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br />

	<#if solicitacao?exists && solicitacao.id?exists>
		<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar" accesskey="V"></button>
	</#if>


	<#if candidatos?exists >
		<div id="legendas" align="right"></div>
		<br>
		
		<#include "formListCandidatoSolicitacaoBusca.ftl" />

		<#if solicitacao?exists && solicitacao.id?exists>
			<div class="buttonGroup">
				<button onclick="prepareEnviarForm();" class="btnInserirSelecionados"></button>
				<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar"></button>
			</div>
		</#if>
	</#if>
</body>
</html>