<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		
		#box, #selectRelatorio
		{
			display: none;
		}
		
		.fieldsetPadrao
		{
			width: 300px !important;
		}
		
		.buttonGroup {
		    width: 300px !important;
		}
	</style>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.cookie.js"/>'></script>
	<script language="javascript">
	
		function habilitarCampo(check, campo)
		{
			if(check.checked)
				$('#' + campo).removeAttr('disabled');
			else
				$('#' + campo).attr("disabled", true);
		}
	
		function imprimirRelatorio(catId) {
			$('#selectRelatorio').dialog({	title: 'Imprimir relatório',
			modal: true, 
			height: 145,
			width: 200,
				buttons: [ 	{ text: "Imprimir", click: function() {
				            if ( $('#tipoRelatorio').val() == 'S' ) {
				            	popupConfigRelatorio(catId);
				            } else {
				            	$("#formRelatorio").find("#catId").val(catId);
				            	$("#formRelatorio").submit();
				            }
							$(this).dialog("close");
						} },
			    		{ text: "Cancelar", click: function() { $(this).dialog("close"); } } ] 
			});
		}
	
		function popupConfigRelatorio(catId)
		{
			$('#formRelatorioSimples').find('#catId').val(catId);
		
			$('#box').dialog({ modal: true, width: 360 });
		}
		
		function gerarRelatorio()
		{
			$('#formRelatorioSimples input').each(function(i, item) {
				if (item.type == 'checkbox')
					$.cookie(item.id, item.checked);
				else
					$.cookie(item.id, item.value);
			});
			
			$('#formRelatorioSimples').submit();

			$('#box').dialog('close');
		}
		
		$(function() {
			$('#formRelatorioSimples input').each(function(i, item) {
				if ($.cookie(item.id))
				{
					if (item.type == 'checkbox')
					{
				 		$("#" + item.id).attr('checked', $.cookie(item.id) == "true");
			 			habilitarCampo(item, item.id.replace('exibirAssinatura','ass'));
			 		}
				 	else
				 		$("#" + item.id).val($.cookie(item.id));
				}
			});
		});
	</script>
	<title>Ficha de Investigação de Acidente (CAT)</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#assign validarCampos="return validaFormulario('form', null, new Array('inicio','fim'), true)"/>

	<#include "../ftl/showFilterImports.ftl" />
	<#if inicio?exists >
		<#assign dataInicio = inicio?date/>
	<#else>
		<#assign dataInicio = ""/>
	</#if>
	<#if fim?exists>
		<#assign dataFim = fim?date/>
	<#else>
		<#assign dataFim = ""/>
	</#if>
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="list.action" onsubmit="${validarCampos}" method="POST">
		Período:<br>
		<@ww.datepicker name="inicio" id="inicio"  value="${dataInicio}" liClass="liLeft" cssClass="mascaraData"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="fim" id="fim" value="${dataFim}" cssClass="mascaraData" />

		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox name="areasCheck" label="Área Organizacional" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>

		<@ww.textfield label="Colaborador" name="nomeBusca" id="nomeBusca" cssStyle="width: 260px;"/>

		<input type="submit" value="" class="btnPesquisar grayBGE" />
		
		<@ww.hidden name="exibirAssinatura1"/>
		<@ww.hidden name="exibirAssinatura2"/>
		<@ww.hidden name="exibirAssinatura3"/>
		<@ww.hidden name="exibirAssinatura4"/>
		<@ww.hidden name="assinatura1"/>
		<@ww.hidden name="assinatura2"/>
		<@ww.hidden name="assinatura3"/>
		<@ww.hidden name="assinatura4"/>
		
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br/>

	<@display.table name="cats" id="cat" pagesize=20 class="dados">
		<@display.column title="Açőes" class="acao">
			<a href="prepareUpdate.action?cat.id=${cat.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?cat.id=${cat.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			<a href="javascript:;" onclick="imprimirRelatorio(${cat.id})"><img border="0" title="Imprimir Ficha de Investigação de Acidente" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
		 </@display.column>
		<@display.column property="colaborador.nome" title="Colaborador" style="width:280px;"/>
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:70px;"/>
		<@display.column property="numeroCat" title="Número" style="width:85px;"/>
		<@display.column property="observacao" title="Descrição do Acidente" style="width:280px;"/>
	</@display.table>

	<div id="box" title="Configurar Informações para Impressão">
		<@ww.form name="formRelatorioSimples" id="formRelatorioSimples" action="imprimirFichaInvestigacaoAcidente.action" method="POST">
			<@ww.hidden name="cat.id" id="catId"/>
			<li>
				<fieldset class="fieldsetPadrao">
					<ul>
						<@ww.checkbox label="Campo de Assinatura 1" id="exibirAssinatura1" name="exibirAssinatura1" labelPosition="left" onclick="habilitarCampo(this, 'ass1');"/>
						<@ww.textfield label="Assinatura 1" id="ass1" name="assinatura1" maxLength="25" cssStyle="width: 180px;"/>
						
						<@ww.checkbox label="Campo de Assinatura 2" id="exibirAssinatura2" name="exibirAssinatura2" labelPosition="left" onclick="habilitarCampo(this, 'ass2');"/>
						<@ww.textfield label="Assinatura 2" id="ass2" name="assinatura2" maxLength="25" cssStyle="width: 180px;"/>
						
						<@ww.checkbox label="Campo de Assinatura 3" id="exibirAssinatura3" name="exibirAssinatura3" labelPosition="left" onclick="habilitarCampo(this, 'ass3');"/>
						<@ww.textfield label="Assinatura 3" id="ass3" name="assinatura3" maxLength="25" cssStyle="width: 180px;"/>
						
						<@ww.checkbox label="Campo de Assinatura 4" id="exibirAssinatura4" name="exibirAssinatura4" labelPosition="left" onclick="habilitarCampo(this, 'ass3');"/>
						<@ww.textfield label="Assinatura 4" id="ass4" name="assinatura4" maxLength="25" cssStyle="width: 180px;"/>
						
						<@ww.checkbox label="Foto do Acidente" id="exibirFotoAcidente" name="exibirFotoAcidente" labelPosition="left" />
					</ul>
				</fieldset>
			</li>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="gerarRelatorio();" class="btnImprimirPdf"></button>
			<button onclick="$('#box').dialog('close');" class="btnCancelar"></button>
		</div>
	</div>
	
	<div id="selectRelatorio" title="Configurar Informações para Impressão">
		<@ww.form name="formRelatorio" id="formRelatorio" action="imprimirCat.action" method="POST">
			<@ww.hidden name="cat.id" id="catId"/>
			<@ww.select label="Tipo de relatório" name="tipoRelatorio" id="tipoRelatorio" list=r"#{'S':'Simples','D':'Detalhado'}"/>
		</@ww.form>
	</div>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
	</div>
</body>
</html>