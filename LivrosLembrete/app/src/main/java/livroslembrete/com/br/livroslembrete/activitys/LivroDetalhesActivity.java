package livroslembrete.com.br.livroslembrete.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.models.Livro;
import livroslembrete.com.br.livroslembrete.utils.ImageUtils;

public class LivroDetalhesActivity extends BaseActivity {
    private TextView txtNome, txtTotalPaginas;
    private ImageView appBarImg;
    private CollapsingToolbarLayout collapsingToolbar;
    private ProgressBar progressBar;
    private FloatingActionButton fabLembrete;
    private Livro livro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros_detalhes);
        setUpToolbar();
        setUpNavigation();

        fabLembrete = findViewById(R.id.fab);
        collapsingToolbar = findViewById(R.id.collapsingToolbar);
        progressBar = findViewById(R.id.progressImg);
        appBarImg = findViewById(R.id.appBarImg);
        txtNome = findViewById(R.id.txtNome);
        txtTotalPaginas = findViewById(R.id.txtTotalPaginas);

        fabLembrete.setOnClickListener(clickFab());
        appBarImg.setOnClickListener(clickImg());

        livro = (Livro) getIntent().getSerializableExtra("livro");

        collapsingToolbar.setTitle(livro.getNome());
        txtNome.setText(livro.getNome());
        txtTotalPaginas.setText("" + livro.getTotalPaginas());
        ImageUtils.setImagemIndividual(this, livro.getUrlImagem(), appBarImg, progressBar, collapsingToolbar);
    }

    private View.OnClickListener clickFab() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Adicionar lembrete", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        };
    }

    private View.OnClickListener clickImg() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LivroDetalhesActivity.this, PhotoViewActivity.class);
                intent.putExtra("url", livro.getUrlImagem());
                intent.putExtra("nome", livro.getNome());
                startActivity(intent);
            }
        };
    }
}
