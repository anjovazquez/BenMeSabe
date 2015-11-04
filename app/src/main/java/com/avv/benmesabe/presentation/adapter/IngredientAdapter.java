package com.avv.benmesabe.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avv.benmesabe.R;
import com.avv.benmesabe.domain.Ingredient;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by angelvazquez on 4/11/15.
 */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{

    private final LayoutInflater layoutInflater;
    private List<Ingredient> ingredientsCollection;
    private Context context;


    public IngredientAdapter(Context context, Collection<Ingredient> ingredientsCollection) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.ingredientsCollection = (List<Ingredient>) ingredientsCollection;
        this.context = context;
    }

    private OnIngredientItemClickListener onItemClickListener;

    public void setOnIngredientItemClickListener (OnIngredientItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnIngredientItemClickListener {
        void onIngredientItemClicked(Ingredient ingredient);
    }

    public void setIngredientsCollection(Collection<Ingredient> ingredientCollection) {
        this.ingredientsCollection = (List<Ingredient>) ingredientCollection;
        this.notifyDataSetChanged();
    }

    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.row_ingredient, parent, false);
        IngredientViewHolder ingredientViewHolder = new IngredientViewHolder(view);

        return ingredientViewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.IngredientViewHolder holder, int position) {
        final Ingredient ingredient = this.ingredientsCollection.get(position);
        holder.textViewTitle.setText(ingredient.getIngredientName());
        /*holder.contentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IngredientAdapter.this.onItemClickListener != null) {
                    IngredientAdapter.this.onItemClickListener.onProductItemClicked(ingredient);
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return (this.ingredientsCollection != null) ? this.ingredientsCollection.size() : 0;
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView textViewTitle;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
