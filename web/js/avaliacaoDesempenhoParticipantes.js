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
    
    $(".portlet-header .pesoAvaliador").each(function(){
    	$(this).val($(this).parents(".portlet").find(".peso:eq(0)").val());
    	if ($(this).val() == "")
    		$(this).val(1);
    	
    	if ( $(this).parents(".portlet").find(".peso").length == 0 && $(this).parents(".portlet").find(".portlet-content .pesoAvaliador").length > 0)
    		$(this).val($(this).parents(".portlet").find(".portlet-content .pesoAvaliador:eq(0)").val());
    });
    
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
		$(this).parents(".box").find(".ui-selectable").addClass("ui-selected");
		
		if ( $(this).parents(".box").find(".ui-selected").length > 0 ) {
			$(this).parents(".box").find(".ui-widget-header.actions .only-selectables").removeClass("disabled");
			if ($(this).parents(".box").find(".ui-selected").hasClass("has-respondida")) {
				$(this).parents(".box").find(".actions .remove").addClass("disabled");
			}
		}
	});
	
	$(".actions .configure-pesos").click(function(){
		$("#avaliadores .option").not(".configure-pesos").toggleClass("inactive");
		$(this).toggleClass("active");
		
		$("#avaliadores-list > .portlet").toggleClass("ui-selectable");
		
		$(".pesoAvaliador").toggle();
		$(".portlet-toggle").toggle();
	});
	
	
	$(".pesoAvaliador").live("keypress", function(event) {
		return(somenteNumeros(event,''));
	});
	$(".pesoAvaliador").live("keyup", function() {
		if ( $(this).parents(".portlet").find(".peso").length == 0 )
			$(this).parents(".portlet").find(".pesoAvaliador").val($(this).val());
	});
	
	$(".pesoAvaliador").live("click", function(event) { event.stopPropagation(); });
	$(".portlet-header .pesoAvaliador").live("keyup", function(event) { 
		$(this).parents(".portlet").find(".peso").val($(this).val());
	});
	
	$(".actions .unselect-all").click(function(){
		$(this).parents(".box").find(".ui-selectable").removeClass("ui-selected");
		
		$(this).parents(".box").find(".ui-widget-header.actions .only-selectables").addClass("disabled");
	});
	
	$("#avaliadores .actions .remove").click(function(){
		if ( !$(this).hasClass("disabled") ) {
			$(this).parents(".box").find(".ui-selected").remove();
			$(this).parents(".box").find(".ui-widget-header.actions .only-selectables").addClass("disabled");
		}
	});
	
	$("#avaliados .actions .remove").click(function(){
		if ( !$(this).hasClass("disabled") ) {
			$(this).parents(".box").find(".ui-selected").each(function(){
				console.log($(this).parent().attr("id"));
				$("#avaliadores .avaliado_" + $(this).attr("id")).remove();
			});
			
			$(this).parents(".box").find(".ui-selected").remove();
			$(this).parents(".box").find(".ui-widget-header.actions .only-selectables").addClass("disabled");
		}
	});
	
	$("#avaliados .actions .move-all").click(function(){
		if ( !$(this).hasClass("disabled") ) {
			if ( $("#avaliadores .portlet-content ul").length > 0 ) {
				$("#avaliadores .portlet-content ul").each(function() {
					var avaliador = $(this);
					$(".ui-selected").each(function(){
						createAvaliadoForAvaliador(avaliador, $(this));
					});
				});
			} else {
				$("<div>Não há avaliadores para relacionar.</div>").dialog({
		    		modal: true,
		    		height: '120',
		    		title: "Aviso",
		    		buttons: { "Ok": function() {
		    			$( this ).dialog( "close" );
		    		} }
		    	});
			}
			notificatedAboutAutoAvaliacao = false;
		}
	});
	
	$("#avaliados .actions .generate-autoavaliacao").click(function(){
		if ( !$(this).hasClass("disabled") ) {
			$(".ui-selected").each(function(){
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
	});
	
	$(".notaProdutividade").live("click", function(event){ event.stopPropagation(); });
	$("#avaliados .actions .produtividade").click(function(event){
		$("#avaliados .option").not(".produtividade").toggleClass("inactive");
		$(this).toggleClass("active");
		$("#avaliados-list > li").toggleClass("ui-selectable");
		$(".notaProdutividade").toggle();
	});
	
	disableParentsRespondida();
});

function conectAvaliadosAvaliadores() {
	$( "#avaliadores ul" ).droppable({
      activeClass: "ui-state-default",
      hoverClass: "ui-state-hover",
      accept: ":not(.ui-sortable-helper)",
      sort: function( event, ui ) {
    	  
      },
      drop: function( event, ui ) {
    	var ulAvaliadores = $(this);
    	if ( $("#avaliados li.ui-selected").length > 0 ) {
	    	$("#avaliados li.ui-selected").each(function(){
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
    
    $( "#avaliados li" ).draggable({
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
	    			if ( !$(this).hasClass("ui-selected") )
	    				$(this).addClass("ui-selected");
	    		});
			}
		} else {
			!$(this).hasClass("ui-selected") ?  $(this).addClass("ui-selected") : $(this).removeClass("ui-selected");
		}
		
		if ( $(idList + " " + item).hasClass("ui-selected") ) {
			$(idList).parents(".box").find(".ui-widget-header.actions .only-selectables").removeClass("disabled");
			if ($(idList).find(".ui-selected").hasClass("has-respondida")) {
				$(idList).parents(".box").find(".actions .remove").addClass("disabled");
			}
		} else
			$(idList).parents(".box").find(".ui-widget-header.actions .only-selectables").addClass("disabled"); 
		
		lastSelected= $(this).attr("id");
		
		event.stopPropagation();
	});
	$(idList + " " + item).not(".ui-selectable").addClass("ui-selectable");
}

function atualizeSelectablesMini() {
	$("#avaliadores li").live("click", function(event){
		if ( !$(this).hasClass("placeholder") && !$(this).hasClass("has-respondida") ) {
			if ( $(this).parents(".box").find("li.ui-selected").length > 0 && $(this).parents("ul").find("li.ui-selected").length == 0 )
				$(this).parents(".box").find("li.ui-selected").removeClass("ui-selected");
			
			$(this).hasClass("ui-selected") ? $(this).removeClass("ui-selected") : $(this).addClass("ui-selected");
			
			$(this).parents(".portlet").find(".mini-actions").toggle( $(this).parents(".portlet").find("li.ui-selected").length > 0 );
		}
		
		event.stopPropagation();
	});
	
	//$("#avaliadores li").not(".ui-selectable").not(".has-respondida").addClass("ui-selectable");
}

function createAvaliador(id, nome) {
	if ( $("#avaliadores .portlet[id="+id+"] ul").length == 0 ) {
		$("#avaliadores .column").append('<div class="portlet ui-selectable" id="'+ id +'">' +
      			 '<input type="hidden" name="participantesAvaliadores['+countParticipantesAvaliadores+'].colaborador.id" value="'+id+'"/>' +
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
					        							   '<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].avaliador.id" value="' + $(avaliadorUlTag).parents(".portlet").attr("id") + '"/>' +
					        							   '<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].avaliacao.id" value="' + avaliacaoId + '"/>' +
					        							   '<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].avaliacaoDesempenho.id" value="' + avaliacaoDesempenhoId + '"/>');
        	
        	console.log(avaliadorLiTag.attr('id'));
        	console.log(avaliadorUlTag.attr('id'));
        	if ( avaliadorLiTag.attr('id') == avaliadorUlTag.attr('id') ) {
        		var pesoAvaliadorVisible = $(".pesoAvaliador").is(":visible");
        		$( avaliadorUlTag ).find(".avaliado_"+ avaliadorLiTag.attr('id')).append('<input type="text" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].pesoAvaliador" class="pesoAvaliador" value="1"/>' );
        		if ( pesoAvaliadorVisible )
	        			$( avaliadorUlTag ).find(".avaliado_"+ avaliadorLiTag.attr('id') + " .pesoAvaliador").show();
        			
        	} else {
        		$( avaliadorUlTag ).find(".avaliado_"+ avaliadorLiTag.attr('id')).append('<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].pesoAvaliador" class="peso" value="' + $(avaliadorUlTag).parents(".portlet").find(".portlet-header .pesoAvaliador").val() + '"/>');
        	}
        		
        	console.log($(avaliadorUlTag).parents(".portlet").find(".portlet-header .pesoAvaliador").val());

        	$("#avaliadores ul li .ui-icon-closethick").not(".disabled").click(function(){
		    	if( $(this).parent().parent().find("li").length == 1 )
		    		$(this).parent().parent().append('<li class="placeholder">Arraste os avaliados até aqui</li>');
		    		
		    	$(this).parent().remove();
		    });
        	
		    
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
