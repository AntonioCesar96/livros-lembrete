package livroslembrete.com.br.livroslembrete.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.models.Livro;
import livroslembrete.com.br.livroslembrete.utils.ImageUtils;

public class LivroAdapter extends RecyclerView.Adapter<LivroAdapter.LivrosViewHolder> {

    private final List<Livro> livros;
    private final Context context;
    private LivrosOnClickListener onClickListener;

    public LivroAdapter(Context context, List<Livro> livros, LivrosOnClickListener
            onClickListener) {
        this.context = context;
        this.livros = livros;
        this.onClickListener = onClickListener;
    }

    public void updateList(List<Livro> livros) {
        this.livros.addAll(livros);
        notifyDataSetChanged();
    }

    @Override
    public LivrosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_livros, parent, false);
        LivrosViewHolder viewHolder = new LivrosViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final LivrosViewHolder holder, final int position) {
        Livro livro = this.livros.get(position);

        holder.txtNome.setText(livro.getNome());
        holder.txtTotalPaginas.setText("" + livro.getTotalPaginas());

        ImageUtils.setImagemFeed(context, livro.getUrlImagem(), holder.img, holder.imgWrapper, holder.progress);

        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClick(holder, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.livros != null ? this.livros.size() : 0;
    }

    public interface LivrosOnClickListener {
        void onClick(LivrosViewHolder holder, int idx);
    }

    public static class LivrosViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtTotalPaginas;
        ImageView img;
        LinearLayout imgWrapper;
        ProgressBar progress;

        LivrosViewHolder(View view) {
            super(view);
            txtNome = view.findViewById(R.id.txtNome);
            txtTotalPaginas = view.findViewById(R.id.txtTotalPaginas);
            imgWrapper = view.findViewById(R.id.imgWrapper);
            img = view.findViewById(R.id.img);
            progress = view.findViewById(R.id.progress);
        }
    }
}

