<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css"/>');
			.ordem { width: 40px; text-align: right; }
			.dados tbody tr { cursor: ns-resize; }
			.dica { background-color: #eee; padding: 5px; }
		</style>
		
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FasePcmatDWR.js"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
		
		<script>
			$(function() {
				$('.dados tbody').sortable({
					stop: function( event, ui ) 
					{
						zebra();
						var fases = [];
						$('.dados input:hidden').each(function() { fases.push(this.value); });
						
						FasePcmatDWR.reordenar(fases);
					}
				});
			});
			
			function zebra()
			{
				$('.dados tr:odd').removeClass('even').addClass('odd');
				$('.dados tr:even').removeClass('odd').addClass('even');
			} 
		</script>
		
		<title>Fases</title>
		
		<#assign validarCampos="return validaFormulario('form', new Array('fase'))"/>
	</head>
	<body>
		<@ww.actionmessage />
		<@ww.actionerror />
		
		<#include "pcmatLinks.ftl"/>
		
		<@ww.form name="form" action="insert.action" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="pcmat.id"/>
			<@ww.hidden name="fasePcmat.pcmat.id" value="${pcmat.id}"/>
			<@ww.hidden name="fasePcmat.ordem" value="0"/>
			<@ww.token/>

			<@ww.select label="Adicionar fase" name="fasePcmat.fase.id" id="fase" list="fases" listKey="id" listValue="descricao" headerValue="Selecione" headerKey="-1" required="true" liClass="liLeft"/>&nbsp;
			<button type="button" onclick="${validarCampos};" class="btnGravar"></button>
		</@ww.form>
	
		<br />
		
		<div class="dica"><strong>Dica:</strong> Clique nas fases abaixo e arraste-as para ordená-las</div>
		<@display.table name="fasesPcmat" id="fasePcmat" class="dados">
			<@display.column title="Ações" class="acao">
				<a href="../riscoFasePcmat/list.action?fasePcmat.id=${fasePcmat.id}"><img border="0" title="Riscos e medidas de segurança" src="<@ww.url value="/imgs/form2.gif"/>"></a>
				<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?fasePcmat.id=${fasePcmat.id}&pcmat.id=${pcmat.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
				<@ww.hidden name="fasePcmatId" value="${fasePcmat.id}"/>
			</@display.column>
			<@display.column title="Fase" property="fase.descricao"/>
		</@display.table>
		
		<div class="buttonGroup">
			<button class="btnInserir" onclick="window.location='prepareInsert.action?fasePcmat.pcmat.id=${pcmat.id}'"></button>
		</div>
	</body>
</html>
