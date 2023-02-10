CREATE TABLE roles
(id SERIAL PRIMARY KEY,
nombre varchar(50) NOT NULL,
estado varchar(12) NOT NULL
);

CREATE TABLE usuarios
(id SERIAL PRIMARY KEY,
idrole int DEFAULT NULL,
foreign key(idrole) references roles(id),
nombres varchar(100) DEFAULT NULL,
usuario varchar(20) NOT NULL,
clave varchar(100) NOT NULL,
token varchar(50) DEFAULT NULL,
correo varchar(100) DEFAULT NULL,
estado varchar(12) NOT NULL
);

CREATE TABLE categorias
(id SERIAL PRIMARY KEY,
descripcion varchar(100) not null,
estado varchar(12) not null
);

CREATE TABLE laboratorios
(id SERIAL PRIMARY KEY,
descripcion varchar(100) NOT NULL,
estado varchar(12) NOT NULL
);

CREATE TABLE marcas
(id SERIAL PRIMARY KEY,
descripcion varchar(100) NOT NULL,
estado varchar(12) NOT NULL
);

CREATE TABLE undmedida
(id SERIAL PRIMARY KEY,
descripcion varchar(50) NOT NULL
);

CREATE TABLE productos
(id SERIAL PRIMARY KEY,
idlaboratorio int NOT NULL,
idcategoria int NOT NULL,
idmarca int NOT NULL,
idunidad int NOT NULL,
foreign key(idlaboratorio) references laboratorios(id),
foreign key(idcategoria) references categorias(id),
foreign key(idmarca) references marcas(id),
foreign key(idunidad) references undmedida(id),
descripcion varchar(150) NOT NULL,
stock_minimo int DEFAULT NULL,
stock_maximo int NOT NULL,
stock int DEFAULT NULL,
fecha_vto date DEFAULT NULL,
precio_compra decimal(12,2) DEFAULT NULL,
utilidad decimal(12,2) DEFAULT NULL,
precio_venta decimal(12,2) DEFAULT NULL,
estado varchar(12) NOT NULL
);

CREATE TABLE tipomovimiento
(id SERIAL PRIMARY KEY,
descripcion varchar(50) NOT NULL
);

CREATE TABLE movimientos
(id SERIAL PRIMARY KEY,
idtipo int NOT NULL,
idusuario int NOT NULL,
fecha_registro timestamp NOT NULL,
fecha_anulado timestamp DEFAULT NULL,
serie varchar(4) NOT NULL,
numero varchar(8) NOT NULL,
observaciones varchar(500) DEFAULT NULL,
motivo_anulado text,
estado varchar(12) NOT NULL
);

CREATE TABLE detallemovimiento
(id SERIAL PRIMARY KEY,
idmovimiento INT NOT NULL,
idproducto INT NOT NULL,
cantidad INT NOT NULL
);

/* 
			PROCEDIMIENTOS ALMACENADOS
			
LOGIN
*/
CREATE OR REPLACE FUNCTION func_login
(p_usuario VARCHAR(20),
p_clave VARCHAR(100)
)
RETURNS TABLE(
	id integer, idrole integer, nombres VARCHAR(100), usuario VARCHAR(100),
	clave VARCHAR(100), token VARCHAR(50), correo VARCHAR(100), estado VARCHAR(12), role VARCHAR(50))
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT 
			u.*, r.nombre as role
		FROM usuarios u
			INNER JOIN roles r on r.id=u.idrole
		WHERE u.usuario=p_usuario AND u.clave=p_clave AND u.estado='ACTIVO';
END
$BODY$;

/* ROLES */
CREATE OR REPLACE FUNCTION func_listar_roles()
RETURNS SETOF roles
LANGUAGE 'plpgsql'
AS $BODY$
	BEGIN
		RETURN QUERY
			SELECT * FROM roles;
    END;
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_role
(p_id INT
)
RETURNS SETOF roles
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT * FROM roles WHERE id=p_id;
END
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_role_nombre
(p_nombre VARCHAR(100)
)
RETURNS SETOF roles
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT * FROM roles WHERE nombre LIKE '%'||p_nombre||'%';
END
$BODY$;

