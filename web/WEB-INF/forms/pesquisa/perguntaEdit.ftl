<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if pergunta.id?exists>
	<title>Editar Pergunta</title>
	<#assign formAction="update.action"/>
	<#assign buttonClass="btnGravar"/>
	<#assign accessKey="A"/>
	<#assign voltarAction="../pergunta/list.action?questionario.id=${questionario.id}"/>
<#else>
	<title>Inserir Pergunta</title>
	<#assign formAction="insert.action"/>
	<#assign buttonClass="btnGravar"/>
	<#assign accessKey="I"/>
	<#assign voltarAction="../pergunta/list.action?questionario.id=${questionario.id}"/>
</#if>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AspectoDWR.js?version=${versao}"/>'></script>
	
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/questionario.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
		.yui-ac-container, .yui-ac-content, .yui-ac-shadow, .yui-ac-content ul{
			width: 300px;
		}
		#text {margin:50px auto; width:500px}
		.hotspot {color:#900; padding-bottom:1px; cursor:pointer}

		#tt {position:absolute; display:block;}
		#tttop {display:block; height:5px; margin-left:5px; overflow:hidden}
		#ttcont {display:block; padding:2px 12px 3px 7px; margin-left:5px; background:#666; color:#FFF}
		#ttbot {display:block; height:5px; margin-left:5px; overflow:hidden}
	</style>

<script type="text/javascript">
	
	function exibeComentario()
	{
		if(document.getElementById("checkComentario") && document.getElementById("checkComentario").checked)
		{
			document.getElementById("coment").style.display = "";
			<#if pergunta.textoComentario?exists>
				document.getElementById("textoComentario").value = '${pergunta.textoComentario}';
			<#else>
				document.getElementById("textoComentario").value = 'Justifique sua resposta:';
			</#if>
		}
		else if(document.getElementById("checkComentario") && !document.getElementById("checkComentario").checked)
		{
			document.getElementById("coment").style.display = "none";
			document.getElementById("textoComentario").value = "";
		}
	}

	function exibePorTipo()
	{
		if(document.getElementById("tipo").value == ${tipoPerguntas.getObjetiva()})
		{
			document.getElementById("opcaoComentario").style.display = "";
			document.getElementById("divMultiplasRespostas").style.display = "none";
			document.getElementById("divRespostas").style.display = "";
			document.getElementById("notas").style.display = "none";
			if (document.getElementById("respostasSugeridas") != null)
			{
				document.getElementById("respostasSugeridas").style.display = "none";
			}
			<#if !pergunta.id?exists && !pergunta.respostas?exists>
				document.getElementById("respostaInsert").style.display = "";
			</#if>
		}
		else if(document.getElementById("tipo").value == 2)
		{
			document.getElementById("opcaoComentario").style.display = "";
			document.getElementById("divRespostas").style.display = "";
			document.getElementById("notas").style.display = "none";
			document.getElementById("respostasSugeridas").style.display = "";
			<#if !pergunta.id?exists && !pergunta.respostas?exists>
				document.getElementById("respostaInsert").style.display = "none";
			</#if>
		}
		else if(document.getElementById("tipo").value == ${tipoPerguntas.getSubjetiva()})
		{
			document.getElementById("opcaoComentario").style.display = "none";
			document.getElementById("divMultiplasRespostas").style.display = "none";
			document.getElementById("divRespostas").style.display = "none";
			document.getElementById("notas").style.display = "none";
			document.getElementById("respostasSugeridas").style.display = "none";
			<#if !pergunta.id?exists && !pergunta.respostas?exists>
				document.getElementById("respostaInsert").style.display = "none";
			</#if>
		}
		else if(document.getElementById("tipo").value == ${tipoPerguntas.getNota()})
		{
			document.getElementById("opcaoComentario").style.display = "";
			document.getElementById("divMultiplasRespostas").style.display = "none";
			document.getElementById("divRespostas").style.display = "none";
			document.getElementById("notas").style.display = "";
			document.getElementById("respostasSugeridas").style.display = "none";
			<#if !pergunta.id?exists && !pergunta.respostas?exists>
				document.getElementById("respostaInsert").style.display = "none";
			</#if>
		}
		else if(document.getElementById("tipo").value == ${tipoPerguntas.getMultiplaEscolha()})
		{
			document.getElementById("divMultiplasRespostas").style.display = "";
			document.getElementById("opcaoComentario").style.display = "";
			document.getElementById("divRespostas").style.display = "none";
			document.getElementById("notas").style.display = "none";
			<#if !pergunta.id?exists && !pergunta.respostas?exists>
				document.getElementById("respostaInsert").style.display = "";
			</#if>
		}
	}

	function validaForm()
	{
		if(document.getElementById("tipo").value == 4)
			return validaFormulario('form', new Array('texto','notaMaxima','notaMinima'), null);
		else
			return validaFormulario('form', new Array('texto'), null);
	}

	var idInputObjetiva = 1;
	var idInputObjetivaMultipla = 1;

	function addRespostas()
	{
	 	var d = document.createElement("div");
	 	d.setAttribute("id", "d" + idInputObjetiva);

		var num = document.createElement("div");
		d.appendChild(num);

		if(navigator.appName=='Microsoft Internet Explorer')
		{
			var campo = document.createElement("<input type='input' id='ro_"+idInputObjetiva+"' name='respostaObjetiva' style='width: 355px'>");
			d.appendChild(campo);
			var str = "d" + idInputObjetiva;
			var imagem = document.createElement("<img src='<@ww.url value="/imgs/delete.gif"/>' onClick=remResposta('"+ str +"'); style='cursor: hand'>");
			d.appendChild(imagem);
		}
		else
		{
			var campo = document.createElement("input");
			campo.setAttribute("type", "input");
			campo.setAttribute("name", "respostaObjetiva");
			campo.setAttribute("style", "width: 355px");
			campo.setAttribute("id", 'ro_'+idInputObjetiva);
			d.appendChild(campo);

			var imagem = document.createElement("img");
			imagem.setAttribute("src", "<@ww.url value="/imgs/delete.gif"/>");
			var str = "d";
			imagem.setAttribute("onclick", "remResposta('"+str.concat(idInputObjetiva)+"')");
			imagem.setAttribute("style", "cursor: pointer");
			d.appendChild(imagem);
		}

	 	document.getElementById("maisRespostas").appendChild(d);
	 	document.getElementById('ro_'+idInputObjetiva).focus();
	 	idInputObjetiva++;
	}

	function addMultiplaRespostas()
	{
	 	var d = document.createElement("div");
	 	d.setAttribute("id", "d" + idInputObjetivaMultipla);

		var num = document.createElement("div");
		d.appendChild(num);

		if(navigator.appName=='Microsoft Internet Explorer')
		{
			var campo = document.createElement("<input type='input' id='rm_"+idInputObjetivaMultipla+"' name='multiplaResposta' style='width: 355px'>");
			d.appendChild(campo);
			var str = "d" + idInputObjetivaMultipla;
			var imagem = document.createElement("<img src='<@ww.url value="/imgs/delete.gif"/>' onClick=remMultiplaResposta('"+ str +"'); style='cursor: hand'>");
			d.appendChild(imagem);
		}
		else
		{
			var campo = document.createElement("input");
			campo.setAttribute("type", "input");
			campo.setAttribute("name", "multiplaResposta");
			campo.setAttribute("style", "width: 355px");
			campo.setAttribute("id", 'rm_'+idInputObjetivaMultipla);
			d.appendChild(campo);

			var imagem = document.createElement("img");
			imagem.setAttribute("src", "<@ww.url value="/imgs/delete.gif"/>");
			var str = "d";
			imagem.setAttribute("onclick", "remMultiplaResposta('"+str.concat(idInputObjetivaMultipla)+"')");
			imagem.setAttribute("style", "cursor: pointer");
			d.appendChild(imagem);
		}

	 	document.getElementById("maisMultiplasRespostas").appendChild(d);
	 	document.getElementById('rm_'+idInputObjetivaMultipla).focus();
	 	idInputObjetivaMultipla++;
	}

	function remResposta(elemento)
	{
		var resp = document.getElementById("maisRespostas");
		var elem = document.getElementById(elemento);
   	 	resp.removeChild(elem);
	}

	function remMultiplaResposta(elemento)
	{
		var resp = document.getElementById("maisMultiplasRespostas");
		var elem = document.getElementById(elemento);
   	 	resp.removeChild(elem);
	}

	function remResposta2(elemento)
	{
		var resp = document.getElementById("respostasSugeridas");
		var elem = document.getElementById(elemento);
   	 	resp.removeChild(elem);
	}

	function remResposta3(elemento)
	{
		var resp = document.getElementById("respostasEdicao");
		var elem = document.getElementById(elemento);
   	 	resp.removeChild(elem);
	}

	function remResposta6(elemento)
	{
		var resp = document.getElementById("multiplaRespostasEdicao");
		var elem = document.getElementById(elemento);
   	 	resp.removeChild(elem);
	}

	function remResposta4(elemento)
	{
		var resp = document.getElementById("respostaInsert");
		var elem = document.getElementById(elemento);
   	 	resp.removeChild(elem);
	}

	function remResposta5(elemento)
	{
		var resp = document.getElementById("multiplaRespostaInsert");
		var elem = document.getElementById(elemento);
   	 	resp.removeChild(elem);
	}

	function mudaObjetiva(valor) {
		if (valor) {
			document.getElementById('respostaInsert').style.display = 'none';
			document.getElementById('respostasSugeridas').style.display = 'block';
			document.getElementById('respostaSugerida').value = 1;
		} else {
			document.getElementById('respostaInsert').style.display = 'block';
			document.getElementById('respostasSugeridas').style.display = 'none';
			document.getElementById('respostaSugerida').value = 0;
		}
	}


</script>
<#assign validarCampos="return validaForm();"/>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" validate="true" method="POST">
		<@ww.div id="aspectoContainer"></@ww.div>
			Aspecto:<br>
		<#if pergunta.id?exists && pergunta.aspecto?exists && pergunta.aspecto.nome?exists>
			<input name="pergunta.aspecto.nome" value="${pergunta.aspecto.nome}" id="aspecto" style="width: 200px;" maxLength="100" />
		<#else>
			<input name="pergunta.aspecto.nome" id="aspecto" style="width: 200px;" maxLength="100" />
		</#if>

		<#if pergunta.id?exists>
			<a href="../aspecto/list.action?questionario.id=${questionario.id}&pergunta.id=${pergunta.id}"><img src="<@ww.url value="/imgs/agrupar.gif"/>" title="Aspectos" width="16" height="16" /></a>
		<#else>
			<a href="../aspecto/list.action?questionario.id=${questionario.id}"><img src="<@ww.url value="/imgs/agrupar.gif"/>" title="Aspectos" width="16" height="16" /></a>
		</#if>

			<span class="hotspot" id="help_aspecto" ><img src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" /></span>
		<br>
		<br>
		<@ww.textarea label="Pergunta" name="pergunta.texto" id="texto" required="true" cssStyle="height:30px;"/>
		<@ww.select label="Tipos de Respostas" name="pergunta.tipo" id="tipo" list="tipoPerguntas" cssStyle="width: 200px;" required="true" onchange="exibePorTipo();"/>
		<#-- Perguntas Objetivas -->
		<@ww.div id="divRespostas" cssClass="divTipoResposta">
			<#if pergunta.id?exists && pergunta.respostas?exists>
				<@ww.div id="respostasEdicao">
					Opção de Resposta:
					<br>
					<#list pergunta.respostas as respostaLista>
						<@ww.div id="respostaObjetiva${respostaLista.ordem}">
							<input name="respostaObjetiva" id="respostaObjetiva" value="${respostaLista.texto}" style="width: 355px;"/><img src="<@ww.url value="/imgs/delete.gif"/>" width="16" height="16" style="cursor: pointer" onclick="remResposta3('respostaObjetiva${respostaLista.ordem}');"/>
						</@ww.div>
					</#list>
				</@ww.div>
			<#else>
			<#if respostasSugeridas?exists && 0<respostasSugeridas?size>
				<@ww.checkbox label="Sugerir respostas da última pergunta objetiva" name="" id="sugestaoRespostas" onclick="mudaObjetiva(this.checked);" labelposition="left"/>
			</#if> 
				<@ww.div id="respostasSugeridas" style="display:none;">
					<#if respostasSugeridas?exists && 0<respostasSugeridas?size>
						Opções de Resposta:
						<br>
						<#list respostasSugeridas as respostaSugerida>
							<@ww.div id="respostaObjetiva${respostaSugerida.ordem}">
								<input name="respostaObjetivaSugerida" id="respostaObjetivaSugerida" value="${respostaSugerida.texto}" style="width: 355px;"/><img src="<@ww.url value="/imgs/delete.gif"/>" width="16" height="16" style="cursor: pointer" onclick="remResposta2('respostaObjetiva${respostaSugerida.ordem}');"/>
								<br>
							</@ww.div>
						</#list>
					</#if>
				</@ww.div>
				<@ww.div id="respostaInsert">
					Opções de Resposta:
					<br>
					<@ww.div id="respostaObjetivaInsert">
						<input name="respostaObjetiva" id="respostaObjetiva" style="width: 355px;"/><img src="<@ww.url value="/imgs/delete.gif"/>" width="16" height="16" style="cursor: pointer" onclick="remResposta4('respostaObjetivaInsert');"/>
					</@ww.div>
				</@ww.div>
			</#if>
			<@ww.div id="maisRespostas"/>
			<div id="maisRespostasLink"><a href="javascript:addRespostas();" style="text-decoration: none"><img border="0" title="" id="imgCompl" src="<@ww.url value="/imgs/mais.gif"/>" style="margin-top: 5px; margin-bottom: 5px;" align="absmiddle" border="0" width="16" height="16">  Mais uma opção de resposta</a></div>
		</@ww.div>
		<#-- Fim Perguntas Objetivas -->

		<#-- Perguntas Multiplas Escolhas -->
		<@ww.div id="divMultiplasRespostas" cssClass="divTipoResposta">
			<#if pergunta.id?exists && pergunta.respostas?exists>
				<@ww.div id="multiplaRespostasEdicao">
					Opção de Resposta:
					<br>
					<#list pergunta.respostas as respostaLista>
						<@ww.div id="multiplaResposta${respostaLista.ordem}">
							<input name="multiplaResposta" id="multiplaResposta" value="${respostaLista.texto}" style="width: 355px;"/><img src="<@ww.url value="/imgs/delete.gif"/>" width="16" height="16" style="cursor: pointer" onclick="remResposta6('multiplaResposta${respostaLista.ordem}');"/>
						</@ww.div>
					</#list>
				</@ww.div>
			<#else>
				<@ww.div id="multiplaRespostaInsert">
					Opções de Resposta:
					<br>
					<@ww.div id="respostaMultiplaEscolhaInsert">
						<input name="multiplaResposta" id="multiplaResposta" style="width: 355px;"/><img src="<@ww.url value="/imgs/delete.gif"/>" width="16" height="16" style="cursor: pointer" onclick="remResposta5('respostaMultiplaEscolhaInsert');"/>
					</@ww.div>
				</@ww.div>
			</#if>
			<@ww.div id="maisMultiplasRespostas"/>
			<div id="maisMultiplasRespostasLink"><a id="adicionarMultiplaResposta" href="javascript:addMultiplaRespostas();" style="text-decoration: none"><img border="0" title="" id="imgCompl" src="<@ww.url value="/imgs/mais.gif"/>" style="margin-top: 5px; margin-bottom: 5px;" align="absmiddle" border="0" width="16" height="16">  Mais uma opção de resposta</a></div>
		</@ww.div>
		<#-- Fim Perguntas Multiplas Escolhas -->

		<#-- Perguntas Notas -->
		<@ww.div id="notas" cssClass="divTipoResposta">
			<@ww.div> Faixa de Notas:</@ww.div>
			<#if pergunta.notaMinima?exists && pergunta.notaMaxima?exists>
				<input name="pergunta.notaMinima" value="${pergunta.notaMinima}" id="notaMinima" style="width: 50px;" />
				a
				<input name="pergunta.notaMaxima" value="${pergunta.notaMaxima}" id="notaMaxima" style="width: 50px;"/>
			<#else>
				<input name="pergunta.notaMinima" value="1" id="notaMinima" style="width: 30px;" />
				a
				<input name="pergunta.notaMaxima" value="10" id="notaMaxima" style="width: 30px;"/>
			</#if>
		</@ww.div>
		

			<@ww.div id="opcaoComentario" cssClass="divTipoResposta">
				<ul>
					<@ww.checkbox label="Solicitar comentário da resposta (especifique abaixo a solicitação)" name="pergunta.comentario" id="checkComentario" onclick="exibeComentario();" labelposition="left"/>
					<li>
						<@ww.div id="coment" cssStyle="display:none;">
							<ul>
								<@ww.textarea name="pergunta.textoComentario" id="textoComentario" value="" cssStyle="height:30px;" />
							</ul>
						</@ww.div>
					</li>
				</ul>
			</@ww.div>
		
		<@ww.hidden name="questionario.id" id="questionario" />
		<@ww.hidden name="pergunta.id" />
		<@ww.hidden name="pergunta.ordem" />
		<@ww.hidden name="ordemSugerida" />
		<@ww.hidden name="respostaSugerida" id="respostaSugerida" />
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="${buttonClass}" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='${voltarAction}'" class="btnVoltar" accesskey="V">
		</button>
	</div>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/forms/pesquisa/perguntaEdit.js?version=${versao}"/>'></script>

</body>
</html>