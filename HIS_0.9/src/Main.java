import ui.HomeFrame;
import ui.Login;
import util.JDBCUtil;

public class Main {
	public static void main(String[] args) {
		new Login();
		JDBCUtil.connect();
	//	new HomeFrame().setVisible(true);
	//	new MedRecord("11111");
	//	new ExportMed("导出模板哈哈哈哈哈哈");
	//	new ImportMed("导入模板");
	//	new Med("1110");
		
/*		
		String s = "2018-11-12 00:00:00.0";
		Timestamp t = Timestamp.valueOf(s);
		SimpleDateFormat sdf = new  SimpleDateFormat("yyyy.MM.dd");
		System.out.println(sdf.format(t));*/
	}

}
