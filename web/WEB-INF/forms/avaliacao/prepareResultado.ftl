<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Resultado da Avaliação de Desempenho (${avaliacaoDesempenho.titulo})</title>
	
	<#assign validarCampos="return validaFormulario('form', new Array('@colaboradorsCheck'), null)"/>
	
	<script type="text/javascript">
		function filtrarOpt(opcao)
		{
			if (opcao == 'avaliador')
				jQuery('#opcoesDoRelatorio').hide();
			else
				jQuery('#opcoesDoRelatorio').show();
		}
		
		jQuery(document).ready(function($){
			var opcaoResultado = jQuery('#opcaoResultado');
			filtrarOpt(opcaoResultado.val());
		});
	</script>
	
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="resultado.action" onsubmit="${validarCampos}" method="POST">
		<@frt.checkListBox name="colaboradorsCheck" label="Avaliados" list="colaboradorsCheckList" width="600" height="300"/>
		
		
		<@ww.select label="Resultado" required="true" name="opcaoResultado" id="opcaoResultado" list=r"#{'avaliador':'Resultado por Avaliador', 'criterio':'Resultado por Critérios'}" onchange="filtrarOpt(this.value);"/>
		
		<div id="opcoesDoRelatorio">
			<@ww.checkbox label="Exibir todas as respostas" id="exibirRespostas" name="exibirRespostas" labelPosition="left"/>
			<@ww.checkbox label="Exibir comentários" id="exibirComentarios" name="exibirComentarios" labelPosition="left"/>
			<@ww.checkbox label="Agrupar perguntas por aspecto" id="agruparPorAspectos" name="agruparPorAspectos" labelPosition="left"/>
			
		</div>
		
		<@ww.hidden name="avaliacaoDesempenho.id"  />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio"></button>		
		<button onclick="window.location='list.action'" class="btnVoltar"></button>
	</div>
</body>
</html>