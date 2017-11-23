package livroslembrete.com.br.livroslembrete.utils;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageUtils {

    public static void setImagemFeed(final Context context, String url_img, final ImageView img, final LinearLayout imgEventoWrapper, final ProgressBar progress) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        final int largura = metrics.widthPixels;
        final int altura = largura / 2;

        img.getLayoutParams().width = largura;
        img.getLayoutParams().height = altura;

        if (url_img != null && url_img.trim().length() > 0 && URLUtil.isValidUrl(url_img)) {
            progress.setVisibility(View.VISIBLE);
            Picasso.with(context).load(url_img).resize(largura, altura).centerCrop().into(img, new Callback() {
                @Override
                public void onSuccess() {
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    progress.setVisibility(View.GONE);
                    imgEventoWrapper.setVisibility(View.GONE);
                }
            });
        } else {
            imgEventoWrapper.setVisibility(View.GONE);
        }
    }

    public static void setImagemIndividual(final AppCompatActivity activity, String url_img, final ImageView img,
                                           final ProgressBar progress, final CollapsingToolbarLayout c) {

        if (url_img != null && url_img.trim().length() > 0 && URLUtil.isValidUrl(url_img)) {

            DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
            final int largura = metrics.widthPixels;
            final int altura = img.getLayoutParams().height;

            img.getLayoutParams().width = largura;
            img.getLayoutParams().height = altura;

            progress.setVisibility(View.VISIBLE);
            Picasso.with(activity).load(url_img).resize(largura, altura).centerCrop().into(img, new Callback() {
                @Override
                public void onSuccess() {
                    progress.setVisibility(View.GONE);

                    ColorsUtils.changeColorToolbar(img, activity, c);
                }

                @Override
                public void onError() {
                    progress.setVisibility(View.GONE);
                }
            });

        }
    }


}
