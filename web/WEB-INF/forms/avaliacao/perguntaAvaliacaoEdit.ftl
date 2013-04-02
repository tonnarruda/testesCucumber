<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if pergunta.id?exists>
	<title>Editar Critério de Avaliação</title>
	<#assign formAction="update.action"/>
<#else>
	<title>Inserir Critério de Avaliação</title>
	<#assign formAction="insert.action"/>
</#if>

<#if modeloAvaliacao?exists && modeloAvaliacao == 'S'>
	<#assign tipoAvaliado="candidato"/>
<#else>
	<#assign tipoAvaliado="colaborador"/>
</#if>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>

	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/questionario.css"/>');</style>
	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');</style>

	<script type="text/javascript">
		var aspectos = [${aspectos}];
		
		$(function() {
			$("#aspecto").autocomplete(aspectos);
			
			$('#criterioTooltipHelp').qtip({
				content: 'Utilize a expressão #AVALIADO# onde desejar<br> exibir o nome do ${tipoAvaliado} avaliado.'
				,
				style: {
		        	 width: '280px'
		        }
			});
			
			$('#aspectoTooltipHelp').qtip({
				content: '<strong>Aspectos</strong><br />Em uma Avaliação pode ser necessário avaliar vários Aspectos.<br/> Os aspectos podem ou não ser exibidos na hora da aplicação<br/> do Modelo de Avaliação.'
				,
				style: {
		        	 width: '100px'
		        }
			});
			
			$('#aspectoTooltip').qtip(
			{
				content: '${aspectosFormatados}'
				,
         		position: {
                  corner: {
                     tooltip: 'leftMiddle', // Use the corner...
                     target: 'rightMiddle' // ...and opposite corner
                  }
                }
                ,
				hide: {
					fixed: true
				}
				,
		        style: {
		        	padding: '5px 10px' 
		        }
			});
		});
		
		function setAspecto(nome)
		{
			$("#aspecto").val(nome);
		}
		
		function validaForm()
		{
			if(document.getElementById("tipo").value == ${tipoPerguntas.getNota()})
			{
				return validaFormulario('form', new Array('criterio','notaMaxima','notaMinima'), null);
			}
			else
			{
				return validaFormulario('form', new Array('criterio'), null);
			}
		}
			
		function exibePorTipo()
		{
			var tipoPergunta = $("#tipo").val();
		
			if(tipoPergunta == ${tipoPerguntas.getObjetiva()})
			{
				$("#opcaoComentario").css('display','');
				$("#divRespostas").css('display','');
				$("#notas").css('display','none');
				$("#divMultiplasRespostas").css('display','none');
				
				<#if !pergunta.id?exists && !pergunta.respostas?exists>
					$("#respostaInsert").css('display','');
				</#if>
			}
			else if(tipoPergunta == ${tipoPerguntas.getNota()})
			{
				$("#opcaoComentario").css('display','');
				$("#notas").css('display','');
				$("#divRespostas").css('display','none');
				$("#divMultiplasRespostas").css('display','none');
				
				$("#respostaInsert").css('display','none');
				
			}
			else if(tipoPergunta == ${tipoPerguntas.getSubjetiva()})
			{
				document.getElementById("respostasSugeridas").style.display = "none";
			
				$("#opcaoComentario").css('display','none');
				$("#divRespostas").css('display','none');
				$("#divMultiplasRespostas").css('display','none');
				$("#notas").css('display','none');
				
				$("#respostaInsert").css('display','none');
			}
			else if (tipoPergunta == ${tipoPerguntas.getMultiplaEscolha()})
			{
				$("#opcaoComentario").css('display','');
				$("#divMultiplasRespostas").css('display','');
				$("#divRespostas").css('display','none');
				$("#notas").css('display','none');
				<#if !pergunta.id?exists && !pergunta.respostas?exists>
					$("#respostaInsert").css('display','');
				</#if>
			}
		}
		
		function exibeComentario()
		{
			checkComentario = document.getElementById('checkComentario');
			
			if(document.getElementById("tipo").value == ${tipoPerguntas.getSubjetiva()})
				document.getElementById('opcaoComentario').style.display = 'none';
			
			if (checkComentario.checked)
				document.getElementById('coment').style.display = '';
		}
		
		function exibeSugestoesObjetivas(valor) 
		{
			if (valor) 
			{
				$("#respostaInsert").css('display','none');
				$("#respostasSugeridas").css('display','block');
				$("#respostaSugerida").val(1);
			}
			else 
			{
				$("#respostaInsert").css('display','block');
				$("#respostasSugeridas").css('display','none');
				$("#respostaSugerida").val(0);
			}
		}
		
		var idInputObjetiva = 1;
		var pesoSugerido = 1;
		var pesoSugeridoMultipla = 1;
		var idInputObjetivaMultipla = 1;
		
		function addRespostas()
		{
		 	pesoSugerido++;
		 	
	       	var d = $("<div>").attr("id", "d" + idInputObjetiva);
			$("<input type='input' id='ro_"+idInputObjetiva+"' name='respostaObjetiva' style='width: 355px'>").appendTo(d);
			$(document.createTextNode(" Peso: ")).appendTo(d);
			$("<input name='pesoRespostaObjetiva' id='pesoRespostaObjetiva' value='" + pesoSugerido + "' onkeypress=\"return(somenteNumeros(event,''));\" style='width:30px;text-align:right;'>").appendTo(d);

			var str = "d" + idInputObjetiva;
			$("<span> </span> ").appendTo(d); // espaco em branco
			$("<img src='<@ww.url value="/imgs/delete.gif"/>' onClick=remResposta('"+ str +"'); style='cursor: pointer'>").appendTo(d);
	
		 	$("#maisRespostas").append(d);
		 	$('#ro_'+idInputObjetiva).focus();
		 	idInputObjetiva++;
		}
		
		function addMultiplaRespostas()
		{
		 	var d = $("<div>").attr("id", "d" + idInputObjetivaMultipla);
		 	$("<input type='input' id='multiplaResposta_"+idInputObjetivaMultipla+"' name='multiplaResposta' style='width: 355px'>").appendTo(d);
		 	$(document.createTextNode(" Peso: ")).appendTo(d);
		 	$("<input name='pesoRespostaMultipla' id='pesoRespostaMultipla' value='" + pesoSugeridoMultipla + "' onkeypress=\"return(somenteNumeros(event,''));\" style='width:30px;text-align:right;'> ").appendTo(d);
		 	
		 	var str = "d" + idInputObjetivaMultipla;
		 	$("<span> </span> ").appendTo(d); // espaco em branco
		 	$("<img src='<@ww.url value="/imgs/delete.gif"/>' onClick=remMultiplaResposta('"+ str +"'); style='cursor: pointer'>").appendTo(d);
		 	
		 	$("#maisMultiplasRespostas").append(d);
		 	$('#multiplaResposta_'+idInputObjetivaMultipla).focus();
		 	
		 	idInputObjetivaMultipla++;
		}
		
		function remResposta(elemento)
		{
			var resp = document.getElementById("maisRespostas");
			var elem = document.getElementById(elemento);
	   	 	resp.removeChild(elem);
		}
		
		function remResposta3(elemento)
		{
			var resp = document.getElementById("respostasEdicao");
			var elem = document.getElementById(elemento);
	   	 	resp.removeChild(elem);
		}
		
		function remResposta4(elemento)
		{
			var resp = document.getElementById("respostaInsert");
			var elem = document.getElementById(elemento);
	   	 	resp.removeChild(elem);
		}
		
		function remMultiplaResposta(elemento)
		{
			var resp = document.getElementById("maisMultiplasRespostas");
			var elem = document.getElementById(elemento);
	   	 	resp.removeChild(elem);
		}
	</script>
	<#assign validarCampos="return validaForm();"/>
	<#assign pesoSugerido=1/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" method="POST">
	
		Aspecto:<br>
		<@ww.textfield label="Aspecto" name="pergunta.aspecto.nome" id="aspecto" maxLength="100" cssStyle="width: 200px;" theme="simple" />
	
		<img id="aspectoTooltip" src="<@ww.url value="/imgs/agrupar.gif"/>" width="16" height="16" />
		<img id="aspectoTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" />
		
		<br>
		<@ww.select label="Ordem" name="pergunta.ordem" id="ordem" list="ordens" required="true" cssStyle="width:40px;text-align:right;" liClass="liLeft"/>
		<@ww.textfield id="peso" label="Peso" name="pergunta.peso" maxLength="4" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:30px;text-align:right;"/>
		Critério:* <br> <@ww.textfield theme="simple" id="criterio" label="Critério" name="pergunta.texto" required="true" cssStyle="width: 350px;"/>
		<img id="criterioTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" />
		<@ww.select label="Tipos de Respostas" name="pergunta.tipo" id="tipo" list="tipoPerguntas" cssStyle="width: 200px;" required="true" onchange="exibePorTipo();"/>
		
		
		<#-- Perguntas Objetivas -->
		<@ww.div id="divRespostas" cssClass="divTipoResposta">
			<#if pergunta.id?exists && pergunta.respostas?exists>
				<@ww.div id="respostasEdicao">
					Opção de Resposta:
					<br>
					<#list pergunta.respostas as respostaLista>
						
						<#if respostaLista.peso?exists>
							<#assign pesoSugerido=respostaLista.peso/>
						<#else>
							<#assign pesoSugerido="1"/>
						</#if>
						
						<@ww.div id="respostaObjetiva${respostaLista.ordem}">
							<input name="respostaObjetiva" id="respostaObjetiva" value="${respostaLista.texto}" style="width: 355px;"/>
							Peso: <input name="pesoRespostaObjetiva" id="pesoRespostaObjetiva" value="${pesoSugerido}" onkeypress="return(somenteNumeros(event,''));" style="width:30px;text-align:right;"/>
							<img src="<@ww.url value="/imgs/delete.gif"/>" width="16" height="16" style="cursor: pointer" onclick="remResposta3('respostaObjetiva${respostaLista.ordem}');"/>
						</@ww.div>
					</#list>
				</@ww.div>
			<#else>
				<#if respostasSugeridas?exists && 0<respostasSugeridas?size>
					<@ww.checkbox label="Sugerir respostas da última pergunta objetiva" name="" id="sugerir" onclick="exibeSugestoesObjetivas(this.checked);" labelposition="left"/>
			    </#if>
				
				<@ww.div id="respostasSugeridas" style="display:none;">
					<#if respostasSugeridas?exists && 0<respostasSugeridas?size>
						Opções de Resposta:
						<br>
						<#list respostasSugeridas as respostaSugerida>
						
							<#if respostaSugerida.peso?exists>
								<#assign pesoSugerido=respostaSugerida.peso/>
								<#assign pesoSugeridoEdit=respostaSugerida.peso/>
							<#else>
								<#assign pesoSugerido="1"/>
								<#assign pesoSugeridoEdit=""/>
							</#if>
						
							<@ww.div id="respostaObjetiva${respostaSugerida.ordem}">
								<input name="respostaObjetivaSugerida" id="respostaObjetivaSugerida" value="${respostaSugerida.texto}" style="width: 355px;"/>
								Peso: <input name="pesoRespostaObjetiva" id="pesoRespostaObjetiva" value="${pesoSugeridoEdit}" onkeypress="return(somenteNumeros(event,''));" style="width:30px;text-align:right;"/>
								<img src="<@ww.url value="/imgs/delete.gif"/>" width="16" height="16" style="cursor: pointer" onclick="remResposta2('respostaObjetiva${respostaSugerida.ordem}');"/>
								<br>
							</@ww.div>
						</#list>
					</#if>
				</@ww.div>
				<@ww.div id="respostaInsert">
					Opções de Resposta:
					<br>
					<@ww.div id="respostaObjetivaInsert">
						<input name="respostaObjetiva" id="respostaObjetiva" style="width: 355px;"/>
						Peso: <input name="pesoRespostaObjetiva" id="pesoRespostaObjetiva" value="1" onkeypress="return(somenteNumeros(event,''));" style="width:30px;text-align:right;"/>
						<img src="<@ww.url value="/imgs/delete.gif"/>" width="16" height="16" style="cursor: pointer" onclick="remResposta4('respostaObjetivaInsert');"/>
					</@ww.div>
				</@ww.div>
			</#if>
			<@ww.div id="maisRespostas"/>
			<div id="maisRespostasLink"><a href="javascript:addRespostas();" style="text-decoration: none"><img border="0" title="" id="imgCompl" src="<@ww.url value="/imgs/mais.gif"/>" style="margin-top: 5px; margin-bottom: 5px;" align="absmiddle" border="0" width="16" height="16">   Mais uma opção de resposta</a></div>
		</@ww.div>
		<#-- Fim Perguntas Objetivas -->
		
		<#-- Perguntas Multiplas Escolhas -->
		<@ww.div id="divMultiplasRespostas" cssClass="divTipoResposta">
			<#if pergunta.id?exists && pergunta.respostas?exists>
				<@ww.div id="multiplaRespostasEdicao">
					Opção de Resposta:
					<br>
					<#list pergunta.respostas as respostaLista>
					
						<#if respostaLista.peso?exists>
							<#assign pesoSugerido=respostaLista.peso/>
						<#else>
							<#assign pesoSugerido="1"/>
						</#if>
					
						<@ww.div id="multiplaResposta${respostaLista.ordem}">
							<input name="multiplaResposta" id="multiplaResposta" value="${respostaLista.texto}" style="width: 355px;"/>
							Peso: <input name="pesoRespostaMultipla" id="pesoRespostaMultipla" value="${pesoSugerido}" onkeypress="return(somenteNumeros(event,''));" style="width:30px;text-align:right;"/>
							<img src="<@ww.url value="/imgs/delete.gif"/>" width="16" height="16" style="cursor: pointer" onclick="remResposta6('multiplaResposta${respostaLista.ordem}');"/>
						</@ww.div>
					</#list>
				</@ww.div>
			<#else>
				<@ww.div id="multiplaRespostaInsert">
					Opções de Resposta:
					<br>
					<@ww.div id="respostaMultiplaEscolhaInsert">
						<input name="multiplaResposta" id="multiplaResposta" style="width: 355px;"/>
						Peso: <input name="pesoRespostaMultipla" id="pesoRespostaMultipla" value="1" onkeypress="return(somenteNumeros(event,''));" style="width:30px;text-align:right;"/>
						<img src="<@ww.url value="/imgs/delete.gif"/>" width="16" height="16" style="cursor: pointer" onclick="remResposta5('respostaMultiplaEscolhaInsert');"/>
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
				<input name="pergunta.notaMinima" value="${pergunta.notaMinima}" id="notaMinima" style="width: 50px;text-align:right;" />
				a
				<input name="pergunta.notaMaxima" value="${pergunta.notaMaxima}" id="notaMaxima" style="width: 50px;text-align:right;"/>
			<#else>
				<input name="pergunta.notaMinima" value="1" id="notaMinima" style="width: 30px;text-align:right;" />
				a
				<input name="pergunta.notaMaxima" value="10" id="notaMaxima" style="width: 30px;text-align:right;"/>
			</#if>
		</@ww.div>
		<#-- Fim Perguntas Notas -->
		
		<@ww.div id="opcaoComentario" cssClass="divTipoResposta">
			<ul>
				<@ww.checkbox label="Solicitar comentário" name="pergunta.comentario" id="checkComentario" onclick="mostrar(document.getElementById('coment'));" labelposition="left"/>
				<li>
					<@ww.div id="coment" cssStyle="display:none;">
						<ul>
							<@ww.textarea name="pergunta.textoComentario" id="textoComentario" cssStyle="height:30px;" />
						</ul>
					</@ww.div>
				</li>
			</ul>
		</@ww.div>
		
		<@ww.hidden name="avaliacao.id" id="avaliacao" />
		<@ww.hidden name="pergunta.id" />
		<@ww.hidden name="respostaSugerida" id="respostaSugerida" />
		<@ww.hidden name="modeloAvaliacao" />
	</@ww.form>

	<div class="buttonGroup">
		<#if !temCriterioRespondido>
			<button onclick="${validarCampos}" class="btnGravar"></button>
		</#if>
		<button onclick="window.location='../perguntaAvaliacao/list.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}'" class="btnVoltar"></button>
	</div>

	<script type="text/javascript">
		exibePorTipo();
		exibeComentario();
		pesoSugerido = ${pesoSugerido}
	</script>
</body>
</html>