CREATE OR REPLACE PROCEDURE sp_registrar_role
(p_nombre varchar(50),
p_estado varchar(12),
OUT msge varchar(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
 	IF(EXISTS(SELECT * FROM roles WHERE nombre=p_nombre))THEN
 		msge:=  'El rol: '||p_nombre||', ya existe';
	ELSE
		INSERT INTO roles (nombre,estado) VALUES(p_nombre, p_estado);
		msge:=  'Registrado correctamente';
	END IF;
END
$BODY$;

CREATE OR REPLACE PROCEDURE sp_actualizar_role
(p_id INT,
p_nombre varchar(50),
p_estado varchar(12),
OUT msge varchar(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
 	IF(EXISTS(SELECT * FROM roles WHERE nombre=p_nombre AND id<>p_id))THEN
 		msge:=  'El rol: '||p_nombre||', ya existe';
	ELSE
		UPDATE roles SET nombre=p_nombre,estado=p_estado WHERE id=p_id;
		msge:=  'Actualizado correctamente';
	END IF;
END
$BODY$;

/* USUARIOS */
CREATE OR REPLACE FUNCTION func_listar_usuarios()
RETURNS TABLE(
	id integer, 
	idrole integer, 
	nombres VARCHAR(100), 
	usuario VARCHAR(100),
	clave VARCHAR(100), 
	token VARCHAR(50), 
	correo VARCHAR(100), 
	estado VARCHAR(12), 
	role VARCHAR(50)
)
LANGUAGE 'plpgsql'
AS $BODY$
	BEGIN
		RETURN QUERY
			SELECT
				u.*, r.nombre AS role
			FROM usuarios u
				INNER JOIN roles r ON r.id=u.idrole;
    END;
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_usuario_id
(p_id INT
)
RETURNS TABLE(
	id integer, 
	idrole integer, 
	nombres VARCHAR(100), 
	usuario VARCHAR(100),
	clave VARCHAR(100), 
	token VARCHAR(50), 
	correo VARCHAR(100), 
	estado VARCHAR(12), 
	role VARCHAR(50)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT
			u.*, r.nombre AS role
		FROM usuarios u
			INNER JOIN roles r ON r.id=u.idrole
		WHERE u.id=p_id;
END
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_usuario_nombre
(p_nombre VARCHAR(50)
)
RETURNS SETOF usuarios
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT * FROM usuarios WHERE nombres LIKE '%'||p_nombre||'%';
END
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_usuario_email
(p_correo VARCHAR(100)
)
RETURNS SETOF usuarios
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT * FROM usuarios WHERE correo=p_correo AND estado='ACTIVO' LIMIT 1;
END
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_usuario_token
(p_token VARCHAR(50)
)
RETURNS SETOF usuarios
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT * FROM usuarios WHERE token=p_token AND estado='ACTIVO' LIMIT 1;
END
$BODY$;

CREATE OR REPLACE PROCEDURE sp_registrar_usuario
(p_idrole INT, 
p_nombres VARCHAR(100), 
p_usuario VARCHAR(20), 
p_clave VARCHAR(100),
p_correo VARCHAR(100),
p_estado VARCHAR(12),
OUT msge varchar(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
 	IF(EXISTS(SELECT * FROM usuarios WHERE usuario=p_usuario))THEN
 		msge:=  'El usuario: '||p_usuario||', ya existe';
	ELSEIF(p_correo IS NULL)THEN
		INSERT INTO usuarios (idrole,nombres,usuario,clave,correo,estado) VALUES(p_idrole,p_nombres,p_usuario,p_clave,p_correo,p_estado);
		msge:=  'Registrado correctamente';
	ELSEIF(p_correo IS NOT NULL)THEN
		IF(EXISTS(SELECT * FROM usuarios WHERE correo=p_correo))THEN
			msge:=  'El correo: '||p_correo||', ya existe';
		ELSE
			INSERT INTO usuarios (idrole,nombres,usuario,clave,correo,estado) VALUES(p_idrole,p_nombres,p_usuario,p_clave,p_correo,p_estado);
			msge:=  'Registrado correctamente';
		END IF;
	END IF;
END
$BODY$;

CREATE OR REPLACE PROCEDURE sp_actualizar_usuario
(p_id INT,
p_idrole INT, 
p_nombres VARCHAR(100), 
p_usuario VARCHAR(20), 
p_clave VARCHAR(100),
p_correo VARCHAR(100),
p_estado VARCHAR(12),
OUT msge varchar(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
 	IF(EXISTS(SELECT * FROM usuarios WHERE usuario=p_usuario AND id<>p_id))THEN
 		msge:=  'El usuario: '||p_descripcion||', ya existe';
	ELSE
		UPDATE usuarios SET idrole=p_idrole,nombres=p_nombres,usuario=p_usuario,clave=p_clave,correo=p_correo,estado=p_estado WHERE id=p_id;
		msge:=  'Actualizado correctamente';
	END IF;
END
$BODY$;

CREATE OR REPLACE PROCEDURE sp_actualizar_usuario_token
(p_id INT, 
p_token VARCHAR(50),
OUT msge varchar(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	IF(p_token = '')THEN
		p_token := NULL;
	END IF;
 	IF(EXISTS(SELECT * FROM usuarios WHERE id=p_id))THEN
 		msge:=  'El usuario no existe';
	ELSE
		UPDATE usuarios SET token=p_token WHERE id=p_id;
		msge:=  'Actualizado correctamente';
	END IF;
END
$BODY$;

CREATE OR REPLACE PROCEDURE sp_actualizar_usuario_clave
(p_id INT, 
p_password VARCHAR(100),
OUT msge VARCHAR(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
 	IF(EXISTS(SELECT * FROM usuarios WHERE id=p_id))THEN
 		msge:=  'El usuario no existe';
	ELSE
		UPDATE usuarios SET password=p_password,token=NULL WHERE id=p_id;
		msge:=  'Actualizado correctamente';
	END IF;
END
$BODY$;

/* CATEGORIAS */
CREATE OR REPLACE FUNCTION func_listar_categorias()
RETURNS SETOF categorias
LANGUAGE 'plpgsql'
AS $BODY$
	BEGIN
		RETURN QUERY
			SELECT * FROM categorias;
    END;
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_categoria
(p_id INT
)
RETURNS SETOF categorias
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT * FROM categorias WHERE id=p_id;
END
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_categoria_nombre
(p_descripcion VARCHAR(100)
)
RETURNS SETOF categorias
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT * FROM categorias WHERE descripcion LIKE '%'||p_descripcion||'%';
END
$BODY$;

CREATE OR REPLACE PROCEDURE sp_registrar_categoria
(p_descripcion varchar(100),
p_estado varchar(12),
OUT msge varchar(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
 	IF(EXISTS(SELECT * FROM categorias WHERE descripcion=p_descripcion))THEN
 		msge:=  'La categoria: '||p_descripcion||', ya existe';
	ELSE
		INSERT INTO categorias (descripcion,estado) VALUES(p_descripcion, p_estado);
		msge:=  'Registrado correctamente';
	END IF;
END
$BODY$;

CREATE OR REPLACE PROCEDURE sp_actualizar_categoria
(p_id INT,
p_descripcion varchar(100),
p_estado varchar(12),
OUT msge varchar(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
 	IF(EXISTS(SELECT * FROM categorias WHERE descripcion=p_descripcion AND id<>p_id))THEN
 		msge:=  'La categoria: '||p_descripcion||', ya existe';
	ELSE
		UPDATE categorias SET descripcion=p_descripcion,estado=p_estado WHERE id=p_id;
		msge:=  'Actualizado correctamente';
	END IF;
END
$BODY$;

/* LABORATORIOS */
CREATE OR REPLACE FUNCTION func_listar_laboratorios()
RETURNS SETOF laboratorios
LANGUAGE 'plpgsql'
AS $BODY$
	BEGIN
		RETURN QUERY
			SELECT * FROM laboratorios;
    END;
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_laboratorio
(p_id INT
)
RETURNS SETOF laboratorios
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT * FROM laboratorios WHERE id=p_id;
END
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_laboratorio_nombre
(p_descripcion VARCHAR(100)
)
RETURNS SETOF laboratorios
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT * FROM laboratorios WHERE descripcion LIKE '%'||p_descripcion||'%';
END
$BODY$;

CREATE OR REPLACE PROCEDURE sp_registrar_laboratorio
(p_descripcion varchar(100),
p_estado varchar(12),
OUT msge varchar(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
 	IF(EXISTS(SELECT * FROM laboratorios WHERE descripcion=p_descripcion))THEN
 		msge:=  'El laboratorio: '||p_descripcion||', ya existe';
	ELSE
		INSERT INTO laboratorios (descripcion,estado) VALUES(p_descripcion, p_estado);
		msge:=  'Registrado correctamente';
	END IF;
END
$BODY$;

CREATE OR REPLACE PROCEDURE sp_actualizar_laboratorio
(p_id INT,
p_descripcion varchar(100),
p_estado varchar(12),
OUT msge varchar(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
 	IF(EXISTS(SELECT * FROM laboratorios WHERE descripcion=p_descripcion AND id<>p_id))THEN
 		msge:=  'El laboratorio: '||p_descripcion||', ya existe';
	ELSE
		UPDATE laboratorios SET descripcion=p_descripcion,estado=p_estado WHERE id=p_id;
		msge:=  'Actualizado correctamente';
	END IF;
END
$BODY$;

/* MARCAS */
CREATE OR REPLACE FUNCTION func_listar_marcas()
RETURNS SETOF marcas
LANGUAGE 'plpgsql'
AS $BODY$
	BEGIN
		RETURN QUERY
			SELECT * FROM marcas;
    END;
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_marca
(p_id INT
)
RETURNS SETOF marcas
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT * FROM marcas WHERE id=p_id;
END
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_marca_nombre
(p_descripcion VARCHAR(100)
)
RETURNS SETOF roles
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT * FROM marcas WHERE descripcion LIKE '%'||p_descripcion||'%';
END
$BODY$;

CREATE OR REPLACE PROCEDURE sp_registrar_marca
(p_descripcion varchar(100),
p_estado varchar(12),
OUT msge varchar(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
 	IF(EXISTS(SELECT * FROM marcas WHERE descripcion=p_descripcion))THEN
 		msge:=  'La marca: '||p_descripcion||', ya existe';
	ELSE
		INSERT INTO marcas (descripcion,estado) VALUES(p_descripcion, p_estado);
		msge:=  'Registrado correctamente';
	END IF;
END
$BODY$;

CREATE OR REPLACE PROCEDURE sp_actualizar_marca
(p_id INT,
p_nombre varchar(50),
p_estado varchar(12),
OUT msge varchar(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
 	IF(EXISTS(SELECT * FROM marcas WHERE descripcion=p_descripcion AND id<>p_id))THEN
 		msge:=  'La marca: '||p_nombre||', ya existe';
	ELSE
		UPDATE marcas SET descripcion=p_descripcion,estado=p_estado WHERE id=p_id;
		msge:=  'Actualizado correctamente';
	END IF;
END
$BODY$;

/* UNIDADES DE MEDIDA */
CREATE OR REPLACE FUNCTION func_listar_unidadesmedida()
RETURNS SETOF undmedida
LANGUAGE 'plpgsql'
AS $BODY$
	BEGIN
		RETURN QUERY
			SELECT * FROM undmedida;
    END;
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_unidadmedida
(p_id INT
)
RETURNS SETOF undmedida
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT * FROM undmedida WHERE id=p_id;
END
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_unidadmedida_nombre
(p_descripcion VARCHAR(50)
)
RETURNS SETOF undmedida
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT * FROM undmedida WHERE descripcion LIKE '%'||p_descripcion||'%';
END
$BODY$;

CREATE OR REPLACE PROCEDURE sp_registrar_unidadmedida
(p_descripcion varchar(50),
OUT msge varchar(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
 	IF(EXISTS(SELECT * FROM undmedida WHERE descripcion=p_descripcion))THEN
 		msge:=  'La unidad de medida: '||p_descripcion||', ya existe';
	ELSE
		INSERT INTO undmedida (descripcion) VALUES(p_descripcion);
		msge:=  'Registrado correctamente';
	END IF;
END
$BODY$;

CREATE OR REPLACE PROCEDURE sp_actualizar_unidadmedida
(p_id INT,
p_descripcion varchar(50),
OUT msge varchar(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
 	IF(EXISTS(SELECT * FROM undmedida WHERE descripcion=p_descripcion AND id<>p_id))THEN
 		msge:=  'La unidad de medida: '||p_descripcion||', ya existe';
	ELSE
		UPDATE undmedida SET descripcion=p_descripcion WHERE id=p_id;
		msge:=  'Actualizado correctamente';
	END IF;
END
$BODY$;

/* PRODUCTOS */
CREATE OR REPLACE FUNCTION func_listar_productos()
RETURNS TABLE(
	id integer, 
	laboratorio VARCHAR(100),
	categoria VARCHAR(100), 
	marca VARCHAR(100),
	undmedida VARCHAR(50), 
	descripcion VARCHAR(100), 
	stock_minimo INT,
	stock INT,
	precio_compra DECIMAL(12, 2),
	utilidad DECIMAL(12, 2),
	precio_venta DECIMAL(12, 2),
	estado VARCHAR(12), 
	fecha_vto TEXT
)
LANGUAGE 'plpgsql'
AS $BODY$
	BEGIN
		RETURN QUERY
			SELECT
				p.id,l.descripcion AS laboratorio,c.descripcion AS categoria,m.descripcion AS marca,um.descripcion AS undmedida,
				p.descripcion,p.stock_minimo,p.stock,p.precio_compra,p.utilidad,p.precio_venta,p.estado,
				CASE WHEN p.fecha_vto IS NULL THEN '' ELSE to_char(p.fecha_vto,'dd/mm/YYYY') END AS fecha_vto
			FROM productos p
				INNER JOIN laboratorios l ON l.id=p.idlaboratorio
				INNER JOIN categorias c on c.id=p.idcategoria
				inner join marcas m on m.id=p.idmarca
				inner join undmedida um on um.id=p.idunidad;
    END;
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_producto
(p_id INT
)
RETURNS SETOF productos
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
	RETURN QUERY
		SELECT * FROM productos WHERE id=p_id;
END
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_producto_nombre
(p_nombre VARCHAR(100)
)
RETURNS TABLE(
	id integer, 
	laboratorio VARCHAR(100),
	categoria VARCHAR(100), 
	marca VARCHAR(100),
	undmedida VARCHAR(50), 
	descripcion VARCHAR(100), 
	stock_minimo INT,
	stock INT,
	precio_compra DECIMAL(12, 2),
	utilidad DECIMAL(12, 2),
	precio_venta DECIMAL(12, 2),
	estado VARCHAR(12), 
	fecha_vto TEXT
)
LANGUAGE 'plpgsql'
AS $BODY$
	BEGIN
		RETURN QUERY
			SELECT
				p.id,l.descripcion AS laboratorio,c.descripcion AS categoria,m.descripcion AS marca,um.descripcion AS undmedida,
				p.descripcion,p.stock_minimo,p.stock,p.precio_compra,p.utilidad,p.precio_venta,p.estado,
				CASE WHEN p.fecha_vto IS NULL THEN '' ELSE to_char(p.fecha_vto,'dd/mm/YYYY') END AS fecha_vto
			FROM productos p
				INNER JOIN laboratorios l ON l.id=p.idlaboratorio
				INNER JOIN categorias c on c.id=p.idcategoria
				inner join marcas m on m.id=p.idmarca
				inner join undmedida um on um.id=p.idunidad
			WHERE p.descripcion LIKE '%'||p_nombre||'%' 
				OR l.descripcion LIKE '%'||p_nombre||'%' 
				OR c.descripcion LIKE '%'||p_nombre||'%' 
				OR m.descripcion LIKE '%'||p_nombre||'%' 
				OR p.estado LIKE '%'||p_nombre||'%';
    END;
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_producto_carrito
(p_id INT
)
RETURNS TABLE(
	id integer, 
	laboratorio VARCHAR(100),
	categoria VARCHAR(100), 
	marca VARCHAR(100),
	undmedida VARCHAR(50), 
	descripcion VARCHAR(100), 
	stock_minimo INT,
	stock INT,
	precio_compra DECIMAL(12, 2),
	utilidad DECIMAL(12, 2),
	precio_venta DECIMAL(12, 2),
	estado VARCHAR(12), 
	fecha_vto TEXT
)
LANGUAGE 'plpgsql'
AS $BODY$
	BEGIN
		RETURN QUERY
			SELECT
				p.id,l.descripcion AS laboratorio,c.descripcion AS categoria,m.descripcion AS marca,um.descripcion AS undmedida,
				p.descripcion,p.stock_minimo,p.stock,p.precio_compra,p.utilidad,p.precio_venta,p.estado,
				CASE WHEN p.fecha_vto IS NULL THEN '' ELSE to_char(p.fecha_vto,'dd/mm/YYYY') END AS fecha_vto
			FROM productos p
				INNER JOIN laboratorios l ON l.id=p.idlaboratorio
				INNER JOIN categorias c on c.id=p.idcategoria
				inner join marcas m on m.id=p.idmarca
				inner join undmedida um on um.id=p.idunidad
			WHERE p.id=p_id
			LIMIT 1;
    END;
$BODY$;

CREATE OR REPLACE PROCEDURE sp_registrar_producto
(p_idlaboratorio INT,
p_idcategoria INT,
p_idmarca INT,
p_idunidad INT,
p_descripcion varchar(150),
p_stock_minimo INT,
p_stock_maximo INT,
p_stock INT,
p_fecha_vto DATE,
p_precio_compra DECIMAL(12,2),
p_utilidad DECIMAL(12,2),
p_precio_venta DECIMAL(12,2),
p_estado VARCHAR(12),
OUT msge VARCHAR(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
 	IF(EXISTS(SELECT * FROM productos WHERE descripcion=p_descripcion))THEN
 		msge:=  'El producto: '||p_descripcion||', ya existe';
	ELSE
		INSERT INTO productos (idlaboratorio,idcategoria,idmarca,idunidad,descripcion,stock_minimo,stock_maximo,stock,fecha_vto,precio_compra,utilidad,precio_venta,estado) 
			VALUES(p_idlaboratorio,p_idcategoria,p_idmarca,p_idunidad,p_descripcion,p_stock_minimo,p_stock_maximo,p_stock,p_fecha_vto,p_precio_compra,p_utilidad,p_precio_venta,p_estado);
		msge:=  'Registrado correctamente';
	END IF;
END
$BODY$;

CREATE OR REPLACE PROCEDURE sp_actualizar_producto
(p_id INT,
p_idlaboratorio INT,
p_idcategoria INT,
p_idmarca INT,
p_idunidad INT,
p_descripcion varchar(150),
p_stock_minimo INT,
p_stock_maximo INT,
p_stock INT,
p_fecha_vto DATE,
p_precio_compra DECIMAL(12,2),
p_utilidad DECIMAL(12,2),
p_precio_venta DECIMAL(12,2),
p_estado VARCHAR(12),
OUT msge VARCHAR(100)
)
LANGUAGE 'plpgsql'
AS $BODY$
BEGIN
 	IF(EXISTS(SELECT * FROM productos WHERE descripcion=p_descripcion AND id<>p_id))THEN
 		msge:=  'El producto: '||p_descripcion||', ya existe';
	ELSE
		UPDATE productos SET 
			idlaboratorio=p_idlaboratorio,idcategoria=p_idcategoria,idmarca=p_idmarca,idunidad=p_idunidad,
			descripcion=p_descripcion,stock_minimo=p_stock_minimo,stock_maximo=p_stock_maximo,stock=p_stock,
			fecha_vto=p_fecha_vto,precio_compra=p_precio_compra,utilidad=p_utilidad,precio_venta=p_precio_venta,estado=p_estado
		WHERE id=p_id;
		msge:=  'Actualizado correctamente';
	END IF;
END
$BODY$;

/* TIPOS DE MOVIMIENTOS */
CREATE OR REPLACE PROCEDURE sp_generar_numero_notas
(p_tipomov INT,
OUT p_numero VARCHAR(8)
)
LANGUAGE 'plpgsql'
AS $BODY$
DECLARE 
	local_number INT;
BEGIN
 	IF(NOT EXISTS(SELECT * FROM movimientos WHERE idtipo=p_tipomov))then
 		p_numero:= (SELECT LPAD('1', 8, '0') AS numero);
	ELSE
		local_number:= (SELECT CAST(COALESCE(MAX(numero), '0') AS INTEGER)+1 AS number FROM movimientos WHERE idtipo=p_tipomov);
		p_numero:= (SELECT LPAD(local_number,8,'0') AS numero);
	END IF;
END
$BODY$;

/* NOTAS DE ENTRADA Y SALIDA */
CREATE OR REPLACE FUNCTION func_listar_notas
(p_tipomov INT
)
RETURNS TABLE(
	id INT,
	fecha_ingreso TEXT,
	serie VARCHAR(4),
	numero VARCHAR(8),
	observaciones VARCHAR,
	estado VARCHAR(2),
	usuario VARCHAR(50)
)
LANGUAGE 'plpgsql'
AS $BODY$
	BEGIN
		RETURN QUERY
			SELECT
				m.id,to_char(m.fecha_registro,'dd/mm/YYYY') AS fecha_ingreso,m.serie,m.numero,m.observaciones,
				m.estado,u.nombres AS usuario
			FROM movimientos m
				INNER JOIN usuarios u ON u.id=m.idusuario
			WHERE m.idtipo=p_tipomov;
    END;
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_nota_id
(p_id INT
)
RETURNS TABLE(
	id INT,
	fecha_ingreso TEXT,
	serie VARCHAR(4),
	numero VARCHAR(8),
	observaciones VARCHAR,
	estado VARCHAR(2),
	usuario VARCHAR(50)
)
LANGUAGE 'plpgsql'
AS $BODY$
	BEGIN
		RETURN QUERY
			SELECT
				m.id,to_char(m.fecha_registro,'dd/mm/YYYY') AS fecha_ingreso,m.serie,m.numero,m.observaciones,
				m.estado,u.nombres AS usuario
			FROM movimientos m
				INNER JOIN usuarios u ON u.id=m.idusuario
			WHERE m.id=p_id;
    END;
$BODY$;

CREATE OR REPLACE FUNCTION func_buscar_notas_fechas
(p_tipomov INT,
p_fecha_inicio DATE,
p_fecha_fin DATE
)
RETURNS TABLE(
	id INT,
	fecha_ingreso TEXT,
	serie VARCHAR(4),
	numero VARCHAR(8),
	observaciones VARCHAR,
	estado VARCHAR(2),
	usuario VARCHAR(50)
)
LANGUAGE 'plpgsql'
AS $BODY$
	BEGIN
		RETURN QUERY
			SELECT
				m.id,to_char(m.fecha_registro,'dd/mm/YYYY') AS fecha_ingreso,m.serie,m.numero,m.observaciones,
				m.estado,u.nombres AS usuario
			FROM movimientos m
				INNER JOIN usuarios u ON u.id=m.idusuario
			WHERE m.idtipo=p_tipomov AND CAST(to_char(m.fecha_registro, 'YYYY-mm-dd') AS DATE) BETWEEN p_fecha_inicio AND p_fecha_fin;
    END;
$BODY$;

CREATE OR REPLACE FUNCTION func_filtrar_notas
(p_tipomov INT,
p_dato VARCHAR(100)
)
RETURNS TABLE(
	id INT,
	fecha_ingreso TEXT,
	serie VARCHAR(4),
	numero VARCHAR(8),
	observaciones VARCHAR,
	estado VARCHAR(2),
	usuario VARCHAR(50)
)
LANGUAGE 'plpgsql'
AS $BODY$
	BEGIN
		RETURN QUERY
			SELECT
				m.id,to_char(m.fecha_registro,'dd/mm/YYYY') AS fecha_ingreso,m.serie,m.numero,m.observaciones,
				m.estado,u.nombres AS usuario
			FROM movimientos m
				INNER JOIN usuarios u ON u.id=m.idusuario
			WHERE m.idtipo=p_tipomov AND (
				m.serie||'-'||m.numero LIKE '%'||p_dato||'%'
				OR u.nombres LIKE p_dato||'%'
				OR m.estado LIKE p_dato||'%'
			);
    END;
$BODY$;


/***
	REGISTROS
**/
INSERT INTO roles (id, nombre, estado) VALUES
(1, 'ADMINISTRADOR', 'ACTIVO'),
(2, 'ALMACENERO', 'ACTIVO'),
(3, 'VENTAS', 'ACTIVO'),
(4, 'CAJA', 'ACTIVO');

INSERT INTO usuarios (id, idrole, nombres, usuario, clave, token, correo, estado) VALUES
(1, 1, 'Miguel Rojas Cerna', 'admin', 'rU1ucktdpuMIgVWH4yjF2Q==', NULL, 'mrojas@gmail.com', 'ACTIVO'),
(2, 2, 'JESUS RIVERA', 'JESUS', '80tnxT5earcgCoGvWP3sNw==', NULL, 'jrivera@gmail.com', 'ACTIVO');

SELECT * FROM roles;
SELECT * FROM usuarios;
