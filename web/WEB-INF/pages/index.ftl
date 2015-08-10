<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign ww=JspTaglibs["/WEB-INF/tlds/webwork.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] /> 
<html>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="Content-Language" content="pt-br" />
<meta http-equiv="Cache-Control" content="no-cache, no-store" />
<meta http-equiv="Pragma" content="no-cache, no-store" />
<meta http-equiv="Expires" content="0" />
<title>RH</title>
<head>
	<style>
		.dados a { text-decoration: none; font-size: 11px !important; font-family: Arial, Helvetica, sans-serif !important; }
		.dados td { font-size: 11px !important; }
		.tituloMensagem { width: 390px; white-space: nowrap; overflow: hidden; }
		.remetenteHora { color: #069; }
		
		/* cria link sobre a parte "FECHAR" do splash do Chat */ 
		#fecharSplash {
			 color: red;
			 font-weight: bold;	  
			 cursor: pointer; 
			 border: none; 
			 text-decoration: none; 
		}
		
		#atualizacao { display: none; }
		
		.column { width: 50%; float: left; min-height: 50px; }
		.left .portlet { margin: 0 0.5em 1em 0; }
		.right .portlet { margin: 0 0 1em 0.5em; }
		.portlet-header { margin-bottom: 0.3em; padding: 5px; }
		.portlet-header .ui-icon { float: right; cursor: pointer; }
		.portlet-content { padding: 0.4em; height: 180px; overflow: auto;}
		.ui-sortable-placeholder { background: transparent; border: 1px dotted black; visibility: visible !important; height: 220px !important; }
		.ui-sortable-placeholder * { visibility: hidden; }
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UtilDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UsuarioMensagemDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UsuarioDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery.cookie.js"/>'></script>
	
	<script type='text/javascript'>
		$(function () {
			
			$( "#splash" ).dialog({
				autoOpen: false,
				modal: true,
				zIndex: 99999,
				minWidth: 430,
				create: function (event, ui) {  },
			  	close: function() {
			  		if ( $('#naoExibirMsg').is(':checked') ) 
			  			$.cookie("pgInicialSplashMarca", false, { expires: 30 }); 
			  	}
			});
			
			if($.cookie("pgInicialSplashMarca") != 'false')
			{
				$("#splash").dialog("open");
			}
			
			
			$( ".column" ).sortable({
				connectWith: ".column",
				handle: ".portlet-header",
				stop: gravarPosicoes
			});
			
			$( ".portlet" ).addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
				.find( ".portlet-header" )
					.addClass( "ui-widget-header ui-corner-all" )
					.prepend( "<span class='ui-icon ui-icon-minusthick'></span>")
					.end()
				.find( ".portlet-content" );
	
			$( ".portlet-header .ui-icon" ).click(function() {
				$( this ).toggleClass( "ui-icon-minusthick" ).toggleClass( "ui-icon-plusthick" );
				$( this ).parents( ".portlet:first" ).find( ".portlet-content" ).toggle();
				gravarPosicoes();
			});
			
			<#list configuracaoCaixasMensagens.caixasMinimizadas as tipo>
				$( '.portlet-header-${tipo} .ui-icon' ).toggleClass( "ui-icon-minusthick" ).toggleClass( "ui-icon-plusthick" );
				$( '.portlet-header-${tipo} .ui-icon' ).parents( ".portlet:first" ).find( ".portlet-content" ).toggle();
			</#list>
	
			$( ".column" ).disableSelection();
			
			<@authz.authorize ifAllGranted="ROLE_VISUALIZAR_ATUALIZACAO_SISTEMA">
				UtilDWR.findUltimaVersaoPortal({ 
						callback: function(retorno) {
							var resposta = jQuery.parseJSON(retorno);
							if (resposta.sucesso == '1')
							{
								$('#versaoPortal').text(resposta.versao);
								$('#atualizacao').show();
							}
						},
						errorHandler: function() {  }
				});
			</@authz.authorize>
		});
		
		function marcarMensagemLida(usuarioMensagemId)
		{
			DWREngine.setErrorHandler(errorUsuarioMensagem);
			UsuarioMensagemDWR.gravarMensagemLida(retorno, usuarioMensagemId, ${empresaId}, true);
		}
	
		function retorno()
		{
			window.opener.location.reload();
		}
	
		function errorUsuarioMensagem() 
		{
		
		}
		
		function gravarPosicoes()
		{
			var esquerda = [];
			var direita = [];
			var minimizadas = [];
			
			$(".left input[name='caixa']").each(function() {
				esquerda.push( $(this).val() );
			});

			$(".right input[name='caixa']").each(function() {
				direita.push( $(this).val() );
			});

			$(".portlet").has(".ui-icon-plusthick").find("[name='caixa']").each(function() {
				minimizadas.push( $(this).val() );
			});

			UsuarioDWR.gravarLayoutCaixasMensagens(<@authz.authentication operation="id"/>, esquerda, direita, minimizadas, function() {  });
		}
	</script>

</head>
<body>
	<#assign usuarioId><@authz.authentication operation="id"/></#assign>

	<@ww.actionmessage />
	<@ww.actionerror />	
	
	<div id="atualizacao" class="warning">
		<div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().remove();">x</a></div>
		<ul>
			<li>
				Versão <strong><span id="versaoPortal"></span></strong> disponível. 
				<a title="Acessar o Portal do Cliente" href="http://www.grupofortes.com.br/portaldocliente" target="_blank">Clique aqui</a> para acessar o Portal do Cliente e realizar o download.
			</li>
		</ul>
	</div>
	
	<#if !bancoConsistente >
		<div id="bancoconsistente" class="warning">
			<div style="float:right;"><a title="Ocultar" href="javascript:;" onclick="$(this).parent().parent().remove();">x</a></div>
			<ul>
				<li>O banco de dados não está consistente. Entre em contato com o suporte para ajustá-lo.</li>
			</ul>
		</div>
	</#if>
	
	<#if avaliacaos?exists && 0 < avaliacaos?size>
		<div class="waDivTituloX">Aviso!</div>
		<div class="waDivFormularioX">
			<p>
				<strong>Caro cliente,</strong><br>
				Existe uma inconsistência no cadastro de Modelo de Avaliação, foi criado um novo vínculo entre o "Modelo de Avaliação" e "Períodos de Acompanhamento de Experiência".<br>
				Clique nos "Modelo de Avaliação" abaixo e edite o campo "Períodos de Acompanhamento de Experiência":<br><br>
				<#list avaliacaos as avaliacao>
					<a href="avaliacao/modelo/prepareUpdate.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${avaliacao.tipoModeloAvaliacao}&telaInicial=true">${avaliacao.titulo}</a><br>
				</#list>
			</p>
		</div>
		<br />
	</#if>
	
	<@authz.authorize ifAllGranted="ROLE_VISUALIZAR_MSG">
		<#if caixasMensagens?exists>
			<div class="caixas">
				<div class="column left">
					<#list configuracaoCaixasMensagens.caixasEsquerda as tipo>
						<#include "caixaMensagens.ftl"/>
					</#list>
				</div>

				<div class="column right">
					<#list configuracaoCaixasMensagens.caixasDireita as tipo>
						<#include "caixaMensagens.ftl"/>
					</#list>
				</div>
			</div>
		</#if>
	</@authz.authorize>
	
	<div style="clear:both;"></div>
	
	<@authz.authorize ifAllGranted="ROLE_VISUALIZAR_PENDENCIA_AC">
		<#if integradoAC && pendenciaACs?exists>
			<form action="removerMultiplasPendenciasAC.action" id="removerMultiplasPendenciasAC" method="post">
				<div class="portlet">
					<div class="portlet-header">Pendências com o AC Pessoal</div>
					<div class="portlet-content">
						<#if pendenciaACs?size < 1>
							<span>Nenhuma pendência</span>
						<#else>
							<@display.table name="pendenciaACs" id="pendenciaAC" class="dados portlet" defaultsort=2 sort="list">
								<#if pendenciaAC.msg?exists && pendenciaAC.action?exists && pendenciaAC.role?exists>						
								
									<#assign opacityDisabled = true />
									<@authz.authorize ifAllGranted="${pendenciaAC.role}">
										<#if pendenciaAC.statusCancelado>
											<#assign opacityDisabled = false />
										<#else>
											<#assign opacityDisabled = !usuario.usuarioFortes />
										</#if>
									</@authz.authorize>
									
									<#if usuario.usuarioFortes>
										<@display.column title="<input type='checkbox' id='md' onclick='atualizaChecks(\"checkPendenciasAc\", this.checked);' />" style="width: 30px; text-align: center;">
											<input type="checkbox" class="checkPendenciasAc" value="${pendenciaAC.action}" name="pendenciasAcsSerRemovida"/>
										</@display.column>
									</#if>
									
									<@display.column title="Ações" style="text-align:center; width: 80px;" media="html">
										<@frt.link href="#" onclick="newConfirm('${pendenciaAC.msg}', function(){window.location='${pendenciaAC.action}'});" imgTitleDisabled="Você não tem permição para remover esse ítem." imgTitle="Excluir" imgName="delete.gif" opacity=opacityDisabled disabled=opacityDisabled/>
									</@display.column>
		
								</#if>
								<@display.column property="pendencia" title="Pendência" style="width: 200px; text-align: center;"/>
								<@display.column property="detalhes" title="Detalhes" style="width: 550px; text-align: center;"/>
								<@display.column property="statusDescricao" title="Status" style="width: 200px; text-align: center;"/>
							</@display.table>
							<#if usuario.usuarioFortes>
								<button onclick="javascript: newConfirm('Confirma exclusão de todas as pendências selecionadas?', function(){$('.btnExcluir').css({ opacity: 0.4 }); $('.btnExcluir').attr('disabled', 'disabled'); $('#removerMultiplasPendenciasAC').submit(); DWRUtil.useLoadingMessage('Carregando...');});" class="btnExcluir" type="button" ></button>
							</#if>
						</#if>
					</div>
				</div>
			</form>
		</#if>
	</@authz.authorize>
	
	<div id="splash" style="display: none;">
		<a id="fecharSplash" title="Fechar" href="javascript:;" onclick="$('#splash').dialog('close');" style="float: right; color: red;">
			FECHAR
		</a>
			<img border="0" title="Nova marca"  src="<@ww.url includeParams="none" value="/imgs/splashMarca.jpg"/>">
		</a>
		<input type="checkbox" id="naoExibirMsg" name="naoExibirMsg"/>
		<label for="naoExibirMsg">Não exibir esta mensagem novamente</label>
	</div>

	<script type="text/javascript">
		<#if idiomaIncorreto?exists && !idiomaIncorreto>
			jAlert('O idioma do sistema está incorreto, favor alterar para Português-Brasileiro .');
		</#if>
	</script>
</body>
</html>