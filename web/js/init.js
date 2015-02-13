$(function(){
	// Adiciona setas aos submenus que possuem nodes filhos;
	$("#menuDropDown ul li:has(ul)").addClass("subMenuArrow");
	

	// Mantem o menu visivel
	try {
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
	} catch(e) {}
	
	
	// Calcula tempo de expiracao da sessao
	try {
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
					    location.href = baseUrl + 'logout.action';
				    }	
				}, true);
				
				return false;
			}
	
			$('.expira').text($.format.date(expiracao, "mm:ss"));
			
		}, 1000);
	} catch(e) {}
	

	// Últimas notícias
	try {
		if (ultimasNoticias != null && ultimasNoticias.length > 0) {
			var clss;
			
			$(ultimasNoticias).each(function(i, noticia) {
				clss = 'criticidade-' + noticia.criticidade;
				if (!noticia.lida)
					clss += ' unread';
				if (i%2 != 0)
					clss += ' even';
				
				$('#newsList ul:first').append('<li class="' + clss + '" id="noticia-' + noticia.id + '" link="' + noticia.link + '">' + noticia.texto + '</li>');
			});
			
			showQtdNoticiasNaoLidas();
			
			$('#newsIcon').show().click(function(event) {
								event.preventDefault();
								$('#newsList').dialog({ width: 400, height: 200, title: 'Últimas Notícias', position: { my: "right top", at: "left top", of: "#news" } });
							});
			
			$('#newsList li').click(function(event) {
				var that = $(this);
				
				$('#newsDetails').dialog({ 
					modal: true, 
					width: 980, 
					title: that.text(), 
					open: function(event, ui) {
						$('#newsDetails iframe').attr('src', baseUrl + 'detalheNoticia.action?noticia.id=' + that.attr('id').replace('noticia-','') + '&noticia.link=' + that.attr('link'));
						$('#' + that.attr('id')).removeClass('unread');
						showQtdNoticiasNaoLidas();
					},
					close: function(event, ui) { 
						$('#newsDetails iframe').attr('src', 'about:blank');
					} 
				});
			});
			
			if ( pgInicial && $('#newsList li').filter('.criticidade-0.unread').size() > 0 )
				$('#newsIcon').click();
		}
	} catch(e) { console.log(e); }
});

function showQtdNoticiasNaoLidas()
{
	var qtdNaoLidas = $('#newsList li.unread').size();
	
	if ( qtdNaoLidas > 0 )
		$('#newsCount').text( qtdNaoLidas ).show();
	
	$('#newsCount').toggle( qtdNaoLidas > 0 );
}
