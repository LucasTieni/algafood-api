package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.DTO.UsuarioDTO;
import com.algaworks.algafood.api.v1.DTO.input.SenhaInput;
import com.algaworks.algafood.api.v1.DTO.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.DTO.input.UsuarioInput;
import com.algaworks.algafood.api.v1.assembler.UsuarioAssembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/v1/usuarios")
public class UsuarioController{

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private UsuarioDTOAssembler usuarioDTOAssembler;
	
	@Autowired
	private UsuarioAssembler usuarioAssembler;
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
//	@ApiOperation("Lista os usuários")
	@GetMapping
	public CollectionModel<UsuarioDTO> listar() {
		List<Usuario> allUsers = usuarioRepository.findAll(); 
		return usuarioDTOAssembler.toCollectionModel(allUsers);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/{id}")
	public UsuarioDTO buscar(@PathVariable Long id) {
		Usuario usuario = cadastroUsuario.buscarOuFalhar(id); 

		return usuarioDTOAssembler.toModel(usuario);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO adicionar (@RequestBody @Valid UsuarioComSenhaInput usuarioComSenhaInput){
		try {
			Usuario usuario = usuarioAssembler.toDomainObject(usuarioComSenhaInput);
			
			return usuarioDTOAssembler.toModel(cadastroUsuario.salvar(usuario));
			
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
	@PutMapping("/{id}")
	public UsuarioDTO atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioInput usuarioInput) {
		Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(id);
		
		usuarioAssembler.copyToDomainObject(usuarioInput, usuarioAtual);
		
		try {
			return usuarioDTOAssembler.toModel(cadastroUsuario.salvar(usuarioAtual));
		} catch (UsuarioNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
	@PutMapping("/{userId}/password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changePassword(@PathVariable Long userId, @RequestBody @Valid SenhaInput senha) {
		cadastroUsuario.alterarSenha(userId, senha.getSenhaAtual(), senha.getSenhaNova());
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		cadastroUsuario.excluir(id);
	}
}







