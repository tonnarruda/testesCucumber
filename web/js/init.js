$(function(){
	// Adiciona setas aos submenus que possuem nodes filhos;
	$("#menuDropDown ul li:has(ul)").addClass("subMenuArrow");
	

	// Mantem o menu visivel
	var sticky_navigation_offset_top = $('#menuDropDown').offset().top;
	
	var sticky_navigation = function(){
		var scroll_top = $(window).scrollTop();
		
		if (scroll_top > sticky_navigation_offset_top) { 
			$('#menuDropDown').css({ 'position': 'fixed', 'top':0, 'left':0 });
		} else {
			$('#menuDropDown').css({ 'position': 'relative' }); 
		}
	};
	
	sticky_navigation();
	
	$(window).scroll(function() {
		 sticky_navigation();
	});
	
	
	// Calcula tempo de expiracao da sessao
	var expiracao;
	var horaExpiracao = new Date().getTime() + sessionMaxInactiveInterval;
	var msg = 'Sua sessão expirou.<br /><br />Clique em OK para efetuar o login novamente ou <br />'
	msg += 'clique em CANCELAR* para permanecer visualizando<br />';
	msg += 'esta tela.<br /><br />';
	msg += '(*) optando por permanecer nesta tela, ao tentar realizar<br />alguma operação o login será solicitado.';
	
	var sessionTimer = setInterval(function() {
		expiracao = new Date(horaExpiracao - new Date());

		if (expiracao.getTime() <= 30000)
			$('.expira').css('color', '#F90');

		if (expiracao.getTime() < 1)
		{
			clearInterval(sessionTimer);
			
			jConfirm(msg, 'Sessão Expirada!', function(r) {
			    if(r)
			    {
				    location.href = '<@ww.url includeParams="none" value="/logout.action"/>';
			    }	
			}, true);
			
			return false;
		}

		$('.expira').text($.format.date(expiracao, "mm:ss"));
		
	}, 1000);
	
	
	// Filtro dos checklistboxes
	if ( $('.listCheckBoxFilter').length ) {
		$('.listCheckBoxFilter').keyup(function() {
	        var texto = removerAcento( $( this ).val().toUpperCase() );
		    $( this ).parents( '.listCheckBoxContainer' ).find( ':checkbox' ).each( function() {
		    	 nomeTeste = removerAcento( $( this ).parent( 'label' ).text().toUpperCase() );
				 $( this ).parent().toggle( nomeTeste.indexOf( texto ) >= 0 );
	    	});
		});
	}
});