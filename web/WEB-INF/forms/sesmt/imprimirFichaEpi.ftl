<html>
<head>
<@ww.head/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/fortes.js"/>'></script>

	<#include "../ftl/mascarasImports.ftl" />

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<#assign formAction="imprimirFicha.action" />

	<#if (colaboradors?exists && colaboradors?size > 0)>
		<#assign headerValue="Selecione..."/>
	<#else>
		<#assign headerValue="Utilize o Filtro acima."/>
	</#if>

	<script type='text/javascript'>
		function mudaAction(opcao)
		{
			if (opcao == 'imprimir')
				document.formRelatorio.action = "${formAction}";
			else
				document.formRelatorio.action = "imprimirFichaVerso.action";

			return validaFormulario('formRelatorio',new Array('colaborador'),null);
		}
	</script>

	<title>Ficha de EPI</title>

</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />

<@ww.form name="form" action="filtroImprimirFicha.action" method="POST" >

		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 500px;">
				<ul>
					<span id="divColaborador">
						<@ww.textfield label="Nome" name="colaborador.nome" id="nomeColaborador" cssStyle="width: 300px;"/>
						<@ww.textfield label="Matrícula" name="colaborador.matricula" id="matriculaBusca" liClass="liLeft" cssStyle="width: 60px;"/>
						<@ww.textfield label="CPF" name="colaborador.pessoal.cpf" id="cpfColaborador" cssClass="mascaraCpf"/>
					</span>

					<input type="submit" value="" class="btnPesquisar grayBGDivInfo" />
				</ul>
			</@ww.div>
		</li>
	<br/>
</@ww.form>

	<@ww.form name="formRelatorio" action="${formAction}" method="post">
		<br/>
		<@ww.select label="Colaborador" name="colaborador.id" id="colaborador" list="colaboradors" required="true" listKey="id" listValue="nomeCpf" headerKey="" headerValue="${headerValue}" />
		<@ww.checkbox label="Imprimir verso" id="imprimirVerso" name="imprimirVerso" labelPosition="left"/>
		
		<div class="buttonGroup">
			<button class="btnImprimirPdf" onclick="return mudaAction('imprimir');"></button>
		</div>

		<@ww.hidden name="colaborador.nome" />
		<@ww.hidden name="colaborador.pessoal.cpf" />
		<@ww.hidden name="colaborador.matricula" />
	</@ww.form>

</body>
</html>