package livroslembrete.com.br.livroslembrete.view.activitys;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.presenter.VizualizarImagePresenter;
import livroslembrete.com.br.livroslembrete.view.VizualizarImageView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class VizualizarImageActivity extends BaseActivity implements VizualizarImageView {
    private ImageView imgView;
    private ProgressBar progress;
    private PhotoViewAttacher mAttacher;
    private String url;
    private VizualizarImagePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualizar_imagem);
        setUpToolbar();

        imgView = findViewById(R.id.img);
        progress = findViewById(R.id.progressImg);

        presenter = new VizualizarImagePresenter(this);

        String nome = (String) getIntent().getSerializableExtra("nome");
        presenter.updateTitleToolbar(getSupportActionBar(), nome);

        url = (String) getIntent().getSerializableExtra("url");
        loadImage(url);
    }

    private void loadImage(String url) {
        progress.setVisibility(View.VISIBLE);
        Picasso.with(this).load(url).into(imgView, new Callback() {
            @Override
            public void onSuccess() {
                progress.setVisibility(View.GONE);
                mAttacher = presenter.updateImage(mAttacher, imgView);
            }

            @Override
            public void onError() {
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
