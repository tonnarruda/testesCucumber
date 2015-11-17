var countColaboradorQuestionarios = 0;
var selecteds = new Array;
var lastSelected;
var activeShift = false;
var activeCtrl = false;

$(function() {
	
	conectAvaliadosAvaliadores();
	
    portletEvents();
	    
	atualizeSelectables();
    
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
	
	$("#avaliados .actions .remove").click(function(){
		$(".ui-selected").each(function(){
			$(".avaliado_"+$(this).attr("id")).remove();
		});
		$(".ui-selected").remove();
	});
	
	$("#avaliados .actions .move-all").click(function(){
		$("#avaliadores .portlet-content ul").each(function() {
			var avaliador = $(this);
			$(".ui-selected").each(function(){
				createAvaliadoForAvaliador(avaliador, $(this));
			});
		});
	});
	
	$("#avaliados .actions .generate-autoavaliacao").click(function(){
		$(".ui-selected").each(function(){
			var avaliador = $("#avaliadores .portlet-content ul[id="+$(this).attr("id")+"]");
			
			if ( avaliador.length == 0 ) 
				createAvaliador( $(this).attr("id"), $(this).find(".nome").text() );
			
			avaliador = $("#avaliadores .portlet-content ul[id="+$(this).attr("id")+"]");
			
			createAvaliadoForAvaliador(avaliador, $(this));
			conectAvaliadosAvaliadores();
			portletEvents();
		});
	});
});

function conectAvaliadosAvaliadores() {
	$( "#avaliadores ul" ).droppable({
      activeClass: "ui-state-default",
      hoverClass: "ui-state-hover",
      accept: ":not(.ui-sortable-helper)",
      drop: function( event, ui ) {
    	createAvaliadoForAvaliador($(this), ui.draggable);
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
	    .find( ".portlet-header" )
	      .addClass( "ui-widget-header ui-corner-all" )
	      .prepend( "<span class='ui-icon ui-icon-minusthick portlet-toggle'></span>");
	
	$(".new-portlet").find(".portlet-toggle").click(function() {
	    var icon = $( this );
	    icon.toggleClass( "ui-icon-minusthick ui-icon-plusthick" );
	    icon.closest( ".portlet" ).find( ".portlet-content" ).toggle();
	});
	
	$(".new-portlet").removeClass("new-portlet");
}

function atualizeSelectables() {
	$("#selectable li").not(".ui-selectable").click(function(event){
		if (activeShift) {
			var elements = $("#selectable li");
			if (typeof lastSelected == "undefined") {
					selecteds.push($(this).attr("id"));
					$(this).addClass("ui-selected");
			} else {
	    		var lastElement = $("#selectable li[id="+lastSelected+"]");
	    		var element = $(this);
	    		
	    		elements = elements.splice(elements.index(lastElement), elements.index(element) - elements.index(lastElement) + 1);
	    		
	    		$(elements).each(function(){
	    			if( selecteds.indexOf($(this).attr("id")) == -1 ) {
	    				selecteds.push($(this).attr("id"));
	    				$(this).addClass("ui-selected");
	    			}
	    		});
			}
		} else {
			if( selecteds.indexOf($(this).attr("id")) == -1 ) {
				selecteds.push($(this).attr("id"));
	    		$(this).addClass("ui-selected");
	    	} else {
	    		selecteds.splice(selecteds.indexOf($(this).attr("id")), 1);
		    	$(this).removeClass("ui-selected");
	    	}
		}
		
		$(".ui-widget-header.actions").toggle(selecteds.length > 0);
		$(".ui-widget-header.title").toggle(!(selecteds.length > 0));
		
		lastSelected= $(this).attr("id");
		console.log(selecteds);
	});
	$("#selectable li").not(".ui-selectable").addClass("ui-selectable");
}

function createAvaliador(id, nome) {
	$("#avaliadores .column").append('<div class="portlet avaliador_'+ id +'">' +
	  		 '<div class="portlet-header">' + nome + 
	  		 '</div>' +
	  		 '<div class="portlet-content">' +
	  		 	'<ul id="'+ id +'">' +
	  		 			'<input type="hidden" name="avaliadores" value="'+id+'"/>' +
		        		'<li class="placeholder">Arraste os avaliados até aqui</li>' + 
		      	'</ul>' +
	  		 '</div>' +
	  	'</div>');
}

function createAvaliadoForAvaliador(avaliadorUlTag, avaliadorLiTag) {
	if( ( !permiteAutoAvaliacao && avaliadorLiTag.attr('id') != avaliadorUlTag.attr('id') ) || permiteAutoAvaliacao ) {
        $( avaliadorUlTag ).find( ".placeholder" ).remove();
        if( $(avaliadorUlTag).find(".avaliado_"+ avaliadorLiTag.attr('id')).length == 0 ) {
        	$("<li class='avaliado_"+avaliadorLiTag.attr('id')+"'></li>").text( avaliadorLiTag.find(".nome").text() ).appendTo( avaliadorUlTag );
        	$( avaliadorUlTag ).find(".avaliado_"+ avaliadorLiTag.attr('id')).append('<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].colaborador.id" value="' + avaliadorLiTag.attr("id") + '"/>' +
					        							   '<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].avaliador.id" value="' + $(avaliadorUlTag).attr("id") + '"/>' +
					        							   '<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].avaliacao.id" value="' + avaliacaoId + '"/>' +
					        							   '<input type="hidden" name="colaboradorQuestionarios['+countColaboradorQuestionarios+'].avaliacaoDesempenho.id" value="' + avaliacaoDesempenhoId + '"/>');
        	$( avaliadorUlTag ).find(".avaliado_"+ avaliadorLiTag.attr('id')).prepend('<span class="ui-icon ui-icon-closethick"></span>');
        	
        	$("#avaliadores ul li .ui-icon-closethick").not(".disabled").click(function(){
		    	if( $(this).parent().parent().find("li").length == 1 )
		    		$(this).parent().parent().append('<li class="placeholder">Arraste os avaliados até aqui</li>');
		    		
		    	$(this).parent().remove();
		    });
		    
        	countColaboradorQuestionarios++;
        }
    } else if ( !permiteAutoAvaliacao ) {
    	$("<div>A avaliação não permite autoavaliação</div>").dialog({
    		modal: true,
    		height: '120',
    		title: "Aviso",
    		buttons: { "Ok": function() { $( this ).dialog( "close" );} }
    	});
    }
}
