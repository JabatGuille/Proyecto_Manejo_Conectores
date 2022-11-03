Create table Cliente(
DNI varchar(50) primary key,
Nombre varchar(50),
Ciudad varchar(50),
Edad integer,
Profesion varchar(50),
estado varchar(50)
);

Create table Lugar(
ID integer primary key,
Lugar varchar(50),
Nacionalidad varchar(50)
);

Create table Empleado(
DNI varchar(50) primary key,
Nombre varchar(50),
Apellido varchar(50),
Fecha_nac varchar(50),
Fecha_cont varchar(50),
Nacionalidad varchar(50),
Cargo varchar(50),
estado varchar(50)
);

Create table VisitaGuiada(
N_visita integer primary key,
Nombre varchar(50),
N_max_cli integer,
Punto_partida varchar(50),
Curso varchar(50),
tematica varchar(50),
coste double,
estado varchar(50),
DNI_empleado varchar(50),
id_lugar integer,
foreign key (DNI_empleado) references empleado(DNI),
foreign key (id_lugar) references lugar(ID)
);

Create table Usuario_Visita(
id integer primary key,
DNI_cliente varchar(50),
N_visita integer,
foreign key (DNI_cliente) references cliente(DNI),
foreign key (N_visita) references visitaguiada(N_visita)
);