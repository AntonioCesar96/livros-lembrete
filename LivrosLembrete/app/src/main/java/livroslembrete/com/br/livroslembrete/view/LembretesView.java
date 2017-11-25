package livroslembrete.com.br.livroslembrete.view;

import android.support.v4.app.FragmentActivity;
import java.util.List;
import livroslembrete.com.br.livroslembrete.domain.Lembrete;

public interface LembretesView {
    void showSnack(String msg);
    FragmentActivity getActivity();
    void setAdapter(List<Lembrete> lembretes);
    void updateAdapter(List<Lembrete> lembretes);
}
