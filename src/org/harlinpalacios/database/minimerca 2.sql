drop database if exists DBMiniMercado;

create database DBMiniMErcado;
use DBMiniMercado;

create table Clientes(
	codigoCliente int not null,
	nombreCliente varchar(50) not null,
	apellidoCliente varchar(50) not null,
    NITCliente varchar(10) not null,
	telefonoCliente varchar(8),
	direccionCliente varchar(150),
	correoCliente varchar(45),
	primary key PK_codigoCliente(codigoCliente)
);

create table Proveedores(
	codigoProveedores int not null,
	NITProveedor varchar(10) not null,
	nombreProveedor varchar(50) not null,
	apellidoProveedor varchar(50) not null,
	direccionProveedor varchar(60),
	razonSocial varchar(60),
	contactoPrincipal varchar(100), 
	paginaWeb varchar(50),
	primary key PK_codigoProveedores(codigoProveedores)
);

create table Compras(
	numeroDocumento int not null,
    fechaDocumento date,
    descripcion varchar(100),
    totalDocumento decimal(10,2),
    primary key PK_numedoDocumento(numeroDocumento)
);


create table TipoProducto(
	codigoTipoPro int not null,
	descripcion varchar(100) not null,
	primary key PK_codigoTipoPro(codigoTipoPro)
);


create table CargoEmpleado(
	codigoCargoEm int not null,
    nombreCargo varchar(30) not null,
    descripcionCargo varchar(100) not null,
    primary key PK_codigoCargoEm(codigoCargoEm)
);

create table Empleados(
	codigoEmpleados int not null,
	nombreEmpleado varchar(50) not null,
	apellidoEmpleado varchar(50) not null,
	sueldo decimal(10,2) not null,
	direccion varchar(150) not null,
	turno varchar(50) not null,
    codigoCargoEm int  not null,
	primary key PK_codigoEmpleados(codigoEmpleados),
    Foreign key FK_codigoCargoEm(codigoCargoEm) references CargoEmpleado(codigoCargoEm)
);

create table Facturas(
	codigoFactura int not null,
	estado varchar(50) not null,
	totalFactura decimal(10,2) not null,
	codigoCliente int not null,
	codigoEmpleados int not null,
	primary key PK_codigoFactura(codigoFactura),
    foreign key FK_codigoCliente(codigoCliente) references Clientes(codigoCliente),
	foreign key FK_codigoEmpleados(codigoEmpleados) references Empleados(codigoEmpleados)
);


create table Productos(
	codigoProductos int not null,
	descripcionProducto varchar(100) not null,
	precioUnitario decimal(10,2) not null,
	precioDocena decimal(10,2) not null,
	precioMayor decimal(10,2) not null,
	imagenProducto varchar (25),
	existencia int not null,
	codigoTipoPro int not null,
	codigoProveedores int not null,
	primary key PK_codigoProductos(codigoProductos),
	foreign key FK_codigoTipoPro(codigoTipoPro) references TipoProducto(codigoTipoPro),
	foreign key FK_codigoProveedores(codigoProveedores) references Proveedores(codigoProveedores)
);
/*
create table DetallesCompra(
	codigoDetalles int not null,
	costoUnitario decimal(10,2) not null,
	cantidad int,
    codigoProductos int not null,
	numedoDocumento int not null,
	primary key PK_codigoDetalles(codigoDetalles),
	foreign key FK_codigoProductos(codigoProductos) references Productos(codigoProductos),
	foreign key FK_numedoDocumento(numedoDocumento) references Compras(numedoDocumento)
);



create table DetalleFactura(
	codigoDetalleFac int not null,
	precioUnitario decimal(10,2) not null,
	cantidad int,
    codigoFactura int not null,
	codigoProductos int not null,
	primary key PK_codigoDetalleFac(codigoDetalleFac),
	foreign key FK_codigoFactura(codigoFactura) references Facturas(codigoFactura),
	foreign key FK_codigoProductos(codigoProductos) references Productos(codigoProductos)
);  

create table TelefonoProveedores(
	codigoTelefonoProve int not null,
	numeroPrincipal varchar(8) not null,
	numeroSecundario varchar(8) not null,
	obcervaciones varchar(45) not null,
	codigoProveedores int not null,
	primary key PK_codigoTelefonoProve(codigoTelefonoProve),
	foreign key FK_codigoProveedores(codigoProveedores) references Proveedores(codigoProveedores)
);
*/

  -- /////////////////////////////////////////////////////Procedimiento Almacenado/////////////////////////////////////////////////
  -- ////////////////////////////////////////////////////////////Clientes//////////////////////////////////////////////////////////
  -- /////////////////////////////////////////////////////////AgregarClientes//////////////////////////////////////////////////////

