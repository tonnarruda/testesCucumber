var countColaboradorQuestionarios = 0;
var countParticipantesAvaliados = 0;
var countParticipantesAvaliadores = 0;
var selectedsAvaliados = new Array;
var selectedsAvaliadores = new Array;
var selectedsAvaliadosFromAvaliadores = new Array;
var lastSelected;
var activeShift = false;
var activeCtrl = false;
var notificatedAboutAutoAvaliacao = false;

$(function() {
	
    portletEvents();
    
	$(".show-info").click(function(){
		$('#avaliados, #avaliadores').hide();
		$(this).parent().parent().show();
		$(this).parent().parent().addClass("expanded");
		$(this).hide();
		$(this).parent().find(".hide-info").toggle();
		$(".legend").toggle();
	});

	$(".hide-info").click(function(){
		$('#avaliados, #avaliadores').show();
		$(this).parent().parent().removeClass("expanded");
		$(this).hide();
		$(this).parent().find(".show-info").toggle();
		$(".legend").toggle();
	});

	$("#avaliadores ul li .ui-icon-closethick").not(".disabled").click(function(){
		if( $(this).parent().parent().find("li").length == 1)
			$(this).parent().parent().append('<li class="placeholder">Arraste os avaliados até aqui</li>');
			
		$(this).parent().remove();
	});

	$('body').keydown(function(event){
    	if ( event.which == 16 )
    		activeShift = true;
    	if ( event.which == 17 )
    		activeCtrl = true;
    }).keyup(function(event){
    	if ( event.which == 16 )
    		activeShift = false;
    	if ( event.which == 17 )
    		activeCtrl = false;
    });
	
	$(".mini-actions .remove").live("click", function(event) {
		if($(this).find(".colaboradorQuestionarioId").length > 0 )
			$(this).parents(".portlet").append("<input type='hidden' name='colaboradorQuestionariosRemovidos' value='"+$(this).find(".colaboradorQuestionarioId").val()+"' />")
		
		$(this).parents(".portlet").find("li.ui-selected").each(function(){
			if($(this).find(".colaboradorQuestionarioId").length > 0 )
				$("#colaboradorQuestionariosRemovidos").append("<input type='hidden' name='colaboradorQuestionariosRemovidos' value='" + $(this).find('.colaboradorQuestionarioId').val() + "' />");
		});
			
		$(this).parents(".portlet").find("li.ui-selected").remove();
		if($(this).parents(".portlet").find("li").not('.placeholder').length == 0)
			$(this).parents(".portlet").find("ul").append('<li class="placeholder">Arraste os avaliados até aqui</li>');
		
		$(this).parents(".mini-actions").hide();
		event.stopPropagation();
	});
	
	$(".mini-actions .select-all").live("click", function(event) {
		$(this).parents(".portlet").find("li").not(".ui-selected").not(".has-respondida").addClass("ui-selected");
		event.stopPropagation();
	});
	
	$(".mini-actions .unselect-all").live("click", function(event) {
		$(this).parents(".portlet").find("li").removeClass("ui-selected");
		$(this).parents(".mini-actions").hide();
		event.stopPropagation();
	});
	
	$(".actions .select-all").click(function(){
		$(this).parents(".box").find(".column .ui-selectable:visible").addClass("ui-selected");
		
		if ( $(this).parents(".box").find(".column .ui-selected:visible").length > 0 ) {
			$(this).parents(".box").find(".ui-widget-header.actions .only-selectables").removeClass("disabled");
			if ($(this).parents(".box").find(".column .ui-selected:visible").hasClass("has-respondida")) {
				$(this).parents(".box").find(".actions .remove").addClass("disabled");
			}

			$(this).parents(".box").find(".column .ui-selected:visible").each(function(){
				if ( $(this).parents(".box").find(".selecteds .list #"+$(this).attr("id")).length == 0 )
					$(this).parents(".box").find(".selecteds .list").append($(this).clone());
			});
		}
		
		$(this).parents(".box").find(".selecteds").toggle( $(this).parents(".box").find(".selecteds .list li").length > 0 );
		resizeBox(this);
	});
	
	$(".actions .configure-pesos").click(function(){
		$("#avaliadores .option").not(".configure-pesos").toggleClass("inactive");
		$(this).toggleClass("active");
		
		$("#avaliadores-list > .portlet").toggleClass("ui-selectable");
		
		$(".peso").toggle();
		$(".pesoAvaliador").toggle();
		$(".portlet-toggle").toggle();
	});
	
	$(".pesoAvaliador, .peso").live("keypress", function(event) {
		return(somenteNumeros(event,''));
	});
	
	$(".peso").live("keyup", function() {
		$(this).parents("li").find("input").each(function(){
			$(this).attr("name", $(this).attr("nameTmp"));
		});
	});
	
	$(".pesoAvaliador").live("keyup", function() {
		if ( $(this).parents(".portlet").find(".peso").length == 0 )
			$(this).parents(".portlet").find(".pesoAvaliador").val($(this).val());
		
		$(this).parents("li").find("input").each(function(){
			$(this).attr("name", $(this).attr("nameTmp"));
		});
	});
	
	$(".pesoAvaliador, .peso").live("click", function(event) { event.stopPropagation(); });
	$(".portlet-header .pesoAvaliador").live("keyup", function(event) { 
		$(this).parents(".portlet").find(".peso").val($(this).val());
		$(this).parents(".portlet").find("input").each(function(){
			$(this).attr("name", $(this).attr("nameTmp"));
		});
	});
	
	$(".actions .unselect-all").click(function(){
		$(this).parents(".box").find(".column .ui-selected:visible").each(function(){
			$(this).parents(".box").find(".selecteds .list #"+$(this).attr("id")).remove();
		});

		$(this).parents(".box").find(".column .ui-selectable:visible").removeClass("ui-selected");
		
		if ($(this).parents(".box").find(".selecteds .list li").length == 0)
			$(this).parent().parent().find(".selecteds").hide();
		
		resizeBox(this);
		
		$(this).parents(".box").find(".ui-widget-header.actions .only-selectables").addClass("disabled");
	});
	
	$("#avaliadores .actions .remove").click(function(){
		if ( !$(this).hasClass("disabled") && !$(this).hasClass("force-disabled") ) {
			$(this).parents(".box").find(".ui-selected:not('.avaliadoInterno')").each(function(){
				$("#participantesAvaliadosRemovidos").append("<input type='hidden' name='participantesAvaliadoresRemovidos' value='" + $(this).find('.participanteAvaliadorId').val() + "' />");
				$(this).find(".colaboradorQuestionarioId").each(function(){
					$("#colaboradorQuestionariosRemovidos").append("<input type='hidden' name='colaboradorQuestionariosRemovidos' value='"+$(this).val()+"' />");
				});
			});
			
			$(this).parents(".box").find(".ui-selected").remove();
			$(this).parents(".box").find(".ui-widget-header.actions .only-selectables").addClass("disabled");
			
			$(this).parent().parent().find(".selecteds .list").html("");
			$(this).parent().parent().find(".selecteds").hide();
			resizeBox(this);
		}
	});
	
	$("#avaliados .actions .remove").click(function(){
		if ( !$(this).hasClass("disabled") && !$(this).hasClass("force-disabled") ) {
			$(this).parents(".box").find(".ui-selected").each(function(){
				$("#participantesAvaliadosRemovidos").append("<input type='hidden' name='participantesAvaliadosRemovidos' value='" + $(this).find('.participanteAvaliadoId').val() + "' />");
				
				$("#avaliadores .avaliado_" + $(this).attr("id")).each(function(){
					$("#colaboradorQuestionariosRemovidos").append("<input type='hidden' name='colaboradorQuestionariosRemovidos' value='" + $(this).find('.colaboradorQuestionarioId').val() + "' />");
				});
				
				$("#avaliadores .avaliado_" + $(this).attr("id")).remove();
			});
			
			$(this).parents(".box").find(".ui-selected").remove();
			$(this).parents(".box").find(".ui-widget-header.actions .only-selectables").addClass("disabled");
			
			$(this).parent().parent().find(".selecteds .list").html("");
			$(this).parent().parent().find(".selecteds").hide();
			resizeBox(this);
		}
	});
	
	$("#avaliados .actions .move .for-all").click(function(){
		if ( !$(this).hasClass("disabled") && !$(this).hasClass("force-disabled") ) {
			msg = "Não há avaliadores para relacionar.";
			qtdMenor = true;
			qtdRegistros = ($("#avaliados-list .ui-selected").length)*countParticipantesAvaliadores;
			if(qtdRegistros > 2500){
				qtdMenor = false;
				msg = "Não é possível realizar esse procedimento, pois serão vinculados " + $("#avaliados-list .ui-selected").length + " avaliados " +
						"com " + countParticipantesAvaliadores + " avaliadores, gerando " + qtdRegistros + " registros a serem gravados." +
						" Isso poderia causar uma inconsistência.";
			}
			
			if (qtdMenor && $("#avaliadores .portlet-content ul").length > 0 ) {
				processando(urlImagens);
				setTimeout(function() { 
					contador = 1;
					$("#avaliadores .portlet-content ul").each(function() {
						var avaliador = $(this);
						$("#avaliados-list .ui-selected").each(function(){
							createAvaliadoForAvaliador(avaliador, $(this));
						});
	
						if(contador == countParticipantesAvaliadores)
							$('.processando').remove();
						
						contador++;
					});
				}, 800);
			} else {
				$("<div>" + msg +"</div>").dialog({
		    		modal: true,
		    		height: 160,
		    		width: 450,
		    		title: "Aviso",
		    		buttons: { "Ok": function() {
		    			$( this ).dialog( "close" );
		    		} }
		    	});
			}
			notificatedAboutAutoAvaliacao = false;
		}
	});

	$("#avaliados .actions .move .for-selecteds").click(function(){
		if ( !$(this).hasClass("disabled") && !$(this).hasClass("force-disabled") ) {
			msg = "Não há avaliadores para relacionar.";
			qtdMenor = true;
			var qtdAvaliadoresSelecionados = $("#avaliadores-list .ui-selected").length;
			qtdRegistros = ($("#avaliados-list .ui-selected").length)*qtdAvaliadoresSelecionados;
			if(qtdRegistros > 2500){
				qtdMenor = false;
				msg = "Não é possível realizar esse procedimento, pois serão vinculados " + $("#avaliados-list .ui-selected").length + " avaliados " +
						"com " + qtdAvaliadoresSelecionados + " avaliadores, gerando " + qtdRegistros + " registros a serem gravados." +
						" Isso poderia causar uma inconsistência.";
			}
			
			if (qtdMenor && $("#avaliadores .portlet.ui-selected .portlet-content ul").length > 0 ) {
				processando(urlImagens);
				setTimeout(function() { 
					contador = 1;
					qtdAvaliadoresSelecionados = $("#avaliadores .portlet.ui-selected .portlet-content ul").size();
					$("#avaliadores .portlet.ui-selected .portlet-content ul").each(function() {
						var avaliador = $(this);
						$("#avaliados-list .ui-selected").each(function(){
							createAvaliadoForAvaliador(avaliador, $(this));
						});
	
						if(contador == qtdAvaliadoresSelecionados)
							$('.processando').remove();
						
						contador++;
					});
				}, 800);
			} else {
				$("<div>" + msg +"</div>").dialog({
		    		modal: true,
		    		height: 160,
		    		width: 450,
		    		title: "Aviso",
		    		buttons: { "Ok": function() {
		    			$( this ).dialog( "close" );
		    		} }
		    	});
			}
			notificatedAboutAutoAvaliacao = false;
		}
	});
	
	$("#avaliados .actions .move").click(function(){
		if ( !$(this).hasClass("disabled") && !$(this).hasClass("force-disabled") )
			$(this).toggleClass("enabled");
	});

	$(".actions .pesquisar").click(function(){
		$(this).parents(".box").find(".option").not(".pesquisar").not(".select-all").not(".unselect-all").toggleClass("inactive");
		$(this).parents(".box").find(".option").not(".pesquisar").not(".select-all").not(".unselect-all").toggleClass("force-disabled");
		$(this).parents(".box").find(".box-search").toggle();
		$(this).toggleClass("active");
		
		$(this).parents(".box").find(".column").find(".ui-selectable").show( $(".box-search").is(":visible") );
		$(this).parents(".box").find(".search").val("").focus();
		
		resizeBox($(this));
	});
	
	/*** Pesquisa ***/
	
	var accent_map = {
            'á':'a',
            'à':'a',
            'â':'a',
            'å':'a',
            'ä':'a',
            'a':'a',
            'ã':'a',
            'ç':'c',
            'é':'e',
            'è':'e',
            'ê':'e',
            'ë':'e',
            'í':'i',
            'ì':'i',
            'î':'i',
            'ï':'i',
            'ñ':'n',
            'ó':'o',
            'ò':'o',
            'ô':'o',
            'ö':'o',
            'õ':'o',
            'ú':'u',
            'ù':'u',
            'û':'u',
            'ü':'u',};


	String.prototype.replaceEspecialChars = function() {
        var ret = '', s = this.toString();
        if (!s) { return ''; }
        for (var i=0; i<s.length; i++) {
            ret += accent_map[s.charAt(i)] || s.charAt(i);
        }
        return ret;
	};

    String.prototype.contains = function(otherString) {
        return this.toString().indexOf(otherString) !== -1;
    };

    $.extend($.expr[':'], {

        'contains-IgnoreAccents' : function(elemt, idx, math) {
            
            var expression1 = math[3].toLowerCase(),
                semAcent1 = expression1.replaceEspecialChars(),
                expression2 = elemt.innerHTML.toLowerCase(),
                semAcent2 = expression2.replaceEspecialChars();

            return semAcent2.contains(semAcent1);             
        }
    });
	
    $(".search").keyup(function(e){
    	$(this).parents(".box").find(".column").find(".ui-selectable").hide();
    	$(this).parents(".box").find(".column").find(".ui-selectable .nome:contains-IgnoreAccents('"+$(this).val()+"')").parents(".ui-selectable").show();
    });
	
    /********/
	
	$("#avaliados .actions .generate-autoavaliacao").click(function(){
		if ( !$(this).hasClass("disabled") && !$(this).hasClass("force-disabled") ) {
			$(this).parents(".box").find(".ui-selected").each(function(){
				createAvaliador( $(this).attr("id"), $(this).find(".nome").text() );
				
				var avaliador = $("#avaliadores .portlet[id="+$(this).attr("id")+"] ul");
				
				createAvaliadoForAvaliador(avaliador, $(this));
				conectAvaliadosAvaliadores();
				portletEvents();
				
				var pesoAvaliadorVisible = $(".pesoAvaliador").is(":visible");
		    	$(".portlet[id="+$(this).attr("id")+"] .pesoAvaliador").toggle(pesoAvaliadorVisible);
		    	$(".portlet[id="+$(this).attr("id")+"] .portlet-toggle").toggle(!pesoAvaliadorVisible);
			});
			notificatedAboutAutoAvaliacao = false;
		}
	});
	
	$(".notaProdutividade").live("keypress", function(event) {
		return somenteNumeros(event,',') ;
	}).live("keyup", function(){
		if ( parseFloat($(this).val().replace(",", ".") == "" ? 0 : $(this).val().replace(",", ".")) > 10 )
			$(this).val(Math.min(Math.max( parseFloat($(this).val().replace(",", ".") == "" ? 0 : $(this).val().replace(",", ".")) , 0), 10));
		
		$(this).parents("li").find("input").each(function(){
			$(this).attr("name", $(this).attr("nameTmp"));
		});
	});
	
	$(".notaProdutividade").live("click", function(event){ event.stopPropagation(); });
	$("#avaliados .actions .produtividade").click(function(event){
		if ( !$(this).hasClass("disabled") && !$(this).hasClass("force-disabled") ) {
			$("#avaliados .option").not(".produtividade").toggleClass("inactive");
			$(this).toggleClass("active");
			$("#avaliados-list > li").toggleClass("ui-selectable");
			$(".notaProdutividade").toggle();
		}
	});
	
	$(".selecteds .toggle").click(function(){
		$(this).parent().find(".list").toggle();
		$(this).find(".up").toggle();
		$(this).find(".down").toggle();
		
		resizeBox(this);
	});
	
	disableParentsRespondida();
});

