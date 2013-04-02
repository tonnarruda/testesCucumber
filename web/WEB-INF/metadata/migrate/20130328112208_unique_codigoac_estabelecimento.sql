update estabelecimento set codigoac = null where codigoac = ''; --.go

ALTER TABLE estabelecimento ADD CONSTRAINT no_blank_codigoac_estabelecimento CHECK(codigoac <> ''); --.go
ALTER TABLE estabelecimento ADD CONSTRAINT unique_codigoac_empresa_estabelecimento UNIQUE(codigoac,empresa_id); --.go