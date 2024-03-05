package org.madalina.productsinventory.service;

import org.madalina.productsinventory.entities.Category;
import org.madalina.productsinventory.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository)
    {

        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }






//    public Optional<Category> findCategoryById(Long id) {
//        return categoryRepository.findById(id);
//    }
//
//    public Category saveCategory(Category category) {
//        return categoryRepository.save(category);
//    }
//
//    public void deleteCategory(Long id) {
//        categoryRepository.deleteById(id);
//    }
}
