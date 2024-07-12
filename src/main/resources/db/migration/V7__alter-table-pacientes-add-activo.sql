ALTER TABLE pacientes ADD activo tinyint not null;

UPDATE pacientes SET activo = 1;