Delimiter $$
	create procedure sp_AgregarClientes (in codigoCliente int, NITcliente varchar(10), in nombreCliente varchar(50),
    in apellidoCliente varchar(50), in direccionCliente varchar(150), in telefonoCliente varchar(8), in correoCliente varchar(45))
    Begin
		Insert into Clientes (codigoCliente, NITcliente ,nombreCliente,
		apellidoCliente, direccionCliente, telefonoCliente, correoCliente) values
        (codigoCliente, NITcliente ,nombreCliente,
		apellidoCliente, direccionCliente,  telefonoCliente, correoCliente);
		
	End$$
Delimiter ;

call sp_AgregarClientes(1,'114006350', 'Harol', 'Luna', 'San Raymundo', '23002626', 'harolLuna@gmail.com');
call sp_AgregarClientes(2,'230635006', 'Oliver', 'Donis', 'Amatitlan', '54872365', 'oliverDonis@gmail.com');
call sp_AgregarClientes(3,'230656876', 'Oli', 'Fernando', 'Quiche', '54823435', 'oliFernando@gmail.com');


-- //////////////////////////////////////////////////////////////////Listar Clientes/////////////////////////////////////////////////
Delimiter $$
	create procedure sp_ListarClientes()
    Begin
		select
        A.codigoCliente,
		A.NITcliente,
		A.nombreCliente,
		A.apellidoCliente,
		A.direccionCliente,
		A.telefonoCliente,
		A.correoCliente
        from Clientes A;
	End $$
Delimiter ;

call sp_ListarClientes();

-- ////////////////////////////////////////////////////////////Buscar Clientes/////////////////////////////////////////////////////////////////
Delimiter $$
	create procedure sp_BuscarClientes(in codCli int)
		begin
			select
            A.codigoCliente,
            A.NITcliente,
            A.nombreCliente,
            A.apellidoCliente,
            A.direccionCliente,
            A.telefonoCliente,
            A.correoCliente
            from Clientes A
            where codigoCliente = codCli;
            End $$
Delimiter $$

call sp_BuscarClientes(2);
        
        
-- ///////////////////////////////////////////////////////////Eliminar Clientes ///////////////////////////////////////////////////////////
Delimiter $$
	create procedure sp_EliminarClientes (in codCli int)
		Begin 
			delete from Clientes
			where codigoCliente = codCli;
    end $$
Delimiter ;
 
call sp_EliminarClientes(1);
call sp_ListarClientes ();


-- ///////////////////////////////////////////////////////////Editar Clientes//////////////////////////////////////////////////////////
Delimiter $$
	Create procedure sp_EditarClientes (in codCli int, in nCliente varchar(10), in nomCliente varchar(50), in apCliente varchar(50),
    in direcCliente varchar(150), in telCliente varchar(8), in corrCliente varchar(45))
		Begin 
			Update Clientes A
				set
			A.codigoCliente = codCli,
			A.NITCliente = nCliente,
			A.nombreCliente = nomCliente,
			A.apellidoCliente = apCliente,
			A.direccionCliente = direcCliente,
			A.telefonoCliente = telCliente,
			A.correoCliente = corrCliente
            where codigoCliente = codCli;
		End $$
Delimiter ;

call sp_EditarClientes(2,'5412322008','Kellio','Delincuente','Acienda Las Flores','54872100','DeliKellio.gt');
call sp_ListarClientes();
    
    
    
