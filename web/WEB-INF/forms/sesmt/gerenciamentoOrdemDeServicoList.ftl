<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
	<@ww.head/>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FaixaSalarialDWR.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>"></script>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<#include "../ftl/showFilterImports.ftl" />
	<#assign validarCampos="return validaFormulario('form', null, null, true)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<script type='text/javascript'>
		$(function() {
			$('#cargo').change(function() {
				var cargoId = $(this).val();
					
				if (cargoId)
					FaixaSalarialDWR.findByCargo(createListFaixas, cargoId);
			});
			
			$('#cargo').change();
		});
			
		function createListFaixas(data)
		{
			addOptionsByCollection('faixaSalarial', data, 'Selecione...');
			
			<#if historicoColaborador.faixaSalarial?exists && historicoColaborador.faixaSalarial.id?exists >
				$('#faixaSalarial').val(${historicoColaborador.faixaSalarial.id});
			</#if>
		}
	</script>

	<title>Gerenciamento de Ordem de Serviço</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />


	<@ww.form name="form" id="form" action="listGerenciamentoOS.action" method="POST">
		<table>
				<tr>
					<td width="370">
						<@ww.textfield label="Colaborador" name="colaborador.nome" id="nomeBusca" cssStyle="width: 353px;"/>
						<@ww.textfield label="Matrícula" name="colaborador.matricula" id="matriculaBusca" liClass="liLeft" cssStyle="width: 226px;"/>
						<@ww.textfield label="CPF" name="colaborador.pessoal.cpf" id="cpfBusca" cssClass="mascaraCpf"/>
						<@ww.select label="Situação" name="situacao" id="situacao" list="situacoes" cssStyle="width: 355px;"/>
						<@ww.select label="Considerar colaboradores" name="filtroOrdemDeServico" id="filtroOrdemDeServico" list="filtrosOrdemDeServico" cssStyle="width: 355px;"/>
					</td>
					<td>
						<@ww.select label="Área Organizacional" name="historicoColaborador.areaOrganizacional.id" id="areaOrganizacional" list="areasList"  listKey="id" listValue="descricao" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;"/>
						<@ww.select label="Estabelecimento" name="historicoColaborador.estabelecimento.id" id="estabelecimento" list="estabelecimentosList" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;"/>
						<@ww.select label="Cargo" name="historicoColaborador.faixaSalarial.cargo.id" id="cargo" list="cargosList" listKey="id" listValue="nomeMercado" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;"/>
						<@ww.select label="Faixa Salarial" name="historicoColaborador.faixaSalarial.id" id="faixaSalarial" headerValue="Selecione..." headerKey="" cssStyle="width:355px;"/>
					</td>
				</tr>
			</table>
		<@ww.hidden id="pagina" name="page"/>
		<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	
	<p align="right">
		<span style="background-color: #e36f6f;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Colaboradores Desligados&nbsp;&nbsp;
		<span style="background-color: #002EB8;">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Colaboradores sem função no cadastro de seu último histórico
	</p>

	<@display.table name="colaboradores" id="colaborador" class="dados">
		<#assign style=""/>
		
		<@display.column title="Ações" class="acao" style="width:95px";>
			<#if colaborador.temFuncao >
				<@frt.link href="list.action?colaborador.id=${colaborador.id}" imgTitle="Ordens de Serviço(OS)" imgName="folhaCheia.gif"/>
			<#else>
				<#assign style="color:#002EB8;"/>
				<@frt.link href="list.action?colaborador.id=${colaborador.id}" imgTitle="Colaborador sem histórico com função." imgName="folhaCheia.gif" disabled=true />
			</#if>
		</@display.column>

		<#if colaborador.dataDesligamento?exists>
			<#assign style="color:#e36f6f;"/>
		</#if> 
		<@display.column property="nome" title="Colaborador" style="${style};"/>
		<@display.column property="pessoal.cpf" title="CPF" style="${style};"/>

	</@display.table>

	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>

</body>
</html>