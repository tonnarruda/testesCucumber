<html>
	<head>
		<@ww.head/>
		<style type="text/css">
			.lbl { padding: 2px 4px; margin: 2px; font-size: 9px; font-weight: bold; color: #FFF; border-radius: 3px; }
			.label { padding: 2px; cursor: pointer; border-radius: 3px;}
			.label:hover { text-decoration: none; background-color: #DEDEDE; }
			.labelCheck { text-decoration: none; background-color: #DEDEDE; }
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
				
				var labels = ${labels};
					$(labels).each(function() {
						addLabel(this.name, this.color);
				});

				$(".label").click(function(){
					$(".label").removeClass('labelCheck');
					checkLabel($(this));
				});
			});
			
			function checkLabel(label) {
				$(label).addClass('labelCheck');
				$("#labelCheck").val($(label).text());
			}
			
			function addLabel(name, color) {
				var html = '<span class="label"><span class="lbl" style="background-color:#' + color + ';">' + name + '</span></span>';
				$('#listaLabels').append(html);
			}
		</script>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			<@ww.hidden name="issue.number" />

			<div id="listaLabels">Cartão:</div>
			<input id="labelCheck" type="hidden" name="issue.labelNames" value=""/>
			
			<@ww.textfield label="Título" name="issue.title" id="titulo" cssStyle="width: 600px" maxLength="200" required="true"/>
			<@ww.textarea label="Descrição" name="issue.body" id="body" cssStyle="width: 600px"/>
			
			
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
