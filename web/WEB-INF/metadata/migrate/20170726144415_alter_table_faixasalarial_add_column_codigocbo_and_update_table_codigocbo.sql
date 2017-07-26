
ALTER TABLE faixasalarial add COLUMN codigocbo character varying(6);--.go

CREATE OR REPLACE FUNCTION inserirCodigoCboNaFaixa() RETURNS integer AS $$    
DECLARE   
mv RECORD;   
BEGIN   
	FOR mv IN   
		select c.id, c.cbocodigo from cargo c
	LOOP  	 
		update faixasalarial set codigocbo = mv.cbocodigo where cargo_id = mv.id;
	END LOOP;  
	RETURN 1;  
END;  
$$ LANGUAGE plpgsql;--.go
select inserirCodigoCboNaFaixa();--.go
drop function inserirCodigoCboNaFaixa();--.go

ALTER TABLE cargo drop COLUMN cbocodigo;--.go

insert into codigocbo (codigo, descricao) select '212320','Administrador em segurança da informação' where not exists( select * from codigocbo where codigo = '212320'); --.go
insert into codigocbo (codigo, descricao) select '515302','Agente de açao social' where not exists( select * from codigocbo where codigo = '515302'); --.go
insert into codigocbo (codigo, descricao) select '351905','Agente de inteligência' where not exists( select * from codigocbo where codigo = '351905'); --.go
insert into codigocbo (codigo, descricao) select '411050','Agente de microcrédito' where not exists( select * from codigocbo where codigo = '411050'); --.go
insert into codigocbo (codigo, descricao) select '411050','Agente de Micro-Crédito' where not exists( select * from codigocbo where codigo = '411050'); --.go
insert into codigocbo (codigo, descricao) select '342550','Agente de proteção de aviação civil' where not exists( select * from codigocbo where codigo = '342550'); --.go
insert into codigocbo (codigo, descricao) select '515130','Agente indígena de saneamento' where not exists( select * from codigocbo where codigo = '515130'); --.go
insert into codigocbo (codigo, descricao) select '515125','Agente indígena de saúde' where not exists( select * from codigocbo where codigo = '515125'); --.go
insert into codigocbo (codigo, descricao) select '351910','Agente técnico de inteligência' where not exists( select * from codigocbo where codigo = '351910'); --.go
insert into codigocbo (codigo, descricao) select '763125','Ajudante de confecçao' where not exists( select * from codigocbo where codigo = '763125'); --.go
insert into codigocbo (codigo, descricao) select '142330','Analista de negócios' where not exists( select * from codigocbo where codigo = '142330'); --.go
insert into codigocbo (codigo, descricao) select '142335','Analista de pesquisa de mercado' where not exists( select * from codigocbo where codigo = '142335'); --.go
insert into codigocbo (codigo, descricao) select '111502','Analista de planejamento e orçamento - apo' where not exists( select * from codigocbo where codigo = '111502'); --.go
insert into codigocbo (codigo, descricao) select '226310','Arteterapeuta' where not exists( select * from codigocbo where codigo = '226310'); --.go
insert into codigocbo (codigo, descricao) select '261430','Audiodescritor' where not exists( select * from codigocbo where codigo = '261430'); --.go
insert into codigocbo (codigo, descricao) select '322250','Auxiliar de enfermagem da estratégia de saúde da família' where not exists( select * from codigocbo where codigo = '322250'); --.go
insert into codigocbo (codigo, descricao) select '514310','Auxiliar de manutençao predial' where not exists( select * from codigocbo where codigo = '514310'); --.go
insert into codigocbo (codigo, descricao) select '322430','Auxiliar em saúde bucal da estratégia de saúde da família' where not exists( select * from codigocbo where codigo = '322430'); --.go
insert into codigocbo (codigo, descricao) select '513440','Barista' where not exists( select * from codigocbo where codigo = '513440'); --.go
insert into codigocbo (codigo, descricao) select '221201','Biomédico' where not exists( select * from codigocbo where codigo = '221201'); --.go
insert into codigocbo (codigo, descricao) select '783230','Bloqueiro (trabalhador portuário)' where not exists( select * from codigocbo where codigo = '783230'); --.go
insert into codigocbo (codigo, descricao) select '271105','Chefe de cozinha' where not exists( select * from codigocbo where codigo = '271105'); --.go
insert into codigocbo (codigo, descricao) select '223293','Cirurgião-dentista da estratégia de saúde da família' where not exists( select * from codigocbo where codigo = '223293'); --.go
insert into codigocbo (codigo, descricao) select '223280','Cirurgiao dentista - dentística' where not exists( select * from codigocbo where codigo = '223280'); --.go
insert into codigocbo (codigo, descricao) select '223284','Cirurgiao dentista - disfunçao temporomandibular e dor orofacial' where not exists( select * from codigocbo where codigo = '223284'); --.go
insert into codigocbo (codigo, descricao) select '223276','Cirurgiao dentista - odontologia do trabalho' where not exists( select * from codigocbo where codigo = '223276'); --.go
insert into codigocbo (codigo, descricao) select '223288','Cirurgiao dentista - odontologia para pacientes com necessidades especiais' where not exists( select * from codigocbo where codigo = '223288'); --.go
insert into codigocbo (codigo, descricao) select '848425','Classificador de graos' where not exists( select * from codigocbo where codigo = '848425'); --.go
insert into codigocbo (codigo, descricao) select '514230','Coletor de resíduos sólidos de serviços de saúde' where not exists( select * from codigocbo where codigo = '514230'); --.go
insert into codigocbo (codigo, descricao) select '515304','Conselheiro tutelar' where not exists( select * from codigocbo where codigo = '515304'); --.go
insert into codigocbo (codigo, descricao) select '262415','Conservador-restaurador de bens  culturais' where not exists( select * from codigocbo where codigo = '262415'); --.go
insert into codigocbo (codigo, descricao) select '262425','Desenhista industrial de produto de moda (designer de moda)' where not exists( select * from codigocbo where codigo = '262425'); --.go
insert into codigocbo (codigo, descricao) select '262420','Desenhista industrial de produto (designer de produto)' where not exists( select * from codigocbo where codigo = '262420'); --.go
insert into codigocbo (codigo, descricao) select '239435','Designer educacional' where not exists( select * from codigocbo where codigo = '239435'); --.go
insert into codigocbo (codigo, descricao) select '423110','Despachante de trânsito' where not exists( select * from codigocbo where codigo = '423110'); --.go
insert into codigocbo (codigo, descricao) select '374145','Dj (disc jockey)' where not exists( select * from codigocbo where codigo = '374145'); --.go
insert into codigocbo (codigo, descricao) select '322135','Doula' where not exists( select * from codigocbo where codigo = '322135'); --.go
insert into codigocbo (codigo, descricao) select '515301','Educador social' where not exists( select * from codigocbo where codigo = '515301'); --.go
insert into codigocbo (codigo, descricao) select '223565','Enfermeiro da estratégia de saúde da família' where not exists( select * from codigocbo where codigo = '223565'); --.go
insert into codigocbo (codigo, descricao) select '214005','Engenheiro ambiental' where not exists( select * from codigocbo where codigo = '214005'); --.go
insert into codigocbo (codigo, descricao) select '214275','Engenheiro ambiental' where not exists( select * from codigocbo where codigo = '214275'); --.go
insert into codigocbo (codigo, descricao) select '222205','Engenheiro de alimentos' where not exists( select * from codigocbo where codigo = '222205'); --.go
insert into codigocbo (codigo, descricao) select '202110','Engenheiro de controle e automação' where not exists( select * from codigocbo where codigo = '202110'); --.go
insert into codigocbo (codigo, descricao) select '226315','Equoterapeuta' where not exists( select * from codigocbo where codigo = '226315'); --.go
insert into codigocbo (codigo, descricao) select '111501','Especialista de políticas públicas e gestao governamental - eppgg' where not exists( select * from codigocbo where codigo = '111501'); --.go
insert into codigocbo (codigo, descricao) select '142610','Especialista em desenvolvimento de cigarros' where not exists( select * from codigocbo where codigo = '142610'); --.go
insert into codigocbo (codigo, descricao) select '223415','Farmacêutico analista clínico' where not exists( select * from codigocbo where codigo = '223415'); --.go
insert into codigocbo (codigo, descricao) select '223420','Farmacêutico de alimentos' where not exists( select * from codigocbo where codigo = '223420'); --.go
insert into codigocbo (codigo, descricao) select '223430','Farmacêutico em saúde pública' where not exists( select * from codigocbo where codigo = '223430'); --.go
insert into codigocbo (codigo, descricao) select '223445','Farmacêutico hospitalar e clínico' where not exists( select * from codigocbo where codigo = '223445'); --.go
insert into codigocbo (codigo, descricao) select '223435','Farmacêutico industrial' where not exists( select * from codigocbo where codigo = '223435'); --.go
insert into codigocbo (codigo, descricao) select '223425','Farmacêutico práticas integrativas e complementares' where not exists( select * from codigocbo where codigo = '223425'); --.go
insert into codigocbo (codigo, descricao) select '223440','Farmacêutico toxicologista' where not exists( select * from codigocbo where codigo = '223440'); --.go
insert into codigocbo (codigo, descricao) select '223650','Fisioterapeuta acupunturista' where not exists( select * from codigocbo where codigo = '223650'); --.go
insert into codigocbo (codigo, descricao) select '223660','Fisioterapeuta  do trabalho' where not exists( select * from codigocbo where codigo = '223660'); --.go
insert into codigocbo (codigo, descricao) select '223655','Fisioterapeuta esportivo' where not exists( select * from codigocbo where codigo = '223655'); --.go
insert into codigocbo (codigo, descricao) select '223630','Fisioterapeuta neurofuncional' where not exists( select * from codigocbo where codigo = '223630'); --.go
insert into codigocbo (codigo, descricao) select '223640','Fisioterapeuta osteopata' where not exists( select * from codigocbo where codigo = '223640'); --.go
insert into codigocbo (codigo, descricao) select '223645','Fisioterapeuta quiropraxista' where not exists( select * from codigocbo where codigo = '223645'); --.go
insert into codigocbo (codigo, descricao) select '223625','Fisioterapeuta respiratória' where not exists( select * from codigocbo where codigo = '223625'); --.go
insert into codigocbo (codigo, descricao) select '223635','Fisioterapeuta traumato-ortopédica funcional' where not exists( select * from codigocbo where codigo = '223635'); --.go
insert into codigocbo (codigo, descricao) select '223815','Fonoaudiólogo educacional' where not exists( select * from codigocbo where codigo = '223815'); --.go
insert into codigocbo (codigo, descricao) select '223820','Fonoaudiólogo em audiologia' where not exists( select * from codigocbo where codigo = '223820'); --.go
insert into codigocbo (codigo, descricao) select '223825','Fonoaudiólogo em disfagia' where not exists( select * from codigocbo where codigo = '223825'); --.go
insert into codigocbo (codigo, descricao) select '223830','Fonoaudiólogo em linguagem' where not exists( select * from codigocbo where codigo = '223830'); --.go
insert into codigocbo (codigo, descricao) select '223835','Fonoaudiólogo em motricidade orofacial' where not exists( select * from codigocbo where codigo = '223835'); --.go
insert into codigocbo (codigo, descricao) select '223840','Fonoaudiólogo em saúde coletiva' where not exists( select * from codigocbo where codigo = '223840'); --.go
insert into codigocbo (codigo, descricao) select '223845','Fonoaudiólogo em voz' where not exists( select * from codigocbo where codigo = '223845'); --.go
insert into codigocbo (codigo, descricao) select '141525','Gerente de turismo' where not exists( select * from codigocbo where codigo = '141525'); --.go
insert into codigocbo (codigo, descricao) select '252605','Gestor em segurança' where not exists( select * from codigocbo where codigo = '252605'); --.go
insert into codigocbo (codigo, descricao) select '517335','Guarda portuário' where not exists( select * from codigocbo where codigo = '517335'); --.go
insert into codigocbo (codigo, descricao) select '261425','Intérprete de língua de sinais' where not exists( select * from codigocbo where codigo = '261425'); --.go
insert into codigocbo (codigo, descricao) select '514330','Limpador de piscinas' where not exists( select * from codigocbo where codigo = '514330'); --.go
insert into codigocbo (codigo, descricao) select '782721','Marinheiro de esporte e recreio' where not exists( select * from codigocbo where codigo = '782721'); --.go
insert into codigocbo (codigo, descricao) select '322116','Massoterapeuta' where not exists( select * from codigocbo where codigo = '322116'); --.go
insert into codigocbo (codigo, descricao) select '223162','Médico da estratégia de saúde da família' where not exists( select * from codigocbo where codigo = '223162'); --.go
insert into codigocbo (codigo, descricao) select '515303','Monitor de dependente químico' where not exists( select * from codigocbo where codigo = '515303'); --.go
insert into codigocbo (codigo, descricao) select '226305','Musicoterapeuta' where not exists( select * from codigocbo where codigo = '226305'); --.go
insert into codigocbo (codigo, descricao) select '223915','Musicoterapeuta' where not exists( select * from codigocbo where codigo = '223915'); --.go
insert into codigocbo (codigo, descricao) select '213440','Oceanógrafo' where not exists( select * from codigocbo where codigo = '213440'); --.go
insert into codigocbo (codigo, descricao) select '242905','Oficial de inteligência' where not exists( select * from codigocbo where codigo = '242905'); --.go
insert into codigocbo (codigo, descricao) select '242910','Oficial técnico de inteligência' where not exists( select * from codigocbo where codigo = '242910'); --.go
insert into codigocbo (codigo, descricao) select '842135','Operador de máquina de preparaçao de matéria prima para produçao de cigarros' where not exists( select * from codigocbo where codigo = '842135'); --.go
insert into codigocbo (codigo, descricao) select '226110','Osteopata' where not exists( select * from codigocbo where codigo = '226110'); --.go
insert into codigocbo (codigo, descricao) select '142340','Ouvidor' where not exists( select * from codigocbo where codigo = '142340'); --.go
insert into codigocbo (codigo, descricao) select '354150','Propagandista de produtos famacêuticos' where not exists( select * from codigocbo where codigo = '354150'); --.go
insert into codigocbo (codigo, descricao) select '251555','Psicólogo acupunturista' where not exists( select * from codigocbo where codigo = '251555'); --.go
insert into codigocbo (codigo, descricao) select '226105','Quiropraxista' where not exists( select * from codigocbo where codigo = '226105'); --.go
insert into codigocbo (codigo, descricao) select '142325','Relações públicas' where not exists( select * from codigocbo where codigo = '142325'); --.go
insert into codigocbo (codigo, descricao) select '515325','Sócioeducador' where not exists( select * from codigocbo where codigo = '515325'); --.go
insert into codigocbo (codigo, descricao) select '322245','Técnico de enfermagem da estratégia de saúde da família' where not exists( select * from codigocbo where codigo = '322245'); --.go
insert into codigocbo (codigo, descricao) select '325115','Técnico em farmácia' where not exists( select * from codigocbo where codigo = '325115'); --.go
insert into codigocbo (codigo, descricao) select '325210','Técnico em nutriçao e dietética' where not exists( select * from codigocbo where codigo = '325210'); --.go
insert into codigocbo (codigo, descricao) select '322425','Técnico em saúde bucal da estratégia de saúde da família' where not exists( select * from codigocbo where codigo = '322425'); --.go
insert into codigocbo (codigo, descricao) select '222215','Tecnólogo em alimentos' where not exists( select * from codigocbo where codigo = '222215'); --.go
insert into codigocbo (codigo, descricao) select '202120','Tecnólogo em automação industrial' where not exists( select * from codigocbo where codigo = '202120'); --.go
insert into codigocbo (codigo, descricao) select '214280','Tecnólogo em construção civil' where not exists( select * from codigocbo where codigo = '214280'); --.go
insert into codigocbo (codigo, descricao) select '214435','Tecnólogo em fabricação mecânica' where not exists( select * from codigocbo where codigo = '214435'); --.go
insert into codigocbo (codigo, descricao) select '271110','Tecnólogo em gastronomia' where not exists( select * from codigocbo where codigo = '271110'); --.go
insert into codigocbo (codigo, descricao) select '142120','Tecnólogo em gestão administrativo- financeira' where not exists( select * from codigocbo where codigo = '142120'); --.go
insert into codigocbo (codigo, descricao) select '142535','Tecnólogo em gestão da tecnologia da informação' where not exists( select * from codigocbo where codigo = '142535'); --.go
insert into codigocbo (codigo, descricao) select '342125','Tecnólogo em logistica de transporte' where not exists( select * from codigocbo where codigo = '342125'); --.go
insert into codigocbo (codigo, descricao) select '202115','Tecnólogo em mecatrônica' where not exists( select * from codigocbo where codigo = '202115'); --.go
insert into codigocbo (codigo, descricao) select '214010','Tecnólogo em meio ambiente' where not exists( select * from codigocbo where codigo = '214010'); --.go
insert into codigocbo (codigo, descricao) select '214615','Tecnólogo em metalurgia' where not exists( select * from codigocbo where codigo = '214615'); --.go
insert into codigocbo (codigo, descricao) select '214745','Tecnólogo em petróleo e gás' where not exists( select * from codigocbo where codigo = '214745'); --.go
insert into codigocbo (codigo, descricao) select '213215','Técnólogo em processos químicos' where not exists( select * from codigocbo where codigo = '213215'); --.go
insert into codigocbo (codigo, descricao) select '262135','Tecnólogo em produção audiovisual' where not exists( select * from codigocbo where codigo = '262135'); --.go
insert into codigocbo (codigo, descricao) select '262130','Tecnólogo em produção fonográfica' where not exists( select * from codigocbo where codigo = '262130'); --.go
insert into codigocbo (codigo, descricao) select '214930','Tecnólogo em produção industrial' where not exists( select * from codigocbo where codigo = '214930'); --.go
insert into codigocbo (codigo, descricao) select '214535','Tecnólogo em produção sulcroalcooleira' where not exists( select * from codigocbo where codigo = '214535'); --.go
insert into codigocbo (codigo, descricao) select '214750','Tecnólogo em rochas ornamentais' where not exists( select * from codigocbo where codigo = '214750'); --.go
insert into codigocbo (codigo, descricao) select '214935','Tecnólogo em segurança do trabalho' where not exists( select * from codigocbo where codigo = '214935'); --.go
insert into codigocbo (codigo, descricao) select '214370','Tecnólogo em telecomunicações' where not exists( select * from codigocbo where codigo = '214370'); --.go
insert into codigocbo (codigo, descricao) select '324125','Tecnólogo oftálmico' where not exists( select * from codigocbo where codigo = '324125'); --.go
insert into codigocbo (codigo, descricao) select '322117','Terapeuta holístico' where not exists( select * from codigocbo where codigo = '322117'); --.go
insert into codigocbo (codigo, descricao) select '223905','Terapeuta ocupacional' where not exists( select * from codigocbo where codigo = '223905'); --.go
insert into codigocbo (codigo, descricao) select '514325','Trabalhador da manutençao de edificaçoes' where not exists( select * from codigocbo where codigo = '514325'); --.go
insert into codigocbo (codigo, descricao) select '122520','Turismólogo' where not exists( select * from codigocbo where codigo = '122520'); --.go
insert into codigocbo (codigo, descricao) select '214130','Urbanista' where not exists( select * from codigocbo where codigo = '214130'); --.go

update codigocbo set codigo = '514320' where codigo = '514210' and not exists (select * from codigocbo where codigo = '514320'); --.go
update faixasalarial set codigocbo = '514320' where codigocbo = '514210'; --.go

update codigocbo set codigo = '514305' where codigo = '514220' and not exists (select * from codigocbo where codigo = '514305'); --.go
update faixasalarial set codigocbo = '514305' where codigocbo = '514220'; --.go

update codigocbo set codigo = '514315' where codigo = '991415' and not exists (select * from codigocbo where codigo = '514315'); --.go
update faixasalarial set codigocbo = '514315' where codigocbo = '991415'; --.go

update codigocbo set codigo = '842125' where codigo = '842305' and not exists (select * from codigocbo where codigo = '842125'); --.go
update faixasalarial set codigocbo = '842125' where codigocbo = '842305'; --.go

update codigocbo set codigo = '223910' where codigo = '223615' and not exists (select * from codigocbo where codigo = '223910'); --.go
update faixasalarial set codigocbo = '223910' where codigocbo = '223615'; --.go

update faixasalarial set codigocbo = '322305' where codigocbo = '322310'; --.go
delete from codigocbo where codigo = '322310'; --.go

update faixasalarial set codigocbo = '514315' where codigocbo = '991410'; --.go
delete from codigocbo where codigo = '991410'; --.go

update codigocbo set codigo = '322116' where codigo = '516135' and not exists (select * from codigocbo where codigo = '322116'); --.go
update faixasalarial set codigocbo = '322116' where codigocbo = '516135'; --.go

