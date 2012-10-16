<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<title>Plano de Treinamento Individual (PDI) - Criação de Turmas para Treinamentos</title>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		
		.dados { float: right; width: 400px; }
		fieldset { padding: 10px; }
	</style>

	<@ww.head/>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@ww.form name="form" action="aplicarPdi.action" method="POST">
		<#assign i = 0/>
		
		<#list cursosColaboradores?keys as curso>
			<fieldset>
				<legend>${curso.nome}</legend>
				
				<table class="dados">
					<thead>
						<tr>
							<th>Colaboradores Inscritos</th>
						</tr>
					</thead>
					<tbody>
						<#assign j = 0/>
						<#list action.getColaboradoresCurso(curso) as colab>
							<tr class="<#if j%2 == 0>odd<#else>even</#if>">
								<td>${colab.nome}</td>
							</tr>
							<#assign j = j + 1/>
						</#list>
					</tbody>
				</table>
				
				<@ww.hidden name="turmas[${i}].curso.id" value="${curso.id}"/>
				<@ww.textfield required="true" label="Descrição" name="turmas[${i}].descricao" id="desc" cssStyle="width: 500px;" maxLength="100"/>
				<@ww.textfield required="true" label="Instrutor" size="55" name="turmas[${i}].instrutor" id="inst" cssStyle="width: 500px;" maxLength="100"/>
				
				Período:* <br />
				<@ww.datepicker required="true" name="turmas[${i}].dataPrevIni" id="prevIni" liClass="liLeft" onblur="populaDias(document.forms[0]);" onchange="populaDias(document.forms[0]);"  cssClass="mascaraData validaDataIni"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.datepicker required="true" name="turmas[${i}].dataPrevFim" id="prevFim" onblur="populaDias(document.forms[0]);" onchange="populaDias(document.forms[0]);"  cssClass="mascaraData validaDataFim" />
				<@frt.checkListBox name="diasCheck" label="Dias Previstos" list="diasCheckList" readonly=false valueString=true/>
			</fieldset>
			
			<br />
			
			<#assign i = i + 1/>
		</#list>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="return validaFormulario('form', null, null);" class="btnAplicar" ></button>
	</div>
</body>
</html>