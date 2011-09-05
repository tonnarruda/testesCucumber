CREATE OR REPLACE FUNCTION normalizar(a_string text)
  RETURNS text AS
$BODY$
BEGIN
  RETURN TRANSLATE(a_string, 'áéíóúàèìòùãõâêîôôäëïöüçÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛÄËÏÖÜÇ', 'aeiouaeiouaoaeiooaeioucAEIOUAEIOUAOAEIOOAEIOUC');
END
$BODY$
  LANGUAGE plpgsql;--.go

ALTER FUNCTION normalizar(text) OWNER TO postgres;--.go