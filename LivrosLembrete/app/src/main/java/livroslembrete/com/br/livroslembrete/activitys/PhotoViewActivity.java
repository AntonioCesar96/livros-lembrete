package livroslembrete.com.br.livroslembrete.activitys;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import livroslembrete.com.br.livroslembrete.R;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoViewActivity extends BaseActivity {
    private ImageView imgView;
    private ProgressBar progress;
    private PhotoViewAttacher mAttacher;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        setUpToolbar();

        imgView = findViewById(R.id.img);
        progress = findViewById(R.id.progressImg);

        ActionBar actionBar = getSupportActionBar();
        String nome = (String) getIntent().getSerializableExtra("nome");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

            if (nome != null) {
                actionBar.setTitle(nome);
            }
        }

        url = (String) getIntent().getSerializableExtra("url");
        loadImage(url);
    }

    private void loadImage(String url) {
        progress.setVisibility(View.VISIBLE);
        Picasso.with(PhotoViewActivity.this).load(url).into(imgView, new Callback() {
            @Override
            public void onSuccess() {
                progress.setVisibility(View.GONE);
                if (mAttacher != null) {
                    mAttacher.update();
                } else {
                    mAttacher = new PhotoViewAttacher(imgView);
                }
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
