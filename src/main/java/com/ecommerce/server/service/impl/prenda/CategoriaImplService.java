package com.ecommerce.server.service.impl.prenda;

import com.ecommerce.server.model.dao.prenda.CategoriaDao;
import com.ecommerce.server.model.dto.prenda.CategoriaDto;
import com.ecommerce.server.model.entity.prenda.Categoria;
import com.ecommerce.server.service.prenda.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaImplService implements ICategoriaService {

    @Autowired
    private CategoriaDao categoriaDao;

    @Override
    public List<Categoria> getCategorias() {
        return (List) categoriaDao.findAll();
    }


    @Transactional(readOnly = true)
    @Override
    public Categoria getCategoria(Long id) {
        return categoriaDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Categoria save(CategoriaDto categoriaDto) {
        Categoria categoria = Categoria.builder()
                .id(categoriaDto.getId())
                .nomCategoria(categoriaDto.getNomCategoria())
                .build();
        return categoriaDao.save(categoria);
    }
    @Transactional
    @Override
    public void deleteCategoria(Categoria categoria) {
        categoriaDao.delete(categoria);
    }

    @Override
    public boolean existsByID(Long id) {
        return categoriaDao.existsById(id);
    }
}
