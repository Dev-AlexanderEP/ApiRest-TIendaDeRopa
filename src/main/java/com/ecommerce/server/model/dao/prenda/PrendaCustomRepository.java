package com.ecommerce.server.model.dao.prenda;

                import com.ecommerce.server.model.dto.prenda.FiltroPrendaDTO;
                import com.ecommerce.server.model.dto.descuento.PrendaConDescuentoResponseDto;
                import com.ecommerce.server.model.entity.prenda.Prenda;
                import jakarta.persistence.EntityManager;
                import jakarta.persistence.PersistenceContext;
                import jakarta.persistence.criteria.*;
                import org.springframework.stereotype.Repository;

                import java.util.ArrayList;
                import java.util.List;

                @Repository
                public class PrendaCustomRepository {

                    @PersistenceContext
                    private EntityManager em;

                    public List<PrendaConDescuentoResponseDto> buscarConFiltros(FiltroPrendaDTO filtro) {
                        CriteriaBuilder cb = em.getCriteriaBuilder();
                        CriteriaQuery<PrendaConDescuentoResponseDto> cq = cb.createQuery(PrendaConDescuentoResponseDto.class);
                        Root<Prenda> prenda = cq.from(Prenda.class);

                        Join<Object, Object> talla = prenda.join("talla");
                        Join<Object, Object> marca = prenda.join("marca");
                        Join<Object, Object> categoria = prenda.join("categoria", JoinType.LEFT);
                        Join<Object, Object> descuentoPrenda = prenda.join("descuentoPrenda", JoinType.LEFT);
                        Join<Object, Object> descuentoCategoria = categoria.join("descuentoCategoria", JoinType.LEFT);

                        List<Predicate> predicates = new ArrayList<>();

                        // Filtros dinámicos
                        if (filtro.getTallas() != null && !filtro.getTallas().isEmpty()) {
                            predicates.add(talla.get("nomTalla").in(filtro.getTallas()));
                        }
                        if (filtro.getMarcas() != null && !filtro.getMarcas().isEmpty()) {
                            predicates.add(marca.get("nomMarca").in(filtro.getMarcas()));
                        }
                        if (filtro.getCategoria() != null && !filtro.getCategoria().isEmpty()) {
                            predicates.add(cb.equal(categoria.get("nomCategoria"), filtro.getCategoria()));
                        }
                        if (filtro.getRangosPrecio() != null && !filtro.getRangosPrecio().isEmpty()) {
                            List<Predicate> rangoPrecioPreds = new ArrayList<>();
                            for (FiltroPrendaDTO.Rango r : filtro.getRangosPrecio()) {
                                rangoPrecioPreds.add(cb.between(prenda.get("precio"), r.getMin(), r.getMax()));
                            }
                            predicates.add(cb.or(rangoPrecioPreds.toArray(new Predicate[0])));
                        }
                        if (filtro.getRangosDescuento() != null && !filtro.getRangosDescuento().isEmpty()) {
                            List<Predicate> rangoDescPreds = new ArrayList<>();
                            Expression<Double> descuento = cb.coalesce(
                                    descuentoPrenda.get("porcentaje").as(Double.class),
                                    descuentoCategoria.get("porcentaje").as(Double.class)
                            );
                            descuento = cb.coalesce(descuento, cb.literal(0.0));
                            for (FiltroPrendaDTO.Rango r : filtro.getRangosDescuento()) {
                                rangoDescPreds.add(cb.between(descuento, r.getMin(), r.getMax()));
                            }
                            predicates.add(cb.or(rangoDescPreds.toArray(new Predicate[0])));
                        }

                        cq.where(cb.and(predicates.toArray(new Predicate[0])));

                        // Expresión para descuento aplicado
                        Expression<Double> descuentoAplicado = cb.coalesce(
                                descuentoPrenda.get("porcentaje").as(Double.class),
                                descuentoCategoria.get("porcentaje").as(Double.class)
                        );
                        descuentoAplicado = cb.coalesce(descuentoAplicado, cb.literal(0.0));

                        // Expresión para tipo de descuento
                        Expression<Object> tipoDescuento = cb.selectCase()
                            .when(cb.isNotNull(descuentoPrenda.get("porcentaje")), cb.literal("descuento_prenda"))
                            .when(cb.isNotNull(descuentoCategoria.get("porcentaje")), cb.literal("descuento_categoria"))
                            .otherwise(cb.literal("sin_descuento"));

                        cq.select(cb.construct(
                                PrendaConDescuentoResponseDto.class,
                                prenda.get("id"),
                                prenda.get("nombre"),
                                prenda.get("precio"),
                                prenda.get("imagenUrl"),
                                marca.get("nomMarca"),
                                descuentoAplicado,
                                tipoDescuento
                        ));

                        cq.orderBy(cb.asc(prenda.get("nombre")));

                        return em.createQuery(cq).getResultList();
                    }
                }