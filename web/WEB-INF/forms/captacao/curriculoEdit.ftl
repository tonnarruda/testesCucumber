<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Currículo Escaneado - ${candidato.nome}</title>

	<script type="text/javascript">
		var numero = 2;
		function addFileInput() {
		 	var d = document.createElement("div");
		 	var num = document.createElement("div");
		 	num.innerHTML = "Imagem da página "+numero+":";
		 	var file = document.createElement("input");
		 	file.setAttribute("type", "file");
		 	file.setAttribute("name", "imagemEscaneada");
		 	d.appendChild(file);
		 	document.getElementById("maisUploads").appendChild(num);
		 	document.getElementById("maisUploads").appendChild(d);
		 	numero++;
		}


	</script>
<#assign validarCampos="return validaFormulario('form', new Array('ocrTexto'), null)"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="insertCurriculo.action" onsubmit="" validate="true" method="POST" enctype="multipart/form-data">
		<@ww.hidden name="candidato.id" id="id"/>
		<@ww.hidden name="candidato.nome" id="nome"/>
		<b>Texto reconhecido na imagem</b>
		<br>
		<br>
		Arquivo gerado via OCR:
		<@ww.file name="ocrTexto" id="ocrTexto"/>
		<br>
		<hr>
		<br>
		<b>Páginas Escaneadas</b>
		<br>
		<br>
		<@ww.div liClass="liLeft">Imagem da página 1:</@ww.div>
		<@ww.file name="imagemEscaneada"/>
		<div id="maisUploads"></div>
		<div id="maisUploadsLink"><a href="javascript:addFileInput();">Adicionar mais páginas</a></div>
		<br>
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="document.form.submit();" class="btnSalvarArquivos">
		</button>
		<button onclick="window.location='list.action'" class="btnCancelar">
		</button>
	</div>

</body>
</html>