-- ////////////////////////////////////////////////////////////Proveedores//////////////////////////////////////////////////////////
-- /////////////////////////////////////////////////////////AgregarProveedores//////////////////////////////////////////////////////
Delimiter $$
	create procedure sp_AgregarProveedores(in codigoProveedores int, in NITProveedor varchar(10), in nombreProveedor varchar(50),
    in apellidoProveedor varchar(50), in direccionProveedor varchar(60), in razonSocial varchar(60), in contactoPrincipal varchar
    (100), in paginaWeb varchar(50))
		Begin 
			 Insert into Proveedores (codigoProveedores, NITProveedor, nombreProveedor, apellidoProveedor, direccionProveedor, 
             razonSocial , contactoPrincipal, paginaWeb )values
             (codigoProveedores, NITProveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial , 
             contactoPrincipal, paginaWeb );
		End $$
Delimiter ;

call sp_AgregarProveedores(1,'124585003','Alexander','Sales','San Pedro','Sony','21549832','AlexanderS_gt');
call sp_AgregarProveedores(2,'124581250','Kellyo','Tasha','Estados Unidos','Bando Estado Unidence','45872100','Tasha.com.gt');
call sp_AgregarProveedores(3,'124582340','Kardasha','Selia','New Olanda','Bando Estado','45823100','Selia.com.gt');

   
-- ****************************************************Listar Proveedores*******************************************
Delimiter $$
	create procedure sp_ListarProveedores()
		Begin 
			select
            B.codigoProveedores,
			B.NITProveedor,
			B.nombreProveedor,
			B.apellidoProveedor,
			B.direccionProveedor,
			B.razonSocial ,
			B.contactoPrincipal, 
			B.paginaWeb
			from Proveedores B;
		End $$
Delimiter ;

call sp_ListarProveedores();

-- ***********************************************Buscar Proveedores**********************************
Delimiter $$
	create procedure sp_BuscarProveedores(in prID int)
		Begin 
			select
			B.codigoProveedores,
			B.NITProveedor,
			B.nombreProveedor,
			B.apellidoProveedor,
			B.direccionProveedor,
			B.razonSocial ,
			B.contactoPrincipal, 
			B.paginaWeb
			from Proveedores B
            where codigoProveedores = prID;
		End $$
Delimiter ;
        
call sp_BuscarProveedores(1);

-- ****************************************************Eliminar Proveedores*******************************************************        
Delimiter $$
	create procedure sp_EliminarProveedores(in prove int)
		Begin 
			delete from Proveedores
				where codigoProveedores = prove;
		End $$
Delimiter ;

call sp_EliminarProveedores(1);
call sp_ListarProveedores();

-- *******************************************************Editar Proveedores*************************************************
Delimiter $$
	create procedure sp_EditarProveedores(in codiProve int, in NProve varchar(10), in nomProve varchar(50),
    in apeProve varchar(50), in direcProve varchar(60), in raSocial varchar(60), in contacPrin varchar
    (100), in pagWeb varchar(50))
		Begin 
			update Proveedores B
				set
			B.codigoProveedores= codiProve,
			B.NITProveedor = NProve,
			B.nombreProveedor = nomProve,
			B.apellidoProveedor = apeProve,
			B.direccionProveedor = direcProve,
			B.razonSocial = raSocial,
			B.contactoPrincipal = contacPrin, 
			B.paginaWeb = pagWeb
            where codigoProveedores = codiProve;
		End $$
Delimiter ;

call sp_EditarProveedores(2,'5412322008','Thomas','Delincuente','Acienda Las Flores','Loco','54872100','DeliThomas.gt');
call sp_ListarProveedores();


-- /////////////////////////////////////////////////////Procedimiento Almacenado////////////////////////////////////////////////////// 
-- *********************************************************Agregar Compras************************************************************
Delimiter $$
	create procedure sp_AgregarCompra(in numeroDocumento int, in fechaDocumento date,in descripcion varchar(100), in totalDocumento decimal(10,2))
	begin
		insert into Compras (numeroDocumento, fechaDocumento, descripcion, totalDocumento) values 
        (numeroDocumento, fechaDocumento, descripcion, totalDocumento);
	End $$
