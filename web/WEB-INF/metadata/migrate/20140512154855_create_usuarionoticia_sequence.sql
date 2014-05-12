CREATE FUNCTION criar_usuarionoticia_sequence() RETURNS integer AS $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_class where relname = 'usuarionoticia_sequence')
    THEN
        CREATE SEQUENCE usuarionoticia_sequence;
    END IF;

    RETURN 1;
END;
$$ LANGUAGE plpgsql;--.go

SELECT criar_usuarionoticia_sequence();--.go
DROP FUNCTION criar_usuarionoticia_sequence();--.go

SELECT setval('usuarionoticia_sequence', (SELECT MAX(id) FROM usuarionoticia));--.go