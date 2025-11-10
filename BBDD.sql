CREATE TABLE usuarios (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(20) NOT NULL,
    numero_habitaciones INT DEFAULT 0,
    numero_personajes INT DEFAULT 0,
    numero_enemigos_matados INT DEFAULT 0,
    numero_objetos_recogidos INT DEFAULT 0
);