Delimiter ;

call sp_AgregarCompra(1, '2024-04-25', 'hsfghhryhsesh',100.50);
call sp_AgregarCompra(2, '2024-09-1', 'rthadrharhaher',500.00);
call sp_AgregarCompra(3, '2024-03-1', 'athfazdtjshjdjtsr',300.00);

-- *************************************************************Listar Compras*******************************************************
Delimiter $$
	create procedure sp_ListarCompras()
	Begin
		select
        D.numeroDocumento,
        D.fechaDocumento,
        D.descripcion,
        D.totalDocumento
        from Compras D;
	End $$
Delimiter ;

call sp_ListarCompras();

-- **************************************************************Buscar Compras******************************************************
Delimiter $$
	create procedure sp_BuscarCompra(in comID int)
    Begin 
		select
        D.numeroDocumento,
        D.fechaDocumento,
        D.descripcion,
        D.totalDocumento
        from Compras D
        where numeroDocumento = comID;
	End $$
Delimiter ;

call sp_BuscarCompra(2);

-- ************************************************************Eliminar Compra***************************************************
Delimiter $$
create procedure sp_EliminarCompra(in compa int)
    Begin 
		delete from Compras
			where numeroDocumento = compa;
		End $$
Delimiter ;

call sp_EliminarCompra(1);
call sp_ListarCompras();

-- *******************************************************************Editar Compras********************************************
Delimiter $$
	create procedure sp_EditarCompra(in numeroD int, in fechaD date, in descrp varchar(100), in totalD decimal(10,2))
		Begin 
			update Compras D
				set
			D.numeroDocumento = numeroD,
			D.fechaDocumento = fechaD,
			D.descripcion = descrp,
			D.totalDocumento = totalD
            where numeroDocumento = numeroD;
		End $$
Delimiter ;

call sp_EditarCompra(2,'2022-10-6', 'hsfatjaEghwkyjzeerha', 500.50);
call sp_ListarCompras();


-- //////////////////////////////////////////////////////////Procedimiento Almacenado////////////////////////////////////////////////////
-- ***************************************************************Agregar Cargos*********************************************************
Delimiter $$
	create procedure sp_AgregarCargo(in codigoCargoEm  int, in nombreCargo varchar(30), in descripcionCargo varchar(100))
	Begin
		insert into CargoEmpleado (codigoCargoEm, nombreCargo, descripcionCargo)
		values (codigoCargoEm , nombreCargo, descripcionCargo);
	End $$ 
Delimiter ;

call sp_AgregarCargo(1, 'Aguas Gaseosas', 'Agua que te refrescan en dias calurosos');
call sp_AgregarCargo(2, 'Golosinas', 'Compra una golosina, la que mas te guste');
call sp_AgregarCargo(3, 'Chicles', 'Compra tu mejor chicle, el que mas te guste');



-- ******************************************************************Listar Cargos*******************************************************
Delimiter $$
	create procedure sp_ListarCargo()
	Begin
		select
        E.codigoCargoEm,
        E.nombreCargo,
        E.descripcionCargo
        from CargoEmpleado E;
	End $$
Delimiter ;

call sp_ListarCargo();

-- ***********************************************************Buscar Cargos**************************************************
Delimiter $$
	create procedure sp_BuscarCargo(in carID int)
    Begin 
		select
        E.codigoCargoEm,
        E.nombreCargo,
        E.descripcionCargo
        from CargoEmpleado E
        where codigoCargoEm = carID;
	End $$
Delimiter ;

call sp_BuscarCargo(2);

-- ***********************************************************Eliminar Cargos************************************
Delimiter $$
create procedure sp_EliminarCargo(in carID int)
    Begin 
		delete from CargoEmpleado
			where codigoCargoEm = carID;
		End $$
Delimiter ;

