package com.gomes.ferreira.raphael.githubapiaccess.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gomes.ferreira.raphael.githubapiaccess.Model.PullRequestRepository;
import com.gomes.ferreira.raphael.githubapiaccess.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PullRequestAdapter extends RecyclerView.Adapter<PullRequestAdapter.ViewHolderPull>{


    private List<PullRequestRepository> pulls;
    private Context context;

    public PullRequestAdapter(List<PullRequestRepository> pullList, Context context){
        this.pulls = pullList;
        this.context = context;
    }

    @Override
    public ViewHolderPull onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderPull(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_pull_request,parent,false),pulls,context);
    }

    @Override
    public void onBindViewHolder(ViewHolderPull holder, int position) {
        holder.textViewPullTitle.setText(this.pulls.get(position).getTitle());
        String description = this.pulls.get(position).getDescription();
        if (description != null){
            if (description != ""){
                if (description.length() > 100){
                    description = description.substring(0,100)+"...";
                }
            }
        }
        holder.textViewPullDescription.setText(description);
        holder.textViewAuthorName.setText(this.pulls.get(position).getAuthor().getLogin());
        Picasso.with(this.context).load(this.pulls.get(position).getAuthor().getAvatar_url())
                .resize(35,35)
                .centerCrop().into(holder.imageViewPullAuthorAvatar);
        try {
            String data = new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.US).parse(String.valueOf(this.pulls.get(position).getDate())));
            holder.textViewPullDate.setText(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.pulls.size();
    }

    public class ViewHolderPull extends RecyclerView.ViewHolder implements OnClickListener{

        private TextView textViewPullTitle;
        private TextView textViewPullDescription;
        private TextView textViewAuthorName;
        private ImageView imageViewPullAuthorAvatar;
        private TextView textViewPullDate;
        private List<PullRequestRepository> pulls;
        private Context context;

        public ViewHolderPull(View itemView, List<PullRequestRepository> pullList, Context context) {
            super(itemView);
            this.pulls = pullList;
            this.context = context;
            textViewPullTitle = itemView.findViewById(R.id.textViewPullTitle);
            textViewPullDescription = itemView.findViewById(R.id.textViewPullDescription);
            textViewAuthorName = itemView.findViewById(R.id.textViewPullAuthorname);
            imageViewPullAuthorAvatar = itemView.findViewById(R.id.imageViewPullAuthorAvatar);
            textViewPullDate = itemView.findViewById(R.id.textViewPullDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(this.pulls.get(getAdapterPosition()).getHtml_url()));
            context.startActivity(intent);
        }
    }
}
