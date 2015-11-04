package com.avv.benmesabe.presentation.view;

import com.avv.benmesabe.domain.Allergen;
import com.avv.benmesabe.domain.Ingredient;

import java.util.Collection;

/**
 * Created by angelvazquez on 18/10/15.
 */
public interface DetailProductView extends LoadDataView {

    /**
     * Render a product ingredient list in the UI.
     *
     * @param productIngredientsModelCollection The collection of {@link Ingredient} that will be shown.
     */
    void renderProductIngredientsList(Collection<Ingredient> productIngredientsModelCollection);

    void renderProductAllergensList(Collection<Allergen> productAllergensModelCollection);

}
