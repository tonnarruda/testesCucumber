<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<#if pergunta.id?exists>
		<title>Editar Pergunta da Avaliação</title>
		<#assign formAction="update.action"/>
		<#assign desabilitaTipo="true"/>
	<#else>
		<title>Inserir Pergunta da Avaliação</title>
		<#assign formAction="insert.action"/>
		<#assign desabilitaTipo="false"/>
	</#if>
	
	<#if modeloAvaliacao?exists && modeloAvaliacao == 'S'>
		<#assign tipoAvaliado="candidato"/>
	<#else>
		<#assign tipoAvaliado="colaborador"/>
	</#if>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>

	<style type="text/css">@import url('<@ww.url includeParams="none" value="/css/questionario.css?version=${versao}"/>');</style>
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
			
			exibePorTipo();
		});
		
		function setAspecto(nome)
		{
			$("#aspecto").val(nome);
		}

		function contaResposta(nameResposta)
		{
			var countResposta = 0;
			
			$("input[name='" + nameResposta + "']").each(function(){
				if($(this).val() != "")
					countResposta++;
			});
			
			return countResposta;
		}

		function validaPesos(nameResposta)
		{
			var qtdRespostaComPesoVazio = 0;
			var campoIvalido = false;
		
			$("input[name='" + nameResposta + "']").each(function()
			{
				$(this).css('background', '#FFF');
				
				if($(this).val() == "")
				{
					qtdRespostaComPesoVazio++;
				} 
				else 
				{
					var elementos = $(this).val();
					var qtdElementos =  elementos.length;
					
					for(var contador = 0; contador < qtdElementos; contador++)
					{
						if(elementos.charAt(contador) == '-' && ((qtdElementos > 1 && contador != 0) || (qtdElementos == 1)))
						{
							$(this).css('background', '#FFEEC2');
							campoIvalido = true;
						}
					}
				}	
			});
			
			if(campoIvalido)
			{
				jAlert('Campos inválidos.');
				return false;
			}

			if(qtdRespostaComPesoVazio > 1)
			{
				jAlert('Não é possível inserir mais de uma alternativa com peso vazio.');
				return false;
			}
			
			return true;
		}
		
		function validaQtdAlternativasPerguntaObjetivaEMultiplaEscolha()
		{
			if($("#tipo").val() != ${tipoPerguntas.getObjetiva()} && $("#tipo").val() != ${tipoPerguntas.getMultiplaEscolha()})
				return true;
			
			if($("#tipo").val() == ${tipoPerguntas.getObjetiva()})
			{
				var countRespostaObjetiva = contaResposta('respostaObjetiva');
				
				if ($('#sugerir').is(":checked"))
					countRespostaObjetiva += contaResposta('respostaObjetivaSugerida');
				
				if(!validaPesos('pesoRespostaObjetiva'))
					return false;
			}
			else if($("#tipo").val() == ${tipoPerguntas.getMultiplaEscolha()})
			{
				var countRespostaObjetiva = contaResposta('multiplaResposta');

				if(!validaPesos('pesoRespostaMultipla'))
					return false;
			}
			
			if(countRespostaObjetiva < 2)
			{
				jAlert('A pergunta têm que possuir no mínimo 2 alternativas.');
				return false;
			}
			
			return true;
		}
		
		function validaForm()
		{
			if(!validaQtdAlternativasPerguntaObjetivaEMultiplaEscolha())
				return false;
					
			if(document.getElementById("tipo").value == ${tipoPerguntas.getNota()})
			{
				return validaFormulario('form', new Array('pergunta','notaMaxima','notaMinima'), null);
			}
			else
			{
				return validaFormulario('form', new Array('pergunta'), null);
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
				
				$("#respostasSugeridas").find("input.pesoRespostaObjetiva").attr("name","pesoRespostaObjetiva");
				
				if($("input[name='respostaObjetiva']").size())
				{ 
					$("input[name='respostaObjetiva']").each(function(){
						if($(this).val() == "")
							$(this).parent('div').remove();
					});
				}
			}
			else 
			{
				$("#respostaInsert").css('display','block');
				$("#respostasSugeridas").css('display','none');
				$("#respostaSugerida").val(0);
				
				$("#respostasSugeridas").find("input.pesoRespostaObjetiva").attr("name","");
			}
		}
		
		var idInputObjetiva = 1;
		var pesoSugerido = 1;
		var pesoSugeridoMultipla = 1;
		var idInputObjetivaMultipla = 1;
		
		function addRespostas()
		{
		 	pesoSugerido++;
			var str = "d" + idInputObjetiva;
					 	
	       	var d = $("<div>").attr("id", str);
			$("<input type='input' id='ro_"+idInputObjetiva+"' name='respostaObjetiva' style='width: 355px'>").appendTo(d);
			$(document.createTextNode(" Peso: ")).appendTo(d);
			$("<input name='pesoRespostaObjetiva' id='pesoRespostaObjetiva_"+idInputObjetiva+"' value='" + pesoSugerido + "' onkeypress=\"return(somenteNumeros(event,'-',this.value));\" style='width:30px;text-align:right;'  maxLength=\"4\">").appendTo(d);

			$("<span> </span> ").appendTo(d); // espaco em branco
			$("<img src='<@ww.url value="/imgs/delete.gif"/>' onClick=$('#"+ str +"').remove(); style='cursor: pointer'>").appendTo(d);
			
			if ($("#respostasEdicao").size() > 0) $("#respostasEdicao").append(d);
			if ($("#maisRespostas").size() > 0) $("#maisRespostas").append(d);
		 	$('#ro_'+idInputObjetiva).focus();
		 	idInputObjetiva++;
		}
		
		function addMultiplaRespostas()
		{
		 	var str = "d" + idInputObjetivaMultipla;

		 	var d = $("<div>").attr("id", str);
		 	$("<input type='input' id='multiplaResposta_"+idInputObjetivaMultipla+"' name='multiplaResposta' style='width: 355px'>").appendTo(d);
		 	$(document.createTextNode(" Peso: ")).appendTo(d);
		 	$("<input name='pesoRespostaMultipla' id='pesoRespostaMultipla_"+idInputObjetivaMultipla+"' value='" + pesoSugeridoMultipla + "' onkeypress=\"return(somenteNumeros(event,'-',this.value));\" style='width:30px;text-align:right;' maxLength=\"4\">").appendTo(d);
		 	
		 	$("<span> </span> ").appendTo(d); // espaco em branco
		 	$("<img src='<@ww.url value="/imgs/delete.gif"/>' onClick=$('#"+ str +"').remove(); style='cursor: pointer'>").appendTo(d);
		 	
		 	$("#maisMultiplasRespostas").append(d);
		 	$('#multiplaResposta_'+idInputObjetivaMultipla).focus();
		 	
		 	idInputObjetivaMultipla++;
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
		Pergunta:* <br> <@ww.textfield theme="simple" id="pergunta" label="Pergunta" name="pergunta.texto" required="true" cssStyle="width: 350px;"/>
		<img id="criterioTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: 0px;margin-top: 0px;vertical-align: top;"/>
		
		<@ww.select label="Tipo de Resposta" name="pergunta.tipo" id="tipo" list="tipoPerguntas" cssStyle="width: 200px;" required="true" onchange="exibePorTipo();" disabled="${desabilitaTipo}" />
		<#if desabilitaTipo == "true">
			<@ww.hidden id="tipo" name="pergunta.tipo" />
		</#if>
		
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
							<#assign pesoSugerido=""/>
						</#if>
						
						<@ww.div id="respostaObjetiva${respostaLista.ordem}">
							<input name="respostaObjetiva" id="ro_${respostaLista.ordem}" value="${respostaLista.texto}" style="width: 355px;"/>
							Peso: <input name="pesoRespostaObjetiva" id="pesoRespostaObjetiva_${respostaLista.ordem}" value="${pesoSugerido}" onkeypress="return(somenteNumeros(event,'-',this.value));" style="width:30px;text-align:right;" maxLength="4"/>
							<img src="<@ww.url value="/imgs/delete.gif"/>" width="16" height="16" style="cursor: pointer" onclick="$('#respostaObjetiva' + ${respostaLista.ordem}).remove();"/>
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
								<#assign pesoSugeridoEdit=respostaSugerida.peso/>
							<#else>
								<#assign pesoSugeridoEdit=""/>
							</#if>
						
							<@ww.div id="respostaObjetiva${respostaSugerida.ordem}">
								<input name="respostaObjetivaSugerida" id="respostaObjetivaSugerida${respostaSugerida.ordem}" value="${respostaSugerida.texto}" style="width: 355px;"/>
								Peso: <input id="pesoRespostaObjetiva_${respostaSugerida.ordem}" value="${pesoSugeridoEdit}" onkeypress="return(somenteNumeros(event,'-',this.value));" style="width:30px;text-align:right;" maxLength="4" class="pesoRespostaObjetiva"/>
								<img src="<@ww.url value="/imgs/delete.gif"/>" width="16" height="16" style="cursor: pointer" onclick="$('#respostaObjetiva' + ${respostaSugerida.ordem}).remove();"/>
								<br>
							</@ww.div>
						</#list>
					</#if>
				</@ww.div>
				
				<@ww.div id="maisRespostas">
					<@ww.div id="respostaInsert">
						Opções de Resposta:
						<br>
					</@ww.div>
						<@ww.div id="d0">
							<input name="respostaObjetiva" id="ro_0" style="width: 355px;"/>
							Peso: <input name="pesoRespostaObjetiva" id="pesoRespostaObjetiva_0" value="1" onkeypress="return(somenteNumeros(event,'-',this.value));" style="width:30px;text-align:right;" maxLength="4"/>
							<img src="<@ww.url value="/imgs/delete.gif"/>" width="16" height="16" style="cursor: pointer" onclick="$('#d0').remove();"/>
					</@ww.div>
				</@ww.div>
				
				
			</#if>
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
							<#assign pesoSugerido=""/>
						</#if>
					
						<@ww.div id="multiplaResposta${respostaLista.ordem}">
							<input name="multiplaResposta" id="multiplaResposta${respostaLista.ordem}" value="${respostaLista.texto}" style="width: 355px;"/>
							Peso: <input name="pesoRespostaMultipla" id="pesoRespostaMultipla_${respostaLista.ordem}" value="${pesoSugerido}" onkeypress="return(somenteNumeros(event,'-',this.value));" style="width:30px;text-align:right;" maxLength="4"/>
							<img src="<@ww.url value="/imgs/delete.gif"/>" width="16" height="16" style="cursor: pointer" onclick="$('#multiplaResposta' + ${respostaLista.ordem}).remove();"/>
						</@ww.div>
					</#list>
				</@ww.div>
			<#else>
				<@ww.div id="multiplaRespostaInsert">
					Opções de Resposta:
					<br>
					<@ww.div id="respostaMultiplaEscolhaInsert">
						<input name="multiplaResposta" id="multiplaResposta" style="width: 355px;"/>
						Peso: <input name="pesoRespostaMultipla" id="pesoRespostaMultipla" value="1" onkeypress="return(somenteNumeros(event,'-',this.value));" style="width:30px;text-align:right;" maxLength="4"/>
						<img src="<@ww.url value="/imgs/delete.gif"/>" width="16" height="16" style="cursor: pointer" onclick="$('#respostaMultiplaEscolhaInsert').remove();"/>
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