CREATE OR REPLACE FUNCTION dependent_tables(tabela character varying, id bigint) RETURNS SETOF character varying AS
$BODY$
DECLARE
    rec RECORD;
    tabelas VARCHAR[];
    existe BOOLEAN;
BEGIN 
    FOR rec IN
        SELECT
            tc.table_name,
            tc.constraint_name, 
            kcu.column_name 
        FROM information_schema.table_constraints AS tc 
        INNER JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name
        INNER JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name
        WHERE constraint_type = 'FOREIGN KEY' 
        AND ccu.table_name = tabela
    LOOP
        EXECUTE 'SELECT EXISTS (SELECT * FROM ' || rec.table_name::varchar || ' WHERE ' || rec.column_name || ' = ' || id || ')' INTO existe;
        IF existe THEN
            tabelas := tabelas || rec.table_name::varchar;
        END IF;
    END LOOP;

    RETURN QUERY SELECT DISTINCT t FROM UNNEST(tabelas) AS t ORDER BY 1;
END
$BODY$
  LANGUAGE plpgsql;
