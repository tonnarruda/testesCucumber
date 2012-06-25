CREATE OR REPLACE FUNCTION normalizar(a_string text)
  RETURNS text AS '
BEGIN
  RETURN TRANSLATE(a_string, ''áéíóúàèìòùãõâêîôûäëïöüçÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛÄËÏÖÜÇ'', ''aeiouaeiouaoaeiouaeioucAEIOUAEIOUAOAEIOUAEIOUC'');
END' LANGUAGE plpgsql;--.go

ALTER FUNCTION normalizar(text) OWNER TO postgres;--.go
