package com.avv.benmesabe.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avv.benmesabe.R;
import com.avv.benmesabe.domain.Product;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by angelvazquez on 18/10/15.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private final LayoutInflater layoutInflater;
    private List<Product> productCollection;


    public ProductAdapter(Context context, Collection<Product> productCollection) {
        //this.validateUsersCollection(usersCollection);
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.productCollection = (List<Product>) productCollection;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.row_product, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);

        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product product = this.productCollection.get(position);
        holder.textViewTitle.setText(product.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (ProductAdapter.this.onItemClickListener != null) {
                    ProductAdapter.this.onItemClickListener.onProductItemClicked(product);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (this.productCollection != null) ? this.productCollection.size() : 0;
    }

    public void setProductsCollection(Collection<Product> productCollection) {
        //this.validateUsersCollection(usersCollection);
        this.productCollection = (List<Product>) productCollection;
        this.notifyDataSetChanged();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onProductItemClicked(Product product);
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView textViewTitle;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
