package livroslembrete.com.br.livroslembrete.view.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.model.domain.Livro;
import livroslembrete.com.br.livroslembrete.presenter.LivroFormPresenter;
import livroslembrete.com.br.livroslembrete.model.utils.CameraUtils;
import livroslembrete.com.br.livroslembrete.model.utils.ImageUtils;
import livroslembrete.com.br.livroslembrete.view.LivroFormView;

public class LivroFormActivity extends BaseActivity implements LivroFormView {
    private EditText txtNome, txtTotalPaginas;
    private ImageView img;
    private CameraUtils cameraUtil;
    private LivroFormPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livros_form);
        setUpToolbar();
        setUpNavigation();

        initFields();

        Livro livro = (Livro) getIntent().getSerializableExtra("livro");
        presenter = new LivroFormPresenter(this);
        presenter.setLivro(livro);

        cameraUtil = new CameraUtils(this);
    }

    private void initFields() {
        txtNome = findViewById(R.id.txtNome);
        txtTotalPaginas = findViewById(R.id.txtTotalPaginas);
        img = findViewById(R.id.img);
    }

    public void save(View view) {
        presenter.save(txtNome.getText().toString(), txtTotalPaginas.getText().toString());
    }

    public void openCamera(View view) {
        cameraUtil.abrirCamera(LivroFormActivity.this);
    }

    public void openGaleria(View view) {
        cameraUtil.abrirGaleria(LivroFormActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File fileImage = cameraUtil.pegarImagem(requestCode, resultCode, data, img);
        presenter.setFileImage(fileImage);
    }

    @Override
    public void updateEditsTextAndImage(Livro livro) {
        txtNome.setText(livro.getNome());
        txtTotalPaginas.setText("" + livro.getTotalPaginas());
        ImageUtils.setImagemIndividual(this, livro.getUrlImagem(), img, null, null);
    }

    @Override
    public void finish() {
        presenter.deleteImage();
        super.finish();
    }
}
