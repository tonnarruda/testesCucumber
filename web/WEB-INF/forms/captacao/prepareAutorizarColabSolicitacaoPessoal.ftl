<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#include "../ftl/showFilterImports.ftl" />
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		.alterarStatusDiv {	display: none;}
		.calendar { z-index: 99998 !important; }
		.emAnalise { color: #555 !important; }
		.autorizado { color: green !important; }
		.naoAprovado { color: red !important; }
	</style>

	<title>Autorizar Participação do Colaborador na Solicitacao de Pessoal</title>
	
	<script type='text/javascript'>
		function popupAutorizacaoGestorColabSolicitacaoPessoal(candidatoSolicitacaoId, statusAutorizacaoGestor, dataAutorizacaoGestor, obsAutorizacaoGestor, colaboradorNome) 
		{
			$('#candidatoSolicitacaoId').val(candidatoSolicitacaoId);
			$('#statusAutorizacaoGestor').val(statusAutorizacaoGestor);
			$('#obsAutorizacaoGestor').val(obsAutorizacaoGestor);
			$('#colaboradorNome').val(colaboradorNome);
			$('#colaboradorNomeBuscaForm').val($('colaboradorNomeBusca').val());
			$('#solicitacaoDescricaoBuscaForm').val($('solicitacaoDescricaoBuscaForm').val());
			$('#statusBuscaForm').val($('statusBuscaForm').val());
			
			if(dataAutorizacaoGestor == null || dataAutorizacaoGestor == ""){
				$('#dataAutorizacaoGestorText').val($.datepicker.formatDate('dd/mm/yy',new Date()));
			}else{
				$('#dataAutorizacaoGestor').val(dataAutorizacaoGestor);
				$('#dataAutorizacaoGestorText').val(dataAutorizacaoGestor);
			}
		
			$('#alterarStatusDiv').dialog({ modal: true, 
											width: 500,
											height: 325,
											title: 'Alterar status do colaborador ' + colaboradorNome,
											buttons: 
											[
											    {
											        text: "Gravar",
											        click: function() { 
														$(this).dialog("close");
														validaFormulario('formAutorizarColabSolicitacaoPessoal', new Array(), new Array('dataStatus'));
											        }
											    },
											    {
											        text: "Cancelar",
											        click: function() { $(this).dialog("close"); }
											    }
											]
										}); 
		}
	</script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<#include "../util/topFiltro.ftl" />
		<@ww.form name="form" action="prepareAutorizarColabSolicitacaoPessoal.action" validate="true" method="POST" id="formBusca">
			<@ww.hidden id="pagina" name="page"/>
			<@ww.textfield label="Colaborador" name="colaboradorNomeBusca" id="colaboradorNomeBusca" cssStyle="width: 465px;"/>
			<@ww.textfield label="Descrição da Solicitação de Pessoal" name="solicitacaoDescricaoBusca" id="solicitacaoDescricaoBusca" cssStyle="width: 465px;"/>
			<@ww.select id="status" label="Status" name="statusBusca" list="statusAutorizacao" headerValue="Todos" headerKey="T" cssStyle="width: 465px;"/>
			<@ww.hidden id="pagina" name="page"/>
			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br />
	
	<#if candidatoSolicitacaos?exists && 0 < candidatoSolicitacaos?size>
		<div id="legendas" align="right">
			<span style='background-color: #555;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Em Análise&nbsp;&nbsp;&nbsp;
			<span style='background-color: green;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Autorizados&nbsp;&nbsp;&nbsp;
			<span style='background-color: red;'>&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Não Autorizado&nbsp;&nbsp;
		</div>
		<br />
	
		<@display.table name="candidatoSolicitacaos" id="candidatoSolicitacao" class="dados" >
			<#if candidatoSolicitacao.statusAutorizacaoGestor == 'A'>
				<#assign imgStatus = "status_green.png"/>
				<#assign classe = "autorizado"/>
			<#elseif candidatoSolicitacao.statusAutorizacaoGestor == 'R'>
				<#assign imgStatus = "status_red.png"/>
				<#assign classe = "naoAprovado"/>
			<#else>
				<#assign imgStatus = "status_yellow.png"/>
				<#assign classe = "emAnalise"/>
			</#if>
			
			<#if candidatoSolicitacao.dataAutorizacaoGestor?exists>
				<#assign dataAutorizacaoGestor = "${candidatoSolicitacao.dataAutorizacaoGestor?date}"/>
			<#else>
				<#assign dataAutorizacaoGestor = ""/>
			</#if>
			
			<#if candidatoSolicitacao.obsAutorizacaoGestor?exists>
				<#assign obsAutorizacaoGestor = "${candidatoSolicitacao.obsAutorizacaoGestor}"/>
			<#else>
				<#assign obsAutorizacaoGestor = ""/>
			</#if>
			
			<@display.column title="Ações" class="${classe}">
				<@frt.link verifyRole="ROLE_MOV_AUTOR_COLAB_SOL_PESSOAL" href="javascript:;" imgTitle="Alterar status (${candidatoSolicitacao.statusAutorizacaoGestorFormatado})" imgName="${imgStatus}" 
					onclick="javascript:popupAutorizacaoGestorColabSolicitacaoPessoal(${candidatoSolicitacao.id}, '${candidatoSolicitacao.statusAutorizacaoGestor}','${dataAutorizacaoGestor}', '${obsAutorizacaoGestor?js_string?replace('\"', '$#-')}', '${candidatoSolicitacao.colaboradorNome}')"/>
			</@display.column>
			<@display.column property="colaboradorNome" title="Colaborador" class="${classe}"/>
			<@display.column property="solicitacao.descricao" title="Solicitação" class="${classe}"/>
			<@display.column property="solicitacao.faixaSalarial.nomeDeCargoEFaixa" title="Cargo/Faixa(Solicitação)" class="${classe}"/>
			<@display.column property="solicitacao.areaOrganizacional.nome" title="Área Organizacional(Solicitação)" class="${classe}"/>
			<@display.column property="solicitacao.estabelecimento.nome" title="Estabelecimento(Solicitação)" class="${classe}"/>
		</@display.table>
		
		<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>
	</#if>

	<div id="alterarStatusDiv" class="alterarStatusDiv">
		<@ww.form name="formAutorizarColabSolicitacaoPessoal" id="formAutorizarColabSolicitacaoPessoal" action="autorizarColabSolicitacaoPessoal.action" method="post">
			<@ww.select  label="Status"  name="candidatoSolicitacao.statusAutorizacaoGestor"  list="statusAutorizacao" id="statusAutorizacaoGestor" liClass="liLeft"/>
			<@ww.textfield disabled="true" readonly="true" label="Data" id="dataAutorizacaoGestorText" value="candidatoSolicitacao.dataAutorizacaoGestor" cssClass="mascaraData" cssStyle="background: #EBEBEB;"/>
			<@ww.textarea label="Observações" name="candidatoSolicitacao.obsAutorizacaoGestor" id="obsAutorizacaoGestor" />
			<@ww.hidden name="candidatoSolicitacao.dataAutorizacaoGestor" id="dataAutorizacaoGestor" value=""/>
			<@ww.hidden name="candidatoSolicitacao.id" id="candidatoSolicitacaoId" value=""/>
			<@ww.hidden name="candidatoSolicitacao.colaboradorNome" id="colaboradorNome" value=""/>
			<@ww.hidden name="colaboradorNomeBusca" id="colaboradorNomeBuscaForm" value=""/>
			<@ww.hidden name="solicitacaoDescricaoBusca" id="solicitacaoDescricaoBuscaForm" value=""/>
			<@ww.hidden name="statusBusca" id="statusBuscaForm" value=""/>
		</@ww.form>
	</div>
</body>
</html>