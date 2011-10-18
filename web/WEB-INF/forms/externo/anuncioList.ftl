<html>
<head>
<@ww.head/>

<style type="text/css">
ul.lista-anuncios { list-style-type: square; }
ul.lista-anuncios li { margin: 10px; }
</style>

</head>
<body>

<#if !SESSION_CANDIDATO_NOME?exists >
	<script>window.location='prepareLogin.action';</script>
</#if>

<table width="100%">
	<tr>
		<td width="75%">
			<#if SESSION_CANDIDATO_NOME?exists >
				<b>Bem vindo ${SESSION_CANDIDATO_NOME}!<br>
				CPF: ${SESSION_CANDIDATO_CPF}</b>
			</#if>
		</td>
		<td width="1%">&nbsp;</td>
		<td width="24%" align='right'>
			<#assign areaId = 0>
			<#assign primeira = true>
			<#-- pega o id do candidato na sessao para iniciar o update-->
			<a href="prepareUpdate.action?moduloExterno=true&empresaId=${SESSION_EMPRESA}&candidato.id=${SESSION_CANDIDATO_ID}">Editar Currículo</a><br>
			<a href="prepareUpdateSenha.action?moduloExterno=true&empresaId=${SESSION_EMPRESA}&candidato.id=${SESSION_CANDIDATO_ID}">Alterar Senha</a><br>
			<a href="logoutExterno.action?empresaId=${SESSION_EMPRESA}">Sair</a>
		</td>
	</tr>
	<tr><td colspan="3"><hr><b>Vagas Abertas:</b><br></td></tr>
	<#if (anuncios?exists && anuncios?size > 0)>
		<tr>
			<td colspan="3" width="400px">
				<ul class="lista-anuncios">
					<#list anuncios as anuncio>
						<li>
							<#if anuncio.candidatoCadastrado>
								<strong>${anuncio.titulo}</strong>
								(Candidato já inserido na seleção)
							<#else>
								<a href="verAnuncio.action?anuncio.id=${anuncio.id}" style="font-weight: bold">${anuncio.titulo}</a>
							</#if>
							<br>
							${anuncio.solicitacao.quantidade} vaga(s)
							<#assign primeira = false>
						</li>
					</#list>
				</ul>
			</td>
		</tr>
	<#else>
		<tr><td colspan="3"><div align='center'><br><br><b>Não há vagas no momento.</b></div></td></tr>
	</#if>
	</table>
	<#if msgAlert?exists && msgAlert != "">
		<script>
			jAlert('${msgAlert}');
		</script>
	</#if>

	<#if sucessoEnvioCurriculo>		
		<script language='javascript'>
			jAlert('Agradecemos seu interesse. Seu currículo será analisado e, se aprovado, entraremos em contato.');
		</script>
	</#if>	

</body>
</html>