<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		#formDialog { display: none; width: 600px; }
		#formAssinaturasDialog { display: none; }
	</style>

	<script type='text/javascript'>
		function clonar(fichaMedicaId, titulo)
		{
			$('#fichaMedicaId').val(fichaMedicaId);
			$('#formDialog').dialog({ modal: true, width: 530, title: 'Clonar: ' + titulo });
		}
		
		function imprimirFichaMedica(questionarioId, questionarioTitulo)
		{
			$('#formAssinaturasDialog').dialog({	modal: true, 
													width: 320, 
													title: 'Imprimir ficha médica',
													position: { my: "left top", at: "right bottom", of: "#print" + questionarioId },
													open: function(event, ui) 
													{ 
														$('#questionarioTitulo').text(questionarioTitulo);
														$('#questionarioId').val(questionarioId);
													},
													close: function(event, ui) { $('#formModalAssinaturas input').val(''); }
											    });
		}
	</script>

	<title>Modelos de Fichas Médicas</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@display.table name="fichaMedicas" id="fichaMedica" class="dados">
		<@display.column title="Ações" style="width:135px">

			<#if fichaMedica.questionario.aplicarPorAspecto>
				<a href="javascript: executeLink('../../pesquisa/questionario/prepareAplicarByAspecto.action?questionario.id=${fichaMedica.questionario.id}&preview=true');"><img border="0" title="Visualizar Questionário de Ficha Médica" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
			<#else>
				<a href="javascript: executeLink('../../pesquisa/questionario/prepareAplicar.action?questionario.id=${fichaMedica.questionario.id}&preview=true');"><img border="0" title="Visualizar Questionário de Ficha Médica" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
			</#if>

			<a href="javascript: executeLink('prepareUpdate.action?fichaMedica.id=${fichaMedica.id}&quantidadeDeResposta=${fichaMedica.questionario.quantidadeDeResposta}');"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>

			<#if 0 < fichaMedica.questionario.quantidadeDeResposta>
				<img border="0" title="Questionário - Já existe resposta para este questionário, não é permitido editar" src="<@ww.url includeParams="none" value="/imgs/question.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
				<img border="0" title="Aspectos - Já existe resposta para este questionário, não é permitido editar" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			<#else>
				<a href="javascript: executeLink('../../pesquisa/pergunta/list.action?questionario.id=${fichaMedica.questionario.id}');"><img border="0" title="Questionário da ficha médica" src="<@ww.url includeParams="none" value="/imgs/question.gif"/>"></a>
				<a href="javascript: executeLink('../../pesquisa/aspecto/list.action?questionario.id=${fichaMedica.questionario.id}');"><img border="0" title="Aspectos da ficha médica" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>"></a>
			</#if>

			<a href="javascript:;" onclick="clonar(${fichaMedica.id}, '${fichaMedica.questionario.titulo}')"><img border="0" title="Clonar" src="<@ww.url includeParams="none" value="/imgs/clonar.gif"/>"></a>
			
			<#if 0 < fichaMedica.questionario.quantidadeDeResposta>
				<img border="0" title="Já existe resposta para este questionário, não é permitido excluir." src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			<#else>
				<a href="javascript:newConfirm('Confirma exclusão?', function(){executeLink('delete.action?fichaMedica.id=${fichaMedica.id}&fichaMedica.questionario.empresa.id=${fichaMedica.questionario.empresa.id}&page=${page}');});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
			</#if>
			
			<a href="javascript:;" onclick="imprimirFichaMedica(${fichaMedica.questionario.id}, '${fichaMedica.questionario.titulo}')"><img border="0" id="print${fichaMedica.questionario.id}" title="Imprimir ficha" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
		</@display.column>

		<@display.column property="questionario.titulo" title="Título" />
		<@display.column title="Ativa" style="width:30px">
			<#if fichaMedica.ativa>
				<img border="0" title="Sim" src="<@ww.url includeParams="none" value="/imgs/flag_green.gif"/>">
			<#else>
				<img border="0" title="Não" src="<@ww.url includeParams="none" value="/imgs/flag_red.gif"/>">
			</#if>
		</@display.column>

	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="javascript: executeLink('prepareInsert.action');" accesskey="I">
		</button>
	</div>
	
	<div id="formDialog">
		<@ww.form name="formModal" id="formModal" action="clonarFichaMedica.action" method="POST">
			<@frt.checkListBox label="Selecione as empresas para as quais deseja clonar esta ficha médica" name="empresasCheck" list="empresasCheckList" form="document.getElementById('formModal')" filtro="true"/>
			* Caso nenhuma empresa seja selecionada, a ficha médica será clonada apenas para a empresa <@authz.authentication operation="empresaNome"/><br>
			<@ww.hidden name="fichaMedica.id" id="fichaMedicaId"/>
			<button class="btnClonar" type="submit"></button>
		</@ww.form>
	</div>
	
	<div id="formAssinaturasDialog">
		<@ww.form name="formModalAssinaturas" id="formModalAssinaturas" action="imprimirFichaMedicaList.action" method="POST">
			<h4 id="questionarioTitulo"></h4>
			
			<@ww.hidden name="questionario.id" id="questionarioId"/>
			
			<@ww.textfield label="Assinatura 1" name="assinatura1" maxLength="30" size="30" />
			<@ww.textfield label="Assinatura 2" name="assinatura2" maxLength="30" size="30" />
			<@ww.textfield label="Assinatura 3" name="assinatura3" maxLength="30" size="30" />
			
			<button class="btnImprimirPdf" type="submit"></button>
		</@ww.form>
	</div>
</body>
</html>