public class Date {
  private String nome;
  private String descricao;
  private Presente presente;
  private Roles roleIdeal;

  public String getNome() {
    return nome;
  }

  public Date(String nome, String descricao, Presente presente, Roles roleIdeal) {
    this.nome = nome;
    this.descricao = descricao;
    this.presente = presente;
    this.roleIdeal = roleIdeal;
  }

  public Roles getRoleIdeal() {
    return roleIdeal;
  }

  public void setRoleIdeal(Roles roleIdeal) {
    this.roleIdeal = roleIdeal;
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
}
