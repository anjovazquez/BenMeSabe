package com.avv.benmesabe.data.entity.mapper;

import com.avv.benmesabe.data.entity.AllergenEntity;
import com.avv.benmesabe.data.entity.IngredientEntity;
import com.avv.benmesabe.data.entity.ProductEntity;
import com.avv.benmesabe.domain.Allergen;
import com.avv.benmesabe.domain.Ingredient;
import com.avv.benmesabe.domain.Product;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by angel on 12/10/2015.
 */

@Singleton
public class BenMeSabeDataMapper {

    @Inject
    public BenMeSabeDataMapper(){}

    public Product transform(ProductEntity productEntity){
        Product product = new Product();
        if(productEntity!=null){
            product.setProductId(Integer.parseInt(String.valueOf(productEntity.getProductId())));
            product.setDescription(productEntity.getDescription());
            product.setImageURL(productEntity.getImageURL());
            product.setProductName(productEntity.getProductName());
            product.setProductSection(productEntity.getProductSection());
            product.setProductPrice(productEntity.getProductPrice());
        }
        return product;
    }

    public List<Product> transform(List<ProductEntity> productEntityCollection){
        List<Product> productList = new ArrayList<Product>();

        Product product;
        for(ProductEntity productEntity:productEntityCollection) {
            product = transform(productEntity);
            if(product!=null){
                productList.add(product);
            }
        }

        return productList;
    }


    public Ingredient transformIngredient(IngredientEntity ingredientEntity){
        Ingredient ingredient = new Ingredient();
        if(ingredientEntity!=null){
           ingredient.setIngredientId(ingredientEntity.getIngredientId());
           ingredient.setIngredientName(ingredientEntity.getIngredientName());
           ingredient.setProductId(ingredientEntity.getProductId());
        }
        return ingredient;
    }

    public List<Ingredient> transformIngredientList(List<IngredientEntity> ingredientEntityCollection){
        List<Ingredient> ingredientList = new ArrayList<Ingredient>();

        Ingredient ingredient;
        for(IngredientEntity ingredientEntity:ingredientEntityCollection) {
            ingredient = transformIngredient(ingredientEntity);
            if(ingredient!=null){
                ingredientList.add(ingredient);
            }
        }

        return ingredientList;
    }

    public Allergen transformAllergen(AllergenEntity allergenEntity){
        Allergen allergen = new Allergen();
        if(allergenEntity!=null){
            allergen.setAllergenId(allergenEntity.getAllergenId()!=null?allergenEntity.getAllergenId():0);
            allergen.setAllergenName(allergenEntity.getAllergenName());
        }
        return allergen;
    }

    public List<Allergen> transformAllergenList(List<AllergenEntity> allergenEntityCollection){
        List<Allergen> allergenList = new ArrayList<Allergen>();

        Allergen allergen;
        for(AllergenEntity allergenEntity:allergenEntityCollection) {
            allergen = transformAllergen(allergenEntity);
            if(allergen!=null){
                allergenList.add(allergen);
            }
        }

        return allergenList;
    }
}
