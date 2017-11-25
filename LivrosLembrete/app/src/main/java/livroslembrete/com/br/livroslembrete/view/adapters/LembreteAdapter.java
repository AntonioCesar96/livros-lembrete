package livroslembrete.com.br.livroslembrete.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.domain.Lembrete;

public class LembreteAdapter extends RecyclerView.Adapter<LembreteAdapter.LembretesViewHolder> {
    private final List<Lembrete> lembretes;
    private final Context context;
    private OnCheckedChangeListener onCheckedChangeListener;

    public LembreteAdapter(Context context, List<Lembrete> lembretes, OnCheckedChangeListener
            onCheckedChangeListener) {
        this.context = context;
        this.lembretes = lembretes;
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public void updateList(List<Lembrete> lembretes) {
        this.lembretes.addAll(lembretes);
        notifyDataSetChanged();
    }

    @Override
    public LembretesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_lembrete, parent, false);
        LembretesViewHolder viewHolder = new LembretesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final LembretesViewHolder holder, final int position) {
        Lembrete lembrete = this.lembretes.get(position);
        String horario = new SimpleDateFormat("HH:mm - dd/MM/yyyy", Locale.getDefault()).format(lembrete.getDataHora().getTime());

        holder.txtNome.setText(lembrete.getNomeLivro());
        holder.txtHorario.setText(horario);

        if (onCheckedChangeListener != null) {
            holder.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    onCheckedChangeListener.onChange(isChecked, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.lembretes != null ? this.lembretes.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface OnCheckedChangeListener {
        void onChange(boolean isChecked, int idx);
    }

    public static class LembretesViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtHorario;
        Switch switch1;

        LembretesViewHolder(View view) {
            super(view);
            txtNome = view.findViewById(R.id.txtNome);
            txtHorario = view.findViewById(R.id.txtHorario);
            switch1 = view.findViewById(R.id.switch1);
        }
    }
}

