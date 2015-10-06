<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>

	<@authz.authorize ifAllGranted="ROLE_MOV_AVALIACAO_EDITAR_ACOMPANHAMENTO">
		<#assign roleEditar = "true"/>
	</@authz.authorize>
	
	<@authz.authorize ifAllGranted="ROLE_MOV_PERIODOEXPERIENCIA ">
		<#assign roleMovPeriodoexperiencia = "true" />
	</@authz.authorize>

	<#if colaboradorQuestionario?exists && colaboradorQuestionario.id?exists>
		<title>Editar Acompanhamento do Período de Experiência</title>
		<#assign desabilitarAvaliacaoSelect="true"/>

		<#if colaborador?exists && colaborador.id?exists>
			<#assign formAction="updateAvaliacaoExperiencia.action"/>
		<#else>
			<#assign formAction="updateAvaliacaoSolicitacao.action"/>
		</#if>
	<#else>
		<#assign desabilitarAvaliacaoSelect="false"/>
		
		<#if colaborador?exists && colaborador.id?exists>
			<#assign formAction="insertAvaliacaoExperiencia.action"/>
			<title>Inserir Acompanhamento do Período de Experiência</title>
		<#else>
			<#assign formAction="insertAvaliacaoSolicitacao.action"/>
			<title>Responder Avaliação da Solicitação</title>
		</#if>
	</#if>

	<#if respostaColaborador>
		<#assign desabilitarAvaliacaoSelect="true"/>
	</#if>

	<#if colaboradorQuestionario?exists && colaboradorQuestionario.respondidaEm?exists>
		<#assign data = colaboradorQuestionario.respondidaEm?date/>
	<#else>
		<#assign data = ""/>
	</#if>
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/perguntasAvaliacao.js?version=${versao}"/>'></script>
	
	<script type="text/javascript">
		function validaRespostasSubjetivas(){
			var possuiResposta = true ;
			
			var respostasSubjetivas = $(".respostaSubjetiva");
			
			$(respostasSubjetivas).each(function (i, resposta) {
				if($(resposta).val() == "")
					possuiResposta = false;
			});
			
			if(!possuiResposta){
				$(".popup").dialog({title: 'Existem perguntas subjetivas sem respostas.', modal: true,height: 150,width: 530,
									buttons: [
									    {
									        text: "Sim",
									        click: function() { document.form.submit(); }
									    },
									    {
									        text: "Não",
									        click: function() { $(this).dialog("close"); }
									    }
									] 
				});
			}
			else{
				document.form.submit();
			}
		}

		function validaForm(){
			var validarRespostas = validaRespostas(new Array('data'), new Array('data'), false, true, false, false, true);
			var validarFormulario = validaFormulario('form', new Array('data'), new Array('data'), true)
			
			if(validarRespostas && validarFormulario)
				validaRespostasSubjetivas();
		}
	</script>
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#if !moduloExterno>
		<#assign class="divInfo"/>
	<#else>
		<#assign class=""/>
	</#if>
	
	<#if colaboradorQuestionario?exists && colaboradorQuestionario.respondida && !roleEditar?exists>
			<@ww.div cssClass="info">
				<ul style="margin: 20px 20px 20px 0px;" >Esta avaliação já foi respondida. Não é possível editar as respostas.</ul>
			</@ww.div>
	</#if>
	
	<@ww.div cssClass="${class}" cssStyle="width: 500px;">
		<#if colaborador?exists && colaborador.id?exists>
			<#if colaboradorQuestionario?exists && colaboradorQuestionario.avaliador?exists && colaboradorQuestionario.avaliador.nome?exists>
				<h4>Avaliador: ${colaboradorQuestionario.avaliador.nome}</h4>
			</#if>
			<h4>Colaborador: ${colaborador.nome}</h4>
		
			<@ww.form name="formAvaliacao" action="prepareInsertAvaliacaoExperiencia.action" method="POST">
				<@ww.select label="Avaliação do Período de Experiência" name="colaboradorQuestionario.avaliacao.id" id="cidade" list="avaliacaoExperiencias" listKey="id" listValue="titulo" cssStyle="width: 480px;" headerKey="" headerValue="Selecione..."  onchange="this.form.submit();" disabled="${desabilitarAvaliacaoSelect}"/>
				
				<@ww.hidden name="colaboradorQuestionario.candidato.id" />
				<@ww.hidden name="colaboradorQuestionario.colaborador.id" />
			</@ww.form>
		<#else>
			<h4>
				<#if !moduloExterno>
					Candidato: ${candidato.nome}<br />
				</#if>
				Avaliação: ${colaboradorQuestionario.avaliacao.titulo}
			</h4>
		</#if>
	</@ww.div>
	
	<#if perguntas?exists && 0 < perguntas?size>
		<@ww.form name="form" action="${formAction}" method="POST">
			<#if !moduloExterno>
				<br />
				<@ww.datepicker label="Data" id="data" name="colaboradorQuestionario.respondidaEm" required="true" cssClass="mascaraData" value="${data}"/>
			<#else>
				<@ww.hidden id="data" name="colaboradorQuestionario.respondidaEm" value="${data}"/>
			</#if>
			
			<#include "../avaliacao/includePerguntasAvaliacao.ftl" />

			<#if candidato?exists && candidato.id?exists>
				<@ww.hidden name="colaboradorQuestionario.candidato.id" value="${candidato.id}"/>
			</#if>
			
			<@ww.hidden name="colaboradorQuestionario.avaliador.id" />
			<@ww.hidden name="solicitacao.id" />
			<@ww.hidden name="respostaColaborador" />
			<@ww.hidden name="autoAvaliacao" />
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<#if !preview>
				<#if colaboradorQuestionario?exists && !colaboradorQuestionario.respondida || roleEditar?exists || roleMovPeriodoexperiencia?exists >
					<button onclick="validaForm();" class="btnGravar"></button>
				</#if>
			</#if>
	<#else>
		<div class="buttonGroup">
	</#if>
			<#if autoAvaliacao>
				<button class="btnCancelar" onclick="window.location='../modelo/minhasAvaliacoesList.action'"></button>
			<#elseif colaborador?exists && colaborador.id?exists>
				<#if !respostaColaborador>
					<button class="btnCancelar" onclick="window.location='periodoExperienciaQuestionarioList.action?colaborador.id=${colaborador.id}'"></button>
				</#if>
			<#elseif moduloExterno>
				<button class="btnCancelar" onclick="window.location='prepareListAnuncio.action'"></button>
			<#else>
				<button class="btnCancelar" onclick="window.location='list.action?solicitacao.id=${solicitacao.id}'"></button>		
			</#if>
		</div> <!-- Fecha div aberta dentro do if/else -->
		

		<div class='popup' style="display:none">
			Deseja realmente gravar o questionário sem responder todas as perguntas?
		</div>
</body>
</html>