call sp_EliminarCargo(2);
call sp_ListarCargo();

-- ************************************************************Editar Cargos**************************************
Delimiter $$
	create procedure sp_EditarCargo(in codiCar int, in nomCar varchar(30), in desCar varchar(100))
		Begin 
			update CargoEmpleado E
				set
			E.codigoCargoEm = codiCar,
			E.nombreCargo = nomCar,
			E.descripcionCargo = desCar
            where codigoCargoEm  = codiCar;
		End $$
Delimiter ;

call sp_EditarCargo(1,'Vino', 'EL mejor vido de Guatemala');
call sp_ListarCargo();


-- ///////////////////////////////////////////////////////Procedimeinto Almacenado/////////////////////////////////////////////////
-- *********************************************************Agregar TipoProducto***************************************************
Delimiter $$
	create procedure sp_AgregarTipoProducto(in codigoTipoPro int, in descripcion varchar(100))
	Begin
		insert into TipoProducto (codigoTipoPro, descripcion)
		values (codigoTipoPro, descripcion);
	End $$ 
Delimiter ;

call sp_AgregarTipoProducto(1, 'w5h24bejkujyngsnrmry');
call sp_AgregarTipoProducto(2, 'strhetwtrnwsnrsethwh');

-- *********************************************************Listar TipoProducto***************************************************
Delimiter $$
	create procedure sp_ListarTipoProducto()
	Begin
		select
        F.codigoTipoPro,
        F.descripcion
        from TipoProducto F;
	End $$
Delimiter ;

call sp_ListarTipoProducto();

-- *********************************************************Buscar TipoProducto***************************************************
Delimiter $$
	create procedure sp_BuscarTipoProducto(in TiProID int)
    Begin 
		select
        F.codigoTipoPro,
        F.descripcion
        from TipoProducto F
        where codigoTipoPro = TiProID;
	End $$
Delimiter ;

call sp_BuscarTipoProducto(1);

-- *********************************************************Eliminar TipoProducto***************************************************
Delimiter $$
create procedure sp_EliminarTipoProducto(in TiProID int)
    Begin 
		delete from TipoProducto
			where codigoTipoPro = TiProID;
		End $$
Delimiter ;

call sp_EliminarTipoProducto(1);
call sp_ListarTipoProducto();
-- *********************************************************Editar TipoProducto*****************************************************
Delimiter $$
	create procedure sp_EditarTipoProducto(in codiTiPro int, in descrip varchar(100))
		Begin 
			update TipoProducto F
				set
			F.codigoTipoPro= codiTiPro,
			F.descripcion = descrip
            where codigoTipoPro = codiTiPro;
		End $$
Delimiter ;

call sp_EditarTipoProducto(2,'Vino');
call sp_ListarTipoProducto();



-- ////////////////////////////////////////////////////////Procedimiento Almacenado//////////////////////////////////////////////////
-- ***********************************************************Agregar Empleados******************************************************
Delimiter $$
		create procedure sp_AgregarEmppleado(in codigoEmpleados int, in nombreEmpleado varchar(50), in apellidoEmpleado varchar(50), in sueldo decimal(10,2)
		, in direccion varchar(150), in turno varchar(15), in codigoCargoEm int)
	Begin
		insert into Empleados (codigoEmpleados,  nombreEmpleado, apellidoEmpleado, sueldo, direccion, turno, codigoCargoEm)
		values (codigoEmpleados,  nombreEmpleado, apellidoEmpleado, sueldo, direccion, turno, codigoCargoEm);
	End $$ 
Delimiter ;

call sp_AgregarEmppleado(1,'Luis','Haernandez',3000.00,'avenida las Americas','Tusrno de Noche',3);
call sp_AgregarEmppleado(2,'Pedro','Haernandez',400.00,'Las Americas','Tusrno de Noche',1);
call sp_AgregarEmppleado(3,'Armando','Ventura',45000.00,'Santo Domindo RD','Tusrno de Noche',1);

