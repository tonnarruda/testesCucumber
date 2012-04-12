<html>
	<head>
		<@ww.head/>
		<style type="text/css">
			.lbl { padding: 2px 4px; margin: 2px; font-size: 9px; font-weight: bold; color: #FFF; border-radius: 3px; }
		</style>
		
		<#if issue.number?exists>
			<title>Editar Cartão</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Cartão</title>
			<#assign formAction="insert.action"/>
		</#if>
	
		<#assign validarCampos="return validaFormulario('form', new Array('titulo'))"/>
		
		<script type="text/javascript">
			$(function() {
				<#if issue.labels?exists>
					<#list issue.labels as label>
						addLabel('${label.name}', '${label.color}');
					</#list>
				</#if>
			});
			
			function addLabel(name, color) {
				var html = '<span class="lbl" style="background-color:#' + color + ';">' + name + '</span>';
				html += '<input type="hidden" name="issue.labelNames" value="' + name + '"/>';
				
				$('#listaLabels').append(html);
			}
		</script>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="issue.number" />
			
			<@ww.textfield label="Título" name="issue.title" id="titulo" cssStyle="width: 600px" maxLength="200" required="true"/>
			<@ww.textarea label="Descrição" name="issue.body" id="body" cssStyle="width: 600px"/>
			
			<label>Labels: </label>
			<div id="listaLabels"></div>
			
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