function resizeBox(element) {
	var box = $(element).parents(".box");
	var height = 500;
	if ( $(box).find(".box-search").is(":visible") )
		height = height - 32; 
	if ( $(box).find(".selecteds").is(":visible") )
		height = height - $(box).find(".selecteds").height(); 
		
	$(box).find(".column").height( height );
}

function conectAvaliadosAvaliadores() {
	$( "#avaliadores ul" ).droppable({
      activeClass: "ui-state-default:visible",
      hoverClass: "ui-state-hover",
      accept: ":not(.ui-sortable-helper)",
      sort: function( event, ui ) {
      },
      drop: function( event, ui ) {
    	  
    	var ulAvaliadores = $(this);
    	if ( $("#avaliados .column li.ui-selected").length > 0 ) {
	    	$("#avaliados .column li.ui-selected").each(function(){
	    		createAvaliadoForAvaliador(ulAvaliadores, $(this));
	    	});
    	} else {
    		createAvaliadoForAvaliador(ulAvaliadores, ui.draggable);
    	}
    	notificatedAboutAutoAvaliacao = false;
      }
    }).sortable({
      revert: true,
      items: "li:not(.placeholder)",
      sort: function() {
        $( this ).removeClass( "ui-state-default");
      }
    });
    
    $( "#avaliados .column li" ).draggable({
      connectToSortable: "#sortable ul",
      helper: "clone",
      revert: "invalid"
    });
}