-- ***********************************************************Listar Empleados*******************************************************
Delimiter $$
	create procedure sp_ListarEmpleados()
	Begin
		select
        G.codigoEmpleados,
        G.nombreEmpleado,
        G.apellidoEmpleado,
        G.sueldo,
        G.direccion,
        G.turno,
        G.codigoCargoEm
        from Empleados G;
	End $$
Delimiter ;

call sp_ListarEmpleados();

-- ***********************************************************Buscar Empleados*******************************************************
Delimiter $$
	create procedure sp_BuscarEmpleado(in empleID int)
    Begin 
		select
        G.codigoEmpleados,
        G.nombreEmpleado,
        G.apellidoEmpleado,
        G.sueldo,
        G.direccion,
        G.turno,
        G.codigoCargoEm
        from Empleados G
        where Empleado = empleID;
	End $$
Delimiter ;

call sp_BuscarCargo(1);

-- ***********************************************************Eliminar Empleados*****************************************************
Delimiter $$
create procedure sp_EliminarEmpleado(in empleID int)
    Begin 
		delete from Empleados
			where codigoEmpleados = empleID;
		End $$
Delimiter ;

call sp_EliminarEmpleado(1);
call sp_ListarEmpleados();

-- ***********************************************************Editar Empleados*******************************************************
Delimiter $$
	create procedure sp_EditarEmpleado(in codiEmpl int, in nomEmpl varchar(50), in apeEmpl varchar(50), in suel decimal(10,2), in direc varchar(150), in tur varchar(15), in codiCarEm int)
		Begin 
			update Empleados G
				set
			G.codigoEmpleados = codiEmpl,
			G.nombreEmpleado = nomEmpl,
			G.apellidoEmpleado = apeEmpl,
			G.sueldo = suel,
			G.direccion = direc,
			G.turno = tur,
            G.codigoCargoEm = codiCarEm
            where codigoEmpleados  = codiEmpl;
		End $$
Delimiter ;

call sp_EditarEmpleado(2,'Mariano','Valdez',1000.00,'Amatitlan','Tusrno de Dia',1);
call sp_ListarEmpleados();

-- ////////////////////////////////////////////////////////////////////Procedimiento Almacenado De Facturas///////////////////////////////////////////////////////////////
-- ******************************************************************************Agregar Facturas*************************************************************************
Delimiter $$
		create procedure sp_AgregarFacturas(in codigoFactura  int, in estado varchar(50), in totalFactura decimal(10,2), in codigoCliente int, in codigoEmpleados int)
	Begin
		insert into Facturas (codigoFactura,  estado, totalFactura, codigoCliente, codigoEmpleados)
		values (codigoFactura,  estado, totalFactura, codigoCliente, codigoEmpleados);
	End $$ 
Delimiter ;

call sp_AgregarFacturas(1,'Recibida',100.00,2,3);
call sp_AgregarFacturas(2,'Rechazada',1000.20,3,2);
call sp_AgregarFacturas(3,'Recibida',500.00,2,3);


-- ***********************************************************Listar Facturas*******************************************************
Delimiter $$
	create procedure sp_ListarFacturas()
	Begin
		select
        H.codigoFactura,
        H.estado,
        H.totalFactura,
        H.codigoCliente,
        H.codigoEmpleados
        from Facturas H;
	End $$
Delimiter ;

call sp_ListarFacturas();


-- ***********************************************************Buscar Factura*******************************************************
Delimiter $$
	create procedure sp_BuscarFactura(in factuID int)
    Begin 
		select
        H.codigoFactura,
        H.estado,
        H.totalFactura,
        H.codigoCliente,
        H.codigoEmpleados
        from Facturas H
        where codigoFactura = factuID;
	End $$
Delimiter ;

call sp_BuscarFactura(1);

-- ***********************************************************Eliminar Factura*****************************************************
Delimiter $$
create procedure sp_EliminarFactura(in factuID int)
    Begin 
		delete from Facturas
			where codigoFactura = factuID;
		End $$
Delimiter ;

call sp_EliminarFactura(2);
call sp_ListarFacturas();

