CREATE SCHEMA juegoROL;
USE juegoROL;


CREATE TABLE usuarios (
    nombre VARCHAR(20) PRIMARY KEY NOT NULL,
    numero_habitaciones INT DEFAULT 0,
    numero_personajes INT DEFAULT 0,
    numero_enemigos_matados INT DEFAULT 0,
    numero_objetos_recogidos INT DEFAULT 0
);
