package livroslembrete.com.br.livroslembrete.presenter;

import android.content.Context;
import android.content.Intent;

import livroslembrete.com.br.livroslembrete.view.BaseView;

public class BasePresenter {
    private BaseView view;

    public BasePresenter(BaseView view) {
        this.view = view;
    }

    public Context getContext() {
        return (Context) view;
    }

    public void showAlert(String title, String msg) {
        view.showAlert(title, msg);
    }

    public void openActivity(Class<?> activity) {
        view.openActivity(activity);
    }

    public void showProgressDialog(String title, String msg) {
        view.showProgressDialog(title, msg);
    }

    public void closeProgressDialog() {
        view.closeProgressDialog();
    }

    public void setResultActivity(int code, Intent intent) {
        view.setResultActivity(code, intent);
    }

    public void setResultActivity(int code) {
        view.setResultActivity(code);
    }
}
