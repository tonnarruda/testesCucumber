CREATE OR REPLACE FUNCTION carga_horaria_efetiva(id_curso BIGINT, id_turma BIGINT, id_colaboradorturma BIGINT) RETURNS INTERVAL AS $$  
	BEGIN 
		IF exists(select * from diaturma where turma_id = id_turma and horaini is not null and horaini != '' and horafim is not null and horafim != ''
			and ((horaini = '0:00' and horafim != '0:00') or (horaini != '0:00' and horafim = '0:00') or (horaini != '0:00' and horafim != '0:00')))
		THEN
			RETURN (select sum(to_timestamp(horafim, 'HH24:MI') - to_timestamp(horaini, 'HH24:MI')) as horas
			from colaboradorpresenca  cp
			inner join colaboradorturma ct on ct.id = cp.colaboradorturma_id 
			inner join diaturma dt on dt.id = cp.diaturma_id
			where curso_id = id_curso and dt.turma_id = id_turma and presenca = true
			and ct.id = id_colaboradorturma
			group by ct.id 
			order by ct.id);
		ELSE
			RETURN (select (count(cp.id)*((cargahoraria::double precision)/(select count(*) from diaturma where turma_id = id_turma))|| 'm')::interval as horas
			from colaboradorpresenca  cp
			inner join colaboradorturma ct on ct.id = cp.colaboradorturma_id 
			inner join curso cu on cu.id = curso_id
			where curso_id = id_curso and ct.turma_id = id_turma and presenca = true
			and ct.id = id_colaboradorturma
			group by ct.id, cargahoraria);
		END IF;	
	END; 
$$ LANGUAGE plpgsql; --.go