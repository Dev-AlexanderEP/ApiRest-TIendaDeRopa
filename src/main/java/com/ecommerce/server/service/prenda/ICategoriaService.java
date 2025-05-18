package com.ecommerce.server.service.prenda;


import com.ecommerce.server.model.dto.prenda.CategoriaDto;
import com.ecommerce.server.model.entity.prenda.Categoria;

import java.util.List;

public interface ICategoriaService {
    List<Categoria> getCategorias();
    Categoria getCategoria(Long id);
    Categoria save(CategoriaDto categoriaDto);
    void deleteCategoria(Categoria categoria);
    boolean existsByID(Long id);
}
