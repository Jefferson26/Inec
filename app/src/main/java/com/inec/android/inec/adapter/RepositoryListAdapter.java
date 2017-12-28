package com.inec.android.inec.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inec.android.inec.R;
import com.inec.android.inec.model.Repository;

import java.util.ArrayList;

public class RepositoryListAdapter extends RecyclerView.Adapter<RepositoryListAdapter.NumberViewHolder> {

    //Interface para facilitar a interacao onclick da lista de repositorios
    final private ListItemClickListener mOnClickListener;

    //contator para identificar o index durante onCreateViewHolder
    private static int viewHolderCount;

    //quantidade de itens que sera igual a quantidade de repositorios
    private int mNumberItems;

    //lista de repositorios que serao adicionados na RecyclerView
    private ArrayList<Repository> user_repositories;

    //Construtor
    public RepositoryListAdapter(ArrayList<Repository> repos, ListItemClickListener listener) {
        user_repositories = repos;
        mNumberItems = repos.size();
        mOnClickListener = listener;
        viewHolderCount = 0;
    }

    /**
     * onCreateViewHolder é chamado quando cada novo item do ViewHolder é criado.
     * @param viewGroup Grupo onde os itens estao contidos. Usado para saber o contexto em que serao inseridos
     * @param viewType  Pode ser usando quando existe mais de um tipo de item
     */
    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.number_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        //false = Não deve anexar ao pai imediatamente, pois os itens ainda estao sendo criados
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        //É adicionado o nome do repositorio a um item
        viewHolder.viewHolderIndex.setText(user_repositories.get(viewHolderCount).getName());

        //Contator para saber em qual item a interacao se encontra
        viewHolderCount++;

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        //É adicionado a linguagem de programacao a um item
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    //definicao da interface
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    //Implementa o onclick e recebe os itens que serao gerenciados na RecyclerView
    class NumberViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        TextView listItemNumberView;
        TextView viewHolderIndex;

        //Construtor
        private NumberViewHolder(View itemView) {
            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_language);
            viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            listItemNumberView.setText(user_repositories.get(listIndex).getLanguage());
        }

        //mOnClickListener recebe os clicks nos itens
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
