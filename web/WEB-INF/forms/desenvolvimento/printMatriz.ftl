<html>
<head>
	<title>Matriz de Qualificação</title>
	<@ww.head />
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/matriz.css?version=${versao}"/>');
	</style>
	<script type="text/javascript">
	function vertical(valor)
	{
		for(var i = 0; i < valor.length; i++)
		{
			document.write(valor.charAt(i).toUpperCase() + "<br>");
		}
	}

	function CallPrint(strid)
	{
		var browserName=navigator.appName;

		var prtContent = document.getElementById(strid);
		var WinPrint = window.open('','','letf=20,top=20,width=680,height=368,toolbar=0,scrollbars=1,status=0');

		var cssLink = "";
		cssLink	+=	"<style type='text/css'>";
		cssLink	+=	"@import url('<@ww.url includeParams="none" value="/css/matriz.css?version=${versao}"/>');";
		cssLink	+=	"</style>";
		cssLink	+=	"<script type='text/javascript'>";
		cssLink +=	"function vertical(){}";
		cssLink	+=	"</";
		cssLink +=	"script>";

		WinPrint.document.write(cssLink + prtContent.innerHTML);

		WinPrint.document.close();
		WinPrint.focus();
		WinPrint.print();
		WinPrint.close();
	}
	</script>
</head>
<body>
<div id="divPrint">
<#function getPt idTurma idColab>
	<#assign resultado = "" />

	<#list colaboradorTurmasLista as lista>
		<#if lista.turma.id == idTurma && lista.colaborador.id == idColab>
			<#assign resultado = lista.prioridadeTreinamento.sigla />
		</#if>
	</#list>

	<#return resultado>
</#function>

<table width="100%" cellspacing="0" cellpadding="0" class="matriz">

	<tr>
		<td class="matrizQualificacao">
			<b>Matriz de Qualificação - (Colaborador X Curso)</b>
			<br>
			<b>A partir de:</b> <#if dataIni?exists>${dataIni?string('dd/MM/yyyy')}</#if>
		</td>
		<#list cursos as curso>
			<td class="nomedoCurso">
				<script type="text/javascript">vertical("${curso.nome}");</script>
			</td>
		</#list>
	</tr>

	<#list colaboradorCursoMatrizs as colabMatriz>
		<tr>
			<td class="colaborador">
				${colabMatriz.colaborador.nomeComercial}
			</td>
			<#list cursos as curso>
				<td class="pontuacao">
					<#list colabMatriz.cursoPontuacaos as pontuacaoCurso>
						<#if curso.id == pontuacaoCurso.curso.id>
							${pontuacaoCurso.sigla}
						<#else>
							&nbsp;
						</#if>
					</#list>
				</td>
			</#list>
		</tr>
	</#list>
	<tr class="pontuacaoGeral">
		<td class="prioridade">
			Pontuação Geral
		</td>
		<#list cursos as curso>
			<td class="pontuacao">
				<#list somatorioCursoMatrizs as somaCurso>
					<#if somaCurso.cursoId == curso.id>
						${somaCurso.soma}
					</#if>
				</#list>
			</td>
		</#list>
	</tr>
</table>
</div>

<br>
<div class="buttonGroup">
	<button class="btnImprimir" onclick="CallPrint('divPrint')" accesskey="I">
	</button>
	<button class="btnVoltar" onclick="window.location='prepareImprimirMatriz.action'" accesskey="V">
	</button>
</div>
</body>
</html>