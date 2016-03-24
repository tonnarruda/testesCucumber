<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
	<head>
		<@ww.head/>
		
		<style type="text/css">
			@import url('<@ww.url value="/css/displaytag.css"/>');
		</style>
		
		<#if nivelCompetenciaHistorico?exists && nivelCompetenciaHistorico.id?exists>
			<title>Editar Histórico do Nível de Competência</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Histórico do Nível de Competência</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#assign validarCampos=""/>
	
	<script type="text/javascript">
		$(function() {
			<#if !podeEditar>
				$(":checkbox").attr("disabled","disabled");
			</#if>
		
			$('.configHistoricoNivel').click(function(){
				$(this).parent().parent().find(".campos").attr('disabled', !($(this).attr('checked')));
			});
			
			$('#checkAllNiveis').click(function() {
				tabela = $(this).parent().parent().parent().parent();
				tabela.find('.configHistoricoNivel').attr('checked', ($(this).attr('checked')));
				tabela.find(".campos").attr('disabled', !($(this).attr('checked')));
			});
			
			<#if configHistoricoNivels?exists && 0 < configHistoricoNivels?size>
				$('#checkAllNiveis').attr('checked', 'true');
				$('#configHistNivel').find('.configHistoricoNivel').attr('checked', 'true');
				$('#configHistNivel').find(".campos").attr('disabled', false);
			</#if>
		});
		
		function submeter()
		{
			camposObrigatorios = new Array();
			
			$('.peso').each(function(){
				if($(this).parent().parent().find('[type=checkbox]').is(':checked'))
					camposObrigatorios.push($(this).attr('id'));	
			});

			<#if obrigarPercentual>
				$('.percentual').each(function(){
					if($(this).parent().parent().find('[type=checkbox]').is(':checked'))
						camposObrigatorios.push($(this).attr('id'));	
				});
			</#if>
		
			return validaFormulario('form', camposObrigatorios)
		}
	</script>
	
	</head>
	<body>
		<@ww.actionmessage />
		<@ww.actionerror />
		
		<@ww.form name="form" action="${formAction}" onsubmit="submeter();" method="POST">
			
			<#if configHistoricoNivels?exists && 0 < configHistoricoNivels?size>
				<#if nivelCompetenciaHistorico?exists && nivelCompetenciaHistorico.id?exists>
					<@ww.hidden name="nivelCompetenciaHistorico.id" value="${nivelCompetenciaHistorico.id}" />
					<@ww.hidden name="nivelCompetenciaHistorico.empresa.id" value="${nivelCompetenciaHistorico.empresa.id}" />
					<@ww.hidden name="nivelCompetenciaHistorico.data" value="${nivelCompetenciaHistorico.data}" />
				</#if>
	
					<#assign i = 0/>
					<@display.table name="configHistoricoNivels" id="configHistNivel" class="dados">
									
						<#if configHistNivel?exists && configHistNivel.id?exists>
							<#assign configHistNivelId = "${configHistNivel.id}" />
						<#else>
							<#assign configHistNivelId = "" />
						</#if>
						
						<#if configHistNivel?exists &&  configHistNivel.ordem?exists>
							<#assign configHistNivelOrdem = "${configHistNivel.ordem}" />
						<#else>
							<#assign configHistNivelOrdem = "" />
						</#if>
						
						<@display.column title="<input type='checkbox' id='checkAllNiveis'/>" style="width:50px; text-align:center;" >
							<input type="checkbox" id="configHistoricoNivel_${i}" name="configHistoricoNivels[${i}].id" value="${configHistNivelId}" class="configHistoricoNivel" <#if !podeEditar>disabled="disabled" </#if> />
							<@ww.hidden name="configHistoricoNivels[${i}].nivelCompetencia.descricao" value="${configHistNivel.nivelCompetencia.descricao}"/> 
						</@display.column>
							
						<@display.column property="nivelCompetencia.descricao" title="Descrição"/>
						
						<@display.column title="Peso" style="width: 50px; text-align: center;">
							
							<#if podeEditar>
								<input type="text" disabled="disabled" id="peso_${i}" name="configHistoricoNivels[${i}].ordem" size="4" maxlength="4" value="${configHistNivelOrdem}" class="campos peso" style="width:40px; text-align:right; border: 1px solid #BEBEBE;" onkeypress="return(somenteNumeros(event,''));">
							<#else>
								${configHistNivelOrdem}
								<@ww.hidden name="configHistoricoNivels[${i}].id" value="${configHistNivelId}"/> 
								<@ww.hidden name="configHistoricoNivels[${i}].ordem" value="${configHistNivelOrdem}"/>
							</#if>
							<@ww.hidden name="configHistoricoNivels[${i}].nivelCompetencia.id" value="${configHistNivel.nivelCompetencia.id}"/>
							<#if configHistNivel.nivelCompetenciaHistorico?exists>
								<@ww.hidden name="configHistoricoNivels[${i}].nivelCompetenciaHistorico.id" value="${configHistNivel.nivelCompetenciaHistorico.id}"/>
							</#if>
						</@display.column>
						<@display.column title="Percentual Mínimo (%)" style="width: 150px; text-align: center;">
							<#if podeEditar || !obrigarPercentual>
								<input type="text" disabled="disabled" id="percentual_${i}" name="configHistoricoNivels[${i}].percentual" size="5" maxlength="5" value="${configHistNivel.percentualFormatado}" class="campos percentual" style="width:50px; text-align:right; border: 1px solid #BEBEBE;" onkeypress="return(somenteNumeros(event,','));">
							<#else>
								${configHistNivel.percentualFormatado}
								<@ww.hidden name="configHistoricoNivels[${i}].percentual" value="${configHistNivel.percentualFormatado}"/>
							</#if>
						</@display.column>
	
						<#assign i = i + 1/>
					</@display.table>
				
				<@ww.token/>
			</#if>
		</@ww.form>
	
		<div class="buttonGroup">
			<#if configHistoricoNivels?exists && 0 < configHistoricoNivels?size && (podeEditar || !obrigarPercentual)>
				<button onclick="submeter();" class="btnGravar"></button>
			</#if>
			<button onclick="window.location='../nivelCompetenciaHistorico/list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
