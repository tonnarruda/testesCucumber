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
			
			exibeCampoPercentualMinimo();
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
		
		function exibeCampoPercentualMinimo()
		{
			$('#campoPercMin').toggle( $('#exibeCompatibilidade').is(':checked') );
		}
		
		function pesquisar() 
		{
			if ($('#percentualMinimo').val() > 100)
			{
				jAlert('Informe um percentual válido.');
				$('#percentualMinimo').focus();
				return false;
			}
		
			document.formBusca.submit();
		}
	</script>

	<#include "../ftl/showFilterImports.ftl" />

	<#if !palavrasChave?exists>
		<#assign palavrasChave=""/>
	</#if>
	<#if !formas?exists>
		<#assign formas=""/>
	</#if>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
</head>

<body>
	<#include "buscaCandidatoSolicitacaoLinks.ftl" />

	<#include "../util/topFiltro.ftl" />
		<button onclick="limparFiltro();" class="btnLimparFiltro grayBGE"></button>		

		<@ww.form name="formBusca" id="formBusca" action="triagemColaboradores.action" method="POST">
			
			<@ww.hidden name="solicitacao.id"/>

			<@ww.select label="Empresa" name="empresaId" list="empresas" id="empresaSelect" listKey="id" listValue="nome" required="true" liClass="liLeft" headerKey="-1" headerValue="Todas" disabled="!compartilharCandidatos"/>
			<@ww.select label="Escolaridade mínima" name="escolaridade" id="escolaridade" list="escolaridades" liClass="liLeft" cssStyle="width: 220px;" headerKey="" headerValue=""/>

			<li style="clear:both;"></li>
			<@ww.select label="Sexo" name="sexo" id="sexo" list="sexos" cssStyle="width: 130px;" liClass="liLeft"/>
			
			<li><span>Idade Preferencial:</span></li>
			<@ww.textfield name="idadeMin" id="idadeIni" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
			<@ww.label value="a" liClass="liLeft" />
			<@ww.textfield name="idadeMax" id="idadeFim" cssStyle="width:30px; text-align:right;" liClass="liLeft" maxLength="3" onkeypress = "return(somenteNumeros(event,''));"/>
			<@ww.label value="anos"/>

			<li style="clear:both;"></li>

			<@frt.checkListBox label="Cargo" name="cargosCheck" list="cargosCheckList" />
			<@frt.checkListBox label="Área Organizacional" name="areasCheck" id="areasCheck" list="areasCheckList"/>
			<@ww.checkbox label="Exibir compatibilidade das competências exigidas pelo cargo \"${solicitacao.nomeDoCargoDaFaixaSalarial}\" com as competências do colaborador" id="exibeCompatibilidade" name="exibeCompatibilidade" labelPosition="left"/>

			<li id="campoPercMin">
				<label>Percentual Mínimo de Compatibilidade: *<label>
				<@ww.textfield theme="simple" name="percentualMinimo" id="percentualMinimo" onkeypress = "return somenteNumeros(event,'');" maxLength="3" required="true" cssStyle="width: 30px; text-align:right;" />
				%
			</li>

			<@ww.hidden name="filtro" value="true"/>
			
			<div class="buttonGroup">
				<input type="button" value="" class="btnPesquisar grayBGE" onclick="pesquisar();"/>
			</div>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br />

	<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar" accesskey="V"></button>

	<#if colaboradores?exists >
		<div id="legendas" align="right"></div>
		<br />
		
		<@ww.form name="formColab" action="insertColaboradores.action" validate="true" method="POST">

			<@ww.hidden name="solicitacao.id"/>
		
			<@display.table name="colaboradores" id="colaborador" class="dados">
				
				<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(document.formColab);' />" style="width: 30px; text-align: center;">
					<input type="checkbox" value="${colaborador.id?string}" name="colaboradoresIds" />
				</@display.column>
				
				<@display.column title="Nome" property="nome"/>

				<@display.column property="pessoal.sexo" title="Sexo" style="width:30px; text-align:center;"/>

				<@display.column property="pessoal.idade" title="Idade" style="width:30px; text-align:center;"/>

				<@display.column property="pessoal.escolaridadeDescricao" title="Escolaridade" style="width:300px;"/>
				
				<#if exibeCompatibilidade>
					<@display.column title="Compatibilidade" style="width:100px; text-align:right;">
						<#if colaborador.percentualCompatibilidade?exists> 
							<#if 90 <= colaborador.percentualCompatibilidade?int>
								<#assign color="green"/>
							<#elseif 50 <= colaborador.percentualCompatibilidade?int> 
								<#assign color="orange"/>
							<#else>
								<#assign color="red"/>
							</#if>
							
							<div style="background-color: ${color}; width: ${colaborador.percentualCompatibilidade?int}px; height: 3px;"></div>
							<div style="clear: both"></div>
							${colaborador.percentualCompatibilidade?string(",##0.00")}%
						</#if>
					</@display.column>
				</#if>
			</@display.table>
			<br>Total de Colaboradores: ${colaboradores?size}
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="prepareEnviarForm();" class="btnInserirSelecionados"></button>
			<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar"></button>
		</div>
	</#if>
</body>
</html>