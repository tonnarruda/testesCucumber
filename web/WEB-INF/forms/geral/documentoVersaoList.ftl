<html>
<head>
<@ww.head/>
	<title>Histórico de Versões</title>

	<style type="text/css">
		.linhaVersao
		{
			background-color: #f3f2e9;
		}
		.divVersao
		{
			border:1px solid #AEAEAE;
			overflow: scroll;
			overflow-x: hidden;
			width: 966px;
			height: 450px;
		}

		th.tamanho100
		{
			width: 105px;
		}
		.tabelaTH
		{
			width: 100%;
			border: 1px solid #909090;
		}
		.tabelaTH th
		{
			background-color: #EBEADB;
			border-right: 1px solid #ACA899;
			border-left: 1px solid #FFF;
			border-bottom: 3px solid #D6D2C2;
			border-top: 1px solid #DCDAC5;
		}
		.docVersao td
		{
			border-bottom: 1px solid #DFDFDF;
			padding: 3px;
		}
		.docVersao
		{
			background-color: #FFF;
			font-size: 12px;
		}
		td.tamanho100
		{
			border-right: 1px solid #DFDFDF;
			text-align: center;
			width: 100px;
		}

		.docVersao ul { list-style: disc; padding-left: 20px; }
		.docVersao ul li { padding: 3px; }
	</style>
</head>
<body>
<table cellspacing="0" cellpadding="0" class="tabelaTH">
	<tr>
		<th class="tamanho100">Data</th>
		<th class="tamanho100">Versão</th>
		<th>Descrição</th>
	</tr>
</table>
	<div class="divVersao">
		${textoXml}
	</div>
</body>
</html>