create table pacientes(
    id bigint not null auto_increment,
    nombre varchar(100) not null,
    email varchar(100) not null unique,
    telefono varchar(20) not null,
    documento varchar(7) not null unique,
    activo tinyint not null,
    calle varchar(100) not null,
    numero varchar(10),
    localidad varchar(100) not null,
    provincia varchar(100) not null,
    cp varchar(10) not null,
    primary key(id)
);