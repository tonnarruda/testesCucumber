<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/formModal.css"/>');
		
		#box
		{
			top: 200px !important;
			left: 30% !important;
			width: 400px !important;
			height: 300px !important;
		}
		
		#boxtitle
		{
			width:496px !important;
		}
		
		.fieldsetPadrao
		{
			width: 300px !important;
		}
		
	</style>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formModal.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>'></script>
	<script language="javascript">
	
		function habilitarCampo(check, campo)
		{
			if(check.checked)
				$('#' + campo).removeAttr('disabled');
			else
				$('#' + campo).attr("disabled", true);
		}
	
	</script>
	<title>Ficha de Investigação de Acidente(CAT)</title>

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

		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" />
		<@frt.checkListBox name="areasCheck" label="Área Organizacional" list="areasCheckList"/>

		<@ww.textfield label="Colaborador" name="nomeBusca" id="nomeBusca" cssStyle="width: 260px;"/>

		<input type="submit" value="" class="btnPesquisar grayBGE" />
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br/>

	<@display.table name="cats" id="cat" pagesize=20 class="dados">
		<@display.column title="Açőes" class="acao">
			<a href="prepareUpdate.action?cat.id=${cat.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?cat.id=${cat.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			<a href="#" onclick="openbox('Configurar Impressão', '');"><img border="0" title="Imprimir Ficha de Investigação de Acidente" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			<!-- <a href="imprimirFichaInvestigacaoAcidente.action?cat.id=${cat.id}"><img border="0" title="Imprimir Ficha de Investigação de Acidente" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a> -->
		 </@display.column>
		<@display.column property="colaborador.nome" title="Colaborador" style="width:280px;"/>
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:70px;"/>
		<@display.column property="numeroCat" title="Número" style="width:85px;"/>
		<@display.column property="observacao" title="Observação" style="width:280px;"/>
	</@display.table>

	<div id="box">
		<span id="boxtitle"></span>
		<@ww.form name="form" id="form" action="imprimirCurriculo.action" method="POST">
			<li>
				<fieldset class="fieldsetPadrao">
					<ul>
						<@ww.checkbox label="Campo 1" name="exibirAssinatura1" labelPosition="left" onclick="habilitarCampo(this, 'ass1');"/>
						<@ww.textfield label="Assinatura 1" id="ass1" name="assinatura1" maxLength="50" cssStyle="width: 200px;"/>
						
						<@ww.checkbox label="Campo 2" name="exibirAssinatura2" labelPosition="left" onclick="habilitarCampo(this, 'ass2');"/>
						<@ww.textfield label="Assinatura 2" id="ass2" name="assinatura2" maxLength="50" cssStyle="width: 200px;"/>
						
						<@ww.checkbox label="Campo 3" name="exibirAssinatura3" labelPosition="left" onclick="habilitarCampo(this, 'ass3');"/>
						<@ww.textfield label="Assinatura 3" id="ass3" name="assinatura3" maxLength="50" cssStyle="width: 200px;"/>
						
						<@ww.checkbox label="Campo 4" name="exibirAssinatura4" labelPosition="left" onclick="habilitarCampo(this, 'ass3');"/>
						<@ww.textfield label="Assinatura 4" id="ass3" name="assinatura4" maxLength="50" cssStyle="width: 200px;"/>
					</ul>
				</fieldset>
			</li>
			
			<@ww.hidden name="configuracaoImpressaoCurriculo.id" />
			<@ww.hidden name="candidato.id" />
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="window.location='imprimirFichaInvestigacaoAcidente.action?cat.id=${cat.id}'" class="btnImprimirPdf"></button>
			<button onclick="closebox();" class="btnCancelar"></button>
		</div>
	</div>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
	</div>
</body>
</html>