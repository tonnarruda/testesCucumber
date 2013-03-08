update indice set codigoac = null where codigoac = ''; --.go
update indice set grupoac = null where grupoac = ''; --.go

ALTER TABLE indice ADD CONSTRAINT no_blank_codigoac_indice CHECK(codigoac <> ''); --.go
ALTER TABLE indice ADD CONSTRAINT no_blank_grupoac_indice CHECK(grupoac <> ''); --.go
ALTER TABLE indice ADD CONSTRAINT unique_codigoac_grupoac_indice UNIQUE(codigoac,grupoac); --.go
