-- ---------------------------------------------------------------------------
-- Esquema inicial de la base de datos `crud_db`.
--
-- Postgres ejecuta este script UNA SOLA VEZ: la primera vez que el volumen
-- `crud-webflux-postgres-data` se crea vacio. Si necesitas re-ejecutarlo:
--     docker compose down -v && docker compose up -d
-- ---------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS public.products
(
    product_id    SERIAL PRIMARY KEY,
    product_name  VARCHAR(150) NOT NULL,
    product_price VARCHAR(50)  NOT NULL
);

COMMENT ON TABLE public.products IS 'Catalogo de productos expuesto por el servicio CRUD reactivo';
COMMENT ON COLUMN public.products.product_id IS 'Identificador autoincremental del producto';
COMMENT ON COLUMN public.products.product_name IS 'Nombre comercial del producto';
COMMENT ON COLUMN public.products.product_price IS 'Precio del producto (se almacena como texto)';

-- Datos de ejemplo para poder probar los endpoints desde Swagger UI
INSERT INTO public.products (product_name, product_price)
SELECT * FROM (VALUES ('Teclado mecanico', '1299.00'),
                      ('Mouse inalambrico', '499.50'),
                      ('Monitor 27 pulgadas', '5899.99')) AS seed(product_name, product_price)
WHERE NOT EXISTS (SELECT 1 FROM public.products);
