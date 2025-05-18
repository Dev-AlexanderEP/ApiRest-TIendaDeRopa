package com.ecommerce.server.service.impl;

import com.ecommerce.server.model.dao.ReseniaDao;
import com.ecommerce.server.model.dto.ReseniaDataClientDto;
import com.ecommerce.server.model.dto.ReseniaRequestDto;
import com.ecommerce.server.model.entity.Resenia;
import com.ecommerce.server.model.entity.Usuario;
import com.ecommerce.server.model.entity.prenda.Prenda;
import com.ecommerce.server.service.IReseniaService;
import com.ecommerce.server.service.IUsuarioService;
import com.ecommerce.server.service.prenda.IPrendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReseniaImplService implements IReseniaService {

    @Autowired
    private ReseniaDao reseniaDao;

    @Autowired
    private IPrendaService prendaService;
    @Autowired
    private IUsuarioService usuarioService;

    @Override
    public List<Resenia> getResenias() {
        return (List) reseniaDao.findAll();
    }

    @Override
    public Resenia getResenia(Long id) {
        return reseniaDao.findById(id).orElse(null);
    }

    @Override
    public Resenia save(ReseniaRequestDto reseniaRequestDto) {
        Prenda prenda = prendaService.getPrenda(reseniaRequestDto.getPrendaId());
        Usuario usuario = usuarioService.getUsuario(reseniaRequestDto.getUsuarioId());

        Resenia resenia;
        if (reseniaRequestDto.getId() != null) {
            // Si es una actualización, cargamos la entidad existente
            resenia = reseniaDao.findById(reseniaRequestDto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Reseña no encontrada con ID: " + reseniaRequestDto.getId()));
            // Actualizamos solo los campos necesarios
            resenia.setPrenda(prenda);
            resenia.setUsuario(usuario);
            resenia.setCalificacion(reseniaRequestDto.getCalificacion());
            resenia.setComentario(reseniaRequestDto.getComentario());
            // No tocamos el campo fecha, se mantiene el valor original
        } else {
            // Si es una nueva reseña, creamos una nueva instancia
            resenia = Resenia.builder()
                    .prenda(prenda)
                    .usuario(usuario)
                    .calificacion(reseniaRequestDto.getCalificacion())
                    .comentario(reseniaRequestDto.getComentario())
                    .build();
        }
        return reseniaDao.save(resenia);
    }

    @Override
    public void delete(Resenia resenia) {
        reseniaDao.delete(resenia);
    }

    @Override
    public boolean existById(Long id) {
        return reseniaDao.existsById(id);
    }



    // Nuevo método para devolver DTOs
    @Override
    public List<ReseniaDataClientDto> findReseniaDtoByPrendaId(Long prendaId) {
        List<Resenia> resenias = reseniaDao.findByPrendaId(prendaId);
        return resenias.stream().map(resenia -> ReseniaDataClientDto.builder()
                .id(resenia.getId())
                .nombreUsuario(resenia.getUsuario() != null ? resenia.getUsuario().getNombreUsuario() : "Anónimo")
                .calificacion(resenia.getCalificacion())
                .comentario(resenia.getComentario())
                .fecha(resenia.getFecha())
                .build()).collect(Collectors.toList());
    }
}
