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

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/DiaTurmaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<script type="text/javascript">
		function populaDias(indice)
		{
			var dIni = document.getElementById('prevIni' + indice);
			var dFim = document.getElementById('prevFim' + indice);
	
			if(dIni.value != "  /  /    " && dFim.value != "  /  /    " && validaDate(dIni) && validaDate(dFim))
			{
				DWRUtil.useLoadingMessage('Carregando...');
				DiaTurmaDWR.getDias(dIni.value, 
									dFim.value, 
									function(dados) 
									{
										if(dados != null)
										{
											addChecks('diasTurmasCheck[' + indice + ']', dados);
										}
										else
											jAlert("Data inválida.");
									});
			}
		}
		
		function aplicar()
		{
			var obrigatorios = [];
			var validados = [];
			
			for (var i = 0; i < ${cursosColaboradores?size}; i++)
			{
				obrigatorios.push('desc' + i);
				obrigatorios.push('inst' + i);
				obrigatorios.push('prevIni' + i);
				obrigatorios.push('prevFim' + i);
				obrigatorios.push('@diasTurmasCheck[' + i + ']');

				validados.push('prevIni' + i);
				validados.push('prevFim' + i);
			}
			
			return validaFormularioEPeriodo('form', obrigatorios, validados);
		}
	</script>
	
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
								<td>
									${colab.nome}
									<@ww.hidden name="turmas[${i}].colaboradorTurmas[${j}].colaborador.id" value="${colab.id}"/>
								</td>
							</tr>
							<#assign j = j + 1/>
						</#list>
					</tbody>
				</table>
				
				<@ww.hidden name="turmas[${i}].curso.id" value="${curso.id}"/>
				<@ww.textfield required="true" label="Descrição" name="turmas[${i}].descricao" id="desc${i}" cssStyle="width: 500px;" maxLength="100"/>
				<@ww.textfield required="true" label="Instrutor" size="55" name="turmas[${i}].instrutor" id="inst${i}" cssStyle="width: 500px;" maxLength="100"/>
				
				Período:* <br />
				<@ww.datepicker required="true" name="turmas[${i}].dataPrevIni" id="prevIni${i}" liClass="liLeft" onblur="populaDias(${i});" onchange="populaDias(${i});"  cssClass="mascaraData validaDataIni"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.datepicker required="true" name="turmas[${i}].dataPrevFim" id="prevFim${i}" onblur="populaDias(${i});" onchange="populaDias(${i});"  cssClass="mascaraData validaDataFim" />
				<@frt.checkListBox id="diasCheck${i}" name="diasTurmasCheck[${i}]" label="Dias Previstos" list="diasTurmasCheckList"/>
			</fieldset>
			
			<br />
			
			<#assign i = i + 1/>
		</#list>
	</@ww.form>
	
	<div class="buttonGroup">
		<button type="button" onclick="aplicar()" class="btnAplicar" ></button>
	</div>
</body>
</html>