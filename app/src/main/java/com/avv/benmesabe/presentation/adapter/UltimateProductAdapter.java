package com.avv.benmesabe.presentation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avv.benmesabe.R;
import com.avv.benmesabe.domain.Product;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by angelvazquez on 18/10/15.
 */
public class UltimateProductAdapter extends UltimateViewAdapter<UltimateProductAdapter.ProductViewHolder>{

    private final LayoutInflater layoutInflater;
    private List<Product> productCollection;
    private Context context;

    public UltimateProductAdapter(Context context, Collection<Product> productCollection) {
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.productCollection = (List<Product>) productCollection;
        this.context = context;
    }

    @Override
    public ProductViewHolder getViewHolder(View view) {
        return new ProductViewHolder(view);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_product, parent, false);
        ProductViewHolder vh = new ProductViewHolder(v);
        return vh;
    }

    @Override
    public int getAdapterItemCount() {
        return (this.productCollection != null) ? this.productCollection.size() : 0;
    }

    @Override
    public long generateHeaderId(int position) {
        if (getItem(position).getProductName().length() > 0)
            return getItem(position).getProductName().charAt(0);
        else return -1;
    }

    public Product getItem(int position) {
        if (customHeaderView != null)
            position--;
        if (position < getAdapterItemCount())
            return productCollection.get(position);
        else return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.header_row_product, viewGroup, false);
        return new RecyclerView.ViewHolder(view){};
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.stick_text);
        textView.setText(getItem(position).getProductName());
//        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AA70DB93"));
        viewHolder.itemView.setBackgroundColor(Color.parseColor("#AAffffff"));
        //ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.stick_img);


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
        holder.textViewTitle.setText(product.getProductName());
        Picasso.with(context).load(product.getImageURL()).into(holder.imageProduct);
        holder.contentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UltimateProductAdapter.this.onItemClickListener != null) {
                    UltimateProductAdapter.this.onItemClickListener.onProductItemClicked(product);
                }
            }
        });
    }

    public void setProductsCollection(Collection<Product> productCollection) {
        this.productCollection = (List<Product>) productCollection;
        this.notifyDataSetChanged();
    }

    private OnProductItemClickListener onItemClickListener;

    public void setOnProductItemClickListener (OnProductItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnProductItemClickListener {
        void onProductItemClicked(Product product);
    }

    static class ProductViewHolder extends UltimateRecyclerviewViewHolder {
        @Bind(R.id.title)
        TextView textViewTitle;
        @Bind(R.id.imageProduct)
        ImageView imageProduct;
        @Bind(R.id.cardView)
        CardView productCardView;
        @Bind(R.id.contentCardView)
        View contentCardView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onItemDismiss(int position) {
        productCollection.remove(position);
        super.onItemDismiss(position);
    }
}
