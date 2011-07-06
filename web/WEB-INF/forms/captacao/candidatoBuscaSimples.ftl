<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Inserir Candidatos na Solicitação</title>

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
		function populaCargosConhecimentos(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			<!-- Caso a empresa passada seja -1, vai trazer todos os cargos dando distinct pelo nomeMercado -->
			CargoDWR.getByEmpresa(createListCargos, empresaId);
			ConhecimentoDWR.getByEmpresa(createListConhecimentos, empresaId);
		}
	
		function createListCargos(data)
		{
			addChecks('cargosCheck',data)
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
			
			for(var contador = 0; contador < campos.length; contador++)
			{
				document.getElementById(campos[contador]).value = "";
			}
			
			populaCidades();
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
			
			<@ww.select label="Empresa" name="empresaId" list="empresas" id="empresaSelect" listKey="id" listValue="nome" required="true" onchange="enviaEmpresa(this.value)" liClass="liLeft" headerKey="-1" headerValue="Todas" disabled="!compartilharCandidatos"/>
			<@ww.textfield label="Indicado Por" id="indicadoPor" name="indicadoPorBusca" cssStyle="width: 350px;"/>
			<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width: 395px;" liClass="liLeft" />
			<@ww.textfield label="CPF" name="cpfBusca" id="cpfBusca" cssClass="mascaraCpf"/>

			<@ww.select label="Estado" name="uf" id="uf" list="ufs" liClass="liLeft" cssStyle="width: 45px;" headerKey="" headerValue="" onchange="javascript:populaCidades()"/>
			<@ww.select label="Cidade" name="cidade" id="cidade" list="cidades" cssStyle="width: 300px;" headerKey="" headerValue=""/>

			<@frt.checkListBox label="Cargo / Função Pretendida" name="cargosCheck" list="cargosCheckList" />
			<@frt.checkListBox label="Conhecimentos" name="conhecimentosCheck" list="conhecimentosCheckList" />
			
			<@ww.checkbox label="Trazer apenas candidatos que nunca participaram de processos seletivos" id="somenteCandidatosSemSolicitacao" name="somenteCandidatosSemSolicitacao" labelPosition="left"/>
			
			<@ww.textfield name="qtdRegistros" id="qtdRegistros" cssStyle="width: 45px; text-align:right;" onkeypress = "return(somenteNumeros(event,''));" maxLength="6"  liClass="liLeft" />
			<li>Quantidade de registros a serem listados.<li>
			<br>

			<@ww.hidden name="filtro" value="true"/>
			<@ww.hidden name="solicitacao.id"/>
			
			<div class="buttonGroup">
				<input type="submit" value="" class="btnPesquisar grayBGE" onclick="${validarCampos};">
			</div>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />

	<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar" accesskey="V">
	</button>

	<#if candidatos?exists >
		<br>
		
		<#include "formListCandidatoSolicitacaoBusca.ftl" />

		<div class="buttonGroup">
			<button onclick="prepareEnviarForm();" class="btnInserirSelecionados"></button>
			<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar"></button>
		</div>
	</#if>
</body>
</html>