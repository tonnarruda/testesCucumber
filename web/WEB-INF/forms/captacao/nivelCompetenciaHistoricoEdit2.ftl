<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
		<@ww.head/>
		<#if nivelCompetencia.id?exists>
			<title>Editar Nível de Competência</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Nível de Competência</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/NivelCompetenciaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<script type='text/javascript'>
		function enviaForm()
		{
			if(!$('#percentual').val())
			{
				NivelCompetenciaDWR.existeCriterioAvaliacaoCompetencia(${empresaSistema.id},function(data){
					if(data){			
						jAlert("Não é permitido inserir ou editar o nível de competência sem o percentual mínimo, pois existem critérios de avaliação das competências.", "RH",
						validaFormulario('form', new Array('ordem','descricao', 'percentual')));
					}else{
						return validaFormulario('form', new Array('ordem','descricao'));
					}
						
				});
			}else if($('#percentual').val() < 0 || $('#percentual').val() > 100)
			{
				jAlert("Percentual informado inválido. </br>Valor máximo permitido = 100%."); 
			}else
			{
				NivelCompetenciaDWR.existePercentual($('#nivelCompetenciaId').val(), ${empresaSistema.id},$('#percentual').val().replace(',', '.'), function(data){
					if(data)			
						jAlert("Percentual informado já está cadastrado em outro nível de competência.");
					else
						return validaFormulario('form', new Array('ordem','descricao'));
				});
			}
		}	
	</script>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		<@ww.form name="form" action="${formAction}" onsubmit="enviaForm();" method="POST">
			<@ww.hidden name="nivelCompetencia.id" id = "nivelCompetenciaId"/>
			<@ww.hidden name="nivelCompetencia.empresa.id" />
			<@ww.token/>
			
			<@ww.textfield label="Descrição" name="nivelCompetencia.descricao" id="descricao" maxLength="60" cssStyle="width:300px;" required="true"/>
			<@ww.textfield label="Peso" name="nivelCompetencia.ordem" id="ordem" size="4"  maxLength="4" required="true" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:40px; text-align:right;"/>
			<@ww.textfield label="Percentual mínimo(%)" name="nivelCompetencia.percentual" id="percentual" cssStyle="width:43px; text-align:right;" maxLength="5" onkeypress="return somenteNumeros(event,',');"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="enviaForm();" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
