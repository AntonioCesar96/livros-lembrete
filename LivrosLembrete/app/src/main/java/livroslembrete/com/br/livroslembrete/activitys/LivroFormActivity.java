package livroslembrete.com.br.livroslembrete.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.models.Livro;
import livroslembrete.com.br.livroslembrete.models.ResponseWithURL;
import livroslembrete.com.br.livroslembrete.models.Usuario;
import livroslembrete.com.br.livroslembrete.services.LivroService;
import livroslembrete.com.br.livroslembrete.utils.AlertUtils;
import livroslembrete.com.br.livroslembrete.utils.AndroidUtils;
import livroslembrete.com.br.livroslembrete.utils.CameraUtils;
import livroslembrete.com.br.livroslembrete.utils.ImageUtils;

public class LivroFormActivity extends BaseActivity {
    private EditText txtNome, txtTotalPaginas;
    private ImageButton btnGaleria, btnCamera;
    private ImageView img;
    private ProgressDialog progressDialog;
    private CameraUtils cameraUtil;
    private File fileImage = null;
    private Livro livro = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros_form);
        setUpToolbar();
        setUpNavigation();

        cameraUtil = new CameraUtils(LivroFormActivity.this);
        txtNome = findViewById(R.id.txtNome);
        txtTotalPaginas = findViewById(R.id.txtTotalPaginas);
        btnGaleria = findViewById(R.id.btnGaleria);
        btnCamera = findViewById(R.id.btnCamera);
        img = findViewById(R.id.img);

        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraUtil.abrirGaleria(LivroFormActivity.this);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraUtil.abrirCamera(LivroFormActivity.this);
            }
        });

        livro = (Livro) getIntent().getSerializableExtra("livro");
        Usuario usuario = Application.getInstance().getUsuario();
        if (livro != null) {
            livro.setUsuario(usuario);
            txtNome.setText(livro.getNome());
            txtTotalPaginas.setText("" + livro.getTotalPaginas());
            ImageUtils.setImagemIndividual(this, livro.getUrlImagem(), img, null, null);
        } else {
            livro = new Livro();
            livro.setUsuario(usuario);
        }
    }

    public void salvar(View view) {
        if (txtNome.getText().toString().trim().length() == 0) {
            txtNome.setError("Digite um e-mail");
            return;
        }

        if (txtTotalPaginas.getText().toString().trim().length() == 0) {
            txtTotalPaginas.setError("Digite uma senha");
            return;
        }

        livro.setNome(txtNome.getText().toString());
        livro.setTotalPaginas(Integer.parseInt(txtTotalPaginas.getText().toString()));

        if (AndroidUtils.isNetworkAvailable(getApplicationContext())) {
            new LivroTask(livro).execute();
        } else {
            AlertUtils.alert(LivroFormActivity.this, "Alerta", "Conexão com a internet indisponível!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.fileImage = cameraUtil.pegarImagem(requestCode, resultCode, data, img);
    }

    private class LivroTask extends AsyncTask<Void, Void, Livro> {
        private Livro livro;

        public LivroTask(Livro livro) {
            this.livro = livro;
        }

        @Override
        protected void onPreExecute() {
            if (livro.getId() != null) {
                progressDialog = ProgressDialog.show(LivroFormActivity.this, "Atualizando", "Realizando a atualização dos dados do livro, aguarde...", false, false);
            } else {
                progressDialog = ProgressDialog.show(LivroFormActivity.this, "Cadastrando", "Realizando o cadastro do livro, aguarde...", false, false);
            }
        }

        @Override
        protected Livro doInBackground(Void... voids) {
            LivroService service = new LivroService();

            try {
                if (fileImage != null && fileImage.exists()) {
                    ResponseWithURL responseWithURL = service.postFotoBase64(fileImage);
                    if (responseWithURL != null && responseWithURL.isOk()) {
                        livro.setUrlImagem(responseWithURL.getUrl());
                    }
                }
                return service.save(livro);
            } catch (Exception e) {
                Log.e("ERRO", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(Livro livro) {
            progressDialog.dismiss();
            if (livro != null && livro.getId() != null) {
                if (LivroFormActivity.this.livro.getId() != null) {
                    Intent intent = new Intent();
                    intent.putExtra("livro", livro);
                    setResult(LivroDetalhesActivity.ATUALIZAR_DADOS, intent);
                    finish();
                } else {
                    startActivity(new Intent(LivroFormActivity.this, MainActivity.class));
                    finish();
                }
            } else {
                AlertUtils.alert(LivroFormActivity.this, "Alerta", "Aconteceu algum erro desconhecido!");
            }
        }
    }

    @Override
    public void finish() {
        if (fileImage != null && fileImage.exists()) {
            fileImage.delete();
        }
        super.finish();
    }
}
