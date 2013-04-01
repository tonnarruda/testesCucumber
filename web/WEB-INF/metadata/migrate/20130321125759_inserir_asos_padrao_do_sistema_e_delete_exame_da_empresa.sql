alter table exame add column aso boolean default false; --.go

DO $$
DECLARE
    mviews1 RECORD;
    mviews2 RECORD;
    mviews3 RECORD;
    mviews4 RECORD;
    exameId1 INTEGER;
    exameId2 INTEGER;
    RealizacaoExameId INTEGER;
BEGIN
	exameId1 := nextval('exame_sequence');
	insert into exame (id, nome, periodicidade, empresa_id, periodico, aso) values (exameId1, 'Avaliação Clínica e Anaminese Ocupacional', 12, null, true, true);	
	exameId2 := nextval('exame_sequence');
	insert into exame (id, nome, periodicidade, empresa_id, periodico, aso) values (exameId2, 'Exame de Aptidões Física e Mental na Movimentação', 12, null, true, true);

    FOR mviews1 IN
		select exame_id from empresa where exame_id is not null
	LOOP
		update examesolicitacaoexame set exame_id = exameId1 where exame_id = mviews1.exame_id;
		FOR mviews2 IN
			select id, exame_id, solicitacaoexame_id, clinicaautorizada_id, realizacaoexame_id, periodicidade from examesolicitacaoexame where exame_id = exameId1
		LOOP
			IF  mviews2.realizacaoexame_id is not null THEN
				RealizacaoExameId := nextval('realizacaoexame_sequence');
				insert into RealizacaoExame (id, data, observacao, resultado) select RealizacaoExameId, data, observacao, resultado from RealizacaoExame where id = mviews2.realizacaoexame_id;   
				insert into examesolicitacaoexame (id, exame_id, solicitacaoexame_id, clinicaautorizada_id, realizacaoexame_id, periodicidade) values (nextval('examesolicitacaoexame_sequence'), exameId2, mviews2.solicitacaoexame_id, mviews2.clinicaautorizada_id, RealizacaoExameId, mviews2.periodicidade);
			ELSE
				insert into examesolicitacaoexame (id, exame_id, solicitacaoexame_id, clinicaautorizada_id, realizacaoexame_id, periodicidade) values (nextval('examesolicitacaoexame_sequence'), exameId2, mviews2.solicitacaoexame_id, mviews2.clinicaautorizada_id, null, mviews2.periodicidade);
			END IF;
		END LOOP;
		
		update historicofuncao_exame set exames_id = exameId1 where exames_id = mviews1.exame_id;
		FOR mviews3 IN
			select historicofuncao_id, exames_id from historicofuncao_exame where exames_id = exameId1
		LOOP
			insert into historicofuncao_exame (historicofuncao_id, exames_id ) values (mviews3.historicofuncao_id, exameId2);
		END LOOP;
	
		update clinicaautorizada_exame set exames_id = exameId1 where exames_id = mviews1.exame_id;
		FOR mviews4 IN
			select clinicaautorizada_id, exames_id from clinicaautorizada_exame where exames_id = exameId1
		LOOP
			insert into clinicaautorizada_exame (clinicaautorizada_id, exames_id ) values (mviews4.clinicaautorizada_id, exameId2);
		END LOOP;

	END LOOP;
END$$ LANGUAGE plpgsql;--.go

alter table empresa drop column exame_id;--.go 