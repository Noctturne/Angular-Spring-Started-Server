package com.noctturne.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.noctturne.api.DAO.UserDAO;
import com.noctturne.api.models.Category;
import com.noctturne.api.models.User;

@Service
public class UserService implements UserServiceInterface{

	@Autowired
	private UserDAO userDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return (List<User>) userDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<User> findAll(Pageable pageable) {
		return userDao.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	@Override
	public User findById(Long id) {
		return userDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public User save(User user) {
		return userDao.save(user);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		userDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Category> findAllCategories() {
		return userDao.findAllCategories();
	}


	
}
