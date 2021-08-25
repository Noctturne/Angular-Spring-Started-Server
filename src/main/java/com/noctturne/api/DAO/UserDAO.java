package com.noctturne.api.DAO;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.noctturne.api.models.Category;
import com.noctturne.api.models.User;

public interface UserDAO extends JpaRepository<User, Long>{

	@Query("from Category")
	public List<Category> findAllCategories();
}
