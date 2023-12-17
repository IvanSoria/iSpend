package com.example.ispend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView mProductNameTextView;
        TextView mProductDescriptionTextView;
        TextView mProductPriceTextView;
        TextView mProductQuantityTextView;

        public ProductViewHolder(View intemView) {
            super(intemView);
            mProductNameTextView = intemView.findViewById(R.id.product_name_TextView);
            mProductDescriptionTextView = intemView.findViewById(R.id.product_description_TextView);
            mProductPriceTextView = intemView.findViewById(R.id.product_cost_TextView);
            mProductQuantityTextView = intemView.findViewById(R.id.product_quantity_TextView);
        }
    }

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.mProductNameTextView.setText(holder.mProductNameTextView.getText() + " " + product.getProductName());
        holder.mProductDescriptionTextView.setText(holder.mProductDescriptionTextView.getText() + " " + product.getProductDescription());
        holder.mProductPriceTextView.setText(holder.mProductPriceTextView.getText().toString() + product.getProductPrice() );
        holder.mProductQuantityTextView.setText(holder.mProductQuantityTextView.getText() + " " + product.getProductQuantity() + "");
    }

    @Override
    public int getItemCount() {
        if (productList == null) {
            return -1;
        }
        return productList.size();
    }

}