function portletEvents() {
	$(".portlet").not(".ui-widget")
	    .addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all new-portlet" )
	    .find( ".portlet-header" ).not(".mini-actions")
	      .addClass( "ui-widget-header ui-corner-all" )
	      .prepend( "<span class='ui-icon ui-icon-minusthick portlet-toggle hide-when-expand'></span>");
	
	$(".new-portlet").find(".portlet-toggle").click(function(e) {
	    var icon = $( this );
	    icon.toggleClass( "ui-icon-minusthick ui-icon-plusthick hide-when-expand" );
	    icon.closest( ".portlet" ).find( ".portlet-content" ).toggle();
	    
	    e.stopPropagation();
	});
	
	$(".new-portlet").removeClass("new-portlet");
}

function cleanSelectedItem(item) {
	var $newTag = $("<li class='ui-selectable' id='"+$(item).attr('id')+"'></li>");
	$newTag.append($(item).find(".nome").clone());
	
	return $newTag;
}

function atualizeSelectables(idList, item, type) {
	$(idList + " " + item).live("click", function(event){
		if (activeShift) {
			var elements = $(idList + " " + item);
			if (typeof lastSelected == "undefined") {
				$(this).addClass("ui-selected");
			} else {
	    		var lastElement = $(idList + " "+ item +"[id="+lastSelected+"]");
	    		var element = $(this);
	    		
	    		elements = elements.splice(elements.index(lastElement), elements.index(element) - elements.index(lastElement) + 1);
	    		
	    		$(elements).each(function(){
	    			if ( !$(this).hasClass("ui-selected") ) {
	    				$(this).addClass("ui-selected");
	    				$(idList).parents(".box").find(".selecteds .list").append(cleanSelectedItem($(this)));
	    			}
	    		});
			}
		} else {
			if (!$(this).hasClass("ui-selected")) {
				$(this).addClass("ui-selected");
				$(idList).parents(".box").find(".selecteds .list").append(cleanSelectedItem($(this)));
			} else {
				$(this).removeClass("ui-selected");
				$(idList).parents(".box").find(".selecteds .list #"+$(this).attr("id")).remove();
			}
		}
		
		if ( $(idList + " " + item).hasClass("ui-selected") ) {
			$(idList).parents(".box").find(".ui-widget-header.actions .only-selectables").removeClass("disabled");
			if ($(idList).find(".ui-selected").hasClass("has-respondida")) {
				$(idList).parents(".box").find(".actions .remove").addClass("disabled");
			}
		} else
			$(idList).parents(".box").find(".ui-widget-header.actions .only-selectables").addClass("disabled"); 
		
		$(idList).parents(".box").find(".selecteds").toggle( $(idList).parents(".box").find(".selecteds .list li").length > 0 );
		
		resizeBox($(idList));
		
		event.stopPropagation();
	});
	$(idList + " " + item).not(".ui-selectable").addClass("ui-selectable");
}

