<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
	<head>
		<@ww.head/>
		<title>Download do Backup do Banco de Dados</title>
	
	</head>
	<body>
		<@ww.actionerror />
		<@ww.url id="url" value="/backup/download.action?filename=${filename}"/>
		<div>
			<h3>O download iniciará em alguns segundos, por favor aguarde...</h3>
			Se houver problemas com o download, por favor, tente baixar por este
			<a id="url" href="${url}" style="font-weight: bold;">link direto</a>.<br/>
		</div>
		<script type="text/javascript">
			setTimeout(function() {
				location.href = "${url}";
			}, 4000); // espera por 4 sec antes de inicia o download.
		</script>
	</body>
</html>