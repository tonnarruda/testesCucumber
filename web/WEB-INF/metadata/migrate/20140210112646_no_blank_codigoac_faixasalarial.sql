update faixasalarial set codigoac = null where codigoac = ''; --.go

ALTER TABLE faixasalarial ADD CONSTRAINT no_blank_codigoac_faixasalarial CHECK(codigoac <> ''); --.go