function atualizeSelectablesMini() {
	$("#avaliadores .column li").live("click", function(event){
		if ( !$(this).hasClass("placeholder") && !$(this).hasClass("has-respondida") ) {
			if ( $(this).parents(".box").find("li.ui-selected").length > 0 && $(this).parents("ul").find("li.ui-selected").length == 0 )
				$(this).parents(".box").find("li.ui-selected").removeClass("ui-selected");
			
			$(this).hasClass("ui-selected") ? $(this).removeClass("ui-selected") : $(this).addClass("ui-selected");
			
			$(this).parents(".portlet").find(".mini-actions").toggle( $(this).parents(".portlet").find("li.ui-selected").length > 0 );
		}
		
		event.stopPropagation();
	});
}

function createAvaliador(id, nome) {
	if ( $("#avaliadores .portlet[id="+id+"] ul").length == 0 ) {
		$("#avaliadores .column").append('<div class="portlet ui-selectable" id="'+ id +'">' +
      			 '<input type="hidden" name="participantesAvaliadores['+countParticipantesAvaliadores+'].colaborador.id" value="'+id+'"/>' +
      			'<input type="hidden" name="participantesAvaliadores['+countParticipantesAvaliadores+'].colaborador.nome" value="'+nome+'"/>' +
      			 '<input type="hidden" name="participantesAvaliadores['+countParticipantesAvaliadores+'].avaliacaoDesempenho.id" value="'+avaliacaoDesempenhoId+'"/>' +
      			 '<input type="hidden" name="participantesAvaliadores['+countParticipantesAvaliadores+'].tipo" value="R"/>' +
		  		 '<div class="portlet-header">' +
		  		 '	<input type="text" class="pesoAvaliador" value="1" />' +
      			 '	<div class="nome">' + nome + '</div>' + 
      			 '	<div style="clear: both;"></div>' +
		  		 '</div>' +
		  		 '<div class="portlet-header mini-actions" style="background: #F3F3F3; padding: 0; display: none;">' +
		  		 '	<div class="mini-option remove only-selectables disabled" title="Remover selecionados" style="padding: 3px 15px; float: left;">' +
		  		 '		<span class="ui-icon ui-icon-trash" style="float: none;"></span>' +
		  		 '  </div>' +
		  		 '  <div class="mini-option select-all" title="Selecionar todos" style="padding: 2px 15px; float: left;" >' +
		  		 '		<i class="fa fa-check"></i>' +
		  		 '		<i class="fa fa-align-justify"></i>' +
		  		 '  </div>' +
		  		 '  <div class="mini-option unselect-all" title="Retirar selecão de todos" style="padding: 2px 15px; float: left;">' +
		  		 '		<i class="fa fa-lclose"></i>' +
		  		 '		<i class="fa fa-align-justify"></i>' +
		  		 '  </div>' +
		  		 '  <div style="clear: both;"></div>' +
		  		 '</div>' +
		  		 '<div class="portlet-content hide-when-expand">' +
		  		 	'<ul id="'+id+'">' +
		  		 			'<input type="hidden" name="avaliadores" value="'+id+'"/>' +
			        		'<li class="placeholder">Arraste os avaliados até aqui</li>' + 
			      	'</ul>' +
		  		 '</div>' +
		  	'</div>');
		countParticipantesAvaliadores++;
	}
}

