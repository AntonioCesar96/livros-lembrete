package livroslembrete.com.br.livroslembrete.presenter;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;

import livroslembrete.com.br.livroslembrete.view.VizualizarImageView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class VizualizarImagePresenter {
    private VizualizarImageView view;

    public VizualizarImagePresenter(VizualizarImageView view) {
        this.view = view;
    }

    public Context getContext() {
        return (Context) view;
    }

    public void updateTitleToolbar(ActionBar actionBar, String nome) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (nome != null) {
                actionBar.setTitle(nome);
            }
        }
    }

    public PhotoViewAttacher updateImage(PhotoViewAttacher mAttacher, ImageView imgView) {
        if (mAttacher != null) {
            mAttacher.update();
        } else {
            mAttacher = new PhotoViewAttacher(imgView);
        }
        return mAttacher;
    }
}
