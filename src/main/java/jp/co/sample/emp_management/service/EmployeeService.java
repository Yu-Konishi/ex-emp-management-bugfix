package jp.co.sample.emp_management.service;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.form.InsertEmployeeForm;
import jp.co.sample.emp_management.repository.EmployeeRepository;

/**
 * 従業員情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * 従業員情報を全件取得します.
	 * 
	 * @return　従業員情報一覧
	 */
	public List<Employee> showList() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList;
	}
	
	/**
	 * 従業員情報を取得します.
	 * 
	 * @param id ID
	 * @return 従業員情報
	 * @throws 検索されない場合は例外が発生します
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}
	
	/**
	 * 従業員情報を更新します.
	 * 
	 * @param employee　更新した従業員情報
	 */
	public void update(Employee employee) {
		employeeRepository.update(employee);
	}
	
	/**
	 * 検索ワードが含まれる名前の従業員情報を取得します.
	 * @param searchWord 検索ワード
	 * @return　該当した従業員情報　検索ワードが空文字の場合は全従業員情報
	 */
	public List<Employee> searchName(String searchWord){
		List<Employee> employeeList;
		if(searchWord.isEmpty()) {
			employeeList = employeeRepository.findAll();
		} else {
			employeeList = employeeRepository.findBySearchWord(searchWord);
		}
		return employeeList;
	}
	
	/**
	 * 従業員情報を登録します.
	 * 
	 * @param employee　従業員情報
	 */
	public void insert(InsertEmployeeForm form) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(form, employee);
		Integer dataSize = employeeRepository.getMaxId();
		employee.setId(dataSize + 1);
		employee.setHireDate(Date.valueOf(form.getHireDate()));
		employee.setTelephone(form.getTelephone().replace(",", "-"));
		employee.setSalary(Integer.parseInt(form.getSalary()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		
		File file = new File(".\\src\\main\\resources\\static\\img\\" + form.getImage());
		try {
			BufferedImage image = ImageIO.read(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(baos);
			image.flush();
			String base64Image = "";
			if(Pattern.matches("^.*\\.png$", form.getImage())) {
				base64Image += "data:image/png;base64,";
				ImageIO.write(image, "png", bos);
			} else if(Pattern.matches("^.*\\.jpg$", form.getImage())) {
				base64Image += "data:image/jpeg;base64,";
				ImageIO.write(image, "jpg", bos);
			}
			bos.flush();
			bos.close();
			byte[] byteImage = baos.toByteArray();			
			Base64 base64 = new Base64();
			byte[] encoded = base64.encode(byteImage);
			base64Image = base64Image + new String(encoded);
			employee.setImage(base64Image);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		employeeRepository.insert(employee);
	}
	
	/**
	 * 既にメールアドレスが登録されているかチェックします.
	 * @param mailAddress メールアドレス
	 * @return　従業員情報 　存在しない場合はnullが返ります
	 */
	public Employee checkMailAddress(String mailAddress) {
		Employee employee = employeeRepository.findByMailAddress(mailAddress);
		return employee;
	}
}
