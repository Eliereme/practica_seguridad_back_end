package com.example.practica_seguridad.controller;



import com.example.practica_seguridad.model.Usuario;
import com.example.practica_seguridad.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    //shnitzel
    @Autowired
    private UsuarioService usuarioService;
    @GetMapping
    public ResponseEntity<List<Usuario>> listaUsuario(){
        return new ResponseEntity<>(usuarioService.findAll(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario){
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
        Usuario user =usuarioService.findByEmail(usuario.getEmail());
        if (user.getEmail()==null)
            return new ResponseEntity<>(usuarioService.create(usuario), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(new Usuario(), HttpStatus.CONFLICT);
    }
    @PutMapping
    public  ResponseEntity<Usuario> updateUser(@RequestBody Usuario usuario){
        return new ResponseEntity<>(usuarioService.update(usuario), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> finById(@PathVariable("id") Integer idUsuario){
        Usuario usuario= usuarioService.findById(idUsuario);
        if (usuario==null)
            return new ResponseEntity<>(new Usuario(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(usuario,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer idUsuario) throws Exception {
        Usuario usuario= usuarioService.findById(idUsuario);
        if (usuario==null)
            return new ResponseEntity<>(new Usuario(), HttpStatus.NOT_FOUND);
        usuarioService.delete(idUsuario);
        return new ResponseEntity<>(usuario,HttpStatus.OK);
    }
    @PostMapping("/busquedaUsuario")
    public  ResponseEntity<Usuario> busquedaUser(@RequestBody Usuario usuario){
        Usuario usuarioBusqueda=usuarioService.findByEmail(usuario.getEmail());
        if(usuarioBusqueda==null)
            return new ResponseEntity<>(new Usuario(), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(usuarioBusqueda, HttpStatus.OK);
    }
}
