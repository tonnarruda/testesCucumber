update areaorganizacional set codigoac = null where trim(codigoac) = ''; --.go

ALTER TABLE areaorganizacional ADD CONSTRAINT no_blank_codigoac_areaorganizacional CHECK(trim(codigoac) <> ''); --.go
ALTER TABLE areaorganizacional ADD CONSTRAINT unique_codigoac_areaorganizacional UNIQUE(codigoac,empresa_id); --.go