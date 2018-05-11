package com.gomes.ferreira.raphael.githubapiaccess.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gomes.ferreira.raphael.githubapiaccess.Model.Repository;
import com.gomes.ferreira.raphael.githubapiaccess.View.PullRequestActivity;
import com.gomes.ferreira.raphael.githubapiaccess.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolderRepository> {

    private List<Repository> mArrayList;
    private List<Repository> all;
    private List<Repository> repositories;
    private Context context;

    public RepositoryAdapter(List<Repository> repositories, Context context) {
        this.repositories = repositories;
        this.all = repositories;
        this.context = context;
    }

    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    mArrayList = all;
                } else {
                    ArrayList<Repository> filteredList = new ArrayList<>();

                    for (Repository repo : all) {
                        if (repo.getName().toLowerCase().contains(charString)
                                || repo.getDescription().toLowerCase().contains(charString)
                                || repo.getAuthor().getLogin().toLowerCase().contains(charString)) {
                            filteredList.add(repo);
                        }
                    }
                    mArrayList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                repositories = (ArrayList<Repository>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public ViewHolderRepository onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderRepository(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_repositories, parent, false), this.context, this.repositories);
    }


    @Override
    public void onBindViewHolder(final ViewHolderRepository holder, final int position) {
        holder.textViewRepositoryName.setText(repositories.get(position).getName());
        if (repositories.get(position).getDescription() != null) {
            String description = repositories.get(position).getDescription();
            if (description.length() > 100) {
                description = description.substring(0, 97) + "...";
            }
            holder.textViewRepositoryDescription.setText(description);
        }
        holder.textViewForksCount.setText(String.valueOf(repositories.get(position).getForks_count()));
        holder.textViewStarsCount.setText(String.valueOf(repositories.get(position).getStargazers_count()));
        holder.textViewAuthorName.setText(String.valueOf(repositories.get(position).getAuthor().getLogin()));
        if (this.repositories.get(position).getAuthor().getAvatar_url() != ""){
            Picasso.with(context).load(repositories.get(position).getAuthor().getAvatar_url())
                    .resize(200, 200)
                    .centerCrop().into(holder.imageViewAvatar);
        }
    }

    public void addListMoreItems(List<Repository> repositories) {
        this.repositories.addAll(repositories);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(repositories != null) {
            return this.repositories.size();
        } else {
            return 0;
        }
    }

    public class ViewHolderRepository extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewRepositoryName;
        private TextView textViewRepositoryDescription;
        private TextView textViewForksCount;
        private TextView textViewStarsCount;
        private TextView textViewAuthorName;
        private ImageView imageViewAvatar;
        private ImageView favoriteRepository;
        private List<Repository> repositories;
        private Context context;

        private ViewHolderRepository(View itemView, Context context, List<Repository> repositories) {
            super(itemView);
            this.context = context;
            this.repositories = repositories;

            textViewRepositoryName = itemView.findViewById(R.id.textViewRepositoryName);
            textViewRepositoryDescription = itemView.findViewById(R.id.textViewRepositoryDescription);
            textViewForksCount = itemView.findViewById(R.id.textViewForksCount);
            textViewStarsCount = itemView.findViewById(R.id.textViewStarsCount);
            textViewAuthorName = itemView.findViewById(R.id.textViewAuthorName);
            imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
            favoriteRepository = itemView.findViewById(R.id.favoriteView);

            itemView.setOnClickListener(this);
            favoriteRepository.setTag(2);
            favoriteRepository.setOnClickListener (this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.favoriteView) {
                final ImageView imgView = (ImageView) view;
                if (imgView.getTag().equals(1)) {
                    imgView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                    imgView.setTag(2);
                } else {
                    imgView.setImageResource(R.drawable.ic_toggle_star_24);
                    imgView.setTag(1);
                }
            } else {
                Intent intent = new Intent(this.context, PullRequestActivity.class);
                intent.putExtra("authorName", this.repositories.get(getAdapterPosition()).getAuthor().getLogin());
                intent.putExtra("repositoryName", this.repositories.get(getAdapterPosition()).getName());
                this.context.startActivity(intent);
            }
        }
    }
}
