<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>

<title>Vincular candidato existente</title>
<script type="text/javascript">
	function enviar(vincular)
	{
		$("#vincular").val(vincular);
		$("#form").submit();
	}	
</script>

</head>
<body>

	<@ww.form name="form" id="form" action="prepareColaboradorSolicitacao.action" method="post">
	
		<@ww.hidden name="colaborador.id"/>
		<@ww.hidden name="statusCandSol"/>
		<@ww.hidden name="vincularCandidatoMesmoCpf" id="vincular"/>
		<@ww.hidden name="checarCandidatoMesmoCpf" value="false"/>
		
		<p>Existe um candidato com o mesmo CPF ${colaborador.pessoal.cpf} do colaborador "${colaborador.nome}".</p>
		<p>
			<a href="javascript:popup('../../captacao/candidato/infoCandidato.action?candidato.id=${candidato.id}', 580, 750)" style="text-decoration:none;">
				<img border="0" align="absmiddle" title="Visualizar Currículo" src="<@ww.url includeParams="none" value="/imgs/page_curriculo.gif"/>"> 
				Clique aqui para visualizar o candidato
			</a>
		</p>
		<p>Deseja vincular o candidato ao colaborador?</p>
	</@ww.form>

	<div class="buttonGroup">
		<button type="button" class="btnSim" onclick="enviar(true);" accesskey="S"></button>
		<button type="button" class="btnNao" onclick="enviar(false);" accesskey="N"></button>
	</div>
</body>
</html>