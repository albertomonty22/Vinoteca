DELIMITER ||
CREATE FUNCTION existeNombreBodega(f_nombre VARCHAR(50))
RETURNS BIT
BEGIN
    DECLARE i INT;
    SET i = 0;
    WHILE (i < (SELECT MAX(idbodega) FROM bodegas)) DO
        IF ((SELECT nombre FROM bodegas WHERE idbodega = (i + 1)) LIKE f_nombre) THEN
            RETURN 1;
        END IF;
        SET i = i + 1;
    END WHILE;
    RETURN 0;
END ||
DELIMITER ;

DELIMITER ||
CREATE FUNCTION existeNombreEnologo(f_nombre VARCHAR(50), f_apellidos VARCHAR(150))
RETURNS BIT
BEGIN
    DECLARE i INT;
    SET i = 0;
    WHILE (i < (SELECT MAX(idenologo) FROM enologos)) DO
        IF ((SELECT CONCAT(nombre, ' ', apellidos) FROM enologos
             WHERE idenologo = (i + 1)) LIKE CONCAT(f_nombre, ' ', f_apellidos)) THEN
            RETURN 1;
        END IF;
        SET i = i + 1;
    END WHILE;
    RETURN 0;
END ||
DELIMITER ;

DELIMITER ||
CREATE FUNCTION existeNombreVino(f_nombre VARCHAR(50))
RETURNS BIT
BEGIN
    DECLARE i INT;
    SET i = 0;
    WHILE (i < (SELECT MAX(idvino) FROM vinos)) DO
        IF ((SELECT nombre FROM vinos WHERE idvino = (i + 1)) LIKE f_nombre) THEN
            RETURN 1;
        END IF;
        SET i = i + 1;
    END WHILE;
    RETURN 0;
END ||
DELIMITER ;
