package jp.co.sample.emp_management.controller;

import java.sql.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.form.InsertEmployeeForm;
import jp.co.sample.emp_management.form.UpdateEmployeeForm;
import jp.co.sample.emp_management.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpForm() {
		return new UpdateEmployeeForm();
	}

	/**
	 * 使用する登録フォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return 登録フォーム
	 */
	@ModelAttribute
	public InsertEmployeeForm setUpInsertForm() {
		return new InsertEmployeeForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員一覧画面を出力します.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 * 
	 * @param id    リクエストパラメータで送られてくる従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を更新する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細(ここでは扶養人数のみ)を更新します.
	 * 
	 * @param form 従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@RequestMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return showDetail(form.getId(), model);
		}
		Employee employee = new Employee();
		employee.setId(form.getIntId());
		employee.setDependentsCount(form.getIntDependentsCount());
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}

	/**
	 * 検索ワードが含まれる名前の従業員情報を取得します.
	 * 
	 * @param searchWord 検索ワード
	 * @param model      リクエストスコープ
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/searchName")
	public String searchName(String searchWord, Model model) {
		List<Employee> employeeList = employeeService.searchName(searchWord);
		if (employeeList.size() == 0) {
			employeeList = employeeService.showList();
			model.addAttribute("error", "1件もありませんでした");
		}
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}

	/**
	 * 従業員登録画面を出力します.
	 * 
	 * @return 従業員登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "employee/insert";
	}

	/**
	 * 従業員情報を登録します.
	 * 
	 * @param form   従業員情報用フォーム
	 * @param result 入力チェックのエラーを取得
	 * @return 従業員一覧画面へリダイレクト
	 */
	@RequestMapping("/insert")
	public synchronized String insert(@Validated InsertEmployeeForm form, BindingResult result) {
		String telephone = form.getTelephone().replace(",", "-");
		if (telephone != null) {
			if (!Pattern.matches("^0[1-9]0.*", telephone) && telephone.replace("-", "").length() != 10) {
				if (!result.hasFieldErrors("telephone")) {
					result.addError(new FieldError(result.getObjectName(), "telephone", "電話番号が不正です"));
				}
			}
		}
		if (form.getSalary().length() > 6 || Integer.parseInt(form.getSalary()) > 500000) {
			if (!result.hasFieldErrors("salary")) {
				result.addError(new FieldError(result.getObjectName(), "salary", "給料は50万円以下で入力してください"));
			}
		}
		if (result.hasErrors()) {
			return toInsert();
		}
		Employee employee = new Employee();
		BeanUtils.copyProperties(form, employee);
		Integer dataSize = employeeService.getDataSize();
		employee.setId(dataSize + 1);
		employee.setHireDate(Date.valueOf(form.getHireDate()));
		employee.setTelephone(telephone);
		employee.setSalary(Integer.parseInt(form.getSalary()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		employeeService.insert(employee);
		return "redirect:/employee/showList";
	}
}