-- ***********************************************************Editar Factura*******************************************************
Delimiter $$
	create procedure sp_EditarFactura(in codiFactu  int, in esta varchar(50), in totaFact decimal(10,2), in codiClien int, in codiEmplea int)
		Begin 
			update Facturas H
				set
			H.codigoFactura = codiFactu,
			H.estado = esta,
			H.totalFactura = totaFact,
			H.codigoCliente = codiClien,
			H.codigoEmpleados = codiEmplea
            where codigoFactura  = codiFactu;
		End $$
Delimiter ;

call sp_EditarFactura(1,'Rechazada',100.00,3,2);
call sp_ListarFacturas();


-- ////////////////////////////////////////////////////////Procedimiento Almacenado De Facturas//////////////////////////////////////////////////
-- ***********************************************************Agregar Productos******************************************************
Delimiter $$
		create procedure sp_AgregarProductos(in codigoProductos  int, in descripcionProducto varchar(100), in precioUnitario decimal(10,2), in precioDocena decimal(10,2), in precioMayor decimal(10,2), imagenProducto varchar(25), in existencia int, in codigoTipoPro int, in codigoProveedores int)
	Begin
		insert into Productos (codigoProductos,  descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, codigoTipoPro, codigoProveedores)
		values (codigoProductos,  descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, codigoTipoPro, codigoProveedores);
	End $$ 
Delimiter ;

call sp_AgregarProductos(1,'Alimentos',10.00,100.00,1000.00,'drtsfjyj',1,2,3);
call sp_AgregarProductos(2,'Fritos',50.00,200.00,2000.00,'xgmdghkdkd',1,2,2);
call sp_AgregarProductos(3,'Golocinas',80.00,300.00,3000.00,'syukstukeuskys',1,2,3);

-- ***********************************************************Listar Produstos*******************************************************
Delimiter $$
	create procedure sp_ListarProductos()
	Begin
		select
        I.codigoProductos,
        I.descripcionProducto,
        I.precioUnitario,
        I.precioDocena,
        I.precioMayor,
        I.imagenProducto,
        I.existencia,
        I.codigoTipoPro,
        I.codigoProveedores
        from Productos I;
	End $$
Delimiter ;

call sp_ListarProductos();

-- ***********************************************************Buscar Producto********************************************************
Delimiter $$
	create procedure sp_BuscarProducto(in prodID int)
    Begin 
		select
        I.codigoProductos,
        I.descripcionProducto,
        I.precioUnitario,
        I.precioDocena,
        I.precioMayor,
        I.imagenProducto,
        I.existencia,
        I.codigoTipoPro,
        I.codigoProveedores
        from Productos I
        where codigoProductos = prodID;
	End $$
Delimiter ;

call sp_BuscarProducto(1);

-- ***********************************************************Eliminar Producto******************************************************
Delimiter $$
create procedure sp_EliminarProductos(in produID int)
    Begin 
		delete from Productos
			where codigoProductos = produID;
		End $$
Delimiter ;

call sp_EliminarProductos(2);
call sp_ListarProductos();
-- ***********************************************************Editar Producto********************************************************
Delimiter $$
	create procedure sp_EditarProducto(in codigoProdu  int, in descripcionProdu varchar(100), in precioUnita decimal(10,2), in precioDoce decimal(10,2), in precioMay decimal(10,2), imagenProdu varchar(25), in exis int, in codigoTiPro int, in codigoProve int)
		Begin 
			update Productos I
				set
			I.codigoProductos = codigoProdu,
			I.descripcionProducto = descripcionProdu,
			I.precioUnitario = precioUnita,
			I.precioDocena = precioDoce,
			I.precioMayor = precioMay,
			I.imagenProducto = imagenProdu,
			I.existencia = exis,
			I.codigoTipoPro = codigoTiPro,
			I.codigoProveedores = codigoProve
            where codigoProductos  = codigoProdu;
		End $$
Delimiter ;

call sp_EditarProducto(1,'Fritos',20.00,300.00,5000.00,'drtsfjyj',1,2,3);
call sp_ListarProductos();
