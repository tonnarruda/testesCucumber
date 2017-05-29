
CREATE OR REPLACE FUNCTION setarTodasAsDatasAdmissaoColaborador() RETURNS integer AS $$ 
DECLARE 
mv RECORD; 
BEGIN 
FOR mv IN 
    SELECT hc.data as dataAdmissao, c.id from colaborador c 
    inner join historicocolaborador hc on c.id=hc.colaborador_id 
    where c.dataadmissao is null 
    and hc.data = (select min(hc2.data) from historicocolaborador hc2 where hc2.colaborador_id = c.id) 
LOOP       
    update colaborador set dataadmissao = mv.dataAdmissao where id= mv.id;  
END LOOP;   

RETURN 1;  
END; 
$$ LANGUAGE plpgsql;--.go  

select setarTodasAsDatasAdmissaoColaborador();--.go

ALTER TABLE colaborador ALTER COLUMN dataAdmissao SET NOT NULL;--.go