update codigocbo set descricao = 'Abatedor' where codigo = '848505'; --.go
update codigocbo set descricao = 'Acabador de calçados' where codigo = '764305'; --.go
update codigocbo set descricao = 'Acabador de embalagens (flexíveis e cartotécnicas)' where codigo = '766305'; --.go
update codigocbo set descricao = 'Acabador de superfícies de concreto' where codigo = '716105'; --.go
update codigocbo set descricao = 'Açougueiro' where codigo = '848510'; --.go
update codigocbo set descricao = 'Acrobata' where codigo = '376205'; --.go
update codigocbo set descricao = 'Adestrador de animais' where codigo = '623005'; --.go
update codigocbo set descricao = 'Administrador' where codigo = '252105'; --.go
update codigocbo set descricao = 'Administrador de banco de dados' where codigo = '212305'; --.go
update codigocbo set descricao = 'Administrador de edifícios' where codigo = '510110'; --.go
update codigocbo set descricao = 'Administrador de fundos e carteiras de investimento' where codigo = '252505'; --.go
update codigocbo set descricao = 'Administrador de redes' where codigo = '212310'; --.go
update codigocbo set descricao = 'Administrador de sistemas operacionais' where codigo = '212315'; --.go
update codigocbo set descricao = 'Administrador em segurança da informação' where codigo = '212320'; --.go
update codigocbo set descricao = 'Advogado' where codigo = '241005'; --.go
update codigocbo set descricao = 'Advogado (áreas especiais)' where codigo = '241030'; --.go
update codigocbo set descricao = 'Advogado da uniao' where codigo = '241205'; --.go
update codigocbo set descricao = 'Advogado de empresa' where codigo = '241010'; --.go
update codigocbo set descricao = 'Advogado (direito civil)' where codigo = '241015'; --.go
update codigocbo set descricao = 'Advogado (direito do trabalho)' where codigo = '241035'; --.go
update codigocbo set descricao = 'Advogado (direito penal)' where codigo = '241025'; --.go
update codigocbo set descricao = 'Advogado (direito público)' where codigo = '241020'; --.go
update codigocbo set descricao = 'Afiador de cardas' where codigo = '721305'; --.go
update codigocbo set descricao = 'Afiador de cutelaria' where codigo = '721310'; --.go
update codigocbo set descricao = 'Afiador de ferramentas' where codigo = '721315'; --.go
update codigocbo set descricao = 'Afiador de serras' where codigo = '721320'; --.go
update codigocbo set descricao = 'Afinador de instrumentos musicais' where codigo = '742105'; --.go
update codigocbo set descricao = 'Afretador' where codigo = '342120'; --.go
update codigocbo set descricao = 'Agenciador de propaganda' where codigo = '354110'; --.go
update codigocbo set descricao = 'Agente comunitário de saúde' where codigo = '515105'; --.go
update codigocbo set descricao = 'Agente de açao social' where codigo = '515302'; --.go
update codigocbo set descricao = 'Agente de defesa ambiental' where codigo = '352205'; --.go
update codigocbo set descricao = 'Agente de direitos autorais' where codigo = '352405'; --.go
update codigocbo set descricao = 'Agente de estaçao (ferrovia e metrô)' where codigo = '342405'; --.go
update codigocbo set descricao = 'Agente de higiene e segurança' where codigo = '254310'; --.go
update codigocbo set descricao = 'Agente de inteligência' where codigo = '351905'; --.go
update codigocbo set descricao = 'Agente de manobra e docagem' where codigo = '215105'; --.go
update codigocbo set descricao = 'Agente de microcrédito' where codigo = '411050'; --.go
update codigocbo set descricao = 'Agente de Micro-Crédito' where codigo = '411050'; --.go
update codigocbo set descricao = 'Agente de pátio' where codigo = '783105'; --.go
update codigocbo set descricao = 'Agente de polícia federal' where codigo = '517205'; --.go
update codigocbo set descricao = 'Agente de proteçao de aeroporto' where codigo = '517305'; --.go
update codigocbo set descricao = 'Agente de proteção de aviação civil' where codigo = '342550'; --.go
update codigocbo set descricao = 'Agente de recrutamento e seleçao' where codigo = '351315'; --.go
update codigocbo set descricao = 'Agente de saúde pública' where codigo = '352210'; --.go
update codigocbo set descricao = 'Agente de segurança' where codigo = '517310'; --.go
update codigocbo set descricao = 'Agente de segurança penitenciária' where codigo = '517315'; --.go
update codigocbo set descricao = 'Agente de trânsito' where codigo = '517220'; --.go
update codigocbo set descricao = 'Agente de vendas de serviços' where codigo = '354120'; --.go
update codigocbo set descricao = 'Agente de viagem' where codigo = '354815'; --.go
update codigocbo set descricao = 'Agente fiscal de qualidade' where codigo = '352310'; --.go
update codigocbo set descricao = 'Agente fiscal metrológico' where codigo = '352315'; --.go
update codigocbo set descricao = 'Agente fiscal têxtil' where codigo = '352320'; --.go
update codigocbo set descricao = 'Agente funerário' where codigo = '516505'; --.go
update codigocbo set descricao = 'Agente indígena de saneamento' where codigo = '515130'; --.go
update codigocbo set descricao = 'Agente indígena de saúde' where codigo = '515125'; --.go
update codigocbo set descricao = 'Agente publicitário' where codigo = '253115'; --.go
update codigocbo set descricao = 'Agente técnico de inteligência' where codigo = '351910'; --.go
update codigocbo set descricao = 'Ajudante de carvoaria' where codigo = '632615'; --.go
update codigocbo set descricao = 'Ajudante de confecçao' where codigo = '763125'; --.go
update codigocbo set descricao = 'Ajudante de despachante aduaneiro' where codigo = '342205'; --.go
update codigocbo set descricao = 'Ajudante de motorista' where codigo = '783225'; --.go
update codigocbo set descricao = 'Ajustador de instrumentos de precisao' where codigo = '741105'; --.go
update codigocbo set descricao = 'Ajustador ferramenteiro' where codigo = '725005'; --.go
update codigocbo set descricao = 'Ajustador mecânico' where codigo = '725010'; --.go
update codigocbo set descricao = 'Ajustador mecânico em bancada' where codigo = '725020'; --.go
update codigocbo set descricao = 'Ajustador mecânico (usinagem em bancada e em máquinas-ferramentas)' where codigo = '725015'; --.go
update codigocbo set descricao = 'Ajustador naval (reparo e construçao)' where codigo = '725025'; --.go
update codigocbo set descricao = 'Alambiqueiro' where codigo = '841705'; --.go
update codigocbo set descricao = 'Alfaiate' where codigo = '763005'; --.go
update codigocbo set descricao = 'Alimentador de linha de produçao' where codigo = '784205'; --.go
update codigocbo set descricao = 'Alinhador de pneus' where codigo = '992105'; --.go
update codigocbo set descricao = 'Almoxarife' where codigo = '414105'; --.go
update codigocbo set descricao = 'Alvejador (tecidos)' where codigo = '761405'; --.go
update codigocbo set descricao = 'Amostrador de minérios' where codigo = '711105'; --.go
update codigocbo set descricao = 'Analista de câmbio' where codigo = '252510'; --.go
update codigocbo set descricao = 'Analista de cobrança (instituiçoes financeiras)' where codigo = '252515'; --.go
update codigocbo set descricao = 'Analista de crédito (instituiçoes financeiras)' where codigo = '252525'; --.go
update codigocbo set descricao = 'Analista de crédito rural' where codigo = '252530'; --.go
update codigocbo set descricao = 'Analista de desenvolvimento de sistemas' where codigo = '212405'; --.go
update codigocbo set descricao = 'Analista de exportaçao e importaçao' where codigo = '354305'; --.go
update codigocbo set descricao = 'Analista de folha de pagamento' where codigo = '413105'; --.go
update codigocbo set descricao = 'Analista de informaçoes (pesquisador de informaçoes de rede)' where codigo = '261215'; --.go
update codigocbo set descricao = 'Analista de leasing' where codigo = '252535'; --.go
update codigocbo set descricao = 'Analista de negócios' where codigo = '142330'; --.go
update codigocbo set descricao = 'Analista de negócios' where codigo = '253120'; --.go
update codigocbo set descricao = 'Analista de pesquisa de mercado' where codigo = '142335'; --.go
update codigocbo set descricao = 'Analista de pesquisa de mercado' where codigo = '253125'; --.go
update codigocbo set descricao = 'Analista de planejamento e orçamento - apo' where codigo = '111502'; --.go
update codigocbo set descricao = 'Analista de produtos bancários' where codigo = '252540'; --.go
update codigocbo set descricao = 'Analista de recursos humanos' where codigo = '252405'; --.go
update codigocbo set descricao = 'Analista de redes e de comunicaçao de dados' where codigo = '212410'; --.go
update codigocbo set descricao = 'Analista de seguros (técnico)' where codigo = '351705'; --.go
update codigocbo set descricao = 'Analista de sinistros' where codigo = '351710'; --.go
update codigocbo set descricao = 'Analista de sistemas de automaçao' where codigo = '212415'; --.go
update codigocbo set descricao = 'Analista de suporte computacional' where codigo = '212420'; --.go
update codigocbo set descricao = 'Analista de transporte em comércio exterior' where codigo = '342105'; --.go
update codigocbo set descricao = 'Analista financeiro (instituiçoes financeiras)' where codigo = '252545'; --.go
update codigocbo set descricao = 'Ancora de rádio e televisao' where codigo = '261705'; --.go
update codigocbo set descricao = 'Antropólogo' where codigo = '251105'; --.go
update codigocbo set descricao = 'Apicultor' where codigo = '613405'; --.go
update codigocbo set descricao = 'Aplicador de asfalto impermeabilizante (coberturas)' where codigo = '715705'; --.go
update codigocbo set descricao = 'Aplicador serigráfico em vidros' where codigo = '752205'; --.go
update codigocbo set descricao = 'Apontador de mao-de-obra' where codigo = '414205'; --.go
update codigocbo set descricao = 'Apontador de produçao' where codigo = '414210'; --.go
update codigocbo set descricao = 'Apresentador de circo' where codigo = '376325'; --.go
update codigocbo set descricao = 'Apresentador de eventos' where codigo = '376305'; --.go
update codigocbo set descricao = 'Apresentador de festas populares' where codigo = '376310'; --.go
update codigocbo set descricao = 'Apresentador de programas de rádio' where codigo = '376315'; --.go
update codigocbo set descricao = 'Apresentador de programas de televisao' where codigo = '376320'; --.go
update codigocbo set descricao = 'Arbitro de atletismo' where codigo = '377210'; --.go
update codigocbo set descricao = 'Arbitro de basquete' where codigo = '377215'; --.go
update codigocbo set descricao = 'Arbitro de futebol' where codigo = '377220'; --.go
update codigocbo set descricao = 'Arbitro de futebol de salao' where codigo = '377225'; --.go
update codigocbo set descricao = 'Arbitro de judô' where codigo = '377230'; --.go
update codigocbo set descricao = 'Arbitro de karatê' where codigo = '377235'; --.go
update codigocbo set descricao = 'Arbitro de poló aquático' where codigo = '377240'; --.go
update codigocbo set descricao = 'Arbitro desportivo' where codigo = '377205'; --.go
update codigocbo set descricao = 'Arbitro de vôlei' where codigo = '377245'; --.go
update codigocbo set descricao = 'Armador de estrutura de concreto' where codigo = '715305'; --.go
update codigocbo set descricao = 'Armador de estrutura de concreto armado' where codigo = '715315'; --.go
update codigocbo set descricao = 'Armazenista' where codigo = '414110'; --.go
update codigocbo set descricao = 'Aromista' where codigo = '325010'; --.go
update codigocbo set descricao = 'Arqueólogo' where codigo = '251110'; --.go
update codigocbo set descricao = 'Arquiteto de edificaçoes' where codigo = '214105'; --.go
update codigocbo set descricao = 'Arquiteto de interiores' where codigo = '214110'; --.go
update codigocbo set descricao = 'Arquiteto de patrimônio' where codigo = '214115'; --.go
update codigocbo set descricao = 'Arquiteto paisagista' where codigo = '214120'; --.go
update codigocbo set descricao = 'Arquiteto urbanista' where codigo = '214125'; --.go
update codigocbo set descricao = 'Arquivista' where codigo = '261305'; --.go
update codigocbo set descricao = 'Arquivista de documentos' where codigo = '415105'; --.go
update codigocbo set descricao = 'Arquivista pesquisador (jornalismo)' where codigo = '261105'; --.go
update codigocbo set descricao = 'Arrematadeira' where codigo = '763305'; --.go
update codigocbo set descricao = 'Artesao modelador (vidros)' where codigo = '752105'; --.go
update codigocbo set descricao = 'Arteterapeuta' where codigo = '226310'; --.go
update codigocbo set descricao = 'Artífice do couro' where codigo = '768305'; --.go
update codigocbo set descricao = 'Artista aéreo' where codigo = '376210'; --.go
update codigocbo set descricao = 'Artista (artes visuais)' where codigo = '262405'; --.go
update codigocbo set descricao = 'Artista de circo (outros)' where codigo = '376215'; --.go
update codigocbo set descricao = 'Ascensorista' where codigo = '514105'; --.go
update codigocbo set descricao = 'Assentador de canalizaçao (edificaçoes)' where codigo = '724105'; --.go
update codigocbo set descricao = 'Assessor de imprensa' where codigo = '261110'; --.go
update codigocbo set descricao = 'Assistente administrativo' where codigo = '411010'; --.go
update codigocbo set descricao = 'Assistente comercial de seguros' where codigo = '351715'; --.go
update codigocbo set descricao = 'Assistente de coreografia' where codigo = '262805'; --.go
update codigocbo set descricao = 'Assistente de laboratório industrial' where codigo = '818105'; --.go
update codigocbo set descricao = 'Assistente de vendas' where codigo = '354125'; --.go
update codigocbo set descricao = 'Assistente social' where codigo = '251605'; --.go
update codigocbo set descricao = 'Assistente técnico de seguros' where codigo = '351720'; --.go
update codigocbo set descricao = 'Assoalhador' where codigo = '716505'; --.go
update codigocbo set descricao = 'Astrólogo' where codigo = '516705'; --.go
update codigocbo set descricao = 'Astrônomo' where codigo = '213305'; --.go
update codigocbo set descricao = 'Atendente comercial (agência postal)' where codigo = '421105'; --.go
update codigocbo set descricao = 'Atendente de agência' where codigo = '413205'; --.go
update codigocbo set descricao = 'Atendente de consultório dentário' where codigo = '322415'; --.go
update codigocbo set descricao = 'Atendente de enfermagem' where codigo = '515110'; --.go
update codigocbo set descricao = 'Atendente de farmácia - balconista' where codigo = '521130'; --.go
update codigocbo set descricao = 'Atendente de judiciário' where codigo = '411015'; --.go
update codigocbo set descricao = 'Atendente de lanchonete' where codigo = '513435'; --.go
update codigocbo set descricao = 'Atendente de lavanderia' where codigo = '516340'; --.go
update codigocbo set descricao = 'Atleta profissional de futebol' where codigo = '377110'; --.go
update codigocbo set descricao = 'Atleta profissional de golfe' where codigo = '377115'; --.go
update codigocbo set descricao = 'Atleta profissional de luta' where codigo = '377120'; --.go
update codigocbo set descricao = 'Atleta profissional de tênis' where codigo = '377125'; --.go
update codigocbo set descricao = 'Atleta profissional (outras modalidades)' where codigo = '377105'; --.go
update codigocbo set descricao = 'Ator' where codigo = '262505'; --.go
update codigocbo set descricao = 'Atuário' where codigo = '211105'; --.go
update codigocbo set descricao = 'Audiodescritor' where codigo = '261430'; --.go
update codigocbo set descricao = 'Auditor (contadores e afins)' where codigo = '252205'; --.go
update codigocbo set descricao = 'Auditor-fiscal da previdência social' where codigo = '254205'; --.go
update codigocbo set descricao = 'Auditor-fiscal da receita federal' where codigo = '254105'; --.go
update codigocbo set descricao = 'Auditor-fiscal do trabalho' where codigo = '254305'; --.go
update codigocbo set descricao = 'Autor-roteirista' where codigo = '261505'; --.go
update codigocbo set descricao = 'Auxiliar de banco de sangue' where codigo = '515205'; --.go
update codigocbo set descricao = 'Auxiliar de biblioteca' where codigo = '371105'; --.go
update codigocbo set descricao = 'Auxiliar de cartório' where codigo = '411025'; --.go
update codigocbo set descricao = 'Auxiliar de contabilidade' where codigo = '413110'; --.go
update codigocbo set descricao = 'Auxiliar de corte (preparaçao da confecçao de roupas)' where codigo = '763105'; --.go
update codigocbo set descricao = 'Auxiliar de desenvolvimento infantil' where codigo = '331110'; --.go
update codigocbo set descricao = 'Auxiliar de enfermagem' where codigo = '322230'; --.go
update codigocbo set descricao = 'Auxiliar de enfermagem da estratégia de saúde da família' where codigo = '322250'; --.go
update codigocbo set descricao = 'Auxiliar de enfermagem do trabalho' where codigo = '322235'; --.go
update codigocbo set descricao = 'Auxiliar de escritório, em geral' where codigo = '411005'; --.go
update codigocbo set descricao = 'Auxiliar de estatística' where codigo = '411035'; --.go
update codigocbo set descricao = 'Auxiliar de farmácia de manipulaçao' where codigo = '515210'; --.go
update codigocbo set descricao = 'Auxiliar de faturamento' where codigo = '413115'; --.go
update codigocbo set descricao = 'Auxiliar de judiciário' where codigo = '411020'; --.go
update codigocbo set descricao = 'Auxiliar de laboratório de análises clínicas' where codigo = '515215'; --.go
update codigocbo set descricao = 'Auxiliar de laboratório de análises físico-químicas' where codigo = '818110'; --.go
update codigocbo set descricao = 'Auxiliar de laboratório de imunobiológicos' where codigo = '515220'; --.go
update codigocbo set descricao = 'Auxiliar de lavanderia' where codigo = '516345'; --.go
update codigocbo set descricao = 'Auxiliar de manutençao predial' where codigo = '514310'; --.go
update codigocbo set descricao = 'Auxiliar de maquinista de trem' where codigo = '782625'; --.go
update codigocbo set descricao = 'Auxiliar de pessoal' where codigo = '411030'; --.go
update codigocbo set descricao = 'Auxiliar de processamento de fumo' where codigo = '842120'; --.go
update codigocbo set descricao = 'Auxiliar de produçao farmacêutica' where codigo = '515225'; --.go
update codigocbo set descricao = 'Auxiliar de prótese dentária' where codigo = '322420'; --.go
update codigocbo set descricao = 'Auxiliar de radiologia (revelaçao fotográfica)' where codigo = '766420'; --.go
update codigocbo set descricao = 'Auxiliar de saúde (navegaçao marítima)' where codigo = '322240'; --.go
update codigocbo set descricao = 'Auxiliar de seguros' where codigo = '411040'; --.go
update codigocbo set descricao = 'Auxiliar de serviços de importaçao e exportaçao' where codigo = '411045'; --.go
update codigocbo set descricao = 'Auxiliar de serviços jurídicos' where codigo = '351430'; --.go
update codigocbo set descricao = 'Auxiliar de veterinário' where codigo = '519305'; --.go
update codigocbo set descricao = 'Auxiliar em saúde bucal da estratégia de saúde da família' where codigo = '322430'; --.go
update codigocbo set descricao = 'Auxiliar geral de conservaçao de vias permanentes (exceto trilhos)' where codigo = '992225'; --.go
update codigocbo set descricao = 'Auxiliar nos serviços de alimentaçao' where codigo = '513501'; --.go
update codigocbo set descricao = 'Auxiliar técnico em laboratório de farmácia' where codigo = '325105'; --.go
update codigocbo set descricao = 'Auxiliar técnico em patologia clínica' where codigo = '324210'; --.go
update codigocbo set descricao = 'Avaliador de bens móveis' where codigo = '354415'; --.go
update codigocbo set descricao = 'Avaliador de imóveis' where codigo = '354410'; --.go
update codigocbo set descricao = 'Avaliador de produtos do meio de comunicaçao' where codigo = '352410'; --.go
update codigocbo set descricao = 'Avaliador físico' where codigo = '224105'; --.go
update codigocbo set descricao = 'Avicultor' where codigo = '613305'; --.go
update codigocbo set descricao = 'Babá' where codigo = '516205'; --.go
update codigocbo set descricao = 'Bailarino (exceto danças populares)' where codigo = '262810'; --.go
update codigocbo set descricao = 'Balanceador' where codigo = '992110'; --.go
update codigocbo set descricao = 'Balanceiro' where codigo = '414115'; --.go
update codigocbo set descricao = 'Bamburista' where codigo = '811705'; --.go
update codigocbo set descricao = 'Banhista de animais domésticos' where codigo = '519315'; --.go
update codigocbo set descricao = 'Barbeiro' where codigo = '516105'; --.go
update codigocbo set descricao = 'Barista' where codigo = '513440'; --.go
update codigocbo set descricao = 'Barman' where codigo = '513420'; --.go
update codigocbo set descricao = 'Bate-folha a  máquina' where codigo = '751105'; --.go
update codigocbo set descricao = 'Bibliotecário' where codigo = '261205'; --.go
update codigocbo set descricao = 'Bilheteiro de transportes coletivos' where codigo = '421110'; --.go
update codigocbo set descricao = 'Bilheteiro (estaçoes de metrô, ferroviárias e assemelhadas)' where codigo = '511220'; --.go
update codigocbo set descricao = 'Bilheteiro no serviço de diversoes' where codigo = '421115'; --.go
update codigocbo set descricao = 'Bioengenheiro' where codigo = '201105'; --.go
update codigocbo set descricao = 'Biólogo' where codigo = '221105'; --.go
update codigocbo set descricao = 'Biomédico' where codigo = '221201'; --.go
update codigocbo set descricao = 'Biotecnologista' where codigo = '201110'; --.go
update codigocbo set descricao = 'Bloqueiro (trabalhador portuário)' where codigo = '783230'; --.go
update codigocbo set descricao = 'Bobinador eletricista, à mao' where codigo = '731165'; --.go
update codigocbo set descricao = 'Bobinador eletricista, à máquina' where codigo = '731170'; --.go
update codigocbo set descricao = 'Boiadeiro' where codigo = '782815'; --.go
update codigocbo set descricao = 'Bombeiro de aeródromo' where codigo = '517105'; --.go
update codigocbo set descricao = 'Bombeiro de segurança do trabalho' where codigo = '517110'; --.go
update codigocbo set descricao = 'Boneleiro' where codigo = '765015'; --.go
update codigocbo set descricao = 'Bordador, a  mao' where codigo = '768205'; --.go
update codigocbo set descricao = 'Bordador, à máquina' where codigo = '763310'; --.go
update codigocbo set descricao = 'Borracheiro' where codigo = '992115'; --.go
update codigocbo set descricao = 'Brasador' where codigo = '724305'; --.go
update codigocbo set descricao = 'Cabeleireiro' where codigo = '516110'; --.go
update codigocbo set descricao = 'Cableador' where codigo = '722405'; --.go
update codigocbo set descricao = 'Cabo bombeiro militar' where codigo = '031205'; --.go
update codigocbo set descricao = 'Cabo da polícia militar' where codigo = '021205'; --.go
update codigocbo set descricao = 'Cacique' where codigo = '113005'; --.go
update codigocbo set descricao = 'Cafeicultor' where codigo = '612605'; --.go
update codigocbo set descricao = 'Caixa de banco' where codigo = '413210'; --.go
update codigocbo set descricao = 'Calafetador' where codigo = '716605'; --.go
update codigocbo set descricao = 'Calandrista de borracha' where codigo = '811710'; --.go
update codigocbo set descricao = 'Calandrista de papel' where codigo = '832105'; --.go
update codigocbo set descricao = 'Calceteiro' where codigo = '715205'; --.go
update codigocbo set descricao = 'Caldeireiro (chapas de cobre)' where codigo = '724405'; --.go
update codigocbo set descricao = 'Caldeireiro (chapas de ferro e aço)' where codigo = '724410'; --.go
update codigocbo set descricao = 'Camareira de teatro' where codigo = '513305'; --.go
update codigocbo set descricao = 'Camareira de televisao' where codigo = '513310'; --.go
update codigocbo set descricao = 'Camareiro de embarcaçoes' where codigo = '513320'; --.go
update codigocbo set descricao = 'Camareiro  de hotel' where codigo = '513315'; --.go
update codigocbo set descricao = 'Caminhoneiro autônomo (rotas regionais e internacionais)' where codigo = '782505'; --.go
update codigocbo set descricao = 'Canteiro' where codigo = '711110'; --.go
update codigocbo set descricao = 'Capitao bombeiro militar' where codigo = '030205'; --.go
update codigocbo set descricao = 'Capitao da polícia militar' where codigo = '020205'; --.go
update codigocbo set descricao = 'Capitao de manobra da marinha mercante' where codigo = '215110'; --.go
update codigocbo set descricao = 'Carbonizador' where codigo = '632610'; --.go
update codigocbo set descricao = 'Carpinteiro' where codigo = '715505'; --.go
update codigocbo set descricao = 'Carpinteiro (cenários)' where codigo = '715515'; --.go
update codigocbo set descricao = 'Carpinteiro de carretas' where codigo = '777205'; --.go
update codigocbo set descricao = 'Carpinteiro de carrocerias' where codigo = '777210'; --.go
update codigocbo set descricao = 'Carpinteiro de fôrmas para concreto' where codigo = '715535'; --.go
update codigocbo set descricao = 'Carpinteiro de obras' where codigo = '715525'; --.go
update codigocbo set descricao = 'Carpinteiro de obras civis de arte (pontes, túneis, barragens)' where codigo = '715540'; --.go
update codigocbo set descricao = 'Carpinteiro (esquadrias)' where codigo = '715510'; --.go
update codigocbo set descricao = 'Carpinteiro (mineraçao)' where codigo = '715520'; --.go
update codigocbo set descricao = 'Carpinteiro naval (construçao de pequenas embarcaçoes)' where codigo = '777105'; --.go
update codigocbo set descricao = 'Carpinteiro naval (embarcaçoes)' where codigo = '777110'; --.go
update codigocbo set descricao = 'Carpinteiro naval (estaleiros)' where codigo = '777115'; --.go
update codigocbo set descricao = 'Carpinteiro (telhados)' where codigo = '715530'; --.go
update codigocbo set descricao = 'Carregador (aeronaves)' where codigo = '783205'; --.go
update codigocbo set descricao = 'Carregador (armazém)' where codigo = '783210'; --.go
update codigocbo set descricao = 'Carregador (veículos de transportes terrestres)' where codigo = '783215'; --.go
update codigocbo set descricao = 'Cartazeiro' where codigo = '519905'; --.go
update codigocbo set descricao = 'Carteiro' where codigo = '415205'; --.go
update codigocbo set descricao = 'Cartonageiro, a mao (caixas de papelao)' where codigo = '833205'; --.go
update codigocbo set descricao = 'Cartonageiro, a máquina' where codigo = '833105'; --.go
update codigocbo set descricao = 'Carvoeiro' where codigo = '632605'; --.go
update codigocbo set descricao = 'Caseiro (agricultura)' where codigo = '622005'; --.go
update codigocbo set descricao = 'Catador de caranguejos e siris' where codigo = '631005'; --.go
update codigocbo set descricao = 'Catador de mariscos' where codigo = '631010'; --.go
update codigocbo set descricao = 'Catador de material reciclável' where codigo = '519205'; --.go
update codigocbo set descricao = 'Celofanista na fabricaçao de charutos' where codigo = '842225'; --.go
update codigocbo set descricao = 'Cementador de metais' where codigo = '723105'; --.go
update codigocbo set descricao = 'Cenógrafo carnavalesco e festas populares' where codigo = '262305'; --.go
update codigocbo set descricao = 'Cenógrafo de cinema' where codigo = '262310'; --.go
update codigocbo set descricao = 'Cenógrafo de eventos' where codigo = '262315'; --.go
update codigocbo set descricao = 'Cenógrafo de teatro' where codigo = '262320'; --.go
update codigocbo set descricao = 'Cenógrafo de tv' where codigo = '262325'; --.go
update codigocbo set descricao = 'Cenotécnico (cinema, vídeo, televisao, teatro e espetáculos)' where codigo = '374205'; --.go
update codigocbo set descricao = 'Ceramista' where codigo = '752305'; --.go
update codigocbo set descricao = 'Ceramista modelador' where codigo = '752320'; --.go
update codigocbo set descricao = 'Ceramista moldador' where codigo = '752325'; --.go
update codigocbo set descricao = 'Ceramista prensador' where codigo = '752330'; --.go
update codigocbo set descricao = 'Ceramista (torno de pedal e motor)' where codigo = '752310'; --.go
update codigocbo set descricao = 'Ceramista (torno semi-automático)' where codigo = '752315'; --.go
update codigocbo set descricao = 'Cerzidor' where codigo = '768210'; --.go
update codigocbo set descricao = 'Cesteiro' where codigo = '776405'; --.go
update codigocbo set descricao = 'Chapeador' where codigo = '724415'; --.go
update codigocbo set descricao = 'Chapeador de aeronaves' where codigo = '724430'; --.go
update codigocbo set descricao = 'Chapeador de carrocerias metálicas (fabricaçao)' where codigo = '724420'; --.go
update codigocbo set descricao = 'Chapeador naval' where codigo = '724425'; --.go
update codigocbo set descricao = 'Chapeleiro (chapéus de palha)' where codigo = '768125'; --.go
update codigocbo set descricao = 'Chapeleiro de senhoras' where codigo = '765010'; --.go
update codigocbo set descricao = 'Charuteiro a mao' where codigo = '842230'; --.go
update codigocbo set descricao = 'Chaveiro' where codigo = '523115'; --.go
update codigocbo set descricao = 'Chefe de bar' where codigo = '510130'; --.go
update codigocbo set descricao = 'Chefe de confeitaria' where codigo = '840120'; --.go
update codigocbo set descricao = 'Chefe de contabilidade (técnico)' where codigo = '351110'; --.go
update codigocbo set descricao = 'Chefe de cozinha' where codigo = '271105'; --.go
update codigocbo set descricao = 'Chefe de cozinha' where codigo = '510125'; --.go
update codigocbo set descricao = 'Chefe de estaçao portuária' where codigo = '342605'; --.go
update codigocbo set descricao = 'Chefe de portaria de hotel' where codigo = '510120'; --.go
update codigocbo set descricao = 'Chefe de serviço de transporte rodoviário (passageiros e cargas)' where codigo = '342305'; --.go
update codigocbo set descricao = 'Chefe de serviços bancários' where codigo = '353235'; --.go
update codigocbo set descricao = 'Churrasqueiro' where codigo = '513605'; --.go
update codigocbo set descricao = 'Ciclista mensageiro' where codigo = '519105'; --.go
update codigocbo set descricao = 'Cientista político' where codigo = '251115'; --.go
update codigocbo set descricao = 'Cilindreiro na preparaçao de pasta para fabricaçao de papel' where codigo = '831105'; --.go
update codigocbo set descricao = 'Cilindrista (petroquímica e afins)' where codigo = '813105'; --.go
update codigocbo set descricao = 'Cimentador (poços de petróleo)' where codigo = '316340'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - auditor' where codigo = '223204'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - clínico geral' where codigo = '223208'; --.go
update codigocbo set descricao = 'Cirurgião-dentista da estratégia de saúde da família' where codigo = '223293'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - dentística' where codigo = '223280'; --.go
update codigocbo set descricao = 'Cirurgiao dentista de saúde coletiva' where codigo = '223272'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - disfunçao temporomandibular e dor orofacial' where codigo = '223284'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - endodontista' where codigo = '223212'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - epidemiologista' where codigo = '223216'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - estomatologista' where codigo = '223220'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - implantodontista' where codigo = '223224'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - odontogeriatra' where codigo = '223228'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - odontologia do trabalho' where codigo = '223276'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - odontologia para pacientes com necessidades especiais' where codigo = '223288'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - odontologista legal' where codigo = '223232'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - odontopediatra' where codigo = '223236'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - ortopedista e ortodontista' where codigo = '223240'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - patologista bucal' where codigo = '223244'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - periodontista' where codigo = '223248'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - protesiólogo bucomaxilofacial' where codigo = '223252'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - protesista' where codigo = '223256'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - radiologista' where codigo = '223260'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - reabilitador oral' where codigo = '223264'; --.go
update codigocbo set descricao = 'Cirurgiao dentista - traumatologista bucomaxilofacial' where codigo = '223268'; --.go
update codigocbo set descricao = 'Classificador de charutos' where codigo = '842215'; --.go
update codigocbo set descricao = 'Classificador de couros' where codigo = '762210'; --.go
update codigocbo set descricao = 'Classificador de fibras têxteis' where codigo = '761105'; --.go
update codigocbo set descricao = 'Classificador de fumo' where codigo = '842115'; --.go
update codigocbo set descricao = 'Classificador de graos' where codigo = '848425'; --.go
update codigocbo set descricao = 'Classificador de madeira' where codigo = '772105'; --.go
update codigocbo set descricao = 'Classificador de peles' where codigo = '762105'; --.go
update codigocbo set descricao = 'Classificador de toras' where codigo = '632105'; --.go
update codigocbo set descricao = 'Classificador e empilhador de tijolos refratários' where codigo = '823305'; --.go
update codigocbo set descricao = 'Cobrador de transportes coletivos (exceto trem)' where codigo = '511215'; --.go
update codigocbo set descricao = 'Cobrador externo' where codigo = '421305'; --.go
update codigocbo set descricao = 'Cobrador interno' where codigo = '421310'; --.go
update codigocbo set descricao = 'Codificador de dados' where codigo = '415115'; --.go
update codigocbo set descricao = 'Colchoeiro (confecçao de colchoes)' where codigo = '765205'; --.go
update codigocbo set descricao = 'Colecionador de selos e moedas' where codigo = '371205'; --.go
update codigocbo set descricao = 'Coletor de lixo domiciliar' where codigo = '514205'; --.go
update codigocbo set descricao = 'Coletor de resíduos sólidos de serviços de saúde' where codigo = '514230'; --.go
update codigocbo set descricao = 'Colorista de papel' where codigo = '311705'; --.go
update codigocbo set descricao = 'Colorista têxtil' where codigo = '311710'; --.go
update codigocbo set descricao = 'Comandante da marinha mercante' where codigo = '215115'; --.go
update codigocbo set descricao = 'Comentarista de rádio e televisao' where codigo = '261710'; --.go
update codigocbo set descricao = 'Comerciante atacadista' where codigo = '141405'; --.go
update codigocbo set descricao = 'Comerciante varejista' where codigo = '141410'; --.go
update codigocbo set descricao = 'Comissário de trem' where codigo = '511110'; --.go
update codigocbo set descricao = 'Comissário de vôo' where codigo = '511105'; --.go
update codigocbo set descricao = 'Compensador de banco' where codigo = '413215'; --.go
update codigocbo set descricao = 'Compositor' where codigo = '262605'; --.go
update codigocbo set descricao = 'Comprador' where codigo = '354205'; --.go
update codigocbo set descricao = 'Condutor de processos robotizados de pintura' where codigo = '781105'; --.go
update codigocbo set descricao = 'Condutor de processos robotizados de soldagem' where codigo = '781110'; --.go
update codigocbo set descricao = 'Condutor de veículos a pedais' where codigo = '782820'; --.go
update codigocbo set descricao = 'Condutor de veículos de traçao animal (ruas e estradas)' where codigo = '782805'; --.go
update codigocbo set descricao = 'Condutor maquinista fluvial' where codigo = '341305'; --.go
update codigocbo set descricao = 'Condutor maquinista marítimo' where codigo = '341310'; --.go
update codigocbo set descricao = 'Confeccionador de acordeao' where codigo = '742110'; --.go
update codigocbo set descricao = 'Confeccionador de artefatos de couro (exceto sapatos)' where codigo = '765005'; --.go
update codigocbo set descricao = 'Confeccionador de bolsas, sacos e sacolas e papel, a máquina' where codigo = '833110'; --.go
update codigocbo set descricao = 'Confeccionador de brinquedos de pano' where codigo = '765215'; --.go
update codigocbo set descricao = 'Confeccionador de carimbos de borracha' where codigo = '768630'; --.go
update codigocbo set descricao = 'Confeccionador de escovas, pincéis e produtos similares (a mao)' where codigo = '776410'; --.go
update codigocbo set descricao = 'Confeccionador de escovas, pincéis e produtos similares (a máquina)' where codigo = '776415'; --.go
update codigocbo set descricao = 'Confeccionador de instrumentos de corda' where codigo = '742115'; --.go
update codigocbo set descricao = 'Confeccionador de instrumentos de percussao (pele, couro ou plástico)' where codigo = '742120'; --.go
update codigocbo set descricao = 'Confeccionador de instrumentos de sopro (madeira)' where codigo = '742125'; --.go
update codigocbo set descricao = 'Confeccionador de instrumentos de sopro (metal)' where codigo = '742130'; --.go
update codigocbo set descricao = 'Confeccionador de móveis de vime, junco e bambu' where codigo = '776420'; --.go
update codigocbo set descricao = 'Confeccionador de órgao' where codigo = '742135'; --.go
update codigocbo set descricao = 'Confeccionador de piano' where codigo = '742140'; --.go
update codigocbo set descricao = 'Confeccionador de pneumáticos' where codigo = '811715'; --.go
update codigocbo set descricao = 'Confeccionador de sacos de celofane, a máquina' where codigo = '833115'; --.go
update codigocbo set descricao = 'Confeccionador de velas náuticas, barracas e toldos' where codigo = '765225'; --.go
update codigocbo set descricao = 'Confeccionador de velas por imersao' where codigo = '811725'; --.go
update codigocbo set descricao = 'Confeccionador de velas por moldagem' where codigo = '811735'; --.go
update codigocbo set descricao = 'Confeiteiro' where codigo = '848310'; --.go
update codigocbo set descricao = 'Conferente de carga e descarga' where codigo = '414215'; --.go
update codigocbo set descricao = 'Conferente de serviços bancários' where codigo = '413220'; --.go
update codigocbo set descricao = 'Conferente-expedidor de roupas (lavanderias)' where codigo = '516335'; --.go
update codigocbo set descricao = 'Conselheiro tutelar' where codigo = '515304'; --.go
update codigocbo set descricao = 'Conservador de via permanente (trilhos)' where codigo = '991105'; --.go
update codigocbo set descricao = 'Conservador-restaurador de bens  culturais' where codigo = '262415'; --.go
update codigocbo set descricao = 'Consultor contábil (técnico)' where codigo = '351115'; --.go
update codigocbo set descricao = 'Consultor jurídico' where codigo = '241040'; --.go
update codigocbo set descricao = 'Contador' where codigo = '252210'; --.go
update codigocbo set descricao = 'Contínuo' where codigo = '412205'; --.go
update codigocbo set descricao = 'Contorcionista' where codigo = '376220'; --.go
update codigocbo set descricao = 'Contramestre de acabamento (indústria têxtil)' where codigo = '760105'; --.go
update codigocbo set descricao = 'Contramestre de cabotagem' where codigo = '341205'; --.go
update codigocbo set descricao = 'Contramestre de fiaçao (indústria têxtil)' where codigo = '760110'; --.go
update codigocbo set descricao = 'Contramestre de malharia (indústria têxtil)' where codigo = '760115'; --.go
update codigocbo set descricao = 'Contramestre de tecelagem (indústria têxtil)' where codigo = '760120'; --.go
update codigocbo set descricao = 'Controlador de entrada e saída' where codigo = '391115'; --.go
update codigocbo set descricao = 'Controlador de pragas' where codigo = '519910'; --.go
update codigocbo set descricao = 'Controlador de serviços de máquinas e veículos' where codigo = '342115'; --.go
update codigocbo set descricao = 'Controlador de tráfego aéreo' where codigo = '342505'; --.go
update codigocbo set descricao = 'Coordenador de operaçoes de combate à poluiçao no meio aquaviário' where codigo = '215120'; --.go
update codigocbo set descricao = 'Coordenador pedagógico' where codigo = '239405'; --.go
update codigocbo set descricao = 'Copeiro' where codigo = '513425'; --.go
update codigocbo set descricao = 'Copeiro de hospital' where codigo = '513430'; --.go
update codigocbo set descricao = 'Copiador de chapa' where codigo = '766105'; --.go
update codigocbo set descricao = 'Coreógrafo' where codigo = '262815'; --.go
update codigocbo set descricao = 'Coronel bombeiro militar' where codigo = '030105'; --.go
update codigocbo set descricao = 'Coronel da polícia militar' where codigo = '020105'; --.go
update codigocbo set descricao = 'Corretor de imóveis' where codigo = '354605'; --.go
update codigocbo set descricao = 'Corretor de seguros' where codigo = '354505'; --.go
update codigocbo set descricao = 'Corretor de valores, ativos financeiros, mercadorias e derivativos' where codigo = '253305'; --.go
update codigocbo set descricao = 'Cortador de artefatos de couro (exceto roupas e calçados)' where codigo = '765105'; --.go
update codigocbo set descricao = 'Cortador de calçados, a  mao (exceto solas)' where codigo = '768310'; --.go
update codigocbo set descricao = 'Cortador de calçados, a  máquina (exceto solas e palmilhas)' where codigo = '764105'; --.go
update codigocbo set descricao = 'Cortador de charutos' where codigo = '842220'; --.go
update codigocbo set descricao = 'Cortador de laminados de madeira' where codigo = '773105'; --.go
update codigocbo set descricao = 'Cortador de pedras' where codigo = '712205'; --.go
update codigocbo set descricao = 'Cortador de roupas' where codigo = '763110'; --.go
update codigocbo set descricao = 'Cortador de solas e palmilhas, a  máquina' where codigo = '764110'; --.go
update codigocbo set descricao = 'Cortador de tapeçaria' where codigo = '765110'; --.go
update codigocbo set descricao = 'Cortador de vidro' where codigo = '752210'; --.go
update codigocbo set descricao = 'Costurador de artefatos de couro, a  mao (exceto roupas e calçados)' where codigo = '768315'; --.go
update codigocbo set descricao = 'Costurador de artefatos de couro, a  máquina (exceto roupas e calçados)' where codigo = '765310'; --.go
update codigocbo set descricao = 'Costurador de calçados, a  máquina' where codigo = '764205'; --.go
update codigocbo set descricao = 'Costureira de peças sob encomenda' where codigo = '763010'; --.go
update codigocbo set descricao = 'Costureira de reparaçao de roupas' where codigo = '763015'; --.go
update codigocbo set descricao = 'Costureiro, a  máquina  na confecçao em série' where codigo = '763215'; --.go
update codigocbo set descricao = 'Costureiro de roupa de couro e pele' where codigo = '763020'; --.go
update codigocbo set descricao = 'Costureiro de roupas de couro e pele, a  máquina na  confecçao em série' where codigo = '763205'; --.go
update codigocbo set descricao = 'Costureiro na confecçao em série' where codigo = '763210'; --.go
update codigocbo set descricao = 'Cozinhador (conservaçao de alimentos)' where codigo = '841408'; --.go
update codigocbo set descricao = 'Cozinhador de carnes' where codigo = '841416'; --.go
update codigocbo set descricao = 'Cozinhador de frutas e legumes' where codigo = '841420'; --.go
update codigocbo set descricao = 'Cozinhador de malte' where codigo = '841730'; --.go
update codigocbo set descricao = 'Cozinhador de pescado' where codigo = '841428'; --.go
update codigocbo set descricao = 'Cozinheiro de embarcaçoes' where codigo = '513225'; --.go
update codigocbo set descricao = 'Cozinheiro de hospital' where codigo = '513220'; --.go
update codigocbo set descricao = 'Cozinheiro do serviço doméstico' where codigo = '513210'; --.go
update codigocbo set descricao = 'Cozinheiro geral' where codigo = '513205'; --.go
update codigocbo set descricao = 'Cozinheiro industrial' where codigo = '513215'; --.go
update codigocbo set descricao = 'Criador de animais domésticos' where codigo = '613010'; --.go
update codigocbo set descricao = 'Criador de animais produtores de veneno' where codigo = '613410'; --.go
update codigocbo set descricao = 'Criador de asininos e muares' where codigo = '613105'; --.go
update codigocbo set descricao = 'Criador de bovinos (corte)' where codigo = '613110'; --.go
update codigocbo set descricao = 'Criador de bovinos (leite)' where codigo = '613115'; --.go
update codigocbo set descricao = 'Criador de bubalinos (corte)' where codigo = '613120'; --.go
update codigocbo set descricao = 'Criador de bubalinos (leite)' where codigo = '613125'; --.go
update codigocbo set descricao = 'Criador de camaroes' where codigo = '631305'; --.go
update codigocbo set descricao = 'Criador de caprinos' where codigo = '613205'; --.go
update codigocbo set descricao = 'Criador de eqüínos' where codigo = '613130'; --.go
update codigocbo set descricao = 'Criador de jacarés' where codigo = '631310'; --.go
update codigocbo set descricao = 'Criador de mexilhoes' where codigo = '631315'; --.go
update codigocbo set descricao = 'Criador de ostras' where codigo = '631320'; --.go
update codigocbo set descricao = 'Criador de ovinos' where codigo = '613210'; --.go
update codigocbo set descricao = 'Criador de peixes' where codigo = '631325'; --.go
update codigocbo set descricao = 'Criador de quelônios' where codigo = '631330'; --.go
update codigocbo set descricao = 'Criador de ras' where codigo = '631335'; --.go
update codigocbo set descricao = 'Criador de suínos' where codigo = '613215'; --.go
update codigocbo set descricao = 'Criador em pecuária polivalente' where codigo = '613005'; --.go
update codigocbo set descricao = 'Crítico' where codigo = '261510'; --.go
update codigocbo set descricao = 'Crocheteiro, a  mao' where codigo = '768130'; --.go
update codigocbo set descricao = 'Cronoanalista' where codigo = '391105'; --.go
update codigocbo set descricao = 'Cronometrista' where codigo = '391110'; --.go
update codigocbo set descricao = 'Cubador de madeira' where codigo = '632110'; --.go
update codigocbo set descricao = 'Cuidador de idosos' where codigo = '516210'; --.go
update codigocbo set descricao = 'Cumim' where codigo = '513415'; --.go
update codigocbo set descricao = 'Cunicultor' where codigo = '613310'; --.go
update codigocbo set descricao = 'Curtidor (couros e peles)' where codigo = '762205'; --.go
update codigocbo set descricao = 'Dançarino popular' where codigo = '376110'; --.go
update codigocbo set descricao = 'Dançarino tradicional' where codigo = '376105'; --.go
update codigocbo set descricao = 'Datilógrafo' where codigo = '412105'; --.go
update codigocbo set descricao = 'Decapador' where codigo = '723205'; --.go
update codigocbo set descricao = 'Decorador de cerâmica' where codigo = '752405'; --.go
update codigocbo set descricao = 'Decorador de interiores de nível superior' where codigo = '262905'; --.go
update codigocbo set descricao = 'Decorador de vidro' where codigo = '752410'; --.go
update codigocbo set descricao = 'Decorador de vidro à pincel' where codigo = '752415'; --.go
update codigocbo set descricao = 'Defensor público' where codigo = '242405'; --.go
update codigocbo set descricao = 'Defumador de carnes e pescados' where codigo = '848105'; --.go
update codigocbo set descricao = 'Degustador de café' where codigo = '848405'; --.go
update codigocbo set descricao = 'Degustador de chá' where codigo = '848410'; --.go
update codigocbo set descricao = 'Degustador de charutos' where codigo = '842235'; --.go
update codigocbo set descricao = 'Degustador de derivados de cacau' where codigo = '848415'; --.go
update codigocbo set descricao = 'Degustador de vinhos ou licores' where codigo = '848420'; --.go
update codigocbo set descricao = 'Delegado de polícia' where codigo = '242305'; --.go
update codigocbo set descricao = 'Demolidor de edificaçoes' where codigo = '717005'; --.go
update codigocbo set descricao = 'Demonstrador de mercadorias' where codigo = '521120'; --.go
update codigocbo set descricao = 'Deputado estadual e distrital' where codigo = '111115'; --.go
update codigocbo set descricao = 'Deputado federal' where codigo = '111110'; --.go
update codigocbo set descricao = 'Descarnador de couros e peles, à maquina' where codigo = '762110'; --.go
update codigocbo set descricao = 'Desenhista copista' where codigo = '318010'; --.go
update codigocbo set descricao = 'Desenhista detalhista' where codigo = '318015'; --.go
update codigocbo set descricao = 'Desenhista industrial de produto de moda (designer de moda)' where codigo = '262425'; --.go
update codigocbo set descricao = 'Desenhista industrial de produto (designer de produto)' where codigo = '262420'; --.go
update codigocbo set descricao = 'Desenhista industrial (designer)' where codigo = '262410'; --.go
update codigocbo set descricao = 'Desenhista projetista de arquitetura' where codigo = '318505'; --.go
update codigocbo set descricao = 'Desenhista projetista de construçao civil' where codigo = '318510'; --.go
update codigocbo set descricao = 'Desenhista projetista de eletricidade' where codigo = '318705'; --.go
update codigocbo set descricao = 'Desenhista projetista de máquinas' where codigo = '318605'; --.go
update codigocbo set descricao = 'Desenhista projetista eletrônico' where codigo = '318710'; --.go
update codigocbo set descricao = 'Desenhista projetista mecânico' where codigo = '318610'; --.go
update codigocbo set descricao = 'Desenhista técnico' where codigo = '318005'; --.go
update codigocbo set descricao = 'Desenhista técnico aeronáutico' where codigo = '318210'; --.go
update codigocbo set descricao = 'Desenhista técnico (arquitetura)' where codigo = '318105'; --.go
update codigocbo set descricao = 'Desenhista técnico (artes gráficas)' where codigo = '318405'; --.go
update codigocbo set descricao = 'Desenhista técnico (calefaçao, ventilaçao e refrigeraçao)' where codigo = '318310'; --.go
update codigocbo set descricao = 'Desenhista técnico (cartografia)' where codigo = '318110'; --.go
update codigocbo set descricao = 'Desenhista técnico (construçao civil)' where codigo = '318115'; --.go
update codigocbo set descricao = 'Desenhista técnico de embalagens, maquetes e leiautes' where codigo = '318430'; --.go
update codigocbo set descricao = 'Desenhista técnico (eletricidade e eletrônica)' where codigo = '318305'; --.go
update codigocbo set descricao = 'Desenhista técnico (ilustraçoes artísticas)' where codigo = '318410'; --.go
update codigocbo set descricao = 'Desenhista técnico (ilustraçoes técnicas)' where codigo = '318415'; --.go
update codigocbo set descricao = 'Desenhista técnico (indústria têxtil)' where codigo = '318420'; --.go
update codigocbo set descricao = 'Desenhista técnico (instalaçoes hidrossanitárias)' where codigo = '318120'; --.go
update codigocbo set descricao = 'Desenhista técnico mecânico' where codigo = '318205'; --.go
update codigocbo set descricao = 'Desenhista técnico (mobiliário)' where codigo = '318425'; --.go
update codigocbo set descricao = 'Desenhista técnico naval' where codigo = '318215'; --.go
update codigocbo set descricao = 'Desidratador de alimentos' where codigo = '841432'; --.go
update codigocbo set descricao = 'Designer de interiores' where codigo = '375105'; --.go
update codigocbo set descricao = 'Designer de vitrines' where codigo = '375110'; --.go
update codigocbo set descricao = 'Designer educacional' where codigo = '239435'; --.go
update codigocbo set descricao = 'Desincrustador (poços de petróleo)' where codigo = '316335'; --.go
update codigocbo set descricao = 'Desossador' where codigo = '848515'; --.go
update codigocbo set descricao = 'Despachante aduaneiro' where codigo = '342210'; --.go
update codigocbo set descricao = 'Despachante de trânsito' where codigo = '423110'; --.go
update codigocbo set descricao = 'Despachante de transportes coletivos (exceto trem)' where codigo = '511210'; --.go
update codigocbo set descricao = 'Despachante documentalista' where codigo = '423105'; --.go
update codigocbo set descricao = 'Despachante operacional de vôo' where codigo = '342510'; --.go
update codigocbo set descricao = 'Dessecador de malte' where codigo = '841735'; --.go
update codigocbo set descricao = 'Destilador de madeira' where codigo = '811405'; --.go
update codigocbo set descricao = 'Destilador de produtos químicos (exceto petróleo)' where codigo = '811410'; --.go
update codigocbo set descricao = 'Destroçador de pedra' where codigo = '711115'; --.go
update codigocbo set descricao = 'Detetive profissional' where codigo = '351805'; --.go
update codigocbo set descricao = 'Detonador' where codigo = '711120'; --.go
update codigocbo set descricao = 'Dietista' where codigo = '223705'; --.go
update codigocbo set descricao = 'Digitador' where codigo = '412110'; --.go
update codigocbo set descricao = 'Diretor administrativo' where codigo = '123105'; --.go
update codigocbo set descricao = 'Diretor administrativo e financeiro' where codigo = '123110'; --.go
update codigocbo set descricao = 'Diretor comercial' where codigo = '123305'; --.go
update codigocbo set descricao = 'Diretor comercial em operaçoes de intermediaçao financeira' where codigo = '122705'; --.go
update codigocbo set descricao = 'Diretor de arte' where codigo = '262330'; --.go
update codigocbo set descricao = 'Diretor de câmbio e comércio exterior' where codigo = '122720'; --.go
update codigocbo set descricao = 'Diretor de cinema' where codigo = '262205'; --.go
update codigocbo set descricao = 'Diretor de compliance' where codigo = '122725'; --.go
update codigocbo set descricao = 'Diretor de crédito (exceto crédito imobiliário)' where codigo = '122730'; --.go
update codigocbo set descricao = 'Diretor de crédito imobiliário' where codigo = '122735'; --.go
update codigocbo set descricao = 'Diretor de crédito rural' where codigo = '122715'; --.go
update codigocbo set descricao = 'Diretor de fotografia' where codigo = '372105'; --.go
update codigocbo set descricao = 'Diretor de instituiçao educacional da área privada' where codigo = '131305'; --.go
update codigocbo set descricao = 'Diretor de instituiçao educacional pública' where codigo = '131310'; --.go
update codigocbo set descricao = 'Diretor de leasing' where codigo = '122740'; --.go
update codigocbo set descricao = 'Diretor de manutençao' where codigo = '123805'; --.go
update codigocbo set descricao = 'Diretor de marketing' where codigo = '123310'; --.go
update codigocbo set descricao = 'Diretor de mercado de capitais' where codigo = '122745'; --.go
update codigocbo set descricao = 'Diretor de operaçoes comerciais (comércio atacadista e varejista)' where codigo = '122405'; --.go
update codigocbo set descricao = 'Diretor de operaçoes de correios' where codigo = '122605'; --.go
update codigocbo set descricao = 'Diretor de operaçoes de obras pública e civil' where codigo = '122305'; --.go
update codigocbo set descricao = 'Diretor de operaçoes de serviços de armazenamento' where codigo = '122610'; --.go
update codigocbo set descricao = 'Diretor de operaçoes de serviços de telecomunicaçoes' where codigo = '122615'; --.go
update codigocbo set descricao = 'Diretor de operaçoes de serviços de transporte' where codigo = '122620'; --.go
update codigocbo set descricao = 'Diretor de pesquisa e desenvolvimento (p&d)' where codigo = '123705'; --.go
update codigocbo set descricao = 'Diretor de planejamento estratégico' where codigo = '121005'; --.go
update codigocbo set descricao = 'Diretor de produçao e operaçoes da indústria de transformaçao, extraçao mineral e utilidades' where codigo = '122205'; --.go
update codigocbo set descricao = 'Diretor de  produçao e operaçoes de alimentaçao' where codigo = '122505'; --.go
update codigocbo set descricao = 'Diretor de  produçao e operaçoes de hotel' where codigo = '122510'; --.go
update codigocbo set descricao = 'Diretor de  produçao e operaçoes de turismo' where codigo = '122515'; --.go
update codigocbo set descricao = 'Diretor de produçao e operaçoes em empresa agropecuária' where codigo = '122105'; --.go
update codigocbo set descricao = 'Diretor de produçao e operaçoes em empresa aqüícola' where codigo = '122110'; --.go
update codigocbo set descricao = 'Diretor de produçao e operaçoes em empresa florestal' where codigo = '122115'; --.go
update codigocbo set descricao = 'Diretor de produçao e operaçoes em empresa pesqueira' where codigo = '122120'; --.go
update codigocbo set descricao = 'Diretor de produtos bancários' where codigo = '122710'; --.go
update codigocbo set descricao = 'Diretor de programas de rádio' where codigo = '262210'; --.go
update codigocbo set descricao = 'Diretor de programas de televisao' where codigo = '262215'; --.go
update codigocbo set descricao = 'Diretor de recuperaçao de créditos em operaçoes de intermediaçao financeira' where codigo = '122750'; --.go
update codigocbo set descricao = 'Diretor de recursos humanos' where codigo = '123205'; --.go
update codigocbo set descricao = 'Diretor de redaçao' where codigo = '261115'; --.go
update codigocbo set descricao = 'Diretor de relaçoes de trabalho' where codigo = '123210'; --.go
update codigocbo set descricao = 'Diretor de riscos de mercado' where codigo = '122755'; --.go
update codigocbo set descricao = 'Diretor de serviços culturais' where codigo = '131105'; --.go
update codigocbo set descricao = 'Diretor de serviços de informática' where codigo = '123605'; --.go
update codigocbo set descricao = 'Diretor de serviços de saúde' where codigo = '131205'; --.go
update codigocbo set descricao = 'Diretor de serviços sociais' where codigo = '131110'; --.go
update codigocbo set descricao = 'Diretor de suprimentos' where codigo = '123405'; --.go
update codigocbo set descricao = 'Diretor de suprimentos no serviço público' where codigo = '123410'; --.go
update codigocbo set descricao = 'Diretor financeiro' where codigo = '123115'; --.go
update codigocbo set descricao = 'Diretor geral de empresa e organizaçoes (exceto de interesse público)' where codigo = '121010'; --.go
update codigocbo set descricao = 'Diretor teatral' where codigo = '262220'; --.go
update codigocbo set descricao = 'Dirigente de partido político' where codigo = '114105'; --.go
update codigocbo set descricao = 'Dirigente do serviço público estadual e distrital' where codigo = '111410'; --.go
update codigocbo set descricao = 'Dirigente do serviço público federal' where codigo = '111405'; --.go
update codigocbo set descricao = 'Dirigente do serviço público municipal' where codigo = '111415'; --.go
update codigocbo set descricao = 'Dirigente e administrador de organizaçao da sociedade civil sem fins lucrativos' where codigo = '114405'; --.go
update codigocbo set descricao = 'Dirigente e administrador de organizaçao religiosa' where codigo = '114305'; --.go
update codigocbo set descricao = 'Dirigentes de entidades de trabalhadores' where codigo = '114205'; --.go
update codigocbo set descricao = 'Dirigentes de entidades patronais' where codigo = '114210'; --.go
update codigocbo set descricao = 'Dj (disc jockey)' where codigo = '374145'; --.go
update codigocbo set descricao = 'Documentalista' where codigo = '261210'; --.go
update codigocbo set descricao = 'Domador de animais (circense)' where codigo = '376225'; --.go
update codigocbo set descricao = 'Doula' where codigo = '322135'; --.go
update codigocbo set descricao = 'Drageador (medicamentos)' where codigo = '811810'; --.go
update codigocbo set descricao = 'Dramaturgo de dança' where codigo = '262820'; --.go
update codigocbo set descricao = 'Economista' where codigo = '251205'; --.go
update codigocbo set descricao = 'Economista agroindustrial' where codigo = '251210'; --.go
update codigocbo set descricao = 'Economista ambiental' where codigo = '251230'; --.go
update codigocbo set descricao = 'Economista doméstico' where codigo = '251610'; --.go
update codigocbo set descricao = 'Economista do setor público' where codigo = '251225'; --.go
update codigocbo set descricao = 'Economista financeiro' where codigo = '251215'; --.go
update codigocbo set descricao = 'Economista industrial' where codigo = '251220'; --.go
update codigocbo set descricao = 'Economista regional e urbano' where codigo = '251235'; --.go
update codigocbo set descricao = 'Editor' where codigo = '261120'; --.go
update codigocbo set descricao = 'Editor de jornal' where codigo = '261605'; --.go
update codigocbo set descricao = 'Editor de livro' where codigo = '261610'; --.go
update codigocbo set descricao = 'Editor de mídia eletrônica' where codigo = '261615'; --.go
update codigocbo set descricao = 'Editor de revista' where codigo = '261620'; --.go
update codigocbo set descricao = 'Editor de revista científica' where codigo = '261625'; --.go
update codigocbo set descricao = 'Editor de texto e imagem' where codigo = '766120'; --.go
update codigocbo set descricao = 'Editor de tv  e vídeo' where codigo = '374405'; --.go
update codigocbo set descricao = 'Educador social' where codigo = '515301'; --.go
update codigocbo set descricao = 'Eletricista de bordo' where codigo = '341315'; --.go
update codigocbo set descricao = 'Eletricista de instalaçoes' where codigo = '715615'; --.go
update codigocbo set descricao = 'Eletricista de instalaçoes (aeronaves)' where codigo = '953105'; --.go
update codigocbo set descricao = 'Eletricista de instalaçoes (cenários)' where codigo = '715605'; --.go
update codigocbo set descricao = 'Eletricista de instalaçoes (edifícios)' where codigo = '715610'; --.go
update codigocbo set descricao = 'Eletricista de instalaçoes (embarcaçoes)' where codigo = '953110'; --.go
update codigocbo set descricao = 'Eletricista de instalaçoes (veículos automotores e máquinas operatrizes, exceto aeronaves e embarcaçoes)' where codigo = '953115'; --.go
update codigocbo set descricao = 'Eletricista de manutençao de linhas elétricas, telefônicas e de comunicaçao de dados' where codigo = '732105'; --.go
update codigocbo set descricao = 'Eletricista de manutençao eletroeletrônica' where codigo = '951105'; --.go
update codigocbo set descricao = 'Eletromecânico de manutençao de elevadores' where codigo = '954105'; --.go
update codigocbo set descricao = 'Eletromecânico de manutençao de escadas rolantes' where codigo = '954110'; --.go
update codigocbo set descricao = 'Eletromecânico de manutençao de portas automáticas' where codigo = '954115'; --.go
update codigocbo set descricao = 'Eletrotécnico' where codigo = '313105'; --.go
update codigocbo set descricao = 'Eletrotécnico (produçao de energia)' where codigo = '313110'; --.go
update codigocbo set descricao = 'Eletroténico na fabricaçao, montagem e instalaçao de máquinas e equipamentos' where codigo = '313115'; --.go
update codigocbo set descricao = 'Embalador, a mao' where codigo = '784105'; --.go
update codigocbo set descricao = 'Embalador, a máquina' where codigo = '784110'; --.go
update codigocbo set descricao = 'Embalsamador' where codigo = '328105'; --.go
update codigocbo set descricao = 'Emendador de cabos elétricos e telefônicos (aéreos e subterrâneos)' where codigo = '732110'; --.go
update codigocbo set descricao = 'Emissor de passagens' where codigo = '421120'; --.go
update codigocbo set descricao = 'Empregado doméstico  arrumador' where codigo = '512110'; --.go
update codigocbo set descricao = 'Empregado doméstico diarista' where codigo = '512120'; --.go
update codigocbo set descricao = 'Empregado doméstico  faxineiro' where codigo = '512115'; --.go
update codigocbo set descricao = 'Empregado  doméstico  nos serviços gerais' where codigo = '512105'; --.go
update codigocbo set descricao = 'Empresário de espetáculo' where codigo = '262105'; --.go
update codigocbo set descricao = 'Encanador' where codigo = '724110'; --.go
update codigocbo set descricao = 'Encarregado de acabamento de chapas e metais  (têmpera)' where codigo = '821405'; --.go
update codigocbo set descricao = 'Encarregado de corte na confecçao do vestuário' where codigo = '760305'; --.go
update codigocbo set descricao = 'Encarregado de costura na confecçao do vestuário' where codigo = '760310'; --.go
update codigocbo set descricao = 'Encarregado de equipe de conservaçao de vias permanentes (exceto trilhos)' where codigo = '992210'; --.go
update codigocbo set descricao = 'Encarregado de manutençao de instrumentos de controle, mediçao e similares' where codigo = '313415'; --.go
update codigocbo set descricao = 'Encarregado de manutençao elétrica de veículos' where codigo = '950205'; --.go
update codigocbo set descricao = 'Encarregado de manutençao mecânica de sistemas operacionais' where codigo = '910105'; --.go
update codigocbo set descricao = 'Encarregado geral de operaçoes de conservaçao de vias permanentes (exceto trilhos)' where codigo = '992205'; --.go
update codigocbo set descricao = 'Enfermeiro' where codigo = '223505'; --.go
update codigocbo set descricao = 'Enfermeiro auditor' where codigo = '223510'; --.go
update codigocbo set descricao = 'Enfermeiro da estratégia de saúde da família' where codigo = '223565'; --.go
update codigocbo set descricao = 'Enfermeiro de bordo' where codigo = '223515'; --.go
update codigocbo set descricao = 'Enfermeiro de centro cirúrgico' where codigo = '223520'; --.go
update codigocbo set descricao = 'Enfermeiro de terapia intensiva' where codigo = '223525'; --.go
update codigocbo set descricao = 'Enfermeiro do trabalho' where codigo = '223530'; --.go
update codigocbo set descricao = 'Enfermeiro nefrologista' where codigo = '223535'; --.go
update codigocbo set descricao = 'Enfermeiro neonatologista' where codigo = '223540'; --.go
update codigocbo set descricao = 'Enfermeiro obstétrico' where codigo = '223545'; --.go
update codigocbo set descricao = 'Enfermeiro psiquiátrico' where codigo = '223550'; --.go
update codigocbo set descricao = 'Enfermeiro puericultor e pediátrico' where codigo = '223555'; --.go
update codigocbo set descricao = 'Enfermeiro sanitarista' where codigo = '223560'; --.go
update codigocbo set descricao = 'Enfestador de roupas' where codigo = '763115'; --.go
update codigocbo set descricao = 'Engastador (jóias)' where codigo = '751005'; --.go
update codigocbo set descricao = 'Engenheiro aeronáutico' where codigo = '214425'; --.go
update codigocbo set descricao = 'Engenheiro agrícola' where codigo = '222105'; --.go
update codigocbo set descricao = 'Engenheiro agrimensor' where codigo = '214805'; --.go
update codigocbo set descricao = 'Engenheiro agrônomo' where codigo = '222110'; --.go
update codigocbo set descricao = 'Engenheiro ambiental' where codigo = '214275'; --.go
update codigocbo set descricao = 'Engenheiro ambiental' where codigo = '214005'; --.go
update codigocbo set descricao = 'Engenheiro cartógrafo' where codigo = '214810'; --.go
update codigocbo set descricao = 'Engenheiro civil' where codigo = '214205'; --.go
update codigocbo set descricao = 'Engenheiro civil (aeroportos)' where codigo = '214210'; --.go
update codigocbo set descricao = 'Engenheiro civil (edificaçoes)' where codigo = '214215'; --.go
update codigocbo set descricao = 'Engenheiro civil (estruturas metálicas)' where codigo = '214220'; --.go
update codigocbo set descricao = 'Engenheiro civil (ferrovias e metrovias)' where codigo = '214225'; --.go
update codigocbo set descricao = 'Engenheiro civil (geotécnia)' where codigo = '214230'; --.go
update codigocbo set descricao = 'Engenheiro civil (hidráulica)' where codigo = '214240'; --.go
update codigocbo set descricao = 'Engenheiro civil (hidrologia)' where codigo = '214235'; --.go
update codigocbo set descricao = 'Engenheiro civil (pontes e viadutos)' where codigo = '214245'; --.go
update codigocbo set descricao = 'Engenheiro civil (portos e vias navegáveis)' where codigo = '214250'; --.go
update codigocbo set descricao = 'Engenheiro civil (rodovias)' where codigo = '214255'; --.go
update codigocbo set descricao = 'Engenheiro civil (saneamento)' where codigo = '214260'; --.go
update codigocbo set descricao = 'Engenheiro civil (transportes e trânsito)' where codigo = '214270'; --.go
update codigocbo set descricao = 'Engenheiro civil (túneis)' where codigo = '214265'; --.go
update codigocbo set descricao = 'Engenheiro de alimentos' where codigo = '222205'; --.go
update codigocbo set descricao = 'Engenheiro de aplicativos em computaçao' where codigo = '212205'; --.go
update codigocbo set descricao = 'Engenheiro de controle de qualidade' where codigo = '214910'; --.go
update codigocbo set descricao = 'Engenheiro de controle e automaçao' where codigo = '214355'; --.go
update codigocbo set descricao = 'Engenheiro de controle e automação' where codigo = '202110'; --.go
update codigocbo set descricao = 'Engenheiro de equipamentos em computaçao' where codigo = '212210'; --.go
update codigocbo set descricao = 'Engenheiro de manutençao de telecomunicaçoes' where codigo = '214335'; --.go
update codigocbo set descricao = 'Engenheiro de materiais' where codigo = '214605'; --.go
update codigocbo set descricao = 'Engenheiro de minas' where codigo = '214705'; --.go
update codigocbo set descricao = 'Engenheiro de minas (beneficiamento)' where codigo = '214710'; --.go
update codigocbo set descricao = 'Engenheiro de minas (lavra a céu aberto)' where codigo = '214715'; --.go
update codigocbo set descricao = 'Engenheiro de minas (lavra subterrânea)' where codigo = '214720'; --.go
update codigocbo set descricao = 'Engenheiro de minas (pesquisa mineral)' where codigo = '214725'; --.go
update codigocbo set descricao = 'Engenheiro de minas (planejamento)' where codigo = '214730'; --.go
update codigocbo set descricao = 'Engenheiro de minas (processo)' where codigo = '214735'; --.go
update codigocbo set descricao = 'Engenheiro de minas (projeto)' where codigo = '214740'; --.go
update codigocbo set descricao = 'Engenheiro de pesca' where codigo = '222115'; --.go
update codigocbo set descricao = 'Engenheiro de produçao' where codigo = '214905'; --.go
update codigocbo set descricao = 'Engenheiro de redes de comunicaçao' where codigo = '214350'; --.go
update codigocbo set descricao = 'Engenheiro de riscos' where codigo = '214920'; --.go
update codigocbo set descricao = 'Engenheiro de segurança do trabalho' where codigo = '214915'; --.go
update codigocbo set descricao = 'Engenheiro de telecomunicaçoes' where codigo = '214340'; --.go
update codigocbo set descricao = 'Engenheiro de tempos e movimentos' where codigo = '214925'; --.go
update codigocbo set descricao = 'Engenheiro eletricista' where codigo = '214305'; --.go
update codigocbo set descricao = 'Engenheiro eletricista de manutençao' where codigo = '214315'; --.go
update codigocbo set descricao = 'Engenheiro eletricista de projetos' where codigo = '214320'; --.go
update codigocbo set descricao = 'Engenheiro eletrônico' where codigo = '214310'; --.go
update codigocbo set descricao = 'Engenheiro eletrônico de manutençao' where codigo = '214325'; --.go
update codigocbo set descricao = 'Engenheiro eletrônico de projetos' where codigo = '214330'; --.go
update codigocbo set descricao = 'Engenheiro florestal' where codigo = '222120'; --.go
update codigocbo set descricao = 'Engenheiro mecânico' where codigo = '214405'; --.go
update codigocbo set descricao = 'Engenheiro mecânico automotivo' where codigo = '214410'; --.go
update codigocbo set descricao = 'Engenheiro mecânico (energia nuclear)' where codigo = '214415'; --.go
update codigocbo set descricao = 'Engenheiro mecânico industrial' where codigo = '214420'; --.go
update codigocbo set descricao = 'Engenheiro mecatrônico' where codigo = '202105'; --.go
update codigocbo set descricao = 'Engenheiro metalurgista' where codigo = '214610'; --.go
update codigocbo set descricao = 'Engenheiro naval' where codigo = '214430'; --.go
update codigocbo set descricao = 'Engenheiro projetista de telecomunicaçoes' where codigo = '214345'; --.go
update codigocbo set descricao = 'Engenheiro químico' where codigo = '214505'; --.go
update codigocbo set descricao = 'Engenheiro químico (indústria química)' where codigo = '214510'; --.go
update codigocbo set descricao = 'Engenheiro químico (mineraçao, metalurgia, siderurgia, cimenteira e cerâmica)' where codigo = '214515'; --.go
update codigocbo set descricao = 'Engenheiro químico (papel e celulose)' where codigo = '214520'; --.go
update codigocbo set descricao = 'Engenheiro químico (petróleo e borracha)' where codigo = '214525'; --.go
update codigocbo set descricao = 'Engenheiro químico (utilidades e meio ambiente)' where codigo = '214530'; --.go
update codigocbo set descricao = 'Engenheiros de sistemas operacionais em computaçao' where codigo = '212215'; --.go
update codigocbo set descricao = 'Engraxate' where codigo = '519915'; --.go
update codigocbo set descricao = 'Enólogo' where codigo = '325005'; --.go
update codigocbo set descricao = 'Ensaiador de dança' where codigo = '262825'; --.go
update codigocbo set descricao = 'Entalhador  de madeira' where codigo = '775105'; --.go
update codigocbo set descricao = 'Entrevistador censitário e de pesquisas amostrais' where codigo = '424105'; --.go
update codigocbo set descricao = 'Entrevistador de pesquisa de opiniao e mídia' where codigo = '424110'; --.go
update codigocbo set descricao = 'Entrevistador de pesquisas de mercado' where codigo = '424115'; --.go
update codigocbo set descricao = 'Entrevistador de preços' where codigo = '424120'; --.go
update codigocbo set descricao = 'Enxugador de couros' where codigo = '762215'; --.go
update codigocbo set descricao = 'Equilibrista' where codigo = '376230'; --.go
update codigocbo set descricao = 'Equoterapeuta' where codigo = '226315'; --.go
update codigocbo set descricao = 'Escarfador' where codigo = '821410'; --.go
update codigocbo set descricao = 'Escolhedor de papel' where codigo = '391225'; --.go
update codigocbo set descricao = 'Escorador de minas' where codigo = '711125'; --.go
update codigocbo set descricao = 'Escrevente' where codigo = '351405'; --.go
update codigocbo set descricao = 'Escritor de ficçao' where codigo = '261515'; --.go
update codigocbo set descricao = 'Escritor de nao ficçao' where codigo = '261520'; --.go
update codigocbo set descricao = 'Escriturário de banco' where codigo = '413225'; --.go
update codigocbo set descricao = 'Escriturário  em  estatística' where codigo = '424125'; --.go
update codigocbo set descricao = 'Escrivao de polícia' where codigo = '351420'; --.go
update codigocbo set descricao = 'Escrivao extra - judicial' where codigo = '351415'; --.go
update codigocbo set descricao = 'Escrivao judicial' where codigo = '351410'; --.go
update codigocbo set descricao = 'Esotérico' where codigo = '516805'; --.go
update codigocbo set descricao = 'Especialista de políticas públicas e gestao governamental - eppgg' where codigo = '111501'; --.go
update codigocbo set descricao = 'Especialista em calibraçoes metrológicas' where codigo = '201210'; --.go
update codigocbo set descricao = 'Especialista em desenvolvimento de cigarros' where codigo = '142610'; --.go
update codigocbo set descricao = 'Especialista em ensaios metrológicos' where codigo = '201215'; --.go
update codigocbo set descricao = 'Especialista em instrumentaçao metrológica' where codigo = '201220'; --.go
update codigocbo set descricao = 'Especialista em materiais de referência metrológica' where codigo = '201225'; --.go
update codigocbo set descricao = 'Especialista em pesquisa operacional' where codigo = '211110'; --.go
update codigocbo set descricao = 'Estampador de tecido' where codigo = '761410'; --.go
update codigocbo set descricao = 'Estatístico' where codigo = '211205'; --.go
update codigocbo set descricao = 'Estatístico (estatística aplicada)' where codigo = '211210'; --.go
update codigocbo set descricao = 'Estatístico teórico' where codigo = '211215'; --.go
update codigocbo set descricao = 'Esteireiro' where codigo = '776425'; --.go
update codigocbo set descricao = 'Estenotipista' where codigo = '351515'; --.go
update codigocbo set descricao = 'Esterilizador de alimentos' where codigo = '841440'; --.go
update codigocbo set descricao = 'Esteticista' where codigo = '516115'; --.go
update codigocbo set descricao = 'Esteticista de animais domésticos' where codigo = '519310'; --.go
update codigocbo set descricao = 'Estirador de couros e peles (acabamento)' where codigo = '762305'; --.go
update codigocbo set descricao = 'Estirador de couros e peles (preparaçao)' where codigo = '762115'; --.go
update codigocbo set descricao = 'Estirador de tubos de metal sem costura' where codigo = '722410'; --.go
update codigocbo set descricao = 'Estivador' where codigo = '783220'; --.go
update codigocbo set descricao = 'Estofador de avioes' where codigo = '765230'; --.go
update codigocbo set descricao = 'Estofador de móveis' where codigo = '765235'; --.go
update codigocbo set descricao = 'Examinador de cabos, linhas elétricas e telefônicas' where codigo = '732115'; --.go
update codigocbo set descricao = 'Extrusor de fios ou fibras de vidro' where codigo = '823210'; --.go
update codigocbo set descricao = 'Farmacêutico' where codigo = '223405'; --.go
update codigocbo set descricao = 'Farmacêutico analista clínico' where codigo = '223415'; --.go
update codigocbo set descricao = 'Farmacêutico bioquímico' where codigo = '223410'; --.go
update codigocbo set descricao = 'Farmacêutico de alimentos' where codigo = '223420'; --.go
update codigocbo set descricao = 'Farmacêutico em saúde pública' where codigo = '223430'; --.go
update codigocbo set descricao = 'Farmacêutico hospitalar e clínico' where codigo = '223445'; --.go
update codigocbo set descricao = 'Farmacêutico industrial' where codigo = '223435'; --.go
update codigocbo set descricao = 'Farmacêutico práticas integrativas e complementares' where codigo = '223425'; --.go
update codigocbo set descricao = 'Farmacêutico toxicologista' where codigo = '223440'; --.go
update codigocbo set descricao = 'Faxineiro' where codigo = '514320'; --.go
update codigocbo set descricao = 'Feirante' where codigo = '524205'; --.go
update codigocbo set descricao = 'Fermentador' where codigo = '841715'; --.go
update codigocbo set descricao = 'Ferramenteiro' where codigo = '721105'; --.go
update codigocbo set descricao = 'Ferramenteiro de mandris, calibradores e outros dispositivos' where codigo = '721110'; --.go
update codigocbo set descricao = 'Filólogo' where codigo = '261405'; --.go
update codigocbo set descricao = 'Filósofo' where codigo = '251405'; --.go
update codigocbo set descricao = 'Filtrador de cerveja' where codigo = '841710'; --.go
update codigocbo set descricao = 'Finalizador de filmes' where codigo = '374410'; --.go
update codigocbo set descricao = 'Finalizador de vídeo' where codigo = '374415'; --.go
update codigocbo set descricao = 'Fiscal de aviaçao civil (fac)' where codigo = '342515'; --.go
update codigocbo set descricao = 'Fiscal de pátio de usina de concreto' where codigo = '710225'; --.go
update codigocbo set descricao = 'Fiscal de transportes coletivos (exceto trem)' where codigo = '511205'; --.go
update codigocbo set descricao = 'Fiscal de tributos estadual' where codigo = '254405'; --.go
update codigocbo set descricao = 'Fiscal de tributos municipal' where codigo = '254410'; --.go
update codigocbo set descricao = 'Físico' where codigo = '213105'; --.go
update codigocbo set descricao = 'Físico (acústica)' where codigo = '213110'; --.go
update codigocbo set descricao = 'Físico (atômica e molecular)' where codigo = '213115'; --.go
update codigocbo set descricao = 'Físico (cosmologia)' where codigo = '213120'; --.go
update codigocbo set descricao = 'Físico (estatística e matemática)' where codigo = '213125'; --.go
update codigocbo set descricao = 'Físico (fluidos)' where codigo = '213130'; --.go
update codigocbo set descricao = 'Físico (instrumentaçao)' where codigo = '213135'; --.go
update codigocbo set descricao = 'Físico (matéria condensada)' where codigo = '213140'; --.go
update codigocbo set descricao = 'Físico (materiais)' where codigo = '213145'; --.go
update codigocbo set descricao = 'Físico (medicina)' where codigo = '213150'; --.go
update codigocbo set descricao = 'Físico (nuclear e reatores)' where codigo = '213155'; --.go
update codigocbo set descricao = 'Físico (óptica)' where codigo = '213160'; --.go
update codigocbo set descricao = 'Físico (partículas e campos)' where codigo = '213165'; --.go
update codigocbo set descricao = 'Físico (plasma)' where codigo = '213170'; --.go
update codigocbo set descricao = 'Físico (térmica)' where codigo = '213175'; --.go
update codigocbo set descricao = 'Fisioterapeuta acupunturista' where codigo = '223650'; --.go
update codigocbo set descricao = 'Fisioterapeuta  do trabalho' where codigo = '223660'; --.go
update codigocbo set descricao = 'Fisioterapeuta esportivo' where codigo = '223655'; --.go
update codigocbo set descricao = 'Fisioterapeuta geral' where codigo = '223605'; --.go
update codigocbo set descricao = 'Fisioterapeuta neurofuncional' where codigo = '223630'; --.go
update codigocbo set descricao = 'Fisioterapeuta osteopata' where codigo = '223640'; --.go
update codigocbo set descricao = 'Fisioterapeuta quiropraxista' where codigo = '223645'; --.go
update codigocbo set descricao = 'Fisioterapeuta respiratória' where codigo = '223625'; --.go
update codigocbo set descricao = 'Fisioterapeuta traumato-ortopédica funcional' where codigo = '223635'; --.go
update codigocbo set descricao = 'Fitotecário' where codigo = '415120'; --.go
update codigocbo set descricao = 'Foguista (locomotivas a vapor)' where codigo = '862105'; --.go
update codigocbo set descricao = 'Folheador de móveis de madeira' where codigo = '775110'; --.go
update codigocbo set descricao = 'Fonoaudiólogo' where codigo = '223810'; --.go
update codigocbo set descricao = 'Fonoaudiólogo educacional' where codigo = '223815'; --.go
update codigocbo set descricao = 'Fonoaudiólogo em audiologia' where codigo = '223820'; --.go
update codigocbo set descricao = 'Fonoaudiólogo em disfagia' where codigo = '223825'; --.go
update codigocbo set descricao = 'Fonoaudiólogo em linguagem' where codigo = '223830'; --.go
update codigocbo set descricao = 'Fonoaudiólogo em motricidade orofacial' where codigo = '223835'; --.go
update codigocbo set descricao = 'Fonoaudiólogo em saúde coletiva' where codigo = '223840'; --.go
update codigocbo set descricao = 'Fonoaudiólogo em voz' where codigo = '223845'; --.go
update codigocbo set descricao = 'Forjador' where codigo = '722105'; --.go
update codigocbo set descricao = 'Forjador a martelo' where codigo = '722110'; --.go
update codigocbo set descricao = 'Forjador prensista' where codigo = '722115'; --.go
update codigocbo set descricao = 'Forneiro de cubilô' where codigo = '822105'; --.go
update codigocbo set descricao = 'Forneiro de forno-poço' where codigo = '822110'; --.go
update codigocbo set descricao = 'Forneiro de fundiçao (forno de reduçao)' where codigo = '822115'; --.go
update codigocbo set descricao = 'Forneiro de reaquecimento e tratamento térmico na metalurgia' where codigo = '822120'; --.go
update codigocbo set descricao = 'Forneiro de revérbero' where codigo = '822125'; --.go
update codigocbo set descricao = 'Forneiro e operador (alto-forno)' where codigo = '821205'; --.go
update codigocbo set descricao = 'Forneiro e operador (conversor a oxigênio)' where codigo = '821210'; --.go
update codigocbo set descricao = 'Forneiro e operador de forno de reduçao direta' where codigo = '821225'; --.go
update codigocbo set descricao = 'Forneiro e operador (forno elétrico)' where codigo = '821215'; --.go
update codigocbo set descricao = 'Forneiro e operador (refino de metais nao-ferrosos)' where codigo = '821220'; --.go
update codigocbo set descricao = 'Forneiro (materiais de construçao)' where codigo = '823315'; --.go
update codigocbo set descricao = 'Forneiro na fundiçao de vidro' where codigo = '823215'; --.go
update codigocbo set descricao = 'Forneiro no recozimento de vidro' where codigo = '823220'; --.go
update codigocbo set descricao = 'Fosfatizador' where codigo = '723210'; --.go
update codigocbo set descricao = 'Fotógrafo' where codigo = '261805'; --.go
update codigocbo set descricao = 'Fotógrafo publicitário' where codigo = '261810'; --.go
update codigocbo set descricao = 'Fotógrafo retratista' where codigo = '261815'; --.go
update codigocbo set descricao = 'Frentista' where codigo = '521135'; --.go
update codigocbo set descricao = 'Fuloneiro' where codigo = '762120'; --.go
update codigocbo set descricao = 'Fuloneiro no acabamento de couros e peles' where codigo = '762310'; --.go
update codigocbo set descricao = 'Fundidor de metais' where codigo = '722205'; --.go
update codigocbo set descricao = 'Fundidor (joalheria e ourivesaria)' where codigo = '751110'; --.go
update codigocbo set descricao = 'Funileiro de veículos (reparaçao)' where codigo = '991305'; --.go
update codigocbo set descricao = 'Funileiro industrial' where codigo = '724435'; --.go
update codigocbo set descricao = 'Galvanizador' where codigo = '723215'; --.go
update codigocbo set descricao = 'Gandula' where codigo = '519920'; --.go
update codigocbo set descricao = 'Garagista' where codigo = '514110'; --.go
update codigocbo set descricao = 'Garçom' where codigo = '513405'; --.go
update codigocbo set descricao = 'Garçom (serviços de vinhos)' where codigo = '513410'; --.go
update codigocbo set descricao = 'Garimpeiro' where codigo = '711405'; --.go
update codigocbo set descricao = 'Gelador industrial' where codigo = '631405'; --.go
update codigocbo set descricao = 'Gelador profissional' where codigo = '631410'; --.go
update codigocbo set descricao = 'Geneticista' where codigo = '201115'; --.go
update codigocbo set descricao = 'Geofísico' where codigo = '213415'; --.go
update codigocbo set descricao = 'Geofísico espacial' where codigo = '213310'; --.go
update codigocbo set descricao = 'Geógrafo' where codigo = '251305'; --.go
update codigocbo set descricao = 'Geólogo' where codigo = '213405'; --.go
update codigocbo set descricao = 'Geólogo de engenharia' where codigo = '213410'; --.go
update codigocbo set descricao = 'Geoquímico' where codigo = '213420'; --.go
update codigocbo set descricao = 'Gerente administrativo' where codigo = '142105'; --.go
update codigocbo set descricao = 'Gerente comercial' where codigo = '142305'; --.go
update codigocbo set descricao = 'Gerente da administraçao de aeroportos' where codigo = '342520'; --.go
update codigocbo set descricao = 'Gerente de agência' where codigo = '141710'; --.go
update codigocbo set descricao = 'Gerente de almoxarifado' where codigo = '142415'; --.go
update codigocbo set descricao = 'Gerente de bar' where codigo = '141515'; --.go
update codigocbo set descricao = 'Gerente de câmbio e comércio exterior' where codigo = '141715'; --.go
update codigocbo set descricao = 'Gerente de captaçao (fundos e investimentos institucionais)' where codigo = '253205'; --.go
update codigocbo set descricao = 'Gerente de clientes especiais (private)' where codigo = '253210'; --.go
update codigocbo set descricao = 'Gerente de compras' where codigo = '142405'; --.go
update codigocbo set descricao = 'Gerente de comunicaçao' where codigo = '142310'; --.go
update codigocbo set descricao = 'Gerente de contas - pessoa física e jurídica' where codigo = '253215'; --.go
update codigocbo set descricao = 'Gerente de crédito e cobrança' where codigo = '141720'; --.go
update codigocbo set descricao = 'Gerente de crédito imobiliário' where codigo = '141725'; --.go
update codigocbo set descricao = 'Gerente de crédito rural' where codigo = '141730'; --.go
update codigocbo set descricao = 'Gerente de departamento pessoal' where codigo = '142210'; --.go
update codigocbo set descricao = 'Gerente de desenvolvimento de sistemas' where codigo = '142510'; --.go
update codigocbo set descricao = 'Gerente de empresa aérea em aeroportos' where codigo = '342525'; --.go
update codigocbo set descricao = 'Gerente de grandes contas (corporate)' where codigo = '253220'; --.go
update codigocbo set descricao = 'Gerente de hotel' where codigo = '141505'; --.go
update codigocbo set descricao = 'Gerente de instituiçao educacional da área privada' where codigo = '131315'; --.go
update codigocbo set descricao = 'Gerente de logística (armazenagem e distribuiçao)' where codigo = '141615'; --.go
update codigocbo set descricao = 'Gerente de loja e supermercado' where codigo = '141415'; --.go
update codigocbo set descricao = 'Gerente de marketing' where codigo = '142315'; --.go
update codigocbo set descricao = 'Gerente de operaçoes de correios e telecomunicaçoes' where codigo = '141610'; --.go
update codigocbo set descricao = 'Gerente de operaçoes de serviços de assistência técnica' where codigo = '141420'; --.go
update codigocbo set descricao = 'Gerente de operaçoes de transportes' where codigo = '141605'; --.go
update codigocbo set descricao = 'Gerente de pensao' where codigo = '141520'; --.go
update codigocbo set descricao = 'Gerente de pesquisa e desenvolvimento (p&d)' where codigo = '142605'; --.go
update codigocbo set descricao = 'Gerente de produçao de tecnologia da informaçao' where codigo = '142515'; --.go
update codigocbo set descricao = 'Gerente de produçao e operaçoes' where codigo = '141205'; --.go
update codigocbo set descricao = 'Gerente de produçao e operaçoes agropecuárias' where codigo = '141115'; --.go
update codigocbo set descricao = 'Gerente de produçao e operaçoes  aqüícolas' where codigo = '141105'; --.go
update codigocbo set descricao = 'Gerente de produçao e operaçoes da construçao civil e obras públicas' where codigo = '141305'; --.go
update codigocbo set descricao = 'Gerente de produçao e operaçoes  florestais' where codigo = '141110'; --.go
update codigocbo set descricao = 'Gerente de produçao e operaçoes pesqueiras' where codigo = '141120'; --.go
update codigocbo set descricao = 'Gerente de produtos bancários' where codigo = '141705'; --.go
update codigocbo set descricao = 'Gerente de projetos de tecnologia da informaçao' where codigo = '142520'; --.go
update codigocbo set descricao = 'Gerente de projetos e serviços de manutençao' where codigo = '142705'; --.go
update codigocbo set descricao = 'Gerente de recuperaçao de crédito' where codigo = '141735'; --.go
update codigocbo set descricao = 'Gerente de recursos humanos' where codigo = '142205'; --.go
update codigocbo set descricao = 'Gerente de rede' where codigo = '142505'; --.go
update codigocbo set descricao = 'Gerente de restaurante' where codigo = '141510'; --.go
update codigocbo set descricao = 'Gerente de riscos' where codigo = '142110'; --.go
update codigocbo set descricao = 'Gerente de segurança de tecnologia da informaçao' where codigo = '142525'; --.go
update codigocbo set descricao = 'Gerente de serviços culturais' where codigo = '131115'; --.go
update codigocbo set descricao = 'Gerente de serviços de saúde' where codigo = '131210'; --.go
update codigocbo set descricao = 'Gerente de serviços educacionais da área pública' where codigo = '131320'; --.go
update codigocbo set descricao = 'Gerente de serviços sociais' where codigo = '131120'; --.go
update codigocbo set descricao = 'Gerente de suporte técnico de tecnologia da informaçao' where codigo = '142530'; --.go
update codigocbo set descricao = 'Gerente de suprimentos' where codigo = '142410'; --.go
update codigocbo set descricao = 'Gerente de turismo' where codigo = '141525'; --.go
update codigocbo set descricao = 'Gerente de vendas' where codigo = '142320'; --.go
update codigocbo set descricao = 'Gerente financeiro' where codigo = '142115'; --.go
update codigocbo set descricao = 'Gesseiro' where codigo = '716405'; --.go
update codigocbo set descricao = 'Gestor em segurança' where codigo = '252605'; --.go
update codigocbo set descricao = 'Governador de estado' where codigo = '111230'; --.go
update codigocbo set descricao = 'Governador do distrito federal' where codigo = '111235'; --.go
update codigocbo set descricao = 'Governanta de hotelaria' where codigo = '513115'; --.go
update codigocbo set descricao = 'Gravador, à mao (encadernaçao)' where codigo = '768705'; --.go
update codigocbo set descricao = 'Gravador de inscriçoes em pedra' where codigo = '712210'; --.go
update codigocbo set descricao = 'Gravador de matriz calcográfica' where codigo = '766135'; --.go
update codigocbo set descricao = 'Gravador de matriz para flexografia (clicherista)' where codigo = '766115'; --.go
update codigocbo set descricao = 'Gravador de matriz para rotogravura (eletromecânico e químico)' where codigo = '766130'; --.go
update codigocbo set descricao = 'Gravador de matriz serigráfica' where codigo = '766140'; --.go
update codigocbo set descricao = 'Gravador de relevos em pedra' where codigo = '712215'; --.go
update codigocbo set descricao = 'Gravador de vidro a  água-forte' where codigo = '752215'; --.go
update codigocbo set descricao = 'Gravador de vidro a  esmeril' where codigo = '752220'; --.go
update codigocbo set descricao = 'Gravador de vidro a  jato de areia' where codigo = '752225'; --.go
update codigocbo set descricao = 'Gravador (joalheria e ourivesaria)' where codigo = '751115'; --.go
update codigocbo set descricao = 'Guarda-civil municipal' where codigo = '517215'; --.go
update codigocbo set descricao = 'Guardador de veículos' where codigo = '519925'; --.go
update codigocbo set descricao = 'Guarda portuário' where codigo = '517335'; --.go
update codigocbo set descricao = 'Guarda-roupeira de cinema' where codigo = '513325'; --.go
update codigocbo set descricao = 'Guia de turismo' where codigo = '511405'; --.go
update codigocbo set descricao = 'Guia florestal' where codigo = '632005'; --.go
update codigocbo set descricao = 'Guincheiro (construçao civil)' where codigo = '782205'; --.go
update codigocbo set descricao = 'Hidrogenador de óleos e gorduras' where codigo = '841444'; --.go
update codigocbo set descricao = 'Hidrogeólogo' where codigo = '213425'; --.go
update codigocbo set descricao = 'Identificador florestal' where codigo = '632115'; --.go
update codigocbo set descricao = 'Iluminador (televisao)' where codigo = '372110'; --.go
update codigocbo set descricao = 'Imediato da marinha mercante' where codigo = '215125'; --.go
update codigocbo set descricao = 'Impregnador de madeira' where codigo = '772110'; --.go
update codigocbo set descricao = 'Impressor calcográfico' where codigo = '766210'; --.go
update codigocbo set descricao = 'Impressor de corte e vinco' where codigo = '766310'; --.go
update codigocbo set descricao = 'Impressor de ofsete (plano e rotativo)' where codigo = '766215'; --.go
update codigocbo set descricao = 'Impressor de rotativa' where codigo = '766220'; --.go
update codigocbo set descricao = 'Impressor de rotogravura' where codigo = '766225'; --.go
update codigocbo set descricao = 'Impressor digital' where codigo = '766230'; --.go
update codigocbo set descricao = 'Impressor flexográfico' where codigo = '766235'; --.go
update codigocbo set descricao = 'Impressor letterset' where codigo = '766240'; --.go
update codigocbo set descricao = 'Impressor (serigrafia)' where codigo = '766205'; --.go
update codigocbo set descricao = 'Impressor tampográfico' where codigo = '766245'; --.go
update codigocbo set descricao = 'Impressor tipográfico' where codigo = '766250'; --.go
update codigocbo set descricao = 'Inseminador' where codigo = '623010'; --.go
update codigocbo set descricao = 'Inspetor de alunos de escola privada' where codigo = '334105'; --.go
update codigocbo set descricao = 'Inspetor de alunos de escola pública' where codigo = '334110'; --.go
update codigocbo set descricao = 'Inspetor de aviaçao civil' where codigo = '342530'; --.go
update codigocbo set descricao = 'Inspetor de estamparia (produçao têxtil)' where codigo = '761805'; --.go
update codigocbo set descricao = 'Inspetor de qualidade' where codigo = '391205'; --.go
update codigocbo set descricao = 'Inspetor de risco' where codigo = '351725'; --.go
update codigocbo set descricao = 'Inspetor de serviços de transportes rodoviários (passageiros e cargas)' where codigo = '342310'; --.go
update codigocbo set descricao = 'Inspetor de sinistros' where codigo = '351730'; --.go
update codigocbo set descricao = 'Inspetor de soldagem' where codigo = '314605'; --.go
update codigocbo set descricao = 'Inspetor de terminal' where codigo = '215130'; --.go
update codigocbo set descricao = 'Inspetor de terraplenagem' where codigo = '710215'; --.go
update codigocbo set descricao = 'Inspetor de via permanente (trilhos)' where codigo = '991110'; --.go
update codigocbo set descricao = 'Inspetor naval' where codigo = '215135'; --.go
update codigocbo set descricao = 'Instalador de cortinas e persianas, portas sanfonadas e boxe' where codigo = '523105'; --.go
update codigocbo set descricao = 'Instalador de isolantes acústicos' where codigo = '715710'; --.go
update codigocbo set descricao = 'Instalador de isolantes térmicos de caldeira e tubulaçoes' where codigo = '715720'; --.go
update codigocbo set descricao = 'Instalador de isolantes térmicos (refrigeraçao e climatizaçao)' where codigo = '715715'; --.go
update codigocbo set descricao = 'Instalador de linhas elétricas de alta e baixa - tensao (rede aérea e subterrânea)' where codigo = '732120'; --.go
update codigocbo set descricao = 'Instalador de material isolante, a mao (edificaçoes)' where codigo = '715725'; --.go
update codigocbo set descricao = 'Instalador de material isolante, a máquina (edificaçoes)' where codigo = '715730'; --.go
update codigocbo set descricao = 'Instalador de sistemas eletroeletrônicos de segurança' where codigo = '951305'; --.go
update codigocbo set descricao = 'Instalador de som e acessórios de veículos' where codigo = '523110'; --.go
update codigocbo set descricao = 'Instalador de tubulaçoes' where codigo = '724115'; --.go
update codigocbo set descricao = 'Instalador de tubulaçoes (aeronaves)' where codigo = '724120'; --.go
update codigocbo set descricao = 'Instalador de tubulaçoes de gás combustível (produçao e distribuiçao)' where codigo = '724130'; --.go
update codigocbo set descricao = 'Instalador de tubulaçoes de vapor (produçao e distribuiçao)' where codigo = '724135'; --.go
update codigocbo set descricao = 'Instalador de tubulaçoes (embarcaçoes)' where codigo = '724125'; --.go
update codigocbo set descricao = 'Instalador eletricista (traçao de veículos)' where codigo = '732125'; --.go
update codigocbo set descricao = 'Instalador-reparador de equipamentos de comutaçao em telefonia' where codigo = '731305'; --.go
update codigocbo set descricao = 'Instalador-reparador de equipamentos de energia em telefonia' where codigo = '731310'; --.go
update codigocbo set descricao = 'Instalador-reparador de equipamentos de transmissao em telefonia' where codigo = '731315'; --.go
update codigocbo set descricao = 'Instalador-reparador de linhas e aparelhos de telecomunicaçoes' where codigo = '731320'; --.go
update codigocbo set descricao = 'Instalador-reparador de redes e cabos telefônicos' where codigo = '731325'; --.go
update codigocbo set descricao = 'Instalador-reparador de redes telefônicas e de comunicaçao de dados' where codigo = '732130'; --.go
update codigocbo set descricao = 'Instrumentador cirúrgico' where codigo = '322225'; --.go
update codigocbo set descricao = 'Instrutor de aprendizagem e treinamento agropecuário' where codigo = '233205'; --.go
update codigocbo set descricao = 'Instrutor de aprendizagem e treinamento industrial' where codigo = '233210'; --.go
update codigocbo set descricao = 'Instrutor de auto-escola' where codigo = '333105'; --.go
update codigocbo set descricao = 'Instrutor de cursos livres' where codigo = '333110'; --.go
update codigocbo set descricao = 'Instrutor de vôo' where codigo = '215315'; --.go
update codigocbo set descricao = 'Intérprete' where codigo = '261410'; --.go
update codigocbo set descricao = 'Intérprete de língua de sinais' where codigo = '261425'; --.go
update codigocbo set descricao = 'Investigador de polícia' where codigo = '351810'; --.go
update codigocbo set descricao = 'Jardineiro' where codigo = '622010'; --.go
update codigocbo set descricao = 'Joalheiro' where codigo = '751010'; --.go
update codigocbo set descricao = 'Joalheiro (reparaçoes)' where codigo = '751015'; --.go
update codigocbo set descricao = 'Jóquei' where codigo = '377130'; --.go
update codigocbo set descricao = 'Jornaleiro (em banca de jornal)' where codigo = '524210'; --.go
update codigocbo set descricao = 'Jornalista' where codigo = '261125'; --.go
update codigocbo set descricao = 'Juiz auditor estadual - justiça militar' where codigo = '111340'; --.go
update codigocbo set descricao = 'Juiz auditor federal - justiça militar' where codigo = '111335'; --.go
update codigocbo set descricao = 'Juiz de direito' where codigo = '111325'; --.go
update codigocbo set descricao = 'Juiz do trabalho' where codigo = '111345'; --.go
update codigocbo set descricao = 'Juiz federal' where codigo = '111330'; --.go
update codigocbo set descricao = 'Kardexista' where codigo = '415125'; --.go
update codigocbo set descricao = 'Laboratorista fotográfico' where codigo = '766405'; --.go
update codigocbo set descricao = 'Ladrilheiro' where codigo = '716510'; --.go
update codigocbo set descricao = 'Lagareiro' where codigo = '841448'; --.go
update codigocbo set descricao = 'Laminador de metais preciosos a  mao' where codigo = '751120'; --.go
update codigocbo set descricao = 'Laminador de plástico' where codigo = '811745'; --.go
update codigocbo set descricao = 'Lapidador de vidros e cristais' where codigo = '752230'; --.go
update codigocbo set descricao = 'Lapidador (jóias)' where codigo = '751020'; --.go
update codigocbo set descricao = 'Lavadeiro, em geral' where codigo = '516305'; --.go
update codigocbo set descricao = 'Lavador de artefatos de tapeçaria' where codigo = '516315'; --.go
update codigocbo set descricao = 'Lavador de garrafas, vidros e outros utensílios' where codigo = '519930'; --.go
update codigocbo set descricao = 'Lavador de la' where codigo = '761110'; --.go
update codigocbo set descricao = 'Lavador de peças' where codigo = '992120'; --.go
update codigocbo set descricao = 'Lavador de roupas' where codigo = '516405'; --.go
update codigocbo set descricao = 'Lavador de roupas  a maquina' where codigo = '516310'; --.go
update codigocbo set descricao = 'Lavador de veículos' where codigo = '519935'; --.go
update codigocbo set descricao = 'Leiloeiro' where codigo = '354405'; --.go
update codigocbo set descricao = 'Leiturista' where codigo = '519940'; --.go
update codigocbo set descricao = 'Líder de comunidade caiçara' where codigo = '113010'; --.go
update codigocbo set descricao = 'Ligador de linhas telefônicas' where codigo = '732135'; --.go
update codigocbo set descricao = 'Limpador a seco, à máquina' where codigo = '516320'; --.go
update codigocbo set descricao = 'Limpador de fachadas' where codigo = '514315'; --.go
update codigocbo set descricao = 'Limpador de piscinas' where codigo = '514330'; --.go
update codigocbo set descricao = 'Limpador de roupas a seco, à mao' where codigo = '516410'; --.go
update codigocbo set descricao = 'Limpador de vidros' where codigo = '514305'; --.go
update codigocbo set descricao = 'Lingotador' where codigo = '722210'; --.go
update codigocbo set descricao = 'Lingüista' where codigo = '261415'; --.go
update codigocbo set descricao = 'Linotipista' where codigo = '768610'; --.go
update codigocbo set descricao = 'Lixador de couros e peles' where codigo = '762315'; --.go
update codigocbo set descricao = 'Localizador (cobrador)' where codigo = '421315'; --.go
update codigocbo set descricao = 'Locutor de rádio e televisao' where codigo = '261715'; --.go
update codigocbo set descricao = 'Locutor publicitário de rádio e televisao' where codigo = '261720'; --.go
update codigocbo set descricao = 'Lubrificador de embarcaçoes' where codigo = '919115'; --.go
update codigocbo set descricao = 'Lubrificador de veículos automotores (exceto embarcaçoes)' where codigo = '919110'; --.go
update codigocbo set descricao = 'Lubrificador industrial' where codigo = '919105'; --.go
update codigocbo set descricao = 'Ludomotricista' where codigo = '224110'; --.go
update codigocbo set descricao = 'Lustrador de peças de madeira' where codigo = '775115'; --.go
update codigocbo set descricao = 'Lustrador de piso' where codigo = '716520'; --.go
update codigocbo set descricao = 'Luthier (restauraçao de cordas arcadas)' where codigo = '915215'; --.go
update codigocbo set descricao = 'Macheiro, a mao' where codigo = '722305'; --.go
update codigocbo set descricao = 'Macheiro, a  máquina' where codigo = '722310'; --.go
update codigocbo set descricao = 'Mae social' where codigo = '516215'; --.go
update codigocbo set descricao = 'Magarefe' where codigo = '848520'; --.go
update codigocbo set descricao = 'Mágico' where codigo = '376235'; --.go
update codigocbo set descricao = 'Maître' where codigo = '510135'; --.go
update codigocbo set descricao = 'Major bombeiro militar' where codigo = '030110'; --.go
update codigocbo set descricao = 'Major da polícia militar' where codigo = '020115'; --.go
update codigocbo set descricao = 'Malabarista' where codigo = '376240'; --.go
update codigocbo set descricao = 'Malteiro (germinaçao)' where codigo = '841725'; --.go
update codigocbo set descricao = 'Manicure' where codigo = '516120'; --.go
update codigocbo set descricao = 'Manobrador' where codigo = '783110'; --.go
update codigocbo set descricao = 'Manteigueiro na fabricaçao de laticínio' where codigo = '848215'; --.go
update codigocbo set descricao = 'Mantenedor de equipamentos de parques de diversoes e similares' where codigo = '991205'; --.go
update codigocbo set descricao = 'Mantenedor de sistemas eletroeletrônicos de segurança' where codigo = '951310'; --.go
update codigocbo set descricao = 'Maquetista na marcenaria' where codigo = '771115'; --.go
update codigocbo set descricao = 'Maquiador' where codigo = '516125'; --.go
update codigocbo set descricao = 'Maquiador de caracterizaçao' where codigo = '516130'; --.go
update codigocbo set descricao = 'Maquinista de cinema e vídeo' where codigo = '374210'; --.go
update codigocbo set descricao = 'Maquinista de embarcaçoes' where codigo = '862110'; --.go
update codigocbo set descricao = 'Maquinista de teatro e espetáculos' where codigo = '374215'; --.go
update codigocbo set descricao = 'Maquinista de trem' where codigo = '782610'; --.go
update codigocbo set descricao = 'Maquinista de trem metropolitano' where codigo = '782615'; --.go
update codigocbo set descricao = 'Marcador de peças confeccionadas para bordar' where codigo = '763315'; --.go
update codigocbo set descricao = 'Marcador de produtos (siderúrgico e metalúrgico)' where codigo = '821415'; --.go
update codigocbo set descricao = 'Marceneiro' where codigo = '771105'; --.go
update codigocbo set descricao = 'Marcheteiro' where codigo = '775120'; --.go
update codigocbo set descricao = 'Marinheiro de convés (marítimo e fluviário)' where codigo = '782705'; --.go
update codigocbo set descricao = 'Marinheiro de esporte e recreio' where codigo = '782721'; --.go
update codigocbo set descricao = 'Marinheiro de máquinas' where codigo = '782710'; --.go
update codigocbo set descricao = 'Marmorista (construçao)' where codigo = '716525'; --.go
update codigocbo set descricao = 'Masseiro (massas alimentícias)' where codigo = '848315'; --.go
update codigocbo set descricao = 'Massoterapeuta' where codigo = '322116'; --.go
update codigocbo set descricao = 'Matemático' where codigo = '211115'; --.go
update codigocbo set descricao = 'Matemático aplicado' where codigo = '211120'; --.go
update codigocbo set descricao = 'Matizador de couros e peles' where codigo = '762320'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de aeronaves, em geral' where codigo = '914105'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de aparelhos de levantamento' where codigo = '913105'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de aparelhos esportivos e de ginástica' where codigo = '919305'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de automóveis, motocicletas e veículos similares' where codigo = '914405'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de bicicletas e veículos similares' where codigo = '919310'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de bomba injetora (exceto de veículos automotores)' where codigo = '911105'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de bombas' where codigo = '911110'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de compressores de ar' where codigo = '911115'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de empilhadeiras e outros veículos de cargas leves' where codigo = '914410'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de equipamento de mineraçao' where codigo = '913110'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de instalaçoes mecânicas de edifícios' where codigo = '954120'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de máquinas agrícolas' where codigo = '913115'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de máquinas cortadoras de grama, roçadeiras, motosserras e similares' where codigo = '919205'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de máquinas de construçao e terraplenagem' where codigo = '913120'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de máquinas, em geral' where codigo = '911305'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de máquinas-ferramentas (usinagem de metais)' where codigo = '911325'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de máquinas gráficas' where codigo = '911310'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de máquinas operatrizes (lavra de madeira)' where codigo = '911315'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de máquinas têxteis' where codigo = '911320'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de motocicletas' where codigo = '914415'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de motores diesel (exceto de veículos automotores)' where codigo = '911120'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de motores e equipamentos navais' where codigo = '914205'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de redutores' where codigo = '911125'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de sistema hidráulico de aeronaves (serviços de pista e hangar)' where codigo = '914110'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de tratores' where codigo = '914420'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de turbinas (exceto de aeronaves)' where codigo = '911130'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de turbocompressores' where codigo = '911135'; --.go
update codigocbo set descricao = 'Mecânico de manutençao de veículos ferroviários' where codigo = '914305'; --.go
update codigocbo set descricao = 'Mecânico de manutençao e instalaçao de aparelhos de climatizaçao e  refrigeraçao' where codigo = '911205'; --.go
update codigocbo set descricao = 'Mecânico de refrigeraçao' where codigo = '725705'; --.go
update codigocbo set descricao = 'Mecânico de veículos automotores a diesel (exceto tratores)' where codigo = '914425'; --.go
update codigocbo set descricao = 'Mecânico de vôo' where codigo = '341115'; --.go
update codigocbo set descricao = 'Mecânico montador de motores de aeronaves' where codigo = '725405'; --.go
update codigocbo set descricao = 'Mecânico montador de motores de embarcaçoes' where codigo = '725410'; --.go
update codigocbo set descricao = 'Mecânico montador de motores de explosao e diesel' where codigo = '725415'; --.go
update codigocbo set descricao = 'Mecânico montador de turboalimentadores' where codigo = '725420'; --.go
update codigocbo set descricao = 'Médico acupunturista' where codigo = '223101'; --.go
update codigocbo set descricao = 'Médico alergista e imunologista' where codigo = '223102'; --.go
update codigocbo set descricao = 'Médico anatomopatologista' where codigo = '223103'; --.go
update codigocbo set descricao = 'Médico anestesiologista' where codigo = '223104'; --.go
update codigocbo set descricao = 'Médico angiologista' where codigo = '223105'; --.go
update codigocbo set descricao = 'Médico cardiologista' where codigo = '223106'; --.go
update codigocbo set descricao = 'Médico cirurgiao cardiovascular' where codigo = '223107'; --.go
update codigocbo set descricao = 'Médico cirurgiao de cabeça e pescoço' where codigo = '223108'; --.go
update codigocbo set descricao = 'Médico cirurgiao do aparelho digestivo' where codigo = '223109'; --.go
update codigocbo set descricao = 'Médico cirurgiao geral' where codigo = '223110'; --.go
update codigocbo set descricao = 'Médico cirurgiao pediátrico' where codigo = '223111'; --.go
update codigocbo set descricao = 'Médico cirurgiao plástico' where codigo = '223112'; --.go
update codigocbo set descricao = 'Médico cirurgiao torácico' where codigo = '223113'; --.go
update codigocbo set descricao = 'Médico citopatologista' where codigo = '223114'; --.go
update codigocbo set descricao = 'Médico clínico' where codigo = '223115'; --.go
update codigocbo set descricao = 'Médico da estratégia de saúde da família' where codigo = '223162'; --.go
update codigocbo set descricao = 'Médico dermatologista' where codigo = '223117'; --.go
update codigocbo set descricao = 'Médico de saúde da família' where codigo = '223116'; --.go
update codigocbo set descricao = 'Médico do trabalho' where codigo = '223118'; --.go
update codigocbo set descricao = 'Médico em eletroencefalografia' where codigo = '223119'; --.go
update codigocbo set descricao = 'Médico em endoscopia' where codigo = '223120'; --.go
update codigocbo set descricao = 'Médico em medicina de tráfego' where codigo = '223121'; --.go
update codigocbo set descricao = 'Médico em medicina intensiva' where codigo = '223122'; --.go
update codigocbo set descricao = 'Médico em medicina nuclear' where codigo = '223123'; --.go
update codigocbo set descricao = 'Médico em radiologia e diagnóstico por imagem' where codigo = '223124'; --.go
update codigocbo set descricao = 'Médico endocrinologista e metabologista' where codigo = '223125'; --.go
update codigocbo set descricao = 'Médico fisiatra' where codigo = '223126'; --.go
update codigocbo set descricao = 'Médico foniatra' where codigo = '223127'; --.go
update codigocbo set descricao = 'Médico gastroenterologista' where codigo = '223128'; --.go
update codigocbo set descricao = 'Médico generalista' where codigo = '223129'; --.go
update codigocbo set descricao = 'Médico geneticista' where codigo = '223130'; --.go
update codigocbo set descricao = 'Médico geriatra' where codigo = '223131'; --.go
update codigocbo set descricao = 'Médico ginecologista e obstetra' where codigo = '223132'; --.go
update codigocbo set descricao = 'Médico hematologista' where codigo = '223133'; --.go
update codigocbo set descricao = 'Médico hemoterapeuta' where codigo = '223134'; --.go
update codigocbo set descricao = 'Médico homeopata' where codigo = '223135'; --.go
update codigocbo set descricao = 'Médico infectologista' where codigo = '223136'; --.go
update codigocbo set descricao = 'Médico legista' where codigo = '223137'; --.go
update codigocbo set descricao = 'Médico mastologista' where codigo = '223138'; --.go
update codigocbo set descricao = 'Médico nefrologista' where codigo = '223139'; --.go
update codigocbo set descricao = 'Médico neurocirurgiao' where codigo = '223140'; --.go
update codigocbo set descricao = 'Médico neurofisiologista' where codigo = '223141'; --.go
update codigocbo set descricao = 'Médico neurologista' where codigo = '223142'; --.go
update codigocbo set descricao = 'Médico nutrologista' where codigo = '223143'; --.go
update codigocbo set descricao = 'Médico oftalmologista' where codigo = '223144'; --.go
update codigocbo set descricao = 'Médico oncologista' where codigo = '223145'; --.go
update codigocbo set descricao = 'Médico ortopedista e traumatologista' where codigo = '223146'; --.go
update codigocbo set descricao = 'Médico otorrinolaringologista' where codigo = '223147'; --.go
update codigocbo set descricao = 'Médico patologista clínico' where codigo = '223148'; --.go
update codigocbo set descricao = 'Médico pediatra' where codigo = '223149'; --.go
update codigocbo set descricao = 'Médico perito' where codigo = '223150'; --.go
update codigocbo set descricao = 'Médico pneumologista' where codigo = '223151'; --.go
update codigocbo set descricao = 'Médico proctologista' where codigo = '223152'; --.go
update codigocbo set descricao = 'Médico psiquiatra' where codigo = '223153'; --.go
update codigocbo set descricao = 'Médico radioterapeuta' where codigo = '223154'; --.go
update codigocbo set descricao = 'Médico reumatologista' where codigo = '223155'; --.go
update codigocbo set descricao = 'Médico sanitarista' where codigo = '223156'; --.go
update codigocbo set descricao = 'Médico urologista' where codigo = '223157'; --.go
update codigocbo set descricao = 'Médico veterinário' where codigo = '223305'; --.go
update codigocbo set descricao = 'Membro de liderança quilombola' where codigo = '113015'; --.go
update codigocbo set descricao = 'Membro superior do poder executivo' where codigo = '111225'; --.go
update codigocbo set descricao = 'Mergulhador profissional (raso e profundo)' where codigo = '781705'; --.go
update codigocbo set descricao = 'Mestre (afiador de ferramentas)' where codigo = '720105'; --.go
update codigocbo set descricao = 'Mestre carpinteiro' where codigo = '770110'; --.go
update codigocbo set descricao = 'Mestre (construçao civil)' where codigo = '710205'; --.go
update codigocbo set descricao = 'Mestre (construçao naval)' where codigo = '720205'; --.go
update codigocbo set descricao = 'Mestre de aciaria' where codigo = '820110'; --.go
update codigocbo set descricao = 'Mestre de alto-forno' where codigo = '820115'; --.go
update codigocbo set descricao = 'Mestre de cabotagem' where codigo = '341210'; --.go
update codigocbo set descricao = 'Mestre de caldeiraria' where codigo = '720110'; --.go
update codigocbo set descricao = 'Mestre de construçao de fornos' where codigo = '720220'; --.go
update codigocbo set descricao = 'Mestre de ferramentaria' where codigo = '720115'; --.go
update codigocbo set descricao = 'Mestre de forjaria' where codigo = '720120'; --.go
update codigocbo set descricao = 'Mestre de forno elétrico' where codigo = '820120'; --.go
update codigocbo set descricao = 'Mestre de fundiçao' where codigo = '720125'; --.go
update codigocbo set descricao = 'Mestre de galvanoplastia' where codigo = '720130'; --.go
update codigocbo set descricao = 'Mestre de laminaçao' where codigo = '820125'; --.go
update codigocbo set descricao = 'Mestre de linhas (ferrovias)' where codigo = '710210'; --.go
update codigocbo set descricao = 'Mestre de pintura (tratamento de superfícies)' where codigo = '720135'; --.go
update codigocbo set descricao = 'Mestre de produçao farmacêutica' where codigo = '810305'; --.go
update codigocbo set descricao = 'Mestre de produçao química' where codigo = '810110'; --.go
update codigocbo set descricao = 'Mestre de siderurgia' where codigo = '820105'; --.go
update codigocbo set descricao = 'Mestre de soldagem' where codigo = '720140'; --.go
update codigocbo set descricao = 'Mestre de trefilaçao de metais' where codigo = '720145'; --.go
update codigocbo set descricao = 'Mestre de usinagem' where codigo = '720150'; --.go
update codigocbo set descricao = 'Mestre fluvial' where codigo = '341215'; --.go
update codigocbo set descricao = 'Mestre (indústria de automotores e material de transportes)' where codigo = '720210'; --.go
update codigocbo set descricao = 'Mestre (indústria de borracha e plástico)' where codigo = '810205'; --.go
update codigocbo set descricao = 'Mestre (indústria de celulose, papel e papelao)' where codigo = '830105'; --.go
update codigocbo set descricao = 'Mestre (indústria de madeira e mobiliário)' where codigo = '770105'; --.go
update codigocbo set descricao = 'Mestre (indústria de máquinas e outros equipamentos mecânicos)' where codigo = '720215'; --.go
update codigocbo set descricao = 'Mestre (indústria petroquímica e carboquímica)' where codigo = '810105'; --.go
update codigocbo set descricao = 'Mestre (indústria têxtil e de confecçoes)' where codigo = '760125'; --.go
update codigocbo set descricao = 'Mestre serralheiro' where codigo = '720155'; --.go
update codigocbo set descricao = 'Metalizador a pistola' where codigo = '723220'; --.go
update codigocbo set descricao = 'Metalizador (banho quente)' where codigo = '723225'; --.go
update codigocbo set descricao = 'Meteorologista' where codigo = '213315'; --.go
update codigocbo set descricao = 'Metrologista' where codigo = '352305'; --.go
update codigocbo set descricao = 'Microfonista' where codigo = '374140'; --.go
update codigocbo set descricao = 'Mineiro' where codigo = '711130'; --.go
update codigocbo set descricao = 'Minhocultor' where codigo = '613415'; --.go
update codigocbo set descricao = 'Ministro de culto religioso' where codigo = '263105'; --.go
update codigocbo set descricao = 'Ministro de estado' where codigo = '111215'; --.go
update codigocbo set descricao = 'Ministro do superior tribunal de justiça' where codigo = '111310'; --.go
update codigocbo set descricao = 'Ministro do  superior tribunal do trabalho' where codigo = '111320'; --.go
update codigocbo set descricao = 'Ministro do  superior tribunal militar' where codigo = '111315'; --.go
update codigocbo set descricao = 'Ministro do supremo tribunal federal' where codigo = '111305'; --.go
update codigocbo set descricao = 'Missionário' where codigo = '263110'; --.go
update codigocbo set descricao = 'Misturador de café' where codigo = '841605'; --.go
update codigocbo set descricao = 'Misturador de chá ou mate' where codigo = '841630'; --.go
update codigocbo set descricao = 'Moço de convés (marítimo e fluviário)' where codigo = '782715'; --.go
update codigocbo set descricao = 'Moço de máquinas (marítimo e fluviário)' where codigo = '782720'; --.go
update codigocbo set descricao = 'Modelador de madeira' where codigo = '771110'; --.go
update codigocbo set descricao = 'Modelador de metais (fundiçao)' where codigo = '721115'; --.go
update codigocbo set descricao = 'Modelista de calçados' where codigo = '318815'; --.go
update codigocbo set descricao = 'Modelista de roupas' where codigo = '318810'; --.go
update codigocbo set descricao = 'Modelo artístico' where codigo = '376405'; --.go
update codigocbo set descricao = 'Modelo de modas' where codigo = '376410'; --.go
update codigocbo set descricao = 'Modelo publicitário' where codigo = '376415'; --.go
update codigocbo set descricao = 'Moedor de café' where codigo = '841615'; --.go
update codigocbo set descricao = 'Moedor de sal' where codigo = '841205'; --.go
update codigocbo set descricao = 'Moldador, a  mao' where codigo = '722315'; --.go
update codigocbo set descricao = 'Moldador, a  máquina' where codigo = '722320'; --.go
update codigocbo set descricao = 'Moldador de abrasivos na fabricaçao de cerâmica, vidro e porcelana' where codigo = '823230'; --.go
update codigocbo set descricao = 'Moldador de borracha por compressao' where codigo = '811750'; --.go
update codigocbo set descricao = 'Moldador de corpos de prova em usinas de concreto' where codigo = '715310'; --.go
update codigocbo set descricao = 'Moldador de plástico por compressao' where codigo = '811760'; --.go
update codigocbo set descricao = 'Moldador de plástico por injeçao' where codigo = '811770'; --.go
update codigocbo set descricao = 'Moldador (vidros)' where codigo = '752110'; --.go
update codigocbo set descricao = 'Moleiro de cereais (exceto arroz)' where codigo = '841105'; --.go
update codigocbo set descricao = 'Moleiro de especiarias' where codigo = '841110'; --.go
update codigocbo set descricao = 'Moleiro de minérios' where codigo = '712105'; --.go
update codigocbo set descricao = 'Moleiro (tratamentos químicos e afins)' where codigo = '811105'; --.go
update codigocbo set descricao = 'Monitor de dependente químico' where codigo = '515303'; --.go
update codigocbo set descricao = 'Monitor de teleatendimento' where codigo = '422215'; --.go
update codigocbo set descricao = 'Monotipista' where codigo = '768615'; --.go
update codigocbo set descricao = 'Montador de andaimes (edificaçoes)' where codigo = '715545'; --.go
update codigocbo set descricao = 'Montador de artefatos de couro (exceto roupas e calçados)' where codigo = '765315'; --.go
update codigocbo set descricao = 'Montador de bicicletas' where codigo = '919315'; --.go
update codigocbo set descricao = 'Montador de calçados' where codigo = '764210'; --.go
update codigocbo set descricao = 'Montador de equipamento de levantamento' where codigo = '725305'; --.go
update codigocbo set descricao = 'Montador de equipamentos elétricos' where codigo = '731135'; --.go
update codigocbo set descricao = 'Montador de equipamentos elétricos (aparelhos eletrodomésticos)' where codigo = '731120'; --.go
update codigocbo set descricao = 'Montador de equipamentos elétricos (centrais elétricas)' where codigo = '731125'; --.go
update codigocbo set descricao = 'Montador de equipamentos elétricos (elevadores e equipamentos similares)' where codigo = '731155'; --.go
update codigocbo set descricao = 'Montador de equipamentos elétricos (instrumentos de mediçao)' where codigo = '731115'; --.go
update codigocbo set descricao = 'Montador de equipamentos elétricos (motores e dínamos)' where codigo = '731130'; --.go
update codigocbo set descricao = 'Montador de equipamentos elétricos (transformadores)' where codigo = '731160'; --.go
update codigocbo set descricao = 'Montador de equipamentos eletrônicos' where codigo = '731150'; --.go
update codigocbo set descricao = 'Montador de equipamentos eletrônicos (aparelhos médicos)' where codigo = '731105'; --.go
update codigocbo set descricao = 'Montador de equipamentos eletrônicos (computadores e equipamentos auxiliares)' where codigo = '731110'; --.go
update codigocbo set descricao = 'Montador de equipamentos eletrônicos (estaçao de rádio, tv e equipamentos de radar)' where codigo = '731205'; --.go
update codigocbo set descricao = 'Montador de equipamentos eletrônicos (instalaçoes de sinalizaçao)' where codigo = '731140'; --.go
update codigocbo set descricao = 'Montador de equipamentos eletrônicos (máquinas industriais)' where codigo = '731145'; --.go
update codigocbo set descricao = 'Montador de estruturas de aeronaves' where codigo = '725605'; --.go
update codigocbo set descricao = 'Montador de estruturas metálicas' where codigo = '724205'; --.go
update codigocbo set descricao = 'Montador de estruturas metálicas de embarcaçoes' where codigo = '724210'; --.go
update codigocbo set descricao = 'Montador de filmes' where codigo = '374420'; --.go
update codigocbo set descricao = 'Montador de fotolito (analógico e digital)' where codigo = '766125'; --.go
update codigocbo set descricao = 'Montador de instrumentos de óptica' where codigo = '741110'; --.go
update codigocbo set descricao = 'Montador de instrumentos de precisao' where codigo = '741115'; --.go
update codigocbo set descricao = 'Montador de máquinas' where codigo = '725205'; --.go
update codigocbo set descricao = 'Montador de máquinas agrícolas' where codigo = '725310'; --.go
update codigocbo set descricao = 'Montador de máquinas de minas e pedreiras' where codigo = '725315'; --.go
update codigocbo set descricao = 'Montador de máquinas de terraplenagem' where codigo = '725320'; --.go
update codigocbo set descricao = 'Montador de máquinas-ferramentas (usinagem de metais)' where codigo = '725225'; --.go
update codigocbo set descricao = 'Montador de máquinas gráficas' where codigo = '725210'; --.go
update codigocbo set descricao = 'Montador de máquinas, motores e acessórios (montagem em série)' where codigo = '725105'; --.go
update codigocbo set descricao = 'Montador de máquinas operatrizes para madeira' where codigo = '725215'; --.go
update codigocbo set descricao = 'Montador de máquinas têxteis' where codigo = '725220'; --.go
update codigocbo set descricao = 'Montador de móveis e artefatos de madeira' where codigo = '774105'; --.go
update codigocbo set descricao = 'Montador de sistemas de combustível de aeronaves' where codigo = '725610'; --.go
update codigocbo set descricao = 'Montador de veículos (linha de montagem)' where codigo = '725505'; --.go
update codigocbo set descricao = 'Montador de veículos (reparaçao)' where codigo = '991310'; --.go
update codigocbo set descricao = 'Mordomo de hotelaria' where codigo = '513110'; --.go
update codigocbo set descricao = 'Mordomo de residência' where codigo = '513105'; --.go
update codigocbo set descricao = 'Mosaísta' where codigo = '716530'; --.go
update codigocbo set descricao = 'Motociclista no transporte de documentos e pequenos volumes' where codigo = '519110'; --.go
update codigocbo set descricao = 'Motorista de caminhao (rotas regionais e internacionais)' where codigo = '782510'; --.go
update codigocbo set descricao = 'Motorista de carro de passeio' where codigo = '782305'; --.go
update codigocbo set descricao = 'Motorista de furgao ou veículo similar' where codigo = '782310'; --.go
update codigocbo set descricao = 'Motorista de ônibus rodoviário' where codigo = '782405'; --.go
update codigocbo set descricao = 'Motorista de ônibus urbano' where codigo = '782410'; --.go
update codigocbo set descricao = 'Motorista de táxi' where codigo = '782315'; --.go
update codigocbo set descricao = 'Motorista de trólebus' where codigo = '782415'; --.go
update codigocbo set descricao = 'Motorista operacional de guincho' where codigo = '782515'; --.go
update codigocbo set descricao = 'Motorneiro' where codigo = '782620'; --.go
update codigocbo set descricao = 'Museólogo' where codigo = '261310'; --.go
update codigocbo set descricao = 'Músico arranjador' where codigo = '262610'; --.go
update codigocbo set descricao = 'Músico intérprete cantor' where codigo = '262705'; --.go
update codigocbo set descricao = 'Músico intérprete instrumentista' where codigo = '262710'; --.go
update codigocbo set descricao = 'Musicólogo' where codigo = '262620'; --.go
update codigocbo set descricao = 'Músico regente' where codigo = '262615'; --.go
update codigocbo set descricao = 'Musicoterapeuta' where codigo = '223915'; --.go
update codigocbo set descricao = 'Musicoterapeuta' where codigo = '226305'; --.go
update codigocbo set descricao = 'Narrador em programas de rádio e televisao' where codigo = '261725'; --.go
update codigocbo set descricao = 'Neuropsicólogo' where codigo = '251545'; --.go
update codigocbo set descricao = 'Normalizador de metais e de compósitos' where codigo = '723110'; --.go
update codigocbo set descricao = 'Numerólogo' where codigo = '516710'; --.go
update codigocbo set descricao = 'Nutricionista' where codigo = '223710'; --.go
update codigocbo set descricao = 'Oceanógrafo' where codigo = '213440'; --.go
update codigocbo set descricao = 'Oficial da aeronáutica' where codigo = '010205'; --.go
update codigocbo set descricao = 'Oficial da marinha' where codigo = '010215'; --.go
update codigocbo set descricao = 'Oficial de inteligência' where codigo = '242905'; --.go
update codigocbo set descricao = 'Oficial de justiça' where codigo = '351425'; --.go
update codigocbo set descricao = 'Oficial de quarto de navegaçao da marinha mercante' where codigo = '215140'; --.go
update codigocbo set descricao = 'Oficial de registro de contratos marítimos' where codigo = '241305'; --.go
update codigocbo set descricao = 'Oficial do exército' where codigo = '010210'; --.go
update codigocbo set descricao = 'Oficial do registro civil de pessoas juridicas' where codigo = '241310'; --.go
update codigocbo set descricao = 'Oficial do registro civil de pessoas naturais' where codigo = '241315'; --.go
update codigocbo set descricao = 'Oficial do registro de distribuiçoes' where codigo = '241320'; --.go
update codigocbo set descricao = 'Oficial do registro de imóveis' where codigo = '241325'; --.go
update codigocbo set descricao = 'Oficial do registro de títulos e documentos' where codigo = '241330'; --.go
update codigocbo set descricao = 'Oficial general da aeronáutica' where codigo = '010105'; --.go
update codigocbo set descricao = 'Oficial general da marinha' where codigo = '010115'; --.go
update codigocbo set descricao = 'Oficial general do exército' where codigo = '010110'; --.go
update codigocbo set descricao = 'Oficial superior de máquinas da marinha mercante' where codigo = '215205'; --.go
update codigocbo set descricao = 'Oficial técnico de inteligência' where codigo = '242910'; --.go
update codigocbo set descricao = 'Oleiro (fabricaçao de telhas)' where codigo = '828105'; --.go
update codigocbo set descricao = 'Oleiro (fabricaçao de tijolos)' where codigo = '828110'; --.go
update codigocbo set descricao = 'Operador de abertura (fiaçao)' where codigo = '761205'; --.go
update codigocbo set descricao = 'Operador de acabamento de peças fundidas' where codigo = '722215'; --.go
update codigocbo set descricao = 'Operador de acabamento (indústria gráfica)' where codigo = '766315'; --.go
update codigocbo set descricao = 'Operador de aciaria (basculamento de convertedor)' where codigo = '821230'; --.go
update codigocbo set descricao = 'Operador de aciaria (dessulfuraçao de gusa)' where codigo = '821235'; --.go
update codigocbo set descricao = 'Operador de aciaria (recebimento de gusa)' where codigo = '821240'; --.go
update codigocbo set descricao = 'Operador de alambique de funcionamento contínuo (produtos químicos, exceto petróleo)' where codigo = '811415'; --.go
update codigocbo set descricao = 'Operador de aparelho de flotaçao' where codigo = '712110'; --.go
update codigocbo set descricao = 'Operador de aparelho de precipitaçao (minas de ouro ou prata)' where codigo = '712115'; --.go
update codigocbo set descricao = 'Operador de aparelho de reaçao e conversao (produtos químicos, exceto petróleo)' where codigo = '811420'; --.go
update codigocbo set descricao = 'Operador de área de corrida' where codigo = '821245'; --.go
update codigocbo set descricao = 'Operador de atendimento aeroviário' where codigo = '342535'; --.go
update codigocbo set descricao = 'Operador de atomizador' where codigo = '823135'; --.go
update codigocbo set descricao = 'Operador de áudio de continuidade (rádio)' where codigo = '373105'; --.go
update codigocbo set descricao = 'Operador de banho metálico de vidro por flutuaçao' where codigo = '823235'; --.go
update codigocbo set descricao = 'Operador de bate-estacas' where codigo = '715105'; --.go
update codigocbo set descricao = 'Operador de bateria de gás de hulha' where codigo = '862115'; --.go
update codigocbo set descricao = 'Operador de betoneira' where codigo = '715405'; --.go
update codigocbo set descricao = 'Operador de binadeira' where codigo = '761210'; --.go
update codigocbo set descricao = 'Operador de bobinadeira' where codigo = '761215'; --.go
update codigocbo set descricao = 'Operador de bobinadeira de tiras a quente, no acabamento de chapas e metais' where codigo = '821420'; --.go
update codigocbo set descricao = 'Operador de bomba de concreto' where codigo = '715410'; --.go
update codigocbo set descricao = 'Operador de branqueador de pasta para fabricaçao de papel' where codigo = '831110'; --.go
update codigocbo set descricao = 'Operador de britadeira (tratamentos químicos e afins)' where codigo = '811115'; --.go
update codigocbo set descricao = 'Operador de britador de coque' where codigo = '811605'; --.go
update codigocbo set descricao = 'Operador de britador de mandíbulas' where codigo = '712120'; --.go
update codigocbo set descricao = 'Operador de cabine de laminaçao (fio-máquina)' where codigo = '821425'; --.go
update codigocbo set descricao = 'Operador de caixa' where codigo = '421125'; --.go
update codigocbo set descricao = 'Operador de calandra (química, petroquímica e afins)' where codigo = '813110'; --.go
update codigocbo set descricao = 'Operador de calandras (tecidos)' where codigo = '761415'; --.go
update codigocbo set descricao = 'Operador de calcinaçao (tratamento químico e afins)' where codigo = '811205'; --.go
update codigocbo set descricao = 'Operador de caldeira' where codigo = '862120'; --.go
update codigocbo set descricao = 'Operador de câmaras frias' where codigo = '841456'; --.go
update codigocbo set descricao = 'Operador de câmera de televisao' where codigo = '372115'; --.go
update codigocbo set descricao = 'Operador de caminhao (minas e pedreiras)' where codigo = '711205'; --.go
update codigocbo set descricao = 'Operador de cardas' where codigo = '761220'; --.go
update codigocbo set descricao = 'Operador de carregadeira' where codigo = '711210'; --.go
update codigocbo set descricao = 'Operador de carro de apagamento e coque' where codigo = '811610'; --.go
update codigocbo set descricao = 'Operador de ceifadeira na conservaçao de vias permanentes' where codigo = '992215'; --.go
update codigocbo set descricao = 'Operador de central de concreto' where codigo = '715415'; --.go
update codigocbo set descricao = 'Operador de central de rádio' where codigo = '373110'; --.go
update codigocbo set descricao = 'Operador de central hidrelétrica' where codigo = '861105'; --.go
update codigocbo set descricao = 'Operador de central termoelétrica' where codigo = '861115'; --.go
update codigocbo set descricao = 'Operador de centrifugadora (tratamentos químicos e afins)' where codigo = '811305'; --.go
update codigocbo set descricao = 'Operador de centro de controle' where codigo = '821105'; --.go
update codigocbo set descricao = 'Operador de centro de controle (ferrovia e metrô)' where codigo = '342410'; --.go
update codigocbo set descricao = 'Operador de centro de usinagem com comando numérico' where codigo = '721405'; --.go
update codigocbo set descricao = 'Operador de centro de usinagem de madeira (cnc)' where codigo = '773505'; --.go
update codigocbo set descricao = 'Operador de chamuscadeira de tecidos' where codigo = '761420'; --.go
update codigocbo set descricao = 'Operador de cobrança bancária' where codigo = '413230'; --.go
update codigocbo set descricao = 'Operador de colhedor florestal' where codigo = '642005'; --.go
update codigocbo set descricao = 'Operador de colheitadeira' where codigo = '641005'; --.go
update codigocbo set descricao = 'Operador de compactadora de solos' where codigo = '715110'; --.go
update codigocbo set descricao = 'Operador de compressor de ar' where codigo = '862130'; --.go
update codigocbo set descricao = 'Operador de computador (inclusive microcomputador)' where codigo = '317205'; --.go
update codigocbo set descricao = 'Operador de concentraçao' where codigo = '811120'; --.go
update codigocbo set descricao = 'Operador de conicaleira' where codigo = '761225'; --.go
update codigocbo set descricao = 'Operador de cortadeira de papel' where codigo = '832110'; --.go
update codigocbo set descricao = 'Operador de cristalizaçao na refinaçao de açucar' where codigo = '841305'; --.go
update codigocbo set descricao = 'Operador de desempenadeira na usinagem convencional de madeira' where codigo = '773305'; --.go
update codigocbo set descricao = 'Operador de desgaseificaçao' where codigo = '821250'; --.go
update codigocbo set descricao = 'Operador de destilaçao e subprodutos de coque' where codigo = '811615'; --.go
update codigocbo set descricao = 'Operador de digestor de pasta para fabricaçao de papel' where codigo = '831115'; --.go
update codigocbo set descricao = 'Operador de docagem' where codigo = '782210'; --.go
update codigocbo set descricao = 'Operador de draga' where codigo = '782105'; --.go
update codigocbo set descricao = 'Operador de empilhadeira' where codigo = '782220'; --.go
update codigocbo set descricao = 'Operador de enfornamento e desenfornamento de coque' where codigo = '811620'; --.go
update codigocbo set descricao = 'Operador de engomadeira de urdume' where codigo = '761348'; --.go
update codigocbo set descricao = 'Operador de entalhadeira (usinagem de madeira)' where codigo = '773310'; --.go
update codigocbo set descricao = 'Operador de equipamento de destilaçao de álcool' where codigo = '811425'; --.go
update codigocbo set descricao = 'Operador de equipamento de secagem de pintura' where codigo = '723305'; --.go
update codigocbo set descricao = 'Operador de equipamento para resfriamento' where codigo = '723115'; --.go
update codigocbo set descricao = 'Operador de equipamentos de preparaçao de areia' where codigo = '722325'; --.go
update codigocbo set descricao = 'Operador de equipamentos de refinaçao de açúcar (processo contínuo)' where codigo = '841310'; --.go
update codigocbo set descricao = 'Operador de escavadeira' where codigo = '715115'; --.go
update codigocbo set descricao = 'Operador de escória e sucata' where codigo = '821430'; --.go
update codigocbo set descricao = 'Operador de esmaltadeira' where codigo = '752420'; --.go
update codigocbo set descricao = 'Operador de espelhamento' where codigo = '752425'; --.go
update codigocbo set descricao = 'Operador de espessador' where codigo = '712125'; --.go
update codigocbo set descricao = 'Operador de espuladeira' where codigo = '761351'; --.go
update codigocbo set descricao = 'Operador de estaçao de bombeamento' where codigo = '862140'; --.go
update codigocbo set descricao = 'Operador de estaçao de captaçao, tratamento e distribuiçao de água' where codigo = '862205'; --.go
update codigocbo set descricao = 'Operador de estaçao de tratamento de água e efluentes' where codigo = '862305'; --.go
update codigocbo set descricao = 'Operador de evaporador na destilaçao' where codigo = '811430'; --.go
update codigocbo set descricao = 'Operador de exaustor (coqueria)' where codigo = '811625'; --.go
update codigocbo set descricao = 'Operador de exploraçao de petróleo' where codigo = '811310'; --.go
update codigocbo set descricao = 'Operador de externa (rádio)' where codigo = '373115'; --.go
update codigocbo set descricao = 'Operador de extraçao de café solúvel' where codigo = '841620'; --.go
update codigocbo set descricao = 'Operador de extrusora (química, petroquímica e afins)' where codigo = '813115'; --.go
update codigocbo set descricao = 'Operador de filatório' where codigo = '761230'; --.go
update codigocbo set descricao = 'Operador de filtro de secagem (mineraçao)' where codigo = '811315'; --.go
update codigocbo set descricao = 'Operador de filtro de tambor rotativo (tratamentos químicos e afins)' where codigo = '811320'; --.go
update codigocbo set descricao = 'Operador de filtro-esteira (mineraçao)' where codigo = '811325'; --.go
update codigocbo set descricao = 'Operador de filtro-prensa (tratamentos químicos e afins)' where codigo = '811330'; --.go
update codigocbo set descricao = 'Operador de filtros de parafina (tratamentos químicos e afins)' where codigo = '811335'; --.go
update codigocbo set descricao = 'Operador de forno de incineraçao no tratamento de água, efluentes e resíduos industriais' where codigo = '862310'; --.go
update codigocbo set descricao = 'Operador de forno de tratamento térmico de metais' where codigo = '723120'; --.go
update codigocbo set descricao = 'Operador de forno (fabricaçao de paes, biscoitos e similares)' where codigo = '841805'; --.go
update codigocbo set descricao = 'Operador de forno (serviços funerários)' where codigo = '516605'; --.go
update codigocbo set descricao = 'Operador de fresadora com comando numérico' where codigo = '721410'; --.go
update codigocbo set descricao = 'Operador de fresadora (usinagem de madeira)' where codigo = '773315'; --.go
update codigocbo set descricao = 'Operador de gravaçao de rádio' where codigo = '373120'; --.go
update codigocbo set descricao = 'Operador de guilhotina (corte de papel)' where codigo = '766320'; --.go
update codigocbo set descricao = 'Operador de guindaste (fixo)' where codigo = '782110'; --.go
update codigocbo set descricao = 'Operador de guindaste móvel' where codigo = '782115'; --.go
update codigocbo set descricao = 'Operador de impermeabilizador de tecidos' where codigo = '761425'; --.go
update codigocbo set descricao = 'Operador de incubadora' where codigo = '623315'; --.go
update codigocbo set descricao = 'Operador de inspeçao de qualidade' where codigo = '391215'; --.go
update codigocbo set descricao = 'Operador de instalaçao de ar-condicionado' where codigo = '862515'; --.go
update codigocbo set descricao = 'Operador de instalaçao de extraçao, processamento, envasamento e distribuiçao de gases' where codigo = '862405'; --.go
update codigocbo set descricao = 'Operador de instalaçao de refrigeraçao' where codigo = '862505'; --.go
update codigocbo set descricao = 'Operador de jato abrasivo' where codigo = '821435'; --.go
update codigocbo set descricao = 'Operador de jig (minas)' where codigo = '712130'; --.go
update codigocbo set descricao = 'Operador de laços de cabos de aço' where codigo = '724605'; --.go
update codigocbo set descricao = 'Operador de laminadeira e reunideira' where codigo = '761235'; --.go
update codigocbo set descricao = 'Operador de laminador' where codigo = '821305'; --.go
update codigocbo set descricao = 'Operador de laminador de barras a frio' where codigo = '821310'; --.go
update codigocbo set descricao = 'Operador de laminador de barras a quente' where codigo = '821315'; --.go
update codigocbo set descricao = 'Operador de laminador de metais nao-ferrosos' where codigo = '821320'; --.go
update codigocbo set descricao = 'Operador de laminador de tubos' where codigo = '821325'; --.go
update codigocbo set descricao = 'Operador de lavagem e depuraçao de pasta para fabricaçao de papel' where codigo = '831120'; --.go
update codigocbo set descricao = 'Operador de linha de montagem (aparelhos elétricos)' where codigo = '731175'; --.go
update codigocbo set descricao = 'Operador de linha de montagem (aparelhos eletrônicos)' where codigo = '731180'; --.go
update codigocbo set descricao = 'Operador de lixadeira (usinagem de madeira)' where codigo = '773320'; --.go
update codigocbo set descricao = 'Operador de maçaroqueira' where codigo = '761240'; --.go
update codigocbo set descricao = 'Operador de mandriladora com comando numérico' where codigo = '721415'; --.go
update codigocbo set descricao = 'Operador de máquina bordatriz' where codigo = '773405'; --.go
update codigocbo set descricao = 'Operador de máquina centrifugadora de fundiçao' where codigo = '722220'; --.go
update codigocbo set descricao = 'Operador de máquina copiadora (exceto operador de gráfica rápida)' where codigo = '415130'; --.go
update codigocbo set descricao = 'Operador de máquina cortadora (minas e pedreiras)' where codigo = '711215'; --.go
update codigocbo set descricao = 'Operador de máquina de abrir valas' where codigo = '715120'; --.go
update codigocbo set descricao = 'Operador de máquina de cilindrar chapas' where codigo = '724505'; --.go
update codigocbo set descricao = 'Operador de máquina de cordoalha' where codigo = '761354'; --.go
update codigocbo set descricao = 'Operador de máquina de cortar e dobrar papelao' where codigo = '833120'; --.go
update codigocbo set descricao = 'Operador de máquina de cortina d ''água (produçao de móveis)' where codigo = '773410'; --.go
update codigocbo set descricao = 'Operador de máquina de costura de acabamento' where codigo = '763320'; --.go
update codigocbo set descricao = 'Operador de máquina de dobrar chapas' where codigo = '724510'; --.go
update codigocbo set descricao = 'Operador de máquina de eletroerosao' where codigo = '721205'; --.go
update codigocbo set descricao = 'Operador de máquina de envasar líquidos' where codigo = '784120'; --.go
update codigocbo set descricao = 'Operador de máquina de etiquetar' where codigo = '784115'; --.go
update codigocbo set descricao = 'Operador de máquina de extraçao contínua (minas de carvao)' where codigo = '711220'; --.go
update codigocbo set descricao = 'Operador de máquina de fabricaçao de cosméticos' where codigo = '811815'; --.go
update codigocbo set descricao = 'Operador de máquina de fabricaçao de produtos de higiene e limpeza (sabao, sabonete, detergente, absorvente, fraldas cotonetes e outros)' where codigo = '811820'; --.go
update codigocbo set descricao = 'Operador de máquina de fabricar charutos e cigarrilhas' where codigo = '842210'; --.go
update codigocbo set descricao = 'Operador de máquina de fabricar papel e papelao' where codigo = '832125'; --.go
update codigocbo set descricao = 'Operador de máquina de fabricar papel (fase seca)' where codigo = '832120'; --.go
update codigocbo set descricao = 'Operador de máquina de fabricar papel  (fase úmida)' where codigo = '832115'; --.go
update codigocbo set descricao = 'Operador de máquina de fundir sob pressao' where codigo = '722225'; --.go
update codigocbo set descricao = 'Operador de máquina de lavar fios e tecidos' where codigo = '761430'; --.go
update codigocbo set descricao = 'Operador de máquina de moldar automatizada' where codigo = '722330'; --.go
update codigocbo set descricao = 'Operador de máquina de preparaçao de matéria prima para produçao de cigarros' where codigo = '842135'; --.go
update codigocbo set descricao = 'Operador de máquina de produtos farmacêuticos' where codigo = '811805'; --.go
update codigocbo set descricao = 'Operador de máquina de secar celulose' where codigo = '831125'; --.go
update codigocbo set descricao = 'Operador de máquina de sinterizar' where codigo = '821110'; --.go
update codigocbo set descricao = 'Operador de máquina de soprar vidro' where codigo = '823240'; --.go
update codigocbo set descricao = 'Operador de máquina de usinagem de madeira (produçao em série)' where codigo = '773415'; --.go
update codigocbo set descricao = 'Operador de máquina de usinagem madeira, em geral' where codigo = '773325'; --.go
update codigocbo set descricao = 'Operador de máquina eletroerosao, à fio, com comando numérico' where codigo = '721420'; --.go
update codigocbo set descricao = 'Operador de máquina extrusora de varetas e tubos de vidro' where codigo = '823245'; --.go
update codigocbo set descricao = 'Operador de máquina (fabricaçao de cigarros)' where codigo = '842125'; --.go
update codigocbo set descricao = 'Operador de máquina intercaladora e placas (compensados)' where codigo = '773205'; --.go
update codigocbo set descricao = 'Operador de máquina misturadeira (tratamentos químicos e afins)' where codigo = '811110'; --.go
update codigocbo set descricao = 'Operador de máquina perfuradora (minas e pedreiras)' where codigo = '711225'; --.go
update codigocbo set descricao = 'Operador de máquina perfuratriz' where codigo = '711230'; --.go
update codigocbo set descricao = 'Operador de máquina recobridora de arame' where codigo = '723230'; --.go
update codigocbo set descricao = 'Operador de máquina rodoferroviária' where codigo = '782120'; --.go
update codigocbo set descricao = 'Operador de máquinas de beneficiamento de produtos agrícolas' where codigo = '641010'; --.go
update codigocbo set descricao = 'Operador de máquinas de construçao civil e mineraçao' where codigo = '715125'; --.go
update codigocbo set descricao = 'Operador de máquinas de fabricaçao de chocolates e achocolatados' where codigo = '841815'; --.go
update codigocbo set descricao = 'Operador de máquinas de fabricaçao de doces, salgados e massas alimentícias' where codigo = '841810'; --.go
update codigocbo set descricao = 'Operador de máquinas de usinar madeira (cnc)' where codigo = '773510'; --.go
update codigocbo set descricao = 'Operador de máquinas do acabamento de couros e peles' where codigo = '762325'; --.go
update codigocbo set descricao = 'Operador de máquinas especiais em conservaçao de via permanente (trilhos)' where codigo = '991115'; --.go
update codigocbo set descricao = 'Operador de máquinas-ferramenta convencionais' where codigo = '721215'; --.go
update codigocbo set descricao = 'Operador de máquinas fixas, em geral' where codigo = '862150'; --.go
update codigocbo set descricao = 'Operador de máquinas florestais estáticas' where codigo = '642010'; --.go
update codigocbo set descricao = 'Operador de máquinas operatrizes' where codigo = '721210'; --.go
update codigocbo set descricao = 'Operador de martelete' where codigo = '717010'; --.go
update codigocbo set descricao = 'Operador de mensagens de telecomunicaçoes (correios)' where codigo = '412115'; --.go
update codigocbo set descricao = 'Operador de moenda na fabricaçao de açúcar' where codigo = '841315'; --.go
update codigocbo set descricao = 'Operador de molduradora (usinagem de madeira)' where codigo = '773330'; --.go
update codigocbo set descricao = 'Operador de monta-cargas (construçao civil)' where codigo = '782125'; --.go
update codigocbo set descricao = 'Operador de montagem de cilindros e mancais' where codigo = '821330'; --.go
update codigocbo set descricao = 'Operador de motoniveladora' where codigo = '715130'; --.go
update codigocbo set descricao = 'Operador de motoniveladora (extraçao de minerais sólidos)' where codigo = '711235'; --.go
update codigocbo set descricao = 'Operador de motosserra' where codigo = '632120'; --.go
update codigocbo set descricao = 'Operador de negócios' where codigo = '253225'; --.go
update codigocbo set descricao = 'Operador de open-end' where codigo = '761245'; --.go
update codigocbo set descricao = 'Operador de pá carregadeira' where codigo = '715135'; --.go
update codigocbo set descricao = 'Operador de painel de controle' where codigo = '811630'; --.go
update codigocbo set descricao = 'Operador de painel de controle (refinaçao de petróleo)' where codigo = '811505'; --.go
update codigocbo set descricao = 'Operador de passador (fiaçao)' where codigo = '761250'; --.go
update codigocbo set descricao = 'Operador de pavimentadora (asfalto, concreto e materiais similares)' where codigo = '715140'; --.go
update codigocbo set descricao = 'Operador de peneiras hidráulicas' where codigo = '712135'; --.go
update codigocbo set descricao = 'Operador de penteadeira' where codigo = '761255'; --.go
update codigocbo set descricao = 'Operador de plaina desengrossadeira' where codigo = '773335'; --.go
update codigocbo set descricao = 'Operador de ponte rolante' where codigo = '782130'; --.go
update codigocbo set descricao = 'Operador de pórtico rolante' where codigo = '782135'; --.go
update codigocbo set descricao = 'Operador de prensa de alta freqüência na usinagem de madeira' where codigo = '773420'; --.go
update codigocbo set descricao = 'Operador de prensa de embutir papelao' where codigo = '833125'; --.go
update codigocbo set descricao = 'Operador de prensa de enfardamento' where codigo = '784125'; --.go
update codigocbo set descricao = 'Operador de prensa de moldar vidro' where codigo = '823250'; --.go
update codigocbo set descricao = 'Operador de preparaçao de graos vegetais (óleos e gorduras)' where codigo = '841460'; --.go
update codigocbo set descricao = 'Operador de preservaçao e controle térmico' where codigo = '811635'; --.go
update codigocbo set descricao = 'Operador de processo de moagem' where codigo = '841115'; --.go
update codigocbo set descricao = 'Operador de processo de tratamento de imagem' where codigo = '766150'; --.go
update codigocbo set descricao = 'Operador de processo (química, petroquímica e afins)' where codigo = '813120'; --.go
update codigocbo set descricao = 'Operador de processos químicos e petroquímicos' where codigo = '811005'; --.go
update codigocbo set descricao = 'Operador de produçao (química, petroquímica e afins)' where codigo = '813125'; --.go
update codigocbo set descricao = 'Operador de projetor cinematográfico' where codigo = '374305'; --.go
update codigocbo set descricao = 'Operador de quadro de distribuiçao de energia elétrica' where codigo = '861110'; --.go
update codigocbo set descricao = 'Operador de rádio-chamada' where codigo = '422220'; --.go
update codigocbo set descricao = 'Operador de rameuse' where codigo = '761435'; --.go
update codigocbo set descricao = 'Operador de reator de coque de petróleo' where codigo = '811640'; --.go
update codigocbo set descricao = 'Operador de reator nuclear' where codigo = '861120'; --.go
update codigocbo set descricao = 'Operador de rebobinadeira na fabricaçao de papel e papelao' where codigo = '832135'; --.go
update codigocbo set descricao = 'Operador de rede de teleprocessamento' where codigo = '372205'; --.go
update codigocbo set descricao = 'Operador de refrigeraçao com amônia' where codigo = '862510'; --.go
update codigocbo set descricao = 'Operador de refrigeraçao (coqueria)' where codigo = '811645'; --.go
update codigocbo set descricao = 'Operador de retificadora com comando numérico' where codigo = '721425'; --.go
update codigocbo set descricao = 'Operador de retorcedeira' where codigo = '761260'; --.go
update codigocbo set descricao = 'Operador de sala de controle de instalaçoes químicas, petroquímicas e afins' where codigo = '811010'; --.go
update codigocbo set descricao = 'Operador de salina (sal marinho)' where codigo = '711410'; --.go
update codigocbo set descricao = 'Operador de schutthecar' where codigo = '711240'; --.go
update codigocbo set descricao = 'Operador de serras no desdobramento de madeira' where codigo = '773110'; --.go
update codigocbo set descricao = 'Operador de serras (usinagem de madeira)' where codigo = '773340'; --.go
update codigocbo set descricao = 'Operador de sistema de reversao (coqueria)' where codigo = '811650'; --.go
update codigocbo set descricao = 'Operador de sistemas de prova (analógico e digital)' where codigo = '766145'; --.go
update codigocbo set descricao = 'Operador de sonda de percussao' where codigo = '711305'; --.go
update codigocbo set descricao = 'Operador de sonda rotativa' where codigo = '711310'; --.go
update codigocbo set descricao = 'Operador de subestaçao' where codigo = '861205'; --.go
update codigocbo set descricao = 'Operador de talha elétrica' where codigo = '782140'; --.go
update codigocbo set descricao = 'Operador de teleférico (passageiros)' where codigo = '782630'; --.go
update codigocbo set descricao = 'Operador de telemarketing ativo' where codigo = '422305'; --.go
update codigocbo set descricao = 'Operador de telemarketing ativo e receptivo' where codigo = '422310'; --.go
update codigocbo set descricao = 'Operador de telemarketing receptivo' where codigo = '422315'; --.go
update codigocbo set descricao = 'Operador de telemarketing técnico' where codigo = '422320'; --.go
update codigocbo set descricao = 'Operador de tesoura mecânica e máquina de corte, no acabamento de chapas e metais' where codigo = '821440'; --.go
update codigocbo set descricao = 'Operador de time de montagem' where codigo = '725510'; --.go
update codigocbo set descricao = 'Operador de torno automático (usinagem de madeira)' where codigo = '773345'; --.go
update codigocbo set descricao = 'Operador de torno com comando numérico' where codigo = '721430'; --.go
update codigocbo set descricao = 'Operador de transferência e estocagem - na refinaçao do petróleo' where codigo = '811510'; --.go
update codigocbo set descricao = 'Operador de transmissor de rádio' where codigo = '373125'; --.go
update codigocbo set descricao = 'Operador de transporte multimodal' where codigo = '342110'; --.go
update codigocbo set descricao = 'Operador de tratamento de calda na refinaçao de açúcar' where codigo = '841320'; --.go
update codigocbo set descricao = 'Operador de tratamento químico de materiais radioativos' where codigo = '811215'; --.go
update codigocbo set descricao = 'Operador de trator de lâmina' where codigo = '715145'; --.go
update codigocbo set descricao = 'Operador de trator florestal' where codigo = '642015'; --.go
update codigocbo set descricao = 'Operador de trator (minas e pedreiras)' where codigo = '711245'; --.go
update codigocbo set descricao = 'Operador de trem de metrô' where codigo = '782605'; --.go
update codigocbo set descricao = 'Operador de triagem e transbordo' where codigo = '415210'; --.go
update codigocbo set descricao = 'Operador de tupia (usinagem de madeira)' where codigo = '773350'; --.go
update codigocbo set descricao = 'Operador de turismo' where codigo = '354810'; --.go
update codigocbo set descricao = 'Operador de urdideira' where codigo = '761357'; --.go
update codigocbo set descricao = 'Operador de usinagem convencional por abrasao' where codigo = '721220'; --.go
update codigocbo set descricao = 'Operador de utilidade (produçao e distribuiçao de vapor, gás, óleo, combustível, energia, oxigênio)' where codigo = '862155'; --.go
update codigocbo set descricao = 'Operador de vazamento (lingotamento)' where codigo = '722230'; --.go
update codigocbo set descricao = 'Operador de veículos subaquáticos controlados remotamente' where codigo = '781305'; --.go
update codigocbo set descricao = 'Operador de zincagem (processo eletrolítico)' where codigo = '723235'; --.go
update codigocbo set descricao = 'Operador eletromecânico' where codigo = '954125'; --.go
update codigocbo set descricao = 'Operador-mantenedor de projetor cinematográfico' where codigo = '374310'; --.go
update codigocbo set descricao = 'Operador polivalente da indústria têxtil' where codigo = '761005'; --.go
update codigocbo set descricao = 'Organizador de evento' where codigo = '354820'; --.go
update codigocbo set descricao = 'Orientador educacional' where codigo = '239410'; --.go
update codigocbo set descricao = 'Ortoptista' where codigo = '223910'; --.go
update codigocbo set descricao = 'Osteopata' where codigo = '226110'; --.go
update codigocbo set descricao = 'Ourives' where codigo = '751125'; --.go
update codigocbo set descricao = 'Ouvidor' where codigo = '142340'; --.go
update codigocbo set descricao = 'Ouvidor (ombudsman) do meio de comunicaçao' where codigo = '352415'; --.go
update codigocbo set descricao = 'Oxicortador a mao e a  máquina' where codigo = '724310'; --.go
update codigocbo set descricao = 'Oxidador' where codigo = '723240'; --.go
update codigocbo set descricao = 'Padeiro' where codigo = '848305'; --.go
update codigocbo set descricao = 'Paginador' where codigo = '768620'; --.go
update codigocbo set descricao = 'Palecionador de couros e peles' where codigo = '762335'; --.go
update codigocbo set descricao = 'Paleontólogo' where codigo = '213430'; --.go
update codigocbo set descricao = 'Palhaço' where codigo = '376245'; --.go
update codigocbo set descricao = 'Papiloscopista policial' where codigo = '351815'; --.go
update codigocbo set descricao = 'Paranormal' where codigo = '516810'; --.go
update codigocbo set descricao = 'Parteira leiga' where codigo = '515115'; --.go
update codigocbo set descricao = 'Passadeira de peças confeccionadas' where codigo = '763325'; --.go
update codigocbo set descricao = 'Passador de roupas, à mao' where codigo = '516415'; --.go
update codigocbo set descricao = 'Passador de roupas em geral' where codigo = '516325'; --.go
update codigocbo set descricao = 'Passamaneiro a  máquina' where codigo = '761360'; --.go
update codigocbo set descricao = 'Pasteurizador' where codigo = '848205'; --.go
update codigocbo set descricao = 'Pastilheiro' where codigo = '716515'; --.go
update codigocbo set descricao = 'Patrao de pesca de alto-mar' where codigo = '341220'; --.go
update codigocbo set descricao = 'Patrao de pesca na navegaçao interior' where codigo = '341225'; --.go
update codigocbo set descricao = 'Pedagogo' where codigo = '239415'; --.go
update codigocbo set descricao = 'Pedicure' where codigo = '516140'; --.go
update codigocbo set descricao = 'Pedreiro' where codigo = '715210'; --.go
update codigocbo set descricao = 'Pedreiro (chaminés industriais)' where codigo = '715215'; --.go
update codigocbo set descricao = 'Pedreiro de conservaçao de vias permanentes (exceto trilhos)' where codigo = '992220'; --.go
update codigocbo set descricao = 'Pedreiro de edificaçoes' where codigo = '715230'; --.go
update codigocbo set descricao = 'Pedreiro (material refratário)' where codigo = '715220'; --.go
update codigocbo set descricao = 'Pedreiro (mineraçao)' where codigo = '715225'; --.go
update codigocbo set descricao = 'Perfumista' where codigo = '325015'; --.go
update codigocbo set descricao = 'Perito contábil' where codigo = '252215'; --.go
update codigocbo set descricao = 'Perito criminal' where codigo = '204105'; --.go
update codigocbo set descricao = 'Pescador artesanal de água doce' where codigo = '631105'; --.go
update codigocbo set descricao = 'Pescador artesanal de lagostas' where codigo = '631015'; --.go
update codigocbo set descricao = 'Pescador artesanal de peixes e camaroes' where codigo = '631020'; --.go
update codigocbo set descricao = 'Pescador industrial' where codigo = '631205'; --.go
update codigocbo set descricao = 'Pescador profissional' where codigo = '631210'; --.go
update codigocbo set descricao = 'Pesquisador de clínica médica' where codigo = '203305'; --.go
update codigocbo set descricao = 'Pesquisador de engenharia civil' where codigo = '203205'; --.go
update codigocbo set descricao = 'Pesquisador de engenharia elétrica e eletrônica' where codigo = '203215'; --.go
update codigocbo set descricao = 'Pesquisador de engenharia e tecnologia (outras áreas da engenharia)' where codigo = '203210'; --.go
update codigocbo set descricao = 'Pesquisador de engenharia mecânica' where codigo = '203220'; --.go
update codigocbo set descricao = 'Pesquisador de engenharia metalúrgica, de minas e de materiais' where codigo = '203225'; --.go
update codigocbo set descricao = 'Pesquisador de engenharia química' where codigo = '203230'; --.go
update codigocbo set descricao = 'Pesquisador de medicina básica' where codigo = '203310'; --.go
update codigocbo set descricao = 'Pesquisador em biologia ambiental' where codigo = '203005'; --.go
update codigocbo set descricao = 'Pesquisador em biologia animal' where codigo = '203010'; --.go
update codigocbo set descricao = 'Pesquisador em biologia de microorganismos e parasitas' where codigo = '203015'; --.go
update codigocbo set descricao = 'Pesquisador em biologia humana' where codigo = '203020'; --.go
update codigocbo set descricao = 'Pesquisador em biologia vegetal' where codigo = '203025'; --.go
update codigocbo set descricao = 'Pesquisador em ciências agronômicas' where codigo = '203405'; --.go
update codigocbo set descricao = 'Pesquisador em ciências da computaçao e informática' where codigo = '203105'; --.go
update codigocbo set descricao = 'Pesquisador em ciências da educaçao' where codigo = '203515'; --.go
update codigocbo set descricao = 'Pesquisador em ciências da pesca e aqüicultura' where codigo = '203410'; --.go
update codigocbo set descricao = 'Pesquisador em ciências da terra e meio ambiente' where codigo = '203110'; --.go
update codigocbo set descricao = 'Pesquisador em ciências da zootecnia' where codigo = '203415'; --.go
update codigocbo set descricao = 'Pesquisador em ciências florestais' where codigo = '203420'; --.go
update codigocbo set descricao = 'Pesquisador em ciências sociais e humanas' where codigo = '203505'; --.go
update codigocbo set descricao = 'Pesquisador em economia' where codigo = '203510'; --.go
update codigocbo set descricao = 'Pesquisador em física' where codigo = '203115'; --.go
update codigocbo set descricao = 'Pesquisador em história' where codigo = '203520'; --.go
update codigocbo set descricao = 'Pesquisador em matemática' where codigo = '203120'; --.go
update codigocbo set descricao = 'Pesquisador em medicina veterinária' where codigo = '203315'; --.go
update codigocbo set descricao = 'Pesquisador em metrologia' where codigo = '201205'; --.go
update codigocbo set descricao = 'Pesquisador em psicologia' where codigo = '203525'; --.go
update codigocbo set descricao = 'Pesquisador em química' where codigo = '203125'; --.go
update codigocbo set descricao = 'Pesquisador em saúde coletiva' where codigo = '203320'; --.go
update codigocbo set descricao = 'Petrógrafo' where codigo = '213435'; --.go
update codigocbo set descricao = 'Picotador de cartoes jacquard' where codigo = '761366'; --.go
update codigocbo set descricao = 'Piloto agrícola' where codigo = '341120'; --.go
update codigocbo set descricao = 'Piloto comercial de helicóptero (exceto linhas aéreas)' where codigo = '341110'; --.go
update codigocbo set descricao = 'Piloto comercial (exceto linhas aéreas)' where codigo = '341105'; --.go
update codigocbo set descricao = 'Piloto de aeronaves' where codigo = '215305'; --.go
update codigocbo set descricao = 'Piloto de competiçao automobilística' where codigo = '377135'; --.go
update codigocbo set descricao = 'Piloto de ensaios em vôo' where codigo = '215310'; --.go
update codigocbo set descricao = 'Piloto fluvial' where codigo = '341230'; --.go
update codigocbo set descricao = 'Pintor a pincel e rolo (exceto obras e estruturas metálicas)' where codigo = '723310'; --.go
update codigocbo set descricao = 'Pintor, a  pistola (exceto obras e estruturas metálicas)' where codigo = '723330'; --.go
update codigocbo set descricao = 'Pintor de cerâmica, a  pincel' where codigo = '752430'; --.go
update codigocbo set descricao = 'Pintor de estruturas metálicas' where codigo = '723315'; --.go
update codigocbo set descricao = 'Pintor de letreiros' where codigo = '768625'; --.go
update codigocbo set descricao = 'Pintor de obras' where codigo = '716610'; --.go
update codigocbo set descricao = 'Pintor de veículos (fabricaçao)' where codigo = '723320'; --.go
update codigocbo set descricao = 'Pintor de veículos (reparaçao)' where codigo = '991315'; --.go
update codigocbo set descricao = 'Pintor por imersao' where codigo = '723325'; --.go
update codigocbo set descricao = 'Pipoqueiro ambulante' where codigo = '524310'; --.go
update codigocbo set descricao = 'Pirotécnico' where codigo = '812105'; --.go
update codigocbo set descricao = 'Pizzaiolo' where codigo = '513610'; --.go
update codigocbo set descricao = 'Planejista' where codigo = '391120'; --.go
update codigocbo set descricao = 'Plataformista (petróleo)' where codigo = '711325'; --.go
update codigocbo set descricao = 'Poceiro (edificaçoes)' where codigo = '717015'; --.go
update codigocbo set descricao = 'Podólogo' where codigo = '322110'; --.go
update codigocbo set descricao = 'Poeta' where codigo = '261525'; --.go
update codigocbo set descricao = 'Policial rodoviário federal' where codigo = '517210'; --.go
update codigocbo set descricao = 'Polidor de metais' where codigo = '721325'; --.go
update codigocbo set descricao = 'Polidor de pedras' where codigo = '712220'; --.go
update codigocbo set descricao = 'Porteiro de edifícios' where codigo = '517410'; --.go
update codigocbo set descricao = 'Porteiro de locais de diversao' where codigo = '517415'; --.go
update codigocbo set descricao = 'Porteiro (hotel)' where codigo = '517405'; --.go
update codigocbo set descricao = 'Praça da aeronáutica' where codigo = '010305'; --.go
update codigocbo set descricao = 'Praça da marinha' where codigo = '010315'; --.go
update codigocbo set descricao = 'Praça do exército' where codigo = '010310'; --.go
update codigocbo set descricao = 'Prático de portos da marinha mercante' where codigo = '215145'; --.go
update codigocbo set descricao = 'Prefeito' where codigo = '111250'; --.go
update codigocbo set descricao = 'Prensador de couros e peles' where codigo = '762330'; --.go
update codigocbo set descricao = 'Prensador de frutas (exceto oleaginosas)' where codigo = '841464'; --.go
update codigocbo set descricao = 'Prensista de aglomerados' where codigo = '773210'; --.go
update codigocbo set descricao = 'Prensista de compensados' where codigo = '773215'; --.go
update codigocbo set descricao = 'Prensista (operador de prensa)' where codigo = '724515'; --.go
update codigocbo set descricao = 'Preparador de aditivos' where codigo = '823130'; --.go
update codigocbo set descricao = 'Preparador de aglomerantes' where codigo = '773220'; --.go
update codigocbo set descricao = 'Preparador de atleta' where codigo = '224115'; --.go
update codigocbo set descricao = 'Preparador de barbotina' where codigo = '823120'; --.go
update codigocbo set descricao = 'Preparador de calçados' where codigo = '764115'; --.go
update codigocbo set descricao = 'Preparador de couros curtidos' where codigo = '762340'; --.go
update codigocbo set descricao = 'Preparador de esmaltes (cerâmica)' where codigo = '823125'; --.go
update codigocbo set descricao = 'Preparador de estruturas metálicas' where codigo = '724220'; --.go
update codigocbo set descricao = 'Preparador de fumo na fabricaçao de charutos' where codigo = '842205'; --.go
update codigocbo set descricao = 'Preparador de máquinas-ferramenta' where codigo = '721225'; --.go
update codigocbo set descricao = 'Preparador de massa de argila' where codigo = '823115'; --.go
update codigocbo set descricao = 'Preparador de massa (fabricaçao de abrasivos)' where codigo = '823105'; --.go
update codigocbo set descricao = 'Preparador de massa (fabricaçao de vidro)' where codigo = '823110'; --.go
update codigocbo set descricao = 'Preparador de matrizes de corte e vinco' where codigo = '766325'; --.go
update codigocbo set descricao = 'Preparador de melado e essência de fumo' where codigo = '842105'; --.go
update codigocbo set descricao = 'Preparador de panelas (lingotamento)' where codigo = '722235'; --.go
update codigocbo set descricao = 'Preparador de raçoes' where codigo = '841468'; --.go
update codigocbo set descricao = 'Preparador de solas e palmilhas' where codigo = '764120'; --.go
update codigocbo set descricao = 'Preparador de sucata e aparas' where codigo = '821445'; --.go
update codigocbo set descricao = 'Preparador de tintas' where codigo = '311715'; --.go
update codigocbo set descricao = 'Preparador de tintas (fábrica de tecidos)' where codigo = '311720'; --.go
update codigocbo set descricao = 'Preparador físico' where codigo = '224120'; --.go
update codigocbo set descricao = 'Presidente da república' where codigo = '111205'; --.go
update codigocbo set descricao = 'Primeiro oficial de máquinas da marinha mercante' where codigo = '215210'; --.go
update codigocbo set descricao = 'Primeiro tenente de polícia militar' where codigo = '020305'; --.go
update codigocbo set descricao = 'Processador de fumo' where codigo = '842110'; --.go
update codigocbo set descricao = 'Procurador autárquico' where codigo = '241210'; --.go
update codigocbo set descricao = 'Procurador da assistência judiciária' where codigo = '242410'; --.go
update codigocbo set descricao = 'Procurador da fazenda nacional' where codigo = '241215'; --.go
update codigocbo set descricao = 'Procurador da república' where codigo = '242205'; --.go
update codigocbo set descricao = 'Procurador de justiça' where codigo = '242210'; --.go
update codigocbo set descricao = 'Procurador de justiça militar' where codigo = '242215'; --.go
update codigocbo set descricao = 'Procurador do estado' where codigo = '241220'; --.go
update codigocbo set descricao = 'Procurador do município' where codigo = '241225'; --.go
update codigocbo set descricao = 'Procurador do trabalho' where codigo = '242220'; --.go
update codigocbo set descricao = 'Procurador federal' where codigo = '241230'; --.go
update codigocbo set descricao = 'Procurador fundacional' where codigo = '241235'; --.go
update codigocbo set descricao = 'Procurador regional da república' where codigo = '242225'; --.go
update codigocbo set descricao = 'Procurador regional do trabalho' where codigo = '242230'; --.go
update codigocbo set descricao = 'Produtor agrícola polivalente' where codigo = '612005'; --.go
update codigocbo set descricao = 'Produtor agropecuário, em geral' where codigo = '611005'; --.go
update codigocbo set descricao = 'Produtor cinematográfico' where codigo = '262110'; --.go
update codigocbo set descricao = 'Produtor da cultura de amendoim' where codigo = '612705'; --.go
update codigocbo set descricao = 'Produtor da cultura de canola' where codigo = '612710'; --.go
update codigocbo set descricao = 'Produtor da cultura de coco-da-baia' where codigo = '612715'; --.go
update codigocbo set descricao = 'Produtor da cultura de dendê' where codigo = '612720'; --.go
update codigocbo set descricao = 'Produtor da cultura de girassol' where codigo = '612725'; --.go
update codigocbo set descricao = 'Produtor da cultura de linho' where codigo = '612730'; --.go
update codigocbo set descricao = 'Produtor da cultura de mamona' where codigo = '612735'; --.go
update codigocbo set descricao = 'Produtor da cultura de soja' where codigo = '612740'; --.go
update codigocbo set descricao = 'Produtor de algodao' where codigo = '612205'; --.go
update codigocbo set descricao = 'Produtor de arroz' where codigo = '612105'; --.go
update codigocbo set descricao = 'Produtor de árvores frutíferas' where codigo = '612505'; --.go
update codigocbo set descricao = 'Produtor de cacau' where codigo = '612610'; --.go
update codigocbo set descricao = 'Produtor de cana-de-açúcar' where codigo = '612110'; --.go
update codigocbo set descricao = 'Produtor de cereais de inverno' where codigo = '612115'; --.go
update codigocbo set descricao = 'Produtor de curauá' where codigo = '612210'; --.go
update codigocbo set descricao = 'Produtor de erva-mate' where codigo = '612615'; --.go
update codigocbo set descricao = 'Produtor de especiarias' where codigo = '612805'; --.go
update codigocbo set descricao = 'Produtor de espécies frutíferas rasteiras' where codigo = '612510'; --.go
update codigocbo set descricao = 'Produtor de espécies frutíferas trepadeiras' where codigo = '612515'; --.go
update codigocbo set descricao = 'Produtor de flores de corte' where codigo = '612405'; --.go
update codigocbo set descricao = 'Produtor de flores em vaso' where codigo = '612410'; --.go
update codigocbo set descricao = 'Produtor de forraçoes' where codigo = '612415'; --.go
update codigocbo set descricao = 'Produtor de fumo' where codigo = '612620'; --.go
update codigocbo set descricao = 'Produtor de gramíneas forrageiras' where codigo = '612120'; --.go
update codigocbo set descricao = 'Produtor de guaraná' where codigo = '612625'; --.go
update codigocbo set descricao = 'Produtor de juta' where codigo = '612215'; --.go
update codigocbo set descricao = 'Produtor de milho e sorgo' where codigo = '612125'; --.go
update codigocbo set descricao = 'Produtor de plantas aromáticas e medicinais' where codigo = '612810'; --.go
update codigocbo set descricao = 'Produtor de plantas ornamentais' where codigo = '612420'; --.go
update codigocbo set descricao = 'Produtor de rádio' where codigo = '262115'; --.go
update codigocbo set descricao = 'Produtor de rami' where codigo = '612220'; --.go
update codigocbo set descricao = 'Produtor de sisal' where codigo = '612225'; --.go
update codigocbo set descricao = 'Produtor de teatro' where codigo = '262120'; --.go
update codigocbo set descricao = 'Produtor de televisao' where codigo = '262125'; --.go
update codigocbo set descricao = 'Produtor de texto' where codigo = '261130'; --.go
update codigocbo set descricao = 'Produtor na olericultura de frutos e sementes' where codigo = '612320'; --.go
update codigocbo set descricao = 'Produtor na olericultura de legumes' where codigo = '612305'; --.go
update codigocbo set descricao = 'Produtor na olericultura de raízes, bulbos e tubérculos' where codigo = '612310'; --.go
update codigocbo set descricao = 'Produtor na olericultura de talos, folhas e flores' where codigo = '612315'; --.go
update codigocbo set descricao = 'Proeiro' where codigo = '631415'; --.go
update codigocbo set descricao = 'Professor da área de meio ambiente' where codigo = '233105'; --.go
update codigocbo set descricao = 'Professor da  educaçao de jovens e adultos do ensino fundamental (primeira a quarta série)' where codigo = '231205'; --.go
update codigocbo set descricao = 'Professor de administraçao' where codigo = '234810'; --.go
update codigocbo set descricao = 'Professor de alunos com deficiência auditiva e surdos' where codigo = '239205'; --.go
update codigocbo set descricao = 'Professor de alunos com deficiência física' where codigo = '239210'; --.go
update codigocbo set descricao = 'Professor de alunos com deficiência mental' where codigo = '239215'; --.go
update codigocbo set descricao = 'Professor de alunos com deficiência múltipla' where codigo = '239220'; --.go
update codigocbo set descricao = 'Professor de alunos com deficiência visual' where codigo = '239225'; --.go
update codigocbo set descricao = 'Professor de antropologia do ensino superior' where codigo = '234705'; --.go
update codigocbo set descricao = 'Professor de aprendizagem e treinamento comercial' where codigo = '233215'; --.go
update codigocbo set descricao = 'Professor de arquitetura' where codigo = '234305'; --.go
update codigocbo set descricao = 'Professor de arquivologia do ensino superior' where codigo = '234710'; --.go
update codigocbo set descricao = 'Professor de artes do espetáculo no ensino superior' where codigo = '234905'; --.go
update codigocbo set descricao = 'Professor de artes no ensino médio' where codigo = '232105'; --.go
update codigocbo set descricao = 'Professor de artes visuais no ensino superior (artes plásticas e multimídia)' where codigo = '234910'; --.go
update codigocbo set descricao = 'Professor de astronomia (ensino superior)' where codigo = '234215'; --.go
update codigocbo set descricao = 'Professor de biblioteconomia do ensio superior' where codigo = '234715'; --.go
update codigocbo set descricao = 'Professor de biologia no ensino médio' where codigo = '232110'; --.go
update codigocbo set descricao = 'Professor de ciência política do ensino superior' where codigo = '234720'; --.go
update codigocbo set descricao = 'Professor de ciências biológicas do ensino superior' where codigo = '234405'; --.go
update codigocbo set descricao = 'Professor de ciências exatas e naturais do ensino fundamental' where codigo = '231305'; --.go
update codigocbo set descricao = 'Professor de computaçao (no ensino superior)' where codigo = '234120'; --.go
update codigocbo set descricao = 'Professor de comunicaçao social do ensino superior' where codigo = '234725'; --.go
update codigocbo set descricao = 'Professor de contabilidade' where codigo = '234815'; --.go
update codigocbo set descricao = 'Professor de dança' where codigo = '262830'; --.go
update codigocbo set descricao = 'Professor de desenho técnico' where codigo = '233110'; --.go
update codigocbo set descricao = 'Professor de direito do ensino superior' where codigo = '234730'; --.go
update codigocbo set descricao = 'Professor de disciplinas pedagógicas no ensino médio' where codigo = '232115'; --.go
update codigocbo set descricao = 'Professor de economia' where codigo = '234805'; --.go
update codigocbo set descricao = 'Professor de educaçao artística do ensino fundamental' where codigo = '231310'; --.go
update codigocbo set descricao = 'Professor de educaçao física do ensino fundamental' where codigo = '231315'; --.go
update codigocbo set descricao = 'Professor de educaçao física no ensino médio' where codigo = '232120'; --.go
update codigocbo set descricao = 'Professor de educaçao física no ensino superior' where codigo = '234410'; --.go
update codigocbo set descricao = 'Professor de enfermagem do ensino superior' where codigo = '234415'; --.go
update codigocbo set descricao = 'Professor de engenharia' where codigo = '234310'; --.go
update codigocbo set descricao = 'Professor de ensino superior na área de didática' where codigo = '234505'; --.go
update codigocbo set descricao = 'Professor de ensino superior na área de orientaçao educacional' where codigo = '234510'; --.go
update codigocbo set descricao = 'Professor de ensino superior na área de pesquisa educacional' where codigo = '234515'; --.go
update codigocbo set descricao = 'Professor de ensino superior na área de prática de ensino' where codigo = '234520'; --.go
update codigocbo set descricao = 'Professor de estatística (no ensino superior)' where codigo = '234115'; --.go
update codigocbo set descricao = 'Professor de farmácia e bioquímica' where codigo = '234420'; --.go
update codigocbo set descricao = 'Professor de filologia e crítica textual' where codigo = '234676'; --.go
update codigocbo set descricao = 'Professor de filosofia do ensino superior' where codigo = '234735'; --.go
update codigocbo set descricao = 'Professor de filosofia no ensino médio' where codigo = '232125'; --.go
update codigocbo set descricao = 'Professor de física (ensino superior)' where codigo = '234205'; --.go
update codigocbo set descricao = 'Professor de física no ensino médio' where codigo = '232130'; --.go
update codigocbo set descricao = 'Professor de fisioterapia' where codigo = '234425'; --.go
update codigocbo set descricao = 'Professor de fonoaudiologia' where codigo = '234430'; --.go
update codigocbo set descricao = 'Professor de geofísica' where codigo = '234315'; --.go
update codigocbo set descricao = 'Professor de geografia do ensino fundamental' where codigo = '231320'; --.go
update codigocbo set descricao = 'Professor de geografia do ensino superior' where codigo = '234740'; --.go
update codigocbo set descricao = 'Professor de geografia no ensino médio' where codigo = '232135'; --.go
update codigocbo set descricao = 'Professor de geologia' where codigo = '234320'; --.go
update codigocbo set descricao = 'Professor de história do ensino fundamental' where codigo = '231325'; --.go
update codigocbo set descricao = 'Professor de história do ensino superior' where codigo = '234745'; --.go
update codigocbo set descricao = 'Professor de história no ensino médio' where codigo = '232140'; --.go
update codigocbo set descricao = 'Professor de jornalismo' where codigo = '234750'; --.go
update codigocbo set descricao = 'Professor de língua alema' where codigo = '234604'; --.go
update codigocbo set descricao = 'Professor de língua e literatura brasileira no ensino médio' where codigo = '232145'; --.go
update codigocbo set descricao = 'Professor de língua espanhola' where codigo = '234620'; --.go
update codigocbo set descricao = 'Professor de língua estrangeira moderna do ensino fundamental' where codigo = '231330'; --.go
update codigocbo set descricao = 'Professor de língua estrangeira moderna no ensino médio' where codigo = '232150'; --.go
update codigocbo set descricao = 'Professor de língua francesa' where codigo = '234612'; --.go
update codigocbo set descricao = 'Professor de língua inglesa' where codigo = '234616'; --.go
update codigocbo set descricao = 'Professor de língua italiana' where codigo = '234608'; --.go
update codigocbo set descricao = 'Professor de língua portuguesa' where codigo = '234624'; --.go
update codigocbo set descricao = 'Professor de língua portuguesa do ensino fundamental' where codigo = '231335'; --.go
update codigocbo set descricao = 'Professor de línguas estrangeiras modernas' where codigo = '234668'; --.go
update codigocbo set descricao = 'Professor de lingüística e lingüística aplicada' where codigo = '234672'; --.go
update codigocbo set descricao = 'Professor de literatura alema' where codigo = '234636'; --.go
update codigocbo set descricao = 'Professor de literatura brasileira' where codigo = '234628'; --.go
update codigocbo set descricao = 'Professor de literatura comparada' where codigo = '234640'; --.go
update codigocbo set descricao = 'Professor de literatura de línguas estrangeiras modernas' where codigo = '234660'; --.go
update codigocbo set descricao = 'Professor de literatura espanhola' where codigo = '234644'; --.go
update codigocbo set descricao = 'Professor de literatura francesa' where codigo = '234648'; --.go
update codigocbo set descricao = 'Professor de literatura inglesa' where codigo = '234652'; --.go
update codigocbo set descricao = 'Professor de literatura italiana' where codigo = '234656'; --.go
update codigocbo set descricao = 'Professor de literatura portuguesa' where codigo = '234632'; --.go
update codigocbo set descricao = 'Professor de matemática aplicada (no ensino superior)' where codigo = '234105'; --.go
update codigocbo set descricao = 'Professor de matemática do ensino fundamental' where codigo = '231340'; --.go
update codigocbo set descricao = 'Professor de matemática no ensino médio' where codigo = '232155'; --.go
update codigocbo set descricao = 'Professor de matemática pura (no ensino superior)' where codigo = '234110'; --.go
update codigocbo set descricao = 'Professor de medicina' where codigo = '234435'; --.go
update codigocbo set descricao = 'Professor de medicina veterinária' where codigo = '234440'; --.go
update codigocbo set descricao = 'Professor de museologia do ensino superior' where codigo = '234755'; --.go
update codigocbo set descricao = 'Professor de música no ensino superior' where codigo = '234915'; --.go
update codigocbo set descricao = 'Professor de nível médio na educaçao infantil' where codigo = '331105'; --.go
update codigocbo set descricao = 'Professor de nível médio no ensino fundamental' where codigo = '331205'; --.go
update codigocbo set descricao = 'Professor de nível médio no ensino profissionalizante' where codigo = '331305'; --.go
update codigocbo set descricao = 'Professor de nível superior do ensino fundamental (primeira a quarta série)' where codigo = '231210'; --.go
update codigocbo set descricao = 'Professor de nível superior na educaçao infantil (quatro a seis anos)' where codigo = '231105'; --.go
update codigocbo set descricao = 'Professor de nível superior na educaçao infantil (zero a três anos)' where codigo = '231110'; --.go
update codigocbo set descricao = 'Professor de nutriçao' where codigo = '234445'; --.go
update codigocbo set descricao = 'Professor de odontologia' where codigo = '234450'; --.go
update codigocbo set descricao = 'Professor de outras línguas e literaturas' where codigo = '234664'; --.go
update codigocbo set descricao = 'Professor de pesquisa operacional (no ensino superior)' where codigo = '234125'; --.go
update codigocbo set descricao = 'Professor de psicologia do ensino superior' where codigo = '234760'; --.go
update codigocbo set descricao = 'Professor de psicologia no ensino médio' where codigo = '232160'; --.go
update codigocbo set descricao = 'Professor de química (ensino superior)' where codigo = '234210'; --.go
update codigocbo set descricao = 'Professor de química no ensino médio' where codigo = '232165'; --.go
update codigocbo set descricao = 'Professor de semiótica' where codigo = '234680'; --.go
update codigocbo set descricao = 'Professor de serviço social do ensino superior' where codigo = '234765'; --.go
update codigocbo set descricao = 'Professor de sociologia do ensino superior' where codigo = '234770'; --.go
update codigocbo set descricao = 'Professor de sociologia no ensino médio' where codigo = '232170'; --.go
update codigocbo set descricao = 'Professor de técnicas agrícolas' where codigo = '233115'; --.go
update codigocbo set descricao = 'Professor de técnicas comerciais e secretariais' where codigo = '233120'; --.go
update codigocbo set descricao = 'Professor de técnicas de enfermagem' where codigo = '233125'; --.go
update codigocbo set descricao = 'Professor de técnicas e recursos audiovisuais' where codigo = '239420'; --.go
update codigocbo set descricao = 'Professor de técnicas industriais' where codigo = '233130'; --.go
update codigocbo set descricao = 'Professor de tecnologia e cálculo técnico' where codigo = '233135'; --.go
update codigocbo set descricao = 'Professor de teoria da literatura' where codigo = '234684'; --.go
update codigocbo set descricao = 'Professor de terapia ocupacional' where codigo = '234455'; --.go
update codigocbo set descricao = 'Professor de zootecnia do ensino superior' where codigo = '234460'; --.go
update codigocbo set descricao = 'Professores de cursos livres' where codigo = '333115'; --.go
update codigocbo set descricao = 'Professor instrutor de ensino e aprendizagem agroflorestal' where codigo = '233220'; --.go
update codigocbo set descricao = 'Professor instrutor de ensino e aprendizagem em serviços' where codigo = '233225'; --.go
update codigocbo set descricao = 'Professor leigo no ensino fundamental' where codigo = '332105'; --.go
update codigocbo set descricao = 'Professor prático no ensino profissionalizante' where codigo = '332205'; --.go
update codigocbo set descricao = 'Profissional de atletismo' where codigo = '377140'; --.go
update codigocbo set descricao = 'Profissional do sexo' where codigo = '519805'; --.go
update codigocbo set descricao = 'Programador de internet' where codigo = '317105'; --.go
update codigocbo set descricao = 'Programador de máquinas - ferramenta com comando numérico' where codigo = '317115'; --.go
update codigocbo set descricao = 'Programador de multimídia' where codigo = '317120'; --.go
update codigocbo set descricao = 'Programador de sistemas de informaçao' where codigo = '317110'; --.go
update codigocbo set descricao = 'Programador visual gráfico' where codigo = '766155'; --.go
update codigocbo set descricao = 'Projetista de móveis' where codigo = '318805'; --.go
update codigocbo set descricao = 'Projetista de sistemas de áudio' where codigo = '374135'; --.go
update codigocbo set descricao = 'Projetista de som' where codigo = '374120'; --.go
update codigocbo set descricao = 'Promotor de justiça' where codigo = '242235'; --.go
update codigocbo set descricao = 'Promotor de vendas' where codigo = '521115'; --.go
update codigocbo set descricao = 'Promotor de vendas especializado' where codigo = '354130'; --.go
update codigocbo set descricao = 'Propagandista de produtos famacêuticos' where codigo = '354150'; --.go
update codigocbo set descricao = 'Protético dentário' where codigo = '322410'; --.go
update codigocbo set descricao = 'Psicanalista' where codigo = '251550'; --.go
update codigocbo set descricao = 'Psicólogo acupunturista' where codigo = '251555'; --.go
update codigocbo set descricao = 'Psicólogo clínico' where codigo = '251510'; --.go
update codigocbo set descricao = 'Psicólogo do esporte' where codigo = '251515'; --.go
update codigocbo set descricao = 'Psicólogo do trabalho' where codigo = '251540'; --.go
update codigocbo set descricao = 'Psicólogo do trânsito' where codigo = '251535'; --.go
update codigocbo set descricao = 'Psicólogo educacional' where codigo = '251505'; --.go
update codigocbo set descricao = 'Psicólogo hospitalar' where codigo = '251520'; --.go
update codigocbo set descricao = 'Psicólogo jurídico' where codigo = '251525'; --.go
update codigocbo set descricao = 'Psicólogo social' where codigo = '251530'; --.go
update codigocbo set descricao = 'Psicopedagogo' where codigo = '239425'; --.go
update codigocbo set descricao = 'Pugilista' where codigo = '377145'; --.go
update codigocbo set descricao = 'Queijeiro na fabricaçao de laticínio' where codigo = '848210'; --.go
update codigocbo set descricao = 'Químico' where codigo = '213205'; --.go
update codigocbo set descricao = 'Químico industrial' where codigo = '213210'; --.go
update codigocbo set descricao = 'Quiropraxista' where codigo = '226105'; --.go
update codigocbo set descricao = 'Rachador de couros e peles' where codigo = '762125'; --.go
update codigocbo set descricao = 'Radiotelegrafista' where codigo = '372210'; --.go
update codigocbo set descricao = 'Raizeiro' where codigo = '632010'; --.go
update codigocbo set descricao = 'Rebaixador de couros' where codigo = '762220'; --.go
update codigocbo set descricao = 'Rebarbador de metal' where codigo = '821450'; --.go
update codigocbo set descricao = 'Rebitador, a  mao' where codigo = '724230'; --.go
update codigocbo set descricao = 'Rebitador a  martelo pneumático' where codigo = '724215'; --.go
update codigocbo set descricao = 'Recebedor de apostas (loteria)' where codigo = '421205'; --.go
update codigocbo set descricao = 'Recebedor de apostas (turfe)' where codigo = '421210'; --.go
update codigocbo set descricao = 'Recepcionista de banco' where codigo = '422125'; --.go
update codigocbo set descricao = 'Recepcionista de casas de espetáculos' where codigo = '519945'; --.go
update codigocbo set descricao = 'Recepcionista de consultório médico ou dentário' where codigo = '422110'; --.go
update codigocbo set descricao = 'Recepcionista de hotel' where codigo = '422120'; --.go
update codigocbo set descricao = 'Recepcionista de seguro saúde' where codigo = '422115'; --.go
update codigocbo set descricao = 'Recepcionista, em geral' where codigo = '422105'; --.go
update codigocbo set descricao = 'Recreador' where codigo = '371410'; --.go
update codigocbo set descricao = 'Recreador de acantonamento' where codigo = '371405'; --.go
update codigocbo set descricao = 'Recuperador de guias e cilindros' where codigo = '821335'; --.go
update codigocbo set descricao = 'Redator de publicidade' where codigo = '253110'; --.go
update codigocbo set descricao = 'Redator de textos técnicos' where codigo = '261530'; --.go
update codigocbo set descricao = 'Redeiro' where codigo = '768120'; --.go
update codigocbo set descricao = 'Redeiro (pesca)' where codigo = '631420'; --.go
update codigocbo set descricao = 'Refinador de óleo e gordura' where codigo = '841472'; --.go
update codigocbo set descricao = 'Refinador de sal' where codigo = '841210'; --.go
update codigocbo set descricao = 'Relaçoes públicas' where codigo = '253105'; --.go
update codigocbo set descricao = 'Relações públicas' where codigo = '142325'; --.go
update codigocbo set descricao = 'Relojoeiro (fabricaçao)' where codigo = '741120'; --.go
update codigocbo set descricao = 'Relojoeiro (reparaçao)' where codigo = '741125'; --.go
update codigocbo set descricao = 'Remetedor de fios' where codigo = '761363'; --.go
update codigocbo set descricao = 'Reparador de aparelhos de telecomunicaçoes em laboratório' where codigo = '731330'; --.go
update codigocbo set descricao = 'Reparador de aparelhos eletrodomésticos (exceto imagem e som)' where codigo = '954205'; --.go
update codigocbo set descricao = 'Reparador de equipamentos de escritório' where codigo = '954305'; --.go
update codigocbo set descricao = 'Reparador de equipamentos fotográficos' where codigo = '915405'; --.go
update codigocbo set descricao = 'Reparador de instrumentos musicais' where codigo = '915210'; --.go
update codigocbo set descricao = 'Reparador de rádio, tv e som' where codigo = '954210'; --.go
update codigocbo set descricao = 'Repórter de rádio e televisao' where codigo = '261730'; --.go
update codigocbo set descricao = 'Repórter (exclusive rádio e televisao)' where codigo = '261135'; --.go
update codigocbo set descricao = 'Repositor de mercadorias' where codigo = '521125'; --.go
update codigocbo set descricao = 'Repóter fotográfico' where codigo = '261820'; --.go
update codigocbo set descricao = 'Representante comercial autônomo' where codigo = '354705'; --.go
update codigocbo set descricao = 'Restaurador de instrumentos musicais (exceto cordas arcadas)' where codigo = '915205'; --.go
update codigocbo set descricao = 'Restaurador de livros' where codigo = '768710'; --.go
update codigocbo set descricao = 'Retalhador de carne' where codigo = '848525'; --.go
update codigocbo set descricao = 'Revelador de filmes fotográficos, em cores' where codigo = '766415'; --.go
update codigocbo set descricao = 'Revelador de filmes fotográficos, em preto e branco' where codigo = '766410'; --.go
update codigocbo set descricao = 'Revestidor de interiores (papel, material plástico e emborrachados)' where codigo = '716615'; --.go
update codigocbo set descricao = 'Revestidor de superfícies de concreto' where codigo = '716110'; --.go
update codigocbo set descricao = 'Revisor de fios (produçao têxtil)' where codigo = '761810'; --.go
update codigocbo set descricao = 'Revisor de tecidos acabados' where codigo = '761815'; --.go
update codigocbo set descricao = 'Revisor de tecidos crus' where codigo = '761820'; --.go
update codigocbo set descricao = 'Revisor de texto' where codigo = '261140'; --.go
update codigocbo set descricao = 'Riscador de estruturas metálicas' where codigo = '724225'; --.go
update codigocbo set descricao = 'Riscador de roupas' where codigo = '763120'; --.go
update codigocbo set descricao = 'Sacristao' where codigo = '514115'; --.go
update codigocbo set descricao = 'Salgador de alimentos' where codigo = '848110'; --.go
update codigocbo set descricao = 'Salsicheiro (fabricaçao de lingüiça, salsicha e produtos similares)' where codigo = '848115'; --.go
update codigocbo set descricao = 'Salva-vidas' where codigo = '517115'; --.go
update codigocbo set descricao = 'Sapateiro (calçados sob medida)' where codigo = '768320'; --.go
update codigocbo set descricao = 'Sargento bombeiro militar' where codigo = '031110'; --.go
update codigocbo set descricao = 'Sargento da policia militar' where codigo = '021110'; --.go
update codigocbo set descricao = 'Secador de madeira' where codigo = '772115'; --.go
update codigocbo set descricao = 'Secretária  executiva' where codigo = '252305'; --.go
update codigocbo set descricao = 'Secretária trilíngüe' where codigo = '252315'; --.go
update codigocbo set descricao = 'Secretário  bilíngüe' where codigo = '252310'; --.go
update codigocbo set descricao = 'Secretário - executivo' where codigo = '111220'; --.go
update codigocbo set descricao = 'Segundo oficial de máquinas da marinha mercante' where codigo = '215215'; --.go
update codigocbo set descricao = 'Segundo tenente de polícia militar' where codigo = '020310'; --.go
update codigocbo set descricao = 'Seleiro' where codigo = '768325'; --.go
update codigocbo set descricao = 'Senador' where codigo = '111105'; --.go
update codigocbo set descricao = 'Sepultador' where codigo = '516610'; --.go
update codigocbo set descricao = 'Sericultor' where codigo = '613420'; --.go
update codigocbo set descricao = 'Seringueiro' where codigo = '632205'; --.go
update codigocbo set descricao = 'Serrador de bordas no desdobramento de madeira' where codigo = '773115'; --.go
update codigocbo set descricao = 'Serrador de madeira' where codigo = '773120'; --.go
update codigocbo set descricao = 'Serrador de madeira (serra circular múltipla)' where codigo = '773125'; --.go
update codigocbo set descricao = 'Serrador de madeira (serra de fita múltipla)' where codigo = '773130'; --.go
update codigocbo set descricao = 'Serralheiro' where codigo = '724440'; --.go
update codigocbo set descricao = 'Servente de obras' where codigo = '717020'; --.go
update codigocbo set descricao = 'Sexador' where codigo = '623325'; --.go
update codigocbo set descricao = 'Sinaleiro (ponte-rolante)' where codigo = '782145'; --.go
update codigocbo set descricao = 'Sócioeducador' where codigo = '515325'; --.go
update codigocbo set descricao = 'Sociólogo' where codigo = '251120'; --.go
update codigocbo set descricao = 'Soldado bombeiro militar' where codigo = '031210'; --.go
update codigocbo set descricao = 'Soldado da polícia militar' where codigo = '021210'; --.go
update codigocbo set descricao = 'Soldador' where codigo = '724315'; --.go
update codigocbo set descricao = 'Soldador aluminotérmico em conservaçao de trilhos' where codigo = '991120'; --.go
update codigocbo set descricao = 'Soldador a  oxigás' where codigo = '724320'; --.go
update codigocbo set descricao = 'Soldador elétrico' where codigo = '724325'; --.go
update codigocbo set descricao = 'Sondador de poços (exceto de petróleo e gás)' where codigo = '711320'; --.go
update codigocbo set descricao = 'Sondador (poços de petróleo e gás)' where codigo = '711315'; --.go
update codigocbo set descricao = 'Soprador de convertedor' where codigo = '821255'; --.go
update codigocbo set descricao = 'Soprador de vidro' where codigo = '752115'; --.go
update codigocbo set descricao = 'Subprocurador de justiça militar' where codigo = '242240'; --.go
update codigocbo set descricao = 'Subprocurador-geral da república' where codigo = '242245'; --.go
update codigocbo set descricao = 'Subprocurador-geral do trabalho' where codigo = '242250'; --.go
update codigocbo set descricao = 'Subtenente bombeiro militar' where codigo = '031105'; --.go
update codigocbo set descricao = 'Subtenente da policia militar' where codigo = '021105'; --.go
update codigocbo set descricao = 'Superintendente técnico no transporte aquaviário' where codigo = '215220'; --.go
update codigocbo set descricao = 'Supervisor administrativo' where codigo = '410105'; --.go
update codigocbo set descricao = 'Supervisor da administraçao de aeroportos' where codigo = '342540'; --.go
update codigocbo set descricao = 'Supervisor da aqüicultura' where codigo = '630105'; --.go
update codigocbo set descricao = 'Supervisor da área florestal' where codigo = '630110'; --.go
update codigocbo set descricao = 'Supervisor da confecçao de artefatos de tecidos, couros e afins' where codigo = '760505'; --.go
update codigocbo set descricao = 'Supervisor da indústria de bebidas' where codigo = '840110'; --.go
update codigocbo set descricao = 'Supervisor da indústria de fumo' where codigo = '840115'; --.go
update codigocbo set descricao = 'Supervisor da indústria de minerais nao metálicos (exceto os derivados de petróleo e carvao)' where codigo = '750205'; --.go
update codigocbo set descricao = 'Supervisor da manutençao e reparaçao de veículos leves' where codigo = '910205'; --.go
update codigocbo set descricao = 'Supervisor da manutençao e reparaçao de veículos pesados' where codigo = '910210'; --.go
update codigocbo set descricao = 'Supervisor da mecânica de precisao' where codigo = '740105'; --.go
update codigocbo set descricao = 'Supervisor das artes gráficas  (indústria editorial e gráfica)' where codigo = '760605'; --.go
update codigocbo set descricao = 'Supervisor de almoxarifado' where codigo = '410205'; --.go
update codigocbo set descricao = 'Supervisor de andar' where codigo = '510115'; --.go
update codigocbo set descricao = 'Supervisor de apoio operacional na mineraçao' where codigo = '710105'; --.go
update codigocbo set descricao = 'Supervisor de bombeiros' where codigo = '510305'; --.go
update codigocbo set descricao = 'Supervisor de caixas e bilheteiros (exceto caixa de banco)' where codigo = '420105'; --.go
update codigocbo set descricao = 'Supervisor de câmbio' where codigo = '410210'; --.go
update codigocbo set descricao = 'Supervisor de carga e descarga' where codigo = '342315'; --.go
update codigocbo set descricao = 'Supervisor de cobrança' where codigo = '420110'; --.go
update codigocbo set descricao = 'Supervisor de coletadores de apostas e de jogos' where codigo = '420115'; --.go
update codigocbo set descricao = 'Supervisor de compras' where codigo = '354210'; --.go
update codigocbo set descricao = 'Supervisor de contas a pagar' where codigo = '410215'; --.go
update codigocbo set descricao = 'Supervisor de controle de tratamento térmico' where codigo = '720160'; --.go
update codigocbo set descricao = 'Supervisor de controle patrimonial' where codigo = '410220'; --.go
update codigocbo set descricao = 'Supervisor de crédito e cobrança' where codigo = '410225'; --.go
update codigocbo set descricao = 'Supervisor de curtimento' where codigo = '760205'; --.go
update codigocbo set descricao = 'Supervisor de digitaçao e operaçao' where codigo = '412120'; --.go
update codigocbo set descricao = 'Supervisor de embalagem e etiquetagem' where codigo = '780105'; --.go
update codigocbo set descricao = 'Supervisor de empresa aérea em aeroportos' where codigo = '342545'; --.go
update codigocbo set descricao = 'Supervisor de ensino' where codigo = '239430'; --.go
update codigocbo set descricao = 'Supervisor de entrevistadores e recenseadores' where codigo = '420120'; --.go
update codigocbo set descricao = 'Supervisor de exploraçao agrícola' where codigo = '620105'; --.go
update codigocbo set descricao = 'Supervisor de exploraçao agropecuária' where codigo = '620110'; --.go
update codigocbo set descricao = 'Supervisor de exploraçao pecuária' where codigo = '620115'; --.go
update codigocbo set descricao = 'Supervisor de extraçao de sal' where codigo = '710110'; --.go
update codigocbo set descricao = 'Supervisor de fabricaçao de instrumentos musicais' where codigo = '740110'; --.go
update codigocbo set descricao = 'Supervisor de fabricaçao de produtos cerâmicos, porcelanatos e afins' where codigo = '820205'; --.go
update codigocbo set descricao = 'Supervisor de fabricaçao de produtos de vidro' where codigo = '820210'; --.go
update codigocbo set descricao = 'Supervisor de joalheria' where codigo = '750105'; --.go
update codigocbo set descricao = 'Supervisor de lavanderia' where codigo = '510205'; --.go
update codigocbo set descricao = 'Supervisor de manutençao de aparelhos térmicos, de climatizaçao e de refrigeraçao' where codigo = '910110'; --.go
update codigocbo set descricao = 'Supervisor de manutençao de bombas, motores, compressores e equipamentos de transmissao' where codigo = '910115'; --.go
update codigocbo set descricao = 'Supervisor de manutençao de máquinas gráficas' where codigo = '910120'; --.go
update codigocbo set descricao = 'Supervisor de manutençao de máquinas industriais têxteis' where codigo = '910125'; --.go
update codigocbo set descricao = 'Supervisor de manutençao de máquinas operatrizes e de usinagem' where codigo = '910130'; --.go
update codigocbo set descricao = 'Supervisor de manutençao de vias férreas' where codigo = '910910'; --.go
update codigocbo set descricao = 'Supervisor de manutençao elétrica de alta tensao industrial' where codigo = '950105'; --.go
update codigocbo set descricao = 'Supervisor de manutençao eletromecânica' where codigo = '950305'; --.go
update codigocbo set descricao = 'Supervisor de manutençao eletromecânica industrial, comercial e predial' where codigo = '950110'; --.go
update codigocbo set descricao = 'Supervisor de manutençao eletromecânica (utilidades)' where codigo = '860105'; --.go
update codigocbo set descricao = 'Supervisor de montagem e instalaçao eletroeletrônica' where codigo = '730105'; --.go
update codigocbo set descricao = 'Supervisor de operaçao de fluidos (distribuiçao, captaçao, tratamento de água, gases, vapor)' where codigo = '860110'; --.go
update codigocbo set descricao = 'Supervisor de operaçao elétrica (geraçao, transmissao e distribuiçao de energia elétrica)' where codigo = '860115'; --.go
update codigocbo set descricao = 'Supervisor de operaçoes portuárias' where codigo = '342610'; --.go
update codigocbo set descricao = 'Supervisor de orçamento' where codigo = '410230'; --.go
update codigocbo set descricao = 'Supervisor de perfuraçao e desmonte' where codigo = '710115'; --.go
update codigocbo set descricao = 'Supervisor de produçao da indústria alimentícia' where codigo = '840105'; --.go
update codigocbo set descricao = 'Supervisor de produçao na mineraçao' where codigo = '710120'; --.go
update codigocbo set descricao = 'Supervisor de recepcionistas' where codigo = '420125'; --.go
update codigocbo set descricao = 'Supervisor de reparos linhas férreas' where codigo = '910905'; --.go
update codigocbo set descricao = 'Supervisor de telefonistas' where codigo = '420130'; --.go
update codigocbo set descricao = 'Supervisor de telemarketing e atendimento' where codigo = '420135'; --.go
update codigocbo set descricao = 'Supervisor de tesouraria' where codigo = '410235'; --.go
update codigocbo set descricao = 'Supervisor de transporte na mineraçao' where codigo = '710125'; --.go
update codigocbo set descricao = 'Supervisor de transportes' where codigo = '510105'; --.go
update codigocbo set descricao = 'Supervisor de usina de concreto' where codigo = '710220'; --.go
update codigocbo set descricao = 'Supervisor de vendas comercial' where codigo = '520110'; --.go
update codigocbo set descricao = 'Supervisor de vendas de serviços' where codigo = '520105'; --.go
update codigocbo set descricao = 'Supervisor de vigilantes' where codigo = '510310'; --.go
update codigocbo set descricao = 'Supervisor  (indústria de calçados e artefatos de couro)' where codigo = '760405'; --.go
update codigocbo set descricao = 'Supervisor técnico operacional de sistemas de televisao e produtoras de vídeo' where codigo = '373220'; --.go
update codigocbo set descricao = 'Surfassagista' where codigo = '752235'; --.go
update codigocbo set descricao = 'Sushiman' where codigo = '513615'; --.go
update codigocbo set descricao = 'Tabeliao de notas' where codigo = '241335'; --.go
update codigocbo set descricao = 'Tabeliao de protestos' where codigo = '241340'; --.go
update codigocbo set descricao = 'Taifeiro (exceto militares)' where codigo = '511115'; --.go
update codigocbo set descricao = 'Tanoeiro' where codigo = '771120'; --.go
update codigocbo set descricao = 'Taqueiro' where codigo = '716535'; --.go
update codigocbo set descricao = 'Taquígrafo' where codigo = '351510'; --.go
update codigocbo set descricao = 'Taxidermista' where codigo = '328110'; --.go
update codigocbo set descricao = 'Tecelao de malhas, a  máquina' where codigo = '761327'; --.go
update codigocbo set descricao = 'Tecelao de malhas (máquina circular)' where codigo = '761330'; --.go
update codigocbo set descricao = 'Tecelao de malhas (máquina retilínea)' where codigo = '761333'; --.go
update codigocbo set descricao = 'Tecelao de meias, a  máquina' where codigo = '761336'; --.go
update codigocbo set descricao = 'Tecelao de meias (máquina circular)' where codigo = '761339'; --.go
update codigocbo set descricao = 'Tecelao de meias (máquina retilínea)' where codigo = '761342'; --.go
update codigocbo set descricao = 'Tecelao de tapetes, a  mao' where codigo = '768110'; --.go
update codigocbo set descricao = 'Tecelao de tapetes, a  máquina' where codigo = '761345'; --.go
update codigocbo set descricao = 'Tecelao (redes)' where codigo = '761303'; --.go
update codigocbo set descricao = 'Tecelao (rendas e bordados)' where codigo = '761306'; --.go
update codigocbo set descricao = 'Tecelao (tear automático)' where codigo = '761309'; --.go
update codigocbo set descricao = 'Tecelao (tear jacquard)' where codigo = '761312'; --.go
update codigocbo set descricao = 'Tecelao (tear manual)' where codigo = '768105'; --.go
update codigocbo set descricao = 'Tecelao (tear mecânico de maquineta)' where codigo = '761315'; --.go
update codigocbo set descricao = 'Tecelao (tear mecânico de xadrez)' where codigo = '761318'; --.go
update codigocbo set descricao = 'Tecelao (tear mecânico, exceto jacquard)' where codigo = '761324'; --.go
update codigocbo set descricao = 'Tecelao (tear mecânico liso)' where codigo = '761321'; --.go
update codigocbo set descricao = 'Técnico agrícola' where codigo = '321105'; --.go
update codigocbo set descricao = 'Técnico agropecuário' where codigo = '321110'; --.go
update codigocbo set descricao = 'Técnico da receita federal' where codigo = '254110'; --.go
update codigocbo set descricao = 'Técnico de acabamento em siderurgia' where codigo = '314705'; --.go
update codigocbo set descricao = 'Técnico de aciaria em siderurgia' where codigo = '314710'; --.go
update codigocbo set descricao = 'Técnico de alimentos' where codigo = '325205'; --.go
update codigocbo set descricao = 'Técnico de apoio à bioengenharia' where codigo = '301205'; --.go
update codigocbo set descricao = 'Técnico de apoio ao usuário de informática (helpdesk)' where codigo = '317210'; --.go
update codigocbo set descricao = 'Técnico de apoio em pesquisa e desenvolvimento agropecuário florestal' where codigo = '395110'; --.go
update codigocbo set descricao = 'Técnico de apoio em pesquisa e desenvolvimento (exceto agropecuário e florestal)' where codigo = '395105'; --.go
update codigocbo set descricao = 'Técnico de celulose e papel' where codigo = '311110'; --.go
update codigocbo set descricao = 'Técnico de comunicaçao de dados' where codigo = '313305'; --.go
update codigocbo set descricao = 'Técnico de contabilidade' where codigo = '351105'; --.go
update codigocbo set descricao = 'Técnico de controle de meio ambiente' where codigo = '311505'; --.go
update codigocbo set descricao = 'Técnico de desporto individual e coletivo (exceto futebol)' where codigo = '224125'; --.go
update codigocbo set descricao = 'Técnico de enfermagem' where codigo = '322205'; --.go
update codigocbo set descricao = 'Técnico de enfermagem da estratégia de saúde da família' where codigo = '322245'; --.go
update codigocbo set descricao = 'Técnico de enfermagem de terapia intensiva' where codigo = '322210'; --.go
update codigocbo set descricao = 'Técnico de enfermagem do trabalho' where codigo = '322215'; --.go
update codigocbo set descricao = 'Técnico de enfermagem psiquiátrica' where codigo = '322220'; --.go
update codigocbo set descricao = 'Técnico de estradas' where codigo = '312205'; --.go
update codigocbo set descricao = 'Técnico de fundiçao em siderurgia' where codigo = '314715'; --.go
update codigocbo set descricao = 'Técnico de garantia da qualidade' where codigo = '391210'; --.go
update codigocbo set descricao = 'Técnico de imobilizaçao ortopédica' where codigo = '322605'; --.go
update codigocbo set descricao = 'Técnico de laboratório de análises físico-químicas (materiais de construçao)' where codigo = '301110'; --.go
update codigocbo set descricao = 'Técnico de laboratório e fiscalizaçao desportiva' where codigo = '224130'; --.go
update codigocbo set descricao = 'Técnico de laboratório industrial' where codigo = '301105'; --.go
update codigocbo set descricao = 'Técnico de laminaçao em siderurgia' where codigo = '314720'; --.go
update codigocbo set descricao = 'Técnico de manutençao de sistemas e instrumentos' where codigo = '314405'; --.go
update codigocbo set descricao = 'Técnico de manutençao elétrica' where codigo = '313120'; --.go
update codigocbo set descricao = 'Técnico de manutençao elétrica de máquina' where codigo = '313125'; --.go
update codigocbo set descricao = 'Técnico de manutençao eletrônica' where codigo = '313205'; --.go
update codigocbo set descricao = 'Técnico de manutençao eletrônica (circuitos de máquinas com comando numérico)' where codigo = '313210'; --.go
update codigocbo set descricao = 'Técnico de matéria-prima e material' where codigo = '391135'; --.go
update codigocbo set descricao = 'Técnico de meteorologia' where codigo = '311510'; --.go
update codigocbo set descricao = 'Técnico de mineraçao' where codigo = '316305'; --.go
update codigocbo set descricao = 'Técnico de mineraçao (óleo e petróleo)' where codigo = '316310'; --.go
update codigocbo set descricao = 'Técnico de obras civis' where codigo = '312105'; --.go
update codigocbo set descricao = 'Técnico de operaçao (química, petroquímica e afins)' where codigo = '813130'; --.go
update codigocbo set descricao = 'Técnico de operaçoes e serviços bancários - câmbio' where codigo = '353205'; --.go
update codigocbo set descricao = 'Técnico de operaçoes e serviços bancários - crédito imobiliário' where codigo = '353210'; --.go
update codigocbo set descricao = 'Técnico de operaçoes e serviços bancários - crédito rural' where codigo = '353215'; --.go
update codigocbo set descricao = 'Técnico de operaçoes e serviços bancários - leasing' where codigo = '353220'; --.go
update codigocbo set descricao = 'Técnico de operaçoes e serviços bancários - renda fixa e variável' where codigo = '353225'; --.go
update codigocbo set descricao = 'Técnico de ortopedia' where codigo = '322505'; --.go
update codigocbo set descricao = 'Técnico de painel de controle' where codigo = '391220'; --.go
update codigocbo set descricao = 'Técnico de planejamento de produçao' where codigo = '391125'; --.go
update codigocbo set descricao = 'Técnico de planejamento e programaçao da manutençao' where codigo = '391130'; --.go
update codigocbo set descricao = 'Técnico de produçao em refino de petróleo' where codigo = '316325'; --.go
update codigocbo set descricao = 'Técnico de rede (telecomunicaçoes)' where codigo = '313310'; --.go
update codigocbo set descricao = 'Técnico de reduçao na siderurgia (primeira fusao)' where codigo = '314725'; --.go
update codigocbo set descricao = 'Técnico de refratário em siderurgia' where codigo = '314730'; --.go
update codigocbo set descricao = 'Técnico de resseguros' where codigo = '351735'; --.go
update codigocbo set descricao = 'Técnico de saneamento' where codigo = '312210'; --.go
update codigocbo set descricao = 'Técnico de seguros' where codigo = '351740'; --.go
update codigocbo set descricao = 'Técnico de telecomunicaçoes (telefonia)' where codigo = '313315'; --.go
update codigocbo set descricao = 'Técnico de transmissao (telecomunicaçoes)' where codigo = '313320'; --.go
update codigocbo set descricao = 'Técnico de tributos estadual' where codigo = '254415'; --.go
update codigocbo set descricao = 'Técnico de tributos municipal' where codigo = '254420'; --.go
update codigocbo set descricao = 'Técnico de utilidade (produçao e distribuiçao de vapor, gases, óleos, combustíveis, energia)' where codigo = '311515'; --.go
update codigocbo set descricao = 'Técnico de vendas' where codigo = '354135'; --.go
update codigocbo set descricao = 'Técnico do mobiliário' where codigo = '319205'; --.go
update codigocbo set descricao = 'Técnico eletricista' where codigo = '313130'; --.go
update codigocbo set descricao = 'Técnico eletrônico' where codigo = '313215'; --.go
update codigocbo set descricao = 'Técnico em acupuntura' where codigo = '322105'; --.go
update codigocbo set descricao = 'Técnico em administraçao' where codigo = '351305'; --.go
update codigocbo set descricao = 'Técnico em administraçao de comércio exterior' where codigo = '351310'; --.go
update codigocbo set descricao = 'Técnico em agrimensura' where codigo = '312305'; --.go
update codigocbo set descricao = 'Técnico em atendimento e vendas' where codigo = '354140'; --.go
update codigocbo set descricao = 'Técnico em automobilística' where codigo = '314305'; --.go
update codigocbo set descricao = 'Técnico em biblioteconomia' where codigo = '371110'; --.go
update codigocbo set descricao = 'Técnico em biotecnologia' where codigo = '325305'; --.go
update codigocbo set descricao = 'Técnico em bioterismo' where codigo = '320105'; --.go
update codigocbo set descricao = 'Técnico em borracha' where codigo = '311405'; --.go
update codigocbo set descricao = 'Técnico em calçados e artefatos de couro' where codigo = '319105'; --.go
update codigocbo set descricao = 'Técnico em caldeiraria' where codigo = '314610'; --.go
update codigocbo set descricao = 'Técnico em calibraçao' where codigo = '313405'; --.go
update codigocbo set descricao = 'Técnico em carcinicultura' where codigo = '321310'; --.go
update codigocbo set descricao = 'Técnico em confecçoes do vestuário' where codigo = '319110'; --.go
update codigocbo set descricao = 'Técnico em curtimento' where codigo = '311115'; --.go
update codigocbo set descricao = 'Técnico em direitos autorais' where codigo = '352420'; --.go
update codigocbo set descricao = 'Técnico em eletromecânica' where codigo = '300305'; --.go
update codigocbo set descricao = 'Técnico em estruturas metálicas' where codigo = '314615'; --.go
update codigocbo set descricao = 'Técnico em farmácia' where codigo = '325115'; --.go
update codigocbo set descricao = 'Técnico em fotônica' where codigo = '313505'; --.go
update codigocbo set descricao = 'Técnico em geodésia e cartografia' where codigo = '312310'; --.go
update codigocbo set descricao = 'Técnico em geofísica' where codigo = '316105'; --.go
update codigocbo set descricao = 'Técnico em geologia' where codigo = '316110'; --.go
update codigocbo set descricao = 'Técnico em geoquímica' where codigo = '316115'; --.go
update codigocbo set descricao = 'Técnico em geotecnia' where codigo = '316120'; --.go
update codigocbo set descricao = 'Técnico em gravaçao de áudio' where codigo = '374105'; --.go
update codigocbo set descricao = 'Técnico em hidrografia' where codigo = '312315'; --.go
update codigocbo set descricao = 'Técnico em higiene dental' where codigo = '322405'; --.go
update codigocbo set descricao = 'Técnico em histologia' where codigo = '320110'; --.go
update codigocbo set descricao = 'Técnico em imunobiológicos' where codigo = '325310'; --.go
update codigocbo set descricao = 'Técnico em instalaçao de equipamentos de áudio' where codigo = '374110'; --.go
update codigocbo set descricao = 'Técnico em instrumentaçao' where codigo = '313410'; --.go
update codigocbo set descricao = 'Técnico em laboratório de farmácia' where codigo = '325110'; --.go
update codigocbo set descricao = 'Técnico em madeira' where codigo = '321205'; --.go
update codigocbo set descricao = 'Técnico em manutençao de balanças' where codigo = '915115'; --.go
update codigocbo set descricao = 'Técnico em manutençao de equipamentos de informática' where codigo = '313220'; --.go
update codigocbo set descricao = 'Técnico em manutençao de equipamentos e instrumentos médico-hospitalares' where codigo = '915305'; --.go
update codigocbo set descricao = 'Técnico em manutençao de hidrômetros' where codigo = '915110'; --.go
update codigocbo set descricao = 'Técnico em manutençao de instrumentos de mediçao e precisao' where codigo = '915105'; --.go
update codigocbo set descricao = 'Técnico em manutençao de máquinas' where codigo = '314410'; --.go
update codigocbo set descricao = 'Técnico em masterizaçao de áudio' where codigo = '374115'; --.go
update codigocbo set descricao = 'Técnico em materiais, produtos cerâmicos e vidros' where codigo = '311305'; --.go
update codigocbo set descricao = 'Técnico em mecânica de precisao' where codigo = '314105'; --.go
update codigocbo set descricao = 'Técnico em mecatrônica - automaçao da manufatura' where codigo = '300105'; --.go
update codigocbo set descricao = 'Técnico em mecatrônica - robótica' where codigo = '300110'; --.go
update codigocbo set descricao = 'Técnico em métodos eletrográficos em encefalografia' where codigo = '324105'; --.go
update codigocbo set descricao = 'Técnico em métodos gráficos em cardiologia' where codigo = '324110'; --.go
update codigocbo set descricao = 'Técnico em mitilicultura' where codigo = '321315'; --.go
update codigocbo set descricao = 'Técnico em mixagem de áudio' where codigo = '374130'; --.go
update codigocbo set descricao = 'Técnico em museologia' where codigo = '371210'; --.go
update codigocbo set descricao = 'Técnico em nutriçao e dietética' where codigo = '325210'; --.go
update codigocbo set descricao = 'Técnico em operaçao de equipamento de exibiçao de televisao' where codigo = '373210'; --.go
update codigocbo set descricao = 'Técnico em operaçao de equipamentos de produçao para televisao  e produtoras de vídeo' where codigo = '373205'; --.go
update codigocbo set descricao = 'Técnico em operaçao de equipamentos de transmissao/recepçao de televisao' where codigo = '373215'; --.go
update codigocbo set descricao = 'Técnico em óptica e optometria' where codigo = '322305'; --.go
update codigocbo set descricao = 'Técnico em patologia clínica' where codigo = '324205'; --.go
update codigocbo set descricao = 'Técnico em pecuária' where codigo = '323105'; --.go
update codigocbo set descricao = 'Técnico em pesquisa mineral' where codigo = '316320'; --.go
update codigocbo set descricao = 'Técnico em petroquímica' where codigo = '311205'; --.go
update codigocbo set descricao = 'Técnico em piscicultura' where codigo = '321305'; --.go
update codigocbo set descricao = 'Técnico em planejamento de lavra de minas' where codigo = '316330'; --.go
update codigocbo set descricao = 'Técnico em plástico' where codigo = '311410'; --.go
update codigocbo set descricao = 'Técnico em processamento mineral (exceto petróleo)' where codigo = '316315'; --.go
update codigocbo set descricao = 'Técnico em programaçao visual' where codigo = '371305'; --.go
update codigocbo set descricao = 'Técnico em quiropraxia' where codigo = '322115'; --.go
update codigocbo set descricao = 'Técnico em radiologia e imagenologia' where codigo = '324115'; --.go
update codigocbo set descricao = 'Técnico em ranicultura' where codigo = '321320'; --.go
update codigocbo set descricao = 'Técnico em saúde bucal da estratégia de saúde da família' where codigo = '322425'; --.go
update codigocbo set descricao = 'Técnico em secretariado' where codigo = '351505'; --.go
update codigocbo set descricao = 'Técnico em segurança no trabalho' where codigo = '351605'; --.go
update codigocbo set descricao = 'Técnico em soldagem' where codigo = '314620'; --.go
update codigocbo set descricao = 'Técnico em sonorizaçao' where codigo = '374125'; --.go
update codigocbo set descricao = 'Técnico em tratamento de efluentes' where codigo = '311520'; --.go
update codigocbo set descricao = 'Técnico em turismo' where codigo = '354805'; --.go
update codigocbo set descricao = 'Técnico florestal' where codigo = '321210'; --.go
update codigocbo set descricao = 'Técnico gráfico' where codigo = '371310'; --.go
update codigocbo set descricao = 'Técnico mecânico' where codigo = '314110'; --.go
update codigocbo set descricao = 'Técnico mecânico (aeronaves)' where codigo = '314310'; --.go
update codigocbo set descricao = 'Técnico mecânico (calefaçao, ventilaçao e refrigeraçao)' where codigo = '314115'; --.go
update codigocbo set descricao = 'Técnico mecânico (embarcaçoes)' where codigo = '314315'; --.go
update codigocbo set descricao = 'Técnico mecânico (máquinas)' where codigo = '314120'; --.go
update codigocbo set descricao = 'Técnico mecânico (motores)' where codigo = '314125'; --.go
update codigocbo set descricao = 'Técnico mecânico na fabricaçao de ferramentas' where codigo = '314205'; --.go
update codigocbo set descricao = 'Técnico mecânico na manutençao de ferramentas' where codigo = '314210'; --.go
update codigocbo set descricao = 'Técnico operacional de serviços de correios' where codigo = '391230'; --.go
update codigocbo set descricao = 'Técnico químico' where codigo = '311105'; --.go
update codigocbo set descricao = 'Técnico químico de petróleo' where codigo = '301115'; --.go
update codigocbo set descricao = 'Técnico têxtil' where codigo = '311605'; --.go
update codigocbo set descricao = 'Técnico têxtil de fiaçao' where codigo = '311615'; --.go
update codigocbo set descricao = 'Técnico têxtil de malharia' where codigo = '311620'; --.go
update codigocbo set descricao = 'Técnico têxtil de tecelagem' where codigo = '311625'; --.go
update codigocbo set descricao = 'Técnico têxtil (tratamentos químicos)' where codigo = '311610'; --.go
update codigocbo set descricao = 'Tecnólogo em alimentos' where codigo = '222215'; --.go
update codigocbo set descricao = 'Tecnólogo em automação industrial' where codigo = '202120'; --.go
update codigocbo set descricao = 'Tecnólogo em construção civil' where codigo = '214280'; --.go
update codigocbo set descricao = 'Tecnólogo em eletricidade' where codigo = '214360'; --.go
update codigocbo set descricao = 'Tecnólogo em eletrônica' where codigo = '214365'; --.go
update codigocbo set descricao = 'Tecnólogo em fabricação mecânica' where codigo = '214435'; --.go
update codigocbo set descricao = 'Tecnólogo em gastronomia' where codigo = '271110'; --.go
update codigocbo set descricao = 'Tecnólogo em gestão administrativo- financeira' where codigo = '142120'; --.go
update codigocbo set descricao = 'Tecnólogo em gestão da tecnologia da informação' where codigo = '142535'; --.go
update codigocbo set descricao = 'Tecnólogo em logistica de transporte' where codigo = '342125'; --.go
update codigocbo set descricao = 'Tecnólogo em mecatrônica' where codigo = '202115'; --.go
update codigocbo set descricao = 'Tecnólogo em meio ambiente' where codigo = '214010'; --.go
update codigocbo set descricao = 'Tecnólogo em metalurgia' where codigo = '214615'; --.go
update codigocbo set descricao = 'Tecnólogo em petróleo e gás' where codigo = '214745'; --.go
update codigocbo set descricao = 'Técnólogo em processos químicos' where codigo = '213215'; --.go
update codigocbo set descricao = 'Tecnólogo em produção audiovisual' where codigo = '262135'; --.go
update codigocbo set descricao = 'Tecnólogo em produção fonográfica' where codigo = '262130'; --.go
update codigocbo set descricao = 'Tecnólogo em produção industrial' where codigo = '214930'; --.go
update codigocbo set descricao = 'Tecnólogo em produção sulcroalcooleira' where codigo = '214535'; --.go
update codigocbo set descricao = 'Tecnólogo em rochas ornamentais' where codigo = '214750'; --.go
update codigocbo set descricao = 'Tecnólogo em segurança do trabalho' where codigo = '214935'; --.go
update codigocbo set descricao = 'Tecnólogo em telecomunicações' where codigo = '214370'; --.go
update codigocbo set descricao = 'Tecnólogo oftálmico' where codigo = '324125'; --.go
update codigocbo set descricao = 'Telefonista' where codigo = '422205'; --.go
update codigocbo set descricao = 'Teleoperador' where codigo = '422210'; --.go
update codigocbo set descricao = 'Telhador (telhas de argila e materias similares)' where codigo = '716205'; --.go
update codigocbo set descricao = 'Telhador (telhas de cimento-amianto)' where codigo = '716210'; --.go
update codigocbo set descricao = 'Telhador (telhas metálicas)' where codigo = '716215'; --.go
update codigocbo set descricao = 'Telhador (telhas pláticas)' where codigo = '716220'; --.go
update codigocbo set descricao = 'Temperador de metais e de compósitos' where codigo = '723125'; --.go
update codigocbo set descricao = 'Temperador de vidro' where codigo = '823255'; --.go
update codigocbo set descricao = 'Tenente-coronel bombeiro militar' where codigo = '030115'; --.go
update codigocbo set descricao = 'Tenente-coronel da polícia militar' where codigo = '020110'; --.go
update codigocbo set descricao = 'Tenente do corpo de bombeiros militar' where codigo = '030305'; --.go
update codigocbo set descricao = 'Teólogo' where codigo = '263115'; --.go
update codigocbo set descricao = 'Terapeuta holístico' where codigo = '322117'; --.go
update codigocbo set descricao = 'Terapeuta ocupacional' where codigo = '223905'; --.go
update codigocbo set descricao = 'Tesoureiro de banco' where codigo = '353230'; --.go
update codigocbo set descricao = 'Tingidor de couros e peles' where codigo = '311725'; --.go
update codigocbo set descricao = 'Tingidor de roupas' where codigo = '516330'; --.go
update codigocbo set descricao = 'Tipógrafo' where codigo = '768605'; --.go
update codigocbo set descricao = 'Titeriteiro' where codigo = '376250'; --.go
update codigocbo set descricao = 'Topógrafo' where codigo = '312320'; --.go
update codigocbo set descricao = 'Torneiro (lavra de pedra)' where codigo = '712225'; --.go
update codigocbo set descricao = 'Torneiro na usinagem convencional de madeira' where codigo = '773355'; --.go
update codigocbo set descricao = 'Torrador de cacau' where codigo = '841625'; --.go
update codigocbo set descricao = 'Torrador de café' where codigo = '841610'; --.go
update codigocbo set descricao = 'Torrista (petróleo)' where codigo = '711330'; --.go
update codigocbo set descricao = 'Tosador de animais domésticos' where codigo = '519320'; --.go
update codigocbo set descricao = 'Trabalhador agropecuário em geral' where codigo = '621005'; --.go
update codigocbo set descricao = 'Trabalhador da avicultura de corte' where codigo = '623305'; --.go
update codigocbo set descricao = 'Trabalhador da avicultura de postura' where codigo = '623310'; --.go
update codigocbo set descricao = 'Trabalhador da caprinocultura' where codigo = '623205'; --.go
update codigocbo set descricao = 'Trabalhador da cultura de algodao' where codigo = '622205'; --.go
update codigocbo set descricao = 'Trabalhador da cultura de arroz' where codigo = '622105'; --.go
update codigocbo set descricao = 'Trabalhador da cultura de cacau' where codigo = '622605'; --.go
update codigocbo set descricao = 'Trabalhador da cultura de café' where codigo = '622610'; --.go
update codigocbo set descricao = 'Trabalhador da cultura de cana-de-açúcar' where codigo = '622110'; --.go
update codigocbo set descricao = 'Trabalhador da cultura de erva-mate' where codigo = '622615'; --.go
update codigocbo set descricao = 'Trabalhador da cultura de especiarias' where codigo = '622805'; --.go
update codigocbo set descricao = 'Trabalhador da cultura de fumo' where codigo = '622620'; --.go
update codigocbo set descricao = 'Trabalhador da cultura de guaraná' where codigo = '622625'; --.go
update codigocbo set descricao = 'Trabalhador da cultura de milho e sorgo' where codigo = '622115'; --.go
update codigocbo set descricao = 'Trabalhador da cultura de plantas aromáticas e medicinais' where codigo = '622810'; --.go
update codigocbo set descricao = 'Trabalhador da cultura de sisal' where codigo = '622210'; --.go
update codigocbo set descricao = 'Trabalhador da cultura de trigo, aveia, cevada e triticale' where codigo = '622120'; --.go
update codigocbo set descricao = 'Trabalhador da cultura do rami' where codigo = '622215'; --.go
update codigocbo set descricao = 'Trabalhador da cunicultura' where codigo = '623320'; --.go
update codigocbo set descricao = 'Trabalhador da elaboraçao de pré-fabricados (cimento amianto)' where codigo = '823320'; --.go
update codigocbo set descricao = 'Trabalhador da elaboraçao de pré-fabricados (concreto armado)' where codigo = '823325'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de açaí' where codigo = '632405'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de andiroba' where codigo = '632305'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de árvores e arbustos produtores de substâncias aromát., Medic. E tóxicas' where codigo = '632505'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de babaçu' where codigo = '632310'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de bacaba' where codigo = '632315'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de buriti' where codigo = '632320'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de carnaúba' where codigo = '632325'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de castanha' where codigo = '632410'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de cipós produtores de substâncias aromáticas, medicinais e tóxicas' where codigo = '632510'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de coco-da-praia' where codigo = '632330'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de copaíba' where codigo = '632335'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de espécies produtoras de gomas nao elásticas' where codigo = '632210'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de madeiras tanantes' where codigo = '632515'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de malva (paina)' where codigo = '632340'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de murumuru' where codigo = '632345'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de oiticica' where codigo = '632350'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de ouricuri' where codigo = '632355'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de pequi' where codigo = '632360'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de piaçava' where codigo = '632365'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de pinhao' where codigo = '632415'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de pupunha' where codigo = '632420'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de raízes produtoras de substâncias aromáticas, medicinais e tóxicas' where codigo = '632520'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de resinas' where codigo = '632215'; --.go
update codigocbo set descricao = 'Trabalhador da exploraçao de tucum' where codigo = '632370'; --.go
update codigocbo set descricao = 'Trabalhador da extraçao de substâncias aromáticas, medicinais e tóxicas, em geral' where codigo = '632525'; --.go
update codigocbo set descricao = 'Trabalhador da fabricaçao de muniçao e explosivos' where codigo = '812110'; --.go
update codigocbo set descricao = 'Trabalhador da fabricaçao de pedras artificiais' where codigo = '823330'; --.go
update codigocbo set descricao = 'Trabalhador da fabricaçao de resinas e vernizes' where codigo = '811125'; --.go
update codigocbo set descricao = 'Trabalhador da manutençao de edificaçoes' where codigo = '514325'; --.go
update codigocbo set descricao = 'Trabalhador da ovinocultura' where codigo = '623210'; --.go
update codigocbo set descricao = 'Trabalhador da pecuária (asininos e muares)' where codigo = '623105'; --.go
update codigocbo set descricao = 'Trabalhador da pecuária (bovinos corte)' where codigo = '623110'; --.go
update codigocbo set descricao = 'Trabalhador da pecuária (bovinos leite)' where codigo = '623115'; --.go
update codigocbo set descricao = 'Trabalhador da pecuária (bubalinos)' where codigo = '623120'; --.go
update codigocbo set descricao = 'Trabalhador da pecuária (eqüinos)' where codigo = '623125'; --.go
update codigocbo set descricao = 'Trabalhador da suinocultura' where codigo = '623215'; --.go
update codigocbo set descricao = 'Trabalhador de extraçao florestal, em geral' where codigo = '632125'; --.go
update codigocbo set descricao = 'Trabalhador de fabricaçao de margarina' where codigo = '841476'; --.go
update codigocbo set descricao = 'Trabalhador de fabricaçao de sorvete' where codigo = '848325'; --.go
update codigocbo set descricao = 'Trabalhador de fabricaçao de tintas' where codigo = '811130'; --.go
update codigocbo set descricao = 'Trabalhador de fabricaçao de vinhos' where codigo = '841720'; --.go
update codigocbo set descricao = 'Trabalhador de pecuária polivalente' where codigo = '623015'; --.go
update codigocbo set descricao = 'Trabalhador de preparaçao de pescados (limpeza)' where codigo = '841484'; --.go
update codigocbo set descricao = 'Trabalhador de serviços de limpeza e conservaçao de áreas públicas' where codigo = '514225'; --.go
update codigocbo set descricao = 'Trabalhador de tratamento do leite e fabricaçao de laticínios e afins' where codigo = '841505'; --.go
update codigocbo set descricao = 'Trabalhador do acabamento de artefatos de tecidos e couros' where codigo = '765405'; --.go
update codigocbo set descricao = 'Trabalhador do beneficiamento de fumo' where codigo = '848605'; --.go
update codigocbo set descricao = 'Trabalhador em criatórios de animais produtores de veneno' where codigo = '623405'; --.go
update codigocbo set descricao = 'Trabalhador na apicultura' where codigo = '623410'; --.go
update codigocbo set descricao = 'Trabalhador na cultura de amendoim' where codigo = '622705'; --.go
update codigocbo set descricao = 'Trabalhador na cultura de canola' where codigo = '622710'; --.go
update codigocbo set descricao = 'Trabalhador na cultura de coco-da-baía' where codigo = '622715'; --.go
update codigocbo set descricao = 'Trabalhador na cultura de dendê' where codigo = '622720'; --.go
update codigocbo set descricao = 'Trabalhador na cultura de mamona' where codigo = '622725'; --.go
update codigocbo set descricao = 'Trabalhador na cultura de soja' where codigo = '622730'; --.go
update codigocbo set descricao = 'Trabalhador na cultura do girassol' where codigo = '622735'; --.go
update codigocbo set descricao = 'Trabalhador na cultura do linho' where codigo = '622740'; --.go
update codigocbo set descricao = 'Trabalhador na fabricaçao de produtos abrasivos' where codigo = '823265'; --.go
update codigocbo set descricao = 'Trabalhador na minhocultura' where codigo = '623415'; --.go
update codigocbo set descricao = 'Trabalhador na olericultura (frutos e sementes)' where codigo = '622305'; --.go
update codigocbo set descricao = 'Trabalhador na olericultura (legumes)' where codigo = '622310'; --.go
update codigocbo set descricao = 'Trabalhador na olericultura (raízes, bulbos e tubérculos)' where codigo = '622315'; --.go
update codigocbo set descricao = 'Trabalhador na olericultura (talos, folhas e flores)' where codigo = '622320'; --.go
update codigocbo set descricao = 'Trabalhador na operaçao de sistema de irrigaçao localizada (microaspersao e gotejamento)' where codigo = '643005'; --.go
update codigocbo set descricao = 'Trabalhador na operaçao de sistema de irrigaçao por aspersao (pivô central)' where codigo = '643010'; --.go
update codigocbo set descricao = 'Trabalhador na operaçao de sistemas convencionais de irrigaçao por aspersao' where codigo = '643015'; --.go
update codigocbo set descricao = 'Trabalhador na operaçao de sistemas de irrigaçao e aspersao (alto propelido)' where codigo = '643020'; --.go
update codigocbo set descricao = 'Trabalhador na operaçao de sistemas de irrigaçao por superfície e drenagem' where codigo = '643025'; --.go
update codigocbo set descricao = 'Trabalhador na produçao de mudas e sementes' where codigo = '622015'; --.go
update codigocbo set descricao = 'Trabalhador na sericicultura' where codigo = '623420'; --.go
update codigocbo set descricao = 'Trabalhador no cultivo de árvores frutíferas' where codigo = '622505'; --.go
update codigocbo set descricao = 'Trabalhador no cultivo de espécies frutíferas rasteiras' where codigo = '622510'; --.go
update codigocbo set descricao = 'Trabalhador no cultivo de flores e folhagens de corte' where codigo = '622405'; --.go
update codigocbo set descricao = 'Trabalhador no cultivo de flores em vaso' where codigo = '622410'; --.go
update codigocbo set descricao = 'Trabalhador no cultivo de forraçoes' where codigo = '622415'; --.go
update codigocbo set descricao = 'Trabalhador no cultivo de mudas' where codigo = '622420'; --.go
update codigocbo set descricao = 'Trabalhador no cultivo de plantas ornamentais' where codigo = '622425'; --.go
update codigocbo set descricao = 'Trabalhador no cultivo de trepadeiras frutíferas' where codigo = '622515'; --.go
update codigocbo set descricao = 'Trabalhador polivalente da confecçao de calçados' where codigo = '764005'; --.go
update codigocbo set descricao = 'Trabalhador polivalente do curtimento de couros e peles' where codigo = '762005'; --.go
update codigocbo set descricao = 'Trabalhador volante da agricultura' where codigo = '622020'; --.go
update codigocbo set descricao = 'Traçador de pedras' where codigo = '712230'; --.go
update codigocbo set descricao = 'Tradutor' where codigo = '261420'; --.go
update codigocbo set descricao = 'Trançador de cabos de aço' where codigo = '724610'; --.go
update codigocbo set descricao = 'Transformador de tubos de vidro' where codigo = '752120'; --.go
update codigocbo set descricao = 'Trapezista' where codigo = '376255'; --.go
update codigocbo set descricao = 'Tratador de animais' where codigo = '623020'; --.go
update codigocbo set descricao = 'Tratorista agrícola' where codigo = '641015'; --.go
update codigocbo set descricao = 'Trefilador de borracha' where codigo = '811775'; --.go
update codigocbo set descricao = 'Trefilador de metais, à máquina' where codigo = '722415'; --.go
update codigocbo set descricao = 'Trefilador (joalheria e ourivesaria)' where codigo = '751130'; --.go
update codigocbo set descricao = 'Treinador profissional de futebol' where codigo = '224135'; --.go
update codigocbo set descricao = 'Tricoteiro, à mao' where codigo = '768115'; --.go
update codigocbo set descricao = 'Tropeiro' where codigo = '782810'; --.go
update codigocbo set descricao = 'Turismólogo' where codigo = '122520'; --.go
update codigocbo set descricao = 'Urbanista' where codigo = '214130'; --.go
update codigocbo set descricao = 'Vaqueador de couros e peles' where codigo = '762345'; --.go
update codigocbo set descricao = 'Varredor de rua' where codigo = '514215'; --.go
update codigocbo set descricao = 'Vassoureiro' where codigo = '776430'; --.go
update codigocbo set descricao = 'Vendedor ambulante' where codigo = '524305'; --.go
update codigocbo set descricao = 'Vendedor de comércio varejista' where codigo = '521110'; --.go
update codigocbo set descricao = 'Vendedor em comércio atacadista' where codigo = '521105'; --.go
update codigocbo set descricao = 'Vendedor em domicílio' where codigo = '524105'; --.go
update codigocbo set descricao = 'Vendedor  permissionário' where codigo = '524215'; --.go
update codigocbo set descricao = 'Vendedor pracista' where codigo = '354145'; --.go
update codigocbo set descricao = 'Vereador' where codigo = '111120'; --.go
update codigocbo set descricao = 'Vibradorista' where codigo = '717025'; --.go
update codigocbo set descricao = 'Vice-governador de estado' where codigo = '111240'; --.go
update codigocbo set descricao = 'Vice-governador do distrito federal' where codigo = '111245'; --.go
update codigocbo set descricao = 'Vice-prefeito' where codigo = '111255'; --.go
update codigocbo set descricao = 'Vice-presidente da república' where codigo = '111210'; --.go
update codigocbo set descricao = 'Vidraceiro' where codigo = '716305'; --.go
update codigocbo set descricao = 'Vidraceiro (edificaçoes)' where codigo = '716310'; --.go
update codigocbo set descricao = 'Vidraceiro (vitrais)' where codigo = '716315'; --.go
update codigocbo set descricao = 'Vigia' where codigo = '517420'; --.go
update codigocbo set descricao = 'Vigia florestal' where codigo = '517320'; --.go
update codigocbo set descricao = 'Vigia portuário' where codigo = '517325'; --.go
update codigocbo set descricao = 'Vigilante' where codigo = '517330'; --.go
update codigocbo set descricao = 'Vinagreiro' where codigo = '841740'; --.go
update codigocbo set descricao = 'Visitador sanitário' where codigo = '515120'; --.go
update codigocbo set descricao = 'Vistoriador naval' where codigo = '215150'; --.go
update codigocbo set descricao = 'Visual merchandiser' where codigo = '375115'; --.go
update codigocbo set descricao = 'Viveirista florestal' where codigo = '632015'; --.go
update codigocbo set descricao = 'Xaropeiro' where codigo = '841745'; --.go
update codigocbo set descricao = 'Zelador de edifício' where codigo = '514120'; --.go
update codigocbo set descricao = 'Zootecnista' where codigo = '223310'; --.go