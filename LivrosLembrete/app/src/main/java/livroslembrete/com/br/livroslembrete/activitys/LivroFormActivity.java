package livroslembrete.com.br.livroslembrete.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import livroslembrete.com.br.livroslembrete.R;

public class LivroFormActivity extends BaseActivity {
    private EditText txtNome, txtTotalPaginas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro_form);
        setUpToolbar();
        setUpNavigation();

        txtNome = findViewById(R.id.txtNome);
        txtTotalPaginas = findViewById(R.id.txtTotalPaginas);
    }

    public void salvar(View view) {
        String nome = txtNome.getText().toString();
        String totalPaginas = txtTotalPaginas.getText().toString();

        Toast.makeText(getBaseContext(), nome + " / " + totalPaginas, Toast.LENGTH_SHORT).show();
    }
}
