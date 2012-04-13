<html>
	<head>
		<@ww.head/>
		<style type="text/css">
			.lbl { padding: 2px 4px; margin: 2px; font-size: 9px; font-weight: bold; color: #FFF; border-radius: 3px; }
			.label { padding: 3px 5px 3px 12px; margin-left: 2px; cursor: pointer; border-radius: 3px; border: 1px solid transparent; }
			.label:hover, .labelCheck { 
				border-color: #BBB;
				background-color: #DEDEDE;  
				background-image: url(<@ww.url value="/imgs/menu-bar-right-arrow.gif"/>); 
				background-position: 4px 8px;
				background-repeat: no-repeat; 
			}
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
				var labels = ${labels};
				$(labels).each(function() {
					addLabel(this.name, this.color);
				});
	
				$(".label").click(function(){
					$(this).toggleClass('labelCheck');
					//checkLabel($(this));
				});
				
				<#if issue.labels?exists>
					<#list issue.labels as label>
						$('.lbl:contains("${label.name}")').click();
					</#list>
				</#if>
				
				// ajusta os contrastes
				contraste('lbl');
			});
			
			function checkLabel(label) {
				$(label).addClass('labelCheck');
				$("#labelCheck").val($(label).text());
			}
			
			function addLabel(name, color) {
				var html = '<span class="label"><span class="lbl" style="background-color:#' + color + ';">' + name + '</span></span>';
				$('#listaLabels').append(html);
			}
			
			function gravar() {
				$('#listaCampos').empty();
				
				$('.labelCheck').each(function(i, label) {
					$('#listaCampos').append('<input type="hidden" name="issue.labelNames" value="' + $(label).text() + '"/>');
				});
				
				return validaFormulario('form', new Array('titulo'));
			}
		</script>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="gravar();" method="POST">
			<@ww.hidden name="issue.number" />

			<li>
				<label class="desc">Cartão: </label>
			</li>
			<li>
				<div id="listaLabels"></div>
				<div id="listaCampos"></div>
				<br />
			</li>
			
			<@ww.textfield label="Título" name="issue.title" id="titulo" cssStyle="width: 600px" maxLength="200" required="true"/>
			<@ww.textarea label="Descrição" name="issue.body" id="body" cssStyle="width: 600px"/>
			
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="gravar();" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
