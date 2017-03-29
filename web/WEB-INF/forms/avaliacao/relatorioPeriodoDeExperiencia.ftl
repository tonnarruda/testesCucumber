<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>

<head><@ww.head/>
	<title>Acompanhamento do Período de Experiência</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<#include "../ftl/mascarasImports.ftl" />

	<script type='text/javascript'>
		$(function() 
		{
			$('#wwctrl_periodoCheck * span').eq(0).removeAttr('onclick').css('color', '#6E7B8B').css('cursor', 'default');
			validaQtd();
		});
		function validaQtd()
		{
		    if($("input[name='periodoCheck']:checked").size() >= 4)
		        $("input[name='periodoCheck']").not(':checked').attr('disabled','disabled').parent().css('color', '#DEDEDE');
		    else
		        $("input[name='periodoCheck']").removeAttr('disabled').parent().css('color', '#5C5C5A');
		}
		
		function submeterAction(action)
		{
			$('form[name=form]').attr('action', action);
			$('#relatorioDialog').dialog('close');
			
			return validaFormulario('form', new Array('periodoIni', 'periodoFim','@periodoCheck'), new Array('periodoIni', 'periodoFim'), false , '${urlImgs}');
		}
		
		function relatorioPdf()
		{
			$('#exibirTituloAvaliacao').val(false);
			
			return submeterAction('imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia.action');		
		}
		
		function relatorioXls(){
				$("#relatorioDialog").dialog({title: 'Relatório XLS.', modal: true, resizable: false, height: 170, width: 470,
							buttons: [
							    {
							        text: "Gerar",
							        click: function() {
							        			$('#exibirTituloAvaliacao').val($("input[name=exibirTituloAvaliacaoTemp]:checked").val()); 
							        			return submeterAction('imprimeRelatorioPeriodoDeAcompanhamentoDeExperienciaXls.action'); 
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
	<#assign periodoIniFormatado = "${periodoIni?date}" />
	<#assign periodoFimFormatado = "${periodoFim?date}" />
</head>

<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="form" action="imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia.action" method="POST">

		<@ww.datepicker label="Período" required="true" name="periodoIni" id="periodoIni" cssClass="mascaraData validaDataIni" liClass="liLeft" after="a" value="${periodoIniFormatado}"/>
		<@ww.datepicker label="" name="periodoFim" id="periodoFim" cssClass="mascaraData validaDataFim" value="${periodoFimFormatado}"/><br/>
		<@frt.checkListBox label="Período de Acompanhamento (máx. 4 opções) *" name="periodoCheck" id="periodoCheck" list="periodoCheckList" onClick="validaQtd();" filtro="true"/>						
		<@frt.checkListBox label="Estabelecimento" name="estabelecimentoCheck" id="estabelecimentoCheck" list="estabelecimentoCheckList" filtro="true"/>						
		<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>
		<@ww.hidden id="exibirTituloAvaliacao" name="exibirTituloAvaliacao" value="false" />
			
		<div id="relatorioDialog" style="display:none;">
			Exibir Dias de Acompanhamento do Período de Experiência em: <br \><br \>
			<input name="exibirTituloAvaliacaoTemp" type="radio" value="false" checked style="padding: 0px 20px"/><label>Colunas (Padrão)</label></br>
			<input name="exibirTituloAvaliacaoTemp" type="radio" value="true"/><label>Linhas (Exibe Avaliação e Avaliador)</label></br></br>
		</div>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="relatorioPdf()" class="btnRelatorio" ></button>
		<button onclick="relatorioXls();" class="btnRelatorioExportar"></button>
	</div>
</body>

</html>