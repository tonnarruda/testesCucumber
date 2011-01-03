<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
	</style>
		<#include "../ftl/mascarasImports.ftl" />
	<script>
		function enviarPrepareUpDate(colaborador)
		{
			link = "prepareUpdate.action?colaborador.id="+colaborador+
					"&nomeBusca="+document.getElementById('nomeBusca').value+
					"&cpfBusca="+limpaCamposMascaraCpf(document.getElementById('cpfBusca').value)+
					"&page="+document.getElementById('pagina').value;
			window.location = link;
		}

		function enviarPrepareDesliga(colaborador)
		{
			link = "prepareDesliga.action?colaborador.id="+colaborador+
					"&nomeBusca="+document.getElementById('nomeBusca').value+
					"&cpfBusc="+limpaCamposMascaraCpf(document.getElementById('cpfBusca').value);
			window.location=link;
		}

		function enviarPrepareProgressaoColaborador(colaborador)
		{
			link = "../../cargosalario/historicoColaborador/list.action?colaborador.id="+colaborador;
			window.location = link;
		}
	</script>

	<#include "../ftl/showFilterImports.ftl" />
	<#assign validarCampos="return validaFormulario('formBusca', null, null, true)"/>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<title>Colaboradores</title>
</head>
<body>
	<@ww.actionerror/>
	<@ww.actionmessage/>

	<#if !nomeBusca?exists>
		<#assign nomeBusca="">
	</#if>
	<#if !cpfBusca?exists>
		<#assign cpfBusca="">
	</#if>
	<#if !page?exists>
		<#assign page=1>
	</#if>

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" onsubmit="${validarCampos}" method="POST" id="formBusca">
			<@ww.textfield label="Matrícula" name="matriculaBusca" id="matriculaBusca" liClass="liLeft" cssStyle="width: 243px;"/>
			<@ww.textfield label="CPF" name="cpfBusca" id="cpfBusca" cssClass="mascaraCpf"/>
			<@ww.textfield label="Nome" name="nomeBusca" id="nomeBusca" cssStyle="width: 353px;"/>
			
			<#if integraAc>
				<@ww.select label="Situação" name="situacao" id="situacao" list="situacaosIntegraAC" cssStyle="width: 355px;"/>
			<#else>
				<@ww.select label="Situação" name="situacao" id="situacao" list="situacaos" cssStyle="width: 355px;"/>
			</#if>
			
			<@ww.select label="Área Organizacional" name="areaOrganizacional.id" id="areaOrganizacional" list="areasList"  listKey="id" listValue="descricao" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;"/>
			<@ww.select label="Estabelecimento" name="estabelecimento.id" id="estabelecimento" list="estabelecimentosList" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;"/>
			<@ww.select label="Cargo" name="cargo.id" id="cargo" list="cargosList" listKey="id" listValue="nomeMercado" headerKey="" headerValue="Selecione..." cssStyle="width: 355px;"/>

			<@ww.hidden id="pagina" name="page"/>

			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<@display.table name="colaboradors" id="colaborador" pagesize=20 class="dados" defaultsort=3 >
		<#assign style=""/>
		<@display.column title="Ações" media="html" class="acao" style = "width:230px;">
			<#if !colaborador.desligado>
				<#if integraAc>
					<img border="0" title="Desligue o colaborador no AC Pessoal" src="<@ww.url includeParams="none" value="/imgs/desliga_colab.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				<#else>
					<a href="javascript:enviarPrepareDesliga('${colaborador.id}')"><img border="0" title="Desligar colaborador" src="<@ww.url includeParams="none" value="/imgs/desliga_colab.gif"/>"></a>
				</#if>

				<img border="0" title="Entrevista de Desligamento - disponível apenas após o desligamento do colaborador" src="<@ww.url includeParams="none" value="/imgs/entrevistaBalaoDesligaNova.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			<#else>
				<#if colaborador.dataDesligamento?exists && !colaborador.motivoDemissao.motivo?exists>
					<#if integraAc>
						<img border="0" title="Desligue o colaborador no AC Pessoal" src="<@ww.url includeParams="none" value="/imgs/desliga_colab.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
					<#else>
						<a href="javascript:enviarPrepareDesliga('${colaborador.id}')"><img border="0" title="Desligar colaborador" src="<@ww.url includeParams="none" value="/imgs/desligadoAC5.gif"/>"></a>
					</#if>			
				<#else>
					<a href="javascript:enviarPrepareDesliga('${colaborador.id}')"><img border="0" title="Colaborador já desligado" src="<@ww.url includeParams="none" value="/imgs/desliga_colab.gif"/>" style="opacity:0.5;filter:alpha(opacity=50);"></a>
				</#if>

				<#if colaborador.respondeuEntrevista?exists && colaborador.respondeuEntrevista>
					<a href="../../pesquisa/entrevista/prepareResponderEntrevista.action?colaborador.id=${colaborador.id}&validarFormulario=false&voltarPara=../../geral/colaborador/list.action"><img border="0" title="Entrevista de desligamento" src="<@ww.url includeParams="none" value="/imgs/entrevistaBalaoDesligaEdita.gif"/>"></a>
				<#else>
					<a href="../../pesquisa/entrevista/prepareResponderEntrevista.action?colaborador.id=${colaborador.id}&validarFormulario=false&voltarPara=../../geral/colaborador/list.action"><img border="0" title="Entrevista de desligamento" src="<@ww.url includeParams="none" value="/imgs/entrevistaBalaoDesligaNova.gif"/>"></a>
				</#if>

			</#if>

			<a href="javascript:enviarPrepareUpDate('${colaborador.id}')"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='delete.action?colaborador.id=${colaborador.id}'"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			<a href="javascript:enviarPrepareProgressaoColaborador('${colaborador.id}')"><img border="0" title="Visualizar Progressão" src="<@ww.url includeParams="none" value="/imgs/progressao.gif"/>"></a>
			<a href="preparePerformanceFuncional.action?colaborador.id=${colaborador.id}"><img border="0" title="Performance Profissional" src="<@ww.url includeParams="none" value="/imgs/medalha.gif"/>"></a>
			<#--<a href="../historicoColaboradorBeneficio/list.action?colaborador.id=${colaborador.id}"><img border="0" title="Benefícios" src="<@ww.url includeParams="none" value="/imgs/table.gif"/>"></a>-->

			<#if !colaborador.desligado>
				<a href="javascript:window.location='prepareColaboradorSolicitacao.action?colaborador.id=${colaborador.id}'"><img border="0" title="Incluir em Solicitação" src="<@ww.url includeParams="none" value="/imgs/db_add.gif"/>"></a>
			<#else>
				<img border="0" title="Colaborador desligado" src="<@ww.url includeParams="none" value="/imgs/db_add.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>

			<a href="../documentoAnexo/list.action?documentoAnexo.origem=D&documentoAnexo.origemId=${colaborador.id}"><img border="0" title="Documentos do Colaborador" src="<@ww.url includeParams="none" value="/imgs/anexos.gif"/>"></a>

			<@authz.authorize ifAnyGranted="ROLE_USER">
				<#if colaborador.usuario.id?exists>
					<a href="../../acesso/usuario/prepareUpdate.action?origem=C&usuario.id=${colaborador.usuario.id}&colaborador.id=${colaborador.id}"><img border="0" title="Editar Acesso ao Sistema" src="<@ww.url includeParams="none" value="/imgs/key.gif"/>"></a>
				<#else>
					<a href="../../acesso/usuario/prepareInsert.action?origem=C&colaborador.id=${colaborador.id}&nome=${colaborador.nomeComercial}"><img border="0" title="Criar Acesso ao Sistema" src="<@ww.url includeParams="none" value="/imgs/key_add.gif"/>"></a>
				</#if>
			</@authz.authorize>
			
			<#if colaborador.candidato?exists && colaborador.candidato.id?exists>
				<a href="javascript:popup('../../captacao/candidato/infoCandidato.action?candidato.id=${colaborador.candidato.id}', 580, 750)"><img border="0" title="Visualizar Currículo" src="<@ww.url includeParams="none" value="/imgs/page_curriculo.gif"/>"></a>
			<#else>
				<img border="0" title="Não é possível visualizar currículo, este colaborador não é candidato." src="<@ww.url includeParams="none" value="/imgs/page_curriculo.gif"/>" style="opacity:0.3;filter:alpha(opacity=40);">
			</#if>
		</@display.column>

		<#if colaborador.dataDesligamento?exists>
			<#assign style="color:#e36f6f;"/>
		</#if>

		<@display.column property="matricula" title="Matrícula" style='${style}'/>
		<@display.column property="nome" title="Nome" style='${style}'/>
		<@display.column property="pessoal.cpf" title="CPF" style='${style}'/>
		<@display.column property="dataAdmissao" title="Data de Admissão" format="{0,date,dd/MM/yyyy}" style='${style}'/>
	</@display.table>

	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
		<button class="btnListagemColaborador" onclick="window.location='prepareRelatorioDinamico.action'"></button>
	</div>

</body>
</html>