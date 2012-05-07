<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	
	<title>Inserir Colaboradores na Solicitação</title>
	

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>
	<style type="text/css">#menuBusca a.ativaTriagemColaboradores{color: #FFCB03;}</style>
	
	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CargoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/buscaCandidatoSolicitacao.js"/>'></script>

	<script type='text/javascript'>
		$(function() {
			$('#empresaSelect').change(function() {
				populaCargosAreas($(this).val());
			});
		});
	
		function populaCargosAreas(empresaId)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			<!-- Caso a empresa passada seja -1, vai trazer todos os cargos dando distinct pelo nomeMercado -->
			CargoDWR.getByEmpresa(createListCargo, empresaId);
			AreaOrganizacionalDWR.getByEmpresa(createListArea, empresaId);
		}
		
		function createListCargo(data)
		{
			addChecks('cargosCheck',data);
		}

		function createListArea(data)
		{
			addChecks('areasCheck',data);
		}
				
		function limparFiltro()
		{
			$('#escolaridade').val('');
			$('#sexo').val('I');
			$('#idadeIni').val('');
			$('#idadeFim').val('');
			$('#exibeCompatibilidade').removeAttr('checked');
			$('#percentualMinimo').val('50');
		
			marcarDesmarcarListCheckBox(document.forms[0], 'cargosCheck', false);
			marcarDesmarcarListCheckBox(document.forms[0], 'areasCheck', false);
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
			
			<@ww.hidden name="solicitacao.id"/>

			<@ww.select label="Empresa" name="empresaId" list="empresas" id="empresaSelect" listKey="id" listValue="nome" required="true" liClass="liLeft" headerKey="-1" headerValue="Todas" disabled="!compartilharCandidatos"/>
			<@ww.select label="Escolaridade mínima" name="escolaridade" id="escolaridade" list="escolaridades" liClass="liLeft" cssStyle="width: 220px;" headerKey="" headerValue=""/>

			<li style="clear:both;"></li>
			<@ww.select label="Sexo" name="sexo" id="sexo" list="sexos" cssStyle="width: 130px;" liClass="liLeft"/>
			<li>
				<span>
				Idade Preferencial:
				</span>
			</li>
			<@ww.textfield name="idadeMin" id="idadeIni" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
			<@ww.label value="a" liClass="liLeft" />
			<@ww.textfield name="idadeMax" id="idadeFim" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
			<@ww.label value="anos"/><div style="clear: both"></div>

			<@frt.checkListBox label="Cargo / Função Pretendida" name="cargosCheck" list="cargosCheckList" />
			<@frt.checkListBox label="Área Organizacional" name="areasCheck" id="areasCheck" list="areasCheckList"/>
			<@ww.checkbox label="Exibir compatibilidade das competências exigidas pelo cargo \"${solicitacao.nomeDoCargoDaFaixaSalarial}\" com as competências do colaborador" id="exibeCompatibilidade" name="exibeCompatibilidade" labelPosition="left"/>

			<label>Percentual Mínimo de Compatibilidade: *<label>
			<@ww.textfield theme="simple" name="percentualMinimo" id="percentualMinimo" onkeypress = "return somenteNumeros(event,'');" maxLength="3" required="true" cssStyle="width: 30px; text-align:right;" />
			%

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