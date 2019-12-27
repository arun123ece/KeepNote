package com.stackroute.keepnote.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.CategoryDoesNoteExistsException;
import com.stackroute.keepnote.exception.CategoryNotCreatedException;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.repository.CategoryRepository;

/*
 * Service classes are used here to implement additional business logic/validation 
 * This class has to be annotated with @Service annotation.
 * @Service - It is a specialization of the component annotation. It doesn't currently 
 * provide any additional behavior over the @Component annotation, but it's a good idea 
 * to use @Service over @Component in service-layer classes because it specifies intent 
 * better. Additionally, tool support and additional behavior might rely on it in the 
 * future.
 * */
@Service
public class CategoryServiceImpl implements CategoryService {

	/*
	 * Autowiring should be implemented for the CategoryRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */

	/*
	 * This method should be used to save a new category.Call the corresponding
	 * method of Respository interface.
	 */
	@Autowired
	CategoryRepository categoryRepositoryImpl;

	public Category createCategory(Category category) throws CategoryNotCreatedException {

		/*category.setCategoryCreationDate(new Date());
		try {
			if(null != category) {
				categoryRepositoryImpl.save(category);
				return category;
			}
			return null;
		} catch (Exception e) {
			throw new CategoryNotCreatedException("CategoryNotCreatedException");
		}*/
		category.setCategoryCreationDate(new Date());
		Category category1 = categoryRepositoryImpl.insert(category);

		if (category1 == null) {
			throw new CategoryNotCreatedException("Unable to create Category!!!! Please try again");
		}
		return category1;
	}

	/*
	 * This method should be used to delete an existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteCategory(String categoryId) throws CategoryDoesNoteExistsException {

		Category category = null;

		try {
			category = getCategoryById(categoryId);
			if(null == category) {
				throw new CategoryDoesNoteExistsException("CategoryDoesNoteExistsException");

			}
			categoryRepositoryImpl.delete(category);
			return true;

		} catch (CategoryNotFoundException e) {

			throw new CategoryDoesNoteExistsException("CategoryDoesNoteExistsException");
		}
	}
	/*
	 * This method should be used to update a existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public Category updateCategory(Category category, String categoryId) {


		try {
			if(null != getCategoryById(categoryId)) {
				category.setCategoryCreationDate(new Date());
				Category category1 = categoryRepositoryImpl.save(category);
				return category1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * This method should be used to get a category by categoryId.Call the
	 * corresponding method of Respository interface.
	 */
	/*public Category getCategoryById(String categoryId) throws CategoryNotFoundException {

		if(null == categoryId || categoryId.isEmpty()) {
			return null;
		}
		try {
			return categoryRepositoryImpl.findById(categoryId).get();

		} catch (Exception e) {

			throw new CategoryNotFoundException("CategoryNotFoundException");
		}
	}*/
	public Category getCategoryById(String categoryId) throws CategoryNotFoundException {
		try {
			Category fetchedCategory = categoryRepositoryImpl.findById(categoryId).get();
			return fetchedCategory;
		} catch (NoSuchElementException e) {
			throw new CategoryNotFoundException("Category does not exists");
		}
	}

	/*
	 * This method should be used to get a category by userId.Call the corresponding
	 * method of Respository interface.
	 */
	public List<Category> getAllCategoryByUserId(String userId) {

		if(null == userId || userId.isEmpty()) {
			return null;
		}
		return categoryRepositoryImpl.findAllCategoryByCategoryCreatedBy(userId);
	}
}
