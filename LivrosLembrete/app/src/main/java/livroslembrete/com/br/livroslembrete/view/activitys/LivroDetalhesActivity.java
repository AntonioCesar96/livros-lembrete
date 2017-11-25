package livroslembrete.com.br.livroslembrete.view.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.domain.Lembrete;
import livroslembrete.com.br.livroslembrete.domain.Livro;
import livroslembrete.com.br.livroslembrete.presenter.LivroDetalhesPresenter;
import livroslembrete.com.br.livroslembrete.utils.ImageUtils;
import livroslembrete.com.br.livroslembrete.view.LivroDetalhesView;

public class LivroDetalhesActivity extends BaseActivity implements LivroDetalhesView {
    private TextView txtNome, txtTotalPaginas;
    private ImageView appBarImg;
    private CollapsingToolbarLayout collapsingToolbar;
    private ProgressBar progressBar;
    private LivroDetalhesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros_detalhes);
        setUpToolbar();
        setUpNavigation();
        initFields();

        Livro livro = (Livro) getIntent().getSerializableExtra("livro");
        Lembrete lembrete = (Lembrete) getIntent().getSerializableExtra("lembrete");

        presenter = new LivroDetalhesPresenter(this);
        presenter.setLivro(livro, lembrete);
        updateView();
    }

    private void initFields() {
        collapsingToolbar = findViewById(R.id.collapsingToolbar);
        progressBar = findViewById(R.id.progressImg);
        appBarImg = findViewById(R.id.appBarImg);
        txtNome = findViewById(R.id.txtNome);
        txtTotalPaginas = findViewById(R.id.txtTotalPaginas);
    }

    private void updateView() {
        Livro livro = presenter.getLivro();
        collapsingToolbar.setTitle(livro.getNome());
        txtNome.setText(livro.getNome());
        txtTotalPaginas.setText("" + livro.getTotalPaginas());
        ImageUtils.setImagemIndividual(this, livro.getUrlImagem(), appBarImg, progressBar, collapsingToolbar);
    }

    public void criarAlarm(View view) {
        presenter.criarAlarm(getSupportFragmentManager());
    }

    public void openImage(View view) {
        presenter.openImage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_livro_detalhes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.onOptionsItemSelected(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        presenter.deleteImage();
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Livro livro = (Livro) data.getSerializableExtra("livro");
        presenter.onActivityResult(resultCode, livro);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void initView(Livro livro, Lembrete lembrete) {
        presenter.setLivro(livro, lembrete);
        updateView();
    }
}
