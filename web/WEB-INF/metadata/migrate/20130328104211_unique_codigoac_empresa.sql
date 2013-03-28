update empresa set codigoac = null where codigoac = ''; --.go
update empresa set grupoac = null where grupoac = ''; --.go

ALTER TABLE empresa ADD CONSTRAINT no_blank_codigoac_empresa CHECK(codigoac <> ''); --.go
ALTER TABLE empresa ADD CONSTRAINT no_blank_grupoac_empresa CHECK(grupoac <> ''); --.go
ALTER TABLE empresa ADD CONSTRAINT unique_codigoac_grupoac_empresa UNIQUE(codigoac,grupoac); --.go