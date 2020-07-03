package jp.co.sample.emp_management.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 従業員情報登録時に使用するフォーム.
 * 
 * @author yu.konishi
 *
 */
public class InsertEmployeeForm {

	/** 従業員名 */
	@NotBlank(message="名前を入力してください")
	private String name;
	/** 画像 */
	@Pattern(regexp="^.+\\.png$|^.+\\.jpg$", message="画像を選択してください")
	private String image;
	/** 性別 */
	@NotEmpty(message="性別を選択してください")
	private String gender;
	/** 入社日 */
	@NotEmpty(message="入社日を選択してください")
	private String hireDate;
	/** メールアドレス */
	@NotBlank(message="名前を入力してください")
	@Email(message = "メールアドレスが不正です")
	private String mailAddress;
	/** 郵便番号 */
	@Pattern(regexp="^[0-9]{3}-[0-9]{4}$", message="郵便番号を入力してください")
	private String zipCode;
	/** 住所 */
	@NotBlank(message="住所を入力してください")
	private String address;
	/** 電話番号 */
	@Pattern(regexp="^0[1-9]0,\\d{4},\\d{4}$|^0[1-9]{1,4},\\d{1,4},\\d{4}$", message="電話番号を入力してください")
	private String telephone;
	/** 給料 */
	@Pattern(regexp="^[1-9][0-9]+$|0", message="給料は数値で入力してください")
	private String salary;
	/** 特性 */
	@NotBlank(message="特徴を入力してください")
	private String characteristics;
	/** 扶養人数 */
	@Pattern(regexp="^[0-9]+$", message="扶養人数は数値で入力してください")
	private String dependentsCount;
	@Override
	public String toString() {
		return "InsertEmployeeForm [name=" + name + ", image=" + image + ", gender=" + gender + ", hireDate=" + hireDate
				+ ", mailAddress=" + mailAddress + ", zipCode=" + zipCode + ", address=" + address + ", telephone="
				+ telephone + ", salary=" + salary + ", characteristics=" + characteristics + ", dependentsCount="
				+ dependentsCount + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHireDate() {
		return hireDate;
	}
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getCharacteristics() {
		return characteristics;
	}
	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}
	public String getDependentsCount() {
		return dependentsCount;
	}
	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}
	
}
