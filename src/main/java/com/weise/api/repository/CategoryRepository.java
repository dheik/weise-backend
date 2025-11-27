package com.weise.api.repository;

import com.weise.api.model.Category;
import com.weise.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByUser(User user);
    List<Category> findAllByUserAndType(User user, String type);
}