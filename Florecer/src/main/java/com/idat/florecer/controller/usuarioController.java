package com.idat.florecer.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idat.florecer.entity.Usuario;
import com.idat.florecer.service.IUsuarioService;
import com.idat.florecer.serviceR.UsuarioServiceIm;

@CrossOrigin(origins= {"http://localhost/4200"})
@RestController
@RequestMapping("/usuario")
public class usuarioController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private UsuarioServiceIm userService;

	
	//LISTAR USUARIOS
	@GetMapping("/usuarios")
	public List<Usuario> listar(){
		return usuarioService.findAll();
	}
	
	//BUSCAR USUARIO POR ID
	@GetMapping("/usuario/{id}")
	public Usuario usuario (@PathVariable Long id) {
		return usuarioService.findById(id);
	}
	
	@GetMapping("/{user}/{passw}")
	public int verificacion(@PathVariable String user,@PathVariable String passw) {
		if(userService.findByU(user)!=null) {
			if(userService.iniciosesion(user,passw)!=null) {
				return 1;
			}else {
				return 2;
			}
		}else {
			return 3;
		}
	}
	
	@GetMapping("/{user}")
	public Usuario findByUser(@PathVariable String user) {
		return userService.findByU(user);
		
	}
	
	//CREAR UN NUEVO USUARIO
	@PostMapping("/usuarionew")
	public Usuario usuarionew (@RequestBody Usuario usuario) {
		usuarioService.save(usuario);
		
		return usuarioService.findById(usuario.getIdUsuario());
	}
	
	//ACTUALIZAR USUARIO
	@PutMapping("/usuarioupdate/{id}")
	public Usuario actualizar(@RequestBody Usuario usuario,@PathVariable Long id) {
		Usuario usuarioActual=usuarioService.findById(id);
		usuarioActual.setApellido(usuario.getApellido());
		usuarioActual.setNombre(usuario.getNombre());
		usuarioActual.setUsuario(usuario.getUsuario());
		usuarioActual.setContrasena(usuario.getContrasena());
		usuarioActual.setSexo(usuario.getSexo());
		usuarioActual.setDireccion(usuario.getDireccion());
		usuarioActual.setTelefono(usuario.getTelefono());
		usuarioActual.setDni(usuario.getDni());
		usuarioActual.setEstado(usuario.getEstado());
		usuarioService.save(usuarioActual);
		return usuarioService.findById(id);
	}
	
	//ELIMINAR USUARIO
	@DeleteMapping("/usuariodelete/{id}")
	public void delete(@PathVariable Long id) {
		usuarioService.eliminarUsuario(id);
	}
	
	@DeleteMapping("/usuarioestado/{id}")
	public void deleteestado(@PathVariable Long id) {
		Usuario usuarioActual=usuarioService.findById(id);
		usuarioActual.setEstado(0);
		usuarioService.save(usuarioActual);
		
	}
	
}
