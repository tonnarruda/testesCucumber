<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ComissaoDWR.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/engine.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/util.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	
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
		
		
		function verificaComissaoByColaborador(colaboradorId, colaboradorNome)
		{
			DWRUtil.useLoadingMessage('Carregando...');
			ComissaoDWR.dataEstabilidade(               function(data){
															resultComissaoByColaborador(data, colaboradorId, colaboradorNome);
														}, colaboradorId);
		}
		
		function resultComissaoByColaborador(data, colaboradorId, colaboradorNome)
		{
			link = "prepareDesliga.action?colaborador.id="+colaboradorId+
					"&nomeBusca="+document.getElementById('nomeBusca').value+
					"&cpfBusc="+limpaCamposMascaraCpf(document.getElementById('cpfBusca').value);
			
			if (data != null){
				enviarPrepareDesliga(colaboradorId, colaboradorNome, link, data);
			} else {
				window.location=link; 
			}
		}
		
		function enviarPrepareDesliga(colaboradorId, colaboradorNome, link, data)
		{
			var msg = 'O Colaborador '+ colaboradorNome +', faz parte <br>da CIPA e possui estabilidade até o dia ' + data + '.' +
						'<br> Deseja realmente desligá-lo?';
			
			$('<div>'+ msg +'</div>').dialog({title: 'Alerta!',
													modal: true, 
													height: 150,
													width: 500,
													buttons: [
													    {
													        text: "Sim",
													        click: function() { window.location=link; }
													    },
													    {
													        text: "Não",
													        click: function() { $(this).dialog("close"); }
													    }
													] 
													});
			
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
	<@ww.actionmessage/>
	<@ww.actionerror/>

	<#if !nomeBusca?exists>
		<#assign nomeBusca="">
	</#if>
	<#if !nomeComercialBusca?exists>
		<#assign nomeComercialBusca="">
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
			<@ww.textfield label="Nome Comercial" name="nomeComercialBusca" id="nomeComercialBusca" cssStyle="width: 353px;"/>
			
			<#if integraAc && !colaborador.naoIntegraAc>
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
		<@display.column title="Ações" media="html" class="acao" style = "width:250px;">
			<#if !colaborador.desligado>
				<#assign statusSolicitacao="A"/>
				<#if integraAc && !colaborador.naoIntegraAc>
					<#if colaborador.dataSolicitacaoDesligamentoAc?exists>
						<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="#" imgTitle="Solicitação de desligamento aguardando confirmação no AC Pessoal" imgName="desliga_colab.gif" opacity=true />
					<#else>
						<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="prepareDesligaAC.action?colaborador.id=${colaborador.id}"
						 imgTitle="Solicitação de Desligamento do Colaborador no Ac Pessoal" imgName="desliga_colab.gif"/>
					</#if>
				<#else>
					<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="javascript:verificaComissaoByColaborador('${colaborador.id}', '${colaborador.nome}')" imgTitle="Desligar colaborador" imgName="desliga_colab.gif"/>
				</#if>

				<@frt.link verifyRole="ROLE_COLAB_LIST_ENTREVISTA" href="#" imgTitle="Entrevista de Desligamento - disponível apenas após o desligamento do colaborador" imgName="entrevistaBalaoDesligaNova.gif" opacity=true/>
			<#else>
				<#assign statusSolicitacao="I"/>
				<#if colaborador.dataDesligamento?exists && !colaborador.motivoDemissao.motivo?exists>
					<#if integraAc && !colaborador.naoIntegraAc>
						<#assign imgMotivoDeslig="desligadoAC5.gif"/>
						<#assign titleMotivoDeslig="Inserir motivo de desligamento"/>
					<#else>
						<#assign imgMotivoDeslig="desliga_colab.gif"/>
						<#assign titleMotivoDeslig="Desligar colaborador"/>
					</#if>
					
					<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="javascript:verificaComissaoByColaborador('${colaborador.id}', '${colaborador.nome}')" imgTitle="${titleMotivoDeslig}" imgName="${imgMotivoDeslig}"/>
				<#else>
					
					<@frt.link verifyRole="ROLE_COLAB_LIST_DESLIGAR" href="javascript:verificaComissaoByColaborador('${colaborador.id}', '${colaborador.nome}')" imgTitle="Colaborador já desligado" imgName="desliga_colab.gif" opacity=true/>
				</#if>
	
				<#if colaborador.respondeuEntrevista?exists && colaborador.respondeuEntrevista>
					<#assign imgRespondeuEntrevista="entrevistaBalaoDesligaEdita.gif"/>
				<#else>
					<#assign imgRespondeuEntrevista="entrevistaBalaoDesligaNova.gif"/>
				</#if>
					<@frt.link verifyRole="ROLE_COLAB_LIST_ENTREVISTA" href="../../pesquisa/entrevista/prepareResponderEntrevista.action?colaborador.id=${colaborador.id}&validarFormulario=false&voltarPara=../../geral/colaborador/list.action" 
					imgTitle="Entrevista de desligamento" imgName="${imgRespondeuEntrevista}"/>
			</#if>

			<@frt.link verifyRole="ROLE_COLAB_LIST_EDITAR" href="javascript:enviarPrepareUpDate('${colaborador.id}')" imgTitle="Editar" imgName="edit.gif"/>
			
			<@frt.link verifyRole="ROLE_COLAB_LIST_EXCLUIR" href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?colaborador.id=${colaborador.id}'});" imgTitle="Excluir" imgName="delete.gif"/>
			
			<@frt.link verifyRole="ROLE_CAD_HISTORICOCOLABORADOR" href="javascript:enviarPrepareProgressaoColaborador('${colaborador.id}')" imgTitle="Visualizar Progressão" imgName="progressao.gif"/>

			<@frt.link verifyRole="ROLE_COLAB_LIST_PERFORMANCE" href="preparePerformanceFuncional.action?colaborador.id=${colaborador.id}" imgTitle="Performance Profissional" imgName="medalha.gif"/>
			
			<@frt.link verifyRole="ROLE_COLAB_LIST_NIVELCOMPETENCIA" href="../../captacao/nivelCompetencia/listCompetenciasColaborador.action?colaborador.id=${colaborador.id}" imgTitle="Competências" imgName="competencias.gif"/>

			<@frt.link verifyRole="ROLE_COLAB_LIST_SOLICITACAO" href="prepareColaboradorSolicitacao.action?colaborador.id=${colaborador.id}&statusCandSol=${statusSolicitacao}&voltarPara=../../geral/colaborador/list.action" imgTitle="Incluir em Solicitação" imgName="db_add.gif"/>

			<@frt.link verifyRole="ROLE_COLAB_LIST_DOCUMENTOANEXO" href="../documentoAnexo/list.action?documentoAnexo.origem=D&documentoAnexo.origemId=${colaborador.id}" imgTitle="Documentos do Colaborador" imgName="anexos.gif"/>

			<#if colaborador.usuario.id?exists>
				<@frt.link verifyRole="ROLE_COLAB_LIST_CRIARUSUARIO" href="../../acesso/usuario/prepareUpdate.action?origem=C&usuario.id=${colaborador.usuario.id}&colaborador.id=${colaborador.id}" imgTitle="Editar Acesso ao Sistema" imgName="key.gif"/>
			<#else>
				<@frt.link verifyRole="ROLE_COLAB_LIST_CRIARUSUARIO" href="../../acesso/usuario/prepareInsert.action?origem=C&colaborador.id=${colaborador.id}&nome=${colaborador.nomeComercial}" imgTitle="Criar Acesso ao Sistema" imgName="key_add.gif"/>
			</#if>
		
			<#if colaborador.candidato?exists && colaborador.candidato.id?exists>
				<@frt.link verifyRole="ROLE_COLAB_LIST_VISUALIZARCURRICULO" href="javascript:popup('../../captacao/candidato/infoCandidato.action?candidato.id=${colaborador.candidato.id}&origemList=CO', 580, 750)" imgTitle="Visualizar Currículo" imgName="page_curriculo.gif"/>
			<#else>
				<@frt.link verifyRole="ROLE_COLAB_LIST_VISUALIZARCURRICULO" imgTitle="Não é possível visualizar currículo, este colaborador não é candidato." imgName="page_curriculo.gif" opacity=true/>
			</#if>
		</@display.column>

		<#if colaborador.dataDesligamento?exists>
			<#assign style="color:#e36f6f;"/>
		</#if>

		<@display.column property="matricula" title="Matrícula" style='${style}'/>
		<@display.column property="nome" title="Nome" style='${style}'/>
		<@display.column property="nomeComercial" title="Nome Comercial" style='${style}'/>
		<@display.column property="pessoal.cpf" title="CPF" style='${style}'/>
		<@display.column property="dataAdmissao" title="Data de Admissão" format="{0,date,dd/MM/yyyy}" style='${style}'/>
	</@display.table>

	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>

	<div class="buttonGroup">
		<@authz.authorize ifAllGranted="ROLE_CAD_COLABORADOR">
			<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
		</@authz.authorize>
		<@authz.authorize ifAllGranted="ROLE_REL_LISTA_COLAB">
			<button class="btnListagemColaborador" onclick="window.location='prepareRelatorioDinamico.action'"></button>
		</@authz.authorize>
	</div>
</body>
</html>