package jp.co.sample.emp_management.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.emp_management.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author igamasayuki
 * 
 */
@Repository
public class EmployeeRepository {

	/**
	 * Employeeオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		return employee;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 従業員一覧情報を入社日順で取得します.
	 * 
	 * @return 全従業員一覧 従業員が存在しない場合はサイズ0件の従業員一覧を返します
	 */
	public List<Employee> findAll() {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees ORDER BY hire_date;";

		List<Employee> developmentList = template.query(sql, EMPLOYEE_ROW_MAPPER);

		return developmentList;
	}

	/**
	 * 主キーから従業員情報を取得します.
	 * 
	 * @param id 検索したい従業員ID
	 * @return 検索された従業員情報
	 * @exception 従業員が存在しない場合は例外を発生します
	 */
	public Employee load(Integer id) {
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees WHERE id=:id";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		Employee development = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);

		return development;
	}

	/**
	 * 従業員情報を変更します.
	 */
	public void update(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);

		String updateSql = "UPDATE employees SET dependents_count=:dependentsCount WHERE id=:id";
		template.update(updateSql, param);
	}
	
	/**
	 * 検索ワードが名前に含まれている従業員情報を取得します.
	 * @param searchWord 検索ワード
	 * @return 該当した従業員情報　従業員が存在しない場合はサイズ0件の従業員一覧を返します
	 */
	public List<Employee> findBySearchWord(String searchWord){
		searchWord = "%" + searchWord + "%";
		String sql = "SELECT id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count FROM employees WHERE name LIKE :searchWord ORDER BY hire_date;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("searchWord", searchWord);
		List<Employee> employeeList = template.query(sql, param,EMPLOYEE_ROW_MAPPER);
		return employeeList;
	}
	
	/**
	 * 従業員情報を挿入します.
	 * 
	 * @param employee 従業員情報
	 */
	public void insert(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		String sql = "insert into employees(id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count)values(:id,:name,:image,:gender,:hireDate,:mailAddress,:zipCode,:address,:telephone,:salary,:characteristics,:dependentsCount);";
		template.update(sql, param);
	}
	
	/**
	 * 従業員情報の数を取得します.
	 * 
	 * @return 従業員情報の数	
	 */
	public Integer getMaxId() {
		String sql = "SELECT MAX(id) FROM employees;";
		SqlParameterSource param = new MapSqlParameterSource();
		Integer dataSize = template.queryForObject(sql, param, Integer.class);
		return dataSize;
	}
	
	/**
	 * メールアドレスから管理者情報を取得します.
	 * 
	 * @param mailAddress メールアドレス
	 * @return 従業員情報 存在しない場合はnullを返します
	 */
	public Employee findByMailAddress(String mailAddress) {
		String sql = "select id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count from employees where mail_address=:mailAddress";
		SqlParameterSource param = new MapSqlParameterSource().addValue("mailAddress", mailAddress);
		List<Employee> employeeList = template.query(sql, param, EMPLOYEE_ROW_MAPPER);
		if (employeeList.size() == 0) {
			return null;
		}
		return employeeList.get(0);
	}
}
