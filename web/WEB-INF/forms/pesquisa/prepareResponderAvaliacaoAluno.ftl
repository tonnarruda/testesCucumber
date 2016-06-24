<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
<@ww.head/>
	<title>Responder Avaliação do Aluno</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/perguntasAvaliacao.js?version=${versao}"/>'></script>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		.pergunta { padding: 5px; }
		.dados th:first-child { text-align: left; padding-left: 5px; }
	</style>
	
	<script type='text/javascript'>
		function notaObtida(){
			var notaObtidaQuestionario = 0;
			 $('.perguntaResposta').each(function(){
			 	var peso = $(this).find("#pesoPergunta").val();
			 	var tipo = $(this).find("#tipo").val();
			 	if(tipo == 1)
			 		if($(this).find('.objetiva:checked').size() > 0)
			 			notaObtidaQuestionario += $(this).find('.objetiva:checked').attr('peso') * peso;
			 	
			 	if( tipo == '4')
		 			notaObtidaQuestionario += $(this).find('.nota').val()  * peso;

				if(tipo == 5){
					var pesoMultiplaEscolha = 0;
					$(this).find('.multiplaEscolha:checked').each(function(){
						pesoMultiplaEscolha += parseInt($(this).attr('peso'));
					});
					notaObtidaQuestionario += pesoMultiplaEscolha * peso;
				}
			 });
			 
			 performanceQuestionario = notaObtidaQuestionario / ${pontuacaoMaximaQuestionario};
			 
			 return (performanceQuestionario * 100);
		} 
		
		function dialogCertificacao(){
			msg = "Este colaborador está certificado. </br>" +
					"Deseja realmente descertificar este colaborador?</br>" +
					"Ao confirmar, o colaborador será descertificado " +
					"e caso exista notas de avaliações prática as mesmas serão excluídas.";
			
			$('#dialog').html(msg).dialog({ 	modal: true, 
												width: 500,
												maxHeight: 360,
												buttons: 
												[
												    {
												        text: "Confirmar",
												        click: function() { 
												        	validaRespostas(null, null, true, true, false, false, true);
												        	$(this).dialog("close");									        
												        }
												    },
												    {
												        text: "Cancelar",
												        click: function() { $(this).dialog("close"); }
												    }
												]
											});
		}
		
		function submeter(){
			if(${colaboradorCertificado?string} && ${empresaSistema.controlarVencimentoPorCertificacao?string} && (notaObtida() < ${avaliacaoCurso.minimoAprovacao})){
				dialogCertificacao();
			}else{
				validaRespostas(null, null, true, true, false, false, true);
			}
		}
	</script>
	
	<#assign respostasCompactas=avaliacao.respostasCompactas />
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<b>${avaliacao.titulo}</b><br/>
	<b>Aluno: ${colaborador.nome}</b><br/>
	<#if avaliacao?exists && avaliacao.cabecalho?exists>
		<pre><h4>${avaliacao.cabecalho}</h4></pre>
	</#if>
	
	<#if perguntas?exists && 0 < perguntas?size>
		<@ww.form name="form" action="responderAvaliacaoAluno.action" method="POST">
			<#include "../avaliacao/includePerguntasAvaliacao.ftl" />
			<@ww.hidden name="colaborador.id" />
			<@ww.hidden name="turma.id" />
			<@ww.hidden name="curso.id" />
			<@ww.hidden name="avaliacaoCurso.id" />
			<@ww.hidden name="avaliacaoCurso.avaliacao.id" />
			<@ww.hidden name="colaboradorTurmaId" />
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="submeter();" class="btnGravar"></button>
	<#else>
		<div class="buttonGroup">
	</#if>
			<button class="btnCancelar" onclick="javascript: executeLink('${urlVoltar}');"></button>
		</div>
	<div id="dialog" title="Confirmar Remoção da Certificação"></div>
</body>
</html>