
<html>
<head>
<@ww.head/>
<title>Pesquisa concluída</title>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/preview.css?version=${versao}"/>');
</style>
</head>
<body>
<#if pesquisa.tipoAvaliacao == 0>
	<#assign strPesquisa="Pesquisa"/>
<#elseif pesquisa.tipoAvaliacao == 1>
	<#assign strPesquisa="Avaliação"/>
</#if>


<SCRIPT>
setTimeout("fechar()",5000);
function fechar(){
	window.close();
	if (window.opener && !window.opener.closed) {
		window.opener.location.reload();
	}
}
</SCRIPT>


<br><br>
<table width=100% height=90% >
	<tr>
		<td align=center valign=middle>
			<table bgcolor="#999999" width="300" cellpadding="3" cellspacing="1">
				<tr>
					<td bgcolor="#EEEEEE">
					<div align="center"><font face="Georgia, Times New Roman, Times, serif" size="2" color="#666666"><br>


					<b>${strPesquisa} concluída com sucesso.<br><br>

						<p class="fechar">
							<button onclick="fechar();">Fechar</button>
						</p><br>

					</font></div>
					</td>
				</tr>
			</table>
		</td>

	</tr>
</table>

</body>
</html>