<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<#if faixaSalarialAux.id?exists>
		<title>Editar Faixa Salarial</title>
		<#assign formAction="update.action"/>
		<#assign edicao=true/>
	<#else>
		<title>Inserir Faixa Salarial</title>
		<#assign formAction="insert.action"/>
		<#assign edicao=false/>
	</#if>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/autoCompleteFortes.js?version=${versao}"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/fortes.js?version=${versao}"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/functions.js?version=${versao}"/>'></script>
	<style type="text/css">
	    @import url('<@ww.url includeParams="none" value="/css/fortes.css?version=${versao}"/>');
	    @import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
	    
	    #wwgrp_descricaoCBO
	    {
			float: left;
	    	background-color: #E9E9E9;
			width: 420px;
			padding-left: 4px;
		}
		
		
    </style>
    
	<script type="text/javascript">
		function validaForm(indice, edicao)
		{
			<#if empresaEstaIntegradaEAderiuAoESocial>
				return validaFormulario('form', new Array('nome'), null);
			<#elseif edicao>
				return validaFormulario('form', new Array('nome', 'codigoCBO'), null);
			<#else>
				if(document.getElementById('tipo').value == indice)
				{
					return validaFormulario('form', new Array('nome','data','indice','quantidade','codigoCBO'), new Array('data'));
				}
				else
				{
					return validaFormulario('form', new Array('nome','data','valor', 'codigoCBO'), new Array('data', 'valor'));
				}
			</#if>
		}
		
		$(function() {
			var $imgs = $('.acao a img[title=Excluir]');
			if ($imgs.length==1)
			    $imgs.addClass('disabledImg').
			    	attr('title', 'Não é possível excluir o último histórico.').
			    	parent().removeAttr('onclick'); 
		});
		
		$(document).ready(function() {
			<#if !empresaEstaIntegradaEAderiuAoESocial>
				var urlFind = "<@ww.url includeParams="none" value="/geral/codigoCBO/find.action"/>";
				
				$("#descricaoCBO").autocomplete({
					source: ajaxData(urlFind),				 
					minLength: 2,
					select: function( event, ui ) { 
						$("#codigoCBO").val(ui.item.id);
					}
				}).data( "autocomplete" )._renderItem = renderData;
	
				$('#descricaoCBO').focus(function() {
				    $(this).select();
				});
			</#if>
		});
			
	</script>

	<#include "faixaSalarialHistoricoCadastroHeadInclude.ftl" />
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" id="form" action="${formAction}" onsubmit="validaForm(${tipoAplicacaoIndice.getIndice()});" validate="true" method="POST">
		<b>Cargo:</b> ${cargoAux.nome}<br><br>

		<@ww.textfield label="Faixa" name="faixaSalarialAux.nome" id="nome" cssStyle="width: 220px;"  maxLength="30" required="true"/>
		<#if integradoAC>
			<@ww.textfield label="Descrição no Fortes Pessoal" name="faixaSalarialAux.nomeACPessoal" id="nomeACPessoal" cssStyle="width: 220px; margin-top: 1px"  maxLength="30" required="true" disabled="${empresaEstaIntegradaEAderiuAoESocial?string}"/>
			<#if empresaEstaIntegradaEAderiuAoESocial>
				<img border="0" title="Em virtude de adequações ao eSocial, não é possível editar no Fortes RH." src="<@ww.url value="/imgs/esocial.png"/>" style="margin-top: -47px; float: left; margin-left: 177px; width: 27px; height: 27px;">
			</#if>
		<#else>
			<@ww.hidden	name="faixaSalarialAux.nomeACPessoal" />
		</#if>
		
		<@ww.textfield label="Cód. CBO" name="faixaSalarialAux.codigoCbo" id="codigoCBO" onkeypress="return(somenteNumeros(event,''));" cssStyle="margin-top: 1px" size="6"  maxLength="6" liClass="liLeft" required="true" disabled="${empresaEstaIntegradaEAderiuAoESocial?string}"/>
		<#if empresaEstaIntegradaEAderiuAoESocial>
			<img border="0" title="Em virtude de adequações ao eSocial, não é possível editar no Fortes RH." src="<@ww.url value="/imgs/esocial.png"/>" style="margin-top: -8px; float: left; margin-left: -4px; width: 27px; height: 27px;">
		</#if>
		
		<#if !empresaEstaIntegradaEAderiuAoESocial>
			<@ww.textfield label="Busca CBO (Código ou Descrição)" name="descricaoCBO" id="descricaoCBO" cssStyle="width: 414px;"/>
		</#if>
		<div style="clear:both"></div>
        <@frt.checkListBox label="Certificações" name="certificacaosCheck" list="certificacaosCheckList" filtro="true"/>

		<#if !edicao>
			<b><br>Primeiro Histórico da Faixa Salarial:</b><br>
			<#include "faixaSalarialHistoricoCadastroInclude.ftl" />
		</#if>

		<@ww.hidden	name="faixaSalarialAux.cargo.id" />
		<@ww.hidden name="faixaSalarialAux.codigoAC" />
		<@ww.hidden name="faixaSalarialAux.id" />
		<@ww.hidden name="cargoAux.id" />
	</@ww.form>
	<div class="buttonGroup">
		<button onclick="validaForm(${tipoAplicacaoIndice.getIndice()});" class="btnGravar"></button>

	<#if edicao>
		</div>
		<br>
		<@display.table name="faixaSalarialHistoricoVOs" id="faixaSalarialHistoricoVO" class="dados" style="width: 800px;">
			<@display.column title="Ações" class="acao">
				<#if (faixaSalarialHistoricoVO.editavel)>
					<a href="../faixaSalarialHistorico/prepareUpdate.action?faixaSalarialHistorico.id=${faixaSalarialHistoricoVO.id}&faixaSalarialAux.id=${faixaSalarialAux.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
					<#if empresaEstaIntegradaEAderiuAoESocial && faixaSalarialHistoricoVO.status != 3>
						<img border="0" title="Devido as adequações ao eSocial, não é possível excluir no Fortes RH o histórico de uma faixa salarial." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.5;filter:alpha(opacity=50);">
					<#else>
						<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='../faixaSalarialHistorico/delete.action?faixaSalarialHistorico.id=${faixaSalarialHistoricoVO.id}&faixaSalarialAux.id=${faixaSalarialAux.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url value="/imgs/delete.gif"/>"></a>
					</#if>	
					
				<#else>
					<span title="Essa evolução do histórico da faixa foi devido ao reajuste do índice">(Índice)</span>
				</#if>
			</@display.column>
			<@display.column title="A partir de" style="width: 70px;text-align: center;">
				${faixaSalarialHistoricoVO.dataFaixa?date}
			</@display.column>
			<@display.column title="Tipo" style="width: 600px;">
				Por ${faixaSalarialHistoricoVO.nomeIndice}
			</@display.column>
			<@display.column title="Valor" style="width: 80px;text-align: right;">
				<#if faixaSalarialHistoricoVO.valorFaixa?exists>
					${faixaSalarialHistoricoVO.valorFaixa?string(",##0.00")}
				<#else>
					-
				</#if>
			</@display.column>
			<#if integradoAC>
				<@display.column title="Status no AC" style="width: 50px;text-align: center;">
					<img border="0" title="${statusRetornoAC.getDescricao(faixaSalarialHistoricoVO.status)}"
					src="<@ww.url includeParams="none" value="/imgs/"/>${statusRetornoAC.getImg(faixaSalarialHistoricoVO.status)}">
				</@display.column>
			</#if>
		</@display.table>

		<div class="buttonGroup">
			<button onclick="window.location='../faixaSalarialHistorico/prepareInsert.action?faixaSalarialAux.id=${faixaSalarialAux.id}'" class="btnInserir" accesskey="I"></button>
	</#if>

		<button onclick="window.location='list.action?cargo.id=${cargoAux.id}'" class="btnCancelar"></button>
	</div>

	<script type="text/javascript">
		<#if !edicao>
			alteraTipo(document.getElementById('tipo').value, ${tipoAplicacaoIndice.getIndice()});
			calculaValor();
		</#if>
	</script>
</body>
</html>