CREATE DATABASE IF NOT EXISTS vinoteca;
--
USE vinoteca;
--
CREATE TABLE IF NOT EXISTS bodegas (
    idbodega int auto_increment primary key,
    nombre VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(150),
    denominacion_origen ENUM(
        'D.O. Rioja',
        'D.O. Priorat',
        'D.O. Ribera del Duero',
        'D.O. Somontano',
        'Otra denominación'
    ) NOT NULL
);
--
CREATE TABLE IF NOT EXISTS enologos (
    idenologo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(150) NOT NULL UNIQUE,
    fechanacimiento DATE,
    idbodega INT NOT NULL,
    FOREIGN KEY (idbodega) REFERENCES bodegas(idbodega)
);
--
CREATE TABLE IF NOT EXISTS vinos (
    idvino INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    idenologo INT NOT NULL,
    idbodega INT NOT NULL,
    tipo_vino ENUM('Blanco', 'Tinto', 'Rosado') NOT NULL,
    anio INT NOT NULL,
    FOREIGN KEY (idenologo) REFERENCES enologos(idenologo),
    FOREIGN KEY (idbodega) REFERENCES bodegas(idbodega)
);
--
alter table vinos
add foreign key (idbodega) references bodegas(idbodega),
add foreign key (idenologo) references enologos(idenologo);
--
create function existeNombreVino(f_nombre varchar(40))
returns bit
begin
	declare i int;
	set i=0;
	while (i<(select max(idvino) from vinos)) do
	if ((select nombre from vinos
		 where idvino=(i+1)) like f_nombre)
	then return 1;
	end if;
	set i=i+1;
	end while;
	return 0;
end$$
--
create function existeNombreBodega(f_nombre varchar(50))
returns bit
begin
	declare i int;
	set i=0;
	while (i<(select max(idbodega) from bodegas)) do
	if ((select nombre from bodegas
	     where idbodega = (i+1)) like f_nombre)
	then return 1;
	end if;
	set i=i+1;
	end while;
	return 0;
end$$
--
create FUNCTION existeNombreEnologo(f_nombre varchar(50))
returns bit
begin
	declare i int;
	set i=0;
	while (i<(select max(idenologo) from enologos)) do
	if ((select concat(apellidos,', ',nombre) from enologos
		where idenologo = (i+1)) like f_nombre)
	then return 1;
	end if;
	set i=i+1;
	end while;
	return 0;
end$$
