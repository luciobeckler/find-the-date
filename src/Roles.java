public class Roles {
  private String nome;
  private String descricao;
  private Presente presente;

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public Presente getPresente() {
    return presente;
  }

  public void setPresente(Presente presente) {
    this.presente = presente;
  }

  public Roles(String nome, String descricao, Presente presente) {
    this.nome = nome;
    this.descricao = descricao;
    this.presente = presente;
  }
}
