package livroslembrete.com.br.livroslembrete.view;

import android.content.Intent;

public interface BaseView {
    void showToast(String msg);
    void showAlert(String title, String msg);
    void openActivity(Class<?> activity);
    void showProgressDialog(String title, String msg);
    void closeProgressDialog();
    void setResultActivity(int code, Intent intent);
    void setResultActivity(int code);
    void startActivity(Intent chooser);
    void startActivityForResult(Intent intent, int atualizarDados);
}
