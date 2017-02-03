<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	
	<title>Inserir Colaboradores na Solicitação</title>
	

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		
		#campoPercMin { display: none; }
	</style>
	<style type="text/css">#menuBusca a.ativaTriagemColaboradores{border-bottom: 2px solid #5292C0;}</style>
	
	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FaixaSalarialDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>

	<script type='text/javascript'>
		var empresaIds = new Array();
		<#if empresas?exists>
			<#list empresas as empresa>
				empresaIds.push(${empresa.id});
			</#list>
		</#if>
		
		$(function() {
			$('#empresaSelect').change(function() {
				populaFaixasAreas($(this).val());
			});
			
			exibeCampoPercentualMinimo();
		});
	
		function populaFaixasAreas(empresaId)
		{
			dwr.engine.setAsync(false);
			dwr.util.useLoadingMessage('Carregando...');
			<!-- Caso a empresa passada seja -1, vai trazer todos os cargos dando distinct pelo nomeMercado -->
			FaixaSalarialDWR.getByEmpresas(empresaId, empresaIds, createListFaixa);
			AreaOrganizacionalDWR.getByEmpresas(empresaId, empresaIds, createListArea);
			$("#opcaoTodasEmpresas").val(empresaId == -1);
		}
		
		function createListFaixa(data)
		{
			addChecks('faixasCheck',data);
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
			$('#percentualMinimo').val('0');
		
			marcarDesmarcarListCheckBox(document.forms[0], 'faixasCheck', false);
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
		
		function inserirColaboradores() 
		{
			var qtdMarcado = $("input[name='colaboradoresIds']:checked").size();
		
			if (qtdMarcado < 1) 
			{
				jAlert("Nenhum Candidato selecionado!");
				return false;
			}
		
			document.formColab.submit();
		}
		
		function marcarDesmarcar()
		{
			$("input[name='colaboradoresIds']").attr('checked', $('#md').attr('checked'));
		}
	</script>

	<#include "../ftl/showFilterImports.ftl" />

	<#if !palavrasChaveCurriculoEscaneado?exists>
		<#assign palavrasChaveCurriculoEscaneado=""/>
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
			<@ww.hidden name="opcaoTodasEmpresas" id="opcaoTodasEmpresas" value=""/>
			<@ww.hidden name="solicitacao.id"/>
			<#list empresas as empresa>	
				<input type="hidden" name="empresasPermitidas" value="${empresa.id}" />
			</#list>

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

			<@frt.checkListBox label="Cargo / Faixa Salarial" name="faixasCheck" list="faixasCheckList" filtro="true" selectAtivoInativo="true"/>
			<@frt.checkListBox label="Área Organizacional" name="areasCheck" id="areasCheck" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
			<@ww.checkbox label="Exibir compatibilidade das competências exigidas pela faixa salarial \"${solicitacao.nomeDoCargoDaFaixaSalarial} ${solicitacao.faixaSalarial.nome}\" com as competências do colaborador" onclick="exibeCampoPercentualMinimo();" id="exibeCompatibilidade" name="exibeCompatibilidade" labelPosition="left"/>

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
		
		<@ww.form name="formColab" action="insertColaboradores.action" validate="true" method="post">

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
			<button onclick="inserirColaboradores();" class="btnInserirSelecionados"></button>
			<button onclick="window.location='../candidatoSolicitacao/list.action?solicitacao.id=${solicitacao.id}';" class="btnVoltar"></button>
		</div>
	</#if>
</body>
</html>