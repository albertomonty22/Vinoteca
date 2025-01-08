CREATE DATABASE IF NOT EXISTS vinoteca;

USE vinoteca;
CREATE TABLE IF NOT EXISTS bodegas (
    idbodega INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
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
CREATE TABLE IF NOT EXISTS enologos (
    idenologo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(150) NOT NULL UNIQUE,
    fechanacimiento DATE,
    idbodega INT NOT NULL,
    FOREIGN KEY (idbodega) REFERENCES bodegas(idbodega)
);
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
