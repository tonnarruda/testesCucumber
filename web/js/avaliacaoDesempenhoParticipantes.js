var countColaboradorQuestionarios = 0;
var selectedsAvaliados = new Array;
var selectedsAvaliadores = new Array;
var selectedsAvaliadosFromAvaliadores = new Array;
var lastSelected;
var activeShift = false;
var activeCtrl = false;
var notificatedAboutAutoAvaliacao = false;

$(function() {
	
	conectAvaliadosAvaliadores();
	
    portletEvents();
	    
	atualizeSelectables("#avaliados-list", "li", "avaliados");
	atualizeSelectables("#avaliadores-list", ".portlet", "avaliadores");
	atualizeSelectablesMini();
    
	$(".ui-icon-circle-triangle-e").click(function(){
		$('#avaliados, #avaliadores').hide();
		$(this).parent().parent().show();
		$(this).parent().parent().css("width","720px");
		$(this).parent().parent().find(".faixa").toggle();
		$(this).parent().parent().find(".area").toggle();
		$(this).hide();
		$(this).parent().find(".ui-icon-circle-triangle-w").toggle();
		$(".legend").toggle();
	});

	$(".ui-icon-circle-triangle-w").click(function(){
		$('#avaliados, #avaliadores').show();
		$(this).parent().parent().css("width","350px");
		$(this).parent().parent().find(".faixa").toggle();
		$(this).parent().parent().find(".area").toggle();
		$(this).hide();
		$(this).parent().find(".ui-icon-circle-triangle-e").toggle();
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
			$("#avaliadores .portlet-content ul").each(function() {
				var avaliador = $(this);
				$(".ui-selected").each(function(){
					createAvaliadoForAvaliador(avaliador, $(this));
				});
			});
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
			});
			notificatedAboutAutoAvaliacao = false;
		}
	});
	
	disableParentsRespondida();
});

function conectAvaliadosAvaliadores() {
	$( "#avaliadores ul" ).droppable({
      activeClass: "ui-state-default",
      hoverClass: "ui-state-hover",
      accept: ":not(.ui-sortable-helper)",
      drop: function( event, ui ) {
    	createAvaliadoForAvaliador($(this), ui.draggable);
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
	      .prepend( "<span class='ui-icon ui-icon-minusthick portlet-toggle'></span>");
	
	$(".new-portlet").find(".portlet-toggle").click(function(e) {
	    var icon = $( this );
	    icon.toggleClass( "ui-icon-minusthick ui-icon-plusthick" );
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
			
			$(this).parents(".portlet").find(".mini-actions").toggle( $(this).parents(".box").find("li.ui-selected").length > 0 );
		}
		
		event.stopPropagation();
	});
	
	//$("#avaliadores li").not(".ui-selectable").not(".has-respondida").addClass("ui-selectable");
}

function createAvaliador(id, nome) {
	if ( $("#avaliadores .portlet[id="+id+"] ul").length == 0 ) {
		$("#avaliadores .column").append('<div class="portlet" id="'+ id +'">' +
		  		 '<div class="portlet-header">' + nome + 
		  		 '</div>' +
		  		 '<div class="portlet-content">' +
		  		 	'<ul>' +
		  		 			'<input type="hidden" name="avaliadores" value="'+id+'"/>' +
			        		'<li class="placeholder">Arraste os avaliados até aqui</li>' + 
			      	'</ul>' +
		  		 '</div>' +
		  	'</div>');
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
