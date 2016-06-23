<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
	<@ww.head/>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>"></script>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<script>
		var processandoTime = "";
	
		(function($) {
			processandoTime = getCookieProcessando();		
		});
	
		function avisoAoImprimirOSPelaPrimeiraVez(colaboradorNome, funcaoNome, data, ordemDeServicoId)
		{
			msg = 'Após a impressão não será permidito editar ou excluír esta ordem de serviço.'
			$('<div>'+ msg +'</div>').dialog({title: 'Alerta!',
													modal: true, 
													height: 150,
													width: 450,
													buttons: 
															[
															    {
															        text: "Imprimir",
															        click: function() {
																		window.location='imprimir.action?ordemDeServico.id='+ordemDeServicoId;	
															        	$(this).dialog("close");	
															        	setTimeout("checkProcessandoTime()",500);
															        }
															    },
															    {
															        text: "Cancelar",
															        click: function() { $(this).dialog("close"); }
															    }
															],
													});
					
		};
		
		function checkProcessandoTime(){
			if(processandoTime != getCookieProcessando()){
				$('.processando').remove();
				window.location.reload();
			}
			else
				setTimeout("checkProcessandoTime()",500);
		};
		
		function getCookieProcessando() {
		    var nameEQ = "processando=";
		    var ca = document.cookie.split(';');
		    for(var i=0;i < ca.length;i++) {
		        var c = ca[i];
				while (c.charAt(0)==' ') 
					c = c.substring(1,c.length);
				if (c.indexOf(nameEQ) == 0) 
					return c.substring(nameEQ.length,c.length);
		    }
		    return
		}
	</script>

	<title>Ordens de Serviço - ${colaborador.nome}</title>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@display.table name="ordensDeServico" id="ordemDeServico" class="dados">
		<@display.column title="Ações" class="acao" style="width:95px";>
			<#if ordemDeServico.impressa>
				<@frt.link href="visualizar.action?ordemDeServico.id=${ordemDeServico.id}" id="editar${ordemDeServico.id}" imgTitle="Visualizar" imgName="olho.jpg"/>
				<@frt.link href="#" imgTitle="Não é permitida a exclusão pois a ordem de serviço já foi impressa." imgName="delete.gif" disabled=true/>
				<@frt.link href="#" imgTitle="Não é permitida a edição pois a ordem de serviço já foi impressa." imgName="edit.gif" disabled=true />
				<@frt.link href="imprimir.action?ordemDeServico.id=${ordemDeServico.id}" imgTitle="Imprimir" imgName="printer.gif"/>
			<#else>
				<@frt.link href="visualizar.action?ordemDeServico.id=${ordemDeServico.id}" id="editar${ordemDeServico.id}" imgTitle="Visualização permitida somente após a primeira impressão da ordem de serviço" imgName="olho.jpg"/>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?ordemDeServico.id=${ordemDeServico.id}&colaborador.id=${colaborador.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				<@frt.link href="prepareUpdate.action?ordemDeServico.id=${ordemDeServico.id}" id="editar${ordemDeServico.id}" imgTitle="Editar" imgName="edit.gif"/>
				<@frt.link href="javascript:avisoAoImprimirOSPelaPrimeiraVez('${ordemDeServico.nomeColaborador}', '${ordemDeServico.nomeFuncao}', '${ordemDeServico.dataFormatada}', '${ordemDeServico.id}')" imgTitle="Imprimir" imgName="printer.gif"/>
			</#if>
		</@display.column>
		<@display.column property="revisao" title="Nº da Revisão"/>
		<@display.column property="dataFormatada" title="Data"/>
		<@display.column property="nomeFuncao" title="Função"/>

	</@display.table>

	<@ww.hidden id="pagina" name="page"/>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>
	
	<div class="buttonGroup">
		<#if (ordemDeServicoAtual?exists && ordemDeServicoAtual.impressa) || !ordemDeServicoAtual?exists> 
			<button class="btnInserir" onclick="window.location='prepareInsert.action?colaborador.id=${colaborador.id}'"></button>
		<#else>
			<button class="btnInserir" disabled="disabled" style="opacity: 0.4" title="Não é posspivel inserir uma nova OS, pois existe uma OS em aberto e não impressa"></button>
		</#if>
		
		<button onclick="window.location='listGerenciamentoOS.action'" class="btnVoltar"></button>
	</div>

</body>
</html>