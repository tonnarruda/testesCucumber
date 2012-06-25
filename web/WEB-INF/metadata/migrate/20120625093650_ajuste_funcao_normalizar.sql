
CREATE OR REPLACE FUNCTION normalizar(a_string text)
  RETURNS text AS
$BODY$

BEGIN

  RETURN TRANSLATE(a_string, 'áéíóúàèìòùãõâêîôûäëïöüçÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛÄËÏÖÜÇ', 'aeiouaeiouaoaeiouaeioucAEIOUAEIOUAOAEIOUAEIOUC');

END

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION normalizar(text) OWNER TO postgres;--.go