function createAvaliadoForAvaliador(avaliadorUlTag, avaliadorLiTag) {
	if( ( !permiteAutoAvaliacao && avaliadorLiTag.attr('id') != avaliadorUlTag.attr('id') ) || permiteAutoAvaliacao ) {
        $( avaliadorUlTag ).find( ".placeholder" ).remove();
        if( $(avaliadorUlTag).find(".avaliado_"+ avaliadorLiTag.attr('id')).length == 0 ) {
        	$("<li class='avaliado_"+avaliadorLiTag.attr('id')+"'></li>").text( avaliadorLiTag.find(".nome").text() ).appendTo( avaliadorUlTag );
        	$( avaliadorUlTag ).find(".avaliado_"+ avaliadorLiTag.attr('id')).append('<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].colaborador.id" value="' + avaliadorLiTag.attr("id") + '"/>' +
        												   '<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].colaborador.nome" value="' + avaliadorLiTag.find(".nome").text() + '"/>' +
					        							   '<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].avaliador.id" value="' + $(avaliadorUlTag).parents(".portlet").attr("id") + '"/>' +
        												   '<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].avaliador.nome" value="' + $(avaliadorUlTag).parents(".portlet").find(".nome").text() + '"/>');
        	if(avaliacaoId!=0)
        		$( avaliadorUlTag ).find(".avaliado_"+ avaliadorLiTag.attr('id')).append('<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].avaliacao.id" value="' + avaliacaoId + '"/>');
        	
        	$( avaliadorUlTag ).find(".avaliado_"+ avaliadorLiTag.attr('id')).append('<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].avaliacaoDesempenho.id" value="' + avaliacaoDesempenhoId + '"/>');
        	
        	if ( avaliadorLiTag.attr('id') == avaliadorUlTag.attr('id') ) {
        		var pesoAvaliadorVisible = $(".pesoAvaliador").is(":visible");
        		$( avaliadorUlTag ).find(".avaliado_"+ avaliadorLiTag.attr('id')).append('<input type="text" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].pesoAvaliador" class="pesoAvaliador" value="1"/>' );
        		if ( pesoAvaliadorVisible )
	        			$( avaliadorUlTag ).find(".avaliado_"+ avaliadorLiTag.attr('id') + " .pesoAvaliador").show();
        			
        	} else {
        		$( avaliadorUlTag ).find(".avaliado_"+ avaliadorLiTag.attr('id')).append('<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].pesoAvaliador" class="peso" value="' + $(avaliadorUlTag).parents(".portlet").find(".portlet-header .pesoAvaliador").val() + '"/>');
        	}
        		
        	$("#avaliadores ul li .ui-icon-closethick").not(".disabled").click(function(){
		    	if( $(this).parent().parent().find("li").length == 1 )
		    		$(this).parent().parent().append('<li class="placeholder">Arraste os avaliados até aqui</li>');
		    		
		    	$(this).parent().remove();
		    });
        	
        	$("#formInsereParticipantes").append();
		    
        	countColaboradorQuestionarios++;
        }
    } else if ( !permiteAutoAvaliacao && !notificatedAboutAutoAvaliacao ) {
    	$("<div>A avaliação não permite autoavaliação</div>").dialog({
    		modal: true,
    		height: '120',
    		title: "Aviso",
    		buttons: { "Ok": function() {
    			$( this ).dialog( "close" );
    		} }
    	});
    	notificatedAboutAutoAvaliacao = true;
    }
}

function disableParentsRespondida(idAvaliado, idAvaliador) {
	$("i.respondida").each(function() {
		$(this).parents("li").addClass("has-respondida");
		$(this).parents(".portlet").addClass("has-respondida");
		$(this).parents(".portlet").removeClass("ui-selectable");
		$("#avaliados #"+ $(this).parents("li").attr("class").replace(/(.*)(avaliado_)([0-9]*)(.*)/g, "$3") ).addClass("has-respondida");
	});
	
	$("#avaliadores .portlet.has-respondida").unbind();
}
