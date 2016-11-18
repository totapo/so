drop database Telit;
create database Telit;

use Telit;

create table Localizacao (
	id integer primary key not null auto_increment,
    nome varchar(50)
);

create table Boia (
	id varchar(17) primary key not null,
    id_localizacao integer not null,
    x integer not null,
    y integer not null,
    z integer not null,
    ultima_manut long not null,
    foreign key(id_localizacao) references Localizacao(id)
);

create table TipoSensor(
	id integer primary key not null auto_increment,
    descricao varchar(50) not null
);

create table Sensor(
	id integer primary key not null auto_increment,
    id_boia varchar(17) not null,
    id_tipo integer not null,
    foreign key (id_boia) references Boia(id),
    foreign key (id_tipo) references TipoSensor(id)
);

create table Medicao(
	id_boia varchar(17) not null,
    id_sensor integer not null,
    valor float not null,
    horario long not null,
    foreign key(id_boia) references Boia(id),
    foreign key(id_sensor) references Sensor(id)
);

ALTER TABLE TipoSensor AUTO_INCREMENT = 1;

