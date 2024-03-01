package com.example.practica_seguridad.controller;

import com.example.practica_seguridad.model.TipoSuelo;
import com.example.practica_seguridad.service.TipoSueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketZonaRiegoController {
    @Autowired
    private TipoSueloService tipoSueloService;
   /* @MessageMapping("/tiposuelo.sendList")
    @SendTo("/topic/tiposueloList")
    public List<TipoSuelo> sendTipoSueloList(@Payload TipoSuelo tipoSuelo) {
        List<TipoSuelo> tipoSueloList = tipoSueloService.findByNombre(tipoSuelo.getTipoSuelo());
        return tipoSueloList;
    }*/
}
