package com.ecommerce.server.service.impl.descuento;

import com.ecommerce.server.model.dao.UsuarioDao;
import com.ecommerce.server.model.dao.descuento.DescuentoCodigoDao;
import com.ecommerce.server.model.dao.descuento.DescuentoUsuarioDao;
import com.ecommerce.server.model.dto.descuento.DescuentoCodigoDto;
import com.ecommerce.server.model.entity.Usuario;
import com.ecommerce.server.model.entity.descuento.DescuentoCodigo;
import com.ecommerce.server.model.entity.descuento.DescuentoUsuario;
import com.ecommerce.server.service.descuento.IDescuentoCodigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DescuentoCodigoImplService implements IDescuentoCodigoService {


    @Autowired
    private DescuentoCodigoDao descuentoCodigoDao;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private DescuentoUsuarioDao descuentoUsuarioDao;

    @Override
    public List<DescuentoCodigo> getDescuentoCodigos() {
        return (List) descuentoCodigoDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public DescuentoCodigo getDescuentoCodigo(Long id) {
        return descuentoCodigoDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public DescuentoCodigo save(DescuentoCodigoDto descuentoCodigoDto) {
        DescuentoCodigo descuento = DescuentoCodigo.builder()
                .id(descuentoCodigoDto.getId())
                .codigo(descuentoCodigoDto.getCodigo())
                .descripcion(descuentoCodigoDto.getDescripcion())
                .porcentaje(descuentoCodigoDto.getPorcentaje())
                .fechaInicio(descuentoCodigoDto.getFechaInicio())
                .fechaFin(descuentoCodigoDto.getFechaFin())
                .usoMaximo(descuentoCodigoDto.getUsoMaximo())
                .usado(descuentoCodigoDto.getUsado())
                .activo(descuentoCodigoDto.getActivo())
                .build();
        return descuentoCodigoDao.save(descuento);
    }

    @Transactional
    @Override
    public void deleteDescuentoCodigo(DescuentoCodigo descuentoCodigo) {
        descuentoCodigoDao.delete(descuentoCodigo);
    }

    @Override
    public boolean existsById(Long id) {
        return descuentoCodigoDao.existsById(id);
    }



    @Transactional
    @Override
    public DescuentoCodigo aplicarCodigoDescuento(String codigo, Long usuarioId)  {
        DescuentoCodigo descuentoCodigo = descuentoCodigoDao.findByCodigoWithLock(codigo).orElse(null);

        if (!existsById(descuentoCodigo.getId())) {
            return null;
        }
//
        Usuario usuario = usuarioDao.findById(usuarioId).orElse(null);
        if (descuentoUsuarioDao.existsByDescuentoCodigoAndUsuario(descuentoCodigo, usuario)) {
            return null;
        }
        descuentoCodigo.setUsado(descuentoCodigo.getUsado() + 1);
        if (descuentoCodigo.getUsado() >= descuentoCodigo.getUsoMaximo()) {
            descuentoCodigo.setActivo(false);
        }

        // Crear un registro en la tabla descuento_usuario
        DescuentoUsuario descuentoUsuario = DescuentoUsuario.builder()
                .descuentoCodigo(descuentoCodigo)
                .usuario(usuario)
                .fechaUso(LocalDate.now())
                .build();
        descuentoUsuarioDao.save(descuentoUsuario);
        return descuentoCodigoDao.save(descuentoCodigo);
    }
}
