package com.noctturne.api.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.noctturne.api.models.Category;
import com.noctturne.api.models.User;

public interface UserServiceInterface {

	// Lista de usuarios
	public List<User> findAll();

	// Paginación (Número de páginas y elementos por página)
	public Page<User> findAll(Pageable pageable);
	
	// Buscar usuario
	public User findById(Long id);
	
	// Guardar usuario
	public User save(User user);
	
	// Eliminar usuario
	public void delete(Long id);
	
	// Todas las categorias
	public List<Category> findAllCategories();
}
