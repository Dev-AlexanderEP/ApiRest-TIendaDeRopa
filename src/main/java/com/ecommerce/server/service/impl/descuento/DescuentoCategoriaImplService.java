package com.ecommerce.server.service.impl.descuento;

import com.ecommerce.server.model.dao.descuento.DescuentoCategoriaDao;
import com.ecommerce.server.model.dto.descuento.DescuentoCategoriaDto;
import com.ecommerce.server.model.dto.descuento.DescuentoCategoriaRequestDto;
import com.ecommerce.server.model.entity.descuento.DescuentoCategoria;
import com.ecommerce.server.model.entity.prenda.Categoria;
import com.ecommerce.server.service.descuento.IDescuentoCategoriaService;
import com.ecommerce.server.service.prenda.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DescuentoCategoriaImplService implements IDescuentoCategoriaService {

    @Autowired
    private DescuentoCategoriaDao descuentoCategoriaDao;

    @Autowired
    private ICategoriaService categoriaService;

    @Override
    public List<DescuentoCategoria> getDescuentoCategorias() {
        return (List) descuentoCategoriaDao.findAll();
    }

    @Override
    public DescuentoCategoria getDescuentoCategoria(Long id) {
        return descuentoCategoriaDao.findById(id).orElse(null);
    }

    @Override
    public DescuentoCategoria save(DescuentoCategoriaRequestDto descuentoCategoriaRequestDto) {
        Categoria categoria = categoriaService.getCategoria(descuentoCategoriaRequestDto.getCategoriaId());
        DescuentoCategoria descuentoCategoria = DescuentoCategoria.builder()
                .id(descuentoCategoriaRequestDto.getId())
                .categoria(categoria)
                .porcentaje(descuentoCategoriaRequestDto.getPorcentaje())
                .fechaInicio(descuentoCategoriaRequestDto.getFechaInicio())
                .fechaFin(descuentoCategoriaRequestDto.getFechaFin())
                .activo(descuentoCategoriaRequestDto.getActivo())
                .build();
        return descuentoCategoriaDao.save(descuentoCategoria);

    }

    @Override
    public void deleteDescuentoCategoria(DescuentoCategoria descuentoCategoria) {
        descuentoCategoriaDao.delete(descuentoCategoria);
    }

    @Override
    public boolean existsById(Long id) {
        return descuentoCategoriaDao.existsById(id);
    }
}
