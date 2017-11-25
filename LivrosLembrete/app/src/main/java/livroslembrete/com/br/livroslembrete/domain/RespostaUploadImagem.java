package livroslembrete.com.br.livroslembrete.domain;

public class RespostaUploadImagem {
    private String status;
    private String msg;
    private String url;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String result) {
        this.url = result;
    }

    @Override
    public String toString() {
        return "RespostaUploadImagem{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public boolean isOk() {
        return "OK".equalsIgnoreCase(status);
    }
}
