package livroslembrete.com.br.livroslembrete.presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import livroslembrete.com.br.livroslembrete.Application;
import livroslembrete.com.br.livroslembrete.R;
import livroslembrete.com.br.livroslembrete.dao.LembreteDAO;
import livroslembrete.com.br.livroslembrete.domain.DiasSemana;
import livroslembrete.com.br.livroslembrete.utils.DespertadorUtil;
import livroslembrete.com.br.livroslembrete.view.fragments.dialog.CriarLembreteDialog;
import livroslembrete.com.br.livroslembrete.model.LivroDetalhesModel;
import livroslembrete.com.br.livroslembrete.domain.Lembrete;
import livroslembrete.com.br.livroslembrete.domain.Livro;
import livroslembrete.com.br.livroslembrete.utils.AlarmLembreteUtil;
import livroslembrete.com.br.livroslembrete.view.LivroDetalhesView;
import livroslembrete.com.br.livroslembrete.view.activitys.LivroFormActivity;
import livroslembrete.com.br.livroslembrete.view.activitys.VizualizarImageActivity;

public class LivroDetalhesPresenter extends BasePresenter {
    public static final int ATUALIZAR_DADOS = 6;
    private LivroDetalhesModel model;
    private LivroDetalhesView view;
    private Livro livro;
    private File imagemShare;

    public LivroDetalhesPresenter(LivroDetalhesView view) {
        super(view);
        this.view = view;
        model = new LivroDetalhesModel(this);
    }

    public void criarAlarm(FragmentManager supportFragmentManager) {
        Lembrete lembrete = getLembreteByLivro();

        CriarLembreteDialog.show(supportFragmentManager, lembrete, new CriarLembreteDialog.Callback() {
            @Override
            public void callback(Calendar dataHora, List<DiasSemana> diasSemanaSelecionados) {
                LembreteDAO dao = null;
                try {
                    dao = new LembreteDAO(Application.getInstance().getDataBaseHelper().getConnectionSource());

                    Lembrete lembrete = new Lembrete();
                    lembrete.setDataHora(dataHora);
                    lembrete.setIdLivro(livro.getId());
                    lembrete.setNomeLivro(livro.getNome());
                    lembrete.setTotalPaginasLivro(livro.getTotalPaginas());
                    lembrete.setUrlImagemLivro(livro.getUrlImagem());
                    lembrete.setDiasSemana(diasSemanaSelecionados);

                    dao.save(lembrete);
                    lembrete = dao.getById(lembrete.getIdLivro());

                    new DespertadorUtil(getContext()).criarAlarm(lembrete, lembrete.getDiasSemana());

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Lembrete getLembreteByLivro() {
        LembreteDAO dao = null;
        try {
            dao = new LembreteDAO(Application.getInstance().getDataBaseHelper().getConnectionSource());
            return dao.getById(livro.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private LivroDetalhesModel.DownloadImagemTask.CallbackDownloadImagem onCallbackDownloadImagem() {
        return new LivroDetalhesModel.DownloadImagemTask.CallbackDownloadImagem() {
            @Override
            public void onCallbackDownloadImagem(File imagem) {
                imagemShare = imagem;
                Uri uri = Uri.fromFile(imagem);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.putExtra(Intent.EXTRA_TEXT, livro.getNome());
                shareIntent.setType("image/*");

                Intent chooser = Intent.createChooser(shareIntent, "Compartilhar Livro");
                view.startActivity(chooser);
            }
        };
    }

    public void deleteImage() {
        if (imagemShare != null && imagemShare.exists()) {
            imagemShare.delete();
        }
    }

    public void setLivro(Livro livro, Lembrete lembrete) {
        if (livro == null) {
            livro = new Livro();
            livro.setId(lembrete.getIdLivro());
            livro.setNome(lembrete.getNomeLivro());
            livro.setTotalPaginas(lembrete.getTotalPaginasLivro());
            livro.setUrlImagem(lembrete.getUrlImagemLivro());
        }

        this.livro = livro;
    }

    public Livro getLivro() {
        return livro;
    }

    public void openImage() {
        if (livro.getUrlImagem() != null) {
            Intent intent = new Intent(getContext(), VizualizarImageActivity.class);
            intent.putExtra("url", livro.getUrlImagem());
            intent.putExtra("nome", livro.getNome());
            view.startActivity(intent);
        }
    }

    public void onOptionsItemSelected(int itemId) {
        switch (itemId) {
            case R.id.action_edit:
                Intent intent = new Intent(getContext(), LivroFormActivity.class);
                intent.putExtra("livro", livro);
                view.startActivityForResult(intent, ATUALIZAR_DADOS);
                break;
            case R.id.action_delete:
                openDialogExcluir();
                break;
            case R.id.action_share:
                shareImage();
                break;
        }
    }

    public void shareImage() {
        new LivroDetalhesModel.DownloadImagemTask(getContext(), onCallbackDownloadImagem()).execute(livro.getUrlImagem());
    }

    public void openDialogExcluir() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Alerta");
        builder.setMessage("Você realmente deseja excluir?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                model.deletarLivro(livro.getId());
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

    public void onActivityResult(int resultCode, Livro livro) {
        if (resultCode == ATUALIZAR_DADOS) {
            view.initView(livro, null);
        }
    }
}
