package com.ecommerce.server.service.impl.descuento;

import com.ecommerce.server.model.dao.descuento.DescuentoUsuarioDao;
import com.ecommerce.server.model.dto.descuento.DescuentoCodigoDto;
import com.ecommerce.server.model.dto.descuento.DescuentoUsuarioDto;
import com.ecommerce.server.model.dto.descuento.DescuentoUsuarioRequestDto;
import com.ecommerce.server.model.entity.Usuario;
import com.ecommerce.server.model.entity.descuento.DescuentoCodigo;
import com.ecommerce.server.model.entity.descuento.DescuentoUsuario;
import com.ecommerce.server.service.IUsuarioService;
import com.ecommerce.server.service.descuento.IDescuentoCodigoService;
import com.ecommerce.server.service.descuento.IDescuentoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DescuentoUsuarioImplService implements IDescuentoUsuarioService {

    @Autowired
    private DescuentoUsuarioDao descuentoUsuarioDao;

    @Autowired
    private IDescuentoCodigoService descuentoCodigoService;

    @Autowired
    private IUsuarioService usuarioService;

    @Override
    public List<DescuentoUsuario> getDescuentoUsuarios() {
        return (List) descuentoUsuarioDao.findAll();
    }

    @Override
    public DescuentoUsuario getDescuentoUsuario(Long id) {
        return descuentoUsuarioDao.findById(id).orElse(null);
    }

    
    @Override
    public void deleteDescuentoUsuario(DescuentoUsuario descuentoUsuario) {
        descuentoUsuarioDao.delete(descuentoUsuario);
    }

    @Override
    public boolean existsById(Long id) {
        return descuentoUsuarioDao.existsById(id);
    }
}
