package livroslembrete.com.br.livroslembrete.activitys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.SQLException;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.dao.DataBaseHelper;
import livroslembrete.com.br.livroslembrete.dao.UsuarioDAO;
import livroslembrete.com.br.livroslembrete.models.Livro;
import livroslembrete.com.br.livroslembrete.models.Usuario;
import livroslembrete.com.br.livroslembrete.services.LivroService;
import livroslembrete.com.br.livroslembrete.services.UsuarioService;
import livroslembrete.com.br.livroslembrete.utils.AlertUtils;
import livroslembrete.com.br.livroslembrete.utils.ImageUtils;

public class LivroDetalhesActivity extends BaseActivity {
    public static final int ATUALIZAR_DADOS = 6;

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

        livro = (Livro) getIntent().getSerializableExtra("livro");
        preencherDadosLivro();
    }

    private void preencherDadosLivro() {
        collapsingToolbar.setTitle(livro.getNome());
        txtNome.setText(livro.getNome());
        txtTotalPaginas.setText("" + livro.getTotalPaginas());

        if (livro.getUrlImagem() != null && livro.getUrlImagem().trim().length() > 0 && URLUtil.isValidUrl(livro.getUrlImagem())) {
            appBarImg.setOnClickListener(clickImg());
            ImageUtils.setImagemIndividual(this, livro.getUrlImagem(), appBarImg, progressBar, collapsingToolbar);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_livro_detalhes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(LivroDetalhesActivity.this, LivroFormActivity.class);
                intent.putExtra("livro", livro);
                startActivityForResult(intent, LivroDetalhesActivity.ATUALIZAR_DADOS);
                break;
            case R.id.action_delete:
                abrirDialogExcluir();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void abrirDialogExcluir() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LivroDetalhesActivity.this);
        builder.setTitle("Alerta");
        builder.setMessage("Você realmente deseja excluir?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new LivroExcluirTask().execute(livro.getId());
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private class LivroExcluirTask extends AsyncTask<Long, Void, Long> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LivroDetalhesActivity.this, "Excluindo", "Excluindo livro, aguarde...", false, false);
        }

        @Override
        protected Long doInBackground(Long... longs) {
            try {
                new LivroService().delete(longs[0]);
                return 1L;
            } catch (Exception e) {
                Log.e("ERRO", e.getMessage(), e);
                return 0L;
            }
        }

        @Override
        protected void onPostExecute(Long cod) {
            if (cod == 0) {
                AlertUtils.alert(LivroDetalhesActivity.this, "Alerta", "Aconteceu um erro ao tentar excluir o livro!");
            } else {
                progressDialog.dismiss();
                setResult(MainActivity.RECRIAR_ACTIVITY);
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == LivroDetalhesActivity.ATUALIZAR_DADOS) {
            livro = (Livro) data.getSerializableExtra("livro");
            preencherDadosLivro();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
