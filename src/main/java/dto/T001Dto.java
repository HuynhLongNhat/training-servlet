package dto;

public class T001Dto {
  private String userId ;
  private String password;
  
  public T001Dto() { }
  
  public T001Dto(String userId, String password) {
	
	this.userId = userId;
	this.password = password;
}
  public String getUserId() {
	return userId;
  }
  public void setUserId(String userId) {
	this.userId = userId;
  }
  public String getPassword() {
	return password;
  }
  public void setPassword(String password) {
	this.password = password;
